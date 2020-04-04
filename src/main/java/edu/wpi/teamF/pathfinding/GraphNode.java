package edu.wpi.teamF.pathfinding;

import java.util.Set;

public class GraphNode {

    private final int xcoord;
    private final int ycoord;
    private final String id;
    private final Set<GraphNode> neighbors;

    public GraphNode(int xcoord, int ycoord, String id,Set<GraphNode> neighbors) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.id = id;
        this.neighbors = neighbors;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public String getId() {
        return id;
    }

    public Set<GraphNode> getNeighbors() {
        return neighbors;
    }
}
