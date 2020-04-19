package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DataMapViewController implements Initializable {

  private static class NodeEventHandler implements EventHandler<ActionEvent> {

    private Node node;
    NodeEventHandler (Node node) {
      this.node = node;
    }

    @Override
    public void handle(ActionEvent event) {


    }
  }

  public AnchorPane mapPane;
  private static final int MAP_HEIGHT = 1485;
  private static final int MAP_WIDTH = 2475;
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();

  public DataMapViewController() {
    //    List<Node> nodes = nodeFactory.getAllNodes();
    //    List<Edge> edges = edgeFactory.getAllEdges();

    //    for (Node node : nodes) {
    //      drawNode(node);
    //    }
  }

  private void drawNode(Node node) {
    double heightRatio = mapPane.getHeight() / MAP_HEIGHT;
    double widthRatio = mapPane.getWidth() / MAP_WIDTH;

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
    button.setOnAction();
    mapPane.getChildren().add(button);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    drawNode(null);
    System.out.println("Test");
  }
}
