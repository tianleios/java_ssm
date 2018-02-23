package com.tianlei.controller;

import com.tianlei.common.Encrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianlei on 2017/十月/13.
 */

@Controller
@RequestMapping("/encrypt")
public class EncryptController {

    @ResponseBody
    @GetMapping("/md5/{str}")
    public Object md5(@PathVariable("str") String str){

//        return "123";
        Map map = new HashMap();
        map.put("str",str);
        map.put("md5",Encrypt.md5(str));
        map.put("sha256",Encrypt.sha256(str));

        //

      return  map;

    }

    @ResponseBody
    @GetMapping("/targetHash")
    public Object targetHash() throws ParseException {

        int coefficient = 0x015dcc;
        int exponent = 0x18;
        double targetHash = coefficient*Math.pow(2,8*(exponent - 3));

        //
        int targetResultHash = coefficient*(2 << (8*(exponent - 3) - 1));


        String str = Encrypt.sha256("show me the money8");
        Double.doubleToLongBits(targetHash);
//        return "123";
        Map map = new HashMap();
        map.put("hex_hash",Double.toHexString(targetHash));
        //

        //477,016  高度
String version = "20000012";
        String merkleRoot = "f465b1cd6fb5d23cf896418c8171a9dbe66769e369a8cc308c77021a4a105de6";
        String preBlockHash = "f465b1cd6fb5d23cf896418c8171a9dbe66769e369a8cc308c77021a4a105de6";
        String nonce = "a610d90c";
        String diffTarget = "18015dcc";
        //
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long timeStamp = simpleDateFormat.parse("2017-07-22 22:50:19").getTime();

    //
//        hash=SHA256(SHA256(区块头))
//        区块头=十六进制小端结尾(版本信息+上一个区块头+根merkle+时间戳+难度目标+nonce值)
     String block1 =  version + preBlockHash + merkleRoot + "2b667359" + diffTarget +nonce;
     String rsultHash = Encrypt.sha256(Encrypt.sha256(block1));



        return  map;

    }


}
