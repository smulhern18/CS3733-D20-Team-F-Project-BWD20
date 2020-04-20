package edu.wpi.teamF.ModelClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
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
    this.yCoord = new SimpleStringProperty("" + node.getYCoord());
    this.building = new SimpleStringProperty(node.getBuilding());
    this.floor = new SimpleStringProperty("" + node.getFloor());
    this.longName = new SimpleStringProperty(node.getLongName());
    this.shortName = new SimpleStringProperty(node.getShortName());
    this.nodeType = new SimpleStringProperty("" + node.getType());
  }

  public Node UItoNode() throws ValidationException {
    try {
      return new Node(
          "" + ID.get(),
          Short.parseShort(xCoord.get()),
          Short.parseShort(yCoord.get()),
          building.get(),
          longName.get(),
          shortName.get(),
          Node.NodeType.getEnum(nodeType.get()),
          Short.parseShort(floor.get()));
    } catch (Exception e) {
      System.out.println(e.getClass() + "" + e.getMessage());
      System.out.println(xCoord.get());
    }
    return null;
  }

  /**
   * Checks if two nodes are equal
   *
   * @param other the otherNode to check against
   * @return if the nodes are equal without edges
   */
  public boolean equalsNode(Object other) {
    boolean isEqual = false;
    if (other instanceof Node) {
      Node otherNode = (Node) other;

      isEqual =
          this.ID.toString().equals(otherNode.getId())
              && this.getXCoord().get().equals("" + otherNode.getXCoord())
              && this.getYCoord().get().equals("" + otherNode.getYCoord())
              && this.getFloor().get().equals("" + otherNode.getFloor())
              && this.getNodeType().get().equals(otherNode.getType().getTypeString())
              && this.getBuilding().get().equals(otherNode.getBuilding())
              && this.getLongName().get().equals(otherNode.getLongName())
              && this.getShortName().get().equals(otherNode.getShortName());
    }
    return isEqual;
  }
}
