package com.sunesoft.lemon.webapp.controller.userAchievements;

import com.sunesoft.lemon.webapp.controller.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2016/7/5.
 */
@Controller
public class WorkController extends Layout {

    @RequestMapping(value = "sra_u_work")
    public ModelAndView index(Model model){
        return view(layout,"userAchievements/work",model);
    }

    @RequestMapping(value = "_addWorkForm")
    public ModelAndView addWorkInfo(Model model,HttpServletRequest request,HttpServletResponse response){
        return view(layout,"userAchievements/_addWorkForm",model);
    }
}
