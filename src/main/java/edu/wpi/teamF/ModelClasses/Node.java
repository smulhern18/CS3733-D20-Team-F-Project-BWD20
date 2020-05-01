package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import java.util.HashSet;
import java.util.Set;
import javax.management.InstanceNotFoundException;

public class Node {

  DatabaseManager databaseManager = DatabaseManager.getManager();

  public enum NodeType {
    // Values
    HALL("HALL"),
    ELEV("ELEV"),
    REST("REST"),
    STAI("STAI"),
    DEPT("DEPT"),
    LABS("LABS"),
    INFO("INFO"),
    CONF("CONF"),
    EXIT("EXIT"),
    RETL("RETL"),
    SERV("SERV"),
    STAF("STAF");

    // Constructor
    private String typeString;
    // Constructor
    NodeType(String string) {
      this.typeString = string;
    }

    public String getTypeString() {
      return this.typeString;
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
  private String floor;
  private Set<Edge> edges = new HashSet<>();
  private Set<Node> neighborNodes = new HashSet<>();

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
      String floor)
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
      String floor,
      Set<Edge> edge)
      throws Exception {
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
  public void setEdges(Set<Edge> edge) throws InstanceNotFoundException {
    this.edges = edge;
    //    for (Edge anEdge : edge) {
    //      if (anEdge.getNode1().equals(this.id)) {
    //        neighborNodes.add(nodeFactory.read(anEdge.getNode2()));
    //      } else {
    //        neighborNodes.add(nodeFactory.read(anEdge.getNode1()));
    //      }
    //    }
  }

  /**
   * adds a edge to the edges array
   *
   * @param edge the edges to add
   */
  public void addEdge(Edge edge) throws Exception {
    this.edges.add(edge);
    if (edge.getNode1().equals(this.id)) {
      neighborNodes.add(databaseManager.readNode(edge.getNode2()));
    } else {
      neighborNodes.add(databaseManager.readNode(edge.getNode1()));
    }
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

  /**
   * returns the floor of the node
   *
   * @return the floor the node is on
   */
  public String getFloor() {
    return floor;
  }

  /**
   * sets the floor the node is on
   *
   * @param floor the floor to set
   * @throws ValidationException should the validation fail
   */
  public void setFloor(String floor) throws ValidationException {
    Validators.floorValidation(floor);
    this.floor = floor;
  }

  public Set<Node> getNeighborNodes() {

    return neighborNodes;
  }
}
