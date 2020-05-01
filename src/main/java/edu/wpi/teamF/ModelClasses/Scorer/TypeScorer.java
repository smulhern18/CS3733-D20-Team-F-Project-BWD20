package edu.wpi.teamF.ModelClasses.Scorer;

import edu.wpi.teamF.ModelClasses.Node;
import java.util.ArrayList;
import java.util.List;

public class TypeScorer implements Scorer {

  private final List<Node> nodes;
  private final EuclideanScorer euclideanScorer = new EuclideanScorer();

  public TypeScorer(List<Node> nodes, String startingFloor) {

    this.nodes = new ArrayList<>();
    for (Node node : nodes) {
      if (node.getFloor().equals(startingFloor)) {
        this.nodes.add(node);
      }
    }
  }

  @Override
  public double computeCost(Node from, Node to) {
    double bestCost = Double.MAX_VALUE;
    for (int i = 0; i < nodes.size(); i++) {
      double cost =
          euclideanScorer.computeCost(from, nodes.get(i))
              + euclideanScorer.computeCost(nodes.get(i), to);
      if (cost < bestCost) {
        bestCost = cost;
      }
    }
    return bestCost;
  }
}
