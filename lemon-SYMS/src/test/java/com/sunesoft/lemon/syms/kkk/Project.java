package com.sunesoft.lemon.syms.kkk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by temp on 2016/7/14.
 */
public class Project {

    // 项目ID
    private String id;

    // 项目名称
    private String name;

    // 所属父类别
    private String categoryID;

    // 所属子类别
    private String sonCategoryID;

    // 城市ID
    private String siteID;

    // 城市名称
    private String siteName;

    // 项目详情描述
    private String description;

    // 一句话描述
    private String subHead;

    // 是否支持电子票(1-支持;0-不支持)
    private String isETicket;

    // 最低价格
    private String minPrice;

    // 最高价格
    private String maxPrice;

    // 价格快照
    private String priceStr;

    // 销售结束时间
    private String sellEndTime;

    // 演出时间
    private String showTime;

    // 是否可以购买(1-可以购买；0-不允许购买）
    private String canSell;

    // 是否是测试项目（1-是，0-不是）
    private String isTest;

    // 场馆名称
    private String venueName;

    // 场馆ID
    private String venueID;

    // 是否支持选座(1-支持选座;0-不支持选座)
    private String isXuanZuo;

    // 相关艺人信息节点
    // private String artistinfo;

    // 最后更新时间
    private String updateTime;

    // 商品状态(3,销售中;4,结束;7,预定;8,预售;10,测试;6,禁止显示)
    private String status;

    /** 座位表图片路径 */
    private String seatPicUrl;

    private List<String> performIdList = new ArrayList<String>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSonCategoryID() {
        return sonCategoryID;
    }

    public void setSonCategoryID(String sonCategoryID) {
        this.sonCategoryID = sonCategoryID;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubHead() {
        return subHead;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public String getIsETicket() {
        return isETicket;
    }

    public void setIsETicket(String isETicket) {
        this.isETicket = isETicket;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getSellEndTime() {
        return sellEndTime;
    }

    public void setSellEndTime(String sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getCanSell() {
        return canSell;
    }

    public void setCanSell(String canSell) {
        this.canSell = canSell;
    }

    public String getIsTest() {
        return isTest;
    }

    public void setIsTest(String isTest) {
        this.isTest = isTest;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public String getIsXuanZuo() {
        return isXuanZuo;
    }

    public void setIsXuanZuo(String isXuanZuo) {
        this.isXuanZuo = isXuanZuo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeatPicUrl() {
        return seatPicUrl;
    }

    public void setSeatPicUrl(String seatPicUrl) {
        this.seatPicUrl = seatPicUrl;
    }

    public List<String> getPerformIdList() {
        return performIdList;
    }

    public void setPerformIdList(List<String> performIdList) {
        this.performIdList = performIdList;
    }
}
