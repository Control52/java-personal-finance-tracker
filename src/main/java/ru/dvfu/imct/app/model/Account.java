package ru.dvfu.imct.app.model;

import java.math.BigDecimal;

public class Account {
    private int id;
    private String name;
    private BigDecimal balance;

    public Account(int id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    private BigDecimal applyIncome(BigDecimal amount) {
        balance = balance.add(amount);
        return balance;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
