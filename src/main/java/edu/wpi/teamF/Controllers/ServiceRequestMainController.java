package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ServiceRequestMainController implements Initializable {
  public Label serviceRequestLabel;
  public GridPane gridPaneAll;
  public ImageView handPic;
  public JFXButton sanText;
  public ImageView secHEad;
  public JFXButton secText;
  public GridPane gridUser;
  DatabaseManager dbm = DatabaseManager.getManager();
  SceneController sceneController = App.getSceneController();

  public void switchToComputerService(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("ComputerServiceRequest");
  }

  public void medicineService(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("MedicineDeliveryRequest");
  }

  public void languageService(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("LanguageServiceController2");
  }

  public void sanitationRequest(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("SanitationServiceRequest");
  }

  public void mariachiService(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("MariachiRequest");
  }

  public void securityRequest(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("SecurityRequest");
  }

  public void switchToLaundryService(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("LaundryServiceRequest");
  }

  public void flowerRequest(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("FlowerRequestInfo");
  }

  public void maintenanceRequest(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("MaintenenceRequest");
  }

  public void transportRequest(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("TransportRequest");
  }

  public void appointmentRequest(MouseEvent mouseEvent) {
    try {
      // AppointmentRequest.run(500, 50, 1000, 1000, null, null, null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void resize(double width) {
    System.out.println(width);
    Font newFont = new Font(width / 50);
    serviceRequestLabel.setFont(newFont);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    Account.Type userLevel = dbm.getPermissions();
    // do new permissions
    // non-logged in have only access to sanitation and security requests

    // logged in of type USER can request everything but Transport and Maintenance

    // Staff and Admin can request all
  }
}
