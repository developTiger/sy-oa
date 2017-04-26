package test;

import com.sunesoft.lemon.ay.equipment.application.EquipmentMaintenanceService;
import com.sunesoft.lemon.ay.equipment.application.EquipmentStatusService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentStatusDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EquimentStatusTest {

    @Autowired
    EquipmentStatusService equipmentStatusService;

    @Autowired
    EquipmentMaintenanceService equipmentMaintenanceService;

    @Test
    public void test(){

        EquipmentStatusDto equipmentStatusDto = new EquipmentStatusDto();
        equipmentStatusDto.setPerson("ly");
        equipmentStatusDto.setRunTime(new Date());
        equipmentStatusDto.setWorkContent("some work content");
        equipmentStatusDto.setResId(2411L);
        equipmentStatusService.addOrUpEquipmentStatus(equipmentStatusDto);

    }

    @Test
    public void testget(){
        EquipmentStatus equipmentStatus= equipmentStatusService.getByResId(2411L);
        System.out.print(equipmentStatus.getId());
    }

    @Test
    public void testlist(){
        List<EquipmentMaintenance> list =  equipmentMaintenanceService.getAll();
        System.out.print(list.size());
    }

    @Test
    public void testById(){
//     System.out.print(equipmentMaintenanceService.getById(6779L).getName());
    }

}
