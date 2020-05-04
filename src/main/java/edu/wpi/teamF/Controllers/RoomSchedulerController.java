package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import edu.wpi.teamF.ModelClasses.ScheduleEntry;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class RoomSchedulerController implements Initializable {

  public AnchorPane anchorPane;
  public JFXComboBox<String> roomComboBox;
  public JFXTimePicker startTimeSelector;
  public JFXDatePicker startDateSelector;
  public JFXTimePicker endTimeSelector;
  public JFXDatePicker endDateSelector;
  public JFXButton submitButton;
  public JFXButton cancelButton;

  private List<ScheduleEntry> entryList;

  public void initialize(URL location, ResourceBundle resources) {
    roomComboBox.setItems(
        FXCollections.observableArrayList(
            "Reflection Room 1",
            "Reflection Room 2",
            "Reflection Room 3",
            "On-Call Bed 1",
            "On-Call Bed 2",
            "On-Call Bed 3",
            "On-Call Bed 4",
            "On-Call Bed 5",
            "On-Call bed 6",
            "On-Call Bed 7"));
  }

  public void submit(ActionEvent actionEvent) {

    if (roomComboBox.getValue() != null
        && startDateSelector.getValue() != null
        && startTimeSelector != null
        && endTimeSelector != null
        && endDateSelector != null) {
      String accountID = "3465346e4356345";
      String room = roomComboBox.getValue();
      String startDate = startDateSelector.getValue().format(ScheduleEntry.dateFormatter);
      String startTime = startTimeSelector.getValue().format(ScheduleEntry.timeFormatter);
      String endDate = endDateSelector.getValue().format(ScheduleEntry.dateFormatter);
      String endTime = endTimeSelector.getValue().format(ScheduleEntry.timeFormatter);
      ScheduleEntry newEntry =
          new ScheduleEntry(startDate, startTime, endDate, endTime, room, accountID);
      if (!isScheduleEntryOverlapping(newEntry)) {
        entryList.add(newEntry);
      }
    }
  }

  public void cancel(ActionEvent actionEvent) {
    roomComboBox.setValue(null);
    startTimeSelector.setValue(null);
    startDateSelector.setValue(null);
    endTimeSelector.setValue(null);
    endDateSelector.setValue(null);
  }

  private boolean isScheduleEntryOverlapping(ScheduleEntry newEntry) {
    for (ScheduleEntry entry : entryList) {
      if (entry.getRoom().equals(newEntry.getRoom()) && isOverlapping(newEntry, entry)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isOverlapping(ScheduleEntry newEntry, ScheduleEntry oldEntry) {
    LocalDateTime newStart =
        LocalDateTime.of(
            LocalDate.parse(newEntry.getStartDate(), ScheduleEntry.dateFormatter),
            LocalTime.parse(newEntry.getStartTime(), ScheduleEntry.timeFormatter));
    LocalDateTime newEnd =
        LocalDateTime.of(
            LocalDate.parse(newEntry.getEndDate(), ScheduleEntry.dateFormatter),
            LocalTime.parse(newEntry.getEndTime(), ScheduleEntry.timeFormatter));
    LocalDateTime oldStart =
        LocalDateTime.of(
            LocalDate.parse(oldEntry.getStartDate(), ScheduleEntry.dateFormatter),
            LocalTime.parse(oldEntry.getStartTime(), ScheduleEntry.timeFormatter));
    LocalDateTime oldEnd =
        LocalDateTime.of(
            LocalDate.parse(oldEntry.getEndDate(), ScheduleEntry.dateFormatter),
            LocalTime.parse(oldEntry.getEndTime(), ScheduleEntry.timeFormatter));
    return newStart.isBefore(oldEnd) && oldStart.isBefore(newEnd);
  }
}
