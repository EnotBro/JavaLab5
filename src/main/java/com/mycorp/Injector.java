package com.mycorp;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * class injector
 */
public class Injector  {

    /**
     * name of property file
     */
    private String filenameOfProperties = "var1.properties";

    /**
     * Initializes the fields of an object of any class with annotation @AutoInjectable
     * by class objects according to the property file (default var1.properties)
     * @param someObject The object for injection
     * @param <T> Type of the object
     * @return The object after injection
     */
    public <T> T inject(T someObject)
    {
        try
        {
            Properties properties = new Properties();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream in = classloader.getResourceAsStream(filenameOfProperties);
            properties.load(in);
            for (Field field : someObject.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    field.setAccessible(true);
                    Class<?> clss = Class.forName(properties.getProperty(field.getType().getName()));
                    Object object = clss.getDeclaredConstructor().newInstance();
                    field.set(someObject,object);
                }
            }
        }
        catch (IOException|ClassNotFoundException|NoSuchMethodException|
                IllegalAccessException|InstantiationException| InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return someObject;
    }

    /**
     * Getting name of property file
     * @return  name of property file
     */
    public String getFilenameOfProperties() {
        return filenameOfProperties;
    }

    /**
     * Seting new name of property file
     * @param filename new name of property file
     */
    public void setFilenameOfProperties(String filename)
    {
        filenameOfProperties = filename;
    }
}
