package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Edge;
import java.util.List;

public class EdgeFactory {

  private static final EdgeFactory factory = new EdgeFactory();

  public static EdgeFactory getFactory() {
    return factory;
  }

  public void create(Edge edge) {}

  public Edge read(String id) {
    return null;
  }

  public void update(Edge edge) {}

  public void delete(String id) {}

  public List<Edge> getAllEdges() {
    return null;
  }
}
