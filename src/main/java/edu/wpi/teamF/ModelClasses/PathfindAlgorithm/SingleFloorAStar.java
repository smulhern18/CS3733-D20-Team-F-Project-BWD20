package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.RouteNode;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class SingleFloorAStar implements PathfindAlgorithm {

  NodeFactory nodeFactory = NodeFactory.getFactory();

  @Override
  public Path pathfind(Node startNode, Node endNode) {
    PriorityQueue<RouteNode> priorityQueue = new PriorityQueue<RouteNode>();
    HashSet<Node> visited = new HashSet<Node>();
    EuclideanScorer scorer = new EuclideanScorer();
    // Create the first node and add it to the Priority Queue
    RouteNode start = new RouteNode(startNode, null, 0, scorer.computeCost(startNode, endNode));
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
        Set<Node> neighbors = currentNode.getNode().getNeighborNodes();
        for (Node neighbor : neighbors) {
          if (!visited.contains(neighbor)) {
            double distanceToEnd =
                scorer.computeCost(neighbor, endNode); // Estimated distance to end
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
  public Path pathfind(Node start, Node.NodeType nodeType) {
    List<Node> nodes = nodeFactory.getNodesByType(nodeType);
    List<Path> paths = new ArrayList<>();
    for (Node node : nodes) {
      paths.add(pathfind(start, node));
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
