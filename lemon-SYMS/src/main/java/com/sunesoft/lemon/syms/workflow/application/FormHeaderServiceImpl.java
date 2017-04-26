package com.sunesoft.lemon.syms.workflow.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.application.criteria.FormHeaderCriteria;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.factory.WorkflowDtoFactory;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormHeaderRepository;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/6/25.
 */
@Service("formHeaderService")
public class FormHeaderServiceImpl extends GenericHibernateFinder implements FormHeaderService {

    @Autowired
    FormHeaderRepository formHeaderRepository;

    @Override
    public FormHeaderDto getHeaderByFormNo(Long formNo) {
        FormHeader header = formHeaderRepository.get(formNo);
        FormHeaderDto dto = WorkflowDtoFactory.convertFormHeaderToDto(header);


        return dto;
    }

    @Override
    public PagedResult<FormHeaderDto> findFormPaged(FormHeaderCriteria criteria) {

        String  hql = "select distinct  h from FormHeader h ";
        String countSql = "select count(distinct h.id) from FormHeader h ";
        String whereCondition = " where h.isActive=1 ";
        if (criteria.getApproveStatus() != null || criteria.getApproverId() != null) {

            hql+="join  h.formApprovers a ";
            countSql+="join  h.formApprovers a ";
            if (criteria.getApproveStatus() != null)
                whereCondition+= " and a.approveStatus="+criteria.getApproveStatus().ordinal();
            if (criteria.getApproverId() != null)
                whereCondition+= " and a.appUserId="+criteria.getApproverId();
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            whereCondition+= " and h.formKind='"+criteria.getFormKind()+"'";
        }if (null != criteria.getBlongDept()) {
            whereCondition+= " and h.deptId="+criteria.getBlongDept();
        }
        if (criteria.getApplyer() != null && criteria.getApplyer() > 0) {
            whereCondition+= " and h.applyer="+criteria.getApplyer();
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getApplyerName())) {
            whereCondition+= " and h.applyerName like '%"+criteria.getApplyerName()+"%'";
        }
        if (criteria.getArrFormStatus() != null && criteria.getArrFormStatus().size() > 0) {
            String s = "";
            for(FormStatus status: criteria.getArrFormStatus()){
                s+=status.ordinal()+",";
            }

            whereCondition+= " and h.formStatus in ("+s.substring(0,s.length()-1)+")";

        }
        if (criteria.getFormNo() != null && criteria.getFormNo() > 0) {
            whereCondition+= " and h.formNo="+criteria.getFormNo();
        }

        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {

                whereCondition+= " and h.beginDate>=:beginDate";

        }
        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {

                whereCondition+= " and h.beginDate<=:endDate";
        }




