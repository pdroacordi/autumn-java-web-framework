package io.acordi.autumn.util;

import java.io.*;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ClassPathScanner {


    public static List<Class<?>> scanPackage(String packageName) {
        ArrayList<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');

        try{
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                if ("file".equals(resource.getProtocol())) {
                    classes.addAll(findClassesInDirectory(new File(resource.getFile()), packageName));
                } else if ("jar".equals(resource.getProtocol())) {
                    classes.addAll(findClassesInJar(resource, path));
                }
            }
        }catch (Exception e){
            Logger.error(ClassPathScanner.class, "Error scanning package: "+packageName+" - "+e.getMessage());
        }

        return classes;
    }

    public static List<Class<?>> findAnnotatedClasses(String packageName, Class<? extends Annotation> annotation) {
        return scanPackage(packageName).stream()
                .filter(cls -> cls.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private static List<Class<?>> findClassesInJar(URL resource, String path) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            JarURLConnection connection = (JarURLConnection) resource.openConnection();
            JarFile jarFile = connection.getJarFile();

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                    String className = entryName.replace('/', '.').replace(".class", "");
                    loadClass(className).ifPresent(classes::add);
                }
            }
        } catch (IOException e) {
            Logger.error(ClassPathScanner.class, "Error scanning JAR: " + resource + " - " + e.getMessage());
        }

        return classes;
    }

    private static List<Class<?>> findClassesInDirectory(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists() || !directory.isDirectory()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    loadClass(className).ifPresent(classes::add);
                }
            }
        }
        return classes;
    }

    private static Optional<Class<?>> loadClass(String className) {
        try {
            return Optional.of(Class.forName(className));
        } catch (ClassNotFoundException e) {
            Logger.error(ClassPathScanner.class, "Class not found: " + className);
            return Optional.empty();
        }
    }



}
