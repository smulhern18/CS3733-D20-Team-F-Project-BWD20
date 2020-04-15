package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.Factories.CSVManipulator;
import edu.wpi.teamF.Factories.NodeFactory;
import java.io.File;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class DisplayDataController extends SceneController {

  public TableView<Node> table;
  public TableColumn<Node, String> name;
  public TableColumn<Node, Short> xCoord;
  public TableColumn<Node, Short> yCoord;
  public TableColumn<Node, String> longName;
  // public TableColumn<Node, String> shortName;
  public TableColumn<Node, String> type;
  public TableColumn<Node, String> floor;
  public AnchorPane displayPane;

  public void displayData(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select CSV File");
    File file = fileChooser.showOpenDialog(table.getScene().getWindow());

    CSVManipulator csvM = new CSVManipulator();
    csvM.readCSVFile(file.toPath());

    // eww
    NodeFactory nodeFactory = NodeFactory.getFactory();
    ObservableList<Node> nodes = nodeFactory.getAllNodes();

    // populating the table with Node data
    // table.setItems(nodes);
    populateTable();
  }

  public void populateTable() {}
}
