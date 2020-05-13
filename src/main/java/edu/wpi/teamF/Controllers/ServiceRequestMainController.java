package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamR.AppointmentRequest;
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
  public JFXButton apiButton;

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
  // Text labels
  public Label lanLabel;
  public Label laundryLabel;
  public Label flowerLabel;
  public Label medLabel;
  public Label compLabel;
  public Label transLabel;
  public Label marLabel;
  public Label maintLabel;
  public Label appLabel;
  public Label roomLabel;

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
    sceneController.switchScene("RoomSchedule");
  }

  public void switchToAPIRequests(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("ServiceAPI");
  }

  public void appointmentRequest(MouseEvent mouseEvent) {
    try {
      AppointmentRequest.run(500, 50, 1000, 1000, null, null, null);
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
      compImage.setVisible(false);
      compLabel.setVisible(false);
      medImage.setDisable(true);
      medImage.setVisible(false);
      medLabel.setVisible(false);
      lanImage.setDisable(true);
      lanImage.setVisible(false);
      lanLabel.setVisible(false);
      sanImage.setDisable(false);
      secImage.setDisable(false);
      laundryImage.setDisable(true);
      laundryImage.setVisible(false);
      laundryLabel.setVisible(false);
      flowImage.setDisable(true);
      flowImage.setVisible(false);
      flowerLabel.setVisible(false);
      transportImage.setDisable(true);
      transportImage.setVisible(false);
      transLabel.setVisible(false);
      marImage.setDisable(true);
      marImage.setVisible(false);
      marLabel.setVisible(false);
      mainImage.setDisable(true);
      mainImage.setVisible(false);
      maintLabel.setVisible(false);
      appointmentsImage.setDisable(true);
      appointmentsImage.setVisible(false);
      appLabel.setVisible(false);
      roomSchedulerImage.setDisable(true);
      roomSchedulerImage.setVisible(false);
      roomLabel.setVisible(false);

    } else if (userLevel == Account.Type.USER) {
      compImage.setDisable(true);
      compImage.setVisible(false);
      compLabel.setVisible(false);
      medImage.setDisable(true);
      medImage.setVisible(false);
      medLabel.setVisible(false);
      lanImage.setDisable(false);
      lanImage.setVisible(true);
      sanImage.setDisable(false);
      sanImage.setVisible(true);
      secImage.setDisable(false);
      secImage.setVisible(true);
      laundryImage.setDisable(true);
      laundryImage.setVisible(false);
      laundryLabel.setVisible(false);
      flowImage.setDisable(false);
      flowImage.setVisible(true);
      transportImage.setDisable(true);
      transportImage.setVisible(false);
      transLabel.setVisible(false);
      marImage.setDisable(false);
      marImage.setVisible(true);
      mainImage.setDisable(true);
      mainImage.setVisible(false);
      maintLabel.setVisible(false);
      appointmentsImage.setDisable(true);
      appointmentsImage.setVisible(false);
      appLabel.setVisible(false);
      roomSchedulerImage.setDisable(true);
      roomSchedulerImage.setVisible(false);
      roomLabel.setVisible(false);

    } else if (userLevel == Account.Type.STAFF || userLevel == Account.Type.ADMIN) {
      compImage.setDisable(false);
      compImage.setVisible(true);
      compLabel.setVisible(true);
      medImage.setDisable(false);
      medImage.setVisible(true);
      medLabel.setVisible(true);
      lanImage.setDisable(false);
      lanImage.setVisible(true);
      lanLabel.setVisible(true);
      sanImage.setDisable(false);
      sanImage.setVisible(true);
      secImage.setDisable(false);
      secImage.setVisible(true);
      laundryImage.setDisable(false);
      laundryImage.setVisible(true);
      laundryLabel.setVisible(true);
      flowImage.setDisable(false);
      flowImage.setVisible(true);
      flowerLabel.setVisible(true);
      transportImage.setDisable(false);
      transportImage.setVisible(true);
      transLabel.setVisible(true);
      marImage.setDisable(false);
      marImage.setVisible(true);
      marLabel.setVisible(true);
      mainImage.setVisible(true);
      mainImage.setDisable(false);
      maintLabel.setVisible(true);
      appointmentsImage.setDisable(false);
      appointmentsImage.setVisible(true);
      appLabel.setVisible(true);
      roomSchedulerImage.setDisable(false);
      roomSchedulerImage.setVisible(true);
      roomLabel.setVisible(true);
    }
    // do new permissions
    // non-logged in have only access to sanitation and security requests

    // logged in of type USER can request everything but Transport and Maintenance

    // Staff and Admin can request all
  }
}
