package edu.wpi.teamF.pathfinding;

import java.util.HashSet;
import java.util.Set;

public class GraphNode {

    private final int xcoord;
    private final int ycoord;
    private final String id;
    private final Set<GraphNode> neighbors;

    public GraphNode(int xcoord, int ycoord, String id) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.id = id;
        this.neighbors = new HashSet<>();
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

    public void addNeighbor(GraphNode neighbor) {
        neighbors.add(neighbor);
    }

    /**
     *  Checks if its a GraphNode object and that the IDs are equal
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        boolean isEqual = false;
        if (other != null && other instanceof GraphNode) {
            GraphNode otherGraphNode = (GraphNode) other;
            isEqual = this.id == otherGraphNode.getId();
        }
        return isEqual;
    }
}
