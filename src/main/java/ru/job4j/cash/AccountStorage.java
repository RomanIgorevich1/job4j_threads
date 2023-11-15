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
        return accounts.putIfAbsent(account.id(), account) == null;
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
        Optional<Account> sender = getById(fromId);
        Optional<Account> recipient = getById(toId);
        boolean result = (sender.isPresent() && recipient.isPresent())
                && (sender.get().amount() != 0 && recipient.get().amount() != 0);
        if (result) {
            update(new Account(sender.get().id(), sender.get().amount() - amount));
            update(new Account(recipient.get().id(), recipient.get().amount() + amount));

        } else {
            throw new IllegalArgumentException("No money to transfer");
        }
        return result;
    }
}