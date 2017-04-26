package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendSumCtriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendanceCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.application.factory.AttendFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.attend.Attend;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendRepository;
import com.sunesoft.lemon.syms.eHr.domain.attend.AttendType;

import com.sunesoft.lemon.syms.eHr.domain.empInfo.EmployeeRepository;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.AttendanceByFlowDto;
import com.sunesoft.lemon.syms.hrForms.application.formevection.FormEvectionService;
import com.sunesoft.lemon.syms.hrForms.application.formleave.FormLeaveService;

import org.hibernate.Query;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * 出勤业务
 * Created by xiazl on 2017/3/2.
 */
@Service("attendService")
public class AttendServiceImpl extends GenericHibernateFinder implements AttendService {

    @Autowired
    AttendRepository repository;

    @Autowired
    EmployeeRepository empRepository;

    @Autowired
    AttendTypeService servce;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    HolidayInfoService holidayInfoService;
    @Autowired
    FormLeaveService formLeaveService;
    @Autowired
    FormEvectionService formEvectionService;
    @Autowired
    AttendTypeService attendTypeService;
    @Autowired
    DeptmentService deptmentService;

    @Override
    public CommonResult create(Attend attend) {
        return null;
    }

    @Override
    public CommonResult create(AttendDto dto) {
        Attend att = repository.get(dto.getEmpId(), DateHelper.parse(dto.getSdateTime(), "yyyy-MM-dd"));
        if (att != null && att.getIsActive()) return ResultFactory.commonError("该员工的该日考勤已经存在");
        Attend attend = new Attend();
        attend = AttendFactory.convertFromDto(dto, attend);
        if (dto.getTypeId() != null) {
            AttendType type = servce.get(dto.getTypeId());
            attend.setType(type);
        }
        return ResultFactory.commonSuccess(repository.save(attend));
    }

