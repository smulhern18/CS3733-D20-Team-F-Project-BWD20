package edu.wpi.teamF.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class ContactUsController {

  public WebView webView;
  public AnchorPane web;
  //  SceneController sceneController = App.getSceneController();
  public ImageView facebook;

  @FXML
  private void toFacebook(MouseEvent mouseEvent) throws IOException {
    webView.getEngine().load("https://www.facebook.com/BrighamandWomensHospital/");
    web.setVisible(true);

    // sceneController.switchScene("webView");
    //    primaryStage.setTitle("what");
    //    WebView w = new WebView();
    //    final WebEngine e = w.getEngine();
    //    e.load("https://www.facebook.com/BrighamandWomensHospital/");
    //    Scene scene = new Scene(w, w.getPrefWidth(), w.getPrefHeight());
    //    w.switchScene(scene);
    //    primaryStage.show();
  }
}
