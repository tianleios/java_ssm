package com.tianlei;

//import com.alibaba.fastjson.JSON;
import com.tianlei.common.Response;
import com.tianlei.common.ServerResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianlei on 2017/8/3.
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//      resp.setContentType("text/html");
//      resp.setCharacterEncoding("utf-8");

        Map m = new HashMap();
        m.put("name","田磊");



      PrintWriter out = resp.getWriter();
//        out.print(JSON.toJSONString(Response.failure(1,"自己的错误")));
//        out.print("\n");
//        out.print(JSON.toJSONString(ServerResponse.createByError()));
//        out.print("\n");
//        out.print(JSON.toJSONString(ServerResponse.createByErrorMessage("1这是一个错误的信息")));
//        out.print("\n");
//        out.print(JSON.toJSONString(new ServerResponse(1,"消息",m)));
//        out.print("\n");
//        out.print(JSON.toJSONString(new Response(1,"我的消息",m)));


    }

}
