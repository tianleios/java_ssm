package com.btc.controller;

import com.btc.service.IBTCService;
import com.tianlei.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianlei on 2018/一月/22.
 */

@Controller
@RequestMapping("/btc")
public class BTCController {

    @Autowired
    private IBTCService btcService;

    /*
    1.地址生成
     */
    @GetMapping("/address")
    public Response address() {

        String address = this.btcService.address(true);
        Map map = new HashMap();
        map.put("address",address);
        return Response.success(map);

    }

}
