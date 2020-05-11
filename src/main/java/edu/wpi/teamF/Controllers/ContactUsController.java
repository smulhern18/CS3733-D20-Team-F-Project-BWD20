package edu.wpi.teamF.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class ContactUsController implements Initializable {

  public WebView webView;
  public AnchorPane web;
  public ImageView facebook;
  public ImageView linked;
  public ImageView instagram;
  public ImageView youtube;
  public ImageView twitter;
  public ImageView blog;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  private void toFacebook(MouseEvent mouseEvent) throws IOException {
    webView.getEngine().load("https://www.facebook.com/BrighamandWomensHospital/");
    web.setVisible(true);
  }

  public void toLinkedIn(MouseEvent mouseEvent) throws IOException {
    webView.getEngine().load("https://www.linkedin.com/company/brigham-and-women%27s-hospital");
    web.setVisible(true);
  }

  public void toInstagram(MouseEvent mouseEvent) {
    webView.getEngine().load("https://www.instagram.com/brighamandwomens/?hl=en");
    web.setVisible(true);
  }

  public void toYoutube(MouseEvent mouseEvent) {
    webView.getEngine().load("https://www.youtube.com/user/Brighamandwomens");
    web.setVisible(true);
  }

  public void toTwitter(MouseEvent mouseEvent) {
    webView.getEngine().load("https://twitter.com/BrighamWomens");
    web.setVisible(true);
  }

  public void toBlog(MouseEvent mouseEvent) {
    webView.getEngine().load("https://brighamhealthhub.org/");
    web.setVisible(true);
  }
}
