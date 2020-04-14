package edu.wpi.teamF.ModelClasses;

import java.util.List;

public class ElevatorScorer implements Scorer {

  private final List<Node> elevators;
  private final EuclideanScorer euclideanScorer = new EuclideanScorer();

  public ElevatorScorer(List<Node> elevators) {
    this.elevators = elevators;
  }

  @Override
  public double computeCost(Node from, Node to) {
    double bestCost = Double.MAX_VALUE;
    for (int i = 0; i < elevators.size(); i++) {
      double cost =
          euclideanScorer.computeCost(from, elevators.get(i))
              + euclideanScorer.computeCost(elevators.get(i), to);
      if (cost < bestCost) {
        bestCost = cost;
      }
    }
    return bestCost;
  }
}
