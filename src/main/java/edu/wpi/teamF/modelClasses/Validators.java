package edu.wpi.teamF.modelClasses;

public class Validators {

  public static final int COORDINATE_MIN_VALUE = 0;
  public static final int COORDINATE_MAX_VALUE = 32767;
  public static final int FLOOR_MIN_VALUE = 1;
  public static final int FLOOR_MAX_VALUE = 5;
  public static final int NAME_MIN_LENGTH = 1;
  public static final int NAME_MAX_LENGTH = 32;
  public static final int BUILDING_MIN_LENGTH = 1;
  public static final int BUILDING_MAX_LENGTH = 32;
  public static final int LONG_NAME_MIN_LENGTH = 1;
  public static final int LONG_NAME_MAX_LENGTH = 64;
  public static final int SHORT_NAME_MIN_LENGTH = 1;
  public static final int SHORT_NAME_MAX_LENGTH = 16;

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
    nameValidation(nodeObject.getName());
    longNameValidation(nodeObject.getShortName());
    shortNameValidation(nodeObject.getLongName());
    buildingValidation(nodeObject.getBuilding());
    for (String nodeName : nodeObject.getNeighbors()) {
      nameValidation(nodeName);
    }
    floorValidation(nodeObject.getFloor());
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
   * @throws ValidationException should the validation fail
   */
  public static void baseValidation(Object object, int... constraints) throws ValidationException {
    // accept everything as valid
  }
}
