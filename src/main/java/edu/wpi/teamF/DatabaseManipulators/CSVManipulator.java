package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.Admin;
import edu.wpi.teamF.ModelClasses.Account.Staff;
import edu.wpi.teamF.ModelClasses.Account.User;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import java.io.*;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;

public class CSVManipulator {
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();
  private MaintenanceRequestFactory maintenanceRequestFactory = MaintenanceRequestFactory.getFactory();
  private SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
  private AccountFactory accountFactory = AccountFactory.getFactory();

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
                Short.parseShort(data.get(i + 3))); // floor
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

  /** Writes to the CSV file so that it can become persistant */
  public void writeCSVFileNode(Path path) throws Exception {
    // writing to the file
    List<Node> nodes = nodeFactory.getAllNodes();
    try (FileWriter fw = new FileWriter(path.toString() + "/NodesBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

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
  /** Writes to the CSV file so that it can become persistant */
  public void writeCSVFileEdge(Path path) throws Exception {
    // writing to the file
    List<Edge> Edges = edgeFactory.getAllEdges();

    // csvString = csvString + formatNode(n);

    try (FileWriter fw = new FileWriter(path.toString() + "/EdgesBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

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
   * reads a csv file that contains edges and inserts the data in the file into the correct place in
   * the database
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

      int i = 7;
      while (i < (data.size() - 1)) {
        maintenanceRequestFactory.create(
            new MaintenanceRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6))));

        i = i + 7;
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
    List<MaintenanceRequest> maintenanceRequests = maintenanceRequestFactory.getAllMaintenanceRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/MaintenanceBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write("id,location,assignee,description,dateTimeSubmitted,priority,complete");

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
            + m.getLocation().getId()
            + ","
            + m.getAssignee()
            + ","
            + m.getDescription()
            + ","
            + m.getDateTimeSubmitted().getTime()
            + ","
            + m.getPriority()
            + ","
            + m.getComplete();
    return Main;
  }
  /**
   * reads a csv file that contains edges and inserts the data in the file into the correct place in
   * the database
   */
  public void readCSVFileSecurityService(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 7;
      while (i < (data.size() - 1)) {
        securityRequestFactory.create(
            new SecurityRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6))));

        i = i + 7;
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
  public void writeCSVFileSecurityService(Path path) throws Exception {
    // writing to the file
    List<SecurityRequest> securityRequests = securityRequestFactory.getAllSecurityRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/SecurityBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write("id,location,assignee,description,dateTimeSubmitted,priority,complete");

      for (SecurityRequest s : securityRequests) {
        bw.newLine();
        bw.write((formatSecurityService(s)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatSecurityService(SecurityRequest m) {
    String Main = "";
    Main =
        m.getId()
            + ","
            + m.getLocation().getId()
            + ","
            + m.getAssignee()
            + ","
            + m.getDescription()
            + ","
            + m.getDateTimeSubmitted().getTime()
            + ","
            + m.getPriority()
            + ","
            + m.getComplete();
    return Main;
  }
  /**
   * reads a csv file that contains edges and inserts the data in the file into the correct place in
   * the database
   */
  public void readCSVFileAccount(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 6;
      while (i < (data.size() - 1)) {

        try {
          Account.Type type = Account.Type.getEnum(Integer.parseInt(data.get(i + 5)));
          switch (type.getTypeOrdinal()) {
            case (0):
              databaseManager.manipulateAccount(
                  new Admin(
                      data.get(i + 2),
                      data.get(i + 3),
                      data.get(i + 4),
                      data.get(i),
                      data.get(i + 1)));
              break;
            case (1):
              databaseManager.manipulateAccount(
                  new Staff(
                      data.get(i + 2),
                      data.get(i + 3),
                      data.get(i + 4),
                      data.get(i),
                      data.get(i + 1)));
              break;
            case (2):
              databaseManager.manipulateAccount(
                  new User(
                      data.get(i + 2),
                      data.get(i + 3),
                      data.get(i + 4),
                      data.get(i),
                      data.get(i + 1)));
              break;
            default:
          }
        } catch (Exception a) {
          System.out.println(a.getMessage());
        }

        i = i + 6;
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
  public void writeCSVFileAccount(Path path) throws Exception {
    // writing to the file
    List<Account> Account = databaseManager.getAllAccounts();

    try (FileWriter fw = new FileWriter(path.toString() + "/AccountBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write("Username,password,firstName,lastName,emailAddress,type");

      for (Account a : Account) {
        bw.newLine();
        bw.write(formatAccount(a));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }

  public String formatAccount(Account a) {
    String account = "";
    account =
        a.getUsername()
            + ","
            + a.getPassword()
            + ","
            + a.getFirstName()
            + ","
            + a.getLastName()
            + ","
            + a.getEmailAddress()
            + ","
            + a.getType().getTypeOrdinal();
    return account;
  }
}
