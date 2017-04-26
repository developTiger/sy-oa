package com.sunesoft.lemon.deanery.car.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.CommDriverRepository;
import com.sunesoft.lemon.deanery.car.domain.CommonDriver;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xubo on 2016/6/20 0020.
 */
@Service("commDriverRepository")
public class CommDriverRepositoryImpl
        extends GenericHibernateRepository<CommonDriver,Long>
        implements CommDriverRepository{


    @Override
    public boolean checkHasCar(Long carId) {
        boolean flag = false;
        Criteria criteria = getSession().createCriteria(CommonDriver.class);
        if(null == carId && 0l >= carId){
            return flag;
        }
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("carId",carId));
        System.out.print(criteria.list().size());
        if(criteria.list().size() == 1){
            flag = false;
        }else{
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean checkHasDriver(Long driverId) {
        boolean flag = false;
        Criteria criteria = getSession().createCriteria(CommonDriver.class);
        if(null == driverId && 0l >= driverId){
            return flag;
        }
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("driverId",driverId));
        if(criteria.list().size() == 1 || criteria.list().size()  == 0){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }


    @Override
    public boolean delete(Long[] ids) {
        try {
            Criteria criteria = getSession().createCriteria(CommonDriver.class);
            criteria.add(Restrictions.in("id", ids));
            List<CommonDriver> commdrivers = criteria.list();
            for (CommonDriver cd : commdrivers) {
                cd.setIsActive(false);
                save(cd);
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<CommonDriver> getAll() {
        Criteria criteria = getSession().createCriteria(CommonDriver.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<CommonDriver> list = criteria.list();
        if(null == list || 0 == list.size()){
            return null;
        }
        return list;
    }

    @Override
    public List<CommonDriver> getAllByIsActive(List<Car> cars, List<Driver> drivers) {
        List<Long> cars_id = new ArrayList<Long>();
        List<Long> drivers_id = new ArrayList<Long>();
        List<CommonDriver> cd1 = new ArrayList<CommonDriver>();
        List<CommonDriver> cd2 = new ArrayList<CommonDriver>();
        List<CommonDriver> return_list = new ArrayList<CommonDriver>();
        for (Car car : cars){
            cars_id.add(car.getId());
        }
        for (Driver driver : drivers){
            drivers_id.add(driver.getId());
        }
        Long[] carsid = new Long[cars_id.size()];
        Long[] driverid = new Long[drivers.size()];

        Criteria criteria = getSession().createCriteria(CommonDriver.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.in("carId",cars_id.toArray(carsid)));
        cd1 = criteria.list();

        criteria = getSession().createCriteria(CommonDriver.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.in("driverId", drivers_id.toArray(driverid)));
        cd2 = criteria.list();

        Iterator<CommonDriver> it_car = cd1.iterator();
        Iterator<CommonDriver> it_driver = cd2.iterator();
        while (it_car.hasNext()){
            CommonDriver t1 = it_car.next();
            while (it_driver.hasNext()){
                CommonDriver t2 = it_driver.next();
                if (t1.getId().equals(t2.getId())){
                    return_list.add(t2);
                }
            }
        }
        return return_list;
    }
}


