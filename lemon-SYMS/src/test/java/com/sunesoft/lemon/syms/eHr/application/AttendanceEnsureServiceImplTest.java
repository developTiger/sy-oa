package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpAttendancesCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AttendanceEnsureServiceImplTest {
    @Autowired
    AttendanceEnsureService attendanceEnsureService;
    @Autowired
   EmployeeService employeeService;
    @Autowired
    DeptmentService deptmentService;
    @Test
    public void testAddOrUpdateEnsureInfo() throws Exception {
        AttendanceOperateDto dto = new AttendanceOperateDto();
        EmpDto emp=employeeService.getEmpById(5L);
        dto.setEmpId(emp.getId());
        dto.setName(emp.getName());
        dto.setDepName(emp.getDeptName());
        dto.setDepId(emp.getDeptId());
        dto.setAttendanceKind(AttendanceKind.S1);
        Date d = new Date();
        Date d1 = DateHelper.addDay(d, 3);
        Date d2 = DateHelper.addDay(d, 6);
        dto.setBeginDate(d1);
        dto.setEndDate(d2);
        dto.setTimeRange(2f);
        dto.setAttDate(d);
        CommonResult c = attendanceEnsureService.addOrUpdateEnsureInfo(dto);
        if(c!=null){
            System.out.println(c.getIsSuccess()+"\t"+c.getMsg()+"\t"+c.getId());
        }else {
            System.out.println("null");
        }

    }

    @Test
    public void testEnsureAttendance() throws Exception {
//        attendanceEnsureService.ensureAttendance(1L, "2016-07-05");
//        attendanceEnsureService.ensureAttendance(1L, "2016-06-02 00:00:00");
    }
    @Test
    public void testGetEmpAttendanceInfo() throws Exception {
        EmpAttendancesCriteria criteria=new EmpAttendancesCriteria();
        criteria.setEmpId(3L);
        criteria.setDeptId(1L);
        criteria.setAttDate(new Date());
        List<EmpAttendanceDto> dtos=attendanceEnsureService.getEmpAttendanceInfo(criteria);
        if(dtos!=null){
            System.out.println(dtos.size());
        }

    }
    @Test
    public void testGetNearlyFiveDayAttendance() throws Exception {
        List<NearlyFiveDayAttendanceInfoDto> list=attendanceEnsureService.getNearlyFiveDayAttendance();
        if(list!=null){
            System.out.println(list.size());
        }
    }

    @Test
    public void n(){
        Date d=new Date();
        Date d2=DateHelper.addDay(d,3);
        System.out.println(d+"\t"+d2);

        Float f=1.0F;
        f++;
        System.out.println(f);

    }
}