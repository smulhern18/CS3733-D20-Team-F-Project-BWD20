package edu.wpi.teamF.controllers;

import edu.wpi.teamF.App;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class DisplayDataController extends SceneController {

  public AnchorPane rootPane;

  public void displayData(ActionEvent actionEvent) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File(""));

    File selectedDirectory = directoryChooser.showDialog(App.getPS());
    String directory = selectedDirectory.getAbsolutePath();
    displayTableView(directory);
  }

  SceneController sceneController = new SceneController();

  public void modifyValSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ModifyData");
  }

  public void pathfinderSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void downloadSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DownloadData");
  }

  public void mainMenuSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  private void displayTableView(String filepath) {

    // Need to create a new tableview of type Node
    TableView<String> csvData = new TableView<>();
    // List that the nodes will populate its data with
    ObservableList<String> dataList = FXCollections.observableArrayList();
    // populate dataList, need name of nodes to grab data from them

    // readcvsFile() in cvsmanipulator
    // display in tableview
    // CSVManipulator reader = new CSVManipulator(filepath);

    // Columns for the tableview, not sure how many we need yet

    TableColumn columna1 = new TableColumn<>("A1");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A1"));
    TableColumn columna2 = new TableColumn<>("A2");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A2"));
    TableColumn columna3 = new TableColumn<>("A3");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A3"));
    TableColumn columna4 = new TableColumn<>("A4");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A4"));
    TableColumn columna5 = new TableColumn<>("A5");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A5"));
    TableColumn columna6 = new TableColumn<>("A6");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A6"));
    TableColumn columna7 = new TableColumn<>("A7");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A7"));
    TableColumn columna8 = new TableColumn<>("A8");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A8"));
    TableColumn columna9 = new TableColumn<>("A9");
    columna1.setCellValueFactory(new PropertyValueFactory<>("A9"));

    csvData.setItems(dataList);
    csvData
        .getColumns()
        .addAll(
            columna1, columna2, columna3, columna4, columna5, columna6, columna7, columna8,
            columna9);

    VBox vBox = new VBox();
    vBox.setSpacing(10);
    vBox.getChildren().add(csvData);

    rootPane.getChildren().add(vBox);
  }
}
