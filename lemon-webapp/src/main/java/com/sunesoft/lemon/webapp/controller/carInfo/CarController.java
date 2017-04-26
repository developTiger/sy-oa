package com.sunesoft.lemon.webapp.controller.carInfo;

import com.google.gson.Gson;
import com.sunesoft.lemon.deanery.car.application.CarService;
import com.sunesoft.lemon.deanery.car.application.CompanyService;
import com.sunesoft.lemon.deanery.car.application.criteria.CarCriteria;
import com.sunesoft.lemon.deanery.car.application.criteria.CarReportCriteria;
import com.sunesoft.lemon.deanery.car.application.dtos.CarDto;
import com.sunesoft.lemon.deanery.car.application.dtos.CarReportDto;
import com.sunesoft.lemon.deanery.car.domain.Company;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.JsonHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
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
import java.util.*;

/**
 * 车辆信息
 */
@Controller
public class CarController extends Layout {

    @Autowired
    CarService carService;

    @Autowired
    CompanyService companyService;

    @Autowired
    DeptmentService deptmentService;

    /**
     * 车辆信息首页
     * @param model
     * @param carReportCriteria
     * @return
     */
    @RequestMapping(value = "sra_c_car")
    public ModelAndView carInfo(Model model,CarReportCriteria carReportCriteria){
        CarReportDto carReportDto = carService.carReport(carReportCriteria);
        model.addAttribute("beans",carReportDto);

        return view(layout,"carInfo/carInfo",model);
    }

    /**
     * 车辆信息新增弹窗
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "_addCarInfoForm")
    public ModelAndView addCarInfoForm(Model model,HttpServletRequest request,HttpServletResponse response){

        //新增，返回页面所属部门名称
        List<DeptmentDto> deptmentDtos=deptmentService.getByDeptsIds();

        // shanchu yuanlingdao
     for(int i=0;i<deptmentDtos.size();i++){
          if((deptmentDtos.get(i).getDeptName()).equals("院领导")){
              deptmentDtos.remove(i);
          }
     }
        model.addAttribute("deptName",deptmentDtos);

        //新增，司机名称数据
        List<Company> lists = carService.getComs();
        model.addAttribute("company",lists);

        return view("/carInfo/_addCarInfoForm", model);
    }


    /**
     * 车辆信息弹窗 提交  新增和修改是同一个方法
     * @param carDto
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_add_update_car", method = RequestMethod.POST)
    public void addOrUpdateCar(CarDto carDto,HttpServletRequest request, HttpServletResponse response) {
        String companyId = request.getParameter("companyId");
        carDto.setCompanyId(Long.parseLong(companyId));
        carDto.setCompanyName(companyService.getCompany(Long.parseLong(companyId)).getName());

        //是否新增功能的标志位，用来作提示信息用  0:新增 1：修改
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("flag",0);
        //新增 状态为空闲，值为1
        String hiddenStatus = request.getParameter("hiddenStatus");
        if(!("").equals(hiddenStatus) && hiddenStatus != null ){
            carDto.setStatus(Integer.parseInt(hiddenStatus));
        }

        //修改
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            String repairLog = request.getParameter("repairLog");
            carDto.setRepairLog(repairLog);
            res.put("flag",1);
        }
        String result = carService.addOrUpdate(carDto) > 0 ? "success" : "error";
        res.put("res_info",result);
        AjaxResponse.write(response, "text", new Gson().toJson(res));
    }

    /**
     * 车辆信息修改弹窗
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "_modifyCarInfoForm")
    public ModelAndView modifyCarInfoForm(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        CarDto carDto = new CarDto();
        if (!StringUtils.isNullOrWhiteSpace(id)) {
            carDto = carService.getByIdCar(Long.parseLong(id));
            model.addAttribute("beans",carDto);
        }

        //修改，司机名称数据
        List<Company> lists = carService.getComs();
        model.addAttribute("company",lists);

        //修改，返回页面所属部门名称
        List<DeptmentDto> deptmentDtos=deptmentService.getByDeptsIds();
        for(int i=0;i<deptmentDtos.size();i++){
            if((deptmentDtos.get(i).getDeptName()).equals("院领导")){
                deptmentDtos.remove(i);
            }
        }
        model.addAttribute("deptName",deptmentDtos);

        return view("carInfo/_modifyCarInfoForm",model);
    }




    /**
     * 页面数据显示
     * @param carCriteria
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_car_query_list")
    public void queryCarInfo(CarCriteria carCriteria,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        carCriteria.setCarType(URLDecoder.decode(carCriteria.getCarType(), "UTF-8"));//由于前端是用get方法提交且编码使用UTF-8处理
      //  String deptid=request.getParameter("controlName");
        PagedResult<CarDto> pagedResult = carService.getCars(carCriteria);

        String json = JsonHelper.toJson(pagedResult);
        AjaxResponse.write(response, json);
    }


    /**
     * 页面数据删除
     * @param request
     * @param response
     */
    @RequestMapping(value = "ajax_deleteCar")
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
            Boolean result = carService.delCar(listid.toArray(new Long[listid.size()]));//后面的是进行格式转换
            AjaxResponse.write(response, "text", result ? "success" : "error");
        }
    }

    /**
     * 车辆信息 详情弹窗
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "_carInfoForm",method = RequestMethod.POST)
    public ModelAndView deleteCarInfo(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        if(!StringUtils.isNullOrWhiteSpace(id)){
            CarDto carDto=carService.getByIdCar(Long.parseLong(id));
         //   Deptment s= carService.carReportByNo(Long.parseLong(carDto.getControlName()));
           // carDto.setControlName(s.getDeptName());
            if(carDto.getStatus() == 1){
                model.addAttribute("");
            }


            model.addAttribute("beans",carDto);
        }
        return view("carInfo/_carInfoForm",model);
    }

}
