package io.acordi.autumn.core.scanner;

import io.acordi.autumn.annotation.http.GetMethod;
import io.acordi.autumn.annotation.http.PostMethod;
import io.acordi.autumn.annotation.http.RestController;
import io.acordi.autumn.core.context.ApplicationContext;
import io.acordi.autumn.util.ClassPathScanner;
import io.acordi.autumn.util.Logger;
import io.acordi.autumn.web.routing.RouteRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ControllerScanner {

    public static void scanControllers( Class<?> origin ){
        List<Class<?>> controllers = ClassPathScanner.findAnnotatedClasses(origin.getPackageName(), RestController.class);

        Logger.info(ControllerScanner.class, "Indexing routes...");
        for (Class<?> controller : controllers) {
            /*
                I was really in doubt if I should register the controllers here or at ControllerInvoker class
                If I did id at ControllerInvoker, the controllers would only have their instances once used
                for the first time (lazy instantiation); better for resources saving, worse for performance
                (and for dependency injection). Decided to leave it here for dependency injection matters.
            */
            try {
                Object controllerInstance = ApplicationContext.getBean(controller);
                if (controllerInstance == null) {
                    controllerInstance = controller.getDeclaredConstructor().newInstance();
                    ApplicationContext.registerBean(controller, controllerInstance);
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalStateException
                     | IllegalAccessException | InvocationTargetException e){
                Logger.error(ControllerScanner.class, "Failed to create instance of controller: "+e.getMessage());
            }

            String root = controller.getAnnotation(RestController.class).value();
            for (Method method : controller.getDeclaredMethods()) {
                if (method.isAnnotationPresent(GetMethod.class)) {
                    String path = root + method.getAnnotation(GetMethod.class).value();
                    RouteRegistry.registerRoute("GET", path, controller, method);
                }
                if (method.isAnnotationPresent(PostMethod.class)) {
                    String path = root + method.getAnnotation(PostMethod.class).value();
                    RouteRegistry.registerRoute("POST", path, controller, method);
                }

            }
        }
        Logger.info(ControllerScanner.class, "Route Registry completed successfully");
    }

}
