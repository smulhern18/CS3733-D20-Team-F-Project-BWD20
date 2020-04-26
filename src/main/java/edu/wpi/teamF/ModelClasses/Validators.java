package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import java.util.Date;

public class Validators {

  public static final int COORDINATE_MIN_VALUE = 0;
  public static final int COORDINATE_MAX_VALUE = 3840;
  public static final int FLOOR_MIN_VALUE = 1;
  public static final int FLOOR_MAX_VALUE = 10;
  public static final int NAME_MIN_LENGTH = 1;
  public static final int NAME_MAX_LENGTH = 32;
  public static final int BUILDING_MIN_LENGTH = 1;
  public static final int BUILDING_MAX_LENGTH = 32;
  public static final int LONG_NAME_MIN_LENGTH = 1;
  public static final int LONG_NAME_MAX_LENGTH = 64;
  public static final int SHORT_NAME_MIN_LENGTH = 1;
  public static final int SHORT_NAME_MAX_LENGTH = 16;
  public static final int ID_MIN_LENGTH = 1;
  public static final int ID_MAX_LENGTH = 32;
  public static final int EDGE_ID_MIN_LENGTH = 3;
  public static final int EDGE_ID_MAX_LENGTH = 65;
  public static final int ROOM_MIN_LENGTH = 1;
  public static final int ROOM_MAX_LENGTH = 32;
  public static final int USERID_MIN_LENGTH = 1;
  public static final int USERID_MAX_LENGTH = 32;
  public static final int PCP_MIN_LENGTH = 1;
  public static final int PCP_MAX_LENGTH = 32;
  public static final int PASSWORD_MIN_LENGTH = 1;
  public static final int PASSWORD_MAX_LENGTH = 128;
  public static final int ADDRESS_MIN_LENGTH = 1;
  public static final int ADDRESS_MAX_LENGTH = 64;
  public static final int DESCRIPTION_MIN_LENGTH = 1;
  public static final int DESCRIPTION_MAX_LENGTH = 64;
  public static final int PRIORITY_MIN_LENGTH = 1;
  public static final int PRIORITY_MAX_LENGTH = 3;
  public static final int MAKE_MIN_LENGTH = 1;
  public static final int MAKE_MAX_LENGTH = 32;
  public static final int HARDWARESOFTWARE_MIN_LENGTH = 1;
  public static final int HARDWARESOFTWARE_MAX_LENGTH = 32;
  public static final int OS_MIN_LENGTH = 1;
  public static final int OS_MAX_LENGTH = 8;

  public static <T extends ServiceRequest> void serviceRequestValidation(T t, int... constraints)
      throws ValidationException {
    nullCheckValidation(t, constraints);
    ServiceRequest serviceRequest = (ServiceRequest) t;

    idValidation(serviceRequest.getId());
    nodeValidation(serviceRequest.getLocation());
    descriptionValidation(serviceRequest.getDescription());
    dateValidation(serviceRequest.getDateTimeSubmitted());
    priorityValidation(serviceRequest.getPriority());
    booleanValidation(serviceRequest.getComplete());
    nameValidation(serviceRequest.getAssignee());
  }

  public static <T extends Account> void accountValidation(T t, int... constraints)
      throws ValidationException {
    nullCheckValidation(t, constraints);
    Account accountObject = (Account) t;

    emailAddressValidation(accountObject.getEmailAddress());
    nameValidation(accountObject.getFirstName());
    nameValidation(accountObject.getLastName());
    userIDValidation(accountObject.getUsername());
  }

  public static void emailAddressValidation(String address, int... constraints)
      throws ValidationException {
    nullCheckValidation(address, constraints);
    if ((!address.contains("@") || !address.contains("."))
        && (address.length() > ADDRESS_MIN_LENGTH && address.length() < ADDRESS_MAX_LENGTH)) {
      throw new ValidationException("Invalid Email Address: " + address);
    }
  }

