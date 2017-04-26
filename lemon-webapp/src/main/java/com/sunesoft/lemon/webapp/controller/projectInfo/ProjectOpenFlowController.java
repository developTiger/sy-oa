package com.sunesoft.lemon.webapp.controller.projectInfo;

import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectApplyService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyFileDto;
import com.sunesoft.lemon.deanery.scientificRPKU.ScienticRPKUService;
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
import com.sunesoft.lemon.syms.workflow.application.FormApproveListService;
import com.sunesoft.lemon.syms.workflow.application.FormApproveRoleService;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.*;
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
import java.util.*;

/**
 * Created by zhouz on 2016/8/19.
 * edit by pxj
 */
@Controller
public class ProjectOpenFlowController extends Layout {


    @Autowired
    FormProjectApplyService formProjectApplyService;
    @Autowired
    FormListService formListService;

    @Autowired
    FormHeaderService headerService;

    @Autowired
    UserSession us;

    @Autowired
    FileService fileService;

    @Autowired
    ScientificResearchProjectService srpService;

    @Autowired
    FormApproveRoleService formApproveRoleService;
    @Autowired
    FormApproveListService formApproveListService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    ScienticRPKUService scienticRPKUService;

    @RequestMapping("syy_kg_lc02_a")
    public ModelAndView syy_kg_lc02_view(Model model, HttpServletRequest request) {
        FormListDto dto =formListService.getFormListInfo("SYY_KG_LC02");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        String formNo = request.getParameter("formNo");

        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormProjectApplyDto formLeave = formProjectApplyService.getFormByFormNo((Long.parseLong(formNo)));
            List<ScientificRPKUDto> formcon= scienticRPKUService.queryProjectApprove(formLeave.getProjectNo());
            ScientificRPKUDto rpkuDto=formcon.get(0);
            if(rpkuDto.getProjectKTStatus().equals("1")){
               // model.addAttribute("rpkuDto","");
                formLeave.setAtherPeople(null);
                formLeave.setProjectName(null);
                formLeave.setProjectNo(null);
                model.addAttribute("beandto",formLeave);
            }else{
                model.addAttribute("rpkuDto",rpkuDto);//获取一部审核退回的立项信息
                model.addAttribute("beandto", formLeave);
            }
            model.addAttribute("formNo",formLeave.getFormNo());
        }else{
            model.addAttribute("beandto",null);
            model.addAttribute("rpkuDto",null);
        }
        //List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        String projectNumber="";
        List<ScientificRPKUDto> listProject= scienticRPKUService.queryProjectApprove(projectNumber);
        model.addAttribute("projectName",listProject);
       //当前项目信息
     //  ScientificResearchProjectDto dto = srpService.getScientificResearchProject(Long.parseLong(id));

