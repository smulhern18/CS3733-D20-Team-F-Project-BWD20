package edu.wpi.teamF.ModelClasses.Directions;

public class GoalDirection extends Direction{
    enum Turn {
        LEFT,
        STRAIGHT,
        RIGHT
    }
    private Turn turn;
    private int floor;

    public GoalDirection(float angle, int floor){
        if (angle < -45.0){this.turn = Turn.LEFT;}
        else if (angle < 45.0){this.turn = Turn.STRAIGHT;}
        else {this.turn = Turn.RIGHT;}
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public String getDirectionText() {
        if (this.turn == Turn.LEFT) {
            return "Your destination will be on the left.";
        } else if (this.turn == Turn.STRAIGHT) {
            return "Your destination will be straight ahead.";
        } else {
            return "Your destination will be on the right.";
        }
    }
}
