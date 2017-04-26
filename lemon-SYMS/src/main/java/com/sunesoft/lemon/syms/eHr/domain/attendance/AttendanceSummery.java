package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 个人考勤统计
 * Created by xiazl on 2016/6/28.
 */
@Deprecated
@Entity
@Table(name = "syy_oa_hr_att_summery")
public class AttendanceSummery extends BaseEntity {

    /**
     * 0代表禁用，1代表未禁用
     */
    @Column(name = "a_status")
    private Integer aStatus;

    /**
     * 年
     */
    private Integer year; //年
    /**
     * 月
     */
    private Integer month; //月

    /**
     * 员工id
     */
    @Column(name = "emp_id")
    private Long empId; //员工Id
    /**
     * empployee's name
     */
    @Column(name = "emp_name")
    private String empName;//员工姓名
    /**
     * deptment's id
     */
    @Column(name = "dep_id")
    private Long depId;//部门Id
    /**
     * deptment's name
     */
    @Column(name = "dep_name")
    private String depName;//部门名称
    /**
     * 出勤
     */
    @Column(columnDefinition = "float default 0.0")
    private Float attend;

    /**
     * 探亲
     */

    @Column(name = "visit_family", columnDefinition = "float default 0.0")
    private Float visitFamily;

    /**
     * 疗养
     */
    @Column(columnDefinition = "float default 0.0")
    private Float spa;

    /**
     * 事假
     */
    @Column(columnDefinition = "float default 0.0")
    private Float aleave;

    /**
     * （带薪）休假
     */
    @Column(columnDefinition = "float default 0.0")
    private Float vacation;

    /**
     * 护理
     */
    @Column(columnDefinition = "float default 0.0")
    private Float nurse;

    /**
     * 病假
     */
    @Column(columnDefinition = "float default 0.0")
    private Float sick;

    /**
     * 学历
     */
    @Column(name = "study_experience", columnDefinition = "float default 0.0")
    private Float studyExperience;

    /**
     * 脱产
     */
    @Column(name = "off_job", columnDefinition = "float default 0.0")
    private Float offJob;

    /**
     * 野外
     */
    @Column(columnDefinition = "float default 0.0")
    private Float wild;

    /**
     * 出差
     */
    @Column(name = "business_travel", columnDefinition = "float default 0.0")
    private Float businessTravel;

    /**
     * 婚丧
     */
    @Column(name = "wedding_funeral", columnDefinition = "float default 0.0")
    private Float weddingFuneral;

    /**
     * 产假
     */
    @Column(name = "birth_child", columnDefinition = "float default 0.0")
    private Float birthChild;

    /**
     * 工伤
     */
    @Column(name = "work_hurt", columnDefinition = "float default 0.0")
    private Float workHurt;

    /**
     * 休息
     */
    @Column(columnDefinition = "float default 0.0")
    private Float rest;

    /**
     * 劳保
     */
    @Column(columnDefinition = "float default 0.0")
    private Float labor;

    /**
     * 旷工
     */
    @Column(columnDefinition = "float default 0.0")
    private Float absenteeism;//旷工
    /**
     * 补休
     */
    @Column(columnDefinition = "float default 0.0")
    private Float compensate;//补休
    /**
     * 节日加班天数
     */
    @Column(columnDefinition = "float default 0.0")
    private Float vacationWork;
    /**
     * 双休加班天数
     */
    @Column(columnDefinition = "float default 0.0")
    private Float weekendWork;

    /**
     * 应该享受休假天数
     */
    @Column(columnDefinition = "float default 0.0")
    private Float shouldDays;


    /************后加的出勤种类*********/
    /**
     * 公休加班
     */
    @Column(name = "g_work",columnDefinition = "float default 0.0")
    private Float gwork;
    /**
     * 节日加班
     */
    @Column(name = "j_work",columnDefinition = "float default 0.0")
    private Float jwork;

    /**
     * 脱产学习
     */
    @Column(name = "t_study",columnDefinition = "float default 0.0")
    private Float tstudy;
    /**
     * 产后休长假
     */
    @Column(name = "l_birth",columnDefinition = "float default 0.0")
    private Float lbirth;
    /**
     *拘留
     */

    @Column(name = "custody",columnDefinition = "float default 0.0")
    private Float custody;
    /**
     * 戒毒
     */
    @Column(columnDefinition = "float default 0.0")
    private Float drug;

    @OneToOne(mappedBy = "attendanceSummery")
    private Attendance attendance;

    public AttendanceSummery() {
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
        this.setaStatus(1);
    }

