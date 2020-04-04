package edu.wpi.teamF.pathfinding;

public interface Scorer {

    double computeCost(GraphNode from, GraphNode to);

}
