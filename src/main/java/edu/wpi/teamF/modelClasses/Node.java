package edu.wpi.teamF.modelClasses;



import java.util.HashSet;
import java.util.Set;



public class Node {

    public enum NodeType {
        // Values
        HALL("HALL"), ELEV("ELEV"),REST("REST"),STAI("STAI"),DEPT("DEPT"),LABS("LABS"),INFO("INFO"),CONF("CONF"),EXIT("EXIT"),RETL("RETL"),SERV("SERV");

        private String typeString;
        // Constructor
        private NodeType (String type){
            this.typeString = type;
        }

        // Get the string value from enum type
        public final String getTypeString () {
            return typeString;
        }

        // Get enum type from string
        public static NodeType getEnum (String typeString) {
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
    private String name;
    private NodeType type;
    private short floor;
    private Set<Node> neighbors = new HashSet<>();;

    /**
     * Constructor for Nodes
     * @param xCoord the xCoordinate of the node
     * @param yCoord the yCoordinate of the node
     * @param name the name of the node
     * @param nodeType the type of the node
     * @param floor the floor the node is on
     * @throws ValidationException should anything go wrong
     */
    public Node(short xCoord, short yCoord, String name, NodeType nodeType, short floor) throws ValidationException {
        setXCoord(xCoord);
        setYCoord(yCoord);
        setName(name);
        setType(nodeType);
        setFloor(floor);
    }

    /**
     * returns the neighbors set
     * @return the set of neighbor nodes
     */
    public Set<Node> getNeighbors() {
        return neighbors;
    }

    /**
     * adds a neighbor to the neighbor set
     * @param neighbor the neighbor to add
     */
    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * adds a set of neighbors to the neighbor set
     * @param neighbors the neighbors to add
     */
    public void addNeighbor(Set<Node> neighbors) {
        this.neighbors.addAll(neighbors);
    }


    /**
     * Checks if two nodes are equal
     * @param other the otherNode to check against
     * @return if the nodes are equal or not
     */
    public boolean equals(Object other) {
        boolean isEqual = false;
        if (other != null && other instanceof Node) {
            Node otherNode = (Node) other;
            boolean neighborsEquals = true;
            if (otherNode.getNeighbors().size() == neighbors.size()){
                for (Node neighborNode: neighbors) {
                    if (!(otherNode.getNeighbors().contains(neighborNode) && neighborsEquals)){
                        neighborsEquals = false;
                    } else {
                        neighborsEquals = true;
                    }
                }
            } else {
                neighborsEquals = false;
            }
            isEqual = this.name.equals(otherNode.getName()) &&
                      this.getXCoord() == otherNode.getXCoord() &&
                      this.getYCoord() == otherNode.getYCoord() &&
                      this.getFloor() == otherNode.getFloor() &&
                      this.getType() == otherNode.getType() && neighborsEquals;

        }
        return isEqual;
    }

    /**
     * Returns the xCoord of the node
     * @return the xCoord
     */
    public short getXCoord() {
        return xCoord;
    }

    /**
     * Sets the xCoord
     * @param xCoord the xCoord to set
     * @throws ValidationException should validation Fail
     */
    public void setXCoord(short xCoord) throws ValidationException {
        Validators.coordValidation(xCoord);
        this.xCoord = xCoord;
    }

    /**
     * Returns the yCoord of the node
     * @return the yCoord
     */
    public short getYCoord() {
        return yCoord;
    }

    /**
     * Sets the yCoord
     * @param yCoord the yCoord to set
     * @throws ValidationException should validation fail
     */
    public void setYCoord(short yCoord) throws ValidationException {
        Validators.coordValidation(yCoord);
        this.yCoord = yCoord;
    }

    /**
     * Returns the name of the node
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the node
     * @param name the name to set
     * @throws ValidationException should the validation fail
     */
    public void setName(String name) throws ValidationException {
        Validators.nameValidation(name);
        this.name = name;
    }

    /**
     * returns the type of the node
     * @return the type of the node
     */
    public NodeType getType() {
        return type;
    }

    /**
     * sets the node type
     * @param type the node type to set
     */
    public void setType(NodeType type) {
        this.type = type;
    }

    /**
     * returns the floor of the node
     * @return the floor the node is on
     */
    public int getFloor() {
        return floor;
    }

    /**
     * sets the floor the node is on
     * @param floor the floor to set
     * @throws ValidationException should the validation fail
     */
    public void setFloor(short floor) throws ValidationException{
        Validators.floorValidation(floor);
        this.floor = floor;
    }
}
