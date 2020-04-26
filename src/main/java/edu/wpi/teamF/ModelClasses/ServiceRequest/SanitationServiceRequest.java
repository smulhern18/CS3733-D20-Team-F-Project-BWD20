package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class SanitationServiceRequest extends ServiceRequest{

    private String type;

    public SanitationServiceRequest(
            String id,
            Node location,
            String assignee,
            String description,
            Date dateTimeSubmitted,
            int priority,
            boolean complete,
            String type)
            throws ValidationException {
        super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
        setType(type);
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type) throws ValidationException {
        Validators.sanitationTypeValidation(type);
        this.type = type;
    }

}
