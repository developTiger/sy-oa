package com.sunesoft.lemon.deanery.deliverables.application;

import com.sunesoft.lemon.deanery.deliverables.application.criteria.DeliverResearchCriteria;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverAplyExportDto;
import com.sunesoft.lemon.deanery.deliverables.application.dto.FormDeliverApplyDto;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto1;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectAcceptanceDto;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by swb on 2016/8/30.
 */
public interface FormDeliverApplyService extends FormService<FormDeliverApply,FormDeliverApplyDto>{

    public CommonResult addDeliverAndApprove(ApproveFormDto approveFormDto);

    public List<FormDeliverApply> queryDeliver();

    //第一步审批操作
    CommonResult updateDeliverForm1(ApproveFormDto dto);
    //第二步审批操作
    CommonResult updateDeliverForm2(ApproveFormDto dto,Long formNo,String proficientOpinion);
    //第三步审批操作
    CommonResult updateDeliverForm3(ApproveFormDto dto);

    CommonResult updateDeliverForm31(ApproveFormDto dto,String opinion31);

    CommonResult updateDeliverForm4(ApproveFormDto dto,String opinion4);

    CommonResult updateDeliverForm5(ApproveFormDto dto,String opinion5);

    CommonResult nextDeliverForm(ApproveFormDto dto);

    CommonResult uploadProjectFile(Long formNo, String fileId, String fileName);

    //导出数据Dto
    FormDeliverApplyDto downloadByOrderId(String id);


    //根据条件查询
    PagedResult<FormDeliverApplyDto> getDeliverApproves1(DeliverResearchCriteria deliverResearchCriteria);

    PagedResult<FormDeliverApplyDto> getDeliverApproves2(DeliverResearchCriteria deliverResearchCriteria);

    PagedResult<FormDeliverApplyDto> getDeliverApproves3(DeliverResearchCriteria deliverResearchCriteria);

    CommonResult updateDeliver(ApproveFormDto dto);

//    第四步批量审核数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep();
//第四步选择分管领导
    CommonResult updateProject1(ApproveFormDto dto,String empid);
    CommonResult updateProject2(ApproveFormDto dto);
    //第6步选择主管领导
    CommonResult updateDeliverForm6(ApproveFormDto dto,String empid);
//第五步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep4();
//第五步审核操作
    CommonResult nextProject1(ApproveFormDto dto, String instructions);

//第六步分管领导批量审批数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep5(Long approveId);
//第六步审核操作
    CommonResult nextProject2(ApproveFormDto dto, String clstep);
//第七步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep6(Long approveId);

    CommonResult nextProject3(ApproveFormDto dto, String fileId,String fileName,String awardTime);

    List<FormDeliverAplyExportDto> getFormDeliverApplyByFormNo(String formKind);

    CommonResult saveDeliver(List<FormDeliverAplyExportDto> items, String formNo);

    CommonResult nextProject4(ApproveFormDto dto, String awardTime);

    CommonResult nextProject11(ApproveFormDto dto, String kong);

    CommonResult updateProject2(ApproveFormDto dto, String s);

//查询

    ScientificRPKUDto getfdaByFormNo(String formNo);

//第一步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep1(String specialtyType,List<Long> l);
//第二步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep2(String specialtyType,List<Long> l);
//第三步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep3(String specialtyType,List<Long> l);
//第四步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep4(String specialtyType,List<Long> l);
//第5步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep5(String specialtyType,List<Long> l);
//第6步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep6(String specialtyType,Long userId,List<Long> l);
//第7步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep7(String specialtyType,List<Long> l);
//第8步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep8(String specialtyType,List<Long> l);
//第9步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep9(String specialtyType,List<Long> l);
//第10步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep10(String specialtyType,List<Long> l);
//第11步批量数据
    List<FormDeliverApplyDto> getFormDeliverApplyByClstep11(String specialtyType,List<Long> l);

//第二步审批不通过
    CommonResult updateDeliverForm2No(ApproveFormDto dto);
//第四步审批不通过
    CommonResult updateProject4(ApproveFormDto dto);
//第五步审批不通过
    CommonResult nextProject5(ApproveFormDto dto);
//第六步审批不通过
    CommonResult nextProject6(ApproveFormDto dto);
//第九步审批不通过
    CommonResult nextProject9(ApproveFormDto dto);

    String getLeaderNameById(long l);

    void updateDeliver_Information(FormDeliverApplyDto formDeliverApplyDto);

//第3步批量数据
    List<Long> getFormApproverByUserId(Long id,int clstep);

    CommonResult lc06weiyi(String projectName);

    CommonResult lc06weiyi2(String projectName2, Long id);
}


