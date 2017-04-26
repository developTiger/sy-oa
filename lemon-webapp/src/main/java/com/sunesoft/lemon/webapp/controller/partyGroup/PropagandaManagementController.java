package com.sunesoft.lemon.webapp.controller.partyGroup;

import com.sun.jndi.toolkit.url.Uri;
import com.sunesoft.lemon.ay.partyGroup.application.PropagandaManagementService;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.PropagandaManagementCriteria;
import com.sunesoft.lemon.ay.partyGroup.application.dtos.PropagandaManagementDto;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.NewsType;
import com.sunesoft.lemon.ay.partyGroupForms.application.FormPropagandaManagementService;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormPropagandaManagementDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormList;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.model.PropaManagementModel;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 宣传管理申请流程
 * Created by admin on 2016/9/5.
 */
@Controller
public class PropagandaManagementController extends Layout {


    @Autowired
    UserSession userSession;

    @Autowired
    FormListService formListService;

    @Autowired
    FormPropagandaManagementService formPropagandaManagementService;

    @Autowired
    PropagandaManagementService propagandaManagementService;

    @Autowired
    DeptmentService deptmentService;

    @RequestMapping(value = "syy_dq_lc06_a")
    public ModelAndView index(Model model,HttpServletRequest request){
        FormListDto dto =formListService.getFormListInfo("SYY_DQ_LC06");
        model.addAttribute("formKind", dto.getFormKind());
        model.addAttribute("formKindName", dto.getFormName());
        model.addAttribute("date", DateHelper.formatDate(new Date(), "yyyy-MM-dd"));

        String formNo = request.getParameter("formNo");
        if (!StringUtils.isNullOrWhiteSpace(formNo)){
            FormPropagandaManagementDto managementDto=formPropagandaManagementService.getFormByFormNo(Long.parseLong(formNo));
            model.addAttribute("managementDto",managementDto);
            model.addAttribute("formNo",managementDto.getFormNo());
        }

        return view(applyLayout,"partyGroup/syy_dq_lc06_a",model);
    }

    @RequestMapping(value = "ajax_add_update_propagandaManagement")
    @ResponseBody
    public CommonResult addOrUpdatePropamanage(HttpServletRequest request,FormPropagandaManagementDto formPropagandaManagementDto){
        EmpSessionDto empSessionDto=userSession.getCurrentUser(request);

        String deptName = request.getParameter("deptName");
        String publishTime = request.getParameter("publishTime");

        formPropagandaManagementDto.setApplyer(empSessionDto.getId());
        formPropagandaManagementDto.setApplyerName(empSessionDto.getName());
        formPropagandaManagementDto.setDeptId(empSessionDto.getDeptId());
        formPropagandaManagementDto.setDeptName(empSessionDto.getDeptName());

        if (!StringUtils.isNullOrWhiteSpace(publishTime))
            formPropagandaManagementDto.setTime(DateHelper.parse(publishTime,"yyyy-MM-dd"));


        formPropagandaManagementDto.setUnit(deptName);
        CommonResult commonResult=formPropagandaManagementService.addByDto(formPropagandaManagementDto);
        return formPropagandaManagementService.submitForm(commonResult.getId(),formPropagandaManagementDto.getFormKind());
    }

    @RequestMapping(value = "ajax_syy_dq_lc06_data")
    @ResponseBody
    public FormPropagandaManagementDto queryData(HttpServletRequest request){
        String formNo = request.getParameter("formNo");
        String viewOnly = request.getParameter("viewOnly");
        FormPropagandaManagementDto formPropagandaManagementDto=formPropagandaManagementService.getFormByFormNo(Long.parseLong(formNo));
        formPropagandaManagementDto.setIsViewOnly(Boolean.valueOf(viewOnly));
        return formPropagandaManagementDto;
    }

    @RequestMapping(value = "sra_x_c")
    public ModelAndView indexCount(Model model,HttpServletRequest request) {
        EmpSessionDto empSessionDto = userSession.getCurrentUser(request);
        model.addAttribute("deptNo",empSessionDto.getDeptNo());
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
        return view(layout, "partyGroup/syy_dq_lc06_a_count", model);
    }

