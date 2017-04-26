package com.sunesoft.lemon.webapp.controller.awards;

import com.sunesoft.lemon.deanery.StringCommHelper;
import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.prizewinner.application.PrizewinnerService;
import com.sunesoft.lemon.deanery.projectAchievement.application.ProjectAchievementService;
import com.sunesoft.lemon.deanery.projectAchievement.application.criteria.ProjectAchievementCriteria;
import com.sunesoft.lemon.deanery.projectAchievement.application.dtos.ProjectAchievementDto;
import com.sunesoft.lemon.deanery.projectAchievement.application.dtos.ProjectAchievementDto2;
import com.sunesoft.lemon.deanery.projectCG.application.ProjectResultService;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto1;
import com.sunesoft.lemon.fr.results.CommonResult;
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
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.demo.MultiSelectUserWithDept;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by user on 2016/7/5.
 */
@Controller
public class AwardsInfoController  extends Layout {
    @Autowired
    ProjectResultService projectResultService;

    @Autowired
    ProjectAchievementService projectAchievementService;
    @Autowired
    PrizewinnerService prizewinnerService;
    @Autowired
    EmployeeService  employeeService;
    @Autowired
    FormHeaderService headerService;
    @Autowired
    FormListService formListService;
    @Autowired
    UserSession us;
    @Autowired
    DeptmentService deptmentService;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

    @RequestMapping(value = "sra_u_awards")
    public ModelAndView carInfo(Model model){
        List<SpecialtyType> list=projectAchievementService.getAllSpecialtyType();
        model.addAttribute("SpecialtyTypes", list);
        return view(layout,"awards/awardsInfo",model);
    }

