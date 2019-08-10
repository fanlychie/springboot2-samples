## Spring Boot JDBC

![](https://gitee.com/fanlychie/images/raw/develop/java1.8.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/springboot2.1.6.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/maven3.6.1.svg) 
![](https://gitee.com/fanlychie/images/raw/develop/idea2019.svg) 

---

### 1. 依赖配置

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

使用`JDBC`只需要声明`spring-boot-starter-jdbc`依赖，Spring Boot 会自动配置 Spring JDBC。

---

### 2. 数据库

以 MySQL 为例，创建 BANK_ACCOUNT 表：

```sql
-- ----------------------------
-- Table structure for bank_account
-- ----------------------------
DROP TABLE IF EXISTS `BANK_ACCOUNT`;
CREATE TABLE `BANK_ACCOUNT` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(16) NOT NULL,
  `BALANCE` double DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_account
-- ----------------------------
INSERT INTO `BANK_ACCOUNT` VALUES ('1', 'Tom', '100');
INSERT INTO `BANK_ACCOUNT` VALUES ('2', 'Jerry', '100');
```

---

### 3. 数据库连接配置

在 application.yml 中配置：

```yaml
spring:
  # 数据源配置
  datasource:
    url: jdbc:mysql://127.0.0.1/test
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    # 线程池配置
    hikari:
      auto-commit: true          # 自动提交
      connection-timeout: 30000  # 连接超时
      idle-timeout: 600000       # 连接在池中闲置的最长时间
      max-lifetime: 1800000      # 连接在池中最长的生命周期
      minimum-idle: 30           # 池中维护的最小空闲连接数
      maximum-pool-size: 30      # 池中维护的最大连接数
```

> Spring Boot 2.0 开始默认的数据库连接池不再是`Tomcat JDBC Pool`，而默认采用的是`HikariCP`。

---

### 4. 数据访问层

```java
@Data
public class BankAccount {

    private int id;

    private String name;

    private double balance;

}
```

```java
@Repository
public class BankAccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询所有
    public List<BankAccount> findAll() {
        return jdbcTemplate.query("SELECT * FROM BANK_ACCOUNT", new BankAccountMapper());
    }

    // 根据ID查询
    public BankAccount findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM BANK_ACCOUNT WHERE ID = ?", new Object[]{id}, new BankAccountMapper());
    }

    // 根据名称查询
    public BankAccount findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM BANK_ACCOUNT WHERE NAME = ?", new Object[]{name}, new BankAccountMapper());
    }

    // 保存, 返回成功的行数
    public int insert(BankAccount account) {
        return jdbcTemplate.update("INSERT INTO BANK_ACCOUNT(NAME, BALANCE) VALUE (?, ?)", account.getName(), account.getBalance());
    }

    // 保存, 返回成功的行数, 并设置对象的ID
    public int save(BankAccount account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO BANK_ACCOUNT(NAME, BALANCE) VALUE (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getName());
                ps.setDouble(2, account.getBalance());
                return ps;
            }
        }, keyHolder);
        account.setId(keyHolder.getKey().intValue());
        return result;
    }

    // 更新
    public int update(BankAccount account) {
        return jdbcTemplate.update("UPDATE BANK_ACCOUNT SET NAME = ?, BALANCE = ? WHERE ID = ?", account.getName(), account.getBalance(), account.getId());
    }

    // 批量更新
    public int[] batchUpdate(List<BankAccount> accounts) {
        return jdbcTemplate.batchUpdate("UPDATE BANK_ACCOUNT SET NAME = ?, BALANCE = ? WHERE ID = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        BankAccount account = accounts.get(i);
                        ps.setString(1, account.getName());
                        ps.setDouble(2, account.getBalance());
                        ps.setInt(3, account.getId());
                    }
                    @Override
                    public int getBatchSize() {
                        return accounts.size();
                    }
                });
    }

    // 删除
    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM BANK_ACCOUNT WHERE ID = ?", id);
    }

    // 行包装器
    static class BankAccountMapper implements RowMapper<BankAccount> {
        @Override
        public BankAccount mapRow(ResultSet rs, int i) throws SQLException {
            BankAccount account = new BankAccount();
            account.setId(rs.getInt("id"));
            account.setName(rs.getString("name"));
            account.setBalance(rs.getDouble("balance"));
            return account;
        }
    }

}
```

```java
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
```

---

### 5. 单元测试

```java
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
```