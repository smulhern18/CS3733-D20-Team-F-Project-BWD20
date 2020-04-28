package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class LaundryServiceRequest extends ServiceRequest {
    private String items;
    private String quantity;
    private String temperature;

    public LaundryServiceRequest(
            String id,
            Node location,
            String assignee,
            String description,
            Date dateTimeSubmitted,
            int priority,
            boolean complete,
            String items,
            String quantity,
            String temperature)
            throws ValidationException {
        super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
        setQuantity(quantity);
        setItems(items);
        setTemperature(temperature);
    }

    public LaundryServiceRequest(
            Node location,
            String description,
            String assignee,
            Date dateTimeSubmitted,
            int priority,
            String itemsModel,
            String Quantity,
            String temperature)
            throws ValidationException {
        super(location, assignee, description, dateTimeSubmitted, priority);
        setQuantity(Quantity);
        setItems(itemsModel);
        setTemperature(temperature);
    }

    public String getItems() {
        return items;
    }

    public void setItems(String itemsModel) throws ValidationException {
        Validators.itemsValidation(itemsModel);
        this.items = itemsModel;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) throws ValidationException {
        Validators.quantityValidation(quantity);
        this.quantity = quantity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) throws ValidationException {
        Validators.temperatureValidation(temperature);
        this.temperature = temperature;
    }
}
