package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.RouteNode;
import edu.wpi.teamF.ModelClasses.Scorer.HospitalScorer;
import edu.wpi.teamF.ModelClasses.Scorer.Scorer;
import java.util.*;
import javax.management.InstanceNotFoundException;

public abstract class HeuristicSearch implements PathfindAlgorithm {
  private final Map<String, Node> nodeMap = new HashMap<>();
  private String liftType = "ELEV";

  public HeuristicSearch(List<Node> nodeList) {
    for (Node node : nodeList) {
      nodeMap.put(node.getId(), node);
    }
  }

  public void setLiftType(String liftType) {
    this.liftType = liftType;
  }

  public double calcEstimatedCostOfNeighbor(
      RouteNode currentNode, Node neighbor, Node endNode, Scorer scorer) {
    return 0;
  }

  public Path pathfind(Node startNode, Node endNode) {
    // Check if the destination is on a different floor
    PriorityQueue<RouteNode> priorityQueue = new PriorityQueue<RouteNode>();
    HashSet<Node> visited = new HashSet<>();
    Scorer scorer = new HospitalScorer(nodeMap, liftType);

    // Create the first node and add it to the Priority Queue
    RouteNode start;
    start = new RouteNode(startNode, null, 0, scorer.computeCost(startNode, endNode));

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
        System.out.println("Current node: " + currentNode.getNode().getId());
        Set<Edge> neighborEdges = currentNode.getNode().getEdges();
        System.out.println("Neighbor Edges size " + neighborEdges.size());
        Set<Node> neighbors = new HashSet<>();
        for (Edge edge : neighborEdges) {
          System.out.println(edge.getId());
          final Node neighbor;
          if (edge.getNode1().equals(currentNode.getNode().getId())) {
            neighbor = nodeMap.get(edge.getNode2());

          } else {
            neighbor = nodeMap.get(edge.getNode1());
          }
          String typeToAvoid;
          if ("STAI".equals(liftType)) {
            typeToAvoid = "ELEV";
          } else {
            typeToAvoid = "STAI";
          }
          if (!nodeMap.get(edge.getNode2()).getType().toString().equals(typeToAvoid)) {
            neighbors.add(neighbor);
          }
        }
        for (Node neighbor : neighbors) {
          if (!visited.contains(neighbor)) {
            //                        double distanceToEnd = scorer.computeCost(neighbor, endNode);
            //
            double distanceFromStart =
                currentNode.getRouteScore()
                    + scorer.computeCost(currentNode.getNode(), neighbor); // Actual path distance
            //                        double estimatedCostOfNeighbor = distanceToEnd +
            // distanceFromStart;

            double estimatedCostOfNeighbor =
                calcEstimatedCostOfNeighbor(currentNode, neighbor, endNode, scorer);
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

  public Path pathfind(Node start, Node.NodeType nodeType) throws InstanceNotFoundException {
    List<Path> paths = new ArrayList<>();
    for (Node node : nodeMap.values()) {
      if (node.getFloor().equals(start.getFloor())) {
        if (node.getType().getTypeString().equals(nodeType.getTypeString())) {
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
