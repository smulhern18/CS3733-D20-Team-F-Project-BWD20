package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestStats;
import edu.wpi.teamF.ModelClasses.ServiceRequest.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import lombok.SneakyThrows;

public class DataViewController implements Initializable {

  public JFXComboBox<String> serviceChoice;
  // Maintenance Request
  public BarChart<?, ?> completedChartMain;
  public PieChart pieChartTotalMain;
  public BarChart<?, ?> serviceLocationBarMain;
  public CategoryAxis xAxisMain;
  public NumberAxis yAxisMain;
  public CategoryAxis yAxisLoc;
  public NumberAxis xAxisLoc;
  public PieChart pieChartMainComp;

  // Transport Request

  // Sanitation Reques

  // Traffic

  public AnchorPane rootPane;

  ServiceRequestStats serviceRequestStats = new ServiceRequestStats();
  DatabaseManager databaseManager = DatabaseManager.getManager();
  public List<MaintenanceRequest> mR = databaseManager.getAllMaintenanceRequests();
  public List<TransportRequest> tR = databaseManager.getAllTransportRequests();
  public List<SanitationServiceRequest> sR = databaseManager.getAllSanitationRequests();
  public SceneController sceneController = App.getSceneController();
  DirectoryChooser backup = new DirectoryChooser();

  public DataViewController() throws Exception {}

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    List<ReportsClass> rC = databaseManager.getAllReports();

    /*

    Maintenance Request Tab

     */

    // Data 1 = Employee Number
    // Data 2 = Location Number
    // Data 3 = Completed / Incomplete
    List<String> data1;
    List<String> data2;
    List<String> data3;
    XYChart.Series dataSeries1 = new XYChart.Series<>();
    XYChart.Series dataSeries2 = new XYChart.Series<>();
    data1 = serviceRequestStats.getMaintenanceEmployeeNumbersGraphs(mR);
    data2 = serviceRequestStats.getMaintenanceLocationNumbersGraphs(mR);
    data3 = serviceRequestStats.maintenanceCompleted(mR);

    // completed Graph to show number of completed requests per employee

    for (int i = 0; i < data1.size(); i += 2) {
      dataSeries1
          .getData()
          .add(new XYChart.Data<>(data1.get(i), Integer.parseInt(data1.get(i + 1))));
    }

    for (int i = 0; i < data2.size(); i += 2) {
      dataSeries2
          .getData()
          .add(new XYChart.Data<>(data2.get(i), Integer.parseInt(data2.get(i + 1))));
    }

    completedChartMain.getData().add(dataSeries1);
    serviceLocationBarMain.getData().add(dataSeries2);

    // Pie Chart to compared visually which employee did the most service requests
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    for (int i = 0; i < data1.size(); i += 2) {
      pieChartData.add(new PieChart.Data(data1.get(i), Integer.parseInt(data1.get(i + 1))));
    }
    pieChartTotalMain.setData(pieChartData);

    // Pie chart to show completed / incomplete maintenance requests
    ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();
    for (int i = 0; i < data3.size(); i += 2) {
      pieChartData2.add(new PieChart.Data(data3.get(i), Integer.parseInt(data3.get(i + 1))));
    }
    pieChartMainComp.setData(pieChartData2);
  }

  public void back(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Accounts");
  }

  public void backupServiceRequests(ActionEvent actionEvent) {
    backup.setTitle("Select Where to Backup Database");
    File selDir = backup.showDialog(rootPane.getScene().getWindow());
    serviceRequestStats.downloadStatistics(selDir.toPath());
  }

  public ArrayList<String> top5(ArrayList<String> data){
    ArrayList<String> top5data= new ArrayList<String>();
    String temp1 ="";
    String temp2 ="";
    int top=0;
    if(data.size()>10) {
     while(top <5){
       temp1 = data.get(0);
       temp2 = data.get(1);
       for(int i =2;i < data.size();i++){
        if(Integer.parseInt(temp2) < Integer.parseInt(data.get(i+1))){
          temp1 = data.get(i);
          temp2 = data.get(i+1);
        }
       }
       top5data.add(temp1);
       top5data.add(temp2);
       data.remove(temp1);
       data.remove(temp2);
     }
    } else{
      top5data = data;
    }
    return top5data;
  }

  /*

  Transport Request Tab


   */


}
