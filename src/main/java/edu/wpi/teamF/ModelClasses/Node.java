package edu.wpi.teamF.ModelClasses;

import lombok.Data;

@Data
public class Node {

    public enum Type {

    }

    private String id;
    private short xCoord;
    private short yCoord;
    private String building;
    private String longName;
    private String shortName;
    private Type type;
    private short floor;


    public boolean equals(Object object) {
        return false;
    }


}
