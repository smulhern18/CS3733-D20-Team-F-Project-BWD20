package edu.wpi.teamF;


import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;

public class TestData {

    public String[] validEdgeIDs = {
            "nodeA_nodeH",
            "nodeB_nodeG",
            "nodeC_nodeF",
            "nodeD_nodeE"
    };

    public String[] invalidEdgeIDs = {
            "nodeA",
            "",
            null,
            "this string is waaaaaaaaay toooooooo loooooooong to be valid"
    };

    public String[] validNodeIDs = {
            "nodeA",
            "nodeB",
            "nodeC",
            "nodeD",
            "nodeE",
            "nodeF",
            "nodeG",
            "nodeH"
    };

    public String[] invalidNodeIDS = {
            "",
            null,
            "this string is waaaaaaaaay toooooooo loooooooong to be valid"
    };

    public Short[] validCoordinates = {
            10,
            20,
            100,
            2
    };

    public Short[] invalidCoordinates = {
            0,
            -100,
            null
    };

    public String[] validBuildings = {
            "Building A",
            "Building B",
            "Evil Headquarters",
            "That one"
    };

    public String[] invalidBuildings = {
            "",
            null,
            "this string is waaaaaaaaay toooooooo loooooooong to be valid"
    };

    public String[] validLongNames = {
            "Intensive Care Unit",
            "Administrative offices",
            "Department of Computer Science",
            "Hallway outside everything"
    };

    public String[] invalidLongNames = {
            "",
            null,
            "Did you ever hear the Tragedy of Darth Plagueis the wise? I thought not. It’s not a story the Jedi would tell you. It’s a Sith legend. Darth Plagueis was a Dark Lord of the Sith, so powerful and so wise he could use the Force to influence the midichlorians to create life... He had such a knowledge of the dark side that he could even keep the ones he cared about from dying. The dark side of the Force is a pathway to many abilities some consider to be unnatural. He became so powerful... the only thing he was afraid of was losing his power, which eventually, of course, he did. Unfortunately, he taught his apprentice everything he knew, then his apprentice killed him in his sleep. It’s ironic he could save others from death, but not himself."
    };

    public String[] validShortNames = {
            "ICU",
            "Admin Offices",
            "Dept of CS",
            "HallOutside"
    };

    public String[] invalidShortNames = {
            "",
            null,
            "this string is waaaaaaaaay toooooooo loooooooong to be valid"
    };

    public Short[] validFloors = {
            1,
            5,
            10,
            3
    };

    public Short[] invalidFloors = {
            null,
            -3,
            0
    };

    public Node.NodeType[] validNodeTypes = Node.NodeType.values();

    public Node.NodeType[] invalidNodeTypes = {
            null
    };

    public Node[] validNodes = {
            new Node(
                    validNodeIDs[0],
                    validCoordinates[0],
                    validCoordinates[0],
                    validBuildings[0],
                    validLongNames[0],
                    validShortNames[0],
                    validNodeTypes[0],
                    validFloors[0]
            ),
            new Node(
                    validNodeIDs[1],
                    validCoordinates[1],
                    validCoordinates[1],
                    validBuildings[1],
                    validLongNames[1],
                    validShortNames[1],
                    validNodeTypes[1],
                    validFloors[1]
            ),
            new Node(
                    validNodeIDs[2],
                    validCoordinates[2],
                    validCoordinates[2],
                    validBuildings[2],
                    validLongNames[2],
                    validShortNames[2],
                    validNodeTypes[2],
                    validFloors[2]
            ),
            new Node(
                    validNodeIDs[3],
                    validCoordinates[3],
                    validCoordinates[3],
                    validBuildings[3],
                    validLongNames[3],
                    validShortNames[3],
                    validNodeTypes[3],
                    validFloors[3]
            ),
            new Node(
                    validNodeIDs[4],
                    validCoordinates[0],
                    validCoordinates[0],
                    validBuildings[0],
                    validLongNames[0],
                    validShortNames[0],
                    validNodeTypes[4],
                    validFloors[0]
            ),
            new Node(
                    validNodeIDs[5],
                    validCoordinates[1],
                    validCoordinates[1],
                    validBuildings[1],
                    validLongNames[1],
                    validShortNames[1],
                    validNodeTypes[5],
                    validFloors[1]
            ),
            new Node(
                    validNodeIDs[6],
                    validCoordinates[2],
                    validCoordinates[2],
                    validBuildings[2],
                    validLongNames[2],
                    validShortNames[2],
                    validNodeTypes[6],
                    validFloors[2]
            ),
            new Node(
                    validNodeIDs[7],
                    validCoordinates[3],
                    validCoordinates[3],
                    validBuildings[3],
                    validLongNames[3],
                    validShortNames[3],
                    validNodeTypes[7],
                    validFloors[3]
            )
    };

    public Edge[] validEdges = {
            new Edge(
                    validEdgeIDs[0],
                    validNodes[0],
                    validNodes[7]
            ),
            new Edge(
                    validEdgeIDs[1],
                    validNodes[1],
                    validNodes[6]
            ),
            new Edge(
                    validEdgeIDs[2],
                    validNodes[2],
                    validNodes[5]
            ),
            new Edge(
                    validEdgeIDs[3],
                    validNodes[3],
                    validNodes[4]
            )
    };


    public TestData() throws ValidationException {
    }
}
