package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpAdminController implements Initializable {

  @FXML public AnchorPane rootPane;
  @FXML private StackPane stackPane;
  // @FXML
  // private JFXButton textfield;
  // @FXML
  // private JFXButton search;

  @Override
  public void initialize(URL url, ResourceBundle rb) {}

  @FXML
  private void signup(ActionEvent actionEvent) {
    JFXDialogLayout content1 = new JFXDialogLayout();
    content1.setHeading(new Text("How to sign up?"));
    content1.setBody(
        new Text(
            "1. Click on the 'Login' button on the main menu. \n2. Click on 'Register'. \n3. Enter your information.\n4. Click on 'Register'. New account will be created."));
    JFXDialog dialog1 = new JFXDialog(stackPane, content1, JFXDialog.DialogTransition.CENTER);
    JFXButton button1 = new JFXButton("Close");
    button1.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog1.close();
          }
        });
    content1.setActions(button1);
    dialog1.show();
  }

  @FXML
  private void login(ActionEvent actionEvent) {
    JFXDialogLayout content2 = new JFXDialogLayout();
    content2.setHeading(new Text("How to login?"));
    content2.setBody(
        new Text(
            "1. Click on the 'Login' button on Main Menu.\n"
                + "2. If you have an account, then login.\n"
                + "3. If you don't have an account, then click on 'Register' to sign up.\n"));
    JFXDialog dialog2 = new JFXDialog(stackPane, content2, JFXDialog.DialogTransition.CENTER);
    JFXButton button2 = new JFXButton("Close");
    button2.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog2.close();
          }
        });
    content2.setActions(button2);
    dialog2.show();
  }

  @FXML
  private void findpath(ActionEvent actionEvent) {
    JFXDialogLayout content3 = new JFXDialogLayout();
    content3.setHeading(new Text("How to find a path?"));
    content3.setBody(
        new Text(
            "1.\tGo to pathfinder page.\n"
                + "2.\tSelect or search current location from drop down menu.\n"
                + "3.\tYou can also click current location directly from the map. \n"
                + "4.\tSelect the destination. \n"
                + "5.\tSelect a preference (elevators or stairs).\n"
                + "6.\tClick Find path. \n"
                + "7.\tClick Reset to find another shortest path.\n"));
    JFXDialog dialog3 = new JFXDialog(stackPane, content3, JFXDialog.DialogTransition.CENTER);
    JFXButton button3 = new JFXButton("Close");
    button3.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog3.close();
          }
        });
    content3.setActions(button3);
    dialog3.show();
  }

  @FXML
  private void findnear(ActionEvent actionEvent) {
    JFXDialogLayout content5 = new JFXDialogLayout();
    content5.setHeading(new Text("How to find the nearest restrooms, elevators or stairs?"));
    content5.setBody(
        new Text(
            "1.\tGo to pathfinder page. \n" +
                    "2.\tSelect or search current location from drop down menu.\n" +
                    "3.\tYou can also click current location directly from the map. \n" +
                    "4.\tClick on the icon of restrooms, elevators or stairs.\n" +
                    "5.\tClick Find Path. \n" +
                    "6.\tClick Reset to find another shortest path.\n"));
    JFXDialog dialog5 = new JFXDialog(stackPane, content5, JFXDialog.DialogTransition.CENTER);
    JFXButton button5 = new JFXButton("Close");
    button5.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog5.close();
          }
        });
    content5.setActions(button5);
    dialog5.show();
  }

  @FXML
  private void servicerequest(ActionEvent actionEvent) {
    JFXDialogLayout content6 = new JFXDialogLayout();
    content6.setHeading(new Text("How to request a service?"));
    content6.setBody(
        new Text(
            "1.\tGo to Service Request page.\n" +
                    "2.\tSelect a service you need.\n" +
                    "3.\tEnter relevant information.\n" +
                    "4.\tClick Submit. The request is sent.\n"));
    JFXDialog dialog6 = new JFXDialog(stackPane, content6, JFXDialog.DialogTransition.CENTER);
    JFXButton button6 = new JFXButton("Close");
    button6.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog6.close();
          }
        });
    content6.setActions(button6);
    dialog6.show();
  }

  @FXML
  private void findatt(ActionEvent actionEvent) {
    JFXDialogLayout content7 = new JFXDialogLayout();
    content7.setHeading(new Text("How to find attractions or restaurants?"));
    content7.setBody(
        new Text(
            "1.\tGo to Tools page.\n" +
                    "2.\tClick on Find Attractions.\n" +
                    "3.\tEnter attractions or restaurants you want to search.\n" +
                    "4.\tSelect where you are (Main Campus or Faulkner Hospital).\n" +
                    "5.\tClick Search.\n" +
                    "6.\tClick on a search result.\n" +
                    "7.\tGoogle map jumps out to show you the way to get there. \n" +
                    "8.\tIf you enter your number, directions of text will be sent to mobile. \n"));
    JFXDialog dialog7 = new JFXDialog(stackPane, content7, JFXDialog.DialogTransition.CENTER);
    JFXButton button7 = new JFXButton("Close");
    button7.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog7.close();
          }
        });
    content7.setActions(button7);
    dialog7.show();
  }

  @FXML
  private void news(ActionEvent actionEvent) {
    JFXDialogLayout content8 = new JFXDialogLayout();
    content8.setHeading(new Text("How to find news articles?"));
    content8.setBody(
        new Text(
            "1.\tGo to Tools page.\n" +
                    "2.\tSelect a interested news article.\n" +
                    "3.\tClick on View Articles to read.\n" +
                    "4.\tThe articles can be sent to mobile if you want.\n" +
                    "5.\tNews can be searched.  \n"));
    JFXDialog dialog8 = new JFXDialog(stackPane, content8, JFXDialog.DialogTransition.CENTER);
    JFXButton button8 = new JFXButton("Close");
    button8.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog8.close();
          }
        });
    content8.setActions(button8);
    dialog8.show();
  }

  @FXML
  private void translate(ActionEvent actionEvent) {
    JFXDialogLayout content9 = new JFXDialogLayout();
    content9.setHeading(new Text("How to use a translator?"));
    content9.setBody(
        new Text(
            "1.\tGo to Tools page.\n" +
                    "2.\tSelect languages of translate from and translate to. \n" +
                    "3.\tEnter text to translate. Click on Translate.\n" +
                    "4.\tTranslated text can be sent to mobile.\n" +
                    "5.\tTranslated text can be called to mobile.\n"));
    JFXDialog dialog9 = new JFXDialog(stackPane, content9, JFXDialog.DialogTransition.CENTER);
    JFXButton button9 = new JFXButton("Close");
    button9.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog9.close();
          }
        });
    content9.setActions(button9);
    dialog9.show();
  }

  @FXML
  private void room(ActionEvent actionEvent) {
    JFXDialogLayout content10 = new JFXDialogLayout();
    content10.setHeading(new Text("How to request a room?"));
    content10.setBody(
        new Text(
            "1.\tGo to Service Request.\n" +
                    "2.\tClick on Room Scheduler.\n" +
                    "3.\tSelect Room Type and date.\n" +
                    "4.\tRight Click on table to add new events or change settings.\n" +
                    "5.\tRight Click on created event to set detailed information or delete it. \n"));
    JFXDialog dialog10 = new JFXDialog(stackPane, content10, JFXDialog.DialogTransition.CENTER);
    JFXButton button10 = new JFXButton("Close");
    button10.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog10.close();
          }
        });
    content10.setActions(button10);
    dialog10.show();
  }

  @FXML
  private void ship(ActionEvent actionEvent) {
    JFXDialogLayout content11 = new JFXDialogLayout();
    content11.setHeading(new Text("How to ship items?"));
    content11.setBody(
        new Text(
            "1.\tGo to Tools page.\n" +
                    "2.\tClick on Shipping.\n" +
                    "3.\tEnter relevant information for shipping.\n" +
                    "4.\tClick on Verify Address.\n" +
                    "5.\tVerified Address and Shopping Labels are generated.\n" +
                    "6.\tPrint or view shipping labels.\n" +
                    "7.\tTracking numbers can be sent to mobile. \n"));
    JFXDialog dialog11 = new JFXDialog(stackPane, content11, JFXDialog.DialogTransition.CENTER);
    JFXButton button11 = new JFXButton("Close");
    button11.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog11.close();
          }
        });
    content11.setActions(button11);
    dialog11.show();
  }

  @FXML
  private void map(ActionEvent actionEvent) {
    JFXDialogLayout content12 = new JFXDialogLayout();
    content12.setHeading(new Text("How to edit map?"));
    content12.setBody(
        new Text(
            "1.\tGo to Map Editor page.\n" +
                    "2.\tSelect on Add Node or Add Edge. \n" +
                    "3.\tEnter relevant information of node or edge that want to be added. \n" +
                    "4.\tClick on a present node or edge allows admin to modify or delete it.\n"));
    JFXDialog dialog12 = new JFXDialog(stackPane, content12, JFXDialog.DialogTransition.CENTER);
    JFXButton button12 = new JFXButton("Close");
    button12.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog12.close();
          }
        });
    content12.setActions(button12);
    dialog12.show();
  }

  @FXML
  private void account(ActionEvent actionEvent) {
    JFXDialogLayout content13 = new JFXDialogLayout();
    content13.setHeading(new Text("How to manage accounts?"));
    content13.setBody(
        new Text(
            "1.\tGo to Admin page.\n" +
                    "2.\tThe accounts are shown in the table.\n" +
                    "3.\tIf you want to change account type, for example, change a nurse to staff.\n" +
                    "4.\tDouble click on NURSE under type, enter STAFF. Press Return.\n" +
                    "5.\tClick on Update Staff.\n"));
    JFXDialog dialog13 = new JFXDialog(stackPane, content13, JFXDialog.DialogTransition.CENTER);
    JFXButton button13 = new JFXButton("Close");
    button13.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog13.close();
          }
        });
    content13.setActions(button13);
    dialog13.show();
  }

  @FXML
  private void specificsr(ActionEvent actionEvent) {
    JFXDialogLayout content14 = new JFXDialogLayout();
    content14.setHeading(new Text("How to keep track on specific service request?"));
    content14.setBody(
        new Text(
            "1.\tGo to Service Request page.\n" +
                    "2.\tClick on specific service request.\n" +
                    "3.\tThen click on Status.\n" +
                    "4.\tAssign people to a specific service request.\n" +
                    "5.\tMark completed if the request is solved.\n" +
                    "6.\tUpdate the changes.\n" +
                    "\n" +
                    "For advanced manage of maintenance, transport, sanitation service request:\n" +
                    "1.\tGo to Admin page. \n" +
                    "2.\tClick on Report View.\n" +
                    "3.\tEmployees, most common locations and completed request are shown in the chart.\n" +
                    "4.\tYou can export the chart to PDF or CSV.\n"));
    JFXDialog dialog14 = new JFXDialog(stackPane, content14, JFXDialog.DialogTransition.CENTER);
    JFXButton button14 = new JFXButton("Close");
    button14.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog14.close();
          }
        });
    content14.setActions(button14);
    dialog14.show();
  }

  @FXML
  private void backup(ActionEvent actionEvent) {
    JFXDialogLayout content15 = new JFXDialogLayout();
    content15.setHeading(new Text("Where can I backup data?"));
    content15.setBody(
        new Text(
            "1.\tGo to Admin page.\n" +
                    "2.\tClick on Backup All Data.\n"));
    JFXDialog dialog15 = new JFXDialog(stackPane, content15, JFXDialog.DialogTransition.CENTER);
    JFXButton button15 = new JFXButton("Close");
    button15.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog15.close();
          }
        });
    content15.setActions(button15);
    dialog15.show();
  }

  @FXML
  private void updatepa(ActionEvent actionEvent) {
    JFXDialogLayout content16 = new JFXDialogLayout();
    content16.setHeading(new Text("How to update pathfinder algorithm?"));
    content16.setBody(
        new Text(
            "1.\tGo to Admin page.\n" +
                    "2.\tSelect one algorithm (A-Star, DFS, BFS or Dijkstra’s) for pathfinder. \n"));
    JFXDialog dialog16 = new JFXDialog(stackPane, content16, JFXDialog.DialogTransition.CENTER);
    JFXButton button16 = new JFXButton("Close");
    button16.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog16.close();
          }
        });
    content16.setActions(button16);
    dialog16.show();
  }
}
