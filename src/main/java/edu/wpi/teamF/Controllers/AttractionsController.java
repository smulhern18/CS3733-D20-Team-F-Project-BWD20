package edu.wpi.teamF.Controllers;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class AttractionsController implements Initializable {
  public JFXTextField searchTerm;
  public JFXButton searchBtn;
  public JFXTextArea resultsBox;
  public AnchorPane frame;
  public ImageView backgroundImage;
  public HBox noResults;
  public Label name0;
  public Label address0;
  public Label open0;
  public Label stars0;
  public JFXButton btn0;
  public StackPane box0;
  public Label name1;
  public Label address1;
  public Label open1;
  public Label stars1;
  public JFXButton btn1;
  public StackPane box1;
  public Label name2;
  public Label address2;
  public Label open2;
  public Label stars2;
  public JFXButton btn2;
  public StackPane box2;
  public Label name3;
  public Label address3;
  public Label open3;
  public Label stars3;
  public JFXButton btn3;
  public StackPane box3;
  public Label name4;
  public Label address4;
  public Label open4;
  public Label stars4;
  public JFXButton btn4;
  public StackPane box4;
  public Label name5;
  public Label address5;
  public Label open5;
  public Label stars5;
  public JFXButton btn5;
  public StackPane box5;
  public Label name6;
  public Label address6;
  public Label open6;
  public Label stars6;
  public JFXButton btn6;
  public StackPane box6;
  public Label name7;
  public Label address7;
  public Label open7;
  public Label stars7;
  public JFXButton btn7;
  public StackPane box7;
  public Label name8;
  public Label address8;
  public Label open8;
  public Label stars8;
  public JFXButton btn8;
  public StackPane box8;
  public Label name9;
  public Label address9;
  public Label open9;
  public Label stars9;
  public JFXButton btn9;
  public StackPane box9;

  public DecimalFormat decimalFormat = new DecimalFormat("#.0");

  public PlacesSearchResult[] results;
  public JFXComboBox currentLocation;
  private GeoApiContext context =
      new GeoApiContext.Builder().apiKey("AIzaSyB61pjpz4PvzIKYCsYiwHoWQctXiw9soHc").build();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    reset();

    currentLocation.getItems().addAll("Faulkner Hospital", "Main Campus");

    backgroundImage.setPreserveRatio(false);
    backgroundImage.fitHeightProperty().bind(frame.heightProperty());
    backgroundImage.fitWidthProperty().bind(frame.widthProperty());

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
  }

  public void reset() {
    searchTerm.setText("");
    searchBtn.setDisable(true);
    noResults.setVisible(false);
    noResults.setPrefHeight(0);
    box0.setVisible(false);
    box0.setPrefHeight(0);
    box1.setVisible(false);
    box1.setPrefHeight(0);
    box2.setVisible(false);
    box2.setPrefHeight(0);
    box3.setVisible(false);
    box3.setPrefHeight(0);
    box4.setVisible(false);
    box4.setPrefHeight(0);
    box5.setVisible(false);
    box5.setPrefHeight(0);
    box6.setVisible(false);
    box6.setPrefHeight(0);
    box7.setVisible(false);
    box7.setPrefHeight(0);
    box8.setVisible(false);
    box8.setPrefHeight(0);
    box9.setVisible(false);
    box9.setPrefHeight(0);
  }

  public void searchBtn(ActionEvent actionEvent)
      throws InterruptedException, ApiException, IOException {
    // TODO Figure out what Google's ranking order is and if it can be better
    PlacesSearchResponse response =
        PlacesApi.textSearchQuery(context, searchTerm.getText())
            .location(new LatLng(42.301681, -71.129039))
            .radius(10000)
            .rankby(RankBy.PROMINENCE)
            .await();
    results = response.results;

    if (results.length == 0) {
      noResults.setVisible(true);
      noResults.setPrefHeight(50);
    }
    if (results.length > 0) {
      box0.setPrefHeight(75);
      name0.setText(results[0].name);
      address0.setText(results[0].formattedAddress);
      if (results[0].rating > 0.0) {
        stars0.setText(decimalFormat.format(results[0].rating));
      } else {
        stars0.setText("N/A");
      }
      try {
        if (results[0].openingHours.openNow) {
          open0.setText("Currently Open");
          open0.setTextFill(Color.web("#008000"));
        } else {
          open0.setText("Currently Closed");
          open0.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open0.setText("Opening Hours Unavailable");
        open0.setTextFill(Color.web("#FFFFFF"));
      }
      box0.setVisible(true);
    }
    if (results.length > 1) {
      box1.setPrefHeight(75);
      name1.setText(results[1].name);
      address1.setText(results[1].formattedAddress);
      if (results[1].rating > 0.0) {
        stars1.setText(decimalFormat.format(results[1].rating));
      } else {
        stars1.setText("N/A");
      }
      try {
        if (results[1].openingHours.openNow) {
          open1.setText("Currently Open");
          open1.setTextFill(Color.web("#008000"));
        } else {
          open1.setText("Currently Closed");
          open1.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open1.setText("Opening Hours Unavailable");
        open1.setTextFill(Color.web("#FFFFFF"));
      }
      box1.setVisible(true);
    }
    if (results.length > 2) {
      box2.setPrefHeight(75);
      name2.setText(results[2].name);
      address2.setText(results[2].formattedAddress);
      if (results[2].rating > 0.0) {
        stars2.setText(decimalFormat.format(results[2].rating));
      } else {
        stars2.setText("N/A");
      }
      try {
        if (results[2].openingHours.openNow) {
          open2.setText("Currently Open");
          open2.setTextFill(Color.web("#008000"));
        } else {
          open2.setText("Currently Closed");
          open2.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open2.setText("Opening Hours Unavailable");
        open2.setTextFill(Color.web("#FFFFFF"));
      }
      box2.setVisible(true);
    }
    if (results.length > 3) {
      box3.setPrefHeight(75);
      name3.setText(results[3].name);
      address3.setText(results[3].formattedAddress);
      if (results[3].rating > 0.0) {
        stars3.setText(decimalFormat.format(results[3].rating));
      } else {
        stars3.setText("N/A");
      }
      try {
        if (results[3].openingHours.openNow) {
          open3.setText("Currently Open");
          open3.setTextFill(Color.web("#008000"));
        } else {
          open3.setText("Currently Closed");
          open3.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open3.setText("Opening Hours Unavailable");
        open3.setTextFill(Color.web("#FFFFFF"));
      }
      box3.setVisible(true);
    }
    if (results.length > 4) {
      box4.setPrefHeight(75);
      name4.setText(results[4].name);
      address4.setText(results[4].formattedAddress);
      if (results[4].rating > 0.0) {
        stars4.setText(decimalFormat.format(results[4].rating));
      } else {
        stars4.setText("N/A");
      }
      try {
        if (results[4].openingHours.openNow) {
          open4.setText("Currently Open");
          open4.setTextFill(Color.web("#008000"));
        } else {
          open4.setText("Currently Closed");
          open4.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open4.setText("Opening Hours Unavailable");
        open4.setTextFill(Color.web("#FFFFFF"));
      }
      box4.setVisible(true);
    }
    if (results.length > 5) {
      box5.setPrefHeight(75);
      name5.setText(results[5].name);
      address5.setText(results[5].formattedAddress);
      if (results[5].rating > 0.0) {
        stars5.setText(decimalFormat.format(results[5].rating));
      } else {
        stars5.setText("N/A");
      }
      try {
        if (results[5].openingHours.openNow) {
          open5.setText("Currently Open");
          open5.setTextFill(Color.web("#008000"));
        } else {
          open5.setText("Currently Closed");
          open5.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open5.setText("Opening Hours Unavailable");
        open5.setTextFill(Color.web("#FFFFFF"));
      }
      box5.setVisible(true);
    }
    if (results.length > 6) {
      box6.setPrefHeight(75);
      name6.setText(results[6].name);
      address6.setText(results[6].formattedAddress);
      if (results[6].rating > 0.0) {
        stars6.setText(decimalFormat.format(results[6].rating));
      } else {
        stars6.setText("N/A");
      }
      try {
        if (results[6].openingHours.openNow) {
          open6.setText("Currently Open");
          open6.setTextFill(Color.web("#008000"));
        } else {
          open6.setText("Currently Closed");
          open6.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open6.setText("Opening Hours Unavailable");
        open6.setTextFill(Color.web("#FFFFFF"));
      }
      box6.setVisible(true);
    }
    if (results.length > 7) {
      box7.setPrefHeight(75);
      name7.setText(results[7].name);
      address7.setText(results[7].formattedAddress);
      if (results[7].rating > 0.0) {
        stars7.setText(decimalFormat.format(results[7].rating));
      } else {
        stars7.setText("N/A");
      }
      try {
        if (results[7].openingHours.openNow) {
          open7.setText("Currently Open");
          open7.setTextFill(Color.web("#008000"));
        } else {
          open7.setText("Currently Closed");
          open7.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open7.setText("Opening Hours Unavailable");
        open7.setTextFill(Color.web("#FFFFFF"));
      }
      box7.setVisible(true);
    }
    if (results.length > 8) {
      box8.setPrefHeight(75);
      name8.setText(results[8].name);
      address8.setText(results[8].formattedAddress);
      if (results[8].rating > 0.0) {
        stars8.setText(decimalFormat.format(results[8].rating));
      } else {
        stars8.setText("N/A");
      }
      try {
        if (results[8].openingHours.openNow) {
          open8.setText("Currently Open");
          open8.setTextFill(Color.web("#008000"));
        } else {
          open8.setText("Currently Closed");
          open8.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open8.setText("Opening Hours Unavailable");
        open8.setTextFill(Color.web("#FFFFFF"));
      }
      box8.setVisible(true);
    }
    if (results.length > 9) {
      box9.setPrefHeight(75);
      name9.setText(results[9].name);
      address9.setText(results[9].formattedAddress);
      if (results[9].rating > 0.0) {
        stars9.setText(decimalFormat.format(results[9].rating));
      } else {
        stars9.setText("N/A");
      }
      try {
        if (results[9].openingHours.openNow) {
          open9.setText("Currently Open");
          open9.setTextFill(Color.web("#008000"));
        } else {
          open9.setText("Currently Closed");
          open9.setTextFill(Color.web("#990000"));
        }
      } catch (Exception e) {
        open9.setText("Opening Hours Unavailable");
        open9.setTextFill(Color.web("#FFFFFF"));
      }
      box9.setVisible(true);
    }
  }

  public void openDirections(int index) {}

  public void btn0(ActionEvent actionEvent) {
    openDirections(0);
  }

  public void btn1(ActionEvent actionEvent) {
    openDirections(1);
  }

  public void btn2(ActionEvent actionEvent) {
    openDirections(2);
  }

  public void btn3(ActionEvent actionEvent) {
    openDirections(3);
  }

  public void btn4(ActionEvent actionEvent) {
    openDirections(4);
  }

  public void btn5(ActionEvent actionEvent) {
    openDirections(5);
  }

  public void btn6(ActionEvent actionEvent) {
    openDirections(6);
  }

  public void btn7(ActionEvent actionEvent) {
    openDirections(7);
  }

  public void btn8(ActionEvent actionEvent) {
    openDirections(8);
  }

  public void btn9(ActionEvent actionEvent) {
    openDirections(9);
  }

  public void resetBtn(ActionEvent actionEvent) {
    reset();
  }
}
