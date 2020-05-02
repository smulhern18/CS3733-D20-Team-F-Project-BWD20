package edu.wpi.teamF.ModelClasses.Directions;

public class ExitDirection extends Direction {
    private String exitFrom;
    private String floor;

    public ExitDirection(String exitFrom, String floor){
        this.exitFrom = exitFrom;
        this.floor = floor;
    }

    @Override
    public String getDirectionText() {
        return ("Exit " + exitFrom + ".");
    }

    @Override
    public String getFloor() {
        return floor;
    }
}
