package edu.wpi.teamF.ModelClasses.ServiceRequest;

import java.util.Objects;

public class ReportsClass {

  private int timesVisited;
  private int timesSanitized;
  private String sanitizer;
  private String nodeID;

  public ReportsClass(String nodeID, int timesVisited, int timesSanitized, String sanitizer) {
    setNodeID(nodeID);
    setTimesVisited(timesVisited);
    setTimesSanitized(timesSanitized);
    setSanitizer(sanitizer);
  }

  public int getTimesVisited() {
    return timesVisited;
  }

  public void setTimesVisited(int timesVisited) {
    this.timesVisited = timesVisited;
  }

  public int getTimesSanitized() {
    return timesSanitized;
  }

  public void setTimesSanitized(int timesSanitized) {
    this.timesSanitized = timesSanitized;
  }

  public String getSanitizer() {
    return sanitizer;
  }

  public void setSanitizer(String sanitizer) {
    this.sanitizer = sanitizer;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ReportsClass)) return false;
    ReportsClass that = (ReportsClass) o;
    return getTimesVisited() == that.getTimesVisited()
        && getTimesSanitized() == that.getTimesSanitized()
        && Objects.equals(getSanitizer(), that.getSanitizer())
        && Objects.equals(getNodeID(), that.getNodeID());
  }
}
