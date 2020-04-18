package edu.wpi.teamF.ModelClasses.PathfindAlgorithm;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;

import java.util.ArrayList;
import java.util.List;

public class SingleFloorAStar implements PathfindAlgorithm {

  NodeFactory nodeFactory = NodeFactory.getFactory();

  @Override
  public Path pathfind(Node start, Node end) {
    return null;
  }



  @Override
  public Path pathfind(Node start, Node.NodeType nodeType) {
    List<Node> nodes = nodeFactory.getNodesByType(nodeType);
    List<Path> paths= new ArrayList<>();
    for (Node node : nodes) {
      paths.add(pathfind(start,node));
    }
    double shortestLength = Double.MAX_VALUE;
    Path shortestPath = null;
    for (int i = 0;i < paths.size();i++) {
      double length = paths.get(i).getPathLength();
      if (length < shortestLength) {
        shortestPath = paths.get(i);
        shortestLength = length;
      }
    }
    return shortestPath;
  }

}
