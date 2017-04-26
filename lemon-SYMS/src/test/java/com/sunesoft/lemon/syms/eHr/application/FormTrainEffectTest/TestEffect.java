package com.sunesoft.lemon.syms.eHr.application.FormTrainEffectTest;

import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainEffectDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.TrainFileDto;
import com.sunesoft.lemon.syms.hrForms.application.formTrainEffect.FormTrainEffectService;
import com.sunesoft.lemon.syms.hrForms.domain.Options;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormKindManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestEffect {
    @Autowired
    public FormTrainEffectService formTrainEffectService;
    @Autowired
    public FormKindManagerService formKindManagerService;

    @Test
    public void testAdd(){
        FormTrainEffectDto formTrainEffectDto = new FormTrainEffectDto();

        //基础信息
        formTrainEffectDto.setApplyer(1L);
        formTrainEffectDto.setApplyerName("aaaaa");
        formTrainEffectDto.setDeptId(1L);
        formTrainEffectDto.setDeptName("testdept");
        formTrainEffectDto.setFormKind("SYY_RS_LC02");
        formTrainEffectDto.setFormKindName("1111");


        //表单信息
        formTrainEffectDto.setTrainPlace("new york");
        formTrainEffectDto.setTrainContent("rc 为 Ping++ Java SDK 的源代码，可以关联到 pingpp-java-x.x.x.jar 文件。或者直接把源代码引入到工程之中");
        formTrainEffectDto.setTrainEndTime(new Date());
        formTrainEffectDto.setTrainBeginTime(new Date());
        formTrainEffectDto.setPractical(Options.Bad);

        List<TrainFileDto> files = new ArrayList<>();

        TrainFileDto trainFileDto = new TrainFileDto();
        trainFileDto.setFileName("fileA");
        trainFileDto.setFileId("1");
        TrainFileDto trainFileDto1 = new TrainFileDto();
        trainFileDto1.setFileName("fileB");
        trainFileDto1.setFileId("2");

        files.add(trainFileDto);
        files.add(trainFileDto1);

        formTrainEffectDto.setFilesDto(files);

        formKindManagerService.getFormService(formTrainEffectDto.getFormKind()).addByDto(formTrainEffectDto);

   }
}
