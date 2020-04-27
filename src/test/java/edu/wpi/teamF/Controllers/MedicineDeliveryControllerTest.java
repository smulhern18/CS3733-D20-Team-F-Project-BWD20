package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.*;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MedicineDeliveryRequest;
import edu.wpi.teamF.TestData;
import java.io.IOException;
import java.sql.SQLException;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

public class MedicineDeliveryControllerTest extends ApplicationTest {

  // Initialize the databases needed. Need database manager, nodes (for locations) and obv. the
  // computerservicerequest
  private static DatabaseManager db = new DatabaseManager();
  private static NodeFactory nodes = NodeFactory.getFactory();
  private static MedicineDeliveryRequestFactory medicineDeliveryRequestFactory =
      MedicineDeliveryRequestFactory.getFactory();

  // populate the factories with the needed data

  @BeforeAll
  public static void setUp() throws Exception {
    db.initialize();
    TestData testData = new TestData();
    for (MedicineDeliveryRequest mdr : testData.validMedicineDeliveryRequests) {
      medicineDeliveryRequestFactory.create(mdr);
    }
    for (Node node : testData.validNodes) {
      nodes.create(node);
    }
    ApplicationTest.launch(App.class);
  }

  // navigate to computer service request
  @Start
  public void start(Stage stage) throws IOException {
    stage.show();
    clickOn("Service Requests");
    sleep(500);
    clickOn("Medicine Delivery Requests");
  }

  @AfterAll
  static void tearDown() throws SQLException {
    db.reset();
  }

  // test making nodes

  // test updating nodes

}
