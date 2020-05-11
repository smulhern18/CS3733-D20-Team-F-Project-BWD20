package edu.wpi.teamF.Controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
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
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

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

  private DayPage calendarView;
  private DatabaseManager databaseManager = DatabaseManager.getManager();
  private String loggedInAccountName = "AAAAAA";

  private boolean reset = false;

  @SneakyThrows
  public void initialize(URL location, ResourceBundle resources) {
    loggedInAccountName = databaseManager.getAccount().getUsername();
    initializeCalendarView();
  }

  private void initializeCalendarView() throws Exception {

    calendarView = new DayPage();

    //    calendarView.setShowAddCalendarButton(false);
    //    calendarView.setShowPrintButton(false);
    //    calendarView.setShowSearchField(false);
    //    calendarView.setShowToolBar(false);
    //    calendarView.showDayPage();
    calendarView.setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
    calendarView.setLayout(DateControl.Layout.SWIMLANE);
    addCalenders();
    calendarView.setRequestedTime(LocalTime.now());
    setUpdatedTime();
    setCalenderViewSize();

    calendarView.setSelectionMode(SelectionMode.SINGLE);
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
                } catch (Exception e) {
                  e.printStackTrace();
                }
                Entry<String> entry = createEntry(newEntry);

                return entry;
              } else {
                return null;
              }
            });
    scheduleAnchorPane.getChildren().clear();
    scheduleAnchorPane.getChildren().add(calendarView);

    Platform.runLater(
            () -> {
              try {
                addEntriesFromDatabase();
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
  }

  private @NotNull Entry<String> createEntry(@NotNull ScheduleEntry newEntry) {
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
                      if (t1 == null) {
                        try {
                          ScheduleEntry deletedScheduleEntry =
                                  databaseManager.readScheduleEntry(entry.getId());
                          deletedScheduleEntry.setID("" + System.currentTimeMillis());

                          databaseManager.deleteScheduleEntry(entry.getId());
                          if (!entry.getTitle().equals(loggedInAccountName)) {
                            System.out.println("NOT MINEEEEEEEEEEEEEEEEEE");

                            databaseManager.mainpulateScheduleEntry(deletedScheduleEntry);
                            Entry<String> deletedEntry = createEntry(deletedScheduleEntry);
                            deletedEntry.setCalendar(getCalendar(deletedScheduleEntry.getRoom()));
                            calendarView.refreshData();

                          } else {
                            System.out.println("MINEMINEMINEMINEMINE");
                          }
                        } catch (Exception e) {
                          e.printStackTrace();
                        }
                      } else {
                        if (!reset) {
                          try {
                            ScheduleEntry scheduleEntry = databaseManager.readScheduleEntry(entry.getId());
                            scheduleEntry.setRoom(t1.getName());
                            if (entry.getTitle().equals(loggedInAccountName)
                                    && !isScheduleEntryOverlapping(scheduleEntry)) {
                              // System.out.println("NOT OVERLAPPED");
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
                      }
                    }));
    return entry;
  }

  private void addEntriesFromDatabase() throws Exception {

    for (ScheduleEntry scheduleEntry : databaseManager.getAllScheduleEntries()) {
      Calendar calendar = getCalendar(scheduleEntry.getRoom());
      Entry<String> entry = createEntry(scheduleEntry);
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

  private void setUpdatedTime() {
    //    Thread updateTimeThread =
    //        new Thread("Calendar: Update Time Thread") {
    //          @Override
    //          public void run() {
    //            while (true) {
    //              Platform.runLater(
    //                  () -> {
    //                    calendarView.setToday(LocalDate.now());
    //                    calendarView.setTime(LocalTime.now());
    //                  });
    //
    //              try {
    //                // update every 10 seconds
    //                sleep(10000);
    //              } catch (InterruptedException e) {
    //                e.printStackTrace();
    //              }
    //            }
    //          };
    //        };
    //
    //    updateTimeThread.setPriority(Thread.MIN_PRIORITY);
    //    updateTimeThread.setDaemon(true);
    //    updateTimeThread.start();
  }

  private void addCalenders() {
    CalendarSource reflectionCalenderSource = new CalendarSource("Reflection Rooms");
    Calendar[] reflectionRooms = new Calendar[3];
    for (int i = 0; i < reflectionRooms.length; i++) {
      reflectionRooms[i] = new Calendar("Reflection Room " + (i + 1));

      reflectionRooms[i].setStyle(Calendar.Style.STYLE6);
    }
    reflectionCalenderSource.getCalendars().addAll(reflectionRooms);
    CalendarSource onCallCalenderSource = new CalendarSource("On-Call Beds");
    Calendar[] onCallRooms = new Calendar[7];
    for (int i = 0; i < onCallRooms.length; i++) {
      onCallRooms[i] = new Calendar("On-Call Bed " + (i + 1));
      onCallRooms[i].setStyle(Calendar.Style.STYLE2);
    }
    onCallCalenderSource.getCalendars().addAll(onCallRooms);

    calendarView.getCalendarSources().clear();
    calendarView.getCalendarSources().addAll(reflectionCalenderSource, onCallCalenderSource);
  }

  private void initializeCalendarDeleteEvent(Calendar calendar) {
    calendar.addEventHandler(
            calendarEvent -> {
              if (calendarEvent.getEventType().equals(CalendarEvent.ENTRY_CALENDAR_CHANGED)) {
                System.out.println("ENTRY CHANGED");
                Entry<?> oldEntry = calendarEvent.getEntry();
                if (calendarEvent.isEntryRemoved()
                        && !oldEntry.getTitle().equals(loggedInAccountName)) {
                  System.out.println("ENTRY IS REMOVED " + calendarEvent.getOldCalendar().getName());
                  calendarEvent.getOldCalendar().addEntry(oldEntry);
                  calendarView.refreshData();
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
