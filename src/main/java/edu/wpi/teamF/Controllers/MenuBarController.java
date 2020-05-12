package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class MenuBarController implements Initializable {
  public JFXButton mainMenuButton;
  public JFXButton loginButton;
  public JFXButton serviceButton;
  public JFXButton pathfindButton;
  public JFXButton adminButton;
  public JFXButton helpButton;
  public JFXButton settingsButton;
  public Line line6;
  public Line line7;
  public JFXButton adminButton1;
  public JFXButton settingsButton1;
  public JFXButton helpButton1;
  public JFXButton pathfindButton1;
  public Line line5;
  public JFXButton LogoutButton;
  public JFXButton loginButton1;
  public JFXButton logoutbutton1;
  public Label timelabel;

  SceneController sceneController = App.getSceneController();
  DatabaseManager dbm = DatabaseManager.getManager();

  public void logout(ActionEvent actionEvent) throws IOException {
    try {
      dbm.setLogin(null);
    } catch (Exception e) {

    }
    logoutbutton1.setVisible(false);
    logoutbutton1.setDisable(true);
    loginButton.setVisible(true);
    logoutbutton1.setDisable(false);
    loginButton.toFront();
    LogoutButton.setVisible(false);
    LogoutButton.setDisable(true);
    loginButton1.setVisible(true);
    LogoutButton.setDisable(false);
    loginButton1.toFront();
    sceneController.switchScene("MainMenu");
  }

  public void autoLogout() {
    try {
      dbm.setLogin(null);
      SceneController sceneController2 = App.getSceneController();
      sceneController2.switchScene("MainMenu");
    } catch (Exception e) {
      System.out.println(e.getClass());
    }
  }

  public void login(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Login");
  }

  public void pathfinder(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void serviceRequest(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }

  public void admin(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MapEditor");
  }

  public void help(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("HelpMain");
  }

  public void settings(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Accounts");
  }

  public void mainMenu(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  // time
  @FXML
  public void time() {
    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e -> {
                  LocalTime currentTime = LocalTime.now();
                  timelabel.setText(
                      currentTime.getHour()
                          + ": "
                          + currentTime.getMinute()
                          + ": "
                          + currentTime.getSecond());
                }),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }

  public void tools(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Tools");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    Account.Type userLevel = dbm.getPermissions();
    if (userLevel == null) {
      adminButton.setVisible(false);
      settingsButton.setVisible(false);
      settingsButton1.setVisible(false);
      adminButton.setDisable(true);
      adminButton1.setVisible(false);
      adminButton1.setDisable(true);
      line6.setVisible(false);
      line7.setVisible(false);
      loginButton.setVisible(true);
      loginButton.setDisable(false);
      loginButton.toFront();
      loginButton1.setVisible(true);
      loginButton1.toFront();
      loginButton1.setDisable(false);
      logoutbutton1.setVisible(false);
      LogoutButton.toBack();
      LogoutButton.setVisible(false);
    } else if (userLevel == Account.Type.USER) {
      loginButton.setVisible(false);
      loginButton.setDisable(true);
      logoutbutton1.setVisible(true);
      logoutbutton1.setDisable(false);
      logoutbutton1.toFront();
      loginButton1.setDisable(true);
      loginButton1.setVisible(false);
      loginButton1.toBack();
      adminButton.setVisible(false);
      settingsButton.setVisible(false);
      settingsButton1.setVisible(false);
      adminButton.setDisable(true);
      adminButton1.setVisible(false);
      adminButton1.setDisable(true);
      line6.setVisible(false);
      line7.setVisible(false);
    } else if (userLevel == Account.Type.STAFF || userLevel == Account.Type.ADMIN) {
      adminButton.setDisable(false);
      adminButton.setVisible(true);
      adminButton1.setVisible(true);
      adminButton1.setDisable(false);
      settingsButton.setVisible(true);
      settingsButton1.setVisible(true);
      line6.setVisible(true);
      line7.setVisible(true);
      // set to staff
      // enable admin page
    }
    if (userLevel == Account.Type.STAFF
        || userLevel == Account.Type.ADMIN
        || userLevel == Account.Type.USER) {
      LogoutButton.setDisable(false);
      LogoutButton.setVisible(true);
      LogoutButton.toFront();
      loginButton.setDisable(true);
      loginButton.setVisible(false);
      logoutbutton1.setDisable(false);
      logoutbutton1.setVisible(true);
      logoutbutton1.toFront();
      loginButton1.setDisable(true);
      loginButton1.setVisible(false);
    }
    time();
  }
}
