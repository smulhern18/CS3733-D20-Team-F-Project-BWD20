package edu.wpi.teamF.modelClasses;

import edu.wpi.teamF.modelClasses.Node;

public interface Scorer {
    double computeCost(Node from, Node to);
}
