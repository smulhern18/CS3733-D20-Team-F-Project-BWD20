package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.PathfindAlgorithm;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.SingleFloorAStar;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javax.management.InstanceNotFoundException;

public class PathfinderController implements Initializable {

  public static int MAP_HEIGHT = 1485;
  public static int MAP_WIDTH = 2475;
  public AnchorPane mapPane;
  public StackPane masterPane;

  private NodeFactory nodeFactory = NodeFactory.getFactory();

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

    int xPos = (int) (node.getXCoord() * widthRatio);
    int yPos = (int) (node.getYCoord() * heightRatio);

    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    mapPane.getChildren().add(button);
    button.setOnAction(
        actionEvent -> {
          // button.setStyle();
          if (startNode == null) {
            startNode = node;
          } else if (endNode == null) {
            endNode = node;
            try {
              draw();
            } catch (InstanceNotFoundException e) {
              e.printStackTrace();
            }
          }
        });
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    mapPane.getChildren().clear();
    List<Node> someNodes = nodeFactory.getAllNodes();

    for (Node node : nodeFactory.getAllNodes()) {
      if (node.getId().charAt(0) == 'X') {

        placeButton(node);
      }
    }
  }
}
