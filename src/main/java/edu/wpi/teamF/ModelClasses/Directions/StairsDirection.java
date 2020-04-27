package edu.wpi.teamF.ModelClasses.Directions;

public class StairsDirection extends Direction {
    private int startFloor;
    private int endFloor;

    public void StairsDirection(int startFloor, int endFloor){
        this.startFloor = startFloor;
        this.endFloor = endFloor;
    }

    @Override
    public String getDirectionText() {
        return ("Take the stairs from floor " + Integer.toString(startFloor) + " to floor " + Integer.toString(endFloor) + ".");
    }

    @Override
    public int getFloor() {
        return startFloor;
    }
}
