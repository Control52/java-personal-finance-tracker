package ru.dvfu.imct.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transfer extends Operation {
       
    private Account fromAccount;
    
    private Account toAccount;

    public Transfer (long id, LocalDate date, BigDecimal amount,
                     String description, Account fromAccount, Account toAccount) {
        super(id, date, amount, description);
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    @Override
    public String toString() {
        return String.format("[Перевод] %s | Сумма: %s | С: %s | На: %s",
            date, amount, fromAccount.getAccountName(), toAccount.getAccountName());
    }
    
    @Override
    public void execute(){
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
        fromAccount.addOperation(this);
        toAccount.addOperation(this);
    }

}
