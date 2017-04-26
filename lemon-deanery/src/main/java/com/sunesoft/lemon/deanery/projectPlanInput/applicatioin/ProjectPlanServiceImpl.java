package com.sunesoft.lemon.deanery.projectPlanInput.applicatioin;

import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResult;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanOutput;
import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.factory.ProjectDeaneryUtil;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputDate;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pxj on 2016/9/9.
 */
@Service("projectPlanService")
public class ProjectPlanServiceImpl extends GenericHibernateFinder implements ProjectPlanService{

    @Autowired
    ProjectPlanInputRepository projectPlanInputRepository;

    @Override
    public CommonResult save(List<ProjectPlanInput> list) {
        for (int i = 0; i < list.size(); i++) {
            ProjectPlanInputDate projectPlanInputDate=new ProjectPlanInputDate();
//            BeanUtils.copyProperties(list.get(i),scientificResearchProject);
            projectPlanInputDate.setProjectPlan_InputYear(DateHelper.parse(list.get(i).getProjectPlan_InputYear_Str(),"yyyy"));
            projectPlanInputDate.setProjectPlan_No((list.get(i).getProjectPlan_No()));
            projectPlanInputDate.setProjectPlan_Name(list.get(i).getProjectPlan_Name());
            projectPlanInputDate.setProjectPlan_Type(list.get(i).getProjectPlan_Type());
            projectPlanInputDate.setProjectPlan_Content(list.get(i).getProjectPlan_Content());
            projectPlanInputDate.setProjectPlan_BearUnit(list.get(i).getProjectPlan_BearUnit());
            projectPlanInputDate.setProjectPlan_ParticipatingUnit(list.get(i).getProjectPlan_ParticipatingUnit());
           // projectPlanInputDate.setProjectPlan_StartEndTime(list.get(i).getProjectPlan_StartEndTime());
            projectPlanInputDate.setProjectPlan_Manager(list.get(i).getProjectPlan_Manager());
            projectPlanInputDate.setProjectPlan_Remark(list.get(i).getProjectPlan_Remark());
            projectPlanInputDate.setProjectPlan_Email(list.get(i).getProjectPlan_Email());
            projectPlanInputDate.setProjectPlan_Number(list.get(i).getProjectPlan_Number());
            projectPlanInputDate.setProjectPlan_EndTime(list.get(i).getEndTime());
            projectPlanInputDate.setProjectPlan_StartTime(list.get(i).getStartTime());
            projectPlanInputDate.setProjectPlan_State("0");
           // scientificResearchProject.setProjectPlanInfoTxt(list.get(i).getProjectPlanInfo().getBytes(Charset.forName("UTF-8")));
            projectPlanInputRepository.save(projectPlanInputDate);
        }
        return ResultFactory.commonSuccess();
        }

