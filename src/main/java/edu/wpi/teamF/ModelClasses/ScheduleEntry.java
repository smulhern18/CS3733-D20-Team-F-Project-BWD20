package edu.wpi.teamF.ModelClasses;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.Data;

@Data
public class ScheduleEntry {
  public static DateTimeFormatter dateFormatter =
      DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
  public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma");
  String ID;
  String startDate;
  String endDate;
  String startTime;
  String endTime;
  String room;
  String accountID;

  public ScheduleEntry(
      String id,
      String startDate,
      String startTime,
      String endDate,
      String endTime,
      String room,
      String accountID) {
    this.ID = id;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
    this.room = room;
    this.accountID = accountID;
  }
}
