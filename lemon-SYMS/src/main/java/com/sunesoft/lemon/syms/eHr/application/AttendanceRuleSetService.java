package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.RuleDateCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.RuleDateDto;

import java.util.List;

/**
 * Created by xiazl on 2016/6/30.
 */
public interface AttendanceRuleSetService {

    /**
     * 增加/修改/删除假日
     *
     * @param dto
     * @return
     */
    public CommonResult addOrUpdateOrDelRuleDate(RuleDateDto dto) ;


    /**
     * 获取所有，
     *
     * @param year
     * @param month
     * @return
     */
    public List<RuleDateDto> getAllRuleSets(Integer year,Integer month);

//    /**
//     * 查询实例
//     *
//     * @param year
//     * @param month
//     * @param criteria 查询条件
//     * @return
//     */
//    public PagedResult<RuleDateDto> findRuleDatesPaged(Integer year, Integer month, RuleDateCriteria criteria);

    /**
     * 时间段内的假期查询
     * @param criteria
     * @return
     */
    public PagedResult<RuleDateDto> findRuleDatesPaged(RuleDateCriteria criteria);

}
