package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpAttendanceWorkCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpAttendancesCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceEnsureInfo;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/5.
 */
public interface AttendanceEnsureService{


    public AtendanceEnsureInfoDto getDeptInfoByDay(Long dept,String attDate);

    /**
     * 对出勤进行增加或者修改
     * @param dto
     * @return
     */
    public CommonResult addOrUpdateEnsureInfo(AttendanceOperateDto dto);

    /**
     * 是否确认信息
     * @param deptId
     * @param attDate
     * @param dayStation
     * @return
     */
    public CommonResult ensureAttendance(Long deptId,String attDate,String dayStation);

    /**
     * 个人考勤是否确认信息
     * @param empId
     * @param attDate
     * @param attendanceKind
     * @return
     */
    public CommonResult ensureOneAttendance(Long empId,String attDate,AttendanceKind attendanceKind);

    /**
     * 获取员工出勤信息
     * @param criteria
     * @return
     */
    public List<EmpAttendanceDto> getEmpAttendanceInfo(EmpAttendancesCriteria criteria);

    /**
     * 获取某月员工休假 加班，补休等信息
     * @param criteria
     * @return
     */
    public List<EmpAttendanceWorkDto> getEmpRestInfo(EmpAttendanceWorkCriteria criteria);

    public List<EmpAttendanceWorkDLoadDto> getEmpRestDLoadInfo(EmpAttendanceWorkCriteria empAttendanceWorkCriteria);

    /**
     * 获取最近N天的出勤信息
     * @return
     */

    public List<NearlyFiveDayAttendanceInfoDto> getNearlyFiveDayAttendance();

    /**
     * 修改时，获取到当前数据
     * @param empId
     * @param editDate
     * @return
     */
    public AttendanceOperateDto getInfoDetail(Long empId,String editDate);


}
