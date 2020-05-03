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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

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

  private phoneComms phoneComms = new phoneComms();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    langCodeMap = new HashMap<>();
    langCodeMap.put("English", "en");
    langCodeMap.put("Spanish", "es");
    langCodeMap.put("French", "fr");
    langCodeMap.put("German", "de");
    langCodeMap.put("Italian", "it");

    langVoicesMap = new HashMap<>();
    langVoicesMap.put("English", Say.Language.EN_US.toString());
    langVoicesMap.put("Spanish", Say.Language.ES_MX.toString());
    langVoicesMap.put("French", Say.Language.FR_CA.toString());
    langVoicesMap.put("German", Say.Language.DE_DE.toString());
    langVoicesMap.put("Italian", Say.Language.IT_IT.toString());

    fromLanguage.getItems().addAll(langCodeMap.keySet());
    toLanguage.getItems().addAll(langCodeMap.keySet());

    resetTranslator();

    // TODO Disable translate button if text is empty
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
    fromLanguage.setValue("English");
    toLanguage.setValue("Spanish");
    textInput.setText("");
    textOutput.setText("");
    phoneNumber.setText("");
  }

  public void sendSMS(ActionEvent actionEvent) {
    phoneComms.sendMsg(phoneNumber.getText(), textOutput.getText());
  }

  public void callPhone(ActionEvent actionEvent) {
    String callText =
        ("<Response><Say voice=\"alice\" language=\""
            + langVoicesMap.get(toLanguage.getValue())
            + "\">"
            + textOutput.getText()
            + "</Say></Response>");

    System.out.println(callText);
    phoneComms.callPhone(phoneNumber.getText(), callText);
  }
}
