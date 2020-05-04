package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import edu.wpi.teamF.ModelClasses.ScheduleEntry;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class RoomSchedulerController implements Initializable {

  public AnchorPane anchorPane;
  public JFXComboBox<String> roomComboBox;
  public JFXTimePicker startTimeSelector;
  public JFXDatePicker startDateSelector;
  public JFXTimePicker endTimeSelector;
  public JFXDatePicker endDateSelector;

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

  private boolean checkScheduleEntryOverlap(ScheduleEntry entry) {
    return false;
  }
}
