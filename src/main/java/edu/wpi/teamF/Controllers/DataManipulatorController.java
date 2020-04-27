package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.*;
import edu.wpi.teamF.ModelClasses.UIClasses.UIEdge;
import edu.wpi.teamF.ModelClasses.UIClasses.UINode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javax.management.InstanceNotFoundException;

public class DataManipulatorController implements Initializable {

  public JFXTreeTableView<UINode> treeViewNodes;
  public JFXTextField filterTextFieldNodes;
  public JFXTreeTableView<UIEdge> treeViewEdges;
  public JFXTextField filterTextFieldEdges;
  public JFXToggleButton switcher;
  public JFXButton updateNodesButton;
  public JFXButton updateEdgesButton;
  public JFXButton deleteNodeButton;
  public JFXTextField nodeToDelete;
  public JFXTextField edgeToDelete;
  public JFXButton deleteEdgeButton;
  public AnchorPane mapView;
  public AnchorPane rootPane;
  public JFXButton uploadNodesButton;
  public JFXButton cancelEdgeButton;
  public JFXButton cancelButton;
  public AnchorPane mapPane;
  public AnchorPane edgePane;
  public AnchorPane nodesPane;
  NodeFactory nodes = NodeFactory.getFactory();
  EdgeFactory edges = EdgeFactory.getFactory();
  FileChooser nodesChooser = new FileChooser();
  FileChooser edgesChooser = new FileChooser();
  DirectoryChooser backup = new DirectoryChooser();
  CSVManipulator csvM = new CSVManipulator();
  ObservableList<UINode> UINodes = FXCollections.observableArrayList();
  ObservableList<UIEdge> UIEdges = FXCollections.observableArrayList();
  SceneController sceneController = App.getSceneController();

  @FXML private StackPane modifyNodePane;

  @FXML private JFXButton addNodeButton;

  @FXML private JFXTextField nodeIDInput;

  @FXML private JFXTextField yCoorInput;

  @FXML private JFXTextField xCoorInput;

  @FXML private JFXTextField typeInput;

  @FXML private JFXTextField shortNameInput;

  @FXML private JFXTextField longNameInput;

  @FXML private JFXTextField buildingInput;

  @FXML private JFXTextField floorInput;

  @FXML private StackPane addEdgePane;

  @FXML private JFXButton addEdgeButton;

  @FXML private JFXTextField node1Input;

  @FXML private JFXTextField node2Input;

  @FXML private Label edgeErrorLabel;

  @FXML private Label nodeErrorLabel;

  @FXML private JFXButton addNodePaneButton;

  @FXML private JFXButton addEdgePaneButton;

  EdgeFactory edgeFactory = new EdgeFactory();

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    /*

    NODES

     */

