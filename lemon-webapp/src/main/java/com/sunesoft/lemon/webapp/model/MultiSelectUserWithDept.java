package com.sunesoft.lemon.webapp.model;

import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectUserWithDept {

        public MultiSelectUserWithDept(Long id, String name){
            this.deptId = id;
            this.deptName = name;
            this.empDtos = new ArrayList<>();
        }

        private  Long deptId;

        private  String deptName;


        private List<EmpDto> empDtos;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<EmpDto> getEmpDtos() {
        return empDtos;
    }

    public void setEmpDtos(List<EmpDto> empDtos) {
        this.empDtos = empDtos;
    }
}
