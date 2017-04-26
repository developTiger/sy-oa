package com.sunesoft.lemon.deanery.project.domain.infrastructure.hibernate;

import com.sunesoft.lemon.deanery.project.application.criteria.AccessCriteria;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchPeojectRepository;
import com.sunesoft.lemon.deanery.project.domain.ScientificResearchProject;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
@Service(value = "scientificResearchProjectRepository")
public class ScientificResearchProjectRepositoryImpl extends GenericHibernateRepository<ScientificResearchProject,Long> implements ScientificResearchPeojectRepository {

    @Override
    public List<Map<String, Object>> queryAccessLists(String formKind, AccessCriteria accessCriteria) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * ");
        sb.append("  from (select t.*,");
        sb.append("t1.counter_id,");
        sb.append("t1.counter_name,");
        sb.append("t1.count_remark,");
        sb.append("t1.count_result,");
        sb.append("t2.app_user_id,");
        sb.append("t2.app_name,");
        sb.append("t2.approve_status ");
        sb.append(" from syy_oa_fm_header t ");
        sb.append(" left join syy_oa_form_countersign t1  on t.id = t1.form_no ");
        sb.append(" left join syy_oa_fm_approver t2 on t2.form_no = t.id and t2.cl_step = t.cl_step and t2.approve_status = ? ) a ");
        sb.append(" where a.form_kind = ? and a.cl_step in (3,4,5,6)");
        sb.append(" and ((a.form_status = ? and a.app_user_id = ?) or ");
        sb.append(" (a.form_status = ? and a.counter_id = ?)) ");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setLong(0, ApproveStatus.U.ordinal());
        query.setParameter(1,formKind);
        query.setLong(2, FormStatus.UA.ordinal());
        query.setLong(3,accessCriteria.getCurrentUserId());
        query.setLong(4,FormStatus.NC.ordinal());
        query.setLong(5,accessCriteria.getCurrentUserId());
        return query.list();
    }

    @Override
    public ScientificResearchProject getByFormNo(Long formNo) {
        String sql = "from ScientificResearchProject where formNo = ?";
        Query query = this.getSession().createQuery(sql);
        query.setParameter(0,formNo);
        List<ScientificResearchProject> lists = query.list();
        return lists != null && lists.size() > 0 ? lists.get(0) : null;
    }

    /**
     * 修改项目状态
     * @param id 项目序号
     * @param projectStatus 项目状态
     * @return
     */
    @Override
    public Long modifyProjectStatus(Long id, String projectStatus) {
        ScientificResearchProject scientificResearchProject = this.get(id);
        scientificResearchProject.setProjectStatus(projectStatus);
        return this.save(scientificResearchProject);
    }

    /**
     * 修改项目截止时间
     * @param id
     * @param endTime 项目截止时间
     * @return
     */
    @Override
    public Long modifyEndTime(Long id, Date endTime) {
        ScientificResearchProject scientificResearchProject = this.get(id);
        scientificResearchProject.setEndTime(endTime);
        return this.save(scientificResearchProject);
    }

    @Override
    public List<Map<String, Object>> projectReportByStatus() {
        StringBuffer sb = new StringBuffer("SELECT t.project_yxstatus,count(1) project_num FROM syy_oa_scientific_ku t where t.IS_ACTIVE = 1 and t.project_yxstatus!=0  GROUP BY t.project_yxstatus ");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> projectReportByType() {
        StringBuffer sb = new StringBuffer("SELECT t.project_type,count(1) project_num FROM syy_oa_scientific_ku t where t.IS_ACTIVE = 1 GROUP BY t.project_type");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> projectReportByDept() {
      /*  StringBuffer sb = new StringBuffer("SELECT a.*,b.dept_name FROM (SELECT t.department,count(1) project_num FROM syy_oa_scientific_project t where t.IS_ACTIVE = 1 GROUP BY t.department) a LEFT JOIN syy_oa_hr_dept b on a.department = b.id");*/
         StringBuffer sb = new StringBuffer("SELECT t.DEPTNAME,t.DEPARTMENT, COUNT(1) project_num FROM syy_oa_scientific_ku t WHERE t.IS_ACTIVE = 1 GROUP BY t.DEPTNAME,t.DEPARTMENT");
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> projectReportByYear() {
        //to_char(t.begin_time,'yyyy')
        StringBuffer sb = new StringBuffer();
        if("Oracle10gDialect".equals(this.getDialectName())){
            sb.append("SELECT t.niandu_str as YEAR,COUNT(1) project_num FROM syy_oa_scientific_ku t WHERE t.IS_ACTIVE = 1 GROUP BY t.niandu_str");
        }else if("MySQL5Dialect".equals(this.getDialectName())){
            sb.append("SELECT DATE_FORMAT(t.begin_time,'%y') year,count(1) project_num FROM syy_oa_scientific_project t where t.IS_ACTIVE = 1 GROUP BY DATE_FORMAT(t.begin_time,'%y')");
        }
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

}
