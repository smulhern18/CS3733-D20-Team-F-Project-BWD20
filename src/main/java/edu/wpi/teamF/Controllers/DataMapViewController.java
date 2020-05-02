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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

  @FXML private JFXButton faulknerFloor1Button;

  @FXML private JFXButton faulknerFloor2Button;

  @FXML private JFXButton faulknerFloor3Button;

  @FXML private JFXButton faulknerFloor4Button;

  @FXML private JFXButton faulknerFloor5Button;

  @FXML private AnchorPane pathSwitchFloorPane;

  @FXML private JFXButton edgePopupButton;

  @FXML private JFXButton nodePopupButton;

  @FXML private JFXComboBox<String> hospitalCombo;

  @FXML private AnchorPane mainFloorPane;

  @FXML private JFXButton groundButton;

  @FXML private JFXButton lower1Button;

  @FXML private JFXButton lower2Button;

  @FXML private JFXButton floor1Button;

  @FXML private JFXButton floor2Button;

  @FXML private JFXButton floor3Button;

  @FXML private AnchorPane dataMap;

  @FXML private JFXButton addNodeButton; // Adds the given node

  @FXML private ScrollPane imageScrollPane;

  @FXML private StackPane imageStackPane;

  @FXML private ImageView imageViewFaulkner1;

  @FXML private AnchorPane mapPaneFaulkner1;

  @FXML private ImageView imageViewFaulkner2;

  @FXML private AnchorPane mapPaneFaulkner2;

  @FXML private ImageView imageViewFaulkner3;

  @FXML private AnchorPane mapPaneFaulkner3;

  @FXML private ImageView imageViewFaulkner4;

  @FXML private AnchorPane mapPaneFaulkner4;

  @FXML private ImageView imageViewFaulkner5;

  @FXML private AnchorPane mapPaneFaulkner5;

  @FXML private ImageView imageViewMain1;

  @FXML private ImageView imageViewMain2;

  @FXML private ImageView imageViewMain3;

  @FXML private ImageView imageViewMainG;

  @FXML private ImageView imageViewMainL1;

  @FXML private ImageView imageViewMainL2;

  @FXML private AnchorPane mapPaneMain1;

  @FXML private AnchorPane mapPaneMain2;

  @FXML private AnchorPane mapPaneMain3;

  @FXML private AnchorPane mapPaneMainG;

  @FXML private AnchorPane mapPaneMainL1;

  @FXML private AnchorPane mapPaneMainL2;

  private AnchorPane mapPane;
  private ImageView imageView;

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

  private static final int MAP_HEIGHT_FAULK = 1485;
  private static final int MAP_WIDTH_FAULK = 2475; // height and width of the faulkner hospital map
  private static final int MAP_HEIGHT_MAIN = 3400;
  private static final int MAP_WIDTH_MAIN = 5000; // height and width of the main hospital map
  private static final int PANE_HEIGHT = 550;
  private static final int PANE_WIDTH = 914; // height and width of the pane/image
  private double faulkHeightRatio = (double) PANE_HEIGHT / MAP_HEIGHT_FAULK;
  private double faulkWidthRatio = (double) PANE_WIDTH / MAP_WIDTH_FAULK; // ratio of pane to map
  private double mainHeightRatio = (double) PANE_HEIGHT / MAP_HEIGHT_MAIN;
  private double mainWidthRatio = (double) PANE_WIDTH / MAP_WIDTH_MAIN; // ratio of pane to map
  private DatabaseManager databaseManager = DatabaseManager.getManager();

  private enum Hospital {
    MAIN,
    FAULK
  };

  private Hospital hospital;

  public DataMapViewController() throws Exception {}

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    mapPane = mapPaneMain1;
    imageView = imageViewMain1;
    hospital = Hospital.MAIN;

    for (Edge edge : databaseManager.getAllEdges()) {
      drawEdge(edge);
    } // for every edge that connects two nodes on the fifth floor, draw the edge on the map

    for (Node node : databaseManager.getAllNodes()) {
      drawNode(node);
    }

    ObservableList<String> hospitals = FXCollections.observableArrayList("Faulkner", "Main Campus");

    hospitalCombo.setItems(hospitals);
    hospitalCombo.setValue("Main Campus");

    faulknerFloor1Button.setId("ff1");
    faulknerFloor2Button.setId("ff2");
    faulknerFloor3Button.setId("ff3");
    faulknerFloor4Button.setId("ff4");
    faulknerFloor5Button.setId("ff5");

    groundButton.setId("ground");
    lower1Button.setId("l1");
    lower2Button.setId("l2");
    floor1Button.setId("f1");
    floor2Button.setId("f2");
    floor3Button.setId("f3");
  }

  @FXML
  void setFloorButtons(ActionEvent event) {
    if (hospitalCombo.getValue() == "Main Campus") {
      faulknerFloorPane.setVisible(false);
      mainFloorPane.setVisible(true);
    } else {
      mainFloorPane.setVisible(false);
      faulknerFloorPane.setVisible(true);
    }
  }

  @FXML
  void selectFloor(ActionEvent event) {
    JFXButton btn = (JFXButton) event.getSource();

    switch (btn.getId()) {
      case "ff1":
        mapPane = mapPaneFaulkner1;
        imageView = imageViewFaulkner1;
        hospital = Hospital.FAULK;
        break;
      case "ff2":
        mapPane = mapPaneFaulkner2;
        imageView = imageViewFaulkner2;
        hospital = Hospital.FAULK;
        break;
      case "ff3":
        mapPane = mapPaneFaulkner3;
        imageView = imageViewFaulkner3;
        hospital = Hospital.FAULK;
        break;
      case "ff4":
        mapPane = mapPaneFaulkner4;
        imageView = imageViewFaulkner4;
        hospital = Hospital.FAULK;
        break;
      case "ff5":
        mapPane = mapPaneFaulkner5;
        imageView = imageViewFaulkner5;
        hospital = Hospital.FAULK;
        break;
      case "ground":
        mapPane = mapPaneMainG;
        imageView = imageViewMainG;
        hospital = Hospital.MAIN;
        break;
      case "l1":
        mapPane = mapPaneMainL1;
        imageView = imageViewMainL1;
        hospital = Hospital.MAIN;
        break;
      case "l2":
        mapPane = mapPaneMainL2;
        imageView = imageViewMainL2;
        hospital = Hospital.MAIN;
        break;
      case "f1":
        mapPane = mapPaneMain1;
        imageView = imageViewMain1;
        hospital = Hospital.MAIN;
        break;
      case "f2":
        mapPane = mapPaneMain2;
        imageView = imageViewMain2;
        hospital = Hospital.MAIN;
        break;
      case "f3":
        mapPane = mapPaneMain3;
        imageView = imageViewMain3;
        hospital = Hospital.MAIN;
        break;
    }

    List<AnchorPane> mapPanes = new ArrayList<AnchorPane>();
    mapPanes.addAll(
        Arrays.asList(
            mapPaneFaulkner1,
            mapPaneFaulkner2,
            mapPaneFaulkner3,
            mapPaneFaulkner4,
            mapPaneFaulkner5,
            mapPaneMainG,
            mapPaneMainL1,
            mapPaneMainL2,
            mapPaneMain1,
            mapPaneMain2,
            mapPaneMain3));

    List<ImageView> imageViews = new ArrayList<ImageView>();
    imageViews.addAll(
        Arrays.asList(
            imageViewFaulkner1,
            imageViewFaulkner2,
            imageViewFaulkner3,
            imageViewFaulkner4,
            imageViewFaulkner5,
            imageViewMainG,
            imageViewMainL1,
            imageViewMainL2,
            imageViewMain1,
            imageViewMain2,
            imageViewMain3));

    for (int i = 0; i < mapPanes.size(); i++) {
      if (!mapPanes.get(i).equals(mapPane)) {
        mapPanes.get(i).setVisible(false);
        imageViews.get(i).setVisible(false);
      } else {
        System.out.println("Equal to here");
        mapPanes.get(i).setVisible(true);
        imageViews.get(i).setVisible(true);
      }
    }
  }

  @FXML
  private void addNodeLocation() throws IOException {
    double nodeDeltaX = dataMap.getLayoutX() - mapPane.getLayoutX();
    double nodeDeltaY = dataMap.getLayoutY() - mapPane.getLayoutY();
    nodePopup();
  }

  @FXML
  private void nodePopup() throws IOException {

    Stage stage = new Stage();
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setControllerFactory(
        controllerClass -> {
          if (controllerClass.equals(MapViewNodePopup.class)) {
            System.out.println("initialize with correct constructor");
            return new MapViewNodePopup(this);
          }
          return null;
        });
    Parent root = fxmlLoader.load(App.class.getResource("Views/MapViewNodePopup.fxml"));
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(nodePopupButton.getScene().getWindow());
    stage.showAndWait();
  }

  @FXML
  private void edgePopup() throws IOException {
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
      double heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT_FAULK;
      double widthRatio = (double) PANE_WIDTH / MAP_WIDTH_FAULK;
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

        //        switch (node1.getFloor()) {
        //          case 1:
        //            mapPane1.getChildren().add(line);
        //            break;
        //          case 2:
        //            mapPane2.getChildren().add(line);
        //            break;
        //          case 3:
        //            mapPane3.getChildren().add(line);
        //            break;
        //          case 4:
        //            mapPane4.getChildren().add(line);
        //            break;
        //          case 5:
        //            mapPane5.getChildren().add(line);
        //            break;
        //        }
      }
    } catch (InstanceNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void drawNode(Node node) { // draws the given node on the map

    double heightRatio;
    double widthRatio;
    if(hospital == Hospital.FAULK){
      heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT_FAULK;
      widthRatio = (double) PANE_WIDTH / MAP_WIDTH_FAULK;
    } else{
      heightRatio = (double) PANE_HEIGHT / MAP_HEIGHT_MAIN;
      widthRatio = (double) PANE_WIDTH / MAP_WIDTH_MAIN;
    }

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

    if(node.getBuilding().equals("Faulkner")){
      switch (node.getFloor()) {
        case "1":
          mapPaneFaulkner1.getChildren().add(button);
          break;
        case "2":
          mapPaneFaulkner2.getChildren().add(button);
          break;
        case "3":
          mapPaneFaulkner3.getChildren().add(button);
          break;
        case "4":
          mapPaneFaulkner4.getChildren().add(button);
          break;
        case "5":
          mapPaneFaulkner5.getChildren().add(button);
          break;
      }
    } else{
      switch (node.getFloor()){
        case "Ground":
          mapPaneMainG.getChildren().add(button);
          break;
        case "L2":
          mapPaneMainL2.getChildren().add(button);
          break;
        case "L1":
          mapPaneMainL1.getChildren().add(button);
          break;
        case "F1":
          mapPaneMain1.getChildren().add(button);
          break;
        case "F2":
          mapPaneMain2.getChildren().add(button);
          break;
        case "F3":
          mapPaneMain3.getChildren().add(button);
          break;
      }
    }

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
