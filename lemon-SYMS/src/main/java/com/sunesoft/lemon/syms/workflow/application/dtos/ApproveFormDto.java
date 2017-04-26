package com.sunesoft.lemon.syms.workflow.application.dtos;

import com.sunesoft.lemon.syms.workflow.domain.enums.AppType;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;

import java.util.List;

/**
 * Created by zhouz on 2016/6/30.
 */
public class ApproveFormDto {

    private Long formNo;

    private String formKind;

    private String content;

    private Integer appValue;


    private Long approverId;

    private String approverName;

    private Long deptId;

    private String deptName;


    private List<Long> agentId;

    public Long getFormNo() {
        return formNo;
    }

    public void setFormNo(Long formNo) {
        this.formNo = formNo;
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAppValue() {
        return appValue;
    }

    public void setAppValue(Integer appValue) {
        this.appValue = appValue;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

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

    public List<Long> getAgentId() {
        return agentId;
    }

    public void setAgentId(List<Long> agentId) {
        this.agentId = agentId;
    }
}
