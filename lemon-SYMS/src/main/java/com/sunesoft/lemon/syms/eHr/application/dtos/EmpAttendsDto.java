package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;

import java.util.List;

/**
 * Created by xiazl on 2017/3/2.
 */
public class EmpAttendsDto {


    private String empName;//员工姓名

    List<AttendDto> lists;

    private String deptName;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<AttendDto> getList() {
        return lists;
    }

    public void setList(List<AttendDto> list) {
        this.lists = list;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
