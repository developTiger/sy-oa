package com.sunesoft.lemon.webapp.controller.partyGroup;

import com.sun.jndi.toolkit.url.Uri;
import com.sunesoft.lemon.ay.partyGroup.application.WorkAchievementsService;
import com.sunesoft.lemon.ay.partyGroup.application.WorkProjectService;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.*;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkProjectDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkersProposalDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.CompetitionTitleTypeService;
import com.sunesoft.lemon.ay.partyGroupForms.application.CompetitionTypeService;
import com.sunesoft.lemon.ay.partyGroupForms.application.FormWorkProjectService;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.ApproveProjFormDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionType;
import com.sunesoft.lemon.ay.partyGroupForms.domain.WorkProject_competitionTitleType;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.MultiSelectUserWithDeptDto;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.EmpAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.TrainFileDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.Config.Config;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.workflow.FormController;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.EmpAppraiseDetailDtoModel;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
import com.sunesoft.lemon.webapp.utils.UI;
import com.sunesoft.lemon.webapp.utils.URI;
import org.apache.commons.lang.ObjectUtils;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 劳动竞赛项目
 * Created by admin on 2016/9/3.
 */
@Controller
public class WorkProjectController extends Layout {

    @Autowired
    FormListService formListService;

    @Autowired
    UserSession userSession;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    FormWorkProjectService formWorkProjectService;

    @Autowired
    WorkProjectService workProjectService;

    @Autowired
    FileService fileService;

    @Autowired
    WorkAchievementsService workAchievementsService;

    @Autowired
    CompetitionTitleTypeService competitionTitleTypeService;

    @Autowired
    CompetitionTypeService typeService;

    /**
     * 劳动竞赛项目 申请流程页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_dq_lc01_a")
    public ModelAndView index(Model model,HttpServletRequest request){
        FormListDto dto =formListService.getFormListInfo("SYY_DQ_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);
        List<EmpDto> list= employeeService.getAllEmps();//获取全院人员信息
        model.addAttribute("beans",list);

        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)){
            FormWorkProjectDto projectDto=formWorkProjectService.getFormByFormNo(Long.parseLong(formNo));
            String[] arr_people = projectDto.getJoinPeople().split(",");
            projectDto.setMainPeople(arr_people);
            model.addAttribute("projectDto",projectDto);
            model.addAttribute("formNo",projectDto.getFormNo());
        }

        //获取竞赛主题类型
        CompetitionTitleTypeCriteria criteria=new CompetitionTitleTypeCriteria();
        PagedResult<WorkProject_competitionTitleType> pagedResult= competitionTitleTypeService.pages(criteria);
        List<WorkProject_competitionTitleType> titleTypes = pagedResult.getItems();
        model.addAttribute("titleTypes",titleTypes);

        //获取竞赛类别
        CompetitionTypeCriteria typeCriteria = new CompetitionTypeCriteria();
        PagedResult<CompetitionType> pagedResult1 = typeService.pages(typeCriteria);
        List<CompetitionType> competitionTypes = pagedResult1.getItems();
        model.addAttribute("competitionTypes",competitionTypes);

        //部门信息
        List<DeptmentDto> deptmentDtos=deptmentService.getAllDept();
        model.addAttribute("depts",deptmentDtos);

        return view(applyLayout,"partyGroup/syy_dq_lc01_a",model);
    }

    /**
     * 申请页面 提交
     * @param request
     * @param formWorkProjectDto
     * @param response
     * @return
     */
    @RequestMapping(value = "ajax_add_update_workProject")
    @ResponseBody
    public CommonResult addOrUpdateWorkProject(HttpServletRequest request,FormWorkProjectDto formWorkProjectDto,HttpServletResponse response){
        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);

        String hidEmpNames = request.getParameter("mainPeople");

        String[] arr_empName = hidEmpNames.split(",");
        for (int i = 0; i < arr_empName.length; i++) {
            for(int j=i+1;j<arr_empName.length;j++){
                if (arr_empName[i].equals(arr_empName[j])){
                    return new CommonResult(false,"参与人名称重复，请重新选择！");
                }
            }
        }

