package com.btc.demo.controller;

import com.tianlei.common.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tianlei on 2018/一月/22.
 */

@Controller
@RequestMapping("/eth")
public class BTCController {

    /*
    1.地址生成
     */
   @GetMapping("/address")
   public Response address(){

       return Response.success("");

   }

}
