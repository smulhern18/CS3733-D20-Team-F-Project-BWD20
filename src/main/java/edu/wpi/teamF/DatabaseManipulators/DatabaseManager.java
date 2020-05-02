package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {

  /** Table names */
  static final String SERVICE_REQUEST_TABLE = "serviceRequestsTable";
  /** Column Names */
  // service Request
  static final String SERVICEID_KEY = "serviceId";
  static final String NODEID_KEY = "nodeId";
  static final String DESCRIPTION_KEY = "description";
  static final String ASSIGNED_KEY = "assignee";
  static final String TIME_CREATED_KEY = "timeCreated";
  static final String PRIORITY_KEY = "priority";
  static final String COMPLETED_KEY = "completed";
  static final String DATECOMPLETED_KEY = "DateCompleted";

  // Factories
  private ServiceRequestFactory serviceRequestFactory = ServiceRequestFactory.getFactory();
  private MaintenanceRequestFactory maintenanceRequestFactory =
      MaintenanceRequestFactory.getFactory();

  static Connection connection = null;

  private static DatabaseManager manager = new DatabaseManager();

  private DatabaseManager() {}

  public static DatabaseManager getManager() {
    return manager;
  }

  public void createTable() throws SQLException {
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
            + DATECOMPLETED_KEY
            + " TIMESTAMP, "
            + "PRIMARY KEY ("
            + SERVICEID_KEY
            + "))";

    PreparedStatement preparedStatement = connection.prepareStatement(serviceRequestTableCreationStatement);
    preparedStatement.execute();
    System.out.println("Created Table Successfully");
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
      createTable();
    } catch (SQLException e) {
      if (!e.getMessage().contains("'NODESTABLE' already exists")) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void reset() throws SQLException {
    String serviceRequestTableDropStatement = "DROP TABLE " + SERVICE_REQUEST_TABLE;

    PreparedStatement preparedStatement = connection.prepareStatement(serviceRequestTableDropStatement);
    preparedStatement.execute();
    createTable();
  }

  public static Connection getConnection() {
    return connection;
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

  public MaintenanceRequest readMaintenanceRequest(String serviceId) throws Exception {
    return maintenanceRequestFactory.read(serviceId);
  }

  public void deleteMaintenanceRequest(String serviceId) throws Exception {
    maintenanceRequestFactory.delete(serviceId);
  }

  public List<MaintenanceRequest> getAllMaintenanceRequests() throws Exception {
    return maintenanceRequestFactory.getAllMaintenanceRequests();
  }


  public void readMaintenanceRequests(InputStream stream) {
    CSVManipulator csvManipulator = new CSVManipulator();
    csvManipulator.readCSVFileMaintenanceService(stream);
  }
}
