package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.*;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class ComputerServiceControllerTest extends ApplicationTest {

  // Initialize the databases needed. Need database manager, nodes (for locations) and obv. the
  // computerservicerequest

  DatabaseManager databaseManager = DatabaseManager.getManager();
  List<ComputerServiceRequest> computerServiceRequest =
      databaseManager.getAllComputerServiceRequests();

  public ComputerServiceControllerTest() throws Exception {}

  // populate the factories with the needed data

  @BeforeAll
  public static void setUp() throws Exception {
    ApplicationTest.launch(App.class);
  }

  // navigate to computer service request
  @Start
  public void start(Stage stage) throws IOException {
    stage.show();
    clickOn("Service Requests");
    sleep(500);
    clickOn("Computer Service Requests");
  }

  @AfterAll
  static void tearDown() throws SQLException {
    //
  }

  // test making nodes

  // test updating nodes

}
