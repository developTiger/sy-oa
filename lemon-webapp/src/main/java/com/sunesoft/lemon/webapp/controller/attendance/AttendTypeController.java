package com.sunesoft.lemon.webapp.controller.attendance;

import com.sunesoft.lemon.deanery.projectAchievement.application.criteria.ProjectAchievementCriteria;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.AttendTypeService;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendTypeCriteria;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;
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
 * 出勤类型管理交互层
 * Created by xiazl on 2017/3/2.
 */
@Controller
public class AttendTypeController extends Layout {

    @Autowired
    AttendTypeService typeService;

    /**
     * 考勤类型管理主页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_attend_type_index")
    public ModelAndView attendType_index(HttpServletRequest request, Model model) {

        return view(layout, "attend/attendType", model);
    }

    /**
     * 分页查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_attendType_query_list")
    public void ajax_attendType_query_list(HttpServletRequest request, HttpServletResponse response,AttendTypeCriteria criteria) {
        String name = request.getParameter("name");
       if(!StringUtils.isNullOrWhiteSpace(name)){
           criteria.setName(URI.deURI(name.trim()));
       }
        PagedResult<AttendType> pagedResult =typeService.page(criteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    /**
     * 增修弹窗
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "_attend_type_model")
    public ModelAndView addOrUpdate(HttpServletRequest request, Model model) {
        String id=request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            AttendType type=typeService.get(Long.parseLong(id));
            model.addAttribute("bean",type);
        }
        return view("attend/_addOrUpdateAttendType", model);

    }

    /**
     * 增加或修改
     * @param request
     * @param response
     * @param type
     * @return
     */
    @RequestMapping(value = "ajax_add_update_attend_type")
    @ResponseBody
    public CommonResult ajax_addOrUpdate(HttpServletRequest request, HttpServletResponse response, AttendType type) {
        String name=request.getParameter("name");
        String cord=request.getParameter("cord");
        if(StringUtils.isNullOrWhiteSpace(name)) return new CommonResult(false,"操作失败——请填名称");
        if(StringUtils.isNullOrWhiteSpace(cord)) return new CommonResult(false,"操作失败——请填标识");
        type.setCord(cord.trim().toUpperCase());
        type.setName(name.trim());
        Long result;
        if (type.getId() == null) {
            //新增
            result = typeService.create(type);
        } else {
            //修改
            result = typeService.edit(type);
        }
        if (result!=null&& result > 0) return ResultFactory.commonSuccess();
        else return new CommonResult(false,"操作失败!");
    }

    /**
     * 删除
     * @param request
     * @return
     */
    @RequestMapping(value = "delete_attend_type_one")
    @ResponseBody
    public CommonResult deleteOne(HttpServletRequest request) {
        String id = request.getParameter("id");
        Boolean b = typeService.delete(Long.parseLong(id));
        if (b) return ResultFactory.commonSuccess();
        else return new CommonResult(false,"删除失败");
    }
}
