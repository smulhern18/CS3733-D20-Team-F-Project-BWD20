package edu.wpi.teamF;


import edu.wpi.teamF.controllers.PathfindController;
import edu.wpi.teamF.factories.NodeFactory;
import edu.wpi.teamF.modelClasses.ElevatorScorer;
import edu.wpi.teamF.modelClasses.ElevatorScorer2;
import edu.wpi.teamF.modelClasses.EuclideanScorer;
import edu.wpi.teamF.modelClasses.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AStarTest {
    static NodeFactory nodeFactory;
    static PathfindController pathfinder;



    @BeforeAll
    public void setup() {
        nodeFactory = new NodeFactory();
        pathfinder = new PathfindController(nodeFactory);

    }


    @Test
    public void testElevatorScorer2_1(){
        Node elev1 = new Node(1,1,"elev1", Node.NodeType.ELEV, 1);
        Node elev2 = new Node(10,10,"elev2", Node.NodeType.ELEV, 1);
        Node elev3 = new Node(8,3,"elev3", Node.NodeType.ELEV, 1);
        List<Node> elevators = Arrays.asList(elev1, elev2, elev3);
        ElevatorScorer2 tester = new ElevatorScorer2(elevators);


    }
}
