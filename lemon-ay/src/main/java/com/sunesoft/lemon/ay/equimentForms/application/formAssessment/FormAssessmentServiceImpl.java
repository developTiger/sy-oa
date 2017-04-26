package com.sunesoft.lemon.ay.equimentForms.application.formAssessment;

import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessContentDto;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormEquipmentDto;
import com.sunesoft.lemon.ay.equimentForms.application.formEquiment.FormEquimentService;
import com.sunesoft.lemon.ay.equimentForms.domain.FormAssessContent;
import com.sunesoft.lemon.ay.equimentForms.domain.FormAssessment;
import com.sunesoft.lemon.ay.equimentForms.domain.FormAssessmentRepository;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipmentRepository;
import com.sunesoft.lemon.ay.equipment.application.Critera.AssessContentCritera;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormAssessmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equipment.domain.*;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangkefan on 2016/8/18.
 */
@Service("formAssessmentService")
public class FormAssessmentServiceImpl extends FormBase2<FormAssessment, FormAssessmentDto> implements FormAssessmentService {

    @Autowired
    private FormAssessmentRepository formAssessmentRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private FormEquimentService formEquimentService;

    @Override
    protected CommonResult save(FormAssessment formAssessment) {
        formAssessmentRepository.save(formAssessment);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormAssessment formAssessment) {
        FormAssessment f = this.getByFormNo(formAssessment.getFormNo());
        f.setName(formAssessment.getName());//设备名称
        f.setAssessType(formAssessment.getAssessType());//评估类型
        f.setEquipmentId(formAssessment.getEquipmentId());//设备id
//        f.setStandard(formAssessment.getStandard());//规格
//        f.setOutFactoryNum(formAssessment.getOutFactoryNum());//出厂编号
//        f.setAssetNum(formAssessment.getAssetNum());//资产编码
//        f.setInvestDate(formAssessment.getInvestDate());//投产日期
//        f.setAmmunition(formAssessment.getAmmunition());//存放地点
//        f.setProductUse(formAssessment.getProductUse());//产品或者用途
//        f.setFactory(formAssessment.getFactory());//生产厂家
//        f.setOriginal(formAssessment.getOriginal());//原值
//        f.setUseUnit(formAssessment.getUseUnit());//设备使用单位
//        f.setManagerDept(formAssessment.getManagerDept());//设备管理部门
        formAssessmentRepository.save(f);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormAssessment ConvetDto(FormAssessmentDto Dto) {
        FormAssessment formAssessment = new FormAssessment();
        formAssessment = DtoFactory.convert(Dto, formAssessment);
        formAssessment.setFormAssessContents(null);

        if (Dto.getEquipmentDto() != null)
            formAssessment.setEquipmentId(Dto.getEquipmentDto().getId());
        return formAssessment;
    }

    @Override
    protected String getSummery(FormAssessment formAssessment) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormAssessment formAssessment = this.getByFormNo(formNo);
        Assessment fa = DtoFactory.convert(formAssessment, new Assessment());
        List<AssessContent> contents = new ArrayList<>();
        for (FormAssessContent c : formAssessment.getFormAssessContents()) {
            AssessContent content = DtoFactory.convert(c, new AssessContent());
            content.setId(null);
            contents.add(content);

        }
        fa.setId(null);
        fa.setAssessContents(contents);
        assessmentRepository.save(fa);
        if (formAssessment.getAssessType().equals("1")) {
            CommonResult r = equipmentService.setStation(formAssessment.getEquipmentId(), EquipmentStation.Scrap);
            if (!r.getIsSuccess()) return ResultFactory.commonError("设备状态更新失败");

        }
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormAssessment getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormAssessment.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo", formNo));
        return (FormAssessment) criteria.uniqueResult();
    }

    @Override
    public FormAssessmentDto getFormByFormNo(Long formNo) {
        FormAssessment f = this.getByFormNo(formNo);
        FormAssessmentDto dto = DtoFactory.convert(f, new FormAssessmentDto());
        List<AssessContentDto> list = new ArrayList<>();
        if (f.getFormAssessContents() != null && f.getFormAssessContents().size() > 0) {
            for (FormAssessContent a : f.getFormAssessContents()) {
                AssessContentDto assessContentDto = DtoFactory.convert(a, new AssessContentDto());
                assessContentDto.setFormNo(f.getFormNo());
                list.add(assessContentDto);
            }
        }
        dto.setAssessContentDtos(list);

        EquipmentDto equipmentDto = DtoFactory.convert(equipmentRepository.get(f.getEquipmentId()), new EquipmentDto());
        dto.setEquipmentDto(equipmentDto);

        return dto;
    }

