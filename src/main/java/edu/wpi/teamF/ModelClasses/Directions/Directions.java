package edu.wpi.teamF.ModelClasses.Directions;

import edu.wpi.teamF.Controllers.com.twilio.SendSms;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.util.*;

public class Directions {
  private List<Direction> directionList = new ArrayList<>();
  private EuclideanScorer scorer = new EuclideanScorer();
  private final Map<String, Node> nodeMap = new HashMap<>();
  private Node startNode;
  private Node endNode;
  private SendSms sendSms = new SendSms();

  public Directions(List<Node> fullNodeList, Path path, Node startNode, Node endNode) {
    for (Node node : fullNodeList) {
      nodeMap.put(node.getId(), node);
    }
    this.startNode = startNode;
    this.endNode = endNode;

    List<Node> pathNodeList = path.getPath();

    // Create the starting directions
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
        i < (pathNodeList.size() - 1);
        i++) { // Iterate through the nodes of the path, starting at the second one

      if (pathNodeList.get(i).getType().equals(Node.NodeType.getEnum("HALL"))) {
        // Current node is a hallway

        if (pathNodeList.get(i - 1).getType().equals(Node.NodeType.getEnum("HALL"))) {
          // Previous node was also a hallway, add distance from previous hallway to this one.
          currHall.addDistance(scorer.computeCost(pathNodeList.get(i - 1), pathNodeList.get(i)));
        }

        // Check if the next node is the destination or an elevator/stair
        if (pathNodeList.get(i + 1).getId().equals(endNode.getId())) {
          // Next node is the destination
          // Only add the hallway instruction if it actually went some distance
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          float goalTurnAngle =
              getAngle(pathNodeList.get(i - 1), pathNodeList.get(i), pathNodeList.get(i + 1));
          directionList.add(new GoalDirection(goalTurnAngle, pathNodeList.get(i).getFloor()));
          break;
        } else if (pathNodeList.get(i + 1).getType().equals(Node.NodeType.getEnum("ELEV"))) {
          // Next node is an elevator
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          float exitAngle =
              getAngle(pathNodeList.get(i + 2), pathNodeList.get(i + 3), pathNodeList.get(i + 4));
          directionList.add(
              new ElevatorDirection(
                  pathNodeList.get(i + 1).getFloor(),
                  pathNodeList.get(i + 2).getFloor(),
                  exitAngle));
          // Create a new hallway node where the elevator finishes
          currHall = new StraightDirection(0, 0, pathNodeList.get(i + 2).getFloor());
        } else if (pathNodeList.get(i + 1).getType().equals(Node.NodeType.getEnum("STAI"))) {
          // Next node is a stairwell
          if (currHall.getDistance() > 0) {
            directionList.add(currHall);
          }
          float exitAngle =
              getAngle(pathNodeList.get(i + 2), pathNodeList.get(i + 3), pathNodeList.get(i + 4));
          directionList.add(
              new StairsDirection(
                  pathNodeList.get(i + 1).getFloor(),
                  pathNodeList.get(i + 2).getFloor(),
                  exitAngle));
          // Create a new hallway node where the elevator finishes
          currHall = new StraightDirection(0, 0, pathNodeList.get(i + 2).getFloor());
        } else { // Can now investigate this hall node as a part of the continuing route
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
            float thisTurnAngle =
                getAngle(pathNodeList.get(i - 1), pathNodeList.get(i), pathNodeList.get(i + 1));
            if (Math.abs(thisTurnAngle) > 20.0) {
              // More than 20deg deviation from straight makes it a turn
              directionList.add(currHall);
              directionList.add(
                  new TurnDirection(
                      thisTurnAngle,
                      (currHall.getIntersectionsPassed() + 1),
                      pathNodeList.get(i).getFloor()));
              currHall = new StraightDirection(0, 0, pathNodeList.get(i).getFloor());
            } else {
              // This is a passed intersection
              currHall.addIntersection();
            }
          }
        }
      } else {
        System.out.println("Failed hallway test for current node: " + pathNodeList.get(i).getId());
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
    String returnString = "";
    for (Direction direction : directionList) {
      returnString = returnString + direction.getDirectionText() + "\n";
    }
    return returnString;
  }

  public String getFullDirectionsStringForFloor(int floor) {
    String returnString = "";
    for (Direction direction : directionList) {
      if (direction.getFloor() == floor) {
        returnString = returnString + direction.getDirectionText() + "\n";
      }
    }
    return returnString;
  }

  public String getKeyDirectionForFloor(int floor) {
    String returnString = "";
    for (Direction direction : directionList) {
      if (direction.getFloor() == floor) {
        if (direction instanceof GoalDirection) {
          return ("Directions to: " + endNode.getLongName() + ".");
        } else if (direction instanceof ElevatorDirection) {
          return ("Take the elevator from floor "
              + Integer.toString(startNode.getFloor())
              + " to floor "
              + Integer.toString(endNode.getFloor())
              + ".");
        } else if (direction instanceof StairsDirection) {
          return ("Take the stairs from floor "
              + Integer.toString(startNode.getFloor())
              + " to floor "
              + Integer.toString(endNode.getFloor())
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
    for (Direction direction : directionList) {
      sendMsg = sendMsg + "- " + direction.getDirectionText() + "\n";
    }
    return sendSms.sendMsg(toPhone, sendMsg);
  }
}
