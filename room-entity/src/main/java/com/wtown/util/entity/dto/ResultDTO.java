/**
 * @author LYU
 * @create 2017年12月13日 14:06
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultDTO {

    @JsonProperty("TFS_FILE_NAME")
    private String TFS_FILE_NAME;

    public ResultDTO() {
    }

    public ResultDTO(String TFS_FILE_NAME) {
        this.TFS_FILE_NAME = TFS_FILE_NAME;
    }

    public void setTFS_FILE_NAME(String TFS_FILE_NAME) {
        this.TFS_FILE_NAME = TFS_FILE_NAME;
    }

    public String getTFS_FILE_NAME() {
        return TFS_FILE_NAME;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "TFS_FILE_NAME='" + TFS_FILE_NAME + '\'' +
                '}';
    }
}
