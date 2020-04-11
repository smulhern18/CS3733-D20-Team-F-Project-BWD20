package edu.wpi.teamF.controllers;

import edu.wpi.teamF.factories.NodeFactory;

import edu.wpi.teamF.modelClasses.ElevatorScorer;

import edu.wpi.teamF.modelClasses.ElevatorScorer2;

import edu.wpi.teamF.modelClasses.EuclideanScorer;
import edu.wpi.teamF.modelClasses.Node;
import edu.wpi.teamF.modelClasses.RouteNode;


import java.util.*;

public class PathfindController extends SceneController {

    private NodeFactory nodeFactory;

    public PathfindController(NodeFactory nodeFactory){
        this.nodeFactory = nodeFactory;
    }

    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }

    public void setNodeFactory(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public List<Node> getPath(Node startNode, Node endNode) {
        //Check if the destination is on a different floor
        if (startNode.getFloor() != endNode.getFloor()){
            //create list of elevators to navigate
            List<Node> elevatorList = nodeFactory.getNodes("ELEV");
            //If it is, navigate to the most practical elevator instead
            ElevatorScorer2 elevScorer = new ElevatorScorer2(elevatorList); //Need to find out how we pass it a list of elevators?
            endNode = elevScorer.elevatorScorer(startNode, endNode);
        }

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
                Set<Node> neighbors = currentNode.getNode().getNeighbors();
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


    public List<Node> getPath2(Node startNode, Node endNode) {
        //Check if the destination is on a different floor


        PriorityQueue<RouteNode> priorityQueue = new PriorityQueue<RouteNode>();
        HashSet<Node> visited = new HashSet<Node>();
        EuclideanScorer scorer = new EuclideanScorer();
        ElevatorScorer elevScorer = new ElevatorScorer(nodeFactory.getNodes("ELEV"));

        // Create the first node and add it to the Priority Queue
        RouteNode start;
        if (startNode.getFloor() != endNode.getFloor()){
            //If it is, navigate to the most practical elevator instead
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
                    return path;
                }

                // Make a list of all of the neighbors of this node
                Set<Node> neighbors = currentNode.getNode().getNeighbors();
                for (Node neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        double distanceToEnd = 0;
                        if (currentNode.getNode().getFloor() != endNode.getFloor()){
                            //If its not on the same floor, use elevator scorer
                            distanceToEnd = elevScorer.computeCost(currentNode.getNode(), endNode);
                        } else {
                            distanceToEnd = scorer.computeCost(startNode, endNode);
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
        return new ArrayList<Node>();
    }

    public void printPath(List<Node> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.println((i + 1) + ". " + path.get(i).getName());
        }
    }

}