    public Float getAttend() {
        return attend;
    }

    public void setAttend(Float attend) {
        this.attend = attend == null ? 0 : attend;
    }

    public Float getBirthChild() {
        return birthChild;
    }

    public void setBirthChild(Float birthChild) {
        this.birthChild = birthChild == null ? 0 : birthChild;
    }

    public Float getBusinessTravel() {
        return businessTravel;
    }

    public void setBusinessTravel(Float businessTravel) {
        this.businessTravel = businessTravel == null ? 0 : businessTravel;
    }

    public Float getLabor() {
        return labor;
    }

    public void setLabor(Float labor) {
        this.labor = labor == null ? 0 : labor;
    }


    public Float getNurse() {
        return nurse;
    }

    public void setNurse(Float nurse) {
        this.nurse = nurse == null ? 0 : nurse;
    }

    public Float getOffJob() {
        return offJob;
    }

    public void setOffJob(Float offJob) {
        this.offJob = offJob == null ? 0 : offJob;
    }

    public Float getRest() {
        return rest;
    }

    public void setRest(Float rest) {
        this.rest = rest == null ? 0 : rest;
    }

    public Float getSick() {
        return sick;
    }

    public void setSick(Float sick) {
        this.sick = sick == null ? 0 : sick;
    }

    public Float getSpa() {
        return spa;
    }

    public void setSpa(Float spa) {
        this.spa = spa == null ? 0 : spa;
    }

    public Integer getaStatus() {
        return aStatus;
    }

    public void setaStatus(Integer aStatus) {
        this.aStatus = aStatus == null ? 0 : aStatus;
    }

    public Float getAleave() {
        return aleave;
    }

    public void setAleave(Float aleave) {
        this.aleave = aleave == null ? 0 : aleave;
    }

    public Float getStudyExperience() {
        return studyExperience;
    }

    public void setStudyExperience(Float studyExperience) {
        this.studyExperience = studyExperience == null ? 0 : studyExperience;
    }

    public Float getVacation() {
        return vacation;
    }

    public void setVacation(Float vacation) {
        this.vacation = vacation == null ? 0 : vacation;
    }

    public Float getVisitFamily() {
        return visitFamily;
    }

    public void setVisitFamily(Float visitFamily) {
        this.visitFamily = visitFamily == null ? 0 : visitFamily;
    }

    public Float getWeddingFuneral() {
        return weddingFuneral;
    }

    public void setWeddingFuneral(Float weddingFuneral) {
        this.weddingFuneral = weddingFuneral == null ? 0 : weddingFuneral;
    }

    public Float getWild() {
        return wild;
    }

    public void setWild(Float wild) {
        this.wild = wild == null ? 0 : wild;
    }

    public Float getWorkHurt() {
        return workHurt;
    }

    public void setWorkHurt(Float workHurt) {
        this.workHurt = workHurt == null ? 0 : workHurt;
    }


    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Float getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(Float absenteeism) {
        this.absenteeism = absenteeism;
    }

    public Float getCompensate() {
        return compensate;
    }

    public void setCompensate(Float compensate) {
        this.compensate = compensate == null ? 0 : compensate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Float getVacationWork() {
        return vacationWork;
    }

    public void setVacationWork(Float vacationWork) {
        this.vacationWork = vacationWork == null ? 0 : vacationWork;
    }

    public Float getWeekendWork() {
        return weekendWork;
    }

    public void setWeekendWork(Float weekendWork) {
        this.weekendWork = weekendWork == null ? 0 : weekendWork;
    }

    public Float getShouldDays() {
        return shouldDays;
    }

    public void setShouldDays(Float shouldDays) {
        this.shouldDays = shouldDays == null ? 0 : shouldDays;
    }


    public Float getCustody() {
        return custody;
    }

    public void setCustody(Float custody) {
        this.custody = custody;
    }

    public Float getDrug() {
        return drug;
    }

    public void setDrug(Float drug) {
        this.drug = drug;
    }

    public Float getGwork() {
        return gwork;
    }

    public void setGwork(Float gwork) {
        this.gwork = gwork;
    }

    public Float getJwork() {
        return jwork;
    }

    public void setJwork(Float jwork) {
        this.jwork = jwork;
    }

    public Float getLbirth() {
        return lbirth;
    }

    public void setLbirth(Float lbirth) {
        this.lbirth = lbirth;
    }

    public Float getTstudy() {
        return tstudy;
    }

    public void setTstudy(Float tstudy) {
        this.tstudy = tstudy;
    }
}
