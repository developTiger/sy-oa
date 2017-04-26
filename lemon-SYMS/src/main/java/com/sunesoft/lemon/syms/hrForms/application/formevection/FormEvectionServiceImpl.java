package com.sunesoft.lemon.syms.hrForms.application.formevection;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.AttendanceEnsureService;
import com.sunesoft.lemon.syms.eHr.application.dtos.AttendanceOperateDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
//import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceRepository;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.AttendanceByFlowDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormBusinessDownloadDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormEvectionDto;
import com.sunesoft.lemon.syms.hrForms.application.criteria.EvectionCriteria;
import com.sunesoft.lemon.syms.hrForms.domain.FormEvection;
import com.sunesoft.lemon.syms.hrForms.domain.FormEvectionRepository;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jiangkefan on 2016/7/19.
 */
@Service("formEvectionService")
public class FormEvectionServiceImpl extends FormBase2<FormEvection, FormEvectionDto> implements FormEvectionService {
    @Autowired
    FormEvectionRepository formEvectionRepository;

    /*  @Autowired
      AttendanceRepository attendanceRepository;
  */
    @Autowired
    AttendanceEnsureService attendanceEnsureService;


    @Override
    protected CommonResult save(FormEvection formEvection) {
        formEvectionRepository.save(formEvection);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormEvection formEvection) {
        FormEvection evection = formEvectionRepository.get(formEvection.getId());
        evection.setReason(formEvection.getReason());
        evection.setTarget(formEvection.getTarget());
        evection.setToTime(formEvection.getToTime());
        evection.setEvectionTime(formEvection.getEvectionTime());
        formEvectionRepository.save(evection);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormEvection ConvetDto(FormEvectionDto dto) {
        FormEvection formEvection = new FormEvection();
        formEvection = DtoFactory.convert(dto, formEvection);
        return formEvection;
    }

    /**
     * 获取概要信息（概要信息字符串）
     *
     * @param formEvection
     * @return
     */
    @Override
    protected String getSummery(FormEvection formEvection) {


        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {

        return ResultFactory.commonSuccess();
    }



    /**
     * 审核结束后放入考勤表
     *
     * @param formNo
     * @return
     */
    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormEvection formEvection = this.getByFormNo(formNo);
        AttendanceOperateDto attendanceOperateDto = new AttendanceOperateDto();
        attendanceOperateDto = DtoFactory.FormEvectionConverToAttendanceOpDto(formEvection, attendanceOperateDto);
        attendanceEnsureService.addOrUpdateEnsureInfo(attendanceOperateDto);
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {

        String formNo=param.get("formNo").toString();
        String clStep=param.get("clStep").toString();
        if("3".equals(clStep)){
            try {
                this.doApproveOk(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            FormHeader header=formHeaderRepository.get(Long.parseLong(formNo));
//            header.setFormStatus(FormStatus.LW);
            formHeaderRepository.save(header);
            return ResultFactory.commonSuccess();
        }
        return super.doApprove(dto, param);
    }

    @Override
    public CommonResult doApproveOk(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) throws Exception {
        return super.doApproveOk(formNo, formKind, approverId, approver, content, agentId);
    }

    @Override
    protected FormEvection getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormEvection.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo", formNo));
        return (FormEvection) criteria.uniqueResult();
    }

    @Override
    public FormEvectionDto getFormByFormNo(Long formNo) {
        return DtoFactory.convert(this.getByFormNo(formNo), new FormEvectionDto());
    }

    @Override
    public PagedResult<FormEvectionDto> getEvectionPage(EvectionCriteria criteria) {
        Criteria criterion = getSession().createCriteria(FormEvection.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getApplyerName())) {
            criterion.add(Restrictions.like("applyerName", "%" + criteria.getApplyerName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getTarget())) {
            criterion.add(Restrictions.like("target", "%" + criteria.getTarget() + "%"));
        }
        if (criteria.getFromTime() != null) {
            criterion.add(Restrictions.ge("evectionTime", criteria.getFromTime()));
        }
        if (criteria.getToTime() != null) {
            criterion.add(Restrictions.le("evectionTime", criteria.getToTime()));
        }

        if (criteria.getDeptId() != 0) {
            criterion.add(Restrictions.eq("deptId", criteria.getDeptId()));
        }

        criterion.add(Restrictions.ge("clStep", 2));

        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        criterion.addOrder(
                criteria.isAscOrDesc() ?
                        org.hibernate.criterion.Order.asc(criteria.getOrderByProperty() == null ? "applyDate" : criteria.getOrderByProperty())
                        : org.hibernate.criterion.Order.desc(criteria.getOrderByProperty() == null ? "applyDate" : criteria.getOrderByProperty()));
        List<FormEvection> list = criterion.list();
        List<FormEvectionDto> dtos = new ArrayList<FormEvectionDto>();
        if (list != null && list.size() > 0) {
            for (FormEvection d : list) {
                FormEvectionDto dto = new FormEvectionDto();
                FormEvectionDto fed = DtoFactory.convert(d, dto);
                dtos.add(DtoFactory.convert(d, dto));
            }
        }
        return new PagedResult<FormEvectionDto>(dtos, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public List<AttendanceByFlowDto> getEmpStatusByBusiness(Date time) {
        Criteria criteria = getSession().createCriteria(FormEvection.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formStatus", FormStatus.AP));
        List<FormEvection> formEvections = criteria.list();

        String currentTime = DateHelper.formatDate(time, "yyyy-MM-dd");
        Date currentTime_onlyDay = DateHelper.parse(currentTime, "yyyy-MM-dd");

        List<AttendanceByFlowDto> list = new ArrayList<>();
        for (FormEvection business : formEvections) {
            Date beginTime = business.getEvectionTime();
            String str_beginTime = DateHelper.formatDate(beginTime, "yyyy-MM-dd");
            Date beginTime_onlyDay = DateHelper.parse(str_beginTime, "yyyy-MM-dd");

            Date endTime = business.getToTime();
            String str_endTime = DateHelper.formatDate(endTime, "yyyy-MM-dd");
            Date endTime_onlyDay = DateHelper.parse(str_endTime, "yyyy-MM-dd");

            if (beginTime_onlyDay.compareTo(currentTime_onlyDay) <= 0 && endTime_onlyDay.compareTo(currentTime_onlyDay) >= 0) {
                AttendanceByFlowDto flowDto = new AttendanceByFlowDto();
                flowDto.setApplyer(business.getApplyer());
                flowDto.setApplyName(business.getApplyerName());
                flowDto.setDeptId(business.getDeptId());
                flowDto.setDeptName(business.getDeptName());
                flowDto.setBusinessFlow("出差");
                flowDto.setBeginTime(beginTime_onlyDay);
                flowDto.setEndTime(endTime_onlyDay);
                list.add(flowDto);
            }

        }


        return list;
    }

    @Override
    public List<Long> getEmpStatusByBusiness(Long deptId, Date time) {
        Criteria criteria = getSession().createCriteria(FormEvection.class);
        criteria.add(Restrictions.eq("isActive", true));
        if (deptId != null)
            criteria.add(Restrictions.eq("deptId", deptId));
        criteria.add(Restrictions.eq("formStatus", FormStatus.AP));
        List<FormEvection> formEvections = criteria.list();
        if (formEvections == null || formEvections.size() == 0) return Collections.EMPTY_LIST;

        String currentTime = DateHelper.formatDate(time, "yyyy-MM-dd");
        Date currentTime_onlyDay = DateHelper.parse(currentTime, "yyyy-MM-dd");

        List<Long> list = new ArrayList<>();
        for (FormEvection business : formEvections) {
            Date beginTime = business.getEvectionTime();
            String str_beginTime = DateHelper.formatDate(beginTime, "yyyy-MM-dd");
            Date beginTime_onlyDay = DateHelper.parse(str_beginTime, "yyyy-MM-dd");

            Date endTime = business.getToTime();
            String str_endTime = DateHelper.formatDate(endTime, "yyyy-MM-dd");
            Date endTime_onlyDay = DateHelper.parse(str_endTime, "yyyy-MM-dd");

            if (beginTime_onlyDay.compareTo(currentTime_onlyDay) <= 0 && endTime_onlyDay.compareTo(currentTime_onlyDay) >= 0) {
                AttendanceByFlowDto flowDto = new AttendanceByFlowDto();
                flowDto.setApplyer(business.getApplyer());
                flowDto.setApplyName(business.getApplyerName());
                flowDto.setDeptId(business.getDeptId());
                flowDto.setDeptName(business.getDeptName());
                flowDto.setBusinessFlow("出差");
                flowDto.setBeginTime(beginTime_onlyDay);
                flowDto.setEndTime(endTime_onlyDay);
                list.add(flowDto.getApplyer());
            }

        }


        return list;
    }

    @Override
    public PagedResult getDownlodEvection(EvectionCriteria criteria) {
        PagedResult<FormEvectionDto> result = this.getEvectionPage(criteria);
        List<FormBusinessDownloadDto> list = new ArrayList<>();
        for (FormEvectionDto evectionDto : result.getItems()) {
            FormBusinessDownloadDto downloadDto = new FormBusinessDownloadDto();
            downloadDto.setEvectionTime(DateHelper.formatDate(evectionDto.getEvectionTime(), "yyyy-MM-dd"));
            downloadDto.setToTime(DateHelper.formatDate(evectionDto.getToTime(), "yyyy-MM-dd"));
            downloadDto.setApplyerName(evectionDto.getApplyerName());
            downloadDto.setCategory(evectionDto.getCategory());
            downloadDto.setCountTime(evectionDto.getCountTime());
            downloadDto.setEvecAttr(evectionDto.getEvecAttr());
            downloadDto.setTarget(evectionDto.getTarget());
            downloadDto.setTaskContent(evectionDto.getTaskContent());
            downloadDto.setTaskSource(evectionDto.getTaskSource());
            list.add(downloadDto);
        }
        return new PagedResult(list, 1, 1, 1);
    }

    @Override
    public CommonResult updatePrint(String formKind) {
        Integer evectionDtos = formEvectionRepository.getMaxNo();
        FormEvectionDto evection = this.getFormByFormNo(Long.parseLong(formKind));
        FormEvection formEvection = formEvectionRepository.get(evection.getId());
        if(!formEvection.getPrintFlag()){
            if(evectionDtos==null)
                formEvection.setNumberNo(1);
            else
                formEvection.setNumberNo(evectionDtos+1);
            formEvection.setPrintFlag(true);
            formEvectionRepository.save(formEvection);
        }
        return ResultFactory.commonSuccess();
    }

}
