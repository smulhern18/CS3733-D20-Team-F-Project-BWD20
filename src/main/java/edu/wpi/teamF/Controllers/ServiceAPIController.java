package edu.wpi.teamF.Controllers;

import edu.wpi.cs3733.d20.teamB.api.IncidentReportApplication;
import edu.wpi.cs3733.d20.teamC.InterpreterRequest;
import edu.wpi.cs3733.d20.teamM.AudioVisualRequest;
import edu.wpi.cs3733.d20.teamM.ServiceException;
import edu.wpi.cs3733.d20.teamP.APIController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ServiceAPIController implements Initializable {

  public ImageView backgroundImage;
  public AnchorPane anchorPane;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    backgroundImage.fitWidthProperty().bind(anchorPane.widthProperty());
    backgroundImage.fitHeightProperty().bind(anchorPane.heightProperty());
  }

  public void runIncidentReport() {
    IncidentReportApplication.run(0, 0, 600, 800, null, null, null);
  }

  public void runAudioVisual() throws ServiceException {
    AudioVisualRequest.run(0, 0, 600, 800, null, null, null);
  }

  public void runFoodRequest() throws edu.wpi.cs3733.d20.teamP.ServiceException {
    APIController.run(0, 0, 600, 800, null, null, null);
  }

  public void runInterpreterRequest() throws IOException {
    InterpreterRequest.run(0, 0, 600, 800, null, null, null);
  }
}
