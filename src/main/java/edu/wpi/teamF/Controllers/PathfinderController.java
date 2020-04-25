package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.PathfindAlgorithm;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.SingleFloorAStar;
import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
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

  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private static EdgeFactory edgeFactory = EdgeFactory.getFactory();

  Node startNode = null;
  Node endNode = null;
  PathfindAlgorithm pathFindAlgorithm;
  EuclideanScorer euclideanScorer = new EuclideanScorer();

  public PathfinderController() {}

  public void draw(Path path) throws InstanceNotFoundException {

    List<Node> nodeList = path.getPath();

    double heightRatio = currentPane.getHeight() / MAP_HEIGHT;
    double widthRatio = currentPane.getWidth() / MAP_WIDTH;

    for (int i = 0; i < nodeList.size() - 1; i++) {
      int startX = (int) (nodeList.get(i).getXCoord() * widthRatio);
      int startY = (int) (nodeList.get(i).getYCoord() * heightRatio);
      int endX = (int) (nodeList.get(i + 1).getXCoord() * widthRatio);
      int endY = (int) (nodeList.get(i + 1).getYCoord() * heightRatio);
      Line line = new Line(startX, startY, endX, endY);
      line.setStroke(Color.RED);
      line.setStrokeWidth(2);
      currentPane.getChildren().add(line);
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
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000

    int xPos = (int) ((node.getXCoord() * widthRatioFaulkner5) - 6);
    int yPos = (int) ((node.getYCoord() * heightRatioFaulkner5) - 6);

    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    currentPane.getChildren().add(button);
    button.setOnAction(
        actionEvent -> {
          if (startNode == node && state == 1) { // Click again to de-select if start has been set
            startNode = null;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000
            state = 0;
            startCombo.setDisable(false);
          } else if (endNode == node) { // deselect if end has been set, return to 1
            endNode = null;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000
            state = 1;
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
            startCombo.setDisable(true);
            endCombo.setDisable(false);
          } else if (state == 1) { // select end if not set
            endNode = node;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00cc00; -fx-border-color: #000000; -fx-border-width: 1px"); // 00cc00
            commandText.setText("Select Find Path or Reset");
            state = 2;
            endCombo.setDisable(true);
            pathButton.setDisable(false);
          }
        });
  }

  public void resetPane() {
    currentPane.getChildren().clear();
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

    startCombo.setOnAction(
        actionEvent -> {
          if (startCombo.getValue() != null) {
            choiceSelectStart();
            state = 1;
            commandText.setText("Select End Location or Building Feature");
            endCombo.setDisable(false);
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

    for (Node node : nodeList) {
      if (!node.getType().equals(Node.NodeType.getEnum("HALL"))) {
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
    currentPane = mapPaneFaulkner5;
    setAllInvisible();
    masterPaneFaulkner5.setVisible(true);
    floorButtonsSet();

    for (Node node : nodeFactory.getAllNodes()) {
      node.setEdges(edgeFactory.getAllEdgesConnectedToNode(node.getId()));
      fullNodeList.add(node);
    }

    setNodeList(5);
    for (Node node : fullNodeList) {
      if (!node.getType().equals(Node.NodeType.getEnum("HALL"))) {
        startCombo.getItems().add(node.getLongName());
        endCombo.getItems().add(node.getLongName());
      }
    }

    pathFindAlgorithm = new SingleFloorAStar(fullNodeList);
    resetPane();
  }

  public void findElevator(MouseEvent mouseEvent) throws InstanceNotFoundException {
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum("ELEV"));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
  }

  public void findStairs(MouseEvent mouseEvent) throws InstanceNotFoundException {
    Path newPath = pathFindAlgorithm.pathfind(startNode, Node.NodeType.getEnum("STAI"));
    draw(newPath);
    commandText.setText("See Details Below or Reset for New Path");
  }

  public void findBathroom(MouseEvent mouseEvent) throws InstanceNotFoundException {
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
              if (component.getId() == startNode.getId()) {
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
            if (component.getId() == node.getId()) {
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
              if (component.getId() == endNode.getId()) {
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
            if (component.getId() == node.getId()) {
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
          setNodeList(1);
          resetPane();
          setAllInvisible();
          masterPaneFaulkner1.setVisible(true);
        });
    floor2Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner2;
          setNodeList(2);
          resetPane();
          setAllInvisible();
          masterPaneFaulkner2.setVisible(true);
        });
    floor3Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner3;
          setNodeList(3);
          resetPane();
          setAllInvisible();
          masterPaneFaulkner3.setVisible(true);
        });
    floor4Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner4;
          setNodeList(4);
          resetPane();
          setAllInvisible();
          masterPaneFaulkner4.setVisible(true);
        });
    floor5Button.setOnAction(
        actionEvent -> {
          currentPane = mapPaneFaulkner5;
          setNodeList(5);
          resetPane();
          setAllInvisible();
          masterPaneFaulkner5.setVisible(true);
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
    masterPaneFaulkner1.setVisible(false);
    masterPaneFaulkner2.setVisible(false);
    masterPaneFaulkner3.setVisible(false);
    masterPaneFaulkner4.setVisible(false);
    masterPaneFaulkner5.setVisible(false);
  }
}
