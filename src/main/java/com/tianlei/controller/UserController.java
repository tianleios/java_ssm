package com.tianlei.controller;

import com.tianlei.common.BigDecimalUtil;
import com.tianlei.common.Response;
import com.tianlei.pojo.Product;
import com.tianlei.pojo.User;
import com.tianlei.service.IUserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Key;
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

    public static void main(String[] args) {

        //问题： 怎样实现 切换终端登录，以前的哪个要失效

        //签名的key 前2部分, 用该key签名，得到协议的第三部分
        // key应该固定
        // 生成一个特殊的key
        //  xxxxx.yyyyy.zzzzz
        //  Header
        //  Payload
        //  Signature
        // 保护好这个私钥，这个私钥很重要
        Key key = MacProvider.generateKey();

        //
        String sub = "userId";

        //完整的签名结果


        JwtBuilder jwtBuilder =  Jwts.builder();


        //1. Reserved claims 标准的声明
         //主题
        jwtBuilder.setSubject(sub);
         //签发者
        jwtBuilder.setIssuer("");
         //观众
        jwtBuilder.setAudience("");
        // jwt的签发时间
        jwtBuilder.setIssuedAt(new Date());
        // 延迟生效的时间
        jwtBuilder.setNotBefore(new Date());
         //失效时间，设为一天
        jwtBuilder.setExpiration( new Date(new Date().getTime() + 24*3600));
        // jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
        jwtBuilder.setId("");


        //2. Public claims: 共有的

        //3. Private claims: 私有的

        //签名并获取结果
        String compactJws = jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();

        try {

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);
            //获取结果
            String resultStr = claimsJws.getBody().getSubject();
            // 加入resultStr 为userId, 就可以把userId取出来
            if (resultStr.equals(sub)) {



            }

        } catch (SignatureException e) {
            //验证失败

        }
        System.out.print("");


    }

    //
    @ResponseBody
    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public Response userRegister(String phone, String username, String password) {

        int a = 10;
//        this.iUserService.getUsers();
        this.iUserService.validTest(phone);
      /*  if (phone == null || username == null || password == null) {

            return Response.failure(0,"参数不能为空");

        }*/
//        User user = new User();
//        user.setPhone(phone);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());
//        //
//        return iUserService.regUser(user);

        return new Response(1,"3",null);
    }

    //
    @ResponseBody
    @RequestMapping(path = "/change", method = RequestMethod.POST)
    public Response changPassword(String phone, String password) {

        return iUserService.changeUserPassword(phone, password);

    }

    @ResponseBody
    @RequestMapping(path = "/info", method = RequestMethod.POST)
    public Response getUserInfoByMobile(String phone) {

        return iUserService.getUserByMobile(phone);

    }

    @ResponseBody
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Response<List<User>> getUsers() {

        return iUserService.getUsers();

    }

    @ResponseBody
    @RequestMapping(path = "/page", method = RequestMethod.POST)
    public Response<List<User>> getUsersByPage(@RequestParam(required = true) int start, @RequestParam(required = true) int limit) {

        return iUserService.getUsersByPage(start, limit);

    }

    @ResponseBody
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public User test(String tag, String isTx) throws Exception {

        Integer integer = new Integer(1);
        Product product = new Product();
        product.setStock(integer);

        float a = 1.21233f;
        float b = 1.23339f;
        float sumAB = a + b;
        float muliAB = a * b;

        BigDecimal mulDecimal = BigDecimalUtil.mul(1.21233, 1.23339);


        User user = new User();
        user.setUsername("名字");

        this.iUserService.txTest(tag,isTx);
        return user;
    }

    @RequestMapping(path = "/jsp", method = RequestMethod.GET)
    public ModelAndView JSPTest() {

        ModelAndView mav = new ModelAndView("/index.jsp");
        mav.addObject("name", "田磊");
        return mav;

    }


}


