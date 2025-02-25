package io.acordi.autumn.explorer;

import io.acordi.autumn.util.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClassExplorer {

    public static List<String> retrieveAllClasses(Class<?> origin) {
        return explorePackage( origin.getPackageName() );
    }

    public static List<String> explorePackage(String packageName) {
        ArrayList<String> classNames = new ArrayList<String>();

        String packagePath = packageName.replace('.', '/');
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packagePath);

        if(stream == null) {
            Logger.error(ClassExplorer.class, "Could not find resource " + packagePath);
            return classNames;
        }

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
            String entry;
            while ((entry = reader.readLine()) != null) {
                if( entry.endsWith(".class") ) {
                    String className = packageName + "." + entry.substring(0, entry.length() - 6);
                    classNames.add(className);
                } else {
                    classNames.addAll(explorePackage(packageName + "." + entry));
                }
            }
            return classNames;
        }catch (Exception e){
            Logger.error(ClassExplorer.class, "Error reading package: "+packageName+" - "+e.getMessage());
        }

        return classNames;
    }



}
