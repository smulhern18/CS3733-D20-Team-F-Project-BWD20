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
    public void setEdgeEventHandlers(Line line) {
        line.setOnMousePressed(mouseEvent -> {
            lineModifyEdgeHandler(line,mouseEvent);
        });
        
    }

    private void lineModifyEdgeHandler(Line line,MouseEvent mouseEvent) {
        state = State.MODIFY_EDGE;
        selectedEdge = edgeLineMap.get(line.getId());
        edgeSelection = EdgeSelection.NODE1;
        mapView.highlightEdge(line.getId());
    }

    public void setMapEventHandlers(AnchorPane floorPane) {
        floorPane.setOnMouseClicked(mouseEvent -> {
            if (state == State.ADD_NODE && !mouseEvent.isDragDetect()) {
                paneAddNodeHandler(mouseEvent);
            }
        });
    }

    private void paneAddNodeHandler(MouseEvent mouseEvent) {

    }


    public void setButtonEventHandlers(JFXButton button) {
        
        button.setOnMousePressed(mouseEvent -> {
            if (state == State.MODIFY_EDGE)  {
                buttonModifyEdgeHandler();
            } else if (state == State.ADD_EDGE) {
                buttonAddEdgeHandler();
            }
            else {
                modifyNodeHandler();
            }
        });

    }

    private void buttonAddEdgeHandler() {

    }

    private void buttonModifyEdgeHandler() {


    }

    private void modifyNodeHandler() {
    }


}
