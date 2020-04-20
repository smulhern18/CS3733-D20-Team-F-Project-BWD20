package edu.wpi.teamF.ModelClasses.Scorer;

import edu.wpi.teamF.ModelClasses.Node;

public class EuclideanScorer implements Scorer {
  public double computeCost(Node from, Node to) {
    return Math.sqrt(
            Math.pow(from.getXCoord() - to.getXCoord(), 2)
                    + Math.pow(from.getYCoord() - to.getYCoord(), 2));
  }

}


