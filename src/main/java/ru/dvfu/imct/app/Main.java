package ru.dvfu.imct.app;

import ru.dvfu.imct.app.model.Account;
import ru.dvfu.imct.app.model.Expense;
import ru.dvfu.imct.app.model.Income;
import ru.dvfu.imct.app.model.Transfer;
import ru.dvfu.imct.app.service.WalletService;
import ru.dvfu.imct.app.service.WalletServiceImpl;
import ru.dvfu.imct.app.storage.InMemoryStorage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WalletService wallet = new WalletServiceImpl(new InMemoryStorage());


        Account acc1 = wallet.createAccount("Кошелек", BigDecimal.valueOf(1000));
        Account acc2 = wallet.createAccount("Карта", BigDecimal.valueOf(500));


        Income income = wallet.addIncome(LocalDate.now(), BigDecimal.valueOf(20000),
                            "Оклад", acc1.getAccountId(), "Работа");
        System.out.println("Доход добавлен: " + income);
 
        Expense expense = wallet.addExpense(LocalDate.now(), BigDecimal.valueOf(1500),
                            "Магазин", acc1.getAccountId(), "Продукты");
        System.out.println("Расход добавлен: " + expense);


        Transfer transfer = wallet.transfer(LocalDate.now(), BigDecimal.valueOf(3000),
                             "Сбережения", acc1.getAccountId(), acc2.getAccountId());
        System.out.println("Перевод выполнен: " + transfer);

        List<Account> accounts = wallet.getAllAccounts();
        for (Account acc : accounts) {
            System.out.println("Счет: " + acc.getAccountName() + ", Баланс: " + acc.getBalance());
        }

        System.out.println("Все операции:");
        wallet.getAllOperations().forEach(System.out::println);
    }
}