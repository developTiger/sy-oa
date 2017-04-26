package com.sunesoft.lemon.deanery.carWorkFlow.application.dtos;

import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

/**
 * Created by wangwj on 2016/7/27 0027.
 */
public class CarApplyDto extends BaseFormDto{
    /*
    * 领导意见
    * */
    private String leader_comment;

    public String getLeader_comment() {
        return leader_comment;
    }

    public void setLeader_comment(String leader_comment) {
        this.leader_comment = leader_comment;
    }

    /**
     * 车辆信息表主键ID
     */
    private Long carId;
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 司机信息表主键ID
     */
    private Long driverId;
    /**
     * 司机名称
     */
    private String driverName;


    /**
     * 流程发起时间
     */
    private String applayDate;
    /**
     * 用车情况
     */
    private String carUseInfo;
    /**
     * 流程状态
     */
    private FormStatus formStatus;


    /**
     * 备注
     */
    private String remark;

    /*
    * 服务评价备注
    * */
    private String returnRemark;
    /**
     * 前往出发时间
     */
    private String goStartDate;
    /**
     * 前往到达时间
     */
    private String goArriveDate;
    /**
     * 前往乘坐人员
     */
    private String goRidePeoples;

    /**
     * 返程出发时间
     */
    private String returnStartDate;
    /**
     * 返程到达时间
     */
    private String returnArriveDate;
    /**
     * 返程乘坐人员
     */
    private String returnRidePeoples;

    /**
     * 里程数
     */
    private Double mileage;
    /**
     * 费用
     */
    private Double cost;
//    /**
//     * 服务态度
//     */
//    private Integer attitude;
    /**
     * 用车评价
     * 1：优秀
     * 2：良好
     * 3：一般
     * 4：差
     */
    private String evaluate;

    /**
     * 可用车集合
     */
    private List<Car> cars;

    /**
     * 可用司机集合
     */
    private List<Driver> drivers;

    /**
     * 流程task编号
     */
    private String taskId;
/*
* 前往地点
* */
    private String address;
/*
* 预计出发时间
* */
    private String predictGoStartDate;
    /*
        * 预计返回时间
        * */
    private String predictGoArriveDate;

    public String getPredictGoStartDate() {
        return predictGoStartDate;
    }

    public void setPredictGoStartDate(String predictGoStartDate) {
        this.predictGoStartDate = predictGoStartDate;
    }

    public String getPredictGoArriveDate() {
        return predictGoArriveDate;
    }

    public void setPredictGoArriveDate(String predictGoArriveDate) {
        this.predictGoArriveDate = predictGoArriveDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }


    public String getApplayDate() {
        return applayDate;
    }

    public void setApplayDate(String applayDate) {
        this.applayDate = applayDate;
    }

    public String getCarUseInfo() {
        return carUseInfo;
    }

    public void setCarUseInfo(String carUseInfo) {
        this.carUseInfo = carUseInfo;
    }

    public FormStatus getFormStatus() {
        return formStatus;
    }

    public void setFormStatus(FormStatus formStatus) {
        this.formStatus = formStatus;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoStartDate() {
        return goStartDate;
    }

    public void setGoStartDate(String goStartDate) {
        this.goStartDate = goStartDate;
    }

    public String getGoArriveDate() {
        return goArriveDate;
    }

    public void setGoArriveDate(String goArriveDate) {
        this.goArriveDate = goArriveDate;
    }

    public String getGoRidePeoples() {
        return goRidePeoples;
    }

    public void setGoRidePeoples(String goRidePeoples) {
        this.goRidePeoples = goRidePeoples;
    }

    public String getReturnStartDate() {
        return returnStartDate;
    }

    public void setReturnStartDate(String returnStartDate) {
        this.returnStartDate = returnStartDate;
    }

    public String getReturnArriveDate() {
        return returnArriveDate;
    }

    public void setReturnArriveDate(String returnArriveDate) {
        this.returnArriveDate = returnArriveDate;
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public String getReturnRemark() {
        return returnRemark;
    }

    public void setReturnRemark(String returnRemark) {
        this.returnRemark = returnRemark;
    }
}
