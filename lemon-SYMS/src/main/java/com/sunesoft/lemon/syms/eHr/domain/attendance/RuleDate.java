package com.sunesoft.lemon.syms.eHr.domain.attendance;

import com.sunesoft.lemon.fr.ddd.BaseEntity;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.RuleType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by temp on 2016/6/28.
 */

@Entity
@Table(name = "syy_oa_hr_rule_date")
public class RuleDate extends BaseEntity {



    /**
     * 时间 (统计具体时间)
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "r_date")
    private Date rDate;

    /**
     * 假日类型  (考勤，暂时不用)
     */
    @Column(name = "rule_type")
    private RuleType ruleType;




    /**
     * 假日具体信息 关联id
     */
    @ManyToOne
    @JoinColumn(name="holiday_info_id")
    private HolidayInfo info;

    public RuleDate() {
        this.setLastUpdateTime(new Date());
        this.setCreateDateTime(new Date());
        this.setIsActive(true);

    }


    /**
     * 节假日事件 id
     */
    @Column(name = "holiday_event_id")
    private String holidayEventId;

    /**
     * 节假日事件 节假日类型 （关联节假日）
     */
    @Column(name = "holiday_type")
    private String holidayType;

    /**
     * 节假日事件 节假日描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 节假日事件 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 节假日时间 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 节假日详细信息 关联id
     */
    @OneToMany
    @JoinColumn(name = "dateDetail_id")
    private List<DateDetail> dateDetail;

    public List<DateDetail> getDateDetail() {
        return dateDetail;
    }

    public Date getrDate() {
        return rDate;
    }

    public void setrDate(Date rDate) {
        this.rDate = rDate;
    }

    public void setDateDetail(List<DateDetail> dateDetail) {
        this.dateDetail = dateDetail;
    }

    public String getHolidayEventId() {
        return holidayEventId;
    }

    public void setHolidayEventId(String holidayEventId) {
        this.holidayEventId = holidayEventId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public HolidayInfo getInfo() {
        return info;
    }

    public void setInfo(HolidayInfo info) {
        this.info = info;
    }

//   public Boolean checkTime(){
//       if(getInfo()!=null&&getInfo().getHmonth()!=null&&getrDate()!=null&&DateHelper.getMonth(getrDate())!=Integer.parseInt(getInfo().getHmonth())){
//           if(!info.getHmonth().equals(DateHelper.formatDate(rDate)))return false;
//       }
//       return true;
//   }

}
