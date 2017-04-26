package com.sunesoft.lemon.syms.hrForms.application.formDeptSubAppraise;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.dept.DeptmentRepository;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.DeptSubAppraiseDto;
import com.sunesoft.lemon.syms.hrForms.domain.*;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("formDeptSubAppraiseService")
public class FormDeptSubAppraiseServiceImpl extends FormBase2<DeptSubAppraise,DeptSubAppraiseDto> implements FormDeptSubAppraiseService {

    @Autowired
    DeptSubAppraiseRepository deptSubAppraiseRepository;

    @Autowired
    DeptmentRepository deptmentRepository;

    @Autowired
    DeptAppraiseRepository deptAppraiseRepository;

    @Override
    protected CommonResult save(DeptSubAppraise deptSubAppraise) {
        deptSubAppraiseRepository.save(deptSubAppraise);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(DeptSubAppraise deptSubAppraise) {
        return null;
    }

    @Override
    protected DeptSubAppraise ConvetDto(DeptSubAppraiseDto dto) {
        DeptSubAppraise deptSubAppraise = new DeptSubAppraise();
        deptSubAppraise=    DtoFactory.convert(dto,deptSubAppraise);
        return deptSubAppraise;
    }

    @Override
    protected String getSummery(DeptSubAppraise deptSubAppraise) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {

        DeptSubAppraise subAppraise= getByFormNo(formNo);
        DeptAppraise appraise= deptAppraiseRepository.getByFormNo(subAppraise.getParentFormNo());

        DeptAppraiseDetail appraiseDetail = new DeptAppraiseDetail();
        appraiseDetail.setDeptment(deptmentRepository.get(subAppraise.getBlongDeptId()));
        appraiseDetail.setDeptScores(subAppraise.getDeptScores());
        appraiseDetail.setDeptLevel(subAppraise.getDeptLevel());
        appraiseDetail.setDeptGrade(subAppraise.getDeptGrade());
        appraiseDetail.setDeptRemark(subAppraise.getRemark());
        appraise.getDetails().add(appraiseDetail);
        deptAppraiseRepository.save(appraise);
        return ResultFactory.commonSuccess();
     }

    @Override
    protected DeptSubAppraise getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(DeptSubAppraise.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo", formNo));
        return (DeptSubAppraise)criterion.uniqueResult();
    }

    @Override
    public DeptSubAppraiseDto getFormByFormNo(Long formNo) {
        DeptSubAppraise deptSubAppraise=this.getByFormNo(formNo);
        DeptSubAppraiseDto formAppraiseDto= DtoFactory.convert(deptSubAppraise, new DeptSubAppraiseDto());
        return formAppraiseDto;
    }


    @Override
    public CommonResult doApprove(ApproveFormDto dto,Map<String,Object> param) {

        //部门自评
        if(param.get("clStep").toString().equals("1")&&param.get("isStepComplete").toString().equals("false")&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            DeptSubAppraise appraise = this.getByFormNo(dto.getFormNo());
            appraise.setDeptLevel(param.get("deptLevel").toString());
            appraise.setDeptGrade(param.get("deptGrade").toString());
            appraise.setDeptScores(Float.parseFloat(param.get("deptScores").toString()));
//            appraise.setRemark(param.get("remark").toString());
            appraise.setRemark(param.get("content").toString());
            deptSubAppraiseRepository.save(appraise);
        }

        //领导审核结束，数据汇总到主表单
        if(param.get("clStep").toString().equals("2")&&param.get("isStepComplete").toString().equals("false")&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            DeptSubAppraise subAppraise= getByFormNo(dto.getFormNo());
            DeptAppraise appraise= deptAppraiseRepository.getByFormNo(subAppraise.getParentFormNo());

            DeptAppraiseDetail appraiseDetail = new DeptAppraiseDetail();
            appraiseDetail.setDeptment(deptmentRepository.get(subAppraise.getBlongDeptId()));
            appraiseDetail.setDeptScores(subAppraise.getDeptScores());
            appraiseDetail.setDeptLevel(subAppraise.getDeptLevel());
            appraiseDetail.setDeptGrade(subAppraise.getDeptGrade());
            appraiseDetail.setDeptRemark(subAppraise.getRemark());
            appraise.getDetails().add(appraiseDetail);
            deptAppraiseRepository.save(appraise);
        }
        if(param.get("clStep").toString().equals("3")&&param.get("isStepComplete").toString().equals("false")&&dto.getAppValue().equals(AppValue.Y.ordinal())) {


        }
        return super.doApprove(dto,param);
    }

}
