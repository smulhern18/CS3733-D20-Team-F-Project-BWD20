package edu.wpi.teamF.controllers;

import edu.wpi.teamF.factories.NodeFactory;
import edu.wpi.teamF.modelClasses.Node;
import edu.wpi.teamF.modelClasses.Node.NodeType;
import java.awt.*;
import java.io.IOException;

import edu.wpi.teamF.modelClasses.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ModifyDataController extends SceneController {
  SceneController sceneController = new SceneController();

  public void mainMenuSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  public void pathfinderSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  public void downloadSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DownloadData");
  }

  public void displaySwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DisplayData");
  }

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

  private enum ModifyType{DELETE, ADD, EDIT};
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
    NodeType nodeType = NodeType.getEnum(nodeTypeText.getText());
    short floorNumber = Short.parseShort(floorNumberText.getText());

    Node node = new Node(xCoordinate, yCoordinate, building, longName, shortName, name, nodeType, floorNumber);

    nodeFactory.createNode(node);
  }

  public void deleteNode() {
    try {
      Node node = nodeFactory.readNode(nodeText.getText());
      nodeFactory.deleteNode(node);
    } catch (Exception e) {
      System.out.println("Node not found");
    }
  }

  public void editNode() {

  }

  public void submitData(ActionEvent actionEvent) throws Exception {
    if(modifyType.equals(ModifyType.DELETE)){
      deleteNode();
    }
    if(modifyType.equals(ModifyType.ADD)){
      addNode();
    }
    if(modifyType.equals(ModifyType.EDIT)){
      editNode();
    }
  }
}
