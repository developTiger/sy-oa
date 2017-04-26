package com.sunesoft.lemon.webapp.controller.delay;

import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.delayflow.application.FormDelayApplyService;
import com.sunesoft.lemon.deanery.delayflow.application.dto.FormDelayApplyDto;
import com.sunesoft.lemon.deanery.delayflow.criteria.FormDelayCriteria;
import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApply;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverApplyDto;
import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectAcceptanceService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectApplyService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.scientificRPKU.ScienticRPKUService;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
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
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.demo.MultiSelectUserWithDept;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by swb on 2016/8/25.
 * edit by pxj on 2016/9/6
 */
@Controller
public class ProjectDelayController extends Layout{
    @Autowired
    FormProjectAcceptanceService formProjectAcceptanceService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    FormDelayApplyService formDelayApplyService;
    @Autowired
    FormHeaderService headerService;
    @Autowired
    FormProjectApplyService formProjectApplyService;
    @Autowired
    UserSession us;

    @Autowired
    FileService fileService;
    @Autowired
    FormListService formListService;

    @Autowired
    ScientificResearchProjectService scientificResearchProjectService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ScienticRPKUService scienticRPKUService;

    @RequestMapping("syy_kg_lc04_a")
    public ModelAndView syy_kg_lc04(Model model,HttpServletRequest request){
        FormListDto dto =formListService.getFormListInfo("SYY_KG_LC04");

        String formNo=request.getParameter("formNo");
        FormDelayApplyDto formDelayApplyDto=null;
        ScientificRPKU scientificRPKU=null;
        if(formNo!=null&&formNo!=""){
            formDelayApplyDto=formDelayApplyService.getFormByFormNo(Long.parseLong(formNo));
            if(formDelayApplyDto.getProjectNo()!=null&&formDelayApplyDto.getProjectNo()!=""){
                scientificRPKU=scienticRPKUService.getIdByProjectNo(formDelayApplyDto.getProjectNo());
            }
            model.addAttribute("formNo",formDelayApplyDto.getFormNo());
            model.addAttribute("majorType",formDelayApplyDto.getMajorType());
        }

        model.addAttribute("formDelayApplyDto",formDelayApplyDto);
        if(formDelayApplyDto!=null) {
            model.addAttribute("delaytime", DateHelper.formatDate(formDelayApplyDto.getDelayTimes(), "yyyy-MM-dd"));
        }
        model.addAttribute("scientificRPKU",scientificRPKU);

        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
        List<ScientificRPKU> queryProject=scienticRPKUService.getAllScientificKu2();

        model.addAttribute("projectName", queryProject);
        return view(applyLayout,"delay_flow/syy_kg_lc04_a",model);
    }

    @RequestMapping(value = "ajax_add_update_lc04_apply")
    @ResponseBody
    public String addOrUpdateDelay(HttpServletRequest request,FormDelayApplyDto dto,HttpServletResponse response) {

        String projectNo=request.getParameter("projectNo");

        dto.setDelayTimes(DateHelper.parse(request.getParameter("delayTime"),"yyyy-MM-dd"));
        String type_zy=request.getParameter("type_zy");
        dto.setMajorType(type_zy);
        CommonResult result=formDelayApplyService.addByDto(dto);
        formDelayApplyService.submitForm(result.getId(),dto.getFormKind());

        /*ScientificRPKU scientificRPKU=scienticRPKUService.getIdByProjectNo(projectNo);
        scientificRPKU.setEndTime(dto.getDelayTimes());
        scienticRPKUService.addScientificResearchProject(scientificRPKU);*/

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
                    dto.setFileId(fileId);
                    dto.setFileName(fileName);
                }
                result= formDelayApplyService.uploadProjectFile(formNo,fileId, fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //AjaxResponse.write(response, JsonHelper.toJson(result));
        return JsonHelper.toJson(result);
    }

    //T1审核
    @RequestMapping(value = "syy_kg_lc04_v")
    public ModelAndView FormDelayApplyView(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String formKind=request.getParameter("formKind");
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        List<FormDelayApply> queryDelay=formDelayApplyService.queryDelay();
        model.addAttribute("queryDelay",queryDelay);
        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formNo);
        formDelayApplyDto.setDelayTime(DateHelper.formatDate(formDelayApplyDto.getDelayTimes(),"yyyy-MM-dd"));

