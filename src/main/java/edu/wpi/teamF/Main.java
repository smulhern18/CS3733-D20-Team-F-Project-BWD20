package edu.wpi.teamF;

import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Admin;

public class Main {
  private static CSVManipulator csvm = new CSVManipulator();
  private static DatabaseManager dbm = new DatabaseManager();

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
    AccountFactory accounts = AccountFactory.getFactory();
    csvm.readCSVFileNode(Main.class.getResourceAsStream("CSVFiles/MapFAllnodes.csv"));
    csvm.readCSVFileEdge(Main.class.getResourceAsStream("CSVFiles/MapFAlledges.csv"));
    if (accounts.read("admin") == null) {
      accounts.create(new Admin("admin", "admin", "admin@gmail.com", "admin", "password"));
    }
  }
}
