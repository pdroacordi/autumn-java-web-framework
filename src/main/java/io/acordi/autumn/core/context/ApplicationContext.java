package io.acordi.autumn.core.context;

import io.acordi.autumn.core.scanner.ComponentScanner;
import io.acordi.autumn.core.scanner.ControllerScanner;
import io.acordi.autumn.util.Logger;


import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    private static ApplicationContext INSTANCE;
    private final Map<Class<?>, Object> beanRegistry = new HashMap<>();

    private ApplicationContext() {
    }

    public static synchronized ApplicationContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationContext();
        }
        return INSTANCE;
    }

    public void registerBean(Class<?> clazz, Object instance){
        if (beanRegistry.containsKey(clazz)) {
            Logger.warn(ApplicationContext.class, "Bean already registered: " + clazz.getName());
            return;
        }

        beanRegistry.put(clazz, instance);
    }

    public <T> T getBean(Class<T> clazz){
        return clazz.cast(beanRegistry.get(clazz));
    }

    public void init(Class<?> origin){
        Logger.info(ApplicationContext.class, "Initializing application context...");

        ComponentScanner.scanAndRegister(origin);
        ControllerScanner.scanAndRegister(origin);

        Logger.info(ApplicationContext.class, "ApplicationContext initialization completed.");
    }


}
