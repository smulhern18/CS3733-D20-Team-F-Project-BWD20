package edu.wpi.teamF.ModelClasses;

import lombok.Data;

@Data
public class RouteNode implements Comparable<RouteNode> {

  private Node node;
  private RouteNode previous;
  private double routeScore;
  private double estimatedScore;

  public RouteNode(Node node) {
    this(node, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
  }

  public RouteNode(Node node, RouteNode previous, double routeScore, double estimatedScore) {

    this.node = node;
    this.previous = previous;

    this.routeScore = routeScore;
    this.estimatedScore = estimatedScore;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public RouteNode getPrevious() {
    return previous;
  }

  public void setPrevious(RouteNode previous) {
    this.previous = previous;
  }

  public double getRouteScore() {
    return routeScore;
  }

  public void setRouteScore(double routeScore) {
    this.routeScore = routeScore;
  }

  public double getEstimatedScore() {
    return estimatedScore;
  }

  public void setEstimatedScore(double estimatedScore) {
    this.estimatedScore = estimatedScore;
  }

  @Override
  public int compareTo(RouteNode other) {
    if (this.estimatedScore > other.estimatedScore) {
      return 1;
    } else if (this.estimatedScore < other.estimatedScore) {
      return -1;
    } else {
      return 0;
    }
  }
}
