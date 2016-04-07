package eti.zako.jetty.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {

    public static void main(String[] args) throws Exception {
        
        Server server = new Server(10000);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        
        DefaultServlet defaultServlet = new DefaultServlet();
        ServletHolder holder = new ServletHolder("default", defaultServlet);
        holder.setInitParameter("resourceBase", "./src/main/webapp/");
        
        context.addServlet(holder, "/*");
        
        server.setHandler(context);
        
        server.start();
        server.join();
    }
    
}
