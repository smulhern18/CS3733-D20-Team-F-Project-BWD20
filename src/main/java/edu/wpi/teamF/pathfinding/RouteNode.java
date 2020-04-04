package edu.wpi.teamF.pathfinding;

public class RouteNode {

    private final GraphNode current;
    private final GraphNode previous;
    private final double routeScore;
    private final double estimatedScore;
    public RouteNode(GraphNode current, GraphNode previous, double routeScore, double estimatedScore) {

        this.current = current;
        this.previous = previous;

        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    public GraphNode getCurrent() {
        return current;
    }

    public GraphNode getPrevious() {
        return previous;
    }

    public double getRouteScore() {
        return routeScore;
    }

    public double getEstimatedScore() {
        return estimatedScore;
    }
}
