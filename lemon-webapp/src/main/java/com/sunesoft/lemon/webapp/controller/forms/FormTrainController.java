package com.sunesoft.lemon.webapp.controller.forms;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.NoticeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpDeptsCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.*;
import com.sunesoft.lemon.syms.hrForms.application.criteria.TrainEmpCriteria;
import com.sunesoft.lemon.syms.hrForms.application.formTrain.FormTrainService;
import com.sunesoft.lemon.syms.hrForms.application.formTrainChoose.FormTrainChooseEmpService;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.InnerFormAppPointDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormKindManagerService;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.FormTrainModel;
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
 * *培训申请单 人事提交 生成一个人事主表单和多个部门的子表单 主表单选择的是所有部门的所有员工，子表单选择的对应部门的员工
 * 子表单发到各个部门的主管处，进行选人 然后通过领导一级一级进行审核批准 人事主表单进行人员汇总
 * Created by liulin on 2016/7/21.
 */
@Controller
public class  FormTrainController extends Layout {

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    FormTrainService formTrainService;

    @Autowired
    FormTrainChooseEmpService formTrainChooseEmpService;

    @Autowired
    FormHeaderService headerService;
    @Autowired
    FormListService formListService;
    @Autowired
    UserSession us;

    @Autowired
    FileService fileService;

    @Autowired
    NoticeService noticeService;


    @RequestMapping(value = "sra_t_train_emp")
    public ModelAndView sra_t_train(Model model,HttpServletRequest request){
        return view(layout,"uAuth/queryTrain",model);
    }

    @RequestMapping(value = "ajax_query_train_emp")
    public void ajax_query_train_emp(HttpServletRequest request, HttpServletResponse response) {

        TrainEmpCriteria criteria=new TrainEmpCriteria();

        String name=URI.deURI(request.getParameter("name"));
        String beginDate=request.getParameter("beginDate");
        String endDate=request.getParameter("endDate");
        String trainName=URI.deURI(request.getParameter("trainName"));

        if(!StringUtils.isNullOrWhiteSpace(name)){
            criteria.setName(name);
        }
        if(!StringUtils.isNullOrWhiteSpace(beginDate)){
            criteria.setBeginDate(DateHelper.parse(beginDate+" 00:00:00"));
        }
        if(!StringUtils.isNullOrWhiteSpace(endDate)){
            criteria.setEndDate(DateHelper.parse(endDate + " 23:59:59"));
        }
        if(!StringUtils.isNullOrWhiteSpace(trainName)){
            criteria.setTrainName(trainName);
        }
        //  CommonResult result=new CommonResult(true,"",9L);
        List<FormTrainEmpDto> list=formTrainService.getAllTrainEmp(criteria);
        PagedResult PagedResult = new PagedResult(list,1,1,1);
        String json = JsonHelper.toJson(PagedResult);
        System.out.println(json);
        AjaxResponse.write(response, json);
    }


