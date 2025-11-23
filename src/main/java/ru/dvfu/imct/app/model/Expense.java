package ru.dvfu.imct.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense extends Operation {
     
    private String expenseCategory;
    private Account account;

    public Expense (long id, LocalDate date, BigDecimal amount,
                    String description, Account account, String category) {
        super(id, date, amount, description);
        this.account = account;
        this.expenseCategory = category;
    }

    @Override
    public String toString() {
        return String.format("[Расход] %s | Сумма: %s | Категория: %s | Счёт: %s",
            date, amount, expenseCategory, account.getAccountName());
    }
    
    @Override
    public void execute() {
        account.withdraw(amount);
        account.addOperation(this);
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }
}
