package com.sunesoft.lemon.webapp.controller.partyGroup;

import com.sunesoft.lemon.ay.partyGroup.application.WorkAchievementsService;
import com.sunesoft.lemon.ay.partyGroup.application.WorkProjectService;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.*;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.CompetitionTypeService;
import com.sunesoft.lemon.ay.partyGroupForms.application.FormWorkAchievementsService;
import com.sunesoft.lemon.ay.partyGroupForms.application.FormWorkProjectService;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormInnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
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
import com.sunesoft.lemon.syms.eHr.application.dtos.MultiSelectUserWithDeptDto;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.Config.Config;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
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
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 劳动竞赛成果申报
 * Created by admin on 2016/9/3.
 */
@Controller
public class WorkAchievementsController extends Layout {


    @Autowired
    UserSession userSession;

    @Autowired
    FormListService formListService;

    @Autowired
    FormWorkProjectService formWorkProjectService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    WorkProjectService workProjectService;

    @Autowired
    FormWorkAchievementsService formWorkAchievementsService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    FileService fileService;

    @Autowired
    WorkAchievementsService workAchievementsService;

    @Autowired
    CompetitionTypeService typeService;

    /**
     * 成果申报 流程首页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "syy_dq_lc02_a")
    public ModelAndView index(HttpServletRequest request,Model model){
        FormListDto dto =formListService.getFormListInfo("SYY_DQ_LC02");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        //负责人申报项目，必须是负责人申报成果,其他人不能申报
        //成果一旦入库，就不能重复申请
        List<WorkProjectDto> list=workProjectService.getAllByWorkAchievement();

        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);
        if (list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getLeader().equals(empSessionDto.getName())){
                    list.remove(i);
                    i--;
                }
            }
        }
        model.addAttribute("beans",list);

        //专业主管部门
        List<DeptmentDto> depts=deptmentService.getAllDept();
        model.addAttribute("depts",depts);

        List<MultiSelectUserWithDeptDto> emps=employeeService.getAllDeptEmp();
        model.addAttribute("emps",emps);

        //修改
        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)){
            FormWorkProjectDto achievementDto=formWorkProjectService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("achievementDto",achievementDto);
            model.addAttribute("formNo",achievementDto.getFormNo());
        }

        //获取竞赛类别
        CompetitionTypeCriteria typeCriteria = new CompetitionTypeCriteria();
        PagedResult<com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionType> pagedResult1 = typeService.pages(typeCriteria);
        List<com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionType> competitionTypes = pagedResult1.getItems();
        model.addAttribute("competitionTypes",competitionTypes);

        return view(applyLayout,"partyGroup/syy_dq_lc02_a",model);
    }

    /**
     * 申报页面 根据劳动项目的名称查询项目信息
     */
    @RequestMapping(value = "ajax_query_workProject")
    @ResponseBody
    public WorkProjectDto queryWorkProjectData(HttpServletRequest request){
        String id = request.getParameter("id");
        WorkProjectDto workProjectDto=null;
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            workProjectDto=workProjectService.getById(Long.parseLong(id));
        }
        return workProjectDto;
    }

    /**
     * 申报页面 提交
     * @param request
     * @param formWorkProjectDto
     * @return
     */
    @RequestMapping(value = "ajax_add_update_workAchievements",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdateWorkAchievements(HttpServletRequest request,FormWorkProjectDto formWorkProjectDto){

        String hiddenProjectName = request.getParameter("hiddenProjectName");
        String project_id = request.getParameter("project_id");
        String time = request.getParameter("time");
        String hiddenJoinPeople = request.getParameter("hiddenJoinPeople");
        String hiddenType = request.getParameter("hiddenType");


        //是否有已上传的附件
        String oldFileId = request.getParameter("oldFileId");
        String oldFileName = request.getParameter("oldFileName");

        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);

        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName1");
        CommonResult result=null;
        for (MultipartFile myfile : myfiles) {

            try {
                String fileName = myfile.getOriginalFilename();
                String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                FileInfoDto fileInfoDto=new FileInfoDto();

                fileInfoDto.setFileName(fileName);

                fileInfoDto.setExtensions(extension);
                fileInfoDto.setInputStream(myfile.getInputStream());

                String id=fileService.upload(fileInfoDto);

                if(!StringUtils.isNullOrWhiteSpace(id)) {
                    if (StringUtils.isNullOrWhiteSpace(fileName)) {
                        formWorkProjectDto.setFileName(oldFileName);
                        formWorkProjectDto.setFileId(oldFileId);
                    }
                    else {
                        formWorkProjectDto.setFileName(fileName);
                        formWorkProjectDto.setFileId(id);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        formWorkProjectDto.setApplyer(empSessionDto.getId());
        formWorkProjectDto.setApplyerName(empSessionDto.getName());
        formWorkProjectDto.setDeptId(empSessionDto.getDeptId());
        formWorkProjectDto.setDeptName(empSessionDto.getDeptName());

        formWorkProjectDto.setProjectName(hiddenProjectName);
        if (!StringUtils.isNullOrWhiteSpace(time))
            formWorkProjectDto.setCpmpetitionTime(DateHelper.parse(time,"yyyy-MM-dd"));
        formWorkProjectDto.setFormWork_id(Long.valueOf(project_id));
        formWorkProjectDto.setCompetitionType(hiddenType);
        formWorkProjectDto.setJoinPeople(hiddenJoinPeople);

        CommonResult commonResult=formWorkProjectService.addByDto(formWorkProjectDto);
        return formWorkProjectService.submitForm(commonResult.getId(),formWorkProjectDto.getFormKind());

    }

    /**
     * 审核页面 查询
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_syy_dq_lc02_data")
    @ResponseBody
    public FormWorkProjectDto queryData(HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormWorkProjectDto formWorkProjectDto=formWorkProjectService.getFormByFormNo(Long.parseLong(formNo));
        formWorkProjectDto.setIsViewOnly(Boolean.valueOf(viewOnly));

        List<MultiSelectUserWithDeptDto> list=employeeService.getAllDeptEmp();
        formWorkProjectDto.setProject_empDtos(list);

        String score = formWorkProjectDto.getAchiOnlyMembersScores();
        if (!StringUtils.isNullOrWhiteSpace(score)) {
            String[] arr_score = score.split("//");
            formWorkProjectDto.setAchiOnlyScores(arr_score);
        }
        String suggestion = formWorkProjectDto.getAchiOnlyMembersAdvise();
        if (!StringUtils.isNullOrWhiteSpace(suggestion)) {
            String[] arr_suggestion = suggestion.split("//");
            formWorkProjectDto.setAchiOnlyAdvise(arr_suggestion);
        }

        return formWorkProjectDto;
    }


//    @RequestMapping(value = "sra_w_p")
//    public ModelAndView indexCount(Model model){
//        return view(layout,"partyGroup/syy_dq_lc02_a_count",model);
//    }
//
//    @RequestMapping(value = "ajax_query_workersachievement")
//    public void queryData(HttpServletResponse response,FormWorkProjectCriteria criteria){
//        PagedResult<FormWorkProjectDto> dtoPagedResult = formWorkProjectService.getAllFormsPages(criteria);
//        String json= JsonHelper.toJson(dtoPagedResult);
//        AjaxResponse.write(response,json);
//
//    }


    /**
     * 查看全部为审批申请 页面
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_workAch_fail_all")
    public ModelAndView failAll(Model model,HttpServletRequest request){
        List<DeptmentDto> deptmentDtos=deptmentService.getAllDept();
        model.addAttribute("depts",deptmentDtos);

        List<MultiSelectUserWithDeptDto> list = employeeService.getAllDeptEmp();
        model.addAttribute("beans",list);

        String step = request.getParameter("step");
        model.addAttribute("step",step);

        return view(applyLayout,"partyGroup/syy_dq_lc02_a_failAll",model);
    }

    /**
     * 查看全部未审批申请  formStatus=UA FormWorkProject数据
     * @param request
     * @param criteria
     * @param response
     */
    @RequestMapping(value = "ajax_query_failAll_workAchievement")
    public void queryFailAll(HttpServletRequest request,FormWorkProjectCriteria criteria,HttpServletResponse response){

        String proName = request.getParameter("proName");
        if (!StringUtils.isNullOrWhiteSpace(proName))
            criteria.setProjectName(URI.deURI(proName));

        String comLeader = request.getParameter("comLeader");
        if (!StringUtils.isNullOrWhiteSpace(comLeader))
            criteria.setLeader(URI.deURI(comLeader));

        String dept_name = request.getParameter("dept_name");
        if (!StringUtils.isNullOrWhiteSpace(dept_name))
            criteria.setDeptName(URI.deURI(dept_name));

        String compeType = request.getParameter("compeType");
        if (!StringUtils.isNullOrWhiteSpace(compeType))
            criteria.setCompetitionType(URI.deURI(compeType));

        PagedResult<FormWorkProjectDto> pagedResult=formWorkProjectService.getPagesFormWorkProjectDto(criteria);
        List<MultiSelectUserWithDeptDto> list= employeeService.getAllDeptEmp();
        for (FormWorkProjectDto dto:pagedResult.getItems()){
            dto.setProject_empDtos(list);
        }
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }


    @RequestMapping(value = "ajax_workAchi_failAll_singleDetail")
    public ModelAndView queryFailAllSingleDetail(HttpServletRequest request,Model model){
        String formNo = request.getParameter("formNo");
        FormWorkProjectDto formWorkProjectDto=formWorkProjectService.getFormByFormNo(Long.parseLong(formNo));

        String scores = formWorkProjectDto.getAchiOnlyMembersScores();
        if (!StringUtils.isNullOrWhiteSpace(scores)){
            String[] arr_scores = scores.split("//");
            formWorkProjectDto.setAchiOnlyScores(arr_scores);
        }
        String suggestions = formWorkProjectDto.getAchiOnlyMembersAdvise();
        if (!StringUtils.isNullOrWhiteSpace(suggestions)){
            String[] arr_suggestions = suggestions.split("//");
            formWorkProjectDto.setAchiOnlyAdvise(arr_suggestions);
        }

        model.addAttribute("beans",formWorkProjectDto);
        model.addAttribute("help",Helper.class);
        return view("partyGroup/syy_dq_lc02_a_failAll_detail",model);
    }

    /**
     * 批量审核页面 单个提交
     * @param dto
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_approve_singleOrAll_workAch")
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
        CommonResult result= formWorkAchievementsService.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 批量审核页面 批量提交
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_workAchievement_approve_All")
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

            result= formWorkAchievementsService.doApprove(dto,param);
        }


        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    @RequestMapping(value = "ajax_approve_rejectSingle_workAchi")
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
        CommonResult result= formWorkAchievementsService.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    @RequestMapping(value = "ajax_workAchievement_reject_All")
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
            result= formWorkAchievementsService.doApprove(dto,param);
        }

        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 第四步 审核 生成图片页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_dq_lc02_iframe_html")
    public ModelAndView iframeHtml(Model model,HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        String prizeLevel = request.getParameter("prizeLevel");
        model.addAttribute("formNo",formNo);
        FormWorkProjectDto dto=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo))
            dto = formWorkAchievementsService.getFormByFormNo(Long.parseLong(formNo));
        String[] str = dto.getJoinPeople().split(",");
        dto.setMainPeople(str);

        if (!StringUtils.isNullOrWhiteSpace(prizeLevel))
            dto.setPrizeLeval(URI.deURI(prizeLevel));
        model.addAttribute("dto",dto);

        Date currentTime = new Date();
        model.addAttribute("currentTime",DateHelper.formatDate(currentTime,"yyyy年M月"));

        String[] name = dto.getFormKindName().split("流程");
        model.addAttribute("name",name[0]);

        return view( "partyGroup/syy_dq_lc02_v_image",model);
    }

    /**
     * 图片保存 相对路径 项目目录下
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_dq_02_imageGenerate",method = RequestMethod.POST)
    @ResponseBody
    public boolean imageGenerate(HttpServletRequest request){

        String imgStr = request.getParameter("dataImage");
        String[] imageData = imgStr.split(",");
        String actualData = imageData[1];
        String formNo = request.getParameter("formNo");
        FormWorkProjectDto achievement = null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            achievement = formWorkAchievementsService.getFormByFormNo(Long.parseLong(formNo));
        }

//        if (!StringUtils.isNullOrWhiteSpace(actualData)){
//            achievement.setImageData(actualData);
//        }

        //返回一个图片下载的相对地址 eg: domain/images/formNo.jpg
        achievement.setImageStatus("已生成");
//        String a = request.getParameter("a");
        formWorkAchievementsService.updateByDto(achievement);//保存base64图片字符串数据状态

        String aa = Config.getConfigParameter("dqimg", "../config.properties");

        //TODO 把base64图片转换为IO数据流，存入服务器文件位置
        if (actualData == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(actualData);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
//            String imagePath = InnovationAchievementController.class.getClassLoader().getResource("出差证模板.docx").getPath().replace("/","\\");

//            String imgFilePath = "C:\\Users\\admin\\Desktop\\劳动竞赛挖潜增效成果荣誉证书.jpg";//新生成的图片
            String path = Config.getConfigParameter("dqimg", "../config.properties");
            OutputStream out = new FileOutputStream(path+formNo+".jpg");
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

}
