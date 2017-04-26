package com.sunesoft.lemon.deanery.project.application;

import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApply;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.project.application.criteria.AccessCriteria;
import com.sunesoft.lemon.deanery.project.application.criteria.ScientificResearchProjectCriteria;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto1;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectRptDto;
import com.sunesoft.lemon.deanery.project.domain.ScientficApproveFile;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormAcceptanceProjectFile;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectAcceptance;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecution;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27.
 */
public interface ScientificResearchProjectService extends FormService<ScientificResearchProject,ScientificResearchProjectDto>{
    /**
     * 新增科研项目
     * @param scientificResearchProjectDto
     * @return
     */
    Long addScientificResearchProject(ScientificResearchProjectDto scientificResearchProjectDto);


     Long addOrUpdate(ScientificResearchProjectDto scientificResearchProjectDto);
    /**
     * 修改科研项目
     * @param scientificResearchProjectDto
     * @return
     */
    Long updateScientificResearchProject(ScientificResearchProjectDto scientificResearchProjectDto);

    /**
     * 根据ID获取项目信息
     * @param id
     * @return
     */
    ScientificResearchProjectDto getScientificResearchProject(Long id);
    List<ScientificResearchProject> getProjectByformNo(Long formNo,String opinint);
    List<ScientificResearchProject> getProjectByformNo1(Long formNo,String instrust);
    List<ScientificResearchProject> getProjectById(Long formNo);
    /**
     * 一个或多个科研项目删除
     * @param ids
     * @return
     */
    Boolean delScientificResearchProject(Long[] ids);


    /**
     * 科研项目汇总信息
     * @return
     */
    ScientificResearchProjectRptDto scientificResearchProjectRpt();




/*-----------------------------------------------------------*/
    /**
     * 科管科批量流程
     * @param
     * @return
     */
    List<ScientificResearchProjectDto> getProjectApproves3(ScientificResearchProjectCriteria scientificResearchProjectCriteria,Integer clstep,Long userId);
    List<ScientificResearchProjectDto> getProjectApproves4(ScientificResearchProjectCriteria scientificResearchProjectCriteria,Integer clstep);
    List<ScientificResearchProjectDto> getProjectApproves6(ScientificResearchProjectCriteria scientificResearchProjectCriteria,Integer clstep);
    /**
     * 科管科科长批量流程
     * @param
     * @return
     */
    List<ScientificResearchProjectDto> getProjectApproves4(ScientificResearchProjectCriteria scientificResearchProjectCriteria,Integer clstep,String leadsignname);

    /**
     * 分管领导审批
     * @param
     * @return
     */
    PagedResult<ScientificResearchProjectDto> getProjectApproves5(ScientificResearchProjectCriteria scientificResearchProjectCriteria);

    CommonResult nextProject(ApproveFormDto dto,String clstep);
    //中心科管lot0
    CommonResult updateProject0(ApproveFormDto dto);

    CommonResult updateProject(ApproveFormDto dto,String empid);
    CommonResult updateProject2(ApproveFormDto dto) ;
/*------------------------------------------------------------------*/
    //所有的员工
    List<Employee> queryEmployees();

    Map<String,List<String>> batchAccess(Long[] formNos,String countRemark,Integer countResult,Long appUserId,String appName) throws Exception;


    //形式审查
    CommonResult formalExamination(ScientificResearchProjectDto scientificResearchProjectDto,ApproveFormDto dto,Map<String,Object> param);

    //基层单位领导审批
    CommonResult unitApply(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto,int choice);

//    //基层单位领导审批不同意
//    Long unitApplyNo(ScientificResearchProjectDto dto);

    //科管科筛选审批
    CommonResult filtrationApply(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto,int choice);

    //分管领导审批
    CommonResult secondaryApply(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto,int choice);

    //院科委审定录入
    CommonResult ykwsdlr(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto);

    //更新项目计划
    CommonResult gxxmjh(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto);

    //根据OrderId获取信息
    ScientificResearchProjectDto getByOrderId(String id);

    //导出数据Dto
    ScientificResearchProjectDto1 downByOrderId(String id);

    //导入数据
    CommonResult updateByList(List<ScientificResearchProjectDto1> list,String formNo);
   //根据人名获取id
    public Employee getNameById(String name);
   //根据id获取姓名
    public Employee getNameByName(long id);
   //获取主项目长
    List<ScientificRPKU> queryLeader();
    //会签
    CommonResult proApprove(ApproveFormDto dto, Integer leaderType, List<Long> empIds);

    public List<ScientificResearchProject> queryProject();

    Long addScientificResearchProject(Long formNO,String names,String appIds);

    List<ScientificResearchProject> findScienceProjectByStatus(String status);

   int findByName(String projectName);
    CommonResult uploadProjectFile(Long formNo ,String fileId,String fileName);
    //根据项目编号查找  项目编号 对应的项目 是否重复
    int getNumberByProjectNo(String projectNo);

/********************************************************************************************************/
    //详情页链接
    // 根据projectNo查找项目立项的id；
    //立项
    ScientificResearchProjectDto getIdByProjectNo(String projectNo);
    ScientificResearchProjectDto getIdByFormNo(Long formNo);

    //开题
    FormProjectApply getIdByProjectNo1(String projectNo);
    //延迟
    FormDelayApply getIdByProjectNo2(String projectNo);
    //检测
    FormProjectExecution getIdByProjectNo3(String projectNo);
    //验收
    FormProjectAcceptance getIdByProjectNo4(String projectNo);
    //成果
    FormDeliverApply getIdByProjectNo5(String projectNo);

    Boolean judgeProjectNo(String projectNo);


    //ScientficApproveFile
    ScientficApproveFile getFile(String sid);
}
