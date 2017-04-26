package com.sunesoft.lemon.webapp.controller.car_driver_flow;

import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.DriverService;
import com.sunesoft.lemon.deanery.car.application.criteria.CarApplyCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.CarApplyCriteria2;
import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarReportDto;
import com.sunesoft.lemon.deanery.car.application.static_common.static_common;
import com.sunesoft.lemon.deanery.car.domain.CarCount;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.deanery.carWorkFlow.application.dtos.CarApplyDto;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.fr.utils.excel.ExpotExcel;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.webapp.controller.Layout;
import com.sunesoft.lemon.webapp.utils.AjaxResponse;
import com.sunesoft.lemon.webapp.utils.Helper;
import com.sunesoft.lemon.webapp.utils.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by swb on 2016/7/29.
 */
@Controller
public class DriveringListController extends Layout{
    @Autowired
    DriverService driverService;
    @Autowired
    CarService carService;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
    @RequestMapping("sra_flow_drivering")
    public ModelAndView carListInfo(Model model,HttpServletRequest request,CarReportCriteria carReportCriteria){
        List<Company> companys = driverService.getComs();
        List<Deptment> findAllDept = driverService.findAllDept();
        String bgtime=request.getParameter("bgtime");
        String edtime=request.getParameter("edtime");
        String   driverName=URI.deURI(request.getParameter("driverName"));
        String company=URI.deURI(request.getParameter("companyName"));
        model.addAttribute("companys",companys);
        model.addAttribute("findAllDept",findAllDept);
        model.addAttribute("driverName",driverName);
        model.addAttribute("company",company);
        model.addAttribute("bgtime",bgtime);
        model.addAttribute("edtime",edtime);
        return view(layout, "car_driver_flow/car_drivering_flow_info", model);
    }
    // 显示数据表
  @RequestMapping("ajax_drivering_query_list")
    public void ajax_carListQuery_list(CarApplyCriteria2 carApplyCriteria2, HttpServletRequest request, HttpServletResponse response){

      String applyerName = URI.deURI(request.getParameter("applyerName"));
      if(!StringUtils.isNullOrWhiteSpace(applyerName)){
          carApplyCriteria2.setApplyerName(applyerName);
      }
      String address = URI.deURI(request.getParameter("address"));
      if(!StringUtils.isNullOrWhiteSpace(address)){
          carApplyCriteria2.setAddress(address);
      }
      String deptName = URI.deURI(request.getParameter("deptName"));
      if(!StringUtils.isNullOrWhiteSpace(deptName)){
          carApplyCriteria2.setDeptName(deptName);
      }
      String drivername = URI.deURI(request.getParameter("driverName"));
      if(!StringUtils.isNullOrWhiteSpace(drivername)){
          carApplyCriteria2.setDriverName(drivername);
      }
      String companyName = URI.deURI(request.getParameter("companyName"));
      if(!StringUtils.isNullOrWhiteSpace(companyName)){
          carApplyCriteria2.setCompanyName(companyName);
      }

      String beginDate = request.getParameter("beginDate");
      String endDate = request.getParameter("endDate");
      if(!StringUtils.isNullOrWhiteSpace(beginDate)){
        carApplyCriteria2.setBeginDate(beginDate);
      }
      if(!StringUtils.isNullOrWhiteSpace(endDate)){
          carApplyCriteria2.setEndDate(endDate);
      }
      String evaluate = URI.deURI(request.getParameter("evaluate"));
      if(!StringUtils.isNullOrWhiteSpace(evaluate)){
          carApplyCriteria2.setEvaluate(evaluate);
      }
      PagedResult list =driverService.CarApplyInfo3(carApplyCriteria2);
      String json= JsonHelper.toJson(list);
      AjaxResponse.write(response,json);
    }
//带条件跳转
    @RequestMapping("ajax_drivering_query_list_1")
    public ModelAndView ajax_carListQuery_list_1(CarApplyCriteria2 carApplyCriteria2,Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        List<Company> companys = driverService.getComs();
        List<Deptment> findAllDept = driverService.findAllDept();
        model.addAttribute("companys",companys);
        model.addAttribute("findAllDept",findAllDept);
        String drivername= null;
        drivername = URLDecoder.decode(carApplyCriteria2.getDriverName(),"UTF-8");
        model.addAttribute("driverName",drivername);
        model.addAttribute("deptId",carApplyCriteria2.getDeptId());
        model.addAttribute("companyId",carApplyCriteria2.getCompanyId());
        return view(layout,"car_driver_flow/car_flow_info",model);
    }


