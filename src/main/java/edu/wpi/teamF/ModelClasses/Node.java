package edu.wpi.teamF.ModelClasses;

import lombok.Data;

@Data
public class Node {

    public enum Type {

    }

<<<<<<< HEAD
    // Get the string value from enum type
    public final String getTypeString() {
      return typeString;
    }

    // Get enum type from string
    public static NodeType getEnum(String typeString) {
      NodeType value = null;
      for (final NodeType n : values()) {
        if (n.getTypeString().equals(typeString)) {
          value = n;
        }
      }
      return value;
    }
  }

  private short xCoord;
  private short yCoord;
  private String building;
  private String longName;
  private String shortName;
  private String id;
  private NodeType type;
  private short floor;
  private Set<Edge> edges = new HashSet<>();

  /**
   * Constructor for Nodes
   *
   * @param xCoord the xCoordinate of the node
   * @param yCoord the yCoordinate of the node
   * @param building the building of the node
   * @param longName the long name of the node
   * @param shortName the short name of the node
   * @param id the name of the node
   * @param nodeType the type of the node
   * @param floor the floor the node is on
   * @throws ValidationException should anything go wrong
   */
  public Node(
      String id,
      short xCoord,
      short yCoord,
      String building,
      String longName,
      String shortName,
      NodeType nodeType,
      short floor)
      throws ValidationException {
    setXCoord(xCoord);
    setYCoord(yCoord);
    setBuilding(building);
    setLongName(longName);
    setShortName(shortName);
    setId(id);
    setType(nodeType);
    setFloor(floor);
  }

  public Node(
      String id,
      short xCoord,
      short yCoord,
      String building,
      String longName,
      String shortName,
      NodeType nodeType,
      short floor,
      Set<Edge> edge)
      throws ValidationException {
    this(id, xCoord, yCoord, building, longName, shortName, nodeType, floor);
    setEdges(edge);
  }

  /** @return all of the edges related to this node */
  public Set<Edge> getEdges() {
    return edges;
  }

  /**
   * Sets the edges array
   *
   * @param edge the edge to add
   */
  public void setEdges(Set<Edge> edge) {
    this.edges = edge;
  }

  /**
   * adds a edge to the edges array
   *
   * @param edge the edges to add
   */
  public void addEdge(Edge edge) {
    this.edges.add(edge);
  }

  /**
   * Checks if two nodes are equal
   *
   * @param other the otherNode to check against
   * @return if the nodes are equal or not
   */
  public boolean equals(Object other) {
    boolean isEqual = false;
    if (other instanceof Node) {
      Node otherNode = (Node) other;

      isEqual =
          this.id.equals(otherNode.getId())
              && this.getXCoord() == otherNode.getXCoord()
              && this.getYCoord() == otherNode.getYCoord()
              && this.getFloor() == otherNode.getFloor()
              && this.getType() == otherNode.getType()
              && this.edges.equals((otherNode).edges)
              && this.getBuilding().equals(otherNode.getBuilding())
              && this.getLongName().equals(otherNode.getLongName())
              && this.getShortName().equals(otherNode.getShortName());
    }
    return isEqual;
  }

  /**
   * Returns the xCoord of the node
   *
   * @return the xCoord
   */
  public short getXCoord() {
    return xCoord;
  }

  /**
   * Sets the xCoord
   *
   * @param xCoord the xCoord to set
   * @throws ValidationException should validation Fail
   */
  public void setXCoord(short xCoord) throws ValidationException {
    Validators.coordValidation(xCoord);
    this.xCoord = xCoord;
  }

  /**
   * Returns the yCoord of the node
   *
   * @return the yCoord
   */
  public short getYCoord() {
    return yCoord;
  }

  /**
   * Sets the yCoord
   *
   * @param yCoord the yCoord to set
   * @throws ValidationException should validation fail
   */
  public void setYCoord(short yCoord) throws ValidationException {
    Validators.coordValidation(yCoord);
    this.yCoord = yCoord;
  }

  /**
   * Returns the building of the node
   *
   * @return the building
   */
  public String getBuilding() {
    return building;
  }

  /**
   * Sets the building
   *
   * @param building the building to set
   * @throws ValidationException should the validation fail
   */
  public void setBuilding(String building) throws ValidationException {
    Validators.buildingValidation(building);
    this.building = building;
  }

  /**
   * Returns the longName of the node
   *
   * @return the longName
   */
  public String getLongName() {
    return longName;
  }

  /**
   * Sets the longName
   *
   * @param longName the longName
   * @throws ValidationException should the validation fail
   */
  public void setLongName(String longName) throws ValidationException {
    Validators.longNameValidation(longName);
    this.longName = longName;
  }

  /**
   * Returns the shortName of the node
   *
   * @return the shortName of the node
   */
  public String getShortName() {
    return shortName;
  }

  /**
   * Sets the shortName of the node
   *
   * @param shortName the shortName to set
   * @throws ValidationException should the validation fail
   */
  public void setShortName(String shortName) throws ValidationException {
    Validators.shortNameValidation(shortName);
    this.shortName = shortName;
  }

  /**
   * Returns the id of the node
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * sets the name of the node
   *
   * @param id the id to set
   * @throws ValidationException should the validation fail
   */
  public void setId(String id) throws ValidationException {
    Validators.idValidation(id);
    this.id = id;
  }

  /**
   * returns the type of the node
   *
   * @return the type of the node
   */
  public NodeType getType() {
    return type;
  }

  /**
   * sets the node type
   *
   * @param type the node type to set
   */
  public void setType(NodeType type) {
    this.type = type;
  }
=======
    private String id;
    private short xCoord;
    private short yCoord;
    private String building;
    private String longName;
    private String shortName;
    private Type type;
    private short floor;


    public boolean equals(Object object) {
        return false;
    }
>>>>>>> origin/develop


}
