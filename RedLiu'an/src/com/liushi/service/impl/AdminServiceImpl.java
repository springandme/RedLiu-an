package com.liushi.service.impl;

import com.liushi.dao.AdminDao;
import com.liushi.dao.impl.AdminDaoImpl;
import com.liushi.domain.Admin;
import com.liushi.service.AdminService;
import org.junit.Test;

/**
 * @ClassName AdminServiceImpl
 * @Description TODO
 * @Author liushi
 * @Date 2020/6/6 9:40
 * @Version V1.0
 **/
public class AdminServiceImpl  implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin login(Admin admin) throws Exception {
        return adminDao.findUsernameAndPassword(admin.getUsername(),admin.getPassword());
    }

    @Test
    public void test() throws Exception {
        Admin admin = adminDao.findUsernameAndPassword("root", "root");
        System.out.println(admin);
    }
}
