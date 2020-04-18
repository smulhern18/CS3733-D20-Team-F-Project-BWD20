package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;

public interface PathfindAlgorithm {

    Path pathfind(Node start, Node end);

}
