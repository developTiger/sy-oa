package com.sunesoft.lemon.webapp.controller.partyGroup;

import com.sunesoft.lemon.ay.partyGroup.application.InnovationAchievementService;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.FormWorkProjectCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.InnovationAchievementCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.WorkAchievementsCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.InnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.WorkProjectDto;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.AchiPatent;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.AchiTransformation;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.EducationDegree;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.Sex;
import com.sunesoft.lemon.ay.partyGroupForms.application.FormInnovationAchievementService;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormInnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormInnovationAchievement;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.ImageHelper.ImageFontWriter;
import com.sunesoft.lemon.fr.utils.ImageHelper.ImagePlaceholder;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.MultiSelectUserWithDeptDto;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.TrainFileDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormList;
import com.sunesoft.lemon.webapp.Config.Config;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
import com.sunesoft.lemon.webapp.utils.URI;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.log4j.lf5.viewer.configure.ConfigurationManager;
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
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 五小创新成果
 * Created by admin on 2016/9/3.
 */
@Controller
public class InnovationAchievementController extends Layout {

    @Autowired
    UserSession userSession;

    @Autowired
    FormListService formListService;

    @Autowired
    FormInnovationAchievementService forminnoas;

    @Autowired
    FileService fileService;

    @Autowired
    InnovationAchievementService innovationAchievementService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    EmployeeService employeeService;




    /**
     * 五小创新成果申请 首页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "syy_dq_lc03_a")
    public ModelAndView index(HttpServletRequest request,Model model){
        FormListDto dto =formListService.getFormListInfo("SYY_DQ_LC03");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        List<EmpDto> list=employeeService.getAllEmps();

        model.addAttribute("beans",list);
        List<DeptmentDto> deptmentDtos=deptmentService.getAllDept();
        model.addAttribute("depts",deptmentDtos);

        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);
        EmpDto empDto=employeeService.getEmpById(empSessionDto.getId());
        model.addAttribute("empDto",empDto);

        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)){
            FormInnovationAchievementDto achievementDto=forminnoas.getFormByFormNo(Long.parseLong(formNo));
            String[] arr_people = achievementDto.getCreatorName().split(",");
            achievementDto.setMainPeople(arr_people);
            model.addAttribute("achievementDto",achievementDto);
            model.addAttribute("formNo",achievementDto.getFormNo());
        }

        return view(applyLayout,"partyGroup/syy_dq_lc03_a",model);
    }

    @RequestMapping(value = "ajax_query_singleEmp_info")
    @ResponseBody
    public EmpDto querySingleEmpInfo(HttpServletRequest request){
        String empId = request.getParameter("empId");
        EmpDto empDto = employeeService.getEmpById(Long.parseLong(empId));
        return empDto;
    }

    /**
     * 申请页面 提交
     * @param request
     * @param achievementDto
     * @param response
     */
    @RequestMapping(value = "ajax_add_update_innovationAchi")
    @ResponseBody
    public CommonResult addOrUpdateInnoAchi(HttpServletRequest request,FormInnovationAchievementDto achievementDto,HttpServletResponse response){
        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);

        //已上传的附件
        String oldFileName = request.getParameter("oldFileName");
        String oldFileId = request.getParameter("oldFileId");

        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("file_name");

        String sex = request.getParameter("innoSex");
//        String educationDegree = request.getParameter("edcation_degr");
        String achievementTransfor = request.getParameter("transformation");
        String achiPatent = request.getParameter("patent");
        String time = request.getParameter("time");
        String age = request.getParameter("innoAge");
        String deptName = request.getParameter("deptName");
        String hidEmpName = request.getParameter("mainPeople");

