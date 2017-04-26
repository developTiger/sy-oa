package com.sunesoft.lemon.webapp.controller.userAchievements;

import com.sunesoft.lemon.webapp.controller.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liulin on 2016/7/5.
 */
@Controller
public class UserAchievementsController extends Layout {


    @RequestMapping(value = "sra_u_userAchievements")
    public ModelAndView index(Model model){
        return view(layout,"userAchievements/userAchievements",model);
    }


}
