package com.tianlei.service.impl;

import com.tianlei.common.Response;
import com.tianlei.dao.UserMapper;
import com.tianlei.pojo.User;
import com.tianlei.service.IUserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.*;


/**
 * Created by tianlei on 2017/8/3.
 */

@Transactional
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public Response<List<User>> getUsers() {

        return Response.success(this.userMapper.getUsers());

    }

    public Response<List<User>> getUsersByPage(int start, int limit) {

        UserMapper userMapper = null;
        try {

            String source = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(source);

            SqlSessionFactory sqlSessionFactory = new  SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            userMapper = sqlSession.getMapper(UserMapper.class);

        } catch (Exception e) {

            e.printStackTrace();

        }


        return Response.success(userMapper.getUsersByPage(start,limit));

    }


    public static Boolean validatorObj(Object obj) throws Exception {

        if (obj == null) {
            return Boolean.FALSE;
        }

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(obj);

        StringBuilder stringBuilder = new StringBuilder();
        if (set.size() > 0) {

            Iterator<ConstraintViolation<Object>> iterator = set.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation<Object> constraintViolation =  iterator.next();
             Path path = constraintViolation.getPropertyPath(); //属性名称

             String str =  constraintViolation.getMessageTemplate(); //注释的信息
                //throw  抛出异常
                stringBuilder.append(str);
            }
            throw new Exception(stringBuilder.toString());

        } else  {
            return Boolean.TRUE;
        }
    }

    public Response<String> regUser( User user) {

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> set = validator.validate(user);
        if (set.size() > 0) {

            Iterator<ConstraintViolation<User>> iterator = set.iterator();
            if (iterator.hasNext()) {
               ConstraintViolation<User> constraintViolation =  iterator.next();
               constraintViolation.getPropertyPath(); //属性名称
               constraintViolation.getMessageTemplate(); //注释的信息
               //throw  抛出异常
            }

        }

        //1.校验参数是否完备
        //2.检查用户是否存在
        //3.注册
        UserMapper userMapper = null;
        SqlSession sqlSession = null;
        try {

            String source = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(source);

            SqlSessionFactory sqlSessionFactory = new  SqlSessionFactoryBuilder().build(inputStream);
            //autoCommit, 打开了事务 注意提交
            sqlSession = sqlSessionFactory.openSession(true);
            userMapper = sqlSession.getMapper(UserMapper.class);

        } catch (Exception e) {

           e.printStackTrace();

        }

        int count = userMapper.insertUser(user);
        if (count == 1) {

          return Response.success("注册成功");

        }

        return Response.failure(1,"注册失败");
    }

    public Response<User> getUserByMobile(String mobile) {
        if (mobile == null) {
            return Response.failure(1,"手机号不能为空");
        }

      User user = this.userMapper.getUserByMobile(mobile);
        if (user == null) {
            return Response.failure(1,"不存在该用户");
        }
        return Response.success(user);
    }

    public Response changeUserPassword(String mobile, String password) {


        User user = this.userMapper.getUserByMobile(mobile);
        if (user == null) {
            return Response.failure(1,"用户不存在");
        }


     int count =  userMapper.changePwd(user.getId(),password);
        if (count == 1) {
            return Response.success("修改成功");
        } else  {

            return Response.failure(1,"修改失败");

        }

    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }
}
