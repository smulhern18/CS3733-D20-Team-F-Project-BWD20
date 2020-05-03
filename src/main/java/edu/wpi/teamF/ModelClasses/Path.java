package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Path {

  private List<Node> path;
  private List<Location> locationsList;
  private int uniqueLocations;

  public Path() {}

  public Path(List<Node> path) {
    setPath(path);
    locationsList = new ArrayList<>();
    locationsList.add(new Location(path.get(0).getBuilding(), path.get(0).getFloor()));
    uniqueLocations = 1;
    for (int i = 1; i < path.size(); i++) {
      if (!path.get(i).getFloor().equals(path.get(i - 1).getFloor())) {
        // This is a unique element in the path list because floors aren't the same
        locationsList.add(new Location(path.get(i).getBuilding(), path.get(i).getFloor()));
        uniqueLocations++;
      }
    }
  }

  public List<Node> getPath() {
    return this.path;
  }

  public void setPath(List<Node> path) {
    this.path = path;
  }

  public void addToPath(Node node) {
    path.add(node);
  }

  public double getPathLength() { // find the total length travelled
    double totalLength = 0;
    EuclideanScorer totalLengthScorer = new EuclideanScorer();
    for (int i = 0; i < path.size() - 1; i++) {
      totalLength = totalLength + totalLengthScorer.computeCost(path.get(i), path.get(i + 1));
    }
    return totalLength;
  }

  public Node getFirstNode() {
    return path.get(0);
  }

  public void setFirstNode(Node firstNode) {
    path.set(0, firstNode);
  }

  public Node getLastNode() {
    return path.get(path.size() - 1);
  }

  public void setLastNode(Node lastNode) {
    path.add(path.size(), lastNode);
  }

  public List<Location> getLocationsList() {
    return locationsList;
  }

  public Location getLocationAtIndex(int i) {
    return locationsList.get(i);
  }

  public int getUniqueLocations() {
    return uniqueLocations;
  }
}
