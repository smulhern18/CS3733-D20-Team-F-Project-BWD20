package edu.wpi.cs3733.d20.teamF;

import edu.wpi.cs3733.d20.teamF.App;
import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.*;

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

    App.launch(App.class, args);
  }

  public static void initDB() throws Exception {
    dbm.initialize();
  }
}
