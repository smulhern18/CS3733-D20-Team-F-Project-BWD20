package edu.wpi.teamF.Controllers.MapEditor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MapView implements Initializable {
    private static final int BUTTON_SIZE = 8;
    private static final double LINE_WIDTH = 1.5;
    private static final int MAP_HEIGHT_FAULK = 1485;
    private static final int MAP_WIDTH_FAULK = 2475; // height and width of the faulkner hospital map
    private static final int MAP_HEIGHT_MAIN = 3400;
    private static final int MAP_WIDTH_MAIN = 5000; // height and width of the main hospital map
    private static final int PANE_HEIGHT = 585;
    private static final int PANE_WIDTH = 974; // height a

    @FXML
    private AnchorPane mainFloorPane;

    @FXML private AnchorPane faulknerFloorPane;

    @FXML private JFXButton faulknerFloor1Button;

    @FXML private JFXButton faulknerFloor2Button;

    @FXML private JFXButton faulknerFloor3Button;

    @FXML private JFXButton faulknerFloor4Button;

    @FXML private JFXButton faulknerFloor5Button;

    @FXML private JFXButton groundButton;

    @FXML private JFXButton lower1Button;

    @FXML private JFXButton lower2Button;

    @FXML private JFXButton floor1Button;

    @FXML private JFXButton floor2Button;

    @FXML private JFXButton floor3Button;

    @FXML private JFXComboBox<String> hospitalCombo;

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

    @FXML private JFXButton nodeDisplayButton;

    @FXML private JFXButton edgeDisplayButton;

    @FXML private JFXButton cancelButton;

    @FXML private AnchorPane nodeAnchor;

    @FXML private AnchorPane edgeAnchor;

    @FXML private JFXTextField shortNameInput;

    @FXML private JFXTextField longNameInput;

    @FXML private JFXComboBox<String> typeInput;

    @FXML private JFXTextField xCoorInput;

    @FXML private JFXTextField yCoorInput;

    @FXML private JFXComboBox<String> hospitalInput;

    @FXML private JFXComboBox<String> floorInput;

    @FXML private JFXButton addNodeButton;

    @FXML private JFXButton deleteNodeButton;

    @FXML private JFXButton modifyNodeButton;

    @FXML private JFXButton node1Button;

    @FXML private JFXButton node2Button;

    @FXML private JFXButton addEdgeButton;

    @FXML private JFXButton deleteEdgeButton;

    @FXML private JFXButton modifyEdgeButton;

    @FXML private Label nodeErrorLabel;

    private DatabaseManager databaseManager = DatabaseManager.getManager();
    private MapEditorController mapEditorController;
    Map<String,JFXButton> buttonMap;
    Map<String,Line> lineMap;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         mapEditorController = new MapEditorController(this);

        for (Node node: databaseManager.getAllNodes()) {
            drawNode(node);
        }
        for (Edge edge: databaseManager.getAllEdges()) {
            drawEdge(edge);
        }
        UISetting uiSetting = new UISetting();
        uiSetting.makeZoomable(imageScrollPane, imageStackPane, 1);
        initializeComboBox();
        node1Button.setId("Node1");
        mapEditorController.setEdgeSelectionButtonHandler(node1Button);
        node2Button.setId("Node2");
        mapEditorController.setEdgeSelectionButtonHandler(node2Button);

        initializeAnchorIDs();

    }

    private void initializeAnchorIDs() {
        mapPaneFaulkner1.setId("1");
        mapPaneFaulkner2.setId("2");
        mapPaneFaulkner3.setId("3");
        mapPaneFaulkner4.setId("4");
        mapPaneFaulkner5.setId("5");
        mapPaneMain1.setId("F1");
        mapPaneMain2.setId("F2");
        mapPaneMain3.setId("F3");
        mapPaneMainG.setId("G");
        mapPaneMainL1.setId("L1");
        mapPaneMainL1.setId("L2");

    }

    private void drawNode(Node node) throws Exception {
        JFXButton button = new JFXButton();
        button.setId(node.getId());
        button.setPrefSize(BUTTON_SIZE,BUTTON_SIZE);
        button.setStyle(
                "-fx-background-radius: " + BUTTON_SIZE +"px; -fx-border-radius: 8px; -fx-background-color: #99D9EA; -fx-border-color: #000000; -fx-border-width: 1px; -fx-opacity: 0.7");
        button.setLayoutX(calculateXCoord(node.getXCoord(),node.getBuilding()) - BUTTON_SIZE / 2.0);
        button.setLayoutY(calculateYCoord(node.getYCoord(),node.getBuilding()) - BUTTON_SIZE / 2.0);
        mapEditorController.setNodeEventHandlers(button);
        getFloorPane(node.getFloor()).getChildren().add(button);
        buttonMap.put(button.getId(),button);
    }



    private void drawEdge(Edge edge) throws Exception {
        Node node1 = databaseManager.readNode(edge.getNode1());
        Node node2 = databaseManager.readNode(edge.getNode2());
        if (node1.getFloor().equals(node2.getFloor())) {
            double startX = calculateXCoord(node1.getXCoord(), node1.getBuilding()) + LINE_WIDTH / 2;
            double startY = calculateYCoord(node1.getYCoord(), node1.getBuilding()) + LINE_WIDTH / 2;
            double endX = calculateXCoord(node2.getXCoord(), node2.getBuilding()) + LINE_WIDTH / 2;
            double endY = calculateYCoord(node2.getYCoord(), node2.getBuilding()) + LINE_WIDTH / 2;
            Line line = new Line(startX, startY, endX, endY);
            line.setId(edge.getId());
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(LINE_WIDTH);
            line.setOpacity(0.7);
            getFloorPane(node1.getFloor()).getChildren().addAll(line);
            lineMap.put(line.getId(),line);
            mapEditorController.setEdgeEventHandlers(line);
        }
    }

    public void highlightEdge(String edgeID, String node1ID, String node2ID) throws Exception {
        setButtonColor(buttonMap.get(node1ID), "#012D5A", 1);
        setButtonColor(buttonMap.get(node2ID), "#a40000", 1);
        Line line = lineMap.get(edgeID);
        Node node1 = databaseManager.readNode(node1ID);
        Node node2 = databaseManager.readNode(node2ID);
        if (node1.getFloor().equals(node2.getFloor())) {
            line.setVisible(true);
            double startX = calculateXCoord(node1.getXCoord(), node1.getBuilding()) + LINE_WIDTH / 2;
            double startY = calculateYCoord(node1.getYCoord(), node1.getBuilding()) + LINE_WIDTH / 2;
            double endX = calculateXCoord(node2.getXCoord(), node2.getBuilding()) + LINE_WIDTH / 2;
            double endY = calculateYCoord(node2.getYCoord(), node2.getBuilding()) + LINE_WIDTH / 2;
            line.setStartX(startX);
            line.setStartY(startY);
            line.setEndX(endX);
            line.setEndY(endY);
            if (!node1.getFloor().equals(line.getParent().getId())) {
                getFloorPane(line.getParent().getId()).getChildren().remove(line);
                getFloorPane(node1.getFloor()).getChildren().add(line);
            }
        } else {
            line.setVisible(false);
        }

    }

    private void setButtonColor(JFXButton button, String color, double opacity){
        button.setStyle("-fx-background-radius: " + BUTTON_SIZE +"px; -fx-border-radius: "+ BUTTON_SIZE +"px; -fx-background-color: "+ color +"; -fx-border-color: #000000; -fx-border-width: 1px; -fx-opacity: " + opacity);
    }



    private double calculateXCoord(double x,String building) {
        if ("Faulkner".equals(building)) {
            return x * (double) PANE_WIDTH / MAP_WIDTH_FAULK;
        } else {
            return x * (double) PANE_WIDTH / MAP_WIDTH_MAIN ;

        }
    }
    private double calculateYCoord(double y,String building) {
        if ("Faulkner".equals(building)) {
            return y * (double) PANE_HEIGHT / MAP_HEIGHT_FAULK;
        } else {
            return y * (double) PANE_HEIGHT / MAP_HEIGHT_MAIN;
        }
    }

    private void initializeComboBox() {
        typeInput
                .getItems()
                .addAll(
                        "CONF", "DEPT", "EXIT", "HALL", "INFO", "LABS", "REST", "RETL", "SERV", "STAF", "STAI");
        hospitalInput
                .getItems()
                .addAll("Faulkner", "Tower", "BTM", "45 Francis", "15 Francis", "Shapiro");
        ObservableList<String> hospitals = FXCollections.observableArrayList("Faulkner", "Main Campus");
        hospitalCombo.setItems(hospitals);
    }

    private AnchorPane getFloorPane(String floor) throws Exception {
        switch(floor) {
            case "1":
                return mapPaneFaulkner1;
            case "2":
                return mapPaneFaulkner2;
            case "3":
                return mapPaneFaulkner3;
            case "4":
                return mapPaneFaulkner4;
            case "5":
                return mapPaneFaulkner5;
            case "F1":
                return mapPaneMain1;
            case "F2":
                return mapPaneMain2;
            case "F3":
                return mapPaneMain3;
            case "G":
                return mapPaneMainG;
            case "L1":
                return mapPaneMainL1;
            case "L2":
                return mapPaneMainL2;
            default:
                throw new Exception("NULL FLOOR in getFloorPane");
        }
    }

    private ImageView getImageView(String floor) throws Exception {
        switch(floor) {
            case "1":
                return imageViewFaulkner1;
            case "2":
                return imageViewFaulkner2;
            case "3":
                return imageViewFaulkner3;
            case "4":
                return imageViewFaulkner4;
            case "5":
                return imageViewFaulkner5;
            case "F1":
                return imageViewMain1;
            case "F2":
                return imageViewMain2;
            case "F3":
                return imageViewMain3;
            case "G":
                return imageViewMainG;
            case "L1":
                return imageViewMainL1;
            case "L2":
                return imageViewMainL2;
            default:
                throw new Exception("NULL FLOOR IN getImageView");
        }
    }




}
