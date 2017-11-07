package com.tianlei.service;

import com.tianlei.common.Response;
import com.tianlei.pojo.User;

import java.util.List;

/**
 * Created by tianlei on 2017/8/3.
 */
public interface IUserService {

    public Response<String> regUser( User user);
    public Response<User> getUserByMobile(String mobile);
    public Response changeUserPassword(String mobile, String password);
    public Response<List<User> > getUsers();
    public Response<List<User> > getUsersByPage(int start,int limit);


}
