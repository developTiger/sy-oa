package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendSumCtriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendanceCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.domain.attend.Attend;

import java.util.List;
import java.util.Map;

/**
 * 出勤业务
 * Created by xiazl on 2017/3/2.
 */
public interface AttendService {

    public CommonResult create(Attend attend);

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    public CommonResult create(AttendDto dto);

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    public CommonResult update(AttendDto dto);

    /**
     * 根据id获取
     *
     * @param id
     * @return
     */
    public AttendDto get(Long id);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public CommonResult delete(Long id);

    /**
     * 分页查询
     * 打考勤页面的员工数据展示
     *
     * @param criteria
     * @return
     */
    public PagedResult<AttendDto> page(AttendCriteria criteria) throws Exception;

    /**
     * 该一个人打考勤
     *
     * @return
     */
    public CommonResult ensureOne(AttendDto dto);

    /**
     * 给多个人打考勤
     *
     * @return
     */
    public CommonResult ensureDept(List<AttendDto> dtos);

    /**
     * 请假，出勤统计
     *
     * @param criteria
     * @return
     */
    public PagedResult<AttendSumloadDto> AttendSumPage(AttendSumCtriteria criteria);

    /**
     * 休假、加班、补休统计表
     *
     * @param criteria
     * @return
     */
    public PagedResult<EmpAttendWorkDto> AttendOverPage(AttendSumCtriteria criteria);

    List<EmpAttendsDto> pageFindSimpleByDepId(AttendanceCriteria attendanceCriteria);

    /**
     * 统计最近七日的考勤
     * 周六周日不展示
     *
     * @return
     */
    List<NewNearSevenDay> getNearlySevenDayAttendance();

    PagedResult<AttendSummeryResultDto> PageBySummeryDto(AttendanceCriteria attendanceCriteria);

    public List<AttendCalendar> findOneAttend(Long userId, String beginTime, String endTime);

    List<AttendDowloadsDto> getDowlodsDto(List<DayModel> list, AttendanceCriteria attendanceCriteria);
}
