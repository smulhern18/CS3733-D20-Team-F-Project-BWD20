package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class DataMapViewController implements Initializable {
  @FXML private AnchorPane mapPane;

  @FXML private StackPane modifyPane;

  @FXML private JFXButton addButton;

  @FXML private JFXButton deleteButton;

  @FXML private JFXButton modifyButton;

  @FXML private JFXTextField nodeIDInput;

  @FXML private JFXTextField yCoorInput;

  @FXML private JFXTextField xCoorInput;

  @FXML private JFXTextField typeInput;

  @FXML private JFXTextField shortNameInput;

  @FXML private JFXTextField longNameInput;

  @FXML private JFXTextField buildingInput;

  @FXML private JFXTextField floorInput;

  @FXML private JFXButton addButtonWindow;

  JFXButton nodeButton = null;

  Node node = null;

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

    JFXButton button = new JFXButton();
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
          nodeButton = button;
          this.node = node;
          modifyPane.setVisible(true);
          modifyButton.setDisable(true);
          displayData();
        });
    mapPane.getChildren().add(button);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    drawNode(null);
    System.out.println("Test");
  }

  private enum ModifyType {
    DELETE,
    ADD,
    EDIT
  };

  private ModifyType modifyType;

  @FXML
  private void cancel() {
    reset();
    modifyPane.setVisible(false);
  }

  @FXML
  private void deleteData() {
    nodeFactory.delete(node.getId()); // in the database
    mapPane.getChildren().remove(nodeButton); // on the map
    reset();
  }

  @FXML
  private void addWindow() throws ValidationException {
    modifyPane.setVisible(true);
    modifyButton.setVisible(false);
    deleteButton.setVisible(false);
    addButtonWindow.setVisible(true);
    addButtonWindow.setDisable(true);
  }

  @FXML
  private void addData() throws ValidationException {
    String ID = nodeIDInput.getText();
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = buildingInput.getText();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getText());
    short floorNumber = Short.parseShort(floorInput.getText());

    node.setId(ID);
    node.setXCoord(xCoordinate);
    node.setYCoord(yCoordinate);
    node.setBuilding(building);
    node.setLongName(longName);
    node.setShortName(shortName);
    node.setType(nodeType);
    node.setFloor(floorNumber);

    nodeFactory.create(node);
    drawNode(node);
    reset();
    modifyPane.setVisible(false);
  }

  @FXML
  private void modifyData() throws ValidationException {
    String ID = nodeIDInput.getText();
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = buildingInput.getText();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getText());
    short floorNumber = Short.parseShort(floorInput.getText());

    node.setId(ID);
    node.setXCoord(xCoordinate);
    node.setYCoord(yCoordinate);
    node.setBuilding(building);
    node.setLongName(longName);
    node.setShortName(shortName);
    node.setType(nodeType);
    node.setFloor(floorNumber);

    nodeFactory.update(node);
    double heightRatio = mapPane.getHeight() / MAP_HEIGHT;
    double widthRatio = mapPane.getWidth() / MAP_WIDTH;
    nodeButton.setLayoutX(xCoordinate * widthRatio);
    nodeButton.setLayoutY(yCoordinate * heightRatio);
    reset();
  }

  @FXML
  private void displayData() {
    nodeIDInput.setText(node.getId());
    yCoorInput.setText("" + node.getYCoord());
    xCoorInput.setText("" + node.getXCoord());
    buildingInput.setText(node.getBuilding());
    longNameInput.setText(node.getLongName());
    shortNameInput.setText(node.getShortName());
    typeInput.setText("" + node.getType());
    floorInput.setText("" + node.getFloor());
  }

  @FXML
  public void validateText(KeyEvent keyEvent) {
    if (!xCoorInput.getText().isEmpty()
        && !yCoorInput.getText().isEmpty()
        && !buildingInput.getText().isEmpty()
        && !longNameInput.getText().isEmpty()
        && !shortNameInput.getText().isEmpty()
        && !typeInput.getText().isEmpty()
        && !floorInput.getText().isEmpty()) {
      modifyButton.setDisable(false);
      addButtonWindow.setDisable(false);
    } else {
      modifyButton.setDisable(true);
      addButtonWindow.setDisable(true);
    }
  }

  @FXML
  private void reset() {
    xCoorInput.setText("");
    yCoorInput.setText("");
    buildingInput.setText("");
    longNameInput.setText("");
    shortNameInput.setText("");
    typeInput.setText("");
    floorInput.setText("");
    nodeIDInput.setText("");
    modifyButton.setVisible(true);
    deleteButton.setVisible(true);
    addButtonWindow.setVisible(false);
  }
}
