package edu.wpi.teamF.Controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestStats;
import edu.wpi.teamF.ModelClasses.ServiceRequest.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
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

  // Sanitation Requests

  public BarChart<?, ?> barSaniLoc;
  public CategoryAxis xAxisLocSan;
  public NumberAxis yAxisLocSan;
  public BarChart<?, ?> barSanCom;
  public CategoryAxis xAxisEmpTrans;
  public NumberAxis yAxisEmpTrans;
  public PieChart pieChartEmpSan;
  public PieChart pieChartCompSan;
  public GridPane mainGridPane;
  public GridPane transGridPane;
  public GridPane sanGridPane;

  // High traffic
  public BarChart<?, ?> barCharHigh;
  public CategoryAxis xAxisHigh;
  public NumberAxis yAxisHigh;
  public AnchorPane highTrafPane;

  ServiceRequestStats serviceRequestStats = new ServiceRequestStats();
  DatabaseManager databaseManager = DatabaseManager.getManager();
  public List<MaintenanceRequest> mR = databaseManager.getAllMaintenanceRequests();
  public List<TransportRequest> tR = databaseManager.getAllTransportRequests();
  public List<SanitationServiceRequest> sR = databaseManager.getAllSanitationRequests();
  public SceneController sceneController = App.getSceneController();
  DirectoryChooser downloadDir = new DirectoryChooser();
  FileChooser pdfChooser = new FileChooser();
  DirectoryChooser backup = new DirectoryChooser();
  public List<ReportsClass> reports = databaseManager.getAllReports();

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
    data1 = serviceRequestStats.top5(serviceRequestStats.getMaintenanceEmployeeNumbersGraphs(mR));
    data2 = serviceRequestStats.top5(serviceRequestStats.getMaintenanceLocationNumbersGraphs(mR));
    data3 = serviceRequestStats.top5(serviceRequestStats.maintenanceCompleted(mR));

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

    transComp.getData().add(dataSeries3);
    mostcomLocTrans.getData().add(dataSeries4);

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

    // Sanitation Request
    List<String> data7;
    List<String> data8;
    List<String> data9;
    XYChart.Series dataSeries5 = new XYChart.Series<>();
    XYChart.Series dataSeries6 = new XYChart.Series<>();

    data7 = serviceRequestStats.getSanitationEmployeeNumbersGraphs(sR);
    data8 = serviceRequestStats.getSanitationLocationNumbersGraphs(sR);
    data9 = serviceRequestStats.sanitationCompleted(sR);

    for (int i = 0; i < data7.size(); i += 2) {
      dataSeries5
          .getData()
          .add(new XYChart.Data<>(data7.get(i), Integer.parseInt(data7.get(i + 1))));
    }
    for (int i = 0; i < data8.size(); i += 2) {
      dataSeries6
          .getData()
          .add(new XYChart.Data<>(data8.get(i), Integer.parseInt(data8.get(i + 1))));
    }

    barSanCom.getData().add(dataSeries5);
    barSaniLoc.getData().add(dataSeries6);

    ObservableList<PieChart.Data> pieChartData4 = FXCollections.observableArrayList();
    for (int i = 0; i < data7.size(); i += 2) {
      pieChartData4.add(new PieChart.Data(data7.get(i), Integer.parseInt(data7.get(i + 1))));
    }
    pieChartEmpSan.setData(pieChartData4);

    ObservableList<PieChart.Data> pieChartData7 = FXCollections.observableArrayList();
    for (int i = 0; i < data9.size(); i += 2) {
      pieChartData7.add(new PieChart.Data(data9.get(i), Integer.parseInt(data9.get(i + 1))));
    }
    pieChartCompSan.setData(pieChartData7);

    List<String> dataHigh;
    XYChart.Series dataSeriesHigh = new XYChart.Series();
    dataHigh = serviceRequestStats.getTimesVisitedGraphs(reports);

    for (int i = 0; i < dataHigh.size(); i += 2) {
      dataSeriesHigh
          .getData()
          .add(new XYChart.Data<>(dataHigh.get(i), Integer.parseInt(dataHigh.get(i + 1))));
    }
    barCharHigh.getData().add(dataSeriesHigh);
  }

  public void back(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Accounts");
  }

  public void backupServiceRequests(ActionEvent actionEvent) {
    backup.setTitle("Select Where to Backup Database");
    File selDir = backup.showDialog(rootPane.getScene().getWindow());
    serviceRequestStats.downloadStatistics(selDir.toPath());
  }

  public void exportToPDF(ActionEvent actionEvent) {
    Document document = new Document();
    downloadDir.setTitle("Select where to Export PDF");
    File selDir = downloadDir.showDialog(rootPane.getScene().getWindow());

    String workingdir;
    String OS = (System.getProperty("os.name")).toUpperCase();

    if (OS.contains("WIN")) {
      workingdir = System.getenv("Appdata");
    } else {
      // in either case, we would start in the user's home directory
      workingdir = System.getProperty("user.home");
      // if we are on a Mac, we are not done, we look for "Application Support"
      workingdir += "/Library/Application Support/TeamFPDF";
    }
    // Maintenance Grid
    WritableImage image = mainGridPane.snapshot(new SnapshotParameters(), null);
    File file = new File(workingdir + "/MaintenanceData.png");
    // Transport Grid
    WritableImage image2 = transGridPane.snapshot(new SnapshotParameters(), null);
    File file2 = new File(workingdir + "/TransportData.png");
    // Sanitation Grid
    WritableImage image3 = sanGridPane.snapshot(new SnapshotParameters(), null);
    File file3 = new File(workingdir + "/SanitationGrid.png");
    WritableImage image4 = highTrafPane.snapshot(new SnapshotParameters(), null);
    File file4 = new File(workingdir + "/HighTraff.png");

    try {
      // Writing out to temporary folder
      ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
      System.out.println("Snapshot saved: " + file.getAbsolutePath());
      ImageIO.write(SwingFXUtils.fromFXImage(image2, null), "png", file2);
      System.out.println("Snapshot saved: " + file2.getAbsolutePath());
      ImageIO.write(SwingFXUtils.fromFXImage(image3, null), "png", file3);
      System.out.println("Snapshot saved: " + file3.getAbsolutePath());
      ImageIO.write(SwingFXUtils.fromFXImage(image4, null), "png", file4);

      FileOutputStream fos = new FileOutputStream(selDir.getAbsolutePath() + "/PDFREPORTSVIEW.pdf");
      PdfWriter writer = PdfWriter.getInstance(document, fos);
      // Scaling
      Image image1E = Image.getInstance(file.getAbsolutePath());
      // image1E.setAbsolutePosition(50f, 50f);
      image1E.scaleAbsolute(650, 320);
      Image image2E = Image.getInstance(file2.getAbsolutePath());
      // image2E.setAbsolutePosition(50f, 50f);
      image2E.scaleAbsolute(650, 320);
      Image image3E = Image.getInstance(file3.getAbsolutePath());
      // image3E.setAbsolutePosition(50f, 50f);
      image3E.scaleAbsolute(650, 320);

      Image image4E = Image.getInstance(file4.getAbsolutePath());
      image4E.scaleAbsolute(550, 220);

      Paragraph para1 = new Paragraph("Maintenance Request");
      para1.setAlignment(Element.ALIGN_CENTER);
      Paragraph para2 = new Paragraph("Transport Request");
      para2.setAlignment(Element.ALIGN_CENTER);
      Paragraph para3 = new Paragraph("Sanitation Request");
      para3.setAlignment(Element.ALIGN_CENTER);
      Paragraph para4 = new Paragraph("High Traffic Areas");
      para4.setAlignment(Element.ALIGN_CENTER);

      writer.open();
      document.open();
      document.add(para1);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(image1E);
      document.newPage();
      document.add(para2);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(image2E);
      document.newPage();
      document.add(para3);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(image3E);
      document.newPage();
      document.add(para4);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
      document.add(image4E);

      document.close();
      writer.close();

    } catch (Exception e) {
      System.out.println("Yeah something broke, fuck.");
    }
  }
}
