package edu.wpi.cs3733.d20.teamF;

import edu.wpi.cs3733.d20.teamF.Controllers.MaintenanceRequestLaunch;
import edu.wpi.cs3733.d20.teamF.ModelClasses.ServiceException;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {}

  @Override
  public void start(Stage primaryStage) throws IOException {
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              new MaintenanceRequestLaunch().run(550, 100, 900, 600, null, "seriously", "nojoke");
            } catch (ServiceException e) {
              e.printStackTrace();
            }
          }
        });
  }

  @Override
  public void stop() {}
}
