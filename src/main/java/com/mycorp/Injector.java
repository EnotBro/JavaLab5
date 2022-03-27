package com.mycorp;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Injector  {

    private String filenameOfProperties = "var1.properties";

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

    public String getFilenameOfProperties() {
        return filenameOfProperties;
    }

    public void setFilenameOfProperties(String filename)
    {
        filenameOfProperties = filename;
    }
}
