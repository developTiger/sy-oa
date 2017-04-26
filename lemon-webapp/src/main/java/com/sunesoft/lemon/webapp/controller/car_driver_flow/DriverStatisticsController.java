package com.sunesoft.lemon.webapp.controller.car_driver_flow;

import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.car.application.criteria.DriverStatisticsCriteria;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.car.domain.DriverCount;
import com.sunesoft.lemon.deanery.productionData.ProductionDateDto1;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pxj on 2016/9/14.
 */
@Controller
public class DriverStatisticsController extends Layout {
    @Autowired
    DriverService driverService;

    /**
     * 司机统计筛选条件
     * @param model
     * @return
     */
    @RequestMapping(value = "sra_flow_driver_statistics")
    public ModelAndView sra_flow_driver_statistics(Model model){
        List<Company> companys = driverService.getComs();
        model.addAttribute("companys",companys);
        return view(layout,"car_driver_flow/car_driver_statistics",model);
    }

    @RequestMapping(value = "ajax_driver_statistics_query_list")
    public void ajax_driver_statistics_query_list(DriverStatisticsCriteria driverStatisticsCriteria, HttpServletRequest request, HttpServletResponse response){
        String drivername = URI.deURI(request.getParameter("driverName"));
        if(!StringUtils.isNullOrWhiteSpace(drivername)){
            driverStatisticsCriteria.setDriverName(drivername);
        }
        String beginTime = request.getParameter("beginDate");
        String endTime = request.getParameter("endDate");
        if(!StringUtils.isNullOrWhiteSpace(beginTime)){
            driverStatisticsCriteria.setStartTime(beginTime);
        }
        if(!StringUtils.isNullOrWhiteSpace(endTime)){
            driverStatisticsCriteria.setEndTime(endTime);
        }

        ListResult<Map<String,Object>> list=new ListResult<Map<String,Object>>(driverService.driverStatistics(driverStatisticsCriteria));

        for (int i=0;i<list.getItems().size();i++){
            list.getItems().get(i).put("bgTime",beginTime);
            list.getItems().get(i).put("edTime",endTime);
        }
       /* 司机统计list分页 预防统计做分页*/
/*        PagedResult<Map<String,Object>> pagedResult=driverService.driverStatisticsPage(driverStatisticsCriteria);
        for (int i=0;i<pagedResult.getItems().size();i++){
            pagedResult.getItems().get(i).put("bgTime",beginTime);
            pagedResult.getItems().get(i).put("edTime",endTime);
        }
        String json=JsonHelper.toJson(pagedResult);*/
        String json= JsonHelper.toJson(list);
        AjaxResponse.write(response,json);
    }


    /**
     * 导出数据
     */
    @ResponseBody
    @RequestMapping("ajax_driver_statistics_down")
    public void download(DriverStatisticsCriteria driverStatisticsCriteria,Model model, HttpServletRequest request, HttpServletResponse response) {
        ListResult<Map<String,Object>> list=new ListResult<Map<String,Object>>(driverService.driverStatistics(driverStatisticsCriteria));
        List lists = new ArrayList();

        for(int i=0;i<list.getItems().size();i++){
            DriverCount driverCount=new DriverCount();
          //司机名
           Object DRIVER_NAME= list.getItems().get(i).get("DRIVER_NAME")  ;
          //出车次数
           Object ccno= list.getItems().get(i).get("CCNO");
          //优秀
           Object ano= list.getItems().get(i).get("ANO");
           //良好
           Object bno= list.getItems().get(i).get("BNO");
           //一般
           Object cno= list.getItems().get(i).get("CNO");
           //差
            Object dno= list.getItems().get(i).get("DNO");

            driverCount.setDriveName(DRIVER_NAME.toString());
            driverCount.setCcno(Integer.parseInt(ccno.toString()));
            driverCount.setAno(Integer.parseInt(ano.toString()));
            driverCount.setBno(Integer.parseInt(bno.toString()));
            driverCount.setCno(Integer.parseInt(cno.toString()));
            driverCount.setDno(Integer.parseInt(dno.toString()));
            lists.add(driverCount);
        }
        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"司机姓名", "出车次数", "优秀", "良好", "一般", "差"};
       expotExcel.doExportExcel("司机出车评价统计", header, lists, "yyyy-MM-dd", response);
    }
}
