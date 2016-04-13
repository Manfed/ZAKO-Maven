package eti.zako.jetty.websockets;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class AirportWebSocket {
    
    private Session session;
    
    @OnWebSocketConnect
    public void handleConnect(Session session) {
        this.session = session;
        System.out.println("Test");
    }
    
    @OnWebSocketClose
    public void handleClose(int statusCode, String reason) {
        System.out.println("Connection closed with statusCode=" 
            + statusCode + ", reason=" + reason);
            this.stop();
    }
    
    // called when a message received from the browser
    @OnWebSocketMessage
    public void handleMessage(String message) {
    }
 
    // called in case of an error
    @OnWebSocketError
    public void handleError(Throwable error) {
        error.printStackTrace();    
    }
 
    // sends message to browser
    private void send(String message) {
        try {
            if (session.isOpen()) {
                session.getRemote().sendString(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // closes the socket
    private void stop() {
        try {
            session.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
