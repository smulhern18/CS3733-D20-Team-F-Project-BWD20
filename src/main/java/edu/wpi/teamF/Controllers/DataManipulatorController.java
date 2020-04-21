package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.*;
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
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
  NodeFactory nodes = NodeFactory.getFactory();
  EdgeFactory edges = EdgeFactory.getFactory();
  FileChooser nodesChooser = new FileChooser();
  FileChooser edgesChooser = new FileChooser();
  DirectoryChooser backup = new DirectoryChooser();
  CSVManipulator csvM = new CSVManipulator();
  ObservableList<UINode> UINodes = FXCollections.observableArrayList();
  ObservableList<UIEdge> UIEdges = FXCollections.observableArrayList();
  SceneController sceneController = App.getSceneController();

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

  public void viewerSwitcher(ActionEvent actionEvent) {
    boolean isSelected = switcher.isSelected();
    if (isSelected) {

      filterTextFieldEdges.setVisible(false);
      filterTextFieldNodes.setVisible(false);
      updateNodesButton.setVisible(false);
      updateEdgesButton.setVisible(false);
      treeViewNodes.setVisible(false);
      treeViewEdges.setVisible(false);
      deleteEdgeButton.setVisible(false);
      deleteNodeButton.setVisible(false);
      edgeToDelete.setVisible(false);
      nodeToDelete.setVisible(false);
      mapView.setVisible(true);

      // set map stuff visible
    } else {
      filterTextFieldEdges.setVisible(true);
      filterTextFieldNodes.setVisible(true);
      updateNodesButton.setVisible(true);
      updateEdgesButton.setVisible(true);
      treeViewNodes.setVisible(true);
      treeViewEdges.setVisible(true);
      deleteEdgeButton.setVisible(true);
      deleteNodeButton.setVisible(true);
      edgeToDelete.setVisible(true);
      nodeToDelete.setVisible(true);
      mapView.setVisible(false);
    }
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
      boolean isSame = nodeUI.equalsNode(nodes.read(nodeUI.getID().toString()));
      if (!isSame) {
        // update that node in the db to the new values of that nodeUI
        nodes.update(nodeUI.UItoNode());
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
    // csvM.writeCSVFileEdge(selDir.toPath());
  }
}