        //已上传的附件
        String oldFileName = request.getParameter("oldFileName");
        String oldFileId = request.getParameter("oldFileId");
//        String aaaa = request.getParameter("aaaaaa");
//        if (aaaa == null){
//            return new CommonResult(false,"22");
//        }

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

                    if (StringUtils.isNullOrWhiteSpace(fileName)){
                        formWorkProjectDto.setFileName(oldFileName);
                        formWorkProjectDto.setFileId(oldFileId);
                    }else{
                        formWorkProjectDto.setFileId(id);
                        formWorkProjectDto.setFileName(fileName);
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

        formWorkProjectDto.setJoinPeople(hidEmpNames);
        formWorkProjectDto.setCpmpetitionTime(new Date());

        CommonResult commonResult=formWorkProjectService.addByDto(formWorkProjectDto);
        if(commonResult.getIsSuccess()){
        CommonResult result1= formWorkProjectService.submitForm(commonResult.getId(),formWorkProjectDto.getFormKind());
        return result1;
        }
        else return commonResult;

    }

    /**
     * 审核页面
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_syy_dq_lc01_data")
    @ResponseBody
    public FormWorkProjectDto queryData(HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        String view_only = request.getParameter("viewOnly");
        FormWorkProjectDto formWorkProjectDto=formWorkProjectService.getFormByFormNo(Long.parseLong(formNo));
        formWorkProjectDto.setIsViewOnly(Boolean.valueOf(view_only));
        List<MultiSelectUserWithDeptDto> list=employeeService.getAllDeptEmp();
        formWorkProjectDto.setProject_empDtos(list);

        return formWorkProjectDto;
    }

    /**
     * 劳动竞赛项目及成果统计页面
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_w_a")
    public ModelAndView indexCount(Model model,HttpServletRequest request){
        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);
        if(!empSessionDto.getDeptNo().equals("02YZBGS"))//TODO 部门编码 hotfix

        {
            List<DeptmentDto> deptmentDtos = new ArrayList<>();
            DeptmentDto dpt =new DeptmentDto();
            dpt.setId(empSessionDto.getDeptId());
            dpt.setDeptName(empSessionDto.getDeptName());
            deptmentDtos.add(dpt);
            model.addAttribute("depts",deptmentDtos);
        }
        else {
            List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
            model.addAttribute("depts", deptmentDtos);
        }
        return view(layout,"partyGroup/syy_dq_lc01_a_count",model);
    }

    /**
     * 统计 查询 项目和成果
     * @param workProjectCriteria
     * @param response
     */
    @RequestMapping(value = "ajax_query_count_workProject")
    public void queryCount(WorkProjectCriteria workProjectCriteria,HttpServletResponse response,HttpServletRequest request){
        String proName = request.getParameter("proName");
        if (!StringUtils.isNullOrWhiteSpace(proName))
            workProjectCriteria.setProjectName(URI.deURI(proName));

        String comLeader = request.getParameter("comLeader");
        if (!StringUtils.isNullOrWhiteSpace(comLeader))
            workProjectCriteria.setLeader(URI.deURI(comLeader));

        String dept_name = request.getParameter("dept_name");
        if (!StringUtils.isNullOrWhiteSpace(dept_name))
            workProjectCriteria.setDeptName(URI.deURI(dept_name));

        String begTime = request.getParameter("begTime");
        if (!StringUtils.isNullOrWhiteSpace(begTime))
            workProjectCriteria.setBeginTime(DateHelper.parse(begTime, "yyyy-MM-dd"));
        String enTime = request.getParameter("enTime");
        if (!StringUtils.isNullOrWhiteSpace(enTime)) {
            String end_time = enTime+" 23:59:59";
            workProjectCriteria.setEndTime(DateHelper.parse(end_time, "yyyy-MM-dd HH:mm:ss"));
        }

        String compType = request.getParameter("compType");
        if (!StringUtils.isNullOrWhiteSpace(compType))
            workProjectCriteria.setCompetitionType(URI.deURI(compType));


        PagedResult<WorkProjectDto> pagedResult = workProjectService.getPagesWorkProjectDto(workProjectCriteria);

        //如果项目申报有成果数据，就将成果数据的获奖等级和成果评分设置到项目申报的对应的字段上
        List<WorkAchievementsDto> list=workAchievementsService.getAll();//成果数据  2  2
        List<WorkProjectDto> workProjectDtos = pagedResult.getItems();//项目数据 项目数据比成果数据多 3  1

        if (workProjectDtos !=null && workProjectDtos.size()>0) {
            //遍历数据少的那一张表，但是成果数据必须要全部遍历
            if (list.size()>=0){
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < workProjectDtos.size(); j++) {
                        if (list.get(i).getFormWork_id().equals(workProjectDtos.get(j).getId())) {
                            //成果数据，自行替换
                            if (!StringUtils.isNullOrWhiteSpace(list.get(i).getApproveScore())) {
                                workProjectDtos.get(j).setApproveScore(list.get(i).getApproveScore());
                            }
                            if (list.get(i).getPrizeLeval() != null) {
                                workProjectDtos.get(j).setPrizeLeval(list.get(i).getPrizeLeval());
                            }
                            if (!StringUtils.isNullOrWhiteSpace(list.get(i).getJoinPeople())) {
                                workProjectDtos.get(j).setJoinPeople(list.get(i).getJoinPeople());
                            }
                            if (!StringUtils.isNullOrWhiteSpace(list.get(i).getReviewGroupMembers())) {
                                workProjectDtos.get(j).setReviewGroupMembers(list.get(i).getReviewGroupMembers());
                            }
                            if (!StringUtils.isNullOrWhiteSpace(list.get(i).getAchievementJudgeSuggestions())) {
                                workProjectDtos.get(j).setAchievementJudgeSuggestions(list.get(i).getAchievementJudgeSuggestions());
                            }
                            workProjectDtos.get(j).setWorkAhic_id(list.get(i).getId().toString());
                        }
                    }
                }
            }


        }

