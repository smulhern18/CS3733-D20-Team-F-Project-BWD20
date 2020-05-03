package edu.wpi.teamF.ModelClasses;

import edu.wpi.teamF.ModelClasses.Account.Account;

import java.util.Date;

public class Validators {

  public static final int COORDINATE_MIN_VALUE = 0;
  public static final int COORDINATE_MAX_VALUE = 3840;
  public static final int FLOOR_MIN_LENGTH = 1;
  public static final int FLOOR_MAX_LENGTH = 10;
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
  public static final int MESSAGE_MIN_LENGTH = 1;
  public static final int MESSAGE_MAX_LENGTH = 128;
  public static final int MAINTENANCE_TYPE_MIN_LENGTH = 1;
  public static final int MAINTENANCE_TYPE_MAX_LENGTH = 64;
  public static final double ESTIMATED_COST_MIN_LENGTH = 0.0;
  public static final double ESTIMATED_COST_MAX_LENGTH = 1000000.0;


  public static <T extends MaintenanceRequest> void serviceRequestValidation(T t, int... constraints)
          throws ValidationException {
    nullCheckValidation(t, constraints);
    MaintenanceRequest serviceRequest = (MaintenanceRequest) t;

    idValidation(serviceRequest.getId());
    nameValidation(serviceRequest.getLocation());
    descriptionValidation(serviceRequest.getDescription());
    dateValidation(serviceRequest.getDateTimeSubmitted());
    priorityValidation(serviceRequest.getPriority());
    maintenanceTypeValidation(serviceRequest.getType());
    dateValidation(serviceRequest.getEstimatedCompletionDate());
    estimatedCostValidation(serviceRequest.getEstimatedCost());
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

  public static void maintenanceTypeValidation(String type, int... constraints)
          throws ValidationException {
    nullCheckValidation(type, constraints);
    if (type.length() < MAINTENANCE_TYPE_MIN_LENGTH || type.length() >MAINTENANCE_TYPE_MAX_LENGTH) {
      throw new ValidationException("Maintenance type is outside accepted values");
    }
  }

  public static void estimatedCostValidation(double estimatedCost, int... constraints)
          throws ValidationException {
    nullCheckValidation(estimatedCost, constraints);
    if (estimatedCost < ESTIMATED_COST_MIN_LENGTH ||estimatedCost >ESTIMATED_COST_MAX_LENGTH) {
      throw new ValidationException("Estimated cost is outside accepted values");
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