        Query count= getSession().createQuery(countSql+whereCondition);
        Query query= getSession().createQuery(hql+whereCondition +" order by h.id desc");
        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
            String beginTime = criteria.getBeginDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date begining = sdf.parse(beginTime);
                count.setTimestamp("beginDate", begining);
                query.setTimestamp("beginDate", begining);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {
            String endTime = criteria.getEndDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date ending = sdf.parse(endTime);
                count.setTimestamp("endDate", ending);
                query.setTimestamp("endDate", ending);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        count.setResultTransformer(Criteria.ROOT_ENTITY);
        int totalCount =((Long) count.uniqueResult()).intValue();


        query.setResultTransformer(Criteria.ROOT_ENTITY);

        query.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<FormHeader> beans = query.list();
//
//        DetachedCriteria criterion = DetachedCriteria.forClass(
//                FormHeader.class);
//        // Criteria criterion = getSession().createCriteria(FormHeader.class, "FormHeader");
//
//
//       Criteria cc=criterion .getExecutableCriteria(
//               getSession());
//        int totalCount = ((Long) (cc.setProjection(Projections.countDistinct("id")).uniqueResult())).intValue();
//        cc.setProjection(null);
////
////        ProjectionList projectionList = Projections.projectionList();
////        //决定返回的结果
////        projectionList.add(Projections.property("fh"));
////        criterion.setProjection(Projections.distinct(projectionList));
//        cc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        cc.addOrder(Property.forName("id").desc());
//        cc.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
//
//        List<FormHeader> beans = cc.list();

        List<FormHeaderDto> list = new ArrayList<FormHeaderDto>();
        for (FormHeader header : beans) {
            header.getFormApprovers().size();
            list.add(WorkflowDtoFactory.convert(header, new FormHeaderDto()));
        }
        return new PagedResult<FormHeaderDto>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public PagedResult<FormHeaderDto> findFormPagedWithDept(FormHeaderCriteria criteria) {
        String sql = "select  DISTINCT HEAD.\"ID\",head.APPLYER as applyer, HEAD.APPLYER_NAME as applyerName,head.DEPT_ID as deptId, head.DEPT_NAME as deptName,head.\"ID\" as formNo,head.FORM_KIND as formKind\n" +
                ",HEAD.FORM_KIND_NAME as formKindName ,head.FORM_STATUS as formStatus,head.BEGIN_DATE as beginDate, head.DUE_DATE as dueDate,head.END_DATE as endDate\n" +
                ",HEAD.BLONG_DEPT as blongDept,HEAD.BLONG_DEPT_NAME as blongDeptName,head.LIFE_CYCLE as lifeCycle,head.NOTICE_FLAG as noticeFlag,head.PROVIOUS_APPROVER as proviousApprover\n" +
                ",head.\"CONTENT\" as \"content\",HEAD.REMARK as remark,HEAD.SUMMERY as SUMMERY,HEAD.VIEW_URL as viewUrl,head.CL_STEP as clStep,head.IS_STEP_COMPLETE as isStepComplete\n" +
                ",HEAD.CURRENT_APP_TYPE as currentAppType,HEAD.HAS_APPLY_VIEW as hasApplyView,HEAD.VIEW_ACTION as currentViewAction,HEAD.APPROVE_ACTION as currentApproveAction\n" +
                ",head.CURRENT_APP_POINT_ID as currentAppPointId,HEAD.CURRENT_POINT_NAME as currentPointName ,HEAD.NEXT_APP_POINT_ID as nextAppPointId" +
                " from SYY_OA_FM_HEADER head INNER JOIN SYY_OA_FM_APPROVER  app  on HEAD.\"ID\" = app.FORM_NO ";

        if(null!=criteria.getApproverDeptId() ) {
            sql += " where APP.APP_USER_ID in (select \"ID\" from SYY_OA_HR_EMPLOYEES where DEPT_ID =" + criteria.getApproverDeptId() +") ";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())){
            sql += " and head.form_kind= " +"'"+criteria.getFormKind()+"'";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getApplyerName())){
            sql += " and head.applyer_name like " +"'%"+criteria.getApplyerName()+"%'";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getBeginDate())){
            sql += " and head.begin_date>= to_date(' " + criteria.getBeginDate()+"'" +" ,'yyyy-mm-dd hh24:mi:ss') ";
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getEndDate())){
            sql += " and head.begin_date<= to_date(' " + criteria.getEndDate()+"'" +" ,'yyyy-mm-dd hh24:mi:ss') ";
        }

        sql += " and APP.APPROVE_STATUS = 2 and HEAD.IS_STEP_COMPLETE =0 order by HEAD.\"ID\" desc";
        //List<FormHeaderDto> dtos = this.queryForObjects(FormHeaderDto.class,sql,null);
        return  this.pagingBySql(criteria.getPageNumber(),criteria.getPageSize(),FormHeaderDto.class,sql,null);

//
//        SQLQuery totalCountQuery = getSession().createSQLQuery(countSql);
//        int totalCount  =Integer.valueOf(totalCountQuery.uniqueResult().toString());
//
//        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
//        //sqlQuery.setResultTransformer(Criteria.ROOT_ENTITY);
//        sqlQuery.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
//        List<FormHeader> beans = sqlQuery.list();
//        List<FormHeaderDto> list= new ArrayList<>();
//        for (Object header : beans) {
//            list.add(WorkflowDtoFactory.convert(header, new FormHeaderDto()));
//        }
//        return new PagedResult<>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);

