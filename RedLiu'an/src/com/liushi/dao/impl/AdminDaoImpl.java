package com.liushi.dao.impl;

import com.liushi.dao.AdminDao;
import com.liushi.domain.Admin;
import com.liushi.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @ClassName AdminDaoImpl
 * @Description TODO
 * @Author liushi
 * @Date 2020/6/6 9:38
 * @Version V1.0
 **/
public class AdminDaoImpl implements AdminDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Admin findUsernameAndPassword(String username, String password) throws Exception {
        try {
            String sql="select * from admin where username = ? and password = ? ";
            Admin admin = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Admin>(Admin.class), username,
                    password);
            return admin;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Admin> findAll() {
        String sql="select * from admin";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Admin>(Admin.class));
    }

    @Override
    public Admin findOne(String name) {
        try {
            String sql="select * from admin where name = ?";
            Admin admin = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Admin>(Admin.class), name);
            return admin;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
