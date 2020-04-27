package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.LanguageServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class anguageFactoryTest {

    static TestData testData = null;
    static ComputerServiceRequest[] validLanguageServiceRequest = null;
    LanguageServiceRequestFactory computerServiceRequestFactory =
            LanguageServiceRequestFactory.getFactory();
    NodeFactory nodeFactory = NodeFactory.getFactory();
    static DatabaseManager databaseManager = new DatabaseManager();
    static Node[] validNodes = null;

    @BeforeEach
    public void initialize() throws Exception {
        testData = new TestData();
        validLanguageServiceRequest = testData.validLanguageServiceRequests;
        validNodes = testData.validNodes;
        databaseManager.initialize();
        databaseManager.reset();
    }

    @AfterAll
    public static void reset() throws SQLException {
        databaseManager.reset();
    }

    @Test
    public void testCreateReadDelete() {
        try {
            computerServiceRequestFactory.create(null);
            fail("Creating a null value is unacceptable");
        } catch (ValidationException e) {
            // ignore as expected
        }
        try {
            nodeFactory.create(validNodes[0]);
            nodeFactory.create(validNodes[1]);
            nodeFactory.create(validNodes[2]);
            nodeFactory.create(validNodes[3]);

        } catch (Exception e) {

        }
        try {
            for (ComputerServiceRequest computerServiceRequest : validLanguageServiceRequest) {
                computerServiceRequestFactory.create(computerServiceRequest);

                ComputerServiceRequest readComputer =
                        computerServiceRequestFactory.read(computerServiceRequest.getId());
                assertTrue(readComputer.equals(computerServiceRequest));

                computerServiceRequestFactory.delete(computerServiceRequest.getId());

                try {
                    readComputer = computerServiceRequestFactory.read(computerServiceRequest.getId());
                } // catch (InstanceNotFoundException e) {
                // ignore
                // }
                catch (Exception e) {
                    fail(e.getMessage() + ", " + e.getClass());
                }
            }
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }

    @Test
    public void testCreateReadUpdateDelete() {

        try {
            nodeFactory.create(validNodes[0]);
            nodeFactory.create(validNodes[1]);
            nodeFactory.create(validNodes[2]);
            nodeFactory.create(validNodes[3]);

        } catch (Exception e) {

        }
        try {

            for (ComputerServiceRequest computerServiceRequest : validLanguageServiceRequest) {
                computerServiceRequestFactory.create(computerServiceRequest);

                computerServiceRequest.setDescription("Hello");
                computerServiceRequestFactory.update(computerServiceRequest);

                ComputerServiceRequest readComp =
                        computerServiceRequestFactory.read(computerServiceRequest.getId());

                assertTrue(computerServiceRequest.equals(readComp));

                computerServiceRequestFactory.delete(computerServiceRequest.getId());
            }
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }

    @Test
    public void testGetMainByLocation() {
        try {
            nodeFactory.create(validNodes[0]);
            nodeFactory.create(validNodes[1]);
            nodeFactory.create(validNodes[2]);
            nodeFactory.create(validNodes[3]);

        } catch (Exception e) {

        }
        ComputerServiceRequest main1 = validLanguageServiceRequest[0];
        ComputerServiceRequest main2 = validLanguageServiceRequest[1];
        ComputerServiceRequest main3 = validLanguageServiceRequest[2];
        ComputerServiceRequest main4 = validLanguageServiceRequest[3];

        NodeFactory nodeFactory = NodeFactory.getFactory();

        try {
            computerServiceRequestFactory.create(main1);
            computerServiceRequestFactory.create(main2);
            computerServiceRequestFactory.create(main3);
            computerServiceRequestFactory.create(main4);

            List<ComputerServiceRequest> computerAtBathroom =
                    computerServiceRequestFactory.getComputerRequestsByLocation(testData.validNodes[0]);

            assertTrue(computerAtBathroom.contains(main1));

            assertTrue(computerAtBathroom.size() == 1);

            List<ComputerServiceRequest> computerAtnode2 =
                    computerServiceRequestFactory.getComputerRequestsByLocation(testData.validNodes[1]);

            assertTrue(computerAtnode2.contains(main2));
            assertTrue(computerAtnode2.size() == 1);

            computerServiceRequestFactory.delete(main1.getId());
            computerServiceRequestFactory.delete(main2.getId());
            computerServiceRequestFactory.delete(main3.getId());
            computerServiceRequestFactory.delete(main4.getId());
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }

    @Test
    public void testGetAllMaintenanceRequests() {
        try {
            nodeFactory.create(validNodes[0]);
            nodeFactory.create(validNodes[1]);
            nodeFactory.create(validNodes[2]);
            nodeFactory.create(validNodes[3]);

        } catch (Exception e) {

        }
        ComputerServiceRequest main1 = validLanguageServiceRequest[0];
        ComputerServiceRequest main2 = validLanguageServiceRequest[1];
        ComputerServiceRequest main3 = validLanguageServiceRequest[2];
        ComputerServiceRequest main4 = validLanguageServiceRequest[3];

        try {
            computerServiceRequestFactory.create(main1);
            computerServiceRequestFactory.create(main2);
            computerServiceRequestFactory.create(main3);
            computerServiceRequestFactory.create(main4);
            List<ComputerServiceRequest> computerAll =
                    computerServiceRequestFactory.getAllComputerRequests();

            assertTrue(computerAll.contains(main1));
            assertTrue(computerAll.contains(main2));
            assertTrue(computerAll.contains(main3));
            assertTrue(computerAll.contains(main4));
            assertTrue(computerAll.size() == 4);

            computerServiceRequestFactory.delete(main1.getId());
            computerServiceRequestFactory.delete(main2.getId());
            computerServiceRequestFactory.delete(main3.getId());
            computerServiceRequestFactory.delete(main4.getId());
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }
}