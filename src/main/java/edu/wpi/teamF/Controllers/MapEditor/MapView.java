package edu.wpi.teamF.Controllers.MapEditor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.SneakyThrows;

public class MapView implements Initializable {
  private static final int BUTTON_SIZE = 8;
  private static final double LINE_WIDTH = 1.5;
  private static final int MAP_HEIGHT_FAULK = 1485;
  private static final int MAP_WIDTH_FAULK = 2475; // height and width of the faulkner hospital map
  private static final int MAP_HEIGHT_MAIN = 3400;
  private static final int MAP_WIDTH_MAIN = 5000; // height and width of the main hospital map
  private static final int PANE_HEIGHT = 585;
  private static final int PANE_WIDTH = 974; // height a

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

  @FXML private Label nodeErrorLabel;

  @FXML private JFXComboBox<String> edgeCombo;

  @FXML private JFXButton modifyEdgeFromNodeButton;

  private DatabaseManager databaseManager = DatabaseManager.getManager();
  private MapEditorController mapEditorController;
  Map<String, JFXButton> buttonMap;
  Map<String, Line> lineMap;
  String currentFloor;

  @SneakyThrows
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    buttonMap = new HashMap<>();
    lineMap = new HashMap<>();
    mapEditorController = new MapEditorController(this);

    for (Node node : databaseManager.getAllNodes()) {
      drawNode(node);
    }
    for (Edge edge : databaseManager.getAllEdges()) {
      drawEdge(edge);
    }

    UISetting uiSetting = new UISetting();
    uiSetting.makeZoomable(imageScrollPane, imageStackPane, 1);
    initializeComboBox();

    initializeNodeButtons();
    initializeEdgeAnchorButtons();

