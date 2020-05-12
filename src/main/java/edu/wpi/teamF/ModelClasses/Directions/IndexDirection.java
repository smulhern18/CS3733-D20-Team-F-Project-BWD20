package edu.wpi.teamF.ModelClasses.Directions;

public class IndexDirection extends Direction {
  // A fake direction used for indexing multiple floor paths, this is inserted everytime a floor is
  // switched
  // The floor is the floor that just ended.
  private String floor;
  private String building;

  public IndexDirection(String floor, String building) {
    this.floor = floor;
    this.building = building;
  }

  @Override
  public String getDirectionText() {
    return "";
  }

  @Override
  public String getFloor() {
    return floor;
  }

  public String getBuilding() {
    return building;
  }

  public String getBuildingAndFloor() {
    if ("XX".equals(floor)) {
      return "Travel";
    } else {
      return building + " " + floor.replace("F", "");
    }
  }
}
