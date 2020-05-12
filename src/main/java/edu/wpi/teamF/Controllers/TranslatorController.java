package edu.wpi.teamF.Controllers;

import com.google.cloud.translate.v3.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.twilio.twiml.voice.Say;
import edu.wpi.teamF.Controllers.com.twilio.phoneComms;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TranslatorController implements Initializable {
  public JFXComboBox<String> fromLanguage;
  public JFXComboBox<String> toLanguage;
  public JFXButton translate;
  public JFXTextArea textInput;
  public JFXTextArea textOutput;
  public JFXTextField phoneNumber;
  public JFXButton sendSMS;
  public JFXButton callPhone;
  public Map<String, String> langCodeMap;
  public Map<String, String> langVoicesMap;
  public AnchorPane frame;
  public ImageView backgroundImage;
  public Label commResult;

  private phoneComms phoneComms = new phoneComms();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    langCodeMap = new HashMap<>();
    langCodeMap.put("Swedish", "sv");
    langCodeMap.put("Spanish", "es");
    langCodeMap.put("Russian", "ru");
    langCodeMap.put("Portuguese", "pt");
    langCodeMap.put("Polish", "pl");
    langCodeMap.put("Norwegian", "no");
    langCodeMap.put("Korean", "ko");
    langCodeMap.put("Japanese", "ja");
    langCodeMap.put("Italian", "it");
    langCodeMap.put("German", "de");
    langCodeMap.put("French", "fr");
    langCodeMap.put("Finnish", "fi");
    langCodeMap.put("English", "en");
    langCodeMap.put("Dutch", "nl");
    langCodeMap.put("Danish", "da");
    langCodeMap.put("Chinese (Mandarin)", "zh");
    langCodeMap.put("Chinese (Cantonese)", "zh-TW");

    langVoicesMap = new HashMap<>();
    langVoicesMap.put("English", Say.Language.EN_US.toString());
    langVoicesMap.put("Spanish", Say.Language.ES_MX.toString());
    langVoicesMap.put("French", Say.Language.FR_CA.toString());
    langVoicesMap.put("German", Say.Language.DE_DE.toString());
    langVoicesMap.put("Italian", Say.Language.IT_IT.toString());
    langVoicesMap.put("Danish", Say.Language.DA_DK.toString());
    langVoicesMap.put("Finnish", Say.Language.FI_FI.toString());
    langVoicesMap.put("Japanese", Say.Language.JA_JP.toString());
    langVoicesMap.put("Korean", Say.Language.KO_KR.toString());
    langVoicesMap.put("Norwegian", Say.Language.NB_NO.toString());
    langVoicesMap.put("Dutch", Say.Language.NL_NL.toString());
    langVoicesMap.put("Polish", Say.Language.PL_PL.toString());
    langVoicesMap.put("Portuguese", Say.Language.PT_PT.toString());
    langVoicesMap.put("Russian", Say.Language.RU_RU.toString());
    langVoicesMap.put("Swedish", Say.Language.SV_SE.toString());
    langVoicesMap.put("Chinese (Mandarin)", Say.Language.ZH_CN.toString());
    langVoicesMap.put("Chinese (Cantonese)", Say.Language.ZH_HK.toString());

    fromLanguage.getItems().addAll(langCodeMap.keySet());
    toLanguage.getItems().addAll(langCodeMap.keySet());
    fromLanguage.setValue("English");
    toLanguage.setValue("Spanish");

    resetTranslator();
    backgroundImage.setPreserveRatio(false);
    backgroundImage.fitHeightProperty().bind(frame.heightProperty());
    backgroundImage.fitWidthProperty().bind(frame.widthProperty());

    callPhone.setDisable(true);
    sendSMS.setDisable(true);
    phoneNumber
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() == 10
                    && newValue.matches("[0-9]+")
                    && textOutput.getText().length() > 0) {
                  callPhone.setDisable(false);
                  sendSMS.setDisable(false);
                } else {
                  callPhone.setDisable(true);
                  sendSMS.setDisable(true);
                }
              }
            });

    translate.setDisable(true);
    textInput
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 0) {
                  translate.setDisable(false);
                } else {
                  translate.setDisable(true);
                }
              }
            });
  }

  public static String translateTextWithModel(
      String sourceLanguage, String targetLanguage, String text) throws IOException {
    String projectId = "neural-vista-276017";
    String modelId = "general/nmt";

    try (TranslationServiceClient client = TranslationServiceClient.create()) {
      String location = "us-central1";
      LocationName parent = LocationName.of(projectId, location);
      String modelPath =
          String.format("projects/%s/locations/%s/models/%s", projectId, location, modelId);

      TranslateTextRequest request =
          TranslateTextRequest.newBuilder()
              .setParent(parent.toString())
              .setMimeType("text/plain")
              .setSourceLanguageCode(sourceLanguage)
              .setTargetLanguageCode(targetLanguage)
              .addContents(text)
              .setModel(modelPath)
              .build();

      TranslateTextResponse response = client.translateText(request);

      String translationResult = "";
      for (Translation translation : response.getTranslationsList()) {
        translationResult += (translation.getTranslatedText() + "\n");
      }
      return translationResult;
    }
  }

  public void translate(ActionEvent actionEvent) throws IOException {
    if (phoneNumber.getText().length() == 10 && phoneNumber.getText().matches("[0-9]+")) {
      sendSMS.setDisable(false);
      callPhone.setDisable(false);
    }

    textOutput.setText(
        translateTextWithModel(
            langCodeMap.get(fromLanguage.getValue()),
            langCodeMap.get(toLanguage.getValue()),
            textInput.getText()));
  }

  public void reset(ActionEvent actionEvent) {
    resetTranslator();
  }

  public void resetTranslator() {
    textInput.setText("");
    textOutput.setText("");
    phoneNumber.setText("");
    commResult.setText("");
  }

  public void sendSMS(ActionEvent actionEvent) {
    Boolean success = phoneComms.sendMsg(phoneNumber.getText(), textOutput.getText());
    if (success) {
      commResult.setText("Success! You will get a text momentarily.");
    } else {
      commResult.setText("Couldn't send text. Check the number & try again.");
    }
  }

  public void callPhone(ActionEvent actionEvent) {
    String callText =
        ("<Response><Pause/><Pause/><Say voice=\"alice\" language=\""
            + langVoicesMap.get(toLanguage.getValue())
            + "\">"
            + textOutput.getText()
            + "</Say></Response>");

    System.out.println(callText);
    Boolean success = phoneComms.callPhone(phoneNumber.getText(), callText);
    if (success) {
      commResult.setText("Success! You will get a call momentarily.");
    } else {
      commResult.setText("Unable to call. Check the number and try again.");
    }
  }

  public void toLanguage(ActionEvent actionEvent) {
    textOutput.setText("");
    sendSMS.setDisable(true);
    callPhone.setDisable(true);
  }

  public void fromLanguage(ActionEvent actionEvent) {
    textOutput.setText("");
    sendSMS.setDisable(true);
    callPhone.setDisable(true);
  }
}
