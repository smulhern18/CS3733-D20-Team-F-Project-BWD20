package edu.wpi.teamF;

import edu.wpi.teamF.factories.DatabaseManager;

public class Main {

  static DatabaseManager databaseInitializer = new DatabaseManager();

  public static void main(String[] args) {

    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver Not found");
    }
    databaseInitializer.initialize();

    App.launch(App.class, args);
  }
}
