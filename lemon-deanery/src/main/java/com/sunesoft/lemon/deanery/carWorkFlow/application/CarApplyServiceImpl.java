package com.sunesoft.lemon.deanery.carWorkFlow.application;

//import com.sun.javaws.Main;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.*;
import com.sunesoft.lemon.deanery.carWorkFlow.application.dtos.CarApplyDto;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApplyRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.dept.DeptmentRepository;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.StreamHandler;

/**
 * Created by wangwj on 2016/7/26 0026.
 */
@Service(value = "carApplyService")
public class CarApplyServiceImpl extends FormBase2<CarApply,CarApplyDto> implements CarApplyService {
    @Autowired
    private CarApplyRepository carApplyRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DeptmentRepository deptmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CommDriverRepository commDriverRepository;



    @Override
    protected CommonResult save(CarApply carApply) {
        Long id = carApplyRepository.save(carApply);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    protected CommonResult update(CarApply carApply) {
        CarApply carApply1 = carApplyRepository.get(carApply.getId());
        /*carApply1.setLastUpdateTime(new Date());
        carApply1.setLeaveType(formLeave.getLeaveType());
        carApply1.setReason(formLeave.getReason());
        carApply1.setFromTime(formLeave.getFromTime());
        carApply1.setToTime(formLeave.getToTime());
        carApply1.setTarget(formLeave.getTarget());
        carApply1.setCountTime(formLeave.getCountTime());*/
        if(carApply1 == null){
            return ResultFactory.commonError("更新失败");
        }
        carApplyRepository.save(carApply);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CarApply ConvetDto(CarApplyDto Dto) {
         CarApply carApply =  DeaneryUtil.converDtoCarApply(Dto);
         if ( null != Dto.getGoStartDate()){
             carApply.setGoStartTime(DateHelper.parse(Dto.getGoStartDate()));
         }
         if (null != Dto.getGoArriveDate()){
             carApply.setGoArriveTime(DateHelper.parse(Dto.getGoArriveDate()));
         }
         if (null != Dto.getReturnStartDate()){
             carApply.setGoArriveTime(DateHelper.parse(Dto.getReturnStartDate()));
         }
         if (null != Dto.getReturnArriveDate()){
             carApply.setGoArriveTime(DateHelper.parse(Dto.getReturnArriveDate()));
         }
        if (null != Dto.getPredictGoStartDate()){
            carApply.setPredictGoStartTime(DateHelper.parse(Dto.getPredictGoStartDate()));
        }
        if (null != Dto.getPredictGoArriveDate()){
            carApply.setPredictGoArriveTime(DateHelper.parse(Dto.getPredictGoArriveDate()));
        }
         return carApply;
    }

    @Override
    protected String getSummery(CarApply carApply) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {

        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        //FormLeave formLeave = this.getByFormNo(formNo);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CarApply getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(CarApply.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo",formNo));

        return (CarApply)criterion.uniqueResult();
    }


    /**
     * 根据ID查询流程
     * @param formNo
     * @return
     */
    @Override
    public CarApplyDto getFormByFormNo(Long formNo){
        CarApply carApply = null;
        CarApplyDto carApplyDto = null;
        Car car = null;
        Driver driver = null;
        Deptment deptment = null;
        Employee employee = null;
        Criteria criteria = getSession().createCriteria(CarApply.class);
        criteria.add(Restrictions.eq("formNo",formNo));
        //获取流程集合
        List<CarApply> carApply_list =  criteria.list();

        //车的集合
        List<Car> car_list = new ArrayList<Car>();

        //司机的集合
        List<Driver> driver_list = new ArrayList<Driver>();

        //常用司机集合
        List<CommonDriver> commondriver_list = new ArrayList<CommonDriver>();

        //获取单条流程记录
        if (carApply_list != null && carApply_list.size() > 0 ){
            carApply = carApply_list.get(0);
            carApplyDto = DeaneryUtil.converCarApplyDto(carApply);
            carApplyDto.setPredictGoStartDate(DateHelper.formatDate(carApply.getPredictGoStartTime(),"yyyy-MM-dd HH:mm:ss"));
            carApplyDto.setPredictGoArriveDate(DateHelper.formatDate(carApply.getPredictGoArriveTime(), "yyyy-MM-dd HH:mm:ss"));

            carApplyDto.setGoStartDate(DateHelper.formatDate(carApply.getGoStartTime(),"yyyy-MM-dd HH:mm:ss"));
            carApplyDto.setGoArriveDate(DateHelper.formatDate(carApply.getGoArriveTime(), "yyyy-MM-dd HH:mm:ss"));

            carApplyDto.setReturnStartDate(DateHelper.formatDate(carApply.getReturnStartTime(),"yyyy-MM-dd HH:mm:ss"));
            carApplyDto.setReturnArriveDate(DateHelper.formatDate(carApply.getReturnArriveTime(), "yyyy-MM-dd HH:mm:ss"));
            //如果车ID为空，查询车
            if(null != carApply.getCarId()){
                car = carRepository.get(carApply.getCarId());
                carApplyDto.setCarNo(car.getCarNo());
                carApplyDto.setCarId(car.getId());
            }

            //筛选出所有符合条件的车
            car_list = carRepository.getAllcar();
            if ( null != car_list && car_list.size() > 0 ){
                carApplyDto.setCars(car_list);
            }

            //如果司机ID为空，查询司机
            if(null != carApply.getDriverId()){
                driver = driverRepository.get(carApply.getDriverId());
                carApplyDto.setDriverName(driver.getDriverName());
                carApplyDto.setDriverId(driver.getId());
            }

            //筛选出符合条件的司机
            driver_list = driverRepository.getAlldriver();
            if ( null != driver_list && driver_list.size() > 0 ){
                carApplyDto.setDrivers(driver_list);
            }

            /*List<CommonDriver> commondriver =
                commDriverRepository.getAllByIsActive(car_list,driver_list);*/
            

        //如果部门不为空，加载
        if(null != carApply.getDeptId()) {
            deptment = deptmentRepository.get(carApply.getDeptId());
            carApplyDto.setDeptName(deptment.getDeptName());
            carApplyDto.setDeptId(deptment.getId());
        }

        //如果申请人不为空，加载
        if(null != carApply.getApplyer()){
            employee = employeeRepository.get(carApply.getApplyer());
            carApplyDto.setApplyerName(employee.getName());
            carApplyDto.setApplyer(employee.getId());
        }
    }

        return carApplyDto;
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
        CommonResult cr = null;
        Integer clStep = Integer.parseInt(param.get("clStep").toString());
        Boolean isComplete = Boolean.valueOf(param.get("isStepComplete").toString());
        Integer appValue = dto.getAppValue();
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        /**
         * 第一步同意并保存
         */
        if( clStep.equals(1) && (!isComplete) && appValue.equals(AppValue.Y.ordinal()) ){
            cr = saveCarApplyData_step2(param);
        }
        if( clStep.equals(2) && (!isComplete) && appValue.equals(AppValue.Y.ordinal()) ){

            cr = saveCarApplyData_step3(param);
        }
        if (appValue.equals(AppValue.N.ordinal())){
            return super.doApprove(dto,param);
        }
        if (cr!=null && !cr.getIsSuccess()){
//            return ResultFactory.commonError("保存数据出错");
            return cr;
        }

        return super.doApprove(dto,param);
    }

    /**
     * 保存数据，审批第二步
     * @return
     */
    private CommonResult saveCarApplyData_step2(Map<String, Object> params){
        String carid = (String) params.get("carId");
        String carNo = (String) params.get("carNo");
        String driverid = (String) params.get("driverId");
        String formNo = (String) params.get("formNo");
        CarApply carApply = carApplyRepository.get(Long.valueOf(formNo),true);
        try {
            Car car = null;
            Driver driver = null;
            //车的状态更新
            if (StringUtils.isEmpty(carid)) {
                return ResultFactory.commonError("车辆未选择");
            }
            car = carRepository.get(Long.parseLong(carid), true);
            //如果车不为空，那么更改车的状态为2出车状态;
            if (car == null) {
                return ResultFactory.commonError("选择车辆不符合要求");
            }
            car.setStatus(2);

            //司机状态更新
            if (StringUtils.isEmpty(driverid)) {
                return ResultFactory.commonError("司机未选择");
            }
            driver = driverRepository.get(Long.parseLong(driverid), true);
            //如果司机不为空，那么更改司机的状态为2出车状态;
            if (driver == null) {
                return ResultFactory.commonError("选择司机不符合要求");
            }
            driver.setStatus(2);

            //更新车和司机的状态
            Long cl = carRepository.save(car);
            Long dl = driverRepository.save(driver);
            if (cl.equals(0)) {
                return ResultFactory.commonError("更新车状态失败");
            }
            if (dl.equals(0)) {
                return ResultFactory.commonError("更新司机状态失败");
            }


            if (carApply == null) {
                return ResultFactory.commonError("数据不存在，不能更新");
            }
            carApply.setCarId(car.getId());
            carApply.setCarNo(carNo);
            carApply.setDriverId(driver.getId());
            carApply.setFlow_status(1);
            carApplyRepository.save(carApply);

        } catch (Exception e) {
            carApply.setFlow_status(2);
            carApplyRepository.save(carApply);
            e.printStackTrace();
            return ResultFactory.commonError("更新数据库出错");
        }
        return ResultFactory.commonSuccess();
    }


    /**
     * 保存数据，审批第步三步
     * @param param
     * @return
     */
    private CommonResult saveCarApplyData_step3(Map<String, Object> param){
        CarApply carApply = new CarApply();
        try {
////            里程数
//            String mileage = (String) param.get("mileage");
////            费用
//            String cost = (String) param.get("cost");
//            用车评价
            String formNo = (String) param.get("formNo");
//            返程乘坐人员
            String returnRidePeoples=(String) param.get("returnRidePeoples");
//            实际前往时间
            String goStartTime=(String) param.get("goStartTime");
//            实际到达时间
            String goArriveTime=(String) param.get("goArriveTime");
//            返程出发时间
            String returnStartDate=(String) param.get("returnStartDate");
//            返程到达时间
            String returnArriveDate=(String) param.get("returnArriveDate");
//            用车评价
            String evaluate=(String) param.get("evaluate");
//            用车评价备注
            String returnRemark=(String) param.get("returnRemark");

            if(DateHelper.parse(goStartTime).getTime()>DateHelper.parse(goArriveTime).getTime()){
                return ResultFactory.commonError("实际出发时间不能大于实际到达时间");
            }
            if(DateHelper.parse(returnStartDate).getTime()>DateHelper.parse(returnArriveDate).getTime()){
                return ResultFactory.commonError("返程出发时间不能大于返程到达时间");
            }
            carApply = carApplyRepository.get(Long.valueOf(formNo),true);
//            carApply.setMileage(Double.parseDouble(mileage));
//            carApply.setCost(Double.parseDouble(cost));
            carApply.setFlow_status(3);
            carApply.setReturnRidePeoples(returnRidePeoples);
            carApply.setGoStartTime(DateHelper.parse(goStartTime));
            carApply.setGoArriveTime(DateHelper.parse(goArriveTime));
            carApply.setReturnStartTime(DateHelper.parse(returnStartDate));
            carApply.setReturnArriveTime(DateHelper.parse(returnArriveDate));
            carApply.setEvaluate(evaluate);
            carApply.setReturnRemark(returnRemark);
            carApplyRepository.save(carApply);
            if ( null != carApply.getCarId()) {
                Car car = carRepository.get(carApply.getCarId());
                car.setStatus(1);
                carRepository.save(car);
            }
            if (null != carApply.getDriverId()) {
                Driver driver = driverRepository.get(carApply.getDriverId());
                driver.setStatus(1);
                driverRepository.save(driver);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultFactory.commonError("保存数据失败");
        }

        return ResultFactory.commonSuccess();
    }

    /*
     * 获取processid 这个是主流程ID;
     * @param name
     * @return
    @Override
    public String getProcessId(String name) {
        StringBuffer sb = new StringBuffer();
        sb.append("select pro.id from syy_oa_wf_process pro " +
                "where pro.name = '" + name + "' and pro.state = 1 ");
        Query query =
                this.getSession().createSQLQuery(sb.toString()).
                        setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        Map<String,String> map = new HashMap<String,String>();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        String str = "";
        try {
            list = query.list();
            if (null == list || list.size() == 0) {
                return "";
            }
            map = list.get(0);
            str = map.get("ID");
        } catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 保存表达数据
     * @param dto
     * @return
     */
    @Override
    public CommonResult save_carapply(CarApplyDto dto) {
        CarApply carApply = DeaneryUtil.converDtoCarApply(dto);
        Long id = carApplyRepository.save(carApply);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    public CarApply getByFormNo(Long formNo, Boolean isActive) {
        return carApplyRepository.get(formNo,isActive);
    }
}
