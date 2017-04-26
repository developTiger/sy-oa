package test;

import com.sunesoft.lemon.ay.equimentForms.application.formEquiment.FormEquimentService;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipment;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FormListServiceImplTest  {

    @Autowired
    FormListService formListService;

    @Autowired
    FormEquimentService formEquimentService;
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


    @Test
    public void test(){
        List<FormEquipment> list =  formEquimentService.getLists();
        System.out.print(list.size());

    }
}