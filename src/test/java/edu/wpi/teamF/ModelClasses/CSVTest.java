package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.Factories.NodeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVTest {

  /** Utility classes instantiation */
  @BeforeEach
  public void initBeforeEachTest() {
    try {
      // testData = new TestData();
    } catch (Exception e) {
      fail("Test data cannot initialize: " + e.getMessage());
    }
    /** Valid data */
  }

  @Test
  public void testReadAndWriteCSVNodes() {
    NodeFactory nodeFactory = new NodeFactory();

    System.out.println("Wrote to CSV File");

    System.out.println("Read from CSV File");
  }
}
