package com.sunesoft.lemon.deanery.productionData;

import com.sunesoft.lemon.deanery.car.application.criteria.DriverCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.car.domain.Driver;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto1;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zy on 2016/10/7.
 */
@Service(value = "productionDateService")
public class ProductionDateServiceImpl extends GenericHibernateFinder implements ProductionDateService {
    @Autowired
    ProductionDateRepository productionDateRepository;
    @Override
    public PagedResult<ProductionDate> getByProductionDate(ProductionDateCria productionDateCria) {

        Criteria criterion = getSession().createCriteria(ProductionDate.class);
        criterion.add(Restrictions.eq("isActive", true));
        if(productionDateCria.getYN().equals("0")) {
            criterion.add(Restrictions.eq("YN", 0));
        }
        if(productionDateCria.getYW().equals("1")) {
            criterion.add(Restrictions.eq("YW", 1));
        }
        if(productionDateCria.getYPPH() != null && !"".equals(productionDateCria.getYPPH())) {
            criterion.add(Restrictions.like("YPPH", productionDateCria.getYPPH(), MatchMode.ANYWHERE));
        }
        if(productionDateCria.getWTDW() != null && !"".equals(productionDateCria.getWTDW())) {
            criterion.add(Restrictions.like("WTDW", productionDateCria.getWTDW(), MatchMode.ANYWHERE));
        }
        if(productionDateCria.getWTR() != null && !"".equals(productionDateCria.getWTR())) {
            criterion.add(Restrictions.like("WTR", productionDateCria.getWTR(), MatchMode.ANYWHERE));
        }
        if (null != productionDateCria.getBegintime()&& !"".equals(productionDateCria.getBegintime())){
         //   SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            Date sss=DateHelper.parse(productionDateCria.getBegintime(), "yyyy-MM-dd");
                criterion.add(Restrictions.ge("SYRQ1",DateHelper.parse(productionDateCria.getBegintime(), "yyyy-MM-dd")));

        }
        if (null != productionDateCria.getEndtime()&& !"".equals(productionDateCria.getEndtime())){
                criterion.add(Restrictions.le("SYRQ1",DateHelper.parse(productionDateCria.getEndtime(), "yyyy-MM-dd")));
        }
        if (null != productionDateCria.getBegintime1()&& !"".equals(productionDateCria.getBegintime1())){
            //   SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            criterion.add(Restrictions.ge("BCRQ1",DateHelper.parse(productionDateCria.getBegintime1(), "yyyy-MM-dd")));

        }
        if (null != productionDateCria.getEndtime1()&& !"".equals(productionDateCria.getEndtime1())){
            criterion.add(Restrictions.le("BCRQ1",DateHelper.parse(productionDateCria.getEndtime1(), "yyyy-MM-dd")));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((productionDateCria.getPageNumber() - 1) * productionDateCria.getPageSize()).setMaxResults(totalCount);
        List<ProductionDate> beans = criterion.list();
        PagedResult<ProductionDate> pagedResult = new PagedResult<ProductionDate>(beans,productionDateCria.getPageNumber(),productionDateCria.getPageSize(),totalCount);
        List<ProductionDate> list = new ArrayList<ProductionDate>();

        for ( ProductionDate bea : beans){
            ProductionDate productionDate= new ProductionDate();
            BeanUtils.copyProperties(bea, productionDate);
            list.add(productionDate);
        }

        return new PagedResult<ProductionDate>(list,productionDateCria.getPageNumber(),totalCount,totalCount);
    }

    @Override
    public List<ProductionDateDto1> downByOrderId(Boolean is){
        List<ProductionDateDto1> dto=new ArrayList<ProductionDateDto1>() ;
        Criteria criteria = getSession().createCriteria(ProductionDate.class);
        criteria.add(Restrictions.eq("isActive",is));
        List<ProductionDate> list=criteria.list();
        for(int i=0;i<list.size();i++){
            dto.add(DeaneryUtil.convertFromListProductionDateDto1(list.get(i)));
        }
        return dto;
    }

       @Override
    public ProductionDate getDateById(Long id) {
        ProductionDate productionDate=productionDateRepository.get(id);
           Date BGFSSJ=productionDate.getBGFSSJ1() ;
           Date JSRQ=productionDate.getJSRQ1();
           Date SYRQ=productionDate.getSYRQ1();
           Date BCRQ=productionDate.getBCRQ1();
           productionDate.setBGFSSJ2( DateHelper.formatDate(BGFSSJ, "yyyy-MM-dd"));
           productionDate.setJSRQ2(DateHelper.formatDate(JSRQ, "yyyy-MM-dd"));
           productionDate.setSYRQ2(DateHelper.formatDate(SYRQ, "yyyy-MM-dd"));
           productionDate.setBCRQ2(DateHelper.formatDate(BCRQ, "yyyy-MM-dd"));
           return productionDate;
    }

}
