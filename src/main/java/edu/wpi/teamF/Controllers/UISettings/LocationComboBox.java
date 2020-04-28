package edu.wpi.teamF.Controllers.UISettings;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Node;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LocationComboBox {

  DatabaseManager databaseManager = DatabaseManager.getManager();

  public LocationComboBox() throws Exception {}

  public void setAsLocationComboBox(JFXComboBox<String> comboBox) throws Exception {
    comboBox.setEditable(true);
    ObservableList<String> locations = FXCollections.observableArrayList();
<<<<<<< HEAD
    try {
      for (Node node : databaseManager.getAllNodes()) {
        locations.add(node.getLongName() + " " + node.getId());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
=======
    for (Node node : databaseManager.getAllNodes()) {
      locations.add(node.getLongName() + " " + node.getId());
>>>>>>> 1d9873ee309d93ceea722a3750251c6c45682e5b
    }
    FilteredList<String> filteredLocations = new FilteredList<String>(locations, p -> true);

    comboBox
        .getEditor()
        .textProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              final TextField editor = comboBox.getEditor();
              final String selected = comboBox.getSelectionModel().getSelectedItem();

              Platform.runLater(
                  () -> {
                    if (selected == null || !selected.equals(editor.getText())) {
                      filteredLocations.setPredicate(
                          location -> {
                            return location.toUpperCase().startsWith(newValue.toUpperCase());
                          });
                    }
                  });
            }));

    comboBox.setItems(filteredLocations);

    comboBox.setOnKeyReleased(
        keyEvent -> {
          if (keyEvent.getCode() != KeyCode.ENTER) {
            comboBox.show();
          }
        });
    ComboBoxListViewSkin<String> comboBoxListViewSkin = new ComboBoxListViewSkin<String>(comboBox);
    comboBoxListViewSkin
        .getPopupContent()
        .addEventFilter(
            KeyEvent.ANY,
            (event) -> {
              if (event.getCode() == KeyCode.SPACE) {
                event.consume();
              }
            });
    comboBox.setSkin(comboBoxListViewSkin);
  }
}
