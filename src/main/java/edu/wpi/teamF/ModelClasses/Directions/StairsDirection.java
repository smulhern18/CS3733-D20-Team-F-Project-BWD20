package edu.wpi.teamF.ModelClasses.Directions;

public class StairsDirection extends Direction {
  private int startFloor;
  private int endFloor;

  enum Turn {
    LEFT,
    STRAIGHT,
    RIGHT
  }

  private Turn turn;

  public StairsDirection(int startFloor, int endFloor, float exitAngle) {
    this.startFloor = startFloor;
    this.endFloor = endFloor;
    if (exitAngle > 20.0) {
      this.turn = Turn.LEFT;
    } else if (exitAngle > -20.0) {
      this.turn = Turn.STRAIGHT;
    } else {
      this.turn = Turn.RIGHT;
    }
  }

  @Override
  public String getDirectionText() {
    String returnString =
        ("Take the stairs from floor "
            + Integer.toString(startFloor)
            + " to floor "
            + Integer.toString(endFloor)
            + ".");
    if (this.turn == Turn.LEFT) {
      returnString += " Upon exiting, turning left.";
    } else if (this.turn == Turn.STRAIGHT) {
      returnString += " Upon exiting, proceed straight.";
    } else {
      returnString += " Upon exiting, turning right.";
    }
    return returnString;
  }

  @Override
  public int getFloor() {
    return startFloor;
  }
}
