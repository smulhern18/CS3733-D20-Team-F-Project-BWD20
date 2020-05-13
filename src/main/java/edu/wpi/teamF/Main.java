package edu.wpi.teamF;

import edu.wpi.teamF.DatabaseManipulators.*;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.Admin;
import java.util.List;

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

    // csvm.readCSVFileNode(Main.class.getResourceAsStream("CSVFiles/MapFAllnodes.csv"));
    // csvm.readCSVFileEdge(Main.class.getResourceAsStream("CSVFiles/MapFAlledges.csv"));

    // csvm.readCSVFileAccount(Main.class.getResourceAsStream("CSVFiles/Accounts.csv"));
    dbm.manipulateAccount(
        new Admin("admin2", "admin2", "admasdin@gmail.com", "admin2", "password"));
    dbm.manipulateAccount(new Admin("admin", "admin", "admin@gmail.com", "admin", "password"));
    List<Account> accounts = dbm.getAllAccounts();
    if (dbm.readAccount("admin") == null) {
      dbm.manipulateAccount(new Admin("admin", "admin", "admin@gmail.com", "admin", "password"));
    }
  }
}
