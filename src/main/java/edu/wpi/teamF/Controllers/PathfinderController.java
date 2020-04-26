package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Directions.Directions;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.MultipleFloorAStar;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.PathfindAlgorithm;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
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
  public AnchorPane mapPaneFaulkner5;
  public AnchorPane mapPaneFaulkner4;
  public AnchorPane mapPaneFaulkner3;
  public AnchorPane mapPaneFaulkner2;
  public AnchorPane mapPaneFaulkner1;
  public StackPane masterPaneFaulkner1;
  public StackPane masterPaneFaulkner2;
  public StackPane masterPaneFaulkner3;
  public StackPane masterPaneFaulkner4;
  public StackPane masterPaneFaulkner5;
  public ScrollPane scrollPaneFaulkner1;
  public ScrollPane scrollPaneFaulkner2;
  public ScrollPane scrollPaneFaulkner3;
  public ScrollPane scrollPaneFaulkner4;
  public ScrollPane scrollPaneFaulkner5;
  public List<Node> nodeList;
  public List<Node> fullNodeList;
  public JFXButton stairsBtn;
  public JFXButton elevBtn;
  public JFXButton bathBtn;
  public JFXButton floor1Button;
  public JFXButton floor2Button;
  public JFXButton floor3Button;
  public JFXButton floor4Button;
  public JFXButton floor5Button;
  public Text commandText;
  public JFXComboBox startCombo;
  public JFXComboBox endCombo;
  public JFXButton pathButton;
  public int state;
  public UISetting uiSetting = new UISetting();

  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private static EdgeFactory edgeFactory = EdgeFactory.getFactory();

  Node startNode = null;
  Node endNode = null;
  PathfindAlgorithm pathFindAlgorithm;
  EuclideanScorer euclideanScorer = new EuclideanScorer();

  public PathfinderController() {}

  public void draw(Path path) throws InstanceNotFoundException {

    List<Node> pathNodes = path.getPath();

    double heightRatio = currentPane.getHeight() / MAP_HEIGHT;
    double widthRatio = currentPane.getWidth() / MAP_WIDTH;

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

    // TODO Remove this code before pushing, for testing only
    Directions directions = new Directions(fullNodeList, path, startNode, endNode);
    System.out.println(directions.getFullDirectionsString());
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
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000

    int xPos = (int) ((node.getXCoord() * widthRatioFaulkner5) - 6);
    int yPos = (int) ((node.getYCoord() * heightRatioFaulkner5) - 6);

    button.setLayoutX(xPos);
    button.setLayoutY(yPos);

    button.setOnAction(
        actionEvent -> {
          if (startNode == node && state == 1) { // Click again to de-select if start has been set
            startNode = null;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000
            state = 0;
            startCombo.setValue(null);
            startCombo.setDisable(false);
          } else if (endNode == node) { // deselect if end has been set, return to 1
            endNode = null;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000
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
            startCombo.setValue(node.getLongName());
            endCombo.setDisable(false);
          } else if (state == 1) { // select end if not set
            endNode = node;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00cc00; -fx-border-color: #000000; -fx-border-width: 1px"); // 00cc00
            commandText.setText("Select Find Path or Reset");
            state = 2;
            // endCombo.setDisable(true);
            endCombo.setValue(node.getLongName());
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
      }
    }
    mapPaneFaulkner1.getChildren().removeAll(nodesToRemove1);
    List<javafx.scene.Node> nodesToRemove2 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner2.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove2.add(node);
      }
    }
    mapPaneFaulkner2.getChildren().removeAll(nodesToRemove2);
    List<javafx.scene.Node> nodesToRemove3 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner3.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove3.add(node);
      }
    }
    mapPaneFaulkner3.getChildren().removeAll(nodesToRemove3);
    List<javafx.scene.Node> nodesToRemove4 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner4.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove4.add(node);
      }
    }
    mapPaneFaulkner4.getChildren().removeAll(nodesToRemove4);
    List<javafx.scene.Node> nodesToRemove5 = new ArrayList<>();
    for (javafx.scene.Node node : mapPaneFaulkner5.getChildren()) {
      if (node instanceof Line) {
        nodesToRemove5.add(node);
      }
    }
    mapPaneFaulkner5.getChildren().removeAll(nodesToRemove5);

    if (startNode != null) {
      for (javafx.scene.Node component : currentPane.getChildren()) {
        if (component.getId().equals(startNode.getId())) {
          component.setStyle(
              "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; "
                  + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
        }
      }
    }
    if (endNode != null) {
      for (javafx.scene.Node component : currentPane.getChildren()) {
        if (component.getId().equals(endNode.getId())) {
          component.setStyle(
              "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; "
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
    floorButtonsSet();

    //    uiSetting.makeZoomable(scrollPaneFaulkner1, masterPaneFaulkner1);
    //    uiSetting.makeZoomable(scrollPaneFaulkner2, masterPaneFaulkner2);
    //    uiSetting.makeZoomable(scrollPaneFaulkner3, masterPaneFaulkner3);
    //    uiSetting.makeZoomable(scrollPaneFaulkner4, masterPaneFaulkner4);
    //    uiSetting.makeZoomable(scrollPaneFaulkner5, masterPaneFaulkner5);

    for (Node node : nodeFactory.getAllNodes()) {
      node.setEdges(edgeFactory.getAllEdgesConnectedToNode(node.getId()));
      fullNodeList.add(node);
    }

    setNodeList(1);
    for (Node node : fullNodeList) {
      if (!node.getType().equals(Node.NodeType.getEnum("HALL"))) {
        startCombo.getItems().add(node.getLongName());
        endCombo.getItems().add(node.getLongName());
      }
    }

    pathFindAlgorithm = new MultipleFloorAStar(fullNodeList);
    resetPane();
    drawNodes();
  }

  private void findBestOfType(String nodeType) throws InstanceNotFoundException {
    // TODO Add a check that the node must be connected to a hall or the startNode, this will
    // prevent paths through the ICU to get to stairs
    switchToFloor(startNode.getFloor());
    double lowestCost = 0.0;
    Node bestNode = null;
    for (Node node : nodeList) {
      if (node.getType().equals(Node.NodeType.getEnum(nodeType))) {
        double score = euclideanScorer.computeCost(startNode, node);
        if (score < lowestCost || lowestCost < 0.1) {
          lowestCost = score;
          bestNode = node;
        }
      }
    }
    if (bestNode != null) {
      Path newPath = pathFindAlgorithm.pathfind(startNode, bestNode);
      draw(newPath);
      commandText.setText("See Details Below or Reset for New Path");
    } else {
      System.out.println("No " + nodeType + " could ne found on floor: " + startNode.getFloor());
      throw new InstanceNotFoundException();
    }
  }

  public void findElevator(MouseEvent mouseEvent) throws InstanceNotFoundException {
    switchToFloor(startNode.getFloor());
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum("ELEV"));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
  }

  public void findStairs(MouseEvent mouseEvent) throws InstanceNotFoundException {
    switchToFloor(startNode.getFloor());
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum("STAI"));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
  }

  public void findBathroom(MouseEvent mouseEvent) throws InstanceNotFoundException {
    switchToFloor(startNode.getFloor());
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum("REST"));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
  }

  public void choiceSelectStart() {
    if (startCombo.getValue() != null) {
      for (Node node : fullNodeList) {
        if (node.getLongName() == startCombo.getValue()) {
          if (startNode != null) {
            for (javafx.scene.Node component : currentPane.getChildren()) {
              if (component.getId().equals(startNode.getId())) {
                component.setStyle(
                    "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; "
                        + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
              }
            }
          }
          startNode = node;
          stairsBtn.setDisable(false);
          elevBtn.setDisable(false);
          bathBtn.setDisable(false);
          for (javafx.scene.Node component : currentPane.getChildren()) {
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

  public void choiceSelectEnd() {
    if (endCombo.getValue() != null) {
      for (Node node : fullNodeList) {
        if (node.getLongName() == endCombo.getValue()) {
          if (endNode != null) {
            for (javafx.scene.Node component : currentPane.getChildren()) {
              if (component.getId().equals(endNode.getId())) {
                component.setStyle(
                    "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; "
                        + "-fx-border-color: #000000; -fx-border-width: 1px"); // 800000
              }
            }
          }
          endNode = node;
          stairsBtn.setDisable(true);
          elevBtn.setDisable(true);
          bathBtn.setDisable(true);
          for (javafx.scene.Node component : currentPane.getChildren()) {
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

  public void pathButtonGo() {

    pathButton.setOnAction(
        actionEvent -> {
          Path path = null;
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
          scrollPaneFaulkner1.setVisible(true);
        });
    floor2Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner2;
          currentFloor = 2;
          setNodeList(2);
          // resetPane();
          setAllInvisible();
          scrollPaneFaulkner2.setVisible(true);
        });
    floor3Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner3;
          currentFloor = 3;
          setNodeList(3);
          // resetPane();
          setAllInvisible();
          scrollPaneFaulkner3.setVisible(true);
        });
    floor4Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner4;
          currentFloor = 4;
          setNodeList(4);
          // resetPane();
          setAllInvisible();
          scrollPaneFaulkner4.setVisible(true);
        });
    floor5Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner5;
          currentFloor = 5;
          setNodeList(5);
          // resetPane();
          setAllInvisible();
          scrollPaneFaulkner5.setVisible(true);
        });
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
    scrollPaneFaulkner1.setVisible(false);
    scrollPaneFaulkner2.setVisible(false);
    scrollPaneFaulkner3.setVisible(false);
    scrollPaneFaulkner4.setVisible(false);
    scrollPaneFaulkner5.setVisible(false);
  }

  public void switchToFloor(int floorNum) {
    if (floorNum == 1) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner1;
      currentFloor = 1;
      setNodeList(1);
      resetPane();
      setAllInvisible();
      scrollPaneFaulkner1.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName());
    }
    if (floorNum == 2) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner2;
      currentFloor = 2;
      setNodeList(2);
      resetPane();
      setAllInvisible();
      scrollPaneFaulkner2.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName());
    }
    if (floorNum == 3) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner3;
      currentFloor = 3;
      setNodeList(3);
      resetPane();
      setAllInvisible();
      scrollPaneFaulkner3.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName());
    }
    if (floorNum == 4) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner4;
      currentFloor = 4;
      setNodeList(4);
      resetPane();
      setAllInvisible();
      scrollPaneFaulkner4.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName());
    }
    if (floorNum == 5) {
      Node holdNode = startNode;
      currentPane = mapPaneFaulkner5;
      currentFloor = 5;
      setNodeList(5);
      resetPane();
      setAllInvisible();
      scrollPaneFaulkner5.setVisible(true);
      startNode = holdNode;
      startCombo.setValue(startNode.getLongName());
    }
  }

  public Node findChoiceStart() {
    Node returnNode = null;
    if (startCombo.getValue() != null) {
      for (Node node : fullNodeList) {
        if (node.getLongName() == startCombo.getValue()) {
          returnNode = node;
        }
      }
    }
    return returnNode;
  }

  public Node findChoiceEnd() {
    Node returnNode = null;
    if (endCombo.getValue() != null) {
      for (Node node : fullNodeList) {
        if (node.getLongName() == endCombo.getValue()) {
          returnNode = node;
        }
      }
    }
    return returnNode;
  }

  public void setComboBehavior() {
    startCombo.setOnAction(
        actionEvent -> {
          if (startCombo.getValue() != null) {
            choiceSelectStart();
            state = 1;
            commandText.setText("Select End Location or Building Feature");
            endCombo.setDisable(false);
            if (findChoiceStart().getFloor() != currentFloor) {
              switchToFloor(findChoiceStart().getFloor());
            }
          }
        });

    endCombo.setOnAction(
        actionEvent -> {
          if (endCombo.getValue() != null) {
            choiceSelectEnd();
            state = 2;
            commandText.setText("Select Find Path or Reset");
            pathButton.setDisable(false);
          }
        });
  }
}
