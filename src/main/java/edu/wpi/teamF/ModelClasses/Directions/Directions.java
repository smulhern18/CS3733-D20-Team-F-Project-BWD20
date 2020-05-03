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
    this.endNode = endNode;

    List<Node> pathNodeList = path.getPath();
    endNode = pathNodeList.get(pathNodeList.size() - 1);

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
      // TODO Add handling if the start node is not connected directly to a hallway (need an
      // in-transit room to get to the hallway)
      if (pathNodeList.get(2).getId().equals(endNode.getId())) {
        // Check if there's only a single node between start and goal
        directionList.add(new StartDirection(0, pathNodeList.get(0).getFloor()));
      } else {
        // There are enough intermediate nodes to logically state a room exit angle
        float startTurnAngle =
            getAngle(pathNodeList.get(0), pathNodeList.get(1), pathNodeList.get(2));
        directionList.add(new StartDirection(startTurnAngle, pathNodeList.get(0).getFloor()));
      }
      // Make a new hallway walking direction, we will always keep an object of this type active
      StraightDirection currHall = new StraightDirection(0, 0, pathNodeList.get(0).getFloor());

      for (int i = 1;
          i < (pathNodeList.size());
          i++) { // Iterate through the nodes of the path, starting at the second one
        System.out.println("Now investigating node: " + pathNodeList.get(i).getId());

        if (pathNodeList.get(i).getType().equals(Node.NodeType.getEnum("HALL"))) {
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
        }

        // Check if this node is the destination
        else if (pathNodeList.get(i).getId().equals(endNode.getId())) {
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

          // TODO: What if your goal is an elevator and DFS takes you through multiple elevators to
          // get there?

          // Check if this node is an elevator
        } else if (pathNodeList.get(i).getType().equals(Node.NodeType.getEnum("ELEV"))) {
          System.out.println("Elevator");
          // This node is an elevator
          // Only add the hallway instruction if it actually went some distance
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          currHall = new StraightDirection(0, 0, pathNodeList.get(i).getFloor());

          if (!Node.NodeType.getEnum("ELEV").equals(pathNodeList.get(i - 1).getType())) {
            System.out.println("A starting elevator");
            // The previous node was a not an elevator, this is the first elevator in a sequence
            // Cycle through the next nodes to find the end of the elevator sequence
            float exitAngle = 0;
            String endFloor = pathNodeList.get(i).getFloor();
            for (int j = i; j < (pathNodeList.size() - 1); j++) {
              if (!Node.NodeType.getEnum("ELEV").equals(pathNodeList.get(j + 1).getType())) {
                // If the next node is not an elevator, this is the last elevator in the sequence
                exitAngle =
                    getAngle(pathNodeList.get(j), pathNodeList.get(j + 1), pathNodeList.get(j + 2));
                endFloor = pathNodeList.get(j).getFloor();
                break;
              }
            }
            directionList.add(
                new ElevatorDirection(
                    pathNodeList.get(i).getFloor(),
                    endFloor,
                    exitAngle,
                    pathNodeList.get(i).getFloor()));
          } else if (!Node.NodeType.getEnum("ELEV").equals(pathNodeList.get(i + 1).getType())) {
            System.out.println("A final elevator");
            // The next node is a not an elevator, this is the last elevator in a sequence
            // Use the previous element in the path, but change the floor
            if (directionList.get(directionList.size() - 1) instanceof ElevatorDirection) {
              ElevatorDirection tempElevDir =
                  (ElevatorDirection) directionList.get(directionList.size() - 1);
              tempElevDir.setFloor(pathNodeList.get(i).getFloor());
              directionList.add(tempElevDir);
            }
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

          if (!Node.NodeType.getEnum("STAI").equals(pathNodeList.get(i - 1).getType())) {
            // The previous node was a not an stair, this is the first stair in a sequence
            // Cycle through the next nodes to find the end of the elevator sequence
            float exitAngle = 0;
            String endFloor = pathNodeList.get(i).getFloor();
            for (int j = i; j < (pathNodeList.size() - 1); j++) {
              if (!Node.NodeType.getEnum("STAI").equals(pathNodeList.get(j + 1).getType())) {
                // If the next node is not a stair, this is the last stair in the sequence
                exitAngle =
                    getAngle(pathNodeList.get(j), pathNodeList.get(j + 1), pathNodeList.get(j + 2));
                endFloor = pathNodeList.get(j).getFloor();
                break;
              }
            }
            directionList.add(
                new StairsDirection(
                    pathNodeList.get(i).getFloor(),
                    endFloor,
                    exitAngle,
                    pathNodeList.get(i).getFloor()));
          } else if (!Node.NodeType.getEnum("STAI").equals(pathNodeList.get(i + 1).getType())) {
            // The next node is a not a stair, this is the last stair in a sequence
            // Use the previous element in the path, but change the floor
            if (directionList.get(directionList.size() - 1) instanceof StairsDirection) {
              StairsDirection tempStaiDir =
                  (StairsDirection) directionList.get(directionList.size() - 1);
              tempStaiDir.setFloor(pathNodeList.get(i).getFloor());
              directionList.add(tempStaiDir);
            }
          }
        }

        // Building exit
        else if (Node.NodeType.getEnum("EXIT").equals(pathNodeList.get(i).getType())) {
          // This is a building exit or entrance
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          currHall = new StraightDirection(0, 0, pathNodeList.get(i).getFloor());

          if (!Node.NodeType.getEnum("EXIT").equals(pathNodeList.get(i - 1).getType())) {
            // If the previous node was not an exit, this is the first exit in the exit sequence
            directionList.add(
                new ExitDirection(
                    pathNodeList.get(i).getBuilding(), pathNodeList.get(i).getFloor()));
            if (Node.NodeType.getEnum("EXIT").equals(pathNodeList.get(i + 1).getType())) {
              // If the next node is also an exit, we are transiting between buildings
              directionList.add(
                  new TravelDirection(
                      pathNodeList.get(i).getBuilding(),
                      pathNodeList.get(i + 1).getBuilding(),
                      pathNodeList.get(i).getFloor()));
            }
          } else if (!Node.NodeType.getEnum("EXIT").equals(pathNodeList.get(i + 1).getType())) {
            // This is NOT an initial exit, but it IS the final exit, meaning you're entering a
            // building
            directionList.add(
                new EnterDirection(
                    pathNodeList.get(i).getBuilding(), pathNodeList.get(i).getFloor()));
          }
          // Ignore intermediate exit nodes
        }

        // If the given node is not a hall, the goal, exit, travel, elevator, or stairs, it must be
        // an
        // in-transit node
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

  public String getFullDirectionsStringForFloor(String floor) {
    String returnString = "";
    for (Direction direction : directionList) {
      if (direction.getFloor().equals(floor)) {
        returnString = returnString + direction.getDirectionText() + "\n";
      }
    }
    return returnString;
  }

  public String getKeyDirectionForFloor(String floor) {
    String returnString = "";
    for (Direction direction : directionList) {
      if (direction.getFloor().equals(floor)) {
        if (direction instanceof GoalDirection) {
          return ("Directions to: " + endNode.getLongName() + ".");
        } else if (direction instanceof ElevatorDirection) {
          return ("Take the elevator from floor "
              + startNode.getFloor()
              + " to floor "
              + endNode.getFloor()
              + ".");
        } else if (direction instanceof StairsDirection) {
          return ("Take the stairs from floor "
              + startNode.getFloor()
              + " to floor "
              + endNode.getFloor()
              + ".");
        }
      }
    }
    return "";
  }

  public Boolean smsDirections(String toPhone) {
    String sendMsg =
        ("-\n\nDirections from "
            + startNode.getLongName()
            + " to "
            + endNode.getLongName()
            + " at Brigham & Women's Hospital:\n\n");
    sendMsg += getFullDirectionsString();
    return phoneComms.sendMsg(toPhone, sendMsg);
  }

  public Boolean callDirections(String toPhone) {
    String callText =
        ("<Response><Pause/><Say>This is an automated call from the Brigham and Women's Hospital Information Kiosk. Here are your directions from "
            + startNode.getLongName()
            + " to "
            + endNode.getLongName()
            + ". After each instruction, stay on the line and press any key when you are ready for the next instruction. "
            + "</Say><Pause/><Say>");
    callText +=
        directionList.get(0).getDirectionText()
            + "</Say><Gather input=\"dtmf\" timeout=\"60\" numDigits=\"1\" action=\"http://twimlets.com/message?\"></Gather><Say>";
    for (int i = 1; i < directionList.size() - 1; i++) {
      if (!directionList
          .get(i)
          .getDirectionText()
          .equals(directionList.get(i - 1).getDirectionText())) {
        // If the text is not a duplicate of the previous (i.e. stairs and elevators)
        callText =
            callText
                + directionList.get(i).getDirectionText()
                + "</Say><Gather input=\"dtmf\" timeout=\"60\" numDigits=\"1\" action=\"http://twimlets.com/message?\"></Gather><Say>";
      }
    }
    callText += directionList.get(directionList.size() - 1).getDirectionText() + "</Say>";
    //    for (int j = 0; j < directionList.size() - 1; j++) {
    //      callText += "</Gather>";
    //    }
    callText +=
        "<Pause/><Say>Thank you for using the telephone directions service at Brigham and Women's Hospital. Goodbye. </Say></Response>";
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
    printMsg += (directionList.get(0).getDirectionText() + "\n");

    int linesCounter = 3;
    for (int i = 1; i < directionList.size(); i++) {
      if (!directionList
          .get(i)
          .getDirectionText()
          .equals(directionList.get(i - 1).getDirectionText())) {
        // If the text is not a duplicate of the previous (i.e. stairs and elevators)
        printMsg = printMsg + directionList.get(i).getDirectionText() + "\n";
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
