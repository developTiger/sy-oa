package com.sunesoft.lemon.syms.hrForms.application.formleave;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.AttendanceEnsureService;
import com.sunesoft.lemon.syms.eHr.application.dtos.AttendanceOperateDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.AttendanceByFlowDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DownloadFormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormLeaveDto;
import com.sunesoft.lemon.syms.hrForms.application.criteria.LeaveFormCriteria;
import com.sunesoft.lemon.syms.hrForms.application.factory.FormLeaveFactory;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeaveRepository;
import com.sunesoft.lemon.syms.hrForms.domain.enums.LeaveType;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormHeaderRepository;
import com.sunesoft.lemon.syms.workflow.domain.InnerFormApprovePoint;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhouz on 2016/6/25.
 */
@Service("formLeaveService")
public class FormLeaveServiceImpl extends FormBase2<FormLeave, FormLeaveDto> implements FormLeaveService {

    @Autowired
    FormLeaveRepository formLeaveRepository;

    @Autowired
    AttendanceEnsureService attendanceEnsureService;


    @Autowired
    FormHeaderRepository formHeaderRepository;


    @Override
    protected CommonResult save(FormLeave formLeave) {
        Long id = formLeaveRepository.save(formLeave);
        return ResultFactory.commonSuccess(id);
    }


    @Override
    protected CommonResult update(FormLeave formLeave) {
        FormLeave leave = formLeaveRepository.get(formLeave.getId());
        leave.setLastUpdateTime(new Date());
        leave.setLeaveType(formLeave.getLeaveType());
        leave.setReason(formLeave.getReason());
        leave.setFromTime(formLeave.getFromTime());
        leave.setToTime(formLeave.getToTime());
        leave.setTarget(formLeave.getTarget());
        leave.setCountTime(formLeave.getCountTime());
        leave.setActualTime(formLeave.getActualTime());
        formLeaveRepository.save(leave);
        return ResultFactory.commonSuccess();


    }

    @Override
    protected FormLeave ConvetDto(FormLeaveDto dto) {
        FormLeave leave = new FormLeave();
        leave = DtoFactory.convert(dto, leave);
        return leave;
    }


    @Override
    protected String getSummery(FormLeave formLeave) {
        return null;
    }


    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {

        FormLeave leave = this.getByFormNo(formNo);
        //todo 这里已经完成请假的整个流程，需要对员工假日信息做处理
        if (leave.getLeaveType().equals(LeaveType.recuperation) || leave.getLeaveType().equals(LeaveType.paidAnnual)) {
            Employee employee = employeeRepository.get(leave.getApplyer());
            if (employee == null || !employee.getIsActive()) return ResultFactory.commonError("该员工不存在");
            float days = 0f;
            //计算实际请假天数
            days = leave.getCountTime();
            //带薪年假与疗养假的处理
            CommonResult c = setAfterSpaAndYear(employee, days, leave.getLeaveType(), leave.getFromTime());
            if (!c.getIsSuccess()) return ResultFactory.commonError("其他假日类型");
        }

        AttendanceOperateDto operateDto = new AttendanceOperateDto();
        operateDto.setEmpId(leave.getApplyer());
        operateDto.setName(leave.getApplyerName());
        operateDto.setDepId(leave.getDeptId());
        operateDto.setDepName(leave.getDeptName());
        operateDto.setAttendanceKind(getKind(leave.getLeaveType()));//请假的设置可能会对影响考勤那块
        operateDto.setBeginDate(leave.getFromTime());
        operateDto.setEndDate(leave.getToTime());
        operateDto.setTimeRange(leave.getCountTime());
        operateDto.setAttDate(new Date());
        return attendanceEnsureService.addOrUpdateEnsureInfo(operateDto);
    }


    @Override
    protected FormLeave getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(FormLeave.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo", formNo));

        return (FormLeave) criterion.uniqueResult();
    }


