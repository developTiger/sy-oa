package com.sunesoft.lemon.webapp.controller.workflow;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormLeave;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by swb on 2016/6/29.
 */
@Controller
public class FormHeaderController extends Layout {

    @Autowired
    FormHeaderService formHeaderService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    UserSession us;

    @Autowired
    FormListService formListService;

    @RequestMapping("sra_fm_myapply")
    public ModelAndView formHeaderInfo(Model model) {
        FormListCriteria criteria = new FormListCriteria();
        criteria.setPageSize(100);
        criteria.setHasApplyView(true);
        PagedResult<FormListDto> result = formListService.getFormListPaged(criteria);// new PagedResult<FormListDto>(list,criteria.getPageNumber(),criteria.getPageSize(),list.size());

        model.addAttribute("beans", result);
        return view(layout, "workflow/formHeaderApply", model);
    }

    @RequestMapping("sra_fm_myapply_done")
    public ModelAndView formHeaderInfoEnd(Model model) {

        FormListCriteria criteria = new FormListCriteria();
        criteria.setPageSize(100);
        criteria.setHasApplyView(true);
        PagedResult<FormListDto> result = formListService.getFormListPaged(criteria);// new PagedResult<FormListDto>(list,criteria.getPageNumber(),criteria.getPageSize(),list.size());

        model.addAttribute("beans", result);
        return view(layout, "workflow/formHeaderApplyDone", model);
    }

    @RequestMapping("sra_fm_work_todo")
    public ModelAndView formHeaderInfoWork(Model model) {
        FormListCriteria criteria = new FormListCriteria();
        criteria.setPageSize(100);
        criteria.setHasApplyView(true);
        PagedResult<FormListDto> result = formListService.getFormListPaged(criteria);// new PagedResult<FormListDto>(list,criteria.getPageNumber(),criteria.getPageSize(),list.size());

        List<DeptmentDto> depats = deptmentService.getAllDept();
        List<DeptmentDto> del=new ArrayList<>();
       for (DeptmentDto dto: depats){
           if(dto.getDeptName()!=null&&dto.getDeptName().equals("院领导"))del.add(dto);
       }
       depats.removeAll(del);
        model.addAttribute("beans", result);
        model.addAttribute("depats", depats);
        return view(layout, "workflow/formHeaderWorkTodo", model);
    }

    @RequestMapping("sra_fm_work_done")
    public ModelAndView formHeaderInfoSolve(Model model) {
        FormListCriteria criteria = new FormListCriteria();
        criteria.setPageSize(100);
        criteria.setHasApplyView(true);
        PagedResult<FormListDto> result = formListService.getFormListPaged(criteria);// new PagedResult<FormListDto>(list,criteria.getPageNumber(),criteria.getPageSize(),list.size());

        model.addAttribute("beans", result);
        return view(layout, "workflow/formHeaderWorkDone", model);
    }

    @RequestMapping("sra_fm_dept_work_done")
    public ModelAndView formHeaderInfoDeptSolve(Model model) {
        FormListCriteria criteria = new FormListCriteria();
        criteria.setPageSize(100);
        criteria.setHasApplyView(true);
        PagedResult<FormListDto> result = formListService.getFormListPaged(criteria);// new PagedResult<FormListDto>(list,criteria.getPageNumber(),criteria.getPageSize(),list.size());

        model.addAttribute("beans", result);
        return view(layout, "workflow/formHeaderDeptWorkDone", model);
    }


