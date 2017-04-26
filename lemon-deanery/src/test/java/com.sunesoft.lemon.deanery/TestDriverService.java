package com.sunesoft.lemon.deanery;

import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.car.application.criteria.CarCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.deanery.car.domain.DriverRepository;
import com.sunesoft.lemon.fr.results.PagedResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jiangkefan on 2016/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestDriverService {
    @Autowired
    DriverService driverService;

    @Test
    public void testDriver_getById(){
        DriverDto driverDto = driverService.getByIdDriver(Long.valueOf(1));
        System.out.print(driverDto.getDriverName());

    }

    @Test
    public void testDriver_save(){

        for(int i = 0 ; i< 5 ; i++){
            DriverDto driverDto = new DriverDto();
            driverDto.setCompanyId(2l);
            driverDto.setDriverName("jkf333");
            driverDto.setPhone("1111");
            driverDto.setStatus(1);
            Long l = driverService.addOrUpdate(driverDto);
            System.out.print(l);
        }


    }

    @Test
    public void testDriver_del(){
       boolean b =  driverService.delDriver(new Long[]{1L,2L});
       System.out.print(b);
    }

    @Test
    public void testDriver_page(){
        DriverCriteria driverCriteria = new DriverCriteria();
        PagedResult pagedResult = driverService.getByDriver(driverCriteria);
        System.out.print(pagedResult.getItems());

    }

}
