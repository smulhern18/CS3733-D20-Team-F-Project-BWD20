package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.EdgeFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.UIEdge;
import edu.wpi.teamF.ModelClasses.UINode;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class DataManipulatorController implements Initializable {

  public JFXTreeTableView<UINode> treeView;
  public JFXTextField filterTextFieldNodes;
  public JFXTreeTableView<UIEdge> treeViewEdges;
  public JFXTextField filterTextFieldEdges;
  NodeFactory nodes = NodeFactory.getFactory();
  EdgeFactory edges = EdgeFactory.getFactory();

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
    xCoord.setPrefWidth(100);
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
    yCoord.setPrefWidth(100);
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
    building.setPrefWidth(100);
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
    floor.setPrefWidth(100);
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
    longName.setPrefWidth(100);
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
    shortName.setPrefWidth(100);
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
    UINodeType.setPrefWidth(100);
    UINodeType.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UINode, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UINode, String> param) {
            return param.getValue().getValue().nodeType;
          }
        });

    ObservableList<UINode> UINodes = FXCollections.observableArrayList();

    // Add UINodes to the table, by creating new UINodes
    List<Node> Nodes = nodes.getAllNodes();
    for (Node node : Nodes) {
      UINodes.add(new UINode(node));
    }

    final TreeItem<UINode> root =
        new RecursiveTreeItem<UINode>(UINodes, RecursiveTreeObject::getChildren);

    treeView
        .getColumns()
        .setAll(ID, xCoord, yCoord, building, floor, longName, shortName, UINodeType);

    ID.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    xCoord.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    yCoord.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    building.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    floor.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    longName.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    shortName.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    UINodeType.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeView.setRoot(root);
    treeView.setEditable(true);
    treeView.setShowRoot(false);

    filterTextFieldNodes
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(
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
    IDE.setPrefWidth(100);
    IDE.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UIEdge, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIEdge, String> param) {
            return param.getValue().getValue().ID;
          }
        });

    JFXTreeTableColumn<UIEdge, String> Node1 = new JFXTreeTableColumn<>("Node 1");
    Node1.setPrefWidth(100);
    Node1.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UIEdge, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIEdge, String> param) {
            return param.getValue().getValue().node1ID;
          }
        });

    JFXTreeTableColumn<UIEdge, String> Node2 = new JFXTreeTableColumn<>("Node 2");
    Node2.setPrefWidth(100);
    Node2.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<UIEdge, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIEdge, String> param) {
            return param.getValue().getValue().node2ID;
          }
        });

    ObservableList<UIEdge> UIEdges = FXCollections.observableArrayList();

    List<Edge> Edges = edges.getAllEdges();
    for (Edge edge : Edges) {
      UIEdges.add(new UIEdge(edge));
    }

    final TreeItem<UIEdge> rootE =
        new RecursiveTreeItem<UIEdge>(UIEdges, RecursiveTreeObject::getChildren);

    treeViewEdges.getColumns().setAll(IDE, Node1, Node2);

    IDE.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
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
                treeView.setPredicate(
                    new Predicate<TreeItem<UINode>>() {
                      @Override
                      public boolean test(TreeItem<UINode> UINode) {
                        Boolean flag = UINode.getValue().ID.getValue().contains(newValue);
                        return flag;
                      }
                    });
              }
            });
  }

  public void clearText(MouseEvent mouseEvent) {
    filterTextFieldNodes.setText("");
  }
}
