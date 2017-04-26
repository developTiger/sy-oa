package com.sunesoft.lemon.syms.eHr.application;

import com.aspose.words.net.System.Data.Rule;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.HolidayInfoCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.HolidayInfoDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.RuleDateDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.attendance.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by xiazl on 2016/6/30.
 */
@Service("holidayInfoService")
public class HolidayInfoServiceImpl extends GenericHibernateFinder implements HolidayInfoService {

    @Autowired
    HolidayInfoRepository infoRepository;

    @Autowired
    RuleDateRepository ruleDateRepository;

    @Autowired
    DateDetailRepository dateDetailRepository;

    @Override
    public CommonResult addHolidayInfo(HolidayInfoDto dto) {
        HolidayInfo info = new HolidayInfo();
        if (dto != null) {
            List<HolidayInfoDto> list = getHolidayInfosByName(dto.getHname());
            if (list != null && list.size() > 0) {
                String s = "该节假日已存在";
                return ResultFactory.commonError(s);
            }
            info = DtoFactory.convert(dto, info);
            return ResultFactory.commonSuccess(infoRepository.save(info));
        }
        String s = "请输入不为空的数据";
        return ResultFactory.commonError(s);
    }

    @Override
    public CommonResult deleteHolidayInfo(List<Long> ids) {
        Criteria criterion = getSession().createCriteria(HolidayInfo.class);
        criterion.add(Restrictions.eq("isActive", true));

        if (ids == null || ids.size() < 1) {
            return ResultFactory.commonError("请选择将要删除的假日");
        }
        criterion.add(Restrictions.in("id", ids));
        List<HolidayInfo> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (HolidayInfo info : list) {
                info.setIsActive(false);
                info.setLastUpdateTime(new Date());
                infoRepository.save(info);
            }
            return ResultFactory.commonSuccess();
        }
        String s = "该信息已经不存在";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<RuleDateDto> getHolidayInfoDtoByDate(String startTime, String endTime) {
        Criteria criterion = getSession().createCriteria(RuleDate.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.ge("startTime", DateHelper.parse(startTime)));
        criterion.add(Restrictions.le("endTime", DateHelper.parse(endTime)));
//                criterion.setResultTransformer(criterion.ROOT_ENTITY);
        List<RuleDate> ruleList = criterion.list();
        List<RuleDateDto> list = new ArrayList<>();
        for (RuleDate r : ruleList) {
            RuleDateDto dateDto = DtoFactory.convert(r, new RuleDateDto());
            dateDto.setInfoId(r.getInfo().getId());
            //todo 数据的查询，自己添加
            dateDto.setDateDetail(null);
            list.add(dateDto);
        }
        return list;
    }

    @Override
    public List<HolidayInfoDto> getByHolidayInfosIds(List<Long> ids) {
        List<HolidayInfoDto> dtos = new ArrayList<HolidayInfoDto>();
        Criteria criterion = getSession().createCriteria(HolidayInfo.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (ids == null || ids.size() < 1) {
            return dtos;
        }
        criterion.add(Restrictions.in("id", ids));
        List<HolidayInfo> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (HolidayInfo info : list) {
                HolidayInfoDto dto = new HolidayInfoDto();
                dto = DtoFactory.convert(info, dto);
                dtos.add(dto);
            }
            return dtos;
        }
        return Collections.emptyList();
    }

    @Override
    public HolidayInfoDto getByHolidayInfoId(Long id) {
        HolidayInfoDto infoDto = new HolidayInfoDto();
        HolidayInfo info = infoRepository.get(id);
        if (info != null && info.getIsActive()) {
            infoDto = DtoFactory.convert(info, infoDto);
        }
        return infoDto;
    }

