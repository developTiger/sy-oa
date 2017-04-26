package com.sunesoft.lemon.webapp.controller.projectInfo;

import com.sunesoft.lemon.deanery.car.application.CommonDriverService;
import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.car.application.ItemProjectService;
import com.sunesoft.lemon.deanery.car.application.SpecialtyTypeService;
import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.car.domain.ItemProject;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.project.application.ProjectMainApplyService;
import com.sunesoft.lemon.deanery.project.application.ScientificResearchProjectService;
import com.sunesoft.lemon.deanery.project.application.criteria.ScientificResearchProjectCriteria;
import com.sunesoft.lemon.deanery.project.application.dtos.*;
import com.sunesoft.lemon.deanery.project.application.factory.UploadProjectApplyDetailFactory;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.ProjectPlanService;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.FormProjectApplyService;
import com.sunesoft.lemon.deanery.scientificRPKU.ScienticRPKUService;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
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
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
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
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zy on 2016/8/2.
 */
@Controller
public class ProjectApplyFlowController extends Layout {
    int n=0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    ScientificResearchProjectService scientificResearchProjectService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserSession us;

    @Autowired
    CommonDriverService commonDriverService;


    @Autowired
    FormProjectApplyService formProjectApplyService;

    @Autowired
    DriverService driverService;
    @Autowired
    FileService fileService;
    @Autowired
    FormListService formListService;
    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    ProjectMainApplyService projectMainApplyService;
    @Autowired
    ScienticRPKUService scienticRPKUService;
    @Autowired
    SpecialtyTypeService specialtyTypeService;
    @Autowired
    ItemProjectService itemProjectService;