    @Override
    public FormLeaveDto getFormByFormNo(Long formNo) {
        return DtoFactory.convert(this.getByFormNo(formNo), new FormLeaveDto());
    }


    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {


        //院领导签核流程 添加数据 是在第二签核步骤
        //正常请假流程 添加数据 是在第三步骤
        String formKind = param.get("formKind").toString();
        /*if ((param.get("clStep").toString().equals("3") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())&&formKind.equals("SYY_RS_LC06"))
                || (param.get("clStep").toString().equals("2") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())&&formKind.equals("SYY_RS_LC06_01"))) {*/

        if ((param.get("clStep").toString().equals("3")) || (param.get("clStep").toString().equals("4") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal()))) {
            FormLeave leave = this.getByFormNo(dto.getFormNo());
            String time = "";
            if (param.get("actualTime") != null) {
                time = param.get("actualTime").toString();//实际返岗时间
                if (StringUtils.isNullOrWhiteSpace(time))
                    return new CommonResult(false, "请填写实际返岗时间！");

                if (!StringUtils.isNullOrWhiteSpace(time)) {
                    leave.setActualTime(setHMS(DateHelper.parse(time, "yyyy-MM-dd"), 0));
                }
            }
            String timeCount = "";
            if (param.get("timeCount") != null) {
                timeCount = param.get("timeCount").toString();//合计
                if (StringUtils.isNullOrWhiteSpace(timeCount))
                    return new CommonResult(false, "请计算实际请假天数！");
                leave.setCountTime(Float.parseFloat(timeCount));
            }

            // String content = param.get("remark").toString();//备注


            //   leave.setContent(content);

            formLeaveRepository.save(leave);
        }

