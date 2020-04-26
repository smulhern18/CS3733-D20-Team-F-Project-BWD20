package edu.wpi.teamF.ModelClasses;

public class Edge {
  private String id;
  private String node1;
  private String node2;

  public Edge(String id, String node1, String node2) throws ValidationException {
    setId(id);
    setNode1(node1);
    setNode2(node2);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) throws ValidationException {
    Validators.edgeIdValidation(id);
    this.id = id;
  }

  public String getNode1() {
    return node1;
  }

  public void setNode1(String node1) throws ValidationException {
    Validators.idValidation(node1);
    this.node1 = node1;
  }

  public String getNode2() {
    return node2;
  }

  public void setNode2(String node2) throws ValidationException {
    Validators.idValidation(node2);
    this.node2 = node2;
  }

  public boolean equals(Object other) {
    boolean isEqual = false;
    if (other instanceof Edge) {
      Edge otherEdge = (Edge) other;
      isEqual =
          this.id.equals(otherEdge.id)
              && this.node1.equals(otherEdge.node1)
              && this.node2.equals(otherEdge.node2);
    }
    return isEqual;
  }
}
