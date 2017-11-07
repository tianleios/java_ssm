package com.tianlei.controller;

import com.tianlei.common.BigDecimalUtil;
import com.tianlei.common.Response;
import com.tianlei.pojo.Product;
import com.tianlei.pojo.User;
import com.tianlei.service.IUserService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by tianlei on 2017/7/30.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //
    @Autowired
    private IUserService iUserService;
    //
    //
    @ResponseBody
    @RequestMapping(path = "/reg",method = RequestMethod.POST)
    public Response userRegister(String phone, String username, String password){
      /*  if (phone == null || username == null || password == null) {

            return Response.failure(0,"参数不能为空");

        }*/
        User user = new User();
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        //
        return iUserService.regUser(user);
    }

    //
    @ResponseBody
    @RequestMapping(path = "/change",method = RequestMethod.POST)
    public Response changPassword(String phone, String password) {

     return iUserService.changeUserPassword(phone,password);

    }

    @ResponseBody
    @RequestMapping(path = "/info",method = RequestMethod.POST)
    public Response getUserInfoByMobile(String phone) {

        return iUserService.getUserByMobile(phone);

    }

    @ResponseBody
    @RequestMapping(path = "/list",method = RequestMethod.GET)
    public Response<List<User>> getUsers() {

        return iUserService.getUsers();

    }

    @ResponseBody
    @RequestMapping(path = "/page",method = RequestMethod.POST)
    public Response<List<User>> getUsersByPage(@RequestParam(required = true) int start,@RequestParam(required = true) int limit) {

        return iUserService.getUsersByPage(start,limit);

    }

    @ResponseBody
    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public User test(){

        Integer integer = new Integer(1);
        Product product = new Product();
        product.setStock(integer);

        float a = 1.21233f;
        float b = 1.23339f;
        float sumAB = a + b;
        float muliAB = a*b;

        BigDecimal mulDecimal = BigDecimalUtil.mul(1.21233,1.23339);


        User user = new User();
        user.setUsername("名字");
        return user;
    }

    @RequestMapping(path = "/jsp",method = RequestMethod.GET)
    public ModelAndView JSPTest(){

        ModelAndView mav = new ModelAndView("/index.jsp");
        mav.addObject("name","田磊");
        return mav;

    }




}


