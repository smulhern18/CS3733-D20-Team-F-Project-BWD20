package edu.wpi.teamF.Controllers;

import static javafx.scene.paint.Color.RED;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

public class DataMapViewController implements Initializable {
  @FXML private AnchorPane mapPane;

  @FXML private StackPane modifyNodePane;

  @FXML private StackPane addEdgePane;

  @FXML private StackPane modifyEdgePane;

  @FXML private JFXButton addNodeButton;

  @FXML private JFXButton deleteButton;

  @FXML private JFXButton modifyButton;

  @FXML private JFXTextField nodeIDInput;

  @FXML private JFXTextField yCoorInput;

  @FXML private JFXTextField xCoorInput;

  @FXML private JFXTextField typeInput;

  @FXML private JFXTextField shortNameInput;

  @FXML private JFXTextField longNameInput;

  @FXML private JFXTextField buildingInput;

  @FXML private JFXTextField floorInput;

  @FXML private JFXButton displayNodePaneButton;

  @FXML private JFXButton addEdgeButton;

  @FXML private JFXTextField edgeIDInput;

  @FXML private JFXTextField node1Input;

  @FXML private JFXTextField node2Input;

  @FXML private Label errorNodeLabel;

  @FXML private Label errorEdgeLabel;

  @FXML private JFXButton modifyEdgeButton;

  @FXML private JFXTextField edgeModifyIDInput;

  @FXML private JFXTextField node1ModifyInput;

  @FXML private JFXTextField node2ModifyInput;

  @FXML private JFXButton cancelModifyEdgeButton;

  @FXML private Label errorModifyEdgeLabel;

  @FXML private JFXButton deleteEdgeButton;

  @FXML private JFXButton searchEdgeButton;

  JFXButton nodeButton = null;

  Node node = null;
  Edge edge = null;

  private static final int MAP_HEIGHT = 1485;
  private static final int MAP_WIDTH = 2475;
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();

  public DataMapViewController() {
    //    List<Node> nodes = nodeFactory.getAllNodes();
    //    List<Edge> edges = edgeFactory.getAllEdges();

    //    for (Node node : nodes) {
    //      drawNode(node);
    //    }
  }

  private void drawNode(Node node) {
    double heightRatio = mapPane.getMaxHeight() / MAP_HEIGHT;
    double widthRatio = mapPane.getMaxWidth() / MAP_WIDTH;

    JFXButton button = new JFXButton();
    button.setMinSize(12, 12);
    button.setMaxSize(12, 12);
    button.setPrefSize(12, 12);
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px");
    int xPos = (int) (200 * widthRatio);
    int yPos = (int) (200 * heightRatio);
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    button.setOnAction(
        action -> {
          nodeButton = button;
          this.node = node;
          modifyNodePane.setVisible(true);
          modifyButton.setDisable(true);
          displayData();
        });
    mapPane.getChildren().add(button);
  }

  private void drawEdge(Edge edge) {
    double heightRatio = mapPane.getMaxHeight() / MAP_HEIGHT;
    double widthRatio = mapPane.getMaxWidth() / MAP_WIDTH;

    Line line = new Line();
    .setMinSize(12, 12);
    button.setMaxSize(12, 12);
    button.setPrefSize(12, 12);
    button.setStyle(
            "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px");
    int xPos = (int) (200 * widthRatio);
    int yPos = (int) (200 * heightRatio);
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    button.setOnAction(
            action -> {
              nodeButton = button;
              this.node = node;
              modifyNodePane.setVisible(true);
              modifyButton.setDisable(true);
              displayData();
            });
    mapPane.getChildren().add(button);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    drawNode(null);
    System.out.println("Test");
  }

  private enum ModifyType {
    DELETE,
    ADD,
    EDIT
  };

  private ModifyType modifyType;

  @FXML
  private void cancelNodePane() {
    resetNodePane();
    modifyNodePane.setVisible(false);
  }

  @FXML
  private void cancelEdgePane() {
    resetEdgeAddPane();
    addEdgePane.setVisible(false);
  }

  @FXML
  private void deleteData() {
    nodeFactory.delete(node.getId()); // in the database
    mapPane.getChildren().remove(nodeButton); // on the map
    resetNodePane();
  }

  @FXML
  private void addNodePane() throws ValidationException {
    modifyNodePane.setVisible(true);
    modifyButton.setVisible(false);
    deleteButton.setVisible(false);
    addNodeButton.setVisible(true);
    addNodeButton.setDisable(true);
    nodeIDInput.setEditable(true);
  }

  @FXML
  private void addNode() throws Exception {
    String ID = nodeIDInput.getText();
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = buildingInput.getText();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getText());
    short floorNumber = Short.parseShort(floorInput.getText());

    node = nodeFactory.read(ID);

