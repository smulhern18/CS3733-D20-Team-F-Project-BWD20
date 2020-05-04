package edu.wpi.cs3733.d20.teamF;

<<<<<<< HEAD:src/main/java/edu/wpi/cs3733/d20/teamF/Main.java
import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.*;
=======
import edu.wpi.cs3733.d20.teamF.App;
import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.*;
>>>>>>> 8fa2e494a687483df95e4e676e255de962391745:src/main/java/edu/wpi/teamF/Main.java

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
