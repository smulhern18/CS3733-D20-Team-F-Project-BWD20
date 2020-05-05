package edu.wpi.teamF.Controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.page.DayPage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.ScheduleEntry;
import java.net.URL;
import java.time.*;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;

public class RoomSchedulerController implements Initializable {

  public AnchorPane anchorPane;
  public JFXComboBox<String> roomComboBox;
  public JFXTimePicker startTimeSelector;
  public JFXDatePicker startDateSelector;
  public JFXTimePicker endTimeSelector;
  public JFXDatePicker endDateSelector;
  public JFXButton submitButton;
  public JFXButton cancelButton;
  public AnchorPane scheduleAnchorPane;

  private CalendarView calendarView;
  private DatabaseManager databaseManager = DatabaseManager.getManager();
  private List<ScheduleEntry> entryList;

  @SneakyThrows
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
    initializeCalendarView();
  }

  private void initializeCalendarView() throws Exception {

    calendarView = new CalendarView();
    calendarView.setShowAddCalendarButton(false);
    calendarView.setShowPrintButton(false);
    calendarView.setShowSearchField(false);
    calendarView.setShowToolBar(false);
    calendarView.showDayPage();
    calendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
    calendarView.setLayout(DateControl.Layout.SWIMLANE);
    addCalenders();
    calendarView.setRequestedTime(LocalTime.now());
    setUpdatedTime();
    scheduleAnchorPane.getChildren().add(calendarView);
    setCalenderViewSize();
    addEntriesFromDatabase();
    //    calendarView.setEntryFactory(param -> {
    //      DateControl control = param.getDateControl();
    //      VirtualGrid grid = control.getVirtualGrid();
    //      ZonedDateTime time = param.getZonedDateTime();
    //      DayOfWeek firstDayOfWeek = control.getFirstDayOfWeek();
    //
    //      ZonedDateTime lowerTime = grid.adjustTime(time, false, firstDayOfWeek);
    //      ZonedDateTime upperTime = grid.adjustTime(time, true, firstDayOfWeek);
    //
    //      if (Duration.between(time, lowerTime).abs().minus(Duration.between(time,
    // upperTime).abs()).isNegative()) {
    //        time = lowerTime;
    //      } else {
    //        time = upperTime;
    //      }
    //
    //      Entry<Object> entry = new Entry<>(databaseManager.getAccount().getUsername());
    //      entry.changeStartDate(time.toLocalDate());
    //      entry.changeStartTime(time.toLocalTime());
    //      entry.changeEndDate(entry.getStartDate());
    //      entry.changeEndTime(entry.getStartTime().plusHours(1));
    //
    //      return entry;
    //    });
  }

  private void addEntriesFromDatabase() throws Exception {
    entryList = databaseManager.getAllScheduleEntries();
    System.out.println(LocalTime.now().format(ScheduleEntry.timeFormatter));
    //    ScheduleEntry testEntry =
    //        new ScheduleEntry(
    //            LocalDate.now().format(ScheduleEntry.dateFormatter),
    //            LocalTime.now().format(ScheduleEntry.timeFormatter),
    //            LocalDate.now().format(ScheduleEntry.dateFormatter),
    //            "10:00PM",
    //            "Reflection Room 1",
    //            "asdasdasd");
    //    entryList = new ArrayList<>();
    //    entryList.add(testEntry);
    for (ScheduleEntry scheduleEntry : entryList) {
      Calendar calendar = getCalendar(scheduleEntry.getRoom());
      Entry<String> entry = new Entry<>(scheduleEntry.getAccountID());
      entry.changeStartDate(
          LocalDate.parse(scheduleEntry.getStartDate(), ScheduleEntry.dateFormatter));
      entry.changeStartTime(
          LocalTime.parse(scheduleEntry.getStartTime(), ScheduleEntry.timeFormatter));
      entry.changeEndDate(LocalDate.parse(scheduleEntry.getEndDate(), ScheduleEntry.dateFormatter));
      entry.changeEndTime(LocalTime.parse(scheduleEntry.getEndTime(), ScheduleEntry.timeFormatter));
      entry.setCalendar(calendar);
    }
  }

  private Calendar getCalendar(String room) throws Exception {
    for (Calendar calendar : calendarView.getCalendars()) {
      if (room.equals(calendar.getName())) {
        return calendar;
      }
    }
    throw new Exception("Invalid Room Name");
  }

  private void setCalenderViewSize() {
    scheduleAnchorPane
        .heightProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              calendarView.setPrefHeight(newValue.doubleValue());
            }));
    scheduleAnchorPane
        .widthProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              calendarView.setPrefWidth(newValue.doubleValue());
              System.out.println("Width: " + newValue + " " + calendarView.getWidth());
            }));
  }

  private void setUpdatedTime() {
    Thread updateTimeThread =
        new Thread("Calendar: Update Time Thread") {
          @Override
          public void run() {
            while (true) {
              Platform.runLater(
                  () -> {
                    calendarView.setToday(LocalDate.now());
                    calendarView.setTime(LocalTime.now());
                  });

              try {
                // update every 10 seconds
                sleep(10000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          };
        };

    updateTimeThread.setPriority(Thread.MIN_PRIORITY);
    updateTimeThread.setDaemon(true);
    updateTimeThread.start();
  }

  private void addCalenders() {
    CalendarSource reflectionCalenderSource = new CalendarSource("Reflection Rooms");
    Calendar[] reflectionRooms = new Calendar[3];
    for (int i = 0; i < reflectionRooms.length; i++) {
      reflectionRooms[i] = new Calendar("Reflection Room " + (i + 1));
    }
    reflectionCalenderSource.getCalendars().addAll(reflectionRooms);
    CalendarSource onCallCalenderSource = new CalendarSource("On-Call Beds");
    Calendar[] onCallRooms = new Calendar[7];
    for (int i = 0; i < onCallRooms.length; i++) {
      onCallRooms[i] = new Calendar("On-Call Bed " + (i + 1));
    }
    onCallCalenderSource.getCalendars().addAll(onCallRooms);

    calendarView.getCalendarSources().clear();
    calendarView.getCalendarSources().addAll(reflectionCalenderSource, onCallCalenderSource);
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
