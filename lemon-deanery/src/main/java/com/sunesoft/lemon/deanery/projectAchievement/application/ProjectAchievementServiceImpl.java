package com.sunesoft.lemon.deanery.projectAchievement.application;

import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.projectAchievement.application.criteria.ProjectAchievementCriteria;
import com.sunesoft.lemon.deanery.projectAchievement.application.dtos.ProjectAchievementDto;
import com.sunesoft.lemon.deanery.projectAchievement.application.dtos.ProjectAchievementDto2;
import com.sunesoft.lemon.deanery.projectAchievement.domain.ProjectAchievement;
import com.sunesoft.lemon.deanery.projectAchievement.domain.ProjectAchievementRepository;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJ003 on 2016/10/19.
 */
@Service("ProjectAchievementService")
public class ProjectAchievementServiceImpl extends FormBase2<ProjectAchievement,ProjectAchievementDto>
        implements ProjectAchievementService {

    @Autowired
    ProjectAchievementRepository projectAchievementRepository;

    @Override
    protected CommonResult save(ProjectAchievement projectAchievement) {
        return null;
    }

    @Override
    protected CommonResult update(ProjectAchievement projectAchievement) {
        return null;
    }

    @Override
    protected ProjectAchievement ConvetDto(ProjectAchievementDto Dto) {
        ProjectAchievement projectAchievement=new ProjectAchievement();
        BeanUtils.copyProperties(Dto,projectAchievement);
        projectAchievement.setProjectPlanInfoTxt(Dto.getProjectPlanInfo().getBytes(Charset.forName("UTF-8")));

        return projectAchievement;
    }

    @Override
    protected String getSummery(ProjectAchievement projectAchievement) {
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

    @Override
    protected ProjectAchievement getByFormNo(Long formNo) {
        return null;
    }

    @Override
    public ProjectAchievementDto getFormByFormNo(Long formNo) {
        return null;
    }
    @Override
    public PagedResult<ProjectAchievementDto> getCommonDrivers(ProjectAchievementCriteria projectAchievementCriteria){
        Criteria criteria=getSession().createCriteria(ProjectAchievement.class);
        if(projectAchievementCriteria.getProjectName()!=null && !projectAchievementCriteria.getProjectName().equals("")){
            criteria.add(Restrictions.like("projectName", "%"+projectAchievementCriteria.getProjectName()+"%"));
        }
        if(projectAchievementCriteria.getNianduTime()!=null && !projectAchievementCriteria.getNianduTime().equals("")){
            projectAchievementCriteria.setNiandu(DateHelper.parse3(projectAchievementCriteria.getNianduTime()));
            criteria.add(Restrictions.eq("niandu", projectAchievementCriteria.getNiandu()));
        }
        if(projectAchievementCriteria.getProjectNo()!=null && !projectAchievementCriteria.getProjectNo().equals("")){
            criteria.add(Restrictions.like("projectNo","%"+projectAchievementCriteria.getProjectNo()+"%"));
        }
        if(projectAchievementCriteria.getAssumeCompany()!=null && !projectAchievementCriteria.getAssumeCompany().equals("")){
            criteria.add(Restrictions.like("assumeCompany", "%"+projectAchievementCriteria.getAssumeCompany()+"%"));
        }
        if(projectAchievementCriteria.getSpecialtyType()!=null && !projectAchievementCriteria.getSpecialtyType().equals("")){
            criteria.add(Restrictions.eq("specialtyType", projectAchievementCriteria.getSpecialtyType()));
        }
        if(projectAchievementCriteria.getBeginTime()!=null && !projectAchievementCriteria.getBeginTime().equals("")){
            projectAchievementCriteria.setBeginDate(DateHelper.parse2(projectAchievementCriteria.getBeginTime()));
            criteria.add(Restrictions.ge("awardDate", projectAchievementCriteria.getBeginDate()));
        }
        if(projectAchievementCriteria.getEndTime()!=null && !projectAchievementCriteria.getEndTime().equals("")){
            projectAchievementCriteria.setEndDate(DateHelper.parse2(projectAchievementCriteria.getEndTime()));
            criteria.add(Restrictions.le("awardDate", projectAchievementCriteria.getEndDate()));
        }
        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((projectAchievementCriteria.getPageNumber() - 1) * projectAchievementCriteria.getPageSize()).setMaxResults(projectAchievementCriteria.getPageSize());
        List<ProjectAchievement> list=criteria.list();
        List<ProjectAchievementDto> dtos=new ArrayList<>();
        for(ProjectAchievement projectAchievement : list){
            ProjectAchievementDto dto=convertToDto(projectAchievement);
            dtos.add(dto);
        }
        return new PagedResult<ProjectAchievementDto>(dtos,projectAchievementCriteria.getPageNumber(),projectAchievementCriteria.getPageSize(),totalCount);
    }

    public ProjectAchievementDto convertToDto(ProjectAchievement projectAchievement){
        ProjectAchievementDto dto=new ProjectAchievementDto();
        BeanUtils.copyProperties(projectAchievement,dto);
        dto.setBeginTime(DateHelper.formatDate(dto.getBeginDate()));
        dto.setEndTime(DateHelper.formatDate(dto.getEndDate()));
        dto.setDeliverBeginTime(DateHelper.formatDate(dto.getDeliverBeginDate()));
        dto.setDeliverEndTime(DateHelper.formatDate(dto.getDeliverEndDate()));
        dto.setNianduTime(DateHelper.formatDate2(dto.getNiandu()));
        dto.setAwardTime(DateHelper.formatDate(dto.getAwardDate()));
        try {
            if(projectAchievement.getProjectPlanInfoTxt()!=null){
                dto.setProjectPlanInfo(new String(projectAchievement.getProjectPlanInfoTxt(), "UTF-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }
    @Override
    public ProjectAchievementDto getById(String id){
        Criteria criteria=getSession().createCriteria(ProjectAchievement.class);
        criteria.add(Restrictions.eq("id", Long.parseLong(id)));
        ProjectAchievement projectAchievement=(ProjectAchievement)criteria.uniqueResult();
        ProjectAchievementDto dto=convertToDto(projectAchievement);

        return dto;
    }
    @Override
    public CommonResult saveByDto(ProjectAchievementDto dto){
        ProjectAchievement projectAchievement=projectAchievementRepository.get(dto.getId());
        projectAchievement.setNiandu(DateHelper.parse3(dto.getNianduTime()));
        projectAchievement.setProjectNo(dto.getProjectNo());
        projectAchievement.setProjectName(dto.getProjectName());
        projectAchievement.setProjectPlanInfoTxt(dto.getProjectPlanInfo().getBytes(Charset.forName("UTF-8")));
        projectAchievement.setAssumeCompany(dto.getAssumeCompany());
        projectAchievement.setJoinComopany(dto.getJoinComopany());
        projectAchievement.setBeginDate(DateHelper.parse2(dto.getBeginTime()));
        projectAchievement.setEndDate(DateHelper.parse2(dto.getEndTime()));
        projectAchievement.setLeader(dto.getLeader());
        projectAchievement.setLeaderName(dto.getLeaderName());
        projectAchievement.setRank(dto.getRank());
        projectAchievement.setGrade(dto.getGrade());
        projectAchievement.setIssuing_unit(dto.getIssuing_unit());
        projectAchievementRepository.save(projectAchievement);
        return ResultFactory.commonSuccess();
    }

    @Override
    public  List<SpecialtyType> getAllSpecialtyType(){
        Criteria criterion = getSession().createCriteria(SpecialtyType.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<SpecialtyType> specialtyTypes = criterion.list();
        return specialtyTypes;
    }

    @Override
    public List<ProjectAchievementDto2> getAcievementList() {
        Criteria criterion = getSession().createCriteria(ProjectAchievement.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<ProjectAchievement> list = criterion.list();
        List<ProjectAchievementDto2> list1=new ArrayList<>();
        if(list!=null && list.size()>0){
            for (ProjectAchievement s : list) {
                ProjectAchievementDto2 dto = DeaneryUtil.convertFromProjectAchievementDto2(s);
                if(s.getRank()==1){
                    dto.setRank1("国家级");
                }
                if(s.getRank()==2){
                    dto.setRank1("省部级");
                }
                if(s.getRank()==3){
                    dto.setRank1("市级");
                }
                if(s.getRank()==4){
                    dto.setRank1("油田公司级");
                }
                if(s.getRank()==5){
                    dto.setRank1("院级");
                }
                if(s.getGrade()==1){
                    dto.setGrade1("特等奖");
                }
                if(s.getGrade()==2){
                    dto.setGrade1("一等奖");
                }
                if(s.getGrade()==3){
                    dto.setGrade1("二等奖");
                }
                if(s.getGrade()==4){
                    dto.setGrade1("三等奖");
                }
                list1.add(dto);
            }
            return list1;
        }else{
            return null;
        }
    }

}
