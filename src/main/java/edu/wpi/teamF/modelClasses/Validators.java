package edu.wpi.teamF.modelClasses;

public class Validators {

    public static final int COORDINATE_MIN_VALUE = 0;
    public static final int COORDINATE_MAX_VALUE = 32767;
    public static final int FLOOR_MIN_VALUE = 1;
    public static final int FLOOR_MAX_VALUE = 5;
    public static final int NAME_MIN_LENGTH = 1;
    public static final int NAME_MAX_LENGTH = 32;

    public static void coordValidation(short coord, int... constraints) throws ValidationException{
        nullCheckValidation(coord, constraints);
        if (!(coord > COORDINATE_MIN_VALUE && coord < COORDINATE_MAX_VALUE)){
            throw new ValidationException("Coordinate outside of accepted values");
        }
    }

    /**
     * Validation for the floor variable of the
     * @param floor
     * @param constraints
     * @throws ValidationException
     */
    public static void floorValidation(short floor, int... constraints) throws ValidationException{
        nullCheckValidation(floor, constraints);
        if (!(floor > FLOOR_MIN_VALUE && floor < FLOOR_MAX_VALUE)){
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
        if (string.length() >= NAME_MAX_LENGTH || string.length() <= NAME_MIN_LENGTH) {
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
    public static void nullCheckValidation(Object object, int... constraints) throws ValidationException {
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
