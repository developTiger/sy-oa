/*
package com.sunesoft.lemon.deanery;

import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.criteria.CarCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.fr.results.PagedResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

*
 * Created by jiangkefan on 2016/6/16.


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestCarService {
  @Autowired
    CarService carService;
    @Test
    public void testCar_addOrUpdate(){

            CarDto car = new CarDto();
//        car.setId(5L); //修改
        car.setCarNo("0000001");
        car.setControlName("llllllll");
        car.setCompanyId(1L);

//            car.setCompanyId(1L);

//            List<Company> companies = new ArrayList<Company>();
//            for(Company company : companies) {
//                company.setId(1L);
//                company.setName("llllllll");
//            }


        carService.addOrUpdate(car);


    }

    @Test
    public void testCar_getById(){
        CarDto c =  carService.getByIdCar(6L);
        System.out.println(c.getCarNo());
    }

    @Test
    public void testCar_getPage(){
        CarCriteria carCriteria = new CarCriteria();
        PagedResult result = carService.getCars(carCriteria);
        System.out.println(result.getPageNumber());
        System.out.println(result.getPageSize());
        System.out.println(result.getItems().size());
    }

    @Test
    public void testCar_del(){
        Long l[] = {6l,7l};
        boolean b = carService.delCar(l);
        System.out.print(b);
    }

    @Test
    public void testCar_getAllComs(){
        List<Company> list =  carService.getComs();
        for(Company company : list){
            System.out.print(company.getName());
        }
    }


    @Test
    public void testCar_getID(){
        CarDto car = new CarDto();
        Company company = new Company();
        company.setId(1L);
        car.setCompanyId(company.getId());
        car.setStatus(3);
        Long l = carService.addOrUpdate(car);

    }
}
*/
