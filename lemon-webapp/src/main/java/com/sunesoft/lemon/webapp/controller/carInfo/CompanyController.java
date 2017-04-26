package com.sunesoft.lemon.webapp.controller.carInfo;

import com.google.gson.Gson;
import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.CompanyService;
import com.sunesoft.lemon.deanery.car.application.criteria.CompanyCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CompanyDto;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/16.
 */
@Controller
public class CompanyController extends Layout {

    @Autowired
    CarService carService;

    @Autowired
    CompanyService companyService;

    @RequestMapping(value = "sra_c_company")
    public ModelAndView index(Model model){
        return view(layout,"carInfo/RentCompany",model);
    }

    @RequestMapping(value = "_addCompanyInfoForm")
    public ModelAndView addCompanyInfoForm(Model model,HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            CompanyDto companyDto=companyService.getCompany(Long.parseLong(id));
            model.addAttribute("company",companyDto);
        }
        return view("carInfo/_addCompanyInfoForm",model);
    }

    @RequestMapping(value = "ajax_company_query_list")
    public void queryCompanyInfo(CompanyCriteria companyCriteria,HttpServletRequest request,HttpServletResponse response){
        companyCriteria.setName(URI.deURI(companyCriteria.getName()));
        PagedResult pagedResult = companyService.getCompany(companyCriteria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    @RequestMapping(value = "ajax_deleteCompany")
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
            Boolean result = companyService.delComanyName((listid.toArray(new Long[listid.size()])));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }

    @RequestMapping(value = "ajax_add_update_company", method = RequestMethod.POST)
    public void addOrUpdateCar(CompanyDto companyDto,HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("flag",0);
        if(companyDto.getId() != null && !"".equals(companyDto.getId()))
            res.put("flag",1);
        String result = companyService.addOrUpdate(companyDto) > 0 ? "success" : "error";
        res.put("res_info",result);
        AjaxResponse.write(response, "text", new Gson().toJson(res));
    }


}