    /**
     * 培训申请单 首页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "syy_rs_lc01_a")
    public ModelAndView apply(Model model, HttpServletRequest request) {
        FormListDto dto =formListService.getFormListInfo("SYY_RS_LC01");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());

        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        List<DeptmentDto> list = deptmentService.getAllDept();
        model.addAttribute("dept", list);

        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)) {
            FormTrainDto formLeave = formTrainService.getFormByFormNo((Long.parseLong(formNo)));
            model.addAttribute("beans", formLeave);
        }
        return view(applyLayout, "forms/syy_rs_lc01_a", model);
    }


    /**
     * 表单提交 生成一个主表单和多个子表单
     * @param request
     * @return
     */
    @RequestMapping(value = "ajax_add_update_train")
    @ResponseBody
    public CommonResult addOrupdateTrain(FormTrainDto formTrainDto ,HttpServletRequest request,HttpServletResponse response) {


            List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("fileName1");


        CommonResult result=null;
        for (MultipartFile myfile : myfiles) {
//            if (myfile.isEmpty()) {
//                result = new CommonResult(false,"请选择要上传的文件！");
//                AjaxResponse.write(response, JsonHelper.toJson(result));
//                return result;
//            } else {
                try {
                    String fileName = myfile.getOriginalFilename();
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    FileInfoDto fileInfoDto=new FileInfoDto();

                    fileInfoDto.setFileName(fileName);

                    fileInfoDto.setExtensions(extension);
                    fileInfoDto.setInputStream(myfile.getInputStream());

                    String id=fileService.upload(fileInfoDto);

                    if(!StringUtils.isNullOrWhiteSpace(id)) {
                        formTrainDto.setFileId(id);
                        formTrainDto.setFileName(fileName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//        }




        String deptIds = request.getParameter("trainDeptId");//所选部门 id
        String deptName = request.getParameter("trainDeptName");//所选部门 name

        String trainName = request.getParameter("trainName");
        String trainContent = request.getParameter("trainContent");
        String trainPlace = request.getParameter("trainPlace");
        String formKind = request.getParameter("formKind");
        String formKindName = request.getParameter("formKindName");

        String studyTime=request.getParameter("studyTime");
        String mainCompany=request.getParameter("mainCompany");
        String didCompany=request.getParameter("didCompany");

        String trainCategory=request.getParameter("trainCategory");
        String plan=request.getParameter("plan");

        EmpSessionDto empSessionDto = us.getCurrentUser(request);//获取当前用户
        CommonResult commonResult;

        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            //update
            String beginTime = request.getParameter("strFromTime");
            String endTime = request.getParameter("strToTime");

            formTrainDto.setDeptId(empSessionDto.getDeptId());
            formTrainDto.setDeptName(empSessionDto.getDeptName());
            commonResult = formTrainService.updateByDto(formTrainDto);
        } else {
            //add
            String beginTime = request.getParameter("strFromTime");
            String endTime = request.getParameter("strToTime");

            //下面四个 是表单头部信息 必备信息
            formTrainDto.setApplyer(empSessionDto.getId());
            formTrainDto.setApplyerName(empSessionDto.getName());
            formTrainDto.setDeptId(empSessionDto.getDeptId());
            formTrainDto.setDeptName(empSessionDto.getDeptName());

            formTrainDto.setTrainBeginDate(DateHelper.parse(beginTime,"yyyy-MM-dd"));
            formTrainDto.setTrainEndDate(DateHelper.parse(endTime,"yyyy-MM-dd"));
            formTrainDto.setTrainDepts(deptIds);
            formTrainDto.setTrainContent(trainContent);
            formTrainDto.setTrainPlace(trainPlace);
            formTrainDto.setTrainName(trainName);
            formTrainDto.setFormKind(formKind);
            formTrainDto.setFormDeptName(deptName);
            formTrainDto.setFormKindName(formKindName);

            formTrainDto.setStudyTime(studyTime);
            formTrainDto.setMainCompany(mainCompany);
            formTrainDto.setDidCompany(didCompany);
            formTrainDto.setPlan(plan);
            formTrainDto.setTrainCategory(trainCategory);

            String[] dept_ids = deptIds.split(",");
           // formTrainDto.setBlongDeptId(Long.parseLong(dept_ids[0]));

            String[] deptNames = deptName.split(",");
            List<EmpEasyDto> list = new ArrayList<>();
            for(int i=0;i<deptNames.length;i++){
                EmpEasyDto empEasyDto = new EmpEasyDto();
                empEasyDto.setTrainDeptName(deptNames[i]);
                list.add(empEasyDto);
            }
            formTrainDto.setEmpLists(list);
            commonResult = formTrainService.addByDto(formTrainDto);
        }

        if(commonResult.getIsSuccess()) {
            result = formTrainService.submitForm(commonResult.getId(), formTrainDto.getFormKind());

            Long parentFormNo = commonResult.getId();

            String s = formTrainDto.getTrainDepts();
            String[] arr = s.split(",");
            String[] depts = deptName.split(",");
            FormTrainChooseEmpDto formTrainChooseEmpDto;
            List<EmpEasyDto> list = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {

                formTrainChooseEmpDto = new FormTrainChooseEmpDto();

                String beginTime = request.getParameter("strFromTime");
                String endTime = request.getParameter("strToTime");

                //下面四个 是表单头部信息 必备信息
                formTrainChooseEmpDto.setApplyer(empSessionDto.getId());
                formTrainChooseEmpDto.setApplyerName(empSessionDto.getName());
                formTrainChooseEmpDto.setDeptId(empSessionDto.getDeptId());
                formTrainChooseEmpDto.setDeptName(empSessionDto.getDeptName());

                formTrainChooseEmpDto.setTrainBeginDate(DateHelper.parse(beginTime,"yyyy-MM-dd"));
                formTrainChooseEmpDto.setTrainEndDate(DateHelper.parse(endTime,"yyyy-MM-dd"));
                formTrainChooseEmpDto.setTrainName(formTrainDto.getTrainName());
                formTrainChooseEmpDto.setTrainContent(formTrainDto.getTrainContent());
                formTrainChooseEmpDto.setTrainPlace(formTrainDto.getTrainPlace());

                formTrainChooseEmpDto.setBlongDeptId(Long.parseLong(arr[i]));
                formTrainChooseEmpDto.setBlongDeptName(depts[i]);

                formTrainChooseEmpDto.setFileId(formTrainDto.getFileId());
                formTrainChooseEmpDto.setFileName(formTrainDto.getFileName());

                formTrainChooseEmpDto.setStudyTime(studyTime);
                formTrainChooseEmpDto.setMainCompany(mainCompany);
                formTrainChooseEmpDto.setDidCompany(didCompany);
                formTrainChooseEmpDto.setPlan(plan);
                formTrainChooseEmpDto.setTrainCategory(trainCategory);

                formTrainChooseEmpDto.setFormKind("SYY_RS_LC01_01");
                formTrainChooseEmpDto.setFormKindName(formTrainDto.getFormKindName());

                formTrainChooseEmpDto.setParentFormNo(parentFormNo);
                CommonResult commonResult1 = formTrainChooseEmpService.addByDto((formTrainChooseEmpDto));
                CommonResult result1 = formTrainChooseEmpService.submitForm(commonResult1.getId(), formTrainChooseEmpDto.getFormKind());
            }
        }
        return commonResult;
    }

    /**
     * 主表单 页面数据展示
     * @param request
     * @return
     */
    @RequestMapping("ajax_syy_rs_lc01_data")
    @ResponseBody
    public FormTrainDto applyView(HttpServletRequest request) {
        String formKind = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormTrainDto formTrainDto = formTrainService.getFormByFormNo(Long.parseLong(formKind));
        formTrainDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        if (!StringUtils.isNullOrWhiteSpace(formTrainDto.getPublishTrainNewsName())) {
            List<NoticeDto> noticeDtos = noticeService.getByName(formTrainDto.getPublishTrainNewsName());
            formTrainDto.setPublishTrainNewsId((noticeDtos.get(0).getId()).toString());
        }

        return formTrainDto;
    }


    /**
     * 子表单 页面数据展示
     * @param request
     * @return
     */
    @RequestMapping("ajax_syy_rs_lc01_01_data")
    @ResponseBody
    public FormTrainChooseEmpDto applyView_01(HttpServletRequest request) {
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormTrainChooseEmpDto formTrainChooseEmpDto = formTrainChooseEmpService.getFormByFormNo(Long.parseLong(formNo));
        formTrainChooseEmpDto.setIsViewOnly(Boolean.valueOf(viewOnly));

        if (!StringUtils.isNullOrWhiteSpace(formTrainChooseEmpDto.getPublishTrainNewsName())) {
            List<NoticeDto> noticeDtos = noticeService.getByName(formTrainChooseEmpDto.getPublishTrainNewsName());
            formTrainChooseEmpDto.setPublishTrainNewsId(String.valueOf(noticeDtos.get(0).getId()));
        }

        return formTrainChooseEmpDto;
    }

    @RequestMapping(value = "dowload_train_emp")
    public void dowload_train_emp(HttpServletRequest request,HttpServletResponse response){
        String formNo=request.getParameter("formNo");
        FormTrainDto evectionDto=formTrainService.getFormByFormNo(Long.parseLong(formNo));
        List<FormTrainDownloadDto> downloadDtos=formTrainService.getTrainDownloadDto(evectionDto);
        ExpotExcel<FormTrainDownloadDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"所在部门及单位","员工号","姓名","培训开始时间","培训结束时间","培训班名称","培训内容","培训天数","学时","主办单位","承办单位","培训分类","培训城市","计划内或外"};
        expotExcel.doExportExcel("外派培训统计表", header, downloadDtos, "yyyy-MM-dd", response);
    }

    @RequestMapping(value = "dowload_train_count_emp")
    public void dowload_train_count_emp(HttpServletRequest request,HttpServletResponse response){
        TrainEmpCriteria criteria=new TrainEmpCriteria();

        String name=URI.deURI(request.getParameter("name"));
        String beginDate=request.getParameter("beginDate");
        String endDate=request.getParameter("endDate");
        String trainName=URI.deURI(request.getParameter("trainName"));

        if(!StringUtils.isNullOrWhiteSpace(name)){
            criteria.setName(name);
        }
        if(!StringUtils.isNullOrWhiteSpace(beginDate)){
            criteria.setBeginDate(DateHelper.parse(beginDate+" 00:00:00"));
        }
        if(!StringUtils.isNullOrWhiteSpace(endDate)){
            criteria.setEndDate(DateHelper.parse(endDate + " 23:59:59"));
        }
        if(!StringUtils.isNullOrWhiteSpace(trainName)){
            criteria.setTrainName(trainName);
        }
        List<FormTrainDownloadDto> downloadDtos=formTrainService.getTrainDownloadDto(criteria);
        ExpotExcel<FormTrainDownloadDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"所在部门及单位","员工号","姓名","培训开始时间","培训结束时间","培训班名称","培训内容","培训天数","学时","主办单位","承办单位","培训分类","培训城市","计划内或外"};
        expotExcel.doExportExcel("外派培训统计表", header, downloadDtos, "yyyy-MM-dd", response);
    }


    @RequestMapping(value = "form_train_lc01")
    public ModelAndView getForm(Model model,HttpServletRequest request){
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        String viewOnly = request.getParameter("viewOnly");
        FormHeaderDto dto = headerService.getHeaderByFormNo(formNo);

        model.addAttribute("header",dto);
        model.addAttribute("viewOnly",viewOnly);
        if(viewOnly.equals("true")){
            return view("common/formbody",  "forms/syy_rs_lc01_v", model);
        }
        return view(formLayout,  "forms/syy_rs_lc01_v", model);
    }



    @RequestMapping(value = "form_train_approve")
    public void approves(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){

        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());
        Map<String,Object> param = new HashMap<>();
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            param.put(paraName,request.getParameter(paraName));
        }

