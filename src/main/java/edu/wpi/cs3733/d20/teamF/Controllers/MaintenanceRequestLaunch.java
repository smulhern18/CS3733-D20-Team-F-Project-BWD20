package edu.wpi.cs3733.d20.teamF.Controllers;

import edu.wpi.cs3733.d20.teamF.ModelClasses.ServiceException;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MaintenanceRequestLaunch {
  private int xCoord;
  private int yCoord;
  private int windowWidth;
  private int windowLength;
  private String cssPath;
  private String destNodeID;
  private String originNodeID;

  private Scene scene;

  private Stage primaryStage;

  private Parent root;

  public MaintenanceRequestLaunch() {}

  public void Initialize() throws IOException {

    FXMLLoader loader =
        new FXMLLoader(
            getClass().getResource("/edu/wpi/cs3733/d20/teamF/Views/MaintenenceRequest.fxml"));
    root = loader.load();
    scene = new Scene(root, (double) windowWidth, (double) windowLength);
    primaryStage = new Stage();
    primaryStage.setX((double) xCoord);
    primaryStage.setY((double) yCoord);
    primaryStage.setScene(scene);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.show();
  }
  /** @param cssPath can be null, we provide default CSS file */
  public void run(
      int xcoord,
      int ycoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String doNothing,
      String withThese)
      throws ServiceException {
    if (xcoord >= 0 && ycoord >= 0 && windowWidth >= 0 && windowLength >= 0) {
      if (xcoord > windowWidth) {
        throw new ServiceException("xcoord is out of the bounds.");
      } else if (ycoord > windowLength) {
        throw new ServiceException("ycoord is out of the bounds.");
      }
      this.xCoord = xcoord;
      this.yCoord = ycoord;
      this.windowWidth = windowWidth;
      this.windowLength = windowLength;
      try {
        this.Initialize();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      throw new ServiceException("There cannot be a negative value in the parameter of run().");
    }
  }
}
