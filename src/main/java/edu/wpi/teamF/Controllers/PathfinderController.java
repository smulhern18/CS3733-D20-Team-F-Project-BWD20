package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.PathfindAlgorithm;
import edu.wpi.teamF.ModelClasses.PathfindAlgorithm.SingleFloorAStar;
import java.util.List;
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
        startNode = nodeFactory.read("FELEV00Z05");
    }

    public void draw() {
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

    //    Line line1 =
    ////        new Line(
    ////            (int) (675 * widthRatio),
    ////            (int) (1215 * heightRatio),
    ////            (int) (1330 * widthRatio),
    ////            (int) (1210 * heightRatio));
    ////    line1.setStroke(Color.RED);
    ////    mapPane.getChildren().add(line1);

  }
}
