package com.sunesoft.lemon.webapp.controller.projectInfo;

import com.sunesoft.lemon.deanery.car.application.ItemProjectService;
import com.sunesoft.lemon.deanery.car.application.SpecialtyTypeService;
import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.car.domain.ItemProject;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApply;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.productionData.ProductionDateDto1;
import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectApplyService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectExecutionService;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectExecutoryDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectAcceptance;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecution;
import com.sunesoft.lemon.deanery.scientificRPKU.ScienticRPKUService;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUCriteria;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto1;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/6/28.
 */
@Controller
public class ProjectController extends Layout {

    @Autowired
    ScientificResearchProjectService srpService;
    @Autowired
    ScienticRPKUService scienticRPKUService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    FormProjectApplyService formProjectApplyService;

    @Autowired
    FormProjectExecutionService formProjectExecutionService;
    @Autowired
    SpecialtyTypeService specialtyTypeService;
    @Autowired
    ItemProjectService itemProjectService;

    @RequestMapping(value = "sra_p_project")
    public ModelAndView index(Model model, HttpServletRequest request) {
        List<DeptmentDto> dept= deptmentService.getAllDept();
        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
        List<ItemProject> itemsList= itemProjectService.itemsList();
        model.addAttribute("alltype",alltype);
        model.addAttribute("itemsList",itemsList);
        model.addAttribute("dept",dept);
        return view(layout, "projectInfo/projectInfo", model);
    }

    @RequestMapping(value = "ajax_project_query_list")
    public void queryProjectInfo(ScientificRPKUCriteria criteria, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //中文字符转换，时间类型需要进行中文字符的转换（看页面需要）
        String projectName =URI.deURI(request.getParameter("projectName"));
        String projectNo =URI.deURI(request.getParameter("projectNo"));
        String assumeCompany =URI.deURI(request.getParameter("assumeCompany"));
        String projectType =URI.deURI(request.getParameter("projectType"));
        String projectYXStatus =URI.deURI(request.getParameter("projectYXStatus"));
        String deptName =URI.deURI(request.getParameter("deptName"));
        criteria.setProjectName(projectName);
        criteria.setAssumeCompany(assumeCompany);
        criteria.setProjectNo(projectNo);
        if(projectType!=null && !projectType.equals("")){
            criteria.setProjectType(projectType);
        }
        if(deptName!=null && !deptName.equals("")){
            criteria.setDeptName(deptName);
        }
        criteria.setProjectYXStatus(projectYXStatus);
        String niandu = request.getParameter("niandu_Str");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");
        try {
            //判断开始时间和结束时间不能为空
            if (!StringUtils.isNullOrWhiteSpace(niandu))
                criteria.setNiandu(sf.parse(niandu));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PagedResult<ScientificRPKUDto> projectDtoPagedResult = scienticRPKUService.getScientificResearchProjects(criteria);
        String json = JsonHelper.toJson(projectDtoPagedResult);
        AjaxResponse.write(response, json);

    }

    @RequestMapping(value = "_addProjectInfoForm")
    public ModelAndView addProjectInfo(Model model) {
        List<EmpDto> empDtos = employeeService.getAllEmps();
        model.addAttribute("beans", empDtos);
        model.addAttribute("sp", static_common.SPECIALTYTYPE);
        model.addAttribute("pType", static_common.PROJECTTYPE);

        //新增，返回页面所属部门名称
        List<DeptmentDto> deptmentDtos = deptmentService.getByDeptsIds();
        model.addAttribute("deptName", deptmentDtos);

        return view(layout, "projectInfo/_addProjectInfoForm", model);
    }



    @ResponseBody
    @RequestMapping("lc03_info")
    public FormProjectExecutoryDto formProjectExecutory(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        FormProjectExecutoryDto formProjectExecutoryDto=null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            formProjectExecutoryDto = formProjectExecutionService.getFormProjectExecution(Long.parseLong(id));
        }
        return formProjectExecutoryDto;
    }

