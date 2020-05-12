package edu.wpi.teamF.Controllers;

import com.easypost.EasyPost;
import com.easypost.exception.EasyPostException;
import com.easypost.model.Address;
import com.easypost.model.Parcel;
import com.easypost.model.Rate;
import com.easypost.model.Shipment;
import com.jfoenix.controls.*;
import edu.wpi.teamF.Controllers.com.twilio.phoneComms;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

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
  public VBox optionsPane;
  public VBox labelBtns;
  public Label errorMsg;
  public Label commsResult;
  public JFXButton sendText;
  public JFXTextField phoneNumber;
  public VBox viewLabelPane;
  public WebView webview;
  public JFXComboBox predefinedPackages;

  public Address fromAddress;
  public DecimalFormat decimalFormat = new DecimalFormat("#.00");
  public List<Rate> ratesList;
  public Shipment shipment;
  private edu.wpi.teamF.Controllers.com.twilio.phoneComms phoneComms = new phoneComms();
  public Map<String, String> predefinedPackagesMap;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    resetPane();
    setListeners();
    setPredefinedPackages();

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
      toAddressMap.put("verify", verificationList);
      Address toAddress = Address.create(toAddressMap);

      Map<String, Object> parcelMap = new HashMap<String, Object>();
      if (!"Parcel".equals(predefinedPackages.getValue())) {
        parcelMap.put(
            "predefined_package", predefinedPackagesMap.get(predefinedPackages.getValue()));
      } else {
        parcelMap.put("height", Float.parseFloat(height.getText()));
        parcelMap.put("width", Float.parseFloat(width.getText()));
        parcelMap.put("length", Float.parseFloat(length.getText()));
      }
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

      // Now create the shipment (verification must have succeeded if we get this far)
      Map<String, Object> shipmentMap = new HashMap<String, Object>();
      shipmentMap.put("to_address", toAddress);
      shipmentMap.put("from_address", fromAddress);
      shipmentMap.put("parcel", parcel);
      shipment = Shipment.create(shipmentMap);

      ratesList = shipment.getRates();
      Collections.sort(ratesList, new sortRates());
      optionsPane.getChildren().clear();
      optionsPane.setDisable(false);

      for (int i = 0; i < ratesList.size(); i++) {
        Rate rate = ratesList.get(i);
        String rateString =
            rate.getCarrier()
                + " "
                + rate.getService()
                + " - $"
                + decimalFormat.format(rate.getRate());
        System.out.println(rateString);
        JFXButton rateBtn = new JFXButton();
        rateBtn.setPrefHeight(30);
        rateBtn.setPrefWidth(300);
        rateBtn.setText(rateString);
        rateBtn.setAlignment(Pos.CENTER_LEFT);
        int finalI = i;
        rateBtn.setOnAction(actionEvent1 -> buyShipping(finalI));
        optionsPane.getChildren().add(rateBtn);
      }
    } catch (EasyPostException e) {
      verifiedAddress.setText(
          "Could not verify address and fetch shipping rates. Please check your inputs and try again.");
      System.out.println("Error on Address Verification and parcel creation");
    }
    labelBtns.setDisable(true);
    errorMsg.setText("");
  }

  public void buyShipping(int index) {
    optionsPane.getChildren().get(index).setStyle("-fx-background-color: #99d9ea;");
    optionsPane.setDisable(true);
    try {
      Map<String, Object> buyMap = new HashMap<String, Object>();
      buyMap.put("rate", ratesList.get(index));
      shipment.buy(buyMap);
      System.out.println(shipment.getPostageLabel().getLabelUrl());
      // Successfully bought label
      labelBtns.setDisable(false);
    } catch (EasyPostException e) {
      System.out.println("Error purchasing label");
      System.out.println(e);
      optionsPane.setDisable(true);
      errorMsg.setText(
          "There was an error purchasing the label. Please check the details and try again.");
    }
  }

  public void reset(ActionEvent actionEvent) {
    resetPane();
  }

  public void resetPane() {
    name.setText("");
    company.setText("");
    phone.setText("");
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
    optionsPane.getChildren().clear();
    optionsPane.setDisable(true);
    labelBtns.setDisable(true);
    errorMsg.setText("");
    commsResult.setText("");
    viewLabelPane.setVisible(false);
    predefinedPackages.setValue("Parcel");
    length.setDisable(false);
    width.setDisable(false);
    height.setDisable(false);
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
    phone
        .textProperty()
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

    sendText.setDisable(true);
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

  public void setPredefinedPackages() {
    predefinedPackagesMap = new HashMap<>();
    predefinedPackagesMap.put("Parcel", "Parcel");
    predefinedPackagesMap.put("Letter", "Letter");
    predefinedPackagesMap.put("FedEx Envelope", "FedExEnvelope");
    predefinedPackagesMap.put("FedEx Pak", "FedExPak");
    predefinedPackages.getItems().addAll(predefinedPackagesMap.keySet());
  }

  public void enableVerifyAndRatesBtn() {
    if (name.getText().length() > 0
        && phone.getText().length() > 9
        && address1.getText().length() > 0
        && city.getText().length() > 0
        && state.getText().length() > 1
        && zip.getText().length() > 4
        && ((length.getText().length() > 0
                && width.getText().length() > 0
                && height.getText().length() > 0)
            || !"Parcel".equals(predefinedPackages.getValue()))
        && weight.getText().length() > 0) {
      try {
        if ("Parcel".equals(predefinedPackages.getValue())) {
          Float.parseFloat(length.getText());
          Float.parseFloat(width.getText());
          Float.parseFloat(height.getText());
          Float.parseFloat(weight.getText());
        }
        verifyAndRates.setDisable(false);
      } catch (NumberFormatException e) {
        verifyAndRates.setDisable(true);
      }
    } else {
      verifyAndRates.setDisable(true);
    }
  }

  public void printLabel(ActionEvent actionEvent) {
    try {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("file_format", "EPL2");
      shipment.label(params);
    } catch (Exception e) {
      errorMsg.setText("Error converting to label format.");
    }

    System.out.println("EPL2 Label: " + shipment.getPostageLabel().getLabelEpl2Url());
    try (BufferedInputStream in =
            new BufferedInputStream(
                new URL(shipment.getPostageLabel().getLabelEpl2Url()).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream("lpt1")) {
      byte dataBuffer[] = new byte[1024];
      int bytesRead;
      while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
        fileOutputStream.write(dataBuffer, 0, bytesRead);
      }
      errorMsg.setText("");
    } catch (Exception e) {
      System.out.println("Error Printing");
      System.out.println(e);
      errorMsg.setText("Error printing label.");
    }
  }

  public void viewLabel(ActionEvent actionEvent) {
    viewLabelPane.setVisible(true);
    webview
        .getEngine()
        .loadContent(
            "<img src=\"" + shipment.getPostageLabel().getLabelUrl() + "\" width=\"480\">");
  }

  public void sendText(ActionEvent actionEvent) {
    String msgString =
        "The tracking number for your shipment to " + shipment.getToAddress().getName();
    if (shipment.getToAddress().getCompany() != null) {
      msgString += " at " + shipment.getToAddress().getCompany();
    }
    msgString += " is " + shipment.getTrackingCode() + ". ";
    msgString += "To track your shipment, go to: " + shipment.getTracker().getPublicUrl();

    Boolean success = phoneComms.sendMsg(phoneNumber.getText(), msgString);
    if (success) {
      commsResult.setText("Success! You will get a text message momentarily.");
    } else {
      commsResult.setText("Couldn't send text. Please check the number & try again.");
    }
  }

  public void closeViewLabel(ActionEvent actionEvent) {
    viewLabelPane.setVisible(false);
  }

  public void changePredefinedPackages(ActionEvent actionEvent) {
    if ("Parcel".equals(predefinedPackages.getValue())) {
      length.setDisable(false);
      width.setDisable(false);
      height.setDisable(false);
    } else {
      length.setDisable(true);
      width.setDisable(true);
      height.setDisable(true);
    }
    enableVerifyAndRatesBtn();
  }
}
