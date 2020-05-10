package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestStats;
import edu.wpi.teamF.ModelClasses.ServiceRequest.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
  public BarChart<?, ?> mostcomLocTrans;
  public CategoryAxis xAxisLocTrans;
  public AnchorPane rootPane;
  public NumberAxis yAxisLocTrans;
  public BarChart<?, ?> transComp;
  public CategoryAxis xAxisEmpComp;
  public NumberAxis yAxisEmpComp;
  public PieChart pieChartEmpTrans;
  public PieChart pieChartCompTrans;


  //Sanitation Requests

  public BarChart<?,?> barSaniLoc;
  public CategoryAxis xAxisLocSan;
  public NumberAxis yAxisLocSan;
  public BarChart<?,?> barSanCom;
  public CategoryAxis xAxisEmpTrans;
  public NumberAxis yAxisEmpTrans;
  public PieChart pieChartEmpSan;
  public PieChart pieChartCompSan;

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

    /*

    Transport Request Tab


     */
    List<String> data4;
    List<String> data5;
    List<String> data6;
    XYChart.Series dataSeries3 = new XYChart.Series<>();
    XYChart.Series dataSeries4 = new XYChart.Series<>();

    data4 = serviceRequestStats.getTransportEmployeeNumbersGraphs(tR);
    data5 = serviceRequestStats.getTransportLocationNumbersGraph(tR);
    data6 = serviceRequestStats.transportCompleted(tR);

    for (int i = 0; i < data4.size(); i += 2) {
      dataSeries3
          .getData()
          .add(new XYChart.Data<>(data4.get(i), Integer.parseInt(data4.get(i + 1))));
    }
    for (int i = 0; i < data5.size(); i += 2) {
      dataSeries4
          .getData()
          .add(new XYChart.Data<>(data5.get(i), Integer.parseInt(data5.get(i + 1))));
    }

    mostcomLocTrans.getData().add(dataSeries3);
    transComp.getData().add(dataSeries4);

    ObservableList<PieChart.Data> pieChartData3 = FXCollections.observableArrayList();
    for (int i = 0; i < data4.size(); i += 2) {
      pieChartData3.add(new PieChart.Data(data4.get(i), Integer.parseInt(data4.get(i + 1))));
    }
    pieChartEmpTrans.setData(pieChartData3);

    ObservableList<PieChart.Data> pieChartData5 = FXCollections.observableArrayList();
    for (int i = 0; i < data6.size(); i += 2) {
      pieChartData5.add(new PieChart.Data(data6.get(i), Integer.parseInt(data6.get(i + 1))));
    }
    pieChartCompTrans.setData(pieChartData5);


    //Sanitation Request
    List<String> data7;
    List<String> data8;
    List<String> data9;
    XYChart.Series dataSeries5 = new XYChart.Series<>();
    XYChart.Series dataSeries6 = new XYChart.Series<>();

    data7 = serviceRequestStats.getSanitationEmployeeNumbersGraphs(sR);
    data8 = serviceRequestStats.getSanitationLocationNumbersGraphs(sR);
    data9 = serviceRequestStats.sanitationCompleted(sR);

  }

  public void back(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Accounts");
  }

  public void backupServiceRequests(ActionEvent actionEvent) {
    backup.setTitle("Select Where to Backup Database");
    File selDir = backup.showDialog(rootPane.getScene().getWindow());
    serviceRequestStats.downloadStatistics(selDir.toPath());
  }
}
