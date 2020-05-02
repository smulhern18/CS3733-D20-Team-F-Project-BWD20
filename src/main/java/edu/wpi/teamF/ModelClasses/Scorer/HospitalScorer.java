package edu.wpi.teamF.ModelClasses.Scorer;

import edu.wpi.teamF.ModelClasses.Edge;
import edu.wpi.teamF.ModelClasses.Node;
import java.util.*;

public class HospitalScorer implements Scorer {

  private static final double HOSPITAL_COST = 10000;

  private final Set<Node> nodes;
  private final EuclideanScorer euclideanScorer = new EuclideanScorer();
  private final Map<String, Node> nodeMap;
  private TypeScorer typeScorer;

  private boolean sameHospital(String building1, String building2) {
    return ("Faulkner".equals(building1) && "Faulkner".equals(building2))
        || (!"Faulkner".equals(building1) && !"Faulkner".equals(building2));
  }

  public HospitalScorer(Map<String, Node> nodeMap, String liftType) {
    typeScorer = new TypeScorer(nodeMap, liftType);

    this.nodeMap = nodeMap;

    this.nodes = new HashSet<>();
    for (Node node : nodeMap.values()) {
      if (sameHospital(node.getBuilding(), "Faulkner")) {
        Set<Edge> edges = node.getEdges();
        for (Edge edge : edges) {
          if (!sameHospital("Faulkner", nodeMap.get(edge.getNode1()).getBuilding())
              || !sameHospital("Faulkner", nodeMap.get(edge.getNode1()).getBuilding())) {
            this.nodes.add(node);
          }
        }
      } else {
        Set<Edge> edges = node.getEdges();
        for (Edge edge : edges) {
          if (sameHospital("Faulkner", nodeMap.get(edge.getNode1()).getBuilding())
              || sameHospital("Faulkner", nodeMap.get(edge.getNode1()).getBuilding())) {
            this.nodes.add(node);
          }
        }
      }
    }
  }

  @Override
  public double computeCost(Node from, Node to) {
    if (!sameHospital(from.getBuilding(), to.getBuilding())) {
      List<Node> exitNodes = new ArrayList<>();
      for (Node node : nodes) {
        if (sameHospital(from.getBuilding(), node.getBuilding())) {
          exitNodes.add(node);
        }
      }
      double bestCost = Double.MAX_VALUE;
      for (Node startNode : exitNodes) {
        for (Edge edge : startNode.getEdges()) {
          Node endNode = nodeMap.get(edge.getNode1());
          if (startNode.getId().equals(edge.getNode1())) {
            endNode = nodeMap.get(edge.getNode2());
          }
          if (!sameHospital(startNode.getBuilding(), endNode.getBuilding())) {
            double cost =
                typeScorer.computeCost(from, startNode) + typeScorer.computeCost(endNode, to);
            if (cost < bestCost) {
              bestCost = cost;
            }
          }
        }
      }
      return bestCost + HOSPITAL_COST;
    } else if (!from.getFloor().equals(to.getFloor())) {
      return typeScorer.computeCost(from, to);
    } else {
      return euclideanScorer.computeCost(from, to);
    }
  }
}
