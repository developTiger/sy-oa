package com.sunesoft.lemon.syms.eHr.domain.empInfo;

import com.sunesoft.lemon.syms.eHr.application.dtos.AttendEmpDto;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/5/12.
 */
public interface EmployeeRepository {

    /**
     * add or save
     * @param emp
     * @return
     */
    Long save(Employee emp);

    /**
     * delete
     * @param empId
     */
    void delete(Long empId);

    /**
     * get
     * @param inventorId
     * @return
     */
    Employee get(Long inventorId);

    /**
     * 获取员工登录信息
     * @param empId 员工标识
     * @return
     */
    String getLoginName(Long empId);

    /**
     * 批量获取员工登录信息
     * @param empIds 员工标识集合
     * @return
     */
    List<String> getLoginName(List<Long> empIds);

    /**
     * get list by ids
     * @param ids
     * @return
     */
    List<Employee> getByIds(List<Long> ids);


    List<Employee> getAll();

    List<String> getEmpNameByDept(Long deptId);

    Map<Long,String> getAllByDeptId(Long deptId);

    List<AttendEmpDto> getByDeptId(Long deptId,String empName);

    Map<Long,Long> getEmpDeptIds(List<Long> empIds);




    Employee getEmpByLoginName(String loginName);
    /*List<Employee> getEmpByDeptName(String deptName);*/
}
