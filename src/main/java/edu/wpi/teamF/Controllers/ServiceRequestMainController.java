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
import javafx.scene.layout.AnchorPane;
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

  // Images
  public ImageView compImage;
  public ImageView medImage;
  public ImageView lanImage;
  public ImageView sanImage;
  public ImageView marImage;
  public ImageView secImage;
  public ImageView laundryImage;
  public ImageView flowImage;
  public ImageView transportImage;
  public ImageView mainImage;
  public ImageView appointmentsImage;
  public ImageView roomSchedulerImage;
  public ImageView backgroundImage;
  public AnchorPane anchorPane;

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

  public void switchToRoomScheduler(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("RoomSceduler");
  }

  public void switchToAppointments(MouseEvent mouseEvent) throws IOException {
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
    backgroundImage.fitWidthProperty().bind(anchorPane.widthProperty());
    backgroundImage.fitHeightProperty().bind(anchorPane.heightProperty());

    Account.Type userLevel = dbm.getPermissions();
    if (userLevel == null) {
      // disable everything but sanitation and security request
      compImage.setDisable(true);
      compImage.setOpacity(.25);
      medImage.setDisable(true);
      medImage.setOpacity(.25);
      lanImage.setDisable(true);
      lanImage.setOpacity(.25);
      sanImage.setDisable(false);
      secImage.setDisable(false);
      laundryImage.setDisable(true);
      laundryImage.setOpacity(.25);
      flowImage.setDisable(true);
      flowImage.setOpacity(.25);
      transportImage.setDisable(true);
      transportImage.setOpacity(.25);
      marImage.setDisable(true);
      marImage.setOpacity(.25);
      mainImage.setDisable(true);
      mainImage.setOpacity(.25);
      appointmentsImage.setDisable(true);
      appointmentsImage.setOpacity(.25);
      roomSchedulerImage.setDisable(true);
      roomSchedulerImage.setOpacity(.25);

    } else if (userLevel == Account.Type.USER) {
      compImage.setDisable(true);
      compImage.setOpacity(.25);
      medImage.setDisable(true);
      medImage.setOpacity(.25);
      lanImage.setDisable(false);
      lanImage.setOpacity(1);
      sanImage.setDisable(false);
      sanImage.setOpacity(1);
      secImage.setDisable(false);
      secImage.setOpacity(1);
      laundryImage.setDisable(true);
      flowImage.setDisable(false);
      flowImage.setOpacity(1);
      transportImage.setDisable(true);
      transportImage.setOpacity(.25);
      marImage.setDisable(false);
      marImage.setOpacity(1);
      mainImage.setDisable(true);
      mainImage.setOpacity(.25);
      appointmentsImage.setDisable(true);
      appointmentsImage.setOpacity(.25);
      roomSchedulerImage.setDisable(true);
      roomSchedulerImage.setOpacity(.25);

    } else if (userLevel == Account.Type.STAFF || userLevel == Account.Type.ADMIN) {
      compImage.setDisable(false);
      compImage.setOpacity(1);
      medImage.setDisable(false);
      medImage.setOpacity(1);
      lanImage.setDisable(false);
      lanImage.setOpacity(1);
      sanImage.setDisable(false);
      sanImage.setOpacity(1);
      secImage.setDisable(false);
      secImage.setOpacity(1);
      laundryImage.setDisable(false);
      laundryImage.setOpacity(1);
      flowImage.setDisable(false);
      flowImage.setOpacity(1);
      transportImage.setDisable(false);
      transportImage.setOpacity(1);
      marImage.setDisable(false);
      mainImage.setOpacity(1);
      mainImage.setDisable(false);
      mainImage.setOpacity(1);
      appointmentsImage.setDisable(false);
      appointmentsImage.setOpacity(1);
      roomSchedulerImage.setDisable(false);
      roomSchedulerImage.setOpacity(1);
    }
    // do new permissions
    // non-logged in have only access to sanitation and security requests

    // logged in of type USER can request everything but Transport and Maintenance

    // Staff and Admin can request all
  }
}
