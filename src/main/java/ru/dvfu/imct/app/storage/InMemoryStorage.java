package ru.dvfu.imct.app.storage;

import ru.dvfu.imct.app.exceptions.AccountNotEmptyException;
import ru.dvfu.imct.app.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStorage implements Storage {
    private final List<Account> accounts = new ArrayList<>();

    private final AtomicLong accountIdGenerator = new AtomicLong(1);

    private final AtomicLong operationIdGenerator = new AtomicLong(1);

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    public Account getAccountById(long id) {
        return accounts.stream()
                .filter(a -> a.getAccountId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        Account existing = getAccountById(account.getAccountId());
        if (existing == null) {
            accounts.add(account);
        } else {
            accounts.remove(existing);
            accounts.add(account);
        }
    }

    @Override
    public void deleteAccount(Account account) throws AccountNotEmptyException {
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new AccountNotEmptyException("Нельзя удалить счёт с ненулевым балансом");
        }
        accounts.remove(account);
    }

    @Override
    public long generateAccountId() {
        return accountIdGenerator.getAndIncrement();
    }

    @Override
    public long generateOperationId() {
        return operationIdGenerator.getAndIncrement();
    }
}
