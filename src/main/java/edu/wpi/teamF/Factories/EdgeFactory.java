package edu.wpi.teamF.Factories;

import edu.wpi.teamF.ModelClasses.Node;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class EdgeFactory {

  private static final EdgeFactory factory = new EdgeFactory();

  private NodeFactory nodeFactory = NodeFactory.getFactory();

  public static EdgeFactory getFactory() {
    return factory;
  }

  /**
   * Creates edge entry in database
   *
   * @param node neighbor nodes to create
   * @throws Exception should anything go wrong
   */
  public void create(Node node) throws Exception {
    for (String neighborNode : node.getNeighbors()) {
      String insertStatement =
          "INSERT INTO "
              + DatabaseManager.EDGES_TABLE_NAME
              + " ("
              + DatabaseManager.EDGEID_KEY
              + ", "
              + DatabaseManager.NODE_A_KEY
              + ", "
              + DatabaseManager.NODE_1_KEY
              + ") "
              + "VALUES (?, ?, ?)";

      try (PreparedStatement preparedStatement =
          DatabaseManager.getConnection().prepareStatement(insertStatement)) {
        int param = 1;
        preparedStatement.setString(param++, node.getName() + "_" + neighborNode);
        preparedStatement.setString(param++, node.getName());
        preparedStatement.setString(param++, neighborNode);

        try {
          int numRows = preparedStatement.executeUpdate();
          if (numRows < 1) {
            throw new Exception("Something went wrong with the creation of the edge entry");
          }
        } catch (SQLException e) {
          System.out.println(e.getMessage());
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void create(String node1, String nodeA) throws Exception {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.EDGES_TABLE_NAME
            + " ("
            + DatabaseManager.EDGEID_KEY
            + ", "
            + DatabaseManager.NODE_A_KEY
            + ", "
            + DatabaseManager.NODE_1_KEY
            + ") "
            + "VALUES (?, ?, ?), (?, ?, ?)";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      preparedStatement.setString(param++, node1 + "_" + nodeA);
      preparedStatement.setString(param++, node1);
      preparedStatement.setString(param++, nodeA);
      preparedStatement.setString(param++, node1 + "_" + nodeA + "2");
      preparedStatement.setString(param++, nodeA);
      preparedStatement.setString(param++, node1);

      try {
        int numRows = preparedStatement.executeUpdate();
        if (numRows < 1) {
          throw new Exception("Something went wrong with the creation of the edge entry");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void create(String edgeID, String node1, String nodeA) throws Exception {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.EDGES_TABLE_NAME
            + " ("
            + DatabaseManager.EDGEID_KEY
            + ", "
            + DatabaseManager.NODE_A_KEY
            + ", "
            + DatabaseManager.NODE_1_KEY
            + ") "
            + "VALUES (?, ?, ?), (?, ?, ?)";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      preparedStatement.setString(param++, edgeID);
      preparedStatement.setString(param++, node1);
      preparedStatement.setString(param++, nodeA);
      preparedStatement.setString(param++, edgeID + "2");
      preparedStatement.setString(param++, nodeA);
      preparedStatement.setString(param++, node1);

      try {
        int numRows = preparedStatement.executeUpdate();
        if (numRows < 1) {
          throw new Exception("Something went wrong with the creation of the edge entry");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  /**
   * Reads a node's neighbors from the database
   *
   * @param nodeAName the name used to find the entry to read from the database
   * @return the node's neighbors
   * @throws Exception if anything goes wrong
   */
  public Set<String> read(String nodeAName) throws Exception {
    Set<String> neighborNodes = null;
    String selectStatement =
        "SELECT "
            + DatabaseManager.NODE_1_KEY
            + " FROM "
            + DatabaseManager.EDGES_TABLE_NAME
            + " WHERE "
            + DatabaseManager.NODE_A_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, nodeAName);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        neighborNodes = new HashSet<>();
        while (resultSet.next()) {
          neighborNodes.add(resultSet.getString(DatabaseManager.NODE_1_KEY));
        }
        if (neighborNodes.isEmpty()) {
          return null;
        } else {
          return neighborNodes;
        }
      } catch (Exception e) {
        throw e;
      }
    }
  }

  /**
   * Updates edges in the database
   *
   * @param node the node edges to update in the database
   * @throws Exception if anything goes wrong
   */
  public void update(Node node) throws Exception {
    delete(node);
    create(node);
  }

  /**
   * Deletes a node from the database
   *
   * @param node the node to delete from the database
   */
  public void delete(Node node) {

    String deleteStatement1 =
        "DELETE FROM "
            + DatabaseManager.EDGES_TABLE_NAME
            + " WHERE "
            + DatabaseManager.NODE_A_KEY
            + " = ?";
    String deleteStatement2 =
        "DELETE FROM "
            + DatabaseManager.EDGES_TABLE_NAME
            + " WHERE "
            + DatabaseManager.NODE_1_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(deleteStatement1)) {
      preparedStatement.setString(1, node.getName());

      int numRows = preparedStatement.executeUpdate();
      if (numRows > 1) {
        throw new Exception("Deleted " + numRows + " rows");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(deleteStatement2)) {
      preparedStatement.setString(1, node.getName());

      int numRows = preparedStatement.executeUpdate();
      if (numRows > 1) {
        throw new Exception("Deleted " + numRows + " rows");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
