package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class ServiceRequestStats {
  MaintenanceRequestFactory maintenanceRequestFactory = MaintenanceRequestFactory.getFactory();
  //outputs all of the statistics of a maintenance request

  public void MaintenanceRequestStats(Path path){
    List<MaintenanceRequest> maintenanceRequestList = maintenanceRequestFactory.getAllMaintenanceRequests();


  }



  private ArrayList<String> getEmployeeNumbers(List<MaintenanceRequest> maintenanceRequests){
    ArrayList<String> employeeNum = new ArrayList<String>();

    for(MaintenanceRequest m: maintenanceRequests){
      employeeNum.add(m.get)

    }
    Map<String, Long> frequency =
        employeeNum.stream().collect(Collectors.groupingBy(Function.identity()))



      return employeeNum;

  }









}
