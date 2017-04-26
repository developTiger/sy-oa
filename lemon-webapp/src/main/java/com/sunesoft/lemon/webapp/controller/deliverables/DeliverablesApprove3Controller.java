package com.sunesoft.lemon.webapp.controller.deliverables;

import com.sunesoft.lemon.deanery.deliverables.application.FormDeliverApplyService;
import com.sunesoft.lemon.deanery.deliverables.application.criteria.DeliverResearchCriteria;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverApplyDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swb on 2016/9/12.
 */
@Controller
public class DeliverablesApprove3Controller extends Layout{

    @Autowired
    FormDeliverApplyService formDeliverApplyService;

    @Autowired
    UserSession us;

    @RequestMapping("deliver_app3")
    public ModelAndView syy_kg_lc04(Model model,HttpServletRequest request){

        return view(layout,"deliverables_flow/deliver03",model);
    }

    @RequestMapping(value = "deliver_query03", method = RequestMethod.GET)
    public void deliver_query03(DeliverResearchCriteria criteria,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        PagedResult<FormDeliverApplyDto> deliverDtoPagedResult = formDeliverApplyService.getDeliverApproves3(criteria);

        String json = JsonHelper.toJson(deliverDtoPagedResult);
        AjaxResponse.write(response, json);



    }

    //意见及审批同意
    @RequestMapping(value = "ajax_approveDeliver03")
    public void approvalDeliverOK(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String ideas = request.getParameter("ideas");
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
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setAppValue(AppValue.Y.ordinal());
                dto.setContent("");
                CommonResult nextProject = formDeliverApplyService.updateDeliver(dto);
                AjaxResponse.write(response, "text", "success");
            }
        }else{
            AjaxResponse.write(response, "text", "error");
        }

    }

    //意见及审批拒绝
    @RequestMapping(value = "ajax_rejectDeliver03")
    public void approvalProjectNO(ApproveFormDto dto,Model model, HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        String ideas = request.getParameter("ideas");
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
                dto.setFormNo((Long)listid.get(i));
                dto.setFormKind("SYY_KG_LC06");
                dto.setAppValue(AppValue.N.ordinal());
                dto.setContent("");
                CommonResult nextProject = formDeliverApplyService.updateDeliver(dto);
                AjaxResponse.write(response, "text", "success");
            }
        }else{
            AjaxResponse.write(response, "text", "error");
        }
    }
}
