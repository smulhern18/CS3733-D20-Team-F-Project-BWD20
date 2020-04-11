package edu.wpi.teamF.factories;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.management.InstanceNotFoundException;


public class CSVManipulator {


  /**
   * reads a csv file and insert the data in the file into the correct places in the database
   */
  public void readCSVFile() {
    String row = "";
    ArrayList<String> data = new ArrayList<>();
    try {
      //goes to get the file
      BufferedReader csvReader =
          new BufferedReader(new FileReader(getClass().getResource("../csv/Test.csv").getFile()));
      while ((row = csvReader.readLine()) != null) {
        data.addAll(Arrays.asList(row.split(",")));
      }
      //data now has all the data in a list ready to be used
      //  for(int i =0)
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
  public void writeCSVFile() {

    String csvString = "";
    String selectStatement = "SELECT * FROM ";

    try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        // need to loop here to get all entries and call the 2 functions below
        if (resultSet.next()) {
          //how the stuff should be written
        } else {
          throw new InstanceNotFoundException("Delete did not find a entry to delete");
        }
      }
      // Start formatting and writing to CSV
    } catch (Exception e) {
      System.out.println(e);
    }
  //writing to the file
    try (FileWriter fw = new FileWriter("testText.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {
      out.println(csvString);
    } catch (IOException e) {
      // exception handling left as an exercise for the reader
    }
  }
}
