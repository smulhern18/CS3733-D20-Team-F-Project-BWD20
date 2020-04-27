package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javax.swing.text.html.ImageView;

public class FlowersController implements Initializable {

  @FXML private JFXTextField recipientInput;

  @FXML private JFXTextField buildingInput;

  @FXML private JFXTextField roomInput;

  @FXML private ComboBox<String> flowerComboBox;

  @FXML private JFXTextArea messageInput;

  private ImageView flowerImage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}
