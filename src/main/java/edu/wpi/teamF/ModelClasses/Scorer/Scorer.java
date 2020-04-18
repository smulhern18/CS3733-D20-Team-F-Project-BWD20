package edu.wpi.teamF.ModelClasses.Scorer;

import edu.wpi.teamF.ModelClasses.Node;

public interface Scorer {
  double computeCost(Node start, Node end);
}
