/**
 * @author LYU
 * @create 2017年12月13日 14:06
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultDTO {

    @JsonProperty(value = "TFS_FILE_NAME")
    private String tfsFileName;

    public ResultDTO() {
    }

    public ResultDTO(String tfsFileName) {
        this.tfsFileName = tfsFileName;
    }

    public String getTfsFileName() {
        return tfsFileName;
    }

    public void setTfsFileName(String tfsFileName) {
        this.tfsFileName = tfsFileName;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "tfsFileName='" + tfsFileName + '\'' +
                '}';
    }
}