    /**
     * 流程申请页面
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("syy_kg_lc01_a")
    public ModelAndView apply(Model model, HttpServletRequest request) {
        String formNo = request.getParameter("formNo");
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
           if(formNo!=null){

               ScientificResearchProjectDto s=scientificResearchProjectService.getIdByFormNo(Long.parseLong(formNo));
               String beginTime1 = s.getBeginTime1();
               String endTime1 = s.getEndTime1();
               String begt= beginTime1.substring(0,10);
               String endt= endTime1.substring(0,10);
               s.setBeginTime1(begt);
               s.setEndTime1(endt);
               //年度
               String niandu= sdf.format(s.getNiandu());
               s.setYear_Str(niandu.substring(0,4));
               model.addAttribute("bean", s);
               model.addAttribute("formNo", s.getFormNo());

               String rens=s.getGroupMembers();
               String[] renss=rens.split(",");
               ArrayList<String> ssl=new ArrayList<>();
                for (int i=1;i<renss.length ;i++){
                    ssl.add(renss[i]);
                }
               model.addAttribute("ssls", ssl);
               model.addAttribute("ssl", renss[0]);

           }

        EmpSessionDto user = us.getCurrentUser(request);
        model.addAttribute("applyuser", user);

        List<Employee> queryEmployees=scientificResearchProjectService.queryEmployees();
        model.addAttribute("emp",queryEmployees);//取得付項目長，組員信息
        model.addAttribute("beans", queryEmployees);
       /* model.addAttribute("sp", static_common.SPECIALTYTYPE);
        model.addAttribute("pType", static_common.PROJECTTYPE);*/
        //新增，返回页面所属部门名称
        List<DeptmentDto> deptmentDtos = deptmentService.getByDeptsIds();
        model.addAttribute("deptName", deptmentDtos);

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
        List<ItemProject> itemsList= itemProjectService.itemsList();
        model.addAttribute("alltype",alltype);
        model.addAttribute("itemsList",itemsList);
        return view(applyLayout, "projectInfo/syy_kg_lc01_a", model);
    }

    /**
     * 项目计划list列表
     * @param projectResultCriteria ProjectPlanInput的dto
     * @throws java.io.UnsupportedEncodingException
     */
    @RequestMapping(value = "ajax_projectApplyBao_date")
    public void ajax_projectplan_query_list(ProjectPlanInput projectResultCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        PagedResult pagedResult = projectPlanService.getProjectPlan1(projectResultCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    //获取选中的id
    @RequestMapping("ajax_queryProjectPlanId")
    public void ajax_executionId(Model model, HttpServletRequest request, HttpServletResponse response) {
        String planid = request.getParameter("planid");
        if (!StringUtils.isNullOrWhiteSpace(planid)) {
            ProjectPlanInput formcon = projectPlanService.getByIdProjectPlanInput((Long.parseLong(planid)));
            if (!StringUtils.isNullOrWhiteSpace(formcon.getProjectPlan_Type())) {
                if (formcon.getProjectPlan_Type().equals("油气勘探")) {
                    formcon.setProjectPlan_Type("01");
                } else if (formcon.getProjectPlan_Type().equals("油气开发")) {
                    formcon.setProjectPlan_Type("02");
                } else if (formcon.getProjectPlan_Type().equals("炼油化工")) {
                    formcon.setProjectPlan_Type("03");
                } else if (formcon.getProjectPlan_Type().equals("油气集输")) {
                    formcon.setProjectPlan_Type("04");
                } else if (formcon.getProjectPlan_Type().equals("计算机")) {
                    formcon.setProjectPlan_Type("05");
                } else if (formcon.getProjectPlan_Type().equals("软科学")) {
                    formcon.setProjectPlan_Type("06");
                } else if (formcon.getProjectPlan_Type().equals("安全环保")) {
                    formcon.setProjectPlan_Type("07");
                }
            }

            String start = DateHelper.formatDate(formcon.getProjectPlan_StartTime(), "yyyy-MM-dd");
            String end = DateHelper.formatDate(formcon.getProjectPlan_EndTime(), "yyyy-MM-dd");
            formcon.setEndTime_Str(end);
            formcon.setStartTime_Str(start);
            String json = JsonHelper.toJson(formcon);
            AjaxResponse.write(response, json);
        }
    }


    /**
     * 申请项目信息
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_kg_lc01_apply", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult apply(ScientificResearchProjectDto scientificResearchProjectDto, HttpServletRequest request, HttpServletResponse response) {
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName");
        List<ScientificResearchProjectFileDto> list=new ArrayList<>();
                for (MultipartFile myfile : myfiles) {
                    if (!myfile.isEmpty()) {
                        try {
                            String fileName = myfile.getOriginalFilename();
                            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                            FileInfoDto fileInfoDto=new FileInfoDto();
                            fileInfoDto.setFileName(fileName);
                            fileInfoDto.setExtensions(extension);
                            fileInfoDto.setInputStream(myfile.getInputStream());
                            String id1=fileService.upload(fileInfoDto);
                            if(!StringUtils.isNullOrWhiteSpace(id1)) {
                                ScientificResearchProjectFileDto scientificResearchProjectFileDto = new ScientificResearchProjectFileDto();
                                scientificResearchProjectFileDto.setFileId(id1);
                                scientificResearchProjectFileDto.setFileName(fileName);
                                list.add(scientificResearchProjectFileDto);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
       // ScientificResearchProjectFileDto
        if(scientificResearchProjectDto.getAlready_upFileId()!=null){
            for(int i=0;i<scientificResearchProjectDto.getAlready_upFileId().size();i++){
                ScientificResearchProjectFileDto dto = new ScientificResearchProjectFileDto();
                dto.setFileId(scientificResearchProjectDto.getAlready_upFileId().get(i));
                dto.setFileName(URI.deURI(scientificResearchProjectDto.getAlready_upFileName().get(i)));
                list.add(dto);
            }
        }
                CommonResult success=null;
                EmpSessionDto user = us.getCurrentUser(request);
                String id =request.getParameter("id");
                String formNo =request.getParameter("formNo");
                String renming =request.getParameter("renming");
                Employee employee= scientificResearchProjectService.getNameByName(scientificResearchProjectDto.getLeader());
             //   Employee deput= scientificResearchProjectService.getNameByName(scientificResearchProjectDto.getDeputy());
                System.out.print(DateHelper.parse(scientificResearchProjectDto.getYear_Str(),"yyyy"));
                scientificResearchProjectDto.setNiandu(DateHelper.parse(scientificResearchProjectDto.getYear_Str(), "yyyy"));
                scientificResearchProjectDto.setDeptName(user.getDeptName());
                scientificResearchProjectDto.setApplyer(user.getId());
                String ss=scientificResearchProjectDto.getProjectNo();
                if(ss==null){
                    scientificResearchProjectDto.setProjectNo(bianhao(request));
                }

                scientificResearchProjectDto.setApplyerName(user.getName());
                scientificResearchProjectDto.setDeptId(user.getDeptId());
                scientificResearchProjectDto.setDeptName(user.getDeptName());
                scientificResearchProjectDto.setLeaderName(employee.getName());
              //   scientificResearchProjectDto.setDeputyName(deput.getName());
                String zhuyuan= request.getParameter("groupMember");
                scientificResearchProjectDto.setGroupMembers(renming);
                scientificResearchProjectDto.setGroupMembersName(renming);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                scientificResearchProjectDto.setFormKind("SYY_KG_LC01");
                scientificResearchProjectDto.setFormKindName("项目计划立项审定流程");
                scientificResearchProjectDto.setProjectStatus("1");
              /*  scientificResearchProjectDto.setBaoTitle(baoTitle);
                scientificResearchProjectDto.setBaoBeginDate(baoBeginDate);
                scientificResearchProjectDto.setBaoEndDate(baoEndDate);
                scientificResearchProjectDto.setBaoContent(baoContent);*/
                scientificResearchProjectDto.setHasApplyView(true);
                scientificResearchProjectDto.setFileList(list);
                CommonResult result = scientificResearchProjectService.addByDto(scientificResearchProjectDto);
                List<Long> li=new ArrayList<>();
                li.add(employee.getId());
                success=  scientificResearchProjectService.submitForm(result.getId(), scientificResearchProjectDto.getFormKind());
            return success;

    }
    //审核
    @ResponseBody
    @RequestMapping("ajax_syy_kg_lc01_data")
    public void applyView(Model model, HttpServletRequest request, HttpServletResponse response) {
        String clstep = request.getParameter("clstep");
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        ScientificResearchProjectDto scientificResearchProjectDto = scientificResearchProjectService.getByOrderId(formNo);

        EmpSessionDto user = us.getCurrentUser(request);
        scientificResearchProjectDto.setDept(user.getDeptId());
        scientificResearchProjectDto.setDeptName(user.getDeptName());
        String groups = scientificResearchProjectDto.getGroupMembers();
            /*String members = groups.substring( groups.length() - 1);*/
        scientificResearchProjectDto.setGroupMembers(groups);
        scientificResearchProjectDto.setProjectPlanInfo(scientificResearchProjectDto.getProjectPlanInfo());
        Date beginTime = scientificResearchProjectDto.getBeginTime();
        String beginTime1 = sdf.format(beginTime);
        scientificResearchProjectDto.setBeginTime1(beginTime1);
        Date endTime = scientificResearchProjectDto.getEndTime();
        String endTime1 = sdf.format(endTime);
        scientificResearchProjectDto.setEndTime1(endTime1);
        String niandu= sdf.format(scientificResearchProjectDto.getNiandu());
        scientificResearchProjectDto.setYear_Str(niandu.substring(0,4));

        Boolean iscomplete = scientificResearchProjectDto.getIsComplete();
        String groupMembers = scientificResearchProjectDto.getGroupMembers();
        //     StringBuffer sb = new StringBuffer();
            /*sb.append(groupMembers).append(",");*/
        //获取院领导
     //   List<EmpDto> yuanempDtos =employeeService.getHeader();
        //人员 项目长和副项目长数据
      //  List<EmpDto> empDtos = employeeService.getAllEmps();
        List<Employee> empDtos=scientificResearchProjectService.queryEmployees();
        //修改，返回页面所属部门名称
        List<DeptmentDto> deptmentDtos = deptmentService.getByDeptsIds();
        // model.addAttribute("deptInfos", deptmentDtos);
        //计划select
        List<ProjectPlanInput> queryProjectPlan = projectPlanService.queryProjectPlan();
        List<SpecialtyType> alltype=specialtyTypeService.getAllType();
        List<ItemProject> itemsList= itemProjectService.itemsList();
        //项目库id
        ScientificRPKU scienticRPKU=scienticRPKUService.getIdByProjectNo(scientificResearchProjectDto.getProjectNo());

        List<DeptmentDto> deptmentDto = deptmentService.getAllDept();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        List<MultiSelectUserWithDept> multiSelectUserWithDepts = new ArrayList<>();
        for(DeptmentDto dept:deptmentDto){
            MultiSelectUserWithDept multiSelectUserWithDept = new MultiSelectUserWithDept(dept.getId(),dept.getDeptName());
            for(EmpDto emp:empDtos1){
                if(emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts.add(multiSelectUserWithDept);
        }

        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("empInfos", multiSelectUserWithDepts);
        jsonmap.put("deptInfos", deptmentDtos);
        jsonmap.put("queryProjectPlan", queryProjectPlan);
        jsonmap.put("emp", empDtos);
        jsonmap.put("formNo", formNo);
        jsonmap.put("alltype", alltype);
        jsonmap.put("itemsList",itemsList);
        jsonmap.put("scId", "kong");
        if(scientificResearchProjectDto.getClStep()>1&&scienticRPKU!=null) {
            jsonmap.put("scId", scienticRPKU.getId());
        }
      //  jsonmap.put("listEmpDto",yuanempDtos);
        jsonmap.put("sp", static_common.SPECIALTYTYPE);
        // jsonmap.put("sb",sb);
        jsonmap.put("beans", scientificResearchProjectDto);
        jsonmap.put("iscomplete", iscomplete);
        jsonmap.put("viewonly", Boolean.valueOf(viewOnly));
        jsonmap.put("clstep", scientificResearchProjectDto.getClStep());
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    /**
     * 导出数据
     */
    @ResponseBody
    @RequestMapping("ajax_syy_kg01_lot_down")
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList();
        List listid = new ArrayList();
        String formKind = request.getParameter("formNos");
        String[] pids = formKind.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        for (int i=0;i<listid.size();i++) {
            ScientificResearchProjectDto1 scientificResearchProjectDto1 = scientificResearchProjectService.downByOrderId(String.valueOf(listid.get(i)));

            EmpSessionDto user = us.getCurrentUser(request);
            //人员 项目长和副项目长数据
            List<EmpDto> empDtos = employeeService.getAllEmps();
            for (EmpDto em : empDtos) {
                if ((scientificResearchProjectDto1.getLeader()).equals(String.valueOf(em.getId()))) {
                    scientificResearchProjectDto1.setLeader(em.getName());
                    scientificResearchProjectDto1.setLeaderAdress(em.getEmail());
                }
                if ((scientificResearchProjectDto1.getDeputy()).equals(String.valueOf(em.getId()))) {
                    scientificResearchProjectDto1.setDeputy(em.getName());
                    scientificResearchProjectDto1.setDeputyAdress(em.getEmail());
                }


            }

            list.add(scientificResearchProjectDto1);
        }
        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"项目编号", "项目名称", "研究内容", "承担单位", "参加单位", "开始时间", "截止时间", "备注", "项目长", "项目长邮箱", "副项目长", "副项目长邮箱"};
        expotExcel.doExportExcel("项目申请表", header, list, "yyyy-MM-dd", response);

    }
   // 批量导入
    @ResponseBody
    @RequestMapping("ajax_syy_kg01_lot_upload")
    public void upload(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String formNo = request.getParameter("formNo");
        CommonResult result = null;
        if (StringUtils.isNullOrWhiteSpace(formNo)) {
            result = new CommonResult(false, "sorry！！");
            AjaxResponse.write(response, JsonHelper.toJson(result));
            return;
        }
        EmpSessionDto emp = us.getCurrentUser(request);
        //上传文件的原名(即上传前的文件名字)
        String originalFilename = null;
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("myfiles");
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false, "请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return;
            } else {
             /*   List listid = new ArrayList();
                String[] pids = formNo.split(",");
                for (String id : pids) {
                    listid.add(Long.parseLong(id));
                }
                for (int i=0;i<listid.size();i++) {*/
                    try {
                        UploadProjectApplyDetailFactory factory = new UploadProjectApplyDetailFactory();
                        ListResult<ScientificResearchProjectDto1> dtos = factory.readExcel(myfile.getInputStream());
                        if (dtos.getIsSuccess()) {
                            result = scientificResearchProjectService.updateByList(dtos.getItems(), formNo);
                        } else
                            result = new CommonResult(false, dtos.getMsg());

                    } catch (IOException e) {
                        System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                        e.printStackTrace();

                        result = new CommonResult(false, "文件上传失败，请重试！！");
                        AjaxResponse.write(response, JsonHelper.toJson(result));
                        return;
                    }
               /* }*/
            }
        }
        AjaxResponse.write(response, JsonHelper.toJson(result));
        return;
    }
    //中心科管科页面 t3
    @ResponseBody
    @RequestMapping("syy_kg01_lot0")
    public ModelAndView lotApprove0(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            scientificResearchProjectDto = scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        }
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot0", model);
    }

    //中心科管数据list t3
    @RequestMapping(value = "ajax_kg01_lotTpl0")
    @ResponseBody
    public void ajax_projectLot0(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        List<ScientificResearchProjectDto>  projectDtoPagedResult = scientificResearchProjectService.getProjectApproves3(scientificResearchProjectCriteria, 3,null);
       // List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", projectDtoPagedResult);
       // jsonmap.put("listEmpDto",empDtos);
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    /**
     * 中心科管群审核同意 t3
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectYES_0")
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
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = scientificResearchProjectService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //审批拒绝 t3
    @RequestMapping(value = "ajax_lotProjectNO_0")
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
             List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById((Long) listid.get(i));
                for (ScientificResearchProject tt : ss){
                    ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                    uu.setIsActive(false);
                    scienticRPKUService.addScientificResearchProject(uu);
                }
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);
                scientificResearchProjectService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    //形式批量页面 t2
    @ResponseBody
    @RequestMapping("syy_kg01_lot1")
    public ModelAndView lotApprove1(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            scientificResearchProjectDto = scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        }
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot1", model);
    }

    //形式数据list t2
    @RequestMapping(value = "ajax_kg01_lotTpl1")
    @ResponseBody
    public void ajax_projectLot1(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        Map<String, Object> jsonmap = new HashMap<String, Object>();
        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        List<ScientificResearchProjectDto>  projectDtoPagedResult = scientificResearchProjectService.getProjectApproves3(scientificResearchProjectCriteria, 2,null);

    //    List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        jsonmap.put("emp",empDtos1);
        jsonmap.put("sicent", projectDtoPagedResult);
     ///   jsonmap.put("listEmpDto",empDtos);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    /**
     * 形式审核同意 批量t2
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectYES_1")
    public void approvalProjectOK1(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String instructions = URI.deURI(request.getParameter("instructions"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectByformNo1((Long) listid.get(i), instructions);
                for (ScientificResearchProject sp :ss){
                    //更新项目信息库信息
                    scienticRPKUService.getProjectByformNo(sp.getProjectNo(),instructions);
                }

                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = scientificResearchProjectService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    //形式群审批拒绝 t2
    @RequestMapping(value = "ajax_lotProjectNO_1")
    public void approvalProjectNO1(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById((Long) listid.get(i));
                for (ScientificResearchProject tt : ss){
                    ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                    scienticRPKUService.addScientificResearchProject(uu);
                }
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.B.ordinal());
                dto.setContent(allContent);
                //CommonResult result= formProjectApplyService.addPriceAndApprove(dto, 1D);
                CommonResult nextProject = scientificResearchProjectService.updateProject(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
   //专家批量页面 t5
    @ResponseBody
    @RequestMapping("syy_kg01_lot2")
    public ModelAndView lotApprove2(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        EmpSessionDto user = us.getCurrentUser(request);
        ScientificResearchProjectDto scientificResearchProjectDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            scientificResearchProjectDto = scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        }
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot2", model);
    }

    //专家数据list t5
    @RequestMapping(value = "ajax_kg01_lotTpl2")
    @ResponseBody
    public void ajax_projectLot2(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        EmpSessionDto userInfo = us.getCurrentUser(request);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        List<ScientificResearchProjectDto>  projectDtoPagedResult = scientificResearchProjectService.getProjectApproves3(scientificResearchProjectCriteria, 6,userInfo.getId());

       // List<EmpDto> empDtos =employeeService.getHeader();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        jsonmap.put("emp",empDtos1);
        jsonmap.put("sicent", projectDtoPagedResult);
       // jsonmap.put("listEmpDto",empDtos);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    /**
     * 专家审核同意 t5
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectYES_2")
    public void approvalProjectOK2(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String opinion = URI.deURI(request.getParameter("opinion"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectByformNo((Long) listid.get(i), opinion);
                for (ScientificResearchProject sp :ss){
                    //更新项目信息库信息
                    scienticRPKUService.getProjectByformNo(sp.getProjectNo(),opinion);
                }

                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = scientificResearchProjectService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    //专家群审批拒绝 t5
    @RequestMapping(value = "ajax_lotProjectNO_2")
    public void approvalProjectNO0(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById((Long) listid.get(i));
                for (ScientificResearchProject tt : ss){
                    ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                    uu.setIsActive(false);
                    scienticRPKUService.addScientificResearchProject(uu);
                }
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);
                //CommonResult result= formProjectApplyService.addPriceAndApprove(dto, 1D);
                CommonResult nextProject = scientificResearchProjectService.updateProject(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
  //t6 科研管理人员汇总审批页面
    @RequestMapping("syy_kg01_lot3")
    public ModelAndView lotApprove3(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot3", model);
    }
//t6 科研管理人员汇总审批list
    @RequestMapping(value = "ajax_kg01_lotTpl3")
    @ResponseBody
    public void ajax_projectLot3(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        List<ScientificResearchProjectDto> projectDtoPagedResult=null;
        String specType=request.getParameter("specType");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        projectDtoPagedResult = scientificResearchProjectService.getProjectApproves6(scientificResearchProjectCriteria, 7);
      //  List<EmpDto> empDtos1 =employeeService.getHeader();

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

        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("empInfos",multiSelectUserWithDepts);
        jsonmap.put("sicent", projectDtoPagedResult);
      //  jsonmap.put("listEmpDto",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }

    //t6  科研管理人员汇总审批群审批拒绝
    @RequestMapping(value = "ajax_lotProjectNO3")
    public void approvalProjectNO3(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
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
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById((Long) listid.get(i));
                for (ScientificResearchProject tt : ss){
                    ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                    uu.setIsActive(false);
                    scienticRPKUService.addScientificResearchProject(uu);
                }

                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);
                //CommonResult result= formProjectApplyService.addPriceAndApprove(dto, 1D);
                CommonResult nextProject = scientificResearchProjectService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    /**
     * t6 科研管理人员汇总审批单个审核同意
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectYESONE3")
    public void approvalProjectOKOne3(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
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
            dto.setFormKind("SYY_KG_LC01");
            dto.setAppValue(AppValue.Y.ordinal());
            dto.setContent(allContent);

            CommonResult nextProject = scientificResearchProjectService.updateProject(dto,empid);
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    /**
     * t6 科研管理人员汇总审批单个审核拒绝
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectNOONE3")
    public void approvalProjectNOOne3(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("forno");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        if(empid!=null){
            List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById(Long.parseLong(ids));
            for (ScientificResearchProject tt : ss){
                ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                uu.setIsActive(false);
                scienticRPKUService.addScientificResearchProject(uu);
            }

            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC01");
            dto.setAppValue(AppValue.N.ordinal());
            dto.setContent(allContent);
            CommonResult nextProject = scientificResearchProjectService.updateProject0(dto);
            AjaxResponse.write(response, "text", "success");

        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    @ResponseBody
    @RequestMapping("syy_kg01_lot4")
    public ModelAndView lotApprove4(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            scientificResearchProjectDto = scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        }
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot4", model);
    }

    //中心科管数据list
    @RequestMapping(value = "ajax_kg01_lotTpl4")
    @ResponseBody
    public void ajax_projectLot4(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {

        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        List<ScientificResearchProjectDto>  projectDtoPagedResult = scientificResearchProjectService.getProjectApproves3(scientificResearchProjectCriteria, 9,null);
     //   List<EmpDto> empDtos =employeeService.getHeader();

        Map<String, Object> jsonmap = new HashMap<String, Object>();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        jsonmap.put("emp",empDtos1);
        jsonmap.put("sicent", projectDtoPagedResult);
      //  jsonmap.put("listEmpDto",empDtos);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //科委主任页面 t10
    @ResponseBody
    @RequestMapping("syy_kg01_lot6")
    public ModelAndView lotApprove6(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=null;
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            scientificResearchProjectDto = scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        }
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot6", model);
    }

    //科委主任数据list t10
    @RequestMapping(value = "ajax_kg01_lotTpl6")
    @ResponseBody
    public void ajax_projectLot6(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        List<ScientificResearchProjectDto>  projectDtoPagedResult = scientificResearchProjectService.getProjectApproves6(scientificResearchProjectCriteria, 10);
     //   List<EmpDto> empDtos =employeeService.getHeader();
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        jsonmap.put("emp",empDtos1);
        jsonmap.put("sicent", projectDtoPagedResult);
      //  jsonmap.put("listEmpDto",empDtos);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
//t7 主管领导选人审批 页面
    @ResponseBody
    @RequestMapping("syy_kg01_lot")
    public ModelAndView lotApprove(Model model, HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot", model);
    }
    //t7 主管领导选人审批 list
    @RequestMapping(value = "ajax_kg01_lotTpl")
    @ResponseBody
    public void ajax_projectLot(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        List<ScientificResearchProjectDto> projectDtoPagedResult=null;
        String specType=request.getParameter("specType");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        projectDtoPagedResult = scientificResearchProjectService.getProjectApproves3(scientificResearchProjectCriteria, 4,null);
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
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }


    /**
     * 主管领导选人审批单个审核同意
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectYESONE")
    public void approvalProjectOKOne(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("prono");
        String empid = request.getParameter("empids");
        String clstep= request.getParameter("clstep");
        String allContent = URI.deURI(request.getParameter("allContent"));
        if(empid!=null){
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo(Long.parseLong(ids));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);

                CommonResult nextProject = scientificResearchProjectService.updateProject(dto,empid);
                AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    /**
     * 主管领导选人审批单个审核拒绝
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectNOONE")
    public void approvalProjectNOOne(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("forno");
        String empid = request.getParameter("empids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        String clStep = request.getParameter("clStep");
        if(empid!=null){
            List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById(Long.parseLong(ids));
            for (ScientificResearchProject tt : ss){
                ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                uu.setIsActive(false);
                scienticRPKUService.addScientificResearchProject(uu);
            }
            EmpSessionDto userInfo = us.getCurrentUser(request);
            dto.setApproverId(userInfo.getId());
            dto.setApproverName(userInfo.getName());
            dto.setDeptId(userInfo.getDeptId());
            dto.setDeptName(userInfo.getDeptName());
            dto.setFormNo(Long.parseLong(ids));
            dto.setFormKind("SYY_KG_LC01");
            dto.setAppValue(AppValue.N.ordinal());
            dto.setContent(allContent);
            CommonResult nextProject = scientificResearchProjectService.updateProject(dto,empid);
            AjaxResponse.write(response, "text", "success");

        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }
    /**
     * 主管领导选人审批群审核同意
     * @param dto
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectYES")
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
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                //      FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
                //     List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
                CommonResult nextProject = scientificResearchProjectService.updateProject(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    //主管领导选人审批群审批拒绝
    @RequestMapping(value = "ajax_lotProjectNO")
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
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById((Long)listid.get(i));
                for (ScientificResearchProject tt : ss){
                    ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                    uu.setIsActive(false);
                    scienticRPKUService.addScientificResearchProject(uu);
                }
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);
                //CommonResult result= formProjectApplyService.addPriceAndApprove(dto, 1D);
                CommonResult nextProject = scientificResearchProjectService.updateProject(dto,empid);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
               AjaxResponse.write(response, "text", "error");
        }
    }

    /**
     *
     *科管科页面 t11
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("syy_kg01_lot5")
    public ModelAndView lotApprove5(Model model, HttpServletRequest request, HttpServletResponse response){

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        String clstep = request.getParameter("clstep");
        model.addAttribute("clstep",clstep);
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot5", model);
    }

//科管科list t11
    @RequestMapping(value = "ajax_kg01_lotTpl5")
    @ResponseBody
    public void ajax_projectLot5(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String clstep = request.getParameter("datastep");
        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        List<ScientificResearchProjectDto> projectDtoPagedResult=null;
        scientificResearchProjectCriteria.setApproverId(us.getCurrentUser(request).getId());
        if(clstep!=null){

            projectDtoPagedResult = scientificResearchProjectService.getProjectApproves4(scientificResearchProjectCriteria,11);
        }
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", projectDtoPagedResult);
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
   //科管科审批拒绝t11
   @RequestMapping(value = "ajax_lotProjectNO5")
    public void approvalProjectNO5(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById((Long)listid.get(i));
                for (ScientificResearchProject tt : ss){
                    ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                    uu.setIsActive(false);
                    scienticRPKUService.addScientificResearchProject(uu);
                }
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);

               scientificResearchProjectService.nextProject(dto,"kong");
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    /**
     * 科管科群审核同意t11

     * @param request
     * @param response
     */
   @RequestMapping(value = "ajax_lotProjectYES5")
    public void approvalProjectOK5(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String clstep = request.getParameter("clstep");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                scientificResearchProjectService.nextProject(dto,clstep);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }

    /**
     *
     *科管科长页面 t5
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("syy_kg01_lot5_kgk")
    public ModelAndView lotApprovekgk5(Model model, HttpServletRequest request, HttpServletResponse response){

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        String clstep = request.getParameter("clstep");
        model.addAttribute("clstep",clstep);
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot_5kgk", model);
    }

    //科管科长list t5
    @RequestMapping(value = "ajax_kg01_lotTpl5_kgk")
    @ResponseBody
    public void ajax_projectLotkgk5(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String clstep = request.getParameter("datastep");
        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        List<ScientificResearchProjectDto> projectDtoPagedResult=null;
        scientificResearchProjectCriteria.setApproverId(us.getCurrentUser(request).getId());
            projectDtoPagedResult = scientificResearchProjectService.getProjectApproves4(scientificResearchProjectCriteria, 5);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", projectDtoPagedResult);
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //科管科长审批拒绝t5
    @RequestMapping(value = "ajax_lotProjectNO5_kgk")
    public void approvalProjectNOkgk5(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById((Long)listid.get(i));
                for (ScientificResearchProject tt : ss){
                    ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                    uu.setIsActive(false);
                    scienticRPKUService.addScientificResearchProject(uu);
                }
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);

                scientificResearchProjectService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    /**
     * 科管科长群审核同意t5

     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectYES5_kgk")
    public void approvalProjectOKkgk5(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String clstep = request.getParameter("clstep");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                scientificResearchProjectService.updateProject2(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    /**
     *
     *科管科长页面 t8
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("syy_kg01_lot8_kgk")
    public ModelAndView lotApprovekgk8(Model model, HttpServletRequest request, HttpServletResponse response){

        FormListDto dto = formListService.getFormListInfo("SYY_KG_LC01");
        String id = request.getParameter("id");
        ScientificResearchProjectDto scientificResearchProjectDto=scientificResearchProjectService.getScientificResearchProject(Long.parseLong(id));
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("main", scientificResearchProjectDto);
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
        String clstep = request.getParameter("clstep");
        model.addAttribute("clstep",clstep);
        return view(applyLayout, "projectInfo/syy_kg_lc01_a_lot_8kgk", model);
    }

    //科管科长list t8
    @RequestMapping(value = "ajax_kg01_lotTpl8_kgk")
    @ResponseBody
    public void ajax_projectLotkgk8(ScientificResearchProjectCriteria scientificResearchProjectCriteria, HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String clstep = request.getParameter("datastep");
        String specType= URLDecoder.decode(request.getParameter("specType"),"utf-8");
        scientificResearchProjectCriteria.setSpecialtyType(specType);
        List<ScientificResearchProjectDto> projectDtoPagedResult=null;
        scientificResearchProjectCriteria.setApproverId(us.getCurrentUser(request).getId());
        projectDtoPagedResult = scientificResearchProjectService.getProjectApproves4(scientificResearchProjectCriteria,8);
        Map<String, Object> jsonmap = new HashMap<String, Object>();
        jsonmap.put("sicent", projectDtoPagedResult);
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        jsonmap.put("emp",empDtos1);
        String json = JsonHelper.toJson(jsonmap);
        AjaxResponse.write(response, json);
    }
    //科管科长审批拒绝t8
    @RequestMapping(value = "ajax_lotProjectNO8_kgk")
    public void approvalProjectNOkgk8(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String allContent = URI.deURI(request.getParameter("allContent"));
        List listid = new ArrayList();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if(listid.size()>0) {
            for (int i = 0; i < listid.size(); i++) {
                List<ScientificResearchProject> ss= scientificResearchProjectService.getProjectById((Long)listid.get(i));
                for (ScientificResearchProject tt : ss){
                    ScientificRPKU uu= scienticRPKUService.getIdByProjectNo(tt.getProjectNo());
                    uu.setIsActive(false);
                    scienticRPKUService.addScientificResearchProject(uu);
                }
                EmpSessionDto userInfo = us.getCurrentUser(request);
                dto.setApproverId(userInfo.getId());
                dto.setApproverName(userInfo.getName());
                dto.setDeptId(userInfo.getDeptId());
                dto.setDeptName(userInfo.getDeptName());
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent(allContent);

                scientificResearchProjectService.updateProject0(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }


    /**
     * 科管科长群审核同意t8

     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_lotProjectYES8_kgk")
    public void approvalProjectOKkgk8(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String clstep = request.getParameter("clstep");
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
                dto.setFormNo((Long) listid.get(i));
                dto.setFormKind("SYY_KG_LC01");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent(allContent);
                scientificResearchProjectService.updateProject2(dto);
            }
            AjaxResponse.write(response, "text", "success");
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }






    @RequestMapping(value = "kg_lc01_ApproveUpload")
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
                    String fileName = myfile.getOriginalFilename();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto = new FileInfoDto();
                    fileInfoDto.setFileName(fileName);
                    fileInfoDto.setIsPublic(false);
                    fileInfoDto.setDeptId(empSessionDto.getDeptId());
                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setInputStream(myfile.getInputStream());
                    String fileId = fileService.upload(fileInfoDto);
                    result = scientificResearchProjectService.uploadProjectFile(Long.parseLong(formNo), fileId, fileName);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        AjaxResponse.write(response, JsonHelper.toJson(result));

    }

    @RequestMapping(value="ajax_judgeProjectNo")
    private void ajax_judgeProjectNo(HttpServletResponse response,String projectNo){
        boolean booble=true;
        CommonResult result=null;
        booble= scientificResearchProjectService.judgeProjectNo(projectNo);
        if (!booble){
         result = new CommonResult(false, "项目编号已存在！请重新输入编号");
         AjaxResponse.write(response, JsonHelper.toJson(result));
        }else {
            result = new CommonResult(true, "项目编号不存在");
            AjaxResponse.write(response, JsonHelper.toJson(result));
        }
    }


    private String bianhao(HttpServletRequest request){
        Date time=new Date();
        String s=String.valueOf(time.getTime());
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String t=String.valueOf(empSessionDto.getDeptId());

       return  s;
    }





}