    @Override
    public CommonResult update(AttendDto dto) {
        if (dto.getTypeId() == null) return new CommonResult(false, "请选择出勤类型");
        Attend attend = repository.get(dto.getId());
        if (attend == null || !attend.getIsActive()) return ResultFactory.commonError("该考勤不存在");
        //todo 若果确认的情况下那么该考勤的日期，确认状态不给修改,考勤状态这里暂时不做限制
        AttendType type = null;
        try {
            type = servce.get(dto.getTypeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        attend.setType(type);

        return ResultFactory.commonSuccess(repository.save(attend));
    }

    @Override
    public AttendDto get(Long id) {
        Attend attend = repository.get(id);
        if (attend == null || !attend.getIsActive()) return null;
        return AttendFactory.convertToDto(attend);
    }

    @Override
    public CommonResult delete(Long id) {
        Attend attend = repository.get(id);
        if (attend == null || !attend.getIsActive()) return ResultFactory.commonError("该考勤已经不存在！");
        attend.setIsActive(false);
        attend.setLastUpdateTime(new Date());
        return ResultFactory.commonSuccess(repository.save(attend));
    }

    @Override
    public PagedResult<AttendDto> page(AttendCriteria criteria) throws Exception {

        PagedResult<Attend> page = repository.page(criteria);
        List<AttendDto> attendDtos = AttendFactory.convertList(page.getItems());//现有考勤
        List<Long> ids = new ArrayList<>();
        if (attendDtos != null && attendDtos.size() > 0) {
            for (AttendDto dto : attendDtos) {
                ids.add(dto.getEmpId());
            }
        }
        //部分没有或者全部没有的情况
        //该天的假日情况(出勤vs休息)
        AttendType typeI = servce.getByCord("I");//出勤的
        AttendType typeJ1 = servce.getByCord("J1");//公休加班
        AttendType typeJ2 = servce.getByCord("J2");//公休加班
        AttendType typeZ = servce.getByCord("Z");//出差
        //todo 获取该部门员工的出差情况(deptId day)
        List<Long> zEmpId = formEvectionService.getEmpStatusByBusiness(criteria.getDepId(), criteria.getAttDate());
        //todo 获取该部门员工的请假情况(deptId day)
        List<AttendanceByFlowDto> attendanceByFlowDtos = formLeaveService.getEmpsStatusByLeave(criteria.getDepId(), criteria.getAttDate());
        List<Long> jEmpId = new ArrayList<>();
        if (attendanceByFlowDtos.size() > 0) {
            for (AttendanceByFlowDto dto : attendanceByFlowDtos) {
                jEmpId.add(dto.getApplyer());
            }

        }
        String dayStation = holidayInfoService.getEmpWorkStateByHoliday(criteria.getAttDate());
        List<EmpDto> empDtos = employeeService.findEmpsByDeptIdAndName(criteria.getDepId(), criteria.getEmpName());
        List<Long> empds = new ArrayList<>();
        if (empDtos != null && empDtos.size() > 0) {
            for (EmpDto empDto : empDtos) {
                empds.add(empDto.getId());
                if (!ids.contains(empDto.getId())) {
                    AttendDto attDto = new AttendDto();
                    attDto.setDepId(empDto.getDeptId());
                    attDto.setDepName(empDto.getDeptName());
                    attDto.setEmpId(empDto.getId());
                    attDto.setEmpName(empDto.getName());
                    attDto.setSdateTime(DateHelper.formatDate(criteria.getAttDate(), "yyyy-MM-dd"));
                    if (zEmpId.size() > 0 && zEmpId.contains(empDto.getId())) { //如果该员工在出差日期中
                        attDto.setTypeId(typeZ.getId());
                        attDto.setTypeName(typeZ.getName());
                    } else if (jEmpId.size() > 0 && jEmpId.contains(empDto.getId())) {//如果该员工在请假日期中
                        for (AttendanceByFlowDto a : attendanceByFlowDtos) {
                            AttendType jleave;
                            try {
                                jleave = servce.getByCord(ConvertToCord(a.getLeaveType().name()));
                                attDto.setTypeId(jleave.getId());
                                attDto.setTypeName(jleave.getName());
                            } catch (Exception ex) {
                                System.out.print("没有该出勤类型");
                                ex.printStackTrace();
                            }
                        }
                    } else {

                        if (dayStation != "") { //设计假日类型
                            if (dayStation.equals("休息")) {
                                attDto.setTypeId(typeJ2.getId());//公休打考勤就是公休
                                attDto.setTypeName(typeJ2.getName());
                            } else {
                                attDto.setTypeId(typeI.getId());//正常上班
                                attDto.setTypeName(typeI.getName());
                            }
                        } else {//为设置假日类型
                            if (isWeak(criteria.getAttDate())) {
                                attDto.setTypeId(typeJ1.getId());
                                attDto.setTypeName(typeJ1.getName());
                            } else {
                                attDto.setTypeId(typeI.getId());
                                attDto.setTypeName(typeI.getName());
                            }
                        }
                    }
                    attendDtos.add(attDto);
                }
            }
        }


        // 去掉已经被禁用的用户考勤
        return new PagedResult<AttendDto>(getAttendDto(attendDtos, empds), page.getPageNumber(), page.getPageSize(), page.getTotalItemsCount());
    }

    @Override
    public CommonResult ensureOne(AttendDto dto) {
        Attend hasAttend = repository.get(dto.getEmpId(), DateHelper.parse(dto.getSdateTime(), "yyyy-MM-dd"));
        if (hasAttend != null && hasAttend.getIsActive()) {
            if ((hasAttend.getIsSure() != null && hasAttend.getIsSure()) || (hasAttend.getDeptSure() != null && hasAttend.getDeptSure())) {
                return new CommonResult(false, "该员工考勤已确认");
            }
        } else {
            hasAttend = new Attend();
        }
        hasAttend = AttendFactory.convertFromDto(dto, hasAttend);
        EmpDto dto1 = employeeService.getEmpById(dto.getEmpId());
        hasAttend.setEmpId(dto1.getId());
        hasAttend.setEmpName(dto1.getName());
        hasAttend.setDepId(dto1.getDeptId());
        hasAttend.setDepName(dto1.getDeptName());
        AttendType attendType = attendTypeService.get(dto.getTypeId());
        hasAttend.setType(attendType);
        hasAttend.setOneSureTime(new Date());
        hasAttend.setIsSure(true);
        hasAttend.setLastUpdateTime(new Date());
        return ResultFactory.commonSuccess(repository.save(hasAttend));
    }

    @Override
    public CommonResult ensureDept(List<AttendDto> dtos) {
        if (dtos == null || dtos.isEmpty()) return new CommonResult(false, "无人员");
        for (AttendDto dto : dtos) {
            if (dto.getId() != null) {//已经单个确认过了
                Attend attend = repository.get(dto.getId());
                attend.setDeptSure(true);
                attend.setDeptSureTime(new Date());
                attend.setLastUpdateTime(new Date());
                repository.save(attend);
            } else {
                //今天第一次打考勤
                Attend att = new Attend();
                att.setDateTime(DateHelper.parse(dto.getSdateTime(), "yyyy-MM-dd"));
                att.setDeptSureTime(new Date());
                att.setDeptSure(true);
                EmpDto empDto = employeeService.getEmpById(dto.getEmpId());
                att.setEmpId(empDto.getId());
                att.setEmpName(empDto.getName());
                att.setDepId(empDto.getDeptId());
                att.setDepName(empDto.getDeptName());
                AttendType type = attendTypeService.get(dto.getTypeId());
                att.setType(type);
                att.setLastUpdateTime(new Date());
                repository.save(att);
            }
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<AttendSumloadDto> AttendSumPage(AttendSumCtriteria criteria) {
        String hql = " select new List(a.empId as empId,a.empName as empName,t.cord as cord,count(t.cord) as coun) from Attend a join a.type t where a.isActive=1 and (a.isSure=1 or a.deptSure=1) and t.isActive=1";

        if (criteria.getDeptId() != null) {
            hql = hql + " and a.depId= '" + criteria.getDeptId() + "'";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getName())) {
            hql = hql + " and a.empName like '%" + criteria.getName() + "%' ";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getBegin())) {
            hql = hql + " and to_char(a.dateTime,'yyyy-MM-dd') >= '" + criteria.getBegin() + "'";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getEnd())) {
            hql = hql + " and to_char(a.dateTime,'yyyy-MM-dd') <= '" + criteria.getEnd() + "'";
        }
        hql = hql + " group by t.cord,t.name, a.empId,a.empName order by a.empId";
        List<List> lists = getSession().createQuery(hql).list();
        List<Long> ids = new ArrayList<>();
        List<AttendSumloadDto> summmery = new ArrayList<>();
        Map<Long, AttendSumloadDto> map = new HashMap<>();
        if (lists != null && lists.size() > 0) {
            for (List dto : lists) {
//                if (!ids.contains(dto.get(0))) {//如果不存在这个人的情况下
//                    ids.add((Long)dto.get(0));
                if (map.get((Long) dto.get(0)) == null) {
                    AttendSumloadDto load = new AttendSumloadDto();
                    load.setEmpName((String) dto.get(1));
                    //设置数目
                    load = getA(load, (String) dto.get(2), dto.get(3) == null ? 0 : Integer.parseInt(((Long) dto.get(3)).toString()));
                    map.put((Long) dto.get(0), load);

                } else {//存在这个人的情况下
                    AttendSumloadDto sd = map.get((Long) dto.get(0));
                    //设置数目
                    sd = getA(sd, (String) dto.get(2), dto.get(3) == null ? 0 : Integer.parseInt(((Long) dto.get(3)).toString()));
                }
            }
        }
        if (map.size() > 0) {
            for (Long key : map.keySet()) {
                summmery.add(map.get(key));
            }
        }
        return new PagedResult<AttendSumloadDto>(summmery, 1, 10, summmery.size());
    }

    @Override
    public PagedResult<EmpAttendWorkDto> AttendOverPage(AttendSumCtriteria criteria) {
        List<EmpDto> empDtos = employeeService.findEmpsByDept(criteria.getDeptId());
        List<EmpDto> del = new ArrayList<>();
        if (empDtos != null && empDtos.size() > 0) {
            for (EmpDto empDto : empDtos) {
                if ((empDto.getStatus() == null || empDto.getStatus() == 0) || (!StringUtils.isNullOrWhiteSpace(criteria.getName()) && !empDto.getName().contains(criteria.getName()))) {
                    del.add(empDto);
                }
            }
            empDtos.removeAll(del);
        }
        String hql = " select new List(a.empId as empId,a.empName as empName,t.cord as cord,count(t.cord) as coun) from Attend a join a.type t  where a.isActive=1 and (a.isSure=1 or a.deptSure=1) and t.isActive=1 ";

        if (criteria.getDeptId() != null) {
            hql = hql + " and a.depId= '" + criteria.getDeptId() + "'";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getName())) {
            hql = hql + " and a.empName like '%" + criteria.getName() + "%' ";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getBegin())) {
            hql = hql + " and to_char(a.dateTime,'yyyy-MM-dd') >= '" + criteria.getBegin() + "'";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getEnd())) {
            hql = hql + " and to_char(a.dateTime,'yyyy-MM-dd') <= '" + criteria.getEnd() + "'";
        }
        hql = hql + " group by t.cord,t.name, a.empId,a.empName order by a.empId";
        List<List> lists = getSession().createQuery(hql).list();
        List<EmpAttendWorkDto> summmery = new ArrayList<>();
        Map<Long, EmpAttendWorkDto> map = new HashMap<>();
        if (empDtos != null && empDtos.size() > 0) {
            for (EmpDto empDto : empDtos) {
                EmpAttendWorkDto load = new EmpAttendWorkDto();
                load.setEmpId((Long) empDto.getId());
                load.setEmpName((String) empDto.getName());
                map.put(empDto.getId(), load);
            }
        }
        if (lists != null && lists.size() > 0) {
            for (List dto : lists) {
                EmpAttendWorkDto sd = map.get((Long) dto.get(0));
                if (sd != null)
                    sd = getB(sd, (String) dto.get(2), dto.get(3) == null ? 0 : Integer.parseInt(((Long) dto.get(3)).toString()));
            }
        }
        if (map.size() > 0) {
            for (Long key : map.keySet()) {
                summmery.add(map.get(key));
            }
        }
        List<EmpAttendWorkDto> summm = new ArrayList<>();

        if (summmery.size() > 0) {
            for (EmpAttendWorkDto ek : summmery) {
                EmpAttendWorkDto dto = ek;
                dto.setSpaAndRestDays((dto.getSpa() == null ? 0 : dto.getSpa()) + (dto.getSumDays() == null ? 0 : dto.getSumDays()));
                for (EmpDto empDto : empDtos) {
                    if (empDto.getId().equals(dto.getEmpId())) {
                        dto.setJoinTime(StringUtils.isNullOrWhiteSpace(empDto.getJoinTime()) ? null : (empDto.getJoinTime()).substring(0, 10));
                        dto.setWorkAge(empDto.getSeniority());
                        dto.setNotDays(empDto.getHasYear() == null ? null : empDto.getHasYear().intValue());
                    }
                }
                summm.add(dto);

            }
        }

        //分页
        List twenty = new ArrayList();
        int totalCount = summm.size();
        int min=criteria.getPageNumber()*criteria.getPageSize()>totalCount?totalCount:criteria.getPageNumber()*criteria.getPageSize();
        if (min>0){
            for (int i = (criteria.getPageNumber()-1)*criteria.getPageSize();i <min ;i++){
                twenty.add(summm.get(i));
            }
        }
        return new PagedResult<EmpAttendWorkDto>(twenty, criteria.getPageNumber(), criteria.getPageSize(), summm.size());
    }

    private AttendSumloadDto getA(AttendSumloadDto dto, String cord, Integer count) {
        switch (cord) {
            case "I":
                dto.setAttend(count);
                break;
            case "YB1":
                dto.setYB1(count);
                break;
            case "YB2":
                dto.setYB2(count);
                break;
            case "YB3":
                dto.setYB3(count);
                break;
            case "S3":
                dto.setAleave(count);
                break;
            case "K1":
                dto.setAbsenteeism(count);
                break;
            case "K2":
                dto.setCustody(count);
                break;
            case "K3":
                dto.setDrug(count);
                break;
            case "W":
                dto.setLabor(count);
                break;
            case "Z":
                dto.setBusinessTravel(count);
                break;
            case "B2":
                dto.setCompensate(count);
                break;
            case "J1":
                dto.setGwork(count);
                break;
            case "J2":
                dto.setJwork(count);
                break;
            case "Y1":
            case "Y2":
            case "Y3":
                dto.setWild(dto.getWild() == null ? count : dto.getWild() + count);
                break;
            case "X1":
                dto.setStudyExperience(count);
                break;
            case "X2":
                dto.setTstudy(count);
                break;
            case "S1":
                dto.setVacation(count);
                break;
            case "L":
                dto.setSpa(count);
                break;
            case "T":
                dto.setVisitFamily(count);
                break;
            case "H":
                dto.setWeddingFuneral(count);
                break;
            case "G":
                dto.setWorkHurt(count);
                break;
            case "C":
                dto.setBirthChild(count);
                break;
            case "C1":
                dto.setLbirth(count);
                break;
            case "S2":
            case "S21":
            case "S22":
                dto.setNurse(dto.getNurse() == null ? count : dto.getNurse() + count);
                break;
            case "B":
                dto.setSick(count);
                break;
        }
        return dto;
    }

    private EmpAttendWorkDto getB(EmpAttendWorkDto dto, String cord, Integer count) {
        switch (cord) {
//            case "I":
//            case "Z":
//            case "YB1":
//            case "YB2":
//            case "YB3":
//                dto.setNotDays(dto.getNotDays() == null ? count : dto.getNotDays() + count);
//                break;
            case "B2":
                dto.setCompensate(count);
                break;
            case "J1":
                dto.setWeekendWork(count);
                break;
            case "J2":
                dto.setVacationWork(count);
                break;
            case "L":
                dto.setSpa(count);
                break;
            case "S3":
            case "K1":
            case "K2":
            case "K3":
            case "W":
            case "Y1":
            case "Y2":
            case "Y3":
            case "X1":
            case "X2":
            case "S1":
            case "T":
            case "H":
            case "G":
            case "C":
            case "C1":
            case "S2":
            case "S21":
            case "S22":
            case "B":
                dto.setSumDays(dto.getSumDays() == null ? count : dto.getSumDays() + count);
                break;
        }
        return dto;
    }

    @Override
    public List<EmpAttendsDto> pageFindSimpleByDepId(AttendanceCriteria attendanceCriteria) {
        String hql = "select atd.id as id,atd.empName as empName,atd.dateTime as dateTime,atd.depId as depId,atd.depName as depName,atd.type.id as typeId,atd.type.name as typeName,atd.type.cord as cord from Attend atd";

        String whereCondition = " where atd.isActive=1 ";
        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getEmpName())) {
            whereCondition += " and atd.empName='" + attendanceCriteria.getEmpName() + "'";
        }
        if (attendanceCriteria.getBeginDate() != null) {
            Date FromTime = new java.sql.Date(attendanceCriteria.getBeginDate().getTime());
            whereCondition = whereCondition + " and to_char(atd.dateTime,'yyyy-MM-dd') >= '" + FromTime + "'";
        }
        if (attendanceCriteria.getEndDate() != null) {
            Date EndTime = new java.sql.Date(attendanceCriteria.getEndDate().getTime());
            whereCondition = whereCondition + " and to_char(atd.dateTime,'yyyy-MM-dd') <= '" + EndTime + "'";
        }

        whereCondition += " order by atd.dateTime asc";
        Query query = this.getSession().createQuery(hql + whereCondition);
        query.setResultTransformer(Transformers.aliasToBean(AttendDto.class));
        List<AttendDto> list = query.list();
        //部门员工查询
        List<AttendEmpDto> emps = new ArrayList<>();
        emps = empRepository.getByDeptId(attendanceCriteria.getDepId(), attendanceCriteria.getEmpName());
        List<EmpAttendsDto> empAttendsDtoList = new ArrayList<>(); //考勤信息记录集合。
        Date fromDate = attendanceCriteria.getBeginDate();
        Date endDate = attendanceCriteria.getEndDate();
        for (AttendEmpDto empDto : emps) {
            String name = empDto.getName();
            EmpAttendsDto empAttendsDto = new EmpAttendsDto();
            empAttendsDto.setEmpName(name);
            empAttendsDto.setDeptName(empDto.getDeptName());
            List<AttendDto> attendDtoList = new ArrayList<>();
            //TODO 根据员工姓名对已打考勤信息进行处理
            for (int i = 0; i < list.size(); i++) {
                String checkName = list.get(i).getEmpName();
                if (name.equals(checkName)) {
                    attendDtoList.add(list.get(i));
                }
            }
            empAttendsDto.setList(attendDtoList);
            empAttendsDtoList.add(empAttendsDto);
        }
        List<EmpAttendsDto> arrayList = new ArrayList<>(); //返回考勤信息记录集合。
        for (int i = 0; i < empAttendsDtoList.size(); i++) {
            String name = empAttendsDtoList.get(i).getEmpName();
            List<AttendDto> empAttendsDtoList1 = empAttendsDtoList.get(i).getList();
            EmpAttendsDto empAttendsDto = new EmpAttendsDto();
            empAttendsDto.setEmpName(name);
            empAttendsDto.setDeptName(empAttendsDtoList.get(i).getDeptName());
            //TODO 补全未打考勤信息
            empAttendsDto.setList(CompleteAttendance(fromDate, endDate, empAttendsDtoList1));
            arrayList.add(empAttendsDto);
        }
        return arrayList;
    }

    @Override
    public List<NewNearSevenDay> getNearlySevenDayAttendance() {


        Date endDate = new Date();
        Date beginDate = DateHelper.addDay(endDate, -6);
        String strBegin = DateHelper.formatDate(beginDate, "yyyy-MM-dd");
        String strEnd = DateHelper.formatDate(endDate, "yyyy-MM-dd");
        //todo sql
        String sql = "select a.dep_id as depId, a.dep_name as deptName,a.date_time as attDate,a.dept_sure as isSure from syy_oa_hr_attd a where a.is_active=1 and a.dept_sure=1 and a.date_time between to_date('" + strBegin + "','yyyy_MM-dd') and to_date('" + strEnd + "','yyyy_MM-dd') group by a.dep_id, a.dep_name, a.date_time,a.dept_sure order by a.date_time";
        List<NearlyFiveDayAttListDto> listDtos = queryForObjects(NearlyFiveDayAttListDto.class, sql, null);
        List<NewNearSevenDay> nnsds = getNnnsd(beginDate);
        if (listDtos != null && listDtos.size() > 0) {
            for (NearlyFiveDayAttListDto dto : listDtos) {
                for (NewNearSevenDay day : nnsds) {
                    if (day.getDeptId().equals(dto.getDepId())) {
                        day.getDateToInt().put(DateHelper.formatDate(dto.getAttDate(), "yyyy-MM-dd"), 1);
                    }
                }
            }
        }
        return nnsds;
    }


    private List<AttendDto> CompleteAttendance(Date fromDate, Date endDate, List<AttendDto> empAttendsDtoList1) {
        List<AttendDto> temp = new ArrayList<>();
        temp.addAll(empAttendsDtoList1);
        List<AttendDto> list = new ArrayList<>();
        if (endDate.after(fromDate)) {
            int sumDay = DateHelper.daysBetween(fromDate, endDate);
            if (temp.size() <= 0) {
                for (Integer i = 0; i <= sumDay; i++) {
                    AttendDto dto = new AttendDto();
                    dto.setDateTime(DateHelper.addDay(fromDate, i));
                    list.add(dto);
                }
                return list;
            }
            Boolean[][] flagDat = new Boolean[sumDay + 1][temp.size()];
            for (Integer i = 0; i <= sumDay; i++) {//构造实际查询日期区间
                for (Integer j = 0; j < temp.size(); j++) {
                    Date date = temp.get(j).getDateTime();
                    if (DateHelper.sameDate(date, DateHelper.addDay(fromDate, i))) { //当天日期存在
//                            list.add(temp.get(j));
                        flagDat[i][j] = true;
                    } else {
//                            AttendDto dto = new AttendDto();
//                            dto.setDateTime(DateHelper.addDay(fromDate, i));
//                            list.add(dto);
                        flagDat[i][j] = false;
                    }
                }
            }
            for (int m = 0; m <= sumDay; m++) {
                int count = -1;
                boolean flag = false;
                for (int n = 0; n < temp.size(); n++) {
                    if (flagDat[m][n]) {
                        flag = true;
                        count = n;
                        break;
                    } else {
                        flag = false;
                    }
                }
                if (flag) {
                    list.add(temp.get(count));
                } else {
                    AttendDto dto = new AttendDto();
                    dto.setDateTime(DateHelper.addDay(fromDate, m));
                    list.add(dto);
                }
            }

        }
        return list;
    }

    @Override
    public PagedResult<AttendSummeryResultDto> PageBySummeryDto(AttendanceCriteria attendanceCriteria) {

        String sql = "select att.emp_id as empId, att.emp_name as empName,type.cord as cord,count(type.cord) as countCord" +
                " from syy_oa_hr_attd att" +
                " join syy_oa_hr_attType type on type.id=att.type_id";
        String whereCond = " where att.is_active=1 ";

        if (attendanceCriteria.getBeginDate() != null) {
            Date FromTime = new java.sql.Date(attendanceCriteria.getBeginDate().getTime());
            whereCond += " and to_char(att.date_time,'yyyy-MM-dd') >= '" + DateHelper.formatDate(FromTime, "yyyy-MM-dd") + "'";
        }

        if (attendanceCriteria.getBeginDate() != null) {
            Date endTime = new java.sql.Date(attendanceCriteria.getEndDate().getTime());
            whereCond += " and to_char(att.date_time,'yyyy-MM-dd') <= '" + DateHelper.formatDate(endTime, "yyyy-MM-dd") + "'";
        }

        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getEmpName())) {
            whereCond += " and att.emp_name ='" + attendanceCriteria.getEmpName() + "'";
        }
        if (attendanceCriteria.getDepId() != null) {
            whereCond += " and att.dep_id ='" + attendanceCriteria.getDepId() + "'";
        }

        whereCond += " group by att.emp_id,att.emp_name,type.cord";
        List<AttendSummeryDto> list = queryForObjects(AttendSummeryDto.class, sql + whereCond, null);

        //todo 同名你是如何处理 ??
