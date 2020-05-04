package edu.wpi.cs3733.d20.teamF.DatabaseManipulators;

import edu.wpi.cs3733.d20.teamF.ModelClasses.MaintenanceRequest;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class CSVManipulator {
  private ServiceRequestFactory serviceRequestFactory = ServiceRequestFactory.getFactory();
  private DatabaseManager databaseManager = DatabaseManager.getManager();
  /**
   * reads a csv file that contains MaintenanceRequests and inserts the data in the file into the
   * correct place in the database
   */
  public void readCSVFileMaintenanceService(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 8;
      while (i < (data.size() - 1)) {
        databaseManager.manipulateServiceRequest(
            new MaintenanceRequest(
                data.get(i),
                data.get(i + 1),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                new Date(Integer.parseInt(data.get(i + 7)))));

        i = i + 8;
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File Not found!");
    } catch (EOFException e) {
      // Expected use to end read csv
    } catch (IOException e) {

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  /** Writes to the CSV file so that it can become persistant */
  public void writeCSVFileMaintenanceService(Path path) throws Exception {
    // writing to the file
    List<MaintenanceRequest> maintenanceRequests =
        serviceRequestFactory.getAllMaintenanceRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/MaintenanceBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw)) {

      bw.write(
          "id,location,assignee,description,dateTimeSubmitted,priority,complete,timeCompleted");

      for (MaintenanceRequest m : maintenanceRequests) {
        bw.newLine();
        bw.write((formatMaintenanceService(m)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
      // exception handling left as an exercise for the reader
    }
  }

  public String formatMaintenanceService(MaintenanceRequest m) {
    String Main = "";
    Main =
        m.getId()
            + ","
            + m.getLocation()
            + ","
            + m.getAssignee()
            + ","
            + m.getDescription()
            + ","
            + m.getDateTimeSubmitted().getTime()
            + ","
            + m.getPriority()
            + ","
            + m.getComplete()
            + ","
            + m.getTimeCompleted().getTime();
    return Main;
  }
}
