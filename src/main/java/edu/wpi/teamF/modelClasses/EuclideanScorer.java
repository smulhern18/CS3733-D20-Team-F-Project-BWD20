package edu.wpi.teamF.modelClasses;


public class EuclideanScorer implements Scorer {

  @Override
  public double computeCost(Node from, Node to) {
    return Math.sqrt(Math.pow(from.getXCoord() - to.getXCoord(), 2) + Math.pow(from.getYCoord() - to.getYCoord(), 2));
  }

}

