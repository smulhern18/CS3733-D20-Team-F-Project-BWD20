package edu.wpi.teamF.ModelClasses.Directions;

import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.Path;

import java.util.ArrayList;
import java.util.List;

public class Directions {
    private List<Direction> directionList = new ArrayList<>();

    public Directions(Path path, Node startNode, Node endNode){
        List<Node> nodeList = path.getPath();
        directionList.add(new StartDirection(getAngle(nodeList.get(0), nodeList.get(1), nodeList.get(2)), nodeList.get(0).getFloor()));
        for (int i = 1; i < nodeList.size(); i++){

        }
    }

    private float getAngle (Node previous, Node current, Node next){
        float angleA = (float) Math.atan2((current.getYCoord() - previous.getYCoord()), (current.getXCoord() - previous.getYCoord()));
        angleA = (float) Math.toDegrees(angleA);
        float angleB = (float) Math.atan2((next.getYCoord() - current.getYCoord()), (next.getXCoord() - current.getYCoord()));
        angleB = (float) Math.toDegrees(angleB);
        return angleA - angleB;
    }

    public List<Direction> getDirectionList(){
        return directionList;
    }

    public String getFullDirectionsString(){
        String returnString = "";
        for (Direction direction : directionList){
            returnString = returnString + direction.getDirectionText() + "%n";
        }
        return returnString;
    }
}
