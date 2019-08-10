package org.fanlychie.jdbc.sample.dao;

import org.fanlychie.jdbc.sample.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by fanlychie on 2019/6/24.
 */
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