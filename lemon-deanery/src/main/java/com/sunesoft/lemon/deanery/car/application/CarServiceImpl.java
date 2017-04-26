package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.CarCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.CommDriverCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CarReportDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CommDriverDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CompanyDto;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.*;

import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecution;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.dept.DeptmentRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangwj on 2016/6/15 0015.
 * edit by xubo on 2016/6/15
 */
@Service("carService")
public class CarServiceImpl extends GenericHibernateFinder implements CarService {
    @Autowired
    CarRepository carRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    DeptmentRepository deptmentRepository;
    /**
     * 根据ID获取车辆信息
     * @param carId
     * @return
     */
    @Override
    public CarDto getByIdCar(Long carId) {
        Car car = carRepository.get(carId);
        Company company =  companyRepository.get(car.getCompanyId());
        CarDto carDto = DeaneryUtil.converFormListCatDto(car);
        carDto.setCompanyName(company.getName());
        return carDto;
    }


    @Override
    public Long addCar(CarDto car) {
        return null;
    }


    @Override
    public boolean delCar(Long carId) {
        return false;
    }
    /**
     * 新增或者修改车辆信息
     * @param dto
     * @return
     */
    @Override
    public Long addOrUpdate(CarDto dto) {
        Car c = DeaneryUtil.convertFormListCarrDto(dto);
        if(dto.getId() != null && !"".equals(dto.getId())){
            c.setRepairLogTxt(dto.getRepairLogOld().getBytes(Charset.forName("UTF-8")));
           // if(dto.getStatus() == 3){
                String s = "";
                if(dto.getRepairLogOld() !=null && !"".equals(dto.getRepairLogOld())) {
                    s = dto.getRepairLogOld() + "|" + dto.getRepairLog();
                }else{
                    s = dto.getRepairLog();
                }
                c.setRepairLogTxt(s.getBytes(Charset.forName("UTF-8")));
          //`  }
        }
        Long l = carRepository.save(c);
        return l;
    }

    /**
     * 删除车辆
     * @param carIds
     * @return
     */
    @Override
    public boolean delCar(Long[] carIds) {
        Criteria criterion = getSession().createCriteria(Car.class);
        if (carIds!=null&&carIds.length>0) {
            criterion.add(Restrictions.in("id", carIds));
        }
        List<Car> beans = criterion.list();
        for (Car bean : beans) {
            bean.setIsActive(false);
            carRepository.save(bean);
        }
        return true;
    }

    @Override
    public boolean updCar(CarDto car) {
        return false;
    }

    @Override
    public boolean updCar(Long carId) {
        return false;
    }

    /**
     * 车辆汇总信息查询
     * @param carReportCriteria
     * @return
     */
    @Override
    public CarReportDto carReport(CarReportCriteria carReportCriteria) {
        CarReportDto carReportDto = new CarReportDto();
        carReportDto.setReportInfo(carRepository.carReport(carReportCriteria));
        carReportDto.setCompanyId(carReportCriteria.getCompanyId());
        carReportDto.setCarType(carReportCriteria.getCarType());
        carReportDto.setStatus(carReportCriteria.getStatus());
        carReportDto.setCompanys(companyRepository.getAllcoms());
        carReportDto.setCarTypeList(carRepository.carTypeList());
        return carReportDto;
    }

    public CarRepository getCarRepository() {
        return carRepository;
    }

    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public List<Company> getComs() {
        return companyRepository.getAllcoms();
    }

    /**
     * 车辆展示
     * @param carCriteria
     * @return
     */
    @Override
    public PagedResult<CarDto> getCars(CarCriteria carCriteria) {
        Criteria criterion = getSession().createCriteria(Car.class);
        criterion.add(Restrictions.eq("isActive", true));

        if(carCriteria.getCompanyId()!= null ){
            criterion.add(Restrictions.eq("companyId",carCriteria.getCompanyId()));
        }
        if(carCriteria.getStatus()!=null){
            criterion.add(Restrictions.eq("status",carCriteria.getStatus()));
        }
        if(carCriteria.getCarType()!=null && !("").equals(carCriteria.getCarType())){
            criterion.add(Restrictions.eq("carType",carCriteria.getCarType()));
        }
        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((carCriteria.getPageNumber() - 1) * carCriteria.getPageSize()).setMaxResults(carCriteria.getPageSize());
        List<Car> cars = criterion.list();
        List<CarDto> car = new ArrayList<CarDto>();
        for ( Car carss : cars){
            Company company =  companyRepository.get(carss.getCompanyId());
            CarDto carDto= DeaneryUtil.converFormListCatDto(carss);
            carDto.setCompanyName(company.getName());

           /* Deptment s= carReportByNo(Long.parseLong(carDto.getControlName()));
            carDto.setControlName(s.getDeptName());*/
            car.add(carDto);

        }
        return new PagedResult<CarDto>(car,carCriteria.getPageNumber(),carCriteria.getPageSize(),totalCount);
    }

    @Override
    public List<Car> getAllCar() {
        return carRepository.getAllcar();
    }

    @Override
    public Deptment carReportByNo(Long no) {
        Deptment deptment=deptmentRepository.get(no);
      return deptment;
    }

}
