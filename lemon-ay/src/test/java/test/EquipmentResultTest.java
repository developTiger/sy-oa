package test;

import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
import com.sunesoft.lemon.ay.equipment.application.EquipmentResultService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentResultDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentStatusDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentResult;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatus;
import jxl.demo.Demo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EquipmentResultTest {
    @Autowired
    EquipmentResultService equipmentResultService;

    /**
     * 新增记录
     */
    @Test
    public void testadd(){
        EquipmentResultDto resultDto = new EquipmentResultDto();
        resultDto.setName("zzzz");
        resultDto.setConfirmer("vvvv");
        resultDto.setConfirmer("dddd");
        resultDto.setNum("ssss");
        equipmentResultService.addorUpdateEquipmentResult(resultDto);
    }

    /**
     * 记录运行
     */
    @Test
    public void testaddStatus(){
        EquipmentResult equipmentResult =  equipmentResultService.getEquipmentResultById(2411L);
        System.out.print(equipmentResult.getName());
        EquipmentStatusDto equipmentStatusDto = new EquipmentStatusDto();
        equipmentStatusDto.setResId(2411L);//根据resIds设置值
        equipmentStatusDto.setRunTime(new Date());
        equipmentStatusDto.setName("cccc");
        equipmentStatusDto.setPerson("caozuoren");
        equipmentResultService.addorUpdateEquipmentStatus(equipmentStatusDto);
    }

    /**
     * 记录维修保养
     */
    @Test
    public void testaddMaintenance(){
        EquipmentResult equipmentResult =  equipmentResultService.getEquipmentResultById(2411L);
        System.out.print(equipmentResult.getName());
        EquipmentMaintenanceDto maintenanceDto = new EquipmentMaintenanceDto();
        maintenanceDto.setName("维修保养11111");
//        maintenanceDto.setResId(2870l);
        equipmentResultService.addOrUpdateEquipmentMaintenance(maintenanceDto);
    }

    /**
     *记录设备
     */
    @Test
    public void testaddEquipment(){
        EquipmentResult result = equipmentResultService.getEquipmentResultById(2572L);
        System.out.print(result.getName());
        EquipmentDto equipmentDto = new EquipmentDto();
        equipmentDto.setMeasuringName("zs");
        equipmentDto.setResId(2572L);
        equipmentResultService.addorUpdateEquipment(equipmentDto);
    }

    /**
     * 删除运行信息
     */
    @Test
    public void testDeleteStatus(){
        Long resId = 2411L;
        Long id = 2412L;//运行ID
        equipmentResultService.deleteStatus(resId, id);
    }

    /**
     * 删除设备
     */
    @Test
    public void testDeleteEquipments(){
        Long id =3056L;
        Long resId =2572L;
        equipmentResultService.deleteEquipment(resId,id);
    }

    /**
     * 删除保养维修信息
     */
    @Test
    public void testDelete(){
        Long resId = 2572L;
        Long id = 2793L;
        equipmentResultService.deleteEquiMainten(resId,id);
    }


    /**
     * 删除记录
     */
    @Test
    public void testDel(){
        List<Long> ids = new ArrayList<>();
        ids.add(3078L);
        ids.add(3077L);
        equipmentResultService.deleteRequimentResult(ids);
    }



}
