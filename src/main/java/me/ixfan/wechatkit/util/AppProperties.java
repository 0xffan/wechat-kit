package me.ixfan.wechatkit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by xfan on 16/3/26.
 */
public class AppProperties {

    private static Logger logger = LoggerFactory.getLogger(AppProperties.class);

    private static Properties properties = new Properties();

    static {
        try {
            InputStream in = AppProperties.class.getClassLoader().getResourceAsStream("app.properties");
            if (null != in) {
                properties.load(in);
            } else {
                logger.error("Property file 'app.properties' not found in the classpath!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
