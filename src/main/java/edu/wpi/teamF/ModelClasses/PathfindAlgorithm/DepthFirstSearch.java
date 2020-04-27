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
    for (Edge edge : neighborEdges) {
      if (edge.getNode1().equals(node.getNode().getId())) {
        if (!visited.contains(nodeMap.get(edge.getNode2()))) {
          neighbors.add(nodeMap.get(edge.getNode2()));
        }
      } else {
        if (!visited.contains(nodeMap.get(edge.getNode1()))) {
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

  @Override
  public Path pathfind(Node start, Node.NodeType nodeType) throws InstanceNotFoundException {
    return null;
  }
}
