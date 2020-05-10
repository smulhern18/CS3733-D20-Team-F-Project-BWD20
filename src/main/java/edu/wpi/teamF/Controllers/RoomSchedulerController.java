package edu.wpi.teamF.Controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.VirtualGrid;
import com.calendarfx.view.page.DayPage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.ScheduleEntry;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
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
  public JFXComboBox<String> roomTypeComboBox;

  // private DayPage calendarView;
  private DayPage onCallCalendarView = new DayPage();
  private DayPage reflectionCalendarView = new DayPage();
  private DayPage conferenceCalendarView = new DayPage();
  private DayPage computerCalendarView = new DayPage();
  private DatabaseManager databaseManager = DatabaseManager.getManager();
  private String loggedInAccountName = "AAAAAA";
  private boolean creatingDatabase = true;

  private ScheduleEntry rightClickDeleteBuggedEntry;
  private boolean reset = false;

  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    loggedInAccountName = databaseManager.getAccount().getUsername();
    addCalenders();
    setCalenderViewNames();
    initializeCalendarView(onCallCalendarView);
    initializeCalendarView(conferenceCalendarView);
    initializeCalendarView(reflectionCalendarView);
    initializeCalendarView(computerCalendarView);
    initializeRoomTypeComboBox();

    onCallCalendarView.toFront();
    Platform.runLater(
        () -> {
          try {

            System.out.println("creating DATABASE2" + creatingDatabase);
            addEntriesFromDatabase();

            System.out.println("creating DATABASE3" + creatingDatabase);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
    Platform.runLater(
        () -> {
          System.out.println("creating DATABASE1" + creatingDatabase);
          creatingDatabase = false;
        });
  }

  private void initializeRoomTypeComboBox() {
    roomTypeComboBox.setItems(
        FXCollections.observableArrayList(
            "On-Call Beds", "Reflection Rooms", "Conference Rooms", "Computer Rooms"));
    roomTypeComboBox
        .valueProperty()
        .addListener(
            (obs, oldValue, newValue) -> {
              if (newValue != null && !newValue.equals(oldValue)) {

                getCalendarView(newValue).toFront();
              }
            });
    roomTypeComboBox.setEditable(false);
    roomTypeComboBox.setValue("On-Call Beds");
  }

  private void setCalenderViewNames() {
    onCallCalendarView.setId("On-Call Beds");
    reflectionCalendarView.setId("Reflection Rooms");
    conferenceCalendarView.setId("Conference Rooms");
    computerCalendarView.setId("Computer Rooms");
  }

  private void initializeCalendarView(DayPage calendarView) throws Exception {

    calendarView.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
    calendarView.setLayout(DateControl.Layout.SWIMLANE);
    calendarView.setRequestedTime(LocalTime.now());

    setCalenderViewSize(calendarView);

    calendarView.setSelectionMode(SelectionMode.SINGLE);

    calendarView.setEntryEditPolicy(
        entryEditParameter -> {
          if (creatingDatabase) return true;
          if (entryEditParameter.getEntry().getTitle().equals(loggedInAccountName)) {

            if (entryEditParameter.getEditOperation().equals(DateControl.EditOperation.DELETE)) {

              try {
                System.out.println("DELETEEEEEE: " + entryEditParameter.getEntry().getId());

              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            return true;
          } else {
            System.out.println("NO PERMISSION: " + entryEditParameter.getEntry().getId());
            return false;
          }
        });
    calendarView.setEntryFactory(
        param -> {
          DateControl control = param.getDateControl();
          VirtualGrid grid = control.getVirtualGrid();

          ZonedDateTime time = param.getZonedDateTime();
          DayOfWeek firstDayOfWeek = control.getFirstDayOfWeek();

          ZonedDateTime lowerTime = grid.adjustTime(time, false, firstDayOfWeek);
          ZonedDateTime upperTime = grid.adjustTime(time, true, firstDayOfWeek);

          if (Duration.between(time, lowerTime)
              .abs()
              .minus(Duration.between(time, upperTime).abs())
              .isNegative()) {
            time = lowerTime;
          } else {
            time = upperTime;
          }
          ScheduleEntry newEntry =
              new ScheduleEntry(
                  "" + System.currentTimeMillis(),
                  time.toLocalDate().format(ScheduleEntry.dateFormatter),
                  time.toLocalTime().format(ScheduleEntry.timeFormatter),
                  time.plusHours(1).toLocalDate().format(ScheduleEntry.dateFormatter),
                  time.plusHours(1).toLocalTime().format(ScheduleEntry.timeFormatter),
                  param.getDefaultCalendar().getName(),
                  loggedInAccountName);
          if (!isScheduleEntryOverlapping(newEntry)) {

            try {
              databaseManager.mainpulateScheduleEntry(newEntry);
              System.out.println("newEntryID: " + newEntry.getID());
            } catch (Exception e) {
              e.printStackTrace();
            }
            Entry<String> entry = createEntry(newEntry);

            return entry;
          } else {
            return null;
          }
        });

    scheduleAnchorPane.getChildren().add(calendarView);
  }

  private Entry<String> createEntry(ScheduleEntry newEntry) {
    System.out.println("Create entry");
    Entry<String> entry = new Entry<>(newEntry.getAccountID());
    entry.setId(newEntry.getID());
    entry.changeStartDate(LocalDate.parse(newEntry.getStartDate(), ScheduleEntry.dateFormatter));
    entry.changeStartTime(LocalTime.parse(newEntry.getStartTime(), ScheduleEntry.timeFormatter));
    entry.changeEndDate(LocalDate.parse(newEntry.getEndDate(), ScheduleEntry.dateFormatter));
    entry.changeEndTime(LocalTime.parse(newEntry.getEndTime(), ScheduleEntry.timeFormatter));

    entry
        .intervalProperty()
        .addListener(
            (observableValue, interval, t1) -> {
              System.out.println(interval.getEndTime() + " " + t1.getEndTime());
              if (!reset) {
                try {

                  ScheduleEntry scheduleEntry = databaseManager.readScheduleEntry(entry.getId());
                  scheduleEntry.setStartDate(t1.getStartDate().format(ScheduleEntry.dateFormatter));
                  scheduleEntry.setStartTime(t1.getStartTime().format(ScheduleEntry.timeFormatter));
                  scheduleEntry.setEndDate(t1.getEndDate().format(ScheduleEntry.dateFormatter));
                  scheduleEntry.setEndTime(t1.getEndTime().format(ScheduleEntry.timeFormatter));
                  if (entry.getTitle().equals(loggedInAccountName)
                      && !isScheduleEntryOverlapping(scheduleEntry)) {
                    // System.out.println("NOT OVERLAPPED");
                    databaseManager.mainpulateScheduleEntry(scheduleEntry);
                  } else {
                    // System.out.println("OVERLAP REVERT BACK");
                    reset = true;
                    entry.setInterval(interval);
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                }
              } else {
                // System.out.println("RESETTED");
                reset = false;
              }
            });
    entry
        .calendarProperty()
        .addListener(
            ((observableValue, calendar, t1) -> {
              if (calendar != null) System.out.println("AASDASD" + calendar.getName());
              if (t1 != null) System.out.println("JIUJIUJ" + t1.getName());
              System.out.println("calender");
              if (t1 != null && !reset) {
                try {
                  System.out.println("modifying entry ID: " + entry.getId());
                  for (ScheduleEntry scheduleEntry1 : databaseManager.getAllScheduleEntries()) {
                    System.out.println(scheduleEntry1.getID());
                  }
                  ScheduleEntry scheduleEntry = databaseManager.readScheduleEntry(entry.getId());
                  //                  if (scheduleEntry == null) {
                  //                    scheduleEntry = rightClickDeleteBuggedEntry;
                  //                    databaseManager.mainpulateScheduleEntry(scheduleEntry);
                  //                  }
                  System.out.println(scheduleEntry);
                  System.out.println("calendar name: " + t1.getName());
                  scheduleEntry.setRoom(t1.getName());
                  if (entry.getTitle().equals(loggedInAccountName)
                      && !isScheduleEntryOverlapping(scheduleEntry)) {
                    System.out.println("NOT OVERLAPPED");
                    databaseManager.mainpulateScheduleEntry(scheduleEntry);
                  } else {
                    // System.out.println("OVERLAP REVERT BACK");
                    reset = true;
                    entry.setCalendar(calendar);
                  }

                } catch (Exception e) {

                  e.printStackTrace();
                }
              } else {
                // System.out.println("RESETTED");
                reset = false;
              }
            }));
    return entry;
  }

  private void addEntriesFromDatabase() throws Exception {
    System.out.println("ADDING FROM DATABASE");
    String realLoggedInName = loggedInAccountName;

    for (ScheduleEntry scheduleEntry : databaseManager.getAllScheduleEntries()) {
      loggedInAccountName = scheduleEntry.getAccountID();
      Calendar calendar = getCalendar(scheduleEntry.getRoom());
      Entry<String> entry = createEntry(scheduleEntry);
      entry.setCalendar(calendar);
    }
    loggedInAccountName = realLoggedInName;
  }

  private Calendar getCalendar(String room) throws Exception {
    for (Calendar calendar : onCallCalendarView.getCalendars()) {
      if (room.equals(calendar.getName())) return calendar;
    }
    for (Calendar calendar : reflectionCalendarView.getCalendars()) {
      if (room.equals(calendar.getName())) return calendar;
    }
    for (Calendar calendar : conferenceCalendarView.getCalendars()) {
      if (room.equals(calendar.getName())) return calendar;
    }
    for (Calendar calendar : computerCalendarView.getCalendars()) {
      if (room.equals(calendar.getName())) return calendar;
    }
    throw new Exception("Invalid Room Name");
  }

  private void setCalenderViewSize(DayPage calendarView) {
    scheduleAnchorPane
        .heightProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (newValue != null) {
                calendarView.setPrefHeight(newValue.doubleValue());
              } else if (oldValue != null) {
                calendarView.setPrefHeight(oldValue.doubleValue());
              }
            }));
    scheduleAnchorPane
        .widthProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (newValue != null) {
                calendarView.setPrefWidth(newValue.doubleValue());
              } else if (oldValue != null) {
                calendarView.setPrefWidth(oldValue.doubleValue());
              }

              System.out.println("Width: " + newValue + " " + calendarView.getWidth());
            }));
  }

  private void refreshData() {
    onCallCalendarView.refreshData();
    reflectionCalendarView.refreshData();
    conferenceCalendarView.refreshData();
    computerCalendarView.refreshData();
  }

  private DayPage getCalendarView(String calendarName) {
    switch (calendarName) {
      case "Reflection Rooms":
        return reflectionCalendarView;
      case "Computer Rooms":
        return computerCalendarView;
      case "On-Call Beds":
        return onCallCalendarView;
      case "Conference Rooms":
        return conferenceCalendarView;
    }
    return null;
  }

  private void addCalenders() {
    CalendarSource onCallCalenderSource = new CalendarSource("On-Call Beds");
    Calendar[] onCallRooms = new Calendar[7];
    for (int i = 0; i < onCallRooms.length; i++) {
      onCallRooms[i] = new Calendar("On-Call Bed " + (i + 1));
      calenderDeleteEventHandler(onCallRooms[i]);
      onCallRooms[i].setStyle(Calendar.Style.STYLE6);
    }
    onCallCalenderSource.getCalendars().addAll(onCallRooms);
    onCallCalendarView.getCalendarSources().clear();
    onCallCalendarView.getCalendarSources().add(onCallCalenderSource);

    CalendarSource reflectionCalenderSource = new CalendarSource("Reflection Rooms");
    Calendar[] reflectionRooms = new Calendar[3];
    for (int i = 0; i < reflectionRooms.length; i++) {
      reflectionRooms[i] = new Calendar("Reflection Room " + (i + 1));
      calenderDeleteEventHandler(reflectionRooms[i]);
      reflectionRooms[i].setStyle(Calendar.Style.STYLE6);
    }
    reflectionCalenderSource.getCalendars().addAll(reflectionRooms);
    reflectionCalendarView.getCalendarSources().clear();
    reflectionCalendarView.getCalendarSources().add(reflectionCalenderSource);

    CalendarSource conferenceCalenderSource = new CalendarSource("Conference Rooms");
    Calendar[] conferenceRooms = new Calendar[3];
    for (int i = 0; i < conferenceRooms.length; i++) {
      conferenceRooms[i] = new Calendar("Conference Room " + (i + 1));
      calenderDeleteEventHandler(conferenceRooms[i]);
      conferenceRooms[i].setStyle(Calendar.Style.STYLE4);
    }
    conferenceCalenderSource.getCalendars().addAll(conferenceRooms);
    conferenceCalendarView.getCalendarSources().clear();
    conferenceCalendarView.getCalendarSources().add(conferenceCalenderSource);

    CalendarSource computerCalenderSource = new CalendarSource("Computer Rooms");
    Calendar[] computerRooms = new Calendar[6];
    for (int i = 0; i < computerRooms.length; i++) {
      computerRooms[i] = new Calendar("Computer Room " + (i + 1));
      calenderDeleteEventHandler(computerRooms[i]);
      computerRooms[i].setStyle(Calendar.Style.STYLE5);
    }
    computerCalenderSource.getCalendars().addAll(computerRooms);
    computerCalendarView.getCalendarSources().clear();
    computerCalendarView.getCalendarSources().add(computerCalenderSource);
  }

  private void calenderDeleteEventHandler(Calendar calendar) {
    calendar.addEventHandler(
        calendarEvent -> {
          if (calendarEvent.isEntryRemoved()) {
            System.out.println(calendarEvent.getEntry().getId() + " REMOVED FROM CALENDER");
            try {
              databaseManager.deleteScheduleEntry(calendarEvent.getEntry().getId());
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }

  private boolean isScheduleEntryOverlapping(ScheduleEntry newEntry) {
    // System.out.println("NEW ENTRY ID" + newEntry.getID());
    for (ScheduleEntry entry : databaseManager.getAllScheduleEntries()) {
      // System.out.println(entry.getID());
      if (!entry.getID().equals(newEntry.getID())
          && entry.getRoom().equals(newEntry.getRoom())
          && isOverlapping(newEntry, entry)) {
        return true;
      }
    }
    // System.out.println("not overlap");
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
