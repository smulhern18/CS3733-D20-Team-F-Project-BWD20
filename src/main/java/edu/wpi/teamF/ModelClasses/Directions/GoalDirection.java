package edu.wpi.teamF.ModelClasses.Directions;

public class GoalDirection extends Direction {
  enum Turn {
    LEFT,
    STRAIGHT,
    RIGHT
  }

  private Turn turn;
  private String floor;

  public GoalDirection(float angle, String floor) {
    if (angle > 20.0) {
      this.turn = Turn.LEFT;
    } else if (angle > -20.0) {
      this.turn = Turn.STRAIGHT;
    } else {
      this.turn = Turn.RIGHT;
    }
    this.floor = floor;
  }

  public String getFloor() {
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
