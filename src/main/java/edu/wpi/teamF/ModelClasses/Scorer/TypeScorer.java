package edu.wpi.teamF.ModelClasses.Scorer;

import edu.wpi.teamF.ModelClasses.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeScorer implements Scorer {

  private static final double LIFT_COST = 1000;
  private final List<Node> nodes;
  private final EuclideanScorer euclideanScorer = new EuclideanScorer();

  public TypeScorer(Map<String, Node> nodeMap, String type) {

    this.nodes = new ArrayList<>();
    for (Node node : nodeMap.values()) {
      if (node.getType().getTypeString().equals(type)) {
        nodes.add(node);
      }
    }
  }

  @Override
  public double computeCost(Node from, Node to) {

    double bestCost = Double.MAX_VALUE;
    for (Node node : nodes) {
      if (sameHospital(from.getBuilding(), node.getBuilding())) {
        double cost =
            euclideanScorer.computeCost(from, node) + euclideanScorer.computeCost(node, to);
        if (cost < bestCost) {
          bestCost = cost;
        }
      }
    }
    return bestCost + LIFT_COST;
  }

  private boolean sameHospital(String building1, String building2) {
    return ("Faulkner".equals(building1) && "Faulkner".equals(building2))
        || (!"Faulkner".equals(building1) && !"Faulkner".equals(building2));
  }
}
