package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javax.management.InstanceNotFoundException;

public class DataMapViewController implements Initializable {

  @FXML private AnchorPane dataMap;

  @FXML private JFXButton addNodeButton; // Adds the given node

  @FXML private GridPane nodeGridPane;

  @FXML private GridPane edgeGridPane;

  @FXML private AnchorPane mapPane;

  @FXML private JFXTextField yCoorInput;

  @FXML private JFXTextField xCoorInput;

  @FXML private JFXTextField shortNameInput;

  @FXML private JFXTextField longNameInput;

  @FXML private JFXTextField buildingInput;

  @FXML private JFXTextField floorInput;

  @FXML private JFXButton addEdgeButton;

  @FXML private JFXButton modifyEdgeButton;

  @FXML private JFXButton modifyNodeButton;

  @FXML private JFXButton deleteNodeButton;

  @FXML private JFXButton deleteEdgeButton;

  @FXML private JFXButton selectNode1Button;

  @FXML private JFXButton selectNode2Button;

  @FXML private ChoiceBox typeInput;

  @FXML private Label nodeErrorLabel;

  @FXML private Label edgeErrorLabel;

  @FXML private ScrollPane imageScrollPane;

  @FXML private StackPane imageStackPane;

  JFXButton nodeButton = null;
  Line edgeLine = null;

  Node node = null;
  Edge edge = null;
  boolean selectNode1 = false;
  boolean selectNode2 = false;
  Node node1 = null;
  Node node2 = null;

  double deltaX = 0;
  double deltaY = 0;

  UISetting uiSetting = new UISetting();

  private static final int MAP_HEIGHT = 1485;
  private static final int MAP_WIDTH = 2475; // height and width of the map
  private static final int PANE_HEIGHT = 551;
  private static final int PANE_WIDTH = 912; // height and width of the pane/image
  private double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
  private double widthRatio = (double) PANE_WIDTH / MAP_WIDTH; // ratio of pane to map
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();

  private enum ModifyType {
    DELETE,
    ADD,
    EDIT
  };

  private ModifyType modifyType;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    for (Edge edge : edgeFactory.getAllEdges()) {
      if (edge.getNode1().charAt(edge.getNode1().length() - 1) == '5'
          && edge.getNode2().charAt(edge.getNode2().length() - 1) == '5') {
        drawEdge(edge);
      }
    } // for every edge that connects two nodes on the fifth floor, draw the edge on the map

    for (Node node : nodeFactory.getAllNodes()) {
      if (node.getFloor() == 5) {
        drawNode(node);
      } // for every node on the fifth floor, draw the node on the map
    }

    typeInput
        .getItems()
        .addAll(
            "CONF", "DEPT", "ELEV", "EXIT", "HALL", "INFO", "LABS", "REST", "RETL", "SERV", "STAF",
            "STAI");
    typeInput.setValue("CONF");

