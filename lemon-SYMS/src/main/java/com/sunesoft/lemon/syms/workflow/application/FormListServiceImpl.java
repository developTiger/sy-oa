package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.application.factory.WorkflowDtoFactory;
import com.sunesoft.lemon.syms.workflow.domain.FormList;
import com.sunesoft.lemon.syms.workflow.domain.FormListRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/5/30.
 */
@Service("formListService")
public class FormListServiceImpl extends GenericHibernateFinder implements FormListService {


    @Autowired
    FormListRepository formListRepository;

    @Override
    public FormListDto getByKey(Long id) {
        FormList formList = formListRepository.get(id);
        FormListDto dto = new FormListDto();
        BeanUtils.copyProperties(formList, dto);
        return dto;
    }

    @Override
    public Long add(FormListDto dto) {
        FormList formList = new FormList();
        BeanUtils.copyProperties(dto, formList);
        List<FormList> list = getFormByKind(dto.getFormKind());
        if (list != null && list.size() > 0 && list.get(0).getId() != dto.getId()) {
            System.out.print("有相同的kind");
            return null;
        }
        Long id = formListRepository.save(formList);
        return id;
    }

    @Override
    public FormListDto getFormListInfo(String formKind) {
        List<FormList> lists = getFormByKind(formKind);
        if (lists != null && lists.size() > 0)
            return DtoFactory.convert(lists.get(0), new FormListDto());
        else
            return null;
    }

    @Override
    public CommonResult update(FormListDto dto) {
        FormList formList = formListRepository.get(dto.getId());
        List<FormList> list = getFormByKind(dto.getFormKind());
        if (list.size() > 0 && list.get(0).getId() != dto.getId()) {
            System.out.print("有相同的kind");
            return ResultFactory.commonError("表单编号已存在");
        }

        BeanUtils.copyProperties(dto, formList);
        formListRepository.save(formList);
        return ResultFactory.commonSuccess();
    }

    private List<FormList> getFormByKind(String formKind) {
        Criteria criterion = getSession().createCriteria(FormList.class);
        if (!StringUtils.isNullOrWhiteSpace(formKind)) {
            criterion.add(Restrictions.eq("formKind", formKind));
        }

        List<FormList> beans = criterion.list();

        //  System.out.println(JsonHelper.toJson(beans));
        return beans;
    }

    @Override
    public CommonResult delete(Long id) {
        formListRepository.delete(id);
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<FormListDto> getFormListPaged(FormListCriteria criteria) {
        Criteria criterion = getSession().createCriteria(FormList.class);
        if (criteria.getId() != null && criteria.getId() != 0L) {
            criterion.add(Restrictions.eq("id", criteria.getId()));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            criterion.add(Restrictions.like("formKind", "%" + criteria.getFormKind() + "%"));
        }
        if (criteria.getFormNo() != null && criteria.getFormNo() > 0) {
            criterion.add(Restrictions.eq("formNo", criteria.getFormNo()));
        }
        if(!StringUtils.isNullOrWhiteSpace(criteria.getFormName())) {
            criterion.add(Restrictions.like("formName", "%" + criteria.getFormName() + "%"));
        }
        if (criteria.getHasApplyView() != null) {
            criterion.add(Restrictions.eq("hasApplyView", criteria.getHasApplyView()));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criteria.setOrderByProperty(criteria.getOrderByProperty());
        criteria.setAscOrDesc(criteria.isAscOrDesc());
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<FormList> beans = criterion.list();
        List<FormListDto> list = new ArrayList<FormListDto>();
        for (FormList user : beans) {
            list.add(WorkflowDtoFactory.convertDtoToFormList(user));
        }
        //  System.out.println(JsonHelper.toJson(beans));
        return new PagedResult<FormListDto>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);

    }

    private FormListDto convertToDto(FormList fmlist) {
        FormListDto dto = new FormListDto();
        dto.setFormKind(fmlist.getFormKind());
        dto.setId(fmlist.getId());
        dto.setFormName(fmlist.getFormName());
        return dto;

    }
}
