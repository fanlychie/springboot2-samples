package org.fanlychie.jdbc.sample.service;

import org.fanlychie.jdbc.sample.dao.BankAccountDao;
import org.fanlychie.jdbc.sample.exception.TransactionException;
import org.fanlychie.jdbc.sample.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by fanlychie on 2019/6/24.
 */
@Service
public class BankAccountService {

    @Autowired
    private BankAccountDao bankAccountDao;

    // 入账
    public boolean credit(int accountID, double amount) {
        if (amount < 0) {
            throw new TransactionException("BankAccount ID " + accountID + " credit fail for input ¥" + amount);
        }
        BankAccount account = bankAccountDao.findById(accountID);
        if (account == null) {
            throw new TransactionException("BankAccount ID " + accountID + " not exist");
        }
        double balance = account.getBalance();
        BigDecimal changeDecimal = new BigDecimal(String.valueOf(amount))
                .add(new BigDecimal(String.valueOf(balance)));
        account.setBalance(changeDecimal.doubleValue());
        return bankAccountDao.update(account) == 1;
    }

    // 支出
    public boolean debit(int accountID, double amount) {
        if (amount < 0) {
            throw new TransactionException("BankAccount ID " + accountID + " debit fail for input ¥" + amount);
        }
        BankAccount account = bankAccountDao.findById(accountID);
        if (account == null) {
            throw new TransactionException("BankAccount ID " + accountID + " not exist");
        }
        double balance = account.getBalance();
        BigDecimal balanceDecimal = new BigDecimal(String.valueOf(balance));
        BigDecimal amountDecimal = new BigDecimal(String.valueOf(amount));
        if (balanceDecimal.compareTo(amountDecimal) == -1) {
            throw new TransactionException("BankAccount ID " + accountID + " debit fail by ¥" + amount + ", current ¥" + balance);
        }
        BigDecimal change = balanceDecimal.subtract(amountDecimal);
        account.setBalance(change.doubleValue());
        return bankAccountDao.update(account) == 1;
    }

    // 转账
    @Transactional(rollbackFor = TransactionException.class)
    public boolean transfer(int fromAccountID, int toAccountID, double amount) {
        boolean creResult = credit(toAccountID, amount);
        boolean debResult = debit(fromAccountID, amount);
        return debResult && creResult;
    }

}