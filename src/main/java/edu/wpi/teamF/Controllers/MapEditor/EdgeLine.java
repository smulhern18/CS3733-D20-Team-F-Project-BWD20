package edu.wpi.teamF.Controllers.MapEditor;

import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.ValidationException;
import lombok.Data;


@Data
public class EdgeLine {

    private DatabaseManager databaseManager = DatabaseManager.getManager();

    Edge edge;
    String tempNode1;
    String tempNode2;



    public EdgeLine(Edge edge) {
        this.edge = edge;
        tempNode1 = edge.getNode1();
        tempNode2 = edge.getNode2();

    }

    public void setNode1(String node1ID) {
        tempNode1 = node1ID;
    }

    public void setNode2(String node2ID) {
        tempNode2 = node2ID;
    }

    public String getNode1(){
        return edge.getNode1();
    }

    public String getNode2(){
        return edge.getNode2();
    }

    public String getEdgeID() {
        return this.edge.getId();
    }

    public void reset() {
        tempNode1 = edge.getNode1();
        tempNode2 = edge.getNode2();
    }

    public void updateDatabase() throws Exception {

        Edge updatedEdge = new Edge(tempNode1 + "_" + tempNode2,tempNode1,tempNode2);
        databaseManager.deleteEdge(edge.getId());
        databaseManager.manipulateEdge(updatedEdge);
    }

    public void deleteEdgeDatabase() throws Exception {
        databaseManager.deleteEdge(edge.getId());
    }
}
