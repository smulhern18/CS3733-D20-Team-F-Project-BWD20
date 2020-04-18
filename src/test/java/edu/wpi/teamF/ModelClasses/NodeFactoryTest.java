package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.TestData;
import edu.wpi.teamF.ModelClasses.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.InstanceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class NodeFactoryTest {


    static TestData testData = null;
    static Node[] validNodes = null;
    NodeFactory nodeFactory = NodeFactory.getFactory();

    @BeforeEach
    public static void initialize() throws ValidationException{
        testData = new TestData();
        validNodes = testData.validNodes;

    }


    @Test
    public void testCreateReadUpdateDelete() {
        try {
            nodeFactory.create(null);
            fail("Creating a null value is unacceptable");
        } catch (ValidationException e ) {
            // ignore as expected
        }
        try {
            for (Node node: validNodes) {
                nodeFactory.create(node);

                Node readNode = nodeFactory.read(node.getId());

                assertTrue(readNode.equals(node));

                nodeFactory.delete(node.getId());

                try {
                    readNode = nodeFactory.read(node.getId());
                } catch (InstanceNotFoundException e) {
                    // ignore
                } catch (Exception e) {
                    fail( e.getMessage() + ", " + e.getClass());
                }
            }
        } catch (Exception e) {
            fail(e.getMessage() + ", " + e.getClass());
        }
    }
}
