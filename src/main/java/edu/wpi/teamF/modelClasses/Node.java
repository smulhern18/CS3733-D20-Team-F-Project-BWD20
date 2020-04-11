package edu.wpi.teamF.modelClasses;

import java.util.HashSet;
import java.util.Set;

public class Node {

  public enum NodeType {
    HALL,
    ELEV,
    REST,
    STAI,
    DEPT,
    LABS,
    INFO,
    CONF,
    EXIT,
    RETL,
    SERV
  };

  private final short X;
  private final short Y;
  private final String name;

  private NodeType type;
  private final int floor;
  private final Set<Node> neighbors = new HashSet<>();;

  public Node(short xCoord, short yCoord, String name, NodeType nodeType, int floor) {
    this.X = xCoord;
    this.Y = yCoord;
    this.name = name;
    this.type = nodeType;
    this.floor = floor;
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

  public NodeType getType() {
    return type;
  }

  public int getFloor() {
    return floor;
  }
}
