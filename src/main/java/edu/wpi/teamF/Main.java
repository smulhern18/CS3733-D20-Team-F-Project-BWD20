package edu.wpi.teamF;

import edu.wpi.teamF.pathfinding.Graph;
import edu.wpi.teamF.pathfinding.GraphNode;
import edu.wpi.teamF.pathfinding.PathVisualizer;
import edu.wpi.teamF.pathfinding.Pathfinder;
import java.util.HashSet;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    System.out.println("Testing Pathfinding");
    Graph graph = new Graph(new HashSet<GraphNode>());
    Pathfinder pathfinder = new Pathfinder();
    PathVisualizer pathVisualizer = new PathVisualizer();
    List<GraphNode> path = pathfinder.getPath(graph.getNode("E"), graph.getNode("E"));
    pathVisualizer.printPath(path);

    // App.launch(App.class, args);
  }
}
