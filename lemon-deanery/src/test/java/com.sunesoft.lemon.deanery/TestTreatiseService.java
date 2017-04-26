package com.sunesoft.lemon.deanery;

import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.projectCG.application.ProjectResultService;
import com.sunesoft.lemon.deanery.projectCG.application.ProjectResultServiceImpl;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResult;
import com.sunesoft.lemon.deanery.treatiseCG.application.TreatiseService;
import com.sunesoft.lemon.deanery.treatiseCG.application.criteria.TreatiseCriteria;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.fr.results.PagedResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by jiangkefan on 2016/7/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestTreatiseService {

    @Autowired
    public TreatiseService treatiseService;

    @Autowired
    public ProjectResultService projectResultService;

    @Test
    public void testTreatiseService_addOrUpdate(){
        TreatiseDto treatiseDto = new TreatiseDto();
//        treatiseDto.setId(28l);
        String info = "6@222@3333,7@222@222";
        treatiseDto.setWinner_info(info);
        treatiseDto.setTreatise_Name("ssss");
        treatiseDto.setTreatise_Press("ssss");
        treatiseDto.setTreatise_Press("ssss");
        treatiseDto.setIs_Core(false);
        treatiseDto.setUnit("sss");
        treatiseDto.setMake_No("sss");
        treatiseDto.setIs_cooperate(true);
        treatiseDto.setPublish_Time_("20160909");
        Long l = treatiseService.addOrUpdate(treatiseDto);
    }

    @Test
    public void testTreatiseService_delete(){
        boolean bool = treatiseService.delete(new Long[]{10L,11L});
    }

    @Test
    public void testTreatiseService_getById(){
        TreatiseDto treatiseDto = treatiseService.getByIdTreatise(29L);
        System.out.print(treatiseDto.getIs_cooperate());
    }

    @Test
    public void testTreatiseService_pageList(){
        TreatiseCriteria treatiseCriteria = new TreatiseCriteria();
        PagedResult<TreatiseDto> pageTreatise =  treatiseService.getTreatise(treatiseCriteria);
    }

    @Test
    public void testTreatiseService_testOther(){
        ProjectResultDto projectResultDto = new ProjectResultDto();
        String info = "111@222";
        projectResultDto.setWinner_Info(info);
        projectResultDto.setId(5L);
        projectResultDto.setCertif_No("www");
        projectResultDto.setIssuing_Unit("www");
        projectResultDto.setWin_Grade("ww");
        projectResultDto.setWin_Level("www");
        projectResultDto.setWin_Result_Classify("www");
        projectResultDto.setIssuing_Unit("1");
        projectResultDto.setWin_Date(new Date());
        projectResultDto.setWin_Grade(":");
        Long  l =
                projectResultService.addOrUpdateProjectResult(projectResultDto);

    }
}
