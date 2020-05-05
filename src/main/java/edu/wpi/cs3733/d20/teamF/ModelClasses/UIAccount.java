package edu.wpi.cs3733.d20.teamF.ModelClasses;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.d20.teamF.ModelClasses.Account.Account;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UIAccount extends RecursiveTreeObject<UIAccount> {
    public StringProperty firstName;
    public StringProperty lastName;
    public StringProperty emailAddress;
    public StringProperty userName;
    public StringProperty type;
    public StringProperty specialty;

    public UIAccount(
            String string, String string1, String string2, String string3, Account.Type anEnum, Account.Specialty anEnum2) {
        this.firstName = new SimpleStringProperty(string);
        this.lastName = new SimpleStringProperty(string1);
        this.emailAddress = new SimpleStringProperty(string2);
        this.userName = new SimpleStringProperty(string3);
        this.type = new SimpleStringProperty(anEnum.toString());
        this.specialty = new SimpleStringProperty(anEnum2.toString());
    }

    public UIAccount(Account account) {
        this(account.getFirstName(), account.getLastName(), account.getEmailAddress(), account.getUsername(), account.getType(), account.getSpecialty());
    }

    public boolean equalsAccount(Account account) {
        boolean isEqual = false;
        if (account != null) {
            System.err.println(
                    isEqual =
                            this.firstName.get().equals(account.getFirstName())
                                    && this.lastName.get().equals(account.getLastName())
                                    && this.userName.get().equals(account.getUsername())
                                    && this.emailAddress.get().equals(account.getEmailAddress())
                                    && this.type.get().equals(account.getType().toString())
                                    && this.specialty.get().equals(account.getSpecialty().toString()));
        }  return isEqual;
    }
}