       // model.addAttribute("beandto",dto);
        return view(applyLayout, "projectInfo/syy_kg_lc02_a", model);
    }

    //获取选中的id
    @RequestMapping("ajax_openId")
    public void ajax_executionId(Model model, HttpServletRequest request, HttpServletResponse response) {
        String formNoSel = request.getParameter("formNoSel");
        if (!StringUtils.isNullOrWhiteSpace(formNoSel)) {
           // ScientificResearchProjectDto formcon = srpService.getByOrderId(formNoSel);
            List<ScientificRPKUDto> formcon= scienticRPKUService.queryProjectApprove(formNoSel);
          ScientificRPKUDto rpkuDto=formcon.get(0);
        /*      if(formcon.get(0).getYear()!=null){
                formcon.get(0).setYear_Str(DateHelper.formatDate(formcon.get(0).getYear(),"yyyy-MM-dd"));
            }
            if(formcon.get(0).getBeginTime1()!=null){
                formcon.get(0).setBeginTime1(DateHelper.formatDate(formcon.get(0).getBeginTime(),"yyyy-MM-dd"));
            }
            if(formcon.get(0).getEndTime1()!=null){
                formcon.get(0).setEndTime1(DateHelper.formatDate(formcon.get(0).getEndTime1(),"yyyy-MM-dd"));
            }*/
            String json = JsonHelper.toJson(rpkuDto);
            AjaxResponse.write(response, json);
        }
    }

    //获取选中的开题id
    @RequestMapping("ajax_formApprove_detial")
    public void ajax_formApprove_detial(HttpServletRequest request, HttpServletResponse response) {
        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormProjectApplyDto formProjectApply = formProjectApplyService.getFormByFormNo((Long.parseLong(formNo)));
            String json = JsonHelper.toJson(formProjectApply);
            AjaxResponse.write(response, json);
        }else{
            CommonResult  result = new CommonResult(false,"开题中没有该项目名称！");
            String json = JsonHelper.toJson(result);
            AjaxResponse.write(response, json);
        }
    }

    /**
     *
     * @param formProjectApplyDto 申请
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "ajax_add_update_lc02_apply")
    @ResponseBody
    public String addOrupdateTrain(FormProjectApplyDto formProjectApplyDto ,HttpServletRequest request,HttpServletResponse response) {
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName");
        //String formNo = request.getParameter("formNo");暂留，流程1到2往返审核退回
        CommonResult result=null;
        List<FormProjectApplyFileDto> list = new ArrayList<>();
        for (MultipartFile myfile : myfiles) {
            if (!myfile.isEmpty()) {
                try {
                    String fileName = myfile.getOriginalFilename();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto=new FileInfoDto();
                    fileInfoDto.setFileName(fileName);
                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setInputStream(myfile.getInputStream());
                    String id=fileService.upload(fileInfoDto);
                    if(!StringUtils.isNullOrWhiteSpace(id)) {
                        FormProjectApplyFileDto formProjectApplyFileDto = new FormProjectApplyFileDto();
                        formProjectApplyFileDto.setFileId(id);
                        formProjectApplyFileDto.setFileName(fileName);
                        list.add(formProjectApplyFileDto);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       // String already_FileId=request.getParameter("already_FileId");
      //  String already_FileName=request.getParameter("already_FileName");
        /**
         * 获取第一步审核退回后文件信息
         */
        if(formProjectApplyDto.getAlready_FileId()!=null){
        for(int i=0;i<formProjectApplyDto.getAlready_FileId().size();i++){
            FormProjectApplyFileDto dto = new FormProjectApplyFileDto();
            dto.setFileId(formProjectApplyDto.getAlready_FileId().get(i));
            dto.setFileName(URI.deURI(formProjectApplyDto.getAlready_FileName().get(i)));
            list.add(dto);
        }
        }
        String projectName=request.getParameter("pname");
        if(!StringUtils.isNullOrWhiteSpace(projectName)){
            formProjectApplyDto.setProjectName(URI.deURI(projectName));
        }
        String yx=null;
        String kt="1";
        String ys=null;
        String sb=null;
        String jc=null;
       scienticRPKUService.updateByProjectApply(formProjectApplyDto.getProjectNo(),yx,kt,ys,sb,jc);
        formProjectApplyDto.setFileList(list);
        CommonResult commonResult = formProjectApplyService.addByDto(formProjectApplyDto);
        result = formProjectApplyService.submitForm(commonResult.getId(), formProjectApplyDto.getFormKind());
        return JsonHelper.toJson(result);
    }


//第一步审核页面
    @RequestMapping(value = "syy_kg_lc02_view1")
    public ModelAndView formProjectApplyView(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view", model);//审核 或 补充数据

    }
    //第一步审核保存
    @RequestMapping(value = "kg_lc02_approve1")
    @ResponseBody
    public CommonResult approves(ApproveFormDto dto,FormProjectApplyDto formProjectApplyDto,HttpServletRequest request,HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
//        Map<String,Object> param = new HashMap<>();
//        Enumeration enu=request.getParameterNames();
//        while(enu.hasMoreElements()){
//            String paraName=(String)enu.nextElement();
//            param.put(paraName,request.getParameter(paraName));
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        String appValue=request.getParameter("appValue");
  /*      if (!appValue.equals("1")){
            String yx=null;
            String kt="3";
            String ys=null;
            String sb=null;
            String jc=null;
           // scienticRPKUService.updateByProjectApply(projectNo,yx,kt,ys,sb,jc);
            scienticRPKUService.reset(projectNo,yx,kt,ys,sb,jc);
        }*/
        CommonResult result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDto);
//        FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
//        List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
//        CommonResult result= formProjectApplyService.addPriceAndApprove(dto, 1D);
        return result;
    }

