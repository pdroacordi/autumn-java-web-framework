package io.acordi.autumn.core.context;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private static final Map<Class<?>, Object> beanRegistry = new HashMap<>();

    public static void registerBean(Class<?> clazz, Object instance){
        beanRegistry.put(clazz, instance);
    }

    public static <T> T getBean(Class<T> clazz){
        return clazz.cast(beanRegistry.get(clazz));
    }

}
