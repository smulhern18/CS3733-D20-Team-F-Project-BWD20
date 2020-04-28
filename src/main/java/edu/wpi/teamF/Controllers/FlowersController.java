package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import lombok.Data;

import javax.swing.text.html.ImageView;

@Data
public class FlowersController implements Initializable {



  public FlowersController flowersController;

  @FXML private JFXTextField recipientInput;

  @FXML private JFXTextField buildingInput;

  @FXML private JFXTextField roomInput;

  @FXML private ComboBox<String> flowerComboBox;

  @FXML private JFXTextArea messageInput;

  private ImageView flowerImage;

  public String flowerPicked;
  public FlowersController(String flowerPicked){
    this.flowerPicked = flowerPicked;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  public void selectOccasion(MouseEvent mouseEvent) {
    flowerPicked = "All Occasion";
    FlowersController flowersController = new FlowersController(flowerPicked);
  }


}
