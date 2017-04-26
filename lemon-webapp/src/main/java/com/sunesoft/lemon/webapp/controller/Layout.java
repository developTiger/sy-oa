package com.sunesoft.lemon.webapp.controller;


import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.uAuth.application.SysResourceService;
import com.sunesoft.lemon.syms.uAuth.application.criteria.ResourceCriteria;
import com.sunesoft.lemon.syms.uAuth.application.dtos.ResourceDto;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.Helper;
import com.sunesoft.lemon.webapp.utils.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/5/15.
 */
public class Layout {
    public static String layout = "layout/~layout";
    public static String formLayout = "layout/~formLayout";
    public static String applyLayout="layout/~formApply";
    public static String formViewLayout="layout/~formView";
    public static String formBatchLayout="layout/~formBatch";

    @Autowired
    UserSession us;

    @Autowired
    SysResourceService menuService;

    public ModelAndView view(String layout,String viewName,Model model){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        model.addAttribute("view", viewName + ".html");
        model.addAttribute("ui",UI.class);
        model.addAttribute("helper", Helper.class);
        EmpSessionDto userInfo = us.getCurrentUser(request);
        List<ResourceDto> resourceDtos  = new ArrayList<>();
        String currentUrl = request.getRequestURI().substring(1);
        model.addAttribute("currentUrl",currentUrl);
        if(layout.equals("layout/~layout")) {
            if (userInfo.getLoginName().equals("1") || userInfo.getLoginName().equals("admin")) {
                ResourceCriteria criteria = new ResourceCriteria();
                criteria.setPageSize(100);
                resourceDtos = menuService.getResourceList(criteria);

            } else {
                ///todo 修改多roleid支持
                resourceDtos = us.getUserResource(userInfo.getRoleIds()); //修改多roleid支持
            }
            if(!StringUtils.isNullOrWhiteSpace(currentUrl)&&resourceDtos!=null) {
                Long  currentID=0L;
                for (ResourceDto dto : resourceDtos) {

                    Boolean hasFoundParent = false;
                    if (dto.getChild() != null && dto.getChild().size() > 0) {
                        for (ResourceDto cc : dto.getChild()) {
                            if (cc.getUrl()!=null&&cc.getUrl().equals(currentUrl)) {
                                currentID = dto.getId();
                                hasFoundParent = true;
                                break;
                            }

                        }
                    }
                    if(hasFoundParent) {
                        model.addAttribute("parentMenu", currentID);
                        break;
                    }
                }
            }
            model.addAttribute("menu",resourceDtos);
        }




       model.addAttribute("userInfo",userInfo);
        ModelAndView mv = new ModelAndView(layout);
        return mv;
    }

    public ModelAndView view(String viewName,Model model){
        model.addAttribute("helper", Helper.class);
        model.addAttribute("ui",UI.class);
        ModelAndView mv = new ModelAndView(viewName);
        return mv;
    }


}