    @RequestMapping(value = "_detailCarflow")
    public ModelAndView detailProjectResultInfo(Model model,HttpServletRequest request){
        String id = URI.deURI(request.getParameter("id"));
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            CarApplyDto dto=driverService.getDtoById(id);
          //  Company company=driverService.getCompanyById(dto);
            model.addAttribute("beans", dto);
        //    model.addAttribute("company", company);
        }
        return view("car_driver_flow/_detailCarflow",model);
    }

    /**
     * 导出数据
     */
    @ResponseBody
    @RequestMapping("ajax_driver_flowTpl_down")
    public void download(CarApplyCriteria2 carApplyCriteria2,Model model, HttpServletRequest request, HttpServletResponse response) {
        PagedResult list =driverService.CarApplyInfo3(carApplyCriteria2);
        List<HashMap> item=new ArrayList<HashMap>();
        List lists= new ArrayList();
        item= list.getItems();
        String id="";
        for(int i=0;i<item.size();i++){
            id= item.get(i).get("ID").toString();
            //申请人
            String applyer=item.get(i).get("APPLYER_NAME").toString();
            //前往地点
            String place=item.get(i).get("ADDRESS").toString();
            //申请人单位
            String sqrdw=item.get(i).get("DEPT_NAME").toString();
            //司机名称
            String sjmc=item.get(i).get("DRIVER_NAME").toString();
            //调度中心
          /*  String ddzx=item.get(i).get("NAME").toString();*/
            CarApplyDto dto = driverService.getDtoById(id);
            Company company = driverService.getCompanyById(dto);

            //预计出发时间
            String yjcfsj=dto.getPredictGoStartDate();
            //预计到达时间
            String yjddsj=dto.getPredictGoArriveDate();
            //实际出发时间
            String sjcfsj=dto.getGoStartDate();
            //实际出发到达时间
            String sjcfddsj=dto.getGoArriveDate();
            //实际去程通车人员
            String sjcctcry=dto.getGoRidePeoples();
            //实际返程出发时间
            String sjfccfsj=dto.getReturnStartDate();
           //实际返程到达时间
            String sjfcddsj=dto.getReturnArriveDate();
           //实际返程通车人员
            String sjfctcry=dto.getReturnRidePeoples();
            //服务评价
            String fwpj=item.get(i).get("EVALUATE").toString();
            CarCount carCount=new CarCount();
            carCount.setApplyer(applyer);
            carCount.setPlace(place);
            carCount.setSqrdw(sqrdw);
            carCount.setSjmc(sjmc);
            carCount.setYjchsj(yjcfsj);
            carCount.setYjdasj(yjddsj);
            carCount.setSjcfsj(sjcfsj);
            carCount.setSjdasj(sjcfddsj);
            carCount.setSjcccry(sjcctcry);
            carCount.setSjfccfsj(sjfccfsj);
            carCount.setSjfcddsj(sjfcddsj);
            carCount.setSjfctcry(sjfctcry);
            carCount.setFwpj(fwpj);
            lists.add(carCount);
        }
        ExpotExcel<DeptAppraiseDetailDto> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"申请人", "前往地点", "申请人单位", "司机名称", "预计出发时间", "预计到达时间","实际出发时间",
                                       "实际出发到达时间","实际去程通车人员","实际返程出发时间","实际返程到达时间","实际返程通车人员",
                                       "服务评价"};
        expotExcel.doExportExcel("司机出车评价统计", header, lists, "yyyy-MM-dd", response);
    }
}
