package com.springboot.test;

import com.springboot.dao.UserDao;
import com.springboot.pojo.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserDao userDao;

    /**
     * 测试标准：有继承自Mapper单表的各种方法
     */
    @Test
    public void connectionDbTest(){
        SysUser sysUser = new SysUser();
        sysUser.setAreaId(10L);
        List<SysUser> select = userDao.select(sysUser);
        userDao.selectAll();
        userDao.deleteByExample(sysUser);


    }
}
