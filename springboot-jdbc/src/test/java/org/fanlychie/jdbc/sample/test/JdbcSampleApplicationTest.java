package org.fanlychie.jdbc.sample.test;

import org.fanlychie.jdbc.sample.dao.BankAccountDao;
import org.fanlychie.jdbc.sample.exception.TransactionException;
import org.fanlychie.jdbc.sample.model.BankAccount;
import org.fanlychie.jdbc.sample.service.BankAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by fanlychie on 2019/6/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class JdbcSampleApplicationTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountDao bankAccountDao;

    /**
     * 账户1余额100, 账户2余额100, 账户1向账户2转账50元, 操作成功
     */
    @Test
    public void testTransfer() {
        BankAccount bankAccount1 = bankAccountDao.findById(1);
        BankAccount bankAccount2 = bankAccountDao.findById(2);
        assertThat(bankAccount1.getBalance()).isEqualTo(100);
        assertThat(bankAccount2.getBalance()).isEqualTo(100);
        bankAccountService.transfer(1, 2, 50);
        bankAccount1 = bankAccountDao.findById(1);
        bankAccount2 = bankAccountDao.findById(2);
        assertThat(bankAccount1.getBalance()).isEqualTo(50);
        assertThat(bankAccount2.getBalance()).isEqualTo(150);
    }

    /**
     * 账户1余额100, 账户2余额100, 账户1向账户2转账200元, 操作失败, 报TransactionException, 账户1余额100, 账户2余额100
     */
    @Test(expected = TransactionException.class)
    public void testTransferFailure() {
        BankAccount bankAccount1 = bankAccountDao.findById(1);
        BankAccount bankAccount2 = bankAccountDao.findById(2);
        assertThat(bankAccount1.getBalance()).isEqualTo(100);
        assertThat(bankAccount2.getBalance()).isEqualTo(100);
        try {
            bankAccountService.transfer(1, 2, 200);
        } catch (TransactionException e) {
            bankAccount1 = bankAccountDao.findById(1);
            bankAccount2 = bankAccountDao.findById(2);
            assertThat(bankAccount1.getBalance()).isEqualTo(100);
            assertThat(bankAccount2.getBalance()).isEqualTo(100);
            throw e;
        }
    }

    /**
     * 查询全部数据
     */
    @Test
    public void testFindAll() {
        List<BankAccount> accounts = bankAccountDao.findAll();
        assertThat(accounts.size()).isEqualTo(2);
        accounts.forEach(System.out::println);
    }

}