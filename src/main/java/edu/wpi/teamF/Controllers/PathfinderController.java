package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Directions.Directions;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.*;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.management.InstanceNotFoundException;
import lombok.SneakyThrows;

public class PathfinderController implements Initializable {

  private static int FAULKNER_MAP_HEIGHT = 1485;
  private static int FAULKNER_MAP_WIDTH = 2475;
  private static int MAIN_MAP_HEIGHT = 3400;
  private static int MAIN_MAP_WIDTH = 5000;

  public AnchorPane currentPane;
  public AnchorPane mainMapPane;
  public AnchorPane mapPaneFaulkner5;
  public AnchorPane mapPaneFaulkner4;
  public AnchorPane mapPaneFaulkner3;
  public AnchorPane mapPaneFaulkner2;
  public AnchorPane mapPaneFaulkner1;
  public StackPane masterPaneFaulkner1;
  public ImageView imageViewFaulkner1;
  public ImageView imageViewFaulkner2;
  public ImageView imageViewFaulkner3;
  public ImageView imageViewFaulkner4;
  public ImageView imageViewFaulkner5;
  public ScrollPane scrollPaneFaulkner1;
  public AnchorPane selectButtonsPane;
  public AnchorPane directionsPane;
  public JFXButton stairsBtn;
  public JFXButton elevBtn;
  public JFXButton bathBtn;
  public AnchorPane selectFloorPane;
  public JFXButton floor1Button;
  public JFXButton floor2Button;
  public JFXButton floor3Button;
  public JFXButton floor4Button;
  public JFXButton floor5Button;
  public Text commandText;
  public JFXComboBox<String> startCombo;
  public JFXComboBox<String> endCombo;
  public JFXButton pathButton;
  public JFXTextArea directionsDisplay;
  public AnchorPane pathSwitchFloorPane;
  public JFXButton pathSwitchNext;
  public JFXButton pathSwitchPrevious;
  public JFXButton textDirections;
  public JFXButton callDirections;
  public JFXButton printDirections;
  public AnchorPane mapPaneMainL2;
  public AnchorPane mapPaneMainL1;
  public AnchorPane mapPaneMainG;
  public AnchorPane mapPaneMain1;
  public AnchorPane mapPaneMain2;
  public AnchorPane mapPaneMain3;
  public ImageView imageViewMainL2;
  public ImageView imageViewMainL1;
  public ImageView imageViewMainG;
  public ImageView imageViewMain1;
  public ImageView imageViewMain2;
  public ImageView imageViewMain3;
  public JFXButton mainFloor1Button;
  public JFXButton mainFloor2Button;
  public JFXButton mainFloor3Button;
  public JFXButton mainFloorGButton;
  public JFXButton mainFloorL2Button;
  public JFXButton mainFloorL1Button;
  public AnchorPane selectFloorPaneMain;
  public Label startLabel;
  public Label endLabel;
  public JFXComboBox<String> hospitalComboBox;
  public HBox btnSpacer;
  public JFXTextField phoneNumber;

  // stairs v elev stuff
  String liftType = "ELEV";
  public JFXButton chooseLiftStairs;
  public JFXButton chooseLiftElevator;
  public JFXToggleButton liftToggle;
  public Label elevatorLabel;
  public Label stairsLabel;

  public List<Node> fullNodeList;
  public int state;
  public UISetting uiSetting = new UISetting();
  private String currentBuilding;
  private String currentFloor;
  Node startNode = null;
  Node endNode = null;
  public Path path;
  public Directions directions;
  public int locationIndex;

  EuclideanScorer euclideanScorer = new EuclideanScorer();
  DatabaseManager databaseManager = DatabaseManager.getManager();
  PathfindAlgorithm pathFindAlgorithm;
  private static String newPathfind = " ";

  public PathfinderController() {}

  public static void setPathFindAlgorithm(String newPathFindAlgorithm) {
    newPathfind = newPathFindAlgorithm;
    System.out.println("set new pathfind: " + newPathFindAlgorithm);
  }

