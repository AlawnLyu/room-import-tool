/**
 * @author LYU
 * @create 2017年11月10日 9:09
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.convert.json.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wtown.util.convert.json.JsonDataUtil;

import java.io.IOException;

public class JsonDataUtilImpl implements JsonDataUtil {

    private ObjectMapper objectMapper;

    public JsonDataUtilImpl() {
        init();
    }

    private void init() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public <T> T readObject(String value, Class<T> tClass) throws IOException {
        return objectMapper.readValue(value, tClass);
    }

    @Override
    public <T> String writeObject(T t) throws IOException {
        return objectMapper.writeValueAsString(t);
    }
}
