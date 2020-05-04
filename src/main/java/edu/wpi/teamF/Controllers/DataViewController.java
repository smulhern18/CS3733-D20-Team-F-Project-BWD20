package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestStats;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

public class DataViewController implements Initializable {

  public BarChart<?, ?> completedChart;
  public NumberAxis yAxis;
  public CategoryAxis xAxis;
  public PieChart pieChartTotal;
  ServiceRequestStats serviceRequestStats = new ServiceRequestStats();
  DatabaseManager databaseManager = DatabaseManager.getManager();
  public List<MaintenanceRequest> mR = databaseManager.getAllMaintenanceRequests();

  public DataViewController() throws Exception {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // completed Graph to show number of completed maintenance  requests per employee
    List<String> data = serviceRequestStats.getMaintenanceEmployeeNumbersGraphs(mR);

    XYChart.Series dataSeries1 = new XYChart.Series<>();
    dataSeries1.setName("Maintenance Requests");

    for (int i = 0; i < data.size(); i += 2) {
      dataSeries1.getData().add(new XYChart.Data<>(data.get(i), Integer.parseInt(data.get(i + 1))));
    }

    completedChart.getData().add(dataSeries1);

    // Pie Chart to compared visually which employee did the most service requests
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    for (int i = 0; i < data.size(); i += 2) {
      pieChartData.add(new PieChart.Data(data.get(i), Integer.parseInt(data.get(i + 1))));
    }
    pieChartTotal.setData(pieChartData);
  }
}
