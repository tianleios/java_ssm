package com.tianlei.jwt;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

//import org.apache.commons.;

/**
 * Created by tianlei on 2017/十二月/28.
 */
public class Jwt {

    public static void main(String[] args) {

        String jwt =  getJwt("userId",1000*3600*24);

        try {

            String userId = getUserId(jwt);

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private static SecretKey secretKey = generalKey();
    private static synchronized SecretKey generalKey() {

//        String stringKey = "c8569ba74c474ca4512c8569ba74c474ca4512aca7c13287df8c8569ba74c474ca4512aca7c13287df8aca7c13287df8c8569ba74c474ca4512aca7c13287df8";
//        byte[] encodedKey = Base64.decodeBase64(stringKey);
//        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//        return key;
        return null;

    }


    // userId ， 有效的时间 单位:ms
    public static String getJwt(String userId, long timeInterval) {

        if (userId == null || userId.length() <= 0) {
            return null;
        }

        Key key = Jwt.secretKey;
        //
        String sub = userId;
        JwtBuilder jwtBuilder = Jwts.builder();

        //1. Reserved claims 标准的声明

        //面向的用户
        jwtBuilder.setSubject(sub);

        //签发者
        jwtBuilder.setIssuer("admin");
        //观众
        jwtBuilder.setAudience("");
        // jwt的签发时间
        jwtBuilder.setIssuedAt(new Date());
        // 延迟生效的时间
        jwtBuilder.setNotBefore(new Date());

        //失效时间
//      long timeinterval = 24 * 3600 *1000;
        long timeinterval = timeInterval; //
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + timeinterval));
        // jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
        jwtBuilder.setId("");
        //2. Public claims: 共有的
        //3. Private claims: 私有的

        //签名并获取结果,
        // 结果大概为这样：  第一部分.第二部分.第三部分
        // 第一部分 可以直接base64 解码
        // 第二部分 可以直接base64 解码
        // 第三部分 由第一部分 + 第二部分 + 私钥 运算得到
        String compactJws = jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
        return compactJws;
    }

    public static String getUserId(String compactJws) throws Exception {

        // 以下代码为反向解析
        SecretKey key = Jwt.secretKey;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);
        //获取结果
        String resultStr = claimsJws.getBody().getSubject();
        return resultStr;

    }

}
