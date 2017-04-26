package com.sunesoft.lemon.syms.parameter.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.parameter.application.criteria.ParameterTypeCriteria;
import com.sunesoft.lemon.syms.parameter.application.dtos.ParameterDto;
import com.sunesoft.lemon.syms.parameter.application.dtos.ParameterTypeDto;
import com.sunesoft.lemon.syms.parameter.domain.Parameter;
import com.sunesoft.lemon.syms.parameter.domain.ParameterType;
import com.sunesoft.lemon.syms.parameter.domain.ParameterTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

import java.util.List;


/**
 * Created by zy on 2016/6/2.
 */

@Service("parameterTypeService")
public class ParameterTypeServiceImpl extends GenericHibernateFinder implements ParameterTypeService {

    @Autowired
    ParameterTypeRepository parameterTypeRepository;

    /**
     * 添加数据service
     *
     * @param dto
     * @return
     */
    @Override
    public Long addParameterType(ParameterTypeDto dto) {

        return parameterTypeRepository.save(convertFromDto(dto));
    }


    /**
     * 群删
     *
     * @param parameterTypeId
     * @return
     */
    @Override
    public boolean deleteParameterType(Long[] parameterTypeId) {
        Criteria criterion = getSession().createCriteria(ParameterType.class);
        if (parameterTypeId != null && parameterTypeId.length > 0) {
            criterion.add(Restrictions.in("id", parameterTypeId));
        }
        List<ParameterType> list = criterion.list();
        for (ParameterType parameterType : list) {
            parameterType.setIsActive(false);
            parameterTypeRepository.save(parameterType);
        }
        return true;
    }

    /**
     * 更新数据
     *
     * @param dto
     * @return
     */
    @Override
    public Long updateParameterType(ParameterTypeDto dto) {
        ParameterType parameterType = parameterTypeRepository.get(dto.getId());
        if (parameterType == null) {

            return parameterTypeRepository.save(convertFromDto(dto));
        } else {
            if (dto.getParamTypeName() != null) {
                parameterType.setParamTypeName(parameterType.getParamTypeName());
            }
            if (dto.getParamDesc() != null) {
                parameterType.setParamDesc(parameterType.getParamDesc());
            }
            if (dto.getParameters() != null && dto.getParameters().size() > 0) {
                List<Parameter> prlist = new ArrayList<Parameter>();
                for (ParameterDto d : dto.getParameters()) {
                    Parameter r = new Parameter();
                    r.setId(d.getId());
                    r.setParamName(d.getParamName());
                    r.setParamDesc(d.getParamDesc());
                    r.setParamValue(d.getParamValue());
                    r.setRemark(d.getRemark());
                    r.setAttrbute1(d.getAttrbute1());
                    r.setAttrbute2(d.getAttrbute2());
                     /*......*/
                    prlist.add(r);
                }
            }
            return 1L;
        }
    }

    /**
     * 获得所有数据
     * isActive为true
     *
     * @return
     */
    @Override
    public List<ParameterTypeDto> getAllparametertype() {
        Criteria criterion = getSession().createCriteria(ParameterType.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<ParameterType> list1 = criterion.list();
        List<ParameterTypeDto> list = new ArrayList<ParameterTypeDto>();
        for (ParameterType pt : list1) {
            list.add(convertToDto(pt));
        }
        return list;
    }

    /**
     * 根据id'
     *
     * @param id
     * @return
     */
    @Override
    public ParameterTypeDto getById(Long id) {
        return convertToDto(parameterTypeRepository.get(id));
    }

    @Override
    public List<ParameterTypeDto> getAllParameterType(String parameterTypeName) {
        Criteria criterion = getSession().createCriteria(ParameterType.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(parameterTypeName)) {
            criterion.add(Restrictions.like("param_type_name", "%" + parameterTypeName + "%"));
        }
        List<ParameterType> list1 = criterion.list();
        List<ParameterTypeDto> list = new ArrayList<ParameterTypeDto>();
        for (ParameterType paramterType : list1) {
            list.add(convertToDto(paramterType));
        }
        return list;
    }

    /**
     * 分页查询
     *
     * @param criteria 查询条件
     * @return
     */
    @Override
    public PagedResult<ParameterTypeDto> FindParam(ParameterTypeCriteria criteria) {
        Criteria criterion = getSession().createCriteria(ParameterType.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getParamTypeName())) {
            criterion.add(Restrictions.like("param_name", "%" + criteria.getParamTypeName() + "%"));
        }
        //获取总记录数
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());

        List<ParameterTypeDto> parameterTypeListDto = new ArrayList<ParameterTypeDto>();
        List<ParameterType> parameterTypeList = criterion.list();
        for (ParameterType paramtype : parameterTypeList) {
            if (paramtype.getIsActive() != null && paramtype.getIsActive() != false) {
                parameterTypeListDto.add(convertToDto(paramtype));
            }
        }
        return new PagedResult<ParameterTypeDto>(parameterTypeListDto, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }


    //调用方法1 dto转实体
    private ParameterType convertFromDto(ParameterTypeDto dto) {
        ParameterType parameterType = new ParameterType();
        if (dto.getId() != null && dto.getId() > 0) {
            parameterType = parameterTypeRepository.get(dto.getId());
        }
        //将dto里的参数设置到原实体类里
        parameterType.setParamTypeName(dto.getParamTypeName());
        parameterType.setParamDesc(dto.getParamDesc());
        return parameterType;
    }

    //调用方法2 实体转dto
    private ParameterTypeDto convertToDto(ParameterType paramtype) {
        ParameterTypeDto dto = new ParameterTypeDto();
        if (dto.getId() != null && dto.getId() > 0) {
            dto.setId(paramtype.getId());
        }
        dto.setParamTypeName(paramtype.getParamTypeName());
        dto.setParamDesc(paramtype.getParamDesc());


        return dto;
    }
}
