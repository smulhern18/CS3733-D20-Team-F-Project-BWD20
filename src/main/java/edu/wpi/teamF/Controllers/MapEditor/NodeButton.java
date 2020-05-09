package edu.wpi.teamF.Controllers.MapEditor;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import lombok.Data;

@Data
public class NodeButton {
  Node node;
  Node tempNode;

  String shortName;
  String longName;
  String xCoord;
  String yCoord;
  String building;
  String floor;
  String type;

  private DatabaseManager databaseManager = DatabaseManager.getManager();

  public NodeButton(Node node) {
    this.node = node;
  }

  public NodeButton(
      String shortName,
      String longName,
      String xCoord,
      String yCoord,
      String building,
      String floor,
      String type)
      throws ValidationException {
    this.node = node;
    this.tempNode =
        new Node(
            node.getId(),
            node.getXCoord(),
            node.getYCoord(),
            node.getBuilding(),
            node.getLongName(),
            node.getShortName(),
            node.getType(),
            node.getFloor());
    this.shortName = shortName;
    this.longName = longName;
    // this.xCoord =
  }

  public void updateDatabase() throws Exception {
    databaseManager.manipulateNode(tempNode);
    node = tempNode;
  }

  public void modifyNode(
      String shortName,
      String longName,
      String nodeXCoord,
      String nodeYCoord,
      String building,
      String floor,
      String nodeType)
      throws Exception {
    short xCoord = Short.parseShort(nodeXCoord);
    short yCoord = Short.parseShort(nodeYCoord);
    Node.NodeType type = Node.NodeType.getEnum(nodeType);
    tempNode.setId(generateNodeID(nodeType, floor));
    tempNode.setXCoord(xCoord);
    tempNode.setYCoord(yCoord);
    tempNode.setBuilding(building);
    tempNode.setLongName(longName);
    tempNode.setShortName(shortName);
    tempNode.setType(type);
    tempNode.setFloor(floor);
    databaseManager.deleteNode(node.getId());
    databaseManager.manipulateNode(tempNode);
  }

  private String generateNodeID(String type, String floor) {
    String id = "";
    return id;
  }
}
