package io.acordi.autumn.web;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class AutumnWebApplication {

    public static void run(){
        try{
            Tomcat tomcat = new Tomcat();
            Connector connector = tomcat.getConnector();
            connector.setPort(8080);
            tomcat.setConnector(connector);

            Context ctx = tomcat.addContext("", new File(".").getAbsolutePath() );

            Tomcat.addServlet( ctx, "AutumnDispatchServlet", new AutumnDispatchServlet() );

            ctx.addServletMappingDecoded("/*", "AutumnDispatchServlet");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
