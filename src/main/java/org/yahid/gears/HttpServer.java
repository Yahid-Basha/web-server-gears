package org.yahid.gears;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yahid.gears.config.Configuration;
import org.yahid.gears.config.ConfigurationManager;
import org.yahid.gears.core.ServerListenerThread;

import java.io.IOException;

//Driver class for the HttpServer
public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String [] args){

        LOGGER.info("HttpServer is starting...");

        try {
            ConfigurationManager.getInstance().loadConfiguration("src/main/resources/http.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port:" + conf.getPort());
        LOGGER.info("Using webroot:"+ conf.getWebroot());
        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
