package ru.dvfu.imct.app.service;

import ru.dvfu.imct.app.exceptions.*;
import ru.dvfu.imct.app.model.*;
import ru.dvfu.imct.app.storage.Storage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class WalletServiceImpl implements WalletService {
    private final Storage storage;

    public WalletServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Account createAccount(String name, BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException("Начальный баланс не может быть отрицательным");
        }
        Account account = new Account(storage.generateAccountId(), name, initialBalance);
        storage.saveAccount(account);
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return storage.getAllAccounts();
    }

    @Override
    public void deleteAccount(long accountId) throws AccountNotEmptyException {
        Account account = storage.getAccountById(accountId);
        if (account == null) {
            throw new NoSuchElementException("Счёт не найден");
        }
        storage.deleteAccount(account);
    }

    @Override
        public Income addIncome(LocalDate date, BigDecimal amount,
                    String description, long accountId, String category) {
            Account account = getAccountOrThrow(accountId);
            Income income = new Income(
                storage.generateOperationId(), date == null ? LocalDate.now() : date,
                amount, description, account, category
            );
            income.execute();
            return income;
        }

    @Override
        public Expense addExpense(LocalDate date, BigDecimal amount,
                      String description, long accountId, String category) {
            Account account = getAccountOrThrow(accountId);
            Expense expense = new Expense(
                storage.generateOperationId(), date == null ? LocalDate.now() : date,
                amount, description, account, category
            );
            expense.execute();
            return expense;
        }

    @Override
    public Transfer transfer(LocalDate date, BigDecimal amount,
                             String description, long fromAccountId, long toAccountId) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Нельзя переводить на тот же счёт");
        }
        Account from = getAccountOrThrow(fromAccountId);
        Account to = getAccountOrThrow(toAccountId);

        Transfer transfer = new Transfer(
                storage.generateOperationId(), date == null ? LocalDate.now() : date,
                amount, description, from, to
        );
        transfer.execute();
        return transfer;
    }

    @Override
    public List<Operation> getOperationsForAccount(long accountId) {
        Account account = storage.getAccountById(accountId);
        return account != null ? account.getOperations() : List.of();
    }

    @Override
    public List<Operation> getAllOperations() {
        return storage.getAllAccounts().stream()
                .flatMap(a -> a.getOperations().stream())
                .collect(Collectors.toList());
    }

    private Account getAccountOrThrow(long id) {
        Account account = storage.getAccountById(id);
        if (account == null) {
            throw new NoSuchElementException("Счёт с id " + id + " не найден");
        }
        return account;
    }
}