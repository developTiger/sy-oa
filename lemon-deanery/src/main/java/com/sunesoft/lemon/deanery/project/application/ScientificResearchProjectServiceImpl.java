package com.sunesoft.lemon.deanery.project.application;
import com.sunesoft.lemon.deanery.FormFlow.domain.CountersignRepository;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.ReceptionNB;
import com.sunesoft.lemon.deanery.StringCommHelper;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.CommonDriver;
import com.sunesoft.lemon.deanery.delayflow.domain.FormDelayApply;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.project.application.criteria.AccessCriteria;
import com.sunesoft.lemon.deanery.project.application.criteria.ScientificResearchProjectCriteria;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto1;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectFileDto;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectRptDto;
import com.sunesoft.lemon.deanery.project.domain.ScientficApproveFile;
import com.sunesoft.lemon.deanery.project.domain.ScientficProjectFile;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchPeojectRepository;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.ProjectPlanService;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormOpenProjectFileDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectApplyDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.application.dto.FormProjectExecutoryDto;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormOpenProjectFile;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectAcceptance;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectApply;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormProjectExecution;
import com.sunesoft.lemon.deanery.scientificRPKU.ScienticRPKUService;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKU;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKURepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.InnerFormAppPointDto;
import com.sunesoft.lemon.syms.workflow.domain.*;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/6/27.
 */
@Service(value = "scientificResearchProjectService")
public  class ScientificResearchProjectServiceImpl  extends FormBase2<ScientificResearchProject,ScientificResearchProjectDto> implements ScientificResearchProjectService {
    @Autowired
    FormHeaderService headerService;
    @Autowired
    private ScientificResearchPeojectRepository scientificResearchPeojectRepository;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DeptmentService deptmentService;
    @Autowired
    private FormHeaderRepository formHeaderRepository;
    @Autowired
    private ProjectPlanService projectPlanService;
    @Autowired
    private  ScienticRPKUService scienticRPKUService ;
    @Autowired
    private  ScientificRPKURepository scientificRPKURepository;
    @Override
    public Long addScientificResearchProject(ScientificResearchProjectDto scientificResearchProjectDto) {
        ScientificResearchProject scientificResearchProject=new ScientificResearchProject();
        BeanUtils.copyProperties(scientificResearchProjectDto, scientificResearchProject);
        Long l= scientificResearchPeojectRepository.save(scientificResearchProject);
        return l;
    }

    @Override
    public Long addScientificResearchProject(Long id,String appIds,String names) {
        ScientificResearchProject scientificResearchProject= scientificResearchPeojectRepository.get(id);
          String leadsignid=appIds;
        Long l= scientificResearchPeojectRepository.save(scientificResearchProject);
        return l;
    }
    /**
     * 新增科研项目
     * @param scientificResearchProjectDto
     * @return
     */
    @Override
    public Long addOrUpdate(ScientificResearchProjectDto scientificResearchProjectDto)  {
        ScientificResearchProject scientificResearchProject=new ScientificResearchProject();
        BeanUtils.copyProperties(scientificResearchProjectDto,scientificResearchProject);
        scientificResearchProjectDto.setProjectStatus("1");
        Long l =    scientificResearchPeojectRepository.save(DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProjectDto));
        return l;
    }
    @Override
    public Long updateScientificResearchProject(ScientificResearchProjectDto scientificResearchProjectDto) {
        ScientificResearchProject scientificResearchProject=new ScientificResearchProject();
        BeanUtils.copyProperties(scientificResearchProjectDto,scientificResearchProject);
        Long l =    scientificResearchPeojectRepository.save(scientificResearchProject);
        return l;
    }
    /**
     * 查询科研项目
     * @param id
     * @return
     */
    @Override
    public ScientificResearchProjectDto getScientificResearchProject(Long id) {
            ScientificResearchProject scientificResearchProject = scientificResearchPeojectRepository.get(id);
            ScientificResearchProjectDto scientificResearchProjectRptDto = DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProject);
            return scientificResearchProjectRptDto;
    }

    /**
     * 查询科研项目
     * @param formNo
     * @return
     */
    @Override
    public List<ScientificResearchProject> getProjectByformNo(Long formNo,String opinint) {
        Criteria criterion = getSession().createCriteria(ScientificResearchProject.class);
        criterion.add(Restrictions.eq("formNo", formNo));
        List<ScientificResearchProject> beans = criterion.list();
        for (ScientificResearchProject bean : beans) {
            String s=  bean.getProficientOpinion();
            if(s==null||s==""){
                bean.setProficientOpinion(opinint);
            }else{
                s=s+"  |  ";
                bean.setProficientOpinion(s+opinint);
            }
            scientificResearchPeojectRepository.save(bean);
        }
        return beans;
    }
    /**
     * 查询科研项目
     * @param formNo
     * @return
     */
    @Override
    public List<ScientificResearchProject> getProjectByformNo1(Long formNo,String instrust) {
        Criteria criterion = getSession().createCriteria(ScientificResearchProject.class);
        criterion.add(Restrictions.eq("formNo", formNo));
        List<ScientificResearchProject> beans = criterion.list();
        for (ScientificResearchProject bean : beans) {
            String s=  bean.getProficientOpinion();
           /* if(s==null||s==""){*/
                bean.setInstructions(instrust);
        /*    }else{
                s=s+"  |  ";
                bean.setInstructions(s + instrust);
            }*/
            scientificResearchPeojectRepository.save(bean);
        }
        return beans;
    }

    /**
     * 查询科研项目
     * @param formNo
     * @return
     */
    @Override
    public List<ScientificResearchProject> getProjectById(Long formNo) {
        Criteria criterion = getSession().createCriteria(ScientificResearchProject.class);
        criterion.add(Restrictions.eq("formNo", formNo));
        List<ScientificResearchProject> beans = criterion.list();
        return beans;
    }
    /**
     * 批量删除科研项目
     * @param ids
     * @return
     */
    @Override
    public Boolean delScientificResearchProject(Long[] ids) {
                Criteria criterion = getSession().createCriteria(ScientificResearchProject.class);
                if (ids!=null&&ids.length>0) {
                    criterion.add(Restrictions.in("id", ids));
                }
                List<ScientificResearchProject> beans = criterion.list();
                for (ScientificResearchProject bean : beans) {
                 bean.setIsActive(false);
                 scientificResearchPeojectRepository.save(bean);
        }

        return true;

    }

    /**
     * 科研模块数据统计
     * @return
     */
    @Override
    public ScientificResearchProjectRptDto scientificResearchProjectRpt() {
        ScientificResearchProjectRptDto scientificResearchProjectRptDto = new ScientificResearchProjectRptDto();
        List<Map<String,Object>> rptByStatus = scientificResearchPeojectRepository.projectReportByStatus();
        List<Map<String,Object>> rptByType = scientificResearchPeojectRepository.projectReportByType();
        List<Map<String,Object>> rptByDept = scientificResearchPeojectRepository.projectReportByDept();
        List<Map<String,Object>> rptByYear = scientificResearchPeojectRepository.projectReportByYear();
        scientificResearchProjectRptDto.setProjectRptByStatus(rptByStatus);
        scientificResearchProjectRptDto.setProjectRptByType(rptByType);
        scientificResearchProjectRptDto.setProjectRptByDept(rptByDept);
        scientificResearchProjectRptDto.setProjectRptByYear(rptByYear);
        return scientificResearchProjectRptDto;
    }
    /**
     * 根据姓名查询员工信息
     * @param leaderName
     * @return
     */
    private List<Long> getEmployeeByName(String leaderName){
        Criteria criteria = getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.like("name","%" + leaderName + "%"));
        List<Employee> employees = criteria.list();
        List<Long> ids = new ArrayList<Long>();
        for (Employee e : employees){
            ids.add(e.getId());
        }
        return ids;
    }

       @Override
    public List<Employee> queryEmployees(){
           String hql = "select id,real_name from syy_oa_hr_employees where IS_ACTIVE=1";
           Query query = this.getSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
           List<Employee> list = query.list();
           return list;
       }

    @Override
    public List<ScientificRPKU> queryLeader(){
        Criteria criteria1 = this.getSession().createCriteria(ScientificRPKU.class);
        criteria1.add(Restrictions.eq("isActive",true));
        List<ScientificRPKU> list=criteria1.list();
        return list;
    }

    @Override
    public List<ScientificResearchProject> queryProject() {
        Criteria criteria=this.getSession().createCriteria(ScientificResearchProject.class);
        List<ScientificResearchProject> list=criteria.list();
        return list;
    }

    @Override
    public List<ScientificResearchProject> findScienceProjectByStatus(String status) {
        Criteria criteria=this.getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("projectStatus",status));
        List<ScientificResearchProject> list=criteria.list();
        return list;
    }

    @Override
    public int findByName(String projectName) {
        Criteria criteria=this.getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("projectName",projectName));
        criteria.add(Restrictions.eq("isActive",true));
        List<ScientificResearchProject> list=criteria.list();
        return list.size();
    }

    @Override
    public Map<String, List<String>> batchAccess(Long[] formNos, String countRemark, Integer countResult, Long appUserId, String appName) throws Exception {
        return null;
    }


    @Override
    public CommonResult formalExamination(ScientificResearchProjectDto scientificResearchProjectDto,ApproveFormDto dto,Map<String,Object> param){
        ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProjectDto);
        Long l=scientificResearchPeojectRepository.save(scientificResearchProject);
        if(l!=0){
            return this.doApprove(dto,param);
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
//        snakerEngine.executeTask(scientificResearchProjectDto.getTaskId());
    }
    @Override
    public CommonResult unitApply(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto,int choice){
        ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProjectDto);
        Long l=scientificResearchPeojectRepository.save(scientificResearchProject);
        if(l!=0){
            if(choice!=0){
                FormHeaderDto formDto=headerService.getHeaderByFormNo(dto.getFormNo());
                List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();
                this.setPointAsNext(dto.getFormNo(),dtos.get(0).getId());
            }
            return this.doApprove(dto,param);
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
    }
