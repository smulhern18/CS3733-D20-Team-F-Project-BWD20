package edu.wpi.cs3733.d20.teamF.DatabaseManipulators;

import edu.wpi.cs3733.d20.teamF.ModelClasses.MaintenanceRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ServiceRequestStats {
  ServiceRequestFactory maintenanceRequestFactory = ServiceRequestFactory.getFactory();
  // outputs all of the statistics of a maintenance request
  public void downloadStatistics(Path path) {
    MaintenanceRequestStats(path);
  }

  public void MaintenanceRequestStats(Path path) {
    List<MaintenanceRequest> maintenanceRequestList =
        maintenanceRequestFactory.getAllMaintenanceRequests();
    if (maintenanceRequestList.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv");
          BufferedWriter bw = new BufferedWriter(fw)) {
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getMaintenanceEmployeeNumbers(maintenanceRequestList);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getMaintenanceLocationNumbers(maintenanceRequestList);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("Average time to complete request,");
        bw.write(CalculateAverageMaintenanceTime(maintenanceRequestList));

      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getMaintenanceEmployeeNumbers(
      List<MaintenanceRequest> maintenanceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (MaintenanceRequest m : maintenanceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey() + "," + entry.getValue());
      }
    }

    return csvStyled;
  }

  private ArrayList<String> getMaintenanceLocationNumbers(
      List<MaintenanceRequest> maintenanceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (MaintenanceRequest m : maintenanceRequests) {
      nodeNum.add(m.getLocation());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey() + "," + entry.getValue());
      }
    }

    return csvStyled;
  }

  private String CalculateAverageMaintenanceTime(List<MaintenanceRequest> maintenanceRequest) {
    String total = "";
    Long timeDifference = (long) 0;
    int numOfRequests = 0;
    for (MaintenanceRequest m : maintenanceRequest) {
      if (m.getTimeCompleted() != null) {
        timeDifference = m.getTimeCompleted().getTime() - m.getDateTimeSubmitted().getTime();
        numOfRequests++;
      }
    }
    if (timeDifference == 0) {
      total = "Your average time was 0";
    } else {
      timeDifference = timeDifference / (long) numOfRequests / 1000;
      total = timeDifference / 3600 + " hours";
      timeDifference = timeDifference % 3600;
      total = total + " " + timeDifference / 60 + " Minutes";
    }
    return total;
  }
}
