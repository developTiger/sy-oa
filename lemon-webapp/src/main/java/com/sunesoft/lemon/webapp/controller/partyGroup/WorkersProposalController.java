package com.sunesoft.lemon.webapp.controller.partyGroup;

import com.sunesoft.lemon.ay.partyGroup.application.WorkProjectService;
import com.sunesoft.lemon.ay.partyGroup.application.WorkersProposalService;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.InnovationAchievementCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkersProposalCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.InnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkProjectDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkersProposalDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.FormWorkersProposalService;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkersProposalDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkersProposal;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.MultiSelectUserWithDeptDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 职工提案
 * Created by admin on 2016/9/5.
 */
@Controller
public class WorkersProposalController extends Layout {

    @Autowired
    FormListService formListService;

    @Autowired
    UserSession userSession;

    @Autowired
    FormWorkersProposalService formWorkersProposalService;

    @Autowired
    WorkersProposalService workersProposalService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    EmployeeService employeeService;


    @RequestMapping(value = "syy_dq_lc07_a")
    public ModelAndView index(Model model,HttpServletRequest request){

        FormListDto dto =formListService.getFormListInfo("SYY_DQ_LC07");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        List<DeptmentDto> deptmentDtos=deptmentService.getByDeptsIds();
        List<DeptmentDto> list = new ArrayList<>();
        for (DeptmentDto deptmentDto:deptmentDtos){
            if (deptmentDto.getDeptNo().equals("RSK") || deptmentDto.getDeptNo().equals("DQGZK") || deptmentDto.getDeptNo().equals("KYGLK")
                    || deptmentDto.getDeptNo().equals("YZBGS") || deptmentDto.getDeptNo().equals("ZLAQYXK")){
                list.add(deptmentDto);
            }

        }
        model.addAttribute("depts",list);

        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);
        EmpDto empDto=employeeService.getEmpById(empSessionDto.getId());
        model.addAttribute("emp",empDto);

        List<EmpDto> empDtos=employeeService.getAllEmps();
        model.addAttribute("beans",empDtos);

        //部门信息
        List<DeptmentDto> depts=deptmentService.getAllDept();
        model.addAttribute("depts",depts);

        //在第一级打回时，修改的页面的返回值
        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)){
            FormWorkersProposalDto proposal=formWorkersProposalService.getFormByFormNo(Long.parseLong(formNo));
            String[] arr_people = proposal.getProposalPerson().split(",");
            proposal.setProposalPeople(arr_people);
            model.addAttribute("proposal",proposal);
            model.addAttribute("formNo",proposal.getFormNo());
        }



        return view(applyLayout,"partyGroup/syy_dq_lc07_a",model);
    }

    /**
     * 职工提案 新增
     * @param request
     * @param formWorkersProposalDto
     * @return
     */
    @RequestMapping(value = "ajax_add_update_workProposal")
    @ResponseBody
    public CommonResult addOrUpdateWorkProposal(HttpServletRequest request, FormWorkersProposalDto formWorkersProposalDto){
        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);

        String hidProposalPerson = request.getParameter("hidProposalPerson");

//        String undertakeDept_id = request.getParameter("undertakeDept_id");//建议承办部门id
//        String hiddenDeptName = request.getParameter("hiddenDeptName");//建议承办部门名称

        String[] arr_empName = hidProposalPerson.split(",");
        for (int i = 0; i < arr_empName.length; i++) {
            for(int j=i+1;j<arr_empName.length;j++){
                if (arr_empName[i].equals(arr_empName[j])){
                    return new CommonResult(false,"附议人名称重复，请重新选择！");
                }
            }
        }

//                String aaaa = request.getParameter("aaaaaa");
//        if (aaaa == null){
//            return new CommonResult(false,"22");
//        }

        formWorkersProposalDto.setApplyer(empSessionDto.getId());
        formWorkersProposalDto.setApplyerName(empSessionDto.getName());
        formWorkersProposalDto.setDeptId(empSessionDto.getDeptId());
        formWorkersProposalDto.setDeptName(empSessionDto.getDeptName());

        formWorkersProposalDto.setTime(new Date());
        formWorkersProposalDto.setProposalPerson(hidProposalPerson);

        //设置归口部门
