package edu.wpi.teamF.ModelClasses;

public class Edge {
  private String Id;
  private Node Node1;
  private Node Node2;

  public Edge(String id, Node node1, Node node2) throws ValidationException {
    setId(id);
    setNode1(node1);
    setNode2(node2);
  }

  public String getId() {
    return Id;
  }

  public void setId(String id) throws ValidationException {
    Validators.edgeIdValidation(id);
    this.Id = id;
  }

  public Node getNode1() {
    return Node1;
  }

  public void setNode1(Node node1) throws ValidationException {
    Validators.nodeValidation(node1);
    this.Node1 = node1;
  }

  public Node getNode2() {
    return Node2;
  }

  public void setNode2(Node node2) throws ValidationException {
    Validators.nodeValidation(node2);
    this.Node2 = node2;
  }

  public boolean equals(Object other) {
    boolean isEqual = false;
    if (other instanceof Edge) {
      Edge otherEdge = (Edge) other;
      isEqual = this.Id.equals(otherEdge.Id);
    }
    return isEqual;
  }
}
