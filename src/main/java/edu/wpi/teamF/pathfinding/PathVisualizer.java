package edu.wpi.teamF.pathfinding;

import java.util.List;

public class PathVisualizer {

    public void printPath(List<GraphNode> path) {
        for (int i = 0;i < path.size();i++) {
            System.out.println((i+1) + ". " + path.get(i).getId());
        }
    }
}
