package edu.wpi.teamF.ModelClasses.ServiceRequest;

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
}
