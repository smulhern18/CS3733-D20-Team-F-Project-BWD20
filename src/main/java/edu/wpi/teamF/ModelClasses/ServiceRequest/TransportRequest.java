package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;

import java.util.Date;

public class TransportRequest extends ServiceRequest{
    public String type;
    public Node destination;

    public TransportRequest(
            String id,
            Node location,
            String assignee,
            String description,
            Date dateTimeSubmitted,
            int priority,
            boolean complete, String type, Node destination)
            throws ValidationException {
        super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
        setType(type);
        setDestination(destination);

    }

    public TransportRequest(
            Node location, String assignee, String description, Date dateTimeSubmitted, int priority, String type, Node destination)
            throws ValidationException {
        super(location, assignee, description, dateTimeSubmitted, priority);
        setType(type);
        setDestination(destination);
    }


    public String getType(){
        return type;
    }

    public Node getDestination(){
        return destination;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setDestination(Node destination){
        this.destination = destination;
    }


}