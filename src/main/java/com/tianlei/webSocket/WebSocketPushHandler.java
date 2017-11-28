package com.tianlei.webSocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianlei on 2017/十一月/28.
 */
// 处理信息的发送

public class WebSocketPushHandler implements WebSocketHandler {

    private static final List<WebSocketSession> users = new ArrayList<>();

    // 用户进入系统
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

        //
        users.add(webSocketSession);

    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

        //获取信息
        webSocketMessage.getPayload();
//         webSocketSession.sendMessage();
    }

    //后台错误信息处理方法
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {


    }

    //用户退出后的处理
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        //
        users.remove(webSocketSession);
    }

    //是否支持大消息
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
