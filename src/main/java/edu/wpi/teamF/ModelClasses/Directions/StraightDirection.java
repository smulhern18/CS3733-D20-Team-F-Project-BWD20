package edu.wpi.teamF.ModelClasses.Directions;

public class StraightDirection extends Direction {
    private int distance;
    private int intersectionsPassed;
    private int floor;

    public StraightDirection(int distance, int intersectionsPassed, int floor){
        this.distance = distance;
        this.intersectionsPassed = intersectionsPassed;
        this.floor = floor;
    }

    public void addDistance(int extraDistance){
        this.distance += extraDistance;
    }

    public void addIntersection(){
        intersectionsPassed += 1;
    }

    public int getFloor() {
        return floor;
    }

    public String getDirectionText(){
        //Truncate to the nearest 10 ft
        int distance = this.distance / 10;
        distance = distance * 10;
        //Check if we pass any hallways
        if (intersectionsPassed == 0){
            return ("Walk for about " + Integer.toString(distance) + "ft.");
        }
        else if (intersectionsPassed == 1){
            return ("Walk for about " + Integer.toString(distance) + "ft, passing 1 intersecting hallway.");
        }
        else{
            return ("Walk for about " + Integer.toString(distance) + "ft, passing " + Integer.toString(intersectionsPassed) + " intersecting hallways.");
        }
    }
}
