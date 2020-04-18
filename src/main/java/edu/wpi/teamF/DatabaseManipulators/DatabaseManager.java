package edu.wpi.teamF.DatabaseManipulators;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.SQLException;

public class DatabaseManager {

  /** Table names */
  static final String NODES_TABLE_NAME = "nodesTable";

  static final String EDGES_TABLE_NAME = "edgesTable";
  static final String SERVICEREQUEST_TABLE_NAME = "serviceRequestsTable";
  static final String USER_TABLE_NAME = "usersTable";
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
  static final String TIME_CREATED_KEY = "timeCreated";
  static final String PRIORITY_KEY = "priority";

  // User
  static final String USER_NAME_KEY = "userName";
  static final String PASSWORD_KEY = "password";
  static final String FIRST_NAME_KEY = "firstName";
  static final String LAST_NAME_KEY = "lastName";
  static final String ADDRESS_KEY = "address";
  static final String EMAIL_KEY = "email";
  static final String USER_TYPE_KEY = "userType";

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

    String serviceTableCreationStatement =
            "CREATE TABLE "
                    + SERVICEREQUEST_TABLE_NAME
                    + " ( "
                    + SERVICEID_KEY
                    + " VARCHAR(32) NOT NULL, "
                    + NODEID_KEY
                    + " VARCHAR(32) NOT NULL, "
                    + TIME_CREATED_KEY
                    + " VARCHAR(32) NOT NULL, "
                    + DESCRIPTION_KEY
                    + " VARCHAR(128) NOT NULL, "
                    + PRIORITY_KEY
                    + " INTEGER NOT NULL, "
                    + "PRIMARY KEY ("
                    + SERVICEID_KEY
                    + "))";

    String userTableCreationStatement =
            "CREATE TABLE "
                    + USER_TABLE_NAME
                    + " ( "
                    + USER_NAME_KEY
                    + " VARCHAR(32) NOT NULL, "
                    + PASSWORD_KEY
                    + " INTEGER NOT NULL, "
                    + FIRST_NAME_KEY
                    + " INTEGER NOT NULL, "
                    + LAST_NAME_KEY
                    + " VARCHAR(64) NOT NULL, "
                    + ADDRESS_KEY
                    + " VARCHAR(16) NOT NULL, "
                    + EMAIL_KEY
                    + " VARCHAR(4) NOT NULL, "
                    + USER_TYPE_KEY
                    + " SMALLINT NOT NULL, "
                    + "PRIMARY KEY ("
                    + USER_NAME_KEY
                    + "))";

    PreparedStatement preparedStatement = connection.prepareStatement(nodeTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(edgeTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(serviceTableCreationStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(userTableCreationStatement);
    preparedStatement.execute();
    System.out.println("Created Tables Successfully");
  }

  public void initialize() {
    String dbURL = "jdbc:derby:teamF;create=true";
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
    String serviceDropStatement = "DROP TABLE " + SERVICEREQUEST_TABLE_NAME;
    String userDropStatement = "DROP TABLE " + USER_TABLE_NAME;

    PreparedStatement preparedStatement = connection.prepareStatement(nodeDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(edgeDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(serviceDropStatement);
    preparedStatement.execute();
    preparedStatement = connection.prepareStatement(userDropStatement);
    preparedStatement.execute();
    createTables();
  }

  public static Connection getConnection() {
    return connection;
  }

}
