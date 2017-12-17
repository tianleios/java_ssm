package com.tianlei.dao;

import com.tianlei.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tianlei on 2017/十二月/02.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@Transactional
public class UserMapperTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    UserMapper userMapper;

    @Test
    //事务进行提交
    @Transactional(rollbackFor = Exception.class)
    @Rollback(value = false)
    public void updateUserByPrimaryKey() throws Exception {

        String tag = "28";

        User user = new User();
        user.setId(1);
        user.setUsername("username" + tag);
        this.userMapper.updateUserByPrimaryKeyScentive(user);

        if (1 == 1) {
            throw new  Exception("xxx修改失败xxx" + tag);
        }

        user.setPassword("password" + tag);
        this.userMapper.updateUserByPrimaryKeyScentive(user);

    }

}