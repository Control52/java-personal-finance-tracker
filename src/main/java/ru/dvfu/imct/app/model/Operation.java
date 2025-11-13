package ru.dvfu.imct.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Operation {
    protected int id;
    protected BigDecimal amount;
    protected LocalDate date;
    protected String description;
    protected String category;
    protected Account account;


    public Operation(int id, BigDecimal amount, LocalDate date,
            String description, String category, Account account) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Account geAccount() {
        return account;
    }
}