        Map<String,Object> param1 = new HashMap<>();
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()){
            String name = (String)enu.nextElement();
            param1.put(name,request.getParameter(name));
        }

//        List<Long> empids = new ArrayList<>();
//        empids.add(2065L);
//        FormHeaderDto formDto = headerService.getHeaderByFormNo(dto.getFormNo());
//        List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();//获取到签核节点
//
//
//        formTrainService.resetNextApprover(dto.getFormNo(),empids);//将下一级审核人设置为指定的审核人
//
//        formTrainService.setPointAsNext(dto.getFormNo(),dtos.get(2).getId());//将下一级审核设置为指定的节点。
//
//        formTrainService.setPointAsNext(dto.getFormNo(),dtos.get(2).getId(),empids);// 将下一级审核设置为指定的节点，并指定审核人。
//
         CommonResult result= formTrainService.doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    /**
     * 子表单 审核页面 获取emp数据
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("_get_emp_by_dept")
    public ModelAndView getEmpList(HttpServletRequest request, Model model) {

        String formNo = request.getParameter("formNo");
        FormTrainChooseEmpDto formTrainChooseEmpDto = formTrainChooseEmpService.getFormByFormNo(Long.parseLong(formNo));
//        model.addAttribute("empDto", formTrainChooseEmpDto);
        model.addAttribute("fKind", formTrainChooseEmpDto.getFormKind());
        model.addAttribute("deptId",formTrainChooseEmpDto.getDeptId());


        return view("forms/syy_rs_lc01_01_addEmps", model);
    }

    /**
     * 子表单 新增部门员工 弹窗 页面数据显示和查询
     * @param response
     * @param request
     * @param empCriteria
     */
    @RequestMapping(value = "ajax_query_addEmps")
    public void queryAddEmps(HttpServletResponse response,HttpServletRequest request,EmpCriteria empCriteria){
        String name = URI.deURI(request.getParameter("empName"));
        if (!StringUtils.isNullOrWhiteSpace(name) && !("undefined").equals(name)) {

            empCriteria.setName(name);
        }

        //通过部门id获取部门员工
        String deptId = request.getParameter("deptId");
        Long emp_deptId=null;
        if (!StringUtils.isNullOrWhiteSpace(deptId)) {
            emp_deptId = Long.parseLong(deptId);
        }

        empCriteria.setPageSize(10);
        PagedResult<EmpDto> pagedResult=employeeService.findEmpsPaged(empCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }


    /**
     *  主表单 新增部门员工 弹窗 页面数据显示和查询
     * @param response
     * @param request
     * @param empDeptsCriteria
     */
    @RequestMapping(value = "ajax_query_addManyEmps")
    public void queryAddManyEmps(HttpServletResponse response,HttpServletRequest request,EmpDeptsCriteria empDeptsCriteria){
        String name = URI.deURI(request.getParameter("empName"));
        if (!StringUtils.isNullOrWhiteSpace(name) && !("undefined").equals(name)) {

            empDeptsCriteria.setName(name);
        }
        String deptIds = request.getParameter("deptIds");
        empDeptsCriteria.setDeptId(deptIds);
        PagedResult<EmpDto> pagedResult = employeeService.findEmpsByDeptPaged(empDeptsCriteria);


//        empCriteria.setPageSize(10);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response,json);
    }

    /**
     * 人事 主表单 获取emps数据
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "get_emps_by_depts")
    public ModelAndView getEmpsbyTrain(HttpServletRequest request, Model model) {

        String formNo = request.getParameter("formNo");
        FormTrainDto formTrainDto = formTrainService.getFormByFormNo(Long.parseLong(formNo));
        model.addAttribute("empDto", formTrainDto);
        model.addAttribute("fKind", formTrainDto.getFormKind());

        //通过部门id获取部门员工
        String deptId = request.getParameter("deptId");
        //split  取 第0 个
        String[] deptIds = deptId.split(",");
        List emps = new ArrayList();
        for (String d_id:deptIds) {
            emps.add(employeeService.findEmpsByDept(Long.parseLong(d_id)));
        }
        model.addAttribute("emps", emps);

        return view("forms/syy_rs_lc01_addEmps", model);
    }

    /**
     * 主表单和子表单 新增员工提交
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "ajax_add_update_emps", method = RequestMethod.POST)
    @ResponseBody//此注解：返回ajax
    public CommonResult addOrUpdateEmps(HttpServletRequest request, HttpServletResponse response) {
        String formKind = request.getParameter("formKind");
        CommonResult commonResult = null;

        String formNo = request.getParameter("formNo");
        String empIds = request.getParameter("emps");
        Long empId = Long.parseLong(empIds);
        String[] arr = empIds.split(",");
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(Long.parseLong(arr[i]));
        }

        //对主、子表单进行判断
        if (formKind.equals("SYY_RS_LC01_01")) {
            //判断子表单员工是否重复
            FormTrainChooseEmpDto formTrainChooseEmpDto = formTrainChooseEmpService.getFormByFormNo(Long.parseLong(formNo));
            List<EmpEasyDto> list1 = formTrainChooseEmpDto.getEmpLists();
            if (list1 != null) {
                for (EmpEasyDto empEasyDto : list1) {
                    if (empId.equals(empEasyDto.getId())) {
                        return new CommonResult(false);
                    }
                }
            }
            commonResult = formTrainChooseEmpService.addEmpById(Long.parseLong(formNo), list);
        } else {
            //判断主表单员工是否重复
            FormTrainDto formTrainDto = formTrainService.getFormByFormNo(Long.parseLong(formNo));
            List<EmpEasyDto> list1 = formTrainDto.getEmpLists();
            if (list1 != null) {
                for (EmpEasyDto empEasyDto : list1) {
                    if (empId.equals(empEasyDto.getId())) {
                        return new CommonResult(false);
                    }
                }
            }
            commonResult = formTrainService.addEmpId(Long.parseLong(formNo), list);
        }

        return commonResult;
    }


    /**
     * emp 页面删除
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteEmp")
    @ResponseBody
    public CommonResult deleteEmp(HttpServletRequest request) {
        String formKind = request.getParameter("formKind");
        String formNo = request.getParameter("formNo");
        String id = request.getParameter("id");
        CommonResult commonResult = null;

        //对主、子表单进行判断
        if (formKind.equals("SYY_RS_LC01_01")) {
            commonResult = formTrainChooseEmpService.deleteEmpById(Long.parseLong(formNo), Long.parseLong(id));
        } else {
            commonResult = formTrainService.deleteEmpById(Long.parseLong(formNo), Long.parseLong(id));
        }
        return commonResult;
    }



}
