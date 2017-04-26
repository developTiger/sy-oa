package com.sunesoft.lemon.deanery.projectPlanInput.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputDate;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pxj on 2016/9/12.
 */
@Service(value = "projectPlanInputRepository")
public class ProjectPlanInputRepositoryImpl extends GenericHibernateRepository<ProjectPlanInputDate,Long> implements ProjectPlanInputRepository{

    @Override
    public List<ProjectPlanInputDate> queryProjectPlan() {
        Criteria criteria = this.getSession().createCriteria(ProjectPlanInputDate.class);
        criteria.add(Restrictions.eq("projectPlan_State","0"));
        List<ProjectPlanInputDate> list = criteria.list();
        return list;
    }

    @Override
    public Long getEmployeeId(String email) {
        Long id=null;
        Criteria criteria = this.getSession().createCriteria(Employee.class);
        if (!StringUtils.isNullOrWhiteSpace(email)) {
            criteria.add(Restrictions.eq("email", email));
            Employee employee = (Employee) criteria.uniqueResult();
            if(employee!=null){
                id=employee.getId();
            }
        }
        return id;
    }

    @Override
    public List<ProjectPlanInputDate> queryApproveProjectPlan(String id) {
        List<ProjectPlanInputDate> list=null;
        Criteria criteria = this.getSession().createCriteria(ProjectPlanInputDate.class);
        if(!StringUtils.isNullOrWhiteSpace(id)) {
            String ids[] = id.split(",");
            Long l[]=new  Long[ids.length];
            for (int i = 0; i < ids.length; i++) {
                //criteria.add(Restrictions.eq("id", Long.parseLong(ids[i])));
                   l[i]=Long.parseLong(ids[i]);
            }
            criteria.add(Restrictions.in("id",l));
          list  = criteria.list();
        }
        return list;
    }

    @Override
    public ProjectPlanInputDate getByProjectNo(String projectNo) {
        Criteria criteria = this.getSession().createCriteria(ProjectPlanInputDate.class);
        criteria.add(Restrictions.eq("projectPlan_Number",projectNo));
        ProjectPlanInputDate projectPlanInputDate= (ProjectPlanInputDate) criteria.uniqueResult();
        return projectPlanInputDate;
    }

    @Override
    public void updateProjectPlanState(String projectNumber,String number) {
      ProjectPlanInputDate projectPlanInputDate= this.getByProjectNo(projectNumber);
      projectPlanInputDate.setProjectPlan_State(number);
        this.save(projectPlanInputDate);
    }

    @Override
    public List<ProjectPlanInputDate> getAllProjectPlan() {
        //Criteria criteria=this.getSession().createCriteria(ProjectPlanInputDate.class);
        Criteria criteria = this.getSession().createCriteria(ProjectPlanInputDate.class);
        criteria.add(Restrictions.eq("isActive",true));
        List<ProjectPlanInputDate> projectPlanInputDates=criteria.list();
        return projectPlanInputDates;
    }

/*    @Override
    public void updateProjectPlan(ProjectPlanInputDate projectPlanInputDate) {
        Criteria criteria = this.getSession().createCriteria(ProjectPlanInputDate.class);

    }*/
}
