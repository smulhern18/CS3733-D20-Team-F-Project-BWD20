package edu.wpi.cs3733.d20.teamF.ModelClasses;

public class ServiceException extends Exception {

  String message = "";

  public ServiceException(String message) {
    this.message = this.message + message;
  }
}
