package io.acordi.autumn.web;


import io.acordi.autumn.util.Logger;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.logging.Level;

public class AutumnWebApplication {

    public static void run(){
        java.util.logging.Logger.getLogger("org.apache").setLevel(Level.OFF);
        try{
            long start = System.currentTimeMillis(), end;
            Logger.showBanner();
            Logger.info(AutumnWebApplication.class, "Starting Autumn Web Application");
            Tomcat tomcat = new Tomcat();
            Connector connector = tomcat.getConnector();
            connector.setPort(8080);
            tomcat.setConnector(connector);

            Context ctx = tomcat.addContext("", null );

            Tomcat.addServlet(ctx, "AutumnDispatchServlet", new AutumnDispatchServlet());
            ctx.addServletMappingDecoded("/*", "AutumnDispatchServlet");

            tomcat.start();
            end = System.currentTimeMillis();
            double delay = (double)(end - start) / 1000;
            Logger.info( AutumnWebApplication.class, "Autumn Web Application started in " + delay + " seconds" );
            tomcat.getServer().await();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
