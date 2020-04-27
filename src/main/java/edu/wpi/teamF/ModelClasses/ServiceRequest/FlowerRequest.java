package edu.wpi.teamF.ModelClasses.ServiceRequest;

import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ValidationException;
import edu.wpi.teamF.ModelClasses.Validators;
import java.util.Date;

public class FlowerRequest extends ServiceRequest {
  private String recipientInput;
  private int roomInput;
  private String choice;
  private String messageInput;
  private String buyerName;
  private String phoneNumber;
  private Boolean giftWrap;

  public FlowerRequest(
      String id,
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      String recipientInput,
      int roomInput,
      String choice,
      String messageInput,
      String buyerName,
      String phoneNumber,
      Boolean giftWrap,
      boolean complete)
      throws ValidationException {
    super(id, location, assignee, description, dateTimeSubmitted, priority, complete);
    setRecipientInput(recipientInput);
    setChoice(choice);
    setPhoneNumber(phoneNumber);
    setBuyerName(buyerName);
    setGiftWrap(giftWrap);
    setMessageInput(messageInput);
    setRoomInput(roomInput);
  }

  public FlowerRequest(
      Node location,
      String assignee,
      String description,
      Date dateTimeSubmitted,
      int priority,
      String recipientInput,
      int roomInput,
      String choice,
      String messageInput,
      String phoneNumber,
      String buyerName,
      Boolean giftWrap)
      throws ValidationException {
    super(location, assignee, description, dateTimeSubmitted, priority);
    setRecipientInput(recipientInput);
    setChoice(choice);
    setPhoneNumber(phoneNumber);
    setBuyerName(buyerName);
    setGiftWrap(giftWrap);
    setMessageInput(messageInput);
    setRoomInput(roomInput);
  }

  public String getRecipientInput() {
    return recipientInput;
  }

  public void setRecipientInput(String recipientInput) throws ValidationException {
    Validators.nameValidation(recipientInput);
    this.recipientInput = recipientInput;
  }

  public int getRoomInput() {
    return roomInput;
  }

  public void setRoomInput(int roomInput) {
    this.roomInput = roomInput;
  }

  public String getChoice() {
    return choice;
  }

  public void setChoice(String choice) throws ValidationException {
    Validators.nameValidation(choice);
    this.choice = choice;
  }

  public String getMessageInput() {
    return messageInput;
  }

  public void setMessageInput(String messageInput) throws ValidationException {
    Validators.messageValidation(messageInput);
    this.messageInput = messageInput;
  }

  public String getBuyerName() {
    return buyerName;
  }

  public void setBuyerName(String buyerName) throws ValidationException {
    Validators.nameValidation(buyerName);
    this.buyerName = buyerName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) throws ValidationException {
    Validators.phoneNumberValidation(phoneNumber);
    this.phoneNumber = phoneNumber;
  }

  public Boolean getGiftWrap() {
    return giftWrap;
  }

  public void setGiftWrap(Boolean giftWrap) throws ValidationException {
    Validators.booleanValidation(giftWrap);
    this.giftWrap = giftWrap;
  }
}
