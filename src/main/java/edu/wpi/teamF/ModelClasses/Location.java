package edu.wpi.teamF.ModelClasses;

public class Location {
  private String building;
  private String floor;

  public Location(String building, String floor) {
    this.building = building;
    this.floor = floor;
  }

  public String getBuilding() {
    return building;
  }

  public String getFloor() {
    return floor;
  }
}
