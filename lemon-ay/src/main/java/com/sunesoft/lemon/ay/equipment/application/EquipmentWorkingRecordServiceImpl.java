package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentWorkingRecordCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentWorkingRecordDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecord;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecordRepository;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormInnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormInnovationAchievement;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/10/21.
 */
@Service("equipmentWorkingRecordService")
public class EquipmentWorkingRecordServiceImpl extends GenericHibernateFinder implements EquipmentWorkingRecordService {

    @Autowired
    EquipmentWorkingRecordRepository equipmentWorkingRecordRepository;

    @Override
    public CommonResult addOrUpdateEquipWorkRecord(EquipmentWorkingRecordDto dto) {

        if (dto.getId() != null){
            EquipmentWorkingRecord workingRecord=this.getById(dto.getId());
            workingRecord.setTime(dto.getTime());
            workingRecord.setWorkContent(dto.getWorkContent());
            workingRecord.setSampleCount(dto.getSampleCount());
            workingRecord.setWorkingTime(dto.getWorkingTime());
            workingRecord.setCheckAndWorkingSituation(dto.getCheckAndWorkingSituation());
            workingRecord.setRemark(dto.getRemark());
            equipmentWorkingRecordRepository.save(workingRecord);
        }else{
            EquipmentWorkingRecord equipmentWorkingRecord = DtoFactory.convert(dto,new EquipmentWorkingRecord());
            equipmentWorkingRecordRepository.save(equipmentWorkingRecord);
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<EquipmentWorkingRecordDto> getPagesByequipWorkRecord(EquipmentWorkingRecordCriteria recordCriteria,String equipmentId) {
        Criteria criteria = getSession().createCriteria(EquipmentWorkingRecord.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("equipmentId",equipmentId));

//        if (!StringUtils.isNullOrWhiteSpace(achievementCriteria.getProjectName()))
//            criteria.add(Restrictions.like("projectName","%"+achievementCriteria.getProjectName()+"%"));
//        if (!StringUtils.isNullOrWhiteSpace(achievementCriteria.getDeptName()))
//            criteria.add(Restrictions.eq("applyUnit", achievementCriteria.getProjectName()));
//        if (achievementCriteria.getEducationDegree()!= null)
//            criteria.add(Restrictions.eq("educationDegree", achievementCriteria.getEducationDegree()));
//
        if (recordCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("time",recordCriteria.getBeginTime()));
        if (recordCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("time", recordCriteria.getEndTime()));
//
//        //添加formStatus字段查询，查询出待签核的表单
//        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
//        criteria.add(Restrictions.eq("clStep",Integer.parseInt(achievementCriteria.getStep())));

        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((recordCriteria.getPageNumber() - 1) * recordCriteria.getPageSize()).setMaxResults(recordCriteria.getPageSize());
        List<EquipmentWorkingRecord> beans = criteria.list();
        List<EquipmentWorkingRecordDto> list = new ArrayList<>();
        Float countTime=0.0F;
        Integer sampleCount = 0;

        for (EquipmentWorkingRecord wp:beans){
            countTime += wp.getWorkingTime();
            sampleCount += wp.getSampleCount();
        }
        for (EquipmentWorkingRecord wp:beans){
            EquipmentWorkingRecordDto workingRecordDto = new EquipmentWorkingRecordDto();
            workingRecordDto.setWorkingTimeCount(countTime);
            workingRecordDto.setAllSample(sampleCount);
            list.add(DtoFactory.convert(wp, workingRecordDto));
        }
        return new PagedResult<EquipmentWorkingRecordDto>(list, recordCriteria.getPageNumber(), recordCriteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult delete(Long id) {
        equipmentWorkingRecordRepository.delete(id);
        return ResultFactory.commonSuccess();
    }

    @Override
    public EquipmentWorkingRecord getById(Long id) {
        EquipmentWorkingRecord record=equipmentWorkingRecordRepository.get(id);
        return record;
    }
}
