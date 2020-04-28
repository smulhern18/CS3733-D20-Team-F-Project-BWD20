package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javax.management.InstanceNotFoundException;
import lombok.SneakyThrows;

public class DataMapViewController implements Initializable {

  @FXML private AnchorPane dataMap;

  @FXML private JFXButton addNodeButton; // Adds the given node

  @FXML private GridPane nodeGridPane;

  @FXML private GridPane edgeGridPane;

  @FXML private AnchorPane mapPane1;

  @FXML private ImageView imageView1;

  @FXML private AnchorPane mapPane2;

  @FXML private ImageView imageView2;

  @FXML private AnchorPane mapPane3;

  @FXML private ImageView imageView3;

  @FXML private AnchorPane mapPane4;

  @FXML private ImageView imageView4;

  @FXML private AnchorPane mapPane5;

  @FXML private ImageView imageView5;

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

  @FXML private JFXButton floor1Button;

  @FXML private JFXButton floor2Button;

  @FXML private JFXButton floor3Button;

  @FXML private JFXButton floor4Button;

  @FXML private JFXButton floor5Button;

  @FXML private StackPane imageStackPane;

  private AnchorPane mapPane;

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
  private DatabaseManager databaseManager = DatabaseManager.getManager();

  public DataMapViewController() throws Exception {}

  private enum ModifyType {
    DELETE,
    ADD,
    EDIT
  };

  private ModifyType modifyType;

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    mapPane = mapPane1;

    changeToFloor1();

    for (Edge edge : databaseManager.getAllEdges()) {
      drawEdge(edge);
    } // for every edge that connects two nodes on the fifth floor, draw the edge on the map

    for (Node node : databaseManager.getAllNodes()) {
      drawNode(node);
    }

    typeInput
        .getItems()
        .addAll(
            "CONF", "DEPT", "EXIT", "HALL", "INFO", "LABS", "REST", "RETL", "SERV", "STAF", "STAI");
    typeInput.setValue("CONF");

