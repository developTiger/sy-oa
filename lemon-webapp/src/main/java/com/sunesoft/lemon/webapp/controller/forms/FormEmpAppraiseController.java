package com.sunesoft.lemon.webapp.controller.forms;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.BeanUtils;
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
import com.sunesoft.lemon.syms.hrForms.application.Dtos.*;
import com.sunesoft.lemon.syms.hrForms.application.factory.UploadDeptAppraiseDetailFactory;
import com.sunesoft.lemon.syms.hrForms.application.factory.UploadEmpAppraiseDetailFactory;
import com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise.FormAppraiseDetailsService;
import com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise.FormAppraiseService;
import com.sunesoft.lemon.syms.hrForms.application.formEmpSubAppraise.FormEmpSubAppraiseDetailsService;
import com.sunesoft.lemon.syms.hrForms.application.formEmpSubAppraise.FormEmpSubAppraiseService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.EmpAppDetailAndPages;
import com.sunesoft.lemon.webapp.model.EmpAppraiseDetailDtoModel;
import com.sunesoft.lemon.webapp.model.FromEmpAppraiseDetail;
import com.sunesoft.lemon.webapp.model.FromSubEmpAppraiseDetail;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/7/21.
 */
@Controller
public class FormEmpAppraiseController extends Layout {

    @Autowired
    UserSession us;

    @Autowired
    FormAppraiseService formAppraiseService;

    @Autowired
    FormAppraiseDetailsService formAppraiseDetailsService;

    @Autowired
    FormEmpSubAppraiseService formEmpSubAppraiseService;

    @Autowired
    FormEmpSubAppraiseDetailsService formEmpSubAppraiseDetailsService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    FormListService formListService;


    @RequestMapping(value = "sra_s_selfevaluation")
    public ModelAndView sra_s_selfevaluation(Model model, HttpServletRequest request) {

        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        String id=request.getParameter("id");
       /*  String formNo=request.getParameter("formNo");
        String detailId=request.getParameter("detailId");
        String detailNo=request.getParameter("detailNo");

        if(StringUtils.isNullOrWhiteSpace(detailId)){
            EmpAppraiseDetailDto empAppraiseDetailDto=formAppraiseDetailsService.getAppraiseDetails(Long.parseLong(detailNo)+1);
            model.addAttribute("empAppraiseDetailDto",empAppraiseDetailDto);
        }
        model.addAttribute("formNo",formNo);
        model.addAttribute("formId",id);*/
        EmpAppraiseDetailDto empAppraiseDetailDto=formAppraiseDetailsService.getByid(Long.parseLong(id));
        model.addAttribute("empAppraiseDetailDto",empAppraiseDetailDto);
        model.addAttribute("empSessionDto", empSessionDto);

        return view(layout, "empappraise/self_evaluation", model);
    }

    @RequestMapping(value = "add_emp_self_evaluation")
    @ResponseBody
    public CommonResult add_emp_self_evaluation(Model model, HttpServletRequest request) {

        String id = request.getParameter("detailid");
        EmpAppraiseDetailDto empAppraiseDetailDto  =formAppraiseDetailsService.getByid(Long.parseLong(id));
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        empAppraiseDetailDto.setEmpId(empSessionDto.getId());
//        empAppraiseDetailDto.setEmpSelfLevel(URI.deURI(request.getParameter("empSelfLevel")));
//        empAppraiseDetailDto.setEmpSelfScores(Float.valueOf(request.getParameter("empSelfScores")));
        empAppraiseDetailDto.setEmpSelfGrade(URI.deURI(request.getParameter("empSelfGrade")));
//        empAppraiseDetailDto.setEmpSelfRemark(URI.deURI(request.getParameter("empSelfRemark")));

       /* List<EmpSubAppraiseDetailDto> empSubAppraiseDetailDtos=new ArrayList<>();
        empSubAppraiseDetailDtos.add(empAppraiseDetailDto);


        FormEmpSubAppraiseDto formEmpSubAppraiseDto=new FormEmpSubAppraiseDto();
        formEmpSubAppraiseDto.setEmpSubAppraiseDetailDtos(empSubAppraiseDetailDtos);*/

        /*
        empAppraiseDetailDto.setFormNo(Long.parseLong(request.getParameter("formNo")));
        if(StringUtils.isNullOrWhiteSpace(request.getParameter("detailId")))
            empAppraiseDetailDto.setId(Long.parseLong(request.getParameter("detailId")));
        */
        empAppraiseDetailDto.setAppStatus(1);
        CommonResult commonResult = formAppraiseDetailsService.addOrUpdateFormAppraiseDetails(empAppraiseDetailDto);
        return commonResult;
    }


