package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.FlowerRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LanguageServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LaundryServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MariachiRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MedicineDeliveryRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ReportsClass;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SanitationServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.TransportRequest;
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
  TransportRequestFactory transportRequestFactory = TransportRequestFactory.getFactory();
  ComputerServiceRequestFactory computerServiceRequestFactory =
      ComputerServiceRequestFactory.getFactory();
  FlowerServiceRequestFactory flowerServiceRequestFactory =
      FlowerServiceRequestFactory.getFactory();
  LanguageServiceRequestFactory languageServiceRequestFactory =
      LanguageServiceRequestFactory.getFactory();
  LaundryServiceRequestFactory laundryServiceRequestFactory =
      LaundryServiceRequestFactory.getFactory();
  MariachiRequestFactory mariachiRequestFactory = MariachiRequestFactory.getFactory();
  MedicineDeliveryRequestFactory medicineDeliveryRequestFactory =
      MedicineDeliveryRequestFactory.getFactory();
  SanitationServiceRequestFactory sanitationServiceRequestFactory =
      SanitationServiceRequestFactory.getFactory();
  SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
  ReportsFactory reportsFactory = ReportsFactory.getFactory();
  // outputs all of the statistics of a maintenance request
  public void downloadStatistics(Path path) {
    MaintenanceRequestStats(path);
    TransportRequestStats(path);
    ComputerRequestStats(path);
    FlowerRequestStats(path);
    LanguageRequestStats(path);
    LaundryRequestStats(path);
    MariachiRequestStats(path);
    MedicineRequestStats(path);
    SanitationRequestStats(path);
    SecurityRequestStats(path);
    ReportsRequestStats(path);
  }

  public void MaintenanceRequestStats(Path path) {
    List<MaintenanceRequest> maintenanceRequestList =
        maintenanceRequestFactory.getAllMaintenanceRequests();
    if (maintenanceRequestList.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv");
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("MaintenanceStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getMaintenanceEmployeeNumbersCSV(maintenanceRequestList);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getMaintenanceLocationNumbersCSV(maintenanceRequestList);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("Average time to complete request,");
        bw.write(CalculateAverageMaintenanceTimeCSV(maintenanceRequestList));
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getMaintenanceEmployeeNumbersCSV(
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

  private ArrayList<String> getMaintenanceLocationNumbersCSV(
      List<MaintenanceRequest> maintenanceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (MaintenanceRequest m : maintenanceRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  private String CalculateAverageMaintenanceTimeCSV(List<MaintenanceRequest> maintenanceRequest) {
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
      total = "No data present";
    } else {
      timeDifference = timeDifference / (long) numOfRequests / 1000;
      total = timeDifference / 3600 + " hours";
      timeDifference = timeDifference % 3600;
      total = total + " " + timeDifference / 60 + " Minutes";
    }
    return total;
  }

  public ArrayList<String> getMaintenanceEmployeeNumbersGraphs(
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
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getMaintenanceLocationNumbersGraphs(
      List<MaintenanceRequest> maintenanceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (MaintenanceRequest m : maintenanceRequests) {
      nodeNum.add(m.getLocation().getLongName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public String CalculateAverageMaintenanceTimeGraphs(List<MaintenanceRequest> maintenanceRequest) {
    String total = "";
    Long timeDifference = (long) 0;
    int numOfRequests = 0;
    for (MaintenanceRequest m : maintenanceRequest) {
      if (m.getTimeCompleted() != null) {
        timeDifference += m.getTimeCompleted().getTime() - m.getDateTimeSubmitted().getTime();
        numOfRequests++;
      }
    }
    if (timeDifference == 0) {
      total = "No data present";
    } else {
      total = "" + timeDifference / 60 / 1000 + " Minutes";
    }
    return total;
  }

  public ArrayList<String> maintenanceCompleted(List<MaintenanceRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (MaintenanceRequest m : requests) {
      if (m.getComplete()) {
        nodeNum.add("Complete");
      } else {
        nodeNum.add("Incomplete");
      }
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void TransportRequestStats(Path path) {
    List<TransportRequest> transportRequests = transportRequestFactory.getAllTransportRequests();
    if (transportRequests.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("TransportStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getTransportEmployeeNumbers(transportRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("Start Locations");
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getTransportLocationNumbers(transportRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("End Locations");
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getDestinationNumbers(transportRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("Average time to complete request,");
        bw.write(CalculateAverageTransportTime(transportRequests));
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getTransportEmployeeNumbers(List<TransportRequest> transportRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (TransportRequest m : transportRequests) {
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

  private ArrayList<String> getTransportLocationNumbers(List<TransportRequest> transportRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (TransportRequest m : transportRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  private ArrayList<String> getDestinationNumbers(List<TransportRequest> transportRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (TransportRequest m : transportRequests) {
      nodeNum.add(m.getDestination().getShortName());
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

  private String CalculateAverageTransportTime(List<TransportRequest> transportRequests) {
    String total = "";
    Long timeDifference = (long) 0;
    int numOfRequests = 0;
    for (TransportRequest m : transportRequests) {
      if (m.getDateTimeCompleted() != null) {
        timeDifference = m.getDateTimeCompleted().getTime() - m.getDateTimeSubmitted().getTime();
        numOfRequests++;
      }
    }
    if (timeDifference == 0) {
      total = "No data present";
    } else {
      timeDifference = timeDifference / (long) numOfRequests / 1000;
      total = timeDifference / 3600 + " hours";
      timeDifference = timeDifference % 3600;
      total = total + " " + timeDifference / 60 + " Minutes";
    }
    return total;
  }

  public ArrayList<String> getTransportEmployeeNumbersGraphs(
      List<TransportRequest> transportRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (TransportRequest m : transportRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getTransportLocationNumbersGraph(
      List<TransportRequest> transportRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (TransportRequest m : transportRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getDestinationNumbersGraph(List<TransportRequest> transportRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (TransportRequest m : transportRequests) {
      nodeNum.add(m.getDestination().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }
    return csvStyled;
  }

  public String CalculateAverageTransportTimeGraph(List<TransportRequest> transportRequests) {
    String total = "";
    Long timeDifference = (long) 0;
    int numOfRequests = 0;
    for (TransportRequest m : transportRequests) {
      if (m.getDateTimeCompleted() != null) {
        timeDifference = m.getDateTimeCompleted().getTime() - m.getDateTimeSubmitted().getTime();
        numOfRequests++;
      }
    }
    if (timeDifference == 0) {
      total = "No data present";
    } else {
      total = "" + timeDifference / 60 / 1000 + " Minutes";
    }
    return total;
  }

  public ArrayList<String> transportCompleted(List<TransportRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (TransportRequest m : requests) {
      if (m.getComplete()) {
        nodeNum.add("Complete");
      } else {
        nodeNum.add("Incomplete");
      }
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void ComputerRequestStats(Path path) {
    List<ComputerServiceRequest> computerServiceRequestList =
        computerServiceRequestFactory.getAllComputerRequests();
    if (computerServiceRequestList.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("ComputerStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getComputerEmployeeNumbersCSV(computerServiceRequestList);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getComputerLocationNumbersCSV(computerServiceRequestList);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getComputerEmployeeNumbersCSV(
      List<ComputerServiceRequest> computerServiceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ComputerServiceRequest m : computerServiceRequests) {
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

  private ArrayList<String> getComputerLocationNumbersCSV(
      List<ComputerServiceRequest> computerServiceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : computerServiceRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  public ArrayList<String> getComputerEmployeeNumbersGraphs(
      List<ComputerServiceRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getComputerLocationNumbersGraphs(
      List<ComputerServiceRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> computerCompleted(List<ComputerServiceRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ComputerServiceRequest m : requests) {
      nodeNum.add(Boolean.toString(m.getComplete()));
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void FlowerRequestStats(Path path) {
    List<FlowerRequest> flowerRequests = flowerServiceRequestFactory.getAllFlowerRequests();
    if (flowerRequests.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("FlowerStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getFlowerEmployeeNumbersCSV(flowerRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getFlowerLocationNumbersCSV(flowerRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getFlowerEmployeeNumbersCSV(List<FlowerRequest> flowerRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : flowerRequests) {
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

  private ArrayList<String> getFlowerLocationNumbersCSV(List<FlowerRequest> flowerRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : flowerRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  public ArrayList<String> getFlowerEmployeeNumbersGraphs(List<FlowerRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getFlowerLocationNumbersGraphs(List<FlowerRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> flowerCompleted(List<FlowerRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (FlowerRequest m : requests) {
      nodeNum.add(Boolean.toString(m.getComplete()));
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void LanguageRequestStats(Path path) {
    List<LanguageServiceRequest> serviceRequests =
        languageServiceRequestFactory.getAllLanguageRequests();
    if (serviceRequests.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("LanguageStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getLanguageEmployeeNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getLanguageLocationNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getLanguageEmployeeNumbersCSV(
      List<LanguageServiceRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
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

  private ArrayList<String> getLanguageLocationNumbersCSV(
      List<LanguageServiceRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  public ArrayList<String> getLanguageEmployeeNumbersGraphs(
      List<LanguageServiceRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getLanguageLocationNumbersGraphs(
      List<LanguageServiceRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> languageCompleted(List<LanguageServiceRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (LanguageServiceRequest m : requests) {
      nodeNum.add(Boolean.toString(m.getComplete()));
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void LaundryRequestStats(Path path) {
    List<LaundryServiceRequest> serviceRequests =
        laundryServiceRequestFactory.getAllLaundryRequests();
    if (serviceRequests.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("LaundryStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getLaundryEmployeeNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getLaundryLocationNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getLaundryEmployeeNumbersCSV(
      List<LaundryServiceRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
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

  private ArrayList<String> getLaundryLocationNumbersCSV(
      List<LaundryServiceRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  public ArrayList<String> getLaundryEmployeeNumbersGraphs(
      List<LaundryServiceRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getLaundryLocationNumbersGraphs(
      List<LaundryServiceRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> laundryCompleted(List<LaundryServiceRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (LaundryServiceRequest m : requests) {
      nodeNum.add(Boolean.toString(m.getComplete()));
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void MariachiRequestStats(Path path) {
    List<MariachiRequest> serviceRequests = mariachiRequestFactory.getAllMariachiRequest();
    if (serviceRequests.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("MariachiStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getMariachiEmployeeNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getMariachiLocationNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getMariachiEmployeeNumbersCSV(List<MariachiRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
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

  private ArrayList<String> getMariachiLocationNumbersCSV(List<MariachiRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  public ArrayList<String> getMariachiEmployeeNumbersGraphs(List<MariachiRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getMariachiLocationNumbersGraphs(List<MariachiRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> mariachiCompleted(List<MariachiRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (MariachiRequest m : requests) {
      nodeNum.add(Boolean.toString(m.getComplete()));
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void MedicineRequestStats(Path path) {
    List<MedicineDeliveryRequest> serviceRequests =
        medicineDeliveryRequestFactory.getAllMedicineDeliveryRequests();
    if (serviceRequests.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("MedicineStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getMedicineEmployeeNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getMedicineLocationNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getMedicineEmployeeNumbersCSV(
      List<MedicineDeliveryRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
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

  private ArrayList<String> getMedicineLocationNumbersCSV(
      List<MedicineDeliveryRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  public ArrayList<String> getMedicineEmployeeNumbersGraphs(
      List<MedicineDeliveryRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getMedicineLocationNumbersGraphs(
      List<MedicineDeliveryRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> medicineCompleted(List<MedicineDeliveryRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (MedicineDeliveryRequest m : requests) {
      nodeNum.add(Boolean.toString(m.getComplete()));
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void SanitationRequestStats(Path path) {
    List<SanitationServiceRequest> serviceRequests =
        sanitationServiceRequestFactory.getAllSanitationRequests();
    if (serviceRequests.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("SanitationStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getSanitationEmployeeNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getSanitationLocationNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getSanitationEmployeeNumbersCSV(
      List<SanitationServiceRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
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

  private ArrayList<String> getSanitationLocationNumbersCSV(
      List<SanitationServiceRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  public ArrayList<String> getSanitationEmployeeNumbersGraphs(
      List<SanitationServiceRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getSanitationLocationNumbersGraphs(
      List<SanitationServiceRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> sanitationCompleted(List<SanitationServiceRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (SanitationServiceRequest m : requests) {
      if (m.getComplete()) {
        nodeNum.add("Complete");
      } else {
        nodeNum.add("Incomplete");
      }
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void SecurityRequestStats(Path path) {
    List<SecurityRequest> serviceRequests = securityRequestFactory.getAllSecurityRequest();
    if (serviceRequests.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("SecurityStats");
        bw.newLine();
        bw.write("EmployeeName,NumberOfRequestsAssigned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getSecurityEmployeeNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,NumberOfRequestsAtLocation");
        stats = getSecurityLocationNumbersCSV(serviceRequests);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getSecurityEmployeeNumbersCSV(List<SecurityRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
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

  private ArrayList<String> getSecurityLocationNumbersCSV(List<SecurityRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
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

  public ArrayList<String> getSecurityEmployeeNumbersGraphs(List<SecurityRequest> serviceRequests) {
    ArrayList<String> employeeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      employeeNum.add(m.getAssignee());
    }
    Map<String, Long> frequency =
        employeeNum.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> getSecurityLocationNumbersGraphs(List<SecurityRequest> serviceRequests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ServiceRequest m : serviceRequests) {
      nodeNum.add(m.getLocation().getShortName());
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public ArrayList<String> securityCompleted(List<SecurityRequest> requests) {

    ArrayList<String> nodeNum = new ArrayList<String>();
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (SecurityRequest m : requests) {
      nodeNum.add(Boolean.toString(m.getComplete()));
    }
    Map<String, Long> frequency =
        nodeNum.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    for (Map.Entry<String, Long> entry : frequency.entrySet()) {
      if (entry.getValue() > 0) {
        csvStyled.add(entry.getKey());
        csvStyled.add("" + entry.getValue());
      }
    }

    return csvStyled;
  }

  public void ReportsRequestStats(Path path) {
    List<ReportsClass> reportsClasses = reportsFactory.getAllReports();
    if (reportsClasses.size() != 0) {
      try (FileWriter fw = new FileWriter(path.toString() + "/ServiceRequestReport.csv", true);
          BufferedWriter bw = new BufferedWriter(fw); ) {
        bw.write("Nodes Visited Report");
        bw.newLine();
        bw.write("NodeId,TimesVisitedSinceCleaned");
        ArrayList<String> stats = new ArrayList<String>();
        stats = getNodesVisited(reportsClasses);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
        bw.write("NodeID,TimesSanitized");
        stats = getTimesSanitized(reportsClasses);
        for (String s : stats) {
          bw.newLine();
          bw.write(s);
        }
        bw.newLine();
      } catch (IOException e) {
        System.out.println(e.getMessage() + "" + e.getClass());
      }
    }
  }

  private ArrayList<String> getNodesVisited(List<ReportsClass> reportsClasses) {
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ReportsClass r : reportsClasses) {
      csvStyled.add(r.getNodeID() + "," + r.getTimesVisited());
    }
    return csvStyled;
  }

  private ArrayList<String> getTimesSanitized(List<ReportsClass> reportsClasses) {

    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ReportsClass r : reportsClasses) {
      csvStyled.add(r.getNodeID() + "," + r.getTimesSanitized());
    }
    return csvStyled;
  }

  public ArrayList<String> getTimesVisitedGraphs(List<ReportsClass> reportsClasses) {
    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ReportsClass r : reportsClasses) {
      csvStyled.add(r.getNodeID());
      csvStyled.add("" + r.getTimesVisited());
    }
    return csvStyled;
  }

  public ArrayList<String> getTimesSanitizedGraphs(List<ReportsClass> reportsClasses) {

    ArrayList<String> csvStyled = new ArrayList<String>();
    for (ReportsClass r : reportsClasses) {
      csvStyled.add(r.getNodeID());
      csvStyled.add("" + r.getTimesSanitized());
    }
    return csvStyled;
  }

  public ArrayList<String> top5(ArrayList<String> data) {
    ArrayList<String> top5data = new ArrayList<String>();
    ArrayList<String> data1 = data;
    String temp1 = "";
    String temp2 = "";
    int top = 0;
    if (data.size() > 10) {
      while (top < 5) {
        temp1 = data.get(0);
        temp2 = data.get(1);
        for (int i = 2; i < data1.size(); i += 2) {
          if (Integer.parseInt(temp2) < Integer.parseInt(data1.get(i + 1))) {
            temp1 = data1.get(i);
            temp2 = data1.get(i + 1);
          }
        }
        top5data.add(temp1);
        top5data.add(temp2);
        data1.remove(temp1);
        data1.remove(temp2);
        top++;
      }
    } else {
      top5data = data;
    }
    return top5data;
  }
}
