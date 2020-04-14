package edu.wpi.teamF.controllers;

import edu.wpi.teamF.factories.NodeFactory;
import edu.wpi.teamF.modelClasses.Node;
import java.awt.*;
import java.io.IOException;
import edu.wpi.teamF.modelClasses.ValidationException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ModifyDataController extends SceneController {

  @FXML private TextField nodeText;
  @FXML private Button addButton;
  @FXML private Button deleteButton;
  @FXML private Button editButton;

  @FXML private TextField xCoordinateText;
  @FXML private TextField yCoordinateText;
  @FXML private TextField buildingText;
  @FXML private TextField longNameText;
  @FXML private TextField shortNameText;
  @FXML private TextField nodeTypeText;
  @FXML private TextField floorNumberText;
  @FXML private GridPane gridPane;

  @FXML private Button submitButton;

  private enum ModifyType {
    DELETE,
    ADD,
    EDIT
  };

  private ModifyType modifyType;

  private NodeFactory nodeFactory = null;

  @FXML
  private void deleteButtonPress() {
    nodeText.setDisable(false);
    modifyType = ModifyType.DELETE;
  }

  @FXML
  private void addButtonPress() {
    nodeText.setDisable(false);
    gridPane.setDisable(false);
    modifyType = ModifyType.ADD;
  }

  @FXML
  private void editButtonPress() {
    nodeText.setDisable(false);
    gridPane.setDisable(false);
    modifyType = ModifyType.EDIT;
  }

  public void addNode() throws Exception {
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
  }

  public void deleteNode() {
    try {
      Node node = nodeFactory.read(nodeText.getText());
      nodeFactory.delete(node);
    } catch (Exception e) {
      System.out.println("Node not found");
    }
  }

  public void editNode() throws Exception {
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
  }

  @FXML
  public void findNode(ActionEvent actionEvent) throws Exception {
    String nodeName = nodeText.getText();
    Node node = nodeFactory.read(nodeName);
    xCoordinateText.setText("" + node.getXCoord());
    yCoordinateText.setText("" + node.getYCoord());
    buildingText.setText(node.getBuilding());
    longNameText.setText(node.getLongName());
    shortNameText.setText(node.getShortName());
    nodeTypeText.setText("" + node.getType());
    floorNumberText.setText("" + node.getFloor());
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

}
