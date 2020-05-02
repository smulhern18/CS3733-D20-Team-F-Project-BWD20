package edu.wpi.teamF.ModelClasses.Directions;

public class TravelDirection extends Direction {
    private String from;
    private String to;
    private String floor;

    public TravelDirection(String from, String to, String floor){
        this.from = from;
        this.to = to;
        this.floor = floor;
    }

    @Override
    public String getDirectionText() {
        return ("Travel from " + from + " to " + to + ".");
    }

    @Override
    public String getFloor() {
        return floor;
    }
}
