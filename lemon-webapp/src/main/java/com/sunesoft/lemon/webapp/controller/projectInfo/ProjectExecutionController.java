package com.sunesoft.lemon.webapp.controller.projectInfo;

import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectApplyService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectExecutionService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.criteria.FormProjectExecutoryCriteria;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectExecutoryDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecution;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zy on 2016/8/25.
 * edit by pxj on 2016/9/6
 */

@Controller
public class ProjectExecutionController  extends Layout {
    @Autowired
    FormProjectExecutionService formProjectExecutionService;
    @Autowired
    DeptmentService deptmentService;

    @Autowired
    FormHeaderService headerService;

    @Autowired
    FileService fileService;

    @Autowired
    FormProjectApplyService formProjectApplyService;

    @Autowired
    ScientificResearchProjectService scientificResearchProjectService;

    @Autowired
    UserSession us;
    @Autowired
    FormListService formListService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    ScienticRPKUService scienticRPKUService;

    @RequestMapping("syy_kg_lc03_a")
    public ModelAndView syy_kg_lc03_a(Model model, HttpServletRequest request) {
        FormListDto dto =formListService.getFormListInfo("SYY_KG_LC03");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        String formNo = request.getParameter("formNo");
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        FormProjectExecution formLeave=null;
        ScientificRPKU scientificRPKU=null;
        /*if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            formLeave = formProjectExecutionService.getFormByFormNo((Long.parseLong(formNo)));
            if(!StringUtils.isNullOrWhiteSpace(formLeave.getProjectNo())){
                scientificRPKU=scienticRPKUService.getIdByProjectNo(formLeave.getProjectNo());
            }
        }*/
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            formLeave = formProjectExecutionService.getFormByFormNo1((Long.parseLong(formNo)));
            if(!StringUtils.isNullOrWhiteSpace(formLeave.getProjectNo())){
                scientificRPKU=scienticRPKUService.getIdByProjectNo(formLeave.getProjectNo());
            }
            model.addAttribute("formNo", formLeave.getFormNo());
        }
        model.addAttribute("beans", formLeave);
        model.addAttribute("scientificRPKU", scientificRPKU);

        List<ScientificRPKU> queryProject=scienticRPKUService.getAllScientificKu();
        model.addAttribute("projectName", queryProject);
        //当前项目信息

