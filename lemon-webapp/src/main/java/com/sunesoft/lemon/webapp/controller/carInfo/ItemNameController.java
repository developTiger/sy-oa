package com.sunesoft.lemon.webapp.controller.carInfo;

import com.google.gson.Gson;
import com.sunesoft.lemon.deanery.car.application.ItemProjectService;
import com.sunesoft.lemon.deanery.car.application.SpecialtyTypeService;
import com.sunesoft.lemon.deanery.car.application.criteria.ItemProjectCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.SpecialtyTypeCriteria;
import com.sunesoft.lemon.deanery.car.domain.ItemProject;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
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
public class ItemNameController extends Layout {
    @Autowired
    private ItemProjectService itemProjectService;


    @RequestMapping(value = "sra_type_itemName")
    public ModelAndView typeInfo(Model model){

        return view(layout,"carInfo/ItemProject",model);
    }


    /**
     * 页面数据显示
     * @param
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_itemType_query_list")
    public void queryInfo(ItemProjectCriteria itemProjectCriteria,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        if(!itemProjectCriteria.getItemName().equals("")) {
            itemProjectCriteria.setItemName(URLDecoder.decode(itemProjectCriteria.getItemName(), "UTF-8"));
        }
        PagedResult<ItemProject> pagedResult = itemProjectService.getType(itemProjectCriteria);

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
    @RequestMapping(value = "_additemNameInfoForm")
    public ModelAndView addCarInfoForm(Model model,HttpServletRequest request,HttpServletResponse response){

        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            List<ItemProject> itemProject = itemProjectService.getType(Long.parseLong(id));
            model.addAttribute("beans", itemProject.get(0));
        }
        return view("/carInfo/_additemNameInfoForm", model);
    }

    /**
     * 信息弹窗 提交  新增和修改是同一个方法
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_add_update_item", method = RequestMethod.POST)
    public void addOrUpdateCar(ItemProject itemProject,HttpServletRequest request, HttpServletResponse response) {
        //是否新增功能的标志位，用来作提示信息用  0:新增 1：修改
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("flag",0);
        //新增 状态为空闲，值为1
        String itemName = request.getParameter("itemName");
        if(!("").equals(itemName) && itemName != null ){
            itemProject.setItemName(itemName);
        }
        //修改
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            String itemName1 = request.getParameter("itemName");
            itemProject.setItemName(itemName1);
            res.put("flag",1);
        }
        String result = itemProjectService.save(itemProject) > 0 ? "success" : "error";
        res.put("res_info",result);
        AjaxResponse.write(response, "text", new Gson().toJson(res));
    }

    /**
     * 页面数据删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_deleteItem")
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
            Boolean result = itemProjectService.deletes(listid.toArray(new Long[listid.size()]));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }
}
