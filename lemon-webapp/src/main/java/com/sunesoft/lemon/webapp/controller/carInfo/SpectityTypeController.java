package com.sunesoft.lemon.webapp.controller.carInfo;

import com.google.gson.Gson;
import com.sunesoft.lemon.deanery.car.application.SpecialtyTypeService;
import com.sunesoft.lemon.deanery.car.application.criteria.CarCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.SpecialtyTypeCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CarReportDto;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2016/12/19.
 * 专业类别可配置
 */
@Controller
public class SpectityTypeController extends Layout {
    @Autowired
    private  SpecialtyTypeService specialtyTypeService;


    @RequestMapping(value = "sra_type_spectity")
    public ModelAndView typeInfo(Model model,SpecialtyTypeCriteria specialtyTypeCriteria){

        return view(layout,"carInfo/SpectityType",model);
    }


    /**
     * 页面数据显示
     * @param
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_spectityType_query_list")
    public void queryInfo(SpecialtyTypeCriteria specialtyTypeCriteria,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
       if(!specialtyTypeCriteria.getSpecialtyType().equals("")) {
           specialtyTypeCriteria.setSpecialtyType(URLDecoder.decode(specialtyTypeCriteria.getSpecialtyType(), "UTF-8"));
       }
        PagedResult<SpecialtyType> pagedResult = specialtyTypeService.getType(specialtyTypeCriteria);

        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    /**
     * 专业类别查看
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "_addSpectityInfoForm")
    public ModelAndView addCarInfoForm(Model model,HttpServletRequest request,HttpServletResponse response){

        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            SpecialtyType specialtyType = specialtyTypeService.getByIdType(Long.parseLong(id));
            model.addAttribute("beans", specialtyType);
        }
        return view("/carInfo/_addSpectityInfoForm", model);
    }

    /**
     * 信息弹窗 提交  新增和修改是同一个方法
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_add_update_type", method = RequestMethod.POST)
    public void addOrUpdateCar(SpecialtyType specialtyType,HttpServletRequest request, HttpServletResponse response) {
        //是否新增功能的标志位，用来作提示信息用  0:新增 1：修改
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("flag",0);
        //新增 状态为空闲，值为1
        String spectyType = request.getParameter("specialtyType");
        if(!("").equals(spectyType) && spectyType != null ){
            specialtyType.setSpecialtyType(spectyType);
        }
        //修改
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            String specType = request.getParameter("specialtyType");
            specialtyType.setSpecialtyType(specType);
            res.put("flag",1);
        }
        String result = specialtyTypeService.addType(specialtyType) > 0 ? "success" : "error";
        res.put("res_info",result);
        AjaxResponse.write(response, "text", new Gson().toJson(res));
    }

    /**
     * 页面数据删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_deleteType")
    public void deletePermission(HttpServletRequest request, HttpServletResponse response) {
        String ids = request.getParameter("ids");
        List<Long> listid = new ArrayList<>();
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
        if (listid.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的菜单");
        } else {
            Boolean result = specialtyTypeService.delTypes(listid.toArray(new Long[listid.size()]));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }
}