    @ResponseBody
    @RequestMapping("project_open_info")
    public FormProjectApplyDto formProjectOpen(Model model,HttpServletRequest request){
        String formNo = request.getParameter("formNo1");
        FormProjectApplyDto formProjectApplyDto=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            formProjectApplyDto=formProjectApplyService.getFormByFormNo(Long.parseLong(formNo));
        }
        return formProjectApplyDto;
    }

    @RequestMapping(value = "ajax_query_ex")
    public ModelAndView queryEx(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        String formNo=request.getParameter("formNo");
        model.addAttribute("proId",id);
        model.addAttribute("formNoEx",formNo);
        return view(layout,"projectInfo/syy_kg_lc03_info",model);
    }
    //删除
    @RequestMapping(value = "ajax_deleteProjectInfo")
    public void deleteProjectInfo(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = srpService.delScientificResearchProject(listid.toArray(new Long[listid.size()]));//格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }

/*****详情页方法链接**********************************************************************************************/
@RequestMapping(value = "detailInfo")
public ModelAndView detailInfoProject(Model model, HttpServletRequest request) {
    String id = request.getParameter("id");
    String projectID = request.getParameter("projectID");
        model.addAttribute("id", id);
        model.addAttribute("projectID", projectID);
    return view(layout,"/projectInfo/detailInfo", model);
}
// tab
    @ResponseBody
    @RequestMapping(value = "detailInfo1")
    public ScientificRPKUDto detailInfoProject1(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        //String projectID = request.getParameter("projectID");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            ScientificRPKUDto dto=scienticRPKUService.getByProjectIdDto(Long.parseLong(id));
            String groups = dto.getGroupMembers();
            model.addAttribute("groupMember", groups);
            Employee empLeadName =srpService.getNameByName(dto.getLeader());
            dto.setLeaderName(empLeadName.getName());
            if(dto.getDeputy()!=null){
                Employee empDeptName =srpService.getNameByName(dto.getDeputy());
             //   dto.setDeputyName(empDeptName.getName());
            }
            return dto ;
        }else{
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("detailProjectOpen")
    public FormProjectApply detailProjectOpen(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        String projectID = request.getParameter("projectID");
        if (!StringUtils.isNullOrWhiteSpace(projectID)) {
            ScientificResearchProjectDto dto = srpService.getScientificResearchProject(Long.parseLong(projectID));
            FormProjectApply formProjectApply=srpService.getIdByProjectNo1(dto.getProjectNo());
            formProjectApply.setFiles(null);
            formProjectApply.setFormOpenProjectFiles(null);

            return  formProjectApply;
        }else{
            FormProjectApply ss=new FormProjectApply();
            return ss;
        }
    }

    @ResponseBody
    @RequestMapping("detailProjectCheck")
    public FormProjectExecution detailProjectCheck(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        String projectID = request.getParameter("projectID");
        if (!StringUtils.isNullOrWhiteSpace(projectID)) {
            ScientificResearchProjectDto dto = srpService.getScientificResearchProject(Long.parseLong(projectID));
            FormProjectExecution formProjectApply=srpService.getIdByProjectNo3(dto.getProjectNo());
           formProjectApply.setFormOpenProjectFiles(null);
            formProjectApply.setExecutionFileList(null);
            return  formProjectApply;
        }else{
            FormProjectExecution ss=new FormProjectExecution();
            return ss;
        }
    }

    @ResponseBody
    @RequestMapping("detailProjectDelay")
    public FormDelayApply detailProjectDelay(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        String projectID = request.getParameter("projectID");
        if (!StringUtils.isNullOrWhiteSpace(projectID)) {
            ScientificResearchProjectDto dto = srpService.getScientificResearchProject(Long.parseLong(projectID));
            FormDelayApply formDelayApply=srpService.getIdByProjectNo2(dto.getProjectNo());
            if(formDelayApply!=null){
                formDelayApply.setDelayTimes_str(DateHelper.formatDate(  formDelayApply.getDelayTimes(),"yyyy-MM-dd"));
            }
            formDelayApply.setFormOpenProjectFiles(null);
            formDelayApply.setFileId(null);
            formDelayApply.setFileName(null);
            return  formDelayApply;
        }else{
            FormDelayApply ss=new FormDelayApply();
            return ss;
        }
    }


    @ResponseBody
    @RequestMapping("detailProjectAcceptance")
    public FormProjectAcceptance detailProjectDeliver(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        String projectID = request.getParameter("projectID");
        if (!StringUtils.isNullOrWhiteSpace(projectID)) {
            ScientificResearchProjectDto dto = srpService.getScientificResearchProject(Long.parseLong(projectID));
            FormProjectAcceptance formProjectAcceptance=srpService.getIdByProjectNo4(dto.getProjectNo());
            formProjectAcceptance.setFormAcceptanceProjectFile(null);
            return  formProjectAcceptance;
        }else{
            FormProjectAcceptance ss=new FormProjectAcceptance();
            return ss;
        }
    }

    @ResponseBody
    @RequestMapping("detailProjectDeliver")
    public FormDeliverApply detailProjectAcceptance(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        String projectID = request.getParameter("projectID");
        if (!StringUtils.isNullOrWhiteSpace(projectID)) {
            ScientificResearchProjectDto dto = srpService.getScientificResearchProject(Long.parseLong(projectID));
            FormDeliverApply formDeliverApply=srpService.getIdByProjectNo5(dto.getProjectNo());
            formDeliverApply.setFormDeliverApplyFiles(null);
            return  formDeliverApply;
        }else{
            FormDeliverApply ss=new FormDeliverApply();
            return ss;
        }
    }

   /**
    * 导出数据
    */
    @ResponseBody
    @RequestMapping("ajax_syy_projectInfo_down")
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList();
        List listid = new ArrayList();
        String ids = request.getParameter("formNos");
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        List<ScientificRPKUDto1> scientificRPKUDto1 = scienticRPKUService.getAllScientificKu3();
        for(ScientificRPKUDto1 ss:scientificRPKUDto1){
            int index = scientificRPKUDto1.indexOf(ss);
            ss.setIndex(index+1);
            list.add(ss);
        }

        /*for(int i=0;i<scientificRPKUDto1.size();i++) {
            list.add(scientificRPKUDto1.get(i));
        }*/
        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"序号","项目、课(专)题名称","国家或集团编号","项目类别","油田公司配套","专业主管部门","应用专业类型","新开或接转","研究目的及意义",
                "研究内容","预期成果和技术经济指标","进度安排","经费预算（万元）","承担单位","参加单位","开始时间","结束时间","项目负责人","备注"};
        expotExcel.doExportExcel("项目信息表", header, list, "yyyy-MM-dd", response);

    }
}
