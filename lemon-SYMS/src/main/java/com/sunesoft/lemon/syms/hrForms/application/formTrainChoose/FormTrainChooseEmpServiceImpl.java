package com.sunesoft.lemon.syms.hrForms.application.formTrainChoose;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpEasyDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrain;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainChooseEmp;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainChooseEmpRepository;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainRepository;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangkefan on 2016/7/22.
 */
@Service("formTrainChooseEmpService")
public class FormTrainChooseEmpServiceImpl extends FormBase2<FormTrainChooseEmp,FormTrainChooseEmpDto> implements FormTrainChooseEmpService{

    @Autowired
    FormTrainChooseEmpRepository formTrainChooseEmpRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    @Autowired
    FormTrainRepository formTrainRepository;

    @Override
    protected CommonResult save(FormTrainChooseEmp formTrainChooseEmp) {
        formTrainChooseEmpRepository.save(formTrainChooseEmp);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormTrainChooseEmp formTrainChooseEmp) {
        FormTrainChooseEmp chooseEmp = formTrainChooseEmpRepository.get(formTrainChooseEmp.getId());
        chooseEmp.setTrainName(formTrainChooseEmp.getTrainName());
        chooseEmp.setTrainBeginDate(formTrainChooseEmp.getTrainBeginDate());
        chooseEmp.setTrainEndDate(formTrainChooseEmp.getTrainEndDate());
        chooseEmp.setTrainPlace(formTrainChooseEmp.getTrainPlace());
        chooseEmp.setTrainContent(formTrainChooseEmp.getTrainPlace());
        formTrainChooseEmpRepository.save(chooseEmp);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormTrainChooseEmp ConvetDto(FormTrainChooseEmpDto dto) {
        FormTrainChooseEmp formTrainChooseEmp = new FormTrainChooseEmp();
         formTrainChooseEmp = DtoFactory.convert(dto,formTrainChooseEmp);
        if(dto.getEmpLists()!=null&&dto.getEmpLists().size()>0 && dto.getEmpids()!=null)
            formTrainChooseEmp.setEmps(employeeList(dto.getEmpids()));
        return formTrainChooseEmp;
    }

    private List<Employee> employeeList(List<Long> ids){
        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.in("id",ids));
        return criterion.list();
    }


    @Override
    protected String getSummery(FormTrainChooseEmp formTrainChooseEmp) {
         return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    /**
     * 所有人签合通过
     * @param formNo
     * @return
     */
    @Override
    public CommonResult doAafterAllApproved(Long formNo) {

        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormTrainChooseEmp getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormTrainChooseEmp.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo", formNo));
        return (FormTrainChooseEmp)criteria.uniqueResult();
    }

    private  EmpEasyDto Convert(Employee employee,EmpEasyDto empEasyDto){
         EmpEasyDto easyDto = new EmpEasyDto();
        if(employee.getId()!=null){
            easyDto.setId(employee.getId());
        }
        if(!"".equals( employee.getDept().getDeptName())){
            easyDto.setTrainDeptName(employee.getDept().getDeptName());
        }
        if(employee.getDept().getId()!=null){
            easyDto.setDeptId(employee.getDept().getId());
        }
        if(!"".equals(employee.getUserNo())){
            easyDto.setUserNo(employee.getUserNo());
        }
        if(!"".equals(employee.getName())) {
            easyDto.setName(employee.getName());
        }
        if(!"".equals(employee.getLoginName())) {
            easyDto.setLoginName(employee.getLoginName());
        }
        if(!"".equals(employee.getCurrenttechPosition())) {
            easyDto.setPosition(employee.getPosition());
        }

        return easyDto;
    }

    @Override
    public FormTrainChooseEmpDto getFormByFormNo(Long formNo) {
        FormTrainChooseEmp trainChooseEmp = this.getByFormNo(formNo);
        FormTrainChooseEmpDto dto = DtoFactory.convert(trainChooseEmp,new FormTrainChooseEmpDto());
        List<EmpEasyDto> empEasyDtoList = new ArrayList<>();
        List<Employee> es = new ArrayList<>();
        FormTrain train= formTrainRepository.getByFormNo(trainChooseEmp.getParentFormNo());
        dto.setFormTrainIsComplete(train.getIsComplete().toString());
        //子流程为3时，获取主流程的emp数据，并且塞入到子流程的数据中去
        //主流程没有审批结束，子流程第三步骤不能审批
        if(trainChooseEmp.getClStep()>2){

            es= train.getEmps();
        }else {
            es = trainChooseEmp.getEmps();
        }
        if(trainChooseEmp.getEmps()!=null &&trainChooseEmp.getEmps().size()>0){
            for(Employee e:es){
                if (e.getDept().getId().equals(trainChooseEmp.getBlongDeptId())) {
                    EmpEasyDto easyDto = this.Convert(e, new EmpEasyDto());
                    empEasyDtoList.add(easyDto);
                }
            }
             dto.setEmpLists(empEasyDtoList);
        }
        return dto;
    }

    @Override
    public CommonResult addEmpById(Long formNo, List<Long> empIds) {
            //根据表单号获取对象
            FormTrainChooseEmp formTrainChooseEmp = this.getByFormNo(formNo);
            //判断是否获取人员信息
            List<Employee> employees =  formTrainChooseEmp.getEmps();
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
//            formTrainChooseEmp.setEmps(employees);

//            formTrainChooseEmp.setEmps(employeeList(empIds));
            formTrainChooseEmpRepository.save(formTrainChooseEmp);
            return ResultFactory.commonSuccess();

        }

    @Override
    public CommonResult deleteEmpById(Long formNo, Long id) {
        FormTrainChooseEmp formTrainChooseEmp = this.getByFormNo(formNo);
        List<Employee> emps = formTrainChooseEmp.getEmps();
        if(emps!=null && emps.size()>0) {
            for (Employee employee : emps) {
                if (employee.getId().equals(id)) {
                    formTrainChooseEmp.getEmps().remove(employee);
                    break;
                }
            }
        }
        formTrainChooseEmpRepository.save(formTrainChooseEmp);
        return ResultFactory.commonSuccess();
    }


    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {

        //当子流程步骤为2时，主流程获取子流程的emp数据
        if (param.get("clStep").toString().equals("2") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())) {
            FormTrainChooseEmp f = getByFormNo(dto.getFormNo());
            FormTrain train = formTrainRepository.getByFormNo(f.getParentFormNo());
            if (train.getEmps() != null && train.getEmps().size() > 0) {
                train.getEmps().addAll(f.getEmps());
            } else {
                train.getEmps().addAll(f.getEmps());
            }
            formTrainRepository.save(train);
        }

        return super.doApprove(dto,param);
    }
}