    @Override
    public CommonResult addOrUpdateContent(FormAssessContentDto dto) {

        FormAssessment as = this.getByFormNo(dto.getFormNo());
        if (dto.getEquipmentDto().getId() != null)
            as.setEquipmentId(dto.getEquipmentDto().getId());
        if (as != null) {
            if (dto.getId() != null && dto.getId() > 0) {
                for (FormAssessContent c : as.getFormAssessContents()) {
                    if (c.getId().equals(dto.getId())) {
                        c.setParameterName(dto.getParameterName());
                        c.setParameterRange(dto.getParameterRange());
                        c.setTestValue(dto.getTestValue());
                        c.setConform(dto.getConform());
                        c.setTestCrew(dto.getTestCrew());
                        c.setSuggest(dto.getSuggest());
                        c.setImplement(dto.getImplement());
                    }
                }
            } else {
                FormAssessContent formAssessContent = new FormAssessContent();
                formAssessContent.setEquipmentId(dto.getEquipmentDto().getId());
                as.getFormAssessContents().add(DtoFactory.convert(dto, formAssessContent));
            }
            formAssessmentRepository.save(as);
            return ResultFactory.commonSuccess(dto.getFormNo());
        }
        return ResultFactory.commonError("评估数据不存在！");
    }

    @Override
    public CommonResult delContent(Long formNo, Long id) {
        FormAssessment as = this.getByFormNo(formNo);
        for (FormAssessContent c : as.getFormAssessContents()) {
            if (c.getId().equals(id)) {
                as.getFormAssessContents().remove(c);
                break;
            }
        }
        formAssessmentRepository.save(as);
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<FormAssessContentDto> getAllContent(Long formNo) {
        FormAssessment as = this.getByFormNo(formNo);
        List<FormAssessContentDto> list = new ArrayList<>();
        for (FormAssessContent f : as.getFormAssessContents()) {
            FormAssessContentDto dto = DtoFactory.convert(f, new FormAssessContentDto());
            dto.setAssmentId(as.getId());
            list.add(dto);
        }
        return list;
    }

    @Override
    public FormAssessContentDto getContentById(Long contentId) {
        Criteria criteria = getSession().createCriteria(FormAssessContent.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("id", contentId));
        FormAssessContent fs = (FormAssessContent) criteria.uniqueResult();
        return DtoFactory.convert(fs, new FormAssessContentDto());
    }

    @Override
    public List<FormAssessmentDto> getByEquipmentId(Long id) {
        List<FormAssessment> formAssessments=formAssessmentRepository.getByEquipmentId(id);
        List<FormAssessmentDto> list = new ArrayList<>();
        for (FormAssessment formAssessmentDto:formAssessments){
            List<AssessContentDto> assessContents = new ArrayList<>();
            for (FormAssessContent content:formAssessmentDto.getFormAssessContents()){
                assessContents.add(DtoFactory.convert(content,new AssessContentDto()));
            }

            FormAssessmentDto assessmentDto = DtoFactory.convert(formAssessmentDto,new FormAssessmentDto());
            assessmentDto.setAssessContentDtos(assessContents);

            list.add(assessmentDto);

        }
        return list;
    }

    @Override
    public PagedResult<AssessmentDto> getPages(AssessContentCritera contentCritera, Long equipmentId) {
        Criteria criteria = getSession().createCriteria(Assessment.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("equipmentId",equipmentId));
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((contentCritera.getPageNumber() - 1) * contentCritera.getPageSize()).setMaxResults(contentCritera.getPageSize());
        List<Assessment> beans = criteria.list();
        List<AssessmentDto> list = new ArrayList<>();
        List<AssessContent> contentDtos = new ArrayList<>();
        for(Assessment assessment : beans){
            if (assessment.getAssessContents() != null){
                for (AssessContent dto:assessment.getAssessContents()){
                    contentDtos.add(dto);
                }
            }
            AssessmentDto assessmentDto = DtoFactory.convert(assessment,new AssessmentDto());
            assessmentDto.setAssessContents(contentDtos);
            list.add(DtoFactory.convert(assessment, new AssessmentDto()));
        }
        return new PagedResult<AssessmentDto>(list, contentCritera.getPageNumber(), contentCritera.getPageSize(), totalCount);
    }


    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
        String clStep = param.get("clStep").toString();
        String isStepComplete = param.get("isStepComplete").toString();

        if (clStep.equals("2") && isStepComplete.equals("false")) {
            FormAssessment as = this.getByFormNo(dto.getFormNo());
            String judgeContent = param.get("judgeContent").toString();
            as.setAssessResult(judgeContent);
            formAssessmentRepository.save(as);//将评估的结果储存到数据库中
        }
        return super.doApprove(dto, param);
    }


}
