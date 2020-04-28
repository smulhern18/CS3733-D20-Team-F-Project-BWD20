package edu.wpi.teamF.ModelClasses.Directions;

public class StartDirection extends Direction {
  enum Turn {
    LEFT,
    STRAIGHT,
    RIGHT
  }

  private Turn turn;
  private int floor;

  public StartDirection(float angle, int floor) {
    if (angle > 20.0) {
      this.turn = Turn.LEFT;
    } else if (angle > -20.0) {
      this.turn = Turn.STRAIGHT;
    } else {
      this.turn = Turn.RIGHT;
    }
    this.floor = floor;
  }

  public int getFloor() {
    return floor;
  }

  public String getDirectionText() {
    if (this.turn == Turn.LEFT) {
      return "Turn left into the hallway.";
    } else if (this.turn == Turn.STRAIGHT) {
      return "Proceed straight into the hallway.";
    } else {
      return "Turn right into the hallway.";
    }
  }
}
