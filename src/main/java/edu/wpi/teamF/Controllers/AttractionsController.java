package edu.wpi.teamF.Controllers;

import com.google.cloud.translate.v3.*;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AttractionsController implements Initializable {
  public JFXTextField searchTerm;
  public JFXButton searchBtn;
  public JFXTextArea resultsBox;
  public AnchorPane frame;
  public ImageView backgroundImage;

  //  GooglePlaces places = new GooglePlaces("AIzaSyB61pjpz4PvzIKYCsYiwHoWQctXiw9soHc");
  private GeoApiContext context =
      new GeoApiContext.Builder().apiKey("AIzaSyB61pjpz4PvzIKYCsYiwHoWQctXiw9soHc").build();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    reset();
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
  }

  public void searchBtn(ActionEvent actionEvent)
      throws InterruptedException, ApiException, IOException {
    //      FindPlaceFromText result =
    //          PlacesApi.findPlaceFromText(
    //                  context,
    //                  "Italian Food near Oakville, ON",
    //                  FindPlaceFromTextRequest.InputType.TEXT_QUERY)
    //                            .locationBias(
    //                                new FindPlaceFromTextRequest.LocationBiasCircular(
    //                                    new LatLng(42.3016451, -71.1309705), 10))
    //              .await();
    PlacesSearchResponse response =
        PlacesApi.nearbySearchQuery(context, new LatLng(42.3016451, -71.1309705))
            .keyword(searchTerm.getText())
            .rankby(RankBy.DISTANCE)
            .await();
    PlacesSearchResult[] results = response.results;
    String resultsString = "";
    for (int i = 0; i < results.length; i++) {
      resultsString += results[i].name + "\n";
    }
    resultsBox.setText(resultsString);

    //    List<Place> result = places.getNearbyPlaces(42.3016451, -71.1309705, 10000, 10);
    //    for (Place place : result) {
    //      System.out.println(place.getName());
    //    }
  }
}
