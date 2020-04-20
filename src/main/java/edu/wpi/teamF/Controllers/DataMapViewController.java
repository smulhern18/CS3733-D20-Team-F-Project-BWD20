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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javax.management.InstanceNotFoundException;

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

  @FXML private Label errorNodeLabel;

  @FXML private JFXButton modifyEdgeButton;

  @FXML private JFXButton deleteEdgeButton;

  @FXML private JFXButton selectNode1Button;

  @FXML private JFXButton selectNode2Button;

  @FXML private JFXButton displayEdgePaneButton;

  JFXButton nodeButton = null;
  Line edgeLine = null;

  Node node = null;
  Edge edge = null;
  boolean selectNode1 = false;
  boolean selectNode2 = false;
  Node node1 = null;
  Node node2 = null;

  private static final int MAP_HEIGHT = 1485;
  private static final int MAP_WIDTH = 2475;
  private static final int PANE_HEIGHT = 552;
  private static final int PANE_WIDTH = 920;
  private double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
  private double widthRatio = (double) PANE_WIDTH / MAP_WIDTH;
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();

  public DataMapViewController() {
    //    List<Node> nodes = nodeFactory.getAllNodes();
    //    List<Edge> edges = edgeFactory.getAllEdges();

    //    for (Node node : nodes) {
    //      drawNode(node);
    //    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    for (Node node : nodeFactory.getAllNodes()) {
      if (node.getFloor() == 5) {
        drawNode(node);
      }
    }
    for (Edge edge : edgeFactory.getAllEdges()) {
      if (edge.getNode1().charAt(edge.getNode1().length() - 1) == '5'
          && edge.getNode2().charAt(edge.getNode2().length() - 1) == '5') {
        drawEdge(edge);
      }
    }
  }

  private void drawNode(Node node) {

    double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
    double widthRatio = (double) PANE_WIDTH / MAP_WIDTH;

    JFXButton button = new JFXButton();
    int buttonSize = 6;
    button.setMinSize(buttonSize, buttonSize);
    button.setMaxSize(buttonSize, buttonSize);
    button.setPrefSize(buttonSize, buttonSize);
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px");
    double xPos = (node.getXCoord() * widthRatio - buttonSize / 2.0);
    double yPos = (node.getYCoord() * heightRatio - buttonSize / 2.0);
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    button.setOnAction(
        action -> {
          nodeButton = button;
          this.node = node;
          if (!edgeSelection(node)) {
            modifyNodePane.setVisible(true);
            modifyButton.setDisable(true);
            displayData();
          }
        });
    mapPane.getChildren().add(button);
  }

  private void drawEdge(Edge edge) {
    try {
      double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
      double widthRatio = (double) PANE_WIDTH / MAP_WIDTH;
      Node node1 = nodeFactory.read(edge.getNode1());
      Node node2 = nodeFactory.read(edge.getNode2());
      int startX = (int) (node1.getXCoord() * widthRatio);
      int startY = (int) (node1.getYCoord() * heightRatio);
      int endX = (int) (node2.getXCoord() * widthRatio);
      int endY = (int) (node2.getYCoord() * heightRatio);
      Line line = new Line(startX, startY, endX, endY);
      line.setId(edge.getId());
      line.setStroke(Color.BLACK);
      line.setStrokeWidth(1.5);
      line.setOnMouseClicked(
          mouseEvent -> {
            edgeLine = line;
            this.edge = edge;
            addEdgePane.setVisible(true);
            displayEdgeData();
          });

      mapPane.getChildren().add(0, line);
    } catch (InstanceNotFoundException e) {
      e.printStackTrace();
    }
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
    for (Edge edge : edgeFactory.getAllEdgesConnectedToNode(node.getId())) {
      for (int i = 0; i < mapPane.getChildren().size(); i++) {
        javafx.scene.Node children = mapPane.getChildren().get(i);
        System.out.println(children.getId());
        if (children instanceof Line && children.getId().equals(edge.getId())) {
          mapPane.getChildren().remove(children);
          edgeFactory.delete(children.getId());
          break;
        }
      }
    }
    modifyNodePane.setVisible(false);
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

    Node newNode = nodeFactory.read(ID);

    try { // is the input valid?
      if (newNode == null) { // is the ID available?
        newNode =
            new Node(
                ID, xCoordinate, yCoordinate, building, longName, shortName, nodeType, floorNumber);

        nodeFactory.create(newNode);
        drawNode(newNode);
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
      for (Edge edge : edgeFactory.getAllEdgesConnectedToNode(node.getId())) {
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
          javafx.scene.Node children = mapPane.getChildren().get(i);
          if (children instanceof Line && children.getId().equals(edge.getId())) {
            Line line = (Line) children;
            if (edge.getNode1().equals(node.getId())) {
              line.setStartX(xCoordinate * widthRatio);
              line.setStartY(yCoordinate * heightRatio);
            } else {
              line.setEndX(xCoordinate * widthRatio);
              line.setEndY(yCoordinate * heightRatio);
            }
            break;
          }
        }
      }

      nodeButton.setLayoutX(xCoordinate * widthRatio - 3);
      nodeButton.setLayoutY(yCoordinate * heightRatio - 3);
      resetNodePane();
      modifyNodePane.setVisible(false);

    } catch (Exception e) {
      errorNodeLabel.setText("The input has an incorrect format");
      errorNodeLabel.setVisible(true);
    }
  }

  @FXML
  private void addEdgePane() throws ValidationException {
    addEdgePane.setVisible(true);
    addEdgeButton.setDisable(true);
    modifyEdgeButton.setVisible(false);
    deleteEdgeButton.setVisible(false);
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

  private boolean edgeSelection(Node node) {
    if (selectNode1) {
      node1 = node;
      selectNode1 = false;
      addEdgePane.setVisible(true);
      selectNode1Button.setText(node.getId());
      return true;
    } else if (selectNode2) {
      node2 = node;
      selectNode2 = false;
      addEdgePane.setVisible(true);
      selectNode2Button.setText(node.getId());
      return true;
    }
    return false;
  }

  @FXML
  void selectNode1(ActionEvent event) {
    addEdgePane.setVisible(false);
    selectNode1 = true;
    node1 = null;
    displayNodePaneButton.setVisible(false);
    displayEdgePaneButton.setVisible(false);
    if (!selectNode2Button.getText().equals("Select Node 2")) {
      addEdgeButton.setDisable(false);
    }
  }

  @FXML
  void selectNode2(ActionEvent event) {
    addEdgePane.setVisible(false);
    selectNode2 = true;
    node2 = null;
    displayNodePaneButton.setVisible(false);
    displayEdgePaneButton.setVisible(false);
    if (!selectNode1Button.getText().equals("Select Node 1")) {
      addEdgeButton.setDisable(false);
    }
  }

  private void displayEdgeData() {
    selectNode1Button.setText(edge.getNode1());
    selectNode2Button.setText(edge.getNode2());
    modifyEdgeButton.setVisible(true);
    deleteEdgeButton.setVisible(true);
    addEdgeButton.setVisible(false);
  }

  @FXML
  private void addEdge() throws Exception {
    String node1ID = selectNode1Button.getText();
    String node2ID = selectNode2Button.getText();
    String ID = node1ID + "_" + node2ID;
    Edge edge = new Edge(ID, node1ID, node2ID);
    edgeFactory.create(edge);
    drawEdge(edge);
    addEdgePane.setVisible(false);
    resetEdgeAddPane();
  }

  @FXML
  public void modifyEdge() throws ValidationException {
    String node1ID = selectNode1Button.getText();
    String node2ID = selectNode2Button.getText();
    String ID = node1ID + "_" + node2ID;
    Edge newEdge = new Edge(ID, node1ID, node2ID);
    edgeFactory.delete(edge.getId());
    mapPane.getChildren().remove(edgeLine);
    edgeFactory.create(newEdge);
    drawEdge(newEdge);
    addEdgePane.setVisible(false);
    resetEdgeAddPane();
  }

  @FXML
  public void deleteEdge() {
    edgeFactory.delete(edge.getId());
    mapPane.getChildren().remove(edgeLine);
    addEdgePane.setVisible(false);
    resetEdgeAddPane();
  }

  @FXML
  public void searchEdge() {}

  @FXML
  public void addEdgeModifyPane() {}

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
    selectNode1Button.setText("Select Node 1");
    selectNode2Button.setText("Select Node 2");
    displayNodePaneButton.setVisible(true);
    displayEdgePaneButton.setVisible(true);
    addEdgeButton.setDisable(true);
  }
}
