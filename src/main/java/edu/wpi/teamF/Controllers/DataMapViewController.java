package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class DataMapViewController implements Initializable {
  @FXML
  private AnchorPane mapPane;

  @FXML
  private StackPane modifyPane;

  @FXML
  private JFXButton deleteButton;

  @FXML
  private JFXButton modifyButton;

  @FXML
  private JFXTextField nodeIDInput;

  @FXML
  private JFXTextField yCoorInput;

  @FXML
  private JFXTextField xCoorInput;

  @FXML
  private JFXTextField typeInput;

  @FXML
  private JFXTextField shortNameInput;

  @FXML
  private JFXTextField longNameInput;

  @FXML
  private JFXTextField buildingInput;

  @FXML
  private JFXTextField floorInput;


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
    int xPos = (int) (200 * widthRatio);
    int yPos = (int) (200 * heightRatio);
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    button.setOnAction(
        action -> {
          modifyPane.setVisible(true);
        });
    mapPane.getChildren().add(button);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    drawNode(null);
    System.out.println("Test");
  }
}
