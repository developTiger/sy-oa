package test;

import com.sunesoft.lemon.ay.equimentForms.application.formAssessment.FormAssessmentService;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessmentDto;
import com.sunesoft.lemon.ay.equipment.domain.AssessContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AssessmentTest {
    @Autowired
    FormAssessmentService formAssessmentService;
    @Test
    public void test(){
        FormAssessmentDto formAssessmentDto = new FormAssessmentDto();
        formAssessmentDto.setApplyer(1L);
        formAssessmentDto.setApplyerName("aaaaa");
        formAssessmentDto.setDeptId(1L);
        formAssessmentDto.setFormKind("SYY_AY_LC05");
        formAssessmentDto.setFormKindName("111");


//        formAssessmentDto.setAmmunition("111");
        List<AssessContent> contents = new ArrayList<>();
        AssessContent assessContent = new AssessContent();
        assessContent.setTestValue("111");
        assessContent.setSuggest("222");
        assessContent.setParameterRange("333");
        assessContent.setConform("4444");
        assessContent.setTestCrew("55555");
        contents.add(assessContent);
//        formAssessmentDto.setAssessContentDtos(contents);
        formAssessmentService.addByDto(formAssessmentDto);
    }

}
