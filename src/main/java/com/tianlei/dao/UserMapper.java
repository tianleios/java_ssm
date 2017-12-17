package com.tianlei.dao;

import com.tianlei.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tianlei on 2017/8/2.
 */
public interface UserMapper {

//    @Results(id = "userMap" ,value = {
//            @Result(property = "id", column = "id", id = true),
//            @Result(property = "phone", column = "phone"),
//            @Result(property = "username", column = "username"),
//            @Result(property = "password", column = "password"),
//            @Result(property = "updateTime", column = "update_time"),
//            @Result(property = "createTime", column = "create_time")
//    })
//    @Select("SELECT * FROM `user`")
    int insertUser(User user);
    int changePwd(@Param("userId") int userId,@Param("password") String password); //修改哪个用户的pwd
    User getUserByMobile(String mobile);
    List<User> getUsers();
    List<User> getUsersByPage(@Param("start") int start, @Param("limit") int limit);

    int checkUser(String phone);
    int updateUserByPrimaryKeyScentive(User user);
    int updateUserByPrimaryKey(User user);

    User getUserById(Integer id);



}