  public static void passwordValidation(String password, int... constraints)
      throws ValidationException {
    nullCheckValidation(password, constraints);
    if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
      throw new ValidationException("Invalid password length");
    }
  }

  public static void makeValidation(String make, int... constraints) throws ValidationException {
    nullCheckValidation(make, constraints);
    if (make.length() < MAKE_MIN_LENGTH || make.length() > MAKE_MAX_LENGTH) {
      throw new ValidationException("Invalid make length");
    }
  }

  public static void hardwareSoftwareValidation(String hardwareSoftware, int... constraints)
      throws ValidationException {
    nullCheckValidation(hardwareSoftware, constraints);
    if (hardwareSoftware.length() < HARDWARESOFTWARE_MIN_LENGTH
        || hardwareSoftware.length() > HARDWARESOFTWARE_MAX_LENGTH) {
      throw new ValidationException("Invalid hardwareSoftware length");
    }
  }

  public static void osValidation(String OS, int... constraints) throws ValidationException {
    nullCheckValidation(OS, constraints);
    if (OS.length() < HARDWARESOFTWARE_MIN_LENGTH || OS.length() > HARDWARESOFTWARE_MAX_LENGTH) {
      throw new ValidationException("Invalid hardwareSoftware length");
    }
  }

  /**
   * Validation for Node
   *
   * @param t an instance of node or subclass to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends Node> void nodeValidation(T t, int... constraints)
      throws ValidationException {
    nullCheckValidation(t, constraints);
    Node nodeObject = (Node) t;

    coordValidation(nodeObject.getXCoord());
    coordValidation(nodeObject.getYCoord());
    nameValidation(nodeObject.getId());
    longNameValidation(nodeObject.getLongName());
    shortNameValidation(nodeObject.getShortName());
    buildingValidation(nodeObject.getBuilding());
    if (nodeObject.getEdges() != null) {
      for (Edge edgeName : nodeObject.getEdges()) {
        edgeValidation(edgeName);
      }
    }
    floorValidation(nodeObject.getFloor());
  }
  /**
   * Validation for Edge
   *
   * @param t an instance of node or subclass to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends Edge> void edgeValidation(T t, int... constraints)
      throws ValidationException {
    nullCheckValidation(t, constraints);
    Edge edgeObject = (Edge) t;
    idValidation(edgeObject.getId());
  }

  /**
   * Validation for IDs
   *
   * @param id the id to validate
   * @param constraints the optional Constraints for validation
   * @throws ValidationException should the id be invalid
   */
  public static void idValidation(String id, int... constraints) throws ValidationException {
    nullCheckValidation(id, constraints);
    if (!(id.length() < ID_MAX_LENGTH && id.length() > ID_MIN_LENGTH)) {
      throw new ValidationException("ID is invalid: " + id);
    }
  }

  /**
   * Validation for edge ids
   *
   * @param edgeId the edgeId to validate
   * @param constraints the optional Constraints for validation
   * @throws ValidationException should anything go wrong
   */
  public static void edgeIdValidation(String edgeId, int... constraints)
      throws ValidationException {
    nullCheckValidation(edgeId, constraints);
    if (!(edgeId.length() < EDGE_ID_MAX_LENGTH && edgeId.length() > EDGE_ID_MIN_LENGTH)) {
      throw new ValidationException("edge ID is invalid: " + edgeId);
    } else if (!edgeId.contains("_")) {
      throw new ValidationException("edge ID is invalid: " + edgeId);
    }
  }
  /**
   * Validation for coords
   *
   * @param coord the coord to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void coordValidation(short coord, int... constraints) throws ValidationException {
    nullCheckValidation(coord, constraints);
    if (!(coord > COORDINATE_MIN_VALUE && coord < COORDINATE_MAX_VALUE)) {
      throw new ValidationException("Coordinate outside of accepted values");
    }
  }

  /**
   * Validation for Buildings
   *
   * @param building the building to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the building be invalid
   */
  public static void buildingValidation(String building, int... constraints)
      throws ValidationException {
    nullCheckValidation(building, constraints);
    if (building.length() > BUILDING_MAX_LENGTH || building.length() < BUILDING_MIN_LENGTH) {
      throw new ValidationException("Building string is out of bounds");
    }
  }

  /**
   * Validation for longNames
   *
   * @param longName the long name to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the longName be invalid
   */
  public static void longNameValidation(String longName, int... constraints)
      throws ValidationException {
    nullCheckValidation(longName, constraints);
    if (longName.length() < LONG_NAME_MIN_LENGTH || longName.length() > LONG_NAME_MAX_LENGTH) {
      throw new ValidationException("Long Name string is out of bounds");
    }
  }

  /**
   * Validation for shortNames
   *
   * @param shortName the shortName to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the shortName be invalid
   */
  public static void shortNameValidation(String shortName, int... constraints)
      throws ValidationException {
    nullCheckValidation(shortName, constraints);
    if (shortName.length() < SHORT_NAME_MIN_LENGTH || shortName.length() > SHORT_NAME_MAX_LENGTH) {
      throw new ValidationException("Short Name string is out of bounds");
    }
  }

  /**
   * Validation for the floor variable
   *
   * @param floor the floor to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void floorValidation(short floor, int... constraints) throws ValidationException {
    nullCheckValidation(floor, constraints);
    if (!(floor >= FLOOR_MIN_VALUE && floor <= FLOOR_MAX_VALUE)) {
      throw new ValidationException("Floor outside of accepted values");
    }
  }

  /**
   * Validation for string length
   *
   * @param string the string to be validated
   * @param constraints optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void nameValidation(String string, int... constraints) throws ValidationException {
    nullCheckValidation(string, constraints);
    string = string.trim();
    if (string.length() > NAME_MAX_LENGTH || string.length() < NAME_MIN_LENGTH) {
      throw new ValidationException("string is out of bounds");
    }
  }

  /**
   * Validation for Appointment
   *
   * @param t an instance of appointment
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends Appointment> void appointmentValidation(T t, int... constraints)
      throws ValidationException {
    nullCheckValidation(t, constraints);
    Appointment appointmentObject = (Appointment) t;
    idValidation(appointmentObject.getId());
    nodeValidation(appointmentObject.getLocation());
    roomValidation(appointmentObject.getRoom());
    userIDValidation(appointmentObject.getUserID());
    PCPValidation(appointmentObject.getPCP());
  }

  /**
   * Validation for rooms
   *
   * @param room the room to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void roomValidation(String room, int... constraints) throws ValidationException {
    nullCheckValidation(room, constraints);
    if (room.length() < ROOM_MIN_LENGTH || room.length() > ROOM_MAX_LENGTH) {
      throw new ValidationException("Room is outside of accepted values");
    }
  }

  /**
   * Validation for userIDs
   *
   * @param userID the userID to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void userIDValidation(String userID, int... constraints)
      throws ValidationException {
    nullCheckValidation(userID, constraints);
    if (userID.length() < USERID_MIN_LENGTH || userID.length() > USERID_MAX_LENGTH) {
      throw new ValidationException("UserID is invalid");
    }
  }

  /**
   * Validation for PCPs
   *
   * @param PCP the PCP to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void PCPValidation(String PCP, int... constraints) throws ValidationException {
    nullCheckValidation(PCP, constraints);
    if (PCP.length() < PCP_MIN_LENGTH || PCP.length() > PCP_MAX_LENGTH) {
      throw new ValidationException("PCP is outside the accepted values");
    }
  }

  /**
   * Validation for Maintenance Requests
   *
   * @param t an instance of Maintenance Request to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends MaintenanceRequest> void maintenanceRequestValidation(
      T t, int... constraints) throws ValidationException {
    nullCheckValidation(t, constraints);
    MaintenanceRequest maintenanceRequestObject = (MaintenanceRequest) t;

    idValidation(maintenanceRequestObject.getId());
    nodeValidation(maintenanceRequestObject.getLocation());
    descriptionValidation(maintenanceRequestObject.getDescription());
    dateValidation(maintenanceRequestObject.getDateTimeSubmitted());
    priorityValidation(maintenanceRequestObject.getPriority());
  }

  /**
   * Validation for Maintenance Requests
   *
   * @param t an instance of Maintenance Request to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends ComputerServiceRequest> void computerServiceValidation(
      T t, int... constraints) throws ValidationException {
    nullCheckValidation(t, constraints);
    ComputerServiceRequest computerRequestObject = (ComputerServiceRequest) t;

    idValidation(computerRequestObject.getId());
    nodeValidation(computerRequestObject.getLocation());
    descriptionValidation(computerRequestObject.getDescription());
    dateValidation(computerRequestObject.getDateTimeSubmitted());
    priorityValidation(computerRequestObject.getPriority());
    osValidation(computerRequestObject.getOS());
    makeValidation(computerRequestObject.getMake());
    hardwareSoftwareValidation(computerRequestObject.getHardwareSoftware());
  }
  /**
   * Validation for Security Requests
   *
   * @param t an instance of Security Request to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends SecurityRequest> void securityRequestValidation(T t, int... constraints)
      throws ValidationException {
    nullCheckValidation(t, constraints);
    SecurityRequest securityRequestObject = (SecurityRequest) t;
  }

  /**
   * Validation for descriptions
   *
   * @param description the desciption to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void descriptionValidation(String description, int... constraints)
      throws ValidationException {
    nullCheckValidation(description, constraints);
    if (description.length() < DESCRIPTION_MIN_LENGTH
        || description.length() > DESCRIPTION_MAX_LENGTH) {
      throw new ValidationException("Description is out of bounds");
    }
  }

  /**
   * Validation for dates
   *
   * @param date the date to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void dateValidation(Object date, int... constraints) throws ValidationException {
    nullCheckValidation(date, constraints);
    if (!(date instanceof Date)) {
      throw new ValidationException("Provided object is not a date");
    }
  }

  /**
   * Validation for priority
   *
   * @param priority the priority to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void priorityValidation(int priority, int... constraints)
      throws ValidationException {
    nullCheckValidation(priority, constraints);
    if (priority < PRIORITY_MIN_LENGTH || priority > PRIORITY_MAX_LENGTH) {
      throw new ValidationException("Priority is outside accepted values");
    }
  }

  public static void booleanValidation(boolean bool, int... constraints)
      throws ValidationException {
    nullCheckValidation(bool, constraints);
    if (bool || !bool) {
      // ignore
    } else {
      throw new ValidationException("boolean is not a true or false value");
    }
  }

  /**
   * Validation check for null values
   *
   * @param object the object to be validated
   * @param constraints optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void nullCheckValidation(Object object, int... constraints)
      throws ValidationException {
    baseValidation(object, constraints);
    if (object == null) {
      throw new ValidationException("object cannot be null");
    }
  }

  /**
   * base Validation check
   *
   * @param object the object to be validated
   * @param constraints optional constraints for validation
   */
  public static void baseValidation(Object object, int... constraints) {
    // accept everything as valid
  }
}
