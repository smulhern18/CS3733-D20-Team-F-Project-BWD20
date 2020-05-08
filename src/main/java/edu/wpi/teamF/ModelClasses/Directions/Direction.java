package edu.wpi.teamF.ModelClasses.Directions;

public abstract class Direction {
  public abstract String getDirectionText();

  public String getDirectionTextCall() {
    return getDirectionText();
  }

  public String getDirectionTextSMS() {
    return getDirectionText();
  }

  public String getDirectionTextPrint() {
    return getDirectionText();
  }

  public abstract String getFloor();
}
