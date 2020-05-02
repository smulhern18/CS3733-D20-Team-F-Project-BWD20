package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.*;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.PasswordHasher;
import edu.wpi.teamF.ModelClasses.ServiceRequest.*;
import edu.wpi.teamF.ModelClasses.ValidationException;
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
  static final String COMPUTER_REQUEST_TABLE_NAME = "computerRequestsTable";
  static final String TRANSPORT_REQUEST_TABLE_NAME = "transportRequestsTable";
  static final String SANITATION_REQUEST_TABLE_NAME = "sanitationRequestsTable";
  static final String LANGUAGE_REQUEST_TABLE_NAME = "languageRequestsTable";
  static final String MEDICINE_DELIVERY_REQUEST_TABLE_NAME = "medicineDeliveryRequestsTable";
  static final String LAUNDRY_REQUEST_TABLE_NAME = "laundryRequestsTable";
  static final String FLOWER_REQUEST_TABLE_NAME = "flowerRequestsTable";
  static final String MARIACHI_REQUEST_TABLE_NAME = "mariachiRequestsTable";
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
  static final String GUARDS_REQUESTED_KEY = "guardsRequested";

  // Mariachi Request
  static final String SONG_REQUEST_KEY = "songRequest";

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
  static final String TIME_COMPLETED_KEY = "dateCompleted";

  // Laundry
  static final String QUANTITY_KEY = "Quantity";
  static final String TEMPERTURE_KEY = "Temperature";
  static final String ITEMS_KEY = "Items";

  // SanitationService requests
  static final String SANITATION_TYPE_KEY = "SanitationType";

  // Language service requests
  static final String LANGUAGE_KEY = "Language";
  static final String PROBLEMTYPE_KEY = "ProblemType";
  // MedicineDelivery Requests
  static final String MEDICINE_TYPE_KEY = "medicineType";
  static final String INSTRUCTIONS_KEY = "instructions";
  // Flower request
  static final String RECIPIENT_NAME_KEY = "recipientInput";
  static final String ROOM_NUMBER_KEY = "roomInput";
  static final String BOUQUET_KEY = "choice";
  static final String MESSAGE_KEY = "messageInput";
  static final String BUYER_NAME_KEY = "buyerName";
  static final String PHONE_NUMBER_KEY = "phoneNumber";
  static final String GIFT_WRAP_KEY = "giftWrap";

  // Factories
  private NodeFactory nodeFactory = NodeFactory.getFactory();
  private EdgeFactory edgeFactory = EdgeFactory.getFactory();
  private AccountFactory accountFactory = AccountFactory.getFactory();
  private ServiceRequestFactory serviceRequestFactory = ServiceRequestFactory.getFactory();
  private MaintenanceRequestFactory maintenanceRequestFactory =
      MaintenanceRequestFactory.getFactory();
  private SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
  private ComputerServiceRequestFactory computerServiceRequestFactory =
      ComputerServiceRequestFactory.getFactory();
  private AppointmentFactory appointmentFactory = AppointmentFactory.getFactory();
  private LanguageServiceRequestFactory languageServiceRequestFactory =
      LanguageServiceRequestFactory.getFactory();
  private SanitationServiceRequestFactory sanitationServiceRequestFactory =
      SanitationServiceRequestFactory.getFactory();
  private MariachiRequestFactory mariachiRequestFactory = MariachiRequestFactory.getFactory();
  private MedicineDeliveryRequestFactory medicineDeliveryRequestFactory =
      MedicineDeliveryRequestFactory.getFactory();
  private LaundryServiceRequestFactory laundryServiceRequestFactory =
      LaundryServiceRequestFactory.getFactory();
  private FlowerServiceRequestFactory flowerServiceRequestFactory =
      FlowerServiceRequestFactory.getFactory();
  private TransportRequestFactory transportRequestFactory = TransportRequestFactory.getFactory();

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
            + " VARCHAR(5) NOT NULL, "
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
            + GUARDS_REQUESTED_KEY
            + " INTEGER NOT NULL, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    String mariachiTableCreationStatement =
        "CREATE TABLE "
            + MARIACHI_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + SONG_REQUEST_KEY
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

    String laundryTableCreationStatement =
        "CREATE TABLE "
            + LAUNDRY_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + ITEMS_KEY
            + " VARCHAR(32) NOT NULL, "
            + QUANTITY_KEY
            + " VARCHAR(32) NOT NULL, "
            + TEMPERTURE_KEY
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
            + TIME_COMPLETED_KEY
            + " TIMESTAMP, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    String languageTableCreationStatement =
        "CREATE TABLE "
            + LANGUAGE_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + LANGUAGE_KEY
            + " VARCHAR(32) NOT NULL, "
            + PROBLEMTYPE_KEY
            + " VARCHAR(32) NOT NULL, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    String medicineDeliveryTableCreationStatement =
        "CREATE TABLE "
            + MEDICINE_DELIVERY_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + MEDICINE_TYPE_KEY
            + " VARCHAR(64) NOT NULL, "
            + INSTRUCTIONS_KEY
            + " VARCHAR(64) NOT NULL, "
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

    String sanitationTableCreationStatement =
        "CREATE TABLE "
            + SANITATION_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + SANITATION_TYPE_KEY
            + " VARCHAR(32) NOT NULL, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";
    String flowerTableCreationStatement =
        "CREATE TABLE "
            + FLOWER_REQUEST_TABLE_NAME
            + " ( "
            + SERVICEID_KEY
            + " VARCHAR(32) NOT NULL, "
            + RECIPIENT_NAME_KEY
            + " VARCHAR(32) NOT NULL, "
            + ROOM_NUMBER_KEY
            + " INTEGER, "
            + BOUQUET_KEY
            + " VARCHAR(32) NOT NULL, "
            + MESSAGE_KEY
            + " VARCHAR(128), "
            + BUYER_NAME_KEY
            + " VARCHAR(32) NOT NULL, "
            + PHONE_NUMBER_KEY
            + " VARCHAR(32) NOT NULL, "
            + GIFT_WRAP_KEY
            + " BOOLEAN NOT NULL, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    PreparedStatement preparedStatement = connection.prepareStatement(nodeTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(computerTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(edgeTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(serviceRequestTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(maintenanceTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(securityTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(medicineDeliveryTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(languageTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(accountTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(appointmentTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(transportTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(sanitationTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(flowerTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(laundryTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(mariachiTableCreationStatement);
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
    String medicineDeliveryTableDropStatement =
        "DROP TABLE " + MEDICINE_DELIVERY_REQUEST_TABLE_NAME;
    String accountDropStatement = "DROP TABLE " + ACCOUNT_TABLE_NAME;
    String appointmentDropStatement = "DROP TABLE " + APPOINTMENTS_TABLE_NAME;
    String computerDropStatement = "DROP TABLE " + COMPUTER_REQUEST_TABLE_NAME;
    String transportDropStatement = "DROP TABLE " + TRANSPORT_REQUEST_TABLE_NAME;
    String sanitationDropStatement = "DROP TABLE " + SANITATION_REQUEST_TABLE_NAME;
    String languageDropStatement = "DROP TABLE " + LANGUAGE_REQUEST_TABLE_NAME;
    String flowerDropStatement = "DROP TABLE " + FLOWER_REQUEST_TABLE_NAME;
    String laundryDropStatement = "DROP TABLE " + LAUNDRY_REQUEST_TABLE_NAME;
    String mariachiDropStatement = "DROP TABLE " + MARIACHI_REQUEST_TABLE_NAME;

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
    preparedStatement = connection.prepareStatement(languageDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(securityTableDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(medicineDeliveryTableDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(accountDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(appointmentDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(transportDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(sanitationDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(flowerDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(laundryDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(mariachiDropStatement);
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
    if (ComputerServiceRequestFactory.read(serviceRequest.getId()) == null) {
      computerServiceRequestFactory.create(serviceRequest);
    } else {
      computerServiceRequestFactory.update(serviceRequest);
    }
  }

  public void manipulateServiceRequest(LanguageServiceRequest langRequest)
      throws ValidationException {
    if (LanguageServiceRequestFactory.read(langRequest.getId()) == null) {
      languageServiceRequestFactory.create(langRequest);
    } else {
      languageServiceRequestFactory.update(langRequest);
    }
  }

  public void manipulateServiceRequest(SanitationServiceRequest sanitationRequest)
      throws ValidationException {
    if (sanitationServiceRequestFactory.read(sanitationRequest.getId()) == null) {
      sanitationServiceRequestFactory.create(sanitationRequest);
    } else {
      sanitationServiceRequestFactory.update(sanitationRequest);
    }
  }

  public void manipulateServiceRequest(MariachiRequest mariachiRequest) throws ValidationException {
    if (mariachiRequestFactory.read(mariachiRequest.getId()) == null) {
      mariachiRequestFactory.create(mariachiRequest);
    } else {
      mariachiRequestFactory.update(mariachiRequest);
    }
  }

  public void manipulateServiceRequest(MedicineDeliveryRequest mdRequest)
      throws ValidationException {
    if (medicineDeliveryRequestFactory.read(mdRequest.getId()) == null) {
      medicineDeliveryRequestFactory.create(mdRequest);
    } else {
      medicineDeliveryRequestFactory.update(mdRequest);
    }
  }

  public void manipulateServiceRequest(LaundryServiceRequest lsRequest) throws ValidationException {
    Validators.launduaryServiceValidation(lsRequest);
    if (laundryServiceRequestFactory.read(lsRequest.getId()) == null) {
      laundryServiceRequestFactory.create(lsRequest);
    } else {
      laundryServiceRequestFactory.update(lsRequest);
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

  public LanguageServiceRequest readLanguageServiceRequest(String serviceId) throws Exception {
    return LanguageServiceRequestFactory.read(serviceId);
  }

  public void deleteComputerServiceRequest(String serviceId) throws Exception {
    computerServiceRequestFactory.delete(serviceId);
  }

  public void deleteMaintenanceRequest(String serviceId) throws Exception {
    maintenanceRequestFactory.delete(serviceId);
  }

  public void deleteTransportRequest(String serviceId) throws Exception {
    transportRequestFactory.delete(serviceId);
  }

  public void deleteSecurityRequest(String serviceId) throws Exception {
    securityRequestFactory.delete(serviceId);
  }

  public void deleteLanguageServiceRequest(String serviceId) throws Exception {
    languageServiceRequestFactory.delete(serviceId);
  }

  public List<MaintenanceRequest> getMaintenanceRequestsByLocation(Node node) throws Exception {
    return maintenanceRequestFactory.getMaintenanceRequestsByLocation(node);
  }

  public List<TransportRequest> getTransportRequestsByLocation(Node node) throws Exception {
    return transportRequestFactory.getTransportRequestsByLocation(node);
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
    return securityRequestFactory.getAllSecurityRequest();
  }

  public List<ComputerServiceRequest> getAllComputerServiceRequests() throws Exception {
    return computerServiceRequestFactory.getAllComputerRequests();
  }

  public List<LanguageServiceRequest> getAllLanguageServiceRequests() throws Exception {
    return languageServiceRequestFactory.getAllLanguageRequests();
  }

  public List<LaundryServiceRequest> getLaundryRequestsByLocation(Node location) throws Exception {
    return laundryServiceRequestFactory.getLaundaryRequestsByLocation(location);
  }

  public List<LanguageServiceRequest> getLanguageRequestsByLocation(Node location)
      throws Exception {
    return languageServiceRequestFactory.getLanguageRequestsByLocation(location);
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
    csvManipulator.writeCSVFileComputerService(path);
    csvManipulator.writeCSVFileFlowerService(path);
    csvManipulator.writeCSVFileLanguageService(path);
    csvManipulator.writeCSVFileLaundryService(path);
    csvManipulator.writeCSVFileMariachiService(path);
    csvManipulator.writeCSVFileMedicineDeliveryService(path);
    csvManipulator.writeCSVFileSanitationService(path);
    csvManipulator.writeCSVFileTransportService(path);
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

  public List<SanitationServiceRequest> getAllSanitationRequests() {
    return sanitationServiceRequestFactory.getAllSanitationRequests();
  }

  public SanitationServiceRequest readSanitationServiceRequest(String s) {
    return sanitationServiceRequestFactory.read(s);
  }

  public void deleteSanitationService(String toDelete) {
    sanitationServiceRequestFactory.delete(toDelete);
  }

  public List<MariachiRequest> getAllMariachiServiceRequests() {
    return mariachiRequestFactory.getAllMariachiRequest();
  }

  public MariachiRequest readMariachiServiceRequest(String s) {
    return mariachiRequestFactory.read(s);
  }

  public void deleteMariachiServiceRequest(String toDelete) {
    mariachiRequestFactory.delete(toDelete);
  }

  public List<MariachiRequest> getMariachiRequestByLocation(Node node) {
    return mariachiRequestFactory.getSecurityRequestsByLocation(node);
  }

  public List<MedicineDeliveryRequest> getAllMedicineDeliveryRequests() {
    return medicineDeliveryRequestFactory.getAllMedicineDeliveryRequests();
  }

  public MedicineDeliveryRequest readMedicineDeliveryService(String s) {
    return medicineDeliveryRequestFactory.read(s);
  }

  public void deleteMedicineDeliveryRequest(String toDelete) {
    medicineDeliveryRequestFactory.delete(toDelete);
  }

  public List<LaundryServiceRequest> getAllLaunduaryRequests() {
    return laundryServiceRequestFactory.getAllLaundryRequests();
  }

  public LaundryServiceRequest readLaundryServiceRequest(String s) {
    return laundryServiceRequestFactory.read(s);
  }

  public void deleteLaundryServiceRequest(String toDelte) {
    laundryServiceRequestFactory.delete(toDelte);
  }

  public void manipulateServiceRequest(FlowerRequest flowerRequest) throws ValidationException {
    Validators.FlowerValidation(flowerRequest);
    if (flowerServiceRequestFactory.read(flowerRequest.getId()) == null) {
      flowerServiceRequestFactory.create(flowerRequest);
    } else {
      flowerServiceRequestFactory.update(flowerRequest);
    }
  }

  public FlowerRequest readFlowerRequest(String id) {
    return flowerServiceRequestFactory.read(id);
  }

  public void deleteFlowerRequest(String id) {
    flowerServiceRequestFactory.delete(id);
  }

  public List<FlowerRequest> getFlowerRequestsByLocation(Node node) {
    return flowerServiceRequestFactory.getFlowerRequestsByLocation(node);
  }

  public List<FlowerRequest> getAllFlowerRequests() {
    return flowerServiceRequestFactory.getAllFlowerRequests();
  }

  public List<TransportRequest> getAllTransportRequests() {
    return transportRequestFactory.getAllTransportRequests();
  }

  public void manipulateServiceRequest(TransportRequest transportRequest)
      throws ValidationException {
    Validators.transportRequestValidation(transportRequest);
    if (transportRequestFactory.read(transportRequest.getId()) == null) {
      transportRequestFactory.create(transportRequest);
    } else {
      transportRequestFactory.update(transportRequest);
    }
  }

  public TransportRequest readTransportRequest(String id) throws ValidationException {
    return transportRequestFactory.read(id);
  }
}
