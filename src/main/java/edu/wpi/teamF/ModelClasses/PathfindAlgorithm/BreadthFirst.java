package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.RouteNode;
import java.util.*;
import javax.management.InstanceNotFoundException;

public class BreadthFirst implements PathfindAlgorithm {

  private final Map<String, Node> nodeMap = new HashMap<>();
  private String liftType = "ELEV";

  public BreadthFirst(List<Node> nodeList) {
    for (Node node : nodeList) {
      nodeMap.put(node.getId(), node);
    }
  }

  @Override
  public Path pathfind(Node start, Node end) throws InstanceNotFoundException {
    Queue<RouteNode> nodeQueue = new LinkedList<>();

    HashSet<Node> visited = new HashSet<>();

    RouteNode startRoute = new RouteNode(start, null, 0, 0);
    nodeQueue.add(startRoute);

    String typeToAvoid;
    if ("STAI".equals(liftType)) {
      typeToAvoid = "ELEV";
    } else {
      typeToAvoid = "STAI";
    }

    while (!nodeQueue.isEmpty()) {
      RouteNode currentNode = nodeQueue.poll();
      if (!visited.contains(currentNode.getNode())) {
        visited.add(currentNode.getNode());

        if (currentNode.getNode().equals(end)) {
          List<Node> path = new LinkedList<>();
          do {
            path.add(0, currentNode.getNode());
            currentNode = currentNode.getPrevious();
          } while (currentNode != null);
          Path finalPath = new Path(path);
          return finalPath;
        }
        Set<Edge> neighborEdges = currentNode.getNode().getEdges();
        Set<Node> neighbors = new HashSet<>();
        for (Edge edge : neighborEdges) {
          final Node neighbor;
          if (edge.getNode1().equals(currentNode.getNode().getId())) {
            neighbor = nodeMap.get(edge.getNode2());
          } else {
            neighbor = nodeMap.get(edge.getNode1());
          }
          neighbors.add(neighbor);
        }
//        System.out.println("Current Node: " + currentNode.getNode().getId());
//        System.out.println("Neighbors" + neighbors.size());
        for (Node neighbor : neighbors) {
          // if (!visited.contains(neighbor)) {
          if (!visited.contains(neighbor) && !neighbor.getType().toString().equals(typeToAvoid)) {
            RouteNode neighborOnRoute = new RouteNode(neighbor, currentNode, 0, 0);
            nodeQueue.add(neighborOnRoute);
          }
        }
      }
    }
    System.out.println("No Route Found");
    return new Path();
  }

  @Override
  public Path pathfind(Node start, Node.NodeType nodeType) throws InstanceNotFoundException {
    List<Path> paths = new ArrayList<>();
    for (Node node : nodeMap.values()) {
      if (node.getFloor() == start.getFloor()) {
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



  public void setLiftType(String liftType) {
    this.liftType = liftType;
  }
}
