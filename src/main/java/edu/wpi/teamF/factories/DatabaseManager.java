package edu.wpi.teamF.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    /**
     * Table names
     */
    static final String NODES_TABLE_NAME = "nodesTable";
    static final String EDGES_TABLE_NAME = "edgesTable";

    /**
     * Column Names
     */
    static final String NODE_NAME_KEY = "nodeName";
    static final String X_COORDINATE_KEY = "xCoord";
    static final String Y_COORDINATE_KEY = "yCoord";
    static final String TYPE_KEY = "type";
    static final String FLOOR_KEY = "floor";
    static final String NODE_1_KEY = "node1";
    static final String NODE_A_KEY = "nodeA";


    static Connection connection = null;

    public void createTables() throws SQLException {
        String nodeTableCreationStatement =
                "CREATE TABLE " + NODES_TABLE_NAME +
                        "( " +
                        NODE_NAME_KEY + " VARCHAR(32) NOT NULL, " +
                        X_COORDINATE_KEY + " SMALLINT NOT NULL, " +
                        Y_COORDINATE_KEY + " SMALLINT NOT NULL, " +
                        TYPE_KEY + " VARCHAR(4) NOT NULL, " +
                        FLOOR_KEY + " SMALLINT NOT NULL," +
                        "PRIMARY KEY (" + NODE_NAME_KEY +"))";

        String edgeTableCreationStatement =
                "CREATE TABLE " + EDGES_TABLE_NAME +
                        "( " +
                        NODE_1_KEY + " VARCHAR(32) NOT NULL, " +
                        NODE_A_KEY + " VARCHAR(32) NOT NULL," +
                        "PRIMARY KEY (" + NODE_1_KEY + ", " + NODE_A_KEY + "))";

        PreparedStatement preparedStatement = connection.prepareStatement(nodeTableCreationStatement);
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(edgeTableCreationStatement);
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
        } catch (SQLException e){
            System.out.println("Error when Creating tables: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
