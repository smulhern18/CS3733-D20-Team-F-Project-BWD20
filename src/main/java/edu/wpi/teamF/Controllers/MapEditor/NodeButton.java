package edu.wpi.teamF.Controllers.MapEditor;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Node;
import lombok.Data;

@Data
public class NodeButton {
  private Node node;

  private DatabaseManager databaseManager = DatabaseManager.getManager();

  public NodeButton(Node node) {
    this.node = node;
  }

  public Node getNode() {
    return this.node;
  }

  public NodeButton(
      String shortName,
      String longName,
      String xCoord,
      String yCoord,
      String building,
      String floor,
      String type)
      throws Exception {
    this.node = createNode(shortName, longName, xCoord, yCoord, building, floor, type);
  }

  public void updateDatabase() throws Exception {
    databaseManager.manipulateNode(node);
  }

  public void modifyNode(
      String shortName,
      String longName,
      String xCoord,
      String yCoord,
      String building,
      String floor,
      String type)
      throws Exception {
    databaseManager.deleteNode(node.getId());
    this.node = createNode(shortName, longName, xCoord, yCoord, building, floor, type);
    databaseManager.manipulateNode(node);
  }

  private Node createNode(
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
    String id = generateNodeID(nodeType, floor);
    Node node = new Node(id, xCoord, yCoord, building, longName, shortName, type, floor);
    databaseManager.manipulateNode(node);
    return node;
  }

  private void deleteNode() throws Exception {
    databaseManager.deleteNode(this.node.getId());
  }

  private String generateNodeID(String nodeType, String floor) {
    String id = "";
    return id;
  }
}
