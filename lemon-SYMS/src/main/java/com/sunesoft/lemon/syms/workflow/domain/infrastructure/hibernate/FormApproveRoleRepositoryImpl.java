package com.sunesoft.lemon.syms.workflow.domain.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRole;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRoleRepository;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


/**
 * Created by zhouz on 2016/5/30.
 */
@Service("formApproveRoleRepository")
public class FormApproveRoleRepositoryImpl extends GenericHibernateRepository<FormApproveRole,Long> implements FormApproveRoleRepository {

    @Override
    public HashMap<Long, String> getApprovers(Long roleId) {
        String hql = "select emp.id,emp.name from FormApproveRole inner join Employee emp " +
                "on emp_id = emp.id" ;
               // "where APP_ROLE_ID = " + roleId;
        //Query query = getSession().createQuery(hql);
        SQLQuery sqlQuery  =  getSession().createSQLQuery(
                " select EMP.ID,EMP.REAL_NAME from SYY_OA_FM_ROLE_APPROVER   ar" +
                        " inner join SYY_OA_HR_EMPLOYEES   emp on AR.EMP_ID = EMP.ID " +
                        "where AR.APP_ROLE_ID = "+roleId );
        List<Object[]> links = sqlQuery.list();
        //List<Object[]> links = query.list();
        HashMap<Long,String> emps = new HashMap<>();
        for(Object[] link : links) {
            Long id =  ((BigDecimal)link[0]).longValue();
            String name = link[1].toString();
            emps.put(id,name);
        }
        return emps;
    }
}
