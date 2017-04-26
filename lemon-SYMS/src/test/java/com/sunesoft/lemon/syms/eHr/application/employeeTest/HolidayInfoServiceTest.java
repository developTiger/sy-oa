package com.sunesoft.lemon.syms.eHr.application.employeeTest;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.HolidayInfoService;
import com.sunesoft.lemon.syms.eHr.application.criteria.HolidayInfoCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.HolidayInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiazl on 2016/7/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class HolidayInfoServiceTest {

    @Autowired
    HolidayInfoService infoService;

    @Test
    public void addHolidayInfoTest(){
        HolidayInfoDto dto=new HolidayInfoDto();
        dto.setAmount(3f);
        dto.setHname("x1");
        dto.setHmonth("09");
        CommonResult c=infoService.addHolidayInfo(dto);
        if(c!=null){
            System.out.println(c.getId()+"\t"+c.getIsSuccess()+"\t"+c.getMsg());
        }else{
            System.out.println("error");
        }
    }

    @Test
    public void deleteHolidayInfoTest(){
        List<Long> list=new ArrayList<Long>();
        list.add(1l);
        list.add(2l);
        CommonResult c=infoService.deleteHolidayInfo(list);
        if(c!=null){
            System.out.println(c.getId()+"\t"+c.getIsSuccess()+"\t"+c.getMsg());
        }else{
            System.out.println("error");
        }

    }

    @Test
    public void getByHolidayInfosIdsTest(){
        List<Long> list=new ArrayList<Long>();
        list.add(1l);
        list.add(2l);
        list.add(3l);
        list.add(4l);
       List<HolidayInfoDto> dtos=infoService.getByHolidayInfosIds(list);
        if(dtos!=null){
            System.out.println(dtos.size());
        }
    }

    @Test
    public void getByHolidayInfoIdTest(){
        HolidayInfoDto dto=infoService.getByHolidayInfoId(3l);
        if(dto!=null){
            System.out.println(dto.getId());
        }else{
            System.out.println("error");
        }

    }

    @Test
    public void updateHolidayInfoTest(){
        HolidayInfoDto dto=new HolidayInfoDto();
        dto.setId(3l);
        dto.setHname("11");
        CommonResult c=infoService.updateHolidayInfo(dto);
        if(c!=null){
            System.out.println(c.getId()+"\t"+c.getIsSuccess()+"\t"+c.getMsg());
        }else{
            System.out.println("error");
        }
    }

    @Test
    public void getAllHolidayInfosTest(){
        List<HolidayInfoDto> dtos=infoService.getAllHolidayInfos();
        if(dtos!=null){
            System.out.println(dtos.size());
        }
    }

    @Test
    public void getHolidayInfosByNameTest(){
        List<HolidayInfoDto> dtos=infoService.getHolidayInfosByName("11");
        if(dtos!=null){
            System.out.println(dtos.size());
        }

    }
    @Test
    public void findHolidayInfosPagedTest(){
        HolidayInfoCriteria criteria=new HolidayInfoCriteria();
        criteria.setHname("z");
        PagedResult<HolidayInfoDto> pr=infoService.findHolidayInfosPaged(criteria);
        if(pr!=null){
            System.out.println(pr.getItems().size()+"\t"+pr.getPageNumber()+"\t"+pr.getPageSize()+"\t"+pr.getPagesCount());
        }
    }

}
