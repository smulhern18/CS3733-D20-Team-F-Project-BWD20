package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
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
  MaintenanceRequestFactory maintenanceRequestFactory = MaintenanceRequestFactory.getFactory();
  // outputs all of the statistics of a maintenance request

  public void MaintenanceRequestStats(Path path) {
    List<MaintenanceRequest> maintenanceRequestList =
        maintenanceRequestFactory.getAllMaintenanceRequests();
    try (FileWriter fw = new FileWriter(path.toString() + "/MaintenanceStatistics.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {
      bw.write("EmployeeName,NumberOfRequestsAssigned");
      ArrayList<String> stats = new ArrayList<String>();
      stats = getEmployeeNumbers(maintenanceRequestList);
      for (String s : stats) {
        bw.newLine();
        bw.write(s);
      }
      bw.newLine();
      bw.write("NodeID,NumberOfRequestsAtLocation");
      stats = getLocationNumbers(maintenanceRequestList);
      for (String s : stats) {
        bw.newLine();
        bw.write(s);
      }

    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }

  private ArrayList<String> getEmployeeNumbers(List<MaintenanceRequest> maintenanceRequests) {
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

  private ArrayList<String> getLocationNumbers(List<MaintenanceRequest> maintenanceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (MaintenanceRequest m : maintenanceRequests) {
      nodeNum.add(m.getLocation().getId());
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
}
