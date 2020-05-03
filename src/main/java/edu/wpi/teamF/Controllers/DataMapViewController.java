package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class DataMapViewController implements Initializable {

  @FXML private AnchorPane mainFloorPane;

  @FXML private AnchorPane faulknerFloorPane;

  @FXML private JFXButton faulknerFloor1Button;

  @FXML private JFXButton faulknerFloor2Button;

  @FXML private JFXButton faulknerFloor3Button;

  @FXML private JFXButton faulknerFloor4Button;

  @FXML private JFXButton faulknerFloor5Button;

  @FXML private JFXButton groundButton;

  @FXML private JFXButton lower1Button;

  @FXML private JFXButton lower2Button;

  @FXML private JFXButton floor1Button;

  @FXML private JFXButton floor2Button;

  @FXML private JFXButton floor3Button;

  @FXML private JFXComboBox<String> hospitalCombo;

  @FXML private ScrollPane imageScrollPane;

  @FXML private StackPane imageStackPane;

  @FXML private ImageView imageViewFaulkner1;

  @FXML private AnchorPane mapPaneFaulkner1;

  @FXML private ImageView imageViewFaulkner2;

  @FXML private AnchorPane mapPaneFaulkner2;

  @FXML private ImageView imageViewFaulkner3;

  @FXML private AnchorPane mapPaneFaulkner3;

  @FXML private ImageView imageViewFaulkner4;

  @FXML private AnchorPane mapPaneFaulkner4;

  @FXML private ImageView imageViewFaulkner5;

  @FXML private AnchorPane mapPaneFaulkner5;

  @FXML private ImageView imageViewMain1;

  @FXML private ImageView imageViewMain2;

  @FXML private ImageView imageViewMain3;

  @FXML private ImageView imageViewMainG;

  @FXML private ImageView imageViewMainL1;

  @FXML private ImageView imageViewMainL2;

  @FXML private AnchorPane mapPaneMain1;

  @FXML private AnchorPane mapPaneMain2;

  @FXML private AnchorPane mapPaneMain3;

  @FXML private AnchorPane mapPaneMainG;

  @FXML private AnchorPane mapPaneMainL1;

  @FXML private AnchorPane mapPaneMainL2;

  @FXML private JFXButton nodeDisplayButton;

  @FXML private JFXButton edgeDisplayButton;

  @FXML private JFXButton cancelButton;

  @FXML private AnchorPane nodeAnchor;

  @FXML private AnchorPane edgeAnchor;

  @FXML private JFXTextField shortNameInput;

  @FXML private JFXTextField longNameInput;

  @FXML private JFXComboBox<String> typeInput;

  @FXML private JFXButton locationButton;

  @FXML private JFXTextField xCoorInput;

  @FXML private JFXTextField yCoorInput;

  @FXML private JFXComboBox<String> hospitalInput;

  @FXML private JFXComboBox<String> floorInput;

  @FXML private JFXButton addNodeButton;

  @FXML private JFXButton deleteNodeButton;

  @FXML private JFXButton modifyNodeButton;

  @FXML private JFXButton node1Button;

  @FXML private JFXButton node2Button;

  @FXML private JFXButton addEdgeButton;

  @FXML private JFXButton deleteEdgeButton;

  @FXML private JFXButton modifyEdgeButton;

  private AnchorPane mapPane;
  private ImageView imageView;

  private JFXButton nodeButton = null;
  private Line edgeLine = null;

  private Node node = null;
  private Edge edge = null;
  private boolean selectNode1 = false;
  private boolean selectNode2 = false;
  private Node node1 = null;
  private Node node2 = null;

  double deltaX = 0;
  double deltaY = 0;

  boolean edgeSelection = false;
  int numSelected = 0;

  private UISetting uiSetting = new UISetting();

  private static final int MAP_HEIGHT_FAULK = 1485;
  private static final int MAP_WIDTH_FAULK = 2475; // height and width of the faulkner hospital map
  private static final int MAP_HEIGHT_MAIN = 3400;
  private static final int MAP_WIDTH_MAIN = 5000; // height and width of the main hospital map
  private static final int PANE_HEIGHT = 585;
  private static final int PANE_WIDTH = 974; // height and width of the pane/image
  double heightRatio;
  double widthRatio;
  private DatabaseManager databaseManager = DatabaseManager.getManager();

  private enum Hospital {
    MAIN,
    FAULK
  };

  private Hospital hospital;

  public DataMapViewController() throws Exception {}

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    mapPane = mapPaneMain1;
    imageView = imageViewMain1;
    hospital = Hospital.MAIN;

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

    hospitalInput.getItems().addAll("Main", "Faulkner");
    typeInput.setValue("Main");

    floorInput.getItems().addAll("L2", "L1", "Ground", "F1", "F2", "F3");

    ObservableList<String> hospitals = FXCollections.observableArrayList("Faulkner", "Main Campus");

    hospitalCombo.setItems(hospitals);
    hospitalCombo.setValue("Main Campus");

    faulknerFloor1Button.setId("ff1");
    faulknerFloor2Button.setId("ff2");
    faulknerFloor3Button.setId("ff3");
    faulknerFloor4Button.setId("ff4");
    faulknerFloor5Button.setId("ff5");

    groundButton.setId("ground");
    lower1Button.setId("l1");
    lower2Button.setId("l2");
    floor1Button.setId("f1");
    floor2Button.setId("f2");
    floor3Button.setId("f3");

    uiSetting.makeZoomable(imageScrollPane, imageStackPane, 1);
  }

  @FXML
  private void setFloorButtons(ActionEvent event) {
    if (hospitalCombo.getValue() == "Main Campus") {
      faulknerFloorPane.setVisible(false);
      mainFloorPane.setVisible(true);
    } else {
      mainFloorPane.setVisible(false);
      faulknerFloorPane.setVisible(true);
    }
  }

  @FXML
  private void selectFloor(ActionEvent event) {
    JFXButton btn = (JFXButton) event.getSource();

    switch (btn.getId()) {
      case "ff1":
        mapPane = mapPaneFaulkner1;
        imageView = imageViewFaulkner1;
        hospital = Hospital.FAULK;
        break;
      case "ff2":
        mapPane = mapPaneFaulkner2;
        imageView = imageViewFaulkner2;
        hospital = Hospital.FAULK;
        break;
      case "ff3":
        mapPane = mapPaneFaulkner3;
        imageView = imageViewFaulkner3;
        hospital = Hospital.FAULK;
        break;
      case "ff4":
        mapPane = mapPaneFaulkner4;
        imageView = imageViewFaulkner4;
        hospital = Hospital.FAULK;
        break;
      case "ff5":
        mapPane = mapPaneFaulkner5;
        imageView = imageViewFaulkner5;
        hospital = Hospital.FAULK;
        break;
      case "ground":
        mapPane = mapPaneMainG;
        imageView = imageViewMainG;
        hospital = Hospital.MAIN;
        break;
      case "l1":
        mapPane = mapPaneMainL1;
        imageView = imageViewMainL1;
        hospital = Hospital.MAIN;
        break;
      case "l2":
        mapPane = mapPaneMainL2;
        imageView = imageViewMainL2;
        hospital = Hospital.MAIN;
        break;
      case "f1":
        mapPane = mapPaneMain1;
        imageView = imageViewMain1;
        hospital = Hospital.MAIN;
        break;
      case "f2":
        mapPane = mapPaneMain2;
        imageView = imageViewMain2;
        hospital = Hospital.MAIN;
        break;
      case "f3":
        mapPane = mapPaneMain3;
        imageView = imageViewMain3;
        hospital = Hospital.MAIN;
        break;
    }

    List<AnchorPane> mapPanes = new ArrayList<AnchorPane>();
    mapPanes.addAll(
        Arrays.asList(
            mapPaneFaulkner1,
            mapPaneFaulkner2,
            mapPaneFaulkner3,
            mapPaneFaulkner4,
            mapPaneFaulkner5,
            mapPaneMainG,
            mapPaneMainL1,
            mapPaneMainL2,
            mapPaneMain1,
            mapPaneMain2,
            mapPaneMain3));

    List<ImageView> imageViews = new ArrayList<ImageView>();
    imageViews.addAll(
        Arrays.asList(
            imageViewFaulkner1,
            imageViewFaulkner2,
            imageViewFaulkner3,
            imageViewFaulkner4,
            imageViewFaulkner5,
            imageViewMainG,
            imageViewMainL1,
            imageViewMainL2,
            imageViewMain1,
            imageViewMain2,
            imageViewMain3));

    for (int i = 0; i < mapPanes.size(); i++) {
      if (!mapPanes.get(i).equals(mapPane)) {
        mapPanes.get(i).setVisible(false);
        imageViews.get(i).setVisible(false);
      } else {
        mapPanes.get(i).setVisible(true);
        imageViews.get(i).setVisible(true);
      }
    }
  }

  @FXML
  private void displayAddNode() throws IOException {
    nodeDisplayButton.setVisible(false);
    edgeDisplayButton.setVisible(false);
    cancelButton.setVisible(true);
    edgeAnchor.setVisible(false);
    nodeAnchor.setVisible(true);
  }

  @FXML
  private void displayAddEdge() throws IOException {
    nodeDisplayButton.setVisible(false);
    edgeDisplayButton.setVisible(false);
    cancelButton.setVisible(true);
    nodeAnchor.setVisible(false);
    edgeAnchor.setVisible(true);
    edgeSelection = true;
  }

  @FXML
  private void drawEdge(Edge edge) throws Exception {

    Node node1 = databaseManager.readNode(edge.getNode1());
    Node node2 = databaseManager.readNode(edge.getNode2());
    setRatios(node1);
    if (node1.getFloor().equals(node2.getFloor())) {
      int startX = (int) ((node1.getXCoord() * widthRatio) + 0.75);
      int startY = (int) ((node1.getYCoord() * heightRatio) + 0.75);
      int endX = (int) ((node2.getXCoord() * widthRatio) + 0.75);
      int endY = (int) ((node2.getYCoord() * heightRatio) + 0.75);
      Line line = new Line(startX, startY, endX, endY);
      line.setId(edge.getId()); // allows us to keep track of what line is what edge
      line.setStroke(Color.BLACK);
      line.setStrokeWidth(1.5);
      line.setOpacity(0.7);
      line.setOnMouseClicked(
          mouseEvent -> { // when a user clicks on a line:
            edgeLine = line;
            this.edge = edge;
            displayEdge();
          });
      addToPane(node1, line);
    }
  }

  @FXML
  private void drawNode(Node node) { // draws the given node on the map

    setRatios(node);

    JFXButton button = new JFXButton();
    int buttonSize = 6; // this can be adjusted if we feel like the size is too small or large
    button.setMinSize(buttonSize, buttonSize);
    button.setMaxSize(buttonSize, buttonSize);
    button.setPrefSize(buttonSize, buttonSize); // the button size will not vary
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00008B; -fx-border-color: #000000; -fx-border-width: 1px; -fx-opacity: 0.7");
    double xPos = (node.getXCoord() * widthRatio - buttonSize / 2.0);
    double yPos = (node.getYCoord() * heightRatio - buttonSize / 2.0);
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    button.setOnAction(
        action -> {
          nodeButton = button; // sets classes button variable to the selected button
          this.node = node;
          System.out.println(node.getId());
          if (!edgeSelection) {
            displayNode();
          } else {
            System.out.println("In edge selection");
            if (numSelected == 0) {
              System.out.println("First node");
              node1Button.setText(node.getId());
              numSelected++;
            } else if (numSelected == 1 && !node.getId().equals(node1Button.getText())) {
              System.out.println("Second node");
              node2Button.setText(node.getId());
              numSelected = 0;
              addEdgeButton.setDisable(false);
            }
          }
        });
    addToPane(node, button);
  };

  @FXML
  private void displayNode() {
    nodeAnchor.setVisible(true);
    nodeDisplayButton.setVisible(false);
    edgeDisplayButton.setVisible(false);
    cancelButton.setVisible(true);
    addNodeButton.setVisible(false);
    modifyNodeButton.setVisible(true);
    deleteNodeButton.setVisible(true);
    shortNameInput.setText(node.getShortName());
    longNameInput.setText(node.getLongName());
    typeInput.setValue(node.getType().getTypeString());
    xCoorInput.setText("" + node.getXCoord());
    yCoorInput.setText("" + node.getYCoord());
    hospitalInput.setValue(node.getBuilding());
    floorInput.setValue(node.getFloor());
  }

  @FXML
  private void displayEdge() {
    edgeAnchor.setVisible(true);
    nodeDisplayButton.setVisible(false);
    edgeDisplayButton.setVisible(false);
    cancelButton.setVisible(true);
    addEdgeButton.setVisible(false);
    deleteEdgeButton.setVisible(true);
    modifyEdgeButton.setVisible(true);
    node1Button.setText(edge.getNode1());
    node2Button.setText(edge.getNode2());
  }

  private void setRatios(Node node) {
    if (node.getBuilding().equals("Faulkner")) {
      hospital = Hospital.FAULK;
    } else {
      hospital = Hospital.MAIN;
    }

    if (hospital == Hospital.FAULK) {
      heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT_FAULK;
      widthRatio = (double) PANE_WIDTH / MAP_WIDTH_FAULK;
    } else {
      heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT_MAIN;
      widthRatio = (double) PANE_WIDTH / MAP_WIDTH_MAIN;
    }
  } // helper for the draw statements

  @FXML
  private void addToPane(Node node, javafx.scene.Node child) {
    if (node.getBuilding().equals("Faulkner")) {
      switch (node.getFloor()) {
        case "1":
          mapPaneFaulkner1.getChildren().add(child);
          break;
        case "2":
          mapPaneFaulkner2.getChildren().add(child);
          break;
        case "3":
          mapPaneFaulkner3.getChildren().add(child);
          break;
        case "4":
          mapPaneFaulkner4.getChildren().add(child);
          break;
        case "5":
          mapPaneFaulkner5.getChildren().add(child);
          break;
      }
    } else {
      switch (node.getFloor()) {
        case "Ground":
          mapPaneMainG.getChildren().add(child);
          break;
        case "L2":
          mapPaneMainL2.getChildren().add(child);
          break;
        case "L1":
          mapPaneMainL1.getChildren().add(child);
          break;
        case "F1":
          mapPaneMain1.getChildren().add(child);
          break;
        case "F2":
          mapPaneMain2.getChildren().add(child);
          break;
        case "F3":
          mapPaneMain3.getChildren().add(child);
          break;
      }
    }
  } // helper for the draw functions

  @FXML
  private void addNode(ActionEvent event) throws Exception {

    Stage stage;

    int tracker = 0;
    int instance = 0;
    int newInstance = 0;
    int instanceNum = 0;

    // try { // is the input valid?
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = hospitalInput.getValue().toString();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getValue().toString());
    String floorNumber = floorInput.getValue().toString();
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
      for (int i = 0; i < typeInstances.size(); i++) {
        System.out.println(typeInstances.get(i));
        instance = typeInstances.get(i); // 1
        if (instance - tracker > 1) { // 1-0 = 1
          newInstance = tracker + 1;
          break;
        } else if (instance == typeInstances.size()) {
          newInstance = typeInstances.size() + 1;
          break;
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
    drawNode(newNode);
    stage = (Stage) addNodeButton.getScene().getWindow();
    stage.close();

    clearViews();

    //    } catch (Exception e) { // throws an error if the input provided by the user is invalid
    //      errorLabel.setText("The input is not valid");
    //    }
  }

  @FXML
  private void modifyNode(ActionEvent event) {

    short oldXCoordinate = node.getXCoord();
    short oldYCoordinate = node.getYCoord();
    String oldBuilding = node.getBuilding();
    String oldLongName = node.getLongName();
    String oldShortName = node.getShortName();
    Node.NodeType oldNodeType = node.getType();
    String oldFloorNumber = node.getFloor(); // stores the input in variables

    try { // is the input correct?
      short xCoordinate = Short.parseShort(xCoorInput.getText());
      short yCoordinate = Short.parseShort(yCoorInput.getText());
      String building = hospitalInput.getValue();
      String longName = longNameInput.getText();
      String shortName = shortNameInput.getText();
      Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getValue().toString());
      String floorNumber = floorInput.getValue(); // stores the input in variables

      node.setXCoord(xCoordinate);
      node.setYCoord(yCoordinate);
      node.setBuilding(building);
      node.setLongName(longName);
      node.setShortName(shortName);
      node.setType(nodeType);
      node.setFloor(floorNumber); // sets the node to the provided values

      databaseManager.manipulateNode(node); // adds node in db

      if (oldFloorNumber != floorNumber) { // removes edges
        for (Edge edge : databaseManager.getAllEdgesConnectedToNode(node.getId())) {
          for (int i = 0; i < mapPane.getChildren().size(); i++) {
            javafx.scene.Node children = mapPane.getChildren().get(i);
            if (children instanceof Line && children.getId().equals(edge.getId())) {
              mapPane.getChildren().remove(children);
            }
          }
        }

        mapPane.getChildren().remove(nodeButton); // removes node

        addToPane(node, nodeButton); // adds new node

      } else { // on the same floor
        for (Edge edge : databaseManager.getAllEdgesConnectedToNode(node.getId())) {
          for (int i = 0; i < mapPane.getChildren().size(); i++) {
            javafx.scene.Node children = mapPane.getChildren().get(i);
            if (children instanceof Line && children.getId().equals(edge.getId())) {
              Line line = (Line) children;
              if (edge.getNode1().equals(node.getId())) {
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
        System.out.println("The input is invalid");
        // nodeErrorLabel.setText("The input is invalid");
      }
    }
    clearViews();
  }

  @FXML
  private void deleteNode(ActionEvent event) throws Exception {
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
    clearViews();
  }

  @FXML
  private void addEdge(ActionEvent event) throws Exception {
    String node1ID = node1Button.getText();
    String node2ID = node2Button.getText();
    String edgeID = node1ID + "_" + node2ID;
    Edge newEdge = new Edge(edgeID, node1ID, node2ID);
    databaseManager.manipulateEdge(newEdge);
    drawEdge(newEdge);
    edgeSelection = false;
    clearViews();
  }

  @FXML
  private void modifyEdge(ActionEvent event) throws Exception {
    String node1ID = node1Button.getText();
    String node2ID = node2Button.getText();
    String ID = node1ID + "_" + node2ID;
    Edge newEdge = new Edge(ID, node1ID, node2ID);
    databaseManager.deleteEdge(edge.getId());
    mapPane.getChildren().remove(edgeLine);
    databaseManager.manipulateEdge(newEdge);
    drawEdge(newEdge);
    clearViews();
  }

  @FXML
  private void deleteEdge(ActionEvent event) throws Exception {
    databaseManager.deleteEdge(edge.getId());
    mapPane.getChildren().remove(edgeLine); // deletes the edge on the map
    clearViews();
  }

  @FXML
  private void cancelViews(ActionEvent event) throws Exception {
    nodeAnchor.setVisible(false);
    edgeAnchor.setVisible(false);
    cancelButton.setVisible(false);
    nodeDisplayButton.setVisible(true);
    edgeDisplayButton.setVisible(true);
    clearEdge();
    clearNode();
  }

  @FXML
  private void updateFloor(ActionEvent event) {
    if (hospitalInput.getValue().equals("Faulkner")) {
      floorInput.getItems().removeAll("L2", "L1", "Ground", "F1", "F2", "F3");
      floorInput.getItems().addAll("1", "2", "3", "4", "5");
    } else {
      floorInput.getItems().removeAll("1", "2", "3", "4", "5");
      floorInput.getItems().addAll("L2", "L1", "Ground", "F1", "F2", "F3");
    }
  }

  @FXML
  private void validateNodeText(KeyEvent keyEvent) {
    if (!shortNameInput.getText().isEmpty()
        && !longNameInput.getText().isEmpty()
        && !xCoorInput.getText().isEmpty()
        && !yCoorInput.getText().isEmpty()) {
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

  @FXML
  private void clearNode() {
    shortNameInput.setText("");
    longNameInput.setText("");
    xCoorInput.setText("");
    yCoorInput.setText("");
  }

  @FXML
  private void clearEdge() {
    node1Button.setText("Click Here");
    node2Button.setText("Click Here");
  }

  @FXML
  private void clearViews() {
    nodeAnchor.setVisible(false);
    edgeAnchor.setVisible(false);
    cancelButton.setVisible(false);
    nodeDisplayButton.setVisible(true);
    edgeDisplayButton.setVisible(true);
    clearEdge();
    clearNode();
  }

  @FXML
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
}
