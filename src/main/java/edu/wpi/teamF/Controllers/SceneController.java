package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

  public void switchScene(String aScene) throws IOException {
    Stage newstage = App.getPS();
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamF/Views/" + aScene + ".fxml"));
    Scene scene = new Scene(root);
    newstage.setScene(scene);
    newstage.show();
  }
}
