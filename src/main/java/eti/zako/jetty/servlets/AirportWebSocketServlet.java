package eti.zako.jetty.servlets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import eti.zako.jetty.websockets.AirportWebSocket;

public class AirportWebSocketServlet extends WebSocketServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    public void configure(WebSocketServletFactory factory) {
            factory.register(AirportWebSocket.class);
    }
}
