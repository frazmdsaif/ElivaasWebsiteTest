package org.elivaas.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static String loadProperty(String name) throws IOException {
        InputStream input=new FileInputStream("src/test/resources/config.properties");
        Properties properties=new Properties();
        properties.load(input);
        return properties.getProperty(name);


    }
}
