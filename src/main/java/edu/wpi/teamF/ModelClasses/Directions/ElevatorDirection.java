package edu.wpi.teamF.ModelClasses.Directions;

public class ElevatorDirection extends Direction {
  private String startFloor;
  private String endFloor;

  public ElevatorDirection(String startFloor, String endFloor) {
    this.startFloor = startFloor;
    this.endFloor = endFloor;
  }

  @Override
  public String getDirectionText() {
    String returnString =
        ("Take the elevator from floor " + startFloor + " to floor " + endFloor + ".");
    return returnString;
  }

  @Override
  public String getFloor() {
    return startFloor;
  }

  public void setFloor(String floor) {
    startFloor = floor;
  }
}
