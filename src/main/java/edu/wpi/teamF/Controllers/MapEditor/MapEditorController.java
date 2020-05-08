package edu.wpi.teamF.Controllers.MapEditor;

import com.jfoenix.controls.JFXButton;
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

    MapView mapView;
    Map<String,NodeButton> nodeButtonMap;
    Map<String,EdgeLine> edgeLineMap;
    State state;

    //Edge variables
    EdgeSelection edgeSelection;
    EdgeLine selectedEdge;

    //Node variables


    public MapEditorController(MapView mapView) {
        this.mapView = mapView;
        state = State.MODIFY_NODE;
        edgeSelection = EdgeSelection.NODE1;
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
