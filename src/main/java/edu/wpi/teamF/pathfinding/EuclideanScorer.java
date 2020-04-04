package edu.wpi.teamF.pathfinding;

public class EuclideanScorer implements Scorer {


    @Override
    public double computeCost(GraphNode from, GraphNode to) {
        return Math.sqrt(Math.pow(from.getXcoord() - to.getXcoord(),2) + Math.pow(from.getYcoord() - to.getYcoord(),2));
    }
}
