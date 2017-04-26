package com.sunesoft.lemon.deanery.carWorkFlow.domain;

import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangwj on 2016/7/26 0026.
 * 车辆申请记录表
 */
@Entity
@Table(name = "syy_oa_car_flow")
public class CarApply extends BaseFormEntity{
    /**
     * 车辆ID，关联车辆信息表中的ID
     */
    @Column(name = "car_id",columnDefinition = "NUMBER(19)")
    private Long carId;

    /*
    * 车牌号码
    * */
    @Column(name = "carno",columnDefinition = "VARCHAR2(100)")
    private String CarNo;
    /**
     * 司机ID，关联司机信息表中的ID
     */
    @Column(name = "driver_id",columnDefinition = "NUMBER(19)")
    private Long driverId;

    /*
    * 司机名称
    * */
    @Column(name = "drivername",columnDefinition = "VARCHAR2(100)")
    private String driverName;
    /**
     * 用车说明
     */
    @Column(name = "car_use_info",columnDefinition = "VARCHAR2(500)")
    private String carUseInfo;
    /**
     * 备注
     */
    @Column(name = "remark",columnDefinition = "VARCHAR2(500)")
    private String remark;

    /*
    * 服务评价备注
    * */
    @Column(name = "returnremark",columnDefinition = "VARCHAR2(500)")
    private String returnRemark;


    /*
    * 预计出发时间
    * */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "predict_go_start_time")
    private Date predictGoStartTime;

    /*
    * 预计返回时间
    * */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "predict_go_arrive_time")
    private Date predictGoArriveTime;
    /**
     * 前往出发时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "go_start_time")
    private Date goStartTime;
    /**
     * 前往到达时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "go_arrive_time")
    private Date goArriveTime;
    /**
     * 前往乘坐人员
     */
    @Column(name = "go_ride_peoples",columnDefinition = "VARCHAR2(300)")
    private String goRidePeoples;
    /**
     * 返程出发时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "return_start_time")
    private Date returnStartTime;
    /**
     * 返程到达时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "return_arrive_time")
    private Date returnArriveTime;
    /**
     * 返程乘坐人员
     */
    @Column(name = "return_ride_peoples",columnDefinition = "VARCHAR2(300)")
    private String returnRidePeoples;
    /**
     * 里程数
     */
    @Column(name = "mileage",columnDefinition = "NUMBER(19,2)")
    private Double mileage;
    /**
     * 费用
     */
    @Column(name = "cost",columnDefinition = "NUMBER(19,2)")
    private Double cost;
    /**
     * 服务态度
     * 1：优秀
     * 2：良好
     * 3：一般
     * 4：差
     */
//    @Column(name = "attitude",columnDefinition = "NUMBER(2)")
//    private Integer attitude;
    /**
     * 用车评价
     * 1：优秀
     * 2：良好
     * 3：一般
     * 4：差
     */
    @Column(name = "evaluate",columnDefinition = "VARCHAR2(500)")
    private String evaluate;


    /**
     * 流程task编号
     */
    @Column(name = "orderId",columnDefinition = "VARCHAR2(100)")
    private String orderId;

    /**
     * 流程中的状态
     * 插入数据库为数字
     * 1：代表运行
     * 2：代表撤销
     * 3：代表结束
     */
    @Column(name= "flowstatus" ,columnDefinition = "NUMBER(2)")
    private Integer flow_status;

    /*
    * 前往地点
    * */
    @Column(name = "address",columnDefinition = "VARCHAR2(100)")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFlow_status() {
        return flow_status;
    }

    public void setFlow_status(Integer flow_status) {
        this.flow_status = flow_status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public CarApply() {
        this.setIsActive(Boolean.TRUE);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setViewUrl("car_driver_flow");
        this.setApplyDate(new Date());
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getCarUseInfo() {
        return carUseInfo;
    }

    public void setCarUseInfo(String carUseInfo) {
        this.carUseInfo = carUseInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getGoStartTime() {
        return goStartTime;
    }

    public void setGoStartTime(Date goStartTime) {
        this.goStartTime = goStartTime;
    }

    public Date getGoArriveTime() {
        return goArriveTime;
    }

    public void setGoArriveTime(Date goArriveTime) {
        this.goArriveTime = goArriveTime;
    }

    public String getGoRidePeoples() {
        return goRidePeoples;
    }

    public void setGoRidePeoples(String goRidePeoples) {
        this.goRidePeoples = goRidePeoples;
    }

    public Date getReturnStartTime() {
        return returnStartTime;
    }

    public void setReturnStartTime(Date returnStartTime) {
        this.returnStartTime = returnStartTime;
    }

    public Date getReturnArriveTime() {
        return returnArriveTime;
    }

    public void setReturnArriveTime(Date returnArriveTime) {
        this.returnArriveTime = returnArriveTime;
    }

    public String getReturnRidePeoples() {
        return returnRidePeoples;
    }

    public void setReturnRidePeoples(String returnRidePeoples) {
        this.returnRidePeoples = returnRidePeoples;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

//    public Integer getAttitude() {
//        return attitude;
//    }
//
//    public void setAttitude(Integer attitude) {
//        this.attitude = attitude;
//    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public Date getPredictGoStartTime() {
        return predictGoStartTime;
    }

    public void setPredictGoStartTime(Date predictGoStartTime) {
        this.predictGoStartTime = predictGoStartTime;
    }

    public Date getPredictGoArriveTime() {
        return predictGoArriveTime;
    }

    public void setPredictGoArriveTime(Date predictGoArriveTime) {
        this.predictGoArriveTime = predictGoArriveTime;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark) {
        this.returnRemark = returnRemark;
    }

    public String getCarNo() {
        return CarNo;
    }

    public void setCarNo(String carNo) {
        CarNo = carNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
