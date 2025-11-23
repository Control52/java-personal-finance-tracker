package ru.dvfu.imct.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Income extends Operation{
      
    private String incomeCategory;
    private Account account;

        public Income(long id, LocalDate date, BigDecimal amount,
                  String description, Account account, String category) {
            super(id, date, amount, description);
        this.account = account;
        this.incomeCategory = category;
    }

    @Override
        public String toString() {
            return String.format("[Доход] %s | Сумма: %s | Категория: %s | Счёт: %s", 
            date, amount, incomeCategory, account.getAccountName());
        }
        
    @Override
    public void execute() {
        account.deposit(amount);
        account.addOperation(this);
    }

    public String getIncomeCategory() {
        return incomeCategory;
    }
}
