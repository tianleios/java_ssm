package com.tianlei.webSocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * Created by tianlei on 2017/十一月/28.
 */
public class MyWebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    //握手之前
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;

//            HttpServletRequest httpRequest = servletRequest.getServletRequest();
//            //Constants.CURRENT_USER这个是我定义的常量，是request域的key，通过key就可以获取到用户信息了
//            TsUser user = (TsUser)httpRequest.getAttribute(Constants.CURRENT_USER);
//            //Constants.CURRENT_WEBSOCKET_USER也是常量，用来存储WebsocketSession的key值
//            attributes.put(Constants.CURRENT_WEBSOCKET_USER,user.getUserid());
        }

        boolean is = super.beforeHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, map);
        return is;

    }

    //握手之后
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

        super.afterHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, e);

    }

}
