package edu.wpi.teamF.ModelClasses.Directions;

import edu.wpi.teamF.Controllers.com.twilio.phoneComms;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.util.*;
import javafx.print.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Directions {
  private List<Direction> directionList = new ArrayList<>();
  private EuclideanScorer scorer = new EuclideanScorer();
  private final Map<String, Node> nodeMap = new HashMap<>();
  private Node startNode;
  private Node endNode;
  private phoneComms phoneComms = new phoneComms();

  public Directions(List<Node> fullNodeList, Path path, Node startNode, Node endNode) {
    for (Node node : fullNodeList) {
      nodeMap.put(node.getId(), node);
    }
    this.startNode = startNode;

    List<Node> pathNodeList = path.getPath();
    endNode = pathNodeList.get(pathNodeList.size() - 1);
    this.endNode = endNode;

    System.out.println("Last in list: " + pathNodeList.get(pathNodeList.size() - 1).getId());
    System.out.println("Size of lsit: " + pathNodeList.size());

    System.out.println("Start Node: " + startNode.getId());
    System.out.println("End Node: " + endNode.getId());

    if (pathNodeList.size() < 3) {
      // Very short route
      directionList.add(
          new ProceedDirection(endNode.getLongName(), pathNodeList.get(0).getFloor()));
    } else {
      // Create the starting directions
      int j = 1;
      if (Node.NodeType.getEnum("EXIT").equals(pathNodeList.get(0).getType())
          && "OUT".equals(pathNodeList.get(1).getBuilding())) {
        // Check if you start by leaving the building
        directionList.add(
            new ExitDirection(pathNodeList.get(0).getBuilding(), pathNodeList.get(0).getFloor()));
      } else if (pathNodeList.get(2).getId().equals(endNode.getId())) {
        // Check if there's only a single node between start and goal
        directionList.add(new StartDirection(0, pathNodeList.get(0).getFloor()));
      } else {
        for (j = 1; j < (pathNodeList.size()); j++) {
          if (pathNodeList.get(j).getType().equals(Node.NodeType.getEnum("HALL"))) {
            // Have reached the hallway, can give an angle to exit into the hallway
            float startTurnAngle =
                getAngle(pathNodeList.get(0), pathNodeList.get(1), pathNodeList.get(2));
            directionList.add(new StartDirection(startTurnAngle, pathNodeList.get(0).getFloor()));
            break;
          } else {
            directionList.add(
                new ProceedDirection(
                    pathNodeList.get(j).getLongName(), pathNodeList.get(j - 1).getFloor()));
          }
        }
      }
      // Make a new hallway walking direction, we will always keep an object of this type active
      StraightDirection currHall = new StraightDirection(0, 0, pathNodeList.get(j).getFloor());

      for (int i = j;
          i < (pathNodeList.size());
          i++) { // Iterate through the nodes of the path, starting at the second one
        System.out.println("Now investigating node: " + pathNodeList.get(i).getId());

        if (!pathNodeList.get(i).getFloor().equals(pathNodeList.get(i - 1).getFloor())) {
          // This node is now on a new floor, need to index it
          directionList.add(new IndexDirection(pathNodeList.get(i - 1).getFloor()));
        }

        // Check if this node is the destination
        if (pathNodeList.get(i).getId().equals(endNode.getId())) {
          System.out.println("Destination");
          // This node is the destination
          // Only add the hallway instruction if it actually went some distance
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          float goalTurnAngle =
              getAngle(pathNodeList.get(i - 2), pathNodeList.get(i - 1), pathNodeList.get(i));
          directionList.add(new GoalDirection(goalTurnAngle, pathNodeList.get(i).getFloor()));
          break;
        }

        // Check if this is a hallway node
        else if (pathNodeList.get(i).getType().equals(Node.NodeType.getEnum("HALL"))
            && !"OUT".equals(pathNodeList.get(i).getBuilding())) {
          // Current node is a hallway
          System.out.println("Hallway");
          if (pathNodeList.get(i - 1).getType().equals(Node.NodeType.getEnum("HALL"))) {
            // Previous node was also a hallway, add distance from previous hallway to this one.
            currHall.addDistance(scorer.computeCost(pathNodeList.get(i - 1), pathNodeList.get(i)));
            System.out.println("Incrementing Distance");
          }

          // If the current hall node is an intersection, could be a turn or a continue straight
          // past intersection
          // First, calculate how many neighboring nodes are hallways
          int numHallNeighbors = 0;
          Set<Edge> neighborEdges = pathNodeList.get(i).getEdges();
          for (Edge edge : neighborEdges) {
            if (edge.getNode1().equals(pathNodeList.get(i).getId())) {
              if (nodeMap.get(edge.getNode2()).getType().equals(Node.NodeType.getEnum("HALL"))) {
                numHallNeighbors += 1;
              }
            } else {
              if (nodeMap.get(edge.getNode1()).getType().equals(Node.NodeType.getEnum("HALL"))) {
                numHallNeighbors += 1;
              }
            }
          }
          // Now check if it's an intersection
          if (numHallNeighbors > 2) {
            // This can be a turn or a passed intersection
            System.out.println("Intersection");
            float thisTurnAngle =
                getAngle(pathNodeList.get(i - 1), pathNodeList.get(i), pathNodeList.get(i + 1));
            if (Math.abs(thisTurnAngle) > 20.0) {
              // More than 20deg deviation from straight makes it a turn
              // This is a turn
              System.out.println("This is a turn");
              directionList.add(currHall); // Terminate the hall direction
              directionList.add( // TODO: Add a description of the intersection (name)
                  new TurnDirection(
                      thisTurnAngle,
                      (currHall.getIntersectionsPassed() + 1),
                      pathNodeList.get(i).getFloor()));
              currHall = new StraightDirection(0, 0, pathNodeList.get(i).getFloor());
            } else {
              System.out.println("Passed intersection");
              // This is a passed intersection
              currHall.addIntersection();
            }
          }

          // Check if this node is an elevator
        } else if (pathNodeList.get(i).getType().equals(Node.NodeType.getEnum("ELEV"))) {
          System.out.println("Elevator");
          // This node is an elevator
          // Only add the hallway instruction if it actually went some distance
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          currHall = new StraightDirection(0, 0, pathNodeList.get(i).getFloor());

          if (Node.NodeType.getEnum("ELEV").equals(pathNodeList.get(i + 1).getType())) {
            // If the next node is an elevator, tell the user to take the elevator
            directionList.add(
                new ElevatorDirection(
                    pathNodeList.get(i).getFloor(), pathNodeList.get(i + 1).getFloor()));
          } else {
            // Final elevator node in a sequence
            float exitAngle =
                getAngle(pathNodeList.get(i), pathNodeList.get(i + 1), pathNodeList.get(i + 2));
            directionList.add(new StartDirection(exitAngle, pathNodeList.get(i).getFloor()));
          }

          // Check if this node is a stairwell
        } else if (pathNodeList.get(i).getType().equals(Node.NodeType.getEnum("STAI"))) {
          System.out.println("Stairs");
          // This node is a stairwell
          // Only add the hallway instruction if it actually went some distance
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          currHall = new StraightDirection(0, 0, pathNodeList.get(i).getFloor());

          if (Node.NodeType.getEnum("STAI").equals(pathNodeList.get(i + 1).getType())) {
            // If the next node is a stair, tell the user to take the elevator
            directionList.add(
                new StairsDirection(
                    pathNodeList.get(i).getFloor(), pathNodeList.get(i + 1).getFloor()));
          } else {
            // Final stair node in a sequence
            float exitAngle =
                getAngle(pathNodeList.get(i), pathNodeList.get(i + 1), pathNodeList.get(i + 2));
            directionList.add(new StartDirection(exitAngle, pathNodeList.get(i).getFloor()));
          }
        }

        // Building exit
        else if (Node.NodeType.getEnum("EXIT").equals(pathNodeList.get(i).getType())) {
          // This is a building exit or entrance
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          currHall = new StraightDirection(0, 0, pathNodeList.get(i).getFloor());

          if (!Node.NodeType.getEnum("EXIT").equals(pathNodeList.get(i - 1).getType())
              && !"OUT".equals(pathNodeList.get(i - 1).getBuilding())) {
            // If the previous node was not an exit, this is the first exit in the exit sequence
            directionList.add(
                new ExitDirection(
                    pathNodeList.get(i).getBuilding(), pathNodeList.get(i).getFloor()));
          } else if (!Node.NodeType.getEnum("EXIT").equals(pathNodeList.get(i + 1).getType())) {
            // This is NOT an initial exit, but it IS the final exit, meaning you're entering a
            // building
            directionList.add(
                new EnterDirection(
                    pathNodeList.get(i).getBuilding(), pathNodeList.get(i).getFloor()));
          }
          // Ignore intermediate exit nodes
        } else if ("OUT".equals(pathNodeList.get(i).getBuilding())) {
          // This is the in-transit outdoor node
          directionList.add(
              new TravelDirection(
                  pathNodeList.get(i - 1).getBuilding(),
                  pathNodeList.get(i + 1).getBuilding(),
                  pathNodeList.get(i).getFloor()));
        }

        // If the given node is not a hall, the goal, exit, travel, elevator, or stairs, it must be
        // an in-transit node
        else {
          if (Node.NodeType.getEnum("HALL").equals(pathNodeList.get(i - 1).getType())) {
            // Previous node was a hallway, so we are entering a room
            if (currHall.getDistance() > 0) {
              directionList.add(currHall);
            }
            currHall = new StraightDirection(0, 0, pathNodeList.get(i + 1).getFloor());

            directionList.add(
                new EnterDirection(
                    pathNodeList.get(i).getLongName(), pathNodeList.get(i).getFloor()));
          } else {
            // The previous node was a room
            directionList.add(
                new ProceedDirection(
                    pathNodeList.get(i).getLongName(), pathNodeList.get(i).getFloor()));
          }
        }
        System.out.println("Loop bottom, i: " + i);
      }
    }
  }

  private float getAngle(Node previous, Node current, Node next) {
    // The angle represents positive degrees to the left of straight ahead and negative degrees to
    // the right of straight
    float angleA =
        (float)
            Math.atan2(
                (current.getYCoord() - previous.getYCoord()),
                (current.getXCoord() - previous.getXCoord()));
    angleA = (float) Math.toDegrees(angleA);
    float angleB =
        (float)
            Math.atan2(
                (next.getYCoord() - current.getYCoord()), (next.getXCoord() - current.getXCoord()));
    angleB = (float) Math.toDegrees(angleB);
    float angle = angleA - angleB;
    if (angle < 0.0) {
      angle += 360.0;
    }
    angle -= 360.0;
    if (angle > 180.0) {
      angle -= 360.0;
    }
    if (angle < -180.0) {
      angle += 360.0;
    }
    return angle;
  }

  public List<Direction> getDirectionList() {
    return directionList;
  }

  public String getFullDirectionsString() {
    String returnString = (directionList.get(0).getDirectionText() + "\n");
    for (int i = 1; i < directionList.size(); i++) {
      if (!directionList
          .get(i)
          .getDirectionText()
          .equals(directionList.get(i - 1).getDirectionText())) {
        // If the text is not a duplicate of the previous (i.e. stairs and elevators)
        returnString = returnString + directionList.get(i).getDirectionText() + "\n";
      }
    }
    return returnString;
  }

  public String getFullDirectionsStringSMS() {
    String returnString = (directionList.get(0).getDirectionTextSMS() + "\n");
    for (int i = 1; i < directionList.size(); i++) {
      if (!directionList
          .get(i)
          .getDirectionText()
          .equals(directionList.get(i - 1).getDirectionTextSMS())) {
        // If the text is not a duplicate of the previous (i.e. stairs and elevators)
        returnString = returnString + directionList.get(i).getDirectionTextSMS() + "\n";
      }
    }
    return returnString;
  }

  public String getDirectionsStringForIndex(int index) {
    int indexCounter = 0;
    String returnString = "";
    for (int i = 0; i < directionList.size(); i++) {
      if (directionList.get(i) instanceof IndexDirection) {
        indexCounter++;
      } else if (indexCounter == index) {
        // Only add objects that aren't IndexDirections to the text
        returnString += directionList.get(i).getDirectionText() + "\n";
      }
    }
    return returnString;
  }

  public Boolean smsDirections(String toPhone) {
    String sendMsg =
        ("Directions from "
            + startNode.getLongName()
            + " to "
            + endNode.getLongName()
            + " at Brigham & Women's Hospital:\n\n");
    sendMsg += getFullDirectionsStringSMS();
    return phoneComms.sendMsg(toPhone, sendMsg);
  }

  public Boolean callDirections(String toPhone) {
    String callText =
        ("<Response><Pause/><Pause/><Say>This is an automated call from the Brigham and Women's Hospital Information Kiosk. Here are your directions from "
            + startNode.getLongName()
            + " to "
            + endNode.getLongName()
            + ". After each instruction, stay on the line and press the pound key when you are ready for the next instruction. Press any other key to end the call. "
            + "</Say><Pause/><Say>");
    callText +=
        directionList.get(0).getDirectionTextCall()
            + "</Say><Gather timeout=\"999\" numDigits=\"1\" action=\"http://twimlets.com/message?\"/><Say>";
    for (int i = 1; i < directionList.size() - 1; i++) {
      if (!directionList
              .get(i)
              .getDirectionText()
              .equals(directionList.get(i - 1).getDirectionTextCall())
          && !(directionList.get(i) instanceof IndexDirection)) {
        // If the text is not a duplicate of the previous (i.e. stairs and elevators)
        callText =
            callText
                + directionList.get(i).getDirectionTextCall()
                + "</Say><Gather timeout=\"999\" numDigits=\"1\" action=\"http://twimlets.com/message?\"/><Say>";
      }
    }
    callText += directionList.get(directionList.size() - 1).getDirectionTextCall() + "</Say>";
    //    for (int j = 0; j < directionList.size() - 1; j++) {
    //      callText += "</Gather>";
    //    }
    callText +=
        "<Pause/><Say>Thank you for using the telephone directions service at Brigham and Women's Hospital. Goodbye! </Say></Response>";
    System.out.println(callText);
    return phoneComms.callPhone(toPhone, callText);
  }

  public void printDirections() {
    System.out.println("Creating a printer job...");
    String printMsg =
        ("Directions from "
            + startNode.getLongName()
            + " to "
            + endNode.getLongName()
            + " at Brigham & Women's Hospital:\n\n");
    printMsg += (directionList.get(0).getDirectionTextPrint() + "\n");

    int linesCounter = 3;
    for (int i = 1; i < directionList.size(); i++) {
      if (!directionList
          .get(i)
          .getDirectionText()
          .equals(directionList.get(i - 1).getDirectionTextPrint())) {
        // If the text is not a duplicate of the previous (i.e. stairs and elevators)
        printMsg = printMsg + directionList.get(i).getDirectionTextPrint() + "\n";
      }
      linesCounter++;

      if (linesCounter > 30) {
        // Need to print a label
        System.out.println("Now printing: \n" + printMsg);
        Text printText = new Text(printMsg);
        printText.setFont(new Font(10.5));
        TextFlow printArea = new TextFlow(printText);
        printArea.setMaxWidth(140);

        Printer printer = Printer.getDefaultPrinter();
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job != null) {
          System.out.println(job.jobStatusProperty().asString());

          boolean printed = job.printPage(printArea);
          if (printed) {
            job.endJob();
          } else {
            System.out.println("Printing failed.");
          }
        } else {
          System.out.println("Could not create a printer job.");
        }
        linesCounter = 0;
        printMsg = "";
      }
    }

    if (linesCounter > 0) {
      System.out.println("Now printing: \n" + printMsg);
      Text printText = new Text(printMsg);
      printText.setFont(new Font(10.5));
      TextFlow printArea = new TextFlow(printText);
      printArea.setMaxWidth(140);

      Printer printer = Printer.getDefaultPrinter();
      PrinterJob job = PrinterJob.createPrinterJob(printer);
      if (job != null) {
        System.out.println(job.jobStatusProperty().asString());

        boolean printed = job.printPage(printArea);
        if (printed) {
          job.endJob();
        } else {
          System.out.println("Printing failed.");
        }
      } else {
        System.out.println("Could not create a printer job.");
      }
    }
  }
}