    mapEditorController.setCancelButtonHandler(cancelButton);
    initializeAnchorIDs();
    floorButtonsSet();
    setAsDefaultView();
    switchToFloor("G");
  }

  private void initializeNodeButtons() throws Exception {
    mapEditorController.setFloorInputHandler(floorInput);
    mapEditorController.setDeleteNodeButtonHandler(deleteNodeButton);
    mapEditorController.setModifyNodeButtonHandler(modifyNodeButton);
    mapEditorController.setAddNodeButtonHandler(nodeDisplayButton, edgeCombo);
    mapEditorController.setModifyEdgeFromNodeButton(modifyEdgeFromNodeButton, edgeCombo);
    //    mapEditorController.setAddNodeButtonHandler(addNodeButton);
    //    mapEditorController.setModifyNodeButtonHandler(modifyNodeButton);
    //    mapEditorController.setDeleteNodeButtonHandler(deleteNodeButton);
  }

  private void initializeEdgeAnchorButtons() {
    node1Button.setId("Node1");
    mapEditorController.setEdgeSelectionButtonHandler(node1Button);
    node2Button.setId("Node2");
    mapEditorController.setEdgeSelectionButtonHandler(node2Button);
    mapEditorController.addEdgeButtonHandler(edgeDisplayButton);
    mapEditorController.edgeDeleteButtonHandler(deleteEdgeButton);
    mapEditorController.edgeModifyButtonHandler(modifyEdgeButton);
  }

  public JFXButton drawNode(Node node) throws Exception {
    JFXButton button = new JFXButton();
    button.setId(node.getId());
    button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
    button.setMaxSize(BUTTON_SIZE, BUTTON_SIZE);
    button.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
    setButtonColor(button, "#99D9EA", 0.7);
    button.setLayoutX(calculateXCoord(node.getXCoord(), node.getBuilding()) - BUTTON_SIZE / 2.0);
    button.setLayoutY(calculateYCoord(node.getYCoord(), node.getBuilding()) - BUTTON_SIZE / 2.0);
    mapEditorController.setNodeEventHandlers(button, edgeCombo);
    getFloorPane(node.getFloor()).getChildren().add(button);
    buttonMap.put(button.getId(), button);
    return button;
  }

  public Line drawEdge(Edge edge) throws Exception {
    Node node1 = databaseManager.readNode(edge.getNode1());
    Node node2 = databaseManager.readNode(edge.getNode2());
    Line line = new Line();
    updateLine(line, node1, node2);
    line.setId(edge.getId());
    line.setStroke(Color.BLACK);
    line.setStrokeWidth(LINE_WIDTH);
    line.setOpacity(0.7);
    getFloorPane(node1.getFloor()).getChildren().addAll(line);
    lineMap.put(line.getId(), line);
    mapEditorController.setEdgeEventHandlers(line);

    if (node1.getFloor().equals(node2.getFloor())) {
      line.setVisible(true);
    } else {
      line.setVisible(false);
    }

    return line;
  }

  public void removeEdge(String edgeID) throws Exception {
    Line line = lineMap.get(edgeID);
    getFloorPane(line.getParent().getId()).getChildren().remove(line);
    lineMap.remove(edgeID);
  }

  public void removeNode(String nodeID) throws Exception {
    JFXButton button = buttonMap.get(nodeID);
    getFloorPane(button.getParent().getId()).getChildren().remove(button);
    buttonMap.remove(nodeID);
  }

  public void redrawNode(Node node) throws Exception {
    JFXButton button = buttonMap.get(node.getId());
    setButtonColor(button, "#99D9EA", 0.7);
    button.setLayoutX(calculateXCoord(node.getXCoord(), node.getBuilding()) - BUTTON_SIZE / 2.0);
    button.setLayoutY(calculateYCoord(node.getYCoord(), node.getBuilding()) - BUTTON_SIZE / 2.0);
    if (!node.getFloor().equals(button.getParent().getId())) {
      getFloorPane(button.getParent().getId()).getChildren().remove(button);
      getFloorPane(node.getFloor()).getChildren().add(button);
    }
  }

  public void redrawEdge(Edge edge) throws Exception {
    Node node1 = databaseManager.readNode(edge.getNode1());
    Node node2 = databaseManager.readNode(edge.getNode2());
    Line line = lineMap.get(edge.getId());
    updateLine(line, node1, node2);
    if (node1.getFloor().equals(node2.getFloor())) {
      line.setVisible(true);
      if (!node1.getFloor().equals(line.getParent().getId())) {
        getFloorPane(line.getParent().getId()).getChildren().remove(line);
        getFloorPane(node1.getFloor()).getChildren().add(line);
      }
    } else {
      line.setVisible(false);
    }
  }

  private void updateLine(Line line, Node node1, Node node2) throws Exception {

    double startX = calculateXCoord(node1.getXCoord(), node1.getBuilding()) + LINE_WIDTH / 2;
    double startY = calculateYCoord(node1.getYCoord(), node1.getBuilding()) + LINE_WIDTH / 2;
    double endX = calculateXCoord(node2.getXCoord(), node2.getBuilding()) + LINE_WIDTH / 2;
    double endY = calculateYCoord(node2.getYCoord(), node2.getBuilding()) + LINE_WIDTH / 2;
    line.setStartX(startX);
    line.setStartY(startY);
    line.setEndX(endX);
    line.setEndY(endY);
  }

  public void highlightUpdatedEdge(String edgeID, String node1ID, String node2ID) throws Exception {
    node1Button.setText(node1ID);
    node2Button.setText(node2ID);
    setButtonColor(buttonMap.get(node1ID), "#012D5A", 1);
    setButtonColor(buttonMap.get(node2ID), "#a40000", 1);
    Line line = lineMap.get(edgeID);
    Node node1 = databaseManager.readNode(node1ID);
    Node node2 = databaseManager.readNode(node2ID);
    updateLine(line, node1, node2);
    if (node1.getFloor().equals(node2.getFloor())) {
      line.setVisible(true);
      if (!node1.getFloor().equals(line.getParent().getId())) {
        getFloorPane(line.getParent().getId()).getChildren().remove(line);
        getFloorPane(node1.getFloor()).getChildren().add(line);
      }
    } else {
      line.setVisible(false);
    }
  }

  public void displayInitialNodeData(Node node) {
    floorInput.setOnAction(null);
    setFloorAndBuildingInput(node.getBuilding());
    shortNameInput.setText(node.getShortName());
    longNameInput.setText(node.getLongName());
    typeInput.setValue(node.getType().getTypeString());
    floorInput.setValue(node.getFloor());
    hospitalInput.setValue(node.getBuilding());
    xCoorInput.setText("" + node.getXCoord());
    yCoorInput.setText("" + node.getYCoord());
    mapEditorController.setFloorInputHandler(floorInput);
  }

  public void highlightUpdatedNode(
      String nodeID, double newX, double newY, String newFloor, String newBuilding)
      throws Exception {
    xCoorInput.setText("" + newX);
    yCoorInput.setText("" + newY);
    JFXButton button = buttonMap.get(nodeID);
    setButtonColor(button, "#012D5A", 1);
    button.setLayoutX(calculateXCoord(newX, newBuilding) - BUTTON_SIZE / 2.0);
    button.setLayoutY(
        calculateYCoord(newY, newBuilding) - BUTTON_SIZE / 2.0); // set new x and y coordinates
    if (!newFloor.equals(button.getParent().getId())) {
      getFloorPane(button.getParent().getId()).getChildren().remove(button);
      getFloorPane(newFloor).getChildren().add(button);
      switchToFloor(newFloor); // if the floor id different then you switch to it
    }
    for (Edge edge : databaseManager.getAllEdgesConnectedToNode(nodeID)) {
      updateEdgeWithUpdatedNode(edge, nodeID, newX, newY, newBuilding, newFloor);
    }
  }
  // Only call with highlightUpdatedNode
  private void updateEdgeWithUpdatedNode(
      Edge edge, String nodeID, double newX, double newY, String newBuilding, String newFloor)
      throws Exception {
    Line line = lineMap.get(edge.getId());
    Node node1 = databaseManager.readNode(edge.getNode1());
    Node node2 = databaseManager.readNode(edge.getNode2());
    if (edge.getNode1().equals(nodeID)) {
      double startX = calculateXCoord(newX, newBuilding) + LINE_WIDTH / 2;
      double startY = calculateYCoord(newY, newBuilding) + LINE_WIDTH / 2;
      line.setStartX(startX);
      line.setStartY(startY);
      if (node2.getFloor().equals(newFloor)) {
        line.setVisible(true);
        if (!node2.getFloor().equals(line.getParent().getId())) {
          getFloorPane(line.getParent().getId()).getChildren().remove(line);
          getFloorPane(node2.getFloor()).getChildren().add(line);
        }
      } else {
        line.setVisible(false);
      }
    } else {
      double endX = calculateXCoord(newX, newBuilding) + LINE_WIDTH / 2;
      double endY = calculateYCoord(newY, newBuilding) + LINE_WIDTH / 2;
      line.setEndX(endX);
      line.setEndY(endY);
      if (node1.getFloor().equals(newFloor)) {
        line.setVisible(true);
        if (!node1.getFloor().equals(line.getParent().getId())) {
          getFloorPane(line.getParent().getId()).getChildren().remove(line);
          getFloorPane(node1.getFloor()).getChildren().add(line);
        }
      } else {
        line.setVisible(false);
      }
    }
  }

  private void clearViews() {
    nodeDisplayButton.setVisible(false);
    edgeDisplayButton.setVisible(false);
    cancelButton.setVisible(false);
    edgeAnchor.setVisible(false);
    nodeAnchor.setVisible(false);
  }

  public void setAsDefaultView() {
    clearViews();
    nodeDisplayButton.setVisible(true);
    edgeDisplayButton.setVisible(true);
  }

  public void setAsCancelView() {
    clearViews();
    cancelButton.setVisible(true);
  }

  public void setAsEdgeView() {
    clearViews();
    cancelButton.setVisible(true);
    edgeAnchor.setVisible(true);
  }

  public void setAsModifyNodeView() {
    clearViews();
    cancelButton.setVisible(true);
    nodeAnchor.setVisible(true);
    addNodeButton.setVisible(false);
    modifyNodeButton.setVisible(true);
  }

  public void setAsAddNodeView() {
    clearViews();
    cancelButton.setVisible(true);
    nodeAnchor.setVisible(true);
    addNodeButton.setVisible(true);
    modifyNodeButton.setVisible(false);
  }

  public void setAsMultipleNodeView() {
    clearViews();
  }

  public void switchToFloor(String floor) {
    currentFloor = floor;
    try {
      if ("1".equals(floor)
          || "2".equals(floor)
          || "3".equals(floor)
          || "4".equals(floor)
          || "5".equals(floor)) {
        hospitalCombo.setValue("Faulkner");
        faulknerFloorPane.setVisible(true);
        mainFloorPane.setVisible(false);
      } else {
        hospitalCombo.setValue("Main Campus");
        faulknerFloorPane.setVisible(false);
        mainFloorPane.setVisible(true);
      }
      setAllFloorPanesInvisible();
      setAllImageViewPanesInvisible();
      getFloorPane(floor).setVisible(true);
      getImageView(floor).setVisible(true);
      deselectFloorButtons();
      getFloorButton(floor).setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setAllImageViewPanesInvisible() {
    imageViewFaulkner1.setVisible(false);
    imageViewFaulkner2.setVisible(false);
    imageViewFaulkner3.setVisible(false);
    imageViewFaulkner4.setVisible(false);
    imageViewFaulkner5.setVisible(false);
    imageViewMain1.setVisible(false);
    imageViewMain2.setVisible(false);
    imageViewMain3.setVisible(false);
    imageViewMainG.setVisible(false);
    imageViewMainL1.setVisible(false);
    imageViewMainL2.setVisible(false);
  }

  private void setAllFloorPanesInvisible() {
    mapPaneFaulkner1.setVisible(false);
    mapPaneFaulkner2.setVisible(false);
    mapPaneFaulkner3.setVisible(false);
    mapPaneFaulkner4.setVisible(false);
    mapPaneFaulkner5.setVisible(false);
    mapPaneMain1.setVisible(false);
    mapPaneMain2.setVisible(false);
    mapPaneMain3.setVisible(false);
    mapPaneMainG.setVisible(false);
    mapPaneMainL1.setVisible(false);
    mapPaneMainL2.setVisible(false);
  }

  public void floorButtonsSet() {
    faulknerFloor1Button.setOnAction(actionEvent -> switchToFloor("1"));
    faulknerFloor2Button.setOnAction(actionEvent -> switchToFloor("2"));
    faulknerFloor3Button.setOnAction(actionEvent -> switchToFloor("3"));
    faulknerFloor4Button.setOnAction(actionEvent -> switchToFloor("4"));
    faulknerFloor5Button.setOnAction(actionEvent -> switchToFloor("5"));
    floor1Button.setOnAction(actionEvent -> switchToFloor("F1"));
    floor2Button.setOnAction(actionEvent -> switchToFloor("F2"));
    floor3Button.setOnAction(actionEvent -> switchToFloor("F3"));
    groundButton.setOnAction(actionEvent -> switchToFloor("G"));
    lower1Button.setOnAction(actionEvent -> switchToFloor("L1"));
    lower2Button.setOnAction(actionEvent -> switchToFloor("L2"));
    hospitalCombo
        .valueProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (oldValue != null && !oldValue.equals(newValue)) {
                if ("Faulkner".equals(newValue)) {
                  switchToFloor("1");
                } else if ("Main Campus".equals(newValue)) {
                  switchToFloor("G");
                }
              }
            }));
  }

  public void deselectFloorButtons() {
    for (javafx.scene.Node btn : faulknerFloorPane.getChildren()) {
      btn.setStyle("-fx-background-color: #4d6c8b; -fx-background-radius: 10px");
    }
    for (javafx.scene.Node btn : mainFloorPane.getChildren()) {
      btn.setStyle("-fx-background-color: #4d6c8b; -fx-background-radius: 10px");
    }
  }

  public void setButtonColor(JFXButton button, String color, double opacity) {
    button.setStyle(
        "-fx-background-radius: "
            + BUTTON_SIZE
            + "px; -fx-border-radius: "
            + BUTTON_SIZE
            + "px; -fx-background-color: "
            + color
            + "; -fx-border-color: #000000; -fx-border-width: 1px; -fx-opacity: "
            + opacity);
  }

  private double calculateXCoord(double x, String building) {
    if ("Faulkner".equals(building)) {
      return x * (double) PANE_WIDTH / MAP_WIDTH_FAULK;
    } else {
      return x * (double) PANE_WIDTH / MAP_WIDTH_MAIN;
    }
  }

  private double calculateYCoord(double y, String building) {
    if ("Faulkner".equals(building)) {
      return y * (double) PANE_HEIGHT / MAP_HEIGHT_FAULK;
    } else {
      return y * (double) PANE_HEIGHT / MAP_HEIGHT_MAIN;
    }
  }

  private void initializeComboBox() {
    typeInput
        .getItems()
        .addAll(
            "CONF", "DEPT", "EXIT", "HALL", "INFO", "LABS", "REST", "RETL", "SERV", "STAF", "STAI");
    hospitalInput
        .getItems()
        .addAll("Faulkner", "Tower", "BTM", "45 Francis", "15 Francis", "Shapiro");
    ObservableList<String> hospitals = FXCollections.observableArrayList("Faulkner", "Main Campus");
    hospitalCombo.setItems(hospitals);
  }

  private AnchorPane getFloorPane(String floor) throws Exception {
    switch (floor) {
      case "1":
        return mapPaneFaulkner1;
      case "2":
        return mapPaneFaulkner2;
      case "3":
        return mapPaneFaulkner3;
      case "4":
        return mapPaneFaulkner4;
      case "5":
        return mapPaneFaulkner5;
      case "F1":
        return mapPaneMain1;
      case "F2":
        return mapPaneMain2;
      case "F3":
        return mapPaneMain3;
      case "G":
        return mapPaneMainG;
      case "L1":
        return mapPaneMainL1;
      case "L2":
        return mapPaneMainL2;
      default:
        throw new Exception("NULL FLOOR in getFloorPane");
    }
  }

  private ImageView getImageView(String floor) throws Exception {
    switch (floor) {
      case "1":
        return imageViewFaulkner1;
      case "2":
        return imageViewFaulkner2;
      case "3":
        return imageViewFaulkner3;
      case "4":
        return imageViewFaulkner4;
      case "5":
        return imageViewFaulkner5;
      case "F1":
        return imageViewMain1;
      case "F2":
        return imageViewMain2;
      case "F3":
        return imageViewMain3;
      case "G":
        return imageViewMainG;
      case "L1":
        return imageViewMainL1;
      case "L2":
        return imageViewMainL2;
      default:
        throw new Exception("NULL FLOOR IN getImageView");
    }
  }

  private JFXButton getFloorButton(String floor) throws Exception {
    switch (floor) {
      case "1":
        return faulknerFloor1Button;
      case "2":
        return faulknerFloor2Button;
      case "3":
        return faulknerFloor3Button;
      case "4":
        return faulknerFloor4Button;
      case "5":
        return faulknerFloor5Button;
      case "F1":
        return floor1Button;
      case "F2":
        return floor2Button;
      case "F3":
        return floor3Button;
      case "G":
        return groundButton;
      case "L1":
        return lower1Button;
      case "L2":
        return lower2Button;
      default:
        throw new Exception("NULL FLOOR IN getFloorButton");
    }
  }

  private void initializeAnchorIDs() {
    mapPaneFaulkner1.setId("1");
    mapPaneFaulkner2.setId("2");
    mapPaneFaulkner3.setId("3");
    mapPaneFaulkner4.setId("4");
    mapPaneFaulkner5.setId("5");
    mapPaneMain1.setId("F1");
    mapPaneMain2.setId("F2");
    mapPaneMain3.setId("F3");
    mapPaneMainG.setId("G");
    mapPaneMainL1.setId("L1");
    mapPaneMainL2.setId("L2");
  }

  public String getShortName() {
    return shortNameInput.getText();
  }

  public String getLongName() {
    return longNameInput.getText();
  }

  public String getXCoord() {
    return xCoorInput.getText();
  }

  public String getYCoord() {
    return yCoorInput.getText();
  }

  public String getBuilding() {
    return hospitalInput.getValue();
  }

  public String getFloor() {
    return floorInput.getValue();
  }

  public String getType() {
    return typeInput.getValue();
  }

  public void unHighlightButton(String nodeID) {
    setButtonColor(buttonMap.get(nodeID), "#99D9EA", 0.7);
  }

  private void setFloorAndBuildingInput(String building) {

    if ("Faulkner".equals(building)) {
      floorInput.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
      hospitalInput.setItems(FXCollections.observableArrayList("Faulkner"));
    } else {
      floorInput.setItems(FXCollections.observableArrayList("L2", "L1", "G", "F1", "F2", "F3"));
      hospitalInput.setItems(
          FXCollections.observableArrayList(
              "FLEX", "Tower", "BTM", "45 Francis", "15 Francis", "Shapiro"));
    }
  }

  public double getMapScaleX() {
    return imageStackPane.getScaleX();
  }

  public double getMapScaleY() {
    return imageStackPane.getScaleY();
  }

  public String getCurrentFloor() {
    return currentFloor;
  }
}
