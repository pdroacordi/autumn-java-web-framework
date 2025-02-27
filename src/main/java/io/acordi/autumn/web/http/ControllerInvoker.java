package io.acordi.autumn.web.http;

import com.google.gson.Gson;
import io.acordi.autumn.annotation.http.RequestBody;
import io.acordi.autumn.core.context.ApplicationContext;
import io.acordi.autumn.util.Logger;
import io.acordi.autumn.web.routing.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerInvoker {

    public static Object invoke(Route route, HttpRequest request) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {
        /*
            I was really in doubt if I should register the controllers here or at ControllerScanner class
            If I did id at ControllerScanner, all the controllers would have instances at the very moment
            of the start of the application, which performs better in terms of response time of the api.
            On the other hand, there is no need to overwhelm the memory with unused instances. Decided to
            do it here.
        */
        Class<?> controllerClass = route.getController();
        Object controllerInstance = ApplicationContext.getBean( controllerClass );
        if( controllerInstance == null ) {
            controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
            ApplicationContext.registerBean(controllerClass, controllerInstance);
        }
        Method method = route.getControllerMethod();
        return handleMethod(controllerInstance, method, request);
    }

    private static Object handleMethod(Object instance, Method method, HttpRequest request)
            throws InvocationTargetException, IllegalAccessException {

        if( method.getParameterTypes().length == 0 ) {
            return method.invoke( instance );
        }
        Gson gson = new Gson();

        List<Object> args = new ArrayList<>();

        for( Parameter parameter : method.getParameters() ) {
            Class<?> paramType = parameter.getType();
            Object arg = null;

            if( parameter.isAnnotationPresent( RequestBody.class ) ) {
                String body = readBytesFromRequestBody(request);
                arg = gson.fromJson(body, paramType);
            }

            args.add(arg);

        }
        return method.invoke( instance, args.toArray() );

    }

    private static String readBytesFromRequestBody(HttpRequest request) {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getBody()));
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }catch( IOException e ) {
            Logger.error(ControllerInvoker.class, "Malformatted body: "+e.getMessage());
            return "";
        }
    }

}
