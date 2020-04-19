package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NodeFactory {

  private static final NodeFactory factory = new NodeFactory();

  public static NodeFactory getFactory() {
    return factory;
  }

  private NodeFactory() {}

  /**
   * Creates node entries in the database
   *
   * @param node the node to add
   */
  public void create(Node node) throws ValidationException {
    String insertStatement =
        "INSERT INTO "
            + DatabaseManager.NODES_TABLE_NAME
            + " ( "
            + DatabaseManager.NODEID_KEY
            + ", "
            + DatabaseManager.X_COORDINATE_KEY
            + ", "
            + DatabaseManager.Y_COORDINATE_KEY
            + ", "
            + DatabaseManager.BUILDING_KEY
            + ", "
            + DatabaseManager.LONG_NAME_KEY
            + ", "
            + DatabaseManager.SHORT_NAME_KEY
            + ", "
            + DatabaseManager.TYPE_KEY
            + ", "
            + DatabaseManager.FLOOR_KEY
            + " ) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    Validators.nodeValidation(node);
    try (PreparedStatement prepareStatement =
        DatabaseManager.getConnection().prepareStatement(insertStatement)) {
      int param = 1;
      prepareStatement.setString(param++, node.getId());
      prepareStatement.setInt(param++, node.getXCoord());
      prepareStatement.setInt(param++, node.getYCoord());
      prepareStatement.setString(param++, node.getBuilding());
      prepareStatement.setString(param++, node.getLongName());
      prepareStatement.setString(param++, node.getShortName());
      prepareStatement.setString(param++, node.getType().getTypeString());
      prepareStatement.setInt(param++, node.getFloor());

      try {
        int numRows = prepareStatement.executeUpdate();
        if (numRows < 1) {
          throw new SQLException("Created more than one rows");
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * read a node from the database
   *
   * @param id the node id to read
   * @return the node read
   */
  public Node read(String id) throws Exception {
    Node node = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.NODES_TABLE_NAME
            + " WHERE "
            + DatabaseManager.NODEID_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, id);

      try {
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        node =
            new Node(
                resultSet.getString(DatabaseManager.NODEID_KEY),
                resultSet.getShort(DatabaseManager.X_COORDINATE_KEY),
                resultSet.getShort(DatabaseManager.Y_COORDINATE_KEY),
                resultSet.getString(DatabaseManager.BUILDING_KEY),
                resultSet.getString(DatabaseManager.LONG_NAME_KEY),
                resultSet.getString(DatabaseManager.SHORT_NAME_KEY),
                Node.NodeType.getEnum(resultSet.getString(DatabaseManager.TYPE_KEY)),
                resultSet.getShort(DatabaseManager.FLOOR_KEY));
      } catch (ValidationException e) {
        throw e;
      }
    } catch (IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      System.out.println("Exception in NodeFactory read: " + e.getMessage() + ", " + e.getClass());
    }
    return node;
  }

  /**
   * Updates a node in the database
   *
   * @param node the node to update
   */
  public void update(Node node) {
    String updateStatement =
        "UPDATE "
            + DatabaseManager.NODES_TABLE_NAME
            + " SET "
            + DatabaseManager.NODEID_KEY
            + " = ?, "
            + DatabaseManager.X_COORDINATE_KEY
            + " = ?, "
            + DatabaseManager.Y_COORDINATE_KEY
            + " = ?, "
            + DatabaseManager.BUILDING_KEY
            + " = ?, "
            + DatabaseManager.LONG_NAME_KEY
            + " = ?, "
            + DatabaseManager.SHORT_NAME_KEY
            + " = ?, "
            + DatabaseManager.TYPE_KEY
            + " = ?, "
            + DatabaseManager.FLOOR_KEY
            + " = ? "
            + "WHERE "
            + DatabaseManager.NODEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(updateStatement)) {
      int param = 1;
      preparedStatement.setString(param++, node.getId());
      preparedStatement.setShort(param++, node.getXCoord());
      preparedStatement.setShort(param++, node.getYCoord());
      preparedStatement.setString(param++, node.getBuilding());
      preparedStatement.setString(param++, node.getLongName());
      preparedStatement.setString(param++, node.getShortName());
      preparedStatement.setString(param++, node.getType().getTypeString());
      preparedStatement.setShort(param++, node.getFloor());
      preparedStatement.setString(param++, node.getId());
      int numRows = preparedStatement.executeUpdate();
      if (numRows != 1) {
        throw new Exception("Updated " + numRows + " rows");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Deletes a node object from the database
   *
   * @param id the ID to delete an entry with
   */
  public void delete(String id) {
    String deleteStatement =
        "DELETE FROM "
            + DatabaseManager.NODES_TABLE_NAME
            + " WHERE "
            + DatabaseManager.NODEID_KEY
            + " = ?";
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(deleteStatement)) {
      preparedStatement.setString(1, id);

      int numRows = preparedStatement.executeUpdate();
      if (numRows > 1) {
        throw new SQLException("Deleted " + numRows + " rows");
      }
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage() + ", " + e.getCause());
    }
  }

  /**
   * Returns all the nodes of a certain type
   *
   * @param type the type to read from
   * @return a list of nodes with the type passed in
   */
  public List<Node> getNodesByType(Node.NodeType type) {
    List<Node> nodes = null;
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.NODES_TABLE_NAME
            + " WHERE "
            + DatabaseManager.TYPE_KEY
            + " = ?";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, type.getTypeString());

      try {
        ResultSet resultSet = preparedStatement.executeQuery();
        nodes = new ArrayList<>();
        while (resultSet.next()) {
          nodes.add(
              new Node(
                  resultSet.getString(DatabaseManager.NODEID_KEY),
                  resultSet.getShort(DatabaseManager.X_COORDINATE_KEY),
                  resultSet.getShort(DatabaseManager.Y_COORDINATE_KEY),
                  resultSet.getString(DatabaseManager.BUILDING_KEY),
                  resultSet.getString(DatabaseManager.LONG_NAME_KEY),
                  resultSet.getString(DatabaseManager.SHORT_NAME_KEY),
                  Node.NodeType.getEnum(resultSet.getString(DatabaseManager.TYPE_KEY)),
                  resultSet.getShort(DatabaseManager.FLOOR_KEY)));
        }
      } catch (ValidationException e) {
        throw e;
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in NodeFactory getNodesByType: " + e.getMessage() + ", " + e.getClass());
    }
    return nodes;
  }

  /**
   * Gets all the nodes from the database
   *
   * @return a list of all nodes in the database
   */
  public List<Node> getAllNodes() {
    List<Node> nodes = null;
    String selectStatement = "SELECT * FROM " + DatabaseManager.NODES_TABLE_NAME;

    try (PreparedStatement preparedStatement =
            DatabaseManager.getConnection().prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery()) {;
      nodes = new ArrayList<>();
      while (resultSet.next()) {
        nodes.add(
            new Node(
                resultSet.getString(DatabaseManager.NODEID_KEY),
                resultSet.getShort(DatabaseManager.X_COORDINATE_KEY),
                resultSet.getShort(DatabaseManager.Y_COORDINATE_KEY),
                resultSet.getString(DatabaseManager.BUILDING_KEY),
                resultSet.getString(DatabaseManager.LONG_NAME_KEY),
                resultSet.getString(DatabaseManager.SHORT_NAME_KEY),
                Node.NodeType.getEnum(resultSet.getString(DatabaseManager.TYPE_KEY)),
                resultSet.getShort(DatabaseManager.FLOOR_KEY)));
      }
    } catch (Exception e) {
      System.out.println(
          "Exception in NodeFactory getNodesByType: " + e.getMessage() + ", " + e.getClass());
    }
    return nodes;
  }
}
