package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.ModelClasses.Account.Account;

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
