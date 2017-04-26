package test;

import com.sunesoft.lemon.ay.equipment.application.AssessContentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
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
public class AssessContentTest {

    @Autowired
    AssessContentService assessContentService;

    @Test
    public void testadd(){


        AssessContentDto assessContentDto = new AssessContentDto();

      //  assessContentDto.setFormKind("");
        assessContentDto.setId(6957L);
        assessContentDto.setConform("VVV");
        assessContentDto.setImplement("imp");
        assessContentDto.setParameterName("投入");
        assessContentDto.setParameterRange("范围000");
        assessContentDto.setTestCrew("5");
        assessContentDto.setSuggest("6");
        assessContentDto.setTestValue("7");

        assessContentService.addOrUpdateContent(assessContentDto);

    }

    @Test
    public void testList(){

        List<AssessContent> list = assessContentService.getAllContents();
        System.out.print(list.get(0).getConform());
        for(AssessContent a : list){
            System.out.print(a.getConform());
        }

    }

    @Test
    public void testdel(){
       List<Long> ids =  new ArrayList<Long>();
        ids.add(6956L);
       assessContentService.delContent(ids);


    }

    @Test
    public void testGetbyId(){

        System.out.print(assessContentService.get(6948L).getConform());

    }
}
