package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.*;
import com.sunesoft.lemon.syms.eHr.application.criteria.AttendanceCriteria;
import com.sunesoft.lemon.syms.eHr.application.criteria.EmpAttendancesCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.attendance.*;
import com.sunesoft.lemon.syms.eHr.domain.attendance.enums.AttendanceKind;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.eHr.domain.dept.DeptmentRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by MJ006 on 2016/6/29.
 */
@Service("attendanceService")
public class AttendanceServiceImpl extends GenericHibernateFinder implements AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    AttendanceDetailRepository attendanceDetailRepository;

    @Autowired
    AttendanceSummeryRepository attendanceSummeryRepository;

    @Autowired
    DeptmentRepository deptmentRepository;

    @Autowired
    EmployeeService employeeService;

    //新增或修改
    @Override
    public CommonResult AddOrUpdateAttendance(AttendanceOperateDto dto) {

        Criteria criterion = getSession().createCriteria(Attendance.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("empId", dto.getEmpId()));
        criterion.add(Restrictions.eq("year", DateHelper.getYear(dto.getAttDate())));
        criterion.add(Restrictions.eq("month", DateHelper.getMonth(dto.getAttDate())));
        List<Attendance> list = criterion.list();
        if (list == null || list.size() == 0) {
            //新增
            Attendance attendance = new Attendance();
            BeanUtils.copyProperties(dto, attendance);
            attendance.setEmpName(dto.getName());//字段名不同，只能单独赋值
            //设置年
            attendance.setYear(DateHelper.getYear(dto.getAttDate()));
            //设置月
            attendance.setMonth(DateHelper.getMonth(dto.getAttDate()));
            //设置考勤类型(日)
            //attendance.setK_1(dto.getAttendanceKind());
            //设置部门
            Deptment deptment = deptmentRepository.get(dto.getDepId());
            attendance.setDeptment(deptment);
            attendance.setDepId(deptment.getId());
            attendance.setDepName(deptment.getDeptName());
            //设置AttendanceDetail
            attendance = KaoQinLeiXing(attendance, dto);

            AttendanceDetail attendanceDetail = new AttendanceDetail();
            AttendDetailDto detailDto = new AttendDetailDto();
            detailDto.setBeginDate(dto.getAttDate());
            detailDto.setEndDate(dto.getAttDate());

            attendanceDetail = AD(attendanceDetail, detailDto);

            attendance.setAttendanceDetail(attendanceDetail);
            //设置AttendanceSummery
            AttendanceSummery attendanceSummery = getByAttendanceDto(dto);// todo 问题所在
            attendanceSummery.setYear(DateHelper.getYear(dto.getAttDate()));
            attendanceSummery.setMonth(DateHelper.getMonth(dto.getAttDate()));
            attendanceSummery.setEmpId(dto.getEmpId());
            attendanceSummery.setEmpName(dto.getName());
            attendanceSummery.setDepId(dto.getDepId());
            attendanceSummery.setDepName(dto.getDepName());
            attendance.setAttendanceSummery(getByAttendanceDto2(attendanceSummery, dto));
            //attendance.setAttendanceSummery(attendanceSummery);
            //保存信息
            attendanceRepository.save(attendance);
            return ResultFactory.commonSuccess();
        } else {
            //修改
            Attendance attendance = list.get(0);
            BeanUtils.copyProperties(dto, attendance);

            //设置考勤类型(日)
            attendance = KaoQinLeiXing(attendance, dto);
            //设置AttendanceDetail
            AttendanceDetail attendanceDetail = attendance.getAttendanceDetail();
            if (attendanceDetail == null) {
                attendanceDetail = new AttendanceDetail();
            }
            AttendDetailDto detailDto = new AttendDetailDto();
            detailDto.setBeginDate(dto.getAttDate());
            detailDto.setEndDate(dto.getEndDate());
            attendanceDetail = AD(attendanceDetail, detailDto);
            attendance.setAttendanceDetail(attendanceDetail);
            //设置AttendanceSummery
            AttendanceSummery attendanceSummery = attendance.getAttendanceSummery();
            if (attendanceSummery == null) {
                attendanceSummery = new AttendanceSummery();
            }
            attendance.setAttendanceSummery(getByAttendanceDto2(attendanceSummery, dto));
            //保存信息
            attendanceRepository.save(attendance);
            return ResultFactory.commonSuccess();
        }
    }


    public List<EmpAttendanceDto> getEmpAttendance(EmpAttendancesCriteria criteria) {

        Integer year = DateHelper.getYear(criteria.getAttDate());
        Integer month = DateHelper.getMonth(criteria.getAttDate());
        String day = "k_" + String.valueOf(DateHelper.getDay(criteria.getAttDate()));
        String sql = "select e.id empId,e.real_name empName,e.emp_dept_id deptId,d.dept_name deptName ," + day + "  attendanceKind ,'" + DateHelper.formatDate(criteria.getAttDate()) + "' attDate from syy_oa_hr_employees e LEFT JOIN syy_oa_hr_dept d on e.emp_dept_id = d.id left JOIN " +
                "(select emp_id," + day + " from syy_oa_hr_att   WHERE `year`=" + year + " and `month`=" + month + " )t on e.id = t.emp_id where e.is_active=1 and  e.emp_status=1 ";

        if (criteria.getDeptId() != null && criteria.getDeptId() > 0) {
            sql += " and e.emp_dept_id=" + criteria.getDeptId();
        }
        if (criteria.getEmpId() != null && criteria.getEmpId() > 0) {
            sql += " and e.id=" + criteria.getEmpId();
        }
        List<EmpAttendanceDto> dto = queryForObjects(EmpAttendanceDto.class, sql, null);
//        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
//        addScalar(sqlQuery, EmpAttendanceDto.class);
//        sqlQuery.setResultTransformer(Transformers.aliasToBean(EmpAttendanceDto.class));
        return dto;
    }


    //按个人查询当月考勤(必须参数：empId,beginDate(需要年和月，日可有可无))
    @Override
    public AttendanceWithDetailDto findByEmpId(AttendanceCriteria attendanceCriteria) {
        Criteria criterion = getSession().createCriteria(Attendance.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("empId", attendanceCriteria.getEmpId()));
        criterion.add(Restrictions.eq("depId", attendanceCriteria.getDepId()));
        criterion.add(Restrictions.eq("year", DateHelper.getYear(attendanceCriteria.getBeginDate())));
        criterion.add(Restrictions.eq("month", DateHelper.getMonth(attendanceCriteria.getBeginDate())));
        List<Attendance> list = criterion.list();
        AttendanceWithDetailDto attendanceDto = getDto(list.get(0));
        return attendanceDto;
    }

    //获取某部门某月的考勤
    private List<Attendance> getAtendanceByDate(Long deptId, Date attDate) {
        Integer year = DateHelper.getYear(attDate);
        Integer month = DateHelper.getMonth(attDate);
        String day = "k_" + String.valueOf(DateHelper.getDay(attDate));
        Criteria criterion = getSession().createCriteria(Attendance.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("depId", deptId));
        criterion.add(Restrictions.eq("year", year));
        criterion.add(Restrictions.eq("month", month));
//        List<Attendance> list = criterion.list();
//        if (list.size() > 0)
//            return list.get(0);
//        return null;
        return criterion.list();
    }

    //按部门查询当月考勤(必须参数：depId,beginDate(需要年和月，日可有可无))
    @Override
    public List<AttendanceDto> findByDepid(AttendanceCriteria attendanceCriteria) {
        Criteria criterion = getSession().createCriteria(Attendance.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("depId", attendanceCriteria.getDepId()));
        criterion.add(Restrictions.eq("year", DateHelper.getYear(attendanceCriteria.getBeginDate())));
        criterion.add(Restrictions.eq("month", DateHelper.getMonth(attendanceCriteria.getBeginDate())));
        List<Attendance> beans = criterion.list();
        List<AttendanceDto> list = new ArrayList<AttendanceDto>();
        for (Attendance attendance : beans) {
            AttendanceDto attendanceDto = DtoFactory.convert(list.get(0), new AttendanceDto());
            list.add(attendanceDto);
        }
        return list;
    }

    //按部门查询当月考勤(必须参数：depId,beginDate(需要年和月，日可有可无))  分页
    @Override
    public PagedResult<AttendanceDto> PagefindByDepid(AttendanceCriteria attendanceCriteria) {
        //我的测试
        Criteria criterion = getSession().createCriteria(Attendance.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (attendanceCriteria.getDepId() != null)
            criterion.add(Restrictions.eq("depId", attendanceCriteria.getDepId()));
//        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getMonth())) {
//            criterion.add(Restrictions.eq("month", Integer.parseInt(attendanceCriteria.getMonth())));
//        }
//        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getYear())) {
//            criterion.add(Restrictions.eq("year", Integer.parseInt(attendanceCriteria.getYear())));
//        }
        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getEmpName())) {
            criterion.add(Restrictions.like("empName", "%" + attendanceCriteria.getEmpName() + "%"));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
//        Date d=new Date();
        List<Attendance> beans = criterion.list();//todo slow
//        System.out.print("开始："+d);
//        System.out.print("结束："+new Date());

        List<AttendanceDto> list = new ArrayList<AttendanceDto>();

        List<EmpDto> empDtos = employeeService.findEmpsByDept(attendanceCriteria.getDepId());


        List<Long> ids = new ArrayList<>();
        for (Attendance attendance : beans) {
            AttendanceDto attendanceDto = DtoFactory.convert(attendance, new AttendanceDto());
            AttendanceSummery attendanceSummery = attendance.getAttendanceSummery();
            AttendanceSummeryDto attendanceSummeryDto = DtoFactory.convert(attendanceSummery, new AttendanceSummeryDto());
            attendanceDto.setAttendanceSummeryDto(attendanceSummeryDto);
            list.add(attendanceDto);
            ids.add(attendanceSummery.getEmpId());
        }
        for (EmpDto e : empDtos) {
            if (ids.size() > 0) {
                if (!ArrayUtils.isExist(ids, e.getId())) {
                    AttendanceDto dto = new AttendanceDto();
                    dto.setEmpId(e.getId());

                    dto.setEmpName(e.getName());
                    dto.setDepName(e.getDeptName());
                    list.add(dto);
                }
            } else {
                AttendanceDto dto = new AttendanceDto();
                dto.setEmpId(e.getId());

                dto.setEmpName(e.getName());
                dto.setDepName(e.getDeptName());
                list.add(dto);
            }
        }

        criterion.setFirstResult((attendanceCriteria.getPageNumber() - 1) * attendanceCriteria.getPageSize()).setMaxResults(attendanceCriteria.getPageSize());
        return new PagedResult<AttendanceDto>(list, attendanceCriteria.getPageNumber(), attendanceCriteria.getPageSize(), totalCount);
    }

    @Override
    public PagedResult<AttendanceDto> PagefindSimpleByDepid(AttendanceCriteria attendanceCriteria) {
        String hql = " select new list(a.id,a.empId,a.empName,a.depId,a.depName,a.k_1,a.k_2,a.k_3,a.k_4,a.k_5,a.k_6,a.k_7,a.k_8,a.k_9,a.k_10,a.k_11,a.k_12,a.k_13,a.k_14,a.k_15,a.k_16,a.k_17,a.k_18,a.k_19,a.k_20,a.k_21,a.k_22,a.k_23,a.k_24,a.k_25,a.k_26,a.k_27,a.k_28,a.k_29,a.k_30,a.k_31) from Attendance a where a.isActive=1 ";
        if (attendanceCriteria.getDepId() != null)
            hql = hql + " and a.depId=" + attendanceCriteria.getDepId();
        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getMonth())) {
            hql = hql + " and a.month=" + attendanceCriteria.getMonth();
        }
        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getYear())) {
            hql = hql + " and a.year=" + attendanceCriteria.getYear();
        }
        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getEmpName())) {
            hql = hql + " and a.empName like '%" + attendanceCriteria.getEmpName() + "%'";
        }
        List<List> lists = getSession().createQuery(hql).list();
        //我的测试
        List<AttendanceDto> list = new ArrayList<AttendanceDto>();
        List<EmpDto> empDtos = employeeService.findEmpsByDeptId(attendanceCriteria.getDepId());
        List<Long> ids = new ArrayList<>();
        for (List list1 : lists) {
            AttendanceDto attendanceDto = new AttendanceDto();
//todo 数据复制
            int i = 0;
            attendanceDto.setId((Long) list1.get(i++));
            attendanceDto.setEmpId((Long) list1.get(i++));
            attendanceDto.setEmpName((String) list1.get(i++));
            attendanceDto.setDepId((Long) list1.get(i++));
            attendanceDto.setDepName((String) list1.get(i++));
            attendanceDto.setK_1((AttendanceKind) list1.get(i++));
            attendanceDto.setK_2((AttendanceKind) list1.get(i++));
            attendanceDto.setK_3((AttendanceKind) list1.get(i++));
            attendanceDto.setK_4((AttendanceKind) list1.get(i++));
            attendanceDto.setK_5((AttendanceKind) list1.get(i++));
            attendanceDto.setK_6((AttendanceKind) list1.get(i++));
            attendanceDto.setK_7((AttendanceKind) list1.get(i++));
            attendanceDto.setK_8((AttendanceKind) list1.get(i++));
            attendanceDto.setK_9((AttendanceKind) list1.get(i++));
            attendanceDto.setK_10((AttendanceKind) list1.get(i++));
            attendanceDto.setK_11((AttendanceKind) list1.get(i++));
            attendanceDto.setK_12((AttendanceKind) list1.get(i++));
            attendanceDto.setK_13((AttendanceKind) list1.get(i++));
            attendanceDto.setK_14((AttendanceKind) list1.get(i++));
            attendanceDto.setK_15((AttendanceKind) list1.get(i++));
            attendanceDto.setK_16((AttendanceKind) list1.get(i++));
            attendanceDto.setK_17((AttendanceKind) list1.get(i++));
            attendanceDto.setK_18((AttendanceKind) list1.get(i++));
            attendanceDto.setK_19((AttendanceKind) list1.get(i++));
            attendanceDto.setK_20((AttendanceKind) list1.get(i++));
            attendanceDto.setK_21((AttendanceKind) list1.get(i++));
            attendanceDto.setK_22((AttendanceKind) list1.get(i++));
            attendanceDto.setK_23((AttendanceKind) list1.get(i++));
            attendanceDto.setK_24((AttendanceKind) list1.get(i++));
            attendanceDto.setK_25((AttendanceKind) list1.get(i++));
            attendanceDto.setK_26((AttendanceKind) list1.get(i++));
            attendanceDto.setK_27((AttendanceKind) list1.get(i++));
            attendanceDto.setK_28((AttendanceKind) list1.get(i++));
            attendanceDto.setK_29((AttendanceKind) list1.get(i++));
            attendanceDto.setK_30((AttendanceKind) list1.get(i++));
            attendanceDto.setK_31((AttendanceKind) list1.get(i));

            ids.add(attendanceDto.getEmpId());
            list.add(attendanceDto);
        }
        for (EmpDto e : empDtos) {
            if ((ids.size() > 0 && !ArrayUtils.isExist(ids, e.getId())) || ids.size() == 0) {
                AttendanceDto dto = new AttendanceDto();
                dto.setEmpId(e.getId());

                dto.setEmpName(e.getName());
                dto.setDepName(e.getDeptName());
                list.add(dto);
            }
        }
        return new PagedResult<AttendanceDto>(list, attendanceCriteria.getPageNumber(), attendanceCriteria.getPageSize(),list.size());
    }

    //查询所有的
    @Override
    public List<AttendanceDto> findAll() {
        Criteria criterion = getSession().createCriteria(Attendance.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<Attendance> beans = criterion.list();
        List<AttendanceDto> list = new ArrayList<AttendanceDto>();
        for (Attendance attendance : beans) {
            AttendanceDto attendanceDto = DtoFactory.convert(list.get(0), new AttendanceDto());
            list.add(attendanceDto);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findCountByDateAndDepid(AttendanceCriteria attendanceCriteria) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        sb.append(getDay(attendanceCriteria) + " attType , count(1) num from syy_oa_hr_att a where ");

        sb.append("a.att_year='");
        sb.append(DateHelper.getYear(new Date()) + "' ");
        if (attendanceCriteria.getDepId() != null) {
            sb.append(" and a.dep_id= '");
            sb.append(attendanceCriteria.getDepId() + "' ");
        }
        sb.append("and a.att_month='");
        sb.append(attendanceCriteria.getMonth() + "' GROUP BY ");
        sb.append(getDay(attendanceCriteria));
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    //设置AttendanceSummery(新增)
    public AttendanceSummery getByAttendanceDto(AttendanceOperateDto dto) {
        AttendanceSummery attendanceSummery = new AttendanceSummery();
        AttendanceKind a = dto.getAttendanceKind();
//        if (a == AttendanceKind.I) {
//            attendanceSummery.setAttend(dto.getTimeRange());
//        } else if (a == AttendanceKind.T) {
//            attendanceSummery.setVisitFamily(dto.getTimeRange());
//        } else if (a == AttendanceKind.S1) {
//            attendanceSummery.setVacation(dto.getTimeRange());
//        } else if (a == AttendanceKind.S2) {
//            attendanceSummery.setNurse(dto.getTimeRange());
//        } else if (a == AttendanceKind.S3) {
//            attendanceSummery.setAleave(dto.getTimeRange());
//        } else if (a == AttendanceKind.B) {
//            attendanceSummery.setSick(dto.getTimeRange());
//        } else if (a == AttendanceKind.X1) {
//            attendanceSummery.setStudyExperience(dto.getTimeRange());
//        } else if (a == AttendanceKind.Y) {
//            attendanceSummery.setWild(dto.getTimeRange());
//        } else if (a == AttendanceKind.Z) {
//            attendanceSummery.setBusinessTravel(dto.getTimeRange());
//        } else if (a == AttendanceKind.H) {
//            attendanceSummery.setWeddingFuneral(dto.getTimeRange());
//        } else if (a == AttendanceKind.C) {
//            attendanceSummery.setBirthChild(dto.getTimeRange());
//        } else if (a == AttendanceKind.G) {
//            attendanceSummery.setWorkHurt(dto.getTimeRange());
//        }  else if (a == AttendanceKind.W) {
//            attendanceSummery.setLabor(dto.getTimeRange());
//        } else if (a == AttendanceKind.K1) {
//            attendanceSummery.setAbsenteeism(dto.getTimeRange());
//        } else if (a == AttendanceKind.B2) {
//            attendanceSummery.setCompensate(dto.getTimeRange()); //todo add
//        }else if (a == AttendanceKind.J1) {
//            attendanceSummery.setGwork(dto.getTimeRange());
//        } else if (a == AttendanceKind.J2) {
//            attendanceSummery.setJwork(dto.getTimeRange());
//        } else if (a == AttendanceKind.X2) {
//            attendanceSummery.setTstudy(dto.getTimeRange());
//        } else if (a == AttendanceKind.C1) {
//            attendanceSummery.setLbirth(dto.getTimeRange());
//        } else if (a == AttendanceKind.K2) {
//            attendanceSummery.setCustody(dto.getTimeRange());
//        } else if (a == AttendanceKind.K3) {
//            attendanceSummery.setDrug(dto.getTimeRange());
//        }else if (a == AttendanceKind.L) {
//            attendanceSummery.setSpa(dto.getTimeRange());
//        }

//xzl
        if (a == AttendanceKind.I) {
            attendanceSummery.setAttend(0f);
        } else if (a == AttendanceKind.T) {
            attendanceSummery.setVisitFamily(0f);
        } else if (a == AttendanceKind.S1) {
            attendanceSummery.setVacation(0f);
        } else if (a == AttendanceKind.S2) {
            attendanceSummery.setNurse(0f);
        } else if (a == AttendanceKind.S3) {
            attendanceSummery.setAleave(0f);
        } else if (a == AttendanceKind.B) {
            attendanceSummery.setSick(0f);
        } else if (a == AttendanceKind.X1) {
            attendanceSummery.setStudyExperience(0f);
        } else if (a == AttendanceKind.Y) {
            attendanceSummery.setWild(0f);
        } else if (a == AttendanceKind.Z) {
            attendanceSummery.setBusinessTravel(0f);
        } else if (a == AttendanceKind.H) {
            attendanceSummery.setWeddingFuneral(0f);
        } else if (a == AttendanceKind.C) {
            attendanceSummery.setBirthChild(0f);
        } else if (a == AttendanceKind.G) {
            attendanceSummery.setWorkHurt(0f);
        } else if (a == AttendanceKind.W) {
            attendanceSummery.setLabor(0f);
        } else if (a == AttendanceKind.K1) {
            attendanceSummery.setAbsenteeism(0f);
        } else if (a == AttendanceKind.B2) {
            attendanceSummery.setCompensate(0f);
        } else if (a == AttendanceKind.J1) {
            attendanceSummery.setGwork(0f);
        } else if (a == AttendanceKind.J2) {
            attendanceSummery.setJwork(0f);
        } else if (a == AttendanceKind.X2) {
            attendanceSummery.setTstudy(0f);
        } else if (a == AttendanceKind.C1) {
            attendanceSummery.setLbirth(0f);
        } else if (a == AttendanceKind.K2) {
            attendanceSummery.setCustody(0f);
        } else if (a == AttendanceKind.K3) {
            attendanceSummery.setDrug(0f);
        } else if (a == AttendanceKind.L) {
            attendanceSummery.setSpa(0f);
        }
        return attendanceSummery;
    }

    //设置AttendanceSummery(修改)
    public AttendanceSummery getByAttendanceDto2(AttendanceSummery attendanceSummery, AttendanceOperateDto dto) {
        AttendanceKind a = dto.getAttendanceKind();

        //todo 这个地方不能用到dto.getTimeRange() 否则统计数据就是dto.getTimeRange()倍数
        /*if (a == AttendanceKind.I) {
            attendanceSummery.setAttend((attendanceSummery.getAttend() == null ? 0 : attendanceSummery.getAttend()) + dto.getTimeRange());
        } else
        //出勤使用sql语句更新数据库，那里没有对AttendanceSummery进行操作，所以出勤的天数计算就不能这样计算了
        if (a == AttendanceKind.T) {
            attendanceSummery.setVisitFamily((attendanceSummery.getVisitFamily() == null ? 0 : attendanceSummery.getVisitFamily()) + dto.getTimeRange());
        } else if(a == AttendanceKind.J1){
            attendanceSummery.setGwork((attendanceSummery.getGwork()==null?0:attendanceSummery.getGwork())+dto.getTimeRange());
        } else if(a == AttendanceKind.J2){
            attendanceSummery.setJwork((attendanceSummery.getJwork() == null ? 0 : attendanceSummery.getJwork()) + dto.getTimeRange());
        }else if (a == AttendanceKind.S1) {
            attendanceSummery.setVacation((attendanceSummery.getVacation() == null ? 0 : attendanceSummery.getVacation()) + dto.getTimeRange());
        } else if (a == AttendanceKind.S2) {
            attendanceSummery.setNurse((attendanceSummery.getNurse() == null ? 0 : attendanceSummery.getNurse()) + dto.getTimeRange());
        } else if (a == AttendanceKind.S3) {
            attendanceSummery.setAleave((attendanceSummery.getAleave() == null ? 0 : attendanceSummery.getAleave()) + dto.getTimeRange());
        } else if (a == AttendanceKind.B) {
            attendanceSummery.setSick((attendanceSummery.getSick() == null ? 0 : attendanceSummery.getSick()) + dto.getTimeRange());
        } else if (a == AttendanceKind.X1) {
            attendanceSummery.setStudyExperience((attendanceSummery.getStudyExperience() == null ? 0 : attendanceSummery.getStudyExperience()) + dto.getTimeRange());
        } else if(a == AttendanceKind.X2){
            attendanceSummery.setTstudy((attendanceSummery.getTstudy() == null ? 0 : attendanceSummery.getTstudy()) + dto.getTimeRange());
        } else if(a == AttendanceKind.L){
            attendanceSummery.setSpa((attendanceSummery.getSpa() == null ? 0 : attendanceSummery.getSpa()) + dto.getTimeRange());
        } else if(a == AttendanceKind.C1){
            attendanceSummery.setLbirth((attendanceSummery.getLbirth() == null ? 0 : attendanceSummery.getLbirth()) + dto.getTimeRange());
        } else if (a == AttendanceKind.Y) {
            attendanceSummery.setWild((attendanceSummery.getWild() == null ? 0 : attendanceSummery.getWild()) + dto.getTimeRange());
        } else if (a == AttendanceKind.Z) {
            attendanceSummery.setBusinessTravel((attendanceSummery.getBusinessTravel() == null ? 0 : attendanceSummery.getBusinessTravel()) + dto.getTimeRange());
        } else if (a == AttendanceKind.H) {
            attendanceSummery.setWeddingFuneral((attendanceSummery.getWeddingFuneral() == null ? 0 : attendanceSummery.getWeddingFuneral()) + dto.getTimeRange());
        } else if (a == AttendanceKind.G) {
            attendanceSummery.setWorkHurt((attendanceSummery.getWorkHurt() == null ? 0 : attendanceSummery.getWorkHurt()) + dto.getTimeRange());
        } else if (a == AttendanceKind.X2) {
            attendanceSummery.setRest((attendanceSummery.getRest() == null ? 0 : attendanceSummery.getRest()) + dto.getTimeRange());
        } else if (a == AttendanceKind.W) {
            attendanceSummery.setLabor((attendanceSummery.getLabor() == null ? 0 : attendanceSummery.getLabor()) + dto.getTimeRange());
        } else if (a == AttendanceKind.K1) {
            attendanceSummery.setAbsenteeism((attendanceSummery.getAbsenteeism() == null ? 0 : attendanceSummery.getAbsenteeism()) + dto.getTimeRange());
        } else if(a == AttendanceKind.K2){
            attendanceSummery.setCustody((attendanceSummery.getCustody() == null ? 0 : attendanceSummery.getCustody()) + dto.getTimeRange());
        } else if(a == AttendanceKind.K3){
            attendanceSummery.setDrug((attendanceSummery.getDrug() == null ? 0 : attendanceSummery.getDrug()) + dto.getTimeRange());
        } else if (a == AttendanceKind.B2) {
            attendanceSummery.setCompensate((attendanceSummery.getCompensate() == null ? 0 : attendanceSummery.getCompensate()) + dto.getTimeRange());
        }*/

        if (a == AttendanceKind.I) {
            attendanceSummery.setAttend((attendanceSummery.getAttend() == null ? 0 : attendanceSummery.getAttend()) + 1);
        } else
            //出勤使用sql语句更新数据库，那里没有对AttendanceSummery进行操作，所以出勤的天数计算就不能这样计算了
            if (a == AttendanceKind.T) {
                attendanceSummery.setVisitFamily((attendanceSummery.getVisitFamily() == null ? 0 : attendanceSummery.getVisitFamily()) + 1);
            } else if (a == AttendanceKind.J1) {
                attendanceSummery.setGwork((attendanceSummery.getGwork() == null ? 0 : attendanceSummery.getGwork()) + 1);
            } else if (a == AttendanceKind.J2) {
                attendanceSummery.setJwork((attendanceSummery.getJwork() == null ? 0 : attendanceSummery.getJwork()) + 1);
            } else if (a == AttendanceKind.S1) {
                attendanceSummery.setVacation((attendanceSummery.getVacation() == null ? 0 : attendanceSummery.getVacation()) + 1);
            } else if (a == AttendanceKind.S2) {
                attendanceSummery.setNurse((attendanceSummery.getNurse() == null ? 0 : attendanceSummery.getNurse()) + 1);
            } else if (a == AttendanceKind.S3) {
                attendanceSummery.setAleave((attendanceSummery.getAleave() == null ? 0 : attendanceSummery.getAleave()) + 1);
            } else if (a == AttendanceKind.B) {
                attendanceSummery.setSick((attendanceSummery.getSick() == null ? 0 : attendanceSummery.getSick()) + 1);
            } else if (a == AttendanceKind.X1) {
                attendanceSummery.setStudyExperience((attendanceSummery.getStudyExperience() == null ? 0 : attendanceSummery.getStudyExperience()) + 1);
            } else if (a == AttendanceKind.X2) {
                attendanceSummery.setTstudy((attendanceSummery.getTstudy() == null ? 0 : attendanceSummery.getTstudy()) + 1);
            } else if (a == AttendanceKind.L) {
                attendanceSummery.setSpa((attendanceSummery.getSpa() == null ? 0 : attendanceSummery.getSpa()) + 1);
            } else if (a == AttendanceKind.C1) {
                attendanceSummery.setLbirth((attendanceSummery.getLbirth() == null ? 0 : attendanceSummery.getLbirth()) + 1);
            } else if (a == AttendanceKind.Y) {
                attendanceSummery.setWild((attendanceSummery.getWild() == null ? 0 : attendanceSummery.getWild()) + 1);
            } else if (a == AttendanceKind.Z) {
                attendanceSummery.setBusinessTravel((attendanceSummery.getBusinessTravel() == null ? 0 : attendanceSummery.getBusinessTravel()) + 1);
            } else if (a == AttendanceKind.H) {
                attendanceSummery.setWeddingFuneral((attendanceSummery.getWeddingFuneral() == null ? 0 : attendanceSummery.getWeddingFuneral()) + 1);
            } else if (a == AttendanceKind.G) {
                attendanceSummery.setWorkHurt((attendanceSummery.getWorkHurt() == null ? 0 : attendanceSummery.getWorkHurt()) + 1);
            } else if (a == AttendanceKind.W) {
                attendanceSummery.setLabor((attendanceSummery.getLabor() == null ? 0 : attendanceSummery.getLabor()) + 1);
            } else if (a == AttendanceKind.K1) {
                attendanceSummery.setAbsenteeism((attendanceSummery.getAbsenteeism() == null ? 0 : attendanceSummery.getAbsenteeism()) + 1);
            } else if (a == AttendanceKind.K2) {
                attendanceSummery.setCustody((attendanceSummery.getCustody() == null ? 0 : attendanceSummery.getCustody()) + 1);
            } else if (a == AttendanceKind.K3) {
                attendanceSummery.setDrug((attendanceSummery.getDrug() == null ? 0 : attendanceSummery.getDrug()) + 1);
            } else if (a == AttendanceKind.B2) {
                attendanceSummery.setCompensate((attendanceSummery.getCompensate() == null ? 0 : attendanceSummery.getCompensate()) + 1);
            } else if (a == AttendanceKind.C) {
                attendanceSummery.setBirthChild((attendanceSummery.getBirthChild() == null ? 0 : attendanceSummery.getBirthChild()) + 1);
            }

        return attendanceSummery;
    }

    //设置考勤类型(日)
    public Attendance KaoQinLeiXing(Attendance attendance, AttendanceOperateDto dto) {

        Integer day = DateHelper.getDay(dto.getAttDate());
        if (day == 1) {
            attendance.setK_1(dto.getAttendanceKind());
        } else if (day == 2) {
            attendance.setK_2(dto.getAttendanceKind());
        } else if (day == 3) {
            attendance.setK_3(dto.getAttendanceKind());
        } else if (day == 4) {
            attendance.setK_4(dto.getAttendanceKind());
        } else if (day == 5) {
            attendance.setK_5(dto.getAttendanceKind());
        } else if (day == 6) {
            attendance.setK_6(dto.getAttendanceKind());
        } else if (day == 7) {
            attendance.setK_7(dto.getAttendanceKind());
        } else if (day == 8) {
            attendance.setK_8(dto.getAttendanceKind());
        } else if (day == 9) {
            attendance.setK_9(dto.getAttendanceKind());
        } else if (day == 10) {
            attendance.setK_10(dto.getAttendanceKind());
        } else if (day == 11) {
            attendance.setK_11(dto.getAttendanceKind());
        } else if (day == 12) {
            attendance.setK_12(dto.getAttendanceKind());
        } else if (day == 13) {
            attendance.setK_13(dto.getAttendanceKind());
        } else if (day == 14) {
            attendance.setK_14(dto.getAttendanceKind());
        } else if (day == 15) {
            attendance.setK_15(dto.getAttendanceKind());
        } else if (day == 16) {
            attendance.setK_16(dto.getAttendanceKind());
        } else if (day == 17) {
            attendance.setK_17(dto.getAttendanceKind());
        } else if (day == 18) {
            attendance.setK_18(dto.getAttendanceKind());
        } else if (day == 19) {
            attendance.setK_19(dto.getAttendanceKind());
        } else if (day == 20) {
            attendance.setK_20(dto.getAttendanceKind());
        } else if (day == 21) {
            attendance.setK_21(dto.getAttendanceKind());
        } else if (day == 22) {
            attendance.setK_22(dto.getAttendanceKind());
        } else if (day == 23) {
            attendance.setK_23(dto.getAttendanceKind());
        } else if (day == 24) {
            attendance.setK_24(dto.getAttendanceKind());
        } else if (day == 25) {
            attendance.setK_25(dto.getAttendanceKind());
        } else if (day == 26) {
            attendance.setK_26(dto.getAttendanceKind());
        } else if (day == 27) {
            attendance.setK_27(dto.getAttendanceKind());
        } else if (day == 28) {
            attendance.setK_28(dto.getAttendanceKind());
        } else if (day == 29) {
            attendance.setK_29(dto.getAttendanceKind());
        } else if (day == 30) {
            attendance.setK_30(dto.getAttendanceKind());
        } else if (day == 31) {
            attendance.setK_31(dto.getAttendanceKind());
        }
        return attendance;
    }

    //设置AttendanceDetail
    public AttendanceDetail AD(AttendanceDetail attendanceDetail, AttendDetailDto dto) {
        int d = DateHelper.getDay(dto.getBeginDate());
        if (d == 1) {
            attendanceDetail.setD_1(JsonHelper.toJson(dto));
        } else if (d == 2) {
            attendanceDetail.setD_2(JsonHelper.toJson(dto));
        } else if (d == 3) {
            attendanceDetail.setD_3(JsonHelper.toJson(dto));
        } else if (d == 4) {
            attendanceDetail.setD_4(JsonHelper.toJson(dto));
        } else if (d == 5) {
            attendanceDetail.setD_5(JsonHelper.toJson(dto));
        } else if (d == 6) {
            attendanceDetail.setD_6(JsonHelper.toJson(dto));
        } else if (d == 7) {
            attendanceDetail.setD_7(JsonHelper.toJson(dto));
        } else if (d == 8) {
            attendanceDetail.setD_8(JsonHelper.toJson(dto));
        } else if (d == 9) {
            attendanceDetail.setD_9(JsonHelper.toJson(dto));
        } else if (d == 10) {
            attendanceDetail.setD_10(JsonHelper.toJson(dto));
        } else if (d == 11) {
            attendanceDetail.setD_11(JsonHelper.toJson(dto));
        } else if (d == 12) {
            attendanceDetail.setD_12(JsonHelper.toJson(dto));
        } else if (d == 13) {
            attendanceDetail.setD_13(JsonHelper.toJson(dto));
        } else if (d == 14) {
            attendanceDetail.setD_14(JsonHelper.toJson(dto));
        } else if (d == 15) {
            attendanceDetail.setD_15(JsonHelper.toJson(dto));
        } else if (d == 16) {
            attendanceDetail.setD_16(JsonHelper.toJson(dto));
        } else if (d == 17) {
            attendanceDetail.setD_17(JsonHelper.toJson(dto));
        } else if (d == 18) {
            attendanceDetail.setD_18(JsonHelper.toJson(dto));
        } else if (d == 19) {
            attendanceDetail.setD_19(JsonHelper.toJson(dto));
        } else if (d == 20) {
            attendanceDetail.setD_20(JsonHelper.toJson(dto));
        } else if (d == 21) {
            attendanceDetail.setD_21(JsonHelper.toJson(dto));
        } else if (d == 22) {
            attendanceDetail.setD_22(JsonHelper.toJson(dto));
        } else if (d == 23) {
            attendanceDetail.setD_23(JsonHelper.toJson(dto));
        } else if (d == 24) {
            attendanceDetail.setD_23(JsonHelper.toJson(dto));
        } else if (d == 25) {
            attendanceDetail.setD_25(JsonHelper.toJson(dto));
        } else if (d == 26) {
            attendanceDetail.setD_26(JsonHelper.toJson(dto));
        } else if (d == 27) {
            attendanceDetail.setD_27(JsonHelper.toJson(dto));
        } else if (d == 28) {
            attendanceDetail.setD_28(JsonHelper.toJson(dto));
        } else if (d == 29) {
            attendanceDetail.setD_29(JsonHelper.toJson(dto));
        } else if (d == 30) {
            attendanceDetail.setD_30(JsonHelper.toJson(dto));
        } else if (d == 31) {
            attendanceDetail.setD_31(JsonHelper.toJson(dto));
        }
        //xzl add
//        dto.setBeginDate(DateHelper.addDay(dto.getBeginDate(),1));
//        if(dto.getBeginDate().before(dto.getEndDate())) {
//            AD(attendanceDetail,dto);
//        }
        return attendanceDetail;
    }


    public AttendanceWithDetailDto getDto(Attendance attendance) {
        AttendanceWithDetailDto dto = new AttendanceWithDetailDto();
        BeanUtils.copyProperties(attendance, dto);
        AttendanceDetailDto attendanceDetailDto = new AttendanceDetailDto();
        BeanUtils.copyProperties(attendance.getAttendanceDetail(), attendanceDetailDto);
        dto.setAttendanceDetailDto(attendanceDetailDto);

        return dto;
    }

    public String getDay(AttendanceCriteria attendanceCriteria) {
        String result = "";
        int day = DateHelper.getDay(attendanceCriteria.getBeginDate());
        for (int i = 1; i <= 31; i++) {
            if (day == i) {
                result = "k_" + i;
            }
        }
        return result;
    }

    @Override
    public PagedResult<AttendanceSummeryDto> PageBySummeryDto(AttendanceCriteria attendanceCriteria) {
        Criteria criterion = getSession().createCriteria(AttendanceSummery.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(attendanceCriteria.getEmpName()))
            criterion.add(Restrictions.like("empName", "%" + attendanceCriteria.getEmpName() + "%"));
        if (attendanceCriteria.getDepId() != null)
            criterion.add(Restrictions.eq("depId", attendanceCriteria.getDepId()));
        criterion.add(Restrictions.eq("year", DateHelper.getYear(attendanceCriteria.getBeginDate())));//这里因为controller对时间进行绝对赋值了
        criterion.add(Restrictions.eq("month", DateHelper.getMonth(attendanceCriteria.getBeginDate())));
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        List<AttendanceSummery> beans = criterion.list();
        List<AttendanceSummeryDto> list = new ArrayList<>();
        Date date1 = new Date();
        Map<Long, String> empDtos = employeeService.getEmployIdWithName(attendanceCriteria.getDepId());
        System.out.print("员工获取耗时：" + (new Date().getTime() - date1.getTime()));
        Date date2 = new Date();

        List<Long> ids = new ArrayList<>();
        for (AttendanceSummery attendanceSummery : beans) {
            AttendanceSummeryDto dto = DtoFactory.convert(attendanceSummery, new AttendanceSummeryDto());
            if (attendanceSummery.getAttendance() != null) {
                dto.setEmpId(attendanceSummery.getAttendance().getEmpId());
            }
            ids.add(attendanceSummery.getEmpId());
            list.add(dto);
        }
        System.out.print("第一个for循环：" + (new Date().getTime() - date2.getTime()));
        Iterator iter = empDtos.keySet().iterator();
        Date date3 = new Date();

        while (iter.hasNext()) {
            Long key = (Long) iter.next();
            String name = empDtos.get(key);
            if (ids.size() > 0) {
                if (!ArrayUtils.isExist(ids, key)) {
                    AttendanceSummeryDto dto = new AttendanceSummeryDto();
                    dto.setEmpId(key);
                    dto.setEmpName(name);
                    list.add(dto);//判断部门中的所有员工是否在统计内，若不在就新增
                }
            } else {
                AttendanceSummeryDto dto = new AttendanceSummeryDto();
                dto.setEmpId(key);
                dto.setEmpName(name);
                list.add(dto);
            }
        }
//        for (EmpDto e : empDtos) {
//            if (ids.size() > 0) {
//                if (!ArrayUtils.isExist(ids, e.getId())) {
//                    AttendanceSummeryDto dto = new AttendanceSummeryDto();
//                    dto.setEmpId(e.getId());
//
//                    dto.setEmpName(e.getName());
//                    list.add(dto);//判断部门中的所有员工是否在统计内，若不在就新增
//                }
//            } else {
//                AttendanceSummeryDto dto = new AttendanceSummeryDto();
//                dto.setEmpId(e.getId());
//
//                dto.setEmpName(e.getName());
//                list.add(dto);
//            }
//        }

        System.out.print("员工考勤合并循环：" + (new Date().getTime() - date3.getTime()));

        criterion.setFirstResult((attendanceCriteria.getPageNumber() - 1) * attendanceCriteria.getPageSize()).setMaxResults(attendanceCriteria.getPageSize());
        return new PagedResult<AttendanceSummeryDto>(list, attendanceCriteria.getPageNumber(), attendanceCriteria.getPageSize(), totalCount);
    }

    @Override
    public PagedResult<AttendanceSummeryDowloadDto> PageBySummerDowloadDto(AttendanceCriteria attendanceCriteria) {
        PagedResult<AttendanceSummeryDto> pagedResult = this.PageBySummeryDto(attendanceCriteria);
        List<AttendanceSummeryDowloadDto> list = new ArrayList<>();
        for (AttendanceSummeryDto a : pagedResult.getItems()) {
            AttendanceSummeryDowloadDto ad = new AttendanceSummeryDowloadDto();
            ad.setEmpName(a.getEmpName());
            ad.setAttend(a.getAttend());
            ad.setGwork(a.getGwork());
            ad.setJwork(a.getJwork());
            ad.setWild(a.getWild());
            ad.setBusinessTravel(a.getBusinessTravel());
            ad.setStudyExperience(a.getStudyExperience());
            ad.setTstudy(a.getTstudy());
            ad.setVacation(a.getVacation());
            ad.setSpa(a.getSpa());
            ad.setVisitFamily(a.getVisitFamily());
            ad.setWeddingFuneral(a.getWeddingFuneral());
            ad.setWorkHurt(a.getWorkHurt());
            ad.setBirthChild(a.getBirthChild());
            ad.setLbirth(a.getLbirth());
            ad.setNurse(a.getNurse());
            ad.setSick(a.getSick());
            ad.setAleave(a.getAleave());
            ad.setAbsenteeism(a.getAbsenteeism());
            ad.setCustody(a.getCustody());
            ad.setDrug(a.getDrug());
            ad.setCompensate(a.getCompensate());
            list.add(ad);
        }
        return new PagedResult<AttendanceSummeryDowloadDto>(list, attendanceCriteria.getPageNumber(), attendanceCriteria.getPageSize(), pagedResult.getPagesCount());
    }


    @Override
    public CommonResult updateAttendanceByDept(Long dept, Date attDate,String dayStation) {

        Integer year = DateHelper.getYear(attDate);
        Integer month = DateHelper.getMonth(attDate);
        String day = "k_" + String.valueOf(DateHelper.getDay(attDate));
        List<EmpDto> employees = employeeService.findEmpsByDept(dept);
        List<Attendance> attendances = getAtendanceByDate(dept, attDate);//获取某部门某月的考勤，某月的第一次打考勤会出现null的情况
        if (attendances == null || attendances.size() == 0) {
            //每月第一次打考情的情况
            buildAttendance(employees, year, month);
        } else {
            //这里应该考虑到新员工入职的情况
            List<EmpDto> del = new ArrayList<>();//已经存在的考勤，将不再建立考勤信息
            for (EmpDto dto : employees) {
                for (Attendance ad : attendances) {
                    if (dto.getId().equals(ad.getEmpId()))
                        del.add(dto);
                }
            }
            if (del.size() > 0) {
                employees.removeAll(del);
                if (employees.size() > 0) buildAttendance(employees, year, month);
            }

//            for (EmpDto emp : employees) {
//                Attendance att = new Attendance();
//                //设置年
//                att.setYear(year);
//                //设置月
//                att.setMonth(month);
//
//                att.setEmpId(emp.getId());
//                att.setEmpName(emp.getName());
//                att.setDepId(emp.getDeptId());
//                att.setDepName(emp.getDeptName());
//                AttendanceDetail attendanceDetail = new AttendanceDetail();
//
//                att.setAttendanceDetail(attendanceDetail);
//                //设置AttendanceSummery
//                AttendanceSummery attendanceSummery = new AttendanceSummery();
//                attendanceSummery.setYear(year);
//                attendanceSummery.setMonth(month);
//                attendanceSummery.setEmpId(emp.getId());
//                attendanceSummery.setEmpName(emp.getName());
//                attendanceSummery.setDepId(emp.getDeptId());
//                attendanceSummery.setDepName(emp.getDeptName());
//                att.setAttendanceSummery(attendanceSummery);
//                attendanceRepository.save(att);
//            }
//
        }
//这里缺少出勤统计
        String sql;
        if(dayStation.equals("休息")){
            sql = "update syy_oa_hr_att set " + day + "='" + AttendanceKind.WX.toString() + "' where dep_id=" + dept + " and att_year=" + year + " and att_month=" + month + " and " + day + " is null";
        }else if(dayStation.equals("上班")){
            sql = "update syy_oa_hr_att set " + day + "='" + AttendanceKind.I.toString() + "' where dep_id=" + dept + " and att_year=" + year + " and att_month=" + month + " and " + day + " is null";
        }else if(dayStation.equals("公休加班")){
            sql = "update syy_oa_hr_att set " + day + "='" + AttendanceKind.J1.toString() + "' where dep_id=" + dept + " and att_year=" + year + " and att_month=" + month + " and " + day + " is null";
        }else {
            sql = "update syy_oa_hr_att set " + day + "='" + AttendanceKind.J2.toString() + "' where dep_id=" + dept + " and att_year=" + year + " and att_month=" + month + " and " + day + " is null";
        }
//        if (isWeekday(DateHelper.formatDate(attDate, "yyyy-MM-dd")) == 1)//todo 周末考勤为休息
//            sql = "update syy_oa_hr_att set " + day + "='" + AttendanceKind.WX.toString() + "' where dep_id=" + dept + " and att_year=" + year + " and att_month=" + month + " and " + day + " is null";
//        else
//            sql = "update syy_oa_hr_att set " + day + "='" + AttendanceKind.I.toString() + "' where dep_id=" + dept + " and att_year=" + year + " and att_month=" + month + " and " + day + " is null";
//update stock as s1 set s1.number = ((select s2.number from stock as s2 where s2.id = 1)+1) where s1.id =1

        SQLQuery query = getSession().createSQLQuery(sql);
        query.executeUpdate();
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<AttendanceCalendar> findOneAttendance(Long EmpId, String beginTime, String endTime) {
        //处理时间
        Date begin = DateHelper.parse(beginTime, "yyyy-MM");
        Date end = DateHelper.parse(endTime, "yyyy-MM");
        Date start = DateHelper.parse(beginTime);
        List<AttendanceCalendar> ados = new ArrayList<>();
        while (!begin.after(end)) {
            Criteria criterion = getSession().createCriteria(Attendance.class);
            criterion.add(Restrictions.eq("isActive", true));
            criterion.add(Restrictions.eq("empId", EmpId));
            criterion.add(Restrictions.eq("year", DateHelper.getYear(begin)));
            criterion.add(Restrictions.eq("month", DateHelper.getMonth(begin)));
            List<Attendance> list = criterion.list();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(begin);
            int dayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (list != null && list.size() > 0) {//内部有数据的情况下
                Attendance attendance = list.get(0);
                for (int i = DateHelper.getDay(start); i <= dayOfMonth; i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(DateHelper.getYear(start), DateHelper.getMonth(start) - 1, i);
                    Date d = cal.getTime();
                    String s = DateHelper.formatDate(new Date(), "yyyy-MM-dd 08:00:00");
                    if (d.after(DateHelper.parse(s))) return ados;//超过今天的日期不在迭代
                    String str = descript(getAttendanceKindfromAttendance(i, attendance));
                    if (!StringUtils.isNullOrWhiteSpace(str)) {
                        AttendanceCalendar a = new AttendanceCalendar();
                        a.setTime(d);
                        a.setDescript(str);
                        ados.add(a);
                    }
                }
            }
//            } else {//内部无数据的情况下
//                for (int i = DateHelper.getDay(start); i <= dayOfMonth; i++) {
//                    Calendar cal = Calendar.getInstance();
//                    cal.set(DateHelper.getYear(start), DateHelper.getMonth(start)-1, i);
//                    Date d = cal.getTime();
//                    String s = DateHelper.formatDate(new Date(), "yyyy-MM-dd 08:00:00");
//                    if (d.after(DateHelper.parse(s))) return ados;//超过今天的日期不在迭代
//                    AttendanceCalendar a = new AttendanceCalendar();
//                    a.setTime(d);
//                    a.setDescript("无");
//                    ados.add(a);
//                }
//            }
            begin = DateHelper.addMonth(begin, 1);
            //todo 时间的月份增加，那么它的日期就要变为1；
            start = DateHelper.parse(DateHelper.formatDate(DateHelper.addMonth(start, 1)), "yyyy-MM");
        }

        return ados;
    }



    //员工建立考勤
    private void buildAttendance(List<EmpDto> employees, Integer year, Integer month) {
        for (EmpDto emp : employees) {
            Attendance att = new Attendance();
            //设置年
            att.setYear(year);
            //设置月
            att.setMonth(month);

            att.setEmpId(emp.getId());
            att.setEmpName(emp.getName());
            att.setDepId(emp.getDeptId());
            att.setDepName(emp.getDeptName());
            AttendanceDetail attendanceDetail = new AttendanceDetail();

            att.setAttendanceDetail(attendanceDetail);
            //设置AttendanceSummery
            AttendanceSummery attendanceSummery = new AttendanceSummery();
            attendanceSummery.setYear(year);
            attendanceSummery.setMonth(month);
            attendanceSummery.setEmpId(emp.getId());
            attendanceSummery.setEmpName(emp.getName());
            attendanceSummery.setDepId(emp.getDeptId());
            attendanceSummery.setDepName(emp.getDeptName());
            att.setAttendanceSummery(attendanceSummery);
            attendanceRepository.save(att);
        }
    }

    /**
     * 将字符转化为汉字
     *
     * @param kind
     * @return
     */
    private String descript(AttendanceKind kind) {
        String result = "休息";
        switch (kind) {
            case I:
                result = "出勤";
                break;
            case J1:
                result = "公休加班";
                break;
            case J2:
                result = "节日加班";
                break;
            case Y:
                result = "野外";
                break;
            case Z:
                result = "学历学习";
                break;
            case X1:
                result = "脱产学习";
                break;
            case X2:
                result = "带薪休假";
                break;
            case S1:
                result = "疗养";
                break;
            case L:
                result = "探亲假";
                break;
            case T:
                result = "探亲假";
                break;
            case H:
                result = "婚丧假";
                break;
            case G:
                result = "工伤假";
                break;
            case C:
                result = "产假";
                break;
            case C1:
                result = "产后休长假";
                break;
            case S2:
                result = "护理假，陪护假，计划生育假";
                break;
            case B:
                result = "病假";
                break;
            case S3:
                result = "事假";
                break;
            case K1:
                result = "旷工";
                break;
            case K2:
                result = "拘留";
                break;
            case K3:
                result = "戒毒";
                break;
            case W:
                result = "劳保";
                break;
            case B2:
                result = "补休";
                break;
            case NP:
                result = null;
                break;
            default:
                break;
        }
        return result;

    }

    /**
     * 从出勤表中得某天道考勤类型
     *
     * @param day
     * @param attendance
     * @return
     */
    private AttendanceKind getAttendanceKindfromAttendance(int day, Attendance attendance) {

        switch (day) {
            case 1:
                return attendance.getK_1() == null ? AttendanceKind.NP : attendance.getK_1();
            case 2:
                return attendance.getK_2() == null ? AttendanceKind.NP : attendance.getK_2();
            case 3:
                return attendance.getK_3() == null ? AttendanceKind.NP : attendance.getK_3();
            case 4:
                return attendance.getK_4() == null ? AttendanceKind.NP : attendance.getK_4();
            case 5:
                return attendance.getK_5() == null ? AttendanceKind.NP : attendance.getK_5();
            case 6:
                return attendance.getK_6() == null ? AttendanceKind.NP : attendance.getK_6();
            case 7:
                return attendance.getK_7() == null ? AttendanceKind.NP : attendance.getK_7();
            case 8:
                return attendance.getK_8() == null ? AttendanceKind.NP : attendance.getK_8();
            case 9:
                return attendance.getK_9() == null ? AttendanceKind.NP : attendance.getK_9();
            case 10:
                return attendance.getK_10() == null ? AttendanceKind.NP : attendance.getK_10();
            case 11:
                return attendance.getK_11() == null ? AttendanceKind.NP : attendance.getK_11();
            case 12:
                return attendance.getK_12() == null ? AttendanceKind.NP : attendance.getK_12();
            case 13:
                return attendance.getK_13() == null ? AttendanceKind.NP : attendance.getK_13();
            case 14:
                return attendance.getK_14() == null ? AttendanceKind.NP : attendance.getK_14();
            case 15:
                return attendance.getK_15() == null ? AttendanceKind.NP : attendance.getK_15();
            case 16:
                return attendance.getK_16() == null ? AttendanceKind.NP : attendance.getK_16();
            case 17:
                return attendance.getK_17() == null ? AttendanceKind.NP : attendance.getK_17();
            case 18:
                return attendance.getK_18() == null ? AttendanceKind.NP : attendance.getK_18();
            case 19:
                return attendance.getK_19() == null ? AttendanceKind.NP : attendance.getK_19();
            case 20:
                return attendance.getK_20() == null ? AttendanceKind.NP : attendance.getK_20();
            case 21:
                return attendance.getK_21() == null ? AttendanceKind.NP : attendance.getK_21();
            case 22:
                return attendance.getK_22() == null ? AttendanceKind.NP : attendance.getK_22();
            case 23:
                return attendance.getK_23() == null ? AttendanceKind.NP : attendance.getK_23();
            case 24:
                return attendance.getK_24() == null ? AttendanceKind.NP : attendance.getK_24();
            case 25:
                return attendance.getK_25() == null ? AttendanceKind.NP : attendance.getK_25();
            case 26:
                return attendance.getK_26() == null ? AttendanceKind.NP : attendance.getK_26();
            case 27:
                return attendance.getK_27() == null ? AttendanceKind.NP : attendance.getK_27();
            case 28:
                return attendance.getK_28() == null ? AttendanceKind.NP : attendance.getK_28();
            case 29:
                return attendance.getK_29() == null ? AttendanceKind.NP : attendance.getK_29();
            case 30:
                return attendance.getK_30() == null ? AttendanceKind.NP : attendance.getK_30();
            case 31:
                return attendance.getK_31() == null ? AttendanceKind.NP : attendance.getK_31();
            default:
                return null;
        }

    }

    private int isWeekday(String string) {
        Date date = DateHelper.parse(string, "yyyy-MM-dd");
        int weekday = DateHelper.getWeek(date) - 1;
        if (weekday == 0 || weekday == 6) return 1;
        return 0;

    }
}
