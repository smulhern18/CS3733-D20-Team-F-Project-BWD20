package edu.wpi.teamF.DatabaseManipulators;

import edu.wpi.teamF.ModelClasses.Account.Account;

public class AccountFactory {

    private static final AccountFactory factory = new AccountFactory();

    public static AccountFactory getFactory() {
        return factory;
    }



    public void create(Account account) {

    }

    public Account read(String address) {
        return null;
    }

    public void update(Account account) {

    }

    public void delete(String address) {

    }

    public Account getAccountByUsername(String username) {
        return null;
    }

}