    try { // is the input valid?
      if (node == null) { // is the ID available?
        node.setId(ID);
        node.setXCoord(xCoordinate);
        node.setYCoord(yCoordinate);
        node.setBuilding(building);
        node.setLongName(longName);
        node.setShortName(shortName);
        node.setType(nodeType);
        node.setFloor(floorNumber);

        nodeFactory.create(node);
        drawNode(node);
        resetNodePane();
        modifyNodePane.setVisible(false);
      } else {
        errorNodeLabel.setText("The ID already exists");
        errorNodeLabel.setVisible(true);
        nodeIDInput.setUnFocusColor(RED);
      }
    } catch (Exception e) {
      errorNodeLabel.setText("The input has an incorrect format");
      errorNodeLabel.setVisible(true);
    }
  }

  @FXML
  private void modifyData() throws ValidationException {
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = buildingInput.getText();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getText());
    short floorNumber = Short.parseShort(floorInput.getText());

    try { // is the input correct?
      node.setXCoord(xCoordinate);
      node.setYCoord(yCoordinate);
      node.setBuilding(building);
      node.setLongName(longName);
      node.setShortName(shortName);
      node.setType(nodeType);
      node.setFloor(floorNumber);

      nodeFactory.update(node);
      double heightRatio = mapPane.getHeight() / MAP_HEIGHT;
      double widthRatio = mapPane.getWidth() / MAP_WIDTH;
      nodeButton.setLayoutX(xCoordinate * widthRatio);
      nodeButton.setLayoutY(yCoordinate * heightRatio);
      resetNodePane();
      modifyNodePane.setVisible(false);

    } catch (Exception e) {
      errorNodeLabel.setText("The input has an incorrect format");
      errorNodeLabel.setVisible(true);
    }
    node.setXCoord(xCoordinate);
    node.setYCoord(yCoordinate);
    node.setBuilding(building);
    node.setLongName(longName);
    node.setShortName(shortName);
    node.setType(nodeType);
    node.setFloor(floorNumber);

    nodeFactory.update(node);
    double heightRatio = mapPane.getHeight() / MAP_HEIGHT;
    double widthRatio = mapPane.getWidth() / MAP_WIDTH;
    nodeButton.setLayoutX(xCoordinate * widthRatio);
    nodeButton.setLayoutY(yCoordinate * heightRatio);
    resetNodePane();
  }

  @FXML
  private void addEdgePane() throws ValidationException {
    addEdgePane.setVisible(true);
    addEdgeButton.setDisable(true);
  }

  @FXML
  private void displayData() { // when a node is clicked, the node's attributes will be displayed
    nodeIDInput.setText(node.getId());
    yCoorInput.setText("" + node.getYCoord());
    xCoorInput.setText("" + node.getXCoord());
    buildingInput.setText(node.getBuilding());
    longNameInput.setText(node.getLongName());
    shortNameInput.setText(node.getShortName());
    typeInput.setText("" + node.getType());
    floorInput.setText("" + node.getFloor());
  }

  @FXML
  private void addEdge() throws Exception {
    String ID = edgeIDInput.getText();
    String node1ID = node1Input.getText();
    String node2ID = node2Input.getText();

    edge = edgeFactory.read(ID);

    try { // is the input valid?
      if (edge == null) { // is the ID available?
        edge.setId(ID);
        node = nodeFactory.read(node1ID);
        edge.setNode1(node);
        node = nodeFactory.read(node2ID);
        edge.setNode2(node);
        edgeFactory.create(edge);
        // drawNode(node);
        resetEdgeAddPane();
        addEdgePane.setVisible(false);
      } else {
        errorEdgeLabel.setText("The ID already exists");
        errorEdgeLabel.setVisible(true);
        edgeIDInput.setUnFocusColor(RED);
      }
    } catch (Exception e) {
      errorEdgeLabel.setText("The input is not valid");
      errorEdgeLabel.setVisible(true);
    }
  }

  @FXML
  public void searchEdge() {}

  @FXML
  public void addEdgeModifyPane() {}

  @FXML
  public void modifyEdge() {}

  @FXML
  public void deleteEdge() {}

  @FXML
  public void validateNodeText(KeyEvent keyEvent) {
    if (!xCoorInput.getText().isEmpty()
        && !yCoorInput.getText().isEmpty()
        && !buildingInput.getText().isEmpty()
        && !longNameInput.getText().isEmpty()
        && !shortNameInput.getText().isEmpty()
        && !typeInput.getText().isEmpty()
        && !floorInput.getText().isEmpty()) {
      modifyButton.setDisable(false);
      addNodeButton.setDisable(false);
    } else {
      modifyButton.setDisable(true);
      addNodeButton.setDisable(true);
    }
  }

  @FXML
  public void validateEdgeText(KeyEvent keyEvent) {
    if (!edgeIDInput.getText().isEmpty()
        && !node1Input.getText().isEmpty()
        && !node2Input.getText().isEmpty()) {
      addEdgeButton.setDisable(false);
    } else {
      addEdgeButton.setDisable(true);
    }
  }

  @FXML
  private void resetNodePane() {
    xCoorInput.setText("");
    yCoorInput.setText("");
    buildingInput.setText("");
    longNameInput.setText("");
    shortNameInput.setText("");
    typeInput.setText("");
    floorInput.setText("");
    nodeIDInput.setText("");
    modifyButton.setVisible(true);
    deleteButton.setVisible(true);
    addNodeButton.setVisible(false);
    nodeIDInput.setEditable(false);
    errorNodeLabel.setVisible(false);
    errorNodeLabel.setText("");
  }

  @FXML
  private void resetEdgeAddPane() {
    edgeIDInput.setText("");
    node1Input.setText("");
    node2Input.setText("");
    addEdgeButton.setDisable(true);
    errorEdgeLabel.setText("");
    errorEdgeLabel.setVisible(false);
  }
}
