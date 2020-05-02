package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.App;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.management.InstanceNotFoundException;
import lombok.SneakyThrows;

public class DataMapViewController implements Initializable {

  @FXML private AnchorPane mainMapPane;

  @FXML private ScrollPane scrollPaneFaulkner1;

  @FXML private StackPane masterPaneFaulkner1;

  @FXML private AnchorPane faulknerFloorPane;

  @FXML private JFXButton floor1Button;

  @FXML private JFXButton floor2Button;

  @FXML private JFXButton floor3Button;

  @FXML private JFXButton floor4Button;

  @FXML private JFXButton floor5Button;

  @FXML private AnchorPane pathSwitchFloorPane;

  @FXML private JFXButton edgePopupButton;

  @FXML private JFXButton nodePopupButton;

  @FXML private JFXComboBox<String> hospitalCombo;

  @FXML private AnchorPane mainFloorPane;

  @FXML private JFXButton groundButton;

  @FXML private JFXButton lower1Button;

  @FXML private JFXButton lower2Button;

  @FXML private AnchorPane dataMap;

  @FXML private JFXButton addNodeButton; // Adds the given node

  @FXML private GridPane nodeGridPane;

  @FXML private GridPane edgeGridPane;

  @FXML private AnchorPane mapPane1;

  @FXML private ImageView imageView1;

  @FXML private AnchorPane mapPane2;

  @FXML private ImageView imageView2;

  @FXML private AnchorPane mapPane3;

  @FXML private ImageView imageView3;

  @FXML private AnchorPane mapPane4;

  @FXML private ImageView imageView4;

  @FXML private AnchorPane mapPane5;

  @FXML private ImageView imageView5;

  @FXML private ChoiceBox typeInput;

  @FXML private Label nodeErrorLabel;

  @FXML private Label edgeErrorLabel;

  @FXML private ScrollPane imageScrollPane;

  @FXML private StackPane imageStackPane;

  private AnchorPane mapPane;

  private JFXButton nodeButton = null;
  private Line edgeLine = null;

  private Node node = null;
  private Edge edge = null;
  private boolean selectNode1 = false;
  private boolean selectNode2 = false;
  private Node node1 = null;
  private Node node2 = null;

  private double deltaX = 0;
  private double deltaY = 0;

  private UISetting uiSetting = new UISetting();

  private static final int MAP_HEIGHT = 1485;
  private static final int MAP_WIDTH = 2475; // height and width of the map
  private static final int PANE_HEIGHT = 550;
  private static final int PANE_WIDTH = 914; // height and width of the pane/image
  private double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
  private double widthRatio = (double) PANE_WIDTH / MAP_WIDTH; // ratio of pane to map
  private DatabaseManager databaseManager = DatabaseManager.getManager();

  public DataMapViewController() throws Exception {}

  private enum ModifyType {
    DELETE,
    ADD,
    EDIT
  };

  private ModifyType modifyType;

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    mapPane = mapPane1;

    for (Edge edge : databaseManager.getAllEdges()) {
      drawEdge(edge);
    } // for every edge that connects two nodes on the fifth floor, draw the edge on the map

    for (Node node : databaseManager.getAllNodes()) {
      drawNode(node);
    }

    ObservableList<String> hospitals = FXCollections.observableArrayList("Faulkner", "Main Campus");

    hospitalCombo.setItems(hospitals);
    hospitalCombo.setValue("Main Campus");