    @Override
    public CommonResult savaProjectApprove(List<ProjectPlanInput> list) {
        for (int i = 0; i < list.size(); i++) {
            ProjectPlanInputDate projectPlanInputDate=new ProjectPlanInputDate();
//            BeanUtils.copyProperties(list.get(i),scientificResearchProject);
            projectPlanInputDate.setProjectPlan_InputYear(DateHelper.parse(list.get(i).getProjectPlan_InputYear_Str(),"yyyy"));
            projectPlanInputDate.setProjectPlan_No((list.get(i).getProjectPlan_No()));
            projectPlanInputDate.setProjectPlan_Name(list.get(i).getProjectPlan_Name());
            projectPlanInputDate.setProjectPlan_Type(list.get(i).getProjectPlan_Type());
            projectPlanInputDate.setProjectPlan_Content(list.get(i).getProjectPlan_Content());
            projectPlanInputDate.setProjectPlan_BearUnit(list.get(i).getProjectPlan_BearUnit());
            projectPlanInputDate.setProjectPlan_ParticipatingUnit(list.get(i).getProjectPlan_ParticipatingUnit());
            // projectPlanInputDate.setProjectPlan_StartEndTime(list.get(i).getProjectPlan_StartEndTime());
            projectPlanInputDate.setProjectPlan_Manager(list.get(i).getProjectPlan_Manager());
            projectPlanInputDate.setProjectPlan_Remark(list.get(i).getProjectPlan_Remark());
            projectPlanInputDate.setProjectPlan_Email(list.get(i).getProjectPlan_Email());
            projectPlanInputDate.setProjectPlan_Number(list.get(i).getProjectPlan_Number());
            projectPlanInputDate.setProjectPlan_EndTime(list.get(i).getProjectPlan_EndTime());
            projectPlanInputDate.setProjectPlan_StartTime(list.get(i).getProjectPlan_StartTime());
            projectPlanInputDate.setProjectPlan_State(list.get(i).getProjectPlan_State());
            // scientificResearchProject.setProjectPlanInfoTxt(list.get(i).getProjectPlanInfo().getBytes(Charset.forName("UTF-8")));
            projectPlanInputRepository.save(projectPlanInputDate);
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<ProjectPlanInput> getProjectPlan(ProjectPlanInput projectPlanInput) throws UnsupportedEncodingException {
        Criteria criterion = getSession().createCriteria(ProjectPlanInputDate.class);
        criterion.add(Restrictions.eq("isActive", true));
        String projectPlan_type="";
       // String wrn = URLDecoder.decode(projectPlanInput.getProjectPlan_Name(), "UTF-8");
     //   String projectPlan_name =  URLDecoder.decode(projectPlanInput.getProjectPlan_Name(),"UTF-8");
        if (!StringUtils.isNullOrWhiteSpace(projectPlanInput.getProjectPlan_Type())){
             projectPlan_type = URLDecoder.decode(projectPlanInput.getProjectPlan_Type(),"UTF-8");
        }
        String startTime = URLDecoder.decode(projectPlanInput.getStartTime_Str(),"UTF-8");
        String endTime = URLDecoder.decode(projectPlanInput.getEndTime_Str(),"UTF-8");
        if (!StringUtils.isNullOrWhiteSpace(startTime)){
            Date st= DateHelper.parse(startTime,"yyyy");
            criterion.add(Restrictions.ge("projectPlan_InputYear",st));
        }
        if (!StringUtils.isNullOrWhiteSpace(endTime)){
            Date et= DateHelper.parse(endTime,"yyyy");
            criterion.add(Restrictions.le("projectPlan_InputYear",et));
        }
        if(!StringUtils.isNullOrWhiteSpace(projectPlanInput.getProjectPlan_Name())){
            criterion.add(Restrictions.like("projectPlan_Name",URLDecoder.decode(projectPlanInput.getProjectPlan_Name(),"UTF-8"),MatchMode.ANYWHERE));
        }
        if(!StringUtils.isNullOrWhiteSpace(projectPlan_type)){
            criterion.add(Restrictions.like("projectPlan_Type",projectPlan_type,MatchMode.ANYWHERE));
        }
        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((projectPlanInput.getPageNumber() - 1) * projectPlanInput.getPageSize()).setMaxResults(projectPlanInput.getPageSize());
        List<ProjectPlanInputDate> list = criterion.list();
        List<ProjectPlanInput> projectResultDtos = new ArrayList<ProjectPlanInput>();
        for (ProjectPlanInputDate p : list){
            projectResultDtos.add(ProjectDeaneryUtil.converFromListProjectPlanDto(p));
        }
        return new PagedResult<ProjectPlanInput>(projectResultDtos,projectPlanInput.getPageNumber(),projectPlanInput.getPageSize(),totalCount);
    }

    @Override
    public PagedResult<ProjectPlanInput> getProjectPlan1(ProjectPlanInput projectPlanInput) {
        Criteria criterion = getSession().createCriteria(ProjectPlanInputDate.class);
        criterion.add(Restrictions.eq("isActive", true));
         criterion.add(Restrictions.eq("projectPlan_State","0"));//请放开代码*/
        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
       // criterion.setFirstResult((projectPlanInput.getPageNumber() - 1) * projectPlanInput.getPageSize()).setMaxResults(projectPlanInput.getPageSize());
        List<ProjectPlanInputDate> list = criterion.list();
        List<ProjectPlanInput> projectResultDtos = new ArrayList<ProjectPlanInput>();
        for (ProjectPlanInputDate p : list){
            projectResultDtos.add(ProjectDeaneryUtil.converFromListProjectPlanDto(p));
        }
        return new PagedResult<ProjectPlanInput>(projectResultDtos,projectPlanInput.getPageNumber(),totalCount,totalCount);
    }

    /**
     * 查询项目计划
     * @return
     */
    @Override
    public List<ProjectPlanInput> queryProjectPlan() {
        List<ProjectPlanInput> listInput=new ArrayList<>();
       List<ProjectPlanInputDate> list=projectPlanInputRepository.queryProjectPlan();
        for(ProjectPlanInputDate listDate:list){
            ProjectPlanInput listImput = ProjectDeaneryUtil.converFromListProjectPlanDto(listDate);
            String email=listImput.getProjectPlan_Email();
            listImput.setMenagerid(projectPlanInputRepository.getEmployeeId(email));
            listInput.add(listImput);
        }
        return listInput;
    }
    /**
     * 立项查询项目计划
     * @return
     */
    @Override
    public List<ProjectPlanInput> queryApproveProjectPlan(String id) {
        List<ProjectPlanInput> listInput=new ArrayList<>();
        List<ProjectPlanInputDate> list=projectPlanInputRepository.queryApproveProjectPlan(id);
        for(ProjectPlanInputDate listDate:list){
            ProjectPlanInput listImput = ProjectDeaneryUtil.converFromListProjectPlanDto(listDate);
            String email=listImput.getProjectPlan_Email();
            listImput.setMenagerid(projectPlanInputRepository.getEmployeeId(email));
            listInput.add(listImput);
        }
        return listInput;
    }
    @Override
    public ProjectPlanInput getByIdProjectPlanInput(Long planId) {
        ProjectPlanInput projectPlanInput=null;
        ProjectPlanInputDate  projectPlanInputDate= projectPlanInputRepository.get(planId);
        if (projectPlanInputDate !=null){
             projectPlanInput = ProjectDeaneryUtil.converFromListProjectPlanDto(projectPlanInputDate);
            String email=projectPlanInput.getProjectPlan_Email();
            projectPlanInput.setMenagerid(projectPlanInputRepository.getEmployeeId(email));
           // listInput.add(listImput);
        }
        return projectPlanInput;
    }

    @Override
    public Long addOrUpdateProjectPlan(ProjectPlanInput projectPlanInput) {
        Long id = 0L;
        ProjectPlanInputDate projectPlanInputDate = ProjectDeaneryUtil.converFromListProjectPlanDate(projectPlanInput);
        projectPlanInputDate.setIsActive(true);
        projectPlanInputDate.setProjectPlan_State("0");//0表示新增
        if(null == projectPlanInputDate.getId() || projectPlanInputDate.getId().equals(0l)){
            id = projectPlanInputRepository.save(projectPlanInputDate);
        }
        else{
            id = projectPlanInput.getId();
            projectPlanInputRepository.save(projectPlanInputDate);
          /*  List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id,"syy_oa_patent");
            for(Prizewinner p : prizewinners){
                prizewinnerRepository.delete(p.getId());
            }*/

        }
      /*  String[] infos = info.split(",");
        for (String in : infos) {
            prizewinner = this.getPrizeWinner(id,info, in);
            prizewinnerRepository.save(prizewinner);
        }*/
        return id;
    }

    /**
     * 更加项目计划编号查询项目计划信息
     * @param projectNo
     * @return
     */
    @Override
    public ProjectPlanInput getProjectPlanByProjectNo(String projectNo) {
        ProjectPlanInput  projectPlanInput=new ProjectPlanInput();
        ProjectPlanInputDate  projectPlanInputDate= projectPlanInputRepository.getByProjectNo(projectNo);
        if (projectPlanInputDate !=null){
            projectPlanInput = ProjectDeaneryUtil.converFromListProjectPlanDto(projectPlanInputDate);
            String email=projectPlanInput.getProjectPlan_Email();
            projectPlanInput.setMenagerid(projectPlanInputRepository.getEmployeeId(email));
        }
        return projectPlanInput;
    }

    @Override
    public CommonResult updateProjectPlanState(String projectNumber,String number) {
       projectPlanInputRepository.updateProjectPlanState(projectNumber,number);
        return   ResultFactory.commonSuccess();
    }

    //导出
    @Override
    public List<ProjectPlanOutput> getAllPlan() {
        Criteria criteria=this.getSession().createCriteria(ProjectPlanInputDate.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<ProjectPlanInputDate> list=criteria.list();
        List<ProjectPlanOutput> list1=new ArrayList<>();
        if(list!=null&&list.size()>0) {
            for(ProjectPlanInputDate ss : list) {
                ProjectPlanOutput projectPlanOutput = ProjectDeaneryUtil.converFromListProjectPlanDto1(ss);
                list1.add(projectPlanOutput);
            }
            return list1;
        }
        return null;
    }

}
