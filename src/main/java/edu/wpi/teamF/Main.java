package edu.wpi.teamF;

import edu.wpi.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

  public static void main(String[] args) throws FileNotFoundException {

    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver Not found");
    }

    DatabaseManager databaseManager = new DatabaseManager();
    databaseManager.initialize();
    CSVManipulator newManipulator = new CSVManipulator();
    newManipulator.readCSVFileNode(
        new FileInputStream(
            "C:\\Users\\rfcar\\IdeaProjects\\CS3733-D20-Team-F-Project-BWD20\\src\\main\\resources\\edu\\wpi\\teamF\\CSVFiles\\MapFAllnodes.csv"));
    newManipulator.readCSVFileEdge(
        new FileInputStream(
            "C:\\Users\\rfcar\\IdeaProjects\\CS3733-D20-Team-F-Project-BWD20\\src\\main\\resources\\edu\\wpi\\teamF\\CSVFiles\\MapFAlledges.csv"));

    App.launch(App.class, args);
  }
}
