package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.PasswordHasher;
import edu.wpi.teamF.ModelClasses.Appointment;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UIAccount;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DatabaseManager {

  /** Table names */
  static final String NODES_TABLE_NAME = "nodesTable";

  static final String EDGES_TABLE_NAME = "edgesTable";
  static final String SERVICE_REQUEST_TABLE = "serviceRequestsTable";
  static final String SECURITY_REQUEST_TABLE_NAME = "securityRequestsTable";
  static final String MAINTENANCE_REQUEST_TABLE_NAME = "maintenanceRequestsTable";
  static final String ACCOUNT_TABLE_NAME = "accountsTable";
  static final String APPOINTMENTS_TABLE_NAME = "appointmentsTable";
  static final String COMPUTER_REQUEST_TABLE_NAME = "ComputerRequestsTable";
  static final String TRANSPORT_REQUEST_TABLE_NAME = "TransportRequestsTable";
  /** Column Names */
  // node
  static final String X_COORDINATE_KEY = "xCoord";

  static final String Y_COORDINATE_KEY = "yCoord";
  static final String BUILDING_KEY = "building";
  static final String LONG_NAME_KEY = "longName";
  static final String SHORT_NAME_KEY = "shortName";
  static final String TYPE_KEY = "type";
  static final String FLOOR_KEY = "floor";
  // edge
  static final String EDGEID_KEY = "edgeId";
  static final String NODE_1_KEY = "node1";
  static final String NODE_A_KEY = "nodeA";

  // service Request
  static final String SERVICEID_KEY = "serviceId";
  static final String NODEID_KEY = "nodeId";
  static final String DESCRIPTION_KEY = "description";
  static final String ASSIGNED_KEY = "assignee";
  static final String TIME_CREATED_KEY = "timeCreated";
  static final String PRIORITY_KEY = "priority";
  static final String COMPLETED_KEY = "completed";

  // Maintainence Request
  static final String MAINTENCE_REQUEST_ID_KEY = "serviceId";
  static final String DATECOMPLETED_KEY = "DateCompleted";

  // Security Request
  static final String SECRURITY_REQUEST_ID_KEY = "serviceId";

  // account
  static final String USER_NAME_KEY = "userName";
  static final String PASSWORD_KEY = "password";
  static final String FIRST_NAME_KEY = "firstName";
  static final String LAST_NAME_KEY = "lastName";
  static final String EMAIL_ADDRESS_KEY = "email";
  static final String USER_TYPE_KEY = "userType";

  // Appointments
  static final String APPOINTMENT_ID_KEY = "appointmentId";
  static final String LOCATION_KEY = "location";
  static final String ROOM_KEY = "room";
  static final String USERID_KEY = "userID";
  static final String PCP_KEY = "PCP";
  // ComputerService requests
  static final String OS_ID_KEY = "OperatingSystem";
  static final String MAKE_KEY = "Make";
  static final String HARDWARESOFTWARE_KEY = "HardwareOrSoftware";
  // Transport requests
  static final String TRANSPORT_TYPE_KEY = "type";
  static final String DESTINATION_KEY = "destination";

  // Factories
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();
  private AccountFactory accountFactory = AccountFactory.getFactory();
  private ServiceRequestFactory serviceRequestFactory = ServiceRequestFactory.getFactory();
  private SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
  private MaintenanceRequestFactory maintenanceRequestFactory =
      MaintenanceRequestFactory.getFactory();
  private ComputerServiceRequestFactory computerServiceRequestFactory =
      ComputerServiceRequestFactory.getFactory();
  private AppointmentFactory appointmentFactory = AppointmentFactory.getFactory();

  static Connection connection = null;

  private static DatabaseManager manager = new DatabaseManager();

  private DatabaseManager() {}

  public static DatabaseManager getManager() {
    return manager;
  }

  public void createTables() throws SQLException {
    String nodeTableCreationStatement =
        "CREATE TABLE "
            + NODES_TABLE_NAME
            + " ( "
            + NODEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + X_COORDINATE_KEY
            + " SMALLINT NOT NULL, "
            + Y_COORDINATE_KEY
            + " SMALLINT NOT NULL, "
            + BUILDING_KEY
            + " VARCHAR(32) NOT NULL, "
            + LONG_NAME_KEY
            + " VARCHAR(64) NOT NULL, "
            + SHORT_NAME_KEY
            + " VARCHAR(16) NOT NULL, "
            + TYPE_KEY
            + " VARCHAR(4) NOT NULL, "
            + FLOOR_KEY
            + " SMALLINT NOT NULL, "
            + "PRIMARY KEY ("
            + NODEID_KEY
            + "))";

    String edgeTableCreationStatement =
        "CREATE TABLE "
            + EDGES_TABLE_NAME
            + " ( "
            + EDGEID_KEY
            + " VARCHAR(65) NOT NULL, "
            + NODE_1_KEY
            + " VARCHAR(32) NOT NULL, "
            + NODE_A_KEY
            + " VARCHAR(32) NOT NULL, "
            + "PRIMARY KEY ("
            + EDGEID_KEY
            + "))";

    String serviceRequestTableCreationStatement =
        "CREATE TABLE "
            + SERVICE_REQUEST_TABLE
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + NODEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + TIME_CREATED_KEY
            + " TIMESTAMP NOT NULL, "
            + DESCRIPTION_KEY
            + " VARCHAR(128) NOT NULL, "
            + ASSIGNED_KEY
            + " VARCHAR(32) NOT NULL, "
            + PRIORITY_KEY
            + " INTEGER NOT NULL, "
            + COMPLETED_KEY
            + " BOOLEAN NOT NULL, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    String securityTableCreationStatement =
        "CREATE TABLE "
            + SECURITY_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    String maintenanceTableCreationStatement =
        "CREATE TABLE "
            + MAINTENANCE_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + DATECOMPLETED_KEY
            + " TIMESTAMP, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    String computerTableCreationStatement =
        "CREATE TABLE "
            + COMPUTER_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + MAKE_KEY
            + " VARCHAR(32) NOT NULL, "
            + HARDWARESOFTWARE_KEY
            + " VARCHAR(32) NOT NULL, "
            + OS_ID_KEY
            + " VARCHAR(32) NOT NULL, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    String transportTableCreationStatement =
        "CREATE TABLE "
            + TRANSPORT_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + TRANSPORT_TYPE_KEY
            + " VARCHAR(32) NOT NULL, "
            + DESTINATION_KEY
            + " VARCHAR(32) NOT NULL, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    String accountTableCreationStatement =
        "CREATE TABLE "
            + ACCOUNT_TABLE_NAME
            + " ( "
            + USER_NAME_KEY
            + " VARCHAR(32) NOT NULL, "
            + PASSWORD_KEY
            + " VARCHAR(128) NOT NULL, "
            + FIRST_NAME_KEY
            + " VARCHAR(32) NOT NULL, "
            + LAST_NAME_KEY
            + " VARCHAR(32) NOT NULL, "
            + EMAIL_ADDRESS_KEY
            + " VARCHAR(64) NOT NULL, "
            + USER_TYPE_KEY
            + " SMALLINT NOT NULL, "
            + "PRIMARY KEY ("
            + USER_NAME_KEY
            + "))";

    String appointmentTableCreationStatement =
        "CREATE TABLE "
            + APPOINTMENTS_TABLE_NAME
            + " ( "
            + APPOINTMENT_ID_KEY
            + " VARCHAR(32) NOT NULL, "
            + LOCATION_KEY
            + " VARCHAR(32) NOT NULL, "
            + ROOM_KEY
            + " VARCHAR(32) NOT NULL, "
            + USERID_KEY
            + " VARCHAR(32) NOT NULL, "
            + PCP_KEY
            + " VARCHAR(32) NOT NULL, "
            + "PRIMARY KEY ("
            + APPOINTMENT_ID_KEY
            + "))";

    PreparedStatement preparedStatement = connection.prepareStatement(nodeTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(edgeTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(serviceRequestTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(maintenanceTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(securityTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(computerTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(accountTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(appointmentTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(transportTableCreationStatement);
    preparedStatement.execute();
    System.out.println("Created Tables Successfully");
  }

  public void initialize() {
    String dbURL = "jdbc:derby:database;create=true";
    try {
      DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
    } catch (SQLException e) {
      System.out.println("Unable to register Driver " + e.getMessage());
    }
    try {
      connection = DriverManager.getConnection(dbURL, "teamF", "theFandagoFlamingos");
    } catch (SQLException e) {
      System.out.println("Unable to get connection to database " + e.getMessage());
    }
    try {
      createTables();
    } catch (SQLException e) {
      if (!e.getMessage().contains("'NODESTABLE' already exists")) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void reset() throws SQLException {
    String nodeDropStatement = "DROP TABLE " + NODES_TABLE_NAME;
    String edgeDropStatement = "DROP TABLE " + EDGES_TABLE_NAME;
    String serviceRequestTableDropStatement = "DROP TABLE " + SERVICE_REQUEST_TABLE;
    String maintenanceTableDropStatement = "DROP TABLE " + MAINTENANCE_REQUEST_TABLE_NAME;
    String securityTableDropStatement = "DROP TABLE " + SECURITY_REQUEST_TABLE_NAME;
    String accountDropStatement = "DROP TABLE " + ACCOUNT_TABLE_NAME;
    String appointmentDropStatement = "DROP TABLE " + APPOINTMENTS_TABLE_NAME;
    String computerDropStatement = "DROP TABLE " + COMPUTER_REQUEST_TABLE_NAME;
    String transportDropStatement = "DROP TABLE " + TRANSPORT_REQUEST_TABLE_NAME;

    PreparedStatement preparedStatement = connection.prepareStatement(nodeDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(edgeDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(serviceRequestTableDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(maintenanceTableDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(computerDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(securityTableDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(accountDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(appointmentDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(transportDropStatement);
    preparedStatement.execute();
    createTables();
  }

  public static Connection getConnection() {
    return connection;
  }

  /*
   * Node Factory methods
   */
  public void manipulateNode(Node node) throws Exception {
    if (nodeFactory.read(node.getId()) == null) {
      nodeFactory.create(node);
    } else {
      nodeFactory.update(node);
    }
  }

  public Node readNode(String nodeId) throws Exception {
    return nodeFactory.read(nodeId);
  }

  public void deleteNode(String nodeId) throws Exception {
    nodeFactory.delete(nodeId);
  }

  public List<Node> getAllNodes() throws Exception {
    return nodeFactory.getAllNodes();
  }

  public List<Node> getNodesByType(Node.NodeType type) throws Exception {
    return nodeFactory.getNodesByType(type);
  }

  /*
   * Edge Factory methods
   */
  public void manipulateEdge(Edge edge) throws Exception {
    if (edgeFactory.read(edge.getId()) == null) {
      edgeFactory.create(edge);
    } else {
      edgeFactory.update(edge);
    }
  }

  public Edge readEdge(String edgeId) throws Exception {
    return edgeFactory.read(edgeId);
  }

  public void deleteEdge(String edgeId) throws Exception {
    edgeFactory.delete(edgeId);
  }

  public List<Edge> getAllEdges() throws Exception {
    return edgeFactory.getAllEdges();
  }

  public Set<Edge> getAllEdgesConnectedToNode(String nodeId) throws Exception {
    return edgeFactory.getAllEdgesConnectedToNode(nodeId);
  }

  public void deleteEdgesByNodeId(String nodeId) throws Exception {
    edgeFactory.deleteByNodeID(nodeId);
  }

  /*
   * Appointment Factory methods
   */
  public void manipulateAppointment(Appointment appointment) throws Exception {
    if (appointmentFactory.read(appointment.getId()) == null) {
      appointmentFactory.create(appointment);
    } else {
      appointmentFactory.update(appointment);
    }
  }

  public Appointment readAppointment(String id) throws Exception {
    return appointmentFactory.read(id);
  }

  public void deleteAppointment(String id) throws Exception {
    appointmentFactory.delete(id);
  }

  /*
   * Account Factory methods
   */
  public void manipulateAccount(Account account) throws Exception {
    if (accountFactory.read(account.getUsername()) == null) {
      accountFactory.create(account);
    } else {
      accountFactory.update(account);
    }
  }

  public Account readAccount(String username) throws Exception {
    return accountFactory.read(username);
  }

  public void deleteAccount(String username) throws Exception {
    accountFactory.delete(username);
  }

  public boolean verifyPassword(String username, String password) throws Exception {
    return PasswordHasher.verifyPassword(password, accountFactory.getPasswordByUsername(username));
  }

  public List<Account> getAllAccounts() throws Exception {
    return accountFactory.getAllAccounts();
  }

  public List<UIAccount> getAllUIAccounts() throws Exception {
    return accountFactory.getAccounts();
  }

  /*
   * Service Request Factory Methods
   */
  public void manipulateServiceRequest(MaintenanceRequest serviceRequest) throws Exception {
    if (maintenanceRequestFactory.read(serviceRequest.getId()) == null) {
      maintenanceRequestFactory.create(serviceRequest);
    } else {
      maintenanceRequestFactory.update(serviceRequest);
    }
  }

  public void manipulateServiceRequest(SecurityRequest serviceRequest) throws Exception {
    if (securityRequestFactory.read(serviceRequest.getId()) == null) {
      securityRequestFactory.create(serviceRequest);
    } else {
      serviceRequestFactory.update(serviceRequest);
    }
  }

  public void manipulateServiceRequest(ComputerServiceRequest serviceRequest) throws Exception {
    if (computerServiceRequestFactory.read(serviceRequest.getId()) == null) {
      computerServiceRequestFactory.create(serviceRequest);
    } else {
      computerServiceRequestFactory.update(serviceRequest);
    }
  }

  public MaintenanceRequest readMaintenanceRequest(String serviceId) throws Exception {
    return maintenanceRequestFactory.read(serviceId);
  }

  public SecurityRequest readSecurityRequest(String serviceId) throws Exception {
    return securityRequestFactory.read(serviceId);
  }

  public ComputerServiceRequest readComputerServiceRequest(String serviceId) throws Exception {
    return ComputerServiceRequestFactory.read(serviceId);
  }

  public void deleteComputerServiceRequest(String serviceId) throws Exception {
    computerServiceRequestFactory.delete(serviceId);
  }

  public void deleteMaintenanceRequest(String serviceId) throws Exception {
    maintenanceRequestFactory.delete(serviceId);
  }

  public void deleteSecurityRequest(String serviceId) throws Exception {
    securityRequestFactory.delete(serviceId);
  }

  public List<MaintenanceRequest> getMaintenanceRequestsByLocation(Node node) throws Exception {
    return maintenanceRequestFactory.getMaintenanceRequestsByLocation(node);
  }

  public List<SecurityRequest> getSecurityRequestsByLocation(Node node) throws Exception {
    return securityRequestFactory.getSecurityRequestsByLocation(node);
  }

  public List<ComputerServiceRequest> getComputerServiceRequestsByLocation(Node node)
      throws Exception {
    return computerServiceRequestFactory.getComputerRequestsByLocation(node);
  }

  public List<MaintenanceRequest> getAllMaintenanceRequests() throws Exception {
    return maintenanceRequestFactory.getAllMaintenanceRequests();
  }

  public List<SecurityRequest> getAllSecurityRequests() throws Exception {
    return securityRequestFactory.getAllSecurityRequests();
  }

  public List<ComputerServiceRequest> getAllComputerServiceRequests() throws Exception {
    return computerServiceRequestFactory.getAllComputerRequests();
  }

  /*
   * CSV Manipulator Methods
   */
  public void backup(Path path) throws Exception {
    CSVManipulator csvManipulator = new CSVManipulator();
    csvManipulator.writeCSVFileAccount(path);
    csvManipulator.writeCSVFileEdge(path);
    csvManipulator.writeCSVFileMaintenanceService(path);
    csvManipulator.writeCSVFileNode(path);
    csvManipulator.writeCSVFileSecurityService(path);
  }

  public void readNodes(InputStream stream) {
    CSVManipulator csvManipulator = new CSVManipulator();
    csvManipulator.readCSVFileNode(stream);
  }

  public void readEdges(InputStream stream) {
    CSVManipulator csvManipulator = new CSVManipulator();
    csvManipulator.readCSVFileEdge(stream);
  }

  public void readMaintenanceRequests(InputStream stream) {
    CSVManipulator csvManipulator = new CSVManipulator();
    csvManipulator.readCSVFileMaintenanceService(stream);
  }

  public void readSecurityRequests(InputStream stream) {
    CSVManipulator csvManipulator = new CSVManipulator();
    csvManipulator.readCSVFileSecurityService(stream);
  }

  public void readAccounts(InputStream stream) {
    CSVManipulator csvManipulator = new CSVManipulator();
    csvManipulator.readCSVFileAccount(stream);
  }
}
