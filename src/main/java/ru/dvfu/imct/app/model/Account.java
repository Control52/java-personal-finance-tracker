package ru.dvfu.imct.app.model;

import ru.dvfu.imct.app.exceptions.InsufficientFundsException;
import ru.dvfu.imct.app.exceptions.NegativeAmountException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private final long accountId;

    private String accountName;

    private BigDecimal balance;

    private List<Operation> operations = new ArrayList<>();

    public Account(long accountId, String accountName, BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = initialBalance;
    }

    
    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountException("Сумма снятия должна быть положительной");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                    String.format("Недостаточно средств на счёте %s. Баланс: %s, требуется: %s",
                            accountName, balance, amount));
                        }
        this.balance = this.balance.subtract(amount);
    }
    
    
    public void addOperation(Operation operation) {
        operations.add(operation);
    }
    
    public long getAccountId() {
        return accountId;
    }
    
    
    public String getAccountName() {
        return accountName;
    }
    
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    
    public List<Operation> getOperations() {
        return Collections.unmodifiableList(operations);
    }

    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
