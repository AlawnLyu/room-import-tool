package com.wtown.util.entity.room;

public class Room_product {
  private Long sqlid;
  private String rt_code;
  private String rt_name;
  private String picurls;
  private String room_type;
  private Double area;
  private Long stay_num;
  private String envir;
  private Long nosmoke;
  private Long wififree;
  private Long addbed;
  private String service;
  private Double min_price;
  private Double max_price;
  private String scenic_code;
  private String hotel_code;
  private Long sort;
  private Long status;
  private String createtime;
  private String updatetime;

  public Long getSqlid() {
    return sqlid;
  }

  public void setSqlid(Long sqlid) {
    this.sqlid = sqlid;
  }

  public String getRt_code() {
    return rt_code;
  }

  public void setRt_code(String rt_code) {
    this.rt_code = rt_code;
  }

  public String getRt_name() {
    return rt_name;
  }

  public void setRt_name(String rt_name) {
    this.rt_name = rt_name;
  }

  public String getPicurls() {
    return picurls;
  }

  public void setPicurls(String picurls) {
    this.picurls = picurls;
  }

  public String getRoom_type() {
    return room_type;
  }

  public void setRoom_type(String room_type) {
    this.room_type = room_type;
  }

  public Double getArea() {
    return area;
  }

  public void setArea(Double area) {
    this.area = area;
  }

  public Long getStay_num() {
    return stay_num;
  }

  public void setStay_num(Long stay_num) {
    this.stay_num = stay_num;
  }

  public String getEnvir() {
    return envir;
  }

  public void setEnvir(String envir) {
    this.envir = envir;
  }

  public Long getNosmoke() {
    return nosmoke;
  }

  public void setNosmoke(Long nosmoke) {
    this.nosmoke = nosmoke;
  }

  public Long getWififree() {
    return wififree;
  }

  public void setWififree(Long wififree) {
    this.wififree = wififree;
  }

  public Long getAddbed() {
    return addbed;
  }

  public void setAddbed(Long addbed) {
    this.addbed = addbed;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public Double getMin_price() {
    return min_price;
  }

  public void setMin_price(Double min_price) {
    this.min_price = min_price;
  }

  public Double getMax_price() {
    return max_price;
  }

  public void setMax_price(Double max_price) {
    this.max_price = max_price;
  }

  public String getScenic_code() {
    return scenic_code;
  }

  public void setScenic_code(String scenic_code) {
    this.scenic_code = scenic_code;
  }

  public String getHotel_code() {
    return hotel_code;
  }

  public void setHotel_code(String hotel_code) {
    this.hotel_code = hotel_code;
  }

  public Long getSort() {
    return sort;
  }

  public void setSort(Long sort) {
    this.sort = sort;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public String getCreatetime() {
    return createtime;
  }

  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }

  public String getUpdatetime() {
    return updatetime;
  }

  public void setUpdatetime(String updatetime) {
    this.updatetime = updatetime;
  }

  @Override
  public String toString() {
    return "Room_product{" +
            "sqlid=" + sqlid +
            ", rt_code='" + rt_code + '\'' +
            ", rt_name='" + rt_name + '\'' +
            ", picurls='" + picurls + '\'' +
            ", room_type='" + room_type + '\'' +
            ", area=" + area +
            ", stay_num=" + stay_num +
            ", envir='" + envir + '\'' +
            ", nosmoke=" + nosmoke +
            ", wififree=" + wififree +
            ", addbed=" + addbed +
            ", service='" + service + '\'' +
            ", min_price=" + min_price +
            ", max_price=" + max_price +
            ", scenic_code='" + scenic_code + '\'' +
            ", hotel_code='" + hotel_code + '\'' +
            ", sort=" + sort +
            ", status=" + status +
            ", createtime='" + createtime + '\'' +
            ", updatetime='" + updatetime + '\'' +
            '}';
  }
}
