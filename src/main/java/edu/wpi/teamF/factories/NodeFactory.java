package edu.wpi.teamF.factories;
import edu.wpi.teamF.modelClasses.Node;
import edu.wpi.teamF.modelClasses.ValidationException;
import edu.wpi.teamF.modelClasses.Validators;
import java.util.List;
import javax.management.InstanceNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.Set;

public class NodeFactory {

  /**
   * Creates node entry in database
   *
   * @param node node to create
   * @throws Exception should anything go wrong
   */
  public void createNode(Node node) throws Exception{
    String insertStatement =
            "Insert Info "
                    + DatabaseManager.NODES_TABLE_NAME
                    + " ("
                    + DatabaseManager.NODE_NAME_KEY
                    + ", "
                    + DatabaseManager.X_COORDINATE_KEY
                    + ", "
                    + DatabaseManager.Y_COORDINATE_KEY
                    + ", "
                    + DatabaseManager.TYPE_KEY
                    + ", "
                    + DatabaseManager.FLOOR_KEY
                    + " )"
                    + "Values (?, ? ? ? ?)";
    try(PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement(insertStatement)){
      int param = 1;
      preparedStatement.setString(param++, node.getName());
      preparedStatement.setShort(param++, node.getXCoord());
      preparedStatement.setShort(param++, node.getYCoord());
      preparedStatement.setString(param++, node.getType());
      //Should these be setString? Or use their type from Node?
      preparedStatement.setInt(param++, node.getFloor());
      try{
        int numRows = preparedStatement.executeUpdate();
        if(numRows < 1){
          throw new Exception("Something went wrong with the creation of the node entry");
        }
      } catch (SQLException e){
        System.out.println((e.getMessage()));
      }

    } catch (SQLException e){
      System.out.println(e.getMessage());
    }

  }

  /**
   * Reads a node from the database
   * @param name the name used to find the entry to read from the database
   * @return fully populated node
   * @throws Exception if anything goes wrong
   */
  public Node readNode(String name) throws Exception{
    Node node = null;
    String selectStatement =
            "SELECT "
                    + DatabaseManager.NODE_NAME_KEY
                    + ", "
                    + DatabaseManager.X_COORDINATE_KEY
                    + ", "
                    + DatabaseManager.Y_COORDINATE_KEY
                    + ", "
                    + DatabaseManager.TYPE_KEY
                    + ", "
                    + DatabaseManager.FLOOR_KEY
                    + "FROM "
                    + DatabaseManager.NODES_TABLE_NAME
                    + " WHERE "
                    + DatabaseManager.NODE_NAME_KEY
                    + " = ?";
    try (PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement(selectStatement)){
      preparedStatement.setString(1, name);
      try(ResultSet resultSet = preparedStatement.executeQuery()){
        if(resultSet.next()){
          String nodeName = resultSet.getString(DatabaseManager.NODE_NAME_KEY);
          short xCoord = resultSet.getShort(DatabaseManager.X_COORDINATE_KEY);
          short yCoord = resultSet.getShort(DatabaseManager.Y_COORDINATE_KEY);
          Node.NodeType type = resultSet.getString(DatabaseManager.TYPE_KEY);
          //Should these be getString or get with the type from Node?
          int floor = resultSet.getInt(DatabaseManager.FLOOR_KEY);
          Set<Node> neighbors;
          //Not sure what to do with the set of neighbor nodes.

          node = new Node(nodeName, xCoord, yCoord, type, floor, neighbors);
        }
        else {
          throw new InstanceNotFoundException("Delete did not find entry to delete");
        }
      }catch (Exception e){
        throw e;
      }
      return node;
    }

  }

  /**
   *  Updates a node in the database
   * @param node the node to update in the database
   * @throws Exception if anything goes wrong
   */
  public void updateNode(Node node) throws Exception{

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
                    + DatabaseManager.TYPE_KEY
                    + " = ?, "
                    + DatabaseManager.FLOOR_KEY
                    + " = ?, ";
    try (PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement(updateStatement)){
      int param = 1;
      preparedStatement.setString(param++, node.getName());
      preparedStatement.setShort(param++, node.getXCoord());
      preparedStatement.setShort(param++, node.getYCoord());
      preparedStatement.setString(param++, node.getType());
      //Same question about set.
      preparedStatement.setInt(param++, node.getFloor());
      int numRows = preparedStatement.executeUpdate();
      if(numRows != 1){
        throw new Exception("Updated " + numRows + " rows");
      }

    } catch (Exception e){
      System.out.println(e.getMessage());
    }

  }

  /**
   * Deletes a node from the database
   * @param node the node to delete from the database
   */
  public void deleteNode(Node node){

    String deleteStatement =
            "DELETE FROM " + DatabaseManager.NODES_TABLE_NAME + " WHERE " + DatabaseManager.NODE_NAME_KEY + " = ?";
    try(PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement(deleteStatement)){
      preparedStatement.setString(1, node.getName());

      int numRows = preparedStatement.executeUpdate();
      if(numRows != 1){
        throw new Exception("Deleted " + numRows + " rows");
      }
    } catch (Exception e){
      System.out.println(e.getMessage());
    }

  }

  public List<Node> getNodes(String elev) {

    return null;
  }

  public Node read(String name) {
    return null;
  }
}
