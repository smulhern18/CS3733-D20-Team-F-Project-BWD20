package edu.wpi.teamF.ModelClasses.Directions;

public class EnterDirection extends Direction {
    private String proceedTo;
    private String floor;

    public EnterDirection(String proceedTo, String floor){
        this.proceedTo = proceedTo;
        this.floor = floor;
    }

    @Override
    public String getDirectionText() {
        return ("Enter " + proceedTo + ".");
    }

    @Override
    public String getFloor() {
        return floor;
    }
}
