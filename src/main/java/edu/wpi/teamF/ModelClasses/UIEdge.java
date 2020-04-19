package edu.wpi.teamF.ModelClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIEdge extends RecursiveTreeObject<UIEdge> {

  public StringProperty ID;
  public StringProperty node1ID;
  public StringProperty node2ID;

  public UIEdge(Edge edge) {
    this.ID = new SimpleStringProperty(edge.getId());
    this.node1ID = new SimpleStringProperty(edge.getNode1());
    this.node2ID = new SimpleStringProperty(edge.getNode2());
  }
}
