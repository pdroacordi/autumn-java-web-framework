package io.acordi.autumn.web.http;

import io.acordi.autumn.core.context.ApplicationContext;
import io.acordi.autumn.util.Logger;
import io.acordi.autumn.web.routing.Route;
import io.acordi.autumn.web.server.AutumnDispatchServlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ControllerInvoker {

    public static Object invoke(Route route) throws InvocationTargetException, IllegalAccessException,
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
        return method.invoke(controllerInstance);
    }
}
