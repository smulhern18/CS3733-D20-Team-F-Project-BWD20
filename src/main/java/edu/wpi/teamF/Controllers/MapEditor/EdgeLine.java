package edu.wpi.teamF.Controllers.MapEditor;

import edu.wpi.teamF.ModelClasses.Edge;

public class EdgeLine {
    Edge edge;
    String tempNode1;
    String tempNode2;



    public EdgeLine(Edge edge) {
        this.edge = edge;

    }

    public void setNode1(String node1ID) {
        tempNode1 = node1ID;
    }

    public void setNode2(String node2ID) {
        tempNode2 = node2ID;
    }
}
