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
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javax.management.InstanceNotFoundException;

public class DataMapViewController implements Initializable {

  @FXML
  private AnchorPane mapPane; // The pane that holds the following panes and makes the screen darker

  @FXML private StackPane modifyNodePane; // The pane that deals with nodes

  @FXML private StackPane addEdgePane; // The pane that deals with edges

  @FXML private JFXButton addNodeButton; // Adds the given node

  @FXML private JFXButton deleteButton; // Deletes the given node

  @FXML private JFXButton modifyButton; // Modifies the selected node with the input

  @FXML private JFXTextField nodeIDInput;

  @FXML private JFXTextField yCoorInput;

  @FXML private JFXTextField xCoorInput;

  @FXML private JFXTextField typeInput;

  @FXML private JFXTextField shortNameInput;

  @FXML private JFXTextField longNameInput;

  @FXML private JFXTextField buildingInput;

  @FXML private JFXTextField floorInput;

  @FXML private JFXButton displayNodePaneButton; // "Add Node" button, bottom right of screen

  @FXML private JFXButton addEdgeButton; // "Add Edge" button, bottom right of screen

  @FXML private Label errorNodeLabel; // Error label for incorrect node input

  @FXML private JFXButton modifyEdgeButton; // Modifies the selected edge with the input

  @FXML private JFXButton deleteEdgeButton; // Deletes the selected edge

  @FXML
  private JFXButton selectNode1Button; // Button to select the first node when creating an edge

  @FXML
  private JFXButton selectNode2Button; // Button to select the second node when creating a node

  @FXML private JFXButton displayEdgePaneButton; // "Add Edge" button

  @FXML private StackPane imageStackPane;

  @FXML private ImageView imageView;

  JFXButton nodeButton = null;
  Line edgeLine = null;

  Node node = null;
  Edge edge = null;
  boolean selectNode1 = false;
  boolean selectNode2 = false;
  Node node1 = null;
  Node node2 = null;

