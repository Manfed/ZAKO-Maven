package eti.zako.jetty.websockets;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.hibernate.controller.HibernateController;

import com.google.gson.Gson;

import eti.zako.json.FormData;
import eti.zako.json.JSONBuilder;
import eti.zako.sqlite.model.Airport;

@WebSocket
public class AirportWebSocket {
    
    private Session session;
    
    @OnWebSocketConnect
    public void handleConnect(Session session) {
        this.session = session;
        List<Airport> airports = HibernateController.<Airport>getDataList("Airport", "");
        String autocompleteJson = JSONBuilder.prepareAutocompleteJSON(airports);
        String markersJson = JSONBuilder.prepareMarkersJSON(airports);
        send(autocompleteJson);
        send(markersJson);
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
        Pattern formPattern = Pattern.compile("\\{\"form\": \\{.*");
        if(formPattern.matcher(message).find()) {
            FormData form = extractFormData(message);
            String pathJson = form.parseForm();
            send(pathJson);
        }
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
    
    private FormData extractFormData(String message) {
        Gson gson = new Gson();
        FormData formData = gson.fromJson(message, FormData.class);
        return formData;
    }
}
