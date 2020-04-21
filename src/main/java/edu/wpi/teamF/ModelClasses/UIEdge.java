package edu.wpi.teamF.ModelClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UIEdge extends RecursiveTreeObject<UIEdge> {

  public StringProperty ID;
  public StringProperty node1ID;
  public StringProperty node2ID;

  public UIEdge(Edge edge) {
    this.ID = new SimpleStringProperty(edge.getId());
    this.node1ID = new SimpleStringProperty(edge.getNode1());
    this.node2ID = new SimpleStringProperty(edge.getNode2());
  }

  public Edge UItoEdge() throws ValidationException {
    return new Edge("" + ID.get(), node1ID.get(), node2ID.get());
  }

  public boolean equalsEdge(Object other) {
    boolean isEqual = false;
    if (other instanceof Edge) {
      Edge otherEdge = (Edge) other;
      isEqual =
          this.ID.get().equals(otherEdge.getId())
              && this.node1ID.get().equals(otherEdge.getNode1())
              && this.node2ID.get().equals(otherEdge.getNode2());
    }
    return isEqual;
  }
}
