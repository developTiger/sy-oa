package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.ArrayUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpAttendanceWorkCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpAttendancesCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.attendance.*;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xiazl on 2016/7/5.
 */
@Service("attendanceEnsureService")
public class AttendanceEnsureServiceImpl extends GenericHibernateFinder implements AttendanceEnsureService {

    @Autowired
    AttendanceService attendanceService;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    AttendanceEnsureInfoRepository infoRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DeptmentService deptmentService;


    @Override
    public AtendanceEnsureInfoDto getDeptInfoByDay(Long dept, String attDate) {
        Date date = DateHelper.parse(attDate, "yyyy-MM-dd");
        Criteria criterion = getSession().createCriteria(AttendanceEnsureInfo.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("depId", dept));
        criterion.add(Restrictions.eq("attDate", date));
        List<AttendanceEnsureInfo> list = criterion.list();
        if (list != null && list.size() > 0)
            return DtoFactory.convert(list.get(0), new AtendanceEnsureInfoDto());
        return null;
    }

    @Override
    public CommonResult addOrUpdateEnsureInfo(AttendanceOperateDto dto) {
//        if (StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(dto.getBeginDate())) || StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(dto.getBeginDate()))) {
//            return ResultFactory.commonError("您选择的起止时间有误");
//        }
        DeptmentDto deptmentDto = deptmentService.getByDeptId(dto.getDepId());
        if (dto.getAttDate() != null) {
            dto.setAttDate(DateHelper.parse(DateHelper.formatDate(dto.getAttDate(), "yyyy-MM-dd 00:00:00")));
        }
        dto.setDepName(deptmentDto.getDeptName());
        EmpDto empDto = employeeService.getEmpById(dto.getEmpId());
        dto.setName(empDto.getName());

        Criteria criterion = getSession().createCriteria(AttendanceEnsureInfo.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("depId", dto.getDepId()));

        //   String date = DateHelper.formatDate(new Date());//当天对员工的操作，应该添加到当天的info中
        criterion.add(Restrictions.eq("attDate", dto.getAttDate()));
        List<AttendanceEnsureInfo> list = criterion.list();
        if (list == null || list.size() == 0) {
            AttendanceEnsureInfo info = new AttendanceEnsureInfo();
            info.setDepId(dto.getDepId());
            info.setAttDate(dto.getAttDate());

            AttendanceEnsureDetail ad = new AttendanceEnsureDetail();

            ad = DtoFactory.convert(dto, ad);
            ad.setAttDate(dto.getAttDate());
            info.getAttendanceEnsureDetails().add(ad);
            infoRepository.save(info);
            if (!StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(dto.getBeginDate())) && !StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(dto.getBeginDate()))) {
                if (dto.getTimeRange() != null && dto.getTimeRange() > 0)
                    setRangKind(dto);
            }

