package com.sunesoft.lemon.deanery.scientificRPKU;

import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.deliverables.domain.FormDeliverApply;
import com.sunesoft.lemon.deanery.productionData.*;
import com.sunesoft.lemon.deanery.project.application.criteria.ScientificResearchProjectCriteria;
import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
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
 * Created by zy on 2016/10/7.
 */
@Service(value = "scienticRPKUService")
public class ScienticRPKUServiceImpl extends GenericHibernateFinder implements ScienticRPKUService {

    /**
     * 科研模块页面展示
     * @param
     * @return
     */

    @Autowired
    ScientificRPKURepository scientificRPKURepository;
    @Override
    public Long addScientificResearchProject(ScientificRPKU s) {
        Long l= scientificRPKURepository.save(s);
        return l;
    }


    @Override
    public ScientificRPKU getIdByProjectNo(String projectNo) {
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add(Restrictions.eq("isActive", true));
        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public ScientificRPKUDto getIdByProjectNoDto(String projectNo) {
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("projectJCStatus","0"));
        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            ScientificRPKUDto scientificRPKUDto= DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            return scientificRPKUDto;
        }
        return null;
    }
    @Override
    public ScientificRPKUDto getIdByProjectNoDto1(String projectNo) {
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add(Restrictions.eq("isActive", true));
        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            ScientificRPKUDto scientificRPKUDto= DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            return scientificRPKUDto;
        }
        return null;
    }
    @Override
    public List<ScientificRPKU> getProjectByformNo(String projectNo,String opinint) {
        Criteria criterion = getSession().createCriteria(ScientificRPKU.class);
        criterion.add(Restrictions.eq("projectNo", projectNo));
        List<ScientificRPKU> beans = criterion.list();
        for (ScientificRPKU bean : beans) {
            bean.setProficientOpinion(opinint);
            scientificRPKURepository.save(bean);
        }
        return beans;
    }
    @Override
    public List<ScientificRPKU> getProjectByformNo1(String projectNo,String instust) {
        Criteria criterion = getSession().createCriteria(ScientificRPKU.class);
        criterion.add(Restrictions.eq("projectNo", projectNo));
        List<ScientificRPKU> beans = criterion.list();
        for (ScientificRPKU bean : beans) {
            bean.setInstructions(instust);
            scientificRPKURepository.save(bean);
        }
        return beans;
    }
    @Override
    public ScientificRPKUDto getByProjectIdDto(Long id) {
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("id",id));
        criteria.add(Restrictions.eq("isActive", true));
        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            ScientificRPKUDto scientificRPKUDto= DeaneryUtil.convertFromListScientificResearchProjectDto(list.get(0));
            return scientificRPKUDto;
        }
        return null;
    }

//检测
    public List<ScientificRPKU> getAllScientificKu() {
        Criteria criteria=this.getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectKTStatus","2"));
        criteria.add(Restrictions.eq("projectJCStatus","0"));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add( Restrictions.or(Restrictions.eq("projectYSStatus","0"),Restrictions.eq("projectYSStatus","1")));
        List<ScientificRPKU> list=criteria.list();
        return list;
    }
//验收
    @Override
    public List<ScientificRPKU> getAllScientificKu1() {
        Criteria criteria=this.getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectYSStatus","0"));
        criteria.add(Restrictions.eq("projectKTStatus","2"));
        criteria.add(Restrictions.eq("isActive", true));
        List<ScientificRPKU> list=criteria.list();
        return list;
    }
