package com.harukaze.costume.app;

import com.harukaze.costume.app.dao.UserDao;
import com.harukaze.costume.app.dao.UserRoleDao;
import com.harukaze.costume.app.entity.UserEntity;
import com.harukaze.costume.app.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: com.harukaze.costume.test
 * @ClassName: MybatisTest
 * @Description:
 * @Author: doki
 * @Date: 2022/6/1 16:41
 */
@SpringBootTest
public class MybatisTest {
    @Autowired
    UserDao userDao;

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    WareGoodsService wareGoodsService;

    @Test
    public void testUser() {
        UserEntity userEntity = userDao.selectById(1);
        System.out.println(userEntity.toString());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testCaptcha() {
        System.out.println(loginService.getCaptcha());
    }

    @Test
    public void testPassword() {
        String sdsdsd = passwordEncoder.encode("123456");
        System.out.println(sdsdsd);
        System.out.println(passwordEncoder.matches("sdsdsd", sdsdsd));
    }

    @Test
    public void testPermission() {
//        System.out.println(userRoleDao.selectRole(1L));
        System.out.println(rolePermissionService.listPermission(1L));
    }

//    @Test
//    public void testGoodsList() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("limit", "5");
//        map.put("page", "1");
//        System.out.println(goodsService.listGoodsPage(map).getList());
//    }

    @Test
    public void testListUser() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", "ad");
        System.out.println(userService.listUserPage(map).getList());
    }

    @Test
    public void testWareGoods() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "s");
        map.put("ware", "汉");
        System.out.println(wareGoodsService.queryWareGoodsPage(map).getList());
    }

    @Test
    public void testDeleteRolePermission() {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", "1");
        map.put("permissionId", "2");
        rolePermissionService.removeRolePermission(map);
    }

    @Test
    public void testDeleteUserRole() {
        String str1 = "hello";

        String str2 = "hell" + new String("o");
        str2 = str2.intern();

        System.out.println(str1 == str2);
    }
}
