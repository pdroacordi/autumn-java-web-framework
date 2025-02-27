package io.acordi.autumn.web.http;

import com.google.gson.Gson;
import io.acordi.autumn.util.Logger;
import io.acordi.autumn.web.routing.Route;
import io.acordi.autumn.web.routing.RouteRegistry;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;


public class RequestHandler {

    public static HttpResponse process(HttpRequest request) {
        String uri    = request.getPath();
        String method = request.getMethod();

        Route route = RouteRegistry.getRoute(method, uri);

        Gson gson = new Gson();

        if( route == null ) {
            Logger.warn(RequestHandler.class, "No route found for : " + method+":"+uri);
            return new HttpResponse.Builder()
                    .status(404)
                    .body(gson.toJson( "Route not found: " + uri ))
                    .build();
        }
        try {
            Object result = ControllerInvoker.invoke(route);
            if( result instanceof ResponseEntity<?> responseEntity){
                return new HttpResponse.Builder()
                        .status(responseEntity.getStatus())
                        .body( gson.toJson( responseEntity.getBody() ) )
                        .build();
            }

            return new HttpResponse.Builder()
                    .status(200)
                    .body( gson.toJson(result) )
                    .build();
        }catch (InstantiationException | NoSuchMethodException e) {
            Logger.error(RequestHandler.class, "Could not get instance of controller: "+e.getMessage());
        }catch (InvocationTargetException | IllegalAccessException e){
            Logger.error(RequestHandler.class, "Could not invoke controller method: "+e.getMessage());
        }
        return new HttpResponse.Builder()
                .status(500)
                .body( gson.toJson("Internal server error: " + uri) )
                .build();
    }

    public static void writeResponse(HttpResponse response, HttpServletResponse servletResponse) throws IOException {
        servletResponse.setStatus(response.getStatus());
        servletResponse.setContentType("text/plain");
        PrintWriter out = servletResponse.getWriter();
        out.print(response.getBody());
    }

}