//        if (!StringUtils.isNullOrWhiteSpace(undertakeDept_id))
//            formWorkersProposalDto.setBlongDeptId(Long.parseLong(undertakeDept_id));
//        if (!StringUtils.isNullOrWhiteSpace(hiddenDeptName)) {
//            formWorkersProposalDto.setBlongDeptName(hiddenDeptName);
//            formWorkersProposalDto.setUndertakeDept(hiddenDeptName);
//        }


        CommonResult commonResult=formWorkersProposalService.addByDto(formWorkersProposalDto);
        return formWorkersProposalService.submitForm(commonResult.getId(),formWorkersProposalDto.getFormKind());
    }

    @RequestMapping(value = "ajax_syy_dq_lc07_data")
    @ResponseBody
    public FormWorkersProposalDto queryData(HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormWorkersProposalDto formWorkersProposalDto=formWorkersProposalService.getFormByFormNo(Long.parseLong(formNo));
        formWorkersProposalDto.setIsViewOnly(Boolean.valueOf(viewOnly));

        List<DeptmentDto> list=deptmentService.getAllDept();
        formWorkersProposalDto.setDeptmentDtos(list);

        return formWorkersProposalDto;
    }

    /**
     * 批量审核页面 首页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_workersProposal_fail_all")
    public ModelAndView failAll(HttpServletRequest request,Model model){
        String clStep = request.getParameter("step");
        if (!StringUtils.isNullOrWhiteSpace(clStep))
            model.addAttribute("step",clStep);

        List<DeptmentDto> list=deptmentService.getAllDept();
        model.addAttribute("depts",list);

        return view(applyLayout,"partyGroup/syy_dq_lc07_a_failAll",model);
    }

    /**
     * 批量审核页面 查询
     * @param criteria
     * @param response
     * @param request
     */
    @RequestMapping(value = "ajax_query_failAll_workersProposal")
    public void queryFailAllWorkPro(WorkersProposalCriteria criteria,HttpServletResponse response,HttpServletRequest request){
        String title = request.getParameter("proName");
        if (!StringUtils.isNullOrWhiteSpace(title))
            criteria.setTitle(URI.deURI(title));

        String person = request.getParameter("comLeader");
        if (!StringUtils.isNullOrWhiteSpace(person))
            criteria.setPerson(URI.deURI(person));

        String dept = request.getParameter("dept_name");
        if (!StringUtils.isNullOrWhiteSpace(dept))
            criteria.setDeptName(URI.deURI(dept));

        String step = request.getParameter("step");
        if (!StringUtils.isNullOrWhiteSpace(step))
            criteria.setStep(Integer.parseInt(step));

        PagedResult<FormWorkersProposalDto> pagedResult = formWorkersProposalService.getPagesByStep_2(criteria);
        List<DeptmentDto> list=deptmentService.getAllDept();
        for (FormWorkersProposalDto dto:pagedResult.getItems()){
            dto.setDeptmentDtos(list);
        }
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }


    /**
     * 批量审核页面 单个详细信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "ajax_query_workPropo_single_failAll")
    public ModelAndView queryFailAllSingleDetail(HttpServletRequest request,Model model){
        String formNo = request.getParameter("formNo");
        FormWorkersProposalDto formWorkProjectDto=formWorkersProposalService.getFormByFormNo(Long.parseLong(formNo));
        model.addAttribute("beans",formWorkProjectDto);
        model.addAttribute("help",Helper.class);
        return view("partyGroup/syy_dq_lc07_a_failAll_detail",model);
    }


    /**
     * 批量审核页面 单个提交
     * @param dto
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_approve_singleOrAll_workersProposal")
    public void approve(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){




        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);
        dto.setApproverId(empSessionDto.getId());
        dto.setApproverName(empSessionDto.getName());
        dto.setDeptId(empSessionDto.getDeptId());
        dto.setDeptName(empSessionDto.getDeptName());

        Map<String,Object> param = new HashMap<>();
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            param.put(paraName,request.getParameter(paraName));
        }
        CommonResult result= formWorkersProposalService.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 批量审核页面 批量提交
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_workersProposal_approve_All")
    public void approveAll(HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);

        String formNos = request.getParameter("formNos");
        String formKinds = request.getParameter("formKinds");
        String content = request.getParameter("content");

        String[] form_nos = formNos.split(",");
        String [] form_kinds = formKinds.split(",");
        CommonResult result = null;
        for (int i = 0; i < form_nos.length; i++) {
            ApproveFormDto dto = new ApproveFormDto();

            dto.setFormNo(Long.valueOf(form_nos[i]));
            dto.setFormKind(form_kinds[i]);
            if (!StringUtils.isNullOrWhiteSpace(content)) {
                dto.setContent(content);
            }
            dto.setAppValue(1);

            dto.setApproverId(empSessionDto.getId());
            dto.setApproverName(empSessionDto.getName());
            dto.setDeptId(empSessionDto.getDeptId());
            dto.setDeptName(empSessionDto.getDeptName());

            //获取request里面的数据
            Map<String,Object>  param = new HashMap<>();
            Enumeration enu=request.getParameterNames();
            while(enu.hasMoreElements()){
                String paraName=(String)enu.nextElement();
                param.put(paraName,request.getParameter(paraName));
            }

            result= formWorkersProposalService.doApprove(dto,param);
        }


        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 批量审核页面 单个否决
     * @param dto
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_approve_rejectSingle_workersProposal")
    public void rejectSingle(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){

        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);
        dto.setApproverId(empSessionDto.getId());
        dto.setApproverName(empSessionDto.getName());
        dto.setDeptId(empSessionDto.getDeptId());
        dto.setDeptName(empSessionDto.getDeptName());

        Map<String,Object>  param = new HashMap<>();
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            param.put(paraName,request.getParameter(paraName));
        }
        CommonResult result= formWorkersProposalService.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 批量审核页面 批量否决
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_workersProposal_reject_All")
    public void rejectAll(HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);

        String formNos = request.getParameter("formNos");
        String formKinds = request.getParameter("formKinds");
        String content = request.getParameter("content");

        String[] form_nos = formNos.split(",");
        String [] form_kinds = formKinds.split(",");
        CommonResult result = null;
        for (int i = 0; i < form_nos.length; i++) {
            ApproveFormDto dto = new ApproveFormDto();

            dto.setFormNo(Long.valueOf(form_nos[i]));
            dto.setFormKind(form_kinds[i]);
            if (!StringUtils.isNullOrWhiteSpace(content)) {
                dto.setContent(content);
            }
//            dto.setAppValue(3);

            dto.setApproverId(empSessionDto.getId());
            dto.setApproverName(empSessionDto.getName());
            dto.setDeptId(empSessionDto.getDeptId());
            dto.setDeptName(empSessionDto.getDeptName());

            //获取request里面的数据
            Map<String,Object>  param = new HashMap<>();
            Enumeration enu=request.getParameterNames();
            while(enu.hasMoreElements()){
                String paraName=(String)enu.nextElement();
                param.put(paraName,request.getParameter(paraName));
            }

            String appValue = param.get("appValue").toString();
            dto.setAppValue(Integer.parseInt(appValue));
            result= formWorkersProposalService.doApprove(dto,param);
        }

        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 统计页面 首页
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_p_wp")
    public ModelAndView indeCount(Model  model,HttpServletRequest request) {
        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);

        if (!"02YZBGS".equals(empSessionDto.getDeptNo()) && !empSessionDto.getDeptNo().equals("01YLD"))//TODO 部门编码 hotfix
        {
            List<DeptmentDto> applyDeptmentDtos = new ArrayList<>();
            DeptmentDto dpt = new DeptmentDto();
            dpt.setId(empSessionDto.getDeptId());
            dpt.setDeptName(empSessionDto.getDeptName());
            applyDeptmentDtos.add(dpt);
            model.addAttribute("depts", applyDeptmentDtos);
        }else{
            List<DeptmentDto> deptmentDtos = deptmentService.getAllSimpleDept();
            model.addAttribute("depts", deptmentDtos);
        }
        return view(layout, "partyGroup/syy_dq_lc07_a_count", model);
    }
    /**
     * 统计页面查询
     * @param criteria
     * @param response
     */
    @RequestMapping(value = "ajax_query_workerProposal")
    public void queryAll(WorkersProposalCriteria criteria,HttpServletResponse response,HttpServletRequest request){
        String proName = request.getParameter("proName");
        if (!StringUtils.isNullOrWhiteSpace(proName))
            criteria.setTitle(URI.deURI(proName));

        String comLeader = request.getParameter("comLeader");
        if (!StringUtils.isNullOrWhiteSpace(comLeader))
            criteria.setPerson(URI.deURI(comLeader));

        String dept_name = request.getParameter("dept_name");
        if (!StringUtils.isNullOrWhiteSpace(dept_name))
            criteria.setDeptName(URI.deURI(dept_name));

        String type = request.getParameter("p_type");
        if (!StringUtils.isNullOrWhiteSpace(type))
            criteria.setType(URI.deURI(type));
        String undertakeDept = request.getParameter("p_undertakeDept");
        if (!StringUtils.isNullOrWhiteSpace(undertakeDept))
            criteria.setUndertakeDept(URI.deURI(undertakeDept));


//        String begTime = request.getParameter("begTime");
//        if (!StringUtils.isNullOrWhiteSpace(begTime))
//            criteria.setBeginTime(DateHelper.parse(begTime, "yyyy-MM-dd"));
//        String enTime = request.getParameter("enTime");
//        if (!StringUtils.isNullOrWhiteSpace(enTime))
//            criteria.setEndTime(DateHelper.parse(enTime, "yyyy-MM-dd"));

        PagedResult<FormWorkersProposalDto> pagedResult=formWorkersProposalService.getPagesByFormWorkPro(criteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }


    /**
     * 单个查询 详情
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_workersProposal_countDetail")
    public ModelAndView queryCountDetail(Model model,HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormWorkersProposalDto dto = formWorkersProposalService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("beans", dto);
        }
        model.addAttribute("help", Helper.class);
        return view(layout,"partyGroup/syy_dq_lc07_a_count_detail",model);
    }

    /**
     * 职工提案 申请页面 附议人
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_partyGroup_lc07_mainPeople")
    @ResponseBody
    public ListResult<MultiSelectUserWithDeptDto> queryMainPeople(HttpServletRequest request){
        List<MultiSelectUserWithDeptDto> list = employeeService.getAllDeptEmp();
        return new ListResult<MultiSelectUserWithDeptDto>(list);
    }



}
