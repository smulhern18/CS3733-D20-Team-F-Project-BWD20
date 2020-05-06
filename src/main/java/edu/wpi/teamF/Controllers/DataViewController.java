package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import lombok.SneakyThrows;

public class DataViewController implements Initializable {

  public JFXComboBox<String> serviceChoice;
  // Maintenance Request
  public GridPane maintenanceGrid;
  public BarChart<?, ?> completedChartMain;
  public PieChart pieChartTotalMain;
  public BarChart<?, ?> serviceLocationBarMain;
  public CategoryAxis xAxisMain;
  public NumberAxis yAxisMain;
  public CategoryAxis yAxisLoc;
  public NumberAxis xAxisLoc;
  public Label timeAvgMaint;

  // Transport Request
  public GridPane transportGrid;
  public BarChart<?, ?> completedChartTrans;
  public NumberAxis yAxisTrans;
  public PieChart pieChartTotalTrans;
  public CategoryAxis yAxisLocTrans;
  public NumberAxis xAxisLocTrans;
  public BarChart<?, ?> serviceLocationBarTrans;
  public CategoryAxis xAxisTrans;
  public Label timeAvgTrans;

  // Sanitation Request
  public BarChart<?, ?> completedChartSan;
  public CategoryAxis xAxisSan;
  public NumberAxis yAxisSan;
  public PieChart pieChartTotalSan;
  public BarChart<?, ?> serviceLocationBarSan;
  public CategoryAxis xAxisLocSan;
  public NumberAxis yAxisLocSan;
  public Label timeAvgSan;
  public GridPane sanPane;

  // Traffic

  public BarChart<?, ?> highTrafGraph;
  public CategoryAxis xAxisTraf;
  public NumberAxis yAxisTraf;
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

    // Maintenance

    // Transport
    List<String> data1;
    List<String> data2;
    XYChart.Series dataSeries1 = new XYChart.Series<>();
    XYChart.Series dataSeries2 = new XYChart.Series<>();
    data1 = serviceRequestStats.getMaintenanceEmployeeNumbersGraphs(mR);
    data2 = serviceRequestStats.getMaintenanceLocationNumbersGraphs(mR);
    String avgTime1 = serviceRequestStats.CalculateAverageMaintenanceTimeGraphs(mR);

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
    timeAvgMaint.setText(avgTime1);

    // Pie Chart to compared visually which employee did the most service requests
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    for (int i = 0; i < data1.size(); i += 2) {
      pieChartData.add(new PieChart.Data(data1.get(i), Integer.parseInt(data1.get(i + 1))));
    }
    pieChartTotalMain.setData(pieChartData);

    // Transport

    List<String> data3;
    List<String> data4;
    XYChart.Series dataSeries3 = new XYChart.Series<>();
    XYChart.Series dataSeries4 = new XYChart.Series<>();
    data3 = serviceRequestStats.getTransportEmployeeNumbersGraphs(tR);
    data4 = serviceRequestStats.getTransportLocationNumbersGraph(tR);
    String avgTime2 = serviceRequestStats.CalculateAverageTransportTimeGraph(tR);

    // completed Graph to show number of completed requests per employee

    for (int i = 0; i < data3.size(); i += 2) {
      dataSeries3
          .getData()
          .add(new XYChart.Data<>(data3.get(i), Integer.parseInt(data3.get(i + 1))));
    }

    for (int i = 0; i < data4.size(); i += 2) {
      dataSeries4
          .getData()
          .add(new XYChart.Data<>(data4.get(i), Integer.parseInt(data4.get(i + 1))));
    }

    completedChartTrans.getData().add(dataSeries3);
    serviceLocationBarTrans.getData().add(dataSeries4);
    timeAvgTrans.setText(avgTime2);

    // Pie Chart to compared visually which employee did the most service requests
    ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList();
    for (int i = 0; i < data3.size(); i += 2) {
      pieChartData2.add(new PieChart.Data(data3.get(i), Integer.parseInt(data3.get(i + 1))));
    }
    pieChartTotalTrans.setData(pieChartData2);

    // Sanitation
    List<String> data17;
    List<String> data18;
    XYChart.Series dataSeries17 = new XYChart.Series<>();
    XYChart.Series dataSeries18 = new XYChart.Series<>();
    data17 = serviceRequestStats.getSanitationEmployeeNumbersGraphs(sR);
    data18 = serviceRequestStats.getSanitationLocationNumbersGraphs(sR);

    // completed Graph to show number of completed requests per employee

    for (int i = 0; i < data17.size(); i += 2) {
      dataSeries17
          .getData()
          .add(new XYChart.Data<>(data17.get(i), Integer.parseInt(data17.get(i + 1))));
    }

    for (int i = 0; i < data18.size(); i += 2) {
      dataSeries18
          .getData()
          .add(new XYChart.Data<>(data18.get(i), Integer.parseInt(data18.get(i + 1))));
    }

    completedChartSan.getData().add(dataSeries17);
    serviceLocationBarSan.getData().add(dataSeries18);
    timeAvgSan.setText("Not Calculated Yet.");

    // Pie Chart to compared visually which employee did the most service requests
    ObservableList<PieChart.Data> pieChartData9 = FXCollections.observableArrayList();
    for (int i = 0; i < data17.size(); i += 2) {
      pieChartData9.add(new PieChart.Data(data17.get(i), Integer.parseInt(data17.get(i + 1))));
    }
    pieChartTotalSan.setData(pieChartData9);

    // High traffic Nodes areas
    List<String> trafData;
    XYChart.Series trafDataSer = new XYChart.Series<>();
    trafData = serviceRequestStats.getTimesVisitedGraphs(rC);

    for (int i = 0; i < trafData.size(); i += 2) {
      trafDataSer
          .getData()
          .add(new XYChart.Data<>(trafData.get(i), Integer.parseInt(trafData.get(i + 1))));
    }
    highTrafGraph.getData().add(trafDataSer);
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
