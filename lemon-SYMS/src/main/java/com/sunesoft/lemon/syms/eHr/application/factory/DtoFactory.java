package com.sunesoft.lemon.syms.eHr.application.factory;

import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.domain.attendance.Attendance;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.*;
import com.sunesoft.lemon.syms.hrForms.domain.FormEvection;
import org.hibernate.hql.internal.ast.tree.FromElement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by xiazl on 2016/6/16.
 */
public class DtoFactory {
    /**
     * convert from dto
     *
     * @param source
     * @param target
     * @return
     */
    public static <S, T> T convert(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static AttendanceOperateDto FormEvectionConverToAttendanceOpDto(FormEvection formEvection,  AttendanceOperateDto attendanceOperateDto)
    {
        //考勤类型
        if(formEvection.getEvectionType()!=null){

            attendanceOperateDto.setAttendanceKind(formEvection.getEvectionType());

        }
        //开始时间
        if(formEvection.getEvectionTime() != null){

            attendanceOperateDto.setBeginDate(formEvection.getCreateDateTime());

        }
        //结束时间
        if(formEvection.getToTime() !=null){

            attendanceOperateDto.setEndDate(formEvection.getToTime());

        }
        //部门名称
        if(formEvection.getDeptName()!=null && !("").equals(formEvection.getDeptName())){

            attendanceOperateDto.setDepName(formEvection.getDeptName());

        }
        //部门号
        if(formEvection.getDeptId() != null){

            attendanceOperateDto.setDepId(formEvection.getDeptId());

        }
        //出差人姓名
        if(formEvection.getApplyerName() != null && !("").equals(formEvection.getApplyerName())){

            attendanceOperateDto.setName(formEvection.getApplyerName());

        }

        //出差人ID
        if(formEvection.getApplyer()!=null){

            attendanceOperateDto.setEmpId(formEvection.getApplyer());

        }



        return attendanceOperateDto;
    }


    public static EmpDto convert(Employee source) {
        EmpDto dto = new EmpDto();
        BeanUtils.copyProperties(source, dto);
        if(source.getDept()!=null) {
            dto.setDeptId(source.getDept().getId());
            dto.setDeptName(source.getDept().getDeptName());
        }
        if(source.getLeader()!=null) {
            dto.setLeaderId(source.getLeader().getId());
            dto.setLeaderName(source.getLeader().getName());
        }
        if(source.getEmpGroup()!=null) {
            dto.setGroupId(source.getEmpGroup().getId());
            dto.setGroupName(source.getEmpGroup().getName());
        }
        return dto;
    }
    /**
     * 根据时间求时间段
     *
     * @param time
     * @return
     */
    public static int timeSlot(Date time) {
        if(time==null)return 0;
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
//        if (date.before(time)) {
//            throw new IllegalArgumentException("this time is before now,It's unbelievable!");
//        }
        int yearNow;
        yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(time);
        int yearOld = cal.get(Calendar.YEAR);
        int monthOld = cal.get(Calendar.MONTH) + 1;
        int dayOld = cal.get(Calendar.DAY_OF_MONTH);

        int result = yearNow - yearOld;
        if (monthNow <= monthOld) {
            if (monthNow == monthOld) {
                if (dayNow < dayOld) {
                    result--;
                }
            } else {
                result--;
            }
        }
        return result;
    }

    /**
     * 判断是一点还是半天
     * @param d1
     * @param d2
     * @return
     */
    public float oneDay(Date d1,Date d2){
        if(!DateHelper.addHour(d1,12).before(d2)){
            return 0.5f;
        }else {
            return 1f;
        }

    }


//public  static  void main(String[] args){
//
//    Date time= DateHelper.addYear(new Date(),-12);
//    int i=timeSlot(time);
//    System.out.println(i);
//
//}


    //region Description
    //    /**
//     * convert from dto
//     *
//     * @param s
//     * @return
//     */
//    public static <S, T> T convertFromDto(S s) {
//        T t = null;
//        try {
//            BeanUtils.copyProperties(s, t.getClass().newInstance());

//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return t;
//    }
    //endregion


}
