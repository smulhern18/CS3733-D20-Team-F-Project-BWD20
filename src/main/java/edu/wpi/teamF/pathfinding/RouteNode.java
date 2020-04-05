package edu.wpi.teamF.pathfinding;

public class RouteNode implements Comparable<RouteNode> {

    private  GraphNode current;
    private  RouteNode previous;
    private  double routeScore;
    private  double estimatedScore;

    public RouteNode (GraphNode current) {
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    public RouteNode(GraphNode current, RouteNode previous, double routeScore, double estimatedScore) {

        this.current = current;
        this.previous = previous;

        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    public GraphNode getCurrent() {
        return current;
    }

    public  void setCurrent(GraphNode current) {
        this.current = current;
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