            return ResultFactory.commonSuccess();

        } else {
            AttendanceEnsureInfo inf = list.get(0);
            List<AttendanceEnsureDetail> ads = inf.getAttendanceEnsureDetails();
            inf.setLastUpdateTime(new Date());
            if (ads != null && ads.size() == 0) {
                AttendanceEnsureDetail ad = new AttendanceEnsureDetail();
                ad = DtoFactory.convert(dto, ad);
                ad.setAttDate(dto.getAttDate());
                inf.getAttendanceEnsureDetails().add(ad);
                infoRepository.save(inf);
                if (dto.getTimeRange() != null && dto.getTimeRange() > 0)
                    setRangKind(dto);
                return ResultFactory.commonSuccess();
            } else {
                for (AttendanceEnsureDetail ad : ads) {
                    if (ad.getEmpId().equals(dto.getEmpId())) {
                        ad = DtoFactory.convert(dto, ad);
                        ad.setLastUpdateTime(new Date());
                        ad.setAttDate(dto.getAttDate());
                        infoRepository.save(inf);
                        if (!StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(dto.getBeginDate())) && !StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(dto.getBeginDate()))) {
                            if (dto.getTimeRange() != null && dto.getTimeRange() > 0)
                                setRangKind(dto);
                        }
                        return ResultFactory.commonSuccess();
                    }
                }
                AttendanceEnsureDetail nad = new AttendanceEnsureDetail();
                nad = DtoFactory.convert(dto, nad);
                nad.setAttDate(dto.getAttDate());
                inf.getAttendanceEnsureDetails().add(nad);
                infoRepository.save(inf);
                if (!StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(dto.getBeginDate())) && !StringUtils.isNullOrWhiteSpace(DateHelper.formatDate(dto.getBeginDate()))) {
                    if (dto.getTimeRange() != null && dto.getTimeRange() > 0)
                        setRangKind(dto);
                }
                return ResultFactory.commonSuccess();
            }
        }
    }

    /**
     * 确认按钮
     *
     * @param deptId
     * @param attDate
     * @return
     */
    @Override
    public CommonResult ensureAttendance(Long deptId, String attDate, String dayStation) {
        Date date = DateHelper.parse(attDate, "yyyy-MM-dd");
        Criteria criterion = getSession().createCriteria(AttendanceEnsureInfo.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("depId", deptId));
        criterion.add(Restrictions.eq("attDate", date));

        List<AttendanceEnsureInfo> list = criterion.list();
        AttendanceEnsureInfo ensureInfo = null;
        if (list != null && list.size() > 0) {
            ensureInfo = list.get(0);
            if (ensureInfo.getIsSure())
                return ResultFactory.commonError("该日考勤已确认，请勿重复操作！");
            List<AttendanceEnsureDetail> ensureDetails = ensureInfo.getAttendanceEnsureDetails();
            attendanceService.updateAttendanceByDept(deptId, date, dayStation); // 更新部门员工的出勤状态
            if (ensureDetails != null && ensureDetails.size() > 0) {
                for (AttendanceEnsureDetail detail : ensureDetails) {
                    //todo 筛选掉已经打过考勤的单个人
                    if (detail.getIsSure() == null || !detail.getIsSure()) {
                        AttendanceOperateDto dto = DtoFactory.convert(detail, new AttendanceOperateDto());
                        attendanceService.AddOrUpdateAttendance(dto);
                    }
                }
            }
        } else {
            ensureInfo = new AttendanceEnsureInfo();
            ensureInfo.setAttDate(date);
            ensureInfo.setDepId(deptId);
            attendanceService.updateAttendanceByDept(deptId, date, dayStation);
        }
        ensureInfo.setIsSure(true);
        ensureInfo.setSureDate(new Date());
        ensureInfo.setLastUpdateTime(new Date());
        infoRepository.save(ensureInfo);
        //更新AttendanceSummery的出勤情况
        updateAttendCount(deptId, DateHelper.parse(attDate, "yyyy-MM-dd"), ensureInfo.getAttendanceEnsureDetails(), dayStation);


        return ResultFactory.commonSuccess();
    }

    /**
     * 个人考勤是否确认信息
     *
     * @param empId
     * @param attDate
     * @param attendanceKind
     * @return
     */
    @Override
    public CommonResult ensureOneAttendance(Long empId, String attDate, AttendanceKind attendanceKind) {
        boolean isOne = false;
        Date date = DateHelper.parse(attDate, "yyyy-MM-dd");
        EmpDto empDto = employeeService.getEmpById(empId);
        Long deptId = empDto.getDeptId();
        Criteria criterion = getSession().createCriteria(AttendanceEnsureInfo.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("depId", deptId));
        criterion.add(Restrictions.eq("attDate", date));

        List<AttendanceEnsureInfo> list = criterion.list();
        AttendanceEnsureInfo ensureInfo = null;
        AttendanceEnsureDetail detailOne = null;
        if (list != null && list.size() > 0) {  //todo 查找到部门的考勤
            ensureInfo = list.get(0);
            if (ensureInfo.getIsSure())
                return ResultFactory.commonError("今日该员工的部门考勤已确认，请勿重复操作！");
            List<AttendanceEnsureDetail> ensureDetails = ensureInfo.getAttendanceEnsureDetails();
            if (ensureDetails != null && ensureDetails.size() > 0) { //部门考勤中有个人的
                for (AttendanceEnsureDetail detail : ensureDetails) {
                    if (detail.getEmpId().equals(empId)) {//指定的员工打考勤
                        detailOne = detail;
                        AttendanceOperateDto dto = DtoFactory.convert(detailOne, new AttendanceOperateDto());
                        attendanceService.AddOrUpdateAttendance(dto);
                        detailOne.setIsSure(true);
                        detailOne.setSureDate(new Date());
                        detailOne.setLastUpdateTime(new Date());
                        break;
                    }
                }
            }

        } else { //未找到部门考勤
            ensureInfo = new AttendanceEnsureInfo();
            ensureInfo.setAttDate(date);
            ensureInfo.setDepId(deptId);

            detailOne = getAttendanceEnsureDetail(empDto, attendanceKind);
            ensureInfo.getAttendanceEnsureDetails().add(detailOne);
        }
        infoRepository.save(ensureInfo);

        return ResultFactory.commonSuccess();
    }

    /**
     * 创建一个 确认的 AttendanceEnsureDetail
     *
     * @param empDto
     * @param attendanceKind
     * @return
     */
    private AttendanceEnsureDetail getAttendanceEnsureDetail(EmpDto empDto, AttendanceKind attendanceKind) {
        AttendanceEnsureDetail detailOne = new AttendanceEnsureDetail();
        detailOne.setDepId(empDto.getDeptId());
        detailOne.setDepName(empDto.getDeptName());
        detailOne.setEmpId(empDto.getId());
        detailOne.setName(empDto.getName());
        detailOne.setAttendanceKind(attendanceKind);

        AttendanceOperateDto dto = DtoFactory.convert(detailOne, new AttendanceOperateDto());
        attendanceService.AddOrUpdateAttendance(dto);
        detailOne.setIsSure(true);
        detailOne.setSureDate(new Date());
        detailOne.setLastUpdateTime(new Date());
        return detailOne;

    }


    //更新AttendanceSummery的出勤情况
    private void updateAttendCount(Long deptId, Date date, List<AttendanceEnsureDetail> details, String dayStation) {

        Integer year = DateHelper.getYear(date);
        Integer month = DateHelper.getMonth(date);
        Integer day = DateHelper.getDay(date);
        String k = "k_" + day;
        Criteria criterion = getSession().createCriteria(Attendance.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("depId", deptId));
        criterion.add(Restrictions.eq("year", year));
        criterion.add(Restrictions.eq("month", month));
        if (dayStation.equals("休息")) {
            criterion.add(Restrictions.eq(k, AttendanceKind.WX));
        } else if (dayStation.equals("上班")) {
            criterion.add(Restrictions.eq(k, AttendanceKind.I));
        } else if (dayStation.equals("公休加班")) {
            criterion.add(Restrictions.eq(k, AttendanceKind.J1));
        } else {
            criterion.add(Restrictions.eq(k, AttendanceKind.J2));
        }
//        if (DateHelper.getDay(date) < 7 && DateHelper.getDay(date) > 1) {
//            criterion.add(Restrictions.eq(k, AttendanceKind.I));
//        } else {
//            criterion.add(Restrictions.eq(k, AttendanceKind.WX));
//        }
        List<Attendance> attendances = criterion.list();
        List<Long> empIds = new ArrayList<>();
        if (details != null && details.size() > 0) {
            for (AttendanceEnsureDetail a : details) {
                if (a.getIsSure() != null && a.getIsSure()) //单独打考勤为'出勤'的情况，
                    empIds.add(a.getEmpId());
            }
        }
        if (attendances != null && attendances.size() > 0) {
            for (Attendance attendance : attendances) {
                if (empIds.size() > 0 && empIds.contains(attendance.getEmpId())) continue;
                else {
                    if (dayStation.equals("休息")) {
                        attendance.getAttendanceSummery().setRest((attendance.getAttendanceSummery().getRest() == null ? 0 : attendance.getAttendanceSummery().getRest()) + 1);
                    } else if (dayStation.equals("上班")) {
                        attendance.getAttendanceSummery().setAttend((attendance.getAttendanceSummery().getAttend() == null ? 0 : attendance.getAttendanceSummery().getAttend()) + 1);
                    } else if (dayStation.equals("公休加班")) {
                        attendance.getAttendanceSummery().setWeekendWork((attendance.getAttendanceSummery().getWeekendWork() == null ? 0 : attendance.getAttendanceSummery().getWeekendWork()) + 1);
                    } else {
                        attendance.getAttendanceSummery().setVacationWork((attendance.getAttendanceSummery().getVacationWork() == null ? 0 : attendance.getAttendanceSummery().getVacationWork()) + 1);
                    }

                }
            }

        }

    }

    @Override
    public List<EmpAttendanceDto> getEmpAttendanceInfo(EmpAttendancesCriteria criteria) {
        String date = DateHelper.formatDate(criteria.getAttDate(), "yyyy-MM-dd 00:00:00");
        String sql = "select e.id empId,e.real_name empName,e.emp_dept_id deptId,d.dept_name deptName , is_sure  isSure , attendance_Kind  attendanceKind ,'" + date + "' attDate from syy_oa_hr_employees e LEFT JOIN syy_oa_hr_dept d on e.emp_dept_id = d.id left JOIN" +
                "(select emp_Id,is_sure,attendance_Kind from syy_oa_att_ensure_detail  where to_char(att_Date,'yyyy-MM-dd')='" + DateHelper.formatDate(criteria.getAttDate(), "yyyy-MM-dd") + "' )t on e.id = t.emp_id where e.is_active=1 and  e.emp_status=1";

        if (criteria.getDeptId() != null && criteria.getDeptId() > 0) {
            sql += " and e.emp_dept_id=" + criteria.getDeptId();
        }

        if (criteria.getName() != null) {
            sql += "and e.real_name like'" + "%" + criteria.getName() + "%" + "'";
        }
        sql += " order by e.id asc ";

        List<EmpAttendanceDto> dtos = queryForObjects(EmpAttendanceDto.class, sql, null);
        if (dtos != null)
            return dtos;

        return new ArrayList<>();
    }


    @Override
    public List<EmpAttendanceWorkDto> getEmpRestInfo(EmpAttendanceWorkCriteria criteria) {
        Criteria criterion = getSession().createCriteria(AttendanceSummery.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("year", DateHelper.getYear(new Date())));
        if (criteria.getDeptId() != null && criteria.getDeptId() > 0) {
            criterion.add(Restrictions.eq("depId", criteria.getDeptId()));
        }
        if (criteria.getMonth() != null && criteria.getMonth() > 0) {
            criterion.add(Restrictions.eq("month", criteria.getMonth()));
        }
        if (criteria.getYear() != null && criteria.getYear() > 0) {
            criterion.add(Restrictions.eq("year", criteria.getYear()));
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getEmpName()))
            criterion.add(Restrictions.like("empName", "%" + criteria.getEmpName() + "%"));
        List<AttendanceSummery> list = criterion.list();//当月某部门的考勤统计
        List<EmpAttendanceWorkDto> dtos = new ArrayList<>();

