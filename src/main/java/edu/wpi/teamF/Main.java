package edu.wpi.teamF;

import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;

public class Main {
  private static CSVManipulator csvm = new CSVManipulator();
  private static DatabaseManager dbm = new DatabaseManager();

  public static void main(String[] args) {

    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      initDB();
    } catch (ClassNotFoundException e) {
      System.out.println("Driver Not found");
    }

    App.launch(App.class, args);
  }

  public static void initDB() {
    dbm.initialize();
    csvm.readCSVFileNode(Main.class.getResourceAsStream("CSVFiles/MapFAllnodes.csv"));
    csvm.readCSVFileEdge(Main.class.getResourceAsStream("CSVFiles/MapFAlledges.csv"));
  }
}
