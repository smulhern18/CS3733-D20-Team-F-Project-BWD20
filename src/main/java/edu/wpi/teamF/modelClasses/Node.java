package edu.wpi.teamF.modelClasses;

import edu.wpi.teamF.modelClasses.Node;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private final int X;
    private final int Y;
    private final String name;
    private final Set<Node> neighbors = new HashSet<>();;



    public Node(int xCoord, int yCoord, String name) {
        this.X = xCoord;
        this.Y = yCoord;
        this.name = name;
    }

    public Set<Node> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }


    public boolean equals(Object other) {
        boolean isEqual = false;
        if (other != null && other instanceof Node) {
            Node otherNode = (Node) other;
            isEqual = this.name == otherNode.getName();
        }
        return isEqual;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public String getName() {
        return name;
    }


}