//todo 等待修改，最终指定较为简单的接口
        List<EmpDto> empDtos = employeeService.findEmpsByDept(criteria.getDeptId());//该部门的所有员工-》工龄
        List<Long> ids = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (AttendanceSummery as : list) {
                ids.add(as.getEmpId());
            }
        }
        if (empDtos != null && empDtos.size() > 0) {
            for (EmpDto empDto : empDtos) {
                EmpAttendanceWorkDto dto = new EmpAttendanceWorkDto();
                dto.setEmpName(empDto.getName());
                dto.setJoinTime(empDto.getJoinTime());//参加工作时间
                dto.setWorkAge(empDto.getSeniority());//工龄
                if (ids.contains(empDto.getId())) {
                    for (AttendanceSummery as : list) {
                        if (as.getEmpId().equals(empDto.getId())) {
                            //组装数据
//                    dto.setShouldDays(f(as.getShouldDays()));//应该享受休假天数//未用到
                            Float f = f(as.getVisitFamily()) + f(as.getSpa()) + f(as.getAleave()) + f(as.getVacation())
                                    + f(as.getNurse()) + f(as.getSick()) + f(as.getWeddingFuneral()) + f(as.getStudyExperience())
                                    + f(as.getOffJob()) + f(as.getWild()) + f(as.getLabor()) + f(as.getAbsenteeism())
                                    + f(as.getBirthChild()) + f(as.getWorkHurt()) + f(as.getRest()) + f(as.getCompensate()) + f(as.getDrug()) + f(as.getCustody()) + f(as.getLbirth()) + f(as.getTstudy());
                            dto.setRest(f);//当月休假天数我的理解数所有种类的休息总和
                            //todo 慢点
                            float d = setSumDay(criteria.getDeptId(), empDto.getId(), DateHelper.getYear(new Date())); //累计休假天数---全年的
                            dto.setSumDays(d);//累计休假天数---全年的
                            dto.setNotDays(f(as.getAttend()) + f(as.getGwork()) + f(as.getJwork()) + f(as.getBusinessTravel()));//未休假天数,上班天数
                            dto.setSpa(f(as.getSpa()));//疗养天数
//                    dto.setSpaAndRestDays(f(as.getSpa()) + f(as.getRest()));//休疗累计天数
                            dto.setSpaAndRestDays(f(as.getSpa()) + f(as.getVacation()));//休疗累计天数
//                    dto.setVacationWork(f(as.getVacationWork()));//节日加班天数
//                    dto.setWeekendWork(f(as.getWeekendWork()));//周末加班天数
//                    dto.setSum(f(as.getWeekendWork()) + f(as.getVacationWork()));//合计
                            dto.setCompensate(f(as.getCompensate()));//补休
                        }
                    }
                }
                dtos.add(dto);
            }
        }

