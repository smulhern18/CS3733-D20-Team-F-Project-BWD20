package edu.wpi.teamF.ModelClasses.UIClasses;

import com.jfoenix.controls.RecursiveTreeItem;
import edu.wpi.teamF.ModelClasses.ServiceRequest.FlowerRequest;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
public class UiFlowerServiceRequest extends RecursiveTreeItem<UiFlowerServiceRequest> {
    public SimpleStringProperty ID;
    public SimpleStringProperty location;
    public SimpleStringProperty recipientName;
    public SimpleStringProperty roomNumber;
    public SimpleStringProperty bouquet;
    public SimpleStringProperty message;
    public SimpleStringProperty buyerName;
    public SimpleStringProperty phoneNumber;
    public SimpleStringProperty giftWrap;
    public SimpleStringProperty priority;
    public SimpleStringProperty assignee;
    public SimpleStringProperty dateSubmitted;
    public SimpleStringProperty completed;

    public UiFlowerServiceRequest(FlowerRequest fr) {
        DateFormat date = new SimpleDateFormat("yyyy-mm-dd");
        boolean isCompleted = fr.getComplete();
        String cmp;
        if (isCompleted) {
            cmp = "Complete";
        } else {
            cmp = "Incomplete";
        }

        this.ID = new SimpleStringProperty(fr.getId());
        this.location = new SimpleStringProperty(fr.getLocation().getId());
        this.recipientName = new SimpleStringProperty(fr.getRecipientInput());
        this.roomNumber = new SimpleStringProperty("" + fr.getRoomInput());
        this.bouquet = new SimpleStringProperty(fr.getChoice());
        this.message = new SimpleStringProperty("" + fr.getMessageInput());
        this.buyerName = new SimpleStringProperty(fr.getBuyerName());
        this.phoneNumber = new SimpleStringProperty(fr.getPhoneNumber());
        this.giftWrap = new SimpleStringProperty("" + fr.getGiftWrap());
        this.priority = new SimpleStringProperty("" + fr.getPriority());
        this.assignee = new SimpleStringProperty(fr.getAssignee());
        this.dateSubmitted = new SimpleStringProperty(date.format(fr.getDateTimeSubmitted()));
        this.completed = new SimpleStringProperty(cmp);
    }

    public boolean equalsFR(Object other) {
        boolean isEqual = false;
        if (other instanceof FlowerRequest) {
            FlowerRequest otherFR = (FlowerRequest) other;
            isEqual =
                    this.getDateTimesSubmitted().equals(otherFR.getDateTimeSubmitted())
                            && this.location.equals(otherFR.getLocation())
                            && this.ID.equals(otherFR.getId());
        }
        return isEqual;
    }
}
