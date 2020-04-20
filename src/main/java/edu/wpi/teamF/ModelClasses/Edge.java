package edu.wpi.teamF.ModelClasses;

public class Edge {
  private String Id;
  private String Node1;
  private String Node2;

  public Edge(String id, String node1, String node2) throws ValidationException {
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

  public String getNode1() {
    return Node1;
  }

  public void setNode1(String node1) throws ValidationException {
    Validators.idValidation(node1);
    this.Node1 = node1;
  }

  public String getNode2() {
    return Node2;
  }

  public void setNode2(String node2) throws ValidationException {
    Validators.idValidation(node2);
    this.Node2 = node2;
  }


  public boolean equals(Object other) {
    boolean isEqual = false;
    if (other instanceof Edge) {
      Edge otherEdge = (Edge) other;
      isEqual =
          this.Id.equals(otherEdge.Id)
              && this.Node1.equals(otherEdge.Node1)
              && this.Node2.equals(otherEdge.Node2);
    }
    return isEqual;
  }

}
