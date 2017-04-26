package com.sunesoft.lemon.webapp.controller.car_driver_flow;

import com.sunesoft.lemon.deanery.ReceptionFlow.application.Reception2Service;
import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionDto;
import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionDtoTemp;
import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUCriteria;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
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
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.controller.demo.MultiSelectUserWithDept;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.ToolBymoney;
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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GuestApplyController extends Layout {
    @Autowired
    Reception2Service receptionService;
    @Autowired
    UserSession us;
    @Autowired
    DeptmentService deptmentService;

    @Autowired
    DriverService driverService;

    @Autowired
    FormHeaderService headerService;

    @Autowired
    FileService fileService;

    @Autowired
    EmployeeService employeeService;
    @Autowired
    FormListService formListService;
    @RequestMapping("syy_yw_lc02_a")
    public ModelAndView guestApply(Model model, HttpServletRequest request) {
        FormListDto dto =formListService.getFormListInfo("SYY_YW_LC02");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("formlist_id",dto.getId());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
//        List<DeptmentDto> list = deptmentService.getAllDept();
//        model.addAttribute("dept", list);
        /*model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        String formNo = request.getParameter("formNo");
        String url=receptionService.getApplyUrl("1df48afcfa1d433992e4884f81ab448e");
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        model.addAttribute("s",empSessionDto.getDeptId());*/
//        return view(layout,"reception_flow/syy_yw_lc02_a", model);

        String formNo=request.getParameter("formNo");
        ReceptionDto receptionDto=null;
        if(formNo!=null&&formNo!=""){
            Long fm=Long.parseLong(formNo);
            receptionDto=receptionService.getFormByFormNo(fm);
        }
        model.addAttribute("receptionDtoApply",receptionDto);
        return view(applyLayout, "reception_flow/syy_yw_lc02_a", model);
    }

    @RequestMapping("ajax_syy_yw_lc02_data")
    @ResponseBody
    public ReceptionDto guestView(Model model, HttpServletRequest request) {
        String formKind = request.getParameter("formNo");
        ReceptionDto receptionDto = null;//receptionService.getFormByFormNo(Long.parseLong(formKind));
        return receptionDto;
    }

    @RequestMapping("ajax_add_update_jd_flow")
    @ResponseBody
    public String addOrUpdateLeave(ReceptionDto receptionDto, HttpServletRequest request, Model model, HttpServletResponse response) {
        //TODO 这里要将申请人ID、姓名、部门ID、部门名称记录
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        CommonResult result =null;
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            result=new CommonResult(false,"无id");
        } else {

            String linkman = request.getParameter("linkman");
            String mobile = request.getParameter("mobile");
            String foreignGuest = request.getParameter("foreignGuest");
            String reasons = request.getParameter("reasons");

            receptionDto.setLinkman(linkman);
            receptionDto.setMobile(mobile);
            receptionDto.setForeignGuest(foreignGuest);
            receptionDto.setReasons(reasons);

            receptionDto.setApplyer(empSessionDto.getId());
            receptionDto.setApplyerName(empSessionDto.getName());
            receptionDto.setDeptId(empSessionDto.getDeptId());
            receptionDto.setDeptName(empSessionDto.getDeptName());


            result = receptionService.addByDto(receptionDto);
            receptionService.submitForm(result.getId(), receptionDto.getFormKind());

            //*******************上传*******************
            Long formNo=result.getId();

            List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("projectFile");

            //CommonResult result=null;
            for (MultipartFile myfile : myfiles) {
                if (myfile.isEmpty()) {
                    //result = new CommonResult(false,"请选择要上传的文件！");
                    //break;
                } else {
                    try {
                        String fileName = myfile.getOriginalFilename();
                        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        FileInfoDto fileInfoDto=new FileInfoDto();
                        fileInfoDto.setFileName(fileName);
                        fileInfoDto.setIsPublic(false);
                        fileInfoDto.setDeptId(empSessionDto.getDeptId());
                        fileInfoDto.setExtensions(extension);
                        fileInfoDto.setInputStream(myfile.getInputStream());
                        String fileId=fileService.upload(fileInfoDto);

                        if(!StringUtils.isNullOrWhiteSpace(fileId)) {
                            receptionDto.setFileId(fileId);
                            receptionDto.setFileName(fileName);
                        }
                        //result= receptionService.uploadProjectFile(Long.parseLong(formNo),fileId, fileName);
                        result= receptionService.uploadProjectFile(formNo,fileId, fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return  JsonHelper.toJson(result);
    }

    @RequestMapping("approveApply_data")
    @ResponseBody
    public List<FormApprover> approveApply(HttpServletRequest request, HttpServletResponse response) {
        String formNo = request.getParameter("formNo");
        String clStep = request.getParameter("nextClstep");
        List<FormApprover> list = null;//receptionService.getNextAppInfo(Long.parseLong(formNo), Integer.parseInt(clStep));
        return list;
    }


    @RequestMapping("ajax_employ_data")
    @ResponseBody
    public List<EmpDto> getEmployData(HttpServletRequest request, HttpServletResponse response) {
        List<EmpDto> emp_list = employeeService.getAllEmps();
        return emp_list;
    }

    @RequestMapping("syy_yw_lc02_view")
    public ModelAndView syy_yw_lc02_view(Model model, HttpServletRequest request, ReceptionDto receptionDto) {


//        EmpSessionDto userInfo = us.getCurrentUser(request);
//
//        String processId=request.getParameter("processId");
//        String taskId=request.getParameter("taskId");
//        String orderId=request.getParameter("orderId");
//
//        model.addAttribute("task_id",taskId);
//        model.addAttribute("order_id",orderId);
//
//        receptionDto.setProcessId(processId);
//        receptionDto.setOrderId(orderId);
//
//        ReceptionDto receptionDto1=receptionService.getById(orderId);
//        model.addAttribute("receptionDto1",receptionDto1);
//
//        Long id=receptionDto1.getId();
//        model.addAttribute("id_w",id);
//
//        model.addAttribute("userInfo",userInfo);
//
//        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));
//
//        String formNo = request.getParameter("formNo");
//        //String url=receptionService.getApplyUrl("6902846a7c52465f9eb1c6dc77e1aa3a");
//        EmpSessionDto empSessionDto = us.getCurrentUser(request);
//        model.addAttribute("s",empSessionDto.getDeptId());

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);

        model.addAttribute("header", dto);
        model.addAttribute("viewOnly", viewOnly);
//        if(viewOnly.equals("true")){
//            return view("common/formbody",  "forms/syy_rs_lc01_v", model);
//        }
//        return view(formLayout,  "forms/syy_rs_lc01_v", model);

        ReceptionDto receptionDto1 = receptionService.getFormByFormNo(formNo);
        model.addAttribute("receptionDto1", receptionDto1);

        List<EmpDto> empDtos = employeeService.getAllEmps();

        model.addAttribute("emps", empDtos);

        return view(formLayout, "reception_flow/syy_yw_lc02_view", model);
    }

    @RequestMapping("ajax_reception_data")
    public void ajax_get_reception(Model model, HttpServletRequest request,HttpServletResponse response){
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);

        model.addAttribute("header", dto);
        model.addAttribute("viewOnly", viewOnly);
//        if(viewOnly.equals("true")){
//            return view("common/formbody",  "forms/syy_rs_lc01_v", model);
//        }
//        return view(formLayout,  "forms/syy_rs_lc01_v", model);

        List<Deptment> findAllDept = driverService.findAllDept();


        List<DeptmentDto> deptmentDtos=deptmentService.getAllDept();
        List<EmpDto> empDtos = employeeService.getAllEmps();
        List<MultiSelectUserWithDept> multiSelectUserWithDepts = new ArrayList<>();
        for(DeptmentDto dept:deptmentDtos){
            MultiSelectUserWithDept multiSelectUserWithDept = new MultiSelectUserWithDept(dept.getId(),dept.getDeptName());
            for(EmpDto emp:empDtos){
                if(emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts.add(multiSelectUserWithDept);
        }

        ReceptionDto receptionDto1 = receptionService.getFormByFormNo(formNo);


        List<Long> listId=new ArrayList<Long>();
        for(int i=0;i<empDtos.size();i++){
            Long id=empDtos.get(i).getLeaderId();
            listId.add(id);
        }
        /*for(int i=0 ; i<listId.size(); i++){
            for(int j=0 ; j<listId.size(); j++){
                if(listId.get(i)==listId.get(j)){
                    listId.remove(listId.get(i));
                }
            }
        }*/
        /*Collections.sort(listId);
        List<Long> result=new ArrayList<Long>();
        Long temp=0L;
        for(int i=0;i<listId.size();i++){
            if(listId.get(i)!=temp){
                result.add(listId.get(i));
                temp=listId.get(i);
            }
        }*/

        for (int i = 0; i < listId.size(); i++)  //外循环是循环的次数
        {
            for (int j = listId.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
            {
                if (listId.get(i) == listId.get(j))
                {
                    listId.remove(j);
                }
            }
        }

        List<EmpDto> listEmpDto=new ArrayList<EmpDto>();
        for(int i=0;i<listId.size();i++){
            if(listId.get(i)!=null){
                EmpDto empDto=employeeService.getLeader(listId.get(i));
                listEmpDto.add(empDto);
            }
        }

        List<String> deptList=null;
        if(receptionDto1.getCoopDept()!=null){
            deptList=new ArrayList<String>();
            String arr[]=receptionDto1.getCoopDept().split(",");
            for(int i=0;i<arr.length;i++){
                deptList.add(arr[i]);
            }
        }

        //根据部门名称查找部门员工
        List<EmpDto> empByNameList=null;
        List<EmpDto> empAll=new ArrayList<>();
        if(deptList!=null&&deptList.size()>0){
            for(int i=0;i<deptList.size();i++){
                empByNameList=employeeService.getEmpByDeptName(deptList.get(i));
                if(empByNameList!=null&&empByNameList.size()>0){
                    for(int j=0;j<empByNameList.size();j++){
                        empAll.add(empByNameList.get(j));
                    }
                }
            }

        }

        //receptionDto1.isViewOnly=Boolean.valueOf(viewOnly);
        Map<String,Object> map_json = new HashMap<String,Object>();
        if(receptionDto1.getPersonComp()!=null&&receptionDto1.getPersonComp()!=""){
            String personComp=receptionDto1.getPersonComp().substring(0,receptionDto1.getPersonComp().length()-1);
            map_json.put("personComp",personComp);
        }

        //修改主管领导审核接口
        List<DeptmentDto> deptmentDto = deptmentService.getAllDept();
        List<EmpDto> empDtos1 = employeeService.getAllEmps();
        List<com.sunesoft.lemon.webapp.model.MultiSelectUserWithDept> multiSelectUserWithDepts1 = new ArrayList<>();
        for(DeptmentDto dept:deptmentDto){
            com.sunesoft.lemon.webapp.model.MultiSelectUserWithDept multiSelectUserWithDept = new com.sunesoft.lemon.webapp.model.MultiSelectUserWithDept(dept.getId(),dept.getDeptName());
            for(EmpDto emp:empDtos1){
                if(emp.getDeptId().equals(dept.getId()))
                    multiSelectUserWithDept.getEmpDtos().add(emp);
            }
            multiSelectUserWithDepts1.add(multiSelectUserWithDept);
        }
      //  model.addAttribute("empInfos", multiSelectUserWithDepts1);

        map_json.put("recept",receptionDto1);

        map_json.put("emp",empDtos);
        map_json.put("viewOnly",viewOnly);
        map_json.put("allDept",findAllDept);
        map_json.put("deptList",deptList);

        map_json.put("listEmpDto",listEmpDto);
        map_json.put("emp_dept", multiSelectUserWithDepts);

        map_json.put("empAll",empAll);
        map_json.put("empInfos",multiSelectUserWithDepts1);

        String json = JsonHelper.toJson(map_json);
        AjaxResponse.write(response, json);
    }


    @RequestMapping("syy_yw_lc02_view_yzResolve")
    @ResponseBody
    public CommonResult syy_yw_lc02_view1(ApproveFormDto dto, HttpServletRequest request, Model model, HttpServletResponse response) {
        String leaderType = request.getParameter("leaderType");
//
//        DeaneryUtil.converToMap(request);
//
//        String processId=request.getParameter("processId");
//        String taskId=request.getParameter("taskId");
//        String orderId=request.getParameter("orderId");
//
//        receptionDto.setProcessId(processId);
//        receptionDto.setOrderId(orderId);
//        receptionDto.setTaskId(taskId);

//        ReceptionDto receptionDto1=receptionService.getById(orderId);
//
//
//        receptionService.dean(receptionDto,Integer.parseInt(lead_b));

        EmpSessionDto user = us.getCurrentUser(request);
        dto.setApproverId(user.getId());
        dto.setApproverName(user.getName());
        if (leaderType.equals("2")) {
            String ids = request.getParameter("leaders");
            List<Long> empids = new ArrayList<>();
            String[] strids = ids.split(",");
            if (strids.length > 0) {
                for (String s : strids)
                    empids.add(Long.parseLong(s));
            }
            return receptionService.leaderApprove(dto, 2, empids);
        } else
            return receptionService.doApprove(dto, null);
    }

    @RequestMapping("syy_yw_lc02_view_main")
    @ResponseBody
    public ModelAndView syy_yw_lc02_view2(ReceptionDto receptionDto, HttpServletRequest request, Model model, HttpServletResponse response) {


        /*String processId = request.getParameter("processId");
        String taskId = request.getParameter("taskId");
        String orderId = request.getParameter("orderId");*/

//        ReceptionDto receptionDto2=receptionService.getById(orderId);
//        model.addAttribute("receptionDto2",receptionDto2);
//        Long id=receptionDto2.getId();
//        model.addAttribute("id_w",id);
//
//        model.addAttribute("task_id",taskId);
//        model.addAttribute("order_id",orderId);
//
//        receptionDto.setProcessId(processId);
//        receptionDto.setOrderId(orderId);
//        receptionDto.setTaskId(taskId);
//
//        ReceptionDto receptionDto1=receptionService.getById(orderId);


        // receptionService.mainApply(receptionDto, 0);

        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);

        model.addAttribute("header", dto);
        model.addAttribute("viewOnly", viewOnly);

        ReceptionDto receptionDto2 = receptionService.getFormByFormNo(formNo);
        model.addAttribute("receptionDto2",receptionDto2);

        return view(formLayout, "reception_flow/syy_yw_lc02_view_main", model);
    }


    @RequestMapping("syy_yw_lc02_view_main_s")
    @ResponseBody
    public boolean syy_yw_lc02_view3(ApproveFormDto dto,ReceptionDto receptionDto, HttpServletRequest request, Model model, HttpServletResponse response) {


        String agreeType=request.getParameter("agreeType");
        if(agreeType.equals("0")){

        }
        if(agreeType.equals("1")){

        }
//
//        String agree=request.getParameter("agree");
//
//        String processId=request.getParameter("processId");
//        String taskId=request.getParameter("taskId");
//        String orderId=request.getParameter("orderId");
//
//        ReceptionDto receptionDto2=receptionService.getById(orderId);
//        model.addAttribute("receptionDto2",receptionDto2);
//        Long id=receptionDto2.getId();
//        model.addAttribute("id_w",id);
//
//        model.addAttribute("task_id",taskId);
//        model.addAttribute("order_id",orderId);
//
//        receptionDto.setProcessId(processId);
//        receptionDto.setOrderId(orderId);
//        receptionDto.setTaskId(taskId);
//
//        ReceptionDto receptionDto1=receptionService.getById(orderId);
//
//        if(Integer.parseInt(agree)==0){
//            String budget=request.getParameter("budget");
//
//            String suiteNum=request.getParameter("suiteNum");
//            String singleRoomNum=request.getParameter("singleRoomNum");
//            String standardRoomNum=request.getParameter("standardRoomNum");
//            String otherRoomNum=request.getParameter("otherRoomNum");
//
//            String banquetTime=request.getParameter("banquetTime");
//            String peopleNum=request.getParameter("peopleNum");
//            String standardCost=request.getParameter("standardCost");
//        }
//
//         receptionService.mainApply(receptionDto, Integer.parseInt(agree));

        EmpSessionDto user = us.getCurrentUser(request);
        dto.setApproverId(user.getId());
        dto.setApproverName(user.getName());

        return true;
    }

    @RequestMapping("syy_yw_lc02_view_branch_after")
    public ModelAndView syy_yw_lc02_view_branch_after(Model model, ReceptionDto receptionDto, HttpServletRequest request) {
//        String taskId=request.getParameter("taskId");
//        String orderId=request.getParameter("orderId");
//        String processId = request.getParameter("processId");
//
//        ReceptionDto receptionDto2=receptionService.getById(orderId);
//        model.addAttribute("receptionDto2",receptionDto2);
//
//         receptionDto=receptionService.getById(orderId);
//        model.addAttribute("taskId",taskId);
//        model.addAttribute("orderId",orderId);
//        model.addAttribute("processId",processId);
//        model.addAttribute("recepId",receptionDto.getId());
        return view(layout, "reception_flow/syy_yw_lc02_view_branch_after", model);
    }

    @RequestMapping("syy_yw_lc02_view_confirm_a")
    @ResponseBody
    public Long syy_yw_lc02_view_confirm(Model model, ReceptionDto receptionDto, HttpServletRequest request) {


//        String processId=request.getParameter("processId");
//        String taskId=request.getParameter("taskId");
//        String orderId=request.getParameter("orderId");
//
//        ReceptionDto receptionDto2=receptionService.getById(orderId);
//        model.addAttribute("receptionDto2",receptionDto2);
//        Long id=receptionDto2.getId();
//        model.addAttribute("id_w",id);
//
//        model.addAttribute("task_id",taskId);
//        model.addAttribute("order_id",orderId);
//
//        receptionDto.setProcessId(processId);
//        receptionDto.setOrderId(orderId);
//        receptionDto.setTaskId(taskId);
//
//        ReceptionDto receptionDto1=receptionService.getById(orderId);
//
//        receptionService.dean2(receptionDto);
        return 1L;
    }

    //
    @RequestMapping("syy_yw_lc02_view_confirm_b")
    @ResponseBody
    public Long syy_yw_lc02_view_confirmb(Model model, ReceptionDto receptionDto, HttpServletRequest request) {


//        String processId=request.getParameter("processId");
//        String taskId=request.getParameter("taskId");
//        String orderId=request.getParameter("orderId");
//
//        ReceptionDto receptionDto2=receptionService.getById(orderId);
//        model.addAttribute("receptionDto2",receptionDto2);
//        Long id=receptionDto2.getId();
//        model.addAttribute("id_w",id);
//
//        model.addAttribute("task_id",taskId);
//        model.addAttribute("order_id",orderId);
//
//        receptionDto.setProcessId(processId);
//        receptionDto.setOrderId(orderId);
//        receptionDto.setTaskId(taskId);
//
//        ReceptionDto receptionDto1=receptionService.getById(orderId);
//
//        receptionService.confirm(receptionDto);
        return 1L;
    }

    @RequestMapping("syy_yw_lc02_view_confirm")
    @ResponseBody
    public ModelAndView syy_yw_lc02_view_confirm_a(Model model, ReceptionDto receptionDto, HttpServletRequest request) {
//        String taskId=request.getParameter("taskId");
//        String orderId=request.getParameter("orderId");
//        String processId = request.getParameter("processId");
//
//        ReceptionDto receptionDto2=receptionService.getById(orderId);
//        model.addAttribute("receptionDto2",receptionDto2);
//
//        receptionDto=receptionService.getById(orderId);
//        model.addAttribute("taskId",taskId);
//        model.addAttribute("orderId",orderId);
//        model.addAttribute("processId",processId);
//        model.addAttribute("recepId",receptionDto.getId());

        return view(layout, "reception_flow/syy_yw_lc02_view_confirm_a", model);


    }

    @RequestMapping(value = "kg_lc02_Upload")
    public String uploadFile(HttpServletRequest request,HttpServletResponse response){
        EmpSessionDto empSessionDto = us.getCurrentUser(request);

        String formNo = request.getParameter("formNo");
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("costCostFile");

        CommonResult result=null;
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false,"请选择要上传的文件！");
                /*AjaxResponse.write(response, JsonHelper.toJson(result));
                return;*/
                break;
            } else {
                try {
                    String fileName = myfile.getOriginalFilename();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto=new FileInfoDto();
                    fileInfoDto.setFileName(fileName);
                    fileInfoDto.setIsPublic(false);
                    fileInfoDto.setDeptId(empSessionDto.getDeptId());
                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setInputStream(myfile.getInputStream());
                    String fileId=fileService.upload(fileInfoDto);
                    result= receptionService.uploadProjectFile1(Long.parseLong(formNo),fileId, fileName);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /*AjaxResponse.write(response, JsonHelper.toJson(result));*/
        return JsonHelper.toJson(result);
    }

    /**
     * 导出数据
     */
    @ResponseBody
    @RequestMapping("ajax_syy_yw_lc02_down")
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList();
        String formNo=request.getParameter("formNo_dc");
        ReceptionDto receptionDto=receptionService.downByOrderIdReception(formNo);
        EmpSessionDto user=us.getCurrentUser(request);
        ReceptionDtoTemp receptionDtoTemp=new ReceptionDtoTemp();
        receptionDtoTemp.setFormKindName(receptionDto.getFormKindName());
        receptionDtoTemp.setApplyerName(receptionDto.getApplyerName());
        receptionDtoTemp.setDeptName(receptionDto.getDeptName());
        receptionDtoTemp.setFileName(receptionDto.getFileName());
        receptionDtoTemp.setFileName1(receptionDto.getFileName1());
        receptionDtoTemp.setReceptionWork(receptionDto.getReceptionWork());
        receptionDtoTemp.setReceptionPerson(receptionDto.getReceptionPerson());
        //receptionDtoTemp.setPersonComp(receptionDto.getPersonComp());
        receptionDtoTemp.setActualCost(receptionDto.getActualCost());
        receptionDtoTemp.setActualBeginTime(receptionDto.getActualBeginTime());
        receptionDtoTemp.setActualEndTime(receptionDto.getActualEndTime());
        receptionDtoTemp.setReceptionProcess(receptionDto.getReceptionProcess());
        receptionDtoTemp.setMobile(receptionDto.getMobile());
        receptionDtoTemp.setForeignGuest(receptionDto.getForeignGuest());
        receptionDtoTemp.setReasons(receptionDto.getReasons());
        receptionDtoTemp.setLinkman(receptionDto.getLinkman());


        list.add(receptionDtoTemp);
        ExpotExcel<ReceptionDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"表单类型名", "申请人姓名","外宾单位","联系人","联系人电话","事由","部门","邀请函附件","消费清单","院办分配接待任务","实际接待人","接待费用","开始时间","结束时间","接待过程概述"};
        expotExcel.doExportExcel("公务接待", header, list, "yyyy-MM-dd", response);
    }
    /**
     * 根据formKind批量导出数据
     */
    @RequestMapping("ajax_syy_yw_lc02_allReception")
    public void ajax_syy_yw_lc02_allReception(HttpServletRequest request,HttpServletResponse response){
        String formKind=request.getParameter("formKind_dc");
        List list=new ArrayList();
        List<ReceptionDto> listDto=receptionService.receptionAll(formKind,4,true);
        EmpSessionDto user=us.getCurrentUser(request);
        for(int i=0;i<listDto.size();i++){
            ReceptionDto receptionDto=listDto.get(i);
            ReceptionDtoTemp receptionDtoTemp=new ReceptionDtoTemp();
            receptionDtoTemp.setFormKindName(receptionDto.getFormKindName());
            receptionDtoTemp.setApplyerName(receptionDto.getApplyerName());
            receptionDtoTemp.setDeptName(receptionDto.getDeptName());
            receptionDtoTemp.setFileName(receptionDto.getFileName());
            receptionDtoTemp.setFileName1(receptionDto.getFileName1());
            receptionDtoTemp.setReceptionWork(receptionDto.getReceptionWork());
            receptionDtoTemp.setReceptionPerson(receptionDto.getReceptionPerson());
            //receptionDtoTemp.setPersonComp(receptionDto.getPersonComp());
            receptionDtoTemp.setActualCost(receptionDto.getActualCost());
            receptionDtoTemp.setActualBeginTime(receptionDto.getActualBeginTime());
            receptionDtoTemp.setActualEndTime(receptionDto.getActualEndTime());
            receptionDtoTemp.setReceptionProcess(receptionDto.getReceptionProcess());
            receptionDtoTemp.setMobile(receptionDto.getMobile());
            receptionDtoTemp.setForeignGuest(receptionDto.getForeignGuest());
            receptionDtoTemp.setReasons(receptionDto.getReasons());
            receptionDtoTemp.setLinkman(receptionDto.getLinkman());
            list.add(receptionDtoTemp);
        }
        ExpotExcel<ReceptionDtoTemp> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"表单类型名", "申请人姓名","外宾单位","联系人","联系人电话","事由","部门","邀请函附件","消费清单","院办分配接待任务","实际接待人","接待费用","开始时间","结束时间","接待过程概述"};
        expotExcel.doExportExcel("公务接待", header, list, "yyyy-MM-dd", response);
    }


    @RequestMapping(value = "ajax_xiaoxie")
    public void queryProjectInfo( HttpServletRequest request, HttpServletResponse response) {
        String xiaoxie = request.getParameter("xiaoxie");
        String daxie = ToolBymoney.change(Double.parseDouble(xiaoxie));
        String json = JsonHelper.toJson(daxie);
        AjaxResponse.write(response, json);
    }

}
