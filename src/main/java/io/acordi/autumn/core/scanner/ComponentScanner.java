package io.acordi.autumn.core.scanner;

import io.acordi.autumn.annotation.components.Component;
import io.acordi.autumn.core.context.ApplicationContext;
import io.acordi.autumn.util.ClassPathScanner;
import io.acordi.autumn.util.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ComponentScanner {

    public static void scanAndRegister( Class<?> origin ){
        Logger.info(ComponentScanner.class, "Indexing components...");
        ApplicationContext context = ApplicationContext.getInstance();
        List<Class<?>> components = ClassPathScanner.findAnnotatedClasses(origin.getPackageName(), Component.class);
        for (Class<?> component : components) {
            for ( Class<?> superclass : component.getInterfaces() ) {
                if(context.getBean(superclass) != null) {
                    continue;
                }
                try {
                    Object instance = component.getDeclaredConstructor().newInstance();
                    context.registerBean(superclass, instance);
                }catch (NoSuchMethodException | InstantiationException | IllegalStateException
                        | IllegalAccessException | InvocationTargetException e){
                    Logger.error(ComponentScanner.class, "Failed to create instance of component: "+e.getMessage());
                }
            }
        }

        Logger.info(ComponentScanner.class, "Components indexed successfully.");

    }

}