  private void updatePathFindAlgorithm() {
    switch (newPathfind) {
      case "A Star":
        MultipleHospitalAStar currentAlgorithm1 = new MultipleHospitalAStar(fullNodeList);
        currentAlgorithm1.setLiftType(liftType);
        this.pathFindAlgorithm = currentAlgorithm1;
        System.out.println("successful astar");
        break;
      case "Breadth First":
        BreadthFirst currentAlgorithm2 = new BreadthFirst(fullNodeList);
        currentAlgorithm2.setLiftType(liftType);
        this.pathFindAlgorithm = currentAlgorithm2;
        System.out.println("successful breath");
        break;
      case "Depth First":
        DepthFirstSearch currentAlgorithm3 = new DepthFirstSearch(fullNodeList);
        currentAlgorithm3.setLiftType(liftType);
        this.pathFindAlgorithm = currentAlgorithm3;
        System.out.println("successful Depth first");
        break;
      default:
        break;
    }
  }

  public void draw(Path path) throws InstanceNotFoundException {
    this.path = path;
    List<Node> pathNodes = path.getPath();

    double heightRatioFaulkner = currentPane.getPrefHeight() / FAULKNER_MAP_HEIGHT;
    double widthRatioFaulkner = currentPane.getPrefWidth() / FAULKNER_MAP_WIDTH;
    double heightRatioMain = currentPane.getPrefHeight() / MAIN_MAP_HEIGHT;
    double widthRatioMain = currentPane.getPrefWidth() / MAIN_MAP_WIDTH;

    for (int i = 0; i < pathNodes.size() - 1; i++) {
      Node start = pathNodes.get(i);
      Node end = pathNodes.get(i + 1);
      if (start.getFloor().equals(end.getFloor())
          && sameHospital(start.getBuilding(), end.getBuilding())) {
        double startX;
        double startY;
        double endX;
        double endY;
        if ("Faulkner".equals(start.getBuilding())) {
          startX = (start.getXCoord() * widthRatioFaulkner);
          startY = (start.getYCoord() * heightRatioFaulkner);
          endX = (end.getXCoord() * widthRatioFaulkner);
          endY = (end.getYCoord() * heightRatioFaulkner);
        } else {
          startX = (start.getXCoord() * widthRatioMain);
          startY = (start.getYCoord() * heightRatioMain);
          endX = (end.getXCoord() * widthRatioMain);
          endY = (end.getYCoord() * heightRatioMain);
        }
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.RED);
        line.setStrokeWidth(2);
        animateLine(line);
        getFloorPane(pathNodes.get(i).getFloor(), pathNodes.get(i).getBuilding())
            .getChildren()
            .add(line);
      }
    }

    selectButtonsPane.setVisible(false);
    directionsPane.setVisible(true);
    this.directions = new Directions(fullNodeList, path, startNode, endNode);
    System.out.println(directions.getFullDirectionsString());
    pathSwitchFloorPane.setVisible(true);