//延迟
    @Override
    public List<ScientificRPKU> getAllScientificKu2() {
        Criteria criteria=this.getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectKTStatus","2"));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add( Restrictions.or(Restrictions.eq("projectYSStatus","0"),Restrictions.eq("projectYSStatus","1")));
        List<ScientificRPKU> list=criteria.list();
        return list;
    }
    //导出
    @Override
    public List<ScientificRPKUDto1> getAllScientificKu3() {
        Criteria criteria=this.getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<ScientificRPKU> list=criteria.list();
        List<ScientificRPKUDto1> list1=new ArrayList<>();
        if(list!=null&&list.size()>0) {
            for(ScientificRPKU scientificRPKU : list) {
                ScientificRPKUDto1 scientificRPKUDto1 = DeaneryUtil.convertFromListScientificResearchProjectDto1(scientificRPKU);
                list1.add(scientificRPKUDto1);
            }
            return list1;
        }
        return null;
    }

    @Override
    public void saveByProjectNo(String projectNo,Date Time) {
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add(Restrictions.eq("isActive", true));
        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            ScientificRPKU s=list.get(0);
            s.setEndTime(Time);
            scientificRPKURepository.save(s);
        }
    }

    public PagedResult<ScientificRPKUDto> getScientificResearchProjects(ScientificRPKUCriteria scientificRPKUCriteria)  {


        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("isActive", true));
        if(scientificRPKUCriteria.getNiandu() != null && ! "".equals(scientificRPKUCriteria.getNiandu())){
            criteria.add(Restrictions.like("niandu_str", scientificRPKUCriteria.getNiandu_Str(), MatchMode.ANYWHERE));
        }
        if(scientificRPKUCriteria.getProjectName() != null && !"".equals(scientificRPKUCriteria.getProjectName())){
            try {
                criteria.add(Restrictions.like("projectName", URLDecoder.decode(scientificRPKUCriteria.getProjectName(), "UTF-8"), MatchMode.ANYWHERE));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if(scientificRPKUCriteria.getProjectNo() != null && !"".equals(scientificRPKUCriteria.getProjectNo())){
            criteria.add(Restrictions.like("projectNo", scientificRPKUCriteria.getProjectNo(), MatchMode.ANYWHERE));
        }
        if(scientificRPKUCriteria.getAssumeCompany() != null && !"".equals(scientificRPKUCriteria.getAssumeCompany())){
            criteria.add(Restrictions.like("assumeCompany", scientificRPKUCriteria.getAssumeCompany(), MatchMode.ANYWHERE));
        }
        if(scientificRPKUCriteria.getProjectType() != null&& !"".equals(scientificRPKUCriteria.getProjectType())){
            criteria.add(Restrictions.eq("projectType",scientificRPKUCriteria.getProjectType()));
        }
        if(scientificRPKUCriteria.getProjectYXStatus() != null && !"".equals(scientificRPKUCriteria.getProjectYXStatus())){
            criteria.add(Restrictions.eq("projectYXStatus",scientificRPKUCriteria.getProjectYXStatus()));
        }
        if(scientificRPKUCriteria.getDeptName() != null && !"".equals(scientificRPKUCriteria.getDeptName())){
            criteria.add(Restrictions.eq("dept",Long.valueOf(scientificRPKUCriteria.getDeptName())));
        }
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((scientificRPKUCriteria.getPageNumber() - 1) * scientificRPKUCriteria.getPageSize()).setMaxResults(scientificRPKUCriteria.getPageSize());
        List<ScientificRPKU> beans = criteria.list();
        List<ScientificRPKUDto> dto = new ArrayList<ScientificRPKUDto>();
        for(ScientificRPKU scientificRPKU : beans){
            ScientificRPKUDto scientificRPKUDto = DeaneryUtil.convertFromListScientificResearchProjectDto(scientificRPKU);
            dto.add(scientificRPKUDto);
        }
        return new PagedResult<ScientificRPKUDto>(dto, scientificRPKUCriteria.getPageNumber(), scientificRPKUCriteria.getPageSize(), totalCount);
    }

    @Override
    public Long update(String projectNo,String YX,String KT,String YS,String SB,String JC) {
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add(Restrictions.eq("isActive", true));


        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            ScientificRPKU scientificRPKU=list.get(0);
            if(YX!=null){
                scientificRPKU.setProjectStatus("2");
            }
            if(YS!=null) {
                scientificRPKU.setProjectYSStatus("2");
                scientificRPKU.setProjectYXStatus("2");
            }
            if(KT!=null) {
                scientificRPKU.setProjectKTStatus("2");
            }
            if(JC!=null) {
                scientificRPKU.setProjectJCStatus("2");
            }
            if(SB!=null) {
                scientificRPKU.setProjectSBStatus("2");
            }
            scientificRPKURepository.save(scientificRPKU);
        }
        return 1l;
    }

    @Override
    public Long updateByProjectApply(String projectNo,String YX,String KT,String YS,String SB,String JC) {
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add(Restrictions.eq("isActive", true));


        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            ScientificRPKU scientificRPKU=list.get(0);
            if(YX!=null){
                scientificRPKU.setProjectStatus("1");
            }
            if(YS!=null) {
                scientificRPKU.setProjectYSStatus("1");
            }
            if(KT!=null) {
                scientificRPKU.setProjectKTStatus("1");
            }
            if(JC!=null) {
                scientificRPKU.setProjectJCStatus("1");
            }
            if(SB!=null) {
                scientificRPKU.setProjectSBStatus("1");
            }
            scientificRPKURepository.save(scientificRPKU);
        }
        return 2l;
    }

    @Override
    public Long reset(String projectNo, String YX, String KT, String YS, String SB, String JC) {
        Criteria criteria = getSession().createCriteria(ScientificRPKU.class);
        criteria.add(Restrictions.eq("projectNo",projectNo));
        criteria.add(Restrictions.eq("isActive", true));

        List<ScientificRPKU> list= criteria.list();
        if(list!=null&&list.size()>0) {
            ScientificRPKU scientificRPKU=list.get(0);
            if(YX!=null){
                scientificRPKU.setProjectStatus("0");
                scientificRPKU.setProjectYXStatus("0");
            }
            if(YS!=null) {
                scientificRPKU.setProjectYSStatus("0");
            }
            if(KT!=null) {
                scientificRPKU.setProjectKTStatus("0");
                scientificRPKU.setProjectYXStatus("1");
            }
            if(JC!=null) {
                scientificRPKU.setProjectJCStatus("0");
            }
            if(SB!=null) {
                scientificRPKU.setProjectSBStatus("0");
            }
            scientificRPKURepository.save(scientificRPKU);
        }
        return 2l;
    }

    @Override
    public List<ScientificRPKUDto> queryProjectApprove(String number) {
        List<ScientificRPKU> list=scientificRPKURepository.queryProjectApprove(number);
        List<ScientificRPKUDto> listDto=new ArrayList<>();
        for(ScientificRPKU rpku:list){
            ScientificRPKUDto scientificRPKUDto = DeaneryUtil.convertFromListScientificResearchProjectDto(rpku);
            listDto.add(scientificRPKUDto);
        }
        return listDto;
    }


}
