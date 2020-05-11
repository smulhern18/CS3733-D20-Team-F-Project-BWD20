package edu.wpi.teamF.Controllers.MapEditor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class MapEditorController {
  private static final double FAULKNER_HEIGHT_RATIO = (585.0 / 1485);
  private static final double FAULKNER_WIDTH_RATIO =
      974.0 / 2475; // height and width of the faulkner hospital map
  private static final double MAIN_HEIGHT_RATIO = 585.0 / 3400;
  private static final double MAIN_WIDTH_RATIO =
      974.0 / 5000; // height and width of the main hospital map
  private static final int MAP_HEIGHT_FAULK = 1485;
  private static final int MAP_WIDTH_FAULK = 2475; // height and width of the faulkner hospital map
  private static final int MAP_HEIGHT_MAIN = 3400;
  private static final int MAP_WIDTH_MAIN = 5000;

  public void setModifyEdgeFromNodeButton(
      JFXButton modifyEdgeFromNodeButton, JFXComboBox<String> edgeCombo) {
    modifyEdgeFromNodeButton.setOnMousePressed(
        mouseEvent -> {
          edgeCombo.valueProperty().addListener((observable, oldValue, newValue) -> {});

          if (state == State.MODIFY_NODE) {
            try {
              modifyEdgeFromNodeHandler(edgeCombo);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }

  private void modifyEdgeFromNodeHandler(JFXComboBox<String> edgeCombo) throws Exception {
    if (!edgeCombo.getValue().isEmpty()) {
      System.out.println("Value not empty");
      cancelNodeHandler();
      state = State.MODIFY_EDGE;
      mapView.setAsEdgeView();
      selectedEdge = edgeLineMap.get(edgeCombo.getValue());
      edgeSelection = null;
      EdgeLine edgeLine = edgeLineMap.get(edgeCombo.getValue());
      NodeButton nodeButton1 = nodeButtonMap.get(edgeLine.getNode1());
      NodeButton nodeButton2 = nodeButtonMap.get(edgeLine.getNode2());
      if (nodeButton1.node.getFloor().equals(nodeButton2.node.getFloor())) {
        mapView.highlightUpdatedEdge(
            edgeCombo.getValue(), selectedEdge.getNode1(), selectedEdge.getNode2());
      }
    }
  }

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
  Map<String, NodeButton> nodeButtonMap = new HashMap<>();
  Map<String, EdgeLine> edgeLineMap = new HashMap<>();
  State state = null;

  // Edge variables
  EdgeSelection edgeSelection;
  EdgeLine selectedEdge;
  String addNode1ID;

  // Node variables
  NodeButton selectedNode;
  double deltaX;
  double deltaY;
  boolean isNewNode = false;

  public MapEditorController(MapView mapView) throws Exception {
    this.mapView = mapView;
    state = null;
    edgeSelection = null;
    for (Node node : databaseManager.getAllNodes()) {
      nodeButtonMap.put(node.getId(), new NodeButton(node));
    }
    for (Edge edge : databaseManager.getAllEdges()) {
      edgeLineMap.put(edge.getId(), new EdgeLine(edge));
    }
  }

  // Edge state & event handlers
  public void setEdgeEventHandlers(Line line) {
    line.setOnMousePressed(
        mouseEvent -> {
          try {
            if (state == State.ADD_EDGE || state == State.MODIFY_EDGE) {
              cancelEdgeHandler();
            }
            lineModifyEdgeHandler(line, mouseEvent);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  public void addEdgeButtonHandler(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          state = State.ADD_EDGE;
          mapView.setAsCancelView();
          edgeSelection = EdgeSelection.NODE1;
        });
  }

  public void edgeModifyButtonHandler(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          try {
            mapView.removeEdge(selectedEdge.getEdgeID());
            mapView.unHighlightButton(selectedEdge.getTempNode1());
            mapView.unHighlightButton(selectedEdge.getTempNode2());
            edgeLineMap.remove(selectedEdge.getEdgeID());
            selectedEdge.updateDatabase();
            edgeLineMap.put(selectedEdge.getEdgeID(), selectedEdge);
            mapView.drawEdge(selectedEdge.getEdge());
            resetEdgeStateToDefault();
            mapView.setAsDefaultView();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  public void edgeDeleteButtonHandler(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          try {
            mapView.removeEdge(selectedEdge.getEdgeID());
            mapView.unHighlightButton(selectedEdge.getTempNode1());
            mapView.unHighlightButton(selectedEdge.getTempNode2());
            selectedEdge.deleteEdgeFromDatabase();
            edgeLineMap.remove(selectedEdge.getEdgeID());
            resetEdgeStateToDefault();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  private void resetEdgeStateToDefault() {
    if (addNode1ID != null) {
      mapView.unHighlightButton(addNode1ID);
    }
    mapView.setAsDefaultView();
    edgeSelection = null;
    addNode1ID = null;
    selectedEdge = null;
    state = null;
  }

  public void setEdgeSelectionButtonHandler(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          if ("Node1".equals(button.getId())) {
            edgeSelection = EdgeSelection.NODE1;
          } else if ("Node2".equals(button.getId())) {
            edgeSelection = EdgeSelection.NODE2;
          }
        });
  }

  private void lineModifyEdgeHandler(Line line, MouseEvent mouseEvent) throws Exception {
    state = State.MODIFY_EDGE;
    mapView.setAsEdgeView();
    selectedEdge = edgeLineMap.get(line.getId());
    edgeSelection = null;
    mapView.highlightUpdatedEdge(line.getId(), selectedEdge.getNode1(), selectedEdge.getNode2());
  }

  private void nodeAddEdgeHandler(JFXButton button, MouseEvent mouseEvent) throws Exception {
    if (edgeSelection == EdgeSelection.NODE1) {
      edgeSelection = EdgeSelection.NODE2;
      addNode1ID = button.getId();
      mapView.setButtonColor(button, "#012D5A", 1);
    } else if (edgeSelection == EdgeSelection.NODE2) {

      Edge newEdge = new Edge(addNode1ID + "_" + button.getId(), addNode1ID, button.getId());
      Line line = mapView.drawEdge(newEdge);
      selectedEdge = new EdgeLine(newEdge);
      edgeLineMap.put(newEdge.getId(), selectedEdge);
      lineModifyEdgeHandler(line, mouseEvent);
    }
  }

  private void nodeModifyEdgeHandler(JFXButton button, MouseEvent mouseEvent) throws Exception {
    if (edgeSelection == EdgeSelection.NODE1) {
      mapView.unHighlightButton(selectedEdge.getTempNode1());
      selectedEdge.setNode1(button.getId());
    }
    if (edgeSelection == EdgeSelection.NODE2) {
      mapView.unHighlightButton(selectedEdge.getTempNode2());
      selectedEdge.setNode2(button.getId());
    }
    mapView.highlightUpdatedEdge(
        selectedEdge.getEdgeID(), selectedEdge.getTempNode1(), selectedEdge.getTempNode2());
  }

  private void cancelEdgeHandler() throws Exception {
    if (state == State.ADD_EDGE) {
      resetEdgeStateToDefault();
    } else if (state == State.MODIFY_EDGE) {
      mapView.unHighlightButton(selectedEdge.getTempNode1());
      mapView.unHighlightButton(selectedEdge.getTempNode2());
      selectedEdge.reset();
      if (addNode1ID != null) {
        mapView.removeEdge(selectedEdge.getEdgeID());
      } else {
        mapView.redrawEdge(selectedEdge.edge);
      }
      resetEdgeStateToDefault();
    }
  }

  public void setCancelButtonHandler(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          try {
            cancelEdgeHandler();
            cancelNodeHandler();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  private void cancelNodeHandler() throws Exception {
    if (state == State.ADD_NODE) {

    } else if (state == State.MODIFY_NODE) {
      selectedNode.reset();
      if (isNewNode) {
        mapView.removeNode(selectedNode.getID());
      } else {
        mapView.redrawNode(selectedNode.node);
        for (Edge edge : databaseManager.getAllEdgesConnectedToNode(selectedNode.getID())) {
          mapView.redrawEdge(edge);
        }
      }
    }
    resetNodeStateToDefault();
  }

  public void setMapEventHandlers(AnchorPane floorPane) {
    floorPane.setOnMouseClicked(
        mouseEvent -> {
          if (state == State.ADD_NODE && !mouseEvent.isDragDetect()) {}
        });
  }

  public void setNodeEventHandlers(JFXButton button, JFXComboBox<String> edgeCombo) {

    button.setOnMousePressed(
        mouseEvent -> {
          try {
            if (state == State.MODIFY_EDGE && edgeSelection != null) {
              nodeModifyEdgeHandler(button, mouseEvent);
            } else if (state == State.ADD_EDGE) {
              nodeAddEdgeHandler(button, mouseEvent);
            } else {
              nodeModifyNodeHandler(button, mouseEvent, edgeCombo);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
    button.setOnMouseDragged(
        mouseEvent -> {
          try {
            double widthRatio = MAIN_WIDTH_RATIO;
            double heightRatio = MAIN_HEIGHT_RATIO;
            double maxWidth = MAP_WIDTH_MAIN;
            double maxHeight = MAP_HEIGHT_MAIN;
            if (state == State.MODIFY_NODE) {
              if ("Faulkner".equals(selectedNode.getTempBuilding())) {
                widthRatio = FAULKNER_WIDTH_RATIO;
                heightRatio = FAULKNER_HEIGHT_RATIO;
                maxWidth = MAP_WIDTH_FAULK;
                maxHeight = MAP_HEIGHT_FAULK;
              }
              double newX =
                  (mouseEvent.getSceneX() / mapView.getMapScaleX() + deltaX + 4) / widthRatio;
              double newY =
                  (mouseEvent.getSceneY() / mapView.getMapScaleY() + deltaY + 4) / heightRatio;
              if (newX > maxWidth) {
                newX = maxWidth;
              } else if (newX < 0) {
                newX = 0;
              }
              if (newY > maxHeight) {
                newY = maxHeight;
              } else if (newY < 0) {
                newY = 0;
              }

              selectedNode.setTempX(newX);
              selectedNode.setTempY(newY);

              mapView.highlightUpdatedNode(
                  selectedNode.getID(),
                  selectedNode.getTempX(),
                  selectedNode.getTempY(),
                  selectedNode.getTempFloor(),
                  selectedNode.getTempBuilding());
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  private void nodeModifyNodeHandler(
      JFXButton button, MouseEvent mouseEvent, JFXComboBox<String> edgeCombo) throws Exception {
    if (state == State.MODIFY_EDGE || state == State.ADD_EDGE) {
      cancelEdgeHandler();
    }
    if (state == State.MODIFY_NODE
        && selectedNode != null
        && button.getId().equals(selectedNode.getID())) {
      deltaX = button.getLayoutX() - mouseEvent.getSceneX() / mapView.getMapScaleX();
      deltaY = button.getLayoutY() - mouseEvent.getSceneY() / mapView.getMapScaleY();
    } else {
      cancelNodeHandler();
      state = State.MODIFY_NODE;
      mapView.setAsModifyNodeView();
      deltaX = button.getLayoutX() - mouseEvent.getSceneX() / mapView.getMapScaleX();
      deltaY = button.getLayoutY() - mouseEvent.getSceneY() / mapView.getMapScaleY();
      selectedNode = nodeButtonMap.get(button.getId());
      mapView.displayInitialNodeData(selectedNode.getNode());
      mapView.highlightUpdatedNode(
          selectedNode.getID(),
          selectedNode.getTempX(),
          selectedNode.getTempY(),
          selectedNode.getTempFloor(),
          selectedNode.getTempBuilding());
    }

    edgeCombo.getItems().clear();

    for (Edge edge : databaseManager.getAllEdgesConnectedToNode(selectedNode.getID())) {
      edgeCombo.getItems().add(edge.getId());
    }
  }

  public void setFloorInputHandler(JFXComboBox<String> floorInput) {
    floorInput.setOnAction(
        actionEvent -> {
          try {
            selectedNode.setTempFloor(floorInput.getValue());
            // System.out.println("New Floor: " + floorInput.getValue());
            mapView.highlightUpdatedNode(
                selectedNode.getID(),
                selectedNode.getTempX(),
                selectedNode.getTempY(),
                selectedNode.getTempFloor(),
                selectedNode.getTempBuilding());
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  public void setAddNodeButtonHandler(JFXButton button, JFXComboBox<String> edgeCombo) {
    button.setOnMousePressed(
        mouseEvent -> {
          try {
            isNewNode = true;
            double newX = 1000;
            double newY = 1000;
            selectedNode = new NodeButton(newX, newY, mapView.getCurrentFloor());
            JFXButton newButton = mapView.drawNode(selectedNode.getNode());
            nodeButtonMap.put(selectedNode.getID(), selectedNode);
            nodeModifyNodeHandler(newButton, mouseEvent, edgeCombo);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  public void setModifyNodeButtonHandler(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          NodeButton nodeButton = nodeButtonMap.get(selectedNode);
          try {
            mapView.removeNode(selectedNode.getID());
            nodeButtonMap.remove(selectedNode.getID());
            selectedNode.updateDatabase(
                mapView.getShortName(),
                mapView.getLongName(),
                mapView.getXCoord(),
                mapView.getYCoord(),
                mapView.getBuilding(),
                mapView.getFloor(),
                mapView.getType());
            nodeButtonMap.put(selectedNode.getID(), selectedNode);
            mapView.drawNode(selectedNode.getNode());
            for (Edge edge : databaseManager.getAllEdgesConnectedToNode(selectedNode.getID())) {
              mapView.redrawEdge(edge);
            }
            resetNodeStateToDefault();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  private void resetNodeStateToDefault() {
    state = null;
    selectedNode = null;
    deltaX = 0;
    deltaY = 0;
    isNewNode = false;
    mapView.setAsDefaultView();
  }

  public void setDeleteNodeButtonHandler(JFXButton button) {
    button.setOnMousePressed(
        mouseEvent -> {
          try {
            mapView.removeNode(selectedNode.getID());
            if (!isNewNode) {
              for (Edge edge : databaseManager.getAllEdgesConnectedToNode(selectedNode.getID())) {
                mapView.removeEdge(edge.getId());
              }
              selectedNode.deleteFromDatabase();
            }
            nodeButtonMap.remove(selectedNode.getID());
            resetNodeStateToDefault();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }
}