    uiSetting.makeZoomable(imageScrollPane, imageStackPane);
  }

  @FXML
  public void addNodeLocation() {
    double nodeDeltaX = dataMap.getLayoutX() - mapPane.getLayoutX();
    double nodeDeltaY = dataMap.getLayoutY() - mapPane.getLayoutY();
    System.out.println(mapPane.getLayoutX());
    System.out.println(nodeDeltaX);
    System.out.println(nodeDeltaY);
    mapPane.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent mouseEvent) {
            double xValDouble = mouseEvent.getX();
            double yValDouble = mouseEvent.getY();
            short xVal = (short) (xValDouble / widthRatio);
            short yVal = (short) (yValDouble / heightRatio);

            xCoorInput.setText("" + xVal);
            yCoorInput.setText("" + yVal);
          }
        });
    outlineNode();
  }

  @FXML
  public void outlineNode() {
    clearNode();
    clearEdge();
    edgeGridPane.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 10");
    nodeGridPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10");
    modifyNodeButton.setVisible(false);
    deleteNodeButton.setVisible(false);
    addNodeButton.setVisible(true);
  }

  @FXML
  public void outlineEdge() {
    clearEdge();
    clearNode();
    edgeGridPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10");
    nodeGridPane.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 10");
    modifyEdgeButton.setVisible(false);
    deleteEdgeButton.setVisible(false);
    addEdgeButton.setVisible(true);
    selectNode1Button.setDisable(false);
    selectNode2Button.setDisable(false);
  }

  @FXML
  public void clearNode() {
    nodeGridPane.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 10");
    longNameInput.setText("");
    shortNameInput.setText("");
    typeInput.setValue("CONF");
    xCoorInput.setText("");
    yCoorInput.setText("");
    buildingInput.setText("");
    floorInput.setText("");
    modifyNodeButton.setVisible(true);
    modifyNodeButton.setDisable(true);
    addNodeButton.setVisible(false);
    addNodeButton.setDisable(true);
    deleteNodeButton.setVisible(true);
    deleteNodeButton.setDisable(true);
    mapPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {});
    nodeErrorLabel.setText("");
  }

  @FXML
  private void clearEdge() {
    edgeGridPane.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 10");
    selectNode1Button.setText("Select Node 1");
    selectNode2Button.setText("Select Node 2"); // resets the text in the two buttons
    modifyEdgeButton.setVisible(true);
    modifyEdgeButton.setDisable(true);
    deleteEdgeButton.setVisible(true);
    deleteEdgeButton.setDisable(true);
    deleteNodeButton.setDisable(true);
    addEdgeButton.setVisible(false);
    addEdgeButton.setDisable(true);
    selectNode1Button.setDisable(true);
    selectNode2Button.setDisable(true);
    mapPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {});
    edgeErrorLabel.setText("");
  }

  private void drawEdge(Edge edge) {
    try {
      double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
      double widthRatio = (double) PANE_WIDTH / MAP_WIDTH;
      Node node1 = nodeFactory.read(edge.getNode1());
      Node node2 = nodeFactory.read(edge.getNode2());
      int startX = (int) (node1.getXCoord() * widthRatio);
      int startY = (int) (node1.getYCoord() * heightRatio); // start values correspond to node 1
      int endX = (int) (node2.getXCoord() * widthRatio);
      int endY = (int) (node2.getYCoord() * heightRatio); // end values correspond to node 2
      Line line = new Line(startX, startY, endX, endY);
      line.setId(edge.getId()); // allows us to keep track of what line is what edge
      line.setStroke(Color.BLACK);
      line.setStrokeWidth(1.5);
      line.setOnMouseClicked(
          mouseEvent -> { // when a user clicks on a line:
            edgeLine = line;
            this.edge = edge;
            displayEdgeData();
          });

      mapPane.getChildren().add(line);
    } catch (InstanceNotFoundException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void drawNode(Node node) { // draws the given node on the map

    double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
    double widthRatio = (double) PANE_WIDTH / MAP_WIDTH;

    JFXButton button = new JFXButton();
    int buttonSize = 6; // this can be adjusted if we feel like the size is too small or large
    button.setMinSize(buttonSize, buttonSize);
    button.setMaxSize(buttonSize, buttonSize);
    button.setPrefSize(buttonSize, buttonSize); // the button size will not vary
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px");
    double xPos = (node.getXCoord() * widthRatio - buttonSize / 2.0);
    double yPos = (node.getYCoord() * heightRatio - buttonSize / 2.0);
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    button.setOnAction(
        action -> {
          nodeButton = button; // sets classes button variable to the selected button
          this.node = node;
          if (selectNode1 || selectNode2) {
            edgeSelection(node);
            // addEdgeButton.setDisable(false);
          } else {
            displayNodeData();
          }

          if (!selectNode1Button.getText().equals("Select Node 1")
              && !selectNode2Button.getText().equals("Select Node 2")) {
            addEdgeButton.setDisable(false);
          }
        });
    mapPane.getChildren().add(button);
    // setNodeDraggable(button);
  }

  @FXML
  private void displayNodeData() {
    clearEdge();
    clearNode();
    nodeGridPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10");
    yCoorInput.setText("" + node.getYCoord());
    xCoorInput.setText("" + node.getXCoord());
    buildingInput.setText(node.getBuilding());
    longNameInput.setText(node.getLongName());
    shortNameInput.setText(node.getShortName());
    typeInput.setValue("" + node.getType());
    floorInput.setText("" + node.getFloor());
    deleteNodeButton.setDisable(false);
  }

  private void displayEdgeData() {
    clearEdge();
    clearNode();
    edgeGridPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10");
    selectNode1Button.setText(edge.getNode1());
    selectNode2Button.setText(edge.getNode2()); // Sets the text of the two buttons to the IDs
    deleteEdgeButton.setDisable(false); // sets the modify and delete button to visible
  }

  private void setNodeDraggable(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          deltaX = button.getLayoutX() - mouseEvent.getSceneX();
          deltaY = button.getLayoutY() - mouseEvent.getSceneY();
        });
    button.setOnMouseDragged(
        mouseEvent -> {
          button.setLayoutX(mouseEvent.getSceneX() + deltaX);
          button.setLayoutY(mouseEvent.getSceneY() + deltaY);
        });
  }

  @FXML
  private void deleteNode() {
    mapPane.getChildren().remove(nodeButton); // removes the node on the map
    clearNode();
    for (Edge edge : edgeFactory.getAllEdgesConnectedToNode(node.getId())) {
      for (int i = 0; i < mapPane.getChildren().size(); i++) { // for child in the pane
        javafx.scene.Node children = mapPane.getChildren().get(i);
        if (children instanceof Line && children.getId().equals(edge.getId())) {
          mapPane.getChildren().remove(children); // remove the edge from the map
          edgeFactory.delete(children.getId()); // remove the edge from the database
          break;
        }
      }
    }
    nodeFactory.delete(node.getId()); // removes the node in the database
  }

  @FXML
  private void addNode() throws Exception {


    try { // is the input valid?
      short xCoordinate = Short.parseShort(xCoorInput.getText());
      short yCoordinate = Short.parseShort(yCoorInput.getText());
      String building = buildingInput.getText();
      String longName = longNameInput.getText();
      String shortName = shortNameInput.getText();
      Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getValue().toString());
      short floorNumber = Short.parseShort(floorInput.getText());

      Node newNode =
          new Node(
              "testID",
              xCoordinate,
              yCoordinate,
              building,
              longName,
              shortName,
              nodeType,
              floorNumber); // creates a new node
      nodeFactory.create(newNode); // creates the node in the db
      drawNode(newNode); // creates the node on the map
      clearNode();
      mapPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {});
    } catch (Exception e) { // throws an error if the input provided by the user is invalid
      nodeErrorLabel.setText("The input is not valid");
    }
  }

  @FXML
  private void modifyData() throws ValidationException {
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = buildingInput.getText();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getValue().toString());
    short floorNumber = Short.parseShort(floorInput.getText()); // stores the input in variables

    try { // is the input correct?
      node.setXCoord(xCoordinate);
      node.setYCoord(yCoordinate);
      node.setBuilding(building);
      node.setLongName(longName);
      node.setShortName(shortName);
      node.setType(nodeType);
      node.setFloor(floorNumber); // sets the node to the provided values

      nodeFactory.update(node);
      for (Edge edge :
          edgeFactory.getAllEdgesConnectedToNode(
              node.getId())) { // for all of the edges connected to the node
        for (int i = 0;
            i < mapPane.getChildren().size();
            i++) { // for all of the children in the pane
          javafx.scene.Node children = mapPane.getChildren().get(i);
          if (children instanceof Line && children.getId().equals(edge.getId())) {
            Line line = (Line) children;
            if (edge.getNode1()
                .equals(node.getId())) { // if node one then it is a starting coordinate
              line.setStartX(xCoordinate * widthRatio);
              line.setStartY(yCoordinate * heightRatio);
            } else { // if node two then it is an ending coordinate
              line.setEndX(xCoordinate * widthRatio);
              line.setEndY(yCoordinate * heightRatio);
            }
            break;
          }
        }
      }

      nodeButton.setLayoutX(xCoordinate * widthRatio - 3);
      nodeButton.setLayoutY(
          yCoordinate * heightRatio
              - 3); // if the position of the node is changes, then the edges are updated as well
      clearNode();

    } catch (Exception e) { // throws an error if the input is not valid

    }
  }

  private boolean edgeSelection(Node node) {
    if (selectNode1) {
      node1 = node;
      selectNode1 = false;
      selectNode1Button.setText(node.getId());
      return true; // returns true if the selectedNode1 bool is true and sets the boolean to false
    } else if (selectNode2) {
      node2 = node;
      selectNode2 = false;
      selectNode2Button.setText(node.getId());
      return true; // returns true id the selectedNode2 bool is true and sets the boolean to false
    }
    return false; // returns false if neither booleans are true (the user is not adding an edge)
  }

  @FXML
  void selectNode1(ActionEvent event) {
    selectNode1 = true;
    node1 = null;
    //    if (!selectNode2Button.getText().equals("Select Node 2")) {
    //      addEdgeButton.setDisable(false);
    //    }
  }

  @FXML
  void selectNode2(ActionEvent event) {
    selectNode2 = true;
    node2 = null;
    //    if (!selectNode1Button.getText().equals("Select Node 1")) {
    //      addEdgeButton.setDisable(false);
    //    }
  }

  @FXML
  private void addEdge() throws Exception {
    String node1ID = selectNode1Button.getText();
    String node2ID = selectNode2Button.getText();
    String ID = node1ID + "_" + node2ID; // The edge ID is the two node IDs combined with a "_"
    Edge edge = new Edge(ID, node1ID, node2ID);
    edgeFactory.create(edge); // creates the edge in the db
    drawEdge(edge); // creates the edge on the map
    clearEdge();
  }

  @FXML
  public void modifyEdge() throws ValidationException {
    String node1ID = selectNode1Button.getText();
    String node2ID = selectNode2Button.getText();
    String ID = node1ID + "_" + node2ID; // The edge ID is the two node IDs combined with a "_"
    Edge newEdge = new Edge(ID, node1ID, node2ID);
    edgeFactory.delete(edge.getId()); // deletes the edge in the db
    mapPane.getChildren().remove(edgeLine); // deletes the edge on the map
    edgeFactory.create(newEdge); // creates the edge in the db
    drawEdge(newEdge); // creates the new edge on the map
    // the reason we delete then add for modifying is because the edgeFactory needs the ID to NOT
    // change in order to update the values
    // however, the ID of an edge is dependent on the two nodes it is connected to, and if the admin
    // wants to change the connected nodes, the ID has to change as well
    clearEdge();
  }

  @FXML
  public void deleteEdge() {
    edgeFactory.delete(edge.getId()); // deletes the edge in the db
    mapPane.getChildren().remove(edgeLine); // deletes the edge on the map
    clearEdge();
  }

  @FXML
  public void validateNodeText(KeyEvent keyEvent) {
    if (!xCoorInput.getText().isEmpty()
        && !yCoorInput.getText().isEmpty()
        && !buildingInput.getText().isEmpty()
        && !longNameInput.getText().isEmpty()
        && !shortNameInput.getText().isEmpty()
        && !floorInput.getText().isEmpty()) { // if every input is occupied:
      modifyNodeButton.setDisable(false);
      addNodeButton.setDisable(false);
    } else {
      modifyNodeButton.setDisable(true);
      addNodeButton.setDisable(true);
    }
  }
}
