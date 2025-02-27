package io.acordi.autumn.web.routing;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RouteRegistry {

    private static final Map<String, Route> routes = new HashMap<>();

    public static void registerRoute(String method, String path, Class<?> controller, Method controllerMethod) {
        String key = generateKey(method, path);
        routes.put(key, new Route.Builder()
                .method(method)
                .path(path)
                .controller(controller)
                .controllerMethod(controllerMethod)
                .build() );
    }

    public static Route getRoute(String method, String path) {
        return routes.get(generateKey(method, path));
    }

    public static void print(){
        routes.forEach((k,v)->{
            System.out.println(k+" : "+v.getControllerMethod());
        });
    }

    private static String generateKey(String method, String path) {
        return method.toUpperCase() + ":" + path;
    }
}