//    @Override
//    public Long unitApplyNo(ScientificResearchProjectDto dto){
//        ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(dto);
//        Long l=scientificResearchPeojectRepository.save(scientificResearchProject);
//        Map<String,Object> args = new HashMap<String, Object>();
//        args.put("agree1","unagree");
//        snakerEngine.executeTask(dto.getTaskId(),null,args);
//        return l;
//    }

    @Override
    public CommonResult filtrationApply(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto,int choice){
        ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProjectDto);
        Long l=scientificResearchPeojectRepository.save(scientificResearchProject);
        if(l!=0){
            if(choice!=0){
                FormHeaderDto formDto=headerService.getHeaderByFormNo(dto.getFormNo());
                List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();
                this.setPointAsNext(dto.getFormNo(),dtos.get(1).getId());
            }
            return this.doApprove(dto,param);
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
    }


    @Override
    public  CommonResult secondaryApply(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto,int choice){
        ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProjectDto);
        Long l=scientificResearchPeojectRepository.save(scientificResearchProject);
        if(l!=0){
            if(choice!=0){
                FormHeaderDto formDto=headerService.getHeaderByFormNo(dto.getFormNo());
                List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();
                this.setPointAsNext(dto.getFormNo(),dtos.get(2).getId());
            }
            return this.doApprove(dto,param);
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
    }


    @Override
    public CommonResult ykwsdlr(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto){
        ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProjectDto);
        Long l=scientificResearchPeojectRepository.save(scientificResearchProject);
        if(l!=0){
            return this.doApprove(dto,param);
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
    }

    @Override
    public CommonResult gxxmjh(ApproveFormDto dto,Map<String,Object> param,ScientificResearchProjectDto scientificResearchProjectDto){
        ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProjectDto);
//        scientificResearchProject.setProjectStatus("3");
        Long l=scientificResearchPeojectRepository.save(scientificResearchProject);
        if(l!=0){
            return  this.doAafterAllApproved(dto.getFormNo());
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
    }

    @Override
    public ScientificResearchProjectDto getByOrderId(String fromNo){
        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("formNo",Long.parseLong(fromNo)));
        List<ScientificResearchProject> list=criteria.list();
        if(list!=null && list.size()>0){
            ScientificResearchProjectDto dto=DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            List<ScientificResearchProjectFileDto> fileDtos=new ArrayList<>();
            for(ScientficProjectFile file:list.get(0).getScientficProjectFileList()){
                fileDtos.add(DeaneryUtil.convert(file,new ScientificResearchProjectFileDto()));
            }
            dto.setFileList(fileDtos);
            return dto;
        }else{
            return null;
        }
    }

    @Override
    protected CommonResult save(ScientificResearchProject scientificResearchProject) {
        Long id = scientificResearchPeojectRepository.save(scientificResearchProject);
        return ResultFactory.commonSuccess(id);
    }

    @Override
    protected CommonResult update(ScientificResearchProject scientificResearchProject) {
        ScientificResearchProject scientificResearchProject1=scientificResearchPeojectRepository.get(scientificResearchProject.getId());
        if(scientificResearchProject1==null){
            return ResultFactory.commonError("更新失败");
        }
        scientificResearchPeojectRepository.save(scientificResearchProject);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected ScientificResearchProject ConvetDto(ScientificResearchProjectDto Dto) {
        ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(Dto);
        List<ScientficProjectFile> files=new ArrayList<>();
          for(ScientificResearchProjectFileDto f: Dto.getFileList()){
              files.add(DeaneryUtil.convert(f,new ScientficProjectFile()));
          }
        scientificResearchProject.setScientficProjectFileList(files);
        return scientificResearchProject;
    }

    @Override
    protected String getSummery(ScientificResearchProject scientificResearchProject) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        return null;
    }
