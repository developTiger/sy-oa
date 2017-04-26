package com.sunesoft.lemon.webapp.controller.projectInfo;

import com.sunesoft.lemon.deanery.delayflow.application.dto.FormDelayApplyDto;
import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectAcceptanceService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectApplyService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectExecutionService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.criteria.FormAcceptanceCriteria;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectAcceptanceDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectExecutoryDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectAcceptance;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.scientificRPKU.ScienticRPKUService;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.MultiSelectUserWithDept;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by zy on 2016/8/25.
 */

@Controller
public class ProjectAcceptanceController extends Layout {
    @Autowired
    FormProjectAcceptanceService formProjectAcceptanceService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    FormHeaderService headerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    FormProjectApplyService formProjectApplyService;

    @Autowired
    UserSession us;
    @Autowired
    FormListService formListService;
    @Autowired
    FileService fileService;

    @Autowired
    ScientificResearchProjectService scientificResearchProjectService;

    @Autowired
    ScienticRPKUService scienticRPKUService;

    @RequestMapping("syy_kg_lc05_a")
    public ModelAndView syy_kg_lc05_a(Model model, HttpServletRequest request) {

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        FormProjectAcceptanceDto acceptance=null;
        ScientificRPKU scientificRPKU=null;
        /*if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance= formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            if(!StringUtils.isNullOrWhiteSpace(acceptance.getProjectNo())){
                scientificRPKU=scienticRPKUService.getIdByProjectNo(acc0eptance.getProjectNo());
            }
        }*/
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance= formProjectAcceptanceService.getFormByFormNo1((Long.parseLong(formNo)));
            if(!StringUtils.isNullOrWhiteSpace(acceptance.getProjectNo())){
                scientificRPKU=scienticRPKUService.getIdByProjectNo(acceptance.getProjectNo());
            }
            model.addAttribute("formNo", acceptance.getFormNo());
            model.addAttribute("majorType",acceptance.getMajorType());
            model.addAttribute("pname", acceptance.getProjectName());
        }

        model.addAttribute("beans", acceptance);
        model.addAttribute("scientificRPKU",scientificRPKU);

        List<ScientificRPKU> queryProject=scienticRPKUService.getAllScientificKu1();
        model.addAttribute("projectName", queryProject);
        //当前项目信息

