package edu.wpi.teamF.ModelClasses.Directions;

public class IndexDirection extends Direction {
  // A fake direction used for indexing multiple floor paths, this is inserted everytime a floor is
  // switched
  // The floor is the floor that just ended.
  private String floor;

  public IndexDirection(String floor) {
    this.floor = floor;
  }

  @Override
  public String getDirectionText() {
    return "";
  }

  @Override
  public String getFloor() {
    return floor;
  }
}
