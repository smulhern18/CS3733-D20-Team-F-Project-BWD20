package edu.wpi.teamF.controllers;

import edu.wpi.teamF.factories.NodeFactory;

public class PathfindController extends SceneController {

    private NodeFactory nodeFactory;

    public PathfindController(NodeFactory nodeFactory){
        this.nodeFactory = nodeFactory;
    }

    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }

    public void setNodeFactory(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }


}
