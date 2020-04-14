package edu.wpi.teamF.factories;

import edu.wpi.teamF.modelClasses.Node;
import edu.wpi.teamF.modelClasses.Node.NodeType;
import java.io.*;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.management.InstanceNotFoundException;
import org.apache.derby.iapi.jdbc.BrokeredConnection;

public class CSVManipulator {
  private NodeFactory nodeFactory = new NodeFactory();
  private EdgeFactory edgeFactory = new EdgeFactory();
  /** reads a csv file that contains nodes and inserts the data in the file into the correct place in the database */
  public void readCSVFileNode(Path path) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new FileReader(path.toFile()));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      for (int i = 0; i < data.size(); i = i + 8) {
        // ask how to turn string into node type
        nodeFactory.create(
            new Node(
                data.get(i),
                Short.parseShort(data.get(i + 1)),
                Short.parseShort(data.get(i + 2)),
                data.get(i + 3),
                data.get(i + 4),
                data.get(i + 5),
                Node.NodeType.getEnum(data.get(i + 6)),
                Short.parseShort(data.get(i + 7))));
      }

    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File Not found!");
    } catch (EOFException e) {
      // Expected use to end read csv
    } catch (IOException e) {

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /** Writes to the CSV file so that it can become persistant */
  public void writeCSVFileNode() {
    String csvString = "";
    // writing to the file

    for (Node n: Node) {
      csvString = csvString + formatNode(n);
    }
    try (FileWriter fw = new FileWriter("testText.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(csvString);
    } catch (IOException e) {
      // exception handling left as an exercise for the reader
    }
  }

  /**
   * formats a node into csv
   * @param node
   * @return returns a node in the form of a string
   */
  public String formatNode(Node node){
    String string = "";
   string =  node.getName() +",";
    string = string + String.valueOf(node.getXCoord()) + ",";
    string = string + String.valueOf(node.getYCoord())+ ",";
    string = string + node.getBuilding()+ ",";
    string = string + node.getLongName()+ ",";
    string = string + node.getShortName()+ ",";
    string = string + node.getType().getTypeString()+ ",";
    string = string + String.valueOf(node.getFloor()) + ",";

    return string;
  }

  /** reads a csv file that contains edges and inserts the data in the file into the correct place in the database */
  public void readCSVFileEdge(Path path) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new FileReader(path.toFile()));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      for (int i = 0; i < data.size(); i = i + 8) {
        // ask how to turn string into node type

      }

    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File Not found!");
    } catch (EOFException e) {
      // Expected use to end read csv
    } catch (IOException e) {

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /** Writes to the CSV file so that it can become persistant */
  public void writeCSVFileEdge() {
    String csvString = "";
    // writing to the file
    for( n: ) {
      csvString = csvString + formatNode(n);
    }
    try (FileWriter fw = new FileWriter("testText.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(csvString);
    } catch (IOException e) {
      // exception handling left as an exercise for the reader
    }
  }
}