        String projectNo=formDelayApplyDto.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        //model.addAttribute("scId",scientificResearchProjectDto.getId());
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
        formcon.setLeaderName(empDto.getName());

        model.addAttribute("formDelayApplyDto",formDelayApplyDto);
        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",formcon.getBeginTime1().substring(0,10));
        model.addAttribute("endTime",formcon.getEndTime1().substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("viewOnly",viewOnly);
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){
            return view(formViewLayout,  "delay_flow/syy_kg_lc04_v", model);
        }
        return view(formLayout,"delay_flow/syy_kg_lc04_v",model);
    }

    @RequestMapping(value = "syy_kg_lc04_approve")
    @ResponseBody
    public CommonResult delayApprove(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String opinion=request.getParameter("opinion");

        CommonResult result=formDelayApplyService.updateDelayForm1(dto,opinion);

        return result;
    }
    //T2审核页面
    @RequestMapping(value = "syy_kg_lc04_v1")
    public ModelAndView FormDelayApplyView1(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String formKind=request.getParameter("formKind");
        String viewOnly=request.getParameter("viewOnly");

        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);

        List<FormDelayApply> queryDelay=formDelayApplyService.queryDelay();
        model.addAttribute("queryDelay",queryDelay);

        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formNo);
        formDelayApplyDto.setDelayTime(DateHelper.formatDate(formDelayApplyDto.getDelayTimes(),"yyyy-MM-dd"));

        String projectNo=formDelayApplyDto.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
        formcon.setLeaderName(empDto.getName());

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",formcon.getBeginTime1().substring(0,10));
        model.addAttribute("endTime",formcon.getEndTime1().substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        model.addAttribute("formDelayApplyDto1",formDelayApplyDto);
        model.addAttribute("viewOnly",viewOnly);

        if(viewOnly.equals("true")){
            return view(formViewLayout,  "delay_flow/syy_kg_lc04_v1", model);
        }
        return view(formLayout,"delay_flow/syy_kg_lc04_v1",model);
    }
    //T2审核保存
    @RequestMapping(value = "syy_kg_lc04_v1_approve")
    @ResponseBody
    public CommonResult delayV2Approve(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String leaderWord=request.getParameter("leaderWord");

        CommonResult result=formDelayApplyService.updateDelayForm2(dto,leaderWord);

        return result;
    }
    //T3审核页面
    @RequestMapping(value = "syy_kg_lc04_v2")
    public ModelAndView FormDelayApplyView3(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String formKind=request.getParameter("formKind");
        String viewOnly=request.getParameter("viewOnly");

        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);

        List<FormDelayApply> queryDelay=formDelayApplyService.queryDelay();
        model.addAttribute("queryDelay",queryDelay);

        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formNo);
        formDelayApplyDto.setDelayTime(DateHelper.formatDate(formDelayApplyDto.getDelayTimes(),"yyyy-MM-dd"));

        String projectNo=formDelayApplyDto.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        model.addAttribute("emp_out",empDtos1);

        EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
        formcon.setLeaderName(empDto.getName());

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

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",formcon.getBeginTime1().substring(0,10));
        model.addAttribute("endTime",formcon.getEndTime1().substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        model.addAttribute("formDelayApplyDto2",formDelayApplyDto);
        model.addAttribute("viewOnly",viewOnly);

        if(viewOnly.equals("true")){
            return view(formViewLayout,  "delay_flow/syy_kg_lc04_v2", model);
        }
        return view(formLayout,"delay_flow/syy_kg_lc04_v2",model);
    }
    //T3审核保存
    @RequestMapping(value = "syy_kg_lc04_v2_approve")
    @ResponseBody
    public CommonResult delayV3Approve(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids=request.getParameter("formNo");
        String empid=request.getParameter("hidid");

        if(empid!=null){
            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC04");
            if(dto.getAppValue()==2){
                dto.setAppValue(AppValue.N.ordinal());
            }else if(dto.getAppValue()==1){
                dto.setAppValue(AppValue.Y.ordinal());
            }else if(dto.getAppValue()==3){
                dto.setAppValue(AppValue.B.ordinal());
            }
            //dto.setContent(allContent);


            CommonResult nextProject =formDelayApplyService.updateDelayProject(dto,empid);//formProjectAcceptanceService.updateAcceptProject(dto,empid) ;
            return nextProject;
            //AjaxResponse.write(response, "text", "success");
        }else{
            //AjaxResponse.write(response, "text", "error");
            return null;
        }
        /*EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String opinion2=request.getParameter("opinion2");
        CommonResult result=formDelayApplyService.updateDelayForm3(dto,opinion2);

        return result;*/
    }
    //T4审核页面
    @RequestMapping(value = "syy_kg_lc04_v3")
    public ModelAndView syy_kg_lc04_v3(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String formKind=request.getParameter("formKind");
        String viewOnly=request.getParameter("viewOnly");

        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);

        List<FormDelayApply> queryDelay=formDelayApplyService.queryDelay();
        model.addAttribute("queryDelay",queryDelay);

        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formNo);
        formDelayApplyDto.setDelayTime(DateHelper.formatDate(formDelayApplyDto.getDelayTimes(),"yyyy-MM-dd"));

        String projectNo=formDelayApplyDto.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
        formcon.setLeaderName(empDto.getName());

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",formcon.getBeginTime1().substring(0,10));
        model.addAttribute("endTime",formcon.getEndTime1().substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());
        model.addAttribute("projectNo",projectNo);

        model.addAttribute("formDelayApplyDto2",formDelayApplyDto);
        model.addAttribute("viewOnly",viewOnly);

        if(viewOnly.equals("true")){
            return view(formViewLayout,  "delay_flow/syy_kg_lc04_v3", model);
        }
        return view(formLayout,"delay_flow/syy_kg_lc04_v3",model);
    }
    //T4审核保存
    @RequestMapping(value = "syy_kg_lc04_v3_approve")
    @ResponseBody
    public CommonResult syy_kg_lc04_v3_approve(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){

        String projectNo=request.getParameter("projectNo");
        String formNo=request.getParameter("formNo");
        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(Long.parseLong(formNo));
        ScientificRPKU scientificRPKU=scienticRPKUService.getIdByProjectNo(projectNo);
        scientificRPKU.setEndTime(formDelayApplyDto.getDelayTimes());
        scienticRPKUService.addScientificResearchProject(scientificRPKU);

        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String opinion2=request.getParameter("opinion2");
        CommonResult result=formDelayApplyService.updateDelayForm4(dto,opinion2);

        return result;
    }

    /**
     *
     * @param formNo 开题报流程的formNo
     * @return   开题报告项目名称
     */
    public String returnDelayName(String formNo){
        String  delayName="";
        FormProjectApplyDto formApply = formProjectApplyService.getFormByFormNo(Long.parseLong(formNo));
        delayName=formApply.getProjectName();
        return delayName;
    }




    //获取url
    //获取选中的id
    @RequestMapping("ajax_scientificId")
    public void ajax_scientificId(Model model, HttpServletRequest request, HttpServletResponse response) {
        String projectNoSel = request.getParameter("projectNoSel1");
        if (!StringUtils.isNullOrWhiteSpace(projectNoSel)) {
            ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNoSel);

            String beginTime=formcon.getBeginTime1().substring(0, 10);
            String endTime=formcon.getEndTime1().substring(0,10);
            formcon.setBeginTime1(beginTime);
            formcon.setEndTime1(endTime);

            EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
            formcon.setLeaderName(empDto.getName());

            String json = JsonHelper.toJson(formcon);
            AjaxResponse.write(response, json);
        }
    }

    //中心科管人员汇总审批审核页面
    @RequestMapping(value = "syy_kg_lc04_v2All")
    public ModelAndView FormDelayApplyView3All(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String formKind=request.getParameter("formKind");
        String viewOnly=request.getParameter("viewOnly");

        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);

        List<FormDelayApply> queryDelay=formDelayApplyService.queryDelay();
        model.addAttribute("queryDelay",queryDelay);

        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formNo);
        formDelayApplyDto.setDelayTime(DateHelper.formatDate(formDelayApplyDto.getDelayTimes(),"yyyy-MM-dd"));

        String projectNo=formDelayApplyDto.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
        formcon.setLeaderName(empDto.getName());

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",formcon.getBeginTime1().substring(0,10));
        model.addAttribute("endTime",formcon.getEndTime1().substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        model.addAttribute("formDelayApplyDto2",formDelayApplyDto);
        model.addAttribute("viewOnly",viewOnly);

        if(viewOnly.equals("true")){
            return view(formViewLayout,  "delay_flow/syy_kg_lc04_v2_all", model);
        }
        return view(formLayout,"delay_flow/syy_kg_lc04_v2_all",model);
    }

    //中心科管人员汇总审批审核
    @RequestMapping(value = "syy_kg_lc04_v2All_approve")
    @ResponseBody
    public CommonResult FormDelayApplyView3All(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result=formDelayApplyService.updateDelayFormAll1(dto);
        return result;
    }

    //科管科汇总审核
    @RequestMapping(value = "syy_kg_lc04_v2All01_approve")
    @ResponseBody
    public CommonResult FormDelayApplyView3All01(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result=formDelayApplyService.updateDelayFormAll2(dto);
        return result;
    }

    @RequestMapping("approve_all_show")
    public ModelAndView approve_all_show(Model model,HttpServletResponse response){
        List<FormDelayApply> list=formDelayApplyService.queryDelay();
        model.addAttribute("list",list);
        return view(layout,"delay_flow/approveAllDelay",model);
    }
    @RequestMapping("ajax_formDelay_query")
    public void ajax_formDelay_query(FormHeaderCriteria criteria, HttpServletRequest request, HttpServletResponse response){
        PagedResult<FormHeaderDto> list = formDelayApplyService.findFormPaged(criteria);
        String json = JsonHelper.toJson(list);
        AjaxResponse.write(response, json);
    }
    @RequestMapping("syy_show_delay_project")
    public void show_delay_project(HttpServletRequest request,HttpServletResponse response,Model model){
        String headerId=request.getParameter("formnumber");
        FormHeader formHeader=headerService.getHeaderById(Long.parseLong(headerId));
        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formHeader.getId());
        String json=JsonHelper.toJson(formDelayApplyDto);
        AjaxResponse.write(response,json);
    }

    //科管科汇总审批节点
    @RequestMapping("approve_chief_show")
    public ModelAndView approve_chief_show(Model model,HttpServletResponse response){
        return view(layout,"delay_flow/chiefApproveAll",model);
    }

    //查询本节点延迟申请list
    @RequestMapping("ajax_delayChief_query")
    public void ajax_delayChief_query(FormHeaderCriteria criteria, HttpServletRequest request, HttpServletResponse response){
        PagedResult<FormHeaderDto> list = formDelayApplyService.findFormPaged1(criteria);
        String json = JsonHelper.toJson(list);
        AjaxResponse.write(response, json);
    }

    //选择审批领导页面
    @RequestMapping(value = "_addChiefApprove")
    public ModelAndView editEmp(HttpServletRequest request,Model model){
        String formNo=request.getParameter("formNo");
        String formKind=request.getParameter("formKind");
        FormDelayApplyDto formDelayApplyDto=null;
        if(!StringUtils.isNullOrWhiteSpace(formNo)){
            formDelayApplyDto=formDelayApplyService.getFormByFormNo(Long.parseLong(formNo));
        }
        model.addAttribute("formDelayApplyDto",formDelayApplyDto);

        List<EmpDto> empDtos = employeeService.getAllEmps();
        List<Long> listId=new ArrayList<Long>();
        for(int i=0;i<empDtos.size();i++){
            Long id=empDtos.get(i).getLeaderId();
            listId.add(id);
        }
        for (int i = 0; i < listId.size(); i++)  //外循环是循环的次数
        {
            for (int j = listId.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
            {
                if (listId.get(i) == listId.get(j))
                {
                    listId.remove(j);
                }
            }
        }
        List<EmpDto> listEmpDto=new ArrayList<EmpDto>();
        for(int i=0;i<listId.size();i++){
            if(listId.get(i)!=null){
                EmpDto empDto=employeeService.getLeader(listId.get(i));
                listEmpDto.add(empDto);
            }
        }
        model.addAttribute("listEmpDto",listEmpDto);

        return view("delay_flow/_addChiefApprove",model);
    }

    //获取领导
    @RequestMapping("ajax_get_leaderIdValue")
    public String ajax_get_leaderIdValue(HttpServletRequest request,HttpServletResponse response,Model model){
        String leader_str=request.getParameter("sel_str");
        String formNo=request.getParameter("formNo");
        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(Long.parseLong(formNo));
        return "success";
    }

    //2016.12.28 流程更新
    //中心科管人员汇总审批页面
    @ResponseBody
    @RequestMapping("syy_scientific_lot0")
    public ModelAndView lotApprove0(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        FormDelayApplyDto formDelayApplyDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)){
            formDelayApplyDto=formDelayApplyService.getFormByFormNo(Long.parseLong(id));
        }
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC04");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formDelayApplyDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "delay_flow/syy_kg_lc04_kg_all0", model);
    }

    //延迟申请数据列表
    @RequestMapping(value = "ajax_kg04_lotTpl0")
    @ResponseBody
    public void ajax_projectLot0(FormDelayCriteria criteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String majorType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        List<FormDelayApplyDto>  formDelayPagedResult = formDelayApplyService.getDelayApproves3(criteria,majorType);
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
    @RequestMapping(value = "ajax_zxkg_reject")
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
                dto.setFormKind("SYY_KG_LC04");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);

                formDelayApplyService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
    //中心科管人员汇总审批同意
    @RequestMapping(value = "ajax_delay_approve")
    public void approvalProjectOK0(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
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
                dto.setFormKind("SYY_KG_LC04");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = formDelayApplyService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //科管科汇总审批页面
    @ResponseBody
    @RequestMapping("syy_chief_lot1")
    public ModelAndView lotApprove1(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        FormDelayApplyDto formDelayApplyDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)){
            formDelayApplyDto=formDelayApplyService.getFormByFormNo(Long.parseLong(id));
        }

        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        model.addAttribute("emp1",empDtos1);

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC04");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", formDelayApplyDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "delay_flow/syy_kg_lc04_kg_all1", model);
    }
    //延迟申请数据列表(科管科汇总)
    @RequestMapping(value = "ajax_kg04_lotTpl1")
    @ResponseBody
    public void ajax_projectLot1(FormDelayCriteria criteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String majorType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        List<FormDelayApplyDto>  formDelayPagedResult = formDelayApplyService.getDelayApproves4(criteria,majorType);
        List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", formDelayPagedResult);
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //群审批拒绝（科管科）
    @RequestMapping(value = "ajax_kgk_approve")
    public void approvalProjectNO(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
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
                dto.setFormKind("SYY_KG_LC04");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);
                //CommonResult result= formProjectApplyService.addPriceAndApprove(dto, 1D);
                CommonResult nextProject = formDelayApplyService.updateDelayProject(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
    /**
     * 单个审核拒绝（科管科）
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_kgk_approve_pro")
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
            CommonResult nextProject = formDelayApplyService.updateDelayProject(dto,empid);
            AjaxResponse.write(response, "text", "success");

        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }
    /**
     * 群审核同意
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_kgk_approve_yes")
    public void approvalProjectOK(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
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
                dto.setFormKind("SYY_KG_LC04");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = formDelayApplyService.updateDelayProject(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }
    /**
     * 单个审核同意（科管科）
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_kgk_approve_proyes")
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

            CommonResult nextProject =formDelayApplyService.updateDelayProject(dto,empid) ;
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    //************************第五步选择的领导审核******************************
    //T05审核页面
    @RequestMapping(value = "syy_kg_lc04_v05")
    public ModelAndView FormDelayApplyView5(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String formKind=request.getParameter("formKind");
        String viewOnly=request.getParameter("viewOnly");

        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);

        List<FormDelayApply> queryDelay=formDelayApplyService.queryDelay();
        model.addAttribute("queryDelay",queryDelay);

        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formNo);
        formDelayApplyDto.setDelayTime(DateHelper.formatDate(formDelayApplyDto.getDelayTimes(),"yyyy-MM-dd"));

        String projectNo=formDelayApplyDto.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
        formcon.setLeaderName(empDto.getName());

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",formcon.getBeginTime1().substring(0,10));
        model.addAttribute("endTime",formcon.getEndTime1().substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        model.addAttribute("formDelayApplyDto05",formDelayApplyDto);
        model.addAttribute("viewOnly",viewOnly);

        if(viewOnly.equals("true")){
            return view(formViewLayout,  "delay_flow/syy_kg_lc04_v05", model);
        }
        return view(formLayout,"delay_flow/syy_kg_lc04_v05",model);
    }
    //T05审核保存
    @RequestMapping(value = "syy_kg_lc04_v05_approve")
    @ResponseBody
    public CommonResult delayV5Approve(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String opinion2=request.getParameter("opinion2");
        CommonResult result=formDelayApplyService.updateDelayForm05(dto,opinion2);

        return result;
    }
    //科委主任（院长）审批
    //T06审核页面
    @RequestMapping(value = "syy_kg_lc04_v06")
    public ModelAndView FormDelayApplyView6(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String formKind=request.getParameter("formKind");
        String viewOnly=request.getParameter("viewOnly");

        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);

        List<FormDelayApply> queryDelay=formDelayApplyService.queryDelay();
        model.addAttribute("queryDelay",queryDelay);

        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formNo);
        formDelayApplyDto.setDelayTime(DateHelper.formatDate(formDelayApplyDto.getDelayTimes(),"yyyy-MM-dd"));

        String projectNo=formDelayApplyDto.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
        formcon.setLeaderName(empDto.getName());

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",formcon.getBeginTime1().substring(0,10));
        model.addAttribute("endTime",formcon.getEndTime1().substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        model.addAttribute("formDelayApplyDto06",formDelayApplyDto);
        model.addAttribute("viewOnly",viewOnly);

        if(viewOnly.equals("true")){
            return view(formViewLayout,  "delay_flow/syy_kg_lc04_v06", model);
        }
        return view(formLayout,"delay_flow/syy_kg_lc04_v06",model);
    }
    //T06审核保存
    @RequestMapping(value = "syy_kg_lc04_v06_approve")
    @ResponseBody
    public CommonResult delayV6Approve(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String opinion2=request.getParameter("opinion2");
        CommonResult result=formDelayApplyService.updateDelayForm06(dto,opinion2);

        return result;
    }

    /**
     * 2017.01.05修改
     */
    @RequestMapping(value = "ajax_approval_approves")
    public void approvalProjectOK16(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if (listid.size() > 0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC05");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //CommonResult nextProject = formProjectAcceptanceService.updateProject0(dto);
                CommonResult nextProject = formDelayApplyService.updateDelayProject(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        } else {
            AjaxResponse.write(response, "text", "error");
        }
    }
    //科管科长审核页面
    @RequestMapping(value = "syy_kg_lc04_v1kgkz")
    public ModelAndView FormDelayApplyView1kgkz(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String formKind=request.getParameter("formKind");
        String viewOnly=request.getParameter("viewOnly");

        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);

        List<FormDelayApply> queryDelay=formDelayApplyService.queryDelay();
        model.addAttribute("queryDelay",queryDelay);

        FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(formNo);
        formDelayApplyDto.setDelayTime(DateHelper.formatDate(formDelayApplyDto.getDelayTimes(),"yyyy-MM-dd"));

        String projectNo=formDelayApplyDto.getProjectNo();

        ScientificRPKUDto formcon=scienticRPKUService.getIdByProjectNoDto1(projectNo);

        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getIdByProjectNo(projectNo);
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());

        EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
        formcon.setLeaderName(empDto.getName());

        model.addAttribute("projectName",formcon.getProjectName());
        model.addAttribute("projectNo",formcon.getProjectNo());
        model.addAttribute("assumeCompany",formcon.getAssumeCompany());
        model.addAttribute("beginTime",formcon.getBeginTime1().substring(0,10));
        model.addAttribute("endTime",formcon.getEndTime1().substring(0,10));
        model.addAttribute("leaderName",formcon.getLeaderName());

        model.addAttribute("formDelayApplyDto1",formDelayApplyDto);
        model.addAttribute("viewOnly",viewOnly);

        if(viewOnly.equals("true")){
            return view(formViewLayout,  "delay_flow/syy_kg_lc04_v1kgkz", model);
        }
        return view(formLayout,"delay_flow/syy_kg_lc04_v1kgkz",model);
    }
    //T2审核保存
    @RequestMapping(value = "syy_kg_lc04_v1_approvekgkz")
    @ResponseBody
    public CommonResult delayV2Approvekgkz(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String leaderWord=request.getParameter("leaderWord");

        //CommonResult result=formDelayApplyService.updateDelayForm2(dto,leaderWord);
        CommonResult result=formDelayApplyService.updateDelay001(dto);
        return result;
    }

    //跳转到第1步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg04_pl1")
    public ModelAndView syy_kg04_pl1(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
//        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
//        model.addAttribute("alltype",alltype);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "delay_flow/syy_kg04_pl1", model);
    }

    //第1步批量数据
    @RequestMapping(value = "syy_kg04_plTpl1")
    public void syy_kg04_plTpl1(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDelayApplyService.getFormApproverByUserId(userInfo.getId(),1);
        List<FormDelayApplyDto> projectDtoPagedResult=formDelayApplyService.getFormDeliverApplyByClstep(specialtyType,l,1);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("delay",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "syy_kg_lc04_approve_lot")
    @ResponseBody
    public void syy_kg_lc04_approve_lot(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String appvalue = request.getParameter("appvalue");
        if(appvalue.equals("1")){
            dto.setAppValue(AppValue.Y.ordinal());
        }else if(appvalue.equals("2")){
            dto.setAppValue(AppValue.N.ordinal());
        }else if(appvalue.equals("3")){
            dto.setAppValue(AppValue.B.ordinal());
        }
        String allContent = URI.deURI(request.getParameter("content"));//处理意见
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if (listid.size() > 0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo=us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
//                String opinion=request.getParameter("opinion");
                dto.setFormKind("SYY_KG_LC05");

                dto.setContent(allContent);
                CommonResult result=formDelayApplyService.approve(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    //跳转到第1步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg04_pl2")
    public ModelAndView syy_kg04_pl2(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "delay_flow/syy_kg04_pl2", model);
    }

    //第2步批量数据
    @RequestMapping(value = "syy_kg04_plTpl2")
    public void syy_kg04_plTpl2(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDelayApplyService.getFormApproverByUserId(userInfo.getId(),2);
        List<FormDelayApplyDto> projectDtoPagedResult=formDelayApplyService.getFormDeliverApplyByClstep(specialtyType,l,2);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("delay",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //T2审核保存
    @RequestMapping(value = "syy_kg_lc04_v1_approve_lot")
    @ResponseBody
    public void syy_kg_lc04_v1_approve_lot(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String appvalue = request.getParameter("appvalue");
        if(appvalue.equals("1")){
            dto.setAppValue(AppValue.Y.ordinal());
        }else if(appvalue.equals("2")){
            dto.setAppValue(AppValue.N.ordinal());
        }else if(appvalue.equals("3")){
            dto.setAppValue(AppValue.B.ordinal());
        }
        String leaderWord=URI.deURI(request.getParameter("leaderWord"));
        String allContent = URI.deURI(request.getParameter("content"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if (listid.size() > 0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo=us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
//                String opinion=request.getParameter("opinion");
                dto.setFormKind("SYY_KG_LC05");

                dto.setContent(allContent);
                CommonResult result=formDelayApplyService.updateDelayForm2(dto,leaderWord);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //跳转到第5步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg04_pl5")
    public ModelAndView syy_kg04_pl5(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "delay_flow/syy_kg04_pl5", model);
    }
    //第5步批量数据
    @RequestMapping(value = "syy_kg04_plTpl5")
    public void syy_kg04_plTpl5(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDelayApplyService.getFormApproverByUserId(userInfo.getId(),5);
        List<FormDelayApplyDto> projectDtoPagedResult=formDelayApplyService.getFormDeliverApplyByClstep(specialtyType,l,5);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("delay",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //T5审核保存
    @RequestMapping(value = "syy_kg_lc04_v5_approve_lot")
    @ResponseBody
    public void syy_kg_lc04_v5_approve_lot(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String appvalue = request.getParameter("appvalue");
        String projectDelayStep=request.getParameter("projectDelayStep");//获取步骤
        if(appvalue.equals("1")){
            dto.setAppValue(AppValue.Y.ordinal());
        }else if(appvalue.equals("2")){
            dto.setAppValue(AppValue.N.ordinal());
        }else if(appvalue.equals("3")){
            dto.setAppValue(AppValue.B.ordinal());
        }
        String allContent = URI.deURI(request.getParameter("content"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if (listid.size() > 0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo=us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
//                String opinion=request.getParameter("opinion");
                dto.setFormKind("SYY_KG_LC05");

                dto.setContent(allContent);
                if(projectDelayStep.equals("5")){
                    CommonResult result=formDelayApplyService.updateBatch(dto);
                }else{
                  CommonResult result=formDelayApplyService.approve(dto);
                }
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
    //跳转到第6步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg04_pl6")
    public ModelAndView syy_kg04_pl6(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "delay_flow/syy_kg04_pl6", model);
    }
    //第6步批量数据
    @RequestMapping(value = "syy_kg04_plTpl6")
    public void syy_kg04_plTpl6(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDelayApplyService.getFormApproverByUserId(userInfo.getId(),6);
        List<FormDelayApplyDto> projectDtoPagedResult=formDelayApplyService.getFormDeliverApplyByClstep(specialtyType,l,6);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("delay",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }


    //T6审核保存
    @RequestMapping(value = "syy_kg_lc04_approve_lot6")
    @ResponseBody
    public void syy_kg_lc04_approve_lot6(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String appvalue = request.getParameter("appvalue");
        if(appvalue.equals("1")){
            dto.setAppValue(AppValue.Y.ordinal());
        }else if(appvalue.equals("2")){
            dto.setAppValue(AppValue.N.ordinal());
        }else if(appvalue.equals("3")){
            dto.setAppValue(AppValue.B.ordinal());
        }
        String allContent = URI.deURI(request.getParameter("content"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if (listid.size() > 0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo=us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
//                String opinion=request.getParameter("opinion");
                dto.setFormKind("SYY_KG_LC05");

                dto.setContent(allContent);
                CommonResult result=formDelayApplyService.approve(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //跳转到第7步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg04_pl7")
    public ModelAndView syy_kg04_pl7(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "delay_flow/syy_kg04_pl7", model);
    }
    //第7步批量数据
    @RequestMapping(value = "syy_kg04_plTpl7")
    public void syy_kg04_plTpl7(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDelayApplyService.getFormApproverByUserId(userInfo.getId(),7);
        List<FormDelayApplyDto> projectDtoPagedResult=formDelayApplyService.getFormDeliverApplyByClstep(specialtyType,l,7);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("delay",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //T7审核保存
    @RequestMapping(value = "syy_kg_lc04_approve_lot7")
    @ResponseBody
    public void syy_kg_lc04_approve_lot7(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String appvalue = request.getParameter("appvalue");
        if(appvalue.equals("1")){
            dto.setAppValue(AppValue.Y.ordinal());
        }else if(appvalue.equals("2")){
            dto.setAppValue(AppValue.N.ordinal());
        }else if(appvalue.equals("3")){
            dto.setAppValue(AppValue.B.ordinal());
        }
        String allContent = URI.deURI(request.getParameter("content"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if (listid.size() > 0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo=us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
//                String opinion=request.getParameter("opinion");
                dto.setFormKind("SYY_KG_LC05");

                dto.setContent(allContent);
                CommonResult result=formDelayApplyService.approve(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //跳转到第8步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg04_pl8")
    public ModelAndView syy_kg04_pl8(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "delay_flow/syy_kg04_pl8", model);
    }

    //第8步批量数据
    @RequestMapping(value = "syy_kg04_plTpl8")
    public void syy_kg04_plTpl8(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDelayApplyService.getFormApproverByUserId(userInfo.getId(),8);
        List<FormDelayApplyDto> projectDtoPagedResult=formDelayApplyService.getFormDeliverApplyByClstep(specialtyType,l,8);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("delay",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //T8审核保存
    @RequestMapping(value = "syy_kg_lc04_approve_lot8")
    @ResponseBody
    public void syy_kg_lc04_approve_lot8(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String appvalue = request.getParameter("appvalue");
        if(appvalue.equals("1")){
            dto.setAppValue(AppValue.Y.ordinal());
        }else if(appvalue.equals("2")){
            dto.setAppValue(AppValue.N.ordinal());
        }else if(appvalue.equals("3")){
            dto.setAppValue(AppValue.B.ordinal());
        }
        String allContent = URI.deURI(request.getParameter("content"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
       /* Long[] array=new Long[size];*/
        if (listid.size() > 0) {
            for (int i = 0; i < listid.size(); i++) {
                String formNo=listid.get(i).toString();
                FormDelayApplyDto formDelayApplyDto=formDelayApplyService.getFormByFormNo(Long.parseLong(formNo));
                ScientificRPKU scientificRPKU=scienticRPKUService.getIdByProjectNo(formDelayApplyDto.getProjectNo());
                scientificRPKU.setEndTime(formDelayApplyDto.getDelayTimes());
                scienticRPKUService.addScientificResearchProject(scientificRPKU);

                EmpSessionDto userInfo=us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());

                String opinion2=request.getParameter("opinion2");
                CommonResult result=formDelayApplyService.updateDelayForm4(dto,opinion2);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
}