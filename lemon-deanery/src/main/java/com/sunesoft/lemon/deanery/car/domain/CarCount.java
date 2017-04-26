package com.sunesoft.lemon.deanery.car.domain;

import java.util.Date;

/**
 * Created by zy
 */
public class CarCount {
    //申请人
    private String applyer;
    //前往地点
    private String place;
    //预计出发时间
    private String yjchsj;
    //预计到达时间
    private String yjdasj;
    //申请人单位
    private String sqrdw;
    //司机名称
    private String sjmc;
    //实际出发时间
    private String sjcfsj;
    //实际出发到达时间
    private String sjdasj;
    //调度中心
   /* private String ddzx;*/
    //实际去程通车人员
    private String sjcccry;
    //实际返程出发时间
    private String sjfccfsj;
    //实际返程到达时间
    private String sjfcddsj;
    //实际返程通车人员
    private String sjfctcry;
    //服务评价
    private String fwpj;
    //服务评价备注
  /*  private String fwpjbz;*/

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getYjchsj() {
        return yjchsj;
    }

    public void setYjchsj(String yjchsj) {
        this.yjchsj = yjchsj;
    }

    public String getYjdasj() {
        return yjdasj;
    }

    public void setYjdasj(String yjdasj) {
        this.yjdasj = yjdasj;
    }

    public void setSjcfsj(String sjcfsj) {
        this.sjcfsj = sjcfsj;
    }

    public void setSjdasj(String sjdasj) {
        this.sjdasj = sjdasj;
    }

    public String getSqrdw() {
        return sqrdw;
    }

    public void setSqrdw(String sqrdw) {
        this.sqrdw = sqrdw;
    }

    public String getSjmc() {
        return sjmc;
    }

    public void setSjmc(String sjmc) {
        this.sjmc = sjmc;
    }

    public String getSjcfsj() {
        return sjcfsj;
    }

    public String getSjdasj() {
        return sjdasj;
    }

  /*  public String getDdzx() {
        return ddzx;
    }

    public void setDdzx(String ddzx) {
        this.ddzx = ddzx;
    }
*/
    public String getSjcccry() {
        return sjcccry;
    }

    public void setSjcccry(String sjcccry) {
        this.sjcccry = sjcccry;
    }

    public String getSjfccfsj() {
        return sjfccfsj;
    }

    public void setSjfccfsj(String sjfccfsj) {
        this.sjfccfsj = sjfccfsj;
    }

    public String getSjfcddsj() {
        return sjfcddsj;
    }

    public void setSjfcddsj(String sjfcddsj) {
        this.sjfcddsj = sjfcddsj;
    }

    public String getSjfctcry() {
        return sjfctcry;
    }

    public void setSjfctcry(String sjfctcry) {
        this.sjfctcry = sjfctcry;
    }

    public String getFwpj() {
        return fwpj;
    }

    public void setFwpj(String fwpj) {
        this.fwpj = fwpj;
    }

 /*   public String getFwpjbz() {
        return fwpjbz;
    }

    public void setFwpjbz(String fwpjbz) {
        this.fwpjbz = fwpjbz;
    }*/
}
