package edu.wpi.cs3733.d20.teamF;

import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.CSVManipulator;
import edu.wpi.cs3733.d20.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.cs3733.d20.teamF.ModelClasses.ServiceException;
import javafx.application.Platform;

import java.io.IOException;

public class Main {
  private static CSVManipulator csvm = new CSVManipulator();
  private static DatabaseManager dbm = DatabaseManager.getManager();

  int xCoord = 0;
  int yCoord = 0;
  int windowWidth = 0;
  int windowLength = 0;
  String cssPath = "";

  public static void main(String[] args) throws Exception {

    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      initDB();
    } catch (ClassNotFoundException e) {
      System.out.println("Driver Not found");
    }
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        try {
          new TransportationService().run(550, 100, 900, 600, null, null, null);
        } catch (ServiceException e) {
          e.printStackTrace();
        }
      }
    });
    App.launch(App.class, args);
  }

  public static void initDB() throws Exception {
    dbm.initialize();
  }

  public void run(int xcoord, int ycoord, int windowWidth, int windowLength, String cssPath) throws Exception {
    String cssFile = cssPath;
    if (xcoord >= 0 && ycoord >= 0 && windowWidth >= 0 && windowLength >= 0) {
      if (xcoord > windowWidth) {
        throw new ServiceException("xcoord is out of the bounds.");
      } else if (ycoord > windowLength) {
        throw new ServiceException("ycoord is out of the bounds.");
      }
      this.xCoord = xcoord;
      this.yCoord = ycoord;
      this.windowWidth = windowWidth;
      this.windowLength = windowLength;
      if (cssFile != null) {
        this.cssPath = cssFile;
      } else this.cssPath = "/edu/wpi/cs3733/d19/teamE/api/FXML/maintenceStyle.css";
      try {
        this.initDB();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      throw new ServiceException("There cannot be a negative value in the parameter of run().");
    }
  }
}
