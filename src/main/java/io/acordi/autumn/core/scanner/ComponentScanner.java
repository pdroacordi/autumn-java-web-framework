package io.acordi.autumn.core.scanner;

import io.acordi.autumn.core.processor.AnnotationProcessor;
import io.acordi.autumn.util.ClassPathScanner;

import java.util.List;

public class ComponentScanner {

    public static void scanComponents( Class<?> origin ){
        List<Class<?>> components = ClassPathScanner.findAnnotatedClasses(origin.getPackageName(),
                io.acordi.autumn.annotation.components.Component.class);

    }

}
