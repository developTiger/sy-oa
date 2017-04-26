package com.sunesoft.lemon.webapp.controller.resultCertificate;

import com.sunesoft.lemon.deanery.StringCommHelper;
import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.prizewinner.application.PrizewinnerService;
import com.sunesoft.lemon.deanery.projectCG.application.ProjectResultService;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.deanery.resultCertificate.application.ResultCertificateService;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateDto;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateFileDto;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificatePeopleDto;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificate;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificateFile;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificatePeople;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseFileDto;
import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseDto;
import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseService;
import com.sunesoft.lemon.fr.results.CommonResult;
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
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormAppRoleDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRole;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
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
import java.util.*;

/**
 * Created by pxj on 2016/9/28.
 */
@Controller
public class ResultCertificateFormController  extends Layout {

    @Autowired
    ProjectResultService projectResultService;
    @Autowired
    PrizewinnerService prizewinnerService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    FormHeaderService headerService;
    @Autowired
    FormListService formListService;
    @Autowired
    UserSession us;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    ResultCertificateService resultCertificateService;
    @Autowired
    TypeOfTreatiseService typeOfTreatiseService;

    /*
    * 申请页面*/
    @RequestMapping(value = "syy_gy_lc05_a")
    public ModelAndView syy_gy_lc05_a(Model model, HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        FormListDto dto =formListService.getFormListInfo("SYY_GY_LC05");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
     /*   List<DeptmentDto> deptmentDtos= deptmentService.getByDeptsIds();
        model.addAttribute("deptment", deptmentDtos);
        List<EmpDto> list=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        model.addAttribute("emp",list);*/
        model.addAttribute("winresultclassify", static_common.WIN_RESULT_CLASSIFY);
        List<String> WIN_LEVEL = new ArrayList<String>(static_common.WIN_LEVEL);
        model.addAttribute("winlevel",WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);
        if (!StringUtils.isNullOrWhiteSpace(formNo)){
            ResultCertificateDto resultCertificateDto=resultCertificateService.getResultCertificate(Long.parseLong(formNo));
            resultCertificateDto.setWin_Date_Str(DateHelper.formatDate(resultCertificateDto.getWin_Date(),"yyyy-MM-dd"));
            model.addAttribute("beans_back", resultCertificateDto);
            model.addAttribute("formNo",resultCertificateDto.getFormNo());
            String people_back="";
            String people_InAndOut_back="";
            for(int i=0;i<resultCertificateDto.getPeople().size();i++){
                people_back+=resultCertificateDto.getPeople().get(i).getPeople_Name()+",";
                people_InAndOut_back+=resultCertificateDto.getPeople().get(i).getPeople_InOrOut()+"@"+resultCertificateDto.getPeople().get(i).getPeople_Name()+",";
            }
            if(people_back.length()>0){
                model.addAttribute("people_back",people_back.substring(0,people_back.length()-1));
            }
            if(people_InAndOut_back.length()>0){
                model.addAttribute("people_All_back",people_InAndOut_back.substring(0,people_InAndOut_back.length()-1));
            }
        }else {
            model.addAttribute("beans_back",null);
            List<TypeOfTreatiseDto> typeOfTreatiseDtos=typeOfTreatiseService.findAll();
            model.addAttribute("typeOfTreatise",typeOfTreatiseDtos);
        }
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            Long id1 =Long.parseLong(id);
            ProjectResultDto projectResultDto=projectResultService.getById(id1);
            Date d = projectResultDto.getWin_Date();
            projectResultDto.setWin_Date_Str(DateHelper.formatDate(d,"yyyy-MM-dd"));
            model.addAttribute("beans", projectResultDto);
          //  model.addAttribute("formNo",projectResultDto.getFormNo());
        }
        return view(applyLayout,"resultCertificate/syy_gy_lc05_a",model);
    }

    /*
    * 通过流程新增数据
    * */
    @Autowired
    FormApproveRoleService formApproveRoleService;
    @Autowired
    FormApproveListService formApproveListService;
    @Autowired
    FileService fileService;
    @RequestMapping(value = "add_update_ResultCertificate", method = RequestMethod.POST)
  //  @ResponseBody
    public String add_update_ResultCertificate(ResultCertificateDto resultCertificateDto, HttpServletRequest request, HttpServletResponse response) {
        CommonResult result=null;
        boolean b=judge(resultCertificateDto);
        if(!b){
            result = new CommonResult(false, "该成果名称已申请过");
            AjaxResponse.write(response, JsonHelper.toJson(result));
        }else {
            List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName");
            List<ResultCertificateFileDto> list = new ArrayList<>();
            List<ResultCertificatePeopleDto> peopleDtoList = new ArrayList<>();
            for (MultipartFile myfile : myfiles) {
                if (!myfile.isEmpty()) {
                /*result = new CommonResult(false,"请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));*/
                    //return;
            /*} else {*/
                    try {
                        String fileName = myfile.getOriginalFilename();
                        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        FileInfoDto fileInfoDto = new FileInfoDto();
                        fileInfoDto.setFileName(fileName);
                        fileInfoDto.setExtensions(extension);
                        fileInfoDto.setInputStream(myfile.getInputStream());
                        String id = fileService.upload(fileInfoDto);
                        if (!StringUtils.isNullOrWhiteSpace(id)) {
                            ResultCertificateFileDto resultCertificateFileDto = new ResultCertificateFileDto();
                            resultCertificateFileDto.setFileId(id);
                            resultCertificateFileDto.setFileName(fileName);
                            list.add(resultCertificateFileDto);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            String deptName = "";
            if (!StringUtils.isNullOrWhiteSpace(resultCertificateDto.getAwards_Type())) {
/*            switch (resultCertificateDto.getAwards_Type()){
                case "1":
                    deptName="科研管理科";//科研
                    break;
                case"2":
                    deptName="质量安全运行科";//生产运行
                    break;
                case"3":
                    deptName="质量安全运行科";//质量安全
                    break;
                case"4":
                    deptName=" 院长（党委）办公室";//综治维稳
                    break;
                case "5":
                    deptName="党群工作科（工会办公室）";//工会共青团
                    break;
                case "6":
                    deptName=" 人事（组织）科";//党务
                    break;
                default:
                    deptName="";
                    break;
            }*/
                // List<DeptmentDto> dtos= deptmentService.getDeptsByName(deptName);
//            List<FormAppRoleDto> rolelist=formApproveRoleService.getTreatiseRoleID(roleName);
//            FormListCriteria criteria=new FormListCriteria();
//            criteria.setFormKind(resultCertificateDto.getFormKind());
//            FormApproveRole role= new FormApproveRole();
//            role.setId(rolelist.get(0).getId());
//            //根据类型选择审核人
//            formApproveListService.updateResultCetificateApproveByCriteria(criteria,role);
  /*          if (dtos.size() !=0){
                resultCertificateDto.setBlongDeptId(dtos.get(0).getId());
            }else {
                result = new CommonResult(false,"后台部门名称对应错误");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return JsonHelper.toJson(result);
            }*/
                Long dept_id = typeOfTreatiseService.findByName(resultCertificateDto.getAwards_Type());
                resultCertificateDto.setBlongDeptId(dept_id);

            } else {
                result = new CommonResult(false, "请选择成果类型！！！！！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return JsonHelper.toJson(result);
            }

            EmpSessionDto userInfo = us.getCurrentUser(request);
            resultCertificateDto.setAwards_Name(userInfo.getName());

 /*  获奖人
 String winner_Info=request.getParameter("winner_Info1");
        if(!StringUtils.isNullOrWhiteSpace(winner_Info)){
            String[] infos = winner_Info.split(",");
            for(int i=0;i<infos.length;i++){
                ResultCertificatePeopleDto peopleDto=new ResultCertificatePeopleDto();
                String p[]=infos[i].split("@");
                peopleDto.setPeople_InOrOut(p[0]);
                peopleDto.setPeople_Name(p[1]);
                peopleDtoList.add(peopleDto);
            }
        }*/

//        for (String in : infos) {
//            prizewinner = this.getPrizeWinner(id,winner_Info,in);
//            prizewinnerRepository.save(prizewinner);
//        }
       /* for(int i=0;i<infos.length;i++){
            prizewinner = this.getPrizeWinner(id,winner_Info,infos[i]);
            prizewinner.setSortNo((i+1));
            //prizewinnerRepository.save(prizewinner);
        }*/
//        String result = projectResultService.addOrUpdateProjectResult2(projectResultDto) > 0 ? "success" : "error";
//        System.out.println(result);
//        AjaxResponse.write(response, "text", result);
            if (resultCertificateDto.getAlready_upFileId() != null) {
                for (int i = 0; i < resultCertificateDto.getAlready_upFileId().size(); i++) {
                    ResultCertificateFileDto resultCertificateFileDto = new ResultCertificateFileDto();
                    resultCertificateFileDto.setFileId(resultCertificateDto.getAlready_upFileId().get(i));
                    resultCertificateFileDto.setFileName(resultCertificateDto.getAlready_upFileName().get(i));
                    list.add(resultCertificateFileDto);
                }
            }
            resultCertificateDto.setFiles(list);
            resultCertificateDto.setPeople(peopleDtoList);
            resultCertificateDto.setWin_Date(DateHelper.parse(resultCertificateDto.getWin_Date_Str(), "yyyy-MM-dd"));
            CommonResult commonResult = resultCertificateService.addByDto(resultCertificateDto);
            //CommonResult commonResult=projectResultService.addOrUpdateProjectResult2(projectResultDto);
            result = resultCertificateService.submitForm(commonResult.getId(), resultCertificateDto.getFormKind());
        }
        return  JsonHelper.toJson(result);
    }
    /*
    * 第一步审核页面
    * */
    @RequestMapping(value = "syy_gy_lc05_view1")
    public ModelAndView syy_gy_lc05_view1(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
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
        model.addAttribute("winlevel",WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);
        if (formNo!=null && formNo!=0L) {
            ResultCertificateDto resultCertificateDto=resultCertificateService.getResultCertificate(formNo);
            resultCertificateDto.setWin_Date_Str(DateHelper.formatDate(resultCertificateDto.getWin_Date(),"yyyy-MM-dd"));
            model.addAttribute("beans", resultCertificateDto);
        }
        return view(viewOnly.equals("false")?formLayout:formViewLayout,  "resultCertificate/syy_gy_lc05_view1", model);
    }
    /*
    * 第一步审核操作*/
    @RequestMapping(value ="syy_gy_lc05_approve1")
    @ResponseBody
    public CommonResult syy_gy_lc05_approve1(ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result= resultCertificateService.nextProject(dto);
        return  result;
    }
    /*
    * 第二步审核页面*/
    @RequestMapping(value = "syy_gy_lc05_view2")
    public ModelAndView syy_gy_lc05_view2(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
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
        model.addAttribute("winlevel",WIN_LEVEL);
        model.addAttribute("patenttype",static_common.PATENT_TYPE);
        if (formNo!=null && formNo!=0L) {
            ResultCertificateDto resultCertificateDto=resultCertificateService.getResultCertificate(formNo);
            resultCertificateDto.setWin_Date_Str(DateHelper.formatDate(resultCertificateDto.getWin_Date(),"yyyy-MM-dd"));
            model.addAttribute("beans", resultCertificateDto);
        }
        return view(viewOnly.equals("false")?formLayout:formViewLayout,  "resultCertificate/syy_gy_lc05_view2", model);
    }
    /*
    * 第二步审核操作*/
    @RequestMapping(value ="syy_gy_lc05_approve2")
    @ResponseBody
    public CommonResult syy_gy_lc05_approve2(ApproveFormDto dto,HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result= resultCertificateService.nextProject(dto);
        return  result;
    }


    public boolean judge(ResultCertificateDto resultCertificateDto){
        boolean b=false;
        b=resultCertificateService.findResultCertificate(resultCertificateDto);
        return  b;
    }

}
