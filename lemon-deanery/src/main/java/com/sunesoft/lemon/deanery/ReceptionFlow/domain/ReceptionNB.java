package com.sunesoft.lemon.deanery.ReceptionFlow.domain;

import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormOpenProjectFile;
import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 * 公务接待（内宾）
 * 接待项目子表
 * Accommodation(syy_oa_reception_accommodation) --住宿费用
 * WorkingLunch(syy_oa_reception_lunch) --工作餐费用
 * Banquet(syy_oa_accomodation_banquet) --宴会费用
 */
@Entity
@Table(name = "syy_oa_reception_nb")
public class ReceptionNB extends BaseFormEntity {

    /**
     * 附件id
     */
    @Column(name = "file_id")
    private String fileId;

    /**
     * 附件name
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 附件id
     */
    @Column(name = "file_id1")
    private String fileId1;

    /**
     * 附件name
     */
    @Column(name = "file_name1")
    private String fileName1;

    public String getFileId1() {
        return fileId1;
    }

    public void setFileId1(String fileId1) {
        this.fileId1 = fileId1;
    }

    public String getFileName1() {
        return fileName1;
    }

    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 联系人
     */
    @Column(name = "linkman",columnDefinition = "varchar2(25)")
    private String linkman;
    /**
     * 联系人电话
     */
    @Column(name = "mobile",columnDefinition = "varchar2(11)")
    private String mobile;
    /**
     * 外宾单位
     */
    @Column(name = "foreign_guest",columnDefinition = "varchar2(200)")
    private String foreignGuest;

    /**
     * 院办分配接待任务
     */
    @Column(name = "reception_work",columnDefinition = "varchar2(200)")
    private String receptionWork;

    public String getReceptionWork() {
        return receptionWork;
    }

    public void setReceptionWork(String receptionWork) {
        this.receptionWork = receptionWork;
    }

    /**
     * 事由
     */
    @Column(name = "reasons",columnDefinition = "varchar2(500)")
    private String reasons;

    /**
     * 来宾情况及安排
     */
    @Column(name = "remark",columnDefinition = "varchar2(500)")
    private String remark;

    /**
     * 费用合计
     */
    @Column(name = "cost",columnDefinition = "number(19,2)")
    private Double cost;

    /**
     * 审批人ID
     */
    @Column(name = "approver_id",columnDefinition = "number(19)")
    private Long approverId;

    /**
     * 审批人姓名
     */
    @Column(name = "approver_name",columnDefinition = "varchar2(25)")
    private String approverName;

    /**
     * 签单人ID
     */
    @Column(name = "signer_id",columnDefinition = "number(19)")
    private Long signerId;

    /**
     * 签单人姓名
     */
    @Column(name = "signer_name",columnDefinition = "varchar2(25)")
    private String signerName;

    /**
     * 流程编号
     */
    @Column(name = "process_id",columnDefinition = "varchar2(32)")
    private String processId;

    /**
     * 流程实例编号
     */
    @Column(name = "order_id",columnDefinition = "varchar2(32)")
    private String orderId;