//第二步审核页面
    @RequestMapping(value = "syy_kg_lc02_view2")
    public ModelAndView formProjectApplyView2(HttpServletRequest request,Model model) {

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view2", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view2", model);//审核 或 补充数据
    }

    //第二步审核保存
    @RequestMapping(value = "kg_lc02_approve2")
    @ResponseBody
    public CommonResult approves2(ApproveFormDto dto,FormProjectApplyDto formProjectApplyDto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        CommonResult result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo,formProjectApplyDto);
        return result;
    }

    //第三步审核页面
    @RequestMapping(value = "syy_kg_lc02_view3")
    public ModelAndView formProjectApplyView3(HttpServletRequest request,Model model) {

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        //获取分管领导
        //List<FormAppRoleDto> rolelist=formApproveRoleService.getAll();
        List<DeptmentDto> deptmentDtos = deptmentService.getDeptsByName("院领导");//分管领导为院领导部门下人员
        List<EmpDto> empDtos=new ArrayList<>();
        if(deptmentDtos !=null){
           empDtos = employeeService.getAllHeader(deptmentDtos.get(0).getId());
        }else{
            System.out.print("无院领导这个部门");
        }
        model.addAttribute("role",empDtos);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view3", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view3", model);//审核 或 补充数据
    }

    //中心客观人员汇总审批
    @RequestMapping(value = "unit_Leadership_Summary")
    @ResponseBody
    public ModelAndView unit_Leadership_Summary(HttpServletRequest request,Model model,FormProjectApplyDto formProjectApplyDto) throws UnsupportedEncodingException {
       /* PagedResult<FormProjectApplyDto> listDto=formProjectApplyService.selectLeaderApprove(formProjectApplyDto);
        */
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<EmpDto> empDtos = employeeService.getAllEmps();//获取员工数据
        List<DeptmentDto> deptmentDto = deptmentService.getAllDept();
        List<MultiSelectUserWithDept> multiSelectUserWithDepts = new ArrayList<>();
        for(DeptmentDto dept:deptmentDto){
            MultiSelectUserWithDept multiSelectUserWithDept = new MultiSelectUserWithDept(dept.getId(),dept.getDeptName());
            for(EmpDto emp:empDtos){
                if(emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts.add(multiSelectUserWithDept);
        }


        model.addAttribute("empDtos",multiSelectUserWithDepts);
        List<DeptmentDto> deptmentDtos = deptmentService.getDeptsByName("院领导");//分管领导为院领导部门下人员
        List<EmpDto> empDto=new ArrayList<>();
        if(deptmentDtos !=null){
            empDto = employeeService.getAllHeader(deptmentDtos.get(0).getId());
        }else{
            System.out.print("无院领导这个部门");
        }
        model.addAttribute("role",empDto);
      /*  HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("userInfo",dto);
*/
     //   model.addAttribute("clstep",request.getParameter("clstep"));
        return view(formBatchLayout,"projectInfo/unit_Leadership_Summary",model);
    }

    //中心客管人员汇总数据
    @RequestMapping(value = "ajax_Unit_Leadership_Summary")
    @ResponseBody
    public void ajax_Unit_Leadership_Summary(HttpServletResponse response,HttpServletRequest request,FormProjectApplyDto formProjectApplyDto)throws UnsupportedEncodingException {
        EmpSessionDto user = us.getCurrentUser(request);
        formProjectApplyDto.setApplyer(user.getId());
    /*    if (formProjectApplyDto.getClStep()>1){
            formProjectApplyDto.setClStep(formProjectApplyDto.getClStep()-1);
        }*/
        PagedResult<FormProjectApplyDto> listDto=formProjectApplyService.selectLeaderApprove(formProjectApplyDto);
        String json = JsonHelper.toJson(listDto);
        AjaxResponse.write(response, json);
    }
    //第三步审核保存
    @RequestMapping(value = "kg_lc02_approve3")
    @ResponseBody
    public CommonResult approves3(ApproveFormDto dto,FormProjectApplyDto formProjectApplyDto,HttpServletRequest request,HttpServletResponse response){
        CommonResult result=null;
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        String employeeId=request.getParameter("project_EmployeeId");
        String formKind = request.getParameter("formKind");
        //选择分管领导审核人
      //  result=formProjectApplyService.updateProjectApplyEmployee(dto,employeeId);
        result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDto);
        return result;
    }

    //第4步审核页面
    @RequestMapping(value = "syy_kg_lc02_view4")
    public ModelAndView formProjectApplyView4(HttpServletRequest request,Model model) {

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);

   /*     List<DeptmentDto> deptmentDtos = deptmentService.getDeptsByName("院领导");//分管领导为院领导部门下人员
        List<EmpDto> empDtos=new ArrayList<>();
        if(deptmentDtos !=null){
            empDtos = employeeService.getAllHeader(deptmentDtos.get(0).getId());
        }else{
            System.out.print("无院领导这个部门");
        }*/
        List<EmpDto>  empDtos =employeeService.getAllEmps();
        List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
        List<MultiSelectUserWithDept> multiSelectUserWithDepts = new ArrayList<>();
        for(DeptmentDto dept:deptmentDtos){
            MultiSelectUserWithDept multiSelectUserWithDept = new MultiSelectUserWithDept(dept.getId(),dept.getDeptName());
            for(EmpDto emp:empDtos){
                if(emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts.add(multiSelectUserWithDept);
        }
        //model.addAttribute("empInfos", multiSelectUserWithDepts);
       // List<EmpDto> empDtos = employeeService.getAllEmps();//获取员工数据
        model.addAttribute("empDtos",multiSelectUserWithDepts);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view4", model);
        }
            return view(formLayout,  "projectInfo/syy_kg_lc02_view4", model); //审核 或 补充数据

    }

    //第4步审核保存
    @RequestMapping(value = "kg_lc02_approve4")
    @ResponseBody
    public CommonResult approves4(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response,FormProjectApplyDto formProjectApplyDtos){
        CommonResult result=null;
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        //formApproveListService.
        //获取表单数据
      //  FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(dto.getFormNo());
        //empId.add(Long.parseLong(formProjectApplyDto.getEmployeeId()));

        for(int i=0;i<formProjectApplyDtos.getFormNo_OpenFlow().size();i++){
           // //根据分管领导决定谁审核下一步
            // formProjectApplyService.resetNextApprover(Long.parseLong(formProjectApplyDtos.getFormNo_OpenFlow().get(i)),formProjectApplyDtos.getEmployeeId());
           //将专家id放入表中共第六部审核
            formProjectApplyService.updateProjectApplyByFormNo(Long.parseLong(formProjectApplyDtos.getFormNo_OpenFlow().get(i)),formProjectApplyDtos.getEmployeeId(),"spcialist");
        }
        result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDtos);
        return result;
    }

    //第5步审核页面 科管科长审核
    @RequestMapping(value = "syy_kg_lc02_view5")
    public ModelAndView formProjectApplyView5(HttpServletRequest request,Model model) {

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view5", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view5", model);//审核 或 补充数据
    }
    //第5步审核保存 科管科长审核保存
    @RequestMapping(value = "kg_lc02_approve5")
    @ResponseBody
    public CommonResult approves5(ApproveFormDto dto,FormProjectApplyDto formProjectApplyDto,HttpServletRequest request,HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        for(int i=0;i<formProjectApplyDto.getFormNo_OpenFlow().size();i++){
             //根据分管领导决定谁审核下一步
            String list=formProjectApplyService.findOpenFlowEmployeeids(Long.parseLong(formProjectApplyDto.getFormNo_OpenFlow().get(i)),"specialids");
           if(list.length()>0){
               List<Long> specialid=new ArrayList<>();
               String[] suz=list.split(",");
               for(int a=0;a<suz.length;a++){
                   specialid.add(Long.parseLong(suz[a]));
               }
               formProjectApplyService.resetNextApprover(Long.parseLong(formProjectApplyDto.getFormNo_OpenFlow().get(i)),specialid);
           }

        }

        CommonResult result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDto);
        return result;
    }
    //第6步审核页面专家审查
    @RequestMapping(value = "syy_kg_lc02_view_6")
    public ModelAndView syy_kg_lc02_view_6(HttpServletRequest request,Model model) {

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view_6", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view_6", model);//审核 或 补充数据
    }
    //第6步审核保存专家审查
    @RequestMapping(value = "kg_lc02_approve_6")
    @ResponseBody
    public CommonResult kg_lc02_approve_6(ApproveFormDto dto,FormProjectApplyDto formProjectApplyDto,HttpServletRequest request,HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        CommonResult result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDto);
        return result;
    }

    //第7步审核页面 科研管理人员汇总审批
    @RequestMapping(value = "syy_kg_lc02_view6")
    public ModelAndView formProjectApplyView6(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        List<DeptmentDto> deptmentDtos = deptmentService.getDeptsByName("院领导");//分管领导为院领导部门下人员
        List<EmpDto> empDto=new ArrayList<>();
        if(deptmentDtos !=null){
            empDto = employeeService.getAllHeader(deptmentDtos.get(0).getId());
        }else{
            System.out.print("无院领导这个部门");
        }
        model.addAttribute("role",empDto);
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view6", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view6", model);//审核 或 补充数据
    }
    //第7步审核保存
    @RequestMapping(value = "kg_lc02_approve6")
    @ResponseBody
    public CommonResult approves6(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response,FormProjectApplyDto formProjectApplyDtos){
        CommonResult result=null;
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        for(int i=0;i<formProjectApplyDtos.getFormNo_OpenFlow().size();i++){
            //formProjectApplyService.resetNextApprover(Long.parseLong(formProjectApplyDtos.getFormNo_OpenFlow().get(i)),formProjectApplyDtos.getChoose_LeaderId());
             //往leaderid中填入employeeid
            formProjectApplyService.updateProjectApplyByFormNo(Long.parseLong(formProjectApplyDtos.getFormNo_OpenFlow().get(i)),formProjectApplyDtos.getChoose_LeaderId(),"leader");

        }
        result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDtos);
        return result;
    }

    //第8步审核页面 科管科长审核
    @RequestMapping(value = "syy_kg_lc02_view_8")
    public ModelAndView syy_kg_lc02_view_8(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        List<DeptmentDto> deptmentDtos = deptmentService.getDeptsByName("院领导");//分管领导为院领导部门下人员
        List<EmpDto> empDto=new ArrayList<>();
        if(deptmentDtos !=null){
            empDto = employeeService.getAllHeader(deptmentDtos.get(0).getId());
        }else{
            System.out.print("无院领导这个部门");
        }
        model.addAttribute("role",empDto);
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view_8", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view_8", model);//审核 或 补充数据
    }
    //第8步审核保存
    @RequestMapping(value = "kg_lc02_approve_8")
    @ResponseBody
    public CommonResult kg_lc02_approve_8(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response,FormProjectApplyDto formProjectApplyDtos){
        CommonResult result=null;
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        for(int i=0;i<formProjectApplyDtos.getFormNo_OpenFlow().size();i++){
            String list=formProjectApplyService.findOpenFlowEmployeeids(Long.parseLong(formProjectApplyDtos.getFormNo_OpenFlow().get(i)),"leader");
            if(list.length()>0){
                List<Long> specialid=new ArrayList<>();
                String[] suz=list.split(",");
                for(int a=0;a<suz.length;a++){
                    specialid.add(Long.parseLong(suz[a]));
                }
                formProjectApplyService.resetNextApprover(Long.parseLong(formProjectApplyDtos.getFormNo_OpenFlow().get(i)),specialid);
            }

           // formProjectApplyService.resetNextApprover(Long.parseLong(formProjectApplyDtos.getFormNo_OpenFlow().get(i)),formProjectApplyDtos.getChoose_LeaderId());
        }
        result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDtos);
        return result;
    }




    //第7步审核页面 主管领导审批汇总审批
    @RequestMapping(value = "syy_kg_lc02_view7")
    public ModelAndView formProjectApplyView7(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view7", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view7", model);//审核 或 补充数据
    }

    //第7步审核保存 主管领导汇总审批
    @RequestMapping(value = "kg_lc02_approve7")
    @ResponseBody
    public CommonResult approves7(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response,FormProjectApplyDto formProjectApplyDtos){
        CommonResult result=null;
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDtos);
/*        if(dto.getAppValue()==2){
            String yx=null;
            String kt="0";
            String ys=null;
            String sb=null;
            String jc=null;

            for(int i=0;i<formProjectApplyDtos.getList_projectNo().size();i++){
                scienticRPKUService.reset(formProjectApplyDtos.getList_projectNo().get(i),yx,kt,ys,sb,jc);
            }
            // scienticRPKUService.reset(projectNo,yx,kt,ys,sb,jc);
        }*/
        return result;
    }

    //第8步审核页面 科委主任（院长）汇总审批
    @RequestMapping(value = "syy_kg_lc02_view8")
    public ModelAndView formProjectApplyView8(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_view8", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_view8", model);//审核 或 补充数据
    }
    //第8步审核保存 科委主任（院长）
    @RequestMapping(value = "kg_lc02_approve8")
    @ResponseBody
    public CommonResult approves8(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response,FormProjectApplyDto formProjectApplyDtos){
        CommonResult result=null;
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        String projectPlanInfoTxt= request.getParameter("projectPlanInfoTxt");
        String projName=request.getParameter("projectName");
        String projectNo=request.getParameter("projectNo");
        result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDtos);
