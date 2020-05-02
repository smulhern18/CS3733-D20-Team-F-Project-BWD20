package edu.wpi.teamF.ModelClasses.Directions;

public class TurnDirection extends Direction {
  enum Turn {
    LEFT,
    SLIGHT_LEFT,
    SLIGHT_RIGHT,
    RIGHT
  }

  private Turn turn;
  private int atIntersectionNumber;
  private String floor;

  public TurnDirection(float angle, int atIntersectionNumber, String floor) {
    if (angle > 45.0) {
      this.turn = Turn.LEFT;
    } else if (angle > 0.0) {
      this.turn = Turn.SLIGHT_LEFT;
    } else if (angle > -45.0) {
      this.turn = Turn.SLIGHT_RIGHT;
    } else {
      this.turn = Turn.RIGHT;
    }
    this.atIntersectionNumber = atIntersectionNumber;
    this.floor = floor;
  }

  public String getFloor() {
    return floor;
  }

  public String getDirectionText() {
    String returnString = "";
    if (this.turn == Turn.LEFT) {
      returnString = "Turn left";
    } else if (this.turn == Turn.SLIGHT_LEFT) {
      returnString = "Turn slightly left";
    } else if (this.turn == Turn.SLIGHT_RIGHT) {
      returnString = "Turn slightly right";
    } else {
      returnString = "Turn right";
    }
    // Making assumption that there are less than 100 intersections
    if (atIntersectionNumber == 0) {
      returnString += ".";
    } else if ((atIntersectionNumber % 10) == 1 && (atIntersectionNumber != 11)) {
      returnString =
          returnString
              + " at the "
              + Integer.toString(atIntersectionNumber)
              + "st intersecting hallway.";
    } else if ((atIntersectionNumber % 10) == 2 && (atIntersectionNumber != 12)) {
      returnString =
          returnString
              + " at the "
              + Integer.toString(atIntersectionNumber)
              + "nd intersecting hallway.";
    } else if ((atIntersectionNumber % 10) == 3 && (atIntersectionNumber != 13)) {
      returnString =
          returnString
              + " at the "
              + Integer.toString(atIntersectionNumber)
              + "rd intersecting hallway.";
    } else {
      returnString =
          returnString
              + " at the "
              + Integer.toString(atIntersectionNumber)
              + "th intersecting hallway.";
    }
    return returnString;
  }
}
