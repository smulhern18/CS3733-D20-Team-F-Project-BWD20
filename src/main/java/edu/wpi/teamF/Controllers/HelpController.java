package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class HelpController implements Initializable {

  @FXML public AnchorPane rootPane;
  @FXML private StackPane stackPane;
  // @FXML
  // private JFXButton textfield;
  // @FXML
  // private JFXButton search;

  @Override
  public void initialize(URL url, ResourceBundle rb) {}

  @FXML
  private void loadDialogsignup(ActionEvent actionEvent) {
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
  private void loadDialoglogin(ActionEvent actionEvent) {
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
  private void loadDialogservicerequest(ActionEvent actionEvent) {
    JFXDialogLayout content3 = new JFXDialogLayout();
    content3.setHeading(new Text("How to request a service?"));
    content3.setBody(
        new Text(
            "Click on the 'Service Request' button on Main Menu. You will go to the Service\n"
                + "Request page. You could request services for security and maintenance. Enter\n"
                + "or select the information for location, description, priority, and type. Then click\n"
                + "on'Submit Request'. If you want to cancel the request, click on 'Cancel’.  If you\n"
                + "want to check service your request, click on 'Ongoing Service Request'."));
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
  private void loadDialogadmin(ActionEvent actionEvent) {
    JFXDialogLayout content5 = new JFXDialogLayout();
    content5.setHeading(new Text("How to add/edit/delete nodes?"));
    content5.setBody(
        new Text(
            "Click on the 'Admin' button on main menu.   The information about  nodes\n"
                + "and edges are shown in the table.  If you want to view it in a map, click on\n"
                + "'Map View'. Click again on 'Map View' brings you back to table view.  You\n"
                + "could quick search for node or edges by typing their ID in the text field. If\n"
                + "you want to upload nodes and edges,  click on 'Upload Nodes' or 'Upload\n"
                + "Edges'.  If you want to download nodes or edges,  click on 'Backup'.  You\n "
                + "could enter NodeID and click on 'Delete Node' to delete node."));
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
  private void loadDialogpath(ActionEvent actionEvent) {
    JFXDialogLayout content6 = new JFXDialogLayout();
    content6.setHeading(new Text("How to find a path?"));
    content6.setBody(
        new Text(
            "Click on the 'Pathfinder' button on Main Menu.  You will go to the Pathfinder \n"
                + "page. You could click on nodes’ circles on the map as start points.  Then s-\n"
                + "elect the destination. The shortest path will be shown on the map. After you\n"
                + "select start point and want to find stairs, elevators, and bathrooms, click on\n"
                + "'Find Stairs', 'Find Elevator' or 'Find Bathroom'.   Click on 'Reset' if you want\n"
                + "to find another path. "));
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
}
/*
  @FXML
  private void highlight(java.awt.event.ActionEvent event) {
    // highlight(textfield, search.getText());

  }

  class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
    public MyHighlightPainter(Color color) {
      super(color);
    }

    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.PINK);

    public void highlight(JTextComponent textComponent, String pattern) {
      try {
        Highlighter hilite = textComponent.getHighlighter();
        Document doc = textComponent.getDocument();
        String text = doc.getText(0, doc.getLength());
        int pos = 0;

        while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {
          hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
          pos += pattern.length();
        }

      } catch (Exception e) {
      }
    }
  }
}
 */
