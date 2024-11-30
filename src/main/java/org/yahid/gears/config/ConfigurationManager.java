package org.yahid.gears.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.yahid.gears.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager configurationManager;
    private static Configuration currentConfiguration;
    private ConfigurationManager(){

    }

    public static ConfigurationManager getInstance(){
        if(configurationManager == null){
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }
    /*
     * Load the configuration from a file (throws exception if file not found)
     */
    public void loadConfiguration(String filePath) throws IOException {
        FileReader fileReader = null;
        try{
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e){
            throw new HttpConfigurationException("File Not found to load config.");
        }

        StringBuffer sb = new StringBuffer();
        int i;
        try{
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
        }catch (IOException e){
            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the config file", e);
        }
        try {
            currentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Internal error parsing the config file", e);
        }
    }
    /*
     * Save the current configuration to a file (throws exception if configuration not loaded)
     */
    public Configuration getCurrentConfiguration(){
        if(currentConfiguration == null){
            throw new HttpConfigurationException("No current Configuration Set");
        }
        return currentConfiguration;
    }

}
