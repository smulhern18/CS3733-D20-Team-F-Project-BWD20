package edu.wpi.teamF.ModelClasses.Directions;

public class ElevatorDirection extends Direction {
  private String startFloor;
  private String endFloor;
  private String dirFloor;

  enum Turn {
    LEFT,
    STRAIGHT,
    RIGHT
  }

  private Turn turn;

  public ElevatorDirection(String startFloor, String endFloor, float exitAngle, String dirFloor) {
    this.startFloor = startFloor;
    this.endFloor = endFloor;
    this.dirFloor = dirFloor;
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
        ("Take the elevator from floor " + startFloor + " to floor " + endFloor + ".");
    if (this.turn == Turn.LEFT) {
      returnString += " Upon exiting, turn left.";
    } else if (this.turn == Turn.STRAIGHT) {
      returnString += " Upon exiting, proceed straight.";
    } else {
      returnString += " Upon exiting, turn right.";
    }
    return returnString;
  }

  @Override
  public String getFloor() {
    return dirFloor;
  }

  public void setFloor(String floor) {
    dirFloor = floor;
  }
}
