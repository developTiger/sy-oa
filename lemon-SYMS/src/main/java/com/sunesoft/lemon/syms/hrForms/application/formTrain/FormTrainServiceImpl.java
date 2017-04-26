package com.sunesoft.lemon.syms.hrForms.application.formTrain;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.AttendanceEnsureService;
import com.sunesoft.lemon.syms.eHr.application.dtos.AttendanceOperateDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpEasyDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceRepository;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDownloadDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainEmpDto;
import com.sunesoft.lemon.syms.hrForms.application.criteria.TrainEmpCriteria;
import com.sunesoft.lemon.syms.hrForms.application.formevection.FormEvectionSelector;
import com.sunesoft.lemon.syms.hrForms.domain.*;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangkefan on 2016/7/22.
 */
@Service("formTrainService")
public class FormTrainServiceImpl extends FormBase2<FormTrain,FormTrainDto> implements  FormTrainService {
    @Autowired
    FormTrainRepository formTrainRepository;

    @Autowired
    FormTrainChooseEmpRepository formTrainChooseEmpRepository;


    @Override
    protected CommonResult save(FormTrain formTrain) {
        formTrainRepository.save(formTrain);
        return ResultFactory.commonSuccess();
    }



    @Override
    protected CommonResult update(FormTrain formTrain) {
        FormTrain train = formTrainRepository.get(formTrain.getDeptId());
        train.setTrainName(formTrain.getTrainName());
        train.setTrainBeginDate(formTrain.getTrainBeginDate());
        train.setTrainEndDate(formTrain.getTrainEndDate());
        train.setTrainPlace(formTrain.getTrainPlace());
        train.setTrainContent(formTrain.getTrainContent());
        train.setEmps(formTrain.getEmps());
        formTrainRepository.save(train);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormTrain ConvetDto(FormTrainDto dto) {
        FormTrain formTrain = new FormTrain();
        formTrain = DtoFactory.convert(dto,formTrain);
        if(dto.getEmpids()!=null  && dto.getEmpids().size()>0){
            formTrain.setEmps(employeeList(dto.getEmpids()));
        }
        return formTrain;
    }

    private List<Employee> employeeList(List<Long> ids){
        Criteria criteria = getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.in("id",ids));
        return criteria.list();
    }

