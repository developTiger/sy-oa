package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpGroupDto;

import java.util.List;

/**
 * Created by temp on 2016/6/24.
 */
public class AssembleObject {

    private List<EmpGroupDto> empGroupDto;
    private List<DeptmentDto> deptmentDto;
    private EmpDto emp;
    private List<EmpDto>  leaderSelect;

    public List<EmpDto> getLeaderSelect() {
        return leaderSelect;
    }

    public void setLeaderSelect(List<EmpDto> leaderSelect) {
        this.leaderSelect = leaderSelect;
    }

    public List<EmpGroupDto> getEmpGroupDto() {
        return empGroupDto;
    }

    public void setEmpGroupDto(List<EmpGroupDto> empGroupDto) {
        this.empGroupDto = empGroupDto;
    }

    public List<DeptmentDto> getDeptmentDto() {
        return deptmentDto;
    }

    public void setDeptmentDto(List<DeptmentDto> deptmentDto) {
        this.deptmentDto = deptmentDto;
    }

    public EmpDto getEmp() {
        return emp;
    }

    public void setEmp(EmpDto emp) {
        this.emp = emp;
    }
}
