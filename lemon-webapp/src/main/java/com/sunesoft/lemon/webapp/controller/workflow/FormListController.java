package com.sunesoft.lemon.webapp.controller.workflow;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.application.FormListService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormListCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.domain.FormList;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import com.sunesoft.lemon.deanery.car.application.static_common.static_common;

/**
 * Created by zhouz on 2016/5/30.
 */
@Controller
public class FormListController extends Layout {

    @Autowired
    FormListService formListService;

    @RequestMapping(value = "sra_fm_list")
    public ModelAndView formListinfos(Model model) {
        return view(layout, "workflow/formlist", model);
    }

    @RequestMapping(value = "ajax_formlistQuery_list")
    public void getEmpinfos(FormListCriteria criteria, HttpServletRequest request, HttpServletResponse response) {
        if(!StringUtils.isNullOrWhiteSpace(criteria.getFormName()))
        try {

            criteria.setFormName(URLDecoder.decode(criteria.getFormName(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        criteria.setAscOrDesc(true);
        criteria.setOrderByProperty("formKind");
        PagedResult<FormListDto> result = formListService.getFormListPaged(criteria);// new PagedResult<FormListDto>(list,criteria.getPageNumber(),criteria.getPageSize(),list.size());
        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }

    @RequestMapping("_addOrUpdateForm")
    public ModelAndView addOrUpdateForm(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            FormListDto f1 = formListService.getByKey(Long.parseLong(id));
            model.addAttribute("f", f1);
        }
        return view("workflow/_addform", model);
    }

    @RequestMapping("ajax_add_update_form")
    public void addOrUpdateForm(FormListDto dto, HttpServletRequest request, HttpServletResponse response, Model model) {


        /*String result = "";
        if (dto.getId() != null && dto.getId() > 0) {
            result = formListService.update(dto).getIsSuccess() ? "success" : "error";
        } else {
            result = formListService.add(dto) > 0 ? "success" : "error";
        }

        AjaxResponse.write(response, "text", result);*/
            String result = "";
        Long id = dto.getId();
        if (id != null && id > 0) {
            result = formListService.update(dto).getIsSuccess() ? "success" : "error";
        } else {
            result = formListService.add(dto) > 0 ? "success" : "error";
        }

        AjaxResponse.write(response, "text", result);
    }
    @RequestMapping("sra_f_userApply")
    public ModelAndView getUserFormList(Model model) {
        FormListCriteria criteria= new FormListCriteria();
        criteria.setPageSize(100);
        criteria.setHasApplyView(true);
        criteria.setAscOrDesc(true);
        criteria.setOrderByProperty("formKind");
        PagedResult<FormListDto> result = formListService.getFormListPaged(criteria);// new PagedResult<FormListDto>(list,criteria.getPageNumber(),criteria.getPageSize(),list.size());

        model.addAttribute("list",result);
        return view(layout, "workflow/applyForm", model);
    }

    /*@RequestMapping("_deleteForm")
    public void deleteFrom(HttpServletRequest request, HttpServletResponse response){
        String ids=request.getParameter("ids");
        List<Long> listid=new ArrayList<Long>();
        if(ids!=null&&!ids.equals("")){
            String eids[]=ids.split(",");
            for(String id:eids){
                listid.add(Long.parseLong(id));
            }
        }
        if(listid.size()==0){
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        }else{
            System.out.print("删除选择数据");
            AjaxResponse.write(response, "text", true ? "success" : "error");
        }
    }*/
    @RequestMapping("_deleteForm")
    public void deleteFrom(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<Long>();
        CommonResult commonResult = null;
        if (ids != null && !ids.equals("")) {
            String eids[] = ids.split(",");
            for (String id : eids) {
                listid.add(Long.parseLong(id));
                commonResult = formListService.delete(Long.parseLong(id));
            }
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的表单");
        } else {
            if (commonResult == null) {
                AjaxResponse.write(response, "text", "删除失败");
            } else {
                AjaxResponse.write(response, "text", "success");
            }
        }
        /*if(listid.size()==0){
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        }else{
            System.out.print("删除选择数据");
            AjaxResponse.write(response, "text", true ? "success" : "error");
        }*/
    }

    @RequestMapping(value = "_deleteFormWindow")
    public ModelAndView deleteForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        model.addAttribute("idTemp", id);
        return view("workflow/_deleteForm", model);
    }

    @RequestMapping("ajax_delete_id")
    public void deleteById(HttpServletResponse response, HttpServletRequest request) {
        String id = request.getParameter("id");
        CommonResult commonResult = formListService.delete(Long.parseLong(id));
        String result = commonResult != null ? "success" : "error";
        AjaxResponse.write(response, "text", result);
    }
}
