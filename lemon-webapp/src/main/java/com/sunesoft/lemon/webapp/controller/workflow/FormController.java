package com.sunesoft.lemon.webapp.controller.workflow;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormKindManagerService;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouz on 2016/6/21.
 */
@Controller
public class FormController extends Layout {

    @Autowired
    FormHeaderService formHeaderService;

    @Autowired
    FormKindManagerService formKindManagerService;

    @Autowired
    UserSession us;

    @RequestMapping(value = "sra_fm_form")
    public ModelAndView getForm(Model model,HttpServletRequest request){
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");

        if(formKind.contains("SYY_RS_LC06")){
            formKind=formKind.substring(0,formKind.length()-3);
        }
        if(formKind.contains("SYY_RS_LC05")){
            if(!formKind.equals("SYY_RS_LC05")){
                formKind=formKind.substring(0,formKind.length()-3);
            }
        }
        String views = formKind.toLowerCase().replace('-','_')+"_v";
        String floder = request.getParameter("viewurl").trim();

        FormHeaderDto dto = formHeaderService.getHeaderByFormNo(formNo);

        model.addAttribute("header",dto);
        model.addAttribute("viewOnly",false);
        return view(formLayout, floder+"/"+views, model);
    }

    @RequestMapping(value = "sra_fm_form_viewonly")
    public ModelAndView getFormViewOnly(Model model,HttpServletRequest request){
        Long formNo = Long.parseLong(request.getParameter("formNo"));
        String formKind = request.getParameter("formKind");
        if(formKind.contains("SYY_RS_LC06")) {
            if (!formKind.equals("SYY_RS_LC06")) {
                formKind = formKind.substring(0, formKind.length() - 3);
            }
        }
        if(formKind.contains("SYY_RS_LC05")){
            if(!formKind.equals("SYY_RS_LC05")){
                formKind=formKind.substring(0,formKind.length()-3);
            }
        }
        String views = formKind.toLowerCase().replace('-','_')+"_v";
        String floder = request.getParameter("viewurl").trim();
        //String formview = request.getParameter("view");
        FormHeaderDto dto = formHeaderService.getHeaderByFormNo(formNo);
        model.addAttribute("header",dto);
        model.addAttribute("viewOnly",true);
        return view(formViewLayout, floder+"/"+views, model);
    }

    @RequestMapping(value = "sra_fm_apply")
    public ModelAndView getFormApply(Model model,HttpServletRequest request){

        return view("forms/syy_rs_lc06_a", model);
    }

    @RequestMapping(value = "ajax_approve")
    public void approve(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){

        dto=this.completeDtoUserInfo(dto,request);

        Map<String,Object>  param = new HashMap<>();
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            param.put(paraName,request.getParameter(paraName));
        }
        CommonResult result= formKindManagerService.getFormService(dto.getFormKind()).doApprove(dto,param);
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }
    @RequestMapping(value = "ajax_callback")
    public void callback(ApproveFormDto dto,HttpServletRequest request,HttpServletResponse response){

        dto=this.completeDtoUserInfo(dto,request);
//        Map<String,Object>  param = new HashMap<>();
//        Enumeration enu=request.getParameterNames();
//        while(enu.hasMoreElements()){
//            String paraName=(String)enu.nextElement();
//            param.put(paraName,request.getParameter(paraName));
//        }
        CommonResult result= formKindManagerService.getFormService(dto.getFormKind()).doRecall(dto.getFormNo(),dto.getFormKind(),dto.getContent(),dto.getApproverId());
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }


    public void change(){

    }
    public void addApprover(){

    }

    private ApproveFormDto completeDtoUserInfo(ApproveFormDto dto,HttpServletRequest request){
        EmpSessionDto userInfo = us.getCurrentUser(request);
        dto.setApproverId(userInfo.getId());
        dto.setApproverName(userInfo.getName());
        dto.setDeptId(userInfo.getDeptId());
        dto.setDeptName(userInfo.getDeptName());

        return dto;
    }


}
