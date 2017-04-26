package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.HolidayInfoCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.HolidayInfoDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.RuleDateDto;
import com.sunesoft.lemon.syms.eHr.domain.attendance.RuleDate;


import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2016/6/30.
 */
public interface HolidayInfoService {

    /**
     * 增加假日信息
     * @param dto
     * @return
     */
    public CommonResult addHolidayInfo(HolidayInfoDto dto);

    /**
     * 删除假日信息
     * @param ids
     * @return
     */
    public CommonResult deleteHolidayInfo(List<Long> ids);


    /**
     * 获取一个时间段内的假期信息
     * @return
     */
    public List<RuleDateDto> getHolidayInfoDtoByDate(String startTime,String endTime);


    /**
     * 获取 HolidayInfoDtos
     * @param ids
     * @return
     */
    public List<HolidayInfoDto> getByHolidayInfosIds(List<Long> ids);

    /**
     * 获取 HolidayInfoDtos
     * @param id
     * @return
     */
    public HolidayInfoDto getByHolidayInfoId(Long id);

    /**
     * 修改假日
     * @param dto
     * @return
     */
    public CommonResult updateHolidayInfo(HolidayInfoDto dto);


    /**
     * 获取所有，
     *
     * @return
     */
    public List<HolidayInfoDto> getAllHolidayInfos();

    /**
     * 获取所有同名，这里考虑到用户名不重复
     *@param name
     * @return
     */
    public List<HolidayInfoDto> getHolidayInfosByName(String name);


    /**
     * 查询实例
     *
     * @param criteria 查询条件
     * @return
     */
    public PagedResult<HolidayInfoDto> findHolidayInfosPaged(HolidayInfoCriteria criteria);

    /**
     * 新增ruleDate
     * @param dateDto
     * @return
     */
    public CommonResult addRuleDateInfo(RuleDateDto dateDto);

    /**
     * 修改ruleDate
     * @param dateDto
     * @return
     */
    public CommonResult updateRuleDateInfo(RuleDateDto dateDto);

    /**
     * 获取节假日类型
     * @return
     */
    public List<String> getHnameByHolidayInfo();

    /**
     * 删除ruleDate
     * @param ruleDateId
     * @return
     */
    public CommonResult deleteRuleDateInfo(Long ruleDateId);

    /**
     * 获取所有节假日信息(休息，加班，上班)
     * @return
     */
    public List<RuleDate> getAllRuleDate();

    /**
     * 返回当天上班状态，供考勤查询
     * @return
     */
    public String getEmpWorkStateByHoliday(Date time);

    public String getEmpWorkStateByHoliday(String dateString);

    public int getTotalWorkDays(Date beginTime,Date endTime);

    public int getWeekendsByTimeZone(Date beginTime,Date endTime);

}
