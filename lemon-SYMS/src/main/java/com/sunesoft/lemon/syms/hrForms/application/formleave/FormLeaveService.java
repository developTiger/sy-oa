package com.sunesoft.lemon.syms.hrForms.application.formleave;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.AttendanceByFlowDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DownloadFormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.application.criteria.LeaveFormCriteria;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.hrForms.domain.enums.LeaveType;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/6/25.
 */
public interface FormLeaveService extends FormService<FormLeave,FormLeaveDto> {

    /**
     * 检查员工的节假日是否可行（带薪年假，疗养假）
     * @param empId
     * @param type
     * @return
     */
public CommonResult checkLeave(Long empId,LeaveType type,Date beginTime,Date endTime,Float f);

    public List<AttendanceByFlowDto> getEmpsStatusByLeave(Date time);
    public List<AttendanceByFlowDto> getEmpsStatusByLeave(Long deptId,Date time);

    /**
     * 请假统计查询
     * @param criteria
     * @return
     */
    public PagedResult<FormLeaveDto> page(LeaveFormCriteria criteria);

    /**
     * 请假列表的下载
     * @param criteria
     * @return
     */
    public List<DownloadFormLeaveDto> download(LeaveFormCriteria criteria);



}
