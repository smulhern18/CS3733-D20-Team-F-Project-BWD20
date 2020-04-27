package edu.wpi.teamF.ModelClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.wpi.teamF.DatabaseManipulators.LanguageServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LanguageServiceRequest;
import edu.wpi.teamF.TestData;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LanguageFactoryTest {

    static TestData testData = null;
    static LanguageServiceRequest[] validLanguageServiceRequest = null;
    LanguageServiceRequestFactory languageServiceRequestFactory =
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
            languageServiceRequestFactory.create(null);
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
            for (LanguageServiceRequest languageServiceRequest : validLanguageServiceRequest) {
                languageServiceRequestFactory.create(languageServiceRequest);

                LanguageServiceRequest readLanguage =
                        languageServiceRequestFactory.read(languageServiceRequest.getId());
                assertTrue(readLanguage.equals(languageServiceRequest));

                languageServiceRequestFactory.delete(languageServiceRequest.getId());

                try {
                    readLanguage = languageServiceRequestFactory.read(languageServiceRequest.getId());
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

            for (LanguageServiceRequest languageServiceRequest : validLanguageServiceRequest) {
                languageServiceRequestFactory.create(languageServiceRequest);

                languageServiceRequest.setDescription("Hello");
                languageServiceRequestFactory.update(languageServiceRequest);

                LanguageServiceRequest readLanguage =
                        languageServiceRequestFactory.read(languageServiceRequest.getId());

                assertTrue(languageServiceRequest.equals(readLanguage));

                languageServiceRequestFactory.delete(languageServiceRequest.getId());
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
        LanguageServiceRequest main1 = validLanguageServiceRequest[0];
        LanguageServiceRequest main2 = validLanguageServiceRequest[1];
        LanguageServiceRequest main3 = validLanguageServiceRequest[2];
        LanguageServiceRequest main4 = validLanguageServiceRequest[3];

        NodeFactory nodeFactory = NodeFactory.getFactory();

        try {
            languageServiceRequestFactory.create(main1);
            languageServiceRequestFactory.create(main2);
            languageServiceRequestFactory.create(main3);
            languageServiceRequestFactory.create(main4);

            List<LanguageServiceRequest> languageAtBathroom =
                    languageServiceRequestFactory.getLanguageRequestsByLocation(testData.validNodes[0]);

            assertTrue(languageAtBathroom.contains(main1));

            assertTrue(languageAtBathroom.size() == 1);

            List<LanguageServiceRequest> languageAtnode2 =
                    languageServiceRequestFactory.getLanguageRequestsByLocation(testData.validNodes[1]);

            assertTrue(languageAtnode2.contains(main2));
            assertTrue(languageAtnode2.size() == 1);

            languageServiceRequestFactory.delete(main1.getId());
            languageServiceRequestFactory.delete(main2.getId());
            languageServiceRequestFactory.delete(main3.getId());
            languageServiceRequestFactory.delete(main4.getId());
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
        LanguageServiceRequest main1 = validLanguageServiceRequest[0];
        LanguageServiceRequest main2 = validLanguageServiceRequest[1];
        LanguageServiceRequest main3 = validLanguageServiceRequest[2];
        LanguageServiceRequest main4 = validLanguageServiceRequest[3];

        try {
            languageServiceRequestFactory.create(main1);
            languageServiceRequestFactory.create(main2);
            languageServiceRequestFactory.create(main3);
            languageServiceRequestFactory.create(main4);
            List<LanguageServiceRequest> languageAll =
                    languageServiceRequestFactory.getAllLanguageRequests();

            assertTrue(languageAll.contains(main1));
            assertTrue(languageAll.contains(main2));
            assertTrue(languageAll.contains(main3));
            assertTrue(languageAll.contains(main4));
            assertTrue(languageAll.size() == 4);

            languageServiceRequestFactory.delete(main1.getId());
            languageServiceRequestFactory.delete(main2.getId());
            languageServiceRequestFactory.delete(main3.getId());
            languageServiceRequestFactory.delete(main4.getId());
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }
}