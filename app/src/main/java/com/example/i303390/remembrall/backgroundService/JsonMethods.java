package com.example.i303390.remembrall.backgroundService;

import com.example.i303390.remembrall.POJO.LocationListJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by I303320 on 12/18/2016.
 */

public class JsonMethods {

    public static <T> T getOfromJSON(String json,Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JodaModule());
            return objectMapper.readValue(json,clazz);
        } catch (Exception e) {

        }
        return null;
    }

    public static ArrayList getAllLocation(ArrayList responseList) {
        ArrayList list = new ArrayList();
        for(Object obj : responseList){
            LinkedHashMap map = (LinkedHashMap) obj;
            LocationListJson json = new LocationListJson();
            json.setID((String)map.get("ID"));
            json.setKeyword((String)map.get("Keyword"));
            json.setGoogleID((String)map.get("GoogleID"));
            json.setLatitude((Double) map.get("latitude"));
            json.setLongitude((Double)map.get("longitude"));
            json.setName((String)map.get("name"));
            json.setVicinity((String)map.get("vicinity"));
            json.setIcon((String)map.get("icon"));

            list.add(json);
        }
        return list;
    }
}
