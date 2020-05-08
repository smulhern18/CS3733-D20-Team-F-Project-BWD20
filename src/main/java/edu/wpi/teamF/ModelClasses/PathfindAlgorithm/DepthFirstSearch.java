package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.RouteNode;
import java.util.*;
import javax.management.InstanceNotFoundException;

public class DepthFirstSearch implements PathfindAlgorithm {
  private final Map<String, Node> nodeMap = new HashMap<>();
  private String liftType;

  public DepthFirstSearch(List<Node> nodeList) {
    for (Node node : nodeList) {
      nodeMap.put(node.getId(), node);
    }
  }

  NodeFactory nodeFactory = NodeFactory.getFactory();
  public List<Node> visited = new ArrayList<>();
  public RouteNode finalRouteNode = null;

  public Path pathfind(Node start, Node end) throws InstanceNotFoundException {
    visited = new ArrayList<>();
    finalRouteNode = null;
    RouteNode startRouteNode = new RouteNode(start, null, 0, 0);
    depthFirstSearchAlgorithm(startRouteNode, end);
    if (finalRouteNode != null) {
      RouteNode currentNode = finalRouteNode;
      List<Node> path = new LinkedList<>();
      do {
        path.add(0, currentNode.getNode());
        currentNode = currentNode.getPrevious();
      } while (currentNode != null);
      Path finalPath = new Path(path);
      return finalPath;
    } else {
      System.out.println("No Route Found");
      return new Path();
    }
  }

  private void depthFirstSearchAlgorithm(RouteNode node, Node goal) {
    if (finalRouteNode == null) {
      System.out.println(node.getNode().getId());
      visited.add(node.getNode());
      // Check if this is the goal
      if (node.getNode().getId().equals(goal.getId())) {
        finalRouteNode = node;
        System.out.println("Found the goal!");
        return;
      }
      // Make a list of all of the neighbors of this node
      Set<Edge> neighborEdges = node.getNode().getEdges();
      Set<Node> neighbors = new HashSet<>();

      String typeToAvoid;
      if ("STAI".equals(liftType)) {
        typeToAvoid = "ELEV";
      } else {
        typeToAvoid = "STAI";
      }

      for (Edge edge : neighborEdges) {
        if (edge.getNode1().equals(node.getNode().getId())) {
          System.out.println(liftType);
          System.out.println(typeToAvoid);
          // if (!visited.contains(nodeMap.get(edge.getNode2()))) {
          if (!visited.contains(nodeMap.get(edge.getNode2()))
              && !nodeMap.get(edge.getNode2()).getType().toString().equals(typeToAvoid)) {
            neighbors.add(nodeMap.get(edge.getNode2()));
          }
        } else {
          // if (!visited.contains(nodeMap.get(edge.getNode1()))) {
          if (!visited.contains(nodeMap.get(edge.getNode1()))
              && !nodeMap.get(edge.getNode1()).getType().toString().equals(typeToAvoid)) {
            neighbors.add(nodeMap.get(edge.getNode1()));
          }
        }
      }

      if (neighbors.size() == 0) {
        return;
      }

      for (Node neighbor : neighbors) {
        RouteNode routeNode = new RouteNode(neighbor, node, 0, 0);
        depthFirstSearchAlgorithm(routeNode, goal);
      }
    }
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

  public String getLiftType() {
    return liftType;
  }
}
