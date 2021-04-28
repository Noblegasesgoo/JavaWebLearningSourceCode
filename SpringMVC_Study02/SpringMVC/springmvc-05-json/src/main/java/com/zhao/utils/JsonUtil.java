package com.zhao.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class JsonUtil {

    public static String getJson(Object object) throws JsonProcessingException {
        return getJson(object, "yyyy-MM-hh HH:mm:ss");
    }

    //sdf:simpledateformat
    public static String getJson(Object object, String sdf) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        mapper.setDateFormat(simpleDateFormat);

        return mapper.writeValueAsString(object);
    }
}
