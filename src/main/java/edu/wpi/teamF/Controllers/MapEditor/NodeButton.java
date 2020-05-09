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

    public NodeButton(Node node) throws ValidationException {
        this.node = node;
        this.tempNode = new Node(node.getId(), node.getXCoord(), node.getYCoord(), node.getBuilding(), node.getLongName(), node.getShortName(), node.getType(), node.getFloor())
    }

    public void updateDatabase() throws Exception {
        databaseManager.manipulateNode(tempNode);
        node = tempNode;
    }

    public void modifyNode(String shortName, String longName, String nodeXCoord, String nodeYCoord, String building, String floor, String nodeType) throws ValidationException {
        short xCoord = Short.parseShort(nodeXCoord);
        short yCoord = Short.parseShort(nodeYCoord);
        Node.NodeType type = Node.NodeType.getEnum(nodeType);
        //calculate the id
        tempNode.setXCoord(xCoord);
        tempNode.setYCoord(yCoord);
        tempNode.setBuilding(building);
        tempNode.setLongName(longName);
        tempNode.setShortName(shortName);
        tempNode.setType(type);
        tempNode.setFloor(floor);

    }


}

