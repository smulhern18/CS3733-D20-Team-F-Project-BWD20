package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestStats;
import edu.wpi.teamF.ModelClasses.ServiceRequest.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

public class DataViewController implements Initializable {

  public BarChart<?, ?> completedChart;
  public NumberAxis yAxis;
  public CategoryAxis xAxis;
  public PieChart pieChartTotal;
  public CategoryAxis yAxisLoc;
  public NumberAxis xAxisLoc;
  public Label timeAvgMaint;
  public JFXComboBox<String> serviceChoice;
  public BarChart<?, ?> serviceLocationBar;
  ServiceRequestStats serviceRequestStats = new ServiceRequestStats();
  DatabaseManager databaseManager = DatabaseManager.getManager();
  public List<MaintenanceRequest> mR = databaseManager.getAllMaintenanceRequests();
  public List<TransportRequest> tR = databaseManager.getAllTransportRequests();
  public List<ComputerServiceRequest> cR = databaseManager.getAllComputerServiceRequests();
  public List<FlowerRequest> fR = databaseManager.getAllFlowerRequests();
  public List<LanguageServiceRequest> lR = databaseManager.getAllLanguageServiceRequests();
  public List<LaundryServiceRequest> lsR = databaseManager.getAllLaunduaryRequests();
  public List<MariachiRequest> maR = databaseManager.getAllMariachiServiceRequests();
  public List<MedicineDeliveryRequest> mdR = databaseManager.getAllMedicineDeliveryRequests();
  public List<SanitationServiceRequest> sR = databaseManager.getAllSanitationRequests();
  public List<SecurityRequest> secR = databaseManager.getAllSecurityRequests();
  public List<String> data;
  public List<String> data2;
  public XYChart.Series dataSeries1 = new XYChart.Series<>();
  public XYChart.Series dataSeries2 = new XYChart.Series<>();
  public String avgTime;

  public DataViewController() throws Exception {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    serviceChoice.getItems().add("Maintenance Request");
    serviceChoice.getItems().add("Transport Request");
    serviceChoice.getItems().add("Computer Request");
    serviceChoice.getItems().add("Flower Request");
    serviceChoice.getItems().add("Language Request");
    serviceChoice.getItems().add("Laundry Request");
    serviceChoice.getItems().add("Mariachi Request");
    serviceChoice.getItems().add("Medicine Delivery Request");
    serviceChoice.getItems().add("Sanitation Request");
    serviceChoice.getItems().add("Security Request");
  }

  public void loadGraphs(ActionEvent actionEvent) {
    String choice = serviceChoice.getValue();

    switch (choice) {
      case "Maintenance Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getMaintenanceEmployeeNumbersGraphs(mR);
        data2 = serviceRequestStats.getMaintenanceLocationNumbersGraphs(mR);
        avgTime = serviceRequestStats.CalculateAverageMaintenanceTimeGraphs(mR);
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Maintenance Requests");
        dataSeries2.setName("Locations for Maintenance");

        break;
      case "Transport Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getTransportEmployeeNumbersGraphs(tR);
        data2 = serviceRequestStats.getTransportLocationNumbersGraph(tR);
        avgTime = serviceRequestStats.CalculateAverageTransportTimeGraph(tR);
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Transport Requests");
        dataSeries2.setName("Locations for Transport");
        break;
      case "Computer Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getComputerEmployeeNumbersGraphs(cR);
        data2 = serviceRequestStats.getComputerLocationNumbersGraphs(cR);
        avgTime = "";
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Computer Service Requests");
        dataSeries2.setName("Locations for Computer Service");
        break;
      case "Flower Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getFlowerEmployeeNumbersGraphs(fR);
        data2 = serviceRequestStats.getFlowerLocationNumbersGraphs(fR);
        avgTime = "";
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Flower Requests");
        dataSeries2.setName("Locations for Flower");
        break;
      case "Language Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getLanguageEmployeeNumbersGraphs(lR);
        data2 = serviceRequestStats.getLanguageLocationNumbersGraphs(lR);
        avgTime = "";
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Language Requests");
        dataSeries2.setName("Locations for Language");
        break;
      case "Laundry Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getLaundryEmployeeNumbersGraphs(lsR);
        data2 = serviceRequestStats.getLaundryLocationNumbersGraphs(lsR);
        avgTime = "";
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Laundry Requests");
        dataSeries2.setName("Locations for Laundry");
        break;
      case "Mariachi Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getMariachiEmployeeNumbersGraphs(maR);
        data2 = serviceRequestStats.getMariachiLocationNumbersGraphs(maR);
        avgTime = "";
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Mariachi Requests");
        dataSeries2.setName("Locations for Mariachi");
        break;
      case "Medicine Delivery Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getMedicineEmployeeNumbersGraphs(mdR);
        data2 = serviceRequestStats.getMedicineLocationNumbersGraphs(mdR);
        avgTime = "";
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Medicine Delivery Requests");
        dataSeries2.setName("Locations for Medicine");
        break;
      case "Sanitation Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getSanitationEmployeeNumbersGraphs(sR);
        data2 = serviceRequestStats.getSanitationLocationNumbersGraphs(sR);
        avgTime = "";
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Sanitation Requests");
        dataSeries2.setName("Locations for Sanitation");
        break;
      case "Security Request":
        completedChart.getData().clear();
        serviceLocationBar.getData().clear();
        pieChartTotal.getData().clear();
        data = serviceRequestStats.getSecurityEmployeeNumbersGraphs(secR);
        data2 = serviceRequestStats.getSecurityLocationNumbersGraphs(secR);
        avgTime = "";
        dataSeries1.getData().clear();
        dataSeries2.getData().clear();
        dataSeries1.setName("");
        dataSeries2.setName("");
        dataSeries1.setName("Security Requests");
        dataSeries2.setName("Locations for Security");
        break;
    }
    showGraphs();
  }

  public void showGraphs() {

    // completed Graph to show number of completed maintenance  requests per employee

    for (int i = 0; i < data.size(); i += 2) {
      dataSeries1.getData().add(new XYChart.Data<>(data.get(i), Integer.parseInt(data.get(i + 1))));
    }

    for (int i = 0; i < data2.size(); i += 2) {
      dataSeries2
          .getData()
          .add(new XYChart.Data<>(data2.get(i), Integer.parseInt(data2.get(i + 1))));
    }

    completedChart.getData().add(dataSeries1);
    serviceLocationBar.getData().add(dataSeries2);
    timeAvgMaint.setText(avgTime);

    // Pie Chart to compared visually which employee did the most service requests
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    for (int i = 0; i < data.size(); i += 2) {
      pieChartData.add(new PieChart.Data(data.get(i), Integer.parseInt(data.get(i + 1))));
    }
    pieChartTotal.setData(pieChartData);
  }
}
