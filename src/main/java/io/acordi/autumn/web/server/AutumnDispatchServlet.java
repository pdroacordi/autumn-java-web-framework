package io.acordi.autumn.web.server;

import io.acordi.autumn.web.http.HttpRequest;
import io.acordi.autumn.web.http.HttpResponse;
import io.acordi.autumn.web.http.RequestHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AutumnDispatchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpRequest httpRequest = new HttpRequest.Builder()
                .method( request.getMethod() )
                .path( request.getRequestURI() )
                .build();

        HttpResponse httpResponse = RequestHandler.process(httpRequest);

        RequestHandler.writeResponse(httpResponse, response);



    }
}
