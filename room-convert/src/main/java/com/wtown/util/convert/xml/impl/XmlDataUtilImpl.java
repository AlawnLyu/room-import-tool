/**
 * @author LYU
 * @create 2017年11月10日 9:10
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.convert.xml.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wtown.util.convert.xml.XmlDataUtil;

import java.io.IOException;

public class XmlDataUtilImpl implements XmlDataUtil {

    private XmlMapper xmlMapper;

    public XmlDataUtilImpl() {
        init();
    }

    private void init() {
        xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Override
    public <T> T readObject(String value, Class<T> tClass) throws IOException {
        return xmlMapper.readValue(value, tClass);
    }

    @Override
    public <T> String writeObject(T t) throws IOException {
        return xmlMapper.writeValueAsString(t);
    }
}
