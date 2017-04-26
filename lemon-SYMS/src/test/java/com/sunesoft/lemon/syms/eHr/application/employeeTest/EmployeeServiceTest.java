package com.sunesoft.lemon.syms.eHr.application.employeeTest;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.*;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EmployeeServiceTest {
    @Autowired
    EmployeeService empService;

    @Test
    public void getByFamIdTest() {
        FamilyDto f = empService.getByFamId(4L, 3L);
        if (f != null) {
            System.out.println(f.getId() + "\t" + f.getStrBirthday());
        }
    }

    @Test
    public void getByEduIdTest() {
        EducationDto dto = empService.getByEduId(4L, 4L);
        if (dto != null)
            System.out.println(dto.getId() + "\t" + dto.getStrGraduation() + "\t" + dto.getStrObtain());

    }

    @Test
    public void addOrUpdateEpmTest() {
        EmpDto dto = new EmpDto();
//        dto.setId(1L);
        dto.setJoinTime("1991-01-01 12:12:12");



        System.out.println(empService.addOrUpdateEpm(dto));

    }

    @Test
    public void getEmpByIdTest() {
        EmpDto e = empService.getEmpById(20L);
        if (e != null) {
            System.out.println(e.getId() + "\t" + e.getName() + "\t" + e.getAge() + "\t" + e.getSeniority());
        }
    }

    @Test
    public void loginTest() {
        EmpSessionDto e = empService.login("k", "k");
        if (e == null) {
            System.out.println("登录不了");
        } else {
            System.out.println(e.getName() + "\t" + e.getLoginName() + "\t" + e.getId());
        }

    }

//    @Test
//    public void changePasswordTest() {
//        CommonResult b = empService.changePassword(1L, "kk");
//        System.out.println(b);
//
//    }

//    @Test
//    public void setEmpStatusTest() {
//        List<Long> list=new ArrayList<Long>();
//
//        list.add(1L);
//        list.add(2L);
//        list.add(3L);
////        List<Employee> employees = empService.GetEmpsByIds(list);
////        boolean b=false;
////        b=empService.setEmpStatus(list,1);
//         System.out.println(employees.size());
//    }

    @Test
    public void deleteEpmTest() {
        List<Long> list = new ArrayList<Long>();
        list.add(4L);
        list.add(2L);
        list.add(3L);
        CommonResult b = empService.deleteEpm(list);
        System.out.println(b);

    }

    @Test
    public void FindEmpsPagedTest() {
        EmpCriteria criteria = new EmpCriteria();
        criteria.setPageNumber(1);
        criteria.setPageSize(10);
        PagedResult<EmpDto> pg = empService.findEmpsPaged(criteria);
        System.out.println(pg.getPageNumber() + "\t" + pg.getPageSize() + "\t" + pg.getPagesCount() + "\t" + pg.getItems().size());

    }

    @Test
    public void getHeaderTest() {
        List<EmpDto> pg = empService.getHeader();
        System.out.println(pg.size() + "\t");

    }




    @Test
    public void getEmpsByUserNameTest() {
        List<EmpDto> dtos = empService.getEmpsByUserName("x7");
        System.out.println(dtos.size());
    }

    @Test
    public void getAllEmpsTest() {
        List<EmpDto> dtos = empService.getAllEmps();
        System.out.println(dtos.size());
    }
    @Test
    public void getAllLeaderTest() {
        List<EmpDto> dtos = empService.getAllLeaders(3L);
        System.out.println(dtos.size());
    }

    @Test
    public void addEduTest() {
        EmpDto dto = empService.getEmpById(4L);
        System.out.println(dto.getId() + "\t" + dto.getName());
        EducationDto eDto = new EducationDto();
        eDto.setEmpId(4L);
        eDto.setDegree("doctor");
        eDto.setIsCurrent(true);
        eDto.setStrObtain("2015-01-05 12:25:55");
        eDto.setStrGraduation("2015-01-05 12:25:55");
        CommonResult c = empService.addEdu(eDto);
        System.out.println(c.getIsSuccess());

    }

    @Test
    public void updateEduTest() {
        EducationDto eDto = new EducationDto();
        eDto.setEmpId(4L);
        eDto.setId(3L);
        eDto.setDegree("ma");
        eDto.setIsCurrent(true);
        CommonResult l = empService.updateEdu(eDto);
        System.out.println(l);

    }

    @Test
    public void deleteEduTest() {
        CommonResult b = empService.deleteEdu(4L, 3L);
        System.out.println(b.getId() + "\t" + b.getMsg() + "\t" + b.getIsSuccess());

    }

    @Test
    public void getAllEdusTest() {
        List<EducationDto> list = empService.getAllEdus(4L);
        System.out.println(list.size());

    }

    @Test
    public void finderEdusPagedTest() {
        EduCriteria eduCriteria = new EduCriteria();
        eduCriteria.setDegree("doctor");

        PagedResult<EducationDto> pg = empService.finderEdusPaged(4L, eduCriteria);
        System.out.println(pg.getPageNumber() + "\t" + pg.getPageSize() + "\t" + pg.getItems().size());

    }

//    @Test
//    public void addGroupTest() {
//        EmpGroupDto eg=new EmpGroupDto();
//        eg.setEmpId(4L);
//        eg.setName("jingang");
//        CommonResult l=empService.addGroup(eg);
//        System.out.println(l);
//
//
//    }
//
//    @Test
//    public void updateGroupTest() {
//        EmpGroupDto eg=new EmpGroupDto();
//        eg.setEmpId(4L);
//        eg.setId(3l);
//        eg.setName("maomao");
//        CommonResult l=empService.updateGroup(eg);
//        System.out.println(l);
//
//        EmpDto emp=empService.getEmpById(4L);
//        System.out.println(emp.getId()+"\t"+emp.getName()+"\t"+emp.getEmpGroup().getName());
//
//    }
//
//    @Test
//    public void deleteGroupTest() {
//        CommonResult b=empService.deleteGroup(4L);
//        System.out.println(b);
//        EmpDto dto=empService.getEmpById(4L);
//        if(dto.getEmpGroup()!=null){
//            System.out.println(dto.getEmpGroup().getName()+"\t"+dto.getEmpGroup().getIsActive());
//        }else {
//            System.out.println("null");
//        }
//    }
//
//    @Test
//    public void getGroupByIdTest() {
//        EmpGroupDto dto=empService.getGroupById(4L);
//        if(dto!=null){
//            System.out.println(dto.getName()+dto.getId());
//        }else {
//            System.out.println("null");
//        }
//
//    }

    @Test
    public void addFamTest() {
        FamilyDto dto = new FamilyDto();
        dto.setName("xia");
        dto.setLabel("s");
        dto.setEmpId(4L);
        dto.setStrBirthday("1010-10-10 11:12:13");
        CommonResult l = empService.addFam(dto);
        System.out.println(l.getId() + "\t" + l.getMsg() + "\t" + l.getIsSuccess());

    }

    @Test
    public void deleteFamTest() {
        CommonResult b = empService.deleteFam(4L, 6L);
        System.out.println(b);
//        Employee emp=empService.getById(4L);
//        List<Family> list=emp.getFamilies();
//        System.out.println(list.size());
//        for(Family f:list){
//            System.out.println(f.getId());
//        }

    }

    @Test
    public void updateFamTest() {
        FamilyDto dto = new FamilyDto();
        dto.setName("s");
        dto.setLabel("t");
        dto.setId(6l);
        dto.setEmpId(4L);
        CommonResult l = empService.updateFam(dto);
        System.out.println(l);

    }

    @Test
    public void getAllFamsTest() {
        List<FamilyDto> list = empService.getAllFams(4L);
        if (list != null && list.size() > 0) {
            System.out.println(list.size());
            for (FamilyDto f : list) {
                System.out.println(f.getId() + "\t" + f.getStrBirthday());
            }
        } else {
            System.out.println("null");
        }

    }

    @Test
    public void findFamsPagedTest() {
        FamCriteria fam = new FamCriteria();
        fam.setName("x");
        PagedResult<FamilyDto> pg = empService.findFamsPaged(4L, fam);
        System.out.println(pg.getItems().size() + "\t" + pg.getPagesCount() + "\t" + pg.getPageNumber() + "\t" + pg.getPagesCount());

    }

    @Test
    public void addQualificationTest() {
        QualificationDto dto = new QualificationDto();
        dto.setName("q");
        dto.setEmpId(4L);
        dto.setUrl("#");
        CommonResult l = empService.addQualification(dto);

    }

    @Test
    public void updateQualificationTest() {
        QualificationDto dto = new QualificationDto();
        dto.setName("w");
        dto.setEmpId(4L);
        dto.setId(3L);
        dto.setUrl("$");
        CommonResult l = empService.updateQualification(dto);
        System.out.println(l);

    }

    @Test
    public void deleteQualificationTest() {
        CommonResult b = empService.deleteQualification(4L, 3L);
        System.out.println(b);
    }

    @Test
    public void getAllQualificationsTest() {
        List<QualificationDto> list = empService.getAllQualifications(4L);
        if (list != null && list.size() > 0) {
            System.out.println(list.size());
            for (QualificationDto d : list) {
                System.out.println(d.getId() + "\t" + d.getName());
            }
        }
    }

    @Test
    public void findQualificationsPagedTest() {

        QualificationCriteria criteria = new QualificationCriteria();
        criteria.setName("q");
        PagedResult<QualificationDto> pg = empService.findQualificationsPaged(4L, criteria);
        if (pg != null) {
            System.out.println(pg.getItems().size() + "\t" + pg.getPageNumber() + "\t" + pg.getPageSize() + "\t" + pg.getPagesCount());

        }
    }

    @Test
    public void addTechPositionTest() {
        TechPositionDto dto = new TechPositionDto();
        dto.setName("x");
        dto.setEmpId(4L);
        dto.setWay("y");
        dto.setStrOutTime("1010-10-10 11:12:13");
        dto.setStrInTime("1010-10-10 11:12:13");
//        dto.setStrInObtian("1010-10-10 11:12:13");
        CommonResult l = empService.addTechPosition(dto);
        System.out.println(l);
//        for(int i=0;i<=10;i++){
//            Long l=empService.addTechPosition(dto);
//        }

    }

    @Test
    public void updateTechPositionTest() {
        TechPositionDto dto = new TechPositionDto();
        dto.setId(22L);
        dto.setName("qq");
        dto.setEmpId(4L);
        dto.setWay("qq");
        CommonResult l = empService.updateTechPosition(dto);
        System.out.println(l.getId() + "\t" + l.getMsg() + "\t" + l.getIsSuccess());
    }

    @Test
    public void deleteTechPositionTest() {
        CommonResult b = empService.deleteTechPosition(4L, 22L);
        System.out.println(b);

    }

    @Test
    public void getAllTechPositionsTest() {
        List<TechPositionDto> list = empService.getAllTechPositions(4L);
        if (list != null && list.size() > 0) {
            System.out.println(list.size());
            for (TechPositionDto d : list) {
                System.out.println(d.getId() + "\t" + d.getName());
            }
        }
    }

    @Test
    public void findTechPositionsPagedTest() {
        TechPositionCriteria criteria = new TechPositionCriteria();
        criteria.setName("x");
        PagedResult<TechPositionDto> pg = empService.findTechPositionsPaged(4L, criteria);
        if (pg != null) {
            System.out.println(pg.getPageNumber() + "\t" + pg.getPageSize() + "\t" + pg.getPagesCount() + "\t" + pg.getItems().size());
        }
    }

    @Test
    public void addWorkExperienceTest() {
        WorkExperienceDto dto = new WorkExperienceDto();
        dto.setEmpId(4L);
        dto.setWorkName("x");
        dto.setStrOverTime("1010-10-10 11:12:13");
        CommonResult l = empService.addWorkExperience(dto);
        System.out.println(l);
//        for(int i=0;i<13;i++){
//            Long l=empService.addWorkExperience(dto);
//        }
    }

    @Test
    public void updateWorkExperienceTest() {
        WorkExperienceDto dto = new WorkExperienceDto();
        dto.setEmpId(4L);
        dto.setId(16L);
        dto.setWorkName("a");
        CommonResult l = empService.updateWorkExperience(dto);
        System.out.println(l.getIsSuccess() + "\t" + l.getIsSuccess());
    }

    @Test
    public void deleteWorkExperienceTest() {
        CommonResult b = empService.deleteWorkExperience(4L, 17L);
        System.out.println(b.getId() + "\t" + b.getIsSuccess() + "\t" + b.getMsg());
    }

    @Test
    public void getAllWorkExperiencesTest() {
        List<WorkExperienceDto> list = empService.getAllWorkExperiences(4L);
        if (list != null && list.size() > 0) {
            System.out.println(list.size());
            for (WorkExperienceDto d : list) {
                System.out.println(d.getId() + "\t" + d.getWorkName());
            }
        }
    }

    @Test
    public void findWorkExperiencesPagedTest() {
        WorkExperienceCriteria criteria = new WorkExperienceCriteria();
        criteria.setWorkName("x");
        PagedResult<WorkExperienceDto> pg = empService.findWorkExperiencesPaged(4L, criteria);
        if (pg != null) {
            System.out.println(pg.getPageNumber() + "\t" + pg.getPageSize() + "\t" + pg.getPagesCount() + "\t" + pg.getItems().size());
        }
    }
    @Test
    public void resetYearAndSpa() {
        List<Long> list=new ArrayList<>();
        list.add(1100l);
       CommonResult c=empService.resetYearAndSpa(list);
        if(c.getIsSuccess()){
            System.out.println("lahjhih");
        }

    }
}

