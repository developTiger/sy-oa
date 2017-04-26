package com.sunesoft.lemon.webapp.controller.typeOfTreatise;

import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseDto;
import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseService;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.criteria.DeptmentCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pxj on 2016/12/15.
 */
@Controller
public class TypeOfTreatiseController extends Layout {
    @Autowired
    DeptmentService deptmentService;

    @Autowired
    TypeOfTreatiseService typeOfTreatiseService;

    /**
     * 论著类型
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_u_type")
    public ModelAndView sra_p_plan(Model model){

        return view(layout, "typeOfTreatise/queryTypeOfTreatise", model);
    }


    /**
     * 新增论著类型页面模态框
     * @param typeOfTreatiseDto
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="add_typeoftreatise")
    public ModelAndView addMenuForm(TypeOfTreatiseDto typeOfTreatiseDto, Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            TypeOfTreatiseDto typeOfTreatiseDtos = typeOfTreatiseService.findById(id);
            model.addAttribute("bean", typeOfTreatiseDtos);
        }
        DeptmentCriteria deptmentCriteria=new DeptmentCriteria();
        List<DeptmentDto> deptmentDtoList=deptmentService.getAllDept();
        if(deptmentDtoList.size()>0) {
            model.addAttribute("dept", deptmentDtoList);
        }else{
            model.addAttribute("dept", null);
        }
        return view("typeOfTreatise/addTypeOfTreatiseForm", model);
    }

    /**
     * 保存/更新
     * @param typeOfTreatiseDto
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value =  "ajax_add_update_type_treatise")
    public void ajax_add_update_type_treatise(TypeOfTreatiseDto typeOfTreatiseDto,HttpServletResponse response){
        CommonResult commonResult=typeOfTreatiseService.save(typeOfTreatiseDto);
        if(commonResult.getIsSuccess()){
            if(typeOfTreatiseDto.getId()!=null){
                commonResult.setId(1L);
            }else{
                commonResult.setId(0L);
            }
        }

        AjaxResponse.write(response, JsonHelper.toJson(commonResult));
    }

    /**
     * 获取论著类型数据
     * @param typeOfTreatiseDto
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "ajax_typeOfTreatise_query_list")
    public void deptQuery_PageList(TypeOfTreatiseDto typeOfTreatiseDto, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        PagedResult PagedResult =typeOfTreatiseService.getTypeOfTreatise(typeOfTreatiseDto);
        String json = JsonHelper.toJson(PagedResult);
        AjaxResponse.write(response, json);
    }

    /**
     * 删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_deleteTypeOfTreatise")
    public void ajax_deleteTypeOfTreatise(HttpServletRequest request,HttpServletResponse response){
        String ids = request.getParameter("ids");
        List<Long> listId = new ArrayList<>();
        if(ids!=null && !"".equals(ids)) {
            String[] stringDs = ids.split(",");
            for (String id : stringDs) {
                listId.add(Long.parseLong(id));
            }
        }
        if (listId.size() == 0) {
            AjaxResponse.write(response, "text", "请选择要操作的数据");
        } else {
            CommonResult commonResult = typeOfTreatiseService.delete(listId);
            String json=JsonHelper.toJson(commonResult);
            AjaxResponse.write(response,json);
        }
    }

}
