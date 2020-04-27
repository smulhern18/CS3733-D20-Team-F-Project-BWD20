package edu.wpi.teamF.Controllers.UISettings;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
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

  NodeFactory nodeFactory = NodeFactory.getFactory();

  public void setAsLocationComboBox(JFXComboBox<String> comboBox) {
    comboBox.setEditable(true);
    ObservableList<String> locations = FXCollections.observableArrayList();
    for (Node node : nodeFactory.getAllNodes()) {
      if (!node.getType().equals(Node.NodeType.getEnum("HALL"))) {
        locations.add(node.getLongName() + " " + node.getId());
      }
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