//shenqing
    @Override
    protected ScientificResearchProject getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(ScientificResearchProject.class);
        /*criterion.add(Restrictions.eq("isActive", true));*/
        criterion.add(Restrictions.eq("formNo",formNo));
        ScientificResearchProject s=(ScientificResearchProject)criterion.uniqueResult();
        return s;
    }

    @Override
    public ScientificResearchProjectDto getFormByFormNo(Long formNo) {
        ScientificResearchProject scientificResearchProject=this.getByFormNo(formNo);
        ScientificResearchProjectDto dto= DtoFactory.convert(scientificResearchProject,new ScientificResearchProjectDto());
        return dto;//00001
    }

    @Override
    public CommonResult proApprove(ApproveFormDto dto, Integer leaderType, List<Long> empIds) {
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        Long nextId = header.getNextAppPointId();
        List<Long> emplist = new ArrayList<>();
        for (InnerFormApprovePoint point : header.getInnerFormApprovePoints()) {
            if (nextId.equals(point.getId())) {
                for (Employee e : point.getAppRole().getApprover()) {
                    emplist.add(e.getId());
                }
            }
        }
        emplist.addAll(empIds);
        return this.resetNextApprover(dto.getFormNo(), emplist);
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
        Integer clStep = Integer.parseInt(param.get("clStep").toString());
        Boolean isComplete = Boolean.valueOf(param.get("isStepComplete").toString());
        Long id=StringCommHelper.nullToLong(param.get("id"));
        String projectNo=null;
        ScientificResearchProject scientificResearchProject=scientificResearchPeojectRepository.get(id);

        if(clStep.equals(1)&&!isComplete&&dto.getAppValue().equals(AppValue.B.ordinal())) {
            projectNo=StringCommHelper.nullToString(param.get("projectNo"));
            if(scientificResearchProject!=null) {
                scientificResearchProject.setIsActive(false);
                scientificResearchPeojectRepository.save(scientificResearchProject);
            }
            scienticRPKUService.reset(StringCommHelper.nullToString(param.get("projectNo")),"0",null,null,null,null);
           // projectPlanService.updateProjectPlanState(StringCommHelper.nullToString(param.get("projectNo")),"0");
            ScientificRPKU scientificRPKU1=scienticRPKUService.getIdByProjectNo(projectNo);
            if(scientificRPKU1!=null) {
                scientificRPKU1.setIsActive(false);
                scientificRPKURepository.save(scientificRPKU1);
            }
          }
        ScientificRPKU scientificRPKU1=null;
        ScientificRPKU scientificRPKU=null;
        if((clStep.equals(1))&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            scientificResearchProject.setAssumeCompany(StringCommHelper.nullToString(param.get("assumeCompany")));
            scientificResearchProject.setBeginTime(DateHelper.parse(param.get("beginTime1").toString(),"yyyy-MM-dd"));
            scientificResearchProject.setDept(StringCommHelper.nullToLong(param.get("dept")));
            scientificResearchProject.setDeputy(StringCommHelper.nullToLong(param.get("deputy")));
            scientificResearchProject.setEndTime(DateHelper.parse(param.get("endTime1").toString(),"yyyy-MM-dd"));
            scientificResearchProject.setGroupMembers(StringCommHelper.nullToString(param.get("groupMembers")));
            scientificResearchProject.setLeader(StringCommHelper.nullToLong(param.get("leader")));
            scientificResearchProject.setProjectName(StringCommHelper.nullToString(param.get("projectName")));
            scientificResearchProject.setProjectNo(StringCommHelper.nullToString(param.get("projectNo")));
            byte[] projectPlanInfo = param.get("projectPlanInfo").toString().getBytes(Charset.forName("UTF-8"));
            scientificResearchProject.setProjectPlanInfoTxt(projectPlanInfo);
            scientificResearchProject.setProjectType(StringCommHelper.nullToString(param.get("projectType")));
            scientificResearchProject.setRemark(StringCommHelper.nullToString(param.get("remark")));
            scientificResearchProject.setSpecialtyType(StringCommHelper.nullToString(param.get("specialtyType")));
            scientificResearchProject.setJoinComopany(StringCommHelper.nullToString(param.get("joinComopany")));
            scientificResearchPeojectRepository.save(scientificResearchProject);
//项目库

            scientificRPKU1=scienticRPKUService.getIdByProjectNo(projectNo);
            if(scientificRPKU1==null) {
                scientificRPKU=new ScientificRPKU();
                scientificRPKU.setAssumeCompany(StringCommHelper.nullToString(param.get("assumeCompany")));
                scientificRPKU.setBeginTime(DateHelper.parse(param.get("beginTime1").toString(), "yyyy-MM-dd"));
                scientificRPKU.setDept(StringCommHelper.nullToLong(param.get("dept")));
                scientificRPKU.setDeptName(StringCommHelper.nullToString(param.get("deptName")));
                scientificRPKU.setDeputy(StringCommHelper.nullToLong(param.get("deputy")));
                scientificRPKU.setEndTime(DateHelper.parse(param.get("endTime1").toString(), "yyyy-MM-dd"));
                scientificRPKU.setGroupMembers(StringCommHelper.nullToString(param.get("groupMembers")));
                scientificRPKU.setLeader(StringCommHelper.nullToLong(param.get("leader")));
                scientificRPKU.setProjectName(StringCommHelper.nullToString(param.get("projectName")));
                scientificRPKU.setProjectNo(StringCommHelper.nullToString(param.get("projectNo")));
                byte[] projectPlanInfo1 = param.get("projectPlanInfo").toString().getBytes(Charset.forName("UTF-8"));
                scientificRPKU.setProjectPlanInfoTxt(projectPlanInfo1);
                scientificRPKU.setProjectType(StringCommHelper.nullToString(param.get("projectType")));
                scientificRPKU.setRemark(StringCommHelper.nullToString(param.get("remark")));

                //年度
                scientificRPKU.setNiandu(scientificResearchProject.getNiandu());
                //年度显示
                scientificRPKU.setNiandu_str(DateHelper.formatDate(scientificResearchProject.getNiandu(), "yyyy"));
                scientificRPKU.setProjectStatus("1");//立项进行中
                scientificRPKU.setProjectSBStatus("0");//未申报
                scientificRPKU.setProjectKTStatus("0");//为开题
                scientificRPKU.setProjectYSStatus("0");//未验收
                scientificRPKU.setProjectJCStatus("0");//未检测
                scientificRPKU.setProjectYXStatus("1");//运行中
                scientificRPKU.setProjectID(id);
                scientificRPKU.setSpecialtyType(StringCommHelper.nullToString(param.get("specialtyType")));
                scientificRPKU.setJoinComopany(StringCommHelper.nullToString(param.get("joinComopany")));
                scientificRPKU.setLeaderName(employeeService.getEmpById(scientificRPKU.getLeader()).getName());
                if (scientificResearchProject.getDeputy() != null) {
                    scientificRPKU.setDeputyName(employeeService.getEmpById(scientificRPKU.getDeputy()).getName());
                }
                scientificRPKURepository.save(scientificRPKU);
            }else{
                scientificRPKU1.setGroupMembers(StringCommHelper.nullToString(param.get("groupMembers")));
                scientificRPKU1.setProjectType(StringCommHelper.nullToString(param.get("projectType")));
                scientificRPKU1.setRemark(StringCommHelper.nullToString(param.get("remark")));
                byte[] projectPlanInfo1 = param.get("projectPlanInfo").toString().getBytes(Charset.forName("UTF-8"));
                scientificRPKU1.setProjectPlanInfoTxt(projectPlanInfo1);
                scientificRPKU1.setLeader(StringCommHelper.nullToLong(param.get("leader")));
                scientificRPKU1.setJoinComopany(StringCommHelper.nullToString(param.get("joinComopany")));
                scientificRPKU1.setDept(StringCommHelper.nullToLong(param.get("dept")));
                scientificRPKU1.setDeptName(StringCommHelper.nullToString(param.get("deptName")));
                scientificRPKU1.setLeaderName(employeeService.getEmpById(scientificRPKU1.getLeader()).getName());
                if (scientificResearchProject.getDeputy() != null) {
                    scientificRPKU1.setDeputyName(employeeService.getEmpById(scientificRPKU1.getDeputy()).getName());
                }
                scientificRPKURepository.save(scientificRPKU1);
            }

        }
        if(clStep.equals(2) && !isComplete && dto.getAppValue().equals(AppValue.Y.ordinal())) {
            scientificResearchProject.setInstructions(StringCommHelper.nullToString(param.get("instructions")));
            scientificRPKU1=scienticRPKUService.getIdByProjectNo(StringCommHelper.nullToString(param.get("projectNo")));
            scientificRPKU1.setInstructions(StringCommHelper.nullToString(param.get("instructions")));
            scientificRPKURepository.save(scientificRPKU1);
        }
        if(clStep>2 && !isComplete && dto.getAppValue().equals(AppValue.N.ordinal())) {
            scientificRPKU1=scienticRPKUService.getIdByProjectNo(StringCommHelper.nullToString(param.get("projectNo")));
            scientificRPKU1.setIsActive(false);
            scientificRPKURepository.save(scientificRPKU1);
        }
        if(clStep.equals(4) && !isComplete && dto.getAppValue().equals(AppValue.Y.ordinal())) {
                FormHeader header = formHeaderRepository.get(dto.getFormNo());
                header.setContent(StringCommHelper.nullToString(param.get("hidid")));
                formHeaderRepository.save(header);
        }
        if(clStep.equals(5) && !isComplete && dto.getAppValue().equals(AppValue.Y.ordinal())) {
          FormHeader header = formHeaderRepository.get(dto.getFormNo());
               Long nextId = header.getNextAppPointId();
            String empid = header.getContent();

           // String empid =StringCommHelper.nullToString(param.get("hidid"));
            String[] ids = empid.split(",");
             int ss=ids.length;
            if(empid!=null){
            if(empid.indexOf(",") >= 0) {
                String[] empids = empid.split(",");
                List<Long> emplist = new ArrayList<>();
                for (int i = 0; i < empids.length; i++) {
                    emplist.add(Long.parseLong(empids[i]));
                }


                this.resetNextApprover(dto.getFormNo(), emplist);
            }else{
                List<Long> emplist = new ArrayList<>();
                emplist.add(Long.parseLong(empid));
                this.resetNextApprover(dto.getFormNo(), emplist);
              }
            }
        }

        if((clStep.equals(6))&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            String proficientOpinion =StringCommHelper.nullToString(param.get("proficientOpinion"));
            String ss=scientificResearchProject.getProficientOpinion();
            if(ss!=null) {
                ss = ss + "  |  ";
                scientificResearchProject.setProficientOpinion(ss+proficientOpinion);
            }else {
                scientificResearchProject.setProficientOpinion(proficientOpinion);
            }
            scientificResearchPeojectRepository.save(scientificResearchProject);
            scientificRPKU1=scienticRPKUService.getIdByProjectNo(StringCommHelper.nullToString(param.get("projectNo")));
            scientificRPKU1.setProficientOpinion(scientificResearchProject.getProficientOpinion());
            scientificRPKURepository.save(scientificRPKU1);
        }

        if((clStep.equals(7))&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            String empid =StringCommHelper.nullToString(param.get("hidleadid"));
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent(empid);
            formHeaderRepository.save(header);
        }

        if((clStep.equals(8))&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            Long nextId = header.getNextAppPointId();
            String empid = header.getContent();

           // String empid =StringCommHelper.nullToString(param.get("hidleadid"));
            if(empid!=null){
                if(empid.indexOf(",") >= 0) {
                    String[] empids = empid.split(",");
                    List<Long> emplist = new ArrayList<>();
                    for (int i = 0; i < empids.length; i++) {
                        emplist.add(Long.parseLong(empids[i]));
                    }

                    this.resetNextApprover(dto.getFormNo(), emplist);
                }else{
                    List<Long> emplist = new ArrayList<>();
                    emplist.add(Long.parseLong(empid));
                    this.resetNextApprover(dto.getFormNo(), emplist);
                }
            }
        }

            if(clStep.equals(11)) {
                ScientificResearchProjectDto scientificResearchProjectDto=this.getIdByFormNo(dto.getFormNo());
                /* 立项项目从项目计划中获取
                   CommonResult ProjectPlanByProjectNo=projectPlanService.updateProjectPlanState(scientificResearchProjectDto.getProjectNo(),"2");
                  if(ProjectPlanByProjectNo.getIsSuccess()){
                    //
                  }*/

                ProjectPlanInput projectPlanInput=new ProjectPlanInput();
                projectPlanInput.setProjectPlan_Number(scientificResearchProjectDto.getProjectNo());
                projectPlanInput.setProjectPlan_InputYear_Str(DateHelper.formatDate(scientificResearchProjectDto.getYear(),"yyyy-MM-dd"));
                projectPlanInput.setProjectPlan_Name(scientificResearchProjectDto.getProjectName());
                projectPlanInput.setProjectPlan_Content(scientificResearchProjectDto.getProjectPlanInfo());
                projectPlanInput.setProjectPlan_BearUnit(scientificResearchProjectDto.getAssumeCompany());
                projectPlanInput.setProjectPlan_ParticipatingUnit(scientificResearchProjectDto.getJoinComopany());
                projectPlanInput.setProjectPlan_StartTime(scientificResearchProjectDto.getBeginTime());
                projectPlanInput.setProjectPlan_EndTime(scientificResearchProjectDto.getEndTime());
                projectPlanInput.setProjectPlan_Manager(scientificResearchProjectDto.getLeaderName());
                projectPlanInput.setProjectPlan_State("2");//2为立项结束
                List<ProjectPlanInput> inputs=new ArrayList<>();
                inputs.add(projectPlanInput);
                CommonResult inputProjectPlan=projectPlanService.savaProjectApprove(inputs);
                ScientificRPKU scientificRPKU2=scienticRPKUService.getIdByProjectNo(scientificResearchProjectDto.getProjectNo());
                if(scientificRPKU2!= null ) {
                    scientificRPKU2.setProjectStatus("2");//立项完成
                    scientificRPKURepository.save(scientificRPKU2);
                }
        }
                 return super.doApprove(dto,param);
    }


    public Long UpdateByDto(ScientificResearchProjectDto dto){
            ScientificResearchProject scientificResearchProject=DeaneryUtil.convertFromListScientificResearchProjectDto(dto);
            Long l=scientificResearchPeojectRepository.save(scientificResearchProject);
            return l;
            }

    @Override
    public ScientificResearchProjectDto1 downByOrderId(String id){
        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("formNo",Long.parseLong(id)));
        List<ScientificResearchProject> list=criteria.list();
        if(list!=null && list.size()>0){
            ScientificResearchProjectDto1 dto=DeaneryUtil.convertFromListScientificResearchProjectDto1(list.get(0));
            return dto;
        }else{
            return null;
        }
    }
    @Override
    public CommonResult updateByList(List<ScientificResearchProjectDto1> list,String formNo){
        String[] formno=formNo.split(",");

        for(int i=0;i<list.size();i++){
            ScientificResearchProject scientificResearchProject=this.getByFormNo(Long.parseLong(formno[i]));
           // scientificResearchProject.setProjectNo(list.get(i).getProjectNo());
            scientificResearchProject.setProjectName(list.get(i).getProjectName());
            scientificResearchProject.setAssumeCompany(list.get(i).getAssumeCompany());
            scientificResearchProject.setJoinComopany(list.get(i).getJoinComopany());
            scientificResearchProject.setRemark(list.get(i).getRemark());
            scientificResearchProject.setProjectPlanInfoTxt(list.get(i).getProjectPlanInfo().toString().getBytes(Charset.forName("UTF-8")));
            //根据人命查找id
             String deputyadress=list.get(i).getDeputyAdress();
             String leaderadress=list.get(i).getLeaderAdress();
            if(deputyadress!=null&&leaderadress!=null) {
                Employee employee = getNameById(deputyadress);
                Employee employee1 = getNameById(leaderadress);
                scientificResearchProject.setDeputy(employee.getId());
                scientificResearchProject.setLeader(employee1.getId());
            }
            scientificResearchProject.setProjectPlanInfoTxt(list.get(i).getProjectPlanInfo().getBytes(Charset.forName("UTF-8")));

            if(list.get(i).getBeginTime()!=null){
                scientificResearchProject.setBeginTime(DateHelper.parse(list.get(i).getBeginTime(),"yyyy-MM-dd"));
            }
            if(list.get(i).getEndTime()!=null){
                scientificResearchProject.setEndTime(DateHelper.parse(list.get(i).getEndTime(),"yyyy-MM-dd"));
            }
            scientificResearchPeojectRepository.save(scientificResearchProject);
            //跟新项目库没数据
            ScientificRPKU scientificRPKU=scienticRPKUService.getIdByProjectNo(scientificResearchProject.getProjectNo());
                           scientificRPKU.setBeginTime(scientificResearchProject.getBeginTime());
                           scientificRPKU.setEndTime(scientificResearchProject.getEndTime());
                           scientificRPKU.setAssumeCompany(scientificResearchProject.getAssumeCompany());
                           scientificRPKU.setProjectName(scientificResearchProject.getProjectName());
                           scientificRPKU.setProjectPlanInfoTxt(scientificResearchProject.getProjectPlanInfoTxt());
                           scientificRPKU.setLeader(scientificResearchProject.getLeader());
                           scientificRPKU.setDeputy(scientificResearchProject.getDeputy());
                           EmpDto s=employeeService.getLeader(scientificRPKU.getLeader());
                           scientificRPKU.setLeaderName(s.getName());
                           EmpDto s1=employeeService.getLeader(scientificRPKU.getDeputy());
                           scientificRPKU.setDeputyName(s1.getName());
                           scientificRPKU.setProjectPlanInfoTxt(scientificRPKU.getProjectPlanInfoTxt());
                           scientificRPKU.setRemark(scientificResearchProject.getRemark());
                           scientificRPKURepository.save(scientificRPKU);

        }
        return ResultFactory.commonSuccess();
    }

    //根据邮箱获取id
    @Override
    public Employee getNameById(String adress) {
        List<Employee> listByid = new ArrayList<Employee>();
        Criteria criteria = getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("email",adress));
        criteria.add(Restrictions.eq("isActive",true));
        listByid = criteria.list();
        if ( listByid.size() == 0 ){
            return null;
        }
        return listByid.get(0);
    }

    @Override
    public Employee getNameByName(long id) {
        List<Employee> list = new ArrayList<Employee>();
        Criteria criteria = getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("id",id));
        criteria.add(Restrictions.eq("isActive",true));
        list = criteria.list();
        if ( list.size() == 0 ){
            return null;
        }
        return list.get(0);
    }


