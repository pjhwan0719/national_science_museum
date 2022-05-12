/*
package com.avad.nationalScienceMuseum.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

*/
/**
 * PJH
 * - WebSocket handler
 *//*

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    */
/**
     * PJH
     * - Client가 WebSocket server 접속 시 동작.
     * @param session
     * @throws Exception
     *//*

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    */
/**
     * PJH
     * - Client가 WebSocket server 접속이 끊길시 동작.
     * @param session
     * @param status
     * @throws Exception
     *//*

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }


    */
/**
     * PJH
     * - Client Message 수신 시 동작.
     * @param session
     * @param message
     * @throws Exception
     *//*

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("handleTextMessage");
        String id = session.getId();  //메시지를 보낸 아이디
    }
}
*/