    @RequestMapping(value = "ajax_query_promanage_count")
    public void queryCount(PropagandaManagementCriteria criteria,HttpServletResponse response,HttpServletRequest request){


        String beginTime = request.getParameter("bTime");
        String endTime = request.getParameter("eTime");
        if (!StringUtils.isNullOrWhiteSpace(beginTime))
            criteria.setBeginTime(DateHelper.parse(beginTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(endTime)) {
            String end_time = endTime+" 23:59:59";//针对于当天的查询
            criteria.setEndTime(DateHelper.parse(String.valueOf(end_time), "yyyy-MM-dd HH:mm:ss"));
        }

        String company = request.getParameter("company");
        if (!StringUtils.isNullOrWhiteSpace(company))
            criteria.setUnit(URI.deURI(company));

        String articleTitle = request.getParameter("articleTitle");
        if (!StringUtils.isNullOrWhiteSpace(articleTitle))
            criteria.setTitle(URI.deURI(articleTitle));

        String author = request.getParameter("author");
        if (!StringUtils.isNullOrWhiteSpace(author))
            criteria.setAuthor(URI.deURI(author));

        String newsType = request.getParameter("pr_newsType");
        if (!StringUtils.isNullOrWhiteSpace(newsType))
            criteria.setNewsType(URI.deURI(newsType));


        PagedResult<FormPropagandaManagementDto> result = propagandaManagementService.getPagesByPropaManageDto(criteria);
        String json  = JsonHelper.toJson(result);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_edit_proManage_count")
    public ModelAndView editPromanagement(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        FormPropagandaManagementDto dto=formPropagandaManagementService.getById(Long.parseLong(id));
        model.addAttribute("beans",dto);
        return view("partyGroup/syy_dq_lc06_a_count_edit",model);
    }

    @RequestMapping(value = "ajax_update_propaManagement_countDetail",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePropaManagement(HttpServletRequest request){
        String id = request.getParameter("id");
        String title =request.getParameter("pr_title");
        String type = request.getParameter("pr_type");
        String author = request.getParameter("pr_author");

        FormPropagandaManagementDto dto = new FormPropagandaManagementDto();

        dto.setId(Long.parseLong(id));
        dto.setNewsType(type);
        dto.setTitle(title);
        dto.setAuthor(author);
        CommonResult commonResult=formPropagandaManagementService.updateProManagement(dto);
        return commonResult;
    }

    @RequestMapping(value = "ajax_delete_proManage_count")
    @ResponseBody
    public CommonResult deleteProManage(HttpServletRequest request){
        String id = request.getParameter("id");
        CommonResult commonResult=formPropagandaManagementService.deleteProManagement(Long.parseLong(id));
        return commonResult;
    }

    /**
     * 宣传报道统计 导出列表
     * @param request
     * @param criteria
     */
    @RequestMapping(value = "ajax_syy_dq_lc06_a_count_down")
    @ResponseBody
    public void proManageCountDown(HttpServletRequest request,PropagandaManagementCriteria criteria,HttpServletResponse response){
        String beginTime = request.getParameter("bTime");
        String endTime = request.getParameter("eTime");
        if (!StringUtils.isNullOrWhiteSpace(beginTime))
            criteria.setBeginTime(DateHelper.parse(beginTime,"yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(endTime))
            criteria.setEndTime(DateHelper.parse(endTime,"yyyy-MM-dd"));

        String company = request.getParameter("company");
        if (!StringUtils.isNullOrWhiteSpace(company))
            criteria.setUnit(URI.deURI(company));

        String articleTitle = request.getParameter("articleTitle");
        if (!StringUtils.isNullOrWhiteSpace(articleTitle))
            criteria.setTitle(URI.deURI(articleTitle));

        String author = request.getParameter("author");
        if (!StringUtils.isNullOrWhiteSpace(author))
            criteria.setAuthor(URI.deURI(author));

        String newsType = request.getParameter("pr_newsType");
        if (!StringUtils.isNullOrWhiteSpace(newsType))
            criteria.setNewsType(URI.deURI(newsType));

        criteria.setPageSize(1000000000);
        PagedResult<FormPropagandaManagementDto> result = propagandaManagementService.getPagesByPropaManageDto(criteria);
        List<PropaManagementModel> list = new ArrayList<>();

        for (FormPropagandaManagementDto dto:result.getItems()){
            PropaManagementModel model = new PropaManagementModel();
            model.setTitle(dto.getTitle());
            model.setAuthor(dto.getAuthor());
            model.setDeptName(dto.getDeptName());
            model.setNewsType(dto.getNewsType());
            model.setTime(dto.getTime());
            if (dto.getFormStatus().equals(FormStatus.AP)){
                model.setFormStatus("通过");
            }
            if (dto.getFormStatus().equals(FormStatus.UA)){
                model.setFormStatus("未通过");
            }

            list.add(model);

        }

        ExpotExcel<PropaManagementModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"文章标题","作者","单位","新闻类别","发表时间","审核结果"};
        expotExcel.doExportExcel("宣传报道统计表",header,list,"yyyy-MM-dd",response);

    }

}
