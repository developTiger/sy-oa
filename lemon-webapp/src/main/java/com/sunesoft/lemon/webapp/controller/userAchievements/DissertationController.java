package com.sunesoft.lemon.webapp.controller.userAchievements;

import com.sunesoft.lemon.deanery.treatiseCG.application.TreatiseService;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseFileDto;
import com.sunesoft.lemon.deanery.treatiseCG.domain.Treatise;
import com.sunesoft.lemon.deanery.typeOfTreatise.domain.TypeOfTreatise;
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
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.demo.MultiSelectUserWithDept;
import com.sunesoft.lemon.webapp.function.UserSession;
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
import java.util.*;

/**
 * Created by pxj on 2016/9/23.
 * 论文审批流程
 */
@Controller
public class DissertationController extends Layout {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    TreatiseService treatiseService;
    @Autowired
    FormListService formListService;
    @Autowired
    UserSession us;
    @Autowired
    FormHeaderService headerService;
    @Autowired
    FileService fileService;
    @Autowired
    DeptmentService deptmentService;
    @Autowired
    TypeOfTreatiseService typeOfTreatiseService;
    /*
     * 申请页面
     * */
    @RequestMapping(value="syy_gy_lc04_a")
    public ModelAndView syy_gy_lc04_a(Model model, HttpServletRequest request){
        FormListDto dto =formListService.getFormListInfo("SYY_GY_LC04");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
      //  List<DeptmentDto> deptment= deptmentService.getByDeptsIds();
      //  model.addAttribute("deptment", deptment);
       // List<EmpDto> list1=employeeService.getAllEmps();
     /*   Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list1){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);*/
  /*      List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("people",list);
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
        model.addAttribute("empInfos", multiSelectUserWithDepts);*/
        String formNo=request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)){
            TreatiseDto treatiseDto=treatiseService.getByFormNoTreatise(Long.parseLong(formNo));
            treatiseDto.setPublish_EndTime(DateHelper.formatDate(treatiseDto.getPublish_EndTimes(),"yyyy-MM-dd"));
            model.addAttribute("beans_back",treatiseDto);
            model.addAttribute("formNo",treatiseDto.getFormNo());
            if(!StringUtils.isNullOrWhiteSpace(treatiseDto.getInfo())){
   /*             String []peoples=treatiseDto.getInfo().split(",");
                String peoples2=new String();
                for(int i=0;i<peoples.length;i++){
                    String [] ren=peoples[i].split("@");
                    peoples2+=ren[1]+",";
                }
                peoples2=peoples2.substring(0,peoples2.length()-1);
                model.addAttribute("peoples", peoples2);*/
            }
        }else {
            List<TypeOfTreatiseDto> typeOfTreatiseDtos=typeOfTreatiseService.findAll();
            model.addAttribute("typeOfTreatise",typeOfTreatiseDtos);
        }
        return view(applyLayout,"userAchievements/syy_gy_lc04_a",model);
    }

    /*
* 通过流程新增数据
* */
    @Autowired
    FormApproveRoleService formApproveRoleService;
    @Autowired
    FormApproveListService formApproveListService;
    @RequestMapping(value = "add_update_Dissertation", method = RequestMethod.POST)
    //@ResponseBody
    public String add_update_Dissertation(TreatiseDto treatiseDto,HttpServletRequest request, HttpServletResponse response) {
        EmpSessionDto userInfo = us.getCurrentUser(request);
        CommonResult result = null;
        boolean b=judge(treatiseDto);
        if (!b){
            result = new CommonResult(false, "该论著名称已申请过");
            AjaxResponse.write(response, JsonHelper.toJson(result));
        }else {
            List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName");
            String winner_Info = request.getParameter("winner_Info1");
            treatiseDto.setWinner_info(winner_Info);

            List<TreatiseFileDto> list = new ArrayList<>();
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
                            TreatiseFileDto treatiseFileDto = new TreatiseFileDto();
                            treatiseFileDto.setFileId(id);
                            treatiseFileDto.setFileName(fileName);
                            list.add(treatiseFileDto);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (treatiseDto.getAlready_upFileId() != null) {
                for (int i = 0; i < treatiseDto.getAlready_upFileId().size(); i++) {
                    TreatiseFileDto treatiseFileDto = new TreatiseFileDto();
                    treatiseFileDto.setFileId(treatiseDto.getAlready_upFileId().get(i));
                    treatiseFileDto.setFileName(treatiseDto.getAlready_upFileName().get(i));
                    list.add(treatiseFileDto);
                }
            }
/*        String roleName="";
        if(!StringUtils.isNullOrWhiteSpace(treatiseDto.getAwards_Type())){
            switch (treatiseDto.getAwards_Type()){
                case "1":
                    roleName="科管审核员";//科研
                    break;
                case"2":
                    roleName="安运审核员";//生产运行
                    break;
                case"3":
                    roleName="安运审核员";//质量安全
                    break;
                case"4":
                    roleName="院办审核员";//综治维稳
                    break;
                case "5":
                    roleName="党群审核员";//工会共青团
                    break;
                case "6":
                    roleName="人事审核员";//党务
                    break;
                default:
                    roleName="";
                    break;

            }
            List<FormAppRoleDto> rolelist=formApproveRoleService.getTreatiseRoleID(roleName);
            FormListCriteria criteria=new FormListCriteria();
            criteria.setFormKind(treatiseDto.getFormKind());
            FormApproveRole role= new FormApproveRole();
            role.setId(rolelist.get(0).getId());
            formApproveListService.updateTreatiseApproveByCriteria(criteria,role);
        }*/
            String deptName = "";//类型根据类型选择确定部门名称：部门名称现为固定（等待后续解决方案）
            if (!StringUtils.isNullOrWhiteSpace(treatiseDto.getAwards_Type())) {
     /*       switch (treatiseDto.getAwards_Type()){
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
                Long dept_id = typeOfTreatiseService.findByName(treatiseDto.getAwards_Type());
                treatiseDto.setBlongDeptId(dept_id);
        /*      List<DeptmentDto> dtos= deptmentService.getDeptsByName(deptName);
          if (dtos.size() != 0){
                treatiseDto.setBlongDeptId(dtos.get(0).getId());
            }else {
                result = new CommonResult(false,"后台部门名称对应错误");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return JsonHelper.toJson(result);
            }*/
            } else {
                result = new CommonResult(false, "请选择论著类型！！！！！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return JsonHelper.toJson(result);
            }
            treatiseDto.setFilesDto(list);
            if (treatiseDto.getPublish_EndTime() != null) {
                treatiseDto.setPublish_EndTimes(DateHelper.parse(treatiseDto.getPublish_EndTime(), "yyyy-MM-dd"));
            }
            treatiseDto.setPublish_Time(DateHelper.parse(treatiseDto.getPublish_Time_(), "yyyy-MM-dd"));

            treatiseDto.setFirst_Author(userInfo.getName());
            CommonResult commonResult = treatiseService.addOrUpdate2(treatiseDto);
            result = treatiseService.submitForm(commonResult.getId(), treatiseDto.getFormKind());
        }
        return JsonHelper.toJson(result);
    }

    /*
* 第一步审核页面
* */
    @RequestMapping(value = "syy_gy_lc04_view1")
    public ModelAndView syy_gy_lc04_view1(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("viewOnly",viewOnly);
        FormHeaderDto dto2 = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto2);
        if (formNo!=null && formNo!=0L) {
            TreatiseDto dto=treatiseService.getByFormNoTreatise(formNo);
            dto.setPublish_EndTime(DateHelper.formatDate(dto.getPublish_EndTimes(),"yyyy-MM-dd"));
            model.addAttribute("beans",dto);
         //   String peoples2=new String();
         /*    if(!StringUtils.isNullOrWhiteSpace(dto.getInfo())){
               // String []peoples=dto.getInfo().split(",");
               for(int i=0;i<peoples.length;i++){
                    String [] ren=peoples[i].split("@");
                    peoples2+=ren[1]+",";
                }
                peoples2=peoples2.substring(0,peoples2.length()-1);
                model.addAttribute("peoples", peoples2);
            }else {
                peoples2=null;
            }*/

        }
        List<EmpDto> list1=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list1){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("people",list);
        return view(viewOnly.equals("false")?formLayout:formViewLayout,  "userAchievements/syy_gy_lc04_view", model);
    }

    /*
    * 第一步审核
    * */
    @RequestMapping(value ="gy_lc04_approve1")
    @ResponseBody
    public CommonResult gy_lc04_approve1(TreatiseDto treatiseDto,ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result=treatiseService.addOrUpdate3(treatiseDto,dto);
        return result;
    }

    /*
    * 第二步审核页面
    * */
    @RequestMapping(value = "syy_gy_lc04_view2")
    public ModelAndView syy_gy_lc04_view2(HttpServletRequest request,Model model) {
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        //String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        model.addAttribute("viewOnly",viewOnly);
        FormHeaderDto dto2 = headerService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto2);
        String id = request.getParameter("id");
        if (formNo!=null && formNo!=0L) {
            TreatiseDto dto=treatiseService.getByFormNoTreatise(formNo);
            dto.setPublish_EndTime(DateHelper.formatDate(dto.getPublish_EndTimes(),"yyyy-MM-dd"));
            model.addAttribute("beans",dto);
/*            String peoples2=new String();
            if(!StringUtils.isNullOrWhiteSpace(dto.getInfo())){
                String []peoples=dto.getInfo().split(",");
                for(int i=0;i<peoples.length;i++){
                    String [] ren=peoples[i].split("@");
                    peoples2+=ren[1]+",";
                }
                peoples2=peoples2.substring(0,peoples2.length()-1);
                model.addAttribute("peoples", peoples2);
            }*/
        }
        List<EmpDto> list1=employeeService.getAllEmps();
        Map<String,String> map = new HashMap<String,String>();
        for (EmpDto ed : list1){
            map.put(String.valueOf(ed.getId()),ed.getName());
        }
        String json = JsonHelper.toJson(map);
        model.addAttribute("emp_fb",json);
        List<EmpDto> list=employeeService.getAllEmps();
        model.addAttribute("people",list);
        return view(viewOnly.equals("false")?formLayout:formViewLayout,  "userAchievements/syy_gy_lc04_view1", model);
    }


    /*
    * 第二步审核
    * */
    @RequestMapping(value ="gy_lc04_approve2")
    @ResponseBody
    public CommonResult gy_lc04_approve2(TreatiseDto treatiseDto,ApproveFormDto dto, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        CommonResult result=treatiseService.addOrUpdate3(treatiseDto,dto);
        return result;
    }

     public Boolean judge(TreatiseDto treatiseDto){
         boolean b=false;
         b=treatiseService.findTreatise(treatiseDto);
         return b;
     }


}
