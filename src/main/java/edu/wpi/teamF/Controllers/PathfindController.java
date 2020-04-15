package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.Factories.NodeFactory;
import edu.wpi.teamF.ModelClasses.ElevatorScorer;
import edu.wpi.teamF.ModelClasses.ElevatorScorer2;
import edu.wpi.teamF.ModelClasses.EuclideanScorer;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.RouteNode;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.paint.Color;

public class PathfindController extends SceneController {

  public static int MAP_HEIGHT = 1485;
  public static int MAP_WIDTH = 2475;

  public Canvas canvasMap;
  public MenuButton destinationPicker;
  private NodeFactory nodeFactory;
  private Node startNode;
  private Node destination;

  public PathfindController() {

    this.nodeFactory = NodeFactory.getFactory();
  }

  //  public PathfindController(NodeFactory nodeFactory) {
  //    this.nodeFactory = nodeFactory;
  //  }

  public List<Node> getPath(Node startNode, Node endNode) {
    // Check if the destination is on a different floor
    if (startNode.getFloor() != endNode.getFloor()) {
      // create list of elevators to navigate
      List<Node> elevatorList = nodeFactory.getNodesByType(Node.NodeType.ELEV);
      // If it is, navigate to the most practical elevator instead
      ElevatorScorer2 elevScorer =
          new ElevatorScorer2(elevatorList); // Need to find out how we pass it a list of elevators?
      endNode = elevScorer.elevatorScorer(startNode, endNode);
    }
    PriorityQueue<RouteNode> priorityQueue = new PriorityQueue<RouteNode>();
    HashSet<Node> visited = new HashSet<Node>();
    EuclideanScorer scorer = new EuclideanScorer();
    // Create the first node and add it to the Priority Queue
    RouteNode start = new RouteNode(startNode, null, 0, scorer.computeCost(startNode, endNode));
    priorityQueue.add(start);
    while (!priorityQueue.isEmpty()) {
      RouteNode currentNode = priorityQueue.poll();
      if (!visited.contains(currentNode.getNode())) {
        visited.add(currentNode.getNode());
        if (currentNode.getNode().equals(endNode)) {
          // Has reached the goal node
          List<Node> path = new LinkedList<>();
          do {
            path.add(0, currentNode.getNode());
            currentNode = currentNode.getPrevious();
          } while (currentNode != null);
          return path;
        }
        // Make a list of all of the neighbors of this node
        Set<Node> neighbors = new HashSet<>();
        for (String neighborNode : currentNode.getNode().getNeighbors()) {
          try {
            neighbors.add(nodeFactory.read(neighborNode));
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
        for (Node neighbor : neighbors) {
          if (!visited.contains(neighbor)) {
            double distanceToEnd =
                scorer.computeCost(neighbor, endNode); // Estimated distance to end
            double distanceFromStart =
                currentNode.getRouteScore()
                    + scorer.computeCost(currentNode.getNode(), neighbor); // Actual path distance
            double estimatedCostOfNeighbor = distanceToEnd + distanceFromStart;
            RouteNode neighborOnRoute =
                new RouteNode(neighbor, currentNode, distanceFromStart, estimatedCostOfNeighbor);
            priorityQueue.add(neighborOnRoute);
          }
        }
      }
    }
    // If it exits the while loop without returning a path
    System.out.println("No Route Found");
    return new ArrayList<Node>();
  }

  public List<Node> getPath2(Node startNode, Node endNode) {
    // Check if the destination is on a different floor
    PriorityQueue<RouteNode> priorityQueue = new PriorityQueue<RouteNode>();
    HashSet<Node> visited = new HashSet<Node>();
    EuclideanScorer scorer = new EuclideanScorer();
    ElevatorScorer elevScorer = new ElevatorScorer(nodeFactory.getNodesByType(Node.NodeType.ELEV));
    // Create the first node and add it to the Priority Queue
    RouteNode start;
    if (startNode.getFloor() != endNode.getFloor()) {
      // If it is, navigate to the most practical elevator instead
      start = new RouteNode(startNode, null, 0, elevScorer.computeCost(startNode, endNode));
    } else {
      start = new RouteNode(startNode, null, 0, scorer.computeCost(startNode, endNode));
    }
    priorityQueue.add(start);
    while (!priorityQueue.isEmpty()) {
      RouteNode currentNode = priorityQueue.poll();
      if (!visited.contains(currentNode.getNode())) {
        visited.add(currentNode.getNode());
        if (currentNode.getNode().equals(endNode)) {
          // Has reached the goal node
          List<Node> path = new LinkedList<>();
          do {
            path.add(0, currentNode.getNode());
            currentNode = currentNode.getPrevious();
          } while (currentNode != null);
          return path;
        }
        // Make a list of all of the neighbors of this node
        Set<Node> neighbors = new HashSet<>();
        for (String neighborNode : currentNode.getNode().getNeighbors()) {
          try {
            neighbors.add(nodeFactory.read(neighborNode));
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
        for (Node neighbor : neighbors) {
          if (!visited.contains(neighbor)) {
            double distanceToEnd = 0;
            if (currentNode.getNode().getFloor() != endNode.getFloor()) {
              // If its not on the same floor, use elevator scorer
              distanceToEnd = elevScorer.computeCost(currentNode.getNode(), endNode);
            } else {
              distanceToEnd = scorer.computeCost(startNode, endNode);
            }
            double distanceFromStart =
                currentNode.getRouteScore()
                    + scorer.computeCost(currentNode.getNode(), neighbor); // Actual path distance
            double estimatedCostOfNeighbor = distanceToEnd + distanceFromStart;
            RouteNode neighborOnRoute =
                new RouteNode(neighbor, currentNode, distanceFromStart, estimatedCostOfNeighbor);
            priorityQueue.add(neighborOnRoute);
          }
        }
      }
    }
    // If it exits the while loop without returning a path
    System.out.println("No Route Found");
    return new ArrayList<Node>();
  }

  public void printPath(List<Node> path) {
    for (int i = 0; i < path.size(); i++) {
      System.out.println((i + 1) + ". " + path.get(i).getName());
    }
  }

  // Canvas Testing
  public void drawCanvas(ActionEvent actionEvent) {
    GraphicsContext gc = canvasMap.getGraphicsContext2D();
    if (destination != null) {
      gc.clearRect(0, 0, canvasMap.getWidth(), canvasMap.getHeight());
      List<Node> path = getPath(startNode, destination);
      drawPath(gc, path);
    } else {
      gc.strokeText("NO DESTINATION", 100, 100);
    }
  }

  private void drawLines(GraphicsContext gc) {
    double heightRatio = canvasMap.getHeight() / MAP_HEIGHT;
    double widthRatio = canvasMap.getWidth() / MAP_WIDTH;
    gc.setStroke(Color.RED);
    gc.beginPath();

    gc.moveTo(675 * widthRatio, 1215 * widthRatio);
    gc.lineTo(685 * widthRatio, 1215 * widthRatio);
    gc.lineTo(785 * widthRatio, 1215 * widthRatio);
    gc.lineTo(930 * widthRatio, 1215 * widthRatio);
    gc.lineTo(1120 * widthRatio, 1210 * widthRatio);
    gc.lineTo(1245 * widthRatio, 1210 * widthRatio);
    gc.lineTo(1250 * widthRatio, 1120 * widthRatio);
    gc.lineTo(1250 * widthRatio, 1065 * widthRatio);
    gc.lineTo(1260 * widthRatio, 1060 * widthRatio);
    gc.stroke();
  }

  /**
   * Draw the path on the canvas
   *
   * @param path
   */
  private void drawPath(GraphicsContext gc, List<Node> path) {
    double heightRatio = canvasMap.getHeight() / MAP_HEIGHT;
    double widthRatio = canvasMap.getWidth() / MAP_WIDTH;
    gc.setStroke(Color.RED);
    gc.beginPath();
    gc.moveTo(path.get(0).getXCoord() * widthRatio, path.get(0).getYCoord() * heightRatio);
    for (int i = 1; i < path.size(); i++) {
      gc.lineTo(path.get(i).getXCoord() * widthRatio, path.get(i).getYCoord() * heightRatio);
    }
    gc.stroke();
  }

  /**
   * retrieve the node from database based on destination name
   *
   * @param destinationName
   */
  public void setDestination(String destinationName) {
    try {
      destination = nodeFactory.read(destinationName);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void intensiveCareUnitButton(ActionEvent actionEvent) {
    setDestination("FDEPT00105");
    destinationPicker.setText("Intensive Care Unit");
  }

  public void hemoButton(ActionEvent actionEvent) {
    setDestination("FDEPT00205");
    destinationPicker.setText("Hemo / Dialysis");
  }

  public void resButton(ActionEvent actionEvent) {
    setDestination("FDEPT00405");
    destinationPicker.setText("Residents");
  }

  public void outButton(ActionEvent actionEvent) {
    setDestination("FDEPT00305");
    destinationPicker.setText("Outpatient Infusion Center");
  }

  public void oncButton(ActionEvent actionEvent) {
    setDestination("FDEPT00505");
    destinationPicker.setText("Oncology");
  }

  public void xrayButton(ActionEvent actionEvent) {
    setDestination("FDEPT00605");
    destinationPicker.setText("X-ray");
  }

  public void abu1Button(ActionEvent actionEvent) {
    setDestination("FDEPT00705");
    destinationPicker.setText("Ambulatory Care Room 1");
  }

  public void abu2Button(ActionEvent actionEvent) {
    setDestination("FDEPT00905");
    destinationPicker.setText("Ambulatory Care Room 2");
  }

  public void abu3Button(ActionEvent actionEvent) {
    setDestination("FDEPT01005");
    destinationPicker.setText("Ambulatory Care Room 3");
  }

  public void abu4Button(ActionEvent actionEvent) {
    setDestination("FDEPT01105");
    destinationPicker.setText("Ambulatory Care Room 4");
  }

  public void ambulButton(ActionEvent actionEvent) {
    setDestination("FDEPT01305");
    destinationPicker.setText("Ambulatory Clinic");
  }

  public void internalButton(ActionEvent actionEvent) {
    setDestination("FDEPT01405");
    destinationPicker.setText("Internal Medicine");
  }

  public void s5940Button(ActionEvent actionEvent) {
    setDestination("FSERV00305");
    destinationPicker.setText("Suite 5940");
  }

  public void s5945Button(ActionEvent actionEvent) {
    setDestination("FSERV00205");
    destinationPicker.setText("Suite 5945");
  }

  public void s51BUtton(ActionEvent actionEvent) {
    setDestination("FSERV00405");
    destinationPicker.setText("Suite 51");
  }

  public void entButton(ActionEvent actionEvent) {
    setDestination("FDEPT01605:");
    destinationPicker.setText("ENT / Eye Physical / Derm");
  }

  public void orthoButton(ActionEvent actionEvent) {
    setDestination("");
    destinationPicker.setText("Orthopedic");
  }

  public void s58Button(ActionEvent actionEvent) {
    setDestination("FSERV00505");
    destinationPicker.setText("Suite 58");
  }

  public void surgButton(ActionEvent actionEvent) {
    setDestination("FDEPT01805");
    destinationPicker.setText("BWH Surgical Specialties");
  }

  public void endoButton(ActionEvent actionEvent) {
    setDestination("FDEPT01505");
    destinationPicker.setText("Endocrinology / Diabetes Hem-One");
  }

  public void pcpButton(ActionEvent actionEvent) {
    setDestination("FDEPT00805");
    destinationPicker.setText("BWH Primary Care Physicians");
  }
}
