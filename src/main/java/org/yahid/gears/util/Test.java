package org.yahid.gears.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yahid.gears.config.HttpConfigurationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String [] args){
        FileReader fileReader = null;
        try{
            fileReader = new FileReader("src/main/resources/http.json");
        }catch (IOException e){
            throw new HttpConfigurationException(e);
        }

        StringBuffer sb = new StringBuffer();
        int i;

        try{
            while((i = fileReader.read()) != -1){
                sb.append((char) i);
            }
        } catch(IOException e){
            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;
        try{
            conf = Json.parse(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(conf);
//        System.out.println(sb.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readTree(new File("src/main/resources/http.json"));
            System.out.println(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
