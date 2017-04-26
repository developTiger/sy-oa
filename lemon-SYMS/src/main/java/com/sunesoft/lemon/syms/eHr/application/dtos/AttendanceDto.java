package com.sunesoft.lemon.syms.eHr.application.dtos;

import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceDetail;
import com.sunesoft.lemon.syms.eHr.domain.attendance.AttendanceSummery;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by zhouz on 2016/7/2.
 */
public class AttendanceDto {
    private Long id;
    /**
     * 员工Id
     */
    private Long empId; //员工Id
    /**
     * 员工姓名
     */
    private String empName;//员工姓名
    /**
     * 部门Id
     */
    private Long depId;//部门Id
    /**
     * 部门名称
     */
    private String depName;//部门名称
/**
 * 年
 */
    private Integer year; //年
    /**
     * 月
     */
    private Integer month; //月
    /**
     * 日
     */
    private AttendanceKind k_1; //日

    private AttendanceKind k_2;

    private AttendanceKind k_3;

    private AttendanceKind k_4;

    private AttendanceKind k_5;

    private AttendanceKind k_6;

    private AttendanceKind k_7;

    private AttendanceKind k_8;

    private AttendanceKind k_9;

    private AttendanceKind k_10;

    private AttendanceKind k_11;

    private AttendanceKind k_12;

    private AttendanceKind k_13;

    private AttendanceKind k_14;

    private AttendanceKind k_15;

    private AttendanceKind k_16;

    private AttendanceKind k_17;

    private AttendanceKind k_18;

    private AttendanceKind k_19;

    private AttendanceKind k_20;

    private AttendanceKind k_21;

    private AttendanceKind k_22;

    private AttendanceKind k_23;

    private AttendanceKind k_24;

    private AttendanceKind k_25;

    private AttendanceKind k_26;

    private AttendanceKind k_27;

    private AttendanceKind k_28;

    private AttendanceKind k_29;

    private AttendanceKind k_30;

    private AttendanceKind k_31;

    private AttendanceDetailDto attendanceDetailDto;

    private AttendanceSummeryDto attendanceSummeryDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AttendanceKind getK_1() {
        return k_1;
    }

    public void setK_1(AttendanceKind k_1) {
        this.k_1 = k_1;
    }

    public AttendanceKind getK_2() {
        return k_2;
    }

    public void setK_2(AttendanceKind k_2) {
        this.k_2 = k_2;
    }

    public AttendanceKind getK_3() {
        return k_3;
    }

    public void setK_3(AttendanceKind k_3) {
        this.k_3 = k_3;
    }

    public AttendanceKind getK_4() {
        return k_4;
    }

    public void setK_4(AttendanceKind k_4) {
        this.k_4 = k_4;
    }

    public AttendanceKind getK_5() {
        return k_5;
    }

    public void setK_5(AttendanceKind k_5) {
        this.k_5 = k_5;
    }

    public AttendanceKind getK_6() {
        return k_6;
    }

    public void setK_6(AttendanceKind k_6) {
        this.k_6 = k_6;
    }

    public AttendanceKind getK_7() {
        return k_7;
    }

    public void setK_7(AttendanceKind k_7) {
        this.k_7 = k_7;
    }

    public AttendanceKind getK_8() {
        return k_8;
    }

    public void setK_8(AttendanceKind k_8) {
        this.k_8 = k_8;
    }

    public AttendanceKind getK_9() {
        return k_9;
    }

    public void setK_9(AttendanceKind k_9) {
        this.k_9 = k_9;
    }

    public AttendanceKind getK_10() {
        return k_10;
    }

    public void setK_10(AttendanceKind k_10) {
        this.k_10 = k_10;
    }

    public AttendanceKind getK_11() {
        return k_11;
    }

    public void setK_11(AttendanceKind k_11) {
        this.k_11 = k_11;
    }

    public AttendanceKind getK_12() {
        return k_12;
    }

    public void setK_12(AttendanceKind k_12) {
        this.k_12 = k_12;
    }

    public AttendanceKind getK_13() {
        return k_13;
    }

    public void setK_13(AttendanceKind k_13) {
        this.k_13 = k_13;
    }

    public AttendanceKind getK_14() {
        return k_14;
    }

    public void setK_14(AttendanceKind k_14) {
        this.k_14 = k_14;
    }

    public AttendanceKind getK_15() {
        return k_15;
    }

    public void setK_15(AttendanceKind k_15) {
        this.k_15 = k_15;
    }

    public AttendanceKind getK_16() {
        return k_16;
    }

    public void setK_16(AttendanceKind k_16) {
        this.k_16 = k_16;
    }

    public AttendanceKind getK_17() {
        return k_17;
    }

    public void setK_17(AttendanceKind k_17) {
        this.k_17 = k_17;
    }

    public AttendanceKind getK_18() {
        return k_18;
    }

    public void setK_18(AttendanceKind k_18) {
        this.k_18 = k_18;
    }

    public AttendanceKind getK_19() {
        return k_19;
    }

    public void setK_19(AttendanceKind k_19) {
        this.k_19 = k_19;
    }

    public AttendanceKind getK_20() {
        return k_20;
    }

    public void setK_20(AttendanceKind k_20) {
        this.k_20 = k_20;
    }

    public AttendanceKind getK_21() {
        return k_21;
    }

    public void setK_21(AttendanceKind k_21) {
        this.k_21 = k_21;
    }

    public AttendanceKind getK_22() {
        return k_22;
    }

    public void setK_22(AttendanceKind k_22) {
        this.k_22 = k_22;
    }

    public AttendanceKind getK_23() {
        return k_23;
    }

    public void setK_23(AttendanceKind k_23) {
        this.k_23 = k_23;
    }

    public AttendanceKind getK_24() {
        return k_24;
    }

    public void setK_24(AttendanceKind k_24) {
        this.k_24 = k_24;
    }

    public AttendanceKind getK_25() {
        return k_25;
    }

    public void setK_25(AttendanceKind k_25) {
        this.k_25 = k_25;
    }

    public AttendanceKind getK_26() {
        return k_26;
    }

    public void setK_26(AttendanceKind k_26) {
        this.k_26 = k_26;
    }

    public AttendanceKind getK_27() {
        return k_27;
    }

    public void setK_27(AttendanceKind k_27) {
        this.k_27 = k_27;
    }

    public AttendanceKind getK_28() {
        return k_28;
    }

    public void setK_28(AttendanceKind k_28) {
        this.k_28 = k_28;
    }

    public AttendanceKind getK_29() {
        return k_29;
    }

    public void setK_29(AttendanceKind k_29) {
        this.k_29 = k_29;
    }

    public AttendanceKind getK_30() {
        return k_30;
    }

    public void setK_30(AttendanceKind k_30) {
        this.k_30 = k_30;
    }

    public AttendanceKind getK_31() {
        return k_31;
    }

    public void setK_31(AttendanceKind k_31) {
        this.k_31 = k_31;
    }

    public AttendanceDetailDto getAttendanceDetailDto() {
        return attendanceDetailDto;
    }

    public void setAttendanceDetailDto(AttendanceDetailDto attendanceDetailDto) {
        this.attendanceDetailDto = attendanceDetailDto;
    }

    public AttendanceSummeryDto getAttendanceSummeryDto() {
        return attendanceSummeryDto;
    }

    public void setAttendanceSummeryDto(AttendanceSummeryDto attendanceSummeryDto) {
        this.attendanceSummeryDto = attendanceSummeryDto;
    }
}
