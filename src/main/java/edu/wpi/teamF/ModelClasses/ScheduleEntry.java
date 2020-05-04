package edu.wpi.teamF.ModelClasses;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.Data;

@Data
public class ScheduleEntry {
  public static DateTimeFormatter dateFormatter =
      DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
  public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mma");
  String ID;
  String startDate;
  String endDate;
  String startTime;
  String endTime;
  String room;
  String accountID;

  public ScheduleEntry(
      String startDate,
      String startTime,
      String endDate,
      String endTime,
      String room,
      String accountID) {
    this.ID = startDate + "," + startTime + "," + room;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
    this.room = room;
    this.accountID = accountID;
  }
}
