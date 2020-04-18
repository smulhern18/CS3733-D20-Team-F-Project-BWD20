package edu.wpi.teamF.ModelClasses;

import lombok.Data;

@Data
public class RouteNode implements Comparable<RouteNode> {

  private Node node;
  private RouteNode previous;
  private double routeScore;
  private double estimatedScore;

  @Override
  public int compareTo(RouteNode o) {
    return 0;
  }
}
