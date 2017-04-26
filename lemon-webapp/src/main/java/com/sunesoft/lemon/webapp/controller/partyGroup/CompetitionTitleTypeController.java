package com.sunesoft.lemon.webapp.controller.partyGroup;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.CompetitionTitleTypeCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.application.CompetitionTitleTypeService;
import com.sunesoft.lemon.ay.partyGroupForms.domain.WorkProject_competitionTitleType;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/13.
 */
@Controller
public class CompetitionTitleTypeController extends Layout {

    @Autowired
    CompetitionTitleTypeService service;

    @RequestMapping(value = "sra_w_a_competitionTitleType")
    public ModelAndView index(Model model){
        return view(layout,"partyGroup_edit/syy_dq_lc01_a_competitleType",model);
    }

    @RequestMapping(value = "_addOrUpdateCompetitionTitleType")
    public ModelAndView addOrUpdateTitleType(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)){
            //修改
            WorkProject_competitionTitleType type=service.getById(Long.parseLong(id));
            model.addAttribute("bean",type);
        }


        return view("partyGroup_edit/_addOrUpdateCompetitionTitleType",model);
    }

    @RequestMapping(value = "add_update_competitionTitleType",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdate(HttpServletRequest request,WorkProject_competitionTitleType titleType){
        String id = request.getParameter("id");
        CommonResult commonResult = null;
        if (!StringUtils.isNullOrWhiteSpace(id)){
            //修改
            commonResult=service.updateCompetitionTitleType(titleType);
        }{
            //新增
            commonResult=service.addCompetitionTitleType(titleType);
        }
        return commonResult;
    }

    @RequestMapping(value = "ajax_competitionTitleType_query_list")
    @ResponseBody
    public PagedResult<WorkProject_competitionTitleType> pages(CompetitionTitleTypeCriteria titleTypeCriteria,HttpServletRequest request){
        String name = request.getParameter("name");
        if (!StringUtils.isNullOrWhiteSpace(name))
            titleTypeCriteria.setName(URI.deURI(name));


        PagedResult<WorkProject_competitionTitleType> pagedResult=service.pages(titleTypeCriteria);
        return  pagedResult;
    }

    @RequestMapping(value = "deleteCompetitionTitleType")
    @ResponseBody
    public CommonResult delete(HttpServletRequest request){
        String id = request.getParameter("id");
        CommonResult result = null;
        if (!StringUtils.isNullOrWhiteSpace(id)){
            String[] ids = id.split(",");
            List<Long> list = new ArrayList<>();
            for (int i = 0; i < ids.length; i++) {
                list.add(Long.parseLong(ids[i]));
            }
            result=service.deleteTypes(list);
        }
        return result;
    }


}
