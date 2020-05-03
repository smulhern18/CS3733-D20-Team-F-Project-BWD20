package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import javax.management.InstanceNotFoundException;

public interface PathfindAlgorithm {

  Path pathfind(Node start, Node end) throws InstanceNotFoundException;

  Path pathfind(Node start, Node.NodeType nodeType) throws InstanceNotFoundException;

  void setLiftType(String type);

  String getLiftType();
}
