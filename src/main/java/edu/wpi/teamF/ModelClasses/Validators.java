package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.ServiceRequest.*;
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
  public static final int OS_MAX_LENGTH = 32;
  public static final int TRANSPORT_TYPE_MIN_LENGTH = 1;
  public static final int TRANSPORT_TYPE_MAX_LENGTH = 32;
  public static final int PROBLEMTYPE_MIN_LENGTH = 1;
  public static final int PROBLEMTYPE_MAX_LENGTH = 32;
  public static final int LANGUAGE_MIN_LENGTH = 1;
  public static final int LANGUAGE_MAX_LENGTH = 32;
  private static final int GUARDS_MIN_VALUE = 1;
  private static final int GUARDS_MAX_VALUE = 10;
  public static final int SANITATION_TYPE_MIN_LENGTH = 1;
  public static final int SANITATION_TYPE_MAX_LENGTH = 32;
  public static final int MEDICINE_TYPE_MIN_LENGTH = 0;
  public static final int MEDICINE_TYPE_MAX_LENGTH = 64;
  public static final int INSTRUCTIONS_MIN_LENGTH = 0;
  public static final int INSTRUCTIONS_MAX_LENGTH = 64;
  public static final int TEMPERTURE_MIN_LENGTH = 1;
  public static final int TEMPERTURE_MAX_LENGTH = 8;
  public static final int ITEMS_MIN_LENGTH = 1;
  public static final int ITEMS_MAX_LENGTH = 32;
  public static final int QUANTITY_MIN_LENGTH = 1;
  public static final int QUANTITY_MAX_LENGTH = 32;

  public static <T extends SecurityRequest> void securityRequestValidation(T t, int... constraints)
      throws ValidationException {
    serviceRequestValidation(t, constraints);
    SecurityRequest securityRequest = (SecurityRequest) t;

    guardsRequestedValidation(securityRequest.getGuardsRequested(), constraints);
  }

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

  /** Validation for Security */
  public static void guardsRequestedValidation(int guardsRequested, int... constraints)
      throws ValidationException {
    nullCheckValidation(guardsRequested, constraints);

    if (!(guardsRequested >= GUARDS_MIN_VALUE && guardsRequested <= GUARDS_MAX_VALUE)) {

      throw new ValidationException(" Guards requested outside of accepted values");
    }
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
    if (address.matches(
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        && !(address.length() > ADDRESS_MIN_LENGTH || address.length() < ADDRESS_MAX_LENGTH)) {
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
  /** Validation for Security */
  public static void songRequestValidation(String songRequest, int... constraints)
      throws ValidationException {
    nullCheckValidation(songRequest, constraints);

    if (songRequest.length() == 0) {

      throw new ValidationException("Must enter a song request");
    }
  }

  public static void sanitationTypeValidation(String sanitationType, int... constraints)
      throws ValidationException {
    nullCheckValidation(sanitationType, constraints);
    if (sanitationType.length() < SANITATION_TYPE_MIN_LENGTH
        || sanitationType.length() > SANITATION_TYPE_MAX_LENGTH) {
      throw new ValidationException("Invalid sanitation type length");
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
   * Validation for Language Requests
   *
   * @param t an instance of Language Request to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends LanguageServiceRequest> void languageServiceValidation(
      T t, int... constraints) throws ValidationException {
    nullCheckValidation(t, constraints);
    LanguageServiceRequest languageRequestObject = (LanguageServiceRequest) t;

    idValidation(languageRequestObject.getId());
    nodeValidation(languageRequestObject.getLocation());
    descriptionValidation(languageRequestObject.getDescription());
    dateValidation(languageRequestObject.getDateTimeSubmitted());
    priorityValidation(languageRequestObject.getPriority());
    makeValidation(languageRequestObject.getLanguage());
    hardwareSoftwareValidation(languageRequestObject.getProblemType());
  }

  /*
   * Validation for medicine types
   *
   * @param medicineType the medicine type to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void medicineTypeValidation(String medicineType, int... constraints)
      throws ValidationException {
    nullCheckValidation(medicineType, constraints);
    if (medicineType.length() < MEDICINE_TYPE_MIN_LENGTH
        || medicineType.length() > MEDICINE_TYPE_MAX_LENGTH) {
      throw new ValidationException("Medicine type length is out of bounds");
    }
  }

  /**
   * Validation for medicine delivery instructions
   *
   * @param instructions the instructions to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void instructionsValidation(String instructions, int... constraints)
      throws ValidationException {
    nullCheckValidation(instructions, constraints);
    if (instructions.length() < INSTRUCTIONS_MIN_LENGTH
        || instructions.length() > INSTRUCTIONS_MAX_LENGTH) {
      throw new ValidationException("Instructions length is out of bounds");
    }
  }

  /**
   * Validation for Medicine Delivery Requests
   *
   * @param t an instance of a Medicine Delivery Request to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends MedicineDeliveryRequest> void medicineDeliveryRequestValidation(
      T t, int... constraints) throws ValidationException {
    nullCheckValidation(t, constraints);
    MedicineDeliveryRequest medicineDeliveryRequestObject = (MedicineDeliveryRequest) t;

    idValidation(medicineDeliveryRequestObject.getId());
    nodeValidation(medicineDeliveryRequestObject.getLocation());
    descriptionValidation(medicineDeliveryRequestObject.getDescription());
    dateValidation(medicineDeliveryRequestObject.getDateTimeSubmitted());
    priorityValidation(medicineDeliveryRequestObject.getPriority());
    medicineTypeValidation(medicineDeliveryRequestObject.getMedicineType());
    instructionsValidation(medicineDeliveryRequestObject.getInstructions());
  }

  /**
   * Validation for Security Requests
   *
   * @param t an instance of Security Request to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends MariachiRequest> void mariachiRequestValidation(T t, int... constraints)
      throws ValidationException {
    nullCheckValidation(t, constraints);
    MariachiRequest mariachiRequest = (MariachiRequest) t;

    idValidation(mariachiRequest.getId());
    nodeValidation(mariachiRequest.getLocation());
    descriptionValidation(mariachiRequest.getDescription());
    dateValidation(mariachiRequest.getDateTimeSubmitted());
    priorityValidation(mariachiRequest.getPriority());
    songRequestValidation(mariachiRequest.getSongRequest());
  }

  /**
   * Validation for Laundry Requests
   *
   * @param t an instance of Laundry Request to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends LaundryServiceRequest> void launduaryServiceValidation(
      T t, int... constraints) throws ValidationException {
    nullCheckValidation(t, constraints);
    LaundryServiceRequest launduaryRequestObject = (LaundryServiceRequest) t;

    idValidation(launduaryRequestObject.getId());
    nodeValidation(launduaryRequestObject.getLocation());
    descriptionValidation(launduaryRequestObject.getDescription());
    dateValidation(launduaryRequestObject.getDateTimeSubmitted());
    priorityValidation(launduaryRequestObject.getPriority());
    temperatureValidation(launduaryRequestObject.getTemperature());
    itemsValidation(launduaryRequestObject.getItems());
    quantityValidation(launduaryRequestObject.getQuantity());
  }

  public static void temperatureValidation(String temperature, int... constraints)
      throws ValidationException {
    nullCheckValidation(temperature, constraints);
    if (temperature.length() < TEMPERTURE_MIN_LENGTH
        || temperature.length() > TEMPERTURE_MAX_LENGTH) {
      throw new ValidationException("Invalid temperature size");
    }
  }

  public static void itemsValidation(String items, int... constraints) throws ValidationException {
    nullCheckValidation(items, constraints);
    if (items.length() < ITEMS_MIN_LENGTH || items.length() > ITEMS_MAX_LENGTH) {
      throw new ValidationException("Invalid items length");
    }
  }

  public static void quantityValidation(String quantity, int... constraints)
      throws ValidationException {
    nullCheckValidation(quantity, constraints);
    if (quantity.length() < QUANTITY_MIN_LENGTH || quantity.length() > QUANTITY_MAX_LENGTH) {
      throw new ValidationException("Invalid quantity length");
    }
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

  /**
   * Validation for transport types
   *
   * @param type the type to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static void transportTypeValidation(String type, int... constraints)
      throws ValidationException {
    nullCheckValidation(type, constraints);
    if (type.length() < TRANSPORT_TYPE_MIN_LENGTH || type.length() > TRANSPORT_TYPE_MAX_LENGTH) {
      throw new ValidationException("Transport type is outside accepted values");
    }
  }

  /**
   * Validation for Transport Requests
   *
   * @param t a transport request to validate
   * @param constraints the optional constraints for validation
   * @throws ValidationException should the validation fail
   */
  public static <T extends TransportRequest> void transportRequestValidation(
      T t, int... constraints) throws ValidationException {
    nullCheckValidation(t, constraints);
    TransportRequest transportRequestObject = (TransportRequest) t;
    idValidation(transportRequestObject.getId());
    nodeValidation(transportRequestObject.getLocation());
    descriptionValidation(transportRequestObject.getDescription());
    dateValidation(transportRequestObject.getDateTimeSubmitted());
    priorityValidation(transportRequestObject.getPriority());
    transportTypeValidation(transportRequestObject.getType());
    nodeValidation(transportRequestObject.getDestination());
  }

  public static <T extends SanitationServiceRequest> void sanitationServiceValidation(
      T t, int... constraints) throws ValidationException {
    nullCheckValidation(t, constraints);
    SanitationServiceRequest sanitationServiceRequest = (SanitationServiceRequest) t;
    idValidation(sanitationServiceRequest.getId());
    nodeValidation(sanitationServiceRequest.getLocation());
    descriptionValidation(sanitationServiceRequest.getDescription());
    dateValidation(sanitationServiceRequest.getDateTimeSubmitted());
    priorityValidation(sanitationServiceRequest.getPriority());
    transportTypeValidation(sanitationServiceRequest.getType());
  }

  public static void languageValidation(String type, int... constraints)
      throws ValidationException {
    nullCheckValidation(type, constraints);
    if (type.length() < LANGUAGE_MIN_LENGTH || type.length() > LANGUAGE_MAX_LENGTH) {
      throw new ValidationException("Language is outside values.");
    }
  }

  public static void problemValidation(String type, int... constraints) throws ValidationException {
    nullCheckValidation(type, constraints);
    if (type.length() < PROBLEMTYPE_MIN_LENGTH || type.length() > LANGUAGE_MAX_LENGTH) {
      throw new ValidationException("Problem Type outside values.");
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
