package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import edu.wpi.teamF.Controllers.com.twilio.phoneComms;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import javax.swing.*;

public class NewsController implements Initializable {
  public JFXTextField searchTerm;
  public JFXButton searchBtn;
  public JFXTextArea resultsBox;
  public AnchorPane frame;
  public ImageView backgroundImage;
  public VBox articlesPane;
  public String currUrl;
  public String currTitle;
  public HBox actionsPane;
  public JFXTextField phoneNumber;
  public JFXButton viewArticle;
  public JFXButton sendText;
  public Label commsResult;
  public VBox viewPane;
  public WebView webview;

  public NewsApiClient newsApiClient;
  public Boolean success;
  private edu.wpi.teamF.Controllers.com.twilio.phoneComms phoneComms = new phoneComms();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    reset();
    setListeners();

    backgroundImage.setPreserveRatio(false);
    backgroundImage.fitHeightProperty().bind(frame.heightProperty());
    backgroundImage.fitWidthProperty().bind(frame.widthProperty());

    newsApiClient = new NewsApiClient("89cd565661a44c85bc4f2eda6cd4c90b");
  }

  public void reset() {
    searchTerm.setText("");
    searchBtn.setDisable(true);
    success = false;
    articlesPane.getChildren().clear();
    currUrl = "";
    currTitle = "";
    actionsPane.setDisable(true);
    sendText.setDisable(true);
    phoneNumber.setText("");
    commsResult.setText("");
    viewPane.setVisible(false);
  }

  public void setListeners() {
    searchTerm
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 0) {
                  searchBtn.setDisable(false);
                } else {
                  searchBtn.setDisable(true);
                }
              }
            });
    phoneNumber
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() == 10 && newValue.matches("[0-9]+")) {
                  sendText.setDisable(false);
                } else {
                  sendText.setDisable(true);
                }
              }
            });
  }

  public void searchBtn(ActionEvent actionEvent) {
    articlesPane.getChildren().clear();
    newsApiClient.getEverything(
        new EverythingRequest.Builder()
            .q(searchTerm.getText())
            .language("en")
            .sortBy("relevance")
            .pageSize(50)
            .build(),
        new NewsApiClient.ArticlesResponseCallback() {
          public ArticleResponse articles;

          @Override
          public void onSuccess(ArticleResponse response) {
            System.out.println("Successfully got news");
            Platform.runLater(
                new Runnable() {
                  @Override
                  public void run() {
                    setResponses(response);
                  }
                });
          }

          @Override
          public void onFailure(Throwable throwable) {
            System.out.println(throwable.getMessage());
            Platform.runLater(
                new Runnable() {
                  @Override
                  public void run() {
                    articlesFailed();
                  }
                });
          }
        });
  }

  public void setResponses(ArticleResponse articles) {
    if (articles.getTotalResults() == 0) {
      articlesFailed();
    } else {
      success = true;
      for (Article a : articles.getArticles()) {
        //        System.out.println(a.getTitle());
        //        System.out.println(a.getSource().getName());
        //        System.out.println(a.getDescription());
        //        System.out.println(a.getUrl());
        articlesPane
            .getChildren()
            .add(
                createDisplayObject(
                    a.getTitle(), a.getSource().getName(), a.getDescription(), a.getUrl()));
      }
    }
  }

  public StackPane createDisplayObject(
      String title, String source, String description, String url) {
    StackPane stackPane = new StackPane();
    stackPane.setPrefWidth(750);

    VBox vbox = new VBox();
    vbox.setPrefWidth(750);
    vbox.setMaxHeight(80);
    vbox.setPadding(new Insets(5, 10, 5, 10));
    vbox.setAlignment(Pos.CENTER_LEFT);
    vbox.setBorder(
        new Border(
            new BorderStroke(
                Color.web("rgba(255,255,255,0.9)"),
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(2))));
    vbox.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

    Label label1 = new Label();
    label1.setMinHeight(24);
    label1.setMaxWidth(720);
    label1.setFont(new Font("Arial", 16));
    label1.setStyle("-fx-font-weight: bold");
    label1.setText(title);
    vbox.getChildren().add(label1);

    Label label2 = new Label();
    label2.setMinHeight(16);
    label2.setMaxWidth(720);
    label2.setFont(new Font("Arial", 12));
    label2.setStyle("-fx-font-weight: bold");
    label2.setText(source);
    vbox.getChildren().add(label2);

    Label label3 = new Label();
    label3.setMinHeight(18);
    label3.setMaxHeight(18); // Cut off multiple lines
    label3.setMaxWidth(720);
    label3.setFont(new Font("Arial", 12));
    label3.setText("Select this story to read more...");
    if (description != null) {
      if (!description.contains("<")
          && !description.contains("{")
          && !description.contains("/")) { // Dealing with dumb HTML in the API response
        label3.setText(description);
      }
    }
    vbox.getChildren().add(label3);

    JFXButton btn = new JFXButton();
    btn.setPrefWidth(750);
    btn.setMinHeight(80);
    btn.setPrefHeight(vbox.getHeight());
    btn.setOnAction(actionEvent1 -> onSelect(url, title, vbox));

    stackPane.getChildren().add(vbox);
    stackPane.getChildren().add(btn);

    return stackPane;
  }

  public void onSelect(String url, String title, VBox vbox) {
    if (url.equals(currUrl)) {
      // Deselecting the current item
      vbox.setBackground(
          new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
      currUrl = "";
      currTitle = "";
      actionsPane.setDisable(true);
    } else {
      // Selecting the current item
      for (Node n : articlesPane.getChildren()) { // Setting all others to white
        StackPane s = (StackPane) n;
        VBox v = (VBox) s.getChildren().get(0);
        v.setBackground(
            new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
      }
      vbox.setBackground(
          new Background(
              new BackgroundFill(Color.web("#99d9ea"), CornerRadii.EMPTY, Insets.EMPTY)));
      currUrl = url;
      currTitle = title;
      actionsPane.setDisable(false);
    }
  }

  public void articlesFailed() {
    success = false;

    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);

    Label label1 = new Label();
    label1.setMinHeight(50);
    label1.setFont(new Font("Arial", 24));
    label1.setStyle("-fx-font-weight: bold");
    label1.setText("No Articles Found");

    hbox.getChildren().add(label1);
    articlesPane.getChildren().add(hbox);
  }

  public void resetBtn(ActionEvent actionEvent) {
    reset();
  }

  public void sendText(ActionEvent actionEvent) {
    String msgString =
        "The following news article was sent to you from the Brigham and Women's Hospital Information Kiosk: ";
    msgString += currTitle + ". To read more, visit ";
    msgString += currUrl;

    Boolean successSMS = phoneComms.sendMsg(phoneNumber.getText(), msgString);
    if (successSMS) {
      commsResult.setText("Success! You will get a text message momentarily.");
    } else {
      commsResult.setText("Couldn't send text. Check the number & try again.");
    }
  }

  public void viewArticle(ActionEvent actionEvent) {
    webview.getEngine().load(currUrl);
    viewPane.setVisible(true);
  }

  public void closeBtn(ActionEvent actionEvent) {
    viewPane.setVisible(false);
  }
}
