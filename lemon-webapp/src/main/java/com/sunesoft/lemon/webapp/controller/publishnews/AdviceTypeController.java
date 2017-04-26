package com.sunesoft.lemon.webapp.controller.publishnews;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.AdviceTypeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.AdviceTypeCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.RoleTypeDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiazl on 2017/1/18.
 */
@Controller
public class AdviceTypeController extends Layout {
    @Autowired
    AdviceTypeService service;

    @RequestMapping(value = "advice_type_index")
    public ModelAndView adviceType_index(HttpServletRequest request, Model model) {
        return view(layout, "publishnews/adviceType", model);
    }

    @RequestMapping(value = "_advice_type_model")
    public ModelAndView getTheModel(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            RoleTypeDto dto = service.getById(Long.parseLong(id));
            model.addAttribute("bean", dto);
        }
        return view("publishnews/_addOrUpdateAdviceType", model);
    }

    /**
     * 增加和修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "add_update_adviceType")
    @ResponseBody
    public CommonResult add_update_adviceType(HttpServletRequest request, RoleTypeDto dto) {
        String id = request.getParameter("id");
        CommonResult commonResult = null;
        if (StringUtils.isNullOrWhiteSpace(id)) {
            //新增
            commonResult = service.addAdviceType(dto);
        } else {
            //修改
            commonResult = service.updateAdvicetype(dto);
        }
        return commonResult;
    }

    /**
     * 单个删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "delete_one")
    @ResponseBody
    public CommonResult delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        CommonResult commonResult = service.delete(Long.parseLong(id));
        return commonResult;
    }

    /**
     * 是否禁用
     * @param request
     * @return
     */
    @RequestMapping(value = "forbidden_one_adviceType")
    @ResponseBody
    public CommonResult forbidden(HttpServletRequest request) {
        String id = request.getParameter("id");
        String b = request.getParameter("forbide");

        return service.setStation(Long.parseLong(id), Boolean.valueOf(b));
    }


    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @param criteria
     */
    @RequestMapping(value = "ajax_adviceType_query_list")
    @ResponseBody
    public void ajax_query_datas(HttpServletRequest request, HttpServletResponse response, AdviceTypeCriteria criteria) {
        String name = request.getParameter("name");
        if (!StringUtils.isNullOrWhiteSpace(name)) {
            criteria.setName(URI.deURI(name));
        }
        PagedResult<RoleTypeDto> pagedResult = service.paged(criteria);
        AjaxResponse.write(response, JsonHelper.toJson(pagedResult));
    }
}