        //正常请假签核流程 有两个不同的签核角色：党群科  人事科
        //通过请假类型判断签核角色
        if (param.get("clStep").toString().equals("1") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())) {
            FormLeave leave = this.getByFormNo(dto.getFormNo());

            List<Employee> approvers = new ArrayList<>();

            //通过流程节点 获取签核角色
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            for (InnerFormApprovePoint point : header.getInnerFormApprovePoints()) {
                if (point.getId().equals(header.getNextAppPointId())) {
                    approvers = point.getAppRole().getApprover();
                }
            }


            List<Long> list = new ArrayList<Long>();

            String nextDept = "rs";
            if (leave.getLeaveType() == LeaveType.birth || leave.getLeaveType() == LeaveType.abortion || leave.getLeaveType() == LeaveType.escort
                    || leave.getLeaveType() == LeaveType.marry || leave.getLeaveType() == LeaveType.recuperation) {
                nextDept = "dq";
            }

            for (Employee e : approvers) {
                //todo  部门编码hotfix
                if (nextDept.equals("rs")) {
//                    if (e.getName().equals("李明"))
//                        list.add(e.getId());
                    if (e.getDept().getDeptNo() == "05RSK")
                        list.add(e.getId());
                } else {
//                    if (!e.getName().equals("陈超"))
//                        list.add(e.getId());
                    if (e.getDept().getDeptNo() == "02YZBGS")
                        list.add(e.getId());
                }

            }

            super.resetNextApprover(dto.getFormNo(), list);
        }


        return super.doApprove(dto, param);
    }

    /**
     * 检查员工的节假日是否可行（带薪年假，疗养假）
     *
     * @param empId
     * @param type
     * @return
     */
    @Override
    public CommonResult checkLeave(Long empId, LeaveType type, Date beginTime, Date endTime, Float f) {
        Employee employee = employeeRepository.get(empId);
        if (employee == null || !employee.getIsActive()) return ResultFactory.commonError("该员工不存在");
        if (!beginTime.before(endTime)) return ResultFactory.commonError("结束时间不能比开始时间早");
        float days = 0f;
        days = f == null ? 0 : f;
        if (days == 0f) return ResultFactory.commonError("请假的天数为0,不用请假");
        //检查带薪年休
        if (type.equals(LeaveType.paidAnnual)) {
            CommonResult c = checkYearDate(employee, days, beginTime);
            if (!c.getIsSuccess()) return ResultFactory.commonError(c.getMsg());
        }
        //检查疗养假
        if (type.equals(LeaveType.recuperation)) {
            CommonResult cl = checkliaoyang(employee, days, beginTime);
            if (!cl.getIsSuccess()) return ResultFactory.commonError(cl.getMsg());
        }
        return ResultFactory.commonSuccess();

    }

    @Override
    public List<AttendanceByFlowDto> getEmpsStatusByLeave(Date time) {
        Criteria criteria = getSession().createCriteria(FormLeave.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<FormLeave> leaves = criteria.list();
        List<AttendanceByFlowDto> list = new ArrayList<>();
//        Date currentTime = new Date();//当前时间
        String str_currentTime = DateHelper.formatDate(time, "yyyy-MM-dd");
        Date currentTime_onlyDay = DateHelper.parse(str_currentTime, "yyyy-MM-dd");
//        Calendar cal_currentTime = Calendar.getInstance();
//        cal_currentTime.setTime(currentTime);
        for (FormLeave leave : leaves) {
            //请假流程：审核结束
            //有销假：销假前一步计算，如果填了销假时间，以销假时间为准
            Date beginTime = leave.getFromTime();//开始时间
            Date endTime = null;//结束时间
            Date actualTime = null;//返岗时间
//            Calendar cal_beginTime = Calendar.getInstance();
//            Calendar cal_endTime = Calendar.getInstance();
//            cal_beginTime.setTime(beginTime);
//            cal_endTime.setTime(endTime);
            if (leave.getFormStatus().equals(FormStatus.AP)) {
                //审核结束
                if (leave.getActualTime() != null)
                    actualTime = leave.getActualTime();
                else
                    endTime = leave.getToTime();
            } else {
                //审核没结束：有销假日期
                if (leave.getActualTime() != null) {
                    actualTime = leave.getActualTime();
                } else {
                    //没有销假时间
                    if (leave.getFormKind().equals("SYY_RS_LC06_05") || leave.getFormKind().equals("SYY_RS_LC06_01")) {
                        if (leave.getClStep() == 4) {
                            endTime = leave.getToTime();
                        }
                    }
                    if (leave.getFormKind().equals("SYY_RS_LC06_00") || leave.getFormKind().equals("SYY_RS_LC06_04") || leave.getFormKind().equals("SYY_RS_LC06_07")
                            || leave.getFormKind().equals("SYY_RS_LC06_08")) {
                        if (leave.getClStep() == 3) {
                            endTime = leave.getToTime();
                        }
                    }
                }
            }
            AttendanceByFlowDto flowDto = new AttendanceByFlowDto();
            if (beginTime != null) {
                String str_beginTime = DateHelper.formatDate(beginTime, "yyyy-MM-dd");
                Date beginTime_onlyDays = DateHelper.parse(str_beginTime, "yyyy-MM-dd");
                if (actualTime != null) {
                    //实际返岗天数减一天
                    long countTime = actualTime.getTime() + (-1) * 24 * 60 * 60 * 1000;
                    Date end = new Date(countTime);
                    String str_end = DateHelper.formatDate(end, "yyyy-MM-dd");
                    Date end_onlyDays = DateHelper.parse(str_end, "yyyy-MM-dd");
                    if (beginTime_onlyDays.compareTo(currentTime_onlyDay) <= 0 && end_onlyDays.compareTo(currentTime_onlyDay) >= 0) {
                        flowDto.setApplyer(leave.getApplyer());
                        flowDto.setApplyName(leave.getApplyerName());
                        flowDto.setDeptId(leave.getDeptId());
                        flowDto.setDeptName(leave.getDeptName());
                        flowDto.setLeaveType(leave.getLeaveType());
                        flowDto.setBeginTime(beginTime_onlyDays);
                        flowDto.setEndTime(end_onlyDays);
                        list.add(flowDto);
                    }
                } else {
                    if (endTime != null) {
                        String str_endTime = DateHelper.formatDate(endTime, "yyyy-MM-dd");
                        Date endTime_onlyDays = DateHelper.parse(str_endTime, "yyyy-MM-dd");
                        if (beginTime_onlyDays.compareTo(currentTime_onlyDay) <= 0 && endTime_onlyDays.compareTo(currentTime_onlyDay) >= 0) {
                            flowDto.setApplyer(leave.getApplyer());
                            flowDto.setApplyName(leave.getApplyerName());
                            flowDto.setDeptId(leave.getDeptId());
                            flowDto.setDeptName(leave.getDeptName());
                            flowDto.setLeaveType(leave.getLeaveType());
                            flowDto.setBeginTime(beginTime_onlyDays);
                            flowDto.setEndTime(endTime_onlyDays);
                            list.add(flowDto);
                        }
                    }
                }


            }

        }
        return list;
    }

    @Override
    public List<AttendanceByFlowDto> getEmpsStatusByLeave(Long deptId, Date time) {
        Criteria criteria = getSession().createCriteria(FormLeave.class);
        criteria.add(Restrictions.eq("isActive", true));
        if (deptId != null)
            criteria.add(Restrictions.eq("deptId", deptId));
        List<FormLeave> leaves = criteria.list();
        List<AttendanceByFlowDto> list = new ArrayList<>();
//        Date currentTime = new Date();//当前时间
        String str_currentTime = DateHelper.formatDate(time, "yyyy-MM-dd");
        Date currentTime_onlyDay = DateHelper.parse(str_currentTime, "yyyy-MM-dd");
//        Calendar cal_currentTime = Calendar.getInstance();
//        cal_currentTime.setTime(currentTime);
        for (FormLeave leave : leaves) {
            //请假流程：审核结束
            //有销假：销假前一步计算，如果填了销假时间，以销假时间为准
            Date beginTime = leave.getFromTime();//开始时间
            Date endTime = null;//结束时间
            Date actualTime = null;//返岗时间
//            Calendar cal_beginTime = Calendar.getInstance();
//            Calendar cal_endTime = Calendar.getInstance();
//            cal_beginTime.setTime(beginTime);
//            cal_endTime.setTime(endTime);
            if (leave.getFormStatus().equals(FormStatus.AP)) {
                //审核结束
                if (leave.getActualTime() != null)
                    actualTime = leave.getActualTime();
                else
                    endTime = leave.getToTime();
            } else {
                //审核没结束：有销假日期
                if (leave.getActualTime() != null) {
                    actualTime = leave.getActualTime();
                } else {
                    //没有销假时间
                    if (leave.getFormKind().equals("SYY_RS_LC06_05") || leave.getFormKind().equals("SYY_RS_LC06_01")) {
                        if (leave.getClStep() == 4) {
                            endTime = leave.getToTime();
                        }
                    }
                    if (leave.getFormKind().equals("SYY_RS_LC06_00") || leave.getFormKind().equals("SYY_RS_LC06_04") || leave.getFormKind().equals("SYY_RS_LC06_07")
                            || leave.getFormKind().equals("SYY_RS_LC06_08")) {
                        if (leave.getClStep() == 3) {
                            endTime = leave.getToTime();
                        }
                    }
                }
            }
            AttendanceByFlowDto flowDto = new AttendanceByFlowDto();
            if (beginTime != null) {
                String str_beginTime = DateHelper.formatDate(beginTime, "yyyy-MM-dd");
                Date beginTime_onlyDays = DateHelper.parse(str_beginTime, "yyyy-MM-dd");
                if (actualTime != null) {
                    //实际返岗天数减一天
                    long countTime = actualTime.getTime() + (-1) * 24 * 60 * 60 * 1000;
                    Date end = new Date(countTime);
                    String str_end = DateHelper.formatDate(end, "yyyy-MM-dd");
                    Date end_onlyDays = DateHelper.parse(str_end, "yyyy-MM-dd");
                    if (beginTime_onlyDays.compareTo(currentTime_onlyDay) <= 0 && end_onlyDays.compareTo(currentTime_onlyDay) >= 0) {
                        flowDto.setApplyer(leave.getApplyer());
                        flowDto.setApplyName(leave.getApplyerName());
                        flowDto.setDeptId(leave.getDeptId());
                        flowDto.setDeptName(leave.getDeptName());
                        flowDto.setLeaveType(leave.getLeaveType());
                        flowDto.setBeginTime(beginTime_onlyDays);
                        flowDto.setEndTime(end_onlyDays);
                        list.add(flowDto);
                    }
                } else {
                    if (endTime != null) {
                        String str_endTime = DateHelper.formatDate(endTime, "yyyy-MM-dd");
                        Date endTime_onlyDays = DateHelper.parse(str_endTime, "yyyy-MM-dd");
                        if (beginTime_onlyDays.compareTo(currentTime_onlyDay) <= 0 && endTime_onlyDays.compareTo(currentTime_onlyDay) >= 0) {
                            flowDto.setApplyer(leave.getApplyer());
                            flowDto.setApplyName(leave.getApplyerName());
                            flowDto.setDeptId(leave.getDeptId());
                            flowDto.setDeptName(leave.getDeptName());
                            flowDto.setLeaveType(leave.getLeaveType());
                            flowDto.setBeginTime(beginTime_onlyDays);
                            flowDto.setEndTime(endTime_onlyDays);
                            list.add(flowDto);
                        }
                    }
                }


            }

        }
        return list;
    }

    @Override
    public PagedResult<FormLeaveDto> page(LeaveFormCriteria criteria) {
        PagedResult<FormLeave> pagedResult = formLeaveRepository.page(criteria);
        return new PagedResult<FormLeaveDto>(FormLeaveFactory.convertToListDto(pagedResult.getItems()), pagedResult.getPageNumber(), pagedResult.getPageSize(), pagedResult.getTotalItemsCount());
    }

    @Override
    public List<DownloadFormLeaveDto> download(LeaveFormCriteria criteria) {
        PagedResult<FormLeave> pagedResult = formLeaveRepository.page(criteria);
        List<DownloadFormLeaveDto> list=FormLeaveFactory.convertToDownloadDto(pagedResult.getItems());
        return list;
    }

    /**
     * 检查疗养假的请假情况
     *
     * @param employee
     * @return
     */
    private CommonResult checkliaoyang(Employee employee, float days, Date beginTime) {
        float canSpa = employee.getSpaWithMoney();
//

//        if (days > canSpa || canSpa == 0f || (employee.getNewRestYear() != null && employee.getNewRestYear() == DateHelper.getYear(new Date())))
//            return ResultFactory.commonError("疗养假天数不足");
//        if (!employee.getCanSpa()) ResultFactory.commonError("未到可请疗养假的时候");
//        if (!employee.getCanSpa()) ResultFactory.commonError("未到可请疗养假的时候");
        if (days > canSpa || canSpa == 0f)
            return ResultFactory.commonError("疗养假天数不足");
        if (employee.getNewRestYear() != null && employee.getNewRestYear() >= DateHelper.getYear(new Date()))
            return ResultFactory.commonError("今年修过带薪年假");
        //todo 查看这个人上次休疗养假的年份
        Integer preYear = employee.getNewSpaYear();
        Integer seniority = employee.getSeniority();
        int betweenYear;
        if (seniority == null || seniority < 10)
            betweenYear = Integer.MAX_VALUE;
        else if (seniority >= 10 && seniority < 20)
            betweenYear = 4;
        else if (seniority >= 20 && seniority < 30)
            betweenYear = 3;
        else
            betweenYear = 2;
        if (preYear != null && (preYear + betweenYear > DateHelper.getYear(beginTime)))
            return ResultFactory.commonError("未到可请疗养假的时候");
        return ResultFactory.commonSuccess();
    }


    /**
     * 检查带薪年假的请假情况
     *
     * @param employee
     * @return
     */
    private CommonResult checkYearDate(Employee employee, float days, Date beginTime) {
        float hasYear = employee.getHasYear() == null ? 0f : employee.getHasYear();
        if (hasYear < days) return ResultFactory.commonError("可请带薪年假不足");
        if (employee.getNewSpaYear() != null && employee.getNewSpaYear() >= DateHelper.getYear(beginTime))
            return ResultFactory.commonError("今年已经请了疗养假，今年带薪年假不可请");
        if (employee.getNewRestYear() != null && employee.getNewRestYear() >= DateHelper.getYear(beginTime)) {
            return ResultFactory.commonError("今年已经请了带薪年假，今年带薪年假不可请");
        }
        return ResultFactory.commonSuccess();

    }

    //计算两个时间的天数差
    private Float daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Float.parseFloat(String.valueOf(between_days));
    }

    /**
     * 申请的休假成功后的处理(疗养假与带薪年假的处理)
     *
     * @param employee
     * @param days
     * @param type
     * @return
     */
    private CommonResult setAfterSpaAndYear(Employee employee, float days, LeaveType type, Date date) {
        if (type.equals(LeaveType.paidAnnual)) {//是年假的处理
            float hasYear = employee.getHasYear() == null ? 0f : employee.getHasYear();
            if (days > hasYear) return new CommonResult(false, "带薪年假剩余天数不足！");
            employee.setHasYear(hasYear - days);
            employee.setNewRestYear(DateHelper.getYear(date));
            employee.setSpaWithMoney(0f);
            employee.setCanSpa(false);

        }
        if (type.equals(LeaveType.recuperation)) {//疗养假的处理
            if (days > 15) new CommonResult(false, "疗养假超出规定天数！");
            employee.setSpaWithMoney(0f);
            employee.setCanSpa(false);
            employee.setNewSpaYear(DateHelper.getYear(date));

        }
        return ResultFactory.commonSuccess(employeeRepository.save(employee));
    }

    private AttendanceKind getKind(LeaveType type) {
        switch (type.ordinal()) {

            case 0:
                return AttendanceKind.T;
            case 1:
                return AttendanceKind.S1;
            case 2:
                return AttendanceKind.L;
            case 3:
                return AttendanceKind.B;
            case 4:
                return AttendanceKind.S3;
            case 5:
            case 6:
                return AttendanceKind.H;
            case 7:
                return AttendanceKind.C;
            case 8:
                return AttendanceKind.S2;
            case 9:
                return AttendanceKind.C;
            case 10:
                return AttendanceKind.S2;


        }
        return null;

    }


    /**
     * 早上10点上班，晚上19点下班的工作制度
     *
     * @param time
     * @param i
     * @return
     */
    private Date setHMS(Date time, int i) {
        Date d = null;
        if (i == 0) {//属于开始时间
            d = DateHelper.addHour(time, 10);
        } else {//属于结束时间
            d = DateHelper.addHour(time, 19);
        }
        return d;
    }
}



