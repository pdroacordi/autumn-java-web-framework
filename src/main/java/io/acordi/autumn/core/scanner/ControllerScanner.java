package io.acordi.autumn.core.scanner;

import io.acordi.autumn.annotation.http.GetMethod;
import io.acordi.autumn.annotation.http.PostMethod;
import io.acordi.autumn.util.ClassPathScanner;
import io.acordi.autumn.util.Logger;
import io.acordi.autumn.web.routing.RouteRegistry;

import java.lang.reflect.Method;
import java.util.List;

public class ControllerScanner {

    public static void scanControllers( Class<?> origin ){
        List<Class<?>> controllers = ClassPathScanner.findAnnotatedClasses(origin.getPackageName(),
                io.acordi.autumn.annotation.http.RestController.class);

        Logger.info(ControllerScanner.class, "Indexing routes...");
        for (Class<?> controller : controllers) {
            String root = controller.getAnnotation(io.acordi.autumn.annotation.http.RestController.class).value();
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
