package edu.wpi.teamF.pathfinding;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {

    private final Set<GraphNode> nodes; //make a set of GraphNode from either scratch or csv

    public Graph(Set<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public Set<GraphNode> getNodes(){
        return nodes;
    }

    //not sure if this should be in this class
    GraphNode nodeA = new GraphNode(10, 6, "A");
    GraphNode nodeB = new GraphNode(9, 2, "B");
    Set<GraphNode> newSet = new HashSet<GraphNode>();
    //newSet.add(nodeA); //doesn't like add for some reason

    public GraphNode getNode(String find){
        for(GraphNode i: this.nodes){
            if(i.getId() == find){
                return i;
            }
        }

    }

}
