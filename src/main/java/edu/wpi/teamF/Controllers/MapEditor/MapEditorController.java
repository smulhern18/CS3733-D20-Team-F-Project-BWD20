package edu.wpi.teamF.Controllers.MapEditor;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.Map;

public class MapEditorController {

    private enum State {
        
        ADD_NODE,
        MODIFY_NODE,
        MODIFY_EDGE,
        ADD_EDGE
    }

    private enum EdgeSelection {
        NODE1,
        NODE2
    }

    private DatabaseManager databaseManager = DatabaseManager.getManager();
    MapView mapView;
    Map<String,NodeButton> nodeButtonMap;
    Map<String,EdgeLine> edgeLineMap;
    State state;

    //Edge variables
    EdgeSelection edgeSelection;
    EdgeLine selectedEdge;

    //Node variables


    public MapEditorController(MapView mapView) throws Exception {
        this.mapView = mapView;
        state = State.MODIFY_NODE;
        edgeSelection = EdgeSelection.NODE1;
        for (Node node: databaseManager.getAllNodes()) {
            nodeButtonMap.put(node.getId(),new NodeButton(node));
        }
        for (Edge edge: databaseManager.getAllEdges()) {
            edgeLineMap.put(edge.getId(),new EdgeLine(edge));
        }
    }

    public void setEdgeSelectionButtonHandler(JFXButton button) {
        button.setOnMousePressed(mouseEvent -> {
            if ("Node1".equals(button.getId())) {
                edgeSelection = EdgeSelection.NODE1;
            } else if ("Node2".equals(button.getId())) {
                edgeSelection = EdgeSelection.NODE2;
            }
        });
    }

    public void setMapEventHandlers(AnchorPane floorPane) {
        floorPane.setOnMouseClicked(mouseEvent -> {
            if (state == State.ADD_NODE && !mouseEvent.isDragDetect()) {
                paneAddNodeHandler(mouseEvent);
            }
        });
    }

    public void setEdgeEventHandlers(Line line){
        line.setOnMousePressed(mouseEvent -> {
            try {
                lineModifyEdgeHandler(line,mouseEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        
    }

    public void setNodeEventHandlers(JFXButton button) {

        button.setOnMousePressed(mouseEvent -> {
            if (state == State.MODIFY_EDGE) {
                try {
                    nodeModifyEdgeHandler(button, mouseEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (state == State.ADD_EDGE) {
                nodeAddEdgeHandler();
            } else if (state == State.ADD_NODE) {
                nodeAddNodeHandler();
            } else if (state == State.MODIFY_NODE) {
                nodeModifyNodeHandler();
            } else {
                modifyNodeHandler();
            }
        });

    }

    private void nodeModifyNodeHandler() {

    }

    private void nodeAddNodeHandler() {
    }

    private void lineModifyEdgeHandler(Line line,MouseEvent mouseEvent) throws Exception {
        state = State.MODIFY_EDGE;
        selectedEdge = edgeLineMap.get(line.getId());
        edgeSelection = null;
        mapView.highlightEdge(line.getId(), selectedEdge.getNode1(), selectedEdge.getNode2());
    }





    private void paneAddNodeHandler(MouseEvent mouseEvent) {

    }




    private void nodeAddEdgeHandler() {

    }

    private void nodeModifyEdgeHandler(JFXButton button,MouseEvent mouseEvent) throws Exception {
        if (edgeSelection == EdgeSelection.NODE1) {
            selectedEdge.setNode1(button.getId());
        }
        if (edgeSelection == EdgeSelection.NODE2) {
            selectedEdge.setNode2(button.getId());
        }
        mapView.highlightEdge(selectedEdge.getEdgeID(), selectedEdge.getTempNode1(), selectedEdge.getTempNode2());
    }

    private void modifyNodeHandler() {
    }


}
