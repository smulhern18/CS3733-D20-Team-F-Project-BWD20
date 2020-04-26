package edu.wpi.teamF.DatabaseManipulators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

  static Connection connection = null;

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
    createTables();
  }

  public static Connection getConnection() {
    return connection;
  }
}
