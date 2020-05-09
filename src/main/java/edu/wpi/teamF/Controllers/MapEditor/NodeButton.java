package edu.wpi.teamF.Controllers.MapEditor;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Node;

import edu.wpi.teamF.ModelClasses.ValidationException;
import lombok.Data;


@Data
public class NodeButton {
    Node node;
    Node tempNode;

    private DatabaseManager databaseManager = DatabaseManager.getManager();

    public NodeButton(Node node) {
        this.node = node;

    }

    public NodeButton(String shortName, String longName, String xCoord, String yCoord, String building, String floor, String type) throws Exception {
        createNode(shortName, longName, xCoord, yCoord, building, floor, type);
        this.tempNode = new Node(node.getId(), node.getXCoord(), node.getYCoord(), node.getBuilding(), node.getLongName(), node.getShortName(), node.getType(), node.getFloor());
    }

    public void updateDatabase() throws Exception {
        databaseManager.manipulateNode(tempNode);
        node = tempNode;
        tempNode = null;
    }

    public void modifyNode(String shortName, String longName, String xCoord, String yCoord, String building, String floor, String type) throws Exception {
        databaseManager.deleteNode(node.getId());
        this.tempNode = createNode(shortName, longName, xCoord, yCoord, building, floor, type);
        databaseManager.manipulateNode(tempNode);

    }

    private Node createNode(String shortName, String longName, String nodeXCoord, String nodeYCoord, String building, String floor, String nodeType) throws Exception {
        short xCoord = Short.parseShort(nodeXCoord);
        short yCoord = Short.parseShort(nodeYCoord);
        Node.NodeType type = Node.NodeType.getEnum(nodeType);
        String id = generateNodeID(nodeType, floor);
        Node node = new Node(id, xCoord, yCoord, building, longName, shortName, type, floor);
        databaseManager.manipulateNode(node);
        return node;
    }

    private String generateNodeID(String nodeType, String floor){
        String id = "";
        return id;
    }


}

