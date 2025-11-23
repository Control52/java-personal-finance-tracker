package ru.dvfu.imct.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Operation {
    protected final long id;
    protected LocalDate date;
    protected BigDecimal amount;
    protected String description;

    public Operation (long id, LocalDate date, BigDecimal amount, String description) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Сумма операции должна быть положительной");
        }
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public abstract void execute();

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void  setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getId() {
        return id;
    }
 
    public LocalDate getDate() {
        return date;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }

}
