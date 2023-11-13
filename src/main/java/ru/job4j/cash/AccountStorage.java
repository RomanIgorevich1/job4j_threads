package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.putIfAbsent(account.id(), new Account(account.id(), account.amount()));
        return true;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(),
                getById(account.id()).get(),
                new Account(account.id(), account.amount()));
    }

    public synchronized void delete(int id) {
        getById(id).ifPresent(account -> accounts.remove(account.id()));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    /**
     * Переводит сумму amount со счета fromId в счет toId
     * @param fromId с какого
     * @param toId   куда
     * @param amount сумма
     * @return
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (getById(fromId).get().amount() == 0 || getById(toId).get().amount() == 0) {
            throw new IllegalArgumentException("No money to transfer");
        }
        update(new Account(getById(fromId).get().id(), getById(fromId).get().amount() - amount));
        return update(new Account(getById(toId).get().id(), getById(toId).get().amount() + amount));
    }
}