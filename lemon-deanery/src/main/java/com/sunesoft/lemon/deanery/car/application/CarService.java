package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.CarCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CarReportDto;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;

import java.util.List;
import java.util.Map;

/**
 * Created by wangwj on 2016/6/15 0015.
 * edit by xubo on 2016/6/15
 */
public interface CarService {


    //查询车，以carId为准
    public CarDto getByIdCar(Long carId);



    //新增车辆
    public Long addOrUpdate(CarDto dto);

    //新增车辆
    public Long addCar(CarDto car);

    //单个删除车，但其实是软删除
    public boolean delCar(Long carId);

    //批量删除车，但其实是软删除
    public boolean delCar(Long[] carIds);

    //更新车的信息，以对象传入为准
    public boolean updCar(CarDto car);

    //更新车的信息，以carId为准
    public boolean  updCar(Long carId);

    //获取公司信息
    public List<Company> getComs();


    /**
     * 车辆信息汇总查询
     * @param carReportCriteria
     * @return
     */
    public CarReportDto carReport(CarReportCriteria carReportCriteria);

    public PagedResult<CarDto> getCars(CarCriteria carCriteria) ;

    /**
     * 获取所有符合的车
     */
    public List<Car> getAllCar();

    public Deptment carReportByNo(Long no);
}
