package edu.wpi.cs3733.d20.teamF.ModelClasses;

public class ValidationException extends Exception {
  private static final long serialVersionUID = 1L;

  /** Exception Message */
  String message = "";

  public ValidationException(String message) {
    this.message = "Validation failed: invalid " + message;
  }
}
