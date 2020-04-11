package edu.wpi.teamF.modelClasses;

import java.util.List;

public class ElevatorScorer2 {

    private final List<Node> elevators;
    private final EuclideanScorer euclideanScorer = new EuclideanScorer();

    public ElevatorScorer2(List<Node> elevators) {
        this.elevators = elevators;
    }
    public Node elevatorScorer(Node from, Node to) {
        double bestCost = 0;
        Node bestNode = null;
        for (Node elev : elevators) {
            double cost = euclideanScorer.computeCost(from, elev) + euclideanScorer.computeCost(elev, to);
            if (cost < bestCost || bestCost == 0) {
                bestCost = cost;
                bestNode = elev;
            }
        }
        return bestNode;
    }
}
