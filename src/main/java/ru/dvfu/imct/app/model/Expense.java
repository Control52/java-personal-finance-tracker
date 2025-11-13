package ru.dvfu.imct.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense extends Operation {
    public Expense(int id, BigDecimal amount, LocalDate date,
            String description, String category, Account account) {
        super(id, amount, date, description, category, account);
    }

}
