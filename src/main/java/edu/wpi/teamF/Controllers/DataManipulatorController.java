package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

  public JFXTreeTableView<Node> treeView;
  public JFXTextField filterTextField;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // ID column
    JFXTreeTableColumn<Node, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().ID;
          }
        });

    // XCoord Column
    JFXTreeTableColumn<Node, String> xCoord = new JFXTreeTableColumn<>("xCoord");
    xCoord.setPrefWidth(100);
    xCoord.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().xCoord;
          }
        });

    // yCoord Column
    JFXTreeTableColumn<Node, String> yCoord = new JFXTreeTableColumn<>("yCoord");
    yCoord.setPrefWidth(100);
    yCoord.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().yCoord;
          }
        });

    // Building Column
    JFXTreeTableColumn<Node, String> building = new JFXTreeTableColumn<>("Building");
    building.setPrefWidth(100);
    building.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().building;
          }
        });

    // Floor Column
    JFXTreeTableColumn<Node, String> floor = new JFXTreeTableColumn<>("Floor");
    floor.setPrefWidth(100);
    floor.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().floor;
          }
        });

    // Long Name Column
    JFXTreeTableColumn<Node, String> longName = new JFXTreeTableColumn<>("Long Name");
    longName.setPrefWidth(100);
    longName.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().longName;
          }
        });

    // Short Name
    JFXTreeTableColumn<Node, String> shortName = new JFXTreeTableColumn<>("Short Name");
    shortName.setPrefWidth(100);
    shortName.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().shortName;
          }
        });

    // Node Type Column
    JFXTreeTableColumn<Node, String> nodeType = new JFXTreeTableColumn<>("Node Type");
    nodeType.setPrefWidth(100);
    nodeType.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().nodeType;
          }
        });

    // Edges Column
    JFXTreeTableColumn<Node, String> edges = new JFXTreeTableColumn<>("Edges");
    edges.setPrefWidth(100);
    edges.setCellValueFactory(
        new Callback<TreeTableColumn.CellDataFeatures<Node, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<Node, String> param) {
            return param.getValue().getValue().edges;
          }
        });

    ObservableList<Node> nodes = FXCollections.observableArrayList();

    // Add Nodes to the table, by creating new Nodes

    // Test data for now
    nodes.add(new Node("1", "1", "1", "WPI", "1", "Yeet", "Yt", "Sad", "edg1, edge2"));
    nodes.add(new Node("2", "1", "1", "WPI", "1", "Yeet", "Yt", "Sad", "edg1, edge2"));
    nodes.add(new Node("3", "1", "1", "WPI", "1", "Yeet", "Yt", "Sad", "edg1, edge2"));
    nodes.add(new Node("4", "1", "1", "WPI", "1", "Yeet", "Yt", "Sad", "edg1, edge2"));

    final TreeItem<Node> root =
        new RecursiveTreeItem<Node>(nodes, RecursiveTreeObject::getChildren);
    treeView
        .getColumns()
        .setAll(ID, xCoord, yCoord, building, floor, longName, shortName, nodeType, edges);

    ID.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    xCoord.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    yCoord.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    building.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    floor.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    longName.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    shortName.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    nodeType.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    edges.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeView.setRoot(root);
    treeView.setEditable(true);
    treeView.setShowRoot(false);

    filterTextField
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeView.setPredicate(
                    new Predicate<TreeItem<Node>>() {
                      @Override
                      public boolean test(TreeItem<Node> node) {
                        Boolean flag =
                            node.getValue().ID.getValue().contains(newValue)
                                || node.getValue().nodeType.getValue().contains(newValue);
                        return flag;
                      }
                    });
              }
            });
  }

  public Node createNodefromDB() {
    return null;
  }

  public void clearText(MouseEvent mouseEvent) {
    filterTextField.setText("");
  }

  static class Node extends RecursiveTreeObject<Node> {

    StringProperty ID;
    StringProperty xCoord;
    StringProperty yCoord;
    StringProperty building;
    StringProperty floor;
    StringProperty longName;
    StringProperty shortName;
    StringProperty nodeType;
    StringProperty edges;

    public Node(
        String ID,
        String xCoord,
        String yCoord,
        String building,
        String floor,
        String longName,
        String shortName,
        String nodeType,
        String edges) {
      this.ID = new SimpleStringProperty(ID);
      this.xCoord = new SimpleStringProperty(xCoord);
      this.yCoord = new SimpleStringProperty(yCoord);
      this.building = new SimpleStringProperty(building);
      this.floor = new SimpleStringProperty(floor);
      this.longName = new SimpleStringProperty(longName);
      this.shortName = new SimpleStringProperty(shortName);
      this.nodeType = new SimpleStringProperty(nodeType);
      this.edges = new SimpleStringProperty(edges);
    }
  }
}
