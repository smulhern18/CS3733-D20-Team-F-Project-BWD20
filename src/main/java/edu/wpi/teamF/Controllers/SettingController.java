package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class SettingController implements Initializable {

  @FXML private AnchorPane anchorPane;

  private DatabaseManager databaseManager = DatabaseManager.getManager();

  FileChooser nodesChooser = new FileChooser();
  FileChooser edgesChooser = new FileChooser();
  DirectoryChooser backup = new DirectoryChooser();

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void backupDB(ActionEvent actionEvent) throws Exception {
    backup.setTitle("Select Where to Backup Database");
    File selDir = backup.showDialog(anchorPane.getScene().getWindow());

    // backup
    databaseManager.backup(selDir.toPath());
  }

  public void uploadNodes(ActionEvent actionEvent) throws FileNotFoundException {
    nodesChooser.setTitle("Select CSV File Nodes");
    File file = nodesChooser.showOpenDialog(anchorPane.getScene().getWindow());
    databaseManager.readNodes(new FileInputStream(file));
  }

  public void uploadEdges(ActionEvent actionEvent) throws FileNotFoundException {
    edgesChooser.setTitle("Select CSV File Edges");
    File file = edgesChooser.showOpenDialog(anchorPane.getScene().getWindow());

    databaseManager.readEdges(new FileInputStream(file));
  }
}
