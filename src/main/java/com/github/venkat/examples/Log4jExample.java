package com.github.venkat.examples;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jExample {

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Log4jExample.class);
       // logger.trace("Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));

    }
}
