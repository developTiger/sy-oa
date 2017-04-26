package com.sunesoft.lemon.syms.hrForms.application.formDeptAppraise;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ListResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.dept.Deptment;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDetailDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.domain.*;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("formDeptAppraiseService")
public class FormDeptAppraiseServiceImpl extends FormBase2<DeptAppraise, DeptAppraiseDto> implements FormDeptAppraiseService {

    @Autowired
    DeptAppraiseRepository deptAppraiseRepository;

    @Autowired
    DeptSubAppraiseRepository deptSubAppraiseRepository;

    @Override
    protected CommonResult save(DeptAppraise deptAppraise) {

        deptAppraiseRepository.save(deptAppraise);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(DeptAppraise deptAppraise) {
        return null;
    }

    @Override
    protected DeptAppraise ConvetDto(DeptAppraiseDto dto) {
        DeptAppraise deptAppraise = new DeptAppraise();
        deptAppraise = DtoFactory.convert(dto, deptAppraise);


        return deptAppraise;
    }

    @Override
    protected String getSummery(DeptAppraise deptAppraise) {
        return deptAppraise.getAppraisTitle();
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
//        List<DeptSubAppraise> subAppraises = deptSubAppraiseRepository.getSubAppraiseByParentFormNo(formNo);
//        //todo 检查结果是否上传，这里我设想每一个结果都要已经上传
//        for (DeptSubAppraise dsa : subAppraises) {
//            if ((StringUtils.isNullOrWhiteSpace(dsa.getGroupGrade()) || StringUtils.isNullOrWhiteSpace(dsa.getDeptLevel()) || StringUtils.isNullOrWhiteSpace(dsa.getGroupGrade()))&&(!StringUtils.isNullOrWhiteSpace(dsa.getDeptGrade())||dsa.getDeptGrade()!=null||!StringUtils.isNullOrWhiteSpace(dsa.getDeptLevel())))
//                return new CommonResult(false, dsa.getDeptName() + "没有给予最终考核结果");
//            }
////            StringBuilder builder = new StringBuilder();
////
////            for (DeptSubAppraise sub : subAppraises) {
////                builder.append(sub.getFormNo());
////                builder.append(",");
////            }
////            String forms = builder.toString().substring(0, builder.length() - 1);
////            //最后一级审核 直接更新状态
////            String sql = "begin update SYY_OA_FM_HEADER t set t.form_status=3, t.end_date=SYSDATE,t.life_cycle='F',t.provious_approver='评审小组' where t.id in (" + forms + ");";
////
////            sql += " update SYY_OA_FM_APPROVER a set a.approve_status=2,a.app_value=1,a.end_date=SYSDATE,a.app_actor_name='评审小组' where a.form_no in (" + forms + ") and a.approve_status=0; end;";
////
////
////            Query query = getSession().createSQLQuery(sql);
////            query.executeUpdate();
//
////            return ResultFactory.commonSuccess();
//                return subFormOperate(formNo,true,"评审小组",0);
            return ResultFactory.commonSuccess();
        }

        @Override
        protected DeptAppraise getByFormNo (Long formNo){
            return deptAppraiseRepository.getByFormNo(formNo);
        }

        @Override
        public DeptAppraiseDto getFormByFormNo (Long formNo){
            DeptAppraise deptAppraise = this.getByFormNo(formNo);
            DeptAppraiseDto formAppraiseDto = DtoFactory.convert(deptAppraise, new DeptAppraiseDto());

            List<DeptAppraiseDetailDto> deptAppraiseDetailDtos = new ArrayList<>();
            if (deptAppraise.getDetails().size() > 0) {
                for (DeptAppraiseDetail detail : deptAppraise.getDetails()) {
                    DeptAppraiseDetailDto d = new DeptAppraiseDetailDto();
                    d = DtoFactory.convert(detail, d);
                    if (detail.getDeptment()!=null) {
                        d.setDeptId(detail.getDeptment().getId());
                        d.setDeptName(detail.getDeptment().getDeptName());
                        deptAppraiseDetailDtos.add(d);
                    }
                }
            }

            formAppraiseDto.setDetailDtos(deptAppraiseDetailDtos);

            return formAppraiseDto;
        }

        @Override
        public CommonResult downLoadDate (Long formNo){

            DeptAppraise deptAppraise = this.getByFormNo(formNo);

            return null;
        }


        @Override
        public CommonResult updateByList (Long formNo, List < DeptAppraiseDetailDto > dtos){
            DeptAppraise deptAppraise = this.getByFormNo(formNo);
            List<DeptSubAppraise> subAppraises = deptSubAppraiseRepository.getSubAppraiseByParentFormNo(deptAppraise.getFormNo());
            for (DeptAppraiseDetail detail : deptAppraise.getDetails()) {
                for (DeptAppraiseDetailDto d : dtos) {
                    if (detail.getId().equals(d.getId())) {
                        detail = DtoFactory.convert(d, detail);
                    }
                }
                for (DeptSubAppraise deptSubAppraise : subAppraises) {
                    if (deptSubAppraise.getBlongDeptId().equals(detail.getDeptment().getId())) {

                        deptSubAppraise.setGroupGrade(detail.getGroupGrade());
                        deptSubAppraise.setGroupLevel(detail.getGroupLevel());
                        deptSubAppraise.setGroupScores(detail.getGroupScores());
                        deptSubAppraise.setGroupRemark(detail.getGroupRemark());
                    }
                    deptSubAppraiseRepository.save(deptSubAppraise);
                }
            }
            deptAppraiseRepository.save(deptAppraise);
            return ResultFactory.commonSuccess();
        }


    @Override
    public CommonResult doApprove(ApproveFormDto dto,Map<String,Object> param) {

        //部门自评会签
        if(param.get("clStep").toString().equals("2")&&param.get("isStepComplete").toString().equals("false")&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            DeptAppraise appraise = this.getByFormNo(dto.getFormNo());

            DeptAppraiseDetail detail = new DeptAppraiseDetail();
            detail.setDeptLevel(param.get("deptLevel").toString());
            detail.setDeptGrade(param.get("deptGrade").toString());
            detail.setDeptScores(Float.parseFloat(param.get("deptScores").toString()));

            String deptId = param.get("form_deptId").toString();
            String deptName = param.get("form_deptName").toString();
            Deptment deptment = new Deptment();
            deptment.setId(Long.parseLong(deptId));
            deptment.setDeptName(deptName);
            detail.setDeptment(deptment);

            List<DeptAppraiseDetail> list = appraise.getDetails();
            list.add(detail);

            appraise.setDetails(list);

            deptAppraiseRepository.save(appraise);
        }

        //第4步 填写最终考核结果
        if(param.get("clStep").toString().equals("4")&&param.get("isStepComplete").toString().equals("false")&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            DeptAppraise appraise = this.getByFormNo(dto.getFormNo());
            Criteria criteria = getSession().createCriteria(DeptAppraise.class);
            criteria.add(Restrictions.eq("isActive",true));
            criteria.add(Restrictions.eq("id",appraise.getId()));

            DeptAppraise deptAppraise=(DeptAppraise)criteria.uniqueResult();
            if (deptAppraise.getDetails() != null && deptAppraise.getDetails().size()>0){
                for (DeptAppraiseDetail app:deptAppraise.getDetails()){
                    if (app.getGroupLevel()==null || app.getGroupScores()==null || app.getGroupGrade()==null)
                        return ResultFactory.commonError("请填写最终档次、最终得分或最终等级！");

                }
            }
        }

        return super.doApprove(dto,param);
    }

    }
