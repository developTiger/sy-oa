package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.SpecialtyTypeCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.Car;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.car.domain.SpectityTypeRepository;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy.
 */
@Service("specialtyTypeService")
public class SpecialtyTypeServiceImpl extends GenericHibernateFinder implements SpecialtyTypeService {

    @Autowired
    private SpectityTypeRepository spectityTypeRepository;
    @Override
    public SpecialtyType getByIdType(Long id) {
        return spectityTypeRepository.get(id);
    }

    @Override
    public Long addType(SpecialtyType type) {
        type.setIsActive(true);
        return  spectityTypeRepository.save(type);
    }

    @Override
    public Long updateType(SpecialtyType type) {
        return null;
    }

    @Override
    public boolean getType(Long id) {
        Criteria criteria = getSession().createCriteria(SpecialtyType.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.add(Restrictions.eq("isActive",true));
        List<SpecialtyType> list = criteria.list();
        List<DriverDto> list_dto = new ArrayList<DriverDto>();

        return false;
    }

    @Override
    public boolean delTypes(Long[] ids)
    {
        Criteria criterion = getSession().createCriteria(SpecialtyType.class);
        if (ids!=null&&ids.length>0) {
            criterion.add(Restrictions.in("id", ids));
        }
        List<SpecialtyType> beans = criterion.list();
        for (SpecialtyType bean : beans) {
            bean.setIsActive(false);
            spectityTypeRepository.save(bean);
        }
        return true;


    }

    @Override
    public List<SpecialtyType> getAllType(){
        Criteria criteria = getSession().createCriteria(SpecialtyType.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<SpecialtyType> list = criteria.list();
         return list;

    }

    @Override
    public PagedResult<SpecialtyType> getType(SpecialtyTypeCriteria specialtyTypeCriteria) {
        Criteria criterion = getSession().createCriteria(SpecialtyType.class);
        criterion.add(Restrictions.eq("isActive", true));
        if(specialtyTypeCriteria.getSpecialtyType()!=null&&!"".equals(specialtyTypeCriteria.getSpecialtyType())){
            criterion.add(Restrictions.like("specialtyType","%"+ specialtyTypeCriteria.getSpecialtyType()+"%"));
        }

        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((specialtyTypeCriteria.getPageNumber() - 1) * specialtyTypeCriteria.getPageSize()).setMaxResults(specialtyTypeCriteria.getPageSize());
        List<SpecialtyType> specialtyTypes = criterion.list();

        return new PagedResult<SpecialtyType>(specialtyTypes,specialtyTypeCriteria.getPageNumber(),specialtyTypeCriteria.getPageSize(),totalCount);
    }

    @Override
    public ScientificRPKUDto getIdByProjectNoDto(String projectName){
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectName",projectName));
        criteria.add(Restrictions.eq("isActive", true));
        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            ScientificRPKUDto scientificRPKUDto= DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            return scientificRPKUDto;
        }
        return null;
    }
}
