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


public class AStarTest {
    static NodeFactory nodeFactory;
    static PathfindController pathfinder;
    static EuclideanScorer euclidScorer;
    static ElevatorScorer2 elevatorScorer2;
    static List<Node> elevators;


    @BeforeAll
    public void setup() {
        nodeFactory = new NodeFactory();
        elevators = nodeFactory.getNodes(Node.NodeType.ELEV);
        pathfinder = new PathfindController(nodeFactory);
        euclidScorer = new EuclideanScorer();
        elevatorScorer2 = new ElevatorScorer2(elevators);
    }
}
