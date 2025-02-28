package io.acordi.autumn.core.context;

import io.acordi.autumn.annotation.Injected;
import io.acordi.autumn.core.scanner.ComponentScanner;
import io.acordi.autumn.core.scanner.ControllerScanner;
import io.acordi.autumn.util.Logger;


import java.lang.reflect.Field;
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

    public void init(Class<?> origin) {
        Logger.info(ApplicationContext.class, "Initializing application context...");

        ComponentScanner.scanAndRegister(origin);
        ControllerScanner.scanAndRegister(origin);
        injectDependencies();

        Logger.info(ApplicationContext.class, "ApplicationContext initialization completed.");
    }

    private void injectDependencies() {
        try {
            for (Class<?> clazz : beanRegistry.keySet()) {
                Object instance = beanRegistry.get(clazz);

                for (Field field : clazz.getDeclaredFields()) {
                    if (!field.isAnnotationPresent(Injected.class)) continue;

                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();

                    Object fieldInstance = beanRegistry.get(fieldType);

                    if( fieldInstance == null ){
                        Logger.warn(ApplicationContext.class, "No bean found for type: " + fieldType.getName());
                        continue;
                    }

                    field.set(instance, fieldInstance);
                    field.setAccessible(false);

                }
            }
        }catch (Exception e){
            Logger.error(ComponentScanner.class, "Failed to inject dependency: "+e.getMessage());
        }
    }


}
