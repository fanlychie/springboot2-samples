package org.fanlychie.jdbc.sample.test;

import org.fanlychie.jdbc.sample.JdbcSampleApplication;
import org.fanlychie.jdbc.sample.dao.BankAccountDao;
import org.fanlychie.jdbc.sample.model.BankAccount;
import org.fanlychie.jdbc.sample.service.BankAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by fanlychie on 2019/6/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JdbcSampleApplication.class})
public class JdbcSampleApplicationTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountDao bankAccountDao;

    @Test
    public void testTransfer() {
        bankAccountService.transfer(1, 2, 50);
    }

    @Test
    public void testFindAll() {
        List<BankAccount> accounts = bankAccountDao.findAll();
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
    }

}