package com.sunesoft.lemon.webapp.controller.deliverables;

import com.sunesoft.lemon.deanery.car.application.SpecialtyTypeService;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.deliverables.application.FormDeliverApplyService;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverAplyExportDto;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverApplyDto;
import com.sunesoft.lemon.deanery.deliverables.application.dto.UpLoadDeliver;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectAcceptanceService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectApplyService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.ScientificRPKUCriteria;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectAcceptanceDto;
import com.sunesoft.lemon.deanery.scientificRPKU.ScienticRPKUService;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by swb on 2016/8/30.
 */
@Controller
public class ProjectDeliverablesController extends Layout{

    @Autowired
    EmployeeService employeeService;

    @Autowired
    FormListService formListService;

    @Autowired
    FormHeaderService headerService;

    @Autowired
    UserSession us;

    @Autowired
    FileService fileService;

    @Autowired
    FormDeliverApplyService formDeliverApplyService;

    @Autowired
    FormProjectAcceptanceService formProjectAcceptanceService;

    @Autowired
    FormProjectApplyService formProjectApplyService;

    @Autowired
    ScienticRPKUService scienticRPKUService;

    @Autowired
    SpecialtyTypeService specialtyTypeService;

    @Autowired
    DeptmentService deptmentService;

    //列表数据
    @RequestMapping("syy_kg_lc06_a0")
    @ResponseBody
    public void syy_kg_lc040(Model model, HttpServletRequest request, ScientificRPKUCriteria scientificRPKUCriteria, HttpServletResponse response){
        PagedResult pagedResult=formProjectAcceptanceService.queryAcceptance(scientificRPKUCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    //    项目成果申报申请页面
    @RequestMapping("syy_kg_lc06_a")
    public ModelAndView syy_kg_lc04(Model model,HttpServletRequest request,ScientificRPKUCriteria scientificRPKUCriteria){
        EmpSessionDto user = us.getCurrentUser(request);//查找当前用户
        FormListDto dto =formListService.getFormListInfo("SYY_KG_LC06");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        model.addAttribute("viewOnly", "false");
        List<ScientificRPKUDto> list=formProjectAcceptanceService.ListAcceptance(user.getId());
        model.addAttribute("projectName", list);
        String formNo = request.getParameter("formNo");
        FormDeliverApplyDto formDeliverApplyDto=null;
        if(formNo!=null && !formNo.equals("")){
            formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("formDeliverApplyDto", formDeliverApplyDto);
            String rens=formDeliverApplyDto.getGroupMembers();
            String[] renss=rens.split(",");
            ArrayList<String> ssl=new ArrayList<>();
            for (int i=1;i<renss.length ;i++){
                ssl.add(renss[i]);
            }
            model.addAttribute("ssls", ssl);
            model.addAttribute("ssl", renss[0]);
        }
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
        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
        model.addAttribute("alltype",alltype);
        return view(applyLayout,"deliverables_flow/syy_kg_lc06_a0",model);
    }

    //获取url
    //获取选中的id
    @RequestMapping("ystg")
    public void ystg(Model model, HttpServletRequest request, HttpServletResponse response) {
        String projectName =URI.deURI(request.getParameter("projectName"));
//        String projectNo = request.getParameter("projectNo");
        if (!StringUtils.isNullOrWhiteSpace(projectName)) {
//            ScientificRPKUDto formcon=formProjectApplyService.getIdByProjectNoDto(projectNo);
            ScientificRPKUDto formcon=specialtyTypeService.getIdByProjectNoDto(projectName);
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
    //获取url
    //获取选中的id
    @RequestMapping("ystg2")
    public void ystg2(Model model, HttpServletRequest request, HttpServletResponse response) {
        String projectNo = request.getParameter("projectNo");
        if (!StringUtils.isNullOrWhiteSpace(projectNo)) {
//            ScientificRPKUDto formcon=formProjectApplyService.getIdByProjectNoDto(projectNo);
            FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(Long.parseLong(projectNo));

//            String beginTime=formcon.getBeginTime1().substring(0, 10);
//            String endTime=formcon.getEndTime1().substring(0,10);
//            formcon.setBeginTime1(beginTime);
//            formcon.setEndTime1(endTime);

//            EmpDto empDto=employeeService.getEmpById(formcon.getLeader());
//            formcon.setLeaderName(empDto.getName());

//            String json = JsonHelper.toJson(formcon);
            String json = JsonHelper.toJson(formDeliverApplyDto);
            AjaxResponse.write(response, json);
        }
    }

    //    保存
    @RequestMapping(value = "lc06_weiyi")
    @ResponseBody
    public CommonResult lc06_weiyi(HttpServletRequest request,FormDeliverApplyDto dto) {
        String projectName=request.getParameter("projectName");
        String projectName2=request.getParameter("projectName2");
        String projectName3=request.getParameter("projectName3");
        Boolean xinzeng=Boolean.parseBoolean(request.getParameter("xinzeng"));
        Boolean isKu=Boolean.parseBoolean(request.getParameter("isKu"));
        CommonResult commonResult=null;
        if(xinzeng){
            if(isKu){
                commonResult =formDeliverApplyService.lc06weiyi(projectName);
            }else{
                commonResult =formDeliverApplyService.lc06weiyi(projectName3);
            }
        }else{
            commonResult =formDeliverApplyService.lc06weiyi2(projectName2,dto.getId());
        }
        return commonResult;
    }

    //    保存
    @RequestMapping(value = "ajax_add_update_lc06_apply")
    @ResponseBody
    public CommonResult addOrUpdateDelay(HttpServletRequest request,FormDeliverApplyDto dto) {
        String renming =request.getParameter("renming");
        String id=request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            String formNo2=request.getParameter("formNo2");
            dto.setFormNo(Long.parseLong(formNo2));
        }
//        String projectNo=request.getParameter("projectNo");

        String begindate=request.getParameter("beginTime1");
        String enddate=request.getParameter("endTime1");
        String assumeCompany=request.getParameter("assumeCompany");
        String groupMembers=URI.deURI(request.getParameter("groupMembers"));

        String projectName=request.getParameter("projectName");
        String projectName3=request.getParameter("projectName3");
        String projectName2=request.getParameter("projectName2");
        ScientificRPKUDto formcon=specialtyTypeService.getIdByProjectNoDto(projectName);
        String projectNo=formcon==null?"":formcon.getProjectNo();
        String leader=request.getParameter("leader");
        //根据id查名字
        String leaderName=formDeliverApplyService.getLeaderNameById(Long.parseLong(leader));
        String specialtyType=request.getParameter("specialtyType");
        dto.setBeginDate(DateHelper.parse2(begindate));
        dto.setEndDate(DateHelper.parse2(enddate));
        dto.setAssumeCompany(assumeCompany);
        dto.setGroupMembers(renming);
        dto.setProjectName(projectName);

        dto.setLeader(Long.parseLong(leader));
        dto.setLeaderName(leaderName);
        dto.setSpecialtyType(specialtyType);
        dto.setRank(0L);
        dto.setGrade(0L);



        Boolean isKu=Boolean.parseBoolean(request.getParameter("isKu"));
        ScientificRPKUDto scientificRPKUDto=null;
        if(isKu) {
            scientificRPKUDto= formDeliverApplyService.getfdaByFormNo(projectNo);
            dto.setProjectNo(projectNo);
            dto.setProjectName(scientificRPKUDto.getProjectName());
            dto.setDeputy(scientificRPKUDto.getDeputy());
            dto.setDeputyName(scientificRPKUDto.getDeputyName());
            dto.setProjectPlanInfo(scientificRPKUDto.getProjectPlanInfo());
            dto.setProjectType(scientificRPKUDto.getProjectType());
            dto.setNiandu(scientificRPKUDto.getYear());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                dto.setNiandu(sdf.parse(scientificRPKUDto.getYear_Str()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            dto.setProjectNo(bianhao(request));
            dto.setProjectName(projectName3);
            dto.setNiandu(new Date());
        }
        if(projectName2!=""){
            dto.setProjectName(projectName2);
        }
        CommonResult commonResult=formDeliverApplyService.addByDto(dto);
        CommonResult result=formDeliverApplyService.submitForm(commonResult.getId(),dto.getFormKind());
        if(scientificRPKUDto!=null) {
            String yx = null;
            String kt = null;
            String ys = null;
            String sb = "1";
            String jc = null;
            scienticRPKUService.updateByProjectApply(scientificRPKUDto.getProjectNo(), yx, kt, ys, sb, jc);
        }
        return commonResult;
//        return null;
    }



    //第一步审核页面
    @RequestMapping(value = "syy_kg_lc06_v1")
    public ModelAndView FormDelayApplyView0(HttpServletRequest request,Model model,HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
        List<EmpDto> empDtos = employeeService.getAllEmps();
        model.addAttribute("emp",empDtos);
        model.addAttribute("moren",empDtos.get(0));
        model.addAttribute("viewOnly",viewOnly);
        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){
            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v1",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v1",model);
    }
    //第一步审查
    @RequestMapping(value = "syy_kg_lc06_approve1")
    @ResponseBody
    public void delayApprove1(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String content = URI.deURI(request.getParameter("content"));
        String back=request.getParameter("back");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(content);
                if(back==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                }else{
                    dto.setAppValue(AppValue.B.ordinal());
                }
                CommonResult result=formDeliverApplyService.updateDeliverForm1(dto);
                System.out.print("111");
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //跳转到第1步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl1")
    public ModelAndView syy_kg06_pl1(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
        model.addAttribute("alltype",alltype);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl1", model);
    }
    //第1步批量数据
    @RequestMapping(value = "syy_kg06_plTpl1")
    public void syy_kg06_plTpl1(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),1);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep1(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //第二步审核页面
    @RequestMapping(value = "syy_kg_lc06_v2")
    public ModelAndView FormDelayApplyView(HttpServletRequest request,Model model,HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){
            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v2",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v2",model);
    }
    //跳转到第2步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl2")
    public ModelAndView syy_kg06_pl2(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
        model.addAttribute("alltype",alltype);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl2", model);
    }
    //第2步批量数据
    @RequestMapping(value = "syy_kg06_plTpl2")
    public void syy_kg06_plTpl2(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),2);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep2(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //第二步审查
    @RequestMapping(value = "syy_kg_lc06_approve2")
    @ResponseBody
    public void delayApprove(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto userInfo=us.getCurrentUser(request);
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String proficientOpinion=URI.deURI(request.getParameter("proficientOpinion"));
        String back=request.getParameter("back");
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(allContent);
                if(back==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                    CommonResult result=formDeliverApplyService.updateDeliverForm2(dto,(Long)listid.get(i),proficientOpinion);
                }else{
                    dto.setAppValue(AppValue.B.ordinal());
                    CommonResult result=formDeliverApplyService.updateDeliverForm2No(dto);
                }


            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
//        return result;
    }
    //第三步审批页面
    @RequestMapping(value = "syy_kg_lc06_v3")
    public ModelAndView FormDelayApplyView1(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
        model.addAttribute("viewOnly",viewOnly);
        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){
            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v3",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v3",model);
    }

    //跳转到第3步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl3")
    public ModelAndView syy_kg06_pl3(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
//        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
//        model.addAttribute("alltype",alltype);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl3", model);
    }

    //第3步批量数据
    @RequestMapping(value = "syy_kg06_plTpl3")
    public void syy_kg06_plTpl3(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),3);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep3(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //第三步审批
    @RequestMapping(value = "syy_kg_lc06_approve3")
    @ResponseBody
    public void delayApprove3(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String no=request.getParameter("no");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(allContent);
                if(no==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                }else {
                    dto.setAppValue(AppValue.N.ordinal());
                }
                CommonResult result=formDeliverApplyService.updateDeliverForm3(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //第四步批量审核页面
    @RequestMapping(value = "syy_kg_lc06_v4")
    public ModelAndView FormDelayApplyView2(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
        List<EmpDto> empDtos = employeeService.getAllEmps();
//        model.addAttribute("emp",empDtos);
    //    List<EmpDto> empDtos =employeeService.getHeader();
        model.addAttribute("emp",empDtos);
//        jsonmap.put("listEmpDto",empDtos);
//        model.addAttribute("moren",empDtos.get(0));
        model.addAttribute("viewOnly",viewOnly);

        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){

            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v4",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v4",model);
    }

    //跳转到第4步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl4")
    public ModelAndView syy_kg06_pl4(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        List<EmpDto> empDtos = employeeService.getAllEmps();
        model.addAttribute("emp",empDtos);
        return view(applyLayout, "deliverables_flow/syy_kg06_pl4", model);
    }

    //第4步批量数据
    @RequestMapping(value = "syy_kg06_plTpl04")
    public void syy_kg06_plTpl04(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),4);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep4(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //跳转到第四步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl")
    public ModelAndView syy_kg06_pl(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl", model);
    }

    //第四步批量数据
    @RequestMapping(value = "syy_kg06_plTpl")
    public void ajax_projectLot5(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        List<EmpDto> empDtos =employeeService.getHeader();
        jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("moren",empDtos.get(0));

        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //   第四步审批操作(选择专家)
    @RequestMapping(value = "syy_kg_lc06_approve4")
    public void syy_kg_lc06_approve4(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("prono");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String empid =URI.deURI(request.getParameter("empids"));
        String no=request.getParameter("no");
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
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(allContent);
                if(no==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                    CommonResult nextProject = formDeliverApplyService.updateProject1(dto,empid);
                }else{
                    dto.setAppValue(AppValue.N.ordinal());
                    CommonResult nextProject = formDeliverApplyService.updateProject4(dto);
                }



            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }

        if(empid!=null){

        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
    //    第四步打回
    @RequestMapping(value = "ajax_deliverNOONE")
    public void ajax_deliverNOONE(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("prono");
        String allContent = URI.deURI(request.getParameter("allContent"));
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        dto.setFormNo(Long.parseLong(ids));
        dto.setFormKind("SYY_KG_LC06");
        dto.setAppValue(AppValue.B.ordinal());
        dto.setContent(allContent);
        CommonResult nextProject = formDeliverApplyService.updateProject2(dto,"");
        AjaxResponse.write(response, "text", "success");
    }

  /*插入科管科长审核 */
  //第5步审批页面
  @RequestMapping(value = "syy_kg_lc06_v5_kgk")
  public ModelAndView FormDelayApplyView5_kgk(HttpServletRequest request,Model model){
      Long formNo=Long.parseLong(request.getParameter("formNo"));
      String viewOnly=request.getParameter("viewOnly");
      FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
      model.addAttribute("header",formHeaderDto);
      FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
      model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
      model.addAttribute("viewOnly",viewOnly);
      ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
      if(formcon!=null) {
          model.addAttribute("scId", formcon.getId());
          model.addAttribute("projectID", formcon.getProjectID());
      }
      if(viewOnly.equals("true")){
          return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v5_kgk",model);
      }
      return view(formLayout,"deliverables_flow/syy_kg_lc06_v5_kgk",model);
  }

    //跳转到第5步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl5_kgk")
    public ModelAndView syy_kg06_pl5_kgk(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
//        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
//        model.addAttribute("alltype",alltype);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl5_kgk", model);
    }

    //第5步批量数据
    @RequestMapping(value = "syy_kg06_plTpl5_kgk")
    public void syy_kg06_plTpl5_kgk(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),5);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep5(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //第5步审批
    @RequestMapping(value = "syy_kg_lc06_approve5_kgk")
    @ResponseBody
    public void delayApprove5_kgk(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String no=request.getParameter("no");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(allContent);
                if(no==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                }else {
                    dto.setAppValue(AppValue.N.ordinal());
                }
                CommonResult result=formDeliverApplyService.updateProject2(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


/******************************* 科管科长end***************************************************/



/*插入科管科长审核 */
//第8步审批页面
@RequestMapping(value = "syy_kg_lc06_v8_kgk")
public ModelAndView FormDelayApplyView8_kgk(HttpServletRequest request,Model model){
    Long formNo=Long.parseLong(request.getParameter("formNo"));
    String viewOnly=request.getParameter("viewOnly");
    FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
    model.addAttribute("header",formHeaderDto);
    FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
    model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
    model.addAttribute("viewOnly",viewOnly);
    ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
    if(formcon!=null){
        model.addAttribute("scId",formcon.getId());
        model.addAttribute("projectID",formcon.getProjectID());
    }
    if(viewOnly.equals("true")){
        return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v8_kgk",model);
    }
    return view(formLayout,"deliverables_flow/syy_kg_lc06_v8_kgk",model);
}

    //跳转到第8步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl8_kgk")
    public ModelAndView syy_kg06_pl8_kgk(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
//        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
//        model.addAttribute("alltype",alltype);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl8_kgk", model);
    }

    //第8步批量数据
    @RequestMapping(value = "syy_kg06_plTpl8_kgk")
    public void syy_kg06_plTpl8_kgk(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),8);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep8(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //第8步审批
    @RequestMapping(value = "syy_kg_lc06_approve8_kgk")
    @ResponseBody
    public void delayApprove8_kgk(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String no=request.getParameter("no");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(allContent);
                if(no==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                }else {
                    dto.setAppValue(AppValue.N.ordinal());
                }
                CommonResult result=formDeliverApplyService.updateProject2(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    /******************************* 科管科长end***************************************************/





    //第五步科管科长审批页面
    @RequestMapping(value = "syy_kg_lc06_v5")
    public ModelAndView FormDelayApplyView21(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){
            model.addAttribute("viewOnly",viewOnly);
            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v5",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v5",model);
    }
    //跳转到第5步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl5")
    public ModelAndView syy_kg06_pl5(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl5", model);
    }

    //第6步批量数据
    @RequestMapping(value = "syy_kg06_plTpl5")
    public void syy_kg06_plTpl5(HttpServletRequest request, HttpServletResponse response, Model model,FormDeliverApplyDto dto) throws UnsupportedEncodingException {
        EmpSessionDto user = us.getCurrentUser(request);
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        String applyer = request.getParameter("applyer");
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),6);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep6(specialtyType,user.getId(),l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //跳转到第6步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl6")
    public ModelAndView syy_kg06_pl6(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        List<EmpDto> empDtos = employeeService.getAllEmps();
        model.addAttribute("emp",empDtos);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl6", model);
    }

    //第7步批量数据
    @RequestMapping(value = "syy_kg06_plTpl6")
    public void syy_kg06_plTpl6(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),7);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep7(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //第五步审核操作
    @RequestMapping(value = "syy_kg_lc06_approve5")
    public void syy_kg_lc06_approve5(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String instructions = URI.deURI(request.getParameter("instructions"));
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String no=request.getParameter("no");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(allContent);
                if(no==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                    CommonResult nextProject = formDeliverApplyService.nextProject1(dto,instructions);
                }else{
                    dto.setAppValue(AppValue.N.ordinal());
                    CommonResult nextProject = formDeliverApplyService.nextProject5(dto);
                }
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
    //    第五步打回
    @RequestMapping(value = "ajax_deliverNOONE5")
    public void ajax_deliverNOONE5(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("prono");
        String allContent = URI.deURI(request.getParameter("allContent"));
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        dto.setFormNo(Long.parseLong(ids));
        dto.setFormKind("SYY_KG_LC06");
        dto.setAppValue(AppValue.B.ordinal());
        dto.setContent(allContent);
        CommonResult nextProject = formDeliverApplyService.updateProject2(dto,"");
        AjaxResponse.write(response, "text", "success");
    }
    //第五步群打回
    @RequestMapping(value = "ajax_deliverNO5")
    public void ajax_deliverNO5(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
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
                dto.setFormKind("SYY_KG_LC06");
                dto.setAppValue(AppValue.B.ordinal());
                dto.setContent(allContent);
                CommonResult nextProject = formDeliverApplyService.nextProject11(dto,"kong");
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //第6步科管科长审批页面
    @RequestMapping(value = "syy_kg_lc06_v6")
    public ModelAndView FormDelayApplyView6(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
//        List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos = employeeService.getAllEmps();
        model.addAttribute("emp",empDtos);
        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){
            model.addAttribute("viewOnly",viewOnly);
            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v6",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v6",model);
    }

    //第6步审批
    @RequestMapping(value = "syy_kg_lc06_approve6")
    @ResponseBody
    public void delayApprove6(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response, FormDeliverApplyDto formDeliverApplyDto) throws UnsupportedEncodingException {
        String ids = request.getParameter("prono");
        String empid =URI.deURI(request.getParameter("empids"));
        String allContent = URI.deURI(request.getParameter("allContent"));
        String no=request.getParameter("no");
        String issuing_unit=URI.deURI(request.getParameter("issuing_unit"));
      //  String issing_unit= URLDecoder.decode(request.getParameter("issuing_unit"),"UTF-8");
        formDeliverApplyDto.setIssuing_unit(issuing_unit);
        String rank=request.getParameter("rank");
        if(rank!=null && !rank.equals("")){
            formDeliverApplyDto.setRank(Long.parseLong(rank));
        }
        String grade=request.getParameter("grade");
        if(grade!=null && !grade.equals("")){
            formDeliverApplyDto.setGrade(Long.parseLong(grade));
        }
        String[] pids = ids.split(",");
        List listid = new ArrayList();
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
                dto.setFormKind("SYY_KG_LC06");
                formDeliverApplyDto.setFormNo((Long)listid.get(i));
                dto.setContent(allContent);
                if (no == null) {
                    dto.setAppValue(AppValue.Y.ordinal());
                    formDeliverApplyService.updateDeliver_Information(formDeliverApplyDto);
                    CommonResult result = formDeliverApplyService.updateDeliverForm6(dto, empid);
                } else {
                    dto.setAppValue(AppValue.N.ordinal());
                    CommonResult nextProject = formDeliverApplyService.nextProject6(dto);
                }
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //第7步院分管领导审批页面
    @RequestMapping(value = "syy_kg_lc06_v7")
    public ModelAndView FormDelayApplyView3(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){
            model.addAttribute("viewOnly",viewOnly);
            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v7",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v7",model);
    }

    //跳转到第8步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl8")
    public ModelAndView syy_kg06_pl8(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl8", model);
    }
    //第9步批量数据
    @RequestMapping(value = "syy_kg06_plTpl8")
    public void syy_kg06_plTpl8(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),10);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep10(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //第7、8步审核操作
    @RequestMapping(value = "syy_kg_lc06_approve7")
    public void syy_kg_lc06_approve7(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String no=request.getParameter("no");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(allContent);
                if(no==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                }else{
                    dto.setAppValue(AppValue.N.ordinal());
                }
                CommonResult nextProject = formDeliverApplyService.nextProject2(dto,null);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //    第六步打回
    @RequestMapping(value = "ajax_deliverNOONE6")
    public void ajax_deliverNOONE6(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("prono");
        String allContent = URI.deURI(request.getParameter("allContent"));
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        dto.setFormNo(Long.parseLong(ids));
        dto.setFormKind("SYY_KG_LC06");
        dto.setAppValue(AppValue.B.ordinal());
        dto.setContent(allContent);
        CommonResult nextProject = formDeliverApplyService.updateProject2(dto,"");
        AjaxResponse.write(response, "text", "success");
    }

    //跳转到第七步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl04")
    public ModelAndView syy_kg06_pl04(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl04", model);
    }
    //第六步批量数据
    @RequestMapping(value = "syy_kg06_plTpl4")
    public void syy_kg06_plTpl4(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        Long approveId=us.getCurrentUser(request).getId();
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep6(approveId);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //跳转到第7步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl7")
    public ModelAndView syy_kg06_pl7(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl7", model);
    }

    //第9步批量数据
    @RequestMapping(value = "syy_kg06_plTpl7")
    public void syy_kg06_plTpl7(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),9);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep9(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //第9步审核操作
    @RequestMapping(value = "syy_kg_lc06_approve9")
    public void syy_kg_lc06_approve9(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String awardTime=request.getParameter("awardTime");
        String no=request.getParameter("no");
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        CommonResult nextProject=null;
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setContent(allContent);
                if(no==null){
                    dto.setAppValue(AppValue.Y.ordinal());
                    EmpSessionDto empSessionDto = us.getCurrentUser(request);
                    String formNo =  listid.get(i).toString();
                    List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName");
                    CommonResult result = null;
                    for (MultipartFile myfile : myfiles) {
                        if (myfile.isEmpty()) {
                            nextProject=formDeliverApplyService.nextProject4(dto,awardTime);
                            String yx=null;
                            String kt=null;
                            String ys=null;
                            String sb="2";
                            String jc=null;
                            scienticRPKUService.update(listid.get(i).toString(),yx,kt,ys,sb,jc);
                        } else {
                            try {
                                String fileName = myfile.getOriginalFilename();
                                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                                FileInfoDto fileInfoDto = new FileInfoDto();
                                fileInfoDto.setFileName(fileName);
                                fileInfoDto.setIsPublic(false);
                                fileInfoDto.setDeptId(empSessionDto.getDeptId());
                                fileInfoDto.setExtensions(extension);
                                fileInfoDto.setInputStream(myfile.getInputStream());
                                String fileId = fileService.upload(fileInfoDto);
                                nextProject = formDeliverApplyService.nextProject3(dto,fileId, fileName,awardTime);



                                String yx=null;
                                String kt=null;
                                String ys=null;
                                String sb="2";
                                String jc=null;
                                scienticRPKUService.update(listid.get(i).toString(),yx,kt,ys,sb,jc);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else{
                    dto.setAppValue(AppValue.N.ordinal());
                    nextProject = formDeliverApplyService.nextProject9(dto);
                }
            }
            AjaxResponse.write(response, JsonHelper.toJson(nextProject));
        }else{
            AjaxResponse.write(response, JsonHelper.toJson(nextProject));
        }
    }

    //第八步科委会审批页面
    @RequestMapping(value = "syy_kg_lc06_v8")
    public ModelAndView FormDelayApplyView4(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){
            model.addAttribute("viewOnly",viewOnly);
            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v8",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v8",model);
    }
    //第九步科委会审批页面
    @RequestMapping(value = "syy_kg_lc06_v9")
    public ModelAndView FormDelayApplyView9(HttpServletRequest request,Model model){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        String viewOnly=request.getParameter("viewOnly");
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        FormDeliverApplyDto formDeliverApplyDto=formDeliverApplyService.getFormByFormNo(formNo);
        model.addAttribute("formDeliverApplyDto",formDeliverApplyDto);
        if(formDeliverApplyDto!=null) {
            model.addAttribute("awardDate", DateHelper.formatDate(formDeliverApplyDto.getAwardDate(), "yyyy-MM-dd"));
        }
        model.addAttribute("viewOnly",viewOnly);
        ScientificRPKU formcon=scienticRPKUService.getIdByProjectNo(formDeliverApplyDto.getProjectNo());
        if(formcon!=null) {
            model.addAttribute("scId", formcon.getId());
            model.addAttribute("projectID", formcon.getProjectID());
        }
        if(viewOnly.equals("true")){

            return view(formViewLayout,"deliverables_flow/syy_kg_lc06_v9",model);
        }
        return view(formLayout,"deliverables_flow/syy_kg_lc06_v9",model);
    }

    @RequestMapping(value = "syy_kg_lc06_approve8")
    @ResponseBody
    public CommonResult delayApprove4(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){
        String projectNo=request.getParameter("projectNo");
        EmpSessionDto userInfo=us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        String opinion=request.getParameter("opinion1");

        CommonResult result=formDeliverApplyService.updateDeliverForm5(dto, opinion);

        return result;
    }
    //跳转到第9步批量审核页面
    @ResponseBody
    @RequestMapping("syy_kg06_pl9")
    public ModelAndView syy_kg06_pl9(Model model, HttpServletRequest request, HttpServletResponse response){
        Long formNo=Long.parseLong(request.getParameter("formNo"));
        FormHeaderDto formHeaderDto=headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",formHeaderDto);
        model.addAttribute("formKindName", formHeaderDto.getFormKindName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "deliverables_flow/syy_kg06_pl9", model);
    }
    //第11步批量数据
    @RequestMapping(value = "syy_kg06_plTpl9")
    public void syy_kg06_plTpl9(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specialtyType = URI.deURI(request.getParameter("specialtyType"));
        EmpSessionDto userInfo=us.getCurrentUser(request);
        List<Long> l=formDeliverApplyService.getFormApproverByUserId(userInfo.getId(),11);
        List<FormDeliverApplyDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByClstep11(specialtyType,l);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("deliver",projectDtoPagedResult);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    @RequestMapping(value = "kg_lc06_Upload")
    public void uploadFile(HttpServletRequest request,HttpServletResponse response){
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
                    result= formDeliverApplyService.uploadProjectFile(Long.parseLong(formNo),fileId, fileName);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        AjaxResponse.write(response, JsonHelper.toJson(result));
    }

    @RequestMapping("ajax_syy_kg_lc06_down")
    public void download(Model model, HttpServletRequest request,HttpServletResponse response) {

        String formKind = request.getParameter("formNo1");
        FormDeliverApplyDto formDeliverApplyDto = formDeliverApplyService.downloadByOrderId(formKind);
        //*formDeliverApplyDto.setProjectStatus("运行");
        EmpSessionDto user = us.getCurrentUser(request);

        List list= new ArrayList();
        list.add(formDeliverApplyDto);
        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        //String [] header = new String[]{"编号","表单编号","申请人","申请时间","表单状态","项目编号","项目名称","专业类别","研究内容","承担单位","参加单位","开始时间","截止时间","备注","项目类型","项目长","副项目长","组员名称","部门Id","项目状态"};
        String [] header = new String[]{"项目类别","成果名称","专业分类","主要完成单位","主要协作单位","起止时间","内容摘要","主题词","简要","审核意见一","审核意见二"};
        expotExcel.doExportExcel("项目成果申报审批表",header,list,"yyyy-MM-dd",response);

    }

    /**
     * 导出
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("ajax_syy_kg06_lot_down")
    public void ajax_syy_kg06_lot_down(Model model, HttpServletRequest request,HttpServletResponse response) {

        String formKind = request.getParameter("formNos");
        // FormDeliverApplyDto formDeliverApplyDto = formDeliverApplyService.downloadByOrderId(formKind);
        List<FormDeliverAplyExportDto> projectDtoPagedResult=formDeliverApplyService.getFormDeliverApplyByFormNo(formKind);
        //*formDeliverApplyDto.setProjectStatus("运行");
        EmpSessionDto user = us.getCurrentUser(request);
        List<FormDeliverAplyExportDto> list= new ArrayList<>();
        list.addAll(projectDtoPagedResult);
        ExpotExcel<FormDeliverAplyExportDto> expotExcel = new ExpotExcel<>();
        //String [] header = new String[]{"编号","表单编号","申请人","申请时间","表单状态","项目编号","项目名称","专业类别","研究内容","承担单位","参加单位","开始时间","截止时间","备注","项目类型","项目长","副项目长","组员名称","部门Id","项目状态"};
        String [] header = new String[]{"项目编号","项目名称","主要完成单位","项目长","组员"};
        expotExcel.doExportExcel("项目成果申报审批表",header,list,"yyyy-MM-dd",response);

    }

    /**
     * 导入
     */
    @RequestMapping("ajax_syy_kg06_lot_upload")
    public void ajax_syy_kg06_lot_upload(HttpServletRequest request,HttpServletResponse response) throws IOException{
        CommonResult result=null;
        //List<ProjectPlanInputDate> projectPlanInputDates= projectPlanInputRepository.queryProjectPlan();
        String formNo=request.getParameter("formNo");
        String originalFilename=null;
        List<MultipartFile> myfiles=((DefaultMultipartHttpServletRequest) request).getFiles("myfiles");
        for(MultipartFile myfile:myfiles){
            if (myfile.isEmpty()){
                result=new CommonResult(false,"请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return ;
            }else
            {
                originalFilename=myfile.getOriginalFilename();
                try {
                    UpLoadDeliver factory=new UpLoadDeliver();
                    ListResult<FormDeliverAplyExportDto> dto=factory.readExcel(myfile.getInputStream());
                    if(dto.getIsSuccess()){
                        result = formDeliverApplyService.saveDeliver(dto.getItems(),formNo);
                    }else{
                        result=new CommonResult(false,"请选择要上传的文件！");
                    }
                } catch (IOException e) {
                    System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                    e.printStackTrace();
                    result = new CommonResult(false, "文件上传失败，请重试！！");
                    AjaxResponse.write(response, JsonHelper.toJson(result));
                    return;
                }
            }
        }
        AjaxResponse.write(response, JsonHelper.toJson(result));
        return ;
    }

    private String bianhao(HttpServletRequest request){
        Date time=new Date();
        String s=String.valueOf(time.getTime());
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String t=String.valueOf(empSessionDto.getDeptId());

        return  s;
    }
}
