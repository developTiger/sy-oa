package test;

import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by jiangkefan on 2016/8/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EquipmentTest {

    @Autowired
    EquipmentService equipmentService;

    @Test
    public void test(){
        Equipment equipment = new Equipment();
//        equipment.setBrand("1");
//        equipment.setResId(2572L);
//        equipment.setUnit("aaaa");
//        equipment.setStation("黑色站点");
//        equipment.setAogDate(new Date());
//        EquipmentDto e=  DtoFactory.convert(equipment, new EquipmentDto());
//        equipmentService.addOrUpEquipment(e);
    }



}
