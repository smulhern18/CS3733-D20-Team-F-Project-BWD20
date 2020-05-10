package edu.wpi.teamF.Controllers;

import com.easypost.EasyPost;
import com.easypost.exception.EasyPostException;
import com.easypost.model.Address;
import com.easypost.model.Parcel;
import com.easypost.model.Rate;
import com.easypost.model.Shipment;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

class sortRates implements Comparator<Rate> {
  @Override
  public int compare(Rate o1, Rate o2) {
    return (int) ((o1.getRate() - o2.getRate()) * 100);
  }
}

public class ShippingController implements Initializable {
  public JFXTextField name;
  public JFXTextField company;
  public JFXTextField phone;
  public JFXTextField address1;
  public JFXTextField address2;
  public JFXTextField city;
  public JFXTextField state;
  public JFXTextField zip;
  public JFXTextField length;
  public JFXTextField width;
  public JFXTextField height;
  public JFXTextField weight;
  public JFXButton verifyAndRates;
  public AnchorPane frame;
  public ImageView backgroundImage;
  public JFXTextArea verifiedAddress;

  public Address fromAddress;
  public DecimalFormat decimalFormat = new DecimalFormat("#.00");
  public List<Rate> ratesList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    resetPane();
    setListeners();

    backgroundImage.setPreserveRatio(false);
    backgroundImage.fitHeightProperty().bind(frame.heightProperty());
    backgroundImage.fitWidthProperty().bind(frame.widthProperty());

    try {
      EasyPost.apiKey = "e77gV9oWxX9JNkMt2EIdEQ"; // Test keys for Robosource
      Map<String, Object> fromAddressMap = new HashMap<String, Object>();
      fromAddressMap.put("company", "Brigham and Women's Hospital");
      fromAddressMap.put("street1", "75 Francis St");
      fromAddressMap.put("street2", "Shipping Dept.");
      fromAddressMap.put("city", "Boston");
      fromAddressMap.put("state", "MA");
      fromAddressMap.put("zip", "02115");
      fromAddressMap.put("phone", "000-000-0000");
      fromAddressMap.put("country", "US");
      List<String> verificationList = new ArrayList<>();
      verificationList.add("delivery");
      fromAddressMap.put("verify_strict", verificationList);
      fromAddress = Address.create(fromAddressMap);
    } catch (EasyPostException e) {
      System.out.println("Error on From Address creation");
    }
  }

  public void verifyAndRates(ActionEvent actionEvent) {
    try {
      // TODO: Do we actually want to verify strict? Seems to act more tightly than in the Python
      // app
      Map<String, Object> toAddressMap = new HashMap<String, Object>();
      toAddressMap.put("name", name.getText());
      toAddressMap.put("company", company.getText());
      toAddressMap.put("street1", address1.getText());
      toAddressMap.put("street2", address2.getText());
      toAddressMap.put("city", city.getText());
      toAddressMap.put("state", state.getText());
      toAddressMap.put("zip", zip.getText());
      toAddressMap.put("phone", phone.getText());
      toAddressMap.put("country", "US");
      List<String> verificationList = new ArrayList<>();
      verificationList.add("delivery");
      toAddressMap.put("verify_strict", verificationList);
      Address toAddress = Address.create(toAddressMap);

      Map<String, Object> parcelMap = new HashMap<String, Object>();
      parcelMap.put("height", Float.parseFloat(height.getText()));
      parcelMap.put("width", Float.parseFloat(width.getText()));
      parcelMap.put("length", Float.parseFloat(length.getText()));
      parcelMap.put("weight", Float.parseFloat(weight.getText()) * 16.0);
      Parcel parcel = Parcel.create(parcelMap);

      String verifiedAddressString = toAddress.getName() + "\n";
      if (toAddress.getCompany() != null) {
        verifiedAddressString += toAddress.getCompany() + "\n";
      }
      verifiedAddressString += toAddress.getStreet1() + "\n";
      if (toAddress.getStreet2() != null) {
        if (toAddress.getStreet2().length() > 0) {
          verifiedAddressString += toAddress.getStreet2() + "\n";
        }
      }
      verifiedAddressString +=
          toAddress.getCity()
              + ", "
              + toAddress.getState()
              + ", "
              + toAddress.getZip()
              + ", "
              + toAddress.getCountry();
      verifiedAddress.setText(verifiedAddressString);

      // Now create the shipment (verification must have succeeded if we get this far
      Map<String, Object> shipmentMap = new HashMap<String, Object>();
      shipmentMap.put("to_address", toAddress);
      shipmentMap.put("from_address", fromAddress);
      shipmentMap.put("parcel", parcel);
      Shipment shipment = Shipment.create(shipmentMap);

      ratesList = shipment.getRates();
      Collections.sort(ratesList, new sortRates());
      for (Rate rate : ratesList) {
        System.out.println(
            rate.getCarrier()
                + " "
                + rate.getService()
                + " - $"
                + decimalFormat.format(rate.getRate()));
      }
      // Set up all of the buttons
    } catch (EasyPostException e) {
      verifiedAddress.setText(
          "Could not verify address and fetch shipping rates. Please check your inputs and try again.");
      System.out.println("Error on Address Verification and parcel creation");
    }
  }

  public void reset(ActionEvent actionEvent) {
    resetPane();
  }

  public void resetPane() {
    name.setText("");
    company.setText("");
    address1.setText("");
    address2.setText("");
    city.setText("");
    state.setText("");
    zip.setText("");
    length.setText("");
    width.setText("");
    height.setText("");
    weight.setText("");
    enableVerifyAndRatesBtn();
    verifiedAddress.setText("");
  }

  public void setListeners() {
    name.textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
    address1
        .textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
    city.textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
    state
        .textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
    zip.textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
    length
        .textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
    width
        .textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
    height
        .textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
    weight
        .textProperty()
        .addListener(
            new ChangeListener<>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableVerifyAndRatesBtn();
              }
            });
  }

  public void enableVerifyAndRatesBtn() {
    if (name.getText().length() > 0
        && address1.getText().length() > 0
        && city.getText().length() > 0
        && state.getText().length() > 1
        && zip.getText().length() > 4
        && length.getText().length() > 0
        && width.getText().length() > 0
        && height.getText().length() > 0
        && weight.getText().length() > 0) {
      try {
        Float.parseFloat(length.getText());
        Float.parseFloat(width.getText());
        Float.parseFloat(height.getText());
        Float.parseFloat(weight.getText());
        verifyAndRates.setDisable(false);
      } catch (NumberFormatException e) {
        verifyAndRates.setDisable(true);
      }
    } else {
      verifyAndRates.setDisable(true);
    }
  }
}
