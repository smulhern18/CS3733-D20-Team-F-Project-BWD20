package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import java.io.IOException;
import javafx.scene.input.MouseEvent;

public class ServiceRequestMainController {
  SceneController sceneController = App.getSceneController();

  public void switchToComputerService(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("ComputerServiceRequest");
  }
}