/*——————————————————————————————————————————————————————————————————————————*/
    /**
     * 科管科批量申请流程
     * @param
     * @return
     */
    @Override
    public List<ScientificResearchProjectDto> getProjectApproves3(ScientificResearchProjectCriteria scientificResearchProjectCriteria,Integer clstep,Long userId) {

        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(scientificResearchProjectCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC01");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);
       if(userId!=null) {
           headerCriteria.setApproverId(userId);
       }
       PagedResult<FormHeaderDto> formHeaderDtos= headerService.findFormPaged(headerCriteria);

        List<Long> formNOs = new ArrayList<>();
        if(formHeaderDtos.getItems()!=null &&formHeaderDtos.getItems().size()>0){
            for(FormHeaderDto headerDto : formHeaderDtos.getItems()){
                formNOs.add(headerDto.getId());
            }
        }
        else{
            return  new ArrayList<>();
        }

        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.in("formNo",formNOs));


        if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }

        List<ScientificResearchProject> beans = criteria.list();

        List<ScientificResearchProjectDto> dto = new ArrayList<ScientificResearchProjectDto>();
        for(ScientificResearchProject scientificResearchProject : beans){
            ScientificResearchProjectDto scientificResearchProjectDto = DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProject);
          /*  scientificResearchProjectDto.setDeptName(deptmentService.getByDeptId(scientificResearchProject.getDept()).getDeptName());*/
            scientificResearchProjectDto.setDeptName(scientificResearchProject.getDeptName());
            if(scientificResearchProjectDto.getLeader()!=null && scientificResearchProjectDto.getLeader().intValue()!=0){
                EmpDto empDto = employeeService.getLeader(scientificResearchProjectDto.getLeader());
                scientificResearchProjectDto.setLeaderName(empDto.getName());
            }
            dto.add(scientificResearchProjectDto);
        }
        return dto;
    }

    /**
     * 科管科
     * @param scientificResearchProjectCriteria
     * @param clstep
     * @return
     */
    @Override
    public List<ScientificResearchProjectDto> getProjectApproves6(ScientificResearchProjectCriteria scientificResearchProjectCriteria,Integer clstep) {

        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(scientificResearchProjectCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC01");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);

        PagedResult<FormHeaderDto> formHeaderDtos= headerService.findFormPaged(headerCriteria);

        List<Long> formNOs = new ArrayList<>();
        if(formHeaderDtos.getItems()!=null &&formHeaderDtos.getItems().size()>0){
            for(FormHeaderDto headerDto : formHeaderDtos.getItems()){
                formNOs.add(headerDto.getId());
            }
        }
        else{
            return  new ArrayList<>();
        }

        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.in("formNo",formNOs));

        if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType", "%" + scientificResearchProjectCriteria.getSpecialtyType() + "%"));
        }

        List<ScientificResearchProject> beans = criteria.list();

        List<ScientificResearchProjectDto> dto = new ArrayList<ScientificResearchProjectDto>();
        for(ScientificResearchProject scientificResearchProject : beans){
            ScientificResearchProjectDto scientificResearchProjectDto = DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProject);
          /*  scientificResearchProjectDto.setDeptName(deptmentService.getByDeptId(scientificResearchProject.getDept()).getDeptName());*/
            scientificResearchProjectDto.setDeptName(scientificResearchProject.getDeptName());
            if(scientificResearchProjectDto.getLeader()!=null && scientificResearchProjectDto.getLeader().intValue()!=0){
                EmpDto empDto = employeeService.getLeader(scientificResearchProjectDto.getLeader());
                scientificResearchProjectDto.setLeaderName(empDto.getName());
            }
            dto.add(scientificResearchProjectDto);
        }
        return dto;
    }

    /**
     * 科管科批量申请流程
     * @param
     * @return
     */
    @Override
    public List<ScientificResearchProjectDto> getProjectApproves4(ScientificResearchProjectCriteria scientificResearchProjectCriteria,Integer clstep) {

        FormHeaderCriteria headerCriteria = new FormHeaderCriteria();
        headerCriteria.setApproverId(scientificResearchProjectCriteria.getApproverId());
        headerCriteria.setFormKind("SYY_KG_LC01");
        List<FormStatus> formStatuses = new ArrayList<>();
        formStatuses.add(FormStatus.UA);
        headerCriteria.setPageSize(1000);
        headerCriteria.setArrFormStatus(formStatuses);
        headerCriteria.setApproveStatus(ApproveStatus.U);

        PagedResult<FormHeaderDto> formHeaderDtos= headerService.findFormPaged(headerCriteria);

        List<Long> formNOs = new ArrayList<>();
        if(formHeaderDtos.getItems()!=null &&formHeaderDtos.getItems().size()>0){
            for(FormHeaderDto headerDto : formHeaderDtos.getItems()){
                formNOs.add(headerDto.getId());
            }
        }
        else{
            return  new ArrayList<>();
        }

        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", clstep));
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.in("formNo",formNOs));

        if(scientificResearchProjectCriteria.getSpecialtyType()!=null && !("").equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.like("specialtyType","%"+scientificResearchProjectCriteria.getSpecialtyType()+"%"));
        }

        List<ScientificResearchProject> beans = criteria.list();

        List<ScientificResearchProjectDto> dto = new ArrayList<ScientificResearchProjectDto>();
        for(ScientificResearchProject scientificResearchProject : beans){
            ScientificResearchProjectDto scientificResearchProjectDto = DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProject);
          /*  scientificResearchProjectDto.setDeptName(deptmentService.getByDeptId(scientificResearchProject.getDept()).getDeptName());*/
            scientificResearchProjectDto.setDeptName(scientificResearchProject.getDeptName());
            if(scientificResearchProjectDto.getLeader()!=null && scientificResearchProjectDto.getLeader().intValue()!=0){
                EmpDto empDto = employeeService.getLeader(scientificResearchProjectDto.getLeader());
                scientificResearchProjectDto.setLeaderName(empDto.getName());
            }
            dto.add(scientificResearchProjectDto);
        }
        return dto;
    }
    /**
     * 科管科批量申请流程
     * @param
     * @return
     */
    @Override
    public List<ScientificResearchProjectDto> getProjectApproves4(ScientificResearchProjectCriteria scientificResearchProjectCriteria,Integer clstep,String leadsignname) {
        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.like("leadSignName","%"+leadsignname+"%"));
        criteria.add(Restrictions.eq("clStep", 3));

        List<ScientificResearchProject> beans = criteria.list();
        List<ScientificResearchProjectDto> dto = new ArrayList<ScientificResearchProjectDto>();
        for(ScientificResearchProject scientificResearchProject : beans){
            ScientificResearchProjectDto scientificResearchProjectDto = DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProject);
          /*  scientificResearchProjectDto.setDeptName(deptmentService.getByDeptId(scientificResearchProject.getDept()).getDeptName());*/
            scientificResearchProjectDto.setDeptName(scientificResearchProject.getDeptName());
            dto.add(scientificResearchProjectDto);
        }
        return dto;
    }

    /**
     * 科管科批量申请流程
     * @param
     * @return
     */
    @Override
    public PagedResult<ScientificResearchProjectDto> getProjectApproves5(ScientificResearchProjectCriteria scientificResearchProjectCriteria) {
        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("clStep", 4));
       /* if(scientificResearchProjectCriteria.getBeginTime() != null && ! "".equals(scientificResearchProjectCriteria.getBeginTime())){
            criteria.add(Restrictions.ge("beginTime", scientificResearchProjectCriteria.getBeginTime()));
        }
        if(scientificResearchProjectCriteria.getEndTime() != null && ! "".equals(scientificResearchProjectCriteria.getEndTime())){
            criteria.add(Restrictions.le("beginTime",scientificResearchProjectCriteria.getEndTime()));
        }
        if(scientificResearchProjectCriteria.getProjectName() != null && !"".equals(scientificResearchProjectCriteria.getProjectName())){
            criteria.add(Restrictions.like("projectName", URLDecoder.decode(scientificResearchProjectCriteria.getProjectName(),"UTF-8"), MatchMode.ANYWHERE));
        }
        if(scientificResearchProjectCriteria.getProjectNo() != null && !"".equals(scientificResearchProjectCriteria.getProjectNo())){
            criteria.add(Restrictions.like("projectNo", scientificResearchProjectCriteria.getProjectNo(), MatchMode.ANYWHERE));
        }
        if(scientificResearchProjectCriteria.getProjectType() != null){
            criteria.add(Restrictions.eq("projectType",scientificResearchProjectCriteria.getProjectType()));
        }
        if(scientificResearchProjectCriteria.getSpecialtyType() != null && !"".equals(scientificResearchProjectCriteria.getSpecialtyType())){
            criteria.add(Restrictions.eq("specialtyType",scientificResearchProjectCriteria.getSpecialtyType()));
        }
        if(scientificResearchProjectCriteria.getProjectStatus() != null && !"".equals(scientificResearchProjectCriteria.getProjectStatus())){
            criteria.add(Restrictions.eq("projectStatus",scientificResearchProjectCriteria.getProjectStatus()));
        }
        if(scientificResearchProjectCriteria.getLeader() != null && !"".equals(scientificResearchProjectCriteria.getLeader())){
            criteria.add(Restrictions.eq("leader", scientificResearchProjectCriteria.getLeader()));
        }
*/
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((scientificResearchProjectCriteria.getPageNumber() - 1) * scientificResearchProjectCriteria.getPageSize()).setMaxResults(scientificResearchProjectCriteria.getPageSize());
        List<ScientificResearchProject> beans = criteria.list();

        List<ScientificResearchProjectDto> dto = new ArrayList<ScientificResearchProjectDto>();
        for(ScientificResearchProject scientificResearchProject : beans){
            ScientificResearchProjectDto scientificResearchProjectDto = DeaneryUtil.convertFromListScientificResearchProjectDto(scientificResearchProject);
            scientificResearchProjectDto.setLeaderName(employeeService.getEmpById(scientificResearchProject.getLeader()).getName());
            scientificResearchProjectDto.setDeputyName(employeeService.getEmpById(scientificResearchProject.getDeputy()).getName());
          /*  scientificResearchProjectDto.setDeptName(deptmentService.getByDeptId(scientificResearchProject.getDept()).getDeptName());*/
            scientificResearchProjectDto.setDeptName(scientificResearchProject.getDeptName());
            dto.add(scientificResearchProjectDto);
        }
        return new PagedResult<ScientificResearchProjectDto>(dto, scientificResearchProjectCriteria.getPageNumber(), scientificResearchProjectCriteria.getPageSize(), totalCount);
    }

