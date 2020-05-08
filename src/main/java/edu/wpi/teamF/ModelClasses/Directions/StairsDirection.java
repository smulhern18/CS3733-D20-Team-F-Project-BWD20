package edu.wpi.teamF.ModelClasses.Directions;

public class StairsDirection extends Direction {
  private String startFloor;
  private String endFloor;

  public StairsDirection(String startFloor, String endFloor) {
    this.startFloor = startFloor;
    this.endFloor = endFloor;
  }

  @Override
  public String getDirectionText() {
    String returnString =
        ("Take the stairs from floor "
            + startFloor.replace("F", "")
            + " to floor "
            + endFloor.replace("F", "")
            + ".");
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