//        String  hql = "select distinct  h from FormHeader h ";
//        String countSql = "select count(distinct h.id) from FormHeader h ";
//        String whereCondition = " where h.isActive=1 ";
//        if (criteria.getApproveStatus() != null || criteria.getApproverId() != null) {
//
//            hql+="join  h.formApprovers a ";
//            countSql+="join  h.formApprovers a ";
//            if (criteria.getApproveStatus() != null)
//                whereCondition+= " and a.approveStatus="+criteria.getApproveStatus().ordinal();
//            if (criteria.getApproverId() != null)
//                whereCondition+= " and a.appUserId="+criteria.getApproverId();
//        }
//        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
//            whereCondition+= " and h.formKind='"+criteria.getFormKind()+"'";
//        }if (null != criteria.getBlongDept()) {
//            whereCondition+= " and h.deptId="+criteria.getBlongDept();
//        }
//        if (criteria.getApplyer() != null && criteria.getApplyer() > 0) {
//            whereCondition+= " and h.applyer="+criteria.getApplyer();
//        }
//        if (!StringUtils.isNullOrWhiteSpace(criteria.getApplyerName())) {
//            whereCondition+= " and h.applyerName like '%"+criteria.getApplyerName()+"%'";
//        }
//        if (criteria.getArrFormStatus() != null && criteria.getArrFormStatus().size() > 0) {
//            String s = "";
//            for(FormStatus status: criteria.getArrFormStatus()){
//                s+=status.ordinal()+",";
//            }
//
//            whereCondition+= " and h.formStatus in ("+s.substring(0,s.length()-1)+")";
//
//        }
//        if (criteria.getFormNo() != null && criteria.getFormNo() > 0) {
//            whereCondition+= " and h.formNo="+criteria.getFormNo();
//        }
//
//        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
//
//            whereCondition+= " and h.beginDate>=:beginDate";
//
//        }
//        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {
//
//            whereCondition+= " and h.beginDate<=:endDate";
////        }
////
////
////
////
////        Query count= getSession().createQuery(countSql+whereCondition);
////        Query query= getSession().createQuery(hql+whereCondition +" order by h.id desc");
////        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
////            String beginTime = criteria.getBeginDate();
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////            try {
////                Date begining = sdf.parse(beginTime);
////                count.setTimestamp("beginDate", begining);
////                query.setTimestamp("beginDate", begining);
////            } catch (ParseException e) {
////                e.printStackTrace();
////            }
////
////        }
////        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {
////            String endTime = criteria.getEndDate();
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////            try {
////                Date ending = sdf.parse(endTime);
////                count.setTimestamp("endDate", ending);
////                query.setTimestamp("endDate", ending);
////            } catch (ParseException e) {
////                e.printStackTrace();
////            }
////        }
////        count.setResultTransformer(Criteria.ROOT_ENTITY);
////        int totalCount =((Long) count.uniqueResult()).intValue();
////
////
////        query.setResultTransformer(Criteria.ROOT_ENTITY);
////
////        query.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
////        List<FormHeader> beans = query.list();
////
////        DetachedCriteria criterion = DetachedCriteria.forClass(
////                FormHeader.class);
////        // Criteria criterion = getSession().createCriteria(FormHeader.class, "FormHeader");
////
////
////       Criteria cc=criterion .getExecutableCriteria(
////               getSession());
////        int totalCount = ((Long) (cc.setProjection(Projections.countDistinct("id")).uniqueResult())).intValue();
////        cc.setProjection(null);
//////
//////        ProjectionList projectionList = Projections.projectionList();
//////        //决定返回的结果
//////        projectionList.add(Projections.property("fh"));
//////        criterion.setProjection(Projections.distinct(projectionList));
////        cc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
////        cc.addOrder(Property.forName("id").desc());
////        cc.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
////
////        List<FormHeader> beans = cc.list();
//
//        List<FormHeaderDto> list = new ArrayList<FormHeaderDto>();
//        for (FormHeader header : beans) {
//            header.getFormApprovers().size();
//            list.add(WorkflowDtoFactory.convert(header, new FormHeaderDto()));
//        }
//        return new PagedResult<FormHeaderDto>(list, criteria.getPageNumber(), criteria.getPageSize(), totalCount);
    }

    @Override
    public List<FormHeaderDto> findFormHeader(FormHeaderCriteria criteria) {

        String  hql = "select distinct  h from FormHeader h ";
        String countSql = "select count(distinct h.id) from FormHeader h ";
        String whereCondition = " where h.isActive=1 ";
        if (criteria.getApproveStatus() != null || criteria.getApproverId() != null) {

            hql+="join  h.formApprovers a ";
            countSql+="join  h.formApprovers a ";
            if (criteria.getApproveStatus() != null)
                whereCondition+= " and a.approveStatus="+criteria.getApproveStatus().ordinal();
            if (criteria.getApproverId() != null)
                whereCondition+= " and a.appUserId="+criteria.getApproverId();
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getFormKind())) {
            whereCondition+= " and h.formKind='"+criteria.getFormKind()+"'";
        }if (null != criteria.getBlongDept()) {
            whereCondition+= " and h.deptId="+criteria.getBlongDept();
        }
        if (criteria.getApplyer() != null && criteria.getApplyer() > 0) {
            whereCondition+= " and h.applyer="+criteria.getApplyer();
        }
        if (!StringUtils.isNullOrWhiteSpace(criteria.getApplyerName())) {
            whereCondition+= " and h.applyerName like '%"+criteria.getApplyerName()+"%'";
        }
        if (criteria.getArrFormStatus() != null && criteria.getArrFormStatus().size() > 0) {
            String s = "";
            for(FormStatus status: criteria.getArrFormStatus()){
                s+=status.ordinal()+",";
            }

            whereCondition+= " and h.formStatus in ("+s.substring(0,s.length()-1)+")";

        }
        if (criteria.getFormNo() != null && criteria.getFormNo() > 0) {
            whereCondition+= " and h.formNo="+criteria.getFormNo();
        }

        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {

            whereCondition+= " and h.beginDate>=:beginDate";

        }
        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {

            whereCondition+= " and h.beginDate<=:endDate";
        }




        Query count= getSession().createQuery(countSql+whereCondition);
        Query query= getSession().createQuery(hql+whereCondition +" order by h.id desc");
        if (criteria.getBeginDate() != "" && criteria.getBeginDate() != null) {
            String beginTime = criteria.getBeginDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date begining = sdf.parse(beginTime);
                count.setTimestamp("beginDate", begining);
                query.setTimestamp("beginDate", begining);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (criteria.getEndDate() != "" && criteria.getEndDate() != null) {
            String endTime = criteria.getEndDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date ending = sdf.parse(endTime);
                count.setTimestamp("endDate", ending);
                query.setTimestamp("endDate", ending);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        count.setResultTransformer(Criteria.ROOT_ENTITY);
        int totalCount =((Long) count.uniqueResult()).intValue();


        query.setResultTransformer(Criteria.ROOT_ENTITY);

        query.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize()).setMaxResults(criteria.getPageSize());
        List<FormHeader> beans = query.list();
        List<FormHeaderDto> list = new ArrayList<FormHeaderDto>();
        for (FormHeader header : beans) {
            header.getFormApprovers().size();
            list.add(WorkflowDtoFactory.convert(header, new FormHeaderDto()));
        }
        return list;
    }
    @Override
    public CommonResult AddFormHeader(FormHeaderDto formHeaderDto) {
        FormHeader formHeader = new FormHeader();
        BeanUtils.copyProperties(formHeaderDto, formHeader);
        formHeaderRepository.save(formHeader);
        return ResultFactory.commonSuccess();
    }

    public CommonResult UpdateFormHeader(FormHeaderDto formHeaderDto) {
        FormHeader formHeader = formHeaderRepository.get(formHeaderDto.getApplyer());
        BeanUtils.copyProperties(formHeaderDto, formHeader);
        formHeaderRepository.save(formHeader);
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<FormHeader> getHeaderByParentFormNo(Long parentFormNo) {
        return formHeaderRepository.getHeaderByParentFormNo(parentFormNo);

    }

    @Override
    public FormHeader getHeaderById(Long headerId) {
        return formHeaderRepository.get(headerId);
    }


}
