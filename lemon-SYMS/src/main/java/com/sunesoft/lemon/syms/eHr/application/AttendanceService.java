package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendanceCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpAttendancesCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by MJ006 on 2016/6/29.
 */
public interface AttendanceService {
    /**
     *  //新增或修改
     * @param dto
     * @return
     */
    public CommonResult AddOrUpdateAttendance(AttendanceOperateDto dto);

    /**
     * //按个人查询当月考勤(必须参数：empId,beginDate(需要年和月，日可有可无))
     * @param attendanceCriteria
     * @return
     */
    public AttendanceWithDetailDto findByEmpId(AttendanceCriteria attendanceCriteria);

    /**
     * 获取员工的出勤信息
     * @param criteria
     * @return
     */
    public List<EmpAttendanceDto> getEmpAttendance(EmpAttendancesCriteria criteria);

    /**
     * //按部门查询当月考勤(必须参数：depId,beginDate(需要年和月，日可有可无))
     * @param attendanceCriteria
     * @return
     */
    public List<AttendanceDto> findByDepid(AttendanceCriteria attendanceCriteria);

    /**
     *  //按部门查询当月考勤(必须参数：depId,beginDate(需要年和月，日可有可无))  分页
     * @param attendanceCriteria
     * @return
     */
    public PagedResult<AttendanceDto> PagefindByDepid(AttendanceCriteria attendanceCriteria);

    /**
     *
     * @param attendanceCriteria
     * @return
     */
    public PagedResult<AttendanceDto> PagefindSimpleByDepid(AttendanceCriteria attendanceCriteria);

    /**
     *   //查询所有的
     * @return
     */
    public List<AttendanceDto> findAll();

    /**
     *  //按日查询部门考勤人数
     * @param attendanceCriteria
     * @return
     */
    public List<Map<String, Object>> findCountByDateAndDepid(AttendanceCriteria attendanceCriteria);

    /**
     * //按部门查询当月考勤(必须参数：depId,beginDate(需要年和月，日可有可无)) 分页
     * @param attendanceCriteria
     * @return
     */
    public PagedResult<AttendanceSummeryDto> PageBySummeryDto(AttendanceCriteria attendanceCriteria);

    public PagedResult<AttendanceSummeryDowloadDto> PageBySummerDowloadDto(AttendanceCriteria attendanceCriteria);

    /**
     * 更新部门员工的出勤状态
     * @param dept
     * @param attDate
     * @return
     */
    public CommonResult updateAttendanceByDept(Long dept,Date attDate,String dayStation);

    /**
     * 查询个人的考勤表,必填字段empid, Months
     * @param EmpId
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<AttendanceCalendar> findOneAttendance(Long EmpId,String beginTime,String endTime);


}
