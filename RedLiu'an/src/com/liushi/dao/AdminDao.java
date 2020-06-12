package com.liushi.dao;

import com.liushi.domain.Admin;

/**
 * @ClassName AdminDao
 * @Description TODO
 * @Author liushi
 * @Date 2020/6/6 9:37
 * @Version V1.0
 **/
public interface AdminDao {

    Admin findUsernameAndPassword(String username,String password) throws Exception;

}
