package edu.wpi.teamF.modelClasses;

import edu.wpi.teamF.modelClasses.Scorer;

public class EuclideanScorer implements Scorer {

    @Override
    public double computeCost(Node from, Node to) {
        return Math.sqrt(
                Math.pow(from.getX() - to.getX(), 2)
                        + Math.pow(from.getY() - to.getY(), 2));
    }
}
