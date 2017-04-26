package com.sunesoft.lemon.webapp.controller.projectPlan;

import com.sunesoft.lemon.deanery.projectAchievement.application.dtos.ProjectAchievementDto2;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.ProjectPlanService;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanOutput;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.factory.UploadProjectPlan;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputRepository;
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
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.demo.MultiSelectUserWithDept;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
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
import java.util.ArrayList;
import java.util.List;


/**
 * Created by pxj on 2016/9/9.
 */
@Controller
public class ProjectPlanController  extends Layout {

    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    ProjectPlanInputRepository projectPlanInputRepository;

    @RequestMapping(value = "sra_p_plan")
    public ModelAndView sra_p_plan(Model model){
        return view(layout, "projectInfo/projectPlan", model);
    }

    @RequestMapping(value = "inputProjectPlanModel")
    public ModelAndView inputProjectPlanModel(Model model){
        return view(layout,"projectInfo/inputProjectPlanModel", model);
    }

    /***
     * 项目计划导入
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "inputProjectPlan")
    public void inputProjectPlan(HttpServletRequest request,HttpServletResponse response) throws IOException{
        CommonResult result=null;
        //List<ProjectPlanInputDate> projectPlanInputDates= projectPlanInputRepository.queryProjectPlan();
        String originalFilename=null;
        List<MultipartFile> myfiles=((DefaultMultipartHttpServletRequest) request).getFiles("filename");
        for(MultipartFile myfile:myfiles){
            if (myfile.isEmpty()){
                result=new CommonResult(false,"请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return ;
            }else
            {
                originalFilename=myfile.getOriginalFilename();
                try {
                    UploadProjectPlan factory=new UploadProjectPlan();
                    ListResult<ProjectPlanInput> dto=factory.readExcel(myfile.getInputStream());
                    if(dto.getIsSuccess()){
                        if(judgeProjectNumber(dto)){
                            result = projectPlanService.save(dto.getItems());
                        }else {
                            result=new CommonResult(false,"项目编号重复,无法导入！");
                        }
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
    public boolean judgeProjectNumber(ListResult<ProjectPlanInput> dto){
        List<ProjectPlanInput> projectPlanInputDates= projectPlanService.queryProjectPlan();
        for(ProjectPlanInput plan:projectPlanInputDates){
            for(int i=0;i<dto.getItems().size();i++){
                if(plan.getProjectPlan_Number().equals(dto.getItems().get(i).getProjectPlan_Number())){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 导出项目计划模板
     * @param response
     */
    @RequestMapping(value = "outputProjectPlan")
    public void outputProjectPlan(HttpServletResponse response){
        List list=new ArrayList<>();
        ProjectPlanOutput output=new ProjectPlanOutput();
        output.setProjectPlan_InputYear("项目计划时间 如：2016");
        output.setProjectPlan_Number("项目编号");
        output.setProjectPlan_Name("名称");
        output.setProjectPlan_Content("研究内容");
        output.setProjectPlan_BearUnit("承担单位");
        output.setProjectPlan_ParticipatingUnit("参加单位");
        output.setStartTime("项目开始时间如：(年月日)2016-09-03");
        output.setEndTime("项目终止时间如：(年月日)2016-09-03");
        //output.setProjectPlan_StartEndTime("项目启止时间如2016-2018");
        output.setProjectPlan_Manager("负责人");
       // output.setProjectPlan_Email("负责人邮箱");
        list.add(output);
        List<EmpDto>  empDtos =employeeService.getAllEmps();
        List list1 =new ArrayList();
        list1.add(empDtos);
        String [] email=new String[empDtos.size()];
        for(int i=0;i<empDtos.size();i++){
            email[i]=empDtos.get(i).getEmail();
        }//当下拉框中数据长度>255
        ExpotExcel<ProjectPlanOutput> expotExcel = new ExpotExcel<>();
        String [] header = new String[]{"时间","项目编号","项目名称","研究内容、技术经济指标及项目进度安排","承担单位",
                "参加单位","项目起始时间","项目结束时间","负责人","负责人邮箱"};
        try {
           expotExcel.doExportExcel("项目计划导入模板",header,list,"yyyy-MM-dd",response);
          //expotExcel.doExportListExcel("项目计划导入模板",header,list,email,"yyyy-MM-dd",response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 项目计划list列表
     * @param projectResultCriteria ProjectPlanInput的dto
     * @param request
     * @param response
     * @param model
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "ajax_projectplan_query_list")
    public void ajax_projectplan_query_list(ProjectPlanInput projectResultCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String  projectPlan_Name= request.getParameter("projectPlan_Name");
       // projectResultCriteria.setWin_Level(URI.deURI(win_Level));
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
    /*    try {
            if(!StringUtils.isNullOrWhiteSpace(beginTime)){
                Date beginTime1=sdf.parse(beginTime);
                projectResultCriteria.setWin_begin_date(beginTime1);
            }
            Date endTime1=sdf.parse(endTime);
            projectResultCriteria.setWin_end_date(endTime1);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        PagedResult pagedResult = projectPlanService.getProjectPlan(projectResultCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }
    @Autowired
    EmployeeService employeeService;

    @Autowired
    DeptmentService deptmentService;
    @RequestMapping(value = "addProjectPlanInfoForm")
    public ModelAndView _addProjectPlanInfoForm(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
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

            Long planId =Long.parseLong(id);
            ProjectPlanInput projectPlanInput=projectPlanService.getByIdProjectPlanInput(planId);
            projectPlanInput.setProjectPlan_InputYear_Str(DateHelper.formatDate(projectPlanInput.getProjectPlan_InputYear(),"yyyy"));
            model.addAttribute("projectPlanStartTime",DateHelper.formatDate(projectPlanInput.getProjectPlan_StartTime(),"yyyy-MM-dd"));
            model.addAttribute("projectPlanEndTime",DateHelper.formatDate(projectPlanInput.getProjectPlan_EndTime(),"yyyy-MM-dd"));
            model.addAttribute("beans", projectPlanInput);
            model.addAttribute("views",Boolean.valueOf(false));
        }
        return view(layout,"projectInfo/_addProjectPlanInfoForm",model);
    }
    @RequestMapping(value="ajax_add_update_projectPlan")
    public void ajax_add_update_projectPlan(ProjectPlanInput projectPlanInput,HttpServletRequest request,HttpServletResponse response){
        if (!StringUtils.isNullOrWhiteSpace(projectPlanInput.getId().toString())) {
            projectPlanInput.setProjectPlan_InputYear(DateHelper.parse(projectPlanInput.getProjectPlan_InputYear_Str(),"yyyy"));
            projectPlanInput.setProjectPlan_StartTime(DateHelper.parse(projectPlanInput.getStartTime_Str(),"yyyy-MM-dd"));
            projectPlanInput.setProjectPlan_EndTime(DateHelper.parse(projectPlanInput.getEndTime_Str(),"yyyy-MM-dd"));
        }
        String result = projectPlanService.addOrUpdateProjectPlan(projectPlanInput) > 0 ? "success" : "error";
        System.out.println(result);
        AjaxResponse.write(response, "text", result);
    }
    //详细
    @RequestMapping(value = "detailProjectPlanInfoForm")
    public ModelAndView _detailProjectPlanInfoForm(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long planId =Long.parseLong(id);
            ProjectPlanInput projectPlanInput=projectPlanService.getByIdProjectPlanInput(planId);
            projectPlanInput.setProjectPlan_InputYear_Str(DateHelper.formatDate(projectPlanInput.getProjectPlan_InputYear(),"yyyy"));
            model.addAttribute("beans", projectPlanInput);
            model.addAttribute("projectPlanStartTime",DateHelper.formatDate(projectPlanInput.getProjectPlan_StartTime(),"yyyy-MM-dd"));
            model.addAttribute("projectPlanEndTime",DateHelper.formatDate(projectPlanInput.getProjectPlan_EndTime(),"yyyy-MM-dd"));
//            List<PrizewinnerDto> list= prizewinnerService.getPrizeWinner(id1);
//            model.addAttribute("lists", list);
            model.addAttribute("views",Boolean.valueOf(true));
        }
       // return view("projectInfo/_detailProjectPlanInfoForm",model);
        return view(layout,"projectInfo/_addProjectPlanInfoForm",model);
    }
    //开题页面请求计划详细信息
    @RequestMapping(value = "ajax_Project_Plan",method = RequestMethod.GET)
    public ModelAndView ajax_Project_Plan(Model model,HttpServletRequest request,String projectNo){
        String projectNos = request.getParameter("projectNo");
        if (!StringUtils.isNullOrWhiteSpace(projectNo)) {
            //Long planId =Long.parseLong(id);
            ProjectPlanInput projectPlanInput=projectPlanService.getProjectPlanByProjectNo(projectNos);
            projectPlanInput.setProjectPlan_InputYear_Str(DateHelper.formatDate(projectPlanInput.getProjectPlan_InputYear(),"yyyy"));
            model.addAttribute("beans", projectPlanInput);
            model.addAttribute("projectPlanStartTime",DateHelper.formatDate(projectPlanInput.getProjectPlan_StartTime(),"yyyy-MM-dd"));
            model.addAttribute("projectPlanEndTime",DateHelper.formatDate(projectPlanInput.getProjectPlan_EndTime(),"yyyy-MM-dd"));
//            List<PrizewinnerDto> list= prizewinnerService.getPrizeWinner(id1);
//            model.addAttribute("lists", list);
            model.addAttribute("views",Boolean.valueOf(true));
        }
        // return view("projectInfo/_detailProjectPlanInfoForm",model);
        return view(layout,"projectInfo/_addProjectPlanInfoForm",model);
//        return view("projectInfo/_addProjectPlanInfoForm",model);
    }

    /**
     * 导出数据
     */
    @ResponseBody
    @RequestMapping("ajax_syy_planInfo_down")
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList();
        List<ProjectPlanOutput> projectPlanInputs = projectPlanService.getAllPlan();
        for(int i=0;i<projectPlanInputs.size();i++) {
            list.add(projectPlanInputs.get(i));
        }
        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"项目年度","项目编号","项目名称","研究内容","承担单位","参加单位","项目开始时间","项目结束时间","负责人"};
        expotExcel.doExportExcel("项目计划表", header, list, "yyyy-MM-dd", response);

    }

}
