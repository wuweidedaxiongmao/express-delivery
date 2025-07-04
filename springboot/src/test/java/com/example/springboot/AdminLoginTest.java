
package com.example.springboot;

import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.AdminService;
import com.example.springboot.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminLoginTest {

    //依赖注入
    @Autowired
    private AdminService adminService;

    @Test
    public void testLoginSuccess() {
        Account account = new Account();
        account.setUsername("admin");
        account.setPassword("admin");

        Admin admin = adminService.login(account);
        assertNotNull(admin, "管理员登录应成功返回对象");
    }

    @Test
    public void testLoginFailWrongPassword() {
        Account account = new Account();
        account.setUsername("admin");
        account.setPassword("wrongpassword");

        assertThrows(CustomException.class, () -> {
            adminService.login(account);
        });
    }

    @Test
    public void testLoginFailUserNotExist() {
        Account account = new Account();
        account.setUsername("nonexistent");
        account.setPassword("123456");

        assertThrows(CustomException.class, () -> {
            adminService.login(account);
        });
    }
}
