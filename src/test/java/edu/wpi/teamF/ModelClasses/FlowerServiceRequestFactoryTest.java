package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.FlowerServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.FlowerRequest;
import edu.wpi.teamF.TestData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FlowerServiceRequestFactoryTest {

    static TestData testData = null;
    static FlowerRequest[] validFlowerRequest = null;
    FlowerServiceRequestFactory flowerRequestFactory = FlowerServiceRequestFactory.getFactory();
    NodeFactory nodeFactory = NodeFactory.getFactory();
    static DatabaseManager databaseManager = new DatabaseManager();
    static Node[] validNodes = null;

    @BeforeAll
    public static void databaseIntialize() throws Exception {
        databaseManager.initialize();
    }

    @BeforeEach
    public void initialize() throws Exception {
        testData = new TestData();
        validFlowerRequest = testData.validFlowerRequests;
        validNodes = testData.validNodes;
        databaseManager.reset();
    }

    @AfterAll
    public static void reset() throws SQLException {
        databaseManager.reset();
    }

    @Test
    public void testCreateReadDelete() {
        try {
            flowerRequestFactory.create(null);
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
            for (FlowerRequest flowerRequest : validFlowerRequest) {
                flowerRequestFactory.create(flowerRequest);

                FlowerRequest readFlower = flowerRequestFactory.read(flowerRequest.getId());
                assertTrue(readFlower.equals(flowerRequest));

                flowerRequestFactory.delete(flowerRequest.getId());

                try {
                    readFlower = flowerRequestFactory.read(flowerRequest.getId());
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

            for (FlowerRequest flowerRequest : validFlowerRequest) {
                flowerRequestFactory.create(flowerRequest);

                flowerRequest.setDescription("Hello");
                flowerRequestFactory.update(flowerRequest);

                FlowerRequest readMain = flowerRequestFactory.read(flowerRequest.getId());

                assertTrue(flowerRequest.equals(readMain));

                flowerRequestFactory.delete(flowerRequest.getId());
            }
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }

    @Test
    public void testGetFlowersByLocation() {
        try {
            nodeFactory.create(validNodes[0]);
            nodeFactory.create(validNodes[1]);
            nodeFactory.create(validNodes[2]);
            nodeFactory.create(validNodes[3]);

        } catch (Exception e) {

        }
        FlowerRequest main1 = validFlowerRequest[0];
        FlowerRequest main2 = validFlowerRequest[1];
        FlowerRequest main3 = validFlowerRequest[2];
        FlowerRequest main4 = validFlowerRequest[3];

        NodeFactory nodeFactory = NodeFactory.getFactory();

        try {
            flowerRequestFactory.create(main1);
            flowerRequestFactory.create(main2);
            flowerRequestFactory.create(main3);
            flowerRequestFactory.create(main4);

            List<FlowerRequest> flowersAtBathroom =
                    flowerRequestFactory.getFlowerRequestsByLocation(testData.validNodes[0]);

            assertTrue(flowersAtBathroom.contains(main1));

            assertTrue(flowersAtBathroom.size() == 1);

            List<FlowerRequest> flowersAtnode2 =
                    flowerRequestFactory.getFlowersRequestsByLocation(testData.validNodes[1]);

            assertTrue(flowersAtnode2.contains(main2));
            assertTrue(flowersAtnode2.size() == 1);

            flowerRequestFactory.delete(main1.getId());
            flowerRequestFactory.delete(main2.getId());
            flowerRequestFactory.delete(main3.getId());
            flowerRequestFactory.delete(main4.getId());
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }

    @Test
    public void testGetAllFlowerRequests() {
        try {
            nodeFactory.create(validNodes[0]);
            nodeFactory.create(validNodes[1]);
            nodeFactory.create(validNodes[2]);
            nodeFactory.create(validNodes[3]);

        } catch (Exception e) {

        }
        FlowerRequest main1 = validFlowerRequest[0];
        FlowerRequest main2 = validFlowerRequest[1];
        FlowerRequest main3 = validFlowerRequest[2];
        FlowerRequest main4 = validFlowerRequest[3];

        try {
            flowerRequestFactory.create(main1);
            flowerRequestFactory.create(main2);
            flowerRequestFactory.create(main3);
            flowerRequestFactory.create(main4);

            List<FlowerRequest> flowersAll = flowerRequestFactory.getAllFlowerRequests();

            assertTrue(flowersAll.contains(main1));
            assertTrue(flowersAll.contains(main2));
            assertTrue(flowersAll.contains(main3));
            assertTrue(flowersAll.contains(main4));
            assertTrue(flowersAll.size() == 4);

            nodeFactory.delete(main1.getId());
            nodeFactory.delete(main2.getId());
            nodeFactory.delete(main3.getId());
            nodeFactory.delete(main4.getId());
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }
}
