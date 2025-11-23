package ru.dvfu.imct.app.storage;

import ru.dvfu.imct.app.model.Account;
import ru.dvfu.imct.app.exceptions.AccountNotEmptyException;

import java.util.List;

public interface Storage {
    List<Account> getAllAccounts();

    Account getAccountById(long id);

    void saveAccount(Account account);

    void deleteAccount(Account account) throws AccountNotEmptyException;

    long generateAccountId();

    long generateOperationId();
}
