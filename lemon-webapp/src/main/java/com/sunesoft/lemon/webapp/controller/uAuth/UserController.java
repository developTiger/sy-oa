package com.sunesoft.lemon.webapp.controller.uAuth;

import com.sunesoft.lemon.deanery.resultCertificate.application.ResultCertificateService;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateDto;
import com.sunesoft.lemon.deanery.treatiseCG.application.TreatiseService;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.fr.loggers.Logger;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmpGroupService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.DeptmentCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.uAuth.application.EmpAuthService;
import com.sunesoft.lemon.syms.uAuth.application.SysRoleService;
import com.sunesoft.lemon.syms.uAuth.application.dtos.RoleDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.AssembleObject;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
import com.sunesoft.lemon.webapp.utils.URI;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */

@Controller
public class UserController extends Layout {

    @Autowired
    Logger logger;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmpGroupService empGroupService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    EmpAuthService empAuthService;

    @Autowired
    FileService fileService;

    @Autowired
    TreatiseService treatiseService;//论著

    @Autowired
    UserSession us;

    @RequestMapping(value = "sra_u_userCourty")
    public ModelAndView queryUserCourty(Model model) {

        DeptmentCriteria deptmentCriteria = new DeptmentCriteria();
        deptmentCriteria.setDeptName("");
        List<DeptmentDto> deptmentDto = deptmentService.getAllDept();
        model.addAttribute("permGroup", deptmentDto);
        return view(layout, "uAuth/queryUserCourty", model);
    }


    @RequestMapping(value = "_addUserForm")
    public ModelAndView addUserForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        EmpDto emp = employeeService.getEmpById(Long.parseLong(id));
        model.addAttribute("uname", emp.getName());
        model.addAttribute("roleName", emp.getRoleName().split(","));
        List<RoleDto> list = sysRoleService.getAllRole();
        model.addAttribute("beansRole", list);
        return view("/uAuth/_addUserForm", model);
    }

    @RequestMapping(value = "ajax_update_user_role", method = RequestMethod.POST)
    public void updateUserRole(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Long roleid = null;
        String role = request.getParameter("roleIds");
        CommonResult result = new CommonResult(false, "操作失败!");
        if (!StringUtils.isNullOrWhiteSpace(role)) {
            result = empAuthService.setEmpRole(Long.parseLong(id), role.split(","));
        }

        //  CommonResult result=new CommonResult(true,"",9L);
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "sra_u_user")
    public ModelAndView userInfos(Model model, HttpServletRequest request, HttpServletResponse response) {

        Long empSessionDto = us.getCurrentUser(request).getDeptId();
        List<DeptmentDto> deptmentDto = deptmentService.getAllDept();
        model.addAttribute("showOperate", 0);
        model.addAttribute("UserDept", deptmentDto);
        return view(layout, "uAuth/queryUser", model);
    }

    @RequestMapping(value = "resetPwd")
    @ResponseBody
    public CommonResult resetPwd(Model model, HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        return employeeService.changePassword(id, "123456");
    }

    @RequestMapping(value = "sra_u_userInfo")
    public ModelAndView userInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (id == null) {
            id = "";
        }
        model.addAttribute("empId", id);
        return view(layout, "/uAuth/userInfo", model);
    }

    @Autowired
    ResultCertificateService resultCertificateService;//成果证书

    @RequestMapping(value = "showEmployeeInforTotal")
    public ModelAndView showEmployeeInfoAll(Model model, HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        EmpDto empDto = employeeService.getEmpById(Long.parseLong(id));
        List<EducationDto> educationDto = employeeService.getAllEdus(Long.parseLong(id));
        List<WorkExperienceDto> workExperienceDtos = employeeService.getAllWorkExperiences(Long.parseLong(id));
        List<TechPositionDto> techPositionDtos = employeeService.getAllTechPositions(Long.parseLong(id));
        List<QualificationDto> qualificationDtos = employeeService.getAllQualifications(Long.parseLong(id));
        List<FamilyDto> familyDtos = employeeService.getAllFams(Long.parseLong(id));
        /**
         *成果证书
         */
        List<ResultCertificateDto> resultCertificateList = resultCertificateService.query_ResultCertificate_ByApperId(Long.parseLong(id));
        /**
         *期刊/论文
         */
        List<TreatiseDto> treatisList = treatiseService.query_ByApplyer(Long.parseLong(id));
        //员工组 名称
        List<Long> list = new ArrayList<>();
        list.add(empDto.getGroupId());
        List<EmpGroupDto> empGroupDtos = empGroupService.getByGroupsIds(list);
        if (empGroupDtos != null && empGroupDtos.size() > 0)
            model.addAttribute("empGroupName", empGroupDtos.get(0).getName());

        model.addAttribute("emp", empDto);
        model.addAttribute("educ", educationDto);
        model.addAttribute("workEx", workExperienceDtos);
        model.addAttribute("TechPos", techPositionDtos);
        model.addAttribute("resultCertificateList", resultCertificateList);
        model.addAttribute("treatisList", treatisList);

        model.addAttribute("QualiFic", qualificationDtos);

        model.addAttribute("Family", familyDtos);

        model.addAttribute("help", Helper.class);

        return view(layout, "uAuth/showEmployeeInforTotal", model);
    }

    @RequestMapping(value = "BasicInformation", method = RequestMethod.GET)
    public void toEmpInformation(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        EmpDto empDto = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            empDto = employeeService.getEmpById(Long.parseLong(id));
        } else {
            empDto = new EmpDto();
        }
        List<EmpDto> empDtoList = employeeService.getAllEmps();
        if (!StringUtils.isNullOrWhiteSpace(id)) {//todo 自己不可以做自己的领导
            if (empDtoList.contains(empDto)) {
                empDtoList.remove(empDto);
            }
        }