    @RequestMapping("ajax_awards_query_list")
    public void getEducationInfo(ProjectAchievementCriteria projectAchievementCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String projectName=URI.deURI(request.getParameter("projectName"));
        String nianduTime=URI.deURI(request.getParameter("nianduTime"));
        String projectNo=URI.deURI(request.getParameter("projectNo"));
        String assumeCompany=URI.deURI(request.getParameter("assumeCompany"));
        String specialtyType=URI.deURI(request.getParameter("specialtyType"));
        String beginTime=URI.deURI(request.getParameter("beginTime"));
        String endTime=URI.deURI(request.getParameter("endTime"));
        projectAchievementCriteria.setProjectName(projectName);
        projectAchievementCriteria.setNianduTime(nianduTime);
        projectAchievementCriteria.setProjectNo(projectNo);
        projectAchievementCriteria.setAssumeCompany(assumeCompany);
        projectAchievementCriteria.setSpecialtyType(specialtyType);
        projectAchievementCriteria.setBeginTime(beginTime);
        projectAchievementCriteria.setEndTime(endTime);
        PagedResult pagedResult = projectAchievementService.getCommonDrivers(projectAchievementCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    /*
    * 项目成果库修改
    * */
    @RequestMapping(value = "updateProjectAchievement")
    public ModelAndView updateProjectAchievement(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        ProjectAchievementDto projectAchievementDto=projectAchievementService.getById(id);
        projectAchievementDto.setBeginTime(sdf.format(projectAchievementDto.getBeginDate()));
        projectAchievementDto.setEndTime(sdf.format(projectAchievementDto.getEndDate()));
        model.addAttribute("beans",projectAchievementDto);

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
        model.addAttribute("empInfos", multiSelectUserWithDepts);
        model.addAttribute("views",Boolean.valueOf(false));
        return view(layout,"awards/updateProjectAchievement",model);
    }
    /*
        * 项目成果库详情
        * */
    @RequestMapping(value = "ProjectAchievementDetailInfo")
    public ModelAndView ProjectAchievementDetailInfo(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        ProjectAchievementDto projectAchievementDto=projectAchievementService.getById(id);
        projectAchievementDto.setBeginTime(sdf.format(projectAchievementDto.getBeginDate()));
        projectAchievementDto.setEndTime(sdf.format(projectAchievementDto.getEndDate()));
        model.addAttribute("beans",projectAchievementDto);

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
        model.addAttribute("empInfos", multiSelectUserWithDepts);
        model.addAttribute("views",Boolean.valueOf(true));
        return view(layout,"awards/updateProjectAchievement",model);
    }
    @RequestMapping(value="update_projectAchievement")
    public void update_projectAchievement(ProjectAchievementDto projectAchievementDto, HttpServletRequest request, HttpServletResponse response){
        CommonResult result= projectAchievementService.saveByDto(projectAchievementDto);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }
    /**
     * 修改增加页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addProjectResultsInfoForm")
    public ModelAndView addProjectResultInfo(Model model,HttpServletRequest request){
        List<EmpDto> list=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        model.addAttribute("emp",list);
        model.addAttribute("winresultclassify",static_common.WIN_RESULT_CLASSIFY);
        List<String> WIN_LEVEL = new ArrayList<String>(static_common.WIN_LEVEL);
       // System.out.println(WIN_LEVEL);
        model.addAttribute("winlevel",WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);
        List<DeptmentDto> deptmentDtos= deptmentService.getByDeptsIds();
        model.addAttribute("deptment", deptmentDtos);
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 =Long.parseLong(id);
            ProjectResultDto projectResultDto=projectResultService.getById(id1);
            Date d = projectResultDto.getWin_Date();
            projectResultDto.setWin_Date_Str(sdf.format(d));
            model.addAttribute("beans", projectResultDto);

        }
        return view(layout,"awards/_addProjectResultsInfoForm",model);
    }

    /**
     * 详情页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "detailProjectResultInfoForm")
    public ModelAndView detailProjectResultInfo(Model model,HttpServletRequest request){
        String json = JsonHelper.toJson(static_common.SPECIALTYTYPE);
        model.addAttribute("specialType_fb",json);
        model.addAttribute("specialType",static_common.SPECIALTYTYPE);
        model.addAttribute("winresultclassify",static_common.WIN_RESULT_CLASSIFY);
       // model.addAttribute("winlevel",static_common.WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 =Long.parseLong(id);
            ProjectResultDto projectResultDto=projectResultService.getById(id1);
            Date d = projectResultDto.getWin_Date();
            projectResultDto.setWin_Date_Str(sdf.format(d));
            model.addAttribute("beans", projectResultDto);
            String []peoples=projectResultDto.getWinner_Info().split(",");
            model.addAttribute("peoples", peoples);
        }

        return view("awards/_detailProjectResultsInfoForm",model);
    }
//删除
    @RequestMapping(value = "ajax_deleteProjectResult")
    public void deleteProjectResult(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = projectResultService.deleteProjectResult(listid.toArray(new Long[listid.size()]));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }

    //添加信息
    @RequestMapping(value = "ajax_add_update_projectResult", method = RequestMethod.POST)
    public void addOrUpdateProjectResult( HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String win_Result_Name=request.getParameter("win_Result_Name");
        String winner_Info=request.getParameter("winner_Info1");
//        String winner_Info=request.getParameter("def");
        String issuing_Unit=request.getParameter("issuing_Unit");
        String certif_No=request.getParameter("certif_No");
      String win_Result_type=request.getParameter("win_Result_type");
     /*   String win_Project=request.getParameter("win_Project");*/
        String win_Result_Classify=request.getParameter("win_Result_Classify");
        String win_Level=request.getParameter("win_Level");
        String win_Grade=request.getParameter("win_Grade");
        String win_Date=request.getParameter("win_Date");
        String  is_Cooperate_Result_Str=request.getParameter("is_Cooperate_Result");
        ProjectResultDto projectResultDto = new ProjectResultDto();

        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 = Long.parseLong(id);
            projectResultDto.setId(id1);
        }
        projectResultDto.setWin_Result_Name(win_Result_Name);
        projectResultDto.setWinner_Info(winner_Info);
        projectResultDto.setIssuing_Unit(issuing_Unit);
        projectResultDto.setCertif_No(certif_No);
        projectResultDto.setWin_Result_type(win_Result_type);
       /* projectResultDto.setWin_Project(Long.parseLong(win_Project));*/
        projectResultDto.setWin_Result_Classify(win_Result_Classify);
        projectResultDto.setWin_Level(win_Level);
        projectResultDto.setWin_Grade(win_Grade);

        Boolean is_Cooperate_Result_Str1 =Boolean.valueOf(is_Cooperate_Result_Str).booleanValue();
       // System.out.println(is_Cooperate_Result_Str1);
        projectResultDto.setIs_Cooperate_Result(is_Cooperate_Result_Str1);
        try {
            Date win_Date1= sdf.parse(win_Date);
            projectResultDto.setWin_Date(win_Date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = projectResultService.addOrUpdateProjectResult(projectResultDto) > 0 ? "success" : "error";
        System.out.println(result);
        AjaxResponse.write(response, "text", result);
    }



/*
* 申请页面*/
    @RequestMapping(value = "syy_gy_lc01_a")
    public ModelAndView addProjectResult(Model model,HttpServletRequest request){
        FormListDto dto =formListService.getFormListInfo("SYY_GY_LC01");
//        String formNo = request.getParameter("formNo");
//        String formKind = request.getParameter("formKind");
//        String formKindName =request.getParameter("formKindName")==null?null: URI.deURI(request.getParameter("formKindName"));
//        model.addAttribute("formNo", formNo);
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        List<DeptmentDto> deptmentDtos= deptmentService.getByDeptsIds();
        model.addAttribute("deptment", deptmentDtos);
        List<EmpDto> list=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        model.addAttribute("emp",list);
        model.addAttribute("winresultclassify",static_common.WIN_RESULT_CLASSIFY);
        List<String> WIN_LEVEL = new ArrayList<String>(static_common.WIN_LEVEL);
        // System.out.println(WIN_LEVEL);
        model.addAttribute("winlevel",WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 =Long.parseLong(id);
            ProjectResultDto projectResultDto=projectResultService.getById(id1);
            Date d = projectResultDto.getWin_Date();
            projectResultDto.setWin_Date_Str(sdf.format(d));
            model.addAttribute("beans", projectResultDto);
        }
        return view(applyLayout,"awards/syy_gy_lc01_a",model);
    }

    /*
    * 通过流程新增数据
    * */
    @RequestMapping(value = "add_update_projectResult", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addupdateprojectResult(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String win_Result_Name=request.getParameter("win_Result_Name");
        String winner_Info=request.getParameter("winner_Info1");
        String issuing_Unit=request.getParameter("issuing_Unit");
        String certif_No=request.getParameter("certif_No");
        String win_Result_type=request.getParameter("win_Result_type");
     /*   String win_Project=request.getParameter("win_Project");*/
        String win_Result_Classify=request.getParameter("win_Result_Classify");
        String win_Level=request.getParameter("win_Level");
        String win_Grade=request.getParameter("win_Grade");
        String win_Date=request.getParameter("win_Date");
        String  is_Cooperate_Result_Str=request.getParameter("is_Cooperate_Result");

//        String formNo = request.getParameter("formNo");
        String formKind = request.getParameter("formKind");
        String formKindName = URI.deURI(request.getParameter("formKindName"));
        String applyer=request.getParameter("applyer");
        String applyerName=request.getParameter("applyerName");
        String deptId=request.getParameter("deptId");
        String deptName=request.getParameter("deptName");

        ProjectResultDto projectResultDto = new ProjectResultDto();

        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 = Long.parseLong(id);
            projectResultDto.setId(id1);
        }
        projectResultDto.setWin_Result_Name(win_Result_Name);
        projectResultDto.setWinner_Info(winner_Info);
        projectResultDto.setIssuing_Unit(issuing_Unit);
        projectResultDto.setCertif_No(certif_No);
        projectResultDto.setWin_Result_type(win_Result_type);
       /* projectResultDto.setWin_Project(Long.parseLong(win_Project));*/
        projectResultDto.setWin_Result_Classify(win_Result_Classify);
        projectResultDto.setWin_Level(win_Level);
        projectResultDto.setWin_Grade(win_Grade);

        Boolean is_Cooperate_Result_Str1 =Boolean.valueOf(is_Cooperate_Result_Str).booleanValue();
        // System.out.println(is_Cooperate_Result_Str1);
        projectResultDto.setIs_Cooperate_Result(is_Cooperate_Result_Str1);
//        projectResultDto.setFormNo(Long.parseLong(formNo));
        projectResultDto.setFormKind(formKind);
        projectResultDto.setFormKindName(formKindName);
        projectResultDto.setApplyer(Long.parseLong(applyer));
        projectResultDto.setApplyerName(applyerName);
        projectResultDto.setDeptId(Long.parseLong(deptId));
        projectResultDto.setDeptName(deptName);
        try {
            Date win_Date1= sdf.parse(win_Date);
            projectResultDto.setWin_Date(win_Date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        String result = projectResultService.addOrUpdateProjectResult2(projectResultDto) > 0 ? "success" : "error";
//        System.out.println(result);
//        AjaxResponse.write(response, "text", result);
        CommonResult commonResult=projectResultService.addOrUpdateProjectResult2(projectResultDto);
        CommonResult result = projectResultService.submitForm(commonResult.getId(),projectResultDto.getFormKind());
        return  result;
    }
/*
* 第一步审核页面
* */
    @RequestMapping(value = "syy_gy_lc01_view1")
    public ModelAndView formProjectApplyView(HttpServletRequest request,Model model) {

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("viewOnly",viewOnly);
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<EmpDto> list=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        model.addAttribute("emp",list);
        model.addAttribute("winresultclassify",static_common.WIN_RESULT_CLASSIFY);
        List<String> WIN_LEVEL = new ArrayList<String>(static_common.WIN_LEVEL);
        // System.out.println(WIN_LEVEL);
        model.addAttribute("winlevel",WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);
//        String id = dto.getId().toString();
        if (formNo!=null && formNo!=0L) {
//            Long id1 =Long.parseLong(id);
            ProjectResultDto projectResultDto=projectResultService.getProjectResultByFormNo(formNo);

            Date d = projectResultDto.getWin_Date();
            projectResultDto.setWin_Date_Str(sdf.format(d));
            String []peoples=projectResultDto.getWinner_Info().split(",");
            model.addAttribute("peoples", peoples);
            model.addAttribute("beans", projectResultDto);
        }
//        return view(layout,"awards/_addProjectResultsInfoForm",model);
        System.out.print(viewOnly.equals("false"));
        return view(viewOnly.equals("false")?formLayout:formViewLayout,  "awards/syy_gy_lc01_view1", model);
    }
/*
* 第一步审核操作*/
    @RequestMapping(value ="gy_lc01_approve1")
    @ResponseBody
    public CommonResult approve1(ApproveFormDto dto,HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String win_Result_Name=request.getParameter("win_Result_Name");
        String winner_Info=request.getParameter("winner_Info1");
        String issuing_Unit=request.getParameter("issuing_Unit");
        String certif_No=request.getParameter("certif_No");
        String win_Result_type=request.getParameter("win_Result_type");
     /*   String win_Project=request.getParameter("win_Project");*/
        String win_Result_Classify=request.getParameter("win_Result_Classify");
        String win_Level=request.getParameter("win_Level");
        String win_Grade=request.getParameter("win_Grade");
        String win_Date=request.getParameter("win_Date");
        String  is_Cooperate_Result_Str=request.getParameter("is_Cooperate_Result");
        String leaderOpinion=request.getParameter("leaderOpinion");
        ProjectResultDto projectResultDto = new ProjectResultDto();

        projectResultDto.setFormNo(StringCommHelper.nullToLong(request.getParameter("formNo")));
        projectResultDto.setFormKind(StringCommHelper.nullToString(request.getParameter("formKind")));

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 = Long.parseLong(id);
            projectResultDto.setId(id1);
        }
        projectResultDto.setWin_Result_Name(win_Result_Name);
        projectResultDto.setWinner_Info(winner_Info);
        projectResultDto.setIssuing_Unit(issuing_Unit);
        projectResultDto.setCertif_No(certif_No);
        projectResultDto.setWin_Result_type(win_Result_type);
       /* projectResultDto.setWin_Project(Long.parseLong(win_Project));*/
        projectResultDto.setWin_Result_Classify(win_Result_Classify);
        projectResultDto.setWin_Level(win_Level);
        projectResultDto.setWin_Grade(win_Grade);
        Boolean is_Cooperate_Result_Str1 =Boolean.valueOf(is_Cooperate_Result_Str).booleanValue();
        // System.out.println(is_Cooperate_Result_Str1);
        projectResultDto.setIs_Cooperate_Result(is_Cooperate_Result_Str1);
        projectResultDto.setLeaderOpinion(leaderOpinion);
        try {
            Date win_Date1= sdf.parse(win_Date);
            projectResultDto.setWin_Date(win_Date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CommonResult result=projectResultService.addOrUpdateProjectResult3(projectResultDto,dto);
        return  result;
    }
    /*
    * 第二步审核页面*/
    @RequestMapping(value = "syy_gy_lc01_view2")
    public ModelAndView formProjectApplyView2(HttpServletRequest request,Model model) {

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("viewOnly",viewOnly);
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        List<EmpDto> list=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        model.addAttribute("emp",list);
        model.addAttribute("winresultclassify",static_common.WIN_RESULT_CLASSIFY);
        List<String> WIN_LEVEL = new ArrayList<String>(static_common.WIN_LEVEL);
        // System.out.println(WIN_LEVEL);
        model.addAttribute("winlevel",WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);
//        String id = dto.getId().toString();
        if (formNo!=null && formNo!=0L) {
//            Long id1 =Long.parseLong(id);
            ProjectResultDto projectResultDto=projectResultService.getProjectResultByFormNo(formNo);

            Date d = projectResultDto.getWin_Date();
            projectResultDto.setWin_Date_Str(sdf.format(d));
            String []peoples=projectResultDto.getWinner_Info().split(",");
            model.addAttribute("peoples", peoples);
            model.addAttribute("beans", projectResultDto);
        }
//        return view(layout,"awards/_addProjectResultsInfoForm",model);
        return view(viewOnly.equals("false")?formLayout:formViewLayout,  "awards/syy_gy_lc01_view2", model);
    }
    /*
    * 第二步审核操作*/
    @RequestMapping(value ="gy_lc01_approve2")
    @ResponseBody
    public CommonResult approve2(ApproveFormDto dto,HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String win_Result_Name=request.getParameter("win_Result_Name");
        String winner_Info=request.getParameter("winner_Info1");
        String issuing_Unit=request.getParameter("issuing_Unit");
        String certif_No=request.getParameter("certif_No");
        String win_Result_type=request.getParameter("win_Result_type");
     /*   String win_Project=request.getParameter("win_Project");*/
        String win_Result_Classify=request.getParameter("win_Result_Classify");
        String win_Level=request.getParameter("win_Level");
        String win_Grade=request.getParameter("win_Grade");
        String win_Date=request.getParameter("win_Date");
        String  is_Cooperate_Result_Str=request.getParameter("is_Cooperate_Result");
        String leaderOpinion=request.getParameter("leaderOpinion");
        String deptOpinion=request.getParameter("deptOpinion");
        ProjectResultDto projectResultDto = new ProjectResultDto();
        projectResultDto.setFormNo(StringCommHelper.nullToLong(request.getParameter("formNo")));
        projectResultDto.setFormKind(StringCommHelper.nullToString(request.getParameter("formKind")));
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 = Long.parseLong(id);
            projectResultDto.setId(id1);
        }
        projectResultDto.setWin_Result_Name(win_Result_Name);
        projectResultDto.setWinner_Info(winner_Info);
        projectResultDto.setIssuing_Unit(issuing_Unit);
        projectResultDto.setCertif_No(certif_No);
        projectResultDto.setWin_Result_type(win_Result_type);
        projectResultDto.setWin_Result_Classify(win_Result_Classify);
        projectResultDto.setWin_Level(win_Level);
        projectResultDto.setWin_Grade(win_Grade);
        Boolean is_Cooperate_Result_Str1 =Boolean.valueOf(is_Cooperate_Result_Str).booleanValue();
        projectResultDto.setIs_Cooperate_Result(is_Cooperate_Result_Str1);
        projectResultDto.setLeaderOpinion(leaderOpinion);
        projectResultDto.setDeptOpinion(deptOpinion);
        try {
            Date win_Date1= sdf.parse(win_Date);
            projectResultDto.setWin_Date(win_Date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CommonResult result=projectResultService.addOrUpdateProjectResult3(projectResultDto,dto);
        return  result;
    }



    /**
     * 导出数据
     */
    @ResponseBody
    @RequestMapping("ajax_syy_awardsInfo_down")
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList();
        List<ProjectAchievementDto2> projectAchievementDto2s = projectAchievementService.getAcievementList();
        for(int i=0;i<projectAchievementDto2s.size();i++) {
            list.add(projectAchievementDto2s.get(i));
        }
        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"年度","项目编号","项目名称","申报开始时间","申报结束时间","获奖时间","项目长","组员","专业分类",
                "主要完成单位","主要协作单位","主题词","三新一性","内容摘要","专利类型及数量","级别","等级","颁发单位"};
        expotExcel.doExportExcel("成果信息表", header, list, "yyyy-MM-dd", response);

    }


}

