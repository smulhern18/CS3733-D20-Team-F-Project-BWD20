package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.RouteNode;
import edu.wpi.teamF.ModelClasses.Scorer.Scorer;
import java.util.List;

public class MultipleHospitalDijkstra extends HeuristicSearch {
  public MultipleHospitalDijkstra(List<Node> nodeList) {
    super(nodeList);
  }

  @Override
  public double calcEstimatedCostOfNeighbor(
      RouteNode currentNode, Node neighbor, Node endNode, Scorer scorer) {
    // Return distance from start
    return currentNode.getRouteScore()
        + scorer.computeCost(currentNode.getNode(), neighbor); // Actual path distance
  }
}