//        if (list != null && list.size() > 0) {
//            for (AttendanceSummery as : list) {
//                ids.add(as.getEmpId());
//                EmpDto emp = null;
//                for (EmpDto d : empDtos) {
//                    if (d.getId().equals(as.getEmpId())) {
//                        emp = d;
//                        break;//跳出第二层循环
//                    }
//                }
//                if (emp != null) {
//                    //组装数据
//                    EmpAttendanceWorkDto dto = new EmpAttendanceWorkDto();
//                    dto.setEmpName(emp.getName());
//                    dto.setJoinTime(emp.getJoinTime());//参加工作时间
//                    dto.setWorkAge(emp.getSeniority());//工龄
//
////                    dto.setShouldDays(f(as.getShouldDays()));//应该享受休假天数//未用到
//
//                    Float f = f(as.getVisitFamily()) + f(as.getSpa()) + f(as.getAleave()) + f(as.getVacation())
//                            + f(as.getNurse()) + f(as.getSick()) + f(as.getWeddingFuneral()) + f(as.getStudyExperience())
//                            + f(as.getOffJob()) + f(as.getWild()) + f(as.getLabor()) + f(as.getAbsenteeism())
//                            + f(as.getBirthChild()) + f(as.getWorkHurt()) + f(as.getRest()) + f(as.getCompensate()) + f(as.getDrug()) + f(as.getCustody()) + f(as.getLbirth()) + f(as.getTstudy());
//                    dto.setRest(f);//当月休假天数我的理解数所有种类的休息总和
//                    //todo 慢点
//                    float d = setSumDay(criteria.getDeptId(), emp.getId(), DateHelper.getYear(new Date())); //累计休假天数---全年的
//                    dto.setSumDays(d);//累计休假天数---全年的
//
//
//                    dto.setNotDays(f(as.getAttend()) + f(as.getGwork()) + f(as.getJwork()) + f(as.getBusinessTravel()));//未休假天数,上班天数
//                    dto.setSpa(f(as.getSpa()));//疗养天数
////                    dto.setSpaAndRestDays(f(as.getSpa()) + f(as.getRest()));//休疗累计天数
//                    dto.setSpaAndRestDays(f(as.getSpa()) + f(as.getVacation()));//休疗累计天数
////                    dto.setVacationWork(f(as.getVacationWork()));//节日加班天数
////                    dto.setWeekendWork(f(as.getWeekendWork()));//周末加班天数
////                    dto.setSum(f(as.getWeekendWork()) + f(as.getVacationWork()));//合计
//                    dto.setCompensate(f(as.getCompensate()));//补休
//                    dtos.add(dto);
//                }
//            }
//        } else {
//            //当没有数据的时候，
//            if (empDtos != null && empDtos.size() > 0) {
//                for (EmpDto dto : empDtos) {
//                    EmpAttendanceWorkDto workDto = new EmpAttendanceWorkDto();
//                    workDto.setEmpName(dto.getName());
//                    workDto.setJoinTime(dto.getJoinTime());
//                    workDto.setWorkAge(dto.getSeniority());
//                    dtos.add(workDto);
//                }
//            }
//        }
        return dtos;
    }

    @Override
    public List<EmpAttendanceWorkDLoadDto> getEmpRestDLoadInfo(EmpAttendanceWorkCriteria empAttendanceWorkCriteria) {
        List<EmpAttendanceWorkDto> list = this.getEmpRestInfo(empAttendanceWorkCriteria);
        List<EmpAttendanceWorkDLoadDto> lists = new ArrayList<>();
        for (EmpAttendanceWorkDto attendanceWorkDto : list) {
            EmpAttendanceWorkDLoadDto empAttendanceWorkDto = new EmpAttendanceWorkDLoadDto();
            empAttendanceWorkDto.setEmpName(attendanceWorkDto.getEmpName());
            empAttendanceWorkDto.setJoinTime(DateHelper.formatDate(DateHelper.parse(attendanceWorkDto.getJoinTime()), "yyyy-MM-dd"));
            empAttendanceWorkDto.setWorkAge(attendanceWorkDto.getWorkAge());
            empAttendanceWorkDto.setShouldDays(attendanceWorkDto.getShouldDays());
            empAttendanceWorkDto.setRest(attendanceWorkDto.getRest());
            empAttendanceWorkDto.setSumDays(attendanceWorkDto.getSumDays());
            empAttendanceWorkDto.setNotDays(attendanceWorkDto.getNotDays());
            empAttendanceWorkDto.setSpa(attendanceWorkDto.getSpa());
            empAttendanceWorkDto.setSpaAndRestDays(attendanceWorkDto.getSpaAndRestDays());
            empAttendanceWorkDto.setCompensate(attendanceWorkDto.getCompensate());
            lists.add(empAttendanceWorkDto);
        }
        return lists;
    }

    @Override
    public List<NearlyFiveDayAttendanceInfoDto> getNearlyFiveDayAttendance() {

        Date endDate = new Date();

        Date beginDate = DateHelper.addDay(endDate, -5);
        String strBegin = DateHelper.formatDate(beginDate, "yyyy-MM-dd");
        String strEnd = DateHelper.formatDate(endDate, "yyyy-MM-dd");


        String sql = " select e.id depId,e.dept_name deptName ,t.att_date attDate,is_sure isSure from syy_oa_hr_dept e  left JOIN  \n" +
                " (select dep_id,att_date,is_sure  from syy_oa_att_ensure_info   WHERE att_date BETWEEN to_date('" + strBegin + "','yyyy_MM-dd') and to_date('" + strEnd + "','yyyy_MM-dd') )t on e.id = t.dep_id where e.is_active=1 ";

        sql += " order by e.id";
        List<NearlyFiveDayAttListDto> dtolist = queryForObjects(NearlyFiveDayAttListDto.class, sql, null);

        List<NearlyFiveDayAttendanceInfoDto> fiveDayList = new ArrayList<>();
        if (dtolist != null && dtolist.size() > 0) {
            for (NearlyFiveDayAttListDto info : dtolist) {
                Boolean addflag = false;
                if (fiveDayList.size() > 0) {
                    for (NearlyFiveDayAttendanceInfoDto fiveDto : fiveDayList) {
                        if (fiveDto.getDepId().equals(info.getDepId())) {
                            if (info.getAttDate() != null)
                                fiveDto.getAttInfoMap().put(DateHelper.formatDate(info.getAttDate(), "yyyy-MM-dd"), info.getIsSure());
                            addflag = true;
                            break;
                        }
                    }
                }
                if (!addflag) {
                    NearlyFiveDayAttendanceInfoDto dto = new NearlyFiveDayAttendanceInfoDto();
                    dto.setDepId(info.getDepId());
                    dto.setDeptName(info.getDeptName());
                    Map<String, Boolean> map = new HashMap<>();
                    map.put(DateHelper.formatDate(info.getAttDate(), "yyyy-MM-dd"), info.getIsSure());
                    dto.setAttInfoMap(map);
                    fiveDayList.add(dto);
                }
            }
        }
        return fiveDayList;
    }

    @Override
    public AttendanceOperateDto getInfoDetail(Long empId, String editDate) {

        Criteria criterion = getSession().createCriteria(AttendanceEnsureDetail.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("empId", empId));
        criterion.add(Restrictions.eq("attDate", DateHelper.parse(editDate, "yyyy-MM-dd")));
        List<AttendanceEnsureDetail> ad = criterion.list();
        if (ad != null && ad.size() > 0) {
            return DtoFactory.convert(ad.get(0), new AttendanceOperateDto());
        } else {
//            EmpDto dto=employeeService.getEmpById(empId);
//            AttendanceOperateDto d=new AttendanceOperateDto();
//            d.setEmpId(empId);
//            d.setName(dto.getName());
//            d.setDepId(dto.getDeptId());
//            Date date=DateHelper.parse(editDate, "yyyy-MM-dd");
//            d.setDepName(dto.getDeptName());
//            d.setAttDate(date);
//            d.setBeginDate(DateHelper.parse(editDate));
//            d.setEndDate(DateHelper.addDay(DateHelper.parse(editDate), 1));
//            d.setAttendanceKind(AttendanceKind.I);
//            d.setTimeRange(1f);
//            return d;
            return null;
        }
    }

    private void setRangKind(AttendanceOperateDto dto) {

        if (!dto.getAttDate().after(dto.getEndDate())) {
            Date d = DateHelper.addDay(dto.getAttDate(), 1);
            if (d.after(dto.getEndDate())) return;
            dto.setAttDate(d);
            addOrUpdateEnsureInfo(dto);
        }
    }

    private float f(Float value) {
        return value == null ? 0f : value;
    }

    private float setSumDay(Long deptId, Long empId, int year) {
        float f = 0f;
        Criteria criteria = getSession().createCriteria(AttendanceSummery.class);

        criteria.add(Restrictions.eq("depId", deptId));
        criteria.add(Restrictions.eq("isActive", true));//可查询的
        criteria.add(Restrictions.eq("empId", empId));
        criteria.add(Restrictions.eq("year", year));
        List<AttendanceSummery> list = criteria.list();//一个人全年的统计
        if (list != null && list.size() > 0) {
            for (AttendanceSummery as : list) {
                Float fl = f(as.getVisitFamily()) + f(as.getSpa()) + f(as.getAleave()) + f(as.getVacation())
                        + f(as.getNurse()) + f(as.getSick()) + f(as.getWeddingFuneral()) + f(as.getStudyExperience())
                        + f(as.getOffJob()) + f(as.getWild()) + f(as.getLabor()) + f(as.getAbsenteeism())
                        + f(as.getBirthChild()) + f(as.getWorkHurt()) + f(as.getRest()) + f(as.getCompensate());
                f = f + fl;
            }
        }
        return f;
    }

}




