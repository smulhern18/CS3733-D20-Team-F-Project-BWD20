package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Directions.Directions;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.BreadthFirst;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.DepthFirstSearch;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.MultipleFloorAStar;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.PathfindAlgorithm;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javax.management.InstanceNotFoundException;
import lombok.SneakyThrows;

public class PathfinderController implements Initializable {

  public static int MAP_HEIGHT = 1485;
  public static int MAP_WIDTH = 2475;
  public int currentFloor;
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
  public List<Node> nodeList;
  public List<Node> fullNodeList;
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
  public Label directionsDisplay;
  public AnchorPane pathSwitchFloorPane;
  public JFXButton pathSwitchFloor;
  public int state;
  public UISetting uiSetting = new UISetting();

  Node startNode = null;
  Node endNode = null;

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
        this.pathFindAlgorithm = new MultipleFloorAStar(fullNodeList);
        System.out.println("successful astar");
        break;
      case "Breadth First":
        this.pathFindAlgorithm = new BreadthFirst(fullNodeList);
        System.out.println("successful breath");
        break;
      case "Depth First":
        this.pathFindAlgorithm = new DepthFirstSearch(fullNodeList);
        System.out.println("successful Depth first");
        break;
      default:
        break;
    }
  }

  public void draw(Path path) throws InstanceNotFoundException {

    List<Node> pathNodes = path.getPath();
    if (endNode == null) {
      endNode = pathNodes.get(pathNodes.size() - 1);
    }

    double heightRatio = currentPane.getPrefHeight() / MAP_HEIGHT;
    double widthRatio = currentPane.getPrefWidth() / MAP_WIDTH;

    for (int i = 0; i < pathNodes.size() - 1; i++) {
      int startX = (int) (pathNodes.get(i).getXCoord() * widthRatio);
      int startY = (int) (pathNodes.get(i).getYCoord() * heightRatio);
      int endX = (int) (pathNodes.get(i + 1).getXCoord() * widthRatio);
      int endY = (int) (pathNodes.get(i + 1).getYCoord() * heightRatio);
      Line line = new Line(startX, startY, endX, endY);
      line.setStroke(Color.RED);
      line.setStrokeWidth(2);
      if (pathNodes.get(i).getFloor() == 1) {
        mapPaneFaulkner1.getChildren().add(line);
      } else if (pathNodes.get(i).getFloor() == 2) {
        mapPaneFaulkner2.getChildren().add(line);
      } else if (pathNodes.get(i).getFloor() == 3) {
        mapPaneFaulkner3.getChildren().add(line);
      } else if (pathNodes.get(i).getFloor() == 4) {
        mapPaneFaulkner4.getChildren().add(line);
      } else if (pathNodes.get(i).getFloor() == 5) {
        mapPaneFaulkner5.getChildren().add(line);
      }
    }

    selectButtonsPane.setVisible(false);
    directionsPane.setVisible(true);
    Directions directions = new Directions(fullNodeList, path, startNode, endNode);
    System.out.println(directions.getFullDirectionsString());
    directionsDisplay.setText(directions.getFullDirectionsString());
    pathSwitchFloorPane.setVisible(true);
    if (startNode.getFloor() != endNode.getFloor()) {
      pathSwitchFloor.setVisible(true);
      pathSwitchFloor.setText("Next: Go to floor " + Integer.toString(endNode.getFloor()));
    }
  }

  public void placeButton(Node node) {

    double heightRatioFaulkner5 = (double) currentPane.getPrefHeight() / MAP_HEIGHT;
    double widthRatioFaulkner5 = (double) currentPane.getPrefWidth() / MAP_WIDTH;

    JFXButton button = new JFXButton();
    button.setId(node.getId());
    button.setMinSize(12, 12);
    button.setMaxSize(12, 12);
    button.setPrefSize(12, 12);
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000

    int xPos = (int) ((node.getXCoord() * widthRatioFaulkner5) - 6);
    int yPos = (int) ((node.getYCoord() * heightRatioFaulkner5) - 6);

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
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px"); // 800000
            commandText.setText("Select End Location or Building Feature");
            state = 1;
            // startCombo.setDisable(true);
            startCombo.setValue(node.getLongName() + " " + node.getId());
            endCombo.setDisable(false);
          } else if (state == 1) { // select end if not set
            endNode = node;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00cc00; -fx-border-color: #000000; -fx-border-width: 1px"); // 00cc00
            commandText.setText("Select Find Path or Reset");
            state = 2;
            // endCombo.setDisable(true);
            endCombo.setValue(node.getLongName() + " " + node.getId());
            pathButton.setDisable(false);
          }
        });
    if (node.getFloor() == 1) {
      mapPaneFaulkner1.getChildren().add(button);
    } else if (node.getFloor() == 2) {
      mapPaneFaulkner2.getChildren().add(button);
    } else if (node.getFloor() == 3) {
      mapPaneFaulkner3.getChildren().add(button);
    } else if (node.getFloor() == 4) {
      mapPaneFaulkner4.getChildren().add(button);
    } else if (node.getFloor() == 5) {
      mapPaneFaulkner5.getChildren().add(button);
    }
  }

  public void reset() {
    resetPane();
  }

  public void resetPane() {
    List<javafx.scene.Node> nodesToRemove1 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner1.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove1.add(node);
      } else if (node instanceof JFXButton) {
        JFXButton button = (JFXButton) node;
        button.setStyle(
            "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
      }
    }
    mapPaneFaulkner1.getChildren().removeAll(nodesToRemove1);
    List<javafx.scene.Node> nodesToRemove2 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner2.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove2.add(node);
      } else if (node instanceof JFXButton) {
        JFXButton button = (JFXButton) node;
        button.setStyle(
            "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
      }
    }
    mapPaneFaulkner2.getChildren().removeAll(nodesToRemove2);
    List<javafx.scene.Node> nodesToRemove3 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner3.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove3.add(node);
      } else if (node instanceof JFXButton) {
        JFXButton button = (JFXButton) node;
        button.setStyle(
            "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
      }
    }
    mapPaneFaulkner3.getChildren().removeAll(nodesToRemove3);
    List<javafx.scene.Node> nodesToRemove4 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner4.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove4.add(node);
      } else if (node instanceof JFXButton) {
        JFXButton button = (JFXButton) node;
        button.setStyle(
            "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
      }
    }
    mapPaneFaulkner4.getChildren().removeAll(nodesToRemove4);
    List<javafx.scene.Node> nodesToRemove5 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner5.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove5.add(node);
      } else if (node instanceof JFXButton) {
        JFXButton button = (JFXButton) node;
        button.setStyle(
            "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #012D5A; "
                + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
      }
    }
    mapPaneFaulkner5.getChildren().removeAll(nodesToRemove5);

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
    nodeList = new ArrayList<>();
    fullNodeList = new ArrayList<>();
    currentPane = mapPaneFaulkner1;
    currentFloor = 1;
    setAllInvisible();
    scrollPaneFaulkner1.setVisible(true);
    mapPaneFaulkner1.setVisible(true);
    imageViewFaulkner1.setVisible(true);
    floorButtonsSet();
    pathSwitchFloor.setVisible(false);

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(startCombo);
    uiSetting.setAsLocationComboBox(endCombo);
    String startLocation = startCombo.getValue();
    //    String startID = startLocation.substring(startLocation.length() - 10);
    String endLocation = endCombo.getValue();
    //    String endID = endLocation.substring(endLocation.length() - 10);

    uiSetting.makeZoomable(scrollPaneFaulkner1, masterPaneFaulkner1, 1.33);
    //    imageViewFaulkner1.fitWidthProperty().bind(scrollPaneFaulkner1.widthProperty());
    //    imageViewFaulkner1.fitHeightProperty().bind(scrollPaneFaulkner1.heightProperty());
    //    imageViewFaulkner2.fitWidthProperty().bind(scrollPaneFaulkner1.widthProperty());
    //    imageViewFaulkner2.fitHeightProperty().bind(scrollPaneFaulkner1.heightProperty());
    //    imageViewFaulkner3.fitWidthProperty().bind(scrollPaneFaulkner1.widthProperty());
    //    imageViewFaulkner3.fitHeightProperty().bind(scrollPaneFaulkner1.heightProperty());
    //    imageViewFaulkner4.fitWidthProperty().bind(scrollPaneFaulkner1.widthProperty());
    //    imageViewFaulkner4.fitHeightProperty().bind(scrollPaneFaulkner1.heightProperty());
    //    imageViewFaulkner5.fitWidthProperty().bind(scrollPaneFaulkner1.widthProperty());
    //    imageViewFaulkner5.fitHeightProperty().bind(scrollPaneFaulkner1.heightProperty());

    for (Node node : databaseManager.getAllNodes()) {
      node.setEdges(databaseManager.getAllEdgesConnectedToNode(node.getId()));
      fullNodeList.add(node);
    }

    setNodeList(1);
    //    for (Node node : fullNodeList) {
    //      if (!node.getType().equals(Node.NodeType.getEnum("HALL"))) {
    //        startCombo.getItems().add(node.getLongName());
    //        endCombo.getItems().add(node.getLongName());
    //      }
    //    }

    pathFindAlgorithm = new MultipleFloorAStar(fullNodeList);
    System.out.println("NEW PATHFIND:  " + newPathfind);
    updatePathFindAlgorithm();
    resetPane();
    drawNodes();
    deselectFloorButtons();
    floor1Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
    directionsPane.setVisible(false);
  }

  public void findElevator(MouseEvent mouseEvent) throws InstanceNotFoundException {
    switchToFloor(startNode.getFloor());
    startCombo.setDisable(true);
    endCombo.setDisable(true);
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum("ELEV"));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
  }

  public void findStairs(MouseEvent mouseEvent) throws InstanceNotFoundException {
    switchToFloor(startNode.getFloor());
    startCombo.setDisable(true);
    endCombo.setDisable(true);
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum("STAI"));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
  }

  public void findBathroom(MouseEvent mouseEvent) throws InstanceNotFoundException {
    switchToFloor(startNode.getFloor());
    startCombo.setDisable(true);
    endCombo.setDisable(true);
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum("REST"));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
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
          for (javafx.scene.Node component : getFloorPane(node.getFloor()).getChildren()) {
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

  public void comboSelectEnd() {
    String endLocation = endCombo.getValue();
    if (endLocation.length() > 10) {
      //    String endLocation = endCombo.getValue();
      //    if (endCombo.getValue() != null) {
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
          for (javafx.scene.Node component : getFloorPane(node.getFloor()).getChildren()) {
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

  public Node findComboStart() {
    Node returnNode = null;
    String startLocation = startCombo.getValue();
    if (startLocation.length() > 10) {
      String startID = startLocation.substring(startLocation.length() - 10);
      System.out.println("Start ID: " + startID);
      System.out.println(startNode);
      for (Node node : fullNodeList) {
        if (node.getId().equals(startID)) {
          returnNode = node;
        }
      }
    }
    return returnNode;
  }

  public Node findComboEnd() {
    Node returnNode = null;
    String endLocation = endCombo.getValue();
    System.out.println("EndLocation: " + endLocation);
    if (endLocation.length() > 10) {
      String endID = endLocation.substring(endLocation.length() - 10);
      System.out.println("end" + endID);
      for (Node node : fullNodeList) {
        if (node.getId().equals(endID)) {
          returnNode = node;
        }
      }
    }
    System.out.println(returnNode);
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
            if (findComboStart().getFloor() != currentFloor) {
              String nameHolder = startCombo.getValue();
              Node nodeHolder = findComboStart();
              switchToFloor(findComboStart().getFloor());
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
          startNode = findComboStart();
          endNode = findComboEnd();
          System.out.println("start" + startNode);
          System.out.println("end" + endNode);
          Path path = null;
          switchToFloor(startNode.getFloor());
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
    floor1Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner1;
          currentFloor = 1;
          setNodeList(1);
          // resetPane();
          setAllInvisible();
          mapPaneFaulkner1.setVisible(true);
          imageViewFaulkner1.setVisible(true);
          deselectFloorButtons();
          floor1Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
        });
    floor2Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner2;
          currentFloor = 2;
          setNodeList(2);
          // resetPane();
          setAllInvisible();
          mapPaneFaulkner2.setVisible(true);
          imageViewFaulkner2.setVisible(true);
          deselectFloorButtons();
          floor2Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
        });
    floor3Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner3;
          currentFloor = 3;
          setNodeList(3);
          // resetPane();
          setAllInvisible();
          mapPaneFaulkner3.setVisible(true);
          imageViewFaulkner3.setVisible(true);
          deselectFloorButtons();
          floor3Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
        });
    floor4Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner4;
          currentFloor = 4;
          setNodeList(4);
          // resetPane();
          setAllInvisible();
          mapPaneFaulkner4.setVisible(true);
          imageViewFaulkner4.setVisible(true);
          deselectFloorButtons();
          floor4Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
        });
    floor5Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner5;
          currentFloor = 5;
          setNodeList(5);
          // resetPane();
          setAllInvisible();
          mapPaneFaulkner5.setVisible(true);
          imageViewFaulkner5.setVisible(true);
          deselectFloorButtons();
          floor5Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
        });
  }

  public void deselectFloorButtons() {
    for (javafx.scene.Node btn : selectFloorPane.getChildren()) {
      btn.setStyle("-fx-background-color: #4d6c8b; -fx-background-radius: 10px");
    }
  }

  public void setNodeList(int floorNum) {
    nodeList = new ArrayList<>();
    for (Node node : fullNodeList) {
      if (node.getFloor() == floorNum) { // change for floors
        nodeList.add(node);
      }
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
  }

  public void switchToFloor(int floorNum) {
    if (floorNum == 1) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner1;
      currentFloor = 1;
      setNodeList(1);
      // resetPane();
      setAllInvisible();
      mapPaneFaulkner1.setVisible(true);
      imageViewFaulkner1.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName() + " " + startNode.getId());
      deselectFloorButtons();
      floor1Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
    }
    if (floorNum == 2) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner2;
      currentFloor = 2;
      setNodeList(2);
      // resetPane();
      setAllInvisible();
      mapPaneFaulkner2.setVisible(true);
      imageViewFaulkner2.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName() + " " + startNode.getId());
      deselectFloorButtons();
      floor2Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
    }
    if (floorNum == 3) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner3;
      currentFloor = 3;
      setNodeList(3);
      // resetPane();
      setAllInvisible();
      mapPaneFaulkner3.setVisible(true);
      imageViewFaulkner3.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName() + " " + startNode.getId());
      deselectFloorButtons();
      floor3Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
    }
    if (floorNum == 4) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner4;
      currentFloor = 4;
      setNodeList(4);
      // resetPane();
      setAllInvisible();
      mapPaneFaulkner4.setVisible(true);
      imageViewFaulkner4.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName() + " " + startNode.getId());
      deselectFloorButtons();
      floor4Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
    }
    if (floorNum == 5) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner5;
      currentFloor = 5;
      setNodeList(5);
      // resetPane();
      setAllInvisible();
      mapPaneFaulkner5.setVisible(true);
      imageViewFaulkner5.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName() + " " + startNode.getId());
      deselectFloorButtons();
      floor5Button.setStyle("-fx-background-color: #012D5A; -fx-background-radius: 10px");
    }
  }

  public void pathSwitchFloor(ActionEvent actionEvent) {
    if (currentFloor == startNode.getFloor()) {
      // Currently on the start floor, want to go to the end floor
      switchToFloor(endNode.getFloor());
      pathSwitchFloor.setText("Previous: Go to floor " + Integer.toString(startNode.getFloor()));
    } else {
      switchToFloor(startNode.getFloor());
      pathSwitchFloor.setText("Next: Go to floor " + Integer.toString(endNode.getFloor()));
    }
  }

  public AnchorPane getFloorPane(int floor) {
    switch (floor) {
      case 1:
        return mapPaneFaulkner1;
      case 2:
        return mapPaneFaulkner2;
      case 3:
        return mapPaneFaulkner3;
      case 4:
        return mapPaneFaulkner4;
      default:
        return mapPaneFaulkner5;
    }
  }
}
