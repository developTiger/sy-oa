package com.sunesoft.lemon.deanery.projectWorkFlow.application;

import com.sunesoft.lemon.deanery.projectWorkFlow.application.criteria.FormAcceptanceCriteria;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectExecutoryDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.ScientificRPKUCriteria;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectAcceptanceDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectAcceptance;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by zhouz on 2016/8/19.
 */
public interface FormProjectAcceptanceService extends FormService<FormProjectAcceptance,FormProjectAcceptanceDto> {
 //专家审批
    public Long getDtoByFormNo1(Long formNo,String instructions);
    public CommonResult addNewApprove(ApproveFormDto dto, Double price);

    /**
     *  获取项目开题列表
     * */
    public List<FormProjectApply> queryProject();


    CommonResult updateProject(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto);

    CommonResult updateProject1(ApproveFormDto dto,FormProjectAcceptanceDto formProjectAcceptanceDto);

    CommonResult nextProject(ApproveFormDto dto);

    CommonResult uploadProjectFile(Long formNo, String fileId, String fileName);

    PagedResult<ScientificRPKUDto> queryAcceptance(ScientificRPKUCriteria scientificRPKUCriteria);

    List<ScientificRPKUDto> ListAcceptance(Long leader);

    public FormProjectAcceptanceDto getDtoByFormNo(Long formNo);

    public List<Employee> getAllEmployee();

    public List<ScientificRPKUDto> getByIds(String acceptances);

    Long updateFormProjectAcceptanceById(Long id);

    public FormProjectAcceptanceDto getFormByFormNo1(Long formNo);

    List<FormProjectAcceptanceDto> getAcceptanceApproves3(FormAcceptanceCriteria formAcceptanceCriteria,String majorType);

    List<FormProjectAcceptanceDto> getAcceptanceApproves4(FormAcceptanceCriteria formAcceptanceCriteria,String majorType);

    List<FormProjectAcceptanceDto> getAcceptanceApproves5kgk(FormAcceptanceCriteria formAcceptanceCriteria,String majorType);

    List<FormProjectAcceptanceDto> getAcceptanceApproves8kgk(FormAcceptanceCriteria formAcceptanceCriteria,String majorType);

    List<FormProjectAcceptanceDto> getAcceptanceApproves5(FormAcceptanceCriteria formAcceptanceCriteria,String majorType);

    //中心科管人员汇总审批
    CommonResult updateProject0(ApproveFormDto dto);

    //中心科管人员汇总审批1
    CommonResult updateProject1(ApproveFormDto dto);

    //专家审查
    CommonResult updateAcceptProject(ApproveFormDto dto,String empid);
//科管科长审查001
    CommonResult updateProject001(ApproveFormDto dto);

    //科研管理汇总审批选择专家
    CommonResult updateacceptProject1(ApproveFormDto dto,String empid);

    //专家审查(选择专家在外部)
    CommonResult updateAcceptProjectSu(ApproveFormDto dto,String empid);

    //专家审查(选择专家在外部)
    CommonResult updateAcceptProjectLeader(ApproveFormDto dto,String empid);

    List<FormProjectAcceptanceDto> getAcceptanceApproveszj(FormAcceptanceCriteria formAcceptanceCriteria,String majorType,Long userId);

    FormProjectAcceptance getAcceptance(String projectNo,Long formNo);


}
