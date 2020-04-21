package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.PathfindAlgorithm;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.SingleFloorAStar;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javax.management.InstanceNotFoundException;
import lombok.SneakyThrows;

public class PathfinderController implements Initializable {

  public static int MAP_HEIGHT = 1485;
  public static int MAP_WIDTH = 2475;
  public AnchorPane mapPane;
  public StackPane masterPane;
  public List<Node> nodeList;

  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private static EdgeFactory edgeFactory = EdgeFactory.getFactory();

  Node startNode = null;
  Node endNode = null;
  PathfindAlgorithm pathFindAlgorithm = new SingleFloorAStar();

  public PathfinderController() {

    // startNode = nodeFactory.read("FELEV00Z05");
  }

  public void draw() throws InstanceNotFoundException {

    Path path = pathFindAlgorithm.pathfind(startNode, endNode);
    List<Node> nodeList = path.getPath();

    double heightRatio = mapPane.getHeight() / MAP_HEIGHT;
    double widthRatio = mapPane.getWidth() / MAP_WIDTH;

    for (int i = 0; i < nodeList.size() - 1; i++) {
      int startX = (int) (nodeList.get(i).getXCoord() * widthRatio);
      int startY = (int) (nodeList.get(i).getYCoord() * heightRatio);
      int endX = (int) (nodeList.get(i + 1).getXCoord() * widthRatio);
      int endY = (int) (nodeList.get(i + 1).getYCoord() * heightRatio);
      Line line = new Line(startX, startY, endX, endY);
      line.setStroke(Color.RED);
      line.setStrokeWidth(2);
      mapPane.getChildren().add(line);
    }
  }

  public void placeButton(Node node) {

    double heightRatio = (double) mapPane.getPrefHeight() / MAP_HEIGHT;
    double widthRatio = (double) mapPane.getPrefWidth() / MAP_WIDTH;

    Button button = new Button();
    button.setMinSize(12, 12);
    button.setMaxSize(12, 12);
    button.setPrefSize(12, 12);
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px");

    int xPos = (int) ((node.getXCoord() * widthRatio) - 6);
    int yPos = (int) ((node.getYCoord() * heightRatio) - 6);

    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    mapPane.getChildren().add(button);
    button.setOnAction(
        actionEvent -> {
          if (startNode == null) {
            startNode = node;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #800000; -fx-border-color: #000000; -fx-border-width: 1px");
          } else if (endNode == null) {
            endNode = node;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00cc00; -fx-border-color: #000000; -fx-border-width: 1px");
            try {
              draw();
            } catch (InstanceNotFoundException e) {
              e.printStackTrace();
            }
          }
        });
  }

  public void resetPane() {
    mapPane.getChildren().clear();
    startNode = null;
    endNode = null;

    for (Node node : nodeList) {
      if (node.getId().charAt(0) == 'X' && node.getId().charAt(node.getId().length() - 1) == '5') {
        placeButton(node);
      }
    }
  }

  @SneakyThrows
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    nodeList = new ArrayList<>();
    for (Node node : nodeFactory.getAllNodes()) {
      if (node.getId().charAt(node.getId().length() - 1) == '5') {
        nodeList.add(node);
        node.setEdges(edgeFactory.getAllEdgesConnectedToNode(node.getId()));
        System.out.println(node.getId() + " - " + node.getNeighborNodes());
      }
    }
    resetPane();
  }
}
