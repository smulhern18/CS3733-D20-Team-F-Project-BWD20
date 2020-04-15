package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.Factories.CSVManipulator;
import edu.wpi.teamF.Factories.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import java.io.File;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class DisplayDataController extends SceneController {

  public TableView<Node> table;
  public TableColumn<Node, String> name;
  public TableColumn<Node, String> xCoord;
  public TableColumn<Node, String> yCoord;
  public TableColumn<Node, String> longName;
  public TableColumn<Node, String> type;
  public TableColumn<Node, String> floor;
  public AnchorPane displayPane;

  public void displayNodes(ActionEvent actionEvent) throws Exception {
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    xCoord.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
    yCoord.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
    longName.setCellValueFactory(new PropertyValueFactory<>("longName"));
    type.setCellValueFactory(new PropertyValueFactory<>("type"));
    floor.setCellValueFactory(new PropertyValueFactory<>("floor"));

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select CSV File Nodes");
    File file = fileChooser.showOpenDialog(table.getScene().getWindow());

    CSVManipulator csvM = new CSVManipulator();
    csvM.readCSVFileNode(file.toPath());

    // eww
    NodeFactory nodeFactory = NodeFactory.getFactory();
    ObservableList<Node> nodes = nodeFactory.getAllNodes();
    for (Node n : nodes) {
      System.out.println(csvM.formatNode(n));
    }

    // populating the table with Node data
    table.setItems(nodes);
  }

  public void getEdges(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select CSV File Edges");
    File file = fileChooser.showOpenDialog(table.getScene().getWindow());

    CSVManipulator csvM = new CSVManipulator();
    csvM.readCSVFileEdge(file.toPath());
  }
}
