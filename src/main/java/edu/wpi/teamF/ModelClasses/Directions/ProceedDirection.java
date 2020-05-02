package edu.wpi.teamF.ModelClasses.Directions;

public class ProceedDirection extends Direction {
  private String proceedTo;
  private String floor;

  public ProceedDirection(String proceedTo, String floor) {
    this.proceedTo = proceedTo;
    this.floor = floor;
  }

  @Override
  public String getDirectionText() {
    return ("Proceed to " + proceedTo + ".");
  }

  @Override
  public String getFloor() {
    return floor;
  }
}