    @Override
    public CommonResult updateHolidayInfo(HolidayInfoDto dto) {
        if (dto != null && dto.getId() != null && dto.getId() > 0) {
            HolidayInfo info = infoRepository.get(dto.getId());
            if (info != null && !info.getHname().equals(dto.getHname())) {
                List<HolidayInfoDto> list = getHolidayInfosByName(dto.getHname());
                if (list != null && list.size() > 0) {
                    String s = "您修改的名称已经存在";
                    return ResultFactory.commonError(s);
                }
            }
            if (info != null && info.getIsActive()) {
                info = DtoFactory.convert(dto, info);
                info.setLastUpdateTime(new Date());
                infoRepository.save(info);
                return ResultFactory.commonSuccess();
            }
        }
        String s = "该数据不存在或者已经被删除";
        return ResultFactory.commonError(s);
    }

    @Override
    public List<HolidayInfoDto> getAllHolidayInfos() {
        Criteria criterion = getSession().createCriteria(HolidayInfo.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<HolidayInfo> list = criterion.list();
        List<HolidayInfoDto> dtos = new ArrayList<HolidayInfoDto>();
        if (list != null && list.size() > 0) {
            for (HolidayInfo info : list) {
                HolidayInfoDto dto = new HolidayInfoDto();
                dto = DtoFactory.convert(info, dto);
                dtos.add(dto);
            }
            return dtos;
        }
        return Collections.emptyList();
    }

    @Override
    public List<HolidayInfoDto> getHolidayInfosByName(String name) {
        List<HolidayInfoDto> dtos = new ArrayList<HolidayInfoDto>();
        Criteria criterion = getSession().createCriteria(HolidayInfo.class);
        if (StringUtils.isNullOrWhiteSpace(name)) {
            return dtos;
        }
        criterion.add(Restrictions.eq("hname", name));
        criterion.add(Restrictions.eq("isActive", true));
        List<HolidayInfo> list = criterion.list();
        if (list != null && list.size() > 0) {
            for (HolidayInfo info : list) {
                HolidayInfoDto dto = new HolidayInfoDto();
                dto = DtoFactory.convert(info, dto);
                dtos.add(dto);
            }
            return dtos;
        }
        return Collections.emptyList();
    }

    @Override
    public PagedResult<HolidayInfoDto> findHolidayInfosPaged(HolidayInfoCriteria criteria) {

        Criteria criterion = getSession().createCriteria(HolidayInfo.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(criteria.getHname())) {
            criterion.add(Restrictions.like("hname", "%" + criteria.getHname() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getHmonth())) {
            criterion.add(Restrictions.eq("hmonth", criteria.getHmonth()));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<HolidayInfo> list = criterion.list();
        List<HolidayInfoDto> dtos = new ArrayList<HolidayInfoDto>();
        if (list != null && list.size() > 0) {
            for (HolidayInfo info : list) {
                if (info != null) {
                    HolidayInfoDto dto = new HolidayInfoDto();
                    dto = DtoFactory.convert(info, dto);
                    dtos.add(dto);
                }
            }
        }
        return new PagedResult<HolidayInfoDto>(dtos, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult addRuleDateInfo(RuleDateDto dateDto) {
        HolidayInfo holidayInfo = new HolidayInfo();
        holidayInfo.setId(dateDto.getInfoId());
        RuleDate ruleDate = new RuleDate();
        ruleDate.setInfo(holidayInfo);
        RuleDate rule = DtoFactory.convert(dateDto, ruleDate);

        return ResultFactory.commonSuccess(ruleDateRepository.save(rule));
    }

    @Override
    public CommonResult updateRuleDateInfo(RuleDateDto dateDto) {

        if (dateDto.getId() != null) {
            RuleDate ruleDate = ruleDateRepository.get(dateDto.getId());
            ruleDate.setHolidayType(dateDto.getHolidayType());
            ruleDate.setDescription(dateDto.getDescription());
            ruleDateRepository.save(ruleDate);
            return ResultFactory.commonSuccess();
        } else {
            return ResultFactory.commonError("数据不正确！");
        }
    }

    @Override
    public List<String> getHnameByHolidayInfo() {
        Criteria criteria = getSession().createCriteria(HolidayInfo.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<HolidayInfo> holidayInfos = criteria.list();
        List<String> list = new ArrayList<>();
        for (HolidayInfo info : holidayInfos) {
            if (info.getHname() != null) {
                list.add(info.getHname());
            }
        }
        return list;
    }

    @Override
    public CommonResult deleteRuleDateInfo(Long ruleDateId) {
        ruleDateRepository.delete(ruleDateId);
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<RuleDate> getAllRuleDate() {
        Criteria criteria = getSession().createCriteria(RuleDate.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<RuleDate> list = criteria.list();

        return list;
    }
    @Override
    public String getEmpWorkStateByHoliday(Date time) {
        List<RuleDate> list=this.getAllRuleDate();
//        Date currentTime = new Date();
        String str_time = DateHelper.formatDate(time,"yyyy-MM-dd");
        Date time_onlyDay = DateHelper.parse(str_time,"yyyy-MM-dd");
        for (RuleDate holiday:list){
            Date holidayBeginTime = holiday.getStartTime();
            Date holidayEndTime = holiday.getEndTime();

            String str_beginTime = DateHelper.formatDate(holidayBeginTime,"yyyy-MM-dd");
            Date beginTime_onlyDay = DateHelper.parse(str_beginTime,"yyyy-MM-dd");
            String str_endTime = DateHelper.formatDate(holidayEndTime,"yyyy-MM-dd");
            Date endTime_onlyDay = DateHelper.parse(str_endTime,"yyyy-MM-dd");

            if (beginTime_onlyDay.compareTo(time_onlyDay)<=0 && endTime_onlyDay.compareTo(time_onlyDay)>=0){
                if (holiday.getInfo().getHolidayState().equals("休息")){
                    return "休息";
                }
                if (holiday.getInfo().getHolidayState().equals("上班")){
                    return "上班";
                }
            }

        }
        return "";
    }

    @Override
    public String getEmpWorkStateByHoliday(String dateString) {
        if (StringUtils.isNullOrWhiteSpace(dateString)) {
            dateString = DateHelper.formatDate(new Date(), "yyyy-MM-dd");
        }
        Date date = DateHelper.parse(dateString, "yyyy-MM-dd");
        date=DateHelper.addHour(date,9);
        Criteria criteria = getSession().createCriteria(RuleDate.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.and(Restrictions.le("startTime", date), Restrictions.ge("endTime", date)));
        RuleDate ruleDate = (RuleDate) criteria.uniqueResult();
        if(ruleDate!=null){
            return ruleDate.getInfo().getHolidayState();
        }else {
            if(isWeekday(dateString)){
                return "上班";
            }
           return "休息";
        }
    }

    @Override
    public int getTotalWorkDays(Date beginTime, Date endTime) {
        List<RuleDate> list = this.getAllRuleDate();
        //先计算去掉周末的正常上班
        //在计算例外：周末上班，非周末休息
        Calendar cal_beginTime = Calendar.getInstance();
        Calendar cal_endTime = Calendar.getInstance();
        cal_beginTime.setTime(beginTime);
        cal_endTime.setTime(endTime);

        int weekends=this.getWeekendsByTimeZone(beginTime,endTime);//开始时间到结束时间的总周末天数


        long intervalMilli = endTime.getTime() - beginTime.getTime();
        int dayZones = (int) (intervalMilli / (24 * 60 * 60 * 1000));//开始时间到结束时间的总天数

        int holidayZones=0;//休息天数
        int holiday_weekend=0;//休息中周末天数
        int work_weekend = 0;//周末上班天数
        if (beginTime != null && endTime != null){
            for (RuleDate ruleDate:list){
                Date rule_begin = ruleDate.getStartTime();
                Date rule_end = ruleDate.getEndTime();
                Calendar cal_rule_begin = Calendar.getInstance();
                Calendar cal_rule_end = Calendar.getInstance();
                cal_rule_begin.setTime(rule_begin);
                cal_rule_end.setTime(rule_end);

                if (ruleDate.getInfo().getHolidayState().equals("休息")){
                    //休息包括节假日休息和例外休息
                    if (cal_beginTime.compareTo(cal_rule_begin)<=0 && cal_endTime.compareTo(cal_rule_end)>=0){
                        //开始时间<=节假日区间<=结束时间
                        long holidayTime = rule_end.getTime()-rule_begin.getTime();
                        holidayZones += (int) (holidayTime / (24 * 60 * 60 * 1000));
                        holiday_weekend+=this.getWeekendsByTimeZone(rule_begin,rule_end);
                    }
                    if (cal_beginTime.compareTo(cal_rule_begin)>0 && cal_rule_end.compareTo(cal_beginTime)>=0){
                        //开始时间>节假日开始时间，开始时间<=节假日结束时间
                        long holidayTime =  rule_end.getTime()-beginTime.getTime();
                        holidayZones += (int) (holidayTime / (24 * 60 * 60 * 1000));
                        holiday_weekend+=this.getWeekendsByTimeZone(beginTime,rule_end);
                    }
                    if (cal_endTime.compareTo(cal_rule_begin)>=0 && cal_rule_end.compareTo(cal_endTime)>0){
                        //节假日开始时间<=结束时间，节假日结束时间>结束时间
                        long holidayTime =  endTime.getTime()-rule_begin.getTime();
                        holidayZones += (int) (holidayTime / (24 * 60 * 60 * 1000));
                        holiday_weekend+=this.getWeekendsByTimeZone(rule_begin,endTime);
                    }
                }else{
                    //加班(周末)
                    if (cal_beginTime.compareTo(cal_rule_begin)<=0 && cal_endTime.compareTo(cal_rule_end)>=0){

                        work_weekend+=this.getWeekendsByTimeZone(rule_begin,rule_end);
                    }
                    if (cal_beginTime.compareTo(cal_rule_begin)>0 && cal_rule_end.compareTo(cal_beginTime)>=0){

                        work_weekend+=this.getWeekendsByTimeZone(beginTime,rule_end);
                    }
                    if (cal_endTime.compareTo(cal_rule_begin)>=0 && cal_rule_end.compareTo(cal_endTime)>0){

                        work_weekend+=this.getWeekendsByTimeZone(rule_begin,endTime);
                    }
                }


            }
        }
        int actualDayZones = 0;//实际上班天数
//        天数=总天数-休息(节假日+例外),如果休息有周末，周末天数=总周末-节假日周末，实际天数=天数-周末天数+周末上班天数
        if (holidayZones!=0){
            if (holiday_weekend!=0){
                actualDayZones = (dayZones-holidayZones)-(weekends-holiday_weekend)+work_weekend;
            }else{
                actualDayZones = dayZones-holidayZones-weekends+work_weekend;
            }
        }else{
            actualDayZones = dayZones-weekends+work_weekend;
        }
        return actualDayZones;
    }

    @Override
    public int getWeekendsByTimeZone(Date beginTime, Date endTime) {
        Calendar cal_beginTime = Calendar.getInstance();
        Calendar cal_endTime = Calendar.getInstance();
        cal_beginTime.setTime(beginTime);
        cal_endTime.setTime(endTime);

        int holidays = 0;//时间区间内，实际周末天数
        //星期六：7，星期日：1
//        int begin_week=cal_beginTime.get(Calendar.DAY_OF_WEEK);
//        int end_week = cal_endTime.get(Calendar.DAY_OF_WEEK);

        while(cal_beginTime.compareTo(cal_endTime)<=0) {
            if (cal_beginTime.get(Calendar.DAY_OF_WEEK) == 1 || cal_beginTime.get(Calendar.DAY_OF_WEEK) == 7) {
                holidays++;
            }
            cal_beginTime.add(Calendar.DAY_OF_YEAR, 1);
        }
        return holidays;
    }

    /**
     * 判断是不是周末
     *
     * @param string
     * @return
     */
    private boolean isWeekday(String string) {
        Date date = DateHelper.parse(string, "yyyy-MM-dd");
        int weekday = DateHelper.getWeek(date) - 1;
        if (weekday == 0 || weekday == 6) return false;
        return true;
    }
}
