package edu.wpi.teamF.Controllers.MapEditor;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Node;
import java.util.List;
import lombok.Data;

@Data
public class NodeButton {
  Node node;
  double tempX;
  double tempY;
  String tempBuilding;
  String tempLongName;
  String tempShortName;
  String tempType;
  String tempFloor;

  private DatabaseManager databaseManager = DatabaseManager.getManager();

  public NodeButton(Node node) {
    this.node = node;
    this.tempX = node.getXCoord();
    this.tempY = node.getYCoord();
    this.tempBuilding = node.getBuilding();
    this.tempLongName = node.getLongName();
    this.tempShortName = node.getShortName();
    this.tempType = node.getType().getTypeString();
    this.tempFloor = node.getFloor();
  }

  public NodeButton(double newX, double newY, String floor) throws Exception {
    String building = "45 Francis";
    if (floor.equals("1")
        || floor.equals("2")
        || floor.equals("3")
        || floor.equals("4")
        || floor.equals("5")) {
      building = "Faulkner";
    }
    node =
        new Node(
            generateNodeID("DEPT", floor),
            (short) newX,
            (short) newY,
            building,
            "long name",
            "short name",
            Node.NodeType.DEPT,
            floor);
    reset();
  }

  //  public NodeButton(
  //      String shortName,
  //      String longName,
  //      String xCoord,
  //      String yCoord,
  //      String building,
  //      String floor,
  //      String type)
  //      throws Exception {
  //    createNode(shortName, longName, xCoord, yCoord, building, floor, type);
  //    this.tempNode =
  //        new Node(
  //            node.getId(),
  //            node.getXCoord(),
  //            node.getYCoord(),
  //            node.getBuilding(),
  //            node.getLongName(),
  //            node.getShortName(),
  //            node.getType(),
  //            node.getFloor());
  //  }

  public void reset() {
    this.tempX = node.getXCoord();
    this.tempY = node.getYCoord();
    this.tempBuilding = node.getBuilding();
    this.tempLongName = node.getLongName();
    this.tempShortName = node.getShortName();
    this.tempType = node.getType().getTypeString();
    this.tempFloor = node.getFloor();
  }

  public void updateDatabase(
      String shortName,
      String longName,
      String xCoord,
      String yCoord,
      String building,
      String floor,
      String type)
      throws Exception {
    Node updatedNode =
        new Node(
            node.getId(),
            (short) Double.parseDouble(xCoord),
            (short) Double.parseDouble(yCoord),
            building,
            longName,
            shortName,
            Node.NodeType.getEnum(type),
            floor);

    node = updatedNode;
    reset();
    databaseManager.manipulateNode(updatedNode);
  }

  private String generateNodeID(String nodeType, String floor) throws Exception {
    List<Node> typeNodes = databaseManager.getNodesByType(Node.NodeType.getEnum(nodeType));
    String startOfID = "Z" + nodeType;
    String endOfID = "";
    if (floor.length() == 1) {
      endOfID += "0" + floor;
    } else {
      endOfID += floor;
    }
    for (int i = 0; i <= 999; i++) {
      String strInstance = "" + i;
      switch (strInstance.length()) {
        case 1:
          strInstance = "00" + strInstance;
          break;
        case 2:
          strInstance = "0" + strInstance;
          break;
      }
      String newID = startOfID + strInstance + endOfID;
      if (!containsInTypeNodes(typeNodes, newID)) {
        return newID;
      }
    }
    return null;
  }

  private boolean containsInTypeNodes(List<Node> typeNodes, String newID) {
    for (Node typeNode : typeNodes) {
      if (typeNode.getId().equals(newID)) {
        return true;
      }
    }
    return false;
  }

  public String getID() {
    return node.getId();
  }

  public void deleteFromDatabase() throws Exception {
    databaseManager.deleteNode(node.getId());
  }
}
