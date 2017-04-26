package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.utils.word.WordPlaceHolder;
import com.sunesoft.lemon.fr.utils.word.wdWord;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FormListServiceImplTest  {

    @Autowired
    FormListService formListService;
    @Test
    public void testAdd() throws Exception {
        FormListDto dto = new FormListDto();
        dto.setFormKind("10101");
        dto.setFormName("请假申请单");
        dto.setFormType(1);
        dto.setFormStatus(1);
        dto.setCloseForm(false);
        dto.setDescription("请假申请单");
        dto.setVersion(1);
        dto.setIsBatchApproved(true);
        dto.setLimitTime(1);
        Long id =   formListService.add(dto);
        System.out.print(id);

    }






    public void testUpdate() throws Exception {

    }

    public void testDelete() throws Exception {

    }

    public void testGetFormListPaged() throws Exception {

    }
}