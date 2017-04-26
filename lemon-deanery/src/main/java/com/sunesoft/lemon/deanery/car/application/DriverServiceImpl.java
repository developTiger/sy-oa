package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.*;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverDto;
import com.sunesoft.lemon.deanery.car.application.dtos.DriverReportDto;
import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.car.domain.*;
import com.sunesoft.lemon.deanery.carWorkFlow.application.dtos.CarApplyDto;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApplyRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper;
import org.hibernate.loader.custom.Return;
import org.hibernate.mapping.Array;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* Created by Administrator on 2016/6/17 0017.
*/

@Service("driverService")
public class DriverServiceImpl extends GenericHibernateFinder implements DriverService {

    @Autowired
    DriverRepository driverRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CarApplyRepository carApplyRepository;
    @Autowired
    private CarRepository carRepository;

    @Override
    public DriverDto getByIdDriver(Long driverId) {
          return DeaneryUtil.convertFormListDriverDto(driverRepository.get(driverId));
    // return null;
    }

    @Override
    public PagedResult<DriverDto> getByDriver(DriverCriteria driverCriteria) {

        Criteria criterion = getSession().createCriteria(Driver.class);
        criterion.add(Restrictions.eq("isActive",true));
        if(null != driverCriteria.getCompanyId()
                && 0 != driverCriteria.getCompanyId()){
            criterion.add(Restrictions.eq("companyId",driverCriteria.getCompanyId()));
        }
        if(null != driverCriteria.getName()
                && "" != driverCriteria.getName()){
            criterion.add(Restrictions.like("driverName",
                    driverCriteria.getName(), MatchMode.ANYWHERE));
        }
        if (null != driverCriteria.getStatus()){
            criterion.add(Restrictions.eq("status",driverCriteria.getStatus()));
        }
        if (null != driverCriteria.getDocType()
                && "" != driverCriteria.getDocType()){
            criterion.add(Restrictions.eq("docType",driverCriteria.getDocType()));
        }
        if ("" != driverCriteria.getBeginTime()&&null != driverCriteria.getBeginTime()){
            String bgtime= driverCriteria.getBeginTime();

            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            try {

                Date begintime=sdf.parse(bgtime);
                criterion.add(Restrictions.ge("hrieTime",begintime ));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if ("" != driverCriteria.getEndTime()&&null != driverCriteria.getEndTime()){
            String edtime= driverCriteria.getEndTime();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            try {
                Date endtime= sdf.parse(edtime);
                criterion.add(Restrictions.le("hrieTime",endtime ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((driverCriteria.getPageNumber() - 1) * driverCriteria.getPageSize()).setMaxResults(driverCriteria.getPageSize());
        List<Driver> beans = criterion.list();
        PagedResult<Driver> pagedResult = new PagedResult<Driver>(beans,driverCriteria.getPageNumber(),driverCriteria.getPageSize(),totalCount);
        List<DriverDto> list = new ArrayList<DriverDto>();
        for(Driver driver : beans){
          /*  Company company =
                    companyRepository.get(driver.getCompanyId());*/
            DriverDto dto  =
            DeaneryUtil.convertFormListDriverDto(driver);
          /*  dto.setCompanyId(company.getId());
            dto.setCompanyName(company.getName());*/
            list.add(dto);
        }
        return new PagedResult<DriverDto>(list,driverCriteria.getPageNumber(),driverCriteria.getPageSize(),totalCount);
    }

    @Override
    public Long addOrUpdate(DriverDto dto) {
        Driver driver = DeaneryUtil.converDtoDriver(dto);
        driver.setIsActive(true);
        if( null != driver.getId() && 0 < driver.getId()) {
            driver.setCreateDateTime(new Date());
        }else{
            driver.setLastUpdateTime(new Date());
        }
        return  driverRepository.save(driver);
    }

    @Override
    public Long addDriver(DriverDto dto) {
        return null;
    }

    @Override
    public boolean delDriver(Long driverId) {
        return false;
    }

    @Override
    public boolean delDriver(Long[] driverIds) {

        Criteria criterion = getSession().createCriteria(Driver.class);
        if (driverIds!=null&&driverIds.length>0) {
            criterion.add(Restrictions.in("id", driverIds));
        }
        List<Driver> beans = criterion.list();
        for (Driver bean : beans) {
            bean.setIsActive(false);
            driverRepository.save(bean);
        }
        return true;
    }

    @Override
    public boolean updDriver(DriverDto dto) {
        return false;
    }

    @Override
    public boolean updDriver(Long driverId) {
        return false;
    }

    @Override
    public List<Company> getComs() {
        Criteria criteria = getSession().createCriteria(Company.class);
        criteria.add(Restrictions.eq("isActive",true));
        return criteria.list();
    }

    /**
     * 司机信息汇总查询
     * @param driverReportCriteria
     * @return
     */
    @Override
    public DriverReportDto driverReport(DriverReportCriteria driverReportCriteria) {
        DriverReportDto driverReportDto = new DriverReportDto();
        driverReportDto.setCompanyId(driverReportCriteria.getCompanyId());
        driverReportDto.setStatus(driverReportCriteria.getStatus());
        driverReportDto.setDriverDocType(driverReportCriteria.getDriverDocType());
        driverReportDto.setDriverReportInfo(driverRepository.driverReport(driverReportCriteria));
        driverReportDto.setCompanys(companyRepository.getAllcoms());
        return driverReportDto;
    }

    @Override
    public List<Map<String, Object>> driverinfo(String driverId){
        String sb ="";
        sb+="select trunc(sum((nvl(cast(a.go_arrive_time as date) -" +
                "                        cast(a.go_start_time as date)," +
                "                        0) + nvl(cast(a.return_arrive_time as date) -" +
                "                                  cast(a.return_start_time as date)," +
                "                                  0)))*1400,0) as one_timebillen," +
                "               a.driver_id," +
                "               count(1) ccno," +
                "               sum(a) ano," +
                "               sum(b) bno," +
                "               sum(c) cno," +
                "               sum(d) dno" +
                "          from (select decode(t.evaluate, 1, 1, 0) a," +
                "                       decode(t.evaluate, 2, 1, 0) b," +
                "                       decode(t.evaluate, 3, 1, 0) c," +
                "                       decode(t.evaluate, 4, 1, 0) d," +
                "                       t.driver_id," +
                "                       t.go_arrive_time," +
                "                       t.go_start_time," +
                "                       t.return_arrive_time," +
                "                       t.return_start_time" +
                "                  from syy_oa_car_flow t" +
                "                 where to_char(t.go_start_time, 'yyyy-MM') =" +
                "                       to_char(add_months(sysdate ,-1),'yyyy-MM')" +
                " and t.driver_id='"+driverId+"'"+
                ") a"+
                "         group by a.driver_id";
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    public  List<Map<String, Object>> CarApplyInfo(CarApplyCriteria carApplyCriteria){
//        Criteria criterion = getSession().createCriteria();
        StringBuffer sb=new StringBuffer();
        sb.append("select t.*,t1.car_no,t2.driver_name from syy_oa_car_flow t left join syy_oa_carinfo t1 on t1.id = t.car_id left join syy_oa_driverinfo t2 on t2.id = t.driver_id left join syy_oa_car_company t3  on t1.company_id=t3.id where 1=1 ");
        if(carApplyCriteria.getCarNo()!=null && !carApplyCriteria.getCarNo().equals("")){
            sb.append(" and t1.car_no='"+carApplyCriteria.getCarNo()+"'");
        }
        if(carApplyCriteria.getAttitude() !=null && carApplyCriteria.getAttitude()!=0){
            sb.append(" and t.attitude='"+carApplyCriteria.getAttitude()+"'");
        }
        if(carApplyCriteria.getStartTime() !=null && !carApplyCriteria.getStartTime().equals("")){
            sb.append(" and to_char(t.go_start_time, 'yyyy-MM-dd') >= '"+carApplyCriteria.getStartTime()+"'");
        }
        if(carApplyCriteria.getEndTime()!=null && !carApplyCriteria.getEndTime().equals("")){
            sb.append(" and to_char(t.go_start_time, 'yyyy-MM-dd') <= '"+carApplyCriteria.getEndTime()+"'");
        }
        if(carApplyCriteria.getCompanyId()!=null && carApplyCriteria.getCompanyId()!=0L){
            sb.append(" and t3.id='"+carApplyCriteria.getCompanyId()+"'");
        }
        if(carApplyCriteria.getDeptId()!=null && carApplyCriteria.getDeptId()!=0L){
            sb.append(" and t.dept_id='"+carApplyCriteria.getDeptId()+"'");
        }
        if(carApplyCriteria.getDriverName()!=null && !carApplyCriteria.getDriverName().equals("")){
            sb.append(" and t2.driver_name like '%"+carApplyCriteria.getDriverName()+"%'");
        }
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.list();
    }

    @Override
    public List<DriverDto> getAllIsActive() {
        Criteria criteria = getSession().createCriteria(Driver.class);
        criteria.add(Restrictions.eq("status",1));
        criteria.add(Restrictions.eq("isActive",true));
        List<Driver> list = criteria.list();
        List<DriverDto> list_dto = new ArrayList<DriverDto>();
        for ( Driver d : list ){
            list_dto.add(DeaneryUtil.convertFormListDriverDto(d));
        }
        return list_dto;
    }

    @Override
    public List<DriverDto> isActiveByDriverId(Long driverId) {
        Criteria criteria = getSession().createCriteria(Driver.class);
        criteria.add(Restrictions.eq("id",driverId));
        criteria.add(Restrictions.eq("status",1));
        criteria.add(Restrictions.eq("isActive",true));
        List<Driver> list = criteria.list();
        List<DriverDto> list_dto = new ArrayList<DriverDto>();
        if (null == list || list.size() == 0){
            return null;
        }
        for ( Driver d : list ){
            list_dto.add(DeaneryUtil.convertFormListDriverDto(d));
        }
        return list_dto;
    }

    public List<Map<String, Object>> CarApplyInfo2(CarApplyCriteria2 carApplyCriteria2){
        String sb ="";
//        sb+="select trunc(sum((nvl(cast(a.go_arrive_time as date) -" +
//                "                        cast(a.go_start_time as date)," +
//                "                        0) + nvl(cast(a.return_arrive_time as date) -" +
//                "                                  cast(a.return_start_time as date)," +
//                "                                  0)))*1400,0) as one_timebillen," +
//                "               count(1) ccno," +
//                "               sum(a) ano," +
//                "               sum(b) bno," +
//                "               sum(c) cno," +
//                "               sum(d) dno," +
//                " a.name,"+
//                " a.driver_name,"+
//                " a.dept_name,"+
//                " a.dept_id,"+
//                "a.did,"+
//               " a.id"+
//                /*" a.go_start_time"+*/
//                "          from (select decode(t.attitude, 1, 1, 0) a," +
//                "                       decode(t.attitude, 2, 1, 0) b," +
//                "                       decode(t.attitude, 3, 1, 0) c," +
//                "                       decode(t.attitude, 4, 1, 0) d," +
//                "                       t.go_arrive_time," +
//                "                       t.go_start_time," +
//                "                       t.return_arrive_time," +
//                "                       t.return_start_time," +
//                "                       c.name," +
//                "                       c.id," +
//                " d.id as did,"+
//                "                       d.driver_name," +
//                "                       t.dept_name,"+
//                "                       t.dept_id"+
//                "                  from syy_oa_car_flow t" +
//                " left join syy_oa_carinfo b"+
//                " on t.car_id = b.id"+
//                " left join syy_oa_car_company c"+
//                " on b.company_id = c.id"+
//                " left join syy_oa_driverinfo d"+
//                " on t.driver_id=d.id "+
//                " where 1=1" +
//                "                       " ;
//        if(carApplyCriteria2.getCompanyId()!=null && carApplyCriteria2.getCompanyId()!=0L){
//            sb+=" and c.id='"+carApplyCriteria2.getCompanyId()+"'";
//        }
//        if(carApplyCriteria2.getDeptId()!=null && carApplyCriteria2.getDeptId()!=0L){
//            sb+=" and t.dept_id='"+carApplyCriteria2.getDeptId()+"'";
//        }
//        if(carApplyCriteria2.getDriverName()!=null && !carApplyCriteria2.getDriverName().equals("")){
//            sb+=" and d.driver_name like '%"+carApplyCriteria2.getDriverName()+"%'";
//        }
//        if(carApplyCriteria2.getStartTime() !=null && !carApplyCriteria2.getStartTime().equals("")){
//            sb+=" and to_char(t.go_start_time, 'yyyy-MM-dd') >= '"+carApplyCriteria2.getStartTime()+"'";
//        }
//        if(carApplyCriteria2.getEndTime()!=null && !carApplyCriteria2.getEndTime().equals("")){
//            sb+=" and to_char(t.go_start_time, 'yyyy-MM-dd') <= '"+carApplyCriteria2.getEndTime()+"'";
//        }
//        sb+=       ") a"+
//               /* 原先按公司名统计。" group by a.name,a.driver_name,a.dept_name,a.go_start_time,a.dept_id,a.id";*/
//
//          " group by a.did,a.name, a.driver_name,a.dept_name, a.dept_id,a.id";
//        sb+="select a.*,c.name,d.driver_name from syy_oa_car_flow a";
//        sb+=" left join syy_oa_carinfo b";
//        sb+=" on a.car_id = b.id ";
//        sb+=" left join syy_oa_car_company c";
//        sb+=" on b.company_id = c.id";
//        sb+=" left join syy_oa_driverinfo d";
//        sb+=" on a.driver_id=d.id where 1=1 ";




        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public List<Map<String, Object>> driverStatistics(DriverStatisticsCriteria driverStatisticsCriteria) {
        String sb ="";
        sb+="select trunc(sum((nvl(cast(a.go_arrive_time as date) -" +
                "                        cast(a.go_start_time as date)," +
                "                        0) + nvl(cast(a.return_arrive_time as date) -" +
                "                                  cast(a.return_start_time as date)," +
                "                                  0)))*1400,0) as one_timebillen," +
                "               count(1) ccno," +
                "               sum(a) ano," +
                "               sum(b) bno," +
                "               sum(c) cno," +
                "               sum(d) dno," +
                " a.name,"+
                " a.driver_name,"+
//                " a.dept_name,"+
//                " a.dept_id,"+
                "a.did,"+
                " a.id"+
                /*" a.go_start_time"+*/
                "          from (select decode(t.evaluate, 1, 1, 0) a," +
                "                       decode(t.evaluate, 2, 1, 0) b," +
                "                       decode(t.evaluate, 3, 1, 0) c," +
                "                       decode(t.evaluate, 4, 1, 0) d," +
                "                       t.go_arrive_time," +
                "                       t.go_start_time," +
                "                       t.return_arrive_time," +
                "                       t.return_start_time," +
                "                       c.name," +
                "                       c.id," +
                " d.id as did,"+
                "                       d.driver_name," +
                "                       t.dept_name,"+
                "                       t.dept_id"+
                "                  from syy_oa_car_flow t" +
                " left join syy_oa_carinfo b"+
                " on t.car_id = b.id"+
                " left join syy_oa_car_company c"+
                " on b.company_id = c.id"+
                " left join syy_oa_driverinfo d"+
                " on t.driver_id=d.id "+
                " where 1=1" +
                " and t.is_complete='1'" +
                "                       " ;
        if(driverStatisticsCriteria.getCompanyId()!=null && driverStatisticsCriteria.getCompanyId()!=0L){
            sb+=" and c.id='"+driverStatisticsCriteria.getCompanyId()+"'";
        }
        if(driverStatisticsCriteria.getDeptId()!=null && driverStatisticsCriteria.getDeptId()!=0L){
            sb+=" and t.dept_id='"+driverStatisticsCriteria.getDeptId()+"'";
        }
        if(driverStatisticsCriteria.getDriverName()!=null && !driverStatisticsCriteria.getDriverName().equals("")){
            sb+=" and d.driver_name like '%"+driverStatisticsCriteria.getDriverName()+"%'";
        }
        if(driverStatisticsCriteria.getStartTime() !=null && !driverStatisticsCriteria.getStartTime().equals("")){
            sb+=" and to_char(t.go_start_time, 'yyyy-MM-dd') >= '"+driverStatisticsCriteria.getStartTime()+"'";
        }
        if(driverStatisticsCriteria.getEndTime()!=null && !driverStatisticsCriteria.getEndTime().equals("")){
            sb+=" and to_char(t.go_start_time, 'yyyy-MM-dd') <= '"+driverStatisticsCriteria.getEndTime()+"'";
        }
        sb+=       ") a"+
                " group by a.did,a.name, a.driver_name," +
//                "a.dept_name, a.dept_id," +
                "a.id" ;


        /*统计时间统计总条数。为分页准备
       String count ="";
        count+="select count(b.count) as num from (select count(*) as count"+
                *//*" a.go_start_time"+*//*
                "          from (select decode(t.attitude, 1, 1, 0) a," +
                "                       decode(t.attitude, 2, 1, 0) b," +
                "                       decode(t.attitude, 3, 1, 0) c," +
                "                       decode(t.attitude, 4, 1, 0) d," +
                "                       t.go_arrive_time," +
                "                       t.go_start_time," +
                "                       t.return_arrive_time," +
                "                       t.return_start_time," +
                "                       c.name," +
                "                       c.id," +
                " d.id as did,"+
                "                       d.driver_name," +
                "                       t.dept_name,"+
                "                       t.dept_id"+
                "                  from syy_oa_car_flow t" +
                " left join syy_oa_carinfo b"+
                " on t.car_id = b.id"+
                " left join syy_oa_car_company c"+
                " on b.company_id = c.id"+
                " left join syy_oa_driverinfo d"+
                " on t.driver_id=d.id "+
                " where 1=1 and t.is_complete='1'" +
                "                       " ;
        if(driverStatisticsCriteria.getCompanyId()!=null && driverStatisticsCriteria.getCompanyId()!=0L){
            count+=" and c.id='"+driverStatisticsCriteria.getCompanyId()+"'";
        }
        if(driverStatisticsCriteria.getDeptId()!=null && driverStatisticsCriteria.getDeptId()!=0L){
            count+=" and t.dept_id='"+driverStatisticsCriteria.getDeptId()+"'";
        }
        if(driverStatisticsCriteria.getDriverName()!=null && !driverStatisticsCriteria.getDriverName().equals("")){
            count+=" and d.driver_name like '%"+driverStatisticsCriteria.getDriverName()+"%'";
        }
        if(driverStatisticsCriteria.getStartTime() !=null && !driverStatisticsCriteria.getStartTime().equals("")){
            count+=" and to_char(t.go_start_time, 'yyyy-MM-dd') >= '"+driverStatisticsCriteria.getStartTime()+"'";
        }
        if(driverStatisticsCriteria.getEndTime()!=null && !driverStatisticsCriteria.getEndTime().equals("")){
            count+=" and to_char(t.go_start_time, 'yyyy-MM-dd') <= '"+driverStatisticsCriteria.getEndTime()+"'";
        }
        count+=       ") a"+
                " group by a.did,a.name, a.driver_name,a.dept_name, a.dept_id,a.id)b";

        Query counts = this.getSession().createSQLQuery(count.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
       Map<String,Object> map=(Map<String,Object>)counts.uniqueResult();
        System.out.print(map.get("NUM").toString());
        int num= Integer.parseInt( map.get("NUM").toString());*/
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public List<Deptment> findAllDept(){
        Query query = this.getSession().createSQLQuery("select * from syy_oa_hr_dept").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @Override
    public PagedResult CarApplyInfo3(CarApplyCriteria2 carApplyCriteria2) {
            String sql="SELECT * FROM " +
                    "(" +
                    "SELECT A.*, ROWNUM RN " +
                    "FROM (select a.id,a.applyer_name,a.dept_name,d.driver_name,c.name,a.address," +
                    "a.go_start_time,a.return_arrive_time,a.evaluate " +
                    "from syy_oa_car_flow a left join syy_oa_carinfo b" +
                    "            on a.car_id = b.id" +
                    "          left join syy_oa_car_company c" +
                    "            on b.company_id = c.id" +
                    "          left join syy_oa_driverinfo d" +
                    "            on a.driver_id = d.id where 1=1 " +
                    "and a.is_complete='1'" ;
        if(carApplyCriteria2.getApplyerName()!=null && !carApplyCriteria2.getApplyerName().equals("")){
            sql+=" and a.applyer_name like '%"+carApplyCriteria2.getApplyerName()+"%'";
        }
        if(carApplyCriteria2.getAddress()!=null && !carApplyCriteria2.getAddress().equals("")){
            sql+=" and a.address like '%"+carApplyCriteria2.getAddress()+"%'";
        }
        if(carApplyCriteria2.getDeptName()!=null && !carApplyCriteria2.getDeptName().equals("")){
            sql+=" and a.dept_name like '%"+carApplyCriteria2.getDeptName()+"%'";
        }
        if(carApplyCriteria2.getDriverName()!=null && !carApplyCriteria2.getDriverName().equals("")){
            sql+=" and d.driver_name like '%"+carApplyCriteria2.getDriverName()+"%'";
        }
        if(carApplyCriteria2.getCompanyName()!=null && !carApplyCriteria2.getCompanyName().equals("")){
            sql+=" and c.name like '%"+carApplyCriteria2.getCompanyName()+"%'";
        }
        if(carApplyCriteria2.getEvaluate()!=null && !carApplyCriteria2.getEvaluate().equals("0")){
            sql+=" and a.evaluate = '"+carApplyCriteria2.getEvaluate()+"'";
        }
        if(carApplyCriteria2.getBeginDate()!=null && !carApplyCriteria2.getBeginDate().equals("")){
            sql+=" and to_char(a.go_start_time, 'yyyy-MM-dd') >= '"+carApplyCriteria2.getBeginDate()+"'";
        }
        if(carApplyCriteria2.getEndDate()!=null && !carApplyCriteria2.getEndDate().equals("")){
            sql+=" and to_char(a.go_start_time, 'yyyy-MM-dd') <= '"+carApplyCriteria2.getEndDate()+"'";
        }
                    sql+= ") A " +
                    "WHERE ROWNUM <= "+carApplyCriteria2.getPageSize()+"+"+carApplyCriteria2.getPageSize()+"*"+(carApplyCriteria2.getPageNumber()-1)+
                    ")" +
                    "WHERE RN >= 1+"+carApplyCriteria2.getPageSize()+"*"+(carApplyCriteria2.getPageNumber()-1);
            Query query = this.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List<Map<String, Object>> list=query.list();
            for(int i=0;i<list.size();i++){
                String value=String.valueOf(list.get(i).get("EVALUATE"));
                String evaluate="";
                if(value.equals("1")){
                    evaluate="优秀";
                }else if(value.equals("2")){
                    evaluate="良好";
                }else if(value.equals("3")){
                    evaluate="一般";
                }else if(value.equals("4")){
                    evaluate="差";
                }
                list.get(i).put("EVALUATE",evaluate);
            }
            String totalSql= "select count(*) " +
                    "from syy_oa_car_flow a left join syy_oa_carinfo b" +
                    "            on a.car_id = b.id" +
                    "          left join syy_oa_car_company c" +
                    "            on b.company_id = c.id" +
                    "          left join syy_oa_driverinfo d" +
                    "            on a.driver_id = d.id" +
                    " where 1=1 and a.is_complete='1' " ;
        if(carApplyCriteria2.getApplyerName()!=null && !carApplyCriteria2.getApplyerName().equals("")){
            totalSql+=" and a.applyer_name like '%"+carApplyCriteria2.getApplyerName()+"%'";
        }
        if(carApplyCriteria2.getAddress()!=null && !carApplyCriteria2.getAddress().equals("")){
            totalSql+=" and a.address like '%"+carApplyCriteria2.getAddress()+"%'";
        }
        if(carApplyCriteria2.getDeptName()!=null && !carApplyCriteria2.getDeptName().equals("")){
            totalSql+=" and a.dept_name like '%"+carApplyCriteria2.getDeptName()+"%'";
        }
        if(carApplyCriteria2.getDriverName()!=null && !carApplyCriteria2.getDriverName().equals("")){
            totalSql+=" and d.driver_name like '%"+carApplyCriteria2.getDriverName()+"%'";
        }
        if(carApplyCriteria2.getCompanyName()!=null && !carApplyCriteria2.getCompanyName().equals("")){
            totalSql+=" and c.name like '%"+carApplyCriteria2.getCompanyName()+"%'";
        }
        if(carApplyCriteria2.getEvaluate()!=null && !carApplyCriteria2.getEvaluate().equals("0")){
            totalSql+=" and a.evaluate = '"+carApplyCriteria2.getEvaluate()+"'";
        }
        if(carApplyCriteria2.getBeginDate()!=null && !carApplyCriteria2.getBeginDate().equals("")){
            totalSql+=" and to_char(a.go_start_time, 'yyyy-MM-dd') >= '"+carApplyCriteria2.getBeginDate()+"'";
        }
        if(carApplyCriteria2.getEndDate()!=null && !carApplyCriteria2.getEndDate().equals("")){
            totalSql+=" and to_char(a.go_start_time, 'yyyy-MM-dd') <= '"+carApplyCriteria2.getEndDate()+"'";
        };
            List<Map<String, Object>> list2=this.getSession().createSQLQuery(totalSql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            Set<String> set= list2.get(0).keySet();
            int totalCount=0;
            for(String str:set){
                totalCount=Integer.parseInt(String.valueOf(list2.get(0).get(str)));
                break;
            }
            return new PagedResult<Map<String, Object>>(list,carApplyCriteria2.getPageNumber(),carApplyCriteria2.getPageSize(),totalCount);
    }

    @Override
    public CarApplyDto getDtoById(String id) {
        CarApply carApply=carApplyRepository.get(Long.parseLong(id));
        CarApplyDto dto=new CarApplyDto();
        BeanUtils.copyProperties(carApply,dto);
        dto.setPredictGoStartDate(DateToString(carApply.getPredictGoStartTime()));
        dto.setPredictGoArriveDate(DateToString(carApply.getPredictGoArriveTime()));
        dto.setGoStartDate(DateToString(carApply.getGoStartTime()));
        dto.setGoArriveDate(DateToString(carApply.getGoArriveTime()));
        dto.setReturnStartDate(DateToString(carApply.getReturnStartTime()));
        dto.setReturnArriveDate(DateToString(carApply.getReturnArriveTime()));
        //如果车ID为空，查询车
        if(null != carApply.getCarId()){
            Car car = carRepository.get(carApply.getCarId());
            dto.setCarNo(car.getCarNo());
            dto.setCarId(car.getId());
        }
        //如果司机ID为空，查询司机
        if(null != carApply.getDriverId()){
            Driver driver = driverRepository.get(carApply.getDriverId());
            dto.setDriverName(driver.getDriverName());
            dto.setDriverId(driver.getId());
        }
        return dto;
    }

    @Override
    public Company getCompanyById(CarApplyDto carApplyDto) {
        Company company=null;
        if(carApplyDto.getCarId()!=null && carApplyDto.getCarId()!=0L){
            Car car=carRepository.get(carApplyDto.getCarId());
//            company =companyRepository.get(car.getCompanyId());
        }
        return company;
    }

    @Override
    public PagedResult<Map<String,Object>> driverStatisticsPage(DriverStatisticsCriteria driverStatisticsCriteria) {
        String sb ="";
        sb+="select * from (select rownums.*,rownum as rn from (select trunc(sum((nvl(cast(a.go_arrive_time as date) -" +
                "                        cast(a.go_start_time as date)," +
                "                        0) + nvl(cast(a.return_arrive_time as date) -" +
                "                                  cast(a.return_start_time as date)," +
                "                                  0)))*1400,0) as one_timebillen," +
                "               count(1) ccno," +
                "               sum(a) ano," +
                "               sum(b) bno," +
                "               sum(c) cno," +
                "               sum(d) dno," +
                " a.name,"+
                " a.driver_name,"+
                " a.dept_name,"+
                " a.dept_id,"+
                "a.did,"+
                " a.id"+
                /*" a.go_start_time"+*/
                "          from (select decode(t.attitude, 1, 1, 0) a," +
                "                       decode(t.attitude, 2, 1, 0) b," +
                "                       decode(t.attitude, 3, 1, 0) c," +
                "                       decode(t.attitude, 4, 1, 0) d," +
                "                       t.go_arrive_time," +
                "                       t.go_start_time," +
                "                       t.return_arrive_time," +
                "                       t.return_start_time," +
                "                       c.name," +
                "                       c.id," +
                " d.id as did,"+
                "                       d.driver_name," +
                "                       t.dept_name,"+
                "                       t.dept_id"+
                "                  from syy_oa_car_flow t" +
                " left join syy_oa_carinfo b"+
                " on t.car_id = b.id"+
                " left join syy_oa_car_company c"+
                " on b.company_id = c.id"+
                " left join syy_oa_driverinfo d"+
                " on t.driver_id=d.id "+
                " where 1=1" +
                "                       " ;
        if(driverStatisticsCriteria.getCompanyId()!=null && driverStatisticsCriteria.getCompanyId()!=0L){
            sb+=" and c.id='"+driverStatisticsCriteria.getCompanyId()+"'";
        }
        if(driverStatisticsCriteria.getDeptId()!=null && driverStatisticsCriteria.getDeptId()!=0L){
            sb+=" and t.dept_id='"+driverStatisticsCriteria.getDeptId()+"'";
        }
        if(driverStatisticsCriteria.getDriverName()!=null && !driverStatisticsCriteria.getDriverName().equals("")){
            sb+=" and d.driver_name like '%"+driverStatisticsCriteria.getDriverName()+"%'";
        }
        if(driverStatisticsCriteria.getStartTime() !=null && !driverStatisticsCriteria.getStartTime().equals("")){
            sb+=" and to_char(t.go_start_time, 'yyyy-MM-dd') >= '"+driverStatisticsCriteria.getStartTime()+"'";
        }
        if(driverStatisticsCriteria.getEndTime()!=null && !driverStatisticsCriteria.getEndTime().equals("")){
            sb+=" and to_char(t.go_start_time, 'yyyy-MM-dd') <= '"+driverStatisticsCriteria.getEndTime()+"'";
        }
        sb+=       ") a"+
                " group by a.did,a.name, a.driver_name,a.dept_name, a.dept_id,a.id)rownums where rownum <="
        +driverStatisticsCriteria.getPageSize()+"*"+driverStatisticsCriteria.getPageNumber()+") where rn >"
                +(driverStatisticsCriteria.getPageNumber()-1)+"*"+driverStatisticsCriteria.getPageSize()
                ;
       /* 统计时间统计总条数。为分页准备*/
       String count ="";
        count+="select count(b.count) as num from (select count(*) as count"+

                "          from (select decode(t.attitude, 1, 1, 0) a," +
                "                       decode(t.attitude, 2, 1, 0) b," +
                "                       decode(t.attitude, 3, 1, 0) c," +
                "                       decode(t.attitude, 4, 1, 0) d," +
                "                       t.go_arrive_time," +
                "                       t.go_start_time," +
                "                       t.return_arrive_time," +
                "                       t.return_start_time," +
                "                       c.name," +
                "                       c.id," +
                " d.id as did,"+
                "                       d.driver_name," +
                "                       t.dept_name,"+
                "                       t.dept_id"+
                "                  from syy_oa_car_flow t" +
                " left join syy_oa_carinfo b"+
                " on t.car_id = b.id"+
                " left join syy_oa_car_company c"+
                " on b.company_id = c.id"+
                " left join syy_oa_driverinfo d"+
                " on t.driver_id=d.id "+
                " where 1=1" +
                "                       " ;
        if(driverStatisticsCriteria.getCompanyId()!=null && driverStatisticsCriteria.getCompanyId()!=0L){
            count+=" and c.id='"+driverStatisticsCriteria.getCompanyId()+"'";
        }
        if(driverStatisticsCriteria.getDeptId()!=null && driverStatisticsCriteria.getDeptId()!=0L){
            count+=" and t.dept_id='"+driverStatisticsCriteria.getDeptId()+"'";
        }
        if(driverStatisticsCriteria.getDriverName()!=null && !driverStatisticsCriteria.getDriverName().equals("")){
            count+=" and d.driver_name like '%"+driverStatisticsCriteria.getDriverName()+"%'";
        }
        if(driverStatisticsCriteria.getStartTime() !=null && !driverStatisticsCriteria.getStartTime().equals("")){
            count+=" and to_char(t.go_start_time, 'yyyy-MM-dd') >= '"+driverStatisticsCriteria.getStartTime()+"'";
        }
        if(driverStatisticsCriteria.getEndTime()!=null && !driverStatisticsCriteria.getEndTime().equals("")){
            count+=" and to_char(t.go_start_time, 'yyyy-MM-dd') <= '"+driverStatisticsCriteria.getEndTime()+"'";
        }
        count+=       ") a"+
                " group by a.did,a.name, a.driver_name,a.dept_name, a.dept_id,a.id)b";

        Query counts = this.getSession().createSQLQuery(count.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
       Map<String,Object> map=(Map<String,Object>)counts.uniqueResult();
       // System.out.print(map.get("NUM").toString());
        int num= Integer.parseInt( map.get("NUM").toString());
        Query query = this.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String,Object>> list=query.list();
        return new PagedResult<Map<String,Object>>(list,driverStatisticsCriteria.getPageNumber(),driverStatisticsCriteria.getPageSize(),num);
    }

    public String DateToString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date !=null){
            return sdf.format(date);
        }else{
            return null;
        }
    }
}
