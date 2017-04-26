package com.sunesoft.lemon.syms.hrForms.application.formEffectService;

import com.sunesoft.lemon.syms.hrForms.application.formTrainEffect.FormTrainEffectService;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by jiangkefan on 2016/7/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FormTrainServiceTest {
    @Autowired
    FormTrainEffectService formTrainEffectService;


    @Test
    public void aa() throws Exception{
        List<FormTrain> list =   formTrainEffectService.getAllFormTrain();
        System.out.println(list);
        System.out.println(list.size());
    }


}
