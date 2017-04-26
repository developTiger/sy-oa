package com.sunesoft.lemon.syms.eHr.domain.empInfo.infrastructure.hibernate;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.AttendEmpDto;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/5/12.
 */
@Service("employeeRepository")
public class EmployeeRepositoryImpl extends GenericHibernateRepository<Employee,Long> implements EmployeeRepository {


    @Override
    public String getLoginName(Long empId) {
        String hql =  "select loginName from Employee where id = "+ empId ;
        List<Object> objects= getSession().createQuery(hql).list();
        return objects.get(0).toString();
    }

    @Override
    public List<String> getLoginName(List<Long> empIds) {
        if (null != empIds && empIds.size() > 0) {
            StringBuilder builder = new StringBuilder("select loginName from Employee ");
            if(empIds.size() >1) {
                String ids="";
                for (Long empId : empIds) {
                    ids+=empId+",";
                }
                ids = ids.substring(0, ids.lastIndexOf(","));
                builder.append(" where id in ( " + ids + ")");
            }
            else
                builder.append(" where id = " + empIds.get(0));
            List<Object> objs = getSession().createQuery(builder.toString()).list();
            List<String> names = new ArrayList<>();
            for (Object name : objs)
                names.add(name.toString());
            return names;
        }
        return null;
    }

    @Override
	public List<Employee> getByIds(List<Long> ids) {
		// TODO Auto-generated method stub

        Criteria criterion = getSession().createCriteria(Employee.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.in("id", ids));

		return criterion.list();
	}

    @Override
    public List<Employee> getAll(){
        String hql = "from Employee e order by e.name";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<String> getEmpNameByDept(Long deptId) {
        String hql = "select name from Employee ";
        if(null != deptId)
            hql+= " where detp.id = "+deptId +"  order by e.name";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    @Override
    public Map<Long,String> getAllByDeptId(Long deptId) {
        String hql =  "select id,name from Employee e";
        if(null!= deptId)
            hql +=" where dept.id = " + deptId +"order by e.name";
        Query query = getSession().createQuery(hql);
        List<Object[]> links = query.list();
        HashMap<Long,String> emps = new HashMap<>();
        for(Object[] link : links) {
            Long id = (Long)link[0];
            String name = link[1].toString();
            emps.put(id,name);
        }
        return emps;
    }

    @Override
    public List<AttendEmpDto> getByDeptId(Long deptId,String empName) {
        String hql="select id as id,name as name,dept.deptName as deptName from Employee where isActive=1 and (status is not null and status=1) ";

        if(!StringUtils.isNullOrWhiteSpace(empName)){
//            hql+=" where name='"+empName+"'";
            hql+=" and name='"+empName+"'";
        }else{
            if(null!=deptId)
//                hql+=" where dept.id="+deptId;
                hql+=" and dept.id="+deptId;
        }
        Query query = getSession().createQuery(hql);
        query.setResultTransformer(Transformers.aliasToBean(AttendEmpDto.class));
        List<AttendEmpDto> list=query.list();
        return list;
    }

    @Override
    public Map<Long, Long> getEmpDeptIds(List<Long> empIds) {
        StringBuilder builder = new StringBuilder();
        for(Long empId:empIds)
        {
            builder.append(empId);
            builder.append(",");
        }
        String ids =  builder.substring(0,builder.lastIndexOf(","));

        String hql =  "select id,dept.id from Employee ";
            hql +=" where id in ( " + ids +  ")";
        Query query = getSession().createQuery(hql);
        List<Object[]> links = query.list();
        HashMap<Long,Long> emps = new HashMap<>();
        for(Object[] link : links) {
            Long id = (Long)link[0];
            Long deptId = (Long)link[1];
            emps.put(id,deptId);
        }
        return emps;
    }

    @Override
    public Employee getEmpByLoginName(String loginName) {
        Criteria criterion = getSession().createCriteria(Employee.class);

        criterion.add(Restrictions.eq("loginName", loginName));

        List<Employee> employees = criterion.list();
        if(employees!=null&&employees.size()>0)
            return employees.get(0);
        return null;
    }
/*@Override
    public List<Employee> getEmpByDeptName(String deptName) {
        Criteria criteria=getSession().createCriteria(Employee.class);
        criteria.add(Restrictions.eq("",deptName));
        return criteria.list();
    }*/

}