        String[] arr_empName = hidEmpName.split(",");
        for (int i = 0; i < arr_empName.length; i++) {
            for(int j=i+1;j<arr_empName.length;j++){
                if (arr_empName[i].equals(arr_empName[j])){
                    return new CommonResult(false,"参与人名称重复，请重新选择！");
                }
            }
        }

//                String aaaa = request.getParameter("aaaaaa");
//        if (aaaa == null){
//            return ;
//        }

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
                        achievementDto.setFileId(oldFileId);
                        achievementDto.setFileName(oldFileName);
                    }else{
                        achievementDto.setFileId(id);
                        achievementDto.setFileName(fileName);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        achievementDto.setApplyer(empSessionDto.getId());
        achievementDto.setApplyerName(empSessionDto.getName());
        achievementDto.setDeptId(empSessionDto.getDeptId());
        achievementDto.setDeptName(empSessionDto.getDeptName());

        if (!StringUtils.isNullOrWhiteSpace(sex)) {
            if (sex.equals("1")) {
                achievementDto.setSex(Sex.boy);
            }
            if (sex.equals("2")) {
                achievementDto.setSex(Sex.girl);
            }
        }

//        if (educationDegree.equals("1")){
//            achievementDto.setEducationDegree(EducationDegree.HighSchool);
//        }
//        if (educationDegree.equals("2")){
//            achievementDto.setEducationDegree(EducationDegree.JuniorCollege);
//        }
//        if (educationDegree.equals("3")){
//            achievementDto.setEducationDegree(EducationDegree.Undergraduate);
//        }
//        if (educationDegree.equals("4")){
//            achievementDto.setEducationDegree(EducationDegree.GraduatetudentS);
//        }
//        if (educationDegree.equals("5")){
//            achievementDto.setEducationDegree(EducationDegree.Master);
//        }
//        if (educationDegree.equals("6")){
//            achievementDto.setEducationDegree(EducationDegree.Doctor);
//        }

        if (!StringUtils.isNullOrWhiteSpace(achievementTransfor)) {
            if (achievementTransfor.equals("3")) {
                achievementDto.setAchiTransformation(AchiTransformation.yes);
            }
            if (achievementTransfor.equals("4")) {
                achievementDto.setAchiTransformation(AchiTransformation.no);
            }
        }

        if (!StringUtils.isNullOrWhiteSpace(achiPatent)) {
            if (achiPatent.equals("5")) {
                achievementDto.setAchiPatent(AchiPatent.yes);
            }
            if (achiPatent.equals("6")) {
                achievementDto.setAchiPatent(AchiPatent.no);
            }
        }

        if (!StringUtils.isNullOrWhiteSpace(time)){
            achievementDto.setAchiCreateTime(DateHelper.parse(time,"yyyy-MM-dd"));
        }
        achievementDto.setAge(Integer.parseInt(age));
        achievementDto.setApplyUnit(deptName);
        achievementDto.setCreatorName(hidEmpName);


        CommonResult commonResult = forminnoas.addByDto(achievementDto);
        result=forminnoas.submitForm(commonResult.getId(),achievementDto.getFormKind());
        return result;
    }

    /**
     * 审核页面 查询
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_syy_dq_lc03_data")
    @ResponseBody
    public FormInnovationAchievementDto queryData(HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormInnovationAchievementDto dto=forminnoas.getFormByFormNo(Long.parseLong(formNo));
        dto.setIsViewOnly(Boolean.valueOf(viewOnly));
        List<MultiSelectUserWithDeptDto> list=employeeService.getAllDeptEmp();
        dto.setProject_empDtos(list);

        String score = dto.getInnoOnlyMembersScores();
        if (!StringUtils.isNullOrWhiteSpace(score)) {
            String[] arr_score = score.split("//");
            dto.setInnoOnlyScores(arr_score);
        }
        String suggestion = dto.getInnoOnlyMembersAdvise();
        if (!StringUtils.isNullOrWhiteSpace(suggestion)) {
            String[] arr_suggestion = suggestion.split("//");
            dto.setInnoOnlyAdvise(arr_suggestion);
        }
//        String[] a = new  String[2];
//        a[0]="的接口是否";
//        a[1] ="拿到";
//        dto.setInnoOnlyScores(a);
        return dto;
    }

    /**
     * 统计页面 首页
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_p_c")
    public ModelAndView indeCount(Model  model,HttpServletRequest request) {
        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);
        if (!empSessionDto.getDeptNo().equals("02YZBGS") && !empSessionDto.getDeptNo().equals("01YLD"))//TODO 部门编码 hotfix
        {
            List<DeptmentDto> deptmentDtos = new ArrayList<>();
            DeptmentDto dpt = new DeptmentDto();
            dpt.setId(empSessionDto.getDeptId());
            dpt.setDeptName(empSessionDto.getDeptName());
            deptmentDtos.add(dpt);
            model.addAttribute("depts", deptmentDtos);
        } else {
            List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
            model.addAttribute("depts", deptmentDtos);
        }
        return view(layout, "partyGroup/syy_dq_lc03_a_count", model);
    }

    /**
     * 统计页面查询
     * @param criteria
     * @param response
     */
    @RequestMapping(value = "ajax_query_getAll")
    public void queryAll(InnovationAchievementCriteria criteria,HttpServletResponse response,HttpServletRequest request){
        String proName = request.getParameter("proName");
        if (!StringUtils.isNullOrWhiteSpace(proName))
            criteria.setProjectName(URI.deURI(proName));

        String comLeader = request.getParameter("comLeader");
        if (!StringUtils.isNullOrWhiteSpace(comLeader))
            criteria.setCreatorName(URI.deURI(comLeader));

        String dept_name = request.getParameter("dept_name");
        if (!StringUtils.isNullOrWhiteSpace(dept_name))
            criteria.setDeptName(URI.deURI(dept_name));


        String begTime = request.getParameter("begTime");
        if (!StringUtils.isNullOrWhiteSpace(begTime))
            criteria.setBeginTime(DateHelper.parse(begTime, "yyyy-MM-dd"));
        String enTime = request.getParameter("enTime");
        if (!StringUtils.isNullOrWhiteSpace(enTime)) {
            String end_time = enTime+" 23:59:59";
            criteria.setEndTime(DateHelper.parse(end_time, "yyyy-MM-dd HH:mm:ss"));
        }

        PagedResult<InnovationAchievementDto> pagedResult=innovationAchievementService.getPagesInnovationAchiDto(criteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }


    /**
     * 单个查询 详情
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_query_innoAchievement_countDetail")
    public ModelAndView queryCountDetail(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            InnovationAchievementDto dto = innovationAchievementService.getById(Long.parseLong(id));

            String score = dto.getInnoOnlyMembersScores();
            if (!StringUtils.isNullOrWhiteSpace(score)) {
                String[] arr_score = score.split("//");
                dto.setInnoOnlyScores(arr_score);
            }
            String suggestion = dto.getInnoOnlyMembersAdvise();
            if (!StringUtils.isNullOrWhiteSpace(suggestion)) {
                String[] arr_suggestion = suggestion.split("//");
                dto.setInnoOnlyAdvise(arr_suggestion);
            }


            model.addAttribute("beans",dto);
        }
        model.addAttribute("help", Helper.class);
        return view(layout,"partyGroup/syy_dq_lc03_a_count_detail",model);
    }


    /**
     * 查看全部未申请审批页面
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_innoAchieve_fail_all")
    public ModelAndView failAll(Model model,HttpServletRequest request){
        String clStep = request.getParameter("step");
        if (!StringUtils.isNullOrWhiteSpace(clStep))
            model.addAttribute("step",clStep);
        List<DeptmentDto> deptmentDtos=deptmentService.getAllDept();
        model.addAttribute("depts",deptmentDtos);

        List<MultiSelectUserWithDeptDto> list = employeeService.getAllDeptEmp();
        model.addAttribute("beans",list);

        return view(applyLayout,"partyGroup/syy_dq_lc03_a_failAll",model);
    }

    /**
     *  数据查询
     * @param request
     * @param criteria
     * @param response
     */
    @RequestMapping(value = "ajax_query_failAll_innoAchievement")
    public void queryFailAll(HttpServletRequest request,InnovationAchievementCriteria criteria,HttpServletResponse response){

        String proName = request.getParameter("proName");
        if (!StringUtils.isNullOrWhiteSpace(proName))
            criteria.setProjectName(URI.deURI(proName));

        String dept_name = request.getParameter("dept_name");
        if (!StringUtils.isNullOrWhiteSpace(dept_name))
            criteria.setDeptName(URI.deURI(dept_name));

        String begTime = request.getParameter("begTime");
        if (!StringUtils.isNullOrWhiteSpace(begTime))
            criteria.setBeginTime(DateHelper.parse(begTime,"yyyy-MM-dd"));
        String enTime = request.getParameter("enTime");
        if (!StringUtils.isNullOrWhiteSpace(enTime))
            criteria.setEndTime(DateHelper.parse(enTime,"yyyy-MM-dd"));

        PagedResult<FormInnovationAchievementDto> pagedResult=forminnoas.getPagesFormsByFailAll(criteria);
        List<MultiSelectUserWithDeptDto> list=employeeService.getAllDeptEmp();
        for (FormInnovationAchievementDto dto:pagedResult.getItems()){
            dto.setProject_empDtos(list);
        }
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_innoAhic_failAll_detail")
    public ModelAndView queryFailAllDetail(Model model,HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        FormInnovationAchievementDto dto=forminnoas.getFormByFormNo(Long.parseLong(formNo));


        String scores = dto.getInnoOnlyMembersScores();
        if (!StringUtils.isNullOrWhiteSpace(scores)){
            String[] arr_scores = scores.split("//");
            dto.setInnoOnlyScores(arr_scores);
        }
        String suggestions = dto.getInnoOnlyMembersAdvise();
        if (!StringUtils.isNullOrWhiteSpace(suggestions)){
            String[] arr_suggestions = suggestions.split("//");
            dto.setInnoOnlyAdvise(arr_suggestions);
        }

        model.addAttribute("beans",dto);
        model.addAttribute("help",Helper.class);
        return view("partyGroup/syy_dq_lc03_a_failAll_detail",model);
    }



    /**
     * 批量审核页面 单个提交
     * @param dto
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_approve_singleOrAll_innoAchi")
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
        CommonResult result= forminnoas.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 批量审核页面 批量提交
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_innoAchievement_approve_All")
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

            result= forminnoas.doApprove(dto,param);
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
    @RequestMapping(value = "ajax_approve_rejectSingle_innoAchi")
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
        CommonResult result= forminnoas.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    /**
     * 批量审核页面 批量否决
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_innoAchi_reject_All")
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
            result= forminnoas.doApprove(dto,param);
        }

        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "sra_tt")
    public ModelAndView tt(Model model){

        return  view(applyLayout,"partyGroup_edit/test",model);
    }



    @RequestMapping(value = "syy_dq_03_imageGenerate",method = RequestMethod.POST)
    @ResponseBody
    public boolean imageGenerate(HttpServletRequest request){

        String imgStr = request.getParameter("dataImage");
        String[] imageData = imgStr.split(",");
        String actualData = imageData[1];
        String formNo = request.getParameter("formNo");
        FormInnovationAchievementDto achievement = null;
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            achievement = forminnoas.getFormByFormNo(Long.parseLong(formNo));
        }

//        if (!StringUtils.isNullOrWhiteSpace(actualData)){
//            achievement.setImageData(actualData);
//        }

        //返回一个图片下载的相对地址 eg: domain/images/formNo.jpg
        achievement.setImageStatus("已生成");
        forminnoas.updateByDto(achievement);//保存base64图片字符串数据状态

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
            //String imagePath = InnovationAchievementController.class.getClassLoader().getResource("出差证模板.docx").getPath().replace("/","\\");

            //String imgFilePath = "C:\\Users\\admin\\Desktop\\五小成果荣誉证书.jpg";//新生成的图片
            String path = Config.getConfigParameter("dqimg", "../config.properties");

            OutputStream out = new FileOutputStream(path+formNo+".jpg");


//            String [] fileName = getFileName(path);
//
//            ArrayList<String> listFileName = new ArrayList<String>();
//            getAllFileName(path,listFileName);




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

    /**
     *第四步 图片生成 页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_dq_lc03_iframe_html")
    public ModelAndView iframeHtml(Model model,HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        String prizeLevel = request.getParameter("prizeLevel");
        model.addAttribute("formNo",formNo);
        FormInnovationAchievementDto dto=null;
        if (!StringUtils.isNullOrWhiteSpace(formNo))
            dto = forminnoas.getFormByFormNo(Long.parseLong(formNo));
        String[] str = dto.getCreatorName().split(",");
        dto.setMainPeople(str);

        if (!StringUtils.isNullOrWhiteSpace(prizeLevel))
            dto.setImage_awardLevel(prizeLevel);
        model.addAttribute("dto",dto);

        Date currentTime = new Date();
        model.addAttribute("currentTime",DateHelper.formatDate(currentTime,"yyyy年M月"));

        String[] name = dto.getFormKindName().split("流程");
        model.addAttribute("name",name[0]);

        return view( "partyGroup/syy_dq_lc03_v_image",model);
    }

    /**
     * 获取文件夹下所有的文件名
     * @param path
     * @return
     */
    public static String [] getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }

    public static void getAllFileName(String path,ArrayList<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
            fileName.addAll(Arrays.asList(names));
        for(File a:files)
        {
            if(a.isDirectory())
            {
                getAllFileName(a.getAbsolutePath(),fileName);
            }
        }
    }

    /**
     * 统计 单个详情 证书下载
     * @param request
     * @param response
     */
    @RequestMapping(value = "downloadImage",method = RequestMethod.GET)
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
     * 五小成果申报 申请页面 主要参加人
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_partyGroup_lc03_mainPeople")
    @ResponseBody
    public ListResult<MultiSelectUserWithDeptDto> queryMainPeople(HttpServletRequest request){
        List<MultiSelectUserWithDeptDto> list = employeeService.getAllDeptEmp();
        return new ListResult<MultiSelectUserWithDeptDto>(list);
    }

}
