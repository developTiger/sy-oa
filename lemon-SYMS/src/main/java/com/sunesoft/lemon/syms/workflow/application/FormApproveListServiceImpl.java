package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppListDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppRoleDto;
import com.sunesoft.lemon.syms.workflow.application.factory.WorkflowDtoFactory;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveList;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveListRepository;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRole;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRoleRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/6/16.
 */
@Service("formApproveListService")
public class FormApproveListServiceImpl extends GenericHibernateFinder implements FormApproveListService {

    @Autowired
    FormApproveListRepository formApproveListRepository;
    @Autowired
    FormApproveRoleRepository formApproveRoleRepository;

    @Override
    public List<FormApproveList> getApproveListByFormKind(String formKind) {
        Criteria criterion = getSession().createCriteria(FormApproveList.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (formKind != null && !formKind.equals("")) {
            criterion.add(Restrictions.eq("formKind", formKind));
        }
        return criterion.list();
    }

    @Override
    public CommonResult AddFormApproveList(FormAppListDto dto) {
        FormApproveList formApproveList = new FormApproveList();
        FormApproveRole role = formApproveRoleRepository.get(dto.getAppRoleId());
        BeanUtils.copyProperties(dto, formApproveList);
        formApproveList.setAppRole(role);
        Long id = formApproveListRepository.save(formApproveList);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public CommonResult UpdateFormApproveList(FormAppListDto dto) {
        FormApproveList formApproveList = formApproveListRepository.get(dto.getId());
        FormApproveRole role = formApproveRoleRepository.get(dto.getAppRoleId());
        BeanUtils.copyProperties(dto, formApproveList);
        formApproveList.setAppRole(role);
        Long id = formApproveListRepository.save(formApproveList);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public CommonResult DeleteFormApproveList(Long id) {
        formApproveListRepository.delete(id);
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<FormAppListDto> getApproveListByCriteria(FormListCriteria criteria) {
        Criteria criterion = getSession().createCriteria(FormApproveList.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            criterion.add(Restrictions.eq("formKind",  criteria.getFormKind() ));
        }
        if (criteria.getAppUserId() != null && !criteria.getAppUserId().toString().equals("")) {
            criterion.add(Restrictions.eq("appUserId", criteria.getAppUserId()));
        }
        List<FormApproveList> list = criterion.list();
        List<FormAppListDto> dtos = new ArrayList<>();

        if (list != null && list.size() > 0)
            for (FormApproveList ls : list) {
                FormAppListDto dto = WorkflowDtoFactory.convert(ls, new FormAppListDto());
                if (ls.getAppRole() != null)
                    dto.setFmAppRole(WorkflowDtoFactory.convert(ls.getAppRole(), new FormAppRoleDto()));
                dtos.add(dto);
            }
        return dtos;
    }

    @Override
    public FormAppListDto getById(Long  id) {
        FormApproveList fl= formApproveListRepository.get(id);
        FormAppListDto dto = WorkflowDtoFactory.convert(fl, new FormAppListDto());
        dto.setAppRoleId(fl.getAppRole().getId());
        return dto;
    }

    /**
     * 论著动态修改审核员
     * @param criteria   表单formkind
     * @param approver_role_id 审核员id
     * @return
     */
    @Override
    public CommonResult updateTreatiseApproveByCriteria(FormListCriteria criteria,FormApproveRole approver_role_id) {
        Criteria criterion = getSession().createCriteria(FormApproveList.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            criterion.add(Restrictions.eq("formKind",  criteria.getFormKind() ));
        }
        if (criteria.getAppUserId() != null && !criteria.getAppUserId().toString().equals("")) {
            criterion.add(Restrictions.eq("appUserId", criteria.getAppUserId()));
        }
        criterion.add(Restrictions.eq("appSerial",2));
        FormApproveList list = (FormApproveList) criterion.uniqueResult();
         list.setAppRole(approver_role_id);
        formApproveListRepository.save(list);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult updateResultCetificateApproveByCriteria(FormListCriteria criteria, FormApproveRole approver_role_id) {
        Criteria criterion = getSession().createCriteria(FormApproveList.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            criterion.add(Restrictions.eq("formKind",  criteria.getFormKind() ));
        }
        if (criteria.getAppUserId() != null && !criteria.getAppUserId().toString().equals("")) {
            criterion.add(Restrictions.eq("appUserId", criteria.getAppUserId()));
        }
        criterion.add(Restrictions.eq("appSerial",2));
        FormApproveList list = (FormApproveList) criterion.uniqueResult();
        list.setAppRole(approver_role_id);
        formApproveListRepository.save(list);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult changeRoleId(FormListCriteria criteria, FormApproveRole approver_role_id, String step) {
        Criteria criterion = getSession().createCriteria(FormApproveList.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            criterion.add(Restrictions.eq("formKind",  criteria.getFormKind() ));
        }
        if (criteria.getAppUserId() != null && !criteria.getAppUserId().toString().equals("")) {
            criterion.add(Restrictions.eq("appUserId", criteria.getAppUserId()));
        }
        criterion.add(Restrictions.eq("appSerial",step));
        FormApproveList list = (FormApproveList) criterion.uniqueResult();
        list.setAppRole(approver_role_id);
        formApproveListRepository.save(list);
        return ResultFactory.commonSuccess();
    }
}
