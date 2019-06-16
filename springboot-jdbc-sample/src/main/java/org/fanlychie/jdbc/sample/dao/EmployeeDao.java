package org.fanlychie.jdbc.sample.dao;

import org.fanlychie.jdbc.sample.model.Employee;
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
 * Created by fanlychie on 2019/6/5.
 */
@Repository
public class EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEE", new EmployeeMapper());
    }

    public Employee findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE WHERE NAME = ?", new Object[]{name}, new EmployeeMapper());
    }

    public int insert(Employee e) {
        return jdbcTemplate.update("INSERT INTO EMPLOYEE(NAME, AGE) VALUE (?, ?)", e.getName(), e.getAge());
    }

    public int save(Employee e) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO EMPLOYEE(NAME, AGE) VALUE (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, e.getName());
                ps.setInt(2, e.getAge());
                return ps;
            }
        }, keyHolder);
        e.setId(keyHolder.getKey().longValue());
        return result;
    }

    public int update(Employee e) {
        return jdbcTemplate.update("UPDATE EMPLOYEE SET NAME = ?, AGE = ? WHERE ID = ?", e.getName(), e.getAge(), e.getId());
    }

    public int[] batchUpdate(List<Employee> es) {
        return jdbcTemplate.batchUpdate("UPDATE EMPLOYEE SET NAME = ?, AGE = ? WHERE ID = ?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Employee e = es.get(i);
                        ps.setString(1, e.getName());
                        ps.setInt(2, e.getAge());
                        ps.setLong(3, e.getId());
                    }
                    @Override
                    public int getBatchSize() {
                        return es.size();
                    }
                });
    }

    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM EMPLOYEE WHERE ID = ?", id);
    }

    static class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setAge(rs.getInt("age"));
            employee.setName(rs.getString("name"));
            return employee;
        }

    }

}