/*        if(dto.getAppValue()==2){
            String yx=null;
            String kt="0";
            String ys=null;
            String sb=null;
            String jc=null;

            for(int i=0;i<formProjectApplyDtos.getList_projectNo().size();i++){
                scienticRPKUService.reset(formProjectApplyDtos.getList_projectNo().get(i),yx,kt,ys,sb,jc);
            }
           // scienticRPKUService.reset(projectNo,yx,kt,ys,sb,jc);
        }*/
        return result;
    }


    /**
     * 开题第9步查看 科管科汇总
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "syy_kg_lc02_result")
    public ModelAndView formProjectApplyResultView(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        //begin 表单header基础数据信息 模板数据
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<ScientificResearchProject> queryProject= formProjectApplyService.queryProject();
        model.addAttribute("projectName",queryProject);
        //end
        //获取表单数据
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
        model.addAttribute("beandto",formProjectApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){ //只查看
            return view(formViewLayout,  "projectInfo/syy_kg_lc02_result", model);
        }
        return view(formLayout,  "projectInfo/syy_kg_lc02_result", model);//审核 或 补充数据

    }

    /**
     * 科管科汇总数据处理
     * @param dto
     * @param request
     * @param formProjectApplyDtos
     * @param response
     * @return
     */
    @RequestMapping(value = "kg_lc02_resultApprove")
    @ResponseBody
    public CommonResult resultApprove(ApproveFormDto dto,HttpServletRequest request,FormProjectApplyDto formProjectApplyDtos,HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result=null;
//        Map<String,Object> param = new HashMap<>();
//        Enumeration enu=request.getParameterNames();
//        while(enu.hasMoreElements()){
//            String paraName=(String)enu.nextElement();
//            param.put(paraName,request.getParameter(paraName));
//        }
        //String content= request.getParameter("remark");
//        String projName=request.getParameter("projectName");
//        CommonResult result =formProjectApplyService.updateProject(dto, content, projName);

//        CommonResult result= formProjectApplyService.addPriceAndApprove(dto, 1D);
        String appValue=request.getParameter("appValue");
        String projectNo=request.getParameter("projectNo");
        List<FormProjectApplyFileDto> list = new ArrayList<>();
        FormProjectApplyDto formProjectApplyDto=new FormProjectApplyDto();
        if(appValue.equals("1")){
            List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("projFile");
            for (MultipartFile myfile : myfiles) {
                if (myfile.isEmpty()) {
                    result = new CommonResult(false,"请选择要上传的文件！");
                    AjaxResponse.write(response, JsonHelper.toJson(result));
                    return result;
                } else {
                    try {
                        String fileName = myfile.getOriginalFilename();
                        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        FileInfoDto fileInfoDto=new FileInfoDto();
                        fileInfoDto.setFileName(fileName);
                        fileInfoDto.setIsPublic(false);
                        fileInfoDto.setDeptId(userInfo.getDeptId());
                        fileInfoDto.setExtensions(extension);
                        fileInfoDto.setInputStream(myfile.getInputStream());
                        String fileId=fileService.upload(fileInfoDto);
                        FormProjectApplyFileDto formProjectApplyFileDto = new FormProjectApplyFileDto();
                        formProjectApplyFileDto.setFileId(fileId);
                        formProjectApplyFileDto.setFileName(fileName);
                        list.add(formProjectApplyFileDto);
                        // result= formProjectApplyService.uploadProjectFile(Long.parseLong(formNo),fileId, fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
     /*       String yx=null;
            String kt="2";
            String ys=null;
            String sb=null;
            String jc=null;
            for(int i=0;i<formProjectApplyDtos.getList_projectNo().size();i++){
                scienticRPKUService.update(formProjectApplyDtos.getList_projectNo().get(i),yx,kt,ys,sb,jc);
            }*/

        }/*else if(appValue.equals("2")){
          *//*  FormProjectApplyDto formProjectApplyDto_back = formProjectApplyService.getFormByFormNo(dto.getFormNo());
            List<Long> empId=new ArrayList<>();
            empId.add(Long.parseLong(formProjectApplyDto_back.getEmployeeId()));
            //根据分管领导决定谁审核下一步
           // formProjectApplyService.resetNextApprover(dto.getFormNo(),empId);*//*
            String yx=null;
            String kt="2";
            String ys=null;
            String sb=null;
            String jc=null;
            for(int i=0;i<formProjectApplyDtos.getList_projectNo().size();i++){
                scienticRPKUService.reset(formProjectApplyDtos.getList_projectNo().get(i),yx,kt,ys,sb,jc);
            }

        }*/

        if(formProjectApplyDtos.getFormNo_OpenFlow()==null){
            String formNo = request.getParameter("formNo");
            formProjectApplyDto= findFile(Long.parseLong(formNo));
            list.addAll(formProjectApplyDto.getFileList());
            formProjectApplyDto.setFileList(list);
            result=formProjectApplyService.saveAllApproveFile(formProjectApplyDto);
        }else{
            for(int i=0;i<formProjectApplyDtos.getFormNo_OpenFlow().size();i++){
                formProjectApplyDto= findFile(Long.parseLong(formProjectApplyDtos.getFormNo_OpenFlow().get(i)));
                List<FormProjectApplyFileDto> fileDtoList=formProjectApplyDto.getFileList();
                list.addAll(fileDtoList);
                formProjectApplyDto.setFileList(list);
                result=formProjectApplyService.saveAllApproveFile(formProjectApplyDto);
                list=listRemove(list,fileDtoList);
            }
        }
        String projectPlanInfoTxt="";
        String projName="";
        result =formProjectApplyService.updateProject(dto, projectPlanInfoTxt, projName,projectNo, formProjectApplyDtos);
        return  result;
    }


    @RequestMapping(value = "kg_lc02_resultUpload")
    public void uploadResultFile(HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String formNo = request.getParameter("formNo");
        FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(Long.parseLong(formNo));
        List<FormProjectApplyFileDto> list = new ArrayList<>();
        for(FormProjectApplyFileDto fileDto :formProjectApplyDto.getFileList()){
            list.add(fileDto);
        }
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("projFile");
        CommonResult result=null;
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false,"请选择要上传的文件！");
               // AjaxResponse.write(response, JsonHelper.toJson(result));
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
                    FormProjectApplyFileDto formProjectApplyFileDto = new FormProjectApplyFileDto();
                    formProjectApplyFileDto.setFileId(fileId);
                    formProjectApplyFileDto.setFileName(fileName);
                    list.add(formProjectApplyFileDto);
                   // result= formProjectApplyService.uploadProjectFile(Long.parseLong(formNo),fileId, fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        formProjectApplyDto.setFileList(list);
         result=formProjectApplyService.saveAllApproveFile(formProjectApplyDto);
        AjaxResponse.write(response, JsonHelper.toJson(result));
    }

   public FormProjectApplyDto findFile(long formNo){
       List<FormProjectApplyFileDto> list = new ArrayList<>();
       FormProjectApplyDto formProjectApplyDto = formProjectApplyService.getFormByFormNo(formNo);
       for(FormProjectApplyFileDto fileDto :formProjectApplyDto.getFileList()){
           list.add(fileDto);
       }
       formProjectApplyDto.setFileList(list);
       return  formProjectApplyDto;
   }
   public List<FormProjectApplyFileDto> listRemove(List<FormProjectApplyFileDto> list, List<FormProjectApplyFileDto> fileList){
       int a=list.size();
       for(int i=a-1;i>=(a-fileList.size());i--){
           list.remove(i);
       }
       return  list;
   }


}
