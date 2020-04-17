package edu.wpi.teamF.Factories;

import edu.wpi.teamF.ModelClasses.Node;

import java.util.List;

public class NodeFactory {

    private static final NodeFactory factory = new NodeFactory();

    public static NodeFactory getFactory() {
        return factory;
    }

    public void create(Node node) {

    }

    public Node read(String id) {
        return null;
    }

    public void update(Node node) {

    }

    public void delete(String id) {

    }

    public List<Node> getNodesByType(Node.Type type) {
        return null;
    }

    public List<Node> getAllNodes() {
        return null;
    }
}