    @RequestMapping("ajax_queryFormHeader")
    public void queryFormHeader(HttpServletRequest request, HttpServletResponse response, Model model, FormHeaderCriteria criteria) {

//        criteria.setFormKind(URI.deURI(criteria.getFormKind()));
        String status = URI.deURI(request.getParameter("formStatus"));
        //  String appStatus =request.getParameter("appStatus");
        String applyerId = request.getParameter("applyer");
//        if(!StringUtils.isNullOrWhiteSpace(appStatus)){
//
//            criteria.setApproveStatus(ApproveStatus.valueOf(appStatus));
//        }
        criteria.setApplyer(Long.parseLong(applyerId));

        if (!StringUtils.isNullOrWhiteSpace(status)) {
            List<FormStatus> formStatuses = new ArrayList<>();
            String[] arrStatuses = status.split(",");
            for (String s : arrStatuses) {
                formStatuses.add(FormStatus.valueOf(s));
            }
            criteria.setArrFormStatus(formStatuses);
        }

        if (criteria.getApplyerName() != null && criteria.getApplyerName() != "") {
            criteria.setApplyerName(URI.deURI(criteria.getApplyerName()));
        }
        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
            String beginTime = URI.deURI(criteria.getBeginDate());
            criteria.setBeginDate(beginTime);
        }
        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
            String endTime = URI.deURI(criteria.getEndDate());
            criteria.setEndDate(endTime);
        }
        String formTypeName = request.getParameter("formTypeName");
        criteria.setFormKind(formTypeName);

        PagedResult<FormHeaderDto> result = formHeaderService.findFormPaged(criteria);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    @RequestMapping("ajax_queryFormHeaderApprove")
    public void queryFormHeaderApprove(HttpServletRequest request, HttpServletResponse response, Model model, FormHeaderCriteria criteria) {

//        criteria.setFormKind(URI.deURI(criteria.getFormKind()));
        String status = request.getParameter("formStatus");

        String appStatus = request.getParameter("appStatus");
        String approverId = request.getParameter("approver");
        String deptId = request.getParameter("deptId");

        if (!StringUtils.isNullOrWhiteSpace(deptId)) {
            criteria.setBlongDept(Long.parseLong(deptId));
        }

        if (!StringUtils.isNullOrWhiteSpace(appStatus)) {

            criteria.setApproveStatus(ApproveStatus.valueOf(appStatus));
        }
        criteria.setApproverId(Long.parseLong(approverId));


        if (!StringUtils.isNullOrWhiteSpace(status)) {
            status = URI.deURI(status);
            List<FormStatus> formStatuses = new ArrayList<>();
            String[] arrStatuses = status.split(",");
            for (String s : arrStatuses) {
                formStatuses.add(FormStatus.valueOf(s));
            }
            criteria.setArrFormStatus(formStatuses);
        }


        if (criteria.getApplyerName() != null && criteria.getApplyerName() != "") {
            criteria.setApplyerName(URI.deURI(criteria.getApplyerName()));
        }

        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
            String beginTime = URI.deURI(criteria.getBeginDate());
            criteria.setBeginDate(beginTime);
        }

        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
            String endTime = URI.deURI(criteria.getEndDate());
            criteria.setEndDate(endTime);
        }


        String formTypeName = request.getParameter("formTypeName");
        criteria.setFormKind(formTypeName);

        PagedResult<FormHeaderDto> result = formHeaderService.findFormPaged(criteria);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping("ajax_queryFormHeaderDeptApproved")
    public void queryFormHeaderDeptApproved(HttpServletRequest request, HttpServletResponse response,FormHeaderCriteria criteria) {

//        criteria.setFormKind(URI.deURI(criteria.getFormKind()));
        String status = request.getParameter("formStatus");
        String appStatus = request.getParameter("appStatus");//TODO 增加筛选条件
        Long deptId = us.getCurrentUser(request).getDeptId();

        if (!StringUtils.isNullOrWhiteSpace(appStatus)) {

            criteria.setApproveStatus(ApproveStatus.valueOf(appStatus));
        }
        criteria.setApproverDeptId(deptId);


        if (!StringUtils.isNullOrWhiteSpace(status)) {
            status = URI.deURI(status);
            List<FormStatus> formStatuses = new ArrayList<>();
            String[] arrStatuses = status.split(",");
            for (String s : arrStatuses) {
                formStatuses.add(FormStatus.valueOf(s));
            }
            criteria.setArrFormStatus(formStatuses);
        }


        if (criteria.getApplyerName() != null && criteria.getApplyerName() != "") {
            criteria.setApplyerName(URI.deURI(criteria.getApplyerName()));
        }

        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
            String beginTime = URI.deURI(criteria.getBeginDate());
            criteria.setBeginDate(beginTime);
        }

        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
            String endTime = URI.deURI(criteria.getEndDate());
            criteria.setEndDate(endTime);
        }


        String formTypeName = request.getParameter("formTypeName");
        criteria.setFormKind(formTypeName);

        PagedResult<FormHeaderDto> result = formHeaderService.findFormPagedWithDept(criteria);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping("_ajaxAddFormHeader")
    public void ajaxAddFormHeader(HttpServletResponse response, HttpServletRequest request) {
        String applyerName = request.getParameter("applyerName");
        String formKind = request.getParameter("formKind");
        String content = request.getParameter("content");
        String site = request.getParameter("site");
        /*String beginDate=request.getParameter("beginDate");
        String endDate=request.getParameter("endDate");*/
        String lifeCycle = request.getParameter("lifeCycle");

        FormHeaderDto dto = new FormHeaderDto();
        dto.setApplyerName(applyerName);
        dto.setFormKind(formKind);
        dto.setContent(content);

        dto.setBeginDate(new Date());
        dto.setEndDate(new Date());
        dto.setLifeCycle(lifeCycle);
        FormLeave leave = new FormLeave();
        CommonResult result = formHeaderService.AddFormHeader(dto);
        AjaxResponse.write(response, "text", JsonHelper.toJson(result));
    }
}