    // uiSetting.makeZoomable(imageScrollPane, imageStackPane, 1);
  }

  @FXML
  private void resetImages() {
    mapPane1.setVisible(false);
    imageView1.setVisible(false);
    mapPane2.setVisible(false);
    imageView2.setVisible(false);
    mapPane3.setVisible(false);
    imageView3.setVisible(false);
    mapPane3.setVisible(false);
    imageView3.setVisible(false);
    mapPane4.setVisible(false);
    imageView4.setVisible(false);
    mapPane5.setVisible(false);
    imageView4.setVisible(false);
  }

  @FXML
  private void addNodeLocation() throws IOException {
    double nodeDeltaX = dataMap.getLayoutX() - mapPane.getLayoutX();
    double nodeDeltaY = dataMap.getLayoutY() - mapPane.getLayoutY();
    outlineNode();
  }

  @FXML
  private void outlineNode() throws IOException {

    Stage stage = new Stage();
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setControllerFactory(
        controllerClass -> {
          System.out.println("here 2");
          if (controllerClass.equals(MapViewNodePopup.class)) {
            System.out.println("initialize with correct constructor");
            return new MapViewNodePopup(this);
          }
          return null;
        });
    System.out.println("Here");
    Parent root = fxmlLoader.load(App.class.getResource("Views/MapViewNodePopup.fxml"));
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(nodePopupButton.getScene().getWindow());
    stage.showAndWait();
  }

  @FXML
  private void outlineEdge() throws IOException {
    Stage stage = new Stage();
    Parent root = FXMLLoader.load(App.class.getResource("Views/MapViewEdgePopup.fxml"));
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(edgePopupButton.getScene().getWindow());
    stage.showAndWait();
  }

  @FXML
  private void drawEdge(Edge edge) {
    try {
      double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
      double widthRatio = (double) PANE_WIDTH / MAP_WIDTH;
      Node node1 = databaseManager.readNode(edge.getNode1());
      Node node2 = databaseManager.readNode(edge.getNode2());
      if (node1.getFloor()
          == node2.getFloor()) { // if the edge connects two nodes on the same floor
        int startX = (int) ((node1.getXCoord() * widthRatio) + 0.75);
        int startY =
            (int) ((node1.getYCoord() * heightRatio) + 0.75); // start values correspond to node 1
        int endX = (int) ((node2.getXCoord() * widthRatio) + 0.75);
        int endY =
            (int) ((node2.getYCoord() * heightRatio) + 0.75); // end values correspond to node 2
        Line line = new Line(startX, startY, endX, endY);
        line.setId(edge.getId()); // allows us to keep track of what line is what edge
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(1.5);
        line.setOpacity(0.7);
        line.setOnMouseClicked(
            mouseEvent -> { // when a user clicks on a line:
              edgeLine = line;
              this.edge = edge;
            });

        switch (node1.getFloor()) {
          case 1:
            mapPane1.getChildren().add(line);
            break;
          case 2:
            mapPane2.getChildren().add(line);
            break;
          case 3:
            mapPane3.getChildren().add(line);
            break;
          case 4:
            mapPane4.getChildren().add(line);
            break;
          case 5:
            mapPane5.getChildren().add(line);
            break;
        }
      }
    } catch (InstanceNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void drawNode(Node node) { // draws the given node on the map

    double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT;
    double widthRatio = (double) PANE_WIDTH / MAP_WIDTH;

    JFXButton button = new JFXButton();
    int buttonSize = 6; // this can be adjusted if we feel like the size is too small or large
    button.setMinSize(buttonSize, buttonSize);
    button.setMaxSize(buttonSize, buttonSize);
    button.setPrefSize(buttonSize, buttonSize); // the button size will not vary
    button.setStyle(
        "-fx-background-radius: 6px; -fx-border-radius: 6px; -fx-background-color: #00008B; -fx-border-color: #000000; -fx-border-width: 1px; -fx-opacity: 0.7");
    double xPos = (node.getXCoord() * widthRatio - buttonSize / 2.0);
    double yPos = (node.getYCoord() * heightRatio - buttonSize / 2.0);
    button.setLayoutX(xPos);
    button.setLayoutY(yPos);
    button.setOnAction(
        action -> {
          nodeButton = button; // sets classes button variable to the selected button
          this.node = node;
          System.out.println(node.getId());
          if (selectNode1 || selectNode2) {
            // addEdgeButton.setDisable(false);
          } else {
            nodeButton.setOpacity(1);
          }
        });
    System.out.println("Floor: " + node.getFloor());
    //    switch (node.getFloor()) {
    //      case 1:
    //        mapPane1.getChildren().add(button);
    //        break;
    //      case 2:
    //        mapPane2.getChildren().add(button);
    //        break;
    //      case 3:
    //        mapPane3.getChildren().add(button);
    //        break;
    //      case 4:
    //        mapPane4.getChildren().add(button);
    //        break;
    //      case 5:
    //        mapPane5.getChildren().add(button);
    //        break;
    //    }
  }

  @FXML
  private void setNodeDraggable(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          deltaX = button.getLayoutX() - mouseEvent.getSceneX();
          deltaY = button.getLayoutY() - mouseEvent.getSceneY();
        });
    button.setOnMouseDragged(
        mouseEvent -> {
          button.setLayoutX(mouseEvent.getSceneX() + deltaX);
          button.setLayoutY(mouseEvent.getSceneY() + deltaY);
        });
  }
}