        return view(applyLayout, "projectInfo/syy_kg_lc05_a", model);
    }

    //获取选中的id
    @RequestMapping("ajax_acceptanceId")
    public void ajax_executionId(Model model, HttpServletRequest request, HttpServletResponse response) {
        String projectNo = request.getParameter("projectNoSel");
        if (!StringUtils.isNullOrWhiteSpace(projectNo)) {
            ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);
            String beginTime=formcon.getBeginTime1().substring(0,10);
            String endTime=formcon.getEndTime1().substring(0,10);
            formcon.setBeginTime1(beginTime);
            formcon.setEndTime1(endTime);
            String json = JsonHelper.toJson(formcon);
            AjaxResponse.write(response, json);
        }
    }


    @RequestMapping(value = "ajax_add_update_lc05_apply", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrupdateTrain(FormProjectAcceptanceDto formProjectAcceptanceDto, HttpServletRequest request) {
        String projectNo = request.getParameter("projectNo1");
        String type_zy=request.getParameter("type_zy");
        formProjectAcceptanceDto.setMajorType(type_zy);
        CommonResult commonResult = formProjectAcceptanceService.addByDto(formProjectAcceptanceDto);
        CommonResult result = formProjectAcceptanceService.submitForm(commonResult.getId(), formProjectAcceptanceDto.getFormKind());
        scienticRPKUService.updateByProjectApply(projectNo,null,null,"1",null,null);
        return result;
    }
    //***************************中心科管人员形式审查*********************************
    @RequestMapping("syy_kg_lc05_view1")
    public ModelAndView syy_kg_lc05_view1(Model model, HttpServletRequest request) {


        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
        }
        String projectNo=acceptance.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());
        model.addAttribute("projectNo",formcon.getProjectNo());

        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view1", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view1", model);//审核 或 补充数据
    }

    //第一步审核保存
    @RequestMapping(value = "kg_lc05_approve1")
    @ResponseBody
    public CommonResult approves1(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){

        String projectNo=request.getParameter("projectNo");
        Long id=Long.parseLong(request.getParameter("id"));

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        if(dto.getAppValue()==3){
            Long l=scienticRPKUService.reset(projectNo,null,null,"0",null,null);
            formProjectAcceptanceService.updateFormProjectAcceptanceById(id);
        }

        return result;
    }

    //***************************单位领导审批*********************************
    @RequestMapping("syy_kg_lc05_view20")
    public ModelAndView syy_kg_lc05_view20(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view20", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view20", model);//审核 或 补充数据
    }

    //第2步审核保存
    @RequestMapping(value = "kg_lc05_approve20")
    @ResponseBody
    public CommonResult approves20(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){

        String leaderWord=request.getParameter("leaderWord");

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        formProjectAcceptanceDto.setLeaderWord(leaderWord);
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;
    }
    //***************************中心科管人员汇总审批*********************************
    @RequestMapping("syy_kg_lc05_view21")
    public ModelAndView syy_kg_lc05_view21(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            model.addAttribute("projectNo_hidden",acceptance.getProjectNo());
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view21", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view21", model);//审核 或 补充数据
    }

    //第2步审核保存
    @RequestMapping(value = "kg_lc05_approve21")
    @ResponseBody
    public CommonResult approves21(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo=request.getParameter("projectNo_hidden");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        if(dto.getAppValue()==2){
            scienticRPKUService.reset(projectNo,null,null,"0",null,null);
        }
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;
    }
    //*****************************科研管理人员汇总审批********************************
    @RequestMapping("syy_kg_lc05_view22")
    public ModelAndView syy_kg_lc05_view22(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            model.addAttribute("projectNo_hidden",acceptance.getProjectNo());
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        model.addAttribute("emp_out",empDtos1);

        //修改专家审核接口
        List<DeptmentDto> deptmentDto = deptmentService.getAllDept();
        List<EmpDto> empDtos = employeeService.getAllEmps();
        List<MultiSelectUserWithDept> multiSelectUserWithDepts = new ArrayList<>();
        for(DeptmentDto dept:deptmentDto){
            MultiSelectUserWithDept multiSelectUserWithDept = new MultiSelectUserWithDept(dept.getId(),dept.getDeptName());
            for(EmpDto emp:empDtos){
                if(emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts.add(multiSelectUserWithDept);
        }
        model.addAttribute("empInfos", multiSelectUserWithDepts);

        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view22", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view22", model);//审核 或 补充数据
    }

    //第2步审核保存
    @RequestMapping(value = "kg_lc05_approve22")
    @ResponseBody
    public CommonResult approves22(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo=request.getParameter("projectNo_hidden");
        String ids=request.getParameter("formNo");
        String empid=request.getParameter("hidid");

        if(empid!=null){
            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC05");
            //dto.setAppValue(AppValue.Y.ordinal());
            //dto.setContent(allContent);

            CommonResult nextProject=null;
            if(dto.getAppValue()==1){
                dto.setAppValue(AppValue.Y.ordinal());
                nextProject=formProjectAcceptanceService.updateAcceptProject(dto,empid) ;
                return nextProject;
            }else if(dto.getAppValue()==2){
                scienticRPKUService.updateByProjectApply(projectNo,null,null,"0",null,null);
                nextProject=formProjectAcceptanceService.updateAcceptProject(dto,empid) ;
            }
            return nextProject;
            //AjaxResponse.write(response, "text", "success");
        }else{
            //AjaxResponse.write(response, "text", "error");
            return null;
        }

        /*EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;*/
    }
    //***************************专家审查**********************************
    @RequestMapping("syy_kg_lc05_view20_zj")
    public ModelAndView syy_kg_lc05_view20_zj(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        String appRole[]=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            if(acceptance.getExpertSuggestion()!=null){
                appRole=acceptance.getExpertSuggestion().split("@");
                model.addAttribute("approle",appRole);
            }
        }

        String str="";
        if(acceptance.getExpertSuggestion()!=null&&!acceptance.getExpertSuggestion().equals("$approverRole")){
            //str+=dto1.getProviousApprover()+","+acceptance.getExpertSuggestion();
            str+=acceptance.getExpertSuggestion();
            model.addAttribute("approverRole",str);
        }

        model.addAttribute("projectNo_hidden",acceptance.getProjectNo());
        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        model.addAttribute("emp",empDtos1);


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view20_zj", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view20_zj", model);//审核 或 补充数据
    }

    //第2步审核保存
    @RequestMapping(value = "kg_lc05_approve20_zj")
    @ResponseBody
    public CommonResult approves20_zj(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo=request.getParameter("projectNo_hidden");
        String expertSuggestion=request.getParameter("expertSuggestion");
        String emps=request.getParameter("emps");

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        formProjectAcceptanceService.getDtoByFormNo1(dto.getFormNo(),expertSuggestion);
        CommonResult result =formProjectAcceptanceService.updateProject1(dto,formProjectAcceptanceDto);
        if(dto.getAppValue()==2){
            scienticRPKUService.reset(projectNo,null,null,"0",null,null);
        }
        return result;
    }
    //*****************************科研管理人员汇总审批1********************************
    @RequestMapping("syy_kg_lc05_view23")
    public ModelAndView syy_kg_lc05_view23(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        String appRole[]=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            if(acceptance.getExpertSuggestion()!=null){
                appRole=acceptance.getExpertSuggestion().split("@");
                model.addAttribute("approle",appRole);
            }
        }

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        model.addAttribute("emp_out",empDtos1);
        model.addAttribute("projectNo_hidden",acceptance.getProjectNo());

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);

        //修改主管领导审核接口
        List<DeptmentDto> deptmentDto = deptmentService.getAllDept();
        List<EmpDto> empDtos = employeeService.getAllEmps();
        List<MultiSelectUserWithDept> multiSelectUserWithDepts = new ArrayList<>();
        for(DeptmentDto dept:deptmentDto){
            MultiSelectUserWithDept multiSelectUserWithDept = new MultiSelectUserWithDept(dept.getId(),dept.getDeptName());
            for(EmpDto emp:empDtos){
                if(emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts.add(multiSelectUserWithDept);
        }
        model.addAttribute("empInfos", multiSelectUserWithDepts);

        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view23", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view23", model);//审核 或 补充数据
    }

    //第2步审核保存
    @RequestMapping(value = "kg_lc05_approve23")
    @ResponseBody
    public CommonResult approves23(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo_hidden=request.getParameter("projectNo_hidden");
        String ids = request.getParameter("formNo");
        String empid = request.getParameter("hidid");
        //String allContent = URI.deURI(request.getParameter("allContent"));
        if(empid!=null){
            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC05");
            //dto.setContent(allContent);

            CommonResult nextProject=null;
            if(dto.getAppValue()==2){
                dto.setAppValue(AppValue.N.ordinal());
                scienticRPKUService.reset(projectNo_hidden,null,null,"0",null,null);
            }
            if(dto.getAppValue()==1){
                dto.setAppValue(AppValue.Y.ordinal());
            }
            nextProject =formProjectAcceptanceService.updateAcceptProject(dto,empid) ;
            //AjaxResponse.write(response, "text", "success");
            return nextProject;
        }else{
            //AjaxResponse.write(response, "text", "error");
            return null;
        }
        /*EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;*/
    }
    //*********************************************************

    @RequestMapping("syy_kg_lc05_view2")
    public ModelAndView syy_kg_lc05_view2(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view2", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view2", model);//审核 或 补充数据
    }

    //第2步审核保存
    @RequestMapping(value = "kg_lc05_approve2")
    @ResponseBody
    public CommonResult approves2(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;
    }


    @RequestMapping("syy_kg_lc05_view3")
    public ModelAndView syy_kg_lc05_view3(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view3", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view3", model);//审核 或 补充数据
    }

    //第3步审核保存
    @RequestMapping(value = "kg_lc05_approve3")
    @ResponseBody
    public CommonResult approves3(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        String keguan=request.getParameter("keGuanEvaluate");
        String professorName=request.getParameter("professorName");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        formProjectAcceptanceDto.setProfessorName(professorName);
        formProjectAcceptanceDto.setKeGuanEvaluate(keguan);

        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;
    }

    @RequestMapping("syy_kg_lc05_view4")
    public ModelAndView syy_kg_lc05_view4(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view4", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view4", model);//审核 或 补充数据
    }
    //第4步审核保存
    @RequestMapping(value = "kg_lc05_approve4")
    @ResponseBody
    public CommonResult approves4(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        String keguan=request.getParameter("keGuanEvaluate");
        String zhuguan=request.getParameter("zhuguanEvaluate");
        String professorName=request.getParameter("professorName");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        formProjectAcceptanceDto.setZhuguanEvaluate(zhuguan);
        formProjectAcceptanceDto.setProfessorName(professorName);
        formProjectAcceptanceDto.setKeGuanEvaluate(keguan);

        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;
    }
    @RequestMapping("syy_kg_lc05_view5")
    public ModelAndView syy_kg_lc05_view5(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());
        model.addAttribute("projectNo",projectNo);


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view5", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view5", model);//审核 或 补充数据
    }

    //第5步审核保存
    @RequestMapping(value = "kg_lc05_approve5")
    @ResponseBody
    public CommonResult approves5(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){

        String projectNo=request.getParameter("projectNo");

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        String keguan=request.getParameter("keGuanEvaluate");
        String zhuguan=request.getParameter("zhuguanEvaluate");
        String professorName=request.getParameter("professorName");
        String  yuanEvaluate =request.getParameter("yuanEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        formProjectAcceptanceDto.setZhuguanEvaluate(zhuguan);
        formProjectAcceptanceDto.setProfessorName(professorName);
        formProjectAcceptanceDto.setKeGuanEvaluate(keguan);
        formProjectAcceptanceDto.setYuanEvaluate(yuanEvaluate);
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        Long l=scienticRPKUService.update(projectNo,null,null,"2",null,null);

        return result;
    }

    @RequestMapping(value = "kg_lc05_resultUpload")
    public void uploadResultFile(HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto empSessionDto = us.getCurrentUser(request);

        String formNo = request.getParameter("formNo");
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("projFile");

        CommonResult result=null;
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false,"请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return;
            } else {
                try {
                    String fileName = myfile.getOriginalFilename();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto=new FileInfoDto();
                    fileInfoDto.setFileName(fileName);
                    fileInfoDto.setIsPublic(false);
                    fileInfoDto.setDeptId(empSessionDto.getDeptId());
                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setInputStream(myfile.getInputStream());
                    String fileId=fileService.upload(fileInfoDto);
                    result= formProjectAcceptanceService.uploadProjectFile(Long.parseLong(formNo),fileId, fileName);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        AjaxResponse.write(response, JsonHelper.toJson(result));
    }


    //流程更改（2016.12.29）
    //中心科管人员汇总审批页面
    @ResponseBody
    @RequestMapping("syy_accept_lot0")
    public ModelAndView lotApprove0(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        FormProjectAcceptanceDto formProjectAcceptanceDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)){
            formProjectAcceptanceDto=formProjectAcceptanceService.getDtoByFormNo(Long.parseLong(id));
        }
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formProjectAcceptanceDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc05_kg_all0", model);
    }
    //验收流程数据列表
    @RequestMapping(value = "ajax_kg05_lotTpl0")
    @ResponseBody
    public void ajax_projectLot0(FormAcceptanceCriteria criteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        //String majorType=request.getParameter("specType");
        String majorType=URI.deURI(request.getParameter("specType"));
        List<FormProjectAcceptanceDto>  formDelayPagedResult = formProjectAcceptanceService.getAcceptanceApproves3(criteria,majorType);
        List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", formDelayPagedResult);
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //中心科管人员汇总审批拒绝
    @RequestMapping(value = "ajax_approval_reject")
    public void approvalProjectNO06(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);

                formProjectAcceptanceService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //中心科管人员汇总审批同意
    @RequestMapping(value = "ajax_approval_approve")
    public void approvalProjectOK0(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = formProjectAcceptanceService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //中心科管人员汇总审批同意
    @RequestMapping(value = "ajax_approval_approve001")
    public void approvalProjectOK001(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = formProjectAcceptanceService.updateProject001(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    //科研管理人员汇总审批页面
    @ResponseBody
    @RequestMapping("syy_accept_lot1")
    public ModelAndView lotApprove1(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        FormProjectAcceptanceDto formProjectAcceptanceDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)){
            formProjectAcceptanceDto=formProjectAcceptanceService.getDtoByFormNo(Long.parseLong(id));
        }

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        /*Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("emp1",empDtos1);*/
        model.addAttribute("emp1",empDtos1);

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formProjectAcceptanceDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc05_kg_all1", model);
    }

    //科研管理人员汇总审批页面
    @ResponseBody
    @RequestMapping("syy_accept_lot5")
    public ModelAndView lotApprove5(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        FormProjectAcceptanceDto formProjectAcceptanceDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)){
            formProjectAcceptanceDto=formProjectAcceptanceService.getDtoByFormNo(Long.parseLong(id));
        }

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        /*Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("emp1",empDtos1);*/
        model.addAttribute("emp1",empDtos1);

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formProjectAcceptanceDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc05_kg_all5", model);
    }


    //科研管理人员汇总审批页面
    @ResponseBody
    @RequestMapping("syy_accept_lot8")
    public ModelAndView lotApprove8(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        FormProjectAcceptanceDto formProjectAcceptanceDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)){
            formProjectAcceptanceDto=formProjectAcceptanceService.getDtoByFormNo(Long.parseLong(id));
        }

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        /*Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("emp1",empDtos1);*/
        model.addAttribute("emp1",empDtos1);

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formProjectAcceptanceDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc05_kg_all8", model);
    }


    //科研管理人员汇总数据列表
    @RequestMapping(value = "ajax_kg05_lotTpl1")
    @ResponseBody
    public void ajax_projectLot1(FormAcceptanceCriteria criteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String majorType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        List<FormProjectAcceptanceDto>  formDelayPagedResult = formProjectAcceptanceService.getAcceptanceApproves4(criteria,majorType);
        List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", formDelayPagedResult);
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //科研管理人员汇总数据列表
    @RequestMapping(value = "ajax_kg05_lotTpl5")
    @ResponseBody
    public void ajax_projectLot5(FormAcceptanceCriteria criteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String majorType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        List<FormProjectAcceptanceDto>  formDelayPagedResult = formProjectAcceptanceService.getAcceptanceApproves5kgk(criteria, majorType);
        List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", formDelayPagedResult);
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //科研管理人员汇总数据列表
    @RequestMapping(value = "ajax_kg05_lotTpl8")
    @ResponseBody
    public void ajax_projectLot8(FormAcceptanceCriteria criteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String majorType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        List<FormProjectAcceptanceDto>  formDelayPagedResult = formProjectAcceptanceService.getAcceptanceApproves8kgk(criteria, majorType);
        List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", formDelayPagedResult);
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //科研管理人员汇总审批拒绝
    @RequestMapping(value = "ajax_approval_reject1")
    public void approvalProjectNO07(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.B.ordinal());
                dto.setContent(allContent);

                formProjectAcceptanceService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //中心科管人员汇总审批同意
    @RequestMapping(value = "ajax_approval_approve1")
    public void approvalProjectOK1(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //CommonResult nextProject = formProjectAcceptanceService.updateProject0(dto);
                CommonResult nextProject = formProjectAcceptanceService.updateAcceptProjectSu(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //科研管理人员汇总审批页面
    @ResponseBody
    @RequestMapping("syy_accept_lot2")
    public ModelAndView lotApprove2(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        FormProjectAcceptanceDto formProjectAcceptanceDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)){
            formProjectAcceptanceDto=formProjectAcceptanceService.getDtoByFormNo(Long.parseLong(id));
        }

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        model.addAttribute("emp1",empDtos1);

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formProjectAcceptanceDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc05_kg_all2", model);
    }

    //科研管理人员汇总数据列表1
    @RequestMapping(value = "ajax_kg05_lotTpl2")
    @ResponseBody
    public void ajax_projectLot2(FormAcceptanceCriteria criteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String majorType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        List<FormProjectAcceptanceDto>  formDelayPagedResult = formProjectAcceptanceService.getAcceptanceApproves5(criteria,majorType);
        List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", formDelayPagedResult);
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //科研管理人员汇总审批拒绝
    @RequestMapping(value = "ajax_approval_reject2")
    public void approvalProjectNO02(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.B.ordinal());
                dto.setContent(allContent);

                formProjectAcceptanceService.updateProject1(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //科研管理人员汇总审批同意
    @RequestMapping(value = "ajax_approval_approve1_2")
    public void approvalProjectOK1_2(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                //CommonResult nextProject = formProjectAcceptanceService.updateProject1(dto);
                CommonResult nextProject = formProjectAcceptanceService.updateAcceptProjectLeader(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    /**
     * 单个审核拒绝（科研管理人员汇总审批01）
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_kygl_approve_pro")
    public void approvalProjectNOOne(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("forno");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        if(empid!=null){
            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC04");
            dto.setAppValue(AppValue.B.ordinal());
            dto.setContent(allContent);
            CommonResult nextProject =formProjectAcceptanceService.updateAcceptProject(dto,empid) ;//formDelayApplyService.updateDelayProject(dto,empid);
            AjaxResponse.write(response, "text", "success");

        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    /**
     * 单个审核同意（科研管理人员汇总审批01）
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_kygl_approve_proyes")
    public void approvalProjectOKOne(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("prono");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        if(empid!=null){
            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC04");
            dto.setAppValue(AppValue.Y.ordinal());
            dto.setContent(allContent);

            CommonResult nextProject =formProjectAcceptanceService.updateAcceptProject(dto,empid) ;
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    /**
     * 单个审核拒绝（科研管理002）
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_kygl01_approve_pro")
    public void approvalProjectNOOne01(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("forno");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        if(empid!=null){
            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC04");
            dto.setAppValue(AppValue.B.ordinal());
            dto.setContent(allContent);
            CommonResult nextProject =formProjectAcceptanceService.updateAcceptProject(dto,empid) ;
            AjaxResponse.write(response, "text", "success");

        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    /**
     * 单个审核同意（科研管理002）
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_kygl01_approve_proyes")
    public void approvalProjectOKOne01(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("prono");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        if(empid!=null){
            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC04");
            dto.setAppValue(AppValue.Y.ordinal());
            dto.setContent(allContent);

            CommonResult nextProject =formProjectAcceptanceService.updateAcceptProject(dto,empid) ;
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    //***************************主管领导审批**********************************
    @RequestMapping("syy_kg_lc05_view26_zg")
    public ModelAndView syy_kg_lc05_view26(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        String appRole[]=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            if(acceptance.getExpertSuggestion()!=null){
                appRole=acceptance.getExpertSuggestion().split("@");
                model.addAttribute("approle",appRole);
            }
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        model.addAttribute("projectNo_hidden",acceptance.getProjectNo());

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view26", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view26", model);//审核 或 补充数据
    }
    //主管领导审核
    @RequestMapping(value = "kg_lc05_approve26_zg")
    @ResponseBody
    public CommonResult approves26(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo=request.getParameter("projectNo_hidden");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);

        if(dto.getAppValue()==2){
            scienticRPKUService.reset(projectNo,null,null,"0",null,null);
        }

        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;
    }

    //***************************科委主任**********************************
    @RequestMapping("syy_kg_lc05_view26_kw")
    public ModelAndView syy_kg_lc05_view27(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        String appRole[]=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            if(acceptance.getExpertSuggestion()!=null){
                appRole=acceptance.getExpertSuggestion().split("@");
                model.addAttribute("approle",appRole);
            }
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        model.addAttribute("projectNo_hidden",acceptance.getProjectNo());

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view27", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view27", model);//审核 或 补充数据
    }
    //科委主任审核
    @RequestMapping(value = "kg_lc05_approve26_kw")
    @ResponseBody
    public CommonResult approves27(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo_hidden=request.getParameter("projectNo_hidden");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        if(dto.getAppValue()==2){
            scienticRPKUService.reset(projectNo_hidden,null,null,"0",null,null);
        }
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);

        return result;
    }

    //***************************科管科**********************************
    @RequestMapping("syy_kg_lc05_view26_kgk")
    public ModelAndView syy_kg_lc05_view28(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        String appRole[]=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            if(acceptance.getExpertSuggestion()!=null){
                appRole=acceptance.getExpertSuggestion().split("@");
                model.addAttribute("approle",appRole);
            }
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);
        model.addAttribute("project_no",projectNo);
        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        model.addAttribute("projectNo_hidden",acceptance.getProjectNo());

        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view28", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view28", model);//审核 或 补充数据
    }
    //科管科审核
    @RequestMapping(value = "kg_lc05_approve26_kgk")
    @ResponseBody
    public CommonResult approves28(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo_hidden=request.getParameter("projectNo_hidden");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectNo=request.getParameter("projectNo");

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);
        if(dto.getAppValue()==2){
            scienticRPKUService.reset(projectNo_hidden,null,null,"0",null,null);
        }
        if(dto.getAppValue()==1){
            scienticRPKUService.update(projectNo,null,null,"2",null,null);
        }

        return result;
    }

    //*************************专家汇总审批*************************
    //中心科管人员汇总审批页面
    @ResponseBody
    @RequestMapping("syy_accept_lot_zj")
    public ModelAndView syy_accept_lot_zj(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        FormProjectAcceptanceDto formProjectAcceptanceDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)){
            formProjectAcceptanceDto=formProjectAcceptanceService.getDtoByFormNo(Long.parseLong(id));
        }

        FormProjectAcceptanceDto acceptance=null;
       // model.addAttribute("up_formNo",acceptance.getFormNo());

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formProjectAcceptanceDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc05_kg_allzj", model);
    }
    //列表页面
    //验收流程数据列表
    @RequestMapping(value = "ajax_kg05_lotTplzj")
    @ResponseBody
    public void ajax_kg05_lotTplzj(FormAcceptanceCriteria criteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        EmpSessionDto userInfo=us.getCurrentUser(request);
        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        //String majorType=request.getParameter("specType");
        String majorType=URI.deURI(request.getParameter("specType"));
        List<FormProjectAcceptanceDto>  formDelayPagedResult = formProjectAcceptanceService.getAcceptanceApproveszj(criteria,majorType,userInfo.getId());
        List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", formDelayPagedResult);
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //专家批量拒绝
    @RequestMapping(value = "ajax_approval_reject_zj")
    public void ajax_approval_reject_zj(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        /*String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.B.ordinal());
                dto.setContent(allContent);

                formProjectAcceptanceService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }*/
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List<String> listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(id);
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                String form_project[]=listid.get(i).split("@");
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                //dto.setFormNo((Long)listid.get(i));
                dto.setFormNo(Long.parseLong(form_project[0]));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);

                formProjectAcceptanceService.updateProject0(dto);
                scienticRPKUService.reset(form_project[1],null,null,"0",null,null);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //中心科管人员汇总审批同意
    @RequestMapping(value = "ajax_approval_approvezj")
    public void approvalProjectOK0zj(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //String emps=request.getParameter("up_role");
        String emps= URI.deURI(request.getParameter("up_role"));
        String up_formNo=request.getParameter("up_formNo");
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String proficientOpinion =URI.deURI(request.getParameter("proficientOpinion"));
        String allContent = URI.deURI(request.getParameter("allContent100"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if (listid.size() > 0) {
            for (int i = 0; i < listid.size(); i++) {
                formProjectAcceptanceService.getDtoByFormNo1((Long) listid.get(i),proficientOpinion);
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                FormProjectAcceptanceDto formProjectAcceptanceDto=formProjectAcceptanceService.getDtoByFormNo((Long) listid.get(i));
                formProjectAcceptanceService.getDtoByFormNo1((Long) listid.get(i),proficientOpinion);
                CommonResult result =formProjectAcceptanceService.updateProject1(dto,formProjectAcceptanceDto);

                //CommonResult nextProject = formProjectAcceptanceService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        } else {
            AjaxResponse.write(response, "text", "error");
        }
    }

    //*****************************科管科长审批********************************
    @RequestMapping("syy_kg_lc05_view22kgkz")
    public ModelAndView syy_kg_lc05_view27kgkz(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        String appRole[]=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            if(acceptance.getExpertSuggestion()!=null){
                appRole=acceptance.getExpertSuggestion().split("@");
                model.addAttribute("approle",appRole);
            }
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        model.addAttribute("projectNo_hidden",acceptance.getProjectNo());

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view22kgkz", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view22kgkz", model);//审核 或 补充数据
    }
    //科管科长审批审核
    @RequestMapping(value = "kg_lc05_approve22kgkz")
    @ResponseBody
    public CommonResult approves27kgkz(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo_hidden=request.getParameter("projectNo_hidden");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        if(dto.getAppValue()==2){
            scienticRPKUService.reset(projectNo_hidden,null,null,"0",null,null);
        }
        //CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);
        CommonResult result =formProjectAcceptanceService.updateProject001(dto);
        return result;
    }


    //*****************************科管科长001审批********************************
    @RequestMapping("syy_kg_lc05_view22kgkz1")
    public ModelAndView syy_kg_lc05_view27kgkz1(Model model, HttpServletRequest request) {

        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("formKind", formKind);
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto1 = headerService.getHeaderByFormNo(Long.parseLong(formNo));
        model.addAttribute("header",dto1);
        FormProjectAcceptanceDto acceptance=null;
        String appRole[]=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            acceptance = formProjectAcceptanceService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", acceptance);
            if(acceptance.getExpertSuggestion()!=null){
                appRole=acceptance.getExpertSuggestion().split("@");
                model.addAttribute("approle",appRole);
            }
        }

        String projectNo=acceptance.getProjectNo();
        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        model.addAttribute("projectNo_hidden",acceptance.getProjectNo());

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        List<FormProjectApply> queryProject = formProjectAcceptanceService.queryProject();
        model.addAttribute("projectName", queryProject);
        model.addAttribute("viewOnly",viewOnly);
        //当前项目信息
        //当前项目信息
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc05_view22kgkz1", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc05_view22kgkz1", model);//审核 或 补充数据
    }
    //科管科长审批审核
    @RequestMapping(value = "kg_lc05_approve22kgkz1")
    @ResponseBody
    public CommonResult approves27kgkz1(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto,HttpServletRequest request,HttpServletResponse response){
        String projectNo_hidden=request.getParameter("projectNo_hidden");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String projectPlanInfoTxt = request.getParameter("projectPlanInfoTxt");
        String assumeCompany = request.getParameter("assumeCompany");
        String joinComopany = request.getParameter("joinComopany");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String selfEvaluate = request.getParameter("selfEvaluate");
        formProjectAcceptanceDto.setProjectPlanInfoTxt(projectPlanInfoTxt);
        formProjectAcceptanceDto.setAssumeCompany(assumeCompany);
        formProjectAcceptanceDto.setJoinComopany(joinComopany);
        formProjectAcceptanceDto.setBeginTime(beginTime);
        formProjectAcceptanceDto.setEndTime(endTime);
        formProjectAcceptanceDto.setSelfEvaluate(selfEvaluate);
        if(dto.getAppValue()==2){
            scienticRPKUService.reset(projectNo_hidden,null,null,"0",null,null);
        }
        //CommonResult result =formProjectAcceptanceService.updateProject(dto,formProjectAcceptanceDto);
        CommonResult result =formProjectAcceptanceService.updateProject001(dto);
        return result;
    }
    @RequestMapping("kg_lc05_infomation")
    public ModelAndView getFormProjectAcceptanceProjectNo(HttpServletRequest request,HttpServletResponse response,Model model){
        String projectNo=request.getParameter("infomation_projectNo");
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormProjectAcceptance formProjectAcceptance=formProjectAcceptanceService.getAcceptance(projectNo,formNo);
        model.addAttribute("beans", formProjectAcceptance);
        return view(layout, "projectInfo/syy_kg_lc05_infomation", model);
    }

}