//        Map<String, Map<String, Integer>> stringMapHashMap = new HashMap<>();
//        for (AttendSummeryDto attendSummeryDto : list) {
//            String name = attendSummeryDto.getEmpName();
//            String cord = attendSummeryDto.getCord();
//            Integer cordCount = attendSummeryDto.getCountCord();
//            if (stringMapHashMap.containsKey(name)) {
//                Map<String, Integer> stringIntegerMap = stringMapHashMap.get(name);
//                stringIntegerMap.put(cord, cordCount);
//            } else {
//                Map<String, Integer> stringIntegerMap = new HashMap<>();
//                stringIntegerMap.put(cord, cordCount);
//                stringMapHashMap.put(name, stringIntegerMap);
//            }
//        }
//        List<AttendEmpDto> emps = new ArrayList<>();
//        emps = empRepository.getByDeptId(attendanceCriteria.getDepId(), attendanceCriteria.getEmpName());
//        List<Long> empIds=new ArrayList<>();
//        for (AttendEmpDto empDto : emps) {
//            empIds.add(empDto.getId());
//            String empName = empDto.getName();
//            if (!stringMapHashMap.containsKey(empName)) {
//                Map<String, Integer> map = new HashMap<>();
//                stringMapHashMap.put(empName, map);
//            }
//        }
//        List<AttendSummeryResultDto> resultDtos = new ArrayList<>();
//        for (String key : stringMapHashMap.keySet()) {
//            Map<String, Integer> stringIntegerMap = stringMapHashMap.get(key);
//            AttendSummeryResultDto resultDto = new AttendSummeryResultDto();
//            resultDto.setMaps(stringIntegerMap);
//            resultDto.setEmpName(key);
//            resultDtos.add(resultDto);
//        }

        List<AttendEmpDto> emps = empRepository.getByDeptId(attendanceCriteria.getDepId(), attendanceCriteria.getEmpName());//该方法已经除掉被禁用的用户
        Map<Long, AttendSummeryResultDto> map = new HashMap<>();
        //填充人员数据
        if (emps != null && emps.size() > 0) {
            for (AttendEmpDto dto : emps) {
                AttendSummeryResultDto ad = new AttendSummeryResultDto();
                ad.setEmpName(dto.getName());
                ad.setMaps(new HashMap<String, Integer>());
                map.put(dto.getId(), ad);
            }
        }

        List<AttendSummeryResultDto> resultDtos = new ArrayList<>();
        if (map.size() > 0) {
            //填充人员考勤数据
            if (list != null && list.size() > 0) {
                for (AttendSummeryDto sum : list) {
                    AttendSummeryResultDto r = map.get(sum.getEmpId());
                    if (r != null) {//这个人没有被禁用
                        if (sum.getCord().equals("Y1") || sum.getCord().equals("Y2") || sum.getCord().equals("Y3")) {
                            r.getMaps().put("Y", r.getMaps().get("Y") == null ? sum.getCountCord() : r.getMaps().get("Y") + sum.getCountCord());
                        } else if (sum.getCord().equals("S2") || sum.getCord().equals("S21") || sum.getCord().equals("S22"))
                            r.getMaps().put("S2", r.getMaps().get("S2") == null ? sum.getCountCord() : r.getMaps().get("S2") + sum.getCountCord());
                        else
                            r.getMaps().put(sum.getCord(), sum.getCountCord());
                    }
                }
            }
            //转换成所需要的数据类型
            for (Map.Entry<Long, AttendSummeryResultDto> entry : map.entrySet()) {
                resultDtos.add(entry.getValue());
            }
        }
        int total=resultDtos.size();
        List<AttendSummeryResultDto> dtos=new ArrayList<>();
        int min=attendanceCriteria.getPageNumber()*attendanceCriteria.getPageSize()>total?total:attendanceCriteria.getPageNumber()*attendanceCriteria.getPageSize();
        if(min>0){
            for (int i = (attendanceCriteria.getPageNumber()-1)*attendanceCriteria.getPageSize(); i < min; i++) {
                dtos.add(resultDtos.get(i));
            }
        }
        return new PagedResult<AttendSummeryResultDto>(dtos, attendanceCriteria.getPageNumber(), attendanceCriteria.getPageSize(),total);
    }

    @Override
    public List<AttendCalendar> findOneAttend(Long userId, String beginTime, String endTime) {

        String sql = " select a.id aid,a.date_time attDate,t.name descript from syy_oa_hr_attd a join syy_oa_hr_attType t on a.type_id=t.id where a.is_active=1 and t.is_active=1 and a.emp_id=" + userId + " and a.date_time between to_date('" + beginTime + "','yyyy_MM-dd') and to_date('" + endTime + "','yyyy_MM-dd') order by a.date_time ";
        return queryForObjects(AttendCalendar.class, sql, null);
    }

    @Override
    public List<AttendDowloadsDto> getDowlodsDto(List<DayModel> list, AttendanceCriteria attendanceCriteria) {

        List<EmpAttendsDto> empAttendsDtos = this.pageFindSimpleByDepId(attendanceCriteria);
        List<AttendDowloadsDto> dowloadsDtos = new ArrayList<>();
        for (EmpAttendsDto resultDto : empAttendsDtos) {
            AttendDowloadsDto dowloadsDto = new AttendDowloadsDto();
            dowloadsDto.setDeptName(resultDto.getDeptName());
            dowloadsDto.setEmpName(resultDto.getEmpName());
            dowloadsDto.setBeginTime(DateHelper.formatDate(attendanceCriteria.getBeginDate(), "yyyy-MM-dd"));
            dowloadsDto.setEndTime(DateHelper.formatDate(attendanceCriteria.getEndDate(), "yyyy-MM-dd"));
            List<AttendDto> attendDtos = resultDto.getList();

            dowloadsDto.setDays(getDays(attendDtos));
            dowloadsDto.setSystemWorking(getCount(attendDtos, "18"));
            dowloadsDto.setAttendD(getCount(attendDtos, "I"));
            dowloadsDto.setDriverD(getCount(attendDtos, "0"));
            dowloadsDto.setWuCaiD(getCount(attendDtos, "0"));
            dowloadsDto.setDiseaseD(getCount(attendDtos, "0"));
            dowloadsDto.setGwork(getCount(attendDtos, "J1"));
            dowloadsDto.setJwork(getCount(attendDtos, "J2"));
            dowloadsDto.setOtherNightWork(getCount(attendDtos, "YB1"));
            dowloadsDto.setSmallNightWork(getCount(attendDtos, "YB3"));
            dowloadsDto.setBigNightWork(getCount(attendDtos, "YB2"));
            dowloadsDto.setWildO(getCount(attendDtos, "Y1"));
            dowloadsDto.setWildW(getCount(attendDtos, "Y2"));
            dowloadsDto.setWildT(getCount(attendDtos, "Y3"));
            dowloadsDto.setBusinessTravel(getCount(attendDtos, "Z"));
            dowloadsDto.setStudyExperience(getCount(attendDtos, "X1"));
            dowloadsDto.setTstudy(getCount(attendDtos, "X2"));
            dowloadsDto.setVacation(getCount(attendDtos, "S1"));
            dowloadsDto.setSpa(getCount(attendDtos, "L"));
            dowloadsDto.setVisitFamily(getCount(attendDtos, "T"));
            dowloadsDto.setWeddingFuneral(getCount(attendDtos, "H"));
            dowloadsDto.setWorkHurt(getCount(attendDtos, "G"));
            dowloadsDto.setBirthChild(getCount(attendDtos, "C"));
            dowloadsDto.setLbirth(getCount(attendDtos, "C1"));
            dowloadsDto.setNurseH(getCount(attendDtos, "S2"));
            dowloadsDto.setNurseP(getCount(attendDtos, "S2"));
            dowloadsDto.setNurseJ(getCount(attendDtos, "S2"));
            dowloadsDto.setSick(getCount(attendDtos, "B"));
            dowloadsDto.setAleave(getCount(attendDtos, "S3"));
            dowloadsDto.setAbsenteeism(getCount(attendDtos, "K1"));
            dowloadsDto.setCustody(getCount(attendDtos, "K2"));
            dowloadsDto.setDrug(getCount(attendDtos, "K3"));
            dowloadsDto.setLabor(getCount(attendDtos, "W"));
            dowloadsDto.setHealthCare("18");
            dowloadsDto.setRemark("");
            dowloadsDtos.add(dowloadsDto);
        }
        return dowloadsDtos;
    }

    private String getCount(List<AttendDto> attendDtos, String type) {
        if ("18".equals(type)) {
            return "18";
        }

        int count = 0;
        for (AttendDto dto : attendDtos) {
            String cord = dto.getCord();
            if (type.equals(cord)) {
                count++;
            }
        }

        return count == 0 ? "" : String.valueOf(count);
    }


    private List<AttendDayDto> getDays(List<AttendDto> attendDtos) {

        List<AttendDayDto> dayDtos = new ArrayList<>();
        for (AttendDto attendDto : attendDtos) {
            AttendDayDto dayDto = new AttendDayDto();
            dayDto.setDay(String.valueOf(DateHelper.getDay(attendDto.getDateTime())));
            dayDto.setCord(attendDto.getCord());
            dayDtos.add(dayDto);
        }
        return dayDtos;
    }