        return view(applyLayout, "projectInfo/syy_kg_lc03_a", model);
    }

    //获取选中的id
    @RequestMapping("ajax_executionId")
    public void ajax_executionId(Model model, HttpServletRequest request, HttpServletResponse response) {
        String projectNo = request.getParameter("projectNoSel1");
        if (!StringUtils.isNullOrWhiteSpace(projectNo)) {
            ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto(projectNo);
            formcon.setBeginTime1(formcon.getBeginTime1().substring(0,10));
            formcon.setEndTime1(formcon.getEndTime1().substring(0,10));
            String json = JsonHelper.toJson(formcon);
            AjaxResponse.write(response, json);
        }
    }

    @RequestMapping(value = "ajax_add_update_lc03_apply", method = RequestMethod.POST)
    @ResponseBody
    public String addOrupdateTrain(FormProjectExecutoryDto formProjectExecutoryDto, HttpServletRequest request) {
        String projectNo = request.getParameter("projectNoSel1");
        CommonResult result = formProjectExecutionService.addByDto(formProjectExecutoryDto);
        formProjectExecutionService.submitForm(result.getId(),formProjectExecutoryDto.getFormKind());
        //CommonResult result = formProjectExecutionService.submitForm(commonResult.getId(), formProjectExecutoryDto.getFormKind());
        scienticRPKUService.updateByProjectApply(projectNo,null,null,null,null,"1");

        //上传文件
        EmpSessionDto empSessionDto = us.getCurrentUser(request);

        Long formNo=result.getId();

        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("projectFile");

        for (MultipartFile myfile : myfiles) {
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

                if(!StringUtils.isNullOrWhiteSpace(fileId)) {
                    formProjectExecutoryDto.setFileId(fileId);
                    formProjectExecutoryDto.setFileName(fileName);
                }
                result= formProjectExecutionService.uploadProjectFile(formNo,fileId, fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return JsonHelper.toJson(result);
    }

    //第一步审核页面
    @RequestMapping(value = "syy_kg_lc03_view1")
    public ModelAndView formProjectExcetionView(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header", dto);
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        //model.addAttribute("formNoEx",formProjectApplyDto.getFormNo());

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view1", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view1", model);//审核 或 补充数据

    }

    //第一步审核保存
    @RequestMapping(value = "kg_lc03_approve1")
    @ResponseBody
    public CommonResult approves1(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){

        String projectNo=request.getParameter("projectNo");
        Long id=Long.parseLong(request.getParameter("id"));

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject(dto);

        if(dto.getAppValue()==3){
            //   Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");

        }

        return result;
    }

    //第2步审核页面
    @RequestMapping(value = "syy_kg_lc03_view2")
    public ModelAndView formProjectExcetionView2(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header", dto);
        //
        model.addAttribute("clStep", dto.getClStep());
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view2", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view2", model);//审核 或 补充数据

    }

    //第2步审核保存
    @RequestMapping(value = "kg_lc03_approve2")
    @ResponseBody
    public CommonResult approves2(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        String instructions = request.getParameter("instructions");
        String clStep = request.getParameter("clStep");
        String formNo = request.getParameter("formNo");
        formProjectExecutionService.updateProjectExecutionById(Long.parseLong(formNo),instructions,clStep);
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject(dto);

        return result;
    }
    //第3步审核页面
    @RequestMapping(value = "syy_kg_lc03_view3")
    public ModelAndView formProjectExcetionView3(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header", dto);
        model.addAttribute("clStep", dto.getClStep());
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view3", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view3", model);//审核 或 补充数据

    }

    //第3步审核保存
    @RequestMapping(value = "kg_lc03_approve3")
    @ResponseBody
    public CommonResult approves3(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject(dto);
        String projectNo=request.getParameter("projectNo");
        if(dto.getAppValue()==2){
            Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }
        return result;
    }

    //第4步审核页面
    @RequestMapping(value = "syy_kg_lc03_view4")
    public ModelAndView formProjectExcetionView4(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
      //  List<EmpDto> empDtos = employeeService.getAllEmps();
        model.addAttribute("header", dto);
        model.addAttribute("clStep", dto.getClStep());
       // model.addAttribute("emp",empDtos);
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

        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0, 10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("projectNo",projectNo);

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view4", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view4", model);//审核 或 补充数据

    }

    //第4步审核保存
    @RequestMapping(value = "kg_lc03_approve4")
    @ResponseBody
    public CommonResult approves4(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        //专家id
        String zjid = request.getParameter("hidid");
        String clStep = request.getParameter("clStep");
        String projectNo=request.getParameter("projectNo");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject1(dto,zjid,clStep);
        if(dto.getAppValue()==2){
            Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }
        return result;
    }

    //第5步审核页面
    @RequestMapping(value = "syy_kg_lc03_view5_kgk")
    public ModelAndView formProjectExcetionView5kgk(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header", dto);
        model.addAttribute("clStep", dto.getClStep());
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view5_kgk", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view5_kgk", model);//审核 或 补充数据

    }

    //第5步审核保存
    @RequestMapping(value = "kg_lc03_approve5_kgk")
    @ResponseBody
    public CommonResult approves5kgk(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        String clStep = request.getParameter("clStep");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject2(dto,clStep);
        String projectNo=request.getParameter("projectNo");
        if(dto.getAppValue()==2){
            Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }
        return result;
    }


    //第6步审核页面
    @RequestMapping(value = "syy_kg_lc03_view5")
    public ModelAndView formProjectExcetionView5(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header", dto);
        model.addAttribute("clStep", dto.getClStep());
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("projectNo",projectNo);

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view5", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view5", model);//审核 或 补充数据

    }

    //第6步审核保存
    @RequestMapping(value = "kg_lc03_approve5")
    @ResponseBody
    public CommonResult approves5(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        String proficientOpinion = request.getParameter("proficientOpinion");
        String clStep = request.getParameter("clStep");
        String formNo = request.getParameter("formNo");
        formProjectExecutionService.updateProjectExecutionById(Long.parseLong(formNo),proficientOpinion,clStep);
        String projectNo=request.getParameter("projectNo");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject(dto);
        if(dto.getAppValue()==2){
            Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }
        return result;
    }

    //第7步审核页面
    @RequestMapping(value = "syy_kg_lc03_view6")
    public ModelAndView formProjectExcetionView6(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
       /* List<EmpDto> empDtos = employeeService.getAllEmps();*/
        model.addAttribute("header", dto);
        model.addAttribute("clStep", dto.getClStep());
       /* model.addAttribute("emp",empDtos);*/

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

        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("projectNo",projectNo);

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view6", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view6", model);//审核 或 补充数据

    }

    //第7步审核保存
    @RequestMapping(value = "kg_lc03_approve6")
    @ResponseBody
    public CommonResult approves6(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        String projectNo=request.getParameter("projectNo");
        String clStep = request.getParameter("clStep");
        String zgid=request.getParameter("hidids");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject1(dto,zgid,clStep);
        if(dto.getAppValue()==2){
            Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }
        return result;
    }

    //第8步审核页面
    @RequestMapping(value = "syy_kg_lc03_view8_kgk")
    public ModelAndView formProjectExcetionView8kgk(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header", dto);
        model.addAttribute("clStep", dto.getClStep());
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view8_kgk", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view8_kgk", model);//审核 或 补充数据

    }

    //第8步审核保存
    @RequestMapping(value = "kg_lc03_approve8_kgk")
    @ResponseBody
    public CommonResult approves8kgk(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        String clStep = request.getParameter("clStep");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject2(dto,clStep);
        String projectNo=request.getParameter("projectNo");
        if(dto.getAppValue()==2){
            Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }
        return result;
    }


    //第9步审核页面
    @RequestMapping(value = "syy_kg_lc03_view7")
    public ModelAndView formProjectExcetionView7(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header", dto);
        model.addAttribute("clStep", dto.getClStep());
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("projectNo",projectNo);

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view7", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view7", model);//审核 或 补充数据
    }

    //第9步审核保存
    @RequestMapping(value = "kg_lc03_approve7")
    @ResponseBody
    public CommonResult approves7(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        String projectNo=request.getParameter("projectNo");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject(dto);
        if(dto.getAppValue()==2){
            Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }

        return result;
    }
    //第10步审核页面
    @RequestMapping(value = "syy_kg_lc03_view8")
    public ModelAndView formProjectExcetionView8(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header", dto);
        model.addAttribute("clStep", dto.getClStep());
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);

        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("projectNo",projectNo);

        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view8", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view8", model);//审核 或 补充数据

    }

    //第10步审核保存
    @RequestMapping(value = "kg_lc03_approve8")
    @ResponseBody
    public CommonResult approves8(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        String projectNo=request.getParameter("projectNo");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject(dto);
        if(dto.getAppValue()==2){
            Long l=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }

        return result;
    }

    //第11步审核页面
    @RequestMapping(value = "syy_kg_lc03_view9")
    public ModelAndView formProjectExcetionView9(HttpServletRequest request, Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("clStep", dto.getClStep());
        model.addAttribute("header", dto);
        //end
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);

     // FormProjectExecution f = formProjectExecutionService.getFormByFormNo1(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto);
        model.addAttribute("viewOnly", viewOnly);

        String projectNo=formProjectExecutoryDto.getProjectNo();

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(projectNo);


        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",sdf.format(formcon.getBeginTime()).substring(0,10));/*formProjectApplyDto.getBeginTime().substring(0,11)*/
        model.addAttribute("endTime",sdf.format(formcon.getEndTime()).substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("projectNo",projectNo);
        if (viewOnly.equals("true")) { //只查看
            return view(formViewLayout, "projectInfo/syy_kg_lc03_view9", model);
        }
        return view(formLayout, "projectInfo/syy_kg_lc03_view9", model);//审核 或 补充数据

    }

    @RequestMapping(value = "/file_reload_data",method = RequestMethod.POST)
    public String file_reload_data(HttpServletRequest request,HttpServletResponse response ,Model model){
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        //获取表单数据
        FormProjectExecutoryDto formProjectExecutoryDto = formProjectExecutionService.getFormByFormNo(formNo);
        // FormProjectExecution f = formProjectExecutionService.getFormByFormNo1(formNo);
        model.addAttribute("beandto", formProjectExecutoryDto.getExecutionFileList());
        return "projectInfo/actualizeFileUpload";//审核 或 补充数据
    }



    //第11步审核保存
    @RequestMapping(value = "kg_lc03_approve9")
    @ResponseBody
    public CommonResult approves9(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response){
        String projectNo=request.getParameter("projectNo");
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result =formProjectExecutionService.nextProject(dto);

        Long l=scienticRPKUService.update(projectNo,null,null,null,null,"2");
        if(dto.getAppValue()==2){
            Long l2=scienticRPKUService.reset(projectNo,null,null,null,null,"0");
            //     formProjectExecutionService.updateFormProjectExecutionById(id);
        }
        return result;
    }

    //群审核审核页面
    @RequestMapping(value = "syy_kg03_lot")
    public ModelAndView syy_kg03_lot(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String clStep = request.getParameter("clStep");

        FormProjectExecutoryDto formProjectExecutoryDto =  formProjectExecutionService.getFormProjectExecution(Long.parseLong(id));
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC03");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formProjectExecutoryDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        model.addAttribute("clStep", clStep);
        return view(applyLayout, "projectInfo/syy_kg_lc03_a_lot", model);
    }

    //群审批审批 list
    @RequestMapping(value ="ajax_kg03_lotTpl")
    @ResponseBody
    public void ajax_kg03_lotTpl(FormProjectExecutoryCriteria formProjectExecutoryCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        EmpSessionDto userInfo = us.getCurrentUser(request);
        List<FormProjectExecutoryDto> projectDtoPagedResult=null;
        String specialtyType=URI.deURI(request.getParameter("specialtyType"));
        String clStep = request.getParameter("clStep");
        formProjectExecutoryCriteria.setSpecialtyType(specialtyType);
        projectDtoPagedResult = formProjectExecutionService.getProjectApproves3(formProjectExecutoryCriteria, Integer.parseInt(clStep),userInfo.getId());

        Map<String, Object> jsonmap = new HashMap<String, Object>();
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
        jsonmap.put("empInfos",multiSelectUserWithDepts);
        jsonmap.put("sicent", projectDtoPagedResult);
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("clStep",clStep);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    /**
     * 群审核同意
     */
    @RequestMapping(value = "syy3_lotProjectYES")
    @ResponseBody
    public void syy3_lotProjectYES(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String projectNo = request.getParameter("projectNo");
        String clStep = request.getParameter("clStep");
        //专家
        String zjid = request.getParameter("zjid");
        //主管
        String zgid = request.getParameter("zgid");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String instructions = URI.deURI(request.getParameter("instructions"));
        String proficientOpinion =URI.deURI(request.getParameter("proficientOpinion"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                //添加批示
               /* if(clStep.equals("2")) {
                    formProjectExecutionService.updateProjectExecutionById((Long) listid.get(i), instructions,clStep);
                }*/
                //添加专家意见
                if(clStep.equals("5")) {
                    formProjectExecutionService.updateProjectExecutionById((Long) listid.get(i), proficientOpinion,clStep);
                }
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC03");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = formProjectExecutionService.updateProject(dto,zjid,zgid,clStep);
                //更改信息库状态
                if(clStep.equals("11")) {
                    Long l=scienticRPKUService.update(projectNo,null,null,null,null,"2");
                }
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    @RequestMapping(value = "syy_lotProjectNO")
    public void approvalProjectNO3(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String projectNo = request.getParameter("projectNo");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String clstep = request.getParameter("clstep");
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
                dto.setFormKind("SYY_KG_LC03");
                if(Long.parseLong(clstep)==2) {
                    dto.setAppValue(AppValue.B.ordinal());
                }else{
                    dto.setAppValue(AppValue.N.ordinal());
                }
                dto.setContent(allContent);
                CommonResult nextProject = formProjectExecutionService.reProject(dto);
                //更改信息库状态
                if(Long.parseLong(clstep)>=3) {
                    Long l = scienticRPKUService.reset(projectNo, null, null, null, null, "0");
                }
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    @RequestMapping(value = "kg_lc03_executionUpload")
    public void uploadResultFile(HttpServletRequest request,HttpServletResponse response) {
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String formNo = request.getParameter("formNo");

        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName");
        CommonResult result = null;
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false, "请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return;
            } else {
                try {
                    String fileName1 = myfile.getOriginalFilename();
                    String extension = fileName1.substring(fileName1.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto = new FileInfoDto();
                    fileInfoDto.setFileName(fileName1);
                    fileInfoDto.setIsPublic(false);
                    fileInfoDto.setDeptId(empSessionDto.getDeptId());
                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setInputStream(myfile.getInputStream());
                    String fileId1 = fileService.upload(fileInfoDto);
                    result = formProjectExecutionService.uploadProjectFile2(Long.parseLong(formNo), fileId1, fileName1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        AjaxResponse.write(response, JsonHelper.toJson(result));

    }

    @RequestMapping(value = "kg_lc03_executionUpload_lot")
    public void kg_lc03_executionUpload_lot(HttpServletRequest request,HttpServletResponse response) {
        CommonResult result = null;
        String ids = request.getParameter("ids");
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto empSessionDto = us.getCurrentUser(request);
                String formNo = listid.get(i).toString();

                List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName");

                for (MultipartFile myfile : myfiles) {
                    if (myfile.isEmpty()) {
                        result = new CommonResult(false, "请选择要上传的文件！");
                        AjaxResponse.write(response, JsonHelper.toJson(result));
                        return;
                    } else {
                        try {
                            String fileName1 = myfile.getOriginalFilename();
                            String extension = fileName1.substring(fileName1.lastIndexOf(".") + 1);
                            FileInfoDto fileInfoDto = new FileInfoDto();
                            fileInfoDto.setFileName(fileName1);
                            fileInfoDto.setIsPublic(false);
                            fileInfoDto.setDeptId(empSessionDto.getDeptId());
                            fileInfoDto.setExtensions(extension);
                            fileInfoDto.setInputStream(myfile.getInputStream());
                            String fileId1 = fileService.upload(fileInfoDto);
                            result = formProjectExecutionService.uploadProjectFile2(Long.parseLong(formNo), fileId1, fileName1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        AjaxResponse.write(response, JsonHelper.toJson(result));

    }


}