//        if ("".equals(id)) {
//            empDtoList = employeeService.getAllLeaders(Long.parseLong("0"));
//        } else {
//            empDtoList = employeeService.getAllLeaders(Long.parseLong(id));
//        }
        List<EmpGroupDto> empGroupDto = empGroupService.getByGroupsIds();
        List<DeptmentDto> deptDto = deptmentService.getByDeptsIds();
        AssembleObject assembleObject = new AssembleObject();
        assembleObject.setEmp(empDto);
        assembleObject.setEmpGroupDto(empGroupDto);
        assembleObject.setDeptmentDto(deptDto);
        assembleObject.setLeaderSelect(empDtoList);
        String json = JsonHelper.toJson(assembleObject);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "EducationInformationTable")
    public void toEducation(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        List<EducationDto> educationDto = new ArrayList<>();
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            educationDto = employeeService.getAllEdus(Long.parseLong(id));
        }
        String json = JsonHelper.toJson(new PagedResult<>(educationDto, Integer.parseInt(id), Integer.parseInt(id), Integer.parseInt(id)));
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "WorkExInformationTable")
    public void toWorkExInformation(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        List<WorkExperienceDto> workExDto = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            workExDto = employeeService.getAllWorkExperiences(Long.parseLong(id));
        }
        String json = JsonHelper.toJson(new PagedResult<>(workExDto, Integer.parseInt(id), Integer.parseInt(id), Integer.parseInt(id)));
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "PositionInformationTable")
    public void toTechPosition(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        List<TechPositionDto> techPositionDtos = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            techPositionDtos = employeeService.getAllTechPositions(Long.parseLong(id));
        }
        String json = JsonHelper.toJson(new PagedResult<>(techPositionDtos, Integer.parseInt(id), Integer.parseInt(id), Integer.parseInt(id)));
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "QualificationInformationTable")
    public void toQualification(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        List<QualificationDto> qualificationDtos = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            qualificationDtos = employeeService.getAllQualifications(Long.parseLong(id));
        }
        String json = JsonHelper.toJson(new PagedResult<>(qualificationDtos, Integer.parseInt(id), Integer.parseInt(id), Integer.parseInt(id)));
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "FamilyInformationTable")
    public void toFamily(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        List<FamilyDto> familyDtos = null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            familyDtos = employeeService.getAllFams(Long.parseLong(id));
        }
        String json = JsonHelper.toJson(new PagedResult<>(familyDtos, Integer.parseInt(id), Integer.parseInt(id), Integer.parseInt(id)));
        System.out.println(json);
        AjaxResponse.write(response, json);
    }

    /*
     *取更新或者添加页面
     */
    @RequestMapping(value = "addOrUpdateUserEducation", method = RequestMethod.POST)
    public void addOrUpdateUserEducation(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String empId = request.getParameter("empId");
        if (id != null) {
            EducationDto educationDto = employeeService.getByEduId(Long.parseLong(empId), Long.parseLong(id));
            String json = JsonHelper.toJson(educationDto);
            System.out.println(json);
            AjaxResponse.write(response, json);
        }
    }

    @RequestMapping(value = "addOrUpdateUserWorkExperience", method = RequestMethod.POST)
    public void addOrUpdateUserWorkExperience(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String empId = request.getParameter("empId");
        if (id != null) {
            WorkExperienceDto workExperienceDto = employeeService.getByWorkExperienceId(Long.parseLong(empId), Long.parseLong(id));
            String json = JsonHelper.toJson(workExperienceDto);
            System.out.println(json);
            AjaxResponse.write(response, json);
        }
    }

    @RequestMapping(value = "addOrUpdateUserPositionInformation", method = RequestMethod.POST)
    public void addOrUpdateUserPositionInformation(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String empId = request.getParameter("empId");
        if (id != null) {
            TechPositionDto techPositionDto = employeeService.getByTPId(Long.parseLong(empId), Long.parseLong(id));
            String json = JsonHelper.toJson(techPositionDto);
            System.out.println(json);
            AjaxResponse.write(response, json);
        }
    }

    @RequestMapping(value = "addOrUpdateUserQualification", method = RequestMethod.POST)
    public void addOrUpdateUserQualification(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String empId = request.getParameter("empId");
        if (id != null) {
            QualificationDto qualificationDto = employeeService.getByQuaId(Long.parseLong(empId), Long.parseLong(id));
            String json = JsonHelper.toJson(qualificationDto);
            System.out.println(json);
            AjaxResponse.write(response, json);
        }
    }

    @RequestMapping(value = "addOrUpdateUserFamily", method = RequestMethod.POST)
    public void addOrUpdateUserFamily(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String empId = request.getParameter("empId");
        if (id != null) {
            FamilyDto familyDto = employeeService.getByFamId(Long.parseLong(empId), Long.parseLong(id));
            String json = JsonHelper.toJson(familyDto);
            System.out.println(json);
            AjaxResponse.write(response, json);
        }
    }

    /*
     *
     *做更新或者新增！！！！！
     */
    @RequestMapping(value = "ajax_Add_BasicInformation", method = RequestMethod.POST)
    public void EmpInformationSave(EmpDto empDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            empDto.setId(Long.parseLong(id));
        }
        CommonResult result = employeeService.addOrUpdateEpm(empDto);
        //  CommonResult result=new CommonResult(true,"",9L);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_Add_EducationInformation", method = RequestMethod.POST)
    public void EducationSave(EducationDto educationDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        educationDto.setEmpId(Long.parseLong(id));
        CommonResult result;
        if (educationDto.getId() != null && educationDto.getId() > 0) {
            result = employeeService.updateEdu(educationDto);
        } else {
            result = employeeService.addEdu(educationDto);
        }
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_Add_WorkExInformation", method = RequestMethod.POST)
    public void WorkExperienceSave(WorkExperienceDto workExperienceDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        workExperienceDto.setEmpId(Long.parseLong(id));
        //employeeService.addWorkExperience(workExperienceDto);
        CommonResult result;
        if (workExperienceDto.getId() != null && workExperienceDto.getId() > 0) {
            result = employeeService.updateWorkExperience(workExperienceDto);
        } else {
            result = employeeService.addWorkExperience(workExperienceDto);
        }
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_Add_PositionInformation", method = RequestMethod.POST)
    public void TechPositionSave(TechPositionDto techPositionDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        techPositionDto.setEmpId(Long.parseLong(id));
        //employeeService.addWorkExperience(workExperienceDto);
        CommonResult result;
        if (techPositionDto.getId() != null && techPositionDto.getId() > 0) {
            result = employeeService.updateTechPosition(techPositionDto);
        } else {
            result = employeeService.addTechPosition(techPositionDto);
        }
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_Add_QualificationInformation", method = RequestMethod.POST)
    public void QualificationSave(QualificationDto qualificationDto, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String id = request.getParameter("empId");

        String time = request.getParameter("time");

        qualificationDto.setEmpId(Long.parseLong(id));
        qualificationDto.setCreTime(DateHelper.parse(time, "yyyy-MM-dd"));


        CommonResult commonResult = null;
        //employeeService.addWorkExperience(workExperienceDto);

        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("filename1");
        CommonResult result;
        for (MultipartFile myfile : myfiles) {

            try {
                String fileName = myfile.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                FileInfoDto fileInfoDto = new FileInfoDto();
                fileInfoDto.setFileName(fileName);
                fileInfoDto.setExtensions(extension);
                fileInfoDto.setInputStream(myfile.getInputStream());
                String FileId = fileService.upload(fileInfoDto);

                qualificationDto.setFileId(FileId);
                if (!StringUtils.isNullOrWhiteSpace(fileName)) {
                    qualificationDto.setFileName(fileName);
                }
//                    if(qualificationDto.getId()!=null && qualificationDto.getId()>0) {
//                        result1=  employeeService.updateQualification(qualificationDto);
//                    }else {
//                        result1 = employeeService.addQualification(qualificationDto);
//                    }
//                    String json=JsonHelper.toJson(result1);
//                    System.out.println(result1);
//                    AjaxResponse.write(response,json);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (qualificationDto.getId() != null && qualificationDto.getId() > 0) {
            commonResult = employeeService.updateQualification(qualificationDto);
        } else {
            commonResult = employeeService.addQualification(qualificationDto);
        }
        String json = JsonHelper.toJson(commonResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_Add_FamilyInformation", method = RequestMethod.POST)
    public void FamilySave(FamilyDto familyDto, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("empId");
        familyDto.setEmpId(Long.parseLong(id));
        //employeeService.addWorkExperience(workExperienceDto);
        CommonResult result;
        if (familyDto.getId() != null && familyDto.getId() > 0) {
            result = employeeService.updateFam(familyDto);
        } else {
            result = employeeService.addFam(familyDto);
        }
        String json = JsonHelper.toJson(result);
        System.out.println(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "deleteUserInfo", method = RequestMethod.POST)
    public void deleteUserEducation(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String empId = request.getParameter("empId");
        String type = request.getParameter("type");
        CommonResult commonResult;
        if ("EducationInformation".equals(type)) {
            commonResult = employeeService.deleteEdu(Long.parseLong(empId), Long.parseLong(id));
        } else if ("FamilyInformation".equals(type)) {
            commonResult = employeeService.deleteFam(Long.parseLong(empId), Long.parseLong(id));
        } else if ("PositionInformation".equals(type)) {
            commonResult = employeeService.deleteTechPosition(Long.parseLong(empId), Long.parseLong(id));
        } else if ("QualificationInformation".equals(type)) {
            commonResult = employeeService.deleteQualification(Long.parseLong(empId), Long.parseLong(id));
        } else if ("WorkExInformation".equals(type)) {
            commonResult = employeeService.deleteWorkExperience(Long.parseLong(empId), Long.parseLong(id));
        } else {
            commonResult = new CommonResult(false, "删除有误", -1L);
        }
        String json = JsonHelper.toJson(commonResult);
        System.out.println(json);
        AjaxResponse.write(response, json);

    }

    @RequestMapping(value = "ajax_userQuery_list")
    public void getEmpInFos(EmpCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
//        if(criteria.getDeptId()==null){
//            criteria.setDeptId(us.getCurrentUser(request).getDeptId());
//        }
        String highestEdu = URI.deURI(request.getParameter("highestEdu"));
        if (!StringUtils.isNullOrWhiteSpace(highestEdu)) {
            criteria.setHighestEdu(highestEdu);
        }

        String workKind = URI.deURI(request.getParameter("workKind"));
        if (!StringUtils.isNullOrWhiteSpace(workKind)) {
            criteria.setCurrenttechPosition(workKind);
        }

//        String qualificationName = URI.deURI(request.getParameter("qualificationName"));
//        if (!StringUtils.isNullOrWhiteSpace(qualificationName))
//            criteria.setCurrentQualifications(qualificationName);

        String loginName = URI.deURI(request.getParameter("loginName"));
        if (!StringUtils.isNullOrWhiteSpace(loginName))
            criteria.setLoginName(loginName);

        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        if (!StringUtils.isNullOrWhiteSpace(empSessionDto.getDeptNo())) {
            //TODO 部门编码hotfix
            if (!"05RSK".equals(empSessionDto.getDeptNo()) && !"01YLD".equals(empSessionDto.getDeptNo())) {
                criteria.setDeptId(empSessionDto.getDeptId());
            }
        }


        PagedResult result = employeeService.findEmpsPaged(criteria);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_setUserStstus", method = RequestMethod.POST)
    public void setUserStstus(HttpServletRequest request, HttpServletResponse response) {
        Boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的用户");
        } else {
            CommonResult result = employeeService.setEmpStatus(listid, isActive ? 1 : 0);
            AjaxResponse.write(response, "text", result.getIsSuccess() ? "success" : "error");
        }
    }


    @RequestMapping(value = "sra_changePassword")
    public ModelAndView changePasswordIndex(Model model, HttpServletResponse response) throws IOException {


        return view(layout, "common/changePassword", model);
    }

    @RequestMapping("ajax_changePassword")
    @ResponseBody
    public CommonResult changePassword(HttpServletRequest request) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        CommonResult commonResult = employeeService.userChangePassword(empSessionDto.getId(), oldPassword, newPassword);
        return commonResult;
    }

    @RequestMapping("ajax_resetHoliday")
    @ResponseBody
    public CommonResult resetHoliday(HttpServletRequest request) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        if (empSessionDto == null)
            return ResultFactory.commonError("请先登录");
        else
            return employeeService.resetYearAndSpa(null);
    }

    /**
     * 下载可休的带薪年假或者疗养假的人员名单
     *
     * @param request
     * @param response
     */
    @RequestMapping("download_restYear_span")
    public void getCanRestYearOrSpan(HttpServletRequest request, HttpServletResponse response) {
        String time = request.getParameter("inputTime");
        String type = request.getParameter("inputType");
        String string = "带薪年假";
        if (!StringUtils.isNullOrWhiteSpace(type) && type.trim().equals("0"))
            string = "疗养假";
        Date date = new Date();
        if (!StringUtils.isNullOrWhiteSpace(time))
            date = DateHelper.parse(time, "yyyy-MM-dd");
        List<RestSpanAndYear> list = employeeService.getCanRestYearOrSpan(date, Integer.valueOf(type));
        ExpotExcel<RestSpanAndYear> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"姓名", "员工编号", "部门", "假日天数"};
        expotExcel.doExportExcel(DateHelper.getYear(date) + "年可休" + string + "的人员名单", header, list, "yyyy-MM-dd", response);

    }
}
