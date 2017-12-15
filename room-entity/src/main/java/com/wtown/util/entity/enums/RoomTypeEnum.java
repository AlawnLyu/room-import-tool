/**
 * @author LYU
 * @create 2017-12-14-12:43
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.wtown.util.entity.enums;

public enum RoomTypeEnum implements DescCode {
    BIGBED("1", "大床房"),
    STANDARD("3", "标间"),
    FAMILY("5", "家庭房"),
    SUITE("6", "套房");

    private String code;

    private String desc;

    RoomTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
