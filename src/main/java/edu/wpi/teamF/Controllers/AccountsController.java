package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.Admin;
import edu.wpi.teamF.ModelClasses.Account.Staff;
import edu.wpi.teamF.ModelClasses.Account.User;
import edu.wpi.teamF.ModelClasses.UIClasses.UIAccount;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;
import lombok.SneakyThrows;

public class AccountsController implements Initializable {
  public JFXTreeTableView<UIAccount> accountsView;
  public JFXButton updateStaff;
  public JFXComboBox<String> algoChoiceBox;
    SceneController sceneController = App.getSceneController();
  AccountFactory accounts = AccountFactory.getFactory();
  ObservableList<UIAccount> uiAccount = FXCollections.observableArrayList();

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {


    // ID column
    JFXTreeTableColumn<UIAccount, String> firstName = new JFXTreeTableColumn<>("First Name");
    firstName.setPrefWidth(100);
    firstName.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIAccount, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIAccount, String> param) {
            return param.getValue().getValue().getFirstName();
          }
        });

    // ID column
    JFXTreeTableColumn<UIAccount, String> lastName = new JFXTreeTableColumn<>("Last Name");
    lastName.setPrefWidth(100);
    lastName.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIAccount, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIAccount, String> param) {
            return param.getValue().getValue().getLastName();
          }
        });

    // ID column
    JFXTreeTableColumn<UIAccount, String> emailAddress = new JFXTreeTableColumn<>("Email Address");
    emailAddress.setPrefWidth(180);
    emailAddress.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIAccount, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIAccount, String> param) {
            return param.getValue().getValue().getEmailAddress();
          }
        });

    // ID column
    JFXTreeTableColumn<UIAccount, String> userName = new JFXTreeTableColumn<>("Username");
    userName.setPrefWidth(150);
    userName.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIAccount, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIAccount, String> param) {
            return param.getValue().getValue().getUserName();
          }
        });

    // ID column
    JFXTreeTableColumn<UIAccount, String> type = new JFXTreeTableColumn<>("Type");
    type.setPrefWidth(100);
    type.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIAccount, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIAccount, String> param) {
            return param.getValue().getValue().getType();
          }
        });

    // get all the accounts and set them into a list
    ArrayList<UIAccount> UIAccounts = accounts.getAccounts();
    uiAccount.addAll(UIAccounts);

    final TreeItem<UIAccount> root =
        new RecursiveTreeItem<UIAccount>(uiAccount, RecursiveTreeObject::getChildren);

    accountsView.getColumns().setAll(firstName, lastName, emailAddress, userName, type);

    type.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    accountsView.setRoot(root);
    accountsView.setEditable(true);
    accountsView.setShowRoot(false);
    // testData();
  }

  public void testData() throws Exception {

    String[] validUsernames = {"This one", "TheCuddleMonster", "sjmulhern", "Snuggles"};
    String[] validNames = {"Ferdinand", "Nikolas", "Constantine", "Nero"};
    String[] validEmails = {
      "sjmulhern@wpi.edu", "joeBiden@usa.gov", "putin@rus.ru", "wack@giggle.pig"
    };
    String[] validPasswords = {
      "Areally$tr0ngPassw@rd", "aWeakerPassw0rd", "weakPassword", "password"
    };

    accounts.create(
        new Admin(
            validNames[0], validNames[0], validEmails[0], validUsernames[0], validPasswords[0]));
    accounts.create(
        new Staff(
            validNames[1], validNames[1], validEmails[1], validUsernames[1], validPasswords[1]));
    accounts.create(
        new User(
            validNames[2], validNames[2], validEmails[2], validUsernames[2], validPasswords[2]));
    accounts.create(
        new User(
            validNames[3], validNames[3], validEmails[3], validUsernames[3], validPasswords[3]));
  }

  public void updateAccounts(ActionEvent actionEvent) throws Exception {
    for (UIAccount uiAccount : uiAccount) {
      Account account = accounts.read(uiAccount.getUserName().get());
      boolean isSame = uiAccount.equalsAccount(account);
      if (!isSame) {
        // update that edge in the db to the new values of that nodeUI

        account.setFirstName(uiAccount.getFirstName().get());
        account.setLastName(uiAccount.getLastName().get());
        account.setEmailAddress(uiAccount.getEmailAddress().get());
        switch (uiAccount.getType().get()) {
          case "ADMIN":
            account.setType(Account.Type.ADMIN);
            break;
          case "STAFF":
            account.setType(Account.Type.STAFF);
            break;
          case "USER":
            account.setType(Account.Type.USER);
            break;
        }
        accounts.update(account);
      }
    }
    accountsView.refresh();
  }

  public void backtoData(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DataManipulator");
  }
}
