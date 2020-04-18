package edu.wpi.teamF.ModelClasses;

import java.util.List;

import edu.wpi.teamF.ModelClasses.Scorer.EuclideanScorer;
import lombok.Data;

@Data
public class Path {

  private List<Node> path;

  public Path() {}

  public Path(List<Node> path) {
    setPath(path);
  }

  public List<Node> getPath(){
    return this.path;
  }

  public void setPath(List<Node> path) {
    this.path = path;
  }

  public void addToPath(Node node){
    path.add(node);
  }

  public double getPathLength(){ //find the total length travelled
    double totalLength = 0;
    EuclideanScorer totalLengthScorer = new EuclideanScorer();
    for(int i = 0; i < path.size() - 1; i++){
      totalLength = totalLength + totalLengthScorer.computeCost(path.get(i), path.get(i + 1));
    }
    return totalLength;
  }

  public Node getFirstNode() {
    return path.get(0);
  }

  public void setFirstNode(Node firstNode) {
    path.set(0 , firstNode);
  }

  public Node getLastNode() {
    return path.get(path.size() -1);
  }

  public void setLastNode(Node lastNode) {
    path.add(path.size(), lastNode);
  }
}
