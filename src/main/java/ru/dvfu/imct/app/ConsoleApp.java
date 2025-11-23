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
import java.util.Scanner;

public class ConsoleApp {
    private final WalletService wallet;
    private final Scanner scanner;

    public ConsoleApp() {
        this.wallet = new WalletServiceImpl(new InMemoryStorage());
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Личный финансовый трекер ===");
            System.out.println("1. Посмотреть мои счета");
            System.out.println("2. Добавить доход");
            System.out.println("3. Добавить расход");
            System.out.println("4. Сделать перевод");
            System.out.println("5. Выйти");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    showAccounts();
                    break;
                case "2":
                    addIncome();
                    break;
                case "3":
                    addExpense();
                    break;
                case "4":
                    makeTransfer();
                    break;
                case "5":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void showAccounts() {
        while (true) {
            List<Account> accounts = wallet.getAllAccounts();
            System.out.println("\nВаши счета:");
            if (accounts.isEmpty()) {
                System.out.println("Нет счетов.");
            } else {
                for (Account acc : accounts) {
                    System.out.printf("ID: %d | %s | Баланс: %s\n", acc.getAccountId(), acc.getAccountName(), acc.getBalance());
                }
            }
            System.out.println("\n1. Создать новый счёт");
            System.out.println("2. Удалить счёт");
            System.out.println("3. Посмотреть операции по счёту");
            System.out.println("4. Выйти в главное меню");
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    createAccount();
                    break;
                case "2":
                    deleteAccount();
                    break;
                case "3":
                    showAccountOperations(accounts);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void createAccount() {
        System.out.print("Введите название счёта: ");
        String name = scanner.nextLine();
        BigDecimal balance;
        while (true) {
            System.out.print("Введите начальный баланс: ");
            try {
                balance = new BigDecimal(scanner.nextLine());
                Account acc = wallet.createAccount(name, balance);
                System.out.println("Счёт создан: " + acc.getAccountName() + ", Баланс: " + acc.getBalance());
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
    private void deleteAccount() {
        List<Account> accounts = wallet.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Нет счетов для удаления.");
            return;
        }
        System.out.println("Список счетов:");
        for (Account acc : accounts) {
            System.out.printf("ID: %d | %s | Баланс: %s\n", acc.getAccountId(), acc.getAccountName(), acc.getBalance());
        }
        System.out.print("Введите ID счёта для удаления: ");
        long id = Long.parseLong(scanner.nextLine());
        try {
            wallet.deleteAccount(id);
            System.out.println("Счёт удалён.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void addExpense() {
        if (wallet.getAllAccounts().isEmpty()) {
            System.out.println("Сначала создайте счёт.");
            return;
        }
        System.out.print("Сумма: ");
        BigDecimal amount = readBigDecimal();
        System.out.print("Описание: ");
        String desc = scanner.nextLine();
        System.out.print("Категория: ");
        String category = scanner.nextLine();
        long accountId = selectAccount();
        try {
            Expense expense = wallet.addExpense(LocalDate.now(), amount, desc, accountId, category);
            System.out.println("Расход добавлен: " + expense);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void addIncome() {
        if (wallet.getAllAccounts().isEmpty()) {
            System.out.println("Сначала создайте счёт.");
            return;
        }
        System.out.print("Сумма: ");
        BigDecimal amount = readBigDecimal();
        System.out.print("Описание: ");
        String desc = scanner.nextLine();
        System.out.print("Категория: ");
        String category = scanner.nextLine();
        long accountId = selectAccount();
        try {
            Income income = wallet.addIncome(LocalDate.now(), amount, desc, accountId, category);
            System.out.println("Доход добавлен: " + income);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void makeTransfer() {
        if (wallet.getAllAccounts().size() < 2) {
            System.out.println("Для перевода нужно минимум два счёта.");
            return;
        }
        System.out.println("Выберите счёт-отправитель:");
        long fromId = selectAccount();
        System.out.println("Выберите счёт-получатель:");
        long toId = selectAccount(fromId);
        System.out.print("Сумма: ");
        BigDecimal amount = readBigDecimal();
        System.out.print("Описание: ");
        String desc = scanner.nextLine();
        try {
            Transfer transfer = wallet.transfer(LocalDate.now(), amount, desc, fromId, toId);
            System.out.println("Перевод выполнен: " + transfer);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private long selectAccount() {
        return selectAccount(-1);
    }

    private long selectAccount(long excludeId) {
        List<Account> accounts = wallet.getAllAccounts();
        for (Account acc : accounts) {
            if (acc.getAccountId() != excludeId) {
                System.out.printf("ID: %d | %s | Баланс: %s\n", acc.getAccountId(), acc.getAccountName(), acc.getBalance());
            }
        }
        while (true) {
            System.out.print("Введите ID счёта: ");
            try {
                long id = Long.parseLong(scanner.nextLine());
                if (accounts.stream().anyMatch(a -> a.getAccountId() == id && a.getAccountId() != excludeId)) {
                    return id;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Некорректный ID. Попробуйте снова.");
        }
    }

    private BigDecimal readBigDecimal() {
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод. Введите число: ");
            }
        }
    }

    private void showAccountOperations(List<Account> accounts) {
        if (accounts.isEmpty()) {
            System.out.println("Нет счетов.");
            return;
        }
        System.out.println("Выберите счёт для просмотра операций:");
        long id = selectAccount();
        var operations = wallet.getOperationsForAccount(id);
        if (operations.isEmpty()) {
            System.out.println("Операций по счёту нет.");
        } else {
            System.out.println("Операции по счёту:");
            operations.forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        new ConsoleApp().run();
    }
}
