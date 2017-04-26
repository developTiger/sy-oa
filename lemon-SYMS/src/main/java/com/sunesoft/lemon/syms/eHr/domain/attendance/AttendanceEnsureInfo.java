package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/7/5.
 */

@Deprecated
@Entity
@Table(name="syy_oa_att_ensure_info")
public class AttendanceEnsureInfo extends BaseEntity {

    /**
     * 部门编号
     */
    @Column(name="dep_id")
    private Long depId; //部门编号

    /**
     * 打卡日期(yyyy-MM-dd)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="att_date")
    private Date attDate;

    /**
     * 确认时间(yyyy-MM-dd)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="sure_date")
    private Date sureDate;


    /**
     * 是否确认：false否，ture为是
     */
    @Column(name="is_sure")
    private Boolean isSure;


    @OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.ALL,CascadeType.PERSIST})
    @JoinColumn(name="attend_info_id")
    private List<AttendanceEnsureDetail> attendanceEnsureDetails;

    public AttendanceEnsureInfo() {
        this.setIsActive(true);
        this.setIsSure(false);
        this.attendanceEnsureDetails=new ArrayList<AttendanceEnsureDetail>();
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    public List<AttendanceEnsureDetail> getAttendanceEnsureDetails() {
        return attendanceEnsureDetails;
    }

    public void setAttendanceEnsureDetails(List<AttendanceEnsureDetail> attendanceEnsureDetails) {
        this.attendanceEnsureDetails = attendanceEnsureDetails;
    }

    public Boolean getIsSure() {
        return isSure;
    }

    public void setIsSure(Boolean isSure) {
        this.isSure = isSure;
    }

    public Date getSureDate() {
        return sureDate;
    }

    public void setSureDate(Date sureDate) {
        this.sureDate = sureDate;
    }
}
