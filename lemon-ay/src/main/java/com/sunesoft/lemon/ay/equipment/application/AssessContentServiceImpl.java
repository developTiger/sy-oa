package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.AssessContentCritera;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equipment.domain.AssessContectRepositroy;
import com.sunesoft.lemon.ay.equipment.domain.AssessContent;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/19.
 */
@Service("assessContentService")
public class AssessContentServiceImpl extends GenericHibernateFinder implements AssessContentService {
    @Autowired
    AssessContectRepositroy assessContectRepositroy;

    @Override
    public Long addOrUpdateContent(AssessContentDto dto) {

        Long l = 0L;
        if (null != dto.getId() && 0L != dto.getId()) {
            AssessContent assessContent =  assessContectRepositroy.get(dto.getId());
            assessContent.setParameterName(dto.getParameterName());
            assessContent.setParameterRange(dto.getParameterRange());
            assessContent.setTestValue(dto.getTestValue());
            assessContent.setConform(dto.getConform());
            assessContent.setTestCrew(dto.getTestCrew());
            assessContent.setSuggest(dto.getSuggest());
            assessContent.setImplement(dto.getImplement());
            assessContent.setAssmentId(dto.getAssmentId());
            l = assessContectRepositroy.save(assessContent);
        } else {
            l = assessContectRepositroy.save(DtoFactory.convert(dto, new AssessContent()));
        }
        return  l;
    }


    @Override
    public CommonResult delContent(List<Long> ids) {
        Criteria criteria = getSession().createCriteria(AssessContent.class);
        criteria.add(Restrictions.eq("isActive",true));
        if(ids == null || ids.size() <1 ){
            return ResultFactory.commonError("请选择删除对象");
        }
        criteria.add(Restrictions.in("id",ids));
        List<AssessContent> lists = criteria.list();
        for(AssessContent assessContent:lists){
            assessContent.setIsActive(false);
            assessContent.setLastUpdateTime(new Date());
            assessContectRepositroy.save(assessContent);
        }
        return ResultFactory.commonSuccess();
    }


    @Override
    public List<AssessContent> getAllContents() {
       return  assessContectRepositroy.getAllContents();
    }

    @Override
    public PagedResult<AssessContentDto> getPageAssessContent(AssessContentCritera contentCritera) {
        Criteria criteria = getSession().createCriteria(AssessContent.class);
        criteria.add(Restrictions.eq("isActive",true));
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((contentCritera.getPageNumber() - 1) * contentCritera.getPageSize()).setMaxResults(contentCritera.getPageSize());
        List<AssessContent> beans = criteria.list();
        List<AssessContentDto> list = new ArrayList<>();
        for(AssessContent content : beans){
            list.add(DtoFactory.convert(content,new AssessContentDto()));
        }
        return new PagedResult<AssessContentDto>(list, contentCritera.getPageNumber(), contentCritera.getPageSize(), totalCount);
    }

    @Override
    public AssessContent get(Long id) {
        AssessContent assessContent = null;
        if(id != null){
          assessContent =  assessContectRepositroy.get(id);
        }
        return assessContent;
    }

    @Override
    public List<AssessContentDto> getAllByEquipmentId(Long equipmentId) {
        List<AssessContent> list=assessContectRepositroy.getAllByEquipmentId(equipmentId);
        List<AssessContentDto> assessContentDto = new ArrayList<>();
        for (AssessContent a:list){
            assessContentDto.add(DtoFactory.convert(a,new AssessContentDto()));
        }
        return assessContentDto;
    }
}
