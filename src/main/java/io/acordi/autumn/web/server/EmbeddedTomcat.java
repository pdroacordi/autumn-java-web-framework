package io.acordi.autumn.web.server;

import io.acordi.autumn.util.Logger;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.util.logging.Level;

public class EmbeddedTomcat {

    public static void start(){
        java.util.logging.Logger.getLogger("org.apache").setLevel(Level.OFF);
        try {
            Logger.info(EmbeddedTomcat.class, "Starting Autumn Web Application");
            long start = System.currentTimeMillis(), end;

            Tomcat tomcat = new Tomcat();
            Connector connector = tomcat.getConnector();
            connector.setPort(8080);
            tomcat.setConnector(connector);
            Logger.info(AutumnWebApplication.class, "Web container initialized on port 8080");

            Context ctx = tomcat.addContext("", null);

            Tomcat.addServlet(ctx, "AutumnDispatchServlet", new AutumnDispatchServlet());
            ctx.addServletMappingDecoded("/*", "AutumnDispatchServlet");

            tomcat.start();

            end = System.currentTimeMillis();
            double delay = (double) (end - start) / 1000;
            Logger.info(EmbeddedTomcat.class, "Autumn Web Application started in " + delay + " seconds");
            tomcat.getServer().await();
        }catch (Exception e){
            Logger.error(EmbeddedTomcat.class, "Autumn Web Application failed to start: "+e.getMessage());
        }
    }
}
