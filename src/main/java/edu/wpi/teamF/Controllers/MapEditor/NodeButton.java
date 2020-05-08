package edu.wpi.teamF.Controllers.MapEditor;

import edu.wpi.teamF.ModelClasses.Node;

import lombok.Data;

@Data
public class NodeButton {
    Node node;



    public NodeButton(Node node) {
        this.node = node;
    }
}
