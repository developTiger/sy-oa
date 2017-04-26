package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.CommDriverCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CommDriverDto;
import com.sunesoft.lemon.deanery.car.domain.*;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xubo on 2016/6/20 0020.
 */
@Service("commonDriverService")
public class CommonDriverServiceImpl
        extends GenericHibernateFinder
        implements CommonDriverService{

    @Autowired
    private CommDriverRepository commDriverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    /**
     * 司机页面展示
     * @param cdc
     * @return
     */
    @Override
    public PagedResult<CommDriverDto> getCommonDrivers(CommDriverCriteria cdc) {
        Criteria criterion = getSession().createCriteria(CommonDriver.class);
        criterion.add(Restrictions.eq("isActive", true));
        if(null !=  cdc.getCarId()  && cdc.getCarId() != 0){
            criterion.add(Restrictions.eq("carId", cdc.getCarId()));
        }
        if(null != cdc.getDriverId()
                && cdc.getDriverId() != 0){
            criterion.add(Restrictions.eq("driverId",
                    cdc.getDriverId()));
        }
        int totalCount = ((Long)criterion.
                setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult(
                (cdc.getPageNumber() - 1) *
                        cdc.getPageSize()).
                setMaxResults(cdc.getPageSize());
        List<CommonDriver> commonDrivers = criterion.list();
        List<CommDriverDto> cdds = new ArrayList<CommDriverDto>();
        for (CommonDriver cd : commonDrivers){
            cdds.add(convertToDto(cd));
        }
        return new PagedResult<CommDriverDto>(cdds,
                cdc.getPageNumber(),cdc.getPageSize(),totalCount);
    }
    /**
     * 新增司机
     * @param dto
     * @return
     */
    @Override
    public Long addCommonDriver(CommDriverDto dto) {
        Long l = 0l;
        if (commDriverRepository
                    .checkHasCar(dto.getCar().getId())) {
            CommonDriver cd = new CommonDriver();
            cd.setIsActive(true);
            cd.setCarId(dto.getCar().getId());
            cd.setCreateDateTime(new Date());
                l = commDriverRepository.save(cd);
        }
        return l;
    }

    @Override
    public boolean delCommonDriver(Long[] ids) {
           return commDriverRepository.delete(ids);
    }

    /**
     * 修改司机
     * @param dto
     * @return
     */
    @Override
    public boolean updCommonDriver(CommDriverDto dto) {
        Long l = 0l;
        if(commDriverRepository.checkHasDriver(dto.getDriver().getId())){
            CommonDriver cd = new CommonDriver();
            cd.setIsActive(true);
            cd.setId(dto.getId());
            cd.setCarId(dto.getCar().getId());
            cd.setDriverId(dto.getDriver().getId());
            cd.setLastUpdateTime(new Date());
            l = commDriverRepository.save(cd);
        }
        return l > 0;
    }

    @Override
    public Long addAndUpdate(CommDriverDto dto) {
        Long l = 0l;
            if(null != dto.getId()
                    && 0l != dto.getId()){
                CommonDriver cd = new CommonDriver();
                cd.setId(dto.getId());
                cd.setIsActive(true);
                cd.setCarId(dto.getCar().getId());
                cd.setCreateDateTime(new Date());
                cd.setDriverId(dto.getDriver().getId());
                l = commDriverRepository.save(cd);
            }else{
                if(commDriverRepository.checkHasDriver(dto.getDriver().getId())){
                    CommonDriver cd = new CommonDriver();
                    cd.setIsActive(true);
                    cd.setCarId(dto.getCar().getId());
                    cd.setDriverId(dto.getDriver().getId());
                    cd.setLastUpdateTime(new Date());
                    l = commDriverRepository.save(cd);
                }
            }
        return l;
    }
    /**
     * 根据id获取司机
     * @param id
     * @return
     */
    @Override
    public CommDriverDto get(Long id) {
        Criteria criteria = getSession().createCriteria(CommonDriver.class);
        criteria.add(Restrictions.eq("id",id));
        CommonDriver cd = (CommonDriver) criteria.list().get(0);
        Car car = carRepository.get(cd.getCarId());
        Driver driver = driverRepository.get(cd.getDriverId());
        CommDriverDto cdd = new CommDriverDto();
        cdd.setId(cd.getId());
        cdd.setCar(car);
        cdd.setDriver(driver);
        return cdd;
    }

    /**
     * 获取车辆（避免车辆重复）
     * @return
     */
    @Override
    public List<Car> getCar() {
        Criteria criteria = getSession().createCriteria(Car.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<Car> cars = criteria.list();
        Criteria criteria_cd = getSession().createCriteria(CommonDriver.class);
        criteria_cd.add(Restrictions.eq("isActive",true));
        List<CommonDriver> cds = criteria_cd.list();
        Iterator car_it = cars.iterator();
        while (car_it.hasNext()){
            Car car = (Car) car_it.next();
            for (int i = 0 ; i < cds.size() ; i++){
                if(car.getId().equals(cds.get(i).getCarId())){
                    car_it.remove();
                    break;
                }
            }
        }
        return cars;
    }
    /**
     * 根据ID获取车辆（避免车辆重复）
     * @param id
     * @return
     */
    @Override
    public List<Car> getCar(Long id) {
        Criteria criteria = getSession().createCriteria(Car.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<Car> cars = criteria.list();
        Criteria criteria_cd = getSession().createCriteria(CommonDriver.class);
        criteria_cd.add(Restrictions.eq("isActive",true));
        List<CommonDriver> cds = criteria_cd.list();
        Iterator car_it = cars.iterator();
        while (car_it.hasNext()){
            Car car = (Car) car_it.next();
            for (int i = 0 ; i < cds.size() ; i++){
                if(cds.get(i).getId().equals(id)){
                    continue;
                }
                if(car.getId().equals(cds.get(i).getCarId())){
                    car_it.remove();
                }
            }
        }
        return cars;
    }

    /**
     * 获取所有司机对象
     * @return
     */
    @Override
    public List<Driver> getDriver() {
        Criteria criteria = getSession().createCriteria(Driver.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<Driver> drivers = criteria.list();
        Criteria criteria_cd = getSession().createCriteria(CommonDriver.class);
        criteria_cd.add(Restrictions.eq("isActive",true));
        List<CommonDriver> cds = criteria_cd.list();
        Iterator driver_it = drivers.iterator();
        while (driver_it.hasNext()){
            Driver driver = (Driver) driver_it.next();
            for (int i = 0 ; i < cds.size() ; i++){
                if(driver.getId().equals(cds.get(i).getDriverId())){
                    driver_it.remove();
                    break;
                }
            }
        }


        return drivers;
    }

    /**
     * 根据ID获取司机
     * @param id
     * @return
     */
    @Override
    public List<Driver> getDriver(Long id) {
        Criteria criteria = getSession().createCriteria(Driver.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<Driver> drivers = criteria.list();
        Criteria criteria_cd = getSession().createCriteria(CommonDriver.class);
        criteria_cd.add(Restrictions.eq("isActive",true));
        List<CommonDriver> cds = criteria_cd.list();
        Iterator driver_it = drivers.iterator();
        while (driver_it.hasNext()){
            Driver driver = (Driver) driver_it.next();
            for (int i = 0 ; i < cds.size() ; i++){
                if(cds.get(i).getId().equals(id)){
                    continue;
                }
                if(driver.getId().equals(cds.get(i).getDriverId())){
                    driver_it.remove();
                }
            }
        }
        return drivers;
    }

    /**
     * 获取所有车辆对象
     * @return
     */
    @Override
    public List<Car> getAllCar() {
        List<Car> cars = new ArrayList<Car>();
        Criteria criteria = getSession().createCriteria(Car.class);
        criteria.add(Restrictions.eq("isActive",true));
        cars = criteria.list();
        return cars;
    }

    /**
     * 获取所有司机对象
     * @return
     */
    @Override
    public List<Driver> getAllDriver() {
        List<Driver> drivers = new ArrayList<Driver>();
        Criteria criteria = getSession().createCriteria(Driver.class);
        criteria.add(Restrictions.eq("isActive",true));
        drivers = criteria.list();
        return drivers;
    }

    @Override
    public CommonDriver getCommonDriverByCarId(Long carid) {
        List<CommonDriver> listByCarid = new ArrayList<CommonDriver>();
        Criteria criteria = getSession().createCriteria(CommonDriver.class);
        criteria.add(Restrictions.eq("carId",carid));
        criteria.add(Restrictions.eq("isActive",true));
        listByCarid = criteria.list();
        if ( listByCarid.size() == 0 ){
            return null;
        }
        return listByCarid.get(0);
    }

    private CommDriverDto convertToDto(CommonDriver cd){
        CommDriverDto cdd = new CommDriverDto();
        Car car = carRepository.get(cd.getCarId());
        Driver driver = driverRepository.get(cd.getDriverId());
        cdd.setId(cd.getId());
        cdd.setCar(car);
        cdd.setDriver(driver);
        cdd.setCarNo(car.getCarNo());
        cdd.setDriverName(driver.getDriverName());
        return cdd;
    }
}
