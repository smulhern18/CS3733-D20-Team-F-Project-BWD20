package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.Admin;
import edu.wpi.teamF.ModelClasses.Account.Staff;
import edu.wpi.teamF.ModelClasses.Account.User;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.FlowerRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LanguageServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LaundryServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MariachiRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MedicineDeliveryRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SanitationServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.TransportRequest;
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

  /** Writes the nodes to the CSV file so that it can become persistant */
  public void writeCSVFileNode(Path path) throws Exception {
    // writing to the file
    List<Node> nodes = nodeFactory.getAllNodes();
    try (FileWriter fw = new FileWriter(path.toString() + "/MapFAllnodes.csv");
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
  /** Writes the edges to the CSV file so that it can become persistant */
  public void writeCSVFileEdge(Path path) throws Exception {
    // writing to the file
    List<Edge> Edges = edgeFactory.getAllEdges();

    // csvString = csvString + formatNode(n);

    try (FileWriter fw = new FileWriter(path.toString() + "/MapFAlledges.csv");
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
                nodeFactory.read(data.get(i + 1)),
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
        BufferedWriter bw = new BufferedWriter(fw); ) {

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
            + m.getComplete()
            + ","
            + m.getTimeCompleted().getTime();
    return Main;
  }
  /**
   * reads a csv file that contains Security Requests and inserts the data in the file into the
   * correct place in the database
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

      int i = 8;
      while (i < (data.size() - 1)) {
        securityRequestFactory.create(
            new SecurityRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                Integer.parseInt(data.get(i + 7))));

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
  public void writeCSVFileSecurityService(Path path) throws Exception {
    // writing to the file
    List<SecurityRequest> securityRequests = securityRequestFactory.getAllSecurityRequest();

    try (FileWriter fw = new FileWriter(path.toString() + "/SecurityBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write(
          "id,location,assignee,description,dateTimeSubmitted,priority,complete,GuardsRequested");

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
            + m.getComplete()
            + ","
            + m.getGuardsRequested();
    return Main;
  }
  /**
   * reads a csv file that contains Accounts and inserts the data in the file into the correct place
   * in the database
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
              accountFactory.create(
                  new Admin(
                      data.get(i + 2),
                      data.get(i + 3),
                      data.get(i + 4),
                      data.get(i),
                      data.get(i + 1)));
              break;
            case (1):
              accountFactory.create(
                  new Staff(
                      data.get(i + 2),
                      data.get(i + 3),
                      data.get(i + 4),
                      data.get(i),
                      data.get(i + 1)));
              break;
            case (2):
              accountFactory.create(
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
    List<Account> Account = accountFactory.getAllAccounts();

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

  /**
   * reads a csv file that contains Computer Requests and inserts the data in the file into the
   * correct place in the database
   */
  public void readCSVFileComputerService(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 10;
      while (i < (data.size() - 1)) {
        computerServiceRequestFactory.create(
            new ComputerServiceRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                data.get(i + 7),
                data.get(i + 8),
                data.get(i + 9)));

        i = i + 10;
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
  public void writeCSVFileComputerService(Path path) throws Exception {
    // writing to the file
    List<ComputerServiceRequest> computerServiceRequest =
        computerServiceRequestFactory.getAllComputerRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/ComputerBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write(
          "id,location,assignee,description,dateTimeSubmitted,priority,complete,make,hardwareOrSoftware,OS");

      for (ComputerServiceRequest s : computerServiceRequest) {
        bw.newLine();
        bw.write((formatComputerService(s)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatComputerService(ComputerServiceRequest m) {
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
            + m.getComplete()
            + ","
            + m.getMake()
            + ","
            + m.getHardwareSoftware()
            + ","
            + m.getOS();
    return Main;
  }
  /**
   * reads a csv file that contains Flower Requests and inserts the data in the file into the
   * correct place in the database
   */
  public void readCSVFileFlowerService(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 14;
      while (i < (data.size() - 1)) {
        flowerServiceRequestFactory.create(
            new FlowerRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                data.get(i + 6),
                data.get(i + 7),
                data.get(i + 8),
                data.get(i + 9),
                data.get(i + 10),
                data.get(i + 11),
                Boolean.parseBoolean(data.get(i + 12)),
                Boolean.parseBoolean((data.get(i + 13)))));

        i = i + 14;
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
  public void writeCSVFileFlowerService(Path path) throws Exception {
    // writing to the file
    List<FlowerRequest> flowerRequests = flowerServiceRequestFactory.getAllFlowerRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/FlowerBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write(
          "iid,location,assignee,description,dateTimeSubmitted,priority,recipientInput,roomInput,choice,messageInput,buyerName,phoneNumber,iftWrap,complete");

      for (FlowerRequest s : flowerRequests) {
        bw.newLine();
        bw.write((formatFlowerService(s)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatFlowerService(FlowerRequest m) {
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
            + m.getRecipientInput()
            + ","
            + m.getRoomInput()
            + ","
            + m.getChoice()
            + ","
            + m.getMessageInput()
            + ","
            + m.getBuyerName()
            + ","
            + m.getPhoneNumber()
            + ","
            + m.getGiftWrap()
            + ","
            + m.getComplete();
    return Main;
  }

  /**
   * reads a csv file that contains Mariachi Requests and inserts the data in the file into the
   * correct place in the database
   */
  public void readCSVFileMariachiService(InputStream stream) {
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
        mariachiRequestFactory.create(
            new MariachiRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                data.get(i + 7)));

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
  public void writeCSVFileMariachiService(Path path) throws Exception {
    // writing to the file
    List<MariachiRequest> mariachiRequests = mariachiRequestFactory.getAllMariachiRequest();

    try (FileWriter fw = new FileWriter(path.toString() + "/MariachiBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write(
          "id,location,assignee,description,dateTimeSubmitted,priority,complete,SongRequested");

      for (MariachiRequest s : mariachiRequests) {
        bw.newLine();
        bw.write((formatMariahiService(s)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatMariahiService(MariachiRequest m) {
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
            + m.getComplete()
            + ","
            + m.getSongRequest();
    return Main;
  }

  /**
   * reads a csv file that contains Language Requests and inserts the data in the file into the
   * correct place in the database
   */
  public void readCSVFileLanguageService(InputStream stream) {
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
        languageServiceRequestFactory.create(
            new LanguageServiceRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                data.get(i + 7),
                data.get(i + 8)));

        i = i + 9;
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
  public void writeCSVFileLanguageService(Path path) throws Exception {
    // writing to the file
    List<LanguageServiceRequest> languageServiceRequests =
        languageServiceRequestFactory.getAllLanguageRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/LanguageBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write(
          "id,location,assignee,description,dateTimeSubmitted,priority,complete,language,problemType");

      for (LanguageServiceRequest s : languageServiceRequests) {
        bw.newLine();
        bw.write((formatLanguageService(s)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatLanguageService(LanguageServiceRequest m) {
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
            + m.getComplete()
            + ","
            + m.getLanguage()
            + ","
            + m.getProblemType();
    return Main;
  }
  /**
   * reads a csv file that contains Laundry Requests and inserts the data in the file into the
   * correct place in the database
   */
  public void readCSVFileLaundryService(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 10;
      while (i < (data.size() - 1)) {
        laundryServiceRequestFactory.create(
            new LaundryServiceRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                data.get(i + 7),
                data.get(i + 8),
                data.get(i + 9)));

        i = i + 10;
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
  public void writeCSVFileLaundryService(Path path) throws Exception {
    // writing to the file
    List<LaundryServiceRequest> laundryServiceRequests =
        laundryServiceRequestFactory.getAllLaundryRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/LaundryBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write(
          "id,location,assignee,description,dateTimeSubmitted,priority,complete,language,problemType");

      for (LaundryServiceRequest s : laundryServiceRequests) {
        bw.newLine();
        bw.write((formatLaundryService(s)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatLaundryService(LaundryServiceRequest m) {
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
            + m.getComplete()
            + ","
            + m.getItems()
            + ","
            + m.getQuantity()
            + ","
            + m.getTemperature();
    return Main;
  }
  /**
   * reads a csv file that contains Medicine Delivery Requests and inserts the data in the file into
   * the correct place in the database
   */
  public void readCSVFileMedicineDeliveryService(InputStream stream) {
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
        medicineDeliveryRequestFactory.create(
            new MedicineDeliveryRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                data.get(i + 7),
                data.get(i + 8)));

        i = i + 9;
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
  public void writeCSVFileMedicineDeliveryService(Path path) throws Exception {
    // writing to the file
    List<MedicineDeliveryRequest> medicineDeliveryRequests =
        medicineDeliveryRequestFactory.getAllMedicineDeliveryRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/MedicineBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write(
          "id,location,assignee,description,dateTimeSubmitted,priority,complete,medicineType,instructions");

      for (MedicineDeliveryRequest m : medicineDeliveryRequests) {
        bw.newLine();
        bw.write((formatMedicineDeliveryService(m)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatMedicineDeliveryService(MedicineDeliveryRequest m) {
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
            + m.getComplete()
            + ","
            + m.getMedicineType()
            + ","
            + m.getInstructions();
    return Main;
  }

  /**
   * reads a csv file that contains sanitation Requests and inserts the data in the file into the
   * correct place in the database
   */
  public void readCSVFileSanitationService(InputStream stream) {
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
        sanitationServiceRequestFactory.create(
            new SanitationServiceRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                data.get(i + 7)));

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
  public void writeCSVFileSanitationService(Path path) throws Exception {
    // writing to the file
    List<SanitationServiceRequest> sanitationServiceRequests =
        sanitationServiceRequestFactory.getAllSanitationRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/SanitationBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write("id,location,assignee,description,dateTimeSubmitted,priority,complete,type");

      for (SanitationServiceRequest s : sanitationServiceRequests) {
        bw.newLine();
        bw.write((formatSanitationService(s)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatSanitationService(SanitationServiceRequest m) {
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
            + m.getComplete()
            + ","
            + m.getType();
    return Main;
  }
  /**
   * reads a csv file that contains Transport Requests and inserts the data in the file into the
   * correct place in the database
   */
  public void readCSVFileTransportService(InputStream stream) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(stream));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      int i = 10;
      while (i < (data.size() - 1)) {
        transportRequestFactory.create(
            new TransportRequest(
                data.get(i),
                nodeFactory.read(data.get(i + 1)),
                data.get(i + 2),
                data.get(i + 3),
                new Date(Integer.parseInt(data.get(i + 4))),
                Integer.parseInt(data.get(i + 5)),
                Boolean.parseBoolean(data.get(i + 6)),
                data.get(i + 7),
                nodeFactory.read(data.get(i + 8)),
                new Date(Integer.parseInt(data.get(i + 9)))));

        i = i + 10;
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
  public void writeCSVFileTransportService(Path path) throws Exception {
    // writing to the file
    List<TransportRequest> transportRequests = transportRequestFactory.getAllTransportRequests();

    try (FileWriter fw = new FileWriter(path.toString() + "/TransportBackup.csv");
        BufferedWriter bw = new BufferedWriter(fw); ) {

      bw.write(
          "id,location,assignee,description,dateTimeSubmitted,priority,complete,type,destination,dateTimeCompleted");

      for (TransportRequest s : transportRequests) {
        bw.newLine();
        bw.write((formatTransportService(s)));
      }
      bw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage() + "" + e.getClass());
    }
  }
  //
  public String formatTransportService(TransportRequest m) {
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
            + m.getComplete()
            + ","
            + m.getType()
            + ","
            + m.getDestination().getId()
            + ","
            + m.getDateTimeCompleted().getTime();
    return Main;
  }
}
