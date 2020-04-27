package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;

import java.util.Date;

public class TransportRequest extends ServiceRequest{
    public String type;
    public Node destination;
    public Date dateTimeCompleted;

    public TransportRequest(
            String id,
            Node location,
            String assignee,
            String description,
            Date dateTimeSubmitted,
            int priority,
            boolean complete, String type, Node destination, Date dateTimeCompleted)
            throws ValidationException {
        super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
        setType(type);
        setDestination(destination);
        setDateTimeCompleted(dateTimeCompleted);

    }

    public TransportRequest(
            Node location, String assignee, String description, Date dateTimeSubmitted, int priority, String type, Node destination, Date dateTimeCompleted)
            throws ValidationException {
        super(location, assignee, description, dateTimeSubmitted, priority);
        setType(type);
        setDestination(destination);
        setDateTimeCompleted(dateTimeCompleted);
    }


    public String getType(){
        return type;
    }

    public void setType(String type) throws ValidationException{
        Validators.transportTypeValidation(type);
        this.type = type;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) throws ValidationException{
        Validators.nodeValidation(destination);
        this.destination = destination;
    }

    public Date getDateTimeCompleted(){
        return dateTimeCompleted;
    }

    public void setDateTimeCompleted(Date dateTimeCompleted) throws ValidationException{
        Validators.dateValidation(dateTimeCompleted);
        this.dateTimeCompleted = dateTimeCompleted;
    }


}