package edu.wpi.teamF.ModelClasses.Directions;

public class StraightDirection extends Direction {
  private double distance;
  private int intersectionsPassed;
  private int floor;
  private static double distanceConversion = 1.0; // Conversion multiplier from pixels to ft

  public StraightDirection(double distance, int intersectionsPassed, int floor) {
    this.distance = distance;
    this.intersectionsPassed = intersectionsPassed;
    this.floor = floor;
  }

  public void addDistance(double extraDistance) {
    this.distance += extraDistance;
  }

  public void addIntersection() {
    intersectionsPassed += 1;
  }

  public int getFloor() {
    return floor;
  }

  public String getDirectionText() {
    // Truncate to the nearest 10 ft after conversion to ft
    int distance = (int) (this.distance * distanceConversion);
    distance = distance / 10;
    distance = distance * 10;
    // Check if we pass any hallways
    if (intersectionsPassed == 0) {
      return ("Walk for about " + Integer.toString(distance) + "ft.");
    } else if (intersectionsPassed == 1) {
      return ("Walk for about "
          + Integer.toString(distance)
          + "ft, passing 1 intersecting hallway.");
    } else {
      return ("Walk for about "
          + Integer.toString(distance)
          + "ft, passing "
          + Integer.toString(intersectionsPassed)
          + " intersecting hallways.");
    }
  }

  public int getIntersectionsPassed() {
    return this.intersectionsPassed;
  }

  public double getDistance() {
    return this.distance;
  }
}
