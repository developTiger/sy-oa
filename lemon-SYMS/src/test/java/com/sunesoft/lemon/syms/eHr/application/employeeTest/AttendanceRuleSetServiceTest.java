package com.sunesoft.lemon.syms.eHr.application.employeeTest;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.AttendanceRuleSetService;
import com.sunesoft.lemon.syms.eHr.application.HolidayInfoService;
import com.sunesoft.lemon.syms.eHr.application.criteria.RuleDateCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.HolidayInfoDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.RuleDateDto;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.RuleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by xiazl on 2016/7/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AttendanceRuleSetServiceTest {


    @Autowired
    AttendanceRuleSetService setService;
    @Autowired
    HolidayInfoService infoService;

    @Test
    public void addOrUpdateOrDelRuleDateTest() {
        RuleDateDto dto = new RuleDateDto();
        HolidayInfoDto infoDto = infoService.getByHolidayInfoId(1041l);
        System.out.println(infoDto.getId()+"\t"+infoDto.getHname());
        dto.setInfoId(infoDto.getId());
        dto.setRuleType(RuleType.CountryHoliday);
       dto.setrDate(DateHelper.parse("2016-08-25","yyyy-MM-dd"));
//        dto.setMsg("测试假期");
        CommonResult c = setService.addOrUpdateOrDelRuleDate(dto);
        if (c != null) {
            System.out.println(c.getId() + "\t" + c.getIsSuccess() + "\t" + c.getMsg());
        } else {
            System.out.println("error");
        }
    }

    @Test
    public void getAllRuleSetsTest() {
        List<RuleDateDto> dtos = setService.getAllRuleSets(2016,7);
        if (dtos != null && dtos.size() > 0) {
            System.out.println(dtos.size());
        } else {
            System.out.println("null");
        }

    }

    @Test
    public void findRuleDatesPaged() {
        String begin="2010-02-01 11:12:13";
        String end="2018-02-01 11:12:13";
        RuleDateCriteria criteria=new RuleDateCriteria();
        criteria.setBeginTime(begin);
        criteria.setEndTime(end);
        criteria.setRuleType(RuleType.CompHoliday);

        PagedResult<RuleDateDto> ps= setService.findRuleDatesPaged(criteria);
        if(ps!=null){
            System.out.println(ps.getItems().size()+"\t"+ps.getPageSize()+"\t"+ps.getPageNumber()+"\t"+ps.getPagesCount());
        }else{
            System.out.println("null");
        }
    }
}
