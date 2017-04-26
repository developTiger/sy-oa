package com.sunesoft.lemon.deanery;

import com.sunesoft.lemon.deanery.patentCG.application.PatentService;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.prizewinner.application.PrizewinnerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by jiangkefan on 2016/7/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestPatent {

    @Autowired
    PatentService patentService;

    @Autowired
    PrizewinnerService prizewinnerService;

    @Test
    public void testProjectResultRepository_addOrUpdate(){
        PatentDto patentDto = new PatentDto();
        //设置修改ID
//        patentDto.setId(10l);
        patentDto.setPatent_Type("frf");
        patentDto.setPatent_Name("frf");
        String info = "8@8@8,8@8@8";
        patentDto.setWinner_info(info);
        patentDto.setApp_No("1");
        patentService.addOrUpdatePatent(patentDto);
    }

    @Test
    public void testProjectResultRepository_getById(){
        PatentDto patentDto = patentService.getByIdPatent(10L);
        System.out.print(patentDto.getPatent_Name());
    }

    @Test
    public void testProjectResultRepository_delete(){
        patentService.delPatent(new Long[]{49L,50L});
    }


  /*  @Test
    public void testPri_getCg(){
         Long id = 18L;
         List<PrizewinnerDto> l = prizewinnerService.getPrizeWinner(id);
         for(PrizewinnerDto p : l){
            String s = p.getIsOurSystem();
             String str = p.getWinnerInfos();
             System.out.println(str);
         }
    }*/


}
