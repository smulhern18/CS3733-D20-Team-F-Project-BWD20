package edu.wpi.teamF.ModelClasses;

import lombok.Data;

@Data
public class Appointment {

  private String id;
  private Node location;
  private String room;
  private String userID;
  private String PCP;
}
