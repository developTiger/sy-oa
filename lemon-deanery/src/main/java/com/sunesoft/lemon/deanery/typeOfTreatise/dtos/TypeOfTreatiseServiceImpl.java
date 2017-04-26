package com.sunesoft.lemon.deanery.typeOfTreatise.dtos;

import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.factory.ProjectDeaneryUtil;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputDate;
import com.sunesoft.lemon.deanery.typeOfTreatise.domain.TypeOfTreaiseRepository;
import com.sunesoft.lemon.deanery.typeOfTreatise.domain.TypeOfTreatise;
import com.sunesoft.lemon.deanery.typeOfTreatise.factory.TypeOfTreatiseDeaneryUtil;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pxj on 2016/12/15.
 */
@Service("typeOfTreatiseService")
public class TypeOfTreatiseServiceImpl extends GenericHibernateFinder implements TypeOfTreatiseService {
    @Autowired
    private TypeOfTreaiseRepository typeOfTreaiseRepository;

    @Override
    public PagedResult<TypeOfTreatiseDto> getTypeOfTreatise(TypeOfTreatiseDto typeOfTreatiseDto) throws UnsupportedEncodingException {
        Criteria criterion = getSession().createCriteria(TypeOfTreatise.class);
        criterion.add(Restrictions.eq("isActive", true));
/*        if (!StringUtils.isNullOrWhiteSpace(startTime)){
            Date st= DateHelper.parse(startTime,"yyyy");
            criterion.add(Restrictions.ge("projectPlan_InputYear",st));
        }
        if (!StringUtils.isNullOrWhiteSpace(endTime)){
            Date et= DateHelper.parse(endTime,"yyyy");
            criterion.add(Restrictions.le("projectPlan_InputYear",et));
        }*/
        //论著类型
        if(!StringUtils.isNullOrWhiteSpace(typeOfTreatiseDto.getType_Treatise_Name())){
            criterion.add(Restrictions.like("Type_Treatise_Name",URLDecoder.decode(typeOfTreatiseDto.getType_Treatise_Name(),"UTF-8"), MatchMode.ANYWHERE));
        }
/*        if(!StringUtils.isNullOrWhiteSpace(projectPlan_type)){
            criterion.add(Restrictions.like("projectPlan_Type",projectPlan_type,MatchMode.ANYWHERE));
        }*/
        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((typeOfTreatiseDto.getPageNumber() - 1) * typeOfTreatiseDto.getPageSize()).setMaxResults(typeOfTreatiseDto.getPageSize());
        List<TypeOfTreatise> list = criterion.list();
        List<TypeOfTreatiseDto> typeOfTreatiseDtos = new ArrayList<TypeOfTreatiseDto>();
        for (TypeOfTreatise  t: list){
            typeOfTreatiseDtos.add(TypeOfTreatiseDeaneryUtil.converFromList(t));
        }
        return new PagedResult<TypeOfTreatiseDto>(typeOfTreatiseDtos,typeOfTreatiseDto.getPageNumber(),typeOfTreatiseDto.getPageSize(),totalCount);
    }

    @Override
    public CommonResult save(TypeOfTreatiseDto typeOfTreatiseDto) {
        TypeOfTreatise typeOfTreatise=TypeOfTreatiseDeaneryUtil.converFromListTypeOfTreatise(typeOfTreatiseDto);
        Date date=new Date();
        if(typeOfTreatise.getId()==null){
            typeOfTreatise.setCreateDateTime(date);
        }else{
            typeOfTreatise.setLastUpdateTime(date);
        }
        typeOfTreaiseRepository.save(typeOfTreatise);
        return ResultFactory.commonSuccess();
    }

    @Override
    public TypeOfTreatiseDto findById(String id) {
        TypeOfTreatise typeOfTreatise=typeOfTreaiseRepository.findById(id);
        TypeOfTreatiseDto typeOfTreatiseDto=TypeOfTreatiseDeaneryUtil.converFromList(typeOfTreatise);
        return typeOfTreatiseDto;
    }

    @Override
    public CommonResult delete(List<Long> listId) {
        Criteria criterion = getSession().createCriteria(TypeOfTreatise.class);
        if(listId==null||listId.size()<1){
            return ResultFactory.commonError("请选择将要删除的部门");
        }
        criterion.add(Restrictions.in("id", listId));
        List<TypeOfTreatise> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (TypeOfTreatise typeOfTreatise : list) {
                typeOfTreatise.setIsActive(false);
                typeOfTreatise.setLastUpdateTime(new Date());
                typeOfTreaiseRepository.save(typeOfTreatise);
            }
            return ResultFactory.commonSuccess();
        }
        String s = "此部门已不存在";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<TypeOfTreatiseDto> findAll() {
        List<TypeOfTreatise> typeOfTreatises=typeOfTreaiseRepository.findAll();
        List<TypeOfTreatiseDto> typeOfTreatiseDto=new ArrayList<>();
        for(TypeOfTreatise typeOfTreatise:typeOfTreatises){
            typeOfTreatiseDto.add(TypeOfTreatiseDeaneryUtil.converFromList(typeOfTreatise));
        }
        return typeOfTreatiseDto;
    }

    @Override
    public Long findByName(String awards_type) {
        Long dept_id = typeOfTreaiseRepository.findByName(awards_type);
        return dept_id;
    }
}
