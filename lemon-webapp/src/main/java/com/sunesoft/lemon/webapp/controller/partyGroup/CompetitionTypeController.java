package com.sunesoft.lemon.webapp.controller.partyGroup;

import com.sunesoft.lemon.ay.partyGroup.application.criteria.CompetitionTypeCriteria;
import com.sunesoft.lemon.ay.partyGroupForms.domain.CompetitionType;
import com.sunesoft.lemon.ay.partyGroupForms.application.CompetitionTypeService;
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

/**
 * Created by admin on 2017/2/14.
 */
@Controller
public class CompetitionTypeController extends Layout {


    @Autowired
    CompetitionTypeService service;


    @RequestMapping(value = "sra_w_a_competitionType")
    public ModelAndView index(Model model){
        return view(layout,"partyGroup_edit/syy_dq_lc01_a_competitionType",model);
    }

    @RequestMapping(value = "_addOrUpdateCompetitionType")
    public ModelAndView addOrUpdate(Model model,HttpServletRequest request){
        String id = request.getParameter("id");

        if (!StringUtils.isNullOrWhiteSpace(id)){
            CompetitionType type=service.getById(Long.parseLong(id));
            model.addAttribute("bean",type);
        }

        return view("partyGroup_edit/_addOrUpdateCompetitionType",model);
    }

    @RequestMapping(value = "add_update_competitionType",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addOrUpdate(HttpServletRequest request,CompetitionType type){
        String id = request.getParameter("id");

        CommonResult result = null;
        if (!StringUtils.isNullOrWhiteSpace(id)){
            //修改
            result=service.update(type);
        }else{
            //新增
            result=service.add(type);
        }
        return result;
    }

    @RequestMapping(value = "ajax_competitionType_query_list")
    @ResponseBody
    public PagedResult<CompetitionType> queryData(HttpServletRequest request,CompetitionTypeCriteria criteria){
        String name = request.getParameter("name");
        if (!StringUtils.isNullOrWhiteSpace(name))
            criteria.setName(URI.deURI(name));


        PagedResult<CompetitionType> pagedResult=service.pages(criteria);
        return pagedResult;
    }

    @RequestMapping(value = "deleteCompetitionType")
    @ResponseBody
    public CommonResult delete(HttpServletRequest request){
        String id = request.getParameter("id");
        CommonResult commonResult = null;
        if (!StringUtils.isNullOrWhiteSpace(id)){
            commonResult=service.delete(Long.parseLong(id));
        }
        return commonResult;
    }
}