//    private String getType(List<AttendDto> list,String cord) {
//
//        Integer count=0;
//        for(AttendDto dto:list){
//            String dtoCord=dto.getCord();
//            if(cord.equals(dtoCord)){
//                count++;
//            }
//        }
//        return count==0?"":String.valueOf(count);
//    }

    /**
     * 请假类型转化成指定的考勤类型
     *
     * @param str
     * @return
     */
    private String ConvertToCord(String str) {
        String result = "";
        switch (str) {
            case "visitFamily":
                result = "T";
                break;
            case "paidAnnual":
                result = "S1";
                break;
            case "recuperation":
                result = "L";
                break;
            case "sick":
                result = "B";
                break;
            case "thing":
                result = "S3";
                break;
            case "marry":
                result = "H";
                break;
            case "funeral":
                result = "H";
                break;
            case "birth":
                result = "C";
                break;
            case "onlyChild":
                result = "S2";
                break;
            case "abortion":
                result = "S2";
                break;
            case "escort":
                result = "S2";
                break;
            case "injury":
                result = "G";
                break;
        }
        return result;
    }

    /**
     * 计算是不是周末
     *
     * @param time
     * @return
     */
    private boolean isWeak(Date time) {
        int day = DateHelper.getWeek(time) - 1;
        if (day == 0 || day == 6) return true;
        return false;
    }

    /**
     * 检查是否是上班，或者休息
     *
     * @param time
     * @return
     */
    private Integer chechHoliday(Date time) {
        String dayStation = holidayInfoService.getEmpWorkStateByHoliday(time);
        if (dayStation != "") {
            if (dayStation.equals("休息")) //今天是节假日
                return 2;
            else return 0; //即使是周末公司正常上班
        } else {
            if (isWeak(time)) return 2;//正常周末
            else return 0;//正常工作日
        }
    }

    private List<NewNearSevenDay> getNnnsd(Date beginDate) {
        //todo 得到所有部门
        List<DeptmentDto> deptmentDtos = deptmentService.getAllSimpleDept();
        //todo 先得到空数据的list

        List<NewNearSevenDay> list = new ArrayList<>();
        if (deptmentDtos != null && deptmentDtos.size() > 0) {
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < 7; i++) {
                Date d = DateHelper.addDay(beginDate, i);
                map.put(DateHelper.formatDate(d, "yyyy-MM-dd"), chechHoliday(d));
            }
            for (DeptmentDto dto : deptmentDtos) {
                NewNearSevenDay nn = new NewNearSevenDay();
                nn.setDeptId(dto.getId());
                nn.setDeptName(dto.getDeptName());
                nn.setDateToInt(map);
                list.add(nn);
            }
        }
        return list;
    }

    /**
     * 去除不存在或者禁用的员工的考勤
     *
     * @param dtos
     * @param empIds
     * @return
     */
    private List<AttendDto> getAttendDto(List<AttendDto> dtos, List<Long> empIds) {
        List<AttendDto> deldtos = new ArrayList<>();
        if (dtos != null && dtos.size() > 0) {
            for (AttendDto dto : dtos) {
                if (!empIds.contains(dto.getEmpId())) {
                    deldtos.add(dto);
                }
            }
            dtos.removeAll(deldtos);
        }
        return dtos;
    }
}