    @Column(name = "sumall",columnDefinition = "NUMBER(19,2)")
    private Double sumAll;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "form_reception_id")
    private List<FormOpenProjectFile> formOpenProjectFiles;

    public List<FormOpenProjectFile> getFormOpenProjectFiles() {
        return formOpenProjectFiles;
    }

    public void setFormOpenProjectFiles(List<FormOpenProjectFile> formOpenProjectFiles) {
        this.formOpenProjectFiles = formOpenProjectFiles;
    }

    /**
     * 流程中的状态
     * 插入数据库为数字
     * 1：代表运行
     * 2：代表撤销
     * 3：代表结束
     */
    @Column(name= "flowstatus" ,columnDefinition = "NUMBER(2)")
    private Integer flow_status;

    /**
     * 选择的陪同部门
     * @return
     */


    //实际接待人，来访人员

     private String receptionPerson;

    //陪同人员

    private String personComp;

    //实际接待费用

    //private Long actualCost;
    private String actualCost;

    //实际接待开始时间和结束时间

    private String actualBeginTime;

    private String actualEndTime;

    //接待过程

    private String receptionProcess;

  //套间
    private int taojian;
    //单间
    private int danjian;
    //标准间
    private int biaozhunjian;
    //其他
    private int qitajian;
    //人次
    private String rence1;
    private String rence2;
    private String rence3;
    //宴会时间
    private String yanhuisj1;
    //宴会人数
    private String yanhuirs1;
    //标准
    private String yanhuibz1;
    private String yanhuisj2;
    private String yanhuirs2;
    private String yanhuibz2;
    //小写
    private String xiaoxie;
    //大写
    private String daxie;
    private String feiyongyusuan1;
    private String feiyongyusuan2;
    private String feiyongyusuan3;


    public int getTaojian() {
        return taojian;
    }

    public void setTaojian(int taojian) {
        this.taojian = taojian;
    }

    public int getDanjian() {
        return danjian;
    }

    public void setDanjian(int danjian) {
        this.danjian = danjian;
    }

    public int getBiaozhunjian() {
        return biaozhunjian;
    }

    public void setBiaozhunjian(int biaozhunjian) {
        this.biaozhunjian = biaozhunjian;
    }

    public int getQitajian() {
        return qitajian;
    }

    public void setQitajian(int qitajian) {
        this.qitajian = qitajian;
    }

    public String getRence1() {
        return rence1;
    }

    public void setRence1(String rence1) {
        this.rence1 = rence1;
    }

    public String getRence2() {
        return rence2;
    }

    public void setRence2(String rence2) {
        this.rence2 = rence2;
    }

    public String getRence3() {
        return rence3;
    }

    public void setRence3(String rence3) {
        this.rence3 = rence3;
    }

    public String getYanhuisj1() {
        return yanhuisj1;
    }

    public void setYanhuisj1(String yanhuisj1) {
        this.yanhuisj1 = yanhuisj1;
    }

    public String getYanhuirs1() {
        return yanhuirs1;
    }

    public void setYanhuirs1(String yanhuirs1) {
        this.yanhuirs1 = yanhuirs1;
    }

    public String getYanhuibz1() {
        return yanhuibz1;
    }

    public void setYanhuibz1(String yanhuibz1) {
        this.yanhuibz1 = yanhuibz1;
    }

    public String getYanhuisj2() {
        return yanhuisj2;
    }

    public void setYanhuisj2(String yanhuisj2) {
        this.yanhuisj2 = yanhuisj2;
    }

    public String getYanhuirs2() {
        return yanhuirs2;
    }

    public void setYanhuirs2(String yanhuirs2) {
        this.yanhuirs2 = yanhuirs2;
    }

    public String getYanhuibz2() {
        return yanhuibz2;
    }

    public void setYanhuibz2(String yanhuibz2) {
        this.yanhuibz2 = yanhuibz2;
    }

    public String getXiaoxie() {
        return xiaoxie;
    }

    public void setXiaoxie(String xiaoxie) {
        this.xiaoxie = xiaoxie;
    }

    public String getDaxie() {
        return daxie;
    }

    public void setDaxie(String daxie) {
        this.daxie = daxie;
    }

    public String getFeiyongyusuan1() {
        return feiyongyusuan1;
    }

    public void setFeiyongyusuan1(String feiyongyusuan1) {
        this.feiyongyusuan1 = feiyongyusuan1;
    }

    public String getFeiyongyusuan2() {
        return feiyongyusuan2;
    }

    public void setFeiyongyusuan2(String feiyongyusuan2) {
        this.feiyongyusuan2 = feiyongyusuan2;
    }

    public String getFeiyongyusuan3() {
        return feiyongyusuan3;
    }

    public void setFeiyongyusuan3(String feiyongyusuan3) {
        this.feiyongyusuan3 = feiyongyusuan3;
    }

    public String getReceptionPerson() {
        return receptionPerson;
    }

    public void setReceptionPerson(String receptionPerson) {
        this.receptionPerson = receptionPerson;
    }

    public String getPersonComp() {
        return personComp;
    }

    public void setPersonComp(String personComp) {
        this.personComp = personComp;
    }

    public String getActualCost() {
        return actualCost;
    }

    public void setActualCost(String actualCost) {
        this.actualCost = actualCost;
    }

    public String getActualBeginTime() {
        return actualBeginTime;
    }

    public void setActualBeginTime(String actualBeginTime) {
        this.actualBeginTime = actualBeginTime;
    }

    public String getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getReceptionProcess() {
        return receptionProcess;
    }

    public void setReceptionProcess(String receptionProcess) {
        this.receptionProcess = receptionProcess;
    }

    private String coopDept;

    public String getCoopDept() {
        return coopDept;
    }

    public void setCoopDept(String coopDept) {
        this.coopDept = coopDept;
    }

    public Integer getFlow_status() {
        return flow_status;
    }

    public void setFlow_status(Integer flow_status) {
        this.flow_status = flow_status;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getForeignGuest() {
        return foreignGuest;
    }

    public void setForeignGuest(String foreignGuest) {
        this.foreignGuest = foreignGuest;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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

    public Long getSignerId() {
        return signerId;
    }

    public void setSignerId(Long signerId) {
        this.signerId = signerId;
    }

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getSumAll() {
        return sumAll;
    }

    public void setSumAll(Double sumAll) {
        this.sumAll = sumAll;
    }

    public ReceptionNB() {
        this.setIsActive(true);
        this.setViewUrl("reception_flow");
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }
}
