package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.RouteNode;
import edu.wpi.teamF.ModelClasses.Scorer.ElevatorScorer;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.util.*;
import javax.management.InstanceNotFoundException;

public class MultipleFloorAStar implements PathfindAlgorithm {

  private final Map<String, Node> nodeMap = new HashMap<>();

  public MultipleFloorAStar(List<Node> nodeList) {
    for (Node node : nodeList) {
      nodeMap.put(node.getId(), node);
    }
  }

  NodeFactory nodeFactory = NodeFactory.getFactory();

  public Path pathfind(Node startNode, Node endNode) {
    // Check if the destination is on a different floor
    PriorityQueue<RouteNode> priorityQueue = new PriorityQueue<RouteNode>();
    HashSet<Node> visited = new HashSet<>();
    EuclideanScorer scorer = new EuclideanScorer();
    ElevatorScorer elevScorer =
        new ElevatorScorer(nodeFactory.getNodesByType(Node.NodeType.ELEV), startNode.getFloor());
    // Create the first node and add it to the Priority Queue
    RouteNode start;
    if (startNode.getFloor() != endNode.getFloor()) {
      // If it is, navigate to the most practical elevator instead
      start = new RouteNode(startNode, null, 0, elevScorer.computeCost(startNode, endNode));
    } else {
      start = new RouteNode(startNode, null, 0, scorer.computeCost(startNode, endNode));
    }
    priorityQueue.add(start);
    while (!priorityQueue.isEmpty()) {
      RouteNode currentNode = priorityQueue.poll();
      if (!visited.contains(currentNode.getNode())) {
        visited.add(currentNode.getNode());
        if (currentNode.getNode().equals(endNode)) {
          // Has reached the goal node
          List<Node> path = new LinkedList<>();
          do {
            path.add(0, currentNode.getNode());
            currentNode = currentNode.getPrevious();
          } while (currentNode != null);
          Path finalPath = new Path(path);
          return finalPath;
        }

        // Make a list of all of the neighbors of this node
        Set<Edge> neighborEdges = currentNode.getNode().getEdges();
        Set<Node> neighbors = new HashSet<>();
        for (Edge edge : neighborEdges) {
          final Node neighbor;
          if (edge.getNode1().equals(currentNode.getNode().getId())) {
            neighbor = nodeMap.get(edge.getNode2());

          } else {
            neighbor = nodeMap.get(edge.getNode1());
          }
          if (neighbor.getFloor() == startNode.getFloor()
              || neighbor.getFloor() == endNode.getFloor()) {
            // If the neighbor is on a relevant floor
            // Verify it's accessible by a hall or the room you're starting at
            Boolean isAccessible = false;
            Set<Edge> neighborEdges2 = neighbor.getEdges();
            for (Edge edge2 : neighborEdges2) {
              if (edge2.getNode1().equals(neighbor.getId())) {
                if (nodeMap.get(edge2.getNode2()).getType().equals(Node.NodeType.getEnum("HALL"))
                    || edge2.getNode2().equals(startNode.getId())) {
                  isAccessible = true;
                  break;
                }
              } else {
                if (nodeMap.get(edge2.getNode1()).getType().equals(Node.NodeType.getEnum("HALL"))
                    || edge2.getNode1().equals(startNode.getId())) {
                  isAccessible = true;
                  break;
                }
              }
            }
            if (isAccessible) {
              neighbors.add(neighbor);
            }
          }
        }
        for (Node neighbor : neighbors) {
          if (!visited.contains(neighbor)) {
            double distanceToEnd = 0;
            if (neighbor.getFloor() != endNode.getFloor()) {
              // If its not on the same floor, use elevator scorer
              distanceToEnd = elevScorer.computeCost(neighbor, endNode);
            } else {
              distanceToEnd = scorer.computeCost(neighbor, endNode);
            }
            double distanceFromStart =
                currentNode.getRouteScore()
                    + scorer.computeCost(currentNode.getNode(), neighbor); // Actual path distance
            double estimatedCostOfNeighbor = distanceToEnd + distanceFromStart;
            RouteNode neighborOnRoute =
                new RouteNode(neighbor, currentNode, distanceFromStart, estimatedCostOfNeighbor);
            priorityQueue.add(neighborOnRoute);
          }
        }
      }
    }
    // If it exits the while loop without returning a path
    System.out.println("No Route Found");
    return new Path();
  }

  @Override
  public Path pathfind(Node start, Node.NodeType nodeType) throws InstanceNotFoundException {
    List<Path> paths = new ArrayList<>();
    for (Node node : nodeMap.values()) {
      if (node.getType().getTypeString().equals(nodeType.getTypeString())) {
        // If the node is of the correct type, verify it's accessible by a hall or the room you're
        // starting at
        Boolean isAccessible = false;
        Set<Edge> neighborEdges = node.getEdges();
        for (Edge edge : neighborEdges) {
          if (edge.getNode1().equals(node.getId())) {
            if (nodeMap.get(edge.getNode2()).getType().equals(Node.NodeType.getEnum("HALL"))
                || edge.getNode2().equals(start.getId())) {
              isAccessible = true;
              break;
            }
          } else {
            if (nodeMap.get(edge.getNode1()).getType().equals(Node.NodeType.getEnum("HALL"))
                || edge.getNode1().equals(start.getId())) {
              isAccessible = true;
              break;
            }
          }
        }
        if (isAccessible) {
          paths.add(pathfind(start, node));
        }
      }
    }
    double shortestLength = Double.MAX_VALUE;
    Path shortestPath = null;
    for (int i = 0; i < paths.size(); i++) {
      double length = paths.get(i).getPathLength();
      if (length < shortestLength) {
        shortestPath = paths.get(i);
        shortestLength = length;
      }
    }
    return shortestPath;
  }
}