  private static final int MAP_HEIGHT = 1485;
  private static final int MAP_WIDTH = 2475; // height and width of the map
  private static final int PANE_HEIGHT = 552;
  private static final int PANE_WIDTH = 920; // height and width of the pane/image
  private double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
  private double widthRatio = (double) PANE_WIDTH / MAP_WIDTH; // ratio of pane to map
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    for (Node node : nodeFactory.getAllNodes()) {
      if (node.getFloor() == 5) {
        drawNode(node);
      } // for every node on the fifth floor, draw the node on the map
    }
    for (Edge edge : edgeFactory.getAllEdges()) {
      if (edge.getNode1().charAt(edge.getNode1().length() - 1) == '5'
          && edge.getNode2().charAt(edge.getNode2().length() - 1) == '5') {
        drawEdge(edge);
      }
    } // for every edge that connects two nodes on the fifth floor, draw the edge on the map
  }

  public void zoom(ScrollEvent event) {
    double zoomFactor = 1.5;
    if (event.getDeltaY() <= 0) {
      // zoom out
      zoomFactor = 1 / zoomFactor;
    }
    zoom(imageStackPane, zoomFactor, event.getSceneX(), event.getSceneY());
  }

  public void zoom(javafx.scene.Node node, double factor, double x, double y) {
    // determine scale
    double oldScale = node.getScaleX();
    double scale = oldScale * factor;
    double f = (scale / oldScale) - 1;

    // determine offset that we will have to move the node
    Bounds bounds = node.localToScene(node.getBoundsInLocal());
    double dx = (x - (bounds.getWidth() / 2 + bounds.getMinX()));
    double dy = (y - (bounds.getHeight() / 2 + bounds.getMinY()));
    node.setTranslateX(node.getTranslateX() - f * dx);
    node.setTranslateY(node.getTranslateY() - f * dx);
    node.setScaleX(scale);
    node.setScaleY(scale);
  }

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
    double yPos =
        (node.getYCoord() * heightRatio - buttonSize / 2.0); // points to the center of the button
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    button.setOnAction( // when a user clicks a node:
        action -> {
          nodeButton = button; // sets classes button variable to the selected button
          this.node = node; // sets the classes node variable to the selected node
          if (!edgeSelection(node)) {
            modifyNodePane.setVisible(true);
            modifyButton.setDisable(true);
            displayData();
          } // This makes sure that the action of selecting 2 nodes for an edge on a map differs
          // from selecting and displaying a node
          // If the user is selecting 2 nodes for an edge, the modify pane as well as the nodes info
          // should not be visible
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
            this.edge = edge; // updates the classes' variables
            addEdgePane.setVisible(true);
            displayEdgeData(); // The edge pain that displays the edges information should be
            // displayed
          });

      mapPane.getChildren().add(0, line); // adds the line as a child to the pane
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
  private void cancelNodePane() { // this is called when the red x button is clicked
    resetNodePane(); // resets the values in the node pane
    modifyNodePane.setVisible(false); // makes the pane not visible
  }

  @FXML
  private void cancelEdgePane() { // this is called when the red x button is clicked
    resetEdgeAddPane(); // resets the values in the edge pane
    addEdgePane.setVisible(false); // makes the pane not visible
  }

  @FXML
  private void deleteData() {
    nodeFactory.delete(node.getId()); // removes the node in the database
    mapPane.getChildren().remove(nodeButton); // removes the node on the map
    resetNodePane();
    for (Edge edge : edgeFactory.getAllEdgesConnectedToNode(node.getId())) {
      for (int i = 0; i < mapPane.getChildren().size(); i++) { // for child in the pane
        javafx.scene.Node children = mapPane.getChildren().get(i);
        System.out.println(children.getId());
        if (children instanceof Line && children.getId().equals(edge.getId())) {
          mapPane.getChildren().remove(children); // remove the edge from the map
          edgeFactory.delete(children.getId()); // remove the edge from the database
          break;
        }
      }
    }
    modifyNodePane.setVisible(false);
  }

  @FXML
  private void addNodePane() throws ValidationException {
    modifyNodePane.setVisible(true); // set the pane to be visible
    modifyButton.setVisible(false);
    deleteButton.setVisible(false); // modify and delete button are not visible

    addNodeButton.setVisible(true);
    addNodeButton.setDisable(
        true); // the add button should only be enabled when all fields are occupied
    nodeIDInput.setEditable(true); // The node ID can be edited, it can NOT when modifying and node
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
    short floorNumber = Short.parseShort(floorInput.getText()); // stores the inputs into variables

    Node newNode = nodeFactory.read(ID); // does the ID exist?

    try { // is the input valid?
      if (newNode == null) { // is the ID available?
        newNode =
            new Node(
                ID,
                xCoordinate,
                yCoordinate,
                building,
                longName,
                shortName,
                nodeType,
                floorNumber); // creates a new node
        nodeFactory.create(newNode); // creates the node in the db
        drawNode(newNode); // creates the node on the map
        resetNodePane();
        modifyNodePane.setVisible(false);
      } else { // fails the if statement if the ID already exists
        errorNodeLabel.setText("The ID already exists");
        errorNodeLabel.setVisible(true);
        nodeIDInput.setUnFocusColor(RED);
      }
    } catch (Exception e) { // throws an error if the input provided by the user is invalid
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
    short floorNumber = Short.parseShort(floorInput.getText()); // stores the input in variables

    try { // is the input correct?
      node.setXCoord(xCoordinate);
      node.setYCoord(yCoordinate);
      node.setBuilding(building);
      node.setLongName(longName);
      node.setShortName(shortName);
      node.setType(nodeType);
      node.setFloor(floorNumber); // sets the node to the provided values

      nodeFactory.update(
          node); // finds the node by the ID (this is why the ID is uneditable) and updates the node
      for (Edge edge :
          edgeFactory.getAllEdgesConnectedToNode(
              node.getId())) { // for all of the edges connected to the node
        for (int i = 0;
            i < mapPane.getChildren().size();
            i++) { // for all of the children in the pane
          javafx.scene.Node children = mapPane.getChildren().get(i);
          if (children instanceof Line
              && children
                  .getId()
                  .equals(
                      edge.getId())) { // if a child is an instance of a line and the ID matches one
            // of the ID that is connected to the node
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
      resetNodePane();
      modifyNodePane.setVisible(false);

    } catch (Exception e) { // throws an error if the input is not valid
      errorNodeLabel.setText("The input has an incorrect format");
      errorNodeLabel.setVisible(true);
    }
  }

  @FXML
  private void addEdgePane() throws ValidationException {
    addEdgePane.setVisible(true);
    addEdgeButton.setDisable(true); // the edge should be enabled only when two nodes are selected
    modifyEdgeButton.setVisible(false);
    deleteEdgeButton.setVisible(
        false); // modify and delete should only be displayed when selecting an edge on the map
    addEdgeButton.setVisible(true);
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
    floorInput.setText(
        "" + node.getFloor()); // converts the stored data into strings and displays it
  }

  private boolean edgeSelection(
      Node node) { // this function helps differentiate when a user is selecting a node or selecting
    // two nodes for creating an edge
    if (selectNode1) {
      node1 = node;
      selectNode1 = false;
      addEdgePane.setVisible(true);
      selectNode1Button.setText(node.getId());
      return true; // returns true if the selectedNode1 bool is true and sets the boolean to false
    } else if (selectNode2) {
      node2 = node;
      selectNode2 = false;
      addEdgePane.setVisible(true);
      selectNode2Button.setText(node.getId());
      return true; // returns true id the selectedNode2 bool is true and sets the boolean to false
    }
    return false; // returns false if neither booleans are true (the user is not adding an edge)
  }

  @FXML
  void selectNode1(ActionEvent event) {
    addEdgePane.setVisible(false); // displays the map
    selectNode1 = true;
    node1 = null;
    displayNodePaneButton.setVisible(false);
    displayEdgePaneButton.setVisible(
        false); // sets the two buttons in the bottom right corner to be invisible
    if (!selectNode2Button.getText().equals("Select Node 2")) { // checks the other button
      addEdgeButton.setDisable(false);
    }
  }

  @FXML
  void selectNode2(
      ActionEvent
          event) { // called when the "Select Node 1" button is pressed (when the user is adding an
    // edge)
    addEdgePane.setVisible(false); // displays the map
    selectNode2 = true;
    node2 = null;
    displayNodePaneButton.setVisible(false);
    displayEdgePaneButton.setVisible(
        false); // sets the two buttons in the bottom right corner to be invisible
    if (!selectNode1Button
        .getText()
        .equals("Select Node 1")) { // checks the other button, if populated, the add button is
      // activated
      addEdgeButton.setDisable(false);
    }
  }

  private void displayEdgeData() {
    selectNode1Button.setText(edge.getNode1());
    selectNode2Button.setText(edge.getNode2()); // Sets the text of the two buttons to the IDs
    modifyEdgeButton.setVisible(true);
    deleteEdgeButton.setVisible(true); // sets the modify and delete button to visible
    addEdgeButton.setVisible(false);
  }

  @FXML
  private void addEdge() throws Exception {
    String node1ID = selectNode1Button.getText();
    String node2ID = selectNode2Button.getText();
    String ID = node1ID + "_" + node2ID; // The edge ID is the two node IDs combined with a "_"
    Edge edge = new Edge(ID, node1ID, node2ID);
    edgeFactory.create(edge); // creates the edge in the db
    drawEdge(edge); // creates the edge on the map
    addEdgePane.setVisible(false);
    resetEdgeAddPane();
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
    addEdgePane.setVisible(false);
    resetEdgeAddPane();
  }

  @FXML
  public void deleteEdge() {
    edgeFactory.delete(edge.getId()); // deletes the edge in the db
    mapPane.getChildren().remove(edgeLine); // deletes the edge on the map
    addEdgePane.setVisible(false);
    resetEdgeAddPane();
  }

  @FXML
  public void validateNodeText(KeyEvent keyEvent) {
    System.out.println("Before if");
    if (!nodeIDInput.getText().isEmpty()
        && !xCoorInput.getText().isEmpty()
        && !yCoorInput.getText().isEmpty()
        // && !buildingInput.getText().isEmpty()
        && !longNameInput.getText().isEmpty()
        && !shortNameInput.getText().isEmpty()
        && !typeInput.getText().isEmpty()
    //  && !floorInput.getText().isEmpty()
    ) { // if every input is occupied:
      modifyButton.setDisable(false);
      addNodeButton.setDisable(false); // the add button and the modify button should be enabled
      // delete assumes the node clicked on by the user is always the selected node
    } else {
      modifyButton.setDisable(true);
      addNodeButton.setDisable(
          true); // if the text is added then deleted, this checks for that condition
    }
  }

  @FXML
  private void resetNodePane() {
    xCoorInput.setText("");
    yCoorInput.setText("");
    longNameInput.setText("");
    shortNameInput.setText("");
    typeInput.setText("");
    nodeIDInput.setText("");
    modifyButton.setVisible(true);
    deleteButton.setVisible(true);
    addNodeButton.setVisible(false);
    nodeIDInput.setEditable(false); // the node ID should not be changed, needed to update the node
    errorNodeLabel.setVisible(false);
    errorNodeLabel.setText("");
  }

  @FXML
  private void resetEdgeAddPane() {
    selectNode1Button.setText("Select Node 1");
    selectNode2Button.setText("Select Node 2"); // resets the text in the two buttons
    displayNodePaneButton.setVisible(true);
    displayEdgePaneButton.setVisible(true); // the two buttons (bottom right) are visible
    addEdgeButton.setDisable(true);
  }
}
