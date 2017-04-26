package com.sunesoft.lemon.webapp.controller.userAchievements;

import com.sunesoft.lemon.deanery.patentCG.application.PatentService;
import com.sunesoft.lemon.deanery.patentCG.application.criteria.PatentCriteria;
import com.sunesoft.lemon.deanery.project.application.criteria.ScientificResearchProjectCriteria;
import com.sunesoft.lemon.deanery.projectCG.application.ProjectResultService;
import com.sunesoft.lemon.deanery.treatiseCG.application.TreatiseService;
import com.sunesoft.lemon.deanery.treatiseCG.application.criteria.TreatiseCriteria;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/7/13.
 */
@Controller
public class treatiseCountController extends Layout {

    @Autowired
    TreatiseService treatiseService;

    @Autowired
    ProjectResultService projectResultService;

    @Autowired
    PatentService patentService;

    @RequestMapping(value = "sra_u_t")
    public ModelAndView index(Model model){
        return view(layout,"userAchievements/treatiseCount",model);
    }

    @RequestMapping(value = "ajax_treatiseCount_query_list")
    public void queryData(Model model,HttpServletResponse response){
        List treat=treatiseService.treatiseReport();
        Map m = new HashMap();
        m.put("testname",treat);

        String json = JsonHelper.toJson(m);
        AjaxResponse.write(response,json);

    }



    @RequestMapping(value = "ajax_project_query")
    public void queryProjectData(HttpServletResponse response){
        List project=projectResultService.projectCGReport();
        Map m = new HashMap();
        m.put("project",project);
        String json = JsonHelper.toJson(m);
        AjaxResponse.write(response,json);
    }

    @RequestMapping(value = "ajax_patent_query")
    public void queryPatentData(HttpServletResponse response){
        List patent=patentService.patentReport();
        Map m = new HashMap();
        m.put("patent",patent);
        String json = JsonHelper.toJson(m);
        AjaxResponse.write(response,json);
    }


    @RequestMapping(value = "ajax_treatise_query_list1")
    public ModelAndView a11ProjectInfo1(TreatiseCriteria criteria, Model model, HttpServletRequest request, HttpServletResponse response){

        String treatise_level = request.getParameter("treatise_level");
        String is_core = request.getParameter("is_core");
        String is_cooperate = request.getParameter("is_cooperate");

        criteria.setTreatise_Level(treatise_level);
        model.addAttribute("treatise_level",criteria.getTreatise_Level());

        if(is_core.equals("true")){
            model.addAttribute("is_core","1");
        }
        if(is_core.equals("false")) {
            model.addAttribute("is_core", "0");
        }

        if(is_cooperate.equals("true")){
            model.addAttribute("is_cooperate","1");
        }
        if(is_cooperate.equals("false")) {
            model.addAttribute("is_cooperate", "0");
        }



        return view(layout,"userAchievements/books",model);
    }

    @RequestMapping(value = "ajax_treatise_query_list2")
    public ModelAndView queryPatent( Model model, HttpServletRequest request){

        String patent_type = request.getParameter("patent_type");
        model.addAttribute("patent_type",patent_type);


        return view(layout,"awards/patentInfo",model);
    }

    @RequestMapping(value = "ajax_treatise_query_list3")
    public ModelAndView queryProject( Model model, HttpServletRequest request){

        String win_level = request.getParameter("win_level");
        if(win_level.equals("1")){
            model.addAttribute("win_level","国家级");
        }
        if(win_level.equals("2")){
            model.addAttribute("win_level","省部级");
        }
        if(win_level.equals("3")){
            model.addAttribute("win_level","油田公司级");
        }
        if(win_level.equals("4")){
            model.addAttribute("win_level","厂处级");
        }

        String is_cooperate_result = request.getParameter("is_cooperate_result");
        if(is_cooperate_result.equals("true")){
            model.addAttribute("is_cooperate_result","1");
        }
        if(is_cooperate_result.equals("false")) {
            model.addAttribute("is_cooperate_result", "0");
        }


        return view(layout,"awards/awardsInfo",model);
    }



}
