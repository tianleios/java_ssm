//package com.tianlei.webSocket;
//
////import com.alibaba.fastjson.JSON;
//
//import com.azazar.krotjson.JSON;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
//import org.springframework.web.socket.handler.AbstractWebSocketHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * Created by tianlei on 2017/十一月/28.
// */
//// 处理信息的发送
//public class WebSocketPushHandler extends AbstractWebSocketHandler {
//
////    private static final List<WebSocketSession> users = new ArrayList<>();
//
//    private static final Map<String, WebSocketSession> users = new HashMap<>();
//
//    public void sendTo(String userId, String msg) throws Exception {
//
//        WebSocketSession webSocketSession = users.get(userId);
//        if (webSocketSession == null) {
//            return;
//        }
//
//        this.sendToWSSession(webSocketSession, msg);
//
//    }
//
//    public void sendToWSSession(WebSocketSession webSocketSession, String msg) {
//
//        StandardWebSocketSession standardWebSocketSession = (StandardWebSocketSession) webSocketSession;
//
//        //异步的发送消息
//        standardWebSocketSession.getNativeSession().getAsyncRemote().sendText(msg);
//    }
//
//    // 用户进入系统
//    @Override
//    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
//
//        // 可以在 拦截器 中设置
//        Map map = webSocketSession.getAttributes();
//
//        //
//        WebSocketSession lastWS = users.get("");
//        if (users.get("") != null) {
//            if (lastWS.isOpen()) {
//                //存在已经存在的连接，关闭掉
//                lastWS.close();
//            }
//        }
//        users.put("", webSocketSession);
//
//        StandardWebSocketSession standardWebSocketSession = (StandardWebSocketSession) webSocketSession;
//        standardWebSocketSession.getNativeSession().getBasicRemote().sendText("发送消息的内容");
//
//    }
//
//    //后台错误信息处理方法
//    @Override
//    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
//
//        int a = 10;
//
//    }
//
//    //用户退出后的处理
//    @Override
//    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
//
//        if (webSocketSession.isOpen()) {
//            webSocketSession.close();
//        }
//        //
//        users.remove(webSocketSession);
//    }
//
//    //是否支持大消息
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//        //获取信息
//        String str = message.getPayload();
//        Map map = JSON.parseObject(str, Map
//                .class);
//        if (map.get("event") != null) {
//
//            String uid = (String) map.get("uid");
//            users.put(uid, session);
//
//        }
//
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//
//                for (String string : users.keySet()) {
//
//                    sendToWSSession(users.get(string), string);
//
//                }
//
//            }
//        }, 0, 1000);
//
//
//    }
//
//    //处理 二进制文件
//    @Override
//    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
//
//    }
//
//    //处理  pong  message
//    @Override
//    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
//
//        int a = 10;
//
//    }
//}