//        PagedResult<WorkProjectDto> pages = new PagedResult<WorkProjectDto>(workProjectDtos,workProjectDtos.size()/10,10,workProjectDtos.size());

        //分页
        List ten = new ArrayList();
        int totalCount = workProjectDtos.size();
        if (workProjectCriteria.getPageNumber()*workProjectCriteria.getPageSize()>totalCount){
            for (int i = (workProjectCriteria.getPageNumber()-1)*workProjectCriteria.getPageSize();i <totalCount ;i++){
                ten.add(workProjectDtos.get(i));
            }
        }else {
            for (int i = (workProjectCriteria.getPageNumber() - 1) * workProjectCriteria.getPageSize(); i < workProjectCriteria.getPageNumber() * workProjectCriteria.getPageSize(); i++) {
                ten.add(workProjectDtos.get(i));
            }
        }

        String json = JsonHelper.toJson(new PagedResult<WorkProjectDto>(ten,workProjectCriteria.getPageNumber(),workProjectCriteria.getPageSize(),workProjectDtos.size()));
        AjaxResponse.write(response,json);
    }

    /**
     * 单个查询 详情
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_workProject_countDetail")
    public ModelAndView queryCountDetail(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
//        String workAhic_id = request.getParameter("workAhic_id");

        if (!StringUtils.isNullOrWhiteSpace(id)) {
            WorkProjectDto workProjectDto = workProjectService.getById(Long.parseLong(id));

            //将成果的数据对应的拷贝到项目中去，展示项目数据
            List<WorkAchievementsDto> list=workAchievementsService.getAll();
            for (WorkAchievementsDto dto:list){
                if (dto.getFormWork_id().equals(workProjectDto.getId())){
                    workProjectDto.setReviewGroupMembers(dto.getReviewGroupMembers());
                    workProjectDto.setAchievementJudgeSuggestions(dto.getAchievementJudgeSuggestions());
                    workProjectDto.setPrizeLeval(dto.getPrizeLeval());
                    workProjectDto.setApproveScore(dto.getApproveScore());
                    workProjectDto.setNameOrdept(dto.getNameOrdept());
                    workProjectDto.setPrizeName(dto.getPrizeName());
                    workProjectDto.setAchiGetTime(dto.getAchiGetTime());
                    workProjectDto.setAwardAgency(dto.getAwardAgency());
                    workProjectDto.setAchi_fileId(dto.getFileId());
                    workProjectDto.setAchi_fileName(dto.getFileName());
                    workProjectDto.setAchi_formNo(dto.getFormNo());
                    workProjectDto.setAchi_imageStatus(dto.getImageStatus());

                    String score = dto.getAchiOnlyMembersScores();
                    if (!StringUtils.isNullOrWhiteSpace(score)) {
                        String[] arr_score = score.split("//");
                        workProjectDto.setAchiOnlyScores(arr_score);
                    }
                    String suggestion = dto.getAchiOnlyMembersAdvise();
                    if (!StringUtils.isNullOrWhiteSpace(suggestion)) {
                        String[] arr_suggestion = suggestion.split("//");
                        workProjectDto.setAchiOnlyAdvise(arr_suggestion);
                    }

                }
            }

            model.addAttribute("beans",workProjectDto);
        }

        model.addAttribute("help", Helper.class);
        return view(layout,"partyGroup/syy_dq_lc01_a_count_detail",model);
    }

    /**
     * 统计 单个编辑
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_edit_workProject_countDetail")
    public ModelAndView editCountDetail(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
//        String workAhic_id = request.getParameter("workAhic_id");


        if (!StringUtils.isNullOrWhiteSpace(id)) {
            WorkProjectDto workProjectDto = workProjectService.getById(Long.parseLong(id));
            model.addAttribute("beans",workProjectDto);
        }

        List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("list",list);

        model.addAttribute("ui",UI.class);
        model.addAttribute("help", Helper.class);
        return  view("partyGroup/syy_dq_lc01_a_count_edit",model);
    }

    @RequestMapping(value = "ajax_update_workProject_countDetail",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateCountDetail(HttpServletRequest request,WorkProjectDto dto,WorkAchievementsDto achievementsDto){

        String joinPeople = request.getParameter("hiddenJoinPeople");

        String id = request.getParameter("id");
//        String score = request.getParameter("hidScore");
        CommonResult commonResult = null;

        dto.setJoinPeople(joinPeople);
        dto.setId(Long.parseLong(id));
        commonResult = workProjectService.updateWorkProject(dto);

        return commonResult;
    }


    /**
     * T3专业评审委员会汇总审批节点 跳转到批量审核页面
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_workPro_fail_all")
    public ModelAndView failAll(Model model,HttpServletRequest request){
        List<DeptmentDto> deptmentDtos=deptmentService.getAllDept();
        model.addAttribute("depts",deptmentDtos);

        List<MultiSelectUserWithDeptDto> list=employeeService.getAllDeptEmp();
        model.addAttribute("list",list);

        String step = request.getParameter("step");
        model.addAttribute("step",step);

        return view(applyLayout,"partyGroup/syy_dq_lc01_a_failAll",model);
    }

    /**
     * 批量审核页面 查询
     * @param request
     * @param criteria
     * @param response
     */
    @RequestMapping(value = "ajax_query_failAll_count")
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
        List<FormWorkProjectDto> formWorkProjectDtos = pagedResult.getItems();
        List<MultiSelectUserWithDeptDto> list=employeeService.getAllDeptEmp();

        for (FormWorkProjectDto dto:formWorkProjectDtos){
            dto.setProject_empDtos(list);
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
    @RequestMapping(value = "ajax_workPro_failAll_singleDetail")
    public ModelAndView queryFailAllSingleDetail(HttpServletRequest request,Model model){
        String formNo = request.getParameter("formNo");
        FormWorkProjectDto formWorkProjectDto=formWorkProjectService.getFormByFormNo(Long.parseLong(formNo));
        model.addAttribute("beans",formWorkProjectDto);
        model.addAttribute("help",Helper.class);
        return view("partyGroup/syy_dq_lc01_a_failAll_detail",model);
    }


    /**
     * 批量审核页面 单个提交
     * @param dto
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_approve_singleOrAll")
    public void approve(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){

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
        CommonResult result= formWorkProjectService.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    /**
     * 批量审核页面 批量提交
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_workProject_approve_All")
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

            result= formWorkProjectService.doApprove(dto,param);
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
    @RequestMapping(value = "ajax_approve_rejectSingle")
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
        CommonResult result= formWorkProjectService.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 批量审核页面 批量否决
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_workProject_reject_All")
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
            result= formWorkProjectService.doApprove(dto,param);
        }

        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    @RequestMapping("ajax_workAndAchievement_count_down")
    public void download1(HttpServletRequest request, HttpServletResponse response) {

        String formNo = request.getParameter("formNo");



//        EmpCriteria empCriteria = new EmpCriteria();
//        empCriteria.setPageSize(1000);
//        PagedResult<EmpAppraiseDetailDto> pagedResult=formAppraiseService.getPagesByAllDetails(Long.parseLong(formNo), empCriteria);
//        List<EmpAppraiseDetailDtoModel> empAppraiseDetailDtoModels = new ArrayList<>();
//        for (EmpAppraiseDetailDto detailDto:pagedResult.getItems()){
//            EmpAppraiseDetailDtoModel dtoModel = new EmpAppraiseDetailDtoModel();
//            dtoModel.setLoginName(detailDto.getLoginName());
//            dtoModel.setEmpSelfGrade(detailDto.getEmpSelfGrade());
//            if (detailDto.getDeptScores() != null) {
//                dtoModel.setDeptScores(detailDto.getDeptScores().toString());
//            }
//            dtoModel.setDeptGrade(detailDto.getDeptGrade());
//            if (detailDto.getGroupScores() != null) {
//                dtoModel.setGroupScores(detailDto.getGroupScores().toString());
//            }
//            dtoModel.setGroupGrade(detailDto.getGroupGrade());
//            dtoModel.setChargeLeaderScores(detailDto.getChargeLeaderScores());
//            dtoModel.setChargeLeaderLevel(detailDto.getChargeLeaderLevel());
//
//            empAppraiseDetailDtoModels.add(dtoModel);
//        }
//
//        ExpotExcel<EmpAppraiseDetailDtoModel> expotExcel = new ExpotExcel<>();
//        String[] header = new String[]{"员工姓名", "员工自评", "基层评分", "基层评级", "分管评分", "分管评级","委员会评分","委员会评级"};
//        expotExcel.doExportExcel("部门员工综合考核表", header, empAppraiseDetailDtoModels, "yyyy-MM-dd", response);

    }

    /**
     * 劳动成果证书下载
     * @param request
     * @param response
     */
    @RequestMapping(value = "downloadWorkAchievement",method = RequestMethod.GET)
    @ResponseBody
    public void downloadImage(HttpServletRequest request , HttpServletResponse response) {
        String formNo = request.getParameter("formNo") + ".jpg";
        String path = Config.getConfigParameter("dqimg", "../config.properties");
        String filePath = path + formNo;
        try {

            File file = new File(filePath);
            InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(formNo.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();

        } catch (Exception ex) {

        }
    }

    /**
     * 劳动申报 申请页面 主要参加人
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_partyGroup_lc01_mainPeople")
    @ResponseBody
    public ListResult<MultiSelectUserWithDeptDto> queryMainPeople(HttpServletRequest request){
        List<MultiSelectUserWithDeptDto> list = employeeService.getAllDeptEmp();
        return new ListResult<MultiSelectUserWithDeptDto>(list);
    }


}
