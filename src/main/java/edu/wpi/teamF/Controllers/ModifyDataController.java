package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.Factories.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class ModifyDataController extends SceneController {

  @FXML private TextField nodeText;
  @FXML private Button addButton;
  @FXML private Button deleteButton;
  @FXML private Button editButton;
  @FXML private Label locationNameLabel;

  @FXML private TextField xCoordinateText;
  @FXML private TextField yCoordinateText;
  @FXML private TextField buildingText;
  @FXML private TextField longNameText;
  @FXML private TextField shortNameText;
  @FXML private TextField nodeTypeText;
  @FXML private TextField floorNumberText;
  @FXML private GridPane gridPane;

  @FXML private Button submitButton;
  @FXML private Button findLocationButton;

  private enum ModifyType {
    DELETE,
    ADD,
    EDIT
  };

  private ModifyType modifyType;

  private NodeFactory nodeFactory = NodeFactory.getFactory();

  @FXML
  private void deleteButtonPress() {
    reset();
    nodeText.setDisable(false);
    locationNameLabel.setDisable(false);
    modifyType = ModifyType.DELETE;
  }

  @FXML
  private void addButtonPress() {
    reset();
    nodeText.setDisable(false);
    gridPane.setDisable(false);
    locationNameLabel.setDisable(false);
    modifyType = ModifyType.ADD;
  }

  @FXML
  private void editButtonPress() {
    reset();
    nodeText.setDisable(false);
    locationNameLabel.setDisable(false);
    modifyType = ModifyType.EDIT;
  }

  public void addNode() {
    try {
      String name = nodeText.getText();

      short xCoordinate = Short.parseShort(xCoordinateText.getText());
      short yCoordinate = Short.parseShort(yCoordinateText.getText());
      String building = buildingText.getText();
      String longName = longNameText.getText();
      String shortName = shortNameText.getText();
      Node.NodeType nodeType = Node.NodeType.getEnum(nodeTypeText.getText());
      short floorNumber = Short.parseShort(floorNumberText.getText());

      Node node =
          new Node(
              name, xCoordinate, yCoordinate, building, longName, shortName, nodeType, floorNumber);

      nodeFactory.create(node);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void deleteNode() {
    try {
      Node node = nodeFactory.read(nodeText.getText());
      nodeFactory.delete(node);
    } catch (Exception e) {
      System.out.println("Node not found" + e.getMessage() + " " + e.getClass());
    }
  }

  public void editNode() throws Exception {
    try {
      String name = nodeText.getText();
      short xCoordinate = Short.parseShort(xCoordinateText.getText());
      short yCoordinate = Short.parseShort(yCoordinateText.getText());
      String building = buildingText.getText();
      String longName = longNameText.getText();
      String shortName = shortNameText.getText();
      Node.NodeType nodeType = Node.NodeType.getEnum(nodeTypeText.getText());
      short floorNumber = Short.parseShort(floorNumberText.getText());

      Node node = nodeFactory.read(name);
      node.setXCoord(xCoordinate);
      node.setYCoord(yCoordinate);
      node.setBuilding(building);
      node.setLongName(longName);
      node.setShortName(shortName);
      node.setType(nodeType);
      node.setFloor(floorNumber);

      nodeFactory.update(node);

    } catch (Exception e) {
      System.out.println("invalid information" + e.getMessage() + " " + e.getClass());
    }
  }

  @FXML
  public void findNode(ActionEvent actionEvent) {
    try {
      gridPane.setDisable(false);
      submitButton.setDisable(false);
      String nodeName = nodeText.getText();
      Node node = nodeFactory.read(nodeName);
      xCoordinateText.setText("" + node.getXCoord());
      yCoordinateText.setText("" + node.getYCoord());
      buildingText.setText(node.getBuilding());
      longNameText.setText(node.getLongName());
      shortNameText.setText(node.getShortName());
      nodeTypeText.setText("" + node.getType());
      floorNumberText.setText("" + node.getFloor());
    } catch (Exception e) {
      System.out.println("Error with nodefactory" + e.getClass() + " " + e.getMessage());
    }
  }

  @FXML
  public void submitData(ActionEvent actionEvent) throws Exception {
    if (modifyType.equals(ModifyType.DELETE)) {
      deleteNode();
    }
    if (modifyType.equals(ModifyType.ADD)) {
      addNode();
    }
    if (modifyType.equals(ModifyType.EDIT)) {
      editNode();
    }
  }

  public void validateText(KeyEvent keyEvent) {
    if (modifyType.equals(ModifyType.DELETE)) {
      submitButton.setDisable(false);
    }

    if (modifyType.equals(ModifyType.EDIT)) {
      findLocationButton.setDisable(false);
    }
  }

  public void validateNodeInfo(KeyEvent keyEvent) {
    if (!xCoordinateText.getText().isEmpty()
        && !yCoordinateText.getText().isEmpty()
        && !buildingText.getText().isEmpty()
        && !longNameText.getText().isEmpty()
        && !shortNameText.getText().isEmpty()
        && !nodeTypeText.getText().isEmpty()
        && !floorNumberText.getText().isEmpty()) {
      submitButton.setDisable(false);
    } else {
      submitButton.setDisable(true);
    }
  }

  private void reset() {
    submitButton.setDisable(true);
    gridPane.setDisable(true);
    xCoordinateText.setText("");
    yCoordinateText.setText("");
    buildingText.setText("");
    longNameText.setText("");
    shortNameText.setText("");
    nodeTypeText.setText("");
    floorNumberText.setText("");
    nodeText.setText("");
    findLocationButton.setDisable(true);
    nodeText.setDisable(true);
  }
}
