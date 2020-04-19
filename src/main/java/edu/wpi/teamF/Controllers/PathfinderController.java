package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.PathfindAlgorithm;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.SingleFloorAStar;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class PathfinderController {

  public static int MAP_HEIGHT = 1485;
  public static int MAP_WIDTH = 2475;
  public AnchorPane mapPane;
  private NodeFactory nodeFactory;

  Node startNode = null;
  Node endNode = null;
  PathfindAlgorithm pathFindAlgorithm = new SingleFloorAStar();

  public PathfinderController() throws Exception {
    // startNode = nodeFactory.read("FELEV00Z05");
  }

  public void draw() {
    // Path path = pathFindAlgorithm.pathfind(startNode, endNode);
    List<Node> nodeList = new ArrayList<Node>();
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

  public void placeButton() {
    double heightRatio = mapPane.getHeight() / MAP_HEIGHT;
    double widthRatio = mapPane.getWidth() / MAP_WIDTH;

    Button button = new Button();
    button.setMinSize(12, 12);
    button.setMaxSize(12, 12);
    button.setPrefSize(12, 12);
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px");
    int xPos = (int) (955 * widthRatio);
    int yPos = (int) (1215 * heightRatio);
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    mapPane.getChildren().add(button);
  }
}