    // ID column
    JFXTreeTableColumn<UINode, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().ID;
          }
        });

    // XCoord Column
    JFXTreeTableColumn<UINode, String> xCoord = new JFXTreeTableColumn<>("xCoord");
    xCoord.setPrefWidth(90);
    xCoord.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().xCoord;
          }
        });

    // yCoord Column
    JFXTreeTableColumn<UINode, String> yCoord = new JFXTreeTableColumn<>("yCoord");
    yCoord.setPrefWidth(90);
    yCoord.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().yCoord;
          }
        });

    // Building Column
    JFXTreeTableColumn<UINode, String> building = new JFXTreeTableColumn<>("Building");
    building.setPrefWidth(90);
    building.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().building;
          }
        });

    // Floor Column
    JFXTreeTableColumn<UINode, String> floor = new JFXTreeTableColumn<>("Floor");
    floor.setPrefWidth(90);
    floor.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().floor;
          }
        });

    // Long Name Column
    JFXTreeTableColumn<UINode, String> longName = new JFXTreeTableColumn<>("Long Name");
    longName.setPrefWidth(90);
    longName.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().longName;
          }
        });

    // Short Name
    JFXTreeTableColumn<UINode, String> shortName = new JFXTreeTableColumn<>("Short Name");
    shortName.setPrefWidth(90);
    shortName.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().shortName;
          }
        });

    // UINode Type Column
    JFXTreeTableColumn<UINode, String> UINodeType = new JFXTreeTableColumn<>("Node Type");
    UINodeType.setPrefWidth(90);
    UINodeType.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().nodeType;
          }
        });

    // Add UINodes to the table, by creating new UINodes
    List<Node> Nodes = nodes.getAllNodes();
    for (Node node : Nodes) {
      UINodes.add(new UINode(node));
    }

    final TreeItem<UINode> root =
        new RecursiveTreeItem<UINode>(UINodes, RecursiveTreeObject::getChildren);

    treeViewNodes
        .getColumns()
        .setAll(ID, xCoord, yCoord, building, floor, longName, shortName, UINodeType);

    // ID.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    xCoord.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    yCoord.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    building.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    floor.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    longName.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    shortName.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    UINodeType.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeViewNodes.setRoot(root);
    treeViewNodes.setEditable(true);
    treeViewNodes.setShowRoot(false);

    filterTextFieldNodes
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeViewNodes.setPredicate(
                    new Predicate<TreeItem<UINode>>() {
                      @Override
                      public boolean test(TreeItem<UINode> UINode) {
                        Boolean flag =
                            UINode.getValue().ID.getValue().contains(newValue)
                                || UINode.getValue().nodeType.getValue().contains(newValue);
                        return flag;
                      }
                    });
              }
            });

    /*

    EDGES

     */

    JFXTreeTableColumn<UIEdge, String> IDE = new JFXTreeTableColumn<>("ID");
    IDE.setPrefWidth(150);
    IDE.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UIEdge, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIEdge, String> param) {
            return param.getValue().getValue().ID;
          }
        });

    JFXTreeTableColumn<UIEdge, String> Node1 = new JFXTreeTableColumn<>("Node 1");
    Node1.setPrefWidth(90);
    Node1.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UIEdge, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIEdge, String> param) {
            return param.getValue().getValue().node1ID;
          }
        });

    JFXTreeTableColumn<UIEdge, String> Node2 = new JFXTreeTableColumn<>("Node 2");
    Node2.setPrefWidth(90);
    Node2.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UIEdge, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIEdge, String> param) {
            return param.getValue().getValue().node2ID;
          }
        });

    List<Edge> Edges = edges.getAllEdges();
    for (Edge edge : Edges) {
      UIEdges.add(new UIEdge(edge));
    }

    final TreeItem<UIEdge> rootE =
        new RecursiveTreeItem<UIEdge>(UIEdges, RecursiveTreeObject::getChildren);

    treeViewEdges.getColumns().setAll(IDE, Node1, Node2);

    // IDE.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    Node1.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    Node2.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeViewEdges.setRoot(rootE);
    treeViewEdges.setEditable(true);
    treeViewEdges.setShowRoot(false);

    filterTextFieldEdges
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeViewEdges.setPredicate(
                    new Predicate<TreeItem<UIEdge>>() {
                      @Override
                      public boolean test(TreeItem<UIEdge> UIEdge) {
                        Boolean flag = UIEdge.getValue().ID.getValue().contains(newValue);
                        return flag;
                      }
                    });
              }
            });
  }

  public void clearText(MouseEvent mouseEvent) {
    filterTextFieldNodes.setText("");
  }

  public void uploadNodes(ActionEvent actionEvent) throws FileNotFoundException {
    nodesChooser.setTitle("Select CSV File Nodes");
    File file = nodesChooser.showOpenDialog(rootPane.getScene().getWindow());
    csvM.readCSVFileNode(new FileInputStream(file));
  }

  public void uploadEdges(ActionEvent actionEvent) throws FileNotFoundException {
    edgesChooser.setTitle("Select CSV File Edges");
    File file = edgesChooser.showOpenDialog(rootPane.getScene().getWindow());
    csvM.readCSVFileNode(new FileInputStream(file));
  }

  public void updateNodes(ActionEvent actionEvent)
      throws InstanceNotFoundException, ValidationException {
    for (UINode nodeUI : UINodes) {
      Node node = nodeUI.UItoNode();
      node.setEdges(edgeFactory.getAllEdgesConnectedToNode(node.getId()));
      if (!node.equals(nodes.read(node.getId()))) {
        // update that node in the db to the new values of that nodeUI
        nodes.update(node);
      }
    }
    treeViewNodes.refresh();
  }

  public void updateEdges(ActionEvent actionEvent) throws Exception {
    for (UIEdge edgeUI : UIEdges) {
      boolean isSame = edgeUI.equalsEdge(edges.read(edgeUI.getID().toString()));
      if (!isSame) {
        // update that edge in the db to the new values of that nodeUI
        edges.update(edgeUI.UItoEdge());
      }
    }
    treeViewEdges.refresh();
  }

  public void deleteNode(ActionEvent actionEvent) {
    String nodeID = nodeToDelete.getText();
    nodes.delete(nodeID);
    UINodes.removeIf(node -> node.getID().get().equals(nodeID));
    treeViewNodes.refresh();
  }

  public void deleteEdge(ActionEvent actionEvent) {
    String edgeID = edgeToDelete.getText();
    edges.delete(edgeID);
    UIEdges.removeIf(node -> node.getID().get().equals(edgeID));
    treeViewNodes.refresh();
  }

  public void switchToUserAccounts(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Accounts");
  }

  public void backupDB(ActionEvent actionEvent) {
    backup.setTitle("Select Where to Backup Database");
    File selDir = backup.showDialog(rootPane.getScene().getWindow());

    // backup
    csvM.writeCSVFileNode(selDir.toPath());
    csvM.writeCSVFileEdge(selDir.toPath());
    csvM.writeCSVFileMaintenanceService(selDir.toPath());
    csvM.writeCSVFileSecurityService(selDir.toPath());
    csvM.writeCSVFileAccount(selDir.toPath());
  }

  @FXML
  private void cancelNodePane() {
    resetNodePane();
    modifyNodePane.setVisible(false);
  }

  @FXML
  private void addNodePane() throws ValidationException {
    modifyNodePane.setVisible(true); // set the pane to be visible
    addNodeButton.setVisible(true);
    addNodeButton.setDisable(true);
  }

  @FXML
  private void addNode() throws Exception {
    String ID = nodeIDInput.getText();
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = buildingInput.getText();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getText());
    short floorNumber = Short.parseShort(floorInput.getText()); // stores the inputs into

    Node testNode = nodes.read(ID); // does the ID exist?

    try { // is the input valid?
      if (testNode == null) { // is the ID available?
        Node newNode =
            new Node(
                ID,
                xCoordinate,
                yCoordinate,
                building,
                longName,
                shortName,
                nodeType,
                floorNumber); // creates a new node
        nodes.create(newNode); // creates the node in the db
        resetNodePane();
        modifyNodePane.setVisible(false);
      } else { // fails the if statement if the ID already exists
        nodeIDInput.setUnFocusColor(Color.RED);
        nodeErrorLabel.setText("The ID already exists");
      }
    } catch (Exception e) { // throws an error if the input provided by the user is invalid
      nodeErrorLabel.setText("The input is invalid");
    }
  }

  @FXML
  private void resetNodePane() {
    xCoorInput.setText("");
    yCoorInput.setText("");
    longNameInput.setText("");
    shortNameInput.setText("");
    typeInput.setText("");
    nodeIDInput.setText(""); // sets all of the input to empty strings
    nodeErrorLabel.setText("");
  }

  @FXML
  public void validateNodeText(KeyEvent keyEvent) {
    if (!nodeIDInput.getText().isEmpty()
        && !xCoorInput.getText().isEmpty()
        && !yCoorInput.getText().isEmpty()
        && !buildingInput.getText().isEmpty()
        && !longNameInput.getText().isEmpty()
        && !shortNameInput.getText().isEmpty()
        && !typeInput.getText().isEmpty()
        && !floorInput.getText().isEmpty()) {
      addNodeButton.setDisable(false);
    } else {
      addNodeButton.setDisable(true);
    }
  }

  @FXML
  public void validateEdgeText(KeyEvent keyEvent) {
    if (!node1Input.getText().isEmpty() && !node2Input.getText().isEmpty()) {
      addEdgeButton.setDisable(false);
    } else {
      addEdgeButton.setDisable(false);
    }
  }

  @FXML
  private void cancelEdgePane() {
    resetEdgeAddPane();
    addEdgePane.setVisible(false);
  }

  @FXML
  private void addEdgePane() throws ValidationException {
    addEdgePane.setVisible(true);
    addEdgeButton.setDisable(true); // the edge should be enabled only when two nodes are selected
    addEdgeButton.setVisible(true);
  }

  @FXML
  private void addEdge() throws Exception {
    String node1ID = node1Input.getText();
    String node2ID = node2Input.getText();
    String ID = node1ID + "_" + node2ID; // The edge ID is the two node IDs combined with a "_"
    try {
      Edge newEdge = new Edge(ID, node1ID, node2ID);
      edgeFactory.create(newEdge); // adds edge to db
      addEdgePane.setVisible(false);
      resetEdgeAddPane();
    } catch (Exception e) {
      edgeErrorLabel.setText("The input is invalid");
    }
  }

  @FXML
  private void resetEdgeAddPane() {
    node1Input.setText("");
    node2Input.setText(""); // resets the text in the two buttons
    addEdgeButton.setDisable(true);
    edgeErrorLabel.setText("");
  }

  public void clearEdge(MouseEvent mouseEvent) {
    filterTextFieldEdges.setText("");
  }

  public void switchToNodes(ActionEvent actionEvent) {
    edgePane.setVisible(false);
    mapPane.setVisible(false);
    nodesPane.setVisible(true);
    mapView.setVisible(false);
  }

  public void switchToEdges(ActionEvent actionEvent) {
    edgePane.setVisible(true);
    nodesPane.setVisible(false);
    mapPane.setVisible(false);
    modifyNodePane.setVisible(false);
    mapView.setVisible(false);
  }

  public void switchToMap(ActionEvent actionEvent) {
    edgePane.setVisible(false);
    mapPane.setVisible(true);
    nodesPane.setVisible(false);
    mapView.setVisible(true);
  }
}
