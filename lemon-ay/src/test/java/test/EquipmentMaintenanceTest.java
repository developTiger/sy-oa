package test;

import com.sunesoft.lemon.ay.equipment.application.EquipmentMaintenanceService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EquipmentMaintenanceTest {
    @Autowired
    public EquipmentMaintenanceService equipmentMaintenanceSerivce;

    @Test
    public void test(){

        EquipmentMaintenanceDto equipmentMaintenanceDto = new EquipmentMaintenanceDto();
        equipmentMaintenanceDto.setCost("999");
//        equipmentMaintenanceDto.setLinkId(2572l);
        equipmentMaintenanceDto.setName("下雨了啊");
        equipmentMaintenanceSerivce.addOrUpEquipmentMaintenance(equipmentMaintenanceDto);
    }

    @Test
    public void test1(){
      //  List<EquipmentMaintenance> list=equipmentMaintenanceSerivce.getAllByEquipmentId(7592L);
       // System.out.print(list);
    }



}
