package edu.wpi.teamF.ModelClasses;

import java.util.List;
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

  public int getPathLength(){
    return path.size();
  }

  public Node getFirstNode() {
    return path.get(0);
  }

  public void setFirstNode(Node firstNode) {
    path.set(0 , firstNode);
  }

  public Node getLastNode() {
    return path.get(path.size());
  }

  public void setLastNode(Node lastNode) {
    path.add(path.size() + 1, lastNode);
  }
}
