package edu.wpi.cs3733.d20.teamF;

import edu.wpi.cs3733.d20.teamF.Controllers.MaintenanceRequestController;
import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.DatabaseManager;
import javafx.application.Platform;

public class Main {
  private static CSVManipulator csvm = new CSVManipulator();
  private static DatabaseManager dbm = DatabaseManager.getManager();

  public static void main(String[] args) throws Exception {

    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      initDB();
    } catch (ClassNotFoundException e) {
      System.out.println("Driver Not found");
    }
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              new MaintenanceRequestController()
                  .run(550, 100, 900, 600, null, "seriously", "nojoke");
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
    App.launch(App.class, args);
  }

  public static void initDB() throws Exception {
    dbm.initialize();
  }
}