    uiSetting.makeZoomable(imageScrollPane, imageStackPane, 1);
  }

  @FXML
  public void changeToFloor1() {
    resetImages();
    mapPane1.setVisible(true);
    imageView1.setVisible(true);
    mapPane = mapPane1;
  }

  @FXML
  public void changeToFloor2() {
    resetImages();
    mapPane2.setVisible(true);
    imageView2.setVisible(true);
    mapPane = mapPane2;
  }

  @FXML
  public void changeToFloor3() {
    resetImages();
    mapPane3.setVisible(true);
    imageView3.setVisible(true);
    mapPane = mapPane3;
  }

  @FXML
  public void changeToFloor4() {
    resetImages();
    mapPane4.setVisible(true);
    imageView4.setVisible(true);
    mapPane = mapPane4;
  }

  @FXML
  public void changeToFloor5() {
    resetImages();
    mapPane5.setVisible(true);
    imageView5.setVisible(true);
    mapPane = mapPane5;
  }

  public void resetImages() {
    mapPane1.setVisible(false);
    imageView1.setVisible(false);
    mapPane2.setVisible(false);
    imageView2.setVisible(false);
    mapPane3.setVisible(false);
    imageView3.setVisible(false);
    mapPane3.setVisible(false);
    imageView3.setVisible(false);
    mapPane4.setVisible(false);
    imageView4.setVisible(false);
    mapPane5.setVisible(false);
    imageView4.setVisible(false);
  }

  @FXML
  public void addNodeLocation() {
    double nodeDeltaX = dataMap.getLayoutX() - mapPane.getLayoutX();
    double nodeDeltaY = dataMap.getLayoutY() - mapPane.getLayoutY();
    outlineNode();
    locationSelector();
  }

  @FXML
  public void locationSelector() {
    mapPane.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent mouseEvent) {

            JFXButton oldButton = null;
            for (javafx.scene.Node node : mapPane.getChildren()) {
              if (node instanceof JFXButton) {
                JFXButton oldNode = (JFXButton) node;
                if (oldNode.getId() != null) {
                  oldButton = (JFXButton) node;
                }
              }
            }

            if (oldButton != null) {
              mapPane.getChildren().remove(oldButton);
            }

            double xValDouble = mouseEvent.getX();
            double yValDouble = mouseEvent.getY();
            short xVal = (short) (xValDouble / widthRatio);
            short yVal = (short) (yValDouble / heightRatio);

            xCoorInput.setText("" + xVal);
            yCoorInput.setText("" + yVal);

            JFXButton locationNode = new JFXButton();
            int buttonSize =
                6; // this can be adjusted if we feel like the size is too small or large
            locationNode.setMinSize(buttonSize, buttonSize);
            locationNode.setMaxSize(buttonSize, buttonSize);
            locationNode.setPrefSize(buttonSize, buttonSize); // the button size will not vary
            locationNode.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ADD8E6; -fx-border-color: #000000; -fx-border-width: 1px");
            locationNode.setId("locationNode");
            mapPane.getChildren().add(locationNode);
            locationNode.setLayoutX(xValDouble - buttonSize / 2.0);
            locationNode.setLayoutY(yValDouble - buttonSize / 2.0);
            modifyNodeButton.setDisable(false);
            modifyNodeButton.setOpacity(1);
          }
        });
  }

  @FXML
  public void outlineNode() {
    clearNode();
    clearEdge();
    edgeGridPane.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 10");
    nodeGridPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10");
    typeInput.setStyle(
        "-fx-background-color: #ffffff; -fx-border-radius: 3; -fx-border-color: #b53389");
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
    selectNode1Button.setOpacity(1);
    selectNode2Button.setDisable(false);
    selectNode2Button.setOpacity(1);
  }

  @FXML
  public void clearNode() {
    for (javafx.scene.Node node : mapPane.getChildren()) {
      if (node instanceof JFXButton) {
        JFXButton oldNode = (JFXButton) node;
        if (oldNode.getId() != null) {
          System.out.println("Here2");
          mapPane.getChildren().remove(node);
        }
      }
    }
    nodeGridPane.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 10");
    typeInput.setStyle(
        "-fx-background-color: #eeeeee; -fx-border-radius: 3; -fx-border-color: #b53389");
    longNameInput.setText("");
    shortNameInput.setText("");
    typeInput.setValue("CONF");
    xCoorInput.setText("");
    yCoorInput.setText("");
    buildingInput.setText("");
    floorInput.setText("");
    modifyNodeButton.setVisible(true);
    modifyNodeButton.setDisable(true);
    modifyNodeButton.setOpacity(.4);
    addNodeButton.setVisible(false);
    addNodeButton.setDisable(true);
    deleteNodeButton.setVisible(true);
    deleteNodeButton.setDisable(true);
    deleteNodeButton.setOpacity(.4);
    mapPane.setOnMouseClicked(mouseEvent -> {});
    nodeErrorLabel.setText("");
  }

  @FXML
  private void clearEdge() {
    edgeGridPane.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 10");
    selectNode1Button.setText("Select Node 1");
    selectNode2Button.setText("Select Node 2"); // resets the text in the two buttons
    modifyEdgeButton.setVisible(true);
    modifyEdgeButton.setDisable(true);
    modifyEdgeButton.setOpacity(.4);
    deleteEdgeButton.setVisible(true);
    deleteEdgeButton.setDisable(true);
    deleteEdgeButton.setOpacity(.4);
    deleteNodeButton.setDisable(true);
    deleteNodeButton.setOpacity(.4);
    addEdgeButton.setVisible(false);
    addEdgeButton.setDisable(true);
    addEdgeButton.setOpacity(.4);
    selectNode1Button.setDisable(true);
    selectNode1Button.setOpacity(.4);
    selectNode2Button.setDisable(true);
    selectNode2Button.setOpacity(.4);
    mapPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {});
    edgeErrorLabel.setText("");
  }

  @FXML
  private void drawEdge(Edge edge) {
    try {
      double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
      double widthRatio = (double) PANE_WIDTH / MAP_WIDTH;
      Node node1 = databaseManager.readNode(edge.getNode1());
      Node node2 = databaseManager.readNode(edge.getNode2());
      if (node1.getFloor()
          == node2.getFloor()) { // if the edge connects two nodes on the same floor
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

        switch (node1.getFloor()) {
          case 1:
            mapPane1.getChildren().add(line);
            break;
          case 2:
            mapPane2.getChildren().add(line);
            break;
          case 3:
            mapPane3.getChildren().add(line);
            break;
          case 4:
            mapPane4.getChildren().add(line);
            break;
          case 5:
            mapPane5.getChildren().add(line);
            break;
        }
      }
    } catch (InstanceNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
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
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00008B; -fx-border-color: #000000; -fx-border-width: 1px");
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
            locationSelector();
          }
          if (!selectNode1Button.getText().equals("Select Node 1")
              && !selectNode2Button.getText().equals("Select Node 2")) {
            addEdgeButton.setDisable(false);
            addEdgeButton.setOpacity(1);
            modifyEdgeButton.setDisable(false);
            modifyEdgeButton.setOpacity(1);
          }
        });
    switch (node.getFloor()) {
      case 1:
        mapPane1.getChildren().add(button);
        break;
      case 2:
        mapPane2.getChildren().add(button);
        break;
      case 3:
        mapPane3.getChildren().add(button);
        break;
      case 4:
        mapPane4.getChildren().add(button);
        break;
      case 5:
        mapPane5.getChildren().add(button);
        break;
    }
    // setNodeDraggable(button);
  }

  @FXML
  private void displayNodeData() {
    clearEdge();
    clearNode();
    nodeGridPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10");
    typeInput.setStyle(
        "-fx-background-color: #ffffff; -fx-border-radius: 3; -fx-border-color: #b53389");
    yCoorInput.setText("" + node.getYCoord());
    xCoorInput.setText("" + node.getXCoord());
    buildingInput.setText(node.getBuilding());
    longNameInput.setText(node.getLongName());
    shortNameInput.setText(node.getShortName());
    typeInput.setValue("" + node.getType());
    floorInput.setText("" + node.getFloor());
    deleteNodeButton.setDisable(false);
    deleteNodeButton.setOpacity(1);
  }

  @FXML
  private void displayEdgeData() {
    clearEdge();
    clearNode();
    selectNode1Button.setDisable(false);
    selectNode2Button.setDisable(false);
    edgeGridPane.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10");
    selectNode1Button.setText(edge.getNode1());
    selectNode2Button.setText(edge.getNode2()); // Sets the text of the two buttons to the IDs
    deleteEdgeButton.setDisable(false); // sets the modify and delete button to visible
    deleteEdgeButton.setOpacity(1);
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
  private void deleteNode() throws Exception {
    mapPane.getChildren().remove(nodeButton); // removes the node on the map
    clearNode();
    for (Edge edge : databaseManager.getAllEdgesConnectedToNode(node.getId())) {
      for (int i = 0; i < mapPane.getChildren().size(); i++) { // for child in the pane
        javafx.scene.Node children = mapPane.getChildren().get(i);
        if (children instanceof Line && children.getId().equals(edge.getId())) {
          mapPane.getChildren().remove(children); // remove the edge from the map
          databaseManager.deleteEdge(children.getId()); // remove the edge from the database
          break;
        }
      }
    }
    databaseManager.deleteNode(node.getId()); // removes the node in the database
  }

  @FXML
  private void addNode() throws Exception {

    int tracker = 0;
    int instance = 0;
    int newInstance = 0;
    int instanceNum = 0;

    // try { // is the input valid?
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = buildingInput.getText();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getValue().toString());
    short floorNumber = Short.parseShort(floorInput.getText());
    List<Node> typeNodes = databaseManager.getNodesByType(nodeType);
    List<Integer> typeInstances = new ArrayList<>();
    for (int i = 0; i < typeNodes.size(); i++) { // collects all of the instances for the given type
      if (typeNodes.get(i).getFloor() == floorNumber) {
        instanceNum = Integer.parseInt(typeNodes.get(i).getId().substring(5, 8));
        typeInstances.add(instanceNum);
      }
    }

    Collections.sort(typeInstances); // sorts the list

    if (typeNodes.size() > 0) {
      for (int i = 0; i < typeNodes.size(); i++) {
        instance = Integer.parseInt(typeNodes.get(i).getId().substring(5, 8));
        if (instance - tracker > 1) {
          newInstance = tracker + 1;
        } else {
          tracker++;
        }
      }
    } else {
      newInstance = 1;
    }

    String strInstance = "" + newInstance;
    String strFloor = "0" + floorNumber;

    switch (strInstance.length()) {
      case 1:
        strInstance = "00" + strInstance;
        break;
      case 2:
        strInstance = "0" + strInstance;
        break;
    }

    String ID = "F" + typeInput.getValue() + strInstance + strFloor;

    Node newNode =
        new Node(
            ID,
            xCoordinate,
            yCoordinate,
            building,
            longName,
            shortName,
            nodeType,
            floorNumber); // creates a new node
    databaseManager.manipulateNode(newNode); // creates the node in the db
    drawNode(newNode); // creates the node on the map
    clearNode();
    mapPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {});
    // } catch (Exception e) { // throws an error if the input provided by the user is invalid
    //   nodeErrorLabel.setText("The input is not valid");
    // }
  }

  @FXML
  private void modifyNode() {

    short oldXCoordinate = node.getXCoord();
    short oldYCoordinate = node.getYCoord();
    String oldBuilding = node.getBuilding();
    String oldLongName = node.getLongName();
    String oldShortName = (node.getShortName());
    Node.NodeType oldNodeType = node.getType();
    short oldFloorNumber = node.getFloor(); // stores the input in variables

    try { // is the input correct?
      short xCoordinate = Short.parseShort(xCoorInput.getText());
      short yCoordinate = Short.parseShort(yCoorInput.getText());
      String building = buildingInput.getText();
      String longName = longNameInput.getText();
      String shortName = shortNameInput.getText();
      Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getValue().toString());
      short floorNumber = Short.parseShort(floorInput.getText()); // stores the input in variables

      node.setXCoord(xCoordinate);
      node.setYCoord(yCoordinate);
      node.setBuilding(building);
      node.setLongName(longName);
      node.setShortName(shortName);
      node.setType(nodeType);
      node.setFloor(floorNumber); // sets the node to the provided values

      System.out.println(databaseManager.getAllEdgesConnectedToNode(node.getId()).size());
      databaseManager.manipulateNode(node);
      System.out.println("here1");
      System.out.println(databaseManager.getAllEdgesConnectedToNode(node.getId()).size());
      for (Edge edge :
          databaseManager.getAllEdgesConnectedToNode(
              node.getId())) { // for all of the edges connected to the node
        System.out.println("here2");
        for (int i = 0;
            i < mapPane.getChildren().size();
            i++) { // for all of the children in the pane
          System.out.println("here3");
          javafx.scene.Node children = mapPane.getChildren().get(i);
          if (children instanceof Line && children.getId().equals(edge.getId())) {
            System.out.println("here4");
            Line line = (Line) children;
            if (edge.getNode1()
                .equals(node.getId())) { // if node one then it is a starting coordinate
              System.out.println("here5");
              line.setStartX(xCoordinate * widthRatio);
              line.setStartY(yCoordinate * heightRatio);
            } else { // if node two then it is an ending coordinate
              System.out.println("here6");
              line.setEndX(xCoordinate * widthRatio);
              line.setEndY(yCoordinate * heightRatio);
            }
            break;
          }
        }
      }

      nodeButton.setLayoutX(xCoordinate * widthRatio - 3);
      nodeButton.setLayoutY(yCoordinate * heightRatio - 3);
      clearNode();

    } catch (Exception e) { // throws an error if the input is not valid
      if (oldXCoordinate == node.getXCoord()
          && oldYCoordinate == node.getYCoord()
          && oldBuilding == node.getBuilding()
          && oldLongName.equals(node.getLongName())
          && oldShortName.equals(node.getShortName())
          && oldNodeType.equals(node.getType())
          && oldFloorNumber == node.getFloor()) {
        nodeErrorLabel.setText("The input is invalid");
      }
    }
  }

  @FXML
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
  }

  @FXML
  void selectNode2(ActionEvent event) {
    selectNode2 = true;
    node2 = null;
  }

  @FXML
  private void addEdge() throws Exception {
    String node1ID = selectNode1Button.getText();
    String node2ID = selectNode2Button.getText();
    String ID = node1ID + "_" + node2ID; // The edge ID is the two node IDs combined with a "_"
    Edge edge = new Edge(ID, node1ID, node2ID);
    databaseManager.manipulateEdge(edge); // creates the edge in the db
    drawEdge(edge); // creates the edge on the map
    clearEdge();
  }

  @FXML
  public void modifyEdge() throws Exception {
    String node1ID = selectNode1Button.getText();
    String node2ID = selectNode2Button.getText();
    String ID = node1ID + "_" + node2ID; // The edge ID is the two node IDs combined with a "_"
    Edge newEdge = new Edge(ID, node1ID, node2ID);
    databaseManager.deleteEdge(edge.getId()); // deletes the edge in the db
    mapPane.getChildren().remove(edgeLine); // deletes the edge on the map
    databaseManager.manipulateEdge(newEdge); // creates the edge in the db
    drawEdge(newEdge); // creates the new edge on the map
    // the reason we delete then add for modifying is because the edgeFactory needs the ID to NOT
    clearEdge();
  }

  @FXML
  public void deleteEdge() throws Exception {
    databaseManager.deleteEdge(edge.getId()); // deletes the edge in the db
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
      modifyNodeButton.setOpacity(1);
      addNodeButton.setDisable(false);
      addNodeButton.setOpacity(1);
    } else {
      modifyNodeButton.setDisable(true);
      modifyNodeButton.setOpacity(.4);
      addNodeButton.setDisable(true);
      addNodeButton.setOpacity(.4);
    }
  }
}