    @Override
    protected String getSummery(FormTrain formTrain) {
        return formTrain.getTrainName();
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {

        subFormOperate(formNo,false,"系统自动审核",3);
        return ResultFactory.commonSuccess();
    }

    @Override
    public FormTrain getByFormNo(Long formNo) {
        return formTrainRepository.getByFormNo(formNo);
    }


    @Override
    public FormTrainChooseEmp getBySubFormNo(Long formNo) {
        return formTrainChooseEmpRepository.getByFormNo(formNo);
    }

    @Override
    protected <V extends BaseFormEntity> CommonResult subFormSave(V v) {
        FormTrainChooseEmp f=(FormTrainChooseEmp)v;
        formTrainChooseEmpRepository.save(f);
        return ResultFactory.commonSuccess();
    }
    private  EmpEasyDto Convert(Employee employee){
        EmpEasyDto easyDto = new EmpEasyDto();

        if(employee.getId()!=null){
            easyDto.setId(employee.getId());
        }
        if(!StringUtils.isNullOrWhiteSpace(employee.getDept().getDeptName())){
            easyDto.setTrainDeptName(employee.getDept().getDeptName());
        }
        if(employee.getDept().getId()!=null){
            easyDto.setDeptId(employee.getDept().getId());
        }
        if(!StringUtils.isNullOrWhiteSpace(employee.getUserNo())){
            easyDto.setUserNo(employee.getUserNo());
        }
        if(!StringUtils.isNullOrWhiteSpace(employee.getName())){
            easyDto.setName(employee.getName());
        }
        if(!StringUtils.isNullOrWhiteSpace(employee.getLoginName())){
            easyDto.setLoginName(employee.getLoginName());
        }
        if(!StringUtils.isNullOrWhiteSpace(employee.getPosition())){
            easyDto.setPosition(employee.getPosition());
        }

        return easyDto;
    }

    @Override
    public FormTrainDto getFormByFormNo(Long formNo) {
        FormTrain formTrain = this.getByFormNo(formNo);
        FormTrainDto formTrainDto = DtoFactory.convert(formTrain,new FormTrainDto());
        List<EmpEasyDto> easyDtos = new ArrayList<>();
        List<Employee> lists = formTrain.getEmps();
        if(lists!=null && lists.size()>0){
            for(Employee employee : lists){

                EmpEasyDto easyDto = this.Convert(employee);
                easyDtos.add(easyDto);
            }
            formTrainDto.setEmpLists(easyDtos);
        }
        return formTrainDto;
    }

    @Override
    public CommonResult addEmpId(Long formNo, List<Long> empIds) {
        //根据表单号获取对象
        FormTrain formTrain = this.getByFormNo(formNo);
        //判断是否获取人员信息
        List<Employee> employees =  formTrain.getEmps();
        List<Employee> listE =  employeeList(empIds);
        Boolean isExist=false;
        for(Employee e : listE){

            for(Employee f : employees){

                if(e.getId().equals(f.getId())){
                    isExist=true;
//                        break;
                }
            }
            if(!isExist) {
                employees.add(e); //根据人员ID获取对象

            }
        }
        formTrain.setEmps(employees);

//        formTrain.setEmps(employeeList(empIds));
        formTrainRepository.save(formTrain);
        return ResultFactory.commonSuccess();

    }

    @Override
    public CommonResult deleteEmpById(Long formNo, Long id) {
        FormTrain formTrain = this.getByFormNo(formNo);
        List<Employee> emps = formTrain.getEmps();
        if(emps!=null && emps.size()>0) {
            for (Employee employee : emps) {
                if (employee.getId().equals(id)) {
                    formTrain.getEmps().remove(employee);
                    break;
                }
            }
        }
        formTrainRepository.save(formTrain);
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<FormTrainDto> getFormTrainByEmpID(Long empId) {
        FormStatus[] formStatus = new FormStatus[]{FormStatus.AP,FormStatus.UA};
        //获取已经评价过的培训
        Criteria criteria = getSession().createCriteria(FormTrainEffect.class);
        criteria.add(Restrictions.eq("isActive", true));
       criteria.add(Restrictions.eq("applyer",empId));
        criteria.add(Restrictions.in("formStatus", formStatus));

        List<FormTrainEffect> formTrainEffects = criteria.list();
        List<String> formTrainNames = new ArrayList<>();
        if(formTrainEffects!=null&& formTrainEffects.size()>0){
            for(FormTrainEffect effect :formTrainEffects){
                formTrainNames.add(effect.getTrainName());
            }

        }
        List<FormTrainDto> formTrainDtos = new ArrayList<>();
        Criteria criteria2 = getSession().createCriteria(FormTrain.class);
        criteria2.createAlias("emps","emps");
        criteria2.add(Restrictions.eq("emps.id", empId));
        criteria2.add(Restrictions.eq("formStatus",FormStatus.AP));//查询状态为全部审核通过



        if(formTrainNames.size()>0) {
            criteria2.add(Restrictions.not(Restrictions.in("trainName", formTrainNames)));
        }
        List<FormTrain> list= criteria2.list();

        for(FormTrain form: list){
            FormTrainDto dto =  DtoFactory.convert(form,new FormTrainDto());
            formTrainDtos.add(dto);
        }
        return formTrainDtos;
    }

    @Override
    public List<FormTrainDownloadDto> getTrainDownloadDto(FormTrainDto evectionDto) {

        List<EmpEasyDto> list=evectionDto.getEmpLists();
        List<FormTrainDownloadDto> downloadDtos=new ArrayList<>();
        if(list.size()>0) {
            for (EmpEasyDto empEasyDto : list) {
                FormTrainDownloadDto downloadDto=new FormTrainDownloadDto();
                downloadDto.setDeptName(evectionDto.getDeptName());
                if(!StringUtils.isNullOrWhiteSpace(empEasyDto.getUserNo())) {
                    downloadDto.setUserNo(empEasyDto.getUserNo());
                }else{
                    downloadDto.setUserNo("");
                }
                if(!StringUtils.isNullOrWhiteSpace(empEasyDto.getName())) {
                    downloadDto.setName(empEasyDto.getName());
                }else{
                    downloadDto.setName("");
                }
                downloadDto.setTrainBeginTime(DateHelper.formatDate(evectionDto.getTrainBeginDate(), "yyyy-MM-dd"));
                downloadDto.setTrainEndTime(DateHelper.formatDate(evectionDto.getTrainEndDate(), "yyyy-MM-dd"));
                downloadDto.setTrainName(evectionDto.getTrainName());
                downloadDto.setTrainContent(evectionDto.getTrainContent());
                downloadDto.setDay(String.valueOf(Integer.parseInt(evectionDto.getStudyTime())/8));
                downloadDto.setStudyTime(evectionDto.getStudyTime());
                downloadDto.setMainCompany(evectionDto.getMainCompany());
                downloadDto.setDidCompany(evectionDto.getDidCompany());
                if("0".equals(evectionDto.getTrainCategory()))
                    downloadDto.setTrainCategory("取证复审");
                else if("1".equals(evectionDto.getTrainCategory()))
                    downloadDto.setTrainCategory("取证");
                else if("2".equals(evectionDto.getTrainCategory()))
                    downloadDto.setTrainCategory("其他");
                else
                    downloadDto.setTrainCategory("");
                downloadDto.setTrainPlace(evectionDto.getTrainPlace());
                if("0".equals(evectionDto.getPlan()))
                    downloadDto.setPlan("计划外");
                else if("1".equals(evectionDto.getPlan()))
                    downloadDto.setPlan("计划内");
                else
                    downloadDto.setPlan("");
                downloadDtos.add(downloadDto);
            }
        }
        return downloadDtos;
    }

    @Override
    public List<FormTrainEmpDto> getAllTrainEmp(TrainEmpCriteria criteria) {

//        Criteria criteria = getSession().createCriteria(FormTrain.class);
//        criteria.add(Restrictions.eq("isActive", true));
//        criteria.add(Restrictions.eq("trainName",empCriteria.getTrainName()));
//        criteria.add(Restrictions.ge("trainBeginDate", empCriteria.getBeginDate()));
//        criteria.add(Restrictions.le("trainEndDate", empCriteria.getEndDate()));
//        criteria.add(Restrictions.le("trainEndDate", empCriteria.getEndDate()));

        List<FormTrain> list=formTrainRepository.getAll();
        List<FormTrainEmpDto> empDtos=new ArrayList<>();
        for (FormTrain train:list){
            List<Employee> employees=train.getEmps();
            for(Employee employee:employees) {
                if (!StringUtils.isNullOrWhiteSpace(criteria.getName())) {
                    if (!criteria.getName().equals(employee.getName())){
                        continue;
                    }
                }
                if (criteria.getBeginDate()!=null) {
                    if(criteria.getBeginDate().getTime()>train.getTrainBeginDate().getTime()){
                        continue;
                    };
                }
                if (criteria.getEndDate()!=null) {
                    if(criteria.getEndDate().getTime()<train.getTrainBeginDate().getTime()){
                        continue;
                    };
                }
                if (!StringUtils.isNullOrWhiteSpace(criteria.getTrainName())) {
                    if (!criteria.getTrainName().equals(train.getTrainName())){
                        continue;
                    }
                }
                FormTrainEmpDto empDto=new FormTrainEmpDto();
                empDto.setName(employee.getName());
                empDto.setDeptName(train.getDeptName());
                empDto.setUserNo(employee.getUserNo());
                empDto.setTrainBeginTime(DateHelper.formatDate(train.getTrainBeginDate(),"yyyy-MM-dd"));
                empDto.setTrainEndTime(DateHelper.formatDate(train.getTrainEndDate(),"yyyy-MM-dd"));
                empDto.setTrainName(train.getTrainName());
                empDto.setTrainContent(train.getTrainContent());
                if(!StringUtils.isNullOrWhiteSpace(train.getStudyTime()))
                    empDto.setDay(String.valueOf(Integer.valueOf(train.getStudyTime())/8));
                else
                    empDto.setDay("");
                empDto.setStudyTime(train.getStudyTime());
                empDto.setMainCompany(train.getMainCompany());
                empDto.setDidCompany(train.getDidCompany());
                empDto.setTrainCategory(train.getTrainCategory());
                empDto.setTrainPlace(train.getTrainPlace());
                empDto.setPlan(train.getPlan());
                empDtos.add(empDto);
            }
        }
        return empDtos;
    }

    @Override
    public List<FormTrainDownloadDto> getTrainDownloadDto(TrainEmpCriteria criteria) {
        List<FormTrainEmpDto> list=this.getAllTrainEmp(criteria);
        List<FormTrainDownloadDto> downloadDtos=new ArrayList<>();
        for(FormTrainEmpDto trainEmpDto:list){
            FormTrainDownloadDto downloadDto=new FormTrainDownloadDto();
            downloadDto.setDeptName(trainEmpDto.getDeptName());
            downloadDto.setUserNo(trainEmpDto.getUserNo());
            downloadDto.setName(trainEmpDto.getName());
            //培训开始时间
            downloadDto.setTrainBeginTime(trainEmpDto.getTrainBeginTime());
            downloadDto.setTrainEndTime(trainEmpDto.getTrainEndTime());
            downloadDto.setTrainName(trainEmpDto.getTrainName());
            downloadDto.setTrainContent(trainEmpDto.getTrainContent());
            downloadDto.setDay(trainEmpDto.getDay());
            downloadDto.setStudyTime(trainEmpDto.getStudyTime());
            downloadDto.setMainCompany(trainEmpDto.getMainCompany());
            downloadDto.setDidCompany(trainEmpDto.getDidCompany());
            downloadDto.setTrainCategory(trainEmpDto.getTrainCategory());
            downloadDto.setTrainPlace(trainEmpDto.getTrainPlace());
            downloadDto.setPlan(trainEmpDto.getPlan());
            downloadDtos.add(downloadDto);
        }
        return downloadDtos;
    }

//    @Override
//    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
//
//        List<FormTrainChooseEmpDto> list=formTrainChooseEmpRepository.getAll();
//        for (FormTrainChooseEmpDto trainChooseEmpDto:list){
//            if (trainChooseEmpDto.getIsComplete() == false){
//                return ResultFactory.commonError("派外培训子流程尚未结束，请重新审批！");
//            }
//        }
//
//
//        return super.doApprove(dto,param);
//    }

}