    @RequestMapping(value = "syy_rs_lc03_a")
    public ModelAndView syy_rs_lc03_a(Model model, HttpServletRequest request) {

        FormListDto dto =formListService.getFormListInfo("SYY_RS_LC03");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        EmpSessionDto empDto = us.getCurrentUser(request);

        String formNo = request.getParameter("formNo");
        if (!"".equals(formNo) && formNo != null) {
            FormEmpAppraiseDto formEmpAppraiseDto = formAppraiseService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("beans", formEmpAppraiseDto);
        }
        model.addAttribute("userInfo", empDto);
        return view(applyLayout, "forms/syy_rs_lc03_a", model);
    }

    @RequestMapping(value = "ajax_add_update_appraise", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult ajax_add_update_appraise1(FormEmpAppraiseDto formEmpAppraiseDto, HttpServletRequest request) {

        String startDate = request.getParameter("strStartDate");
        String endDate = request.getParameter("strEndDate");

        if (!StringUtils.isNullOrWhiteSpace(startDate) && !StringUtils.isNullOrWhiteSpace(endDate)) {
            try {
                formEmpAppraiseDto.setBeginDate(DateHelper.parse(startDate,"yyyy-MM-dd"));
                formEmpAppraiseDto.setEndDate(DateHelper.parse(endDate,"yyyy-MM-dd"));
            } catch (Exception ex) {
                return new CommonResult(false, "请选择日期！");
            }
        }
        CommonResult result = formAppraiseService.addByDto(formEmpAppraiseDto);
        if(result.getIsSuccess()) {
            CommonResult commonResult = formAppraiseService.submitForm(result.getId(), formEmpAppraiseDto.getFormKind());
            //增加子表单
            //Date date3 = new Date();
            List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
            //增加子表单
            Long parentFormNo = result.getId();
            Date date4 = new Date();
            for (DeptmentDto dept : deptmentDtos) {
//                if(dept.getId() != 2055) {
                if(!dept.getDeptNo().equals("01YLD")){        //TODO 部门编码 hotfix
                    Date date5 = new Date();
                    FormEmpSubAppraiseDto formEmpSubAppraiseDto = new FormEmpSubAppraiseDto();
                    BeanUtils.copyProperties(formEmpAppraiseDto, formEmpSubAppraiseDto);
                    formEmpSubAppraiseDto.setParentFormNo(parentFormNo);
                    formEmpSubAppraiseDto.setBlongDeptId(dept.getId());
                    formEmpSubAppraiseDto.setBlongDeptName(dept.getDeptName());
                    formEmpSubAppraiseDto.setFormKind("SYY_RS_LC03_01");
                    formEmpSubAppraiseDto.setFormKindName("员工考核部门评分");
                    formEmpSubAppraiseDto.setHasApplyView(false);
                    CommonResult res = formEmpSubAppraiseService.addByDto(formEmpSubAppraiseDto);
                    //Date date42 = new Date();
                    result = formEmpSubAppraiseService.submitForm(res.getId(), formEmpSubAppraiseDto.getFormKind());
                    //System.out.println("submitForm used:"+ (new Date().getTime() - date42.getTime()));
                    System.out.println("dept 1 times used:"+ (new Date().getTime() - date5.getTime()));
                }
            }
            System.out.println("dept for all used:"+ (new Date().getTime() - date4.getTime()));

       /* EmpSessionDto empSessionDto=us.getCurrentUser(request);
        FormEmpSubAppraiseDto formEmpSubAppraiseDto=new FormEmpSubAppraiseDto();
        BeanUtils.copyProperties(formEmpAppraiseDto, formEmpSubAppraiseDto);
        formEmpSubAppraiseDto.setParentFormNo(result.getId());
        formEmpSubAppraiseDto.setBlongDeptId(empSessionDto.getDeptId());
        formEmpSubAppraiseDto.setBlongDeptName(empSessionDto.getDeptName());
        formEmpSubAppraiseDto.setFormKind("SYY_RS_LC03_01");
        formEmpSubAppraiseDto.setFormKindName("员工考核部门评分");
        formEmpSubAppraiseDto.setHasApplyView(false);
        CommonResult res = formEmpSubAppraiseService.addByDto(formEmpSubAppraiseDto);
        commonResult = formEmpSubAppraiseService.submitForm(res.getId(), formEmpSubAppraiseDto.getFormKind());*/
        }
        return result;
    }


    @RequestMapping("ajax_syy_rs_lc03_data")
    @ResponseBody
    public EmpAppDetailAndPages ajax_syy_rs_lc03_data_tt(Model model, HttpServletRequest request,EmpCriteria empCriteria) {
        String formNo = request.getParameter("formNo");
        String formId = request.getParameter("id");
        String viewOnly = request.getParameter("viewOnly");
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        FormEmpAppraiseDto formEmpAppraiseDto = formAppraiseService.getFormByFormNo(Long.parseLong(formNo));

        PagedResult<EmpAppraiseDetailDto> pagedResult=formAppraiseService.getPagesByAllDetails(Long.parseLong(formNo),empCriteria);

        EmpAppDetailAndPages pages = new EmpAppDetailAndPages();

        pages.setFormEmpAppraiseDto(formEmpAppraiseDto);
        pages.setIsViewOnly(Boolean.valueOf(viewOnly));

        pages.setPagedResult(pagedResult);

        return pages;
    }

    @RequestMapping("ajax_emp_all_pages")
    @ResponseBody
    public PagedResult<EmpAppraiseDetailDto> ajax_syy_rs_lc03_data_pages(Model model, HttpServletRequest request,EmpCriteria empCriteria) {
        String formNo = request.getParameter("formNo");
        //  FormEmpAppraiseDto formEmpAppraiseDto = formAppraiseService.getFormByFormNo(Long.parseLong(formNo));

        PagedResult<EmpAppraiseDetailDto> pagedResult=formAppraiseService.getPagesByAllDetails(Long.parseLong(formNo),empCriteria);

        for(EmpAppraiseDetailDto dto : pagedResult.getItems()){

        }

        return pagedResult;
    }


    @RequestMapping("ajax_get_appraiseDate")
    @ResponseBody
    public  ListResult<EmpAppraiseDetailDto> ajax_get_appraiseDate(Model model, HttpServletRequest request) {
        String status = request.getParameter("appStatus");
        EmpSessionDto empSessionDto = us.getCurrentUser(request);
        List<EmpAppraiseDetailDto> list = formAppraiseDetailsService.getByEmpAndStatus(us.getCurrentUser(request).getId(), Integer.parseInt(status));
        return new ListResult<EmpAppraiseDetailDto>(list);
    }


    @RequestMapping("ajax_syy_rs_lc03_01_data")
    @ResponseBody
    public FormEmpSubAppraiseDto ajax_syy_rs_lc03_01_data(Model model, HttpServletRequest request) {

        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormEmpSubAppraiseDto formEmpSubAppraiseDto = formEmpSubAppraiseService.getFormByFormNo(Long.parseLong(formNo));
        formEmpSubAppraiseDto.setIsViewOnly(Boolean.valueOf(viewOnly));

        return formEmpSubAppraiseDto;
    }

    @RequestMapping("ajax_update_confirm_dept_appraise")
    @ResponseBody
    public CommonResult update_confirm_dept_appraise( Model model, HttpServletRequest request) {

//        String deptLevel=URI.deURI(request.getParameter("deptLevel"));
        String deptScores=URI.deURI(request.getParameter("deptScores"));
        String deptGrade=URI.deURI(request.getParameter("deptGrade"));

        String groupScores = request.getParameter("groupScores");
        String groupGrade = request.getParameter("groupGrade");

        String detail_id = request.getParameter("detail_id");

//
        EmpAppraiseDetailDto empSubAppraiseDetailDto=new EmpAppraiseDetailDto();
//        empSubAppraiseDetailDto.setDeptLevel(deptLevel);
        if (!StringUtils.isNullOrWhiteSpace(deptGrade))
            empSubAppraiseDetailDto.setDeptGrade(deptGrade);
        if (!StringUtils.isNullOrWhiteSpace(deptScores))
            empSubAppraiseDetailDto.setDeptScores(Float.parseFloat(deptScores));

        if(!StringUtils.isNullOrWhiteSpace(groupScores))
            empSubAppraiseDetailDto.setGroupScores(Float.valueOf(groupScores));
        if (!StringUtils.isNullOrWhiteSpace(groupGrade))
            empSubAppraiseDetailDto.setGroupGrade(groupGrade);

//        empSubAppraiseDetailDto.setDeptRemark(deptRemark);
        empSubAppraiseDetailDto.setId(Long.parseLong(detail_id));
        CommonResult commonResult = formAppraiseDetailsService.addOrUpdateFormAppraiseDetails(empSubAppraiseDetailDto);

        return commonResult;
    }

    @RequestMapping(value = "ajax_update_confirm_allDdepts_appraise")
    @ResponseBody
    public CommonResult update_confirm_allDepts_appraise(Model model,HttpServletRequest request){
        EmpAppraiseDetailDto empAppraiseDetailDto = new EmpAppraiseDetailDto();

//        String deptScores = request.getParameter("deptScores");
//        if (!StringUtils.isNullOrWhiteSpace(deptScores))
//            empAppraiseDetailDto.setDeptScores(Float.parseFloat(deptScores));
//        String deptGrade = request.getParameter("deptGrade");
//        if (!StringUtils.isNullOrWhiteSpace(deptGrade))
//            empAppraiseDetailDto.setDeptGrade(deptGrade);
        String detail_id = request.getParameter("detail_id");
        empAppraiseDetailDto.setId(Long.parseLong(detail_id));

        String chargeLeaderScores=URI.deURI(request.getParameter("chargeLeaderScores"));
        String chargeLeaderLevel=URI.deURI(request.getParameter("chargeLeaderLevel"));
//        String groupScores=URI.deURI(request.getParameter("groupScores"));
//        String groupGrade=URI.deURI(request.getParameter("groupGrade"));

        empAppraiseDetailDto.setChargeLeaderScores(chargeLeaderScores);
        empAppraiseDetailDto.setChargeLeaderLevel(chargeLeaderLevel);
//        empAppraiseDetailDto.setGroupScores(Float.valueOf(groupScores));
//        empAppraiseDetailDto.setGroupGrade(groupGrade);

        CommonResult commonResult = formAppraiseDetailsService.addOrUpdateFormAppraiseDetails(empAppraiseDetailDto);
        return commonResult;


    }

    /**
     * 下载excel 子流程
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("ajax_syy_rs_lc03_01_down")
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) {

        String parentFormNo = request.getParameter("parentFormNo");
        String blongDeptId = request.getParameter("blongDeptId");
        List<EmpAppraiseDetailDto> list = formEmpSubAppraiseService.getEmpAppraiseDetailDtos(Long.parseLong(parentFormNo), Long.parseLong(blongDeptId));
        List<EmpAppraiseDetailDtoModel> empAppraiseDetailDtoModels = new ArrayList<>();
        for (EmpAppraiseDetailDto detailDto:list){
            EmpAppraiseDetailDtoModel dtoModel = new EmpAppraiseDetailDtoModel();
            dtoModel.setLoginName(detailDto.getLoginName());
            EmpDto empDto=employeeService.getEmpById(detailDto.getEmpId());
            dtoModel.setDeptName(empDto.getDeptName());
            dtoModel.setEmpSelfGrade(detailDto.getEmpSelfGrade());
            if (detailDto.getDeptScores() != null) {
                dtoModel.setDeptScores(detailDto.getDeptScores().toString());
            }
            dtoModel.setDeptGrade(detailDto.getDeptGrade());
            if (detailDto.getGroupScores() != null) {
                dtoModel.setGroupScores(detailDto.getGroupScores().toString());
            }
            dtoModel.setGroupGrade(detailDto.getGroupGrade());
            dtoModel.setChargeLeaderScores(detailDto.getChargeLeaderScores());
            dtoModel.setChargeLeaderLevel(detailDto.getChargeLeaderLevel());

            empAppraiseDetailDtoModels.add(dtoModel);
        }

        ExpotExcel<EmpAppraiseDetailDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"员工姓名","所在部门及单位", "员工自评", "基层评分", "基层评级", "分管评分", "分管评级","委员会评分","委员会评级"};
        expotExcel.doExportExcel("部门员工综合考核表", header, empAppraiseDetailDtoModels, "yyyy-MM-dd", response);

    }

    /**
     * 下载excel 主流程
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("ajax_syy_rs_lc03_down")
    public void download1(Model model, HttpServletRequest request, HttpServletResponse response) {

        String formNo = request.getParameter("formNo");
        EmpCriteria empCriteria = new EmpCriteria();
        empCriteria.setPageSize(1000);
        PagedResult<EmpAppraiseDetailDto> pagedResult=formAppraiseService.getPagesByAllDetails(Long.parseLong(formNo), empCriteria);
        List<EmpAppraiseDetailDtoModel> empAppraiseDetailDtoModels = new ArrayList<>();
        for (EmpAppraiseDetailDto detailDto:pagedResult.getItems()){
            EmpAppraiseDetailDtoModel dtoModel = new EmpAppraiseDetailDtoModel();
            dtoModel.setLoginName(detailDto.getLoginName());
            EmpDto empDto=employeeService.getEmpById(detailDto.getEmpId());
            dtoModel.setDeptName(empDto.getDeptName());
            dtoModel.setEmpSelfGrade(detailDto.getEmpSelfGrade());
            if (detailDto.getDeptScores() != null) {
                dtoModel.setDeptScores(detailDto.getDeptScores().toString());
            }
            dtoModel.setDeptGrade(detailDto.getDeptGrade());
            if (detailDto.getGroupScores() != null) {
                dtoModel.setGroupScores(detailDto.getGroupScores().toString());
            }
            dtoModel.setGroupGrade(detailDto.getGroupGrade());
            dtoModel.setChargeLeaderScores(detailDto.getChargeLeaderScores());
            dtoModel.setChargeLeaderLevel(detailDto.getChargeLeaderLevel());

            empAppraiseDetailDtoModels.add(dtoModel);
        }

        ExpotExcel<EmpAppraiseDetailDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"员工姓名","所在部门及单位", "员工自评", "基层评分", "基层评级", "分管评分", "分管评级","委员会评分","委员会评级"};
        expotExcel.doExportExcel("部门员工综合考核表", header, empAppraiseDetailDtoModels, "yyyy-MM-dd", response);

    }

    /**
     * 上传文件 主表单 第2步 委员会审批
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("ajax_syy_rs_lc03_upload")
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
//                originalFilename = myfile.getOriginalFilename();
                try {
                    UploadEmpAppraiseDetailFactory factory = new UploadEmpAppraiseDetailFactory();
                    ListResult<EmpAppraiseDetailDto> dtos = factory.readExcel(myfile.getInputStream());
                    if (dtos.getIsSuccess()) {
                        result = formAppraiseService.updateByList(Long.parseLong(formNo), dtos.getItems());
                        request.setAttribute("isUpload", true);
                    } else
                        result = new CommonResult(false, dtos.getMsg());

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
        return;
    }



    @RequestMapping("ajax_my")
    public void myAjax(HttpServletResponse response,HttpServletRequest request){

        String id = request.getParameter("id");
        String no = request.getParameter("no");

        String json = JsonHelper.toJson(id);
        AjaxResponse.write(response,json);
    }



}
