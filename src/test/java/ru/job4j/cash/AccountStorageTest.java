package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AccountStorageTest {

    @Test
    public void whenAdd() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 100));
        accountStorage.add(new Account(2, 200));
        accountStorage.add(new Account(3, 300));
        Account firstAccount = accountStorage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        Account secondAccount = accountStorage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        Account thirdAccount = accountStorage.getById(3)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
        assertThat(secondAccount.amount()).isEqualTo(200);
        assertThat(thirdAccount.amount()).isEqualTo(300);
    }

    @Test
    public void whenUpdate() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 100));
        accountStorage.update(new Account(1, 500));
        Account firstAccount = accountStorage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(500);
    }

    @Test
    public void whenDelete() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 100));
        accountStorage.add(new Account(2, 100));
        accountStorage.add(new Account(3, 100));
        accountStorage.delete(2);
        assertThat(accountStorage.getById(2)).isEmpty();
    }

    @Test
    public void whenTransfer() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 100));
        accountStorage.add(new Account(2, 100));
        accountStorage.transfer(1, 2, 100);
        Account firstAccount = accountStorage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        Account secondAccount = accountStorage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
    }

    @Test
    public void whenTransferAndBack() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 200));
        accountStorage.add(new Account(2, 200));
        accountStorage.transfer(1, 2, 100);
        Account firstAccount = accountStorage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        Account secondAccount = accountStorage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
        assertThat(secondAccount.amount()).isEqualTo(300);
        accountStorage.transfer(2, 1, 300);
        Account firstAccount1 = accountStorage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        Account secondAccount2 = accountStorage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount1.amount()).isEqualTo(400);
        assertThat(secondAccount2.amount()).isEqualTo(0);
    }

    @Test
    public void whenNoMoney() {
        AccountStorage accountStorage = new AccountStorage();
        accountStorage.add(new Account(1, 0));
        accountStorage.add(new Account(2, 100));
        assertThatThrownBy(() -> accountStorage.transfer(1, 2, 100))
                .isInstanceOf(IllegalArgumentException.class);
    }
}