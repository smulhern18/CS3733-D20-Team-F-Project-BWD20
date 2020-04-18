package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.RouteNode;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;

import java.util.*;

public class SingleFloorAStar implements PathfindAlgorithm {
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
                    return path;
                }
                // Make a list of all of the neighbors of this node
                Set<Node> neighbors = new HashSet<>();
                for (String neighborNode : currentNode.getNode().getNeighbors()) {
                    try {
                        neighbors.add(nodeFactory.read(neighborNode));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
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
        return new ArrayList<Node>();
    }


}
