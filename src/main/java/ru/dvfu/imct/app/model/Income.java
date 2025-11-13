package ru.dvfu.imct.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Income extends Operation {
    public Income(int id, BigDecimal amount, LocalDate date,
            String description, String category, Account account) {
        super(id, amount, date, description, category, account);
    }

}