//clstep5
    @Override
    public CommonResult nextProject(ApproveFormDto dto,String clstep) {
            ScientificResearchProjectDto scientificResearchProjectDto=this.getIdByFormNo(dto.getFormNo());
            //项目计划库
           // CommonResult ProjectPlanByProjectNo=projectPlanService.updateProjectPlanState(scientificResearchProjectDto.getProjectNo(),"2");
            ProjectPlanInput projectPlanInput=new ProjectPlanInput();
            projectPlanInput.setProjectPlan_Number(scientificResearchProjectDto.getProjectNo());
            projectPlanInput.setProjectPlan_InputYear_Str(DateHelper.formatDate(scientificResearchProjectDto.getYear(),"yyyy-MM-dd"));
            projectPlanInput.setProjectPlan_Name(scientificResearchProjectDto.getProjectName());
            projectPlanInput.setProjectPlan_Content(scientificResearchProjectDto.getProjectPlanInfo());
            projectPlanInput.setProjectPlan_BearUnit(scientificResearchProjectDto.getAssumeCompany());
            projectPlanInput.setProjectPlan_ParticipatingUnit(scientificResearchProjectDto.getJoinComopany());
            projectPlanInput.setProjectPlan_StartTime(scientificResearchProjectDto.getBeginTime());
            projectPlanInput.setProjectPlan_EndTime(scientificResearchProjectDto.getEndTime());
            projectPlanInput.setProjectPlan_Manager(scientificResearchProjectDto.getLeaderName());
            projectPlanInput.setProjectPlan_State("2");//2为立项结束
            List<ProjectPlanInput> inputs=new ArrayList<>();
            inputs.add(projectPlanInput);
            CommonResult inputProjectPlan=projectPlanService.savaProjectApprove(inputs);
            ScientificRPKU scientificRPKU2=scienticRPKUService.getIdByProjectNo(scientificResearchProjectDto.getProjectNo());
            if(scientificRPKU2!= null ) {
                scientificRPKU2.setProjectStatus("2");//立项完成
                scientificRPKURepository.save(scientificRPKU2);
            }
            return super.doApprove(dto, null);
        }
    @Override
    public CommonResult updateProject0(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }
    @Override
    public CommonResult updateProject(ApproveFormDto dto,String empid) {
        if (empid!=""){
       FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent( empid);
            formHeaderRepository.save(header);
          /*  List<Long> emplist = new ArrayList<>();
            if(empid.indexOf(",") >= 0){
                List listid = new ArrayList();
                String[] empids = empid.split(",");
                for (String id : empids) {
                    emplist.add(Long.parseLong(id));
                }
              }else{
                emplist.add(Long.parseLong(empid));
            }
            this.resetNextApprover(dto.getFormNo(), emplist);*/
        }
        return super.doApprove(dto, null);
    }

    @Override
    public CommonResult updateProject2(ApproveFormDto dto) {
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        List<Long> emplist = new ArrayList<>();
        String empid= header.getContent();
        if(empid.indexOf(",") >= 0){
            List listid = new ArrayList();
            String[] empids = empid.split(",");
            for (String id : empids) {
                emplist.add(Long.parseLong(id));
            }
        }else{
            emplist.add(Long.parseLong(empid));
        }
        this.resetNextApprover(dto.getFormNo(), emplist);
        return super.doApprove(dto, null);
    }


    //项目长上传
    @Override
    public CommonResult uploadProjectFile(Long formNo, String fileId, String fileName) {
        ScientificResearchProject approve = this.getByFormNo(formNo);
        ScientficApproveFile file  = new ScientficApproveFile();
        file.setFileId(fileId);
        file.setFileName(fileName);
        approve.getScientficApproveFileList().add(file);
        scientificResearchPeojectRepository.save(approve);
        return  ResultFactory.commonSuccess();
    }

    @Override
    public int getNumberByProjectNo(String projectNo) {
        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        List<ScientificResearchProject> list=criteria.list();
        return list.size();
    }


    /*==================详情============================================================================*/
    @Override
    public ScientificResearchProjectDto getIdByProjectNo(String projectNo){
        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        List<ScientificResearchProject> list=criteria.list();
        if(list!=null && list.size()>0){
            ScientificResearchProjectDto dto=DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            return dto;
        }else{
            return null;
        }
    }

    @Override
    public ScientificResearchProjectDto getIdByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("formNo",formNo));
      //  criteria.add(Restrictions.eq("isActive", true));
        List<ScientificResearchProject> list=criteria.list();
        if(list!=null && list.size()>0){
            ScientificResearchProjectDto dto=DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            return dto;
        }else{
            return null;
        }
    }

    @Override
    public FormProjectApply getIdByProjectNo1(String projectNo) {
        Criteria criteria = getSession().createCriteria(FormProjectApply.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add( Restrictions.or(Restrictions.eq("formStatus", FormStatus.UA), Restrictions.eq("formStatus", FormStatus.AP)));
        criteria.add(Restrictions.eq("isActive", true));
        List<FormProjectApply> list= criteria.list();
        if(list!=null&&list.size()>0) {
            return list.get(0);
        }
        FormProjectApply ss=new FormProjectApply();
        return ss;

    }

    @Override
    public FormDelayApply getIdByProjectNo2(String projectNo) {

          Criteria criteria = getSession().createCriteria(FormDelayApply.class);
          criteria.add(Restrictions.eq("projectNo", projectNo));
          criteria.add( Restrictions.or(Restrictions.eq("formStatus", FormStatus.UA), Restrictions.eq("formStatus", FormStatus.AP)));
          criteria.add(Restrictions.eq("isActive", true));
          List<FormDelayApply> list= criteria.list();
          if(list!=null&&list.size()>0) {
            return list.get(0);
        }
        FormDelayApply ss=new FormDelayApply();
        return ss;
    }

    @Override
    public FormProjectExecution getIdByProjectNo3(String projectNo) {
        Criteria criteria = getSession().createCriteria(FormProjectExecution.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add( Restrictions.or(Restrictions.eq("formStatus",FormStatus.UA),Restrictions.eq("formStatus",FormStatus.AP)));
        criteria.add(Restrictions.eq("isActive", true));
        List<FormProjectExecution> list= criteria.list();
        if(list!=null&&list.size()>0) {
            return list.get(0);
        }
        FormProjectExecution ss=new FormProjectExecution();
        return ss;

    }

    @Override
    public FormProjectAcceptance getIdByProjectNo4(String projectNo) {
        Criteria criteria = getSession().createCriteria(FormProjectAcceptance.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add( Restrictions.or(Restrictions.eq("formStatus", FormStatus.UA), Restrictions.eq("formStatus", FormStatus.AP)));
        criteria.add(Restrictions.eq("isActive", true));

        List<FormProjectAcceptance> list= criteria.list();
        if(list!=null&&list.size()>0) {
            return list.get(0);
        }
        FormProjectAcceptance ss=new FormProjectAcceptance();
        return ss;

    }

    @Override
    public FormDeliverApply getIdByProjectNo5(String projectNo) {
        Criteria cri = getSession().createCriteria(FormDeliverApply.class);
        cri.add(Restrictions.eq("projectNo",projectNo));
        cri.add( Restrictions.or(Restrictions.eq("formStatus", FormStatus.UA), Restrictions.eq("formStatus", FormStatus.AP)));
        cri.add(Restrictions.eq("isActive", true));
        List<FormDeliverApply> list= cri.list();
        if(list!=null&&list.size()>0) {
            return list.get(0);
         }
        FormDeliverApply ss=new FormDeliverApply();
        return ss;
    }

    @Override
    public Boolean judgeProjectNo(String projectNo) {
        boolean boolea=true;
        int count=0;
        Criteria criteria=getSession().createCriteria(ScientificResearchProject.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        List<ScientificResearchProject> list=criteria.list();
        count=list.size();
        if (count>0){
            boolea=false;
        }
        return boolea;
    }

    @Override
    public ScientficApproveFile getFile(String sid) {

        return null;
    }


}
