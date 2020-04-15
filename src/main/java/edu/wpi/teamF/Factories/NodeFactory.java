package edu.wpi.teamF.Factories;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import javax.management.InstanceNotFoundException;

public class NodeFactory {

  private static final NodeFactory factory = new NodeFactory();

  private EdgeFactory edgeFactory = EdgeFactory.getFactory();

  public static NodeFactory getFactory() {
    return factory;
  }

  /**
   * Creates node entry in database
   *
   * @param node node to create
   * @throws Exception should anything go wrong
   */
  public void create(Node node) throws Exception {
    try {
      Validators.nodeValidation(node);
      String insertStatement =
          "INSERT INTO "
              + DatabaseManager.NODES_TABLE_NAME
              + " ("
              + DatabaseManager.NODE_NAME_KEY
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
              + " )"
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      try (PreparedStatement preparedStatement =
          DatabaseManager.getConnection().prepareStatement(insertStatement)) {
        int param = 1;
        preparedStatement.setString(param++, node.getName());
        preparedStatement.setShort(param++, node.getXCoord());
        preparedStatement.setShort(param++, node.getYCoord());
        preparedStatement.setString(param++, node.getBuilding());
        preparedStatement.setString(param++, node.getLongName());
        preparedStatement.setString(param++, node.getShortName());
        preparedStatement.setString(param++, node.getType().getTypeString());
        preparedStatement.setInt(param++, node.getFloor());
        try {
          int numRows = preparedStatement.executeUpdate();
          if (numRows < 1) {
            throw new Exception("Something went wrong with the creation of the node entry");
          }
          edgeFactory.create(node);
        } catch (SQLException e) {
          System.out.println((e.getMessage()));
        }
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Reads a node from the database
   *
   * @param name the name used to find the entry to read from the database
   * @return fully populated node
   * @throws Exception if anything goes wrong
   */
  public Node read(String name) throws Exception {
    Node node = null;
    try {
      Validators.nameValidation(name);
      String selectStatement =
          "SELECT "
              + DatabaseManager.NODE_NAME_KEY
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
              + " FROM "
              + DatabaseManager.NODES_TABLE_NAME
              + " WHERE "
              + DatabaseManager.NODE_NAME_KEY
              + " = ?";
      try (PreparedStatement preparedStatement =
          DatabaseManager.getConnection().prepareStatement(selectStatement)) {
        preparedStatement.setString(1, name);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          if (resultSet.next()) {
            String nodeName = resultSet.getString(DatabaseManager.NODE_NAME_KEY);
            short xCoord = resultSet.getShort(DatabaseManager.X_COORDINATE_KEY);
            short yCoord = resultSet.getShort(DatabaseManager.Y_COORDINATE_KEY);
            String building = resultSet.getString(DatabaseManager.BUILDING_KEY);
            String longName = resultSet.getString(DatabaseManager.LONG_NAME_KEY);
            String shortName = resultSet.getString(DatabaseManager.SHORT_NAME_KEY);
            Node.NodeType type =
                Node.NodeType.getEnum(resultSet.getString(DatabaseManager.TYPE_KEY));
            short floor = resultSet.getShort(DatabaseManager.FLOOR_KEY);
            Set<String> neighbors = edgeFactory.read(name);

            node = new Node(nodeName, xCoord, yCoord, building, longName, shortName, type, floor);
            node.addNeighbor(neighbors);
          } else {
            throw new InstanceNotFoundException("read did not find entry to read");
          }
        } catch (Exception e) {
          throw e;
        }
      }
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
    return node;
  }

  /**
   * Updates a node in the database
   *
   * @param node the node to update in the database
   * @throws Exception if anything goes wrong
   */
  public void update(Node node) throws Exception {
    try {
      Validators.nodeValidation(node);
      String updateStatement =
          "UPDATE "
              + DatabaseManager.NODES_TABLE_NAME
              + " SET "
              + DatabaseManager.NODE_NAME_KEY
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
              + DatabaseManager.NODE_NAME_KEY
              + " = ?";
      try (PreparedStatement preparedStatement =
          DatabaseManager.getConnection().prepareStatement(updateStatement)) {
        int param = 1;
        preparedStatement.setString(param++, node.getName());
        preparedStatement.setShort(param++, node.getXCoord());
        preparedStatement.setShort(param++, node.getYCoord());
        preparedStatement.setString(param++, node.getBuilding());
        preparedStatement.setString(param++, node.getLongName());
        preparedStatement.setString(param++, node.getShortName());
        preparedStatement.setString(param++, node.getType().getTypeString());
        preparedStatement.setInt(param++, node.getFloor());
        preparedStatement.setString(param++, node.getName());
        int numRows = preparedStatement.executeUpdate();
        if (numRows != 1) {
          throw new Exception("Updated " + numRows + " rows");
        }
        edgeFactory.update(node);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Deletes a node from the database
   *
   * @param node the node to delete from the database
   */
  public void delete(Node node) {
    try {
      Validators.nodeValidation(node);

      String deleteStatement =
          "DELETE FROM "
              + DatabaseManager.NODES_TABLE_NAME
              + " WHERE "
              + DatabaseManager.NODE_NAME_KEY
              + " = ?";
      try (PreparedStatement preparedStatement =
          DatabaseManager.getConnection().prepareStatement(deleteStatement)) {
        preparedStatement.setString(1, node.getName());

        int numRows = preparedStatement.executeUpdate();
        if (numRows != 1) {
          throw new Exception("Deleted " + numRows + " rows");
        }
        edgeFactory.delete(node);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
  }

  public List<Node> getNodesByType(Node.NodeType type) {
    List<Node> nodes = new LinkedList<>();
    String selectStatement =
        "SELECT * FROM "
            + DatabaseManager.NODES_TABLE_NAME
            + " WHERE "
            + DatabaseManager.TYPE_KEY
            + " = ? ";

    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      preparedStatement.setString(1, type.getTypeString());

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String nodeName = resultSet.getString(DatabaseManager.NODE_NAME_KEY);
        short xCoord = resultSet.getShort(DatabaseManager.X_COORDINATE_KEY);
        short yCoord = resultSet.getShort(DatabaseManager.Y_COORDINATE_KEY);
        String building = resultSet.getString(DatabaseManager.BUILDING_KEY);
        String longName = resultSet.getString(DatabaseManager.LONG_NAME_KEY);
        String shortName = resultSet.getString(DatabaseManager.SHORT_NAME_KEY);
        Node.NodeType nodeType =
            Node.NodeType.getEnum(resultSet.getString(DatabaseManager.TYPE_KEY));
        short floor = resultSet.getShort(DatabaseManager.FLOOR_KEY);
        Set<String> neighbors = edgeFactory.read(nodeName);
        Node node =
            new Node(nodeName, xCoord, yCoord, building, longName, shortName, nodeType, floor);
        node.addNeighbor(neighbors);
        nodes.add(node);
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    } catch (ValidationException e) {
      System.out.println("Validation Exception: " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    if (nodes.size() > 1) {
      return nodes;
    } else {
      return null;
    }
  }

  public ObservableList<Node> getAllNodes() {
    ObservableList<Node> nodes = null;
    String selectStatement = "SELECT * FROM " + DatabaseManager.NODES_TABLE_NAME;
    try (PreparedStatement preparedStatement =
        DatabaseManager.getConnection().prepareStatement(selectStatement)) {
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String nodeName = resultSet.getString(DatabaseManager.NODE_NAME_KEY);
        short xCoord = resultSet.getShort(DatabaseManager.X_COORDINATE_KEY);
        short yCoord = resultSet.getShort(DatabaseManager.Y_COORDINATE_KEY);
        String building = resultSet.getString(DatabaseManager.BUILDING_KEY);
        String longName = resultSet.getString(DatabaseManager.LONG_NAME_KEY);
        String shortName = resultSet.getString(DatabaseManager.SHORT_NAME_KEY);
        Node.NodeType nodeType =
            Node.NodeType.getEnum(resultSet.getString(DatabaseManager.TYPE_KEY));
        short floor = resultSet.getShort(DatabaseManager.FLOOR_KEY);
        Set<String> neighbors = edgeFactory.read(nodeName);
        Node node =
            new Node(nodeName, xCoord, yCoord, building, longName, shortName, nodeType, floor);
        node.addNeighbor(neighbors);
        nodes.add(node);
      }
    } catch (SQLException e) {
      System.out.println("SQL Exception: " + e.getMessage());
    } catch (ValidationException e) {
      System.out.println("Validation Exception: " + e.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return nodes;
  }
}
