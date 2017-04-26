package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.criteria.AdviceTypeCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.RoleTypeDto;
import com.sunesoft.lemon.syms.eHr.application.factory.AdviceTypeFactory;
import com.sunesoft.lemon.syms.eHr.domain.notice.AdviceType;
import com.sunesoft.lemon.syms.eHr.domain.notice.AdviceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2017/1/18.
 */
@Service("adviceTypeService")
public class AdviceTypeServiceImpl extends GenericHibernateFinder implements AdviceTypeService {

    @Autowired
    AdviceTypeRepository adviceTypeRepository;

    @Override
    public CommonResult addAdviceType(RoleTypeDto dto) {
        //验证是否已经存在
        List<AdviceType> list = adviceTypeRepository.getByName(dto.getName());
        if (list != null && list.size() > 0)
            return ResultFactory.commonError("该名称已经存在!");
        Long id = adviceTypeRepository.save(AdviceTypeFactory.convert(dto, new AdviceType()));
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public CommonResult updateAdvicetype(RoleTypeDto dto) {
        List<AdviceType> list = adviceTypeRepository.getByName(dto.getName());
        AdviceType adviceType = adviceTypeRepository.get(dto.getId());
        if (list != null && list.size() > 0 && adviceType.getIsActive() && !adviceType.getName().equals(dto.getName()))
            return ResultFactory.commonError("修改后的名称已经存在!");
        adviceType=AdviceTypeFactory.convertFromDto(dto, adviceType);
        adviceType.setLastUpdateTime(new Date());
        Long id = adviceTypeRepository.save(adviceType);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public CommonResult delete(Long id) {
        AdviceType adviceType = adviceTypeRepository.get(id);
        if (adviceType == null || !adviceType.getIsActive())
            return ResultFactory.commonError("该数据不存在！");
        adviceType.setLastUpdateTime(new Date());
        adviceType.setIsActive(false);
        adviceTypeRepository.save(adviceType);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public CommonResult setStation(Long id, boolean b) {
        AdviceType adviceType = adviceTypeRepository.get(id);
        if (adviceType == null || !adviceType.getIsActive())
            return ResultFactory.commonError("该数据不存在！");
        adviceType.setLastUpdateTime(new Date());
        adviceType.setForbide(b);
        adviceTypeRepository.save(adviceType);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public CommonResult delete(List<Long> ids) {
        adviceTypeRepository.delete(ids);
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<RoleTypeDto> paged(AdviceTypeCriteria criteria) {
        PagedResult<AdviceType> pagedResult=adviceTypeRepository.page(criteria);
        return new PagedResult<>(AdviceTypeFactory.convertToDto(pagedResult.getItems()),pagedResult.getPageNumber(),pagedResult.getPageSize(),pagedResult.getTotalItemsCount());
    }

    @Override
    public RoleTypeDto getById(Long id) {
        AdviceType adviceType = adviceTypeRepository.get(id);
        if (adviceType != null && adviceType.getIsActive())
            return AdviceTypeFactory.convertToDto(adviceType);
        return null;
    }

    @Override
    public List<RoleTypeDto> getByName(String name) {
        List<AdviceType> list = adviceTypeRepository.getByName(name);
        return AdviceTypeFactory.convertToDto(list);
    }

    @Override
    public List<RoleTypeDto> getCanUse() {
       AdviceTypeCriteria criteria=new AdviceTypeCriteria();
        criteria.setForbide(true);
        PagedResult<AdviceType> pg=adviceTypeRepository.page(criteria);
        List<RoleTypeDto> list=new ArrayList<>();
        if(pg.getItems().size()>0){
            list=AdviceTypeFactory.convertToDto(pg.getItems());
        }
        return list;
    }
}
