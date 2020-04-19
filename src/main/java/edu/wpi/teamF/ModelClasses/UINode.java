package edu.wpi.teamF.ModelClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UINode extends RecursiveTreeObject<UINode> {

  public StringProperty ID;
  public StringProperty xCoord;
  public StringProperty yCoord;
  public StringProperty building;
  public StringProperty floor;
  public StringProperty longName;
  public StringProperty shortName;
  public StringProperty nodeType;

  public UINode(Node node) {
    this.ID = new SimpleStringProperty(node.getId());
    this.xCoord = new SimpleStringProperty("" + node.getXCoord());
    this.yCoord = new SimpleStringProperty("" + node.getXCoord());
    this.building = new SimpleStringProperty(node.getBuilding());
    this.floor = new SimpleStringProperty("" + node.getFloor());
    this.longName = new SimpleStringProperty(node.getLongName());
    this.shortName = new SimpleStringProperty(node.getShortName());
    this.nodeType = new SimpleStringProperty("" + node.getType());
  }

  public Node UItoNode() throws ValidationException {
    return new Node(
        "" + ID,
        Short.parseShort(xCoord.toString()),
        Short.parseShort(yCoord.toString()),
        building.toString(),
        longName.toString(),
        shortName.toString(),
        Node.NodeType.getEnum(nodeType.toString()),
        Short.parseShort(floor.toString()));
  }
}
