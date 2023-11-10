package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        boolean check = false;
        synchronized (accounts) {
            if (!accounts.containsValue(account)) {
                accounts.put(account.id(), account);
                check = true;
            }
        }
        return check;
    }

    public boolean update(Account account) {
        boolean result2 = false;
        synchronized (accounts) {
            Optional<Account> result = getById(account.id());
            if (result.isPresent()) {
                accounts.put(account.id(), new Account(result.get().id(), account.amount()));
                result2 = true;
            }
        }
        return result2;
    }

    public void delete(int id) {
        Optional<Account> result = getById(id);
        synchronized (accounts) {
            result.ifPresent(account -> accounts.remove(account.id()));
        }
    }

    public Optional<Account> getById(int id) {
        Optional<Account> result = Optional.empty();
        synchronized (accounts) {
            if (accounts.containsKey(id)) {
                result = accounts.values().stream().filter(num -> num.id() == id).findAny();
            }
        }
        return result;
    }

    /**
     * Переводит сумму amount со счета fromId в счет toId
     * @param fromId с какого
     * @param toId куда
     * @param amount сумма
     * @return
     */
    public  boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> firstAccount = getById(fromId);
        Optional<Account> secondAccount = getById(toId);
        boolean result = false;
        synchronized (accounts) {
            if (firstAccount.isPresent() && secondAccount.isPresent()) {
                if (firstAccount.get().amount() == 0 || secondAccount.get().amount() == 0) {
                    throw new IllegalArgumentException("No money to transfer");
                }
                accounts.put(firstAccount.get().id(),
                        new Account(firstAccount.get().id(), firstAccount.get().amount() - amount));
                accounts.put(secondAccount.get().id(),
                        new Account(secondAccount.get().id(), secondAccount.get().amount() + amount));
                result = true;
            }
        }
        return result;
    }
}
