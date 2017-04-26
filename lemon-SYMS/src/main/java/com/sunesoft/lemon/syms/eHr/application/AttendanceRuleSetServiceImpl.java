package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.RuleDateCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.RuleDateDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.attendance.*;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.RuleType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2016/6/30.
 */
@Service("attendanceRuleSetService")
public class AttendanceRuleSetServiceImpl extends GenericHibernateFinder implements AttendanceRuleSetService {

    @Autowired
    AttendanceRuleSetRepository setRepository;
    @Autowired
    HolidayInfoRepository infoRepository;

    @Override
    public CommonResult addOrUpdateOrDelRuleDate(RuleDateDto dto) {
        if (dto.getInfoId() == null) {
            return ResultFactory.commonError("假期类型错误");
        }
        Date rdate = dto.getrDate();
        Integer year = DateHelper.getYear(rdate);
        Integer month = DateHelper.getMonth(rdate);
        HolidayInfo info = infoRepository.get(dto.getInfoId());
        if(month!=Integer.parseInt(info.getHmonth())){
            return ResultFactory.commonError("该假期信息属于"+info.getHmonth()+"月份的");
        }
        AttendanceRuleSet set = getByYearAndMonth(year, month);
        if (set == null && dto.getRuleType() == RuleType.Reset) {
            return ResultFactory.commonError("该日期无假期信息，重置失败");
        } else if (set == null) {
            set = new AttendanceRuleSet();
            set.setYear(year);
            set.setMonth(month);
        }
        Boolean findFlag = false;
        List<RuleDate> list = set.getRuleDates();
        List<RuleDate> delList = new ArrayList<RuleDate>();
        Boolean delFlag = false;
        if (set.getIsActive()) {
            if (list.size() > 0) {
                for (RuleDate rd : list) {
                    if (DateHelper.formatDate(rd.getrDate(), "yyyy-MM-dd").equals(DateHelper.formatDate(dto.getrDate(), "yyyy-MM-dd"))) {
                        findFlag = true;
                        if (dto.getRuleType() == RuleType.Reset) {
                            delFlag = true;
                            rd.setLastUpdateTime(new Date());
                            delList.add(rd);
                        } else {
                            rd = DtoFactory.convert(dto, rd);
                            rd.setrDate(rdate);
                            rd.setInfo(info);
                            rd.setLastUpdateTime(new Date());
                        }
                    }
                }
            }
            if (delFlag) {
                list.removeAll(delList);
            }
            if (!findFlag) {
                RuleDate rd = new RuleDate();
                rd = DtoFactory.convert(dto, rd);
                rd.setInfo(info);
                rd.setrDate(rdate);
                list.add(rd);
            }
        }
        return ResultFactory.commonSuccess(setRepository.save(set));
    }

    @Override
    public List<RuleDateDto> getAllRuleSets(Integer year, Integer month) {
        Criteria criterion = getSession().createCriteria(AttendanceRuleSet.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("year", year));
        criterion.add(Restrictions.eq("month", month+1));
        List<AttendanceRuleSet> list = criterion.list();
        List<RuleDateDto> dtos = new ArrayList<RuleDateDto>();
        if (list != null && list.size() > 0) {
            List<RuleDate> dateList = list.get(0).getRuleDates();
            if (dateList != null && dateList.size() > 0) {
                for (RuleDate rd : dateList) {
                    RuleDateDto dto = new RuleDateDto();
                    dto = DtoFactory.convert(rd, dto);
                    if(rd.getInfo()!=null){
                        dto.setInfoId(rd.getInfo().getId());
                        dto.setIntoName(rd.getInfo().getHname());
                    }
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

    @Override
    public PagedResult<RuleDateDto> findRuleDatesPaged(RuleDateCriteria criteria) {
        if(!StringUtils.isNullOrWhiteSpace(criteria.getBeginTime())&&!StringUtils.isNullOrWhiteSpace(criteria.getEndTime()))
            if (DateHelper.parse(criteria.getBeginTime()).after(DateHelper.parse(criteria.getEndTime()))) {
                return null;
            }
        Criteria criterion = getSession().createCriteria(RuleDate.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getRuleType().toString())) {
            criterion.add(Restrictions.eq("ruleType", criteria.getRuleType()));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<RuleDate> list = criterion.list();
        List<RuleDateDto> dtos = new ArrayList<RuleDateDto>();
        if (list != null && list.size() > 0) {
            for (RuleDate rd : list) {
                if (!StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(rd.getrDate()))) {
                    if ((StringUtils.isNullOrWhiteSpace(criteria.getBeginTime()) || DateHelper.parse(criteria.getBeginTime()).before(rd.getrDate())) && (StringUtils.isNullOrWhiteSpace(criteria.getEndTime()) || DateHelper.parse(criteria.getEndTime()).after(rd.getrDate()))) {
                        RuleDateDto dto = new RuleDateDto();
                        dto = DtoFactory.convert(rd, dto);
                        if(rd.getInfo()!=null) {
                            dto.setInfoId(rd.getInfo().getId());
                            dto.setIntoName(rd.getInfo().getHname());
                        }
                        dtos.add(dto);
                    }
                }
            }
        }
        return new PagedResult<RuleDateDto>(dtos, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }


    private AttendanceRuleSet getByYearAndMonth(Integer year, Integer month) {

        Criteria criterion = getSession().createCriteria(AttendanceRuleSet.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("year", year));
        criterion.add(Restrictions.eq("month", month));
        List<AttendanceRuleSet> list = criterion.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


}
