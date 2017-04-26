package com.sunesoft.lemon.webapp.controller.productionDate;


import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.productionData.ProductionDate;
import com.sunesoft.lemon.deanery.productionData.ProductionDateCria;
import com.sunesoft.lemon.deanery.productionData.ProductionDateDto1;
import com.sunesoft.lemon.deanery.productionData.ProductionDateService;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto1;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.function.UserSession;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2016/10/9.
 */
@Controller
public class ProductionDateController extends Layout {

    @Autowired
    ProductionDateService productionDateService;
    @RequestMapping(value = " sra_date_product")
    public ModelAndView productionInfo(Model model){

        return  view(layout,"productionDate/ProductionDateInfo",model);
    }
    //表格数据
   @RequestMapping(value = "ajax_productionDate_list", method = RequestMethod.GET)
    public void getDriver(HttpServletRequest request, HttpServletResponse response,ProductionDateCria productionDateCria,Model model) {
         String YN = request.getParameter("yuanneiwai");
        /* String YW = request.getParameter("yuanneiwai");*/
         String YPPH = request.getParameter("YPPH");
         String WTDW = request.getParameter("WTDW");
         String WTR = request.getParameter("WTR");
         String beginTime = request.getParameter("beginTime");
         String endTime = request.getParameter("endTime");
         String beginTime1 = request.getParameter("beginTime1");
         String endTime1 = request.getParameter("endTime1");
         productionDateCria.setYN(YN);
         productionDateCria.setYW(YN);
         productionDateCria.setYPPH(YPPH);
         productionDateCria.setWTDW(WTDW);
         productionDateCria.setWTR(WTR);
         productionDateCria.setBegintime(beginTime);
         productionDateCria.setBegintime1(beginTime1);
         productionDateCria.setEndtime(endTime);
         productionDateCria.setEndtime1(endTime1);
        PagedResult<ProductionDate> pagedResult=productionDateService.getByProductionDate(productionDateCria);
        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }

    //展示详情页面
    @RequestMapping(value = "_detailProductionDate")
    public ModelAndView detailInfoForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ProductionDate productionDate=productionDateService.getDateById(Long.parseLong(id));
        model.addAttribute("beans",productionDate);
        return view(layout,"/productionDate/_detailProductionDate", model);
    }
  /**
     * 导出数据
     */
    @ResponseBody
    @RequestMapping("ajax_syy_productDate_down")
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) {
        List list = new ArrayList();
        List listid = new ArrayList();
        String ids = request.getParameter("formNos");
        String[] pids = ids.split(",");
        for (String id : pids) {
            listid.add(Long.parseLong(id));
        }
            List<ProductionDateDto1> productionDateDto1 = productionDateService.downByOrderId(true);
        for(int i=0;i<productionDateDto1.size();i++) {
            list.add(productionDateDto1.get(i));
        }
        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"样品批号", "井号", "报出日期", "检测项目", "子项目", "类别", "参数名称", "前期工序", "项目组名称", "地区",
                "送样日期", "委托单位","委托人","结算日期","样品数量","计划数量","加工数量","原始单价","单价","金额","实收金额","核算岗","院内","院外","实验中心","报告发送时间",
                "报告份数","备注","单位属性","课题名称","任务名称","课题负责人","产值说明","手工录入","二级单位","结算状态","结算标识码","基层审核码","委托单位名称","委托单位编码","委托部门编码",
                "井号编码","科研项目编码","二级单位编码"};
        expotExcel.doExportExcel("AH124", header, list, "yyyy-MM-dd", response);

    }
}
