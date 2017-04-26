package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.*;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverReportDto;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.deanery.carWorkFlow.application.dtos.CarApplyDto;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/6/17 0017.
 */
public interface DriverService {
    //查询司机，以driverId为准
    public DriverDto getByIdDriver(Long driverId);

    //查询司机，以driverId为准
    public PagedResult<DriverDto> getByDriver(DriverCriteria driverCriteria);

    //新增司机
    public Long addOrUpdate(DriverDto dto);

    //新增司机
    public Long addDriver(DriverDto dto);

    //单个删除司机，但其实是软删除
    public boolean delDriver(Long driverId);

    //批量删除司机，但其实是软删除
    public boolean delDriver(Long[] driverIds);

    //更新司机的信息，以对象传入为准
    public boolean updDriver(DriverDto dto);

    //更新司机的信息，以carId为准
    public boolean updDriver(Long driverId);

    //获取公司信息
    public List<Company> getComs();

    /**
     * 司机信息汇总查询
     * @param driverReportCriteria
     * @return
     */
    public DriverReportDto driverReport(DriverReportCriteria driverReportCriteria);

    //统计具体某个司机上个月的出车情况
    public List<Map<String, Object>> driverinfo(String driverId);

    /**
     * 出入ID判断是否有效
     * @param driverId
     * @return
     */
    public List<DriverDto> isActiveByDriverId(Long driverId);


    //统计车辆申请信息
    public  List<Map<String, Object>> CarApplyInfo(CarApplyCriteria carApplyCriteria);


    //获取所有合法的DRIVER信息
    public List<DriverDto> getAllIsActive();

    //统计车辆申请信息
    public List<Map<String, Object>> CarApplyInfo2(CarApplyCriteria2 carApplyCriteria2);

    //统计司机信息
    public List<Map<String, Object>> driverStatistics(DriverStatisticsCriteria driverStatisticsCriteria);

    //查询所有部门
    public List<Deptment> findAllDept();

    public PagedResult CarApplyInfo3(CarApplyCriteria2 carApplyCriteria2);

    CarApplyDto getDtoById(String id);

    Company getCompanyById(CarApplyDto carApplyDto);

    PagedResult<Map<String,Object>> driverStatisticsPage(DriverStatisticsCriteria driverStatisticsCriteria);
}