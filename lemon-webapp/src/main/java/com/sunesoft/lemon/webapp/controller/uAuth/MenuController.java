package com.sunesoft.lemon.webapp.controller.uAuth;

import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.uAuth.application.SysResourceService;
import com.sunesoft.lemon.syms.uAuth.application.criteria.ResourceCriteria;
import com.sunesoft.lemon.syms.uAuth.application.dtos.ResourceDto;
import com.sunesoft.lemon.syms.uAuth.domain.SysResource;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/5/25.
 */
@Controller
public class MenuController extends Layout {

    @Autowired
    SysResourceService menuService;

    @RequestMapping(value = "sra_menu")
    public ModelAndView menuIndex(Model modal, HttpServletRequest request, HttpServletResponse response) {
        return view(layout, "uAuth/menuIndex", modal);
    }

    /**
     * @param menu
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_add_update_menu", method = RequestMethod.POST)
    public void addOrUpdateMenu(ResourceDto menu ,HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String url = request.getParameter("url");
        String sort = request.getParameter("sort");
        String id = request.getParameter("id");
        String parentId = request.getParameter("parentId");
        String target = request.getParameter("target");
        String resType = request.getParameter("resType");
        menu.setName(name);
        menu.setUrl(url);
        menu.setSort(Integer.parseInt(sort));
        menu.setTarget(target);
        if (!StringUtils.isNullOrWhiteSpace(parentId)) {
            menu.setParentId(Long.parseLong(parentId));
        }
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            menu.setId(Long.parseLong(id));
        }
        if (!StringUtils.isNullOrWhiteSpace(resType)) {
            menu.setResType(Integer.parseInt(resType));
        }
        String result = menuService.addOrUodate(menu) > 0 ? "success" : "error";
        AjaxResponse.write(response, "text", result);
    }

    @RequestMapping(value = "_addMenuForm")
    public ModelAndView addMenuForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String parentId = request.getParameter("parentId");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            SysResource menu = menuService.getByKey(Long.parseLong(id));
            model.addAttribute("bean", menu);
            if(!menu.getIsRoot())
                model.addAttribute("parentId",menu.getParentResource().getId());
        }
        else {
            if (!StringUtils.isNullOrWhiteSpace(parentId)) {
                model.addAttribute("parentId", parentId);
            }
        }
        return view("uAuth/_addMenuForm", model);
    }

    @RequestMapping(value = "ajax_menu_query_list")
    public void menuQuery_pagelist(ResourceCriteria param, HttpServletRequest request, HttpServletResponse response) {
     /*   if(!StringUtils.isNullOrWhiteSpace(param.getMenuName())){
            param.setMenuName(URI.deURI(param.getMenuName()));
        }*/
        ListResult result =new ListResult( menuService.getResources(""));

        String json = JsonHelper.toJson(result);
        AjaxResponse.write(response, json);
    }



    @RequestMapping(value = "ajax_deleteMenu")
    public void deleteMenu(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        if(ids!=null && !"".equals(ids)) {
            for (String id : pids) {
                listid.add(Long.parseLong(id));
            }
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = menuService.deleteMenu(listid.toArray(new Long[listid.size()]));
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }

}
