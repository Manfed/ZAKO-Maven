package eti.zako.jetty.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import eti.zako.jetty.servlets.AirportHttpServlet;
import eti.zako.jetty.servlets.AirportWebSocketServlet;

public class JettyServer {

    public static void main(String[] args) throws Exception {

        Server server = new Server(10000);

        //konfiguracja servletu odpowiadajacego za wyswietlenie strony
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        AirportHttpServlet servlet = new AirportHttpServlet();
        ServletHolder holder = new ServletHolder("airport", servlet);
        holder.setInitParameter("resourceBase", "./src/main/webapp/");
        holder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
        holder.setInitParameter("com.sun.jersey.config.property.packages", "eti.zako.restful");
        holder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

        context.addServlet(holder, "");

        //dodanie socketa

        AirportWebSocketServlet wsServlet = new AirportWebSocketServlet();
        ServletHolder wsHolder = new ServletHolder("socket", wsServlet);
        context.addServlet(wsHolder, "/socket");

        server.setHandler(context);

        server.start();
        server.join();
    }

}
