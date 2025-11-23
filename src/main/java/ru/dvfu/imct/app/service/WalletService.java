package ru.dvfu.imct.app.service;

import ru.dvfu.imct.app.exceptions.AccountNotEmptyException;
import ru.dvfu.imct.app.model.Account;
import ru.dvfu.imct.app.model.Expense;
import ru.dvfu.imct.app.model.Income;
import ru.dvfu.imct.app.model.Operation;
import ru.dvfu.imct.app.model.Transfer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface WalletService {
    Account createAccount(String name, BigDecimal initialBalance);

    List<Account> getAllAccounts();

    void deleteAccount(long accountId) throws AccountNotEmptyException;

    Income addIncome(LocalDate date, BigDecimal amount, String description,
                     long accountId, String category);

    Expense addExpense(LocalDate date, BigDecimal amount, String description,
                      long accountId, String category);

    Transfer transfer(LocalDate date, BigDecimal amount, String description,
                      long fromAccountId, long toAccountId);

    List<Operation> getOperationsForAccount(long accountId);

    List<Operation> getAllOperations();
}
