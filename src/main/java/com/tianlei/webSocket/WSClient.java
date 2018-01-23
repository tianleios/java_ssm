package com.tianlei.webSocket;

import org.springframework.http.HttpHeaders;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.AbstractWebSocketClient;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by tianlei on 2017/十一月/30.
 */
public class WSClient extends AbstractWebSocketClient {

    @Override
    protected ListenableFuture<WebSocketSession> doHandshakeInternal(WebSocketHandler webSocketHandler, HttpHeaders httpHeaders, URI uri, List<String> list, List<WebSocketExtension> list1, Map<String, Object> map) {
        return null;
    }

    public WSClient() {
        super();
    }

    @Override
    public ListenableFuture<WebSocketSession> doHandshake(WebSocketHandler webSocketHandler, String uriTemplate, Object... uriVars) {
        return super.doHandshake(webSocketHandler, uriTemplate, uriVars);
    }

    @Override
    protected void assertUri(URI uri) {
        super.assertUri(uri);
    }
}
