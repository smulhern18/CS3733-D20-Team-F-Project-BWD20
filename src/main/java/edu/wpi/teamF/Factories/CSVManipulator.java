package edu.wpi.teamF.Factories;

import edu.wpi.teamF.ModelClasses.Node;
import java.io.*;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.management.InstanceNotFoundException;
import org.apache.derby.iapi.jdbc.BrokeredConnection;

public class CSVManipulator {
  private NodeFactory nodeFactory = new NodeFactory();

  /** reads a csv file and insert the data in the file into the correct places in the database */
  public ArrayList<String> readCSVFile(Path path) {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      // goes to get the file
      BufferedReader csvReader = new BufferedReader(new FileReader(path.toFile()));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }

      for (int i = 0; i < data.size(); i = i + 8) {
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

    return data;
  }

  /** Writes to the CSV file so that it can become persistant */
  public void writeCSVFile() {

    String csvString = "";
    String selectStatement = "SELECT * FROM ";

    BrokeredConnection connection = null;
    try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        // need to loop here to get all entries and call the 2 functions below
        if (resultSet.next()) {
          // how the stuff should be written
        } else {
          throw new InstanceNotFoundException("Delete did not find a entry to delete");
        }
      }
      // Start formatting and writing to CSV
    } catch (Exception e) {
      System.out.println(e);
    }
    // writing to the file
    try (FileWriter fw = new FileWriter("testText.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(csvString);
    } catch (IOException e) {
      // exception handling left as an exercise for the reader
    }
  }
}