    if (path.getUniqueLocations() > 1) {
      // Spans multiple floors
      pathSwitchPrevious.setVisible(false);
      pathSwitchPrevious.setPrefHeight(0);
      pathSwitchNext.setVisible(true);
      pathSwitchNext.setPrefHeight(50);
      btnSpacer.setPrefHeight(0);

      if (!(path.getLocationAtIndex(0)
          .getBuilding()
          .equals(path.getLocationAtIndex(1).getBuilding()))) {
        // Next stop is at a different hospital
        pathSwitchNext.setText("Next: Go to " + path.getLocationAtIndex(1).getBuilding());
      } else {
        pathSwitchNext.setText("Next: Go to floor " + path.getLocationAtIndex(1).getFloor());
      }
      locationIndex = 0;
      System.out.println("Changed to Index: " + locationIndex);
      System.out.println("Floor: " + path.getLocationAtIndex(locationIndex).getFloor());
      System.out.println("Building: " + path.getLocationAtIndex(locationIndex).getBuilding());

      // TODO Update directions to understand Locations
      directionsDisplay.setText(directions.getFullDirectionsString());
    } else {
      // Single floor navigation
      pathSwitchPrevious.setVisible(false);
      pathSwitchPrevious.setPrefHeight(0);
      pathSwitchNext.setVisible(false);
      pathSwitchNext.setPrefHeight(0);
      btnSpacer.setPrefHeight(0);

      directionsDisplay.setText(directions.getFullDirectionsString());
    }
  }

  public void placeButton(Node node) {

    double heightRatioFaulkner = (double) currentPane.getPrefHeight() / FAULKNER_MAP_HEIGHT;
    double widthRatioFaulkner = (double) currentPane.getPrefWidth() / FAULKNER_MAP_WIDTH;
    double heightRatioMain = (double) currentPane.getPrefHeight() / MAIN_MAP_HEIGHT;
    double widthRatioMain = (double) currentPane.getPrefWidth() / MAIN_MAP_WIDTH;

    JFXButton button = new JFXButton();
    button.setId(node.getId());
    button.setMinSize(6, 6);
    button.setMaxSize(6, 6);
    button.setPrefSize(6, 6);
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000
    double xPos;
    double yPos;
    if ("Faulkner".equals(node.getBuilding())) {
      xPos = ((node.getXCoord() * widthRatioFaulkner) - 3);
      yPos = ((node.getYCoord() * heightRatioFaulkner) - 3);
    } else {
      xPos = ((node.getXCoord() * widthRatioMain) - 3);
      yPos = ((node.getYCoord() * heightRatioMain) - 3);
    }
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);

    button.setOnAction(
        actionEvent -> {
          if (startNode == node && state == 1) { // Click again to de-select if start has been set
            startNode = null;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000
            state = 0;
            startCombo.setValue(null);
            startCombo.setDisable(false);
          } else if (endNode == node) { // deselect if end has been set, return to 1
            endNode = null;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000
            state = 1;
            endCombo.setValue(null);
            pathButton.setDisable(true);
            endCombo.setDisable(false);
          } else if (state == 0) { // if nothing has been set
            startNode = node;
            stairsBtn.setDisable(false);
            elevBtn.setDisable(false);
            bathBtn.setDisable(false);
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00cc00; -fx-border-color: #000000; -fx-border-width: 1px"); // 800000
            commandText.setText("Select End Location or Building Feature");
            state = 1;
            // startCombo.setDisable(true);
            startCombo.setValue(node.getLongName() + " " + node.getId());
            endCombo.setDisable(false);
          } else if (state == 1) { // select end if not set
            endNode = node;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px"); // 00cc00
            commandText.setText("Select Find Path or Reset");
            // labelNode("end");
            state = 2;
            // endCombo.setDisable(true);
            endCombo.setValue(node.getLongName() + " " + node.getId());
            pathButton.setDisable(false);
          }
        });
    getFloorPane(node.getFloor(), node.getBuilding()).getChildren().add(button);
  }

  public void reset() {
    resetPane();
  }

  public void resetPane() {
    resetButtonLine("1", "Faulkner");
    resetButtonLine("2", "Faulkner");
    resetButtonLine("3", "Faulkner");
    resetButtonLine("4", "Faulkner");
    resetButtonLine("5", "Faulkner");
    resetButtonLine("F1", "Main");
    resetButtonLine("F2", "Main");
    resetButtonLine("F3", "Main");
    resetButtonLine("G", "Main");
    resetButtonLine("L1", "Main");
    resetButtonLine("L2", "Main");

    if (startNode != null) {
      for (javafx.scene.Node component : currentPane.getChildren()) {
        if (component.getId().equals(startNode.getId())) {
          component.setStyle(
              "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                  + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
        }
      }
    }
    if (endNode != null) {
      for (javafx.scene.Node component : currentPane.getChildren()) {
        if (component.getId().equals(endNode.getId())) {
          component.setStyle(
              "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                  + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
        }
      }
    }
    startNode = null;
    endNode = null;
    state = 0;

    startCombo.setDisable(false);
    endCombo.setDisable(true);
    pathButton.setDisable(true);
    stairsBtn.setDisable(true);
    elevBtn.setDisable(true);
    bathBtn.setDisable(true);
    commandText.setText("Select Starting Location");
    directionsPane.setVisible(false);
    selectButtonsPane.setVisible(true);
    pathSwitchFloorPane.setVisible(false);

    uiSetting.makeZoomable(scrollPaneFaulkner1, masterPaneFaulkner1, 1.33);

    startCombo.setValue(null);
    endCombo.setValue(null);

    setComboBehavior();

    if (startLabel != null) {
      startLabel.setVisible(false);
    }
    if (endLabel != null) {
      endLabel.setVisible(false);
    }
    phoneNumber.setText("");
  }

  private void resetButtonLine(String floor, String building) {
    List<javafx.scene.Node> nodesToRemove = new ArrayList<>();
    for (javafx.scene.Node node : getFloorPane(floor, building).getChildren()) {
      if (node instanceof Line) {
        nodesToRemove.add(node);
      } else if (node instanceof JFXButton) {
        JFXButton button = (JFXButton) node;
        button.setStyle(
            "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
      }
    }
    getFloorPane(floor, building).getChildren().removeAll(nodesToRemove);
  }

  public void drawNodes() {
    for (Node node : fullNodeList) {
      if (!node.getType().equals(Node.NodeType.getEnum("HALL"))
          && !node.getType().equals(Node.NodeType.getEnum("STAI"))
          && !node.getType().equals(Node.NodeType.getEnum("ELEV"))
          && !node.getType().equals(Node.NodeType.getEnum("REST"))) {
        placeButton(node);
        pathButtonGo();
      }
    }
  }

  @SneakyThrows
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    fullNodeList = new ArrayList<>();
    currentPane = mapPaneFaulkner1;
    currentFloor = "1";
    setAllInvisible();
    selectFloorPaneMain.setVisible(false);
    scrollPaneFaulkner1.setVisible(true);
    mapPaneFaulkner1.setVisible(true);
    imageViewFaulkner1.setVisible(true);
    floorButtonsSet();
    initializehospitalComboBox();
    setToggleBehavior();

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(startCombo);
    uiSetting.setAsLocationComboBox(endCombo);

    uiSetting.makeZoomable(scrollPaneFaulkner1, masterPaneFaulkner1, 1.33);

    for (Node node : databaseManager.getAllNodes()) {
      node.setEdges(databaseManager.getAllEdgesConnectedToNode(node.getId()));
      fullNodeList.add(node);
    }

    pathFindAlgorithm = new MultipleHospitalAStar(fullNodeList);
    System.out.println("NEW PATHFIND:  " + newPathfind);
    updatePathFindAlgorithm();
    resetPane();
    drawNodes();
    deselectFloorButtons();
    floor1Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
    directionsPane.setVisible(false);
    setChooseLiftBehavior();

    // Set up the enable/disable of phone comms btns
    callDirections.setDisable(true);
    textDirections.setDisable(true);
    phoneNumber
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() == 10
                    && newValue.matches("[0-9]+")
                    && directionsDisplay.getText().length() > 0) {
                  callDirections.setDisable(false);
                  textDirections.setDisable(false);
                } else {
                  callDirections.setDisable(true);
                  textDirections.setDisable(true);
                }
              }
            });
  }

  public void animateLine(Line line) {

    line.getStrokeDashArray().setAll(5d, 5d, 5d, 5d);
    line.setStrokeWidth(2);

    final double maxOffset = line.getStrokeDashArray().stream().reduce(0d, (a, b) -> a + b);

    Timeline timeline =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new KeyValue(line.strokeDashOffsetProperty(), maxOffset, Interpolator.LINEAR)),
            new KeyFrame(
                Duration.seconds(1),
                new KeyValue(line.strokeDashOffsetProperty(), 0, Interpolator.LINEAR)));

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  private void initializehospitalComboBox() {
    hospitalComboBox.setItems(FXCollections.observableArrayList("Faulkner", "Main Campus"));
    hospitalComboBox.setValue("Faulkner");
    hospitalComboBox
        .valueProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (oldValue != null && !oldValue.equals(newValue)) {
                if ("Faulkner".equals(newValue)) {
                  switchToFloor("1", "Faulkner");

                } else if ("Main Campus".equals(newValue)) {
                  switchToFloor("G", "Main Campus");
                }
              }
            }));
  }

  public void findType(String type) throws InstanceNotFoundException {
    switchToFloor(startNode.getFloor(), startNode.getBuilding());
    startCombo.setDisable(true);
    endCombo.setDisable(true);
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum(type));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
  }

  public void findElevator(MouseEvent mouseEvent) throws InstanceNotFoundException {
    findType("ELEV");
  }

  public void findStairs(MouseEvent mouseEvent) throws InstanceNotFoundException {
    findType("STAI");
  }

  public void findBathroom(MouseEvent mouseEvent) throws InstanceNotFoundException {
    findType("REST");
  }

  public void comboSelectStart() {
    String startLocation = startCombo.getValue();
    if (startLocation.length() > 10) {
      String startID = startLocation.substring(startLocation.length() - 10);
      for (Node node : fullNodeList) {
        if (node.getId().equals(startID)) {
          if (startNode != null) {
            for (javafx.scene.Node component : currentPane.getChildren()) {
              if (component.getId().equals(startNode.getId())) {
                component.setStyle(
                    "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                        + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
              }
            }
          }
          startNode = node;
          stairsBtn.setDisable(false);
          elevBtn.setDisable(false);
          bathBtn.setDisable(false);
          for (javafx.scene.Node component :
              getFloorPane(node.getFloor(), node.getBuilding()).getChildren()) {
            if (component.getId().equals(node.getId())) {
              component.setStyle(
                  "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00cc00; "
                      + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
            }
          }
        }
      }
    }
  }

  public void comboSelectEnd() {
    String endLocation = endCombo.getValue();
    if (endLocation.length() > 10) {
      String endID = endLocation.substring(endLocation.length() - 10);
      for (Node node : fullNodeList) {

        if (node.getId().equals(endID)) {
          if (endNode != null) {
            for (javafx.scene.Node component : currentPane.getChildren()) {
              if (component.getId().equals(endNode.getId())) {
                component.setStyle(
                    "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                        + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
              }
            }
          }
          endNode = node;
          stairsBtn.setDisable(true);
          elevBtn.setDisable(true);
          bathBtn.setDisable(true);
          for (javafx.scene.Node component :
              getFloorPane(node.getFloor(), node.getBuilding()).getChildren()) {
            if (component.getId().equals(node.getId())) {
              component.setStyle(
                  "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; "
                      + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
            }
          }
        }
      }
    }
  }

  public Node findComboLocation(JFXComboBox<String> comboBox) {
    Node returnNode = null;
    String location = comboBox.getValue();
    if (location.length() > 10) {
      String startID = location.substring(location.length() - 10);
      for (Node node : fullNodeList) {
        if (node.getId().equals(startID)) {
          returnNode = node;
        }
      }
    }
    return returnNode;
  }

  public void setComboBehavior() {
    startCombo.setOnAction(
        actionEvent -> {
          String startLocation = startCombo.getValue();
          if (startLocation != null && startLocation.length() > 10) {
            comboSelectStart();
            state = 1;
            commandText.setText("Select End Location or Building Feature");
            endCombo.setDisable(false);
            if (findComboLocation(startCombo).getFloor().equals(currentFloor)
                && findComboLocation(startCombo).getBuilding().equals(currentBuilding)) {
              String nameHolder = startCombo.getValue();
              Node nodeHolder = findComboLocation(startCombo);
              switchToFloor(nodeHolder.getFloor(), nodeHolder.getBuilding());
              startCombo.setValue(nameHolder);
              endNode = nodeHolder;
            }
          }
        });

    endCombo.setOnAction(
        actionEvent -> {
          String endLocation = endCombo.getValue();
          if (endLocation != null && endLocation.length() > 10) {
            comboSelectEnd();
            state = 2;
            commandText.setText("Select Find Path or Reset");
            pathButton.setDisable(false);
          }
        });
  }

  public void pathButtonGo() {
    pathButton.setOnAction(
        actionEvent -> {
          startCombo.setDisable(true);
          endCombo.setDisable(true);
          startNode = findComboLocation(startCombo);
          endNode = findComboLocation(endCombo);
          System.out.println("start" + startNode);
          System.out.println("end" + endNode);
          Path path = null;
          switchToFloor(startNode.getFloor(), startNode.getBuilding());
          labelNode();
          labelNodeEnd();

          System.out.println(liftType);

          try {
            path = pathFindAlgorithm.pathfind(startNode, endNode);
          } catch (InstanceNotFoundException e) {
            e.printStackTrace();
          }
          try {
            commandText.setText("See Path Below for Directions");
            draw(path);
          } catch (InstanceNotFoundException e) {
            e.printStackTrace();
          }
        });
  }

  public void floorButtonsSet() {
    floor1Button.setOnAction(actionEvent -> switchToFloor("1", "Faulkner"));
    floor2Button.setOnAction(actionEvent -> switchToFloor("2", "Faulkner"));
    floor3Button.setOnAction(actionEvent -> switchToFloor("3", "Faulkner"));
    floor4Button.setOnAction(actionEvent -> switchToFloor("4", "Faulkner"));
    floor5Button.setOnAction(actionEvent -> switchToFloor("5", "Faulkner"));
    mainFloor1Button.setOnAction(actionEvent -> switchToFloor("F1", "Main"));
    mainFloor2Button.setOnAction(actionEvent -> switchToFloor("F2", "Main"));
    mainFloor3Button.setOnAction(actionEvent -> switchToFloor("F3", "Main"));
    mainFloorGButton.setOnAction(actionEvent -> switchToFloor("G", "Main"));
    mainFloorL1Button.setOnAction(actionEvent -> switchToFloor("L1", "Main"));
    mainFloorL2Button.setOnAction(actionEvent -> switchToFloor("L2", "Main"));
  }

  public void deselectFloorButtons() {
    for (javafx.scene.Node btn : selectFloorPane.getChildren()) {
      btn.setStyle("-fx-background-color: #4d6c8b; -fx-background-radius: 10px");
    }
    for (javafx.scene.Node btn : selectFloorPaneMain.getChildren()) {
      btn.setStyle("-fx-background-color: #4d6c8b; -fx-background-radius: 10px");
    }
  }

  public void setAllInvisible() {
    mapPaneFaulkner1.setVisible(false);
    imageViewFaulkner1.setVisible(false);
    mapPaneFaulkner2.setVisible(false);
    imageViewFaulkner2.setVisible(false);
    mapPaneFaulkner3.setVisible(false);
    imageViewFaulkner3.setVisible(false);
    mapPaneFaulkner4.setVisible(false);
    imageViewFaulkner4.setVisible(false);
    mapPaneFaulkner5.setVisible(false);
    imageViewFaulkner5.setVisible(false);
    mapPaneMain1.setVisible(false);
    mapPaneMain2.setVisible(false);
    mapPaneMain3.setVisible(false);
    mapPaneMainG.setVisible(false);
    mapPaneMainL1.setVisible(false);
    mapPaneMainL2.setVisible(false);
    imageViewMain1.setVisible(false);
    imageViewMain2.setVisible(false);
    imageViewMain3.setVisible(false);
    imageViewMainG.setVisible(false);
    imageViewMainL1.setVisible(false);
    imageViewMainL2.setVisible(false);
  }

  public void switchToFloor(String floorNum, String building) {
    if ("Faulkner".equals(building)) {
      hospitalComboBox.setValue("Faulkner");
      selectFloorPane.setVisible(true);
      selectFloorPaneMain.setVisible(false);
    } else {
      hospitalComboBox.setValue("Main Campus");
      selectFloorPane.setVisible(false);
      selectFloorPaneMain.setVisible(true);
    }
    currentPane = getFloorPane(floorNum, building);
    currentFloor = floorNum;
    currentBuilding = building;
    setAllInvisible();
    currentPane.setVisible(true);
    getFloorImage(floorNum, building).setVisible(true);
    deselectFloorButtons();
    getFloorButton(floorNum, building)
        .setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
  }

  public void pathSwitchPrevious(ActionEvent actionEvent) {
    locationIndex--;
    pathSwitchNext.setVisible(true);
    pathSwitchNext.setPrefHeight(50);
    btnSpacer.setPrefHeight(10);
    System.out.println("Changed to Index: " + locationIndex);
    System.out.println("Floor: " + path.getLocationAtIndex(locationIndex).getFloor());
    System.out.println("Building: " + path.getLocationAtIndex(locationIndex).getBuilding());
    switchToFloor(
        path.getLocationAtIndex(locationIndex).getFloor(),
        path.getLocationAtIndex(locationIndex).getBuilding());

    if (locationIndex == 0) {
      // If we have gotten back to the first floor, disable and hide the previous button
      pathSwitchPrevious.setVisible(false);
      pathSwitchPrevious.setPrefHeight(0);
      btnSpacer.setPrefHeight(0);
    } else {
      // Need to update the text for previous button
      if (!(path.getLocationAtIndex(locationIndex)
          .getBuilding()
          .equals(path.getLocationAtIndex(locationIndex - 1).getBuilding()))) {
        // previous stop is at a different hospital
        pathSwitchPrevious.setText(
            "Previous: Go to " + path.getLocationAtIndex(locationIndex - 1).getBuilding());
      } else {
        pathSwitchPrevious.setText(
            "Previous: Go to floor " + path.getLocationAtIndex(locationIndex - 1).getFloor());
      }
    }
    // Need to update the text for next button
    if (!(path.getLocationAtIndex(locationIndex)
        .getBuilding()
        .equals(path.getLocationAtIndex(locationIndex + 1).getBuilding()))) {
      // Next stop is at a different hospital
      pathSwitchNext.setText(
          "Next: Go to " + path.getLocationAtIndex(locationIndex + 1).getBuilding());
    } else {
      pathSwitchNext.setText(
          "Next: Go to floor " + path.getLocationAtIndex(locationIndex + 1).getFloor());
    }
  }

  public void pathSwitchNext(ActionEvent actionEvent) {
    locationIndex++;
    pathSwitchPrevious.setVisible(true);
    pathSwitchPrevious.setPrefHeight(50);
    btnSpacer.setPrefHeight(10);
    System.out.println("Changed to Index: " + locationIndex);
    System.out.println("Floor: " + path.getLocationAtIndex(locationIndex).getFloor());
    System.out.println("Building: " + path.getLocationAtIndex(locationIndex).getBuilding());
    switchToFloor(
        path.getLocationAtIndex(locationIndex).getFloor(),
        path.getLocationAtIndex(locationIndex).getBuilding());

    if (locationIndex == (path.getUniqueLocations() - 1)) {
      // If we have gotten to the final location, disable and hide the next button
      pathSwitchNext.setVisible(false);
      pathSwitchNext.setPrefHeight(0);
      btnSpacer.setPrefHeight(0);
    } else {
      // Need to update the text for next button
      if (!(path.getLocationAtIndex(locationIndex)
          .getBuilding()
          .equals(path.getLocationAtIndex(locationIndex + 1).getBuilding()))) {
        // Next stop is at a different hospital
        pathSwitchNext.setText(
            "Next: Go to " + path.getLocationAtIndex(locationIndex + 1).getBuilding());
      } else {
        pathSwitchNext.setText(
            "Next: Go to floor " + path.getLocationAtIndex(locationIndex + 1).getFloor());
      }
    }
    // Need to update the text for previous button
    if (!(path.getLocationAtIndex(locationIndex)
        .getBuilding()
        .equals(path.getLocationAtIndex(locationIndex - 1).getBuilding()))) {
      // Next stop is at a different hospital
      pathSwitchPrevious.setText(
          "Previous: Go to " + path.getLocationAtIndex(locationIndex - 1).getBuilding());
    } else {
      pathSwitchPrevious.setText(
          "Previous: Go to floor " + path.getLocationAtIndex(locationIndex - 1).getFloor());
    }
  }

  public AnchorPane getFloorPane(String floor, String building) {
    if ("Faulkner".equals(building)) {
      switch (floor) {
        case "1":
          return mapPaneFaulkner1;
        case "2":
          return mapPaneFaulkner2;
        case "3":
          return mapPaneFaulkner3;
        case "4":
          return mapPaneFaulkner4;
        default:
          return mapPaneFaulkner5;
      }
    } else {
      switch (floor) {
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
        default:
          return mapPaneMainL2;
      }
    }
  }

  public ImageView getFloorImage(String floor, String building) {
    if ("Faulkner".equals(building)) {
      switch (floor) {
        case "1":
          return imageViewFaulkner1;
        case "2":
          return imageViewFaulkner2;
        case "3":
          return imageViewFaulkner3;
        case "4":
          return imageViewFaulkner4;
        default:
          return imageViewFaulkner5;
      }
    } else {
      switch (floor) {
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
        default:
          return imageViewMainL2;
      }
    }
  }

  public JFXButton getFloorButton(String floor, String building) {
    if ("Faulkner".equals(building)) {
      switch (floor) {
        case "1":
          return floor1Button;
        case "2":
          return floor2Button;
        case "3":
          return floor3Button;
        case "4":
          return floor4Button;
        default:
          return floor5Button;
      }
    } else {
      switch (floor) {
        case "F1":
          return mainFloor1Button;
        case "F2":
          return mainFloor2Button;
        case "F3":
          return mainFloor3Button;
        case "G":
          return mainFloorGButton;
        case "L1":
          return mainFloorL1Button;
        default:
          return mainFloorL2Button;
      }
    }
  }

  private void setChooseLiftBehavior() {
    chooseLiftElevator.setOnAction(
        actionEvent -> {
          liftType = "ELEV";
          pathFindAlgorithm.setLiftType(liftType);
        });
    chooseLiftStairs.setOnAction(
        actionEvent -> {
          liftType = "STAI";
          pathFindAlgorithm.setLiftType(liftType);
        });
  }

  public void labelNode() {

    startLabel = new Label();
    for (javafx.scene.Node component :
        getFloorPane(startNode.getFloor(), startNode.getBuilding()).getChildren()) {
      if (component.getId().equals(startNode.getId())) {
        startLabel.setStyle("-fx-font-size: 12");
        startLabel.setText(startNode.getLongName());
        //        startLabel
        //            .layoutXProperty()
        //            .bind(startLabel.widthProperty().divide(2.0).add(component.getLayoutX()));

        System.out.println(startLabel.getPrefWidth());
        System.out.println(startLabel.getWidth());
        System.out.println(startLabel.getMinWidth());
        System.out.println(startLabel.getMaxWidth());
        getFloorPane(startNode.getFloor(), startNode.getBuilding()).getChildren().add(startLabel);

        startLabel.setLayoutX(component.getLayoutX() - 20); // (startLabel.getHeight()));
        startLabel.setLayoutY(component.getLayoutY() - 20);
        startLabel.setId("startLabel");
        startLabel.setVisible(true);
        return;
      }
    }
  }

  private void labelNodeEnd() {
    endLabel = new Label();
    for (javafx.scene.Node component :
        getFloorPane(endNode.getFloor(), endNode.getBuilding()).getChildren()) {
      if (component.getId().equals(endNode.getId())) {
        endLabel.setStyle("-fx-font-size: 12");
        endLabel.setText(endNode.getLongName());
        getFloorPane(endNode.getFloor(), endNode.getBuilding()).getChildren().add(endLabel);
        endLabel.setLayoutX(component.getLayoutX() - (endLabel.getHeight()));
        endLabel.setLayoutY(component.getLayoutY() - 20);
        endLabel.setId("endLabel");
        endLabel.setVisible(true);
        return;
      }
    }
  }

  private boolean sameHospital(String building1, String building2) {
    return ("Faulkner".equals(building1) && "Faulkner".equals(building2))
        || (!"Faulkner".equals(building1) && !"Faulkner".equals(building2));
  }

  public void textDirections(ActionEvent actionEvent) {
    directions.smsDirections(phoneNumber.getText());
  }

  public void callDirections(ActionEvent actionEvent) {
    directions.callDirections(phoneNumber.getText());
  }

  public void printDirections(ActionEvent actionEvent) {}

  private void setToggleBehavior() {
    liftToggle.setOnAction(
        actionEvent -> {
          if (liftToggle.isSelected()) {
            liftType = "STAI";
            pathFindAlgorithm.setLiftType(liftType);
          } else {
            liftType = "ELEV";
            pathFindAlgorithm.setLiftType(liftType);
          }
        });
  }
}
