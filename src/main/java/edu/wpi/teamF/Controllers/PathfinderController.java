package edu.wpi.teamF.Controllers;

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
import javafx.scene.control.Button;
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
  public AnchorPane mapPane;
  public StackPane masterPane;
  public List<Node> nodeList;
  public Button stairsBtn;
  public Button elevBtn;
  public Button bathBtn;
  public Text commandText;

  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private static EdgeFactory edgeFactory = EdgeFactory.getFactory();

  Node startNode = null;
  Node endNode = null;
  PathfindAlgorithm pathFindAlgorithm;
  EuclideanScorer euclideanScorer = new EuclideanScorer();

  public PathfinderController() {

    // startNode = nodeFactory.read("FELEV00Z05");
  }

  public void draw(Path path) throws InstanceNotFoundException {

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
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #3281a8; -fx-border-color: #000000; -fx-border-width: 1px"); // ff0000

    int xPos = (int) ((node.getXCoord() * widthRatio) - 6);
    int yPos = (int) ((node.getYCoord() * heightRatio) - 6);

    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    mapPane.getChildren().add(button);
    button.setOnAction(
        actionEvent -> {
          if (startNode == null) {
            startNode = node;
            stairsBtn.setDisable(false);
            elevBtn.setDisable(false);
            bathBtn.setDisable(false);
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 1px"); // 800000
            commandText.setText("Select End Location or Building Feature");
          } else if (endNode == null) {
            endNode = node;
            button.setStyle(
                "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00cc00; -fx-border-color: #000000; -fx-border-width: 1px"); // 00cc00
            Path path = null;
            elevBtn.setDisable(true);
            bathBtn.setDisable(true);
            stairsBtn.setDisable(true);
            try {
              path = pathFindAlgorithm.pathfind(startNode, endNode);
            } catch (InstanceNotFoundException e) {
              e.printStackTrace();
            }
            try {
              draw(path);
            } catch (InstanceNotFoundException e) {
              e.printStackTrace();
            }
            commandText.setText("See Details Below or Reset for New Path");
          }
        });
  }

  public void resetPane() {
    mapPane.getChildren().clear();
    startNode = null;
    endNode = null;

    stairsBtn.setDisable(true);
    elevBtn.setDisable(true);
    bathBtn.setDisable(true);
    commandText.setText("Select Starting Location");

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
        // System.out.println(node.getId() + " - " + node.getNeighborNodes());
      }
    }
    pathFindAlgorithm = new SingleFloorAStar(nodeList);
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
}
