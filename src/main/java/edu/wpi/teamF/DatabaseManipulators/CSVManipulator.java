package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.Admin;
import edu.wpi.teamF.ModelClasses.Account.Janitor;
import edu.wpi.teamF.ModelClasses.Account.Nurse;
import edu.wpi.teamF.ModelClasses.Account.Patient;
import edu.wpi.teamF.ModelClasses.Account.Staff;
import edu.wpi.teamF.ModelClasses.Account.User;
import edu.wpi.teamF.ModelClasses.MaintenanceRequest;

import java.io.*;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;

public class CSVManipulator {
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();
  private MaintenanceRequestFactory maintenanceRequestFactory =
      MaintenanceRequestFactory.getFactory();
  private SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
  private AccountFactory accountFactory = AccountFactory.getFactory();
  private DatabaseManager databaseManager = DatabaseManager.getManager();
  private MariachiRequestFactory mariachiRequestFactory = MariachiRequestFactory.getFactory();
  private ComputerServiceRequestFactory computerServiceRequestFactory =
      ComputerServiceRequestFactory.getFactory();
  private FlowerServiceRequestFactory flowerServiceRequestFactory =
      FlowerServiceRequestFactory.getFactory();
  private LanguageServiceRequestFactory languageServiceRequestFactory =
      LanguageServiceRequestFactory.getFactory();
  private LaundryServiceRequestFactory laundryServiceRequestFactory =
      LaundryServiceRequestFactory.getFactory();
  private MedicineDeliveryRequestFactory medicineDeliveryRequestFactory =
      MedicineDeliveryRequestFactory.getFactory();
  private SanitationServiceRequestFactory sanitationServiceRequestFactory =
      SanitationServiceRequestFactory.getFactory();
  private TransportRequestFactory transportRequestFactory = TransportRequestFactory.getFactory();
  /**
   * reads a csv file that contains nodes and inserts the data in the file into the correct place in
   * the database
   */
  public void readCSVFileNode(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 9;
      while (i < (data.size() - 1)) {
        Node node =
            new Node(
                data.get(i), // nodeID
                Short.parseShort(data.get(i + 1)), // xcoord
                Short.parseShort(data.get(i + 2)), // ycoord
                data.get(i + 4), // building
                data.get(i + 6), // longname
                data.get(i + 7), // shortname
                Node.NodeType.getEnum(data.get(i + 5)), // nodetype
                data.get(i + 3)); // floor
        System.out.println("Created Node on line " + i / 9 + ", " + node.getId());
        try {
          nodeFactory.create(node);
        } catch (SQLException e) {
          // ignore
        } catch (Exception e) {
          System.out.println(e.getMessage() + ", " + e.getClass());
        }
        i = i + 9;
      }

    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File Not found!");
    } catch (EOFException e) {
      // Expected use to end read csv
    } catch (IOException e) {

    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println(e.getClass());
    }
  }

  /** Writes the nodes to the CSV file so that it can become persistant */
  public void writeCSVFileNode(Path path) throws Exception {
    // writing to the file
    List<Node> nodes = nodeFactory.getAllNodes();
    try (FileWriter fw = new FileWriter(path.toString() + "/MapFAllnodes.csv");
        BufferedWriter bw = new BufferedWriter(fw)) {

      bw.write("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName,teamAssigned");

      for (Node n : nodes) {
        bw.newLine();
        bw.write(formatNode(n));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }

  /**
   * formats a node into csv
   *
   * @param node
   * @return returns a node in the form of a string
   */
  private String formatNode(Node node) {
    String string = "";
    string = node.getId() + ",";
    string = string + String.valueOf(node.getXCoord()) + ",";
    string = string + String.valueOf(node.getYCoord()) + ",";
    string = string + String.valueOf(node.getFloor()) + ",";
    string = string + node.getBuilding() + ",";
    string = string + node.getType().getTypeString() + ",";
    string = string + node.getLongName() + ",";
    string = string + node.getShortName() + ",";
    string = string + "dataWritten";

    return string;
  }

  /**
   * reads a csv file that contains edges and inserts the data in the file into the correct place in
   * the database
   */
  public void readCSVFileEdge(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 3;
      while (i < (data.size() - 1)) {
        edgeFactory.create(new Edge(data.get(i), data.get(i + 1), data.get(i + 2)));
        i = i + 3;
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File Not found!");
    } catch (SQLException e) {
      // ignore
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  /** Writes the edges to the CSV file so that it can become persistant */
  public void writeCSVFileEdge(Path path) throws Exception {
    // writing to the file
    List<Edge> Edges = edgeFactory.getAllEdges();

    // csvString = csvString + formatNode(n);

    try (FileWriter fw = new FileWriter(path.toString() + "/MapFAlledges.csv");
        BufferedWriter bw = new BufferedWriter(fw)) {

      bw.write("edgeID,startNode,endNode");

      for (Edge e : Edges) {
        bw.newLine();
        bw.write(formatEdge(e));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }

  public String formatEdge(Edge e) {
    String edge = "";
    edge = e.getId() + "," + e.getNode1() + "," + e.getNode2();
    return edge;
  }
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
        maintenanceRequestFactory.getAllMaintenanceRequests();

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
