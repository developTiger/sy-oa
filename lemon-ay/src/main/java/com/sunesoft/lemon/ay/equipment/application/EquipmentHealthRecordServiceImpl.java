package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentHealthRecordCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentHealthRecordDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentWorkingRecordDto;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentHealthRecord;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentHealthRecordRepository;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentWorkingRecord;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
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
@Service("equipmentHealthRecordService")
public class EquipmentHealthRecordServiceImpl extends GenericHibernateFinder implements EquipmentHealthRecordService {

    @Autowired
    EquipmentHealthRecordRepository equipmentHealthRecordRepository;

    @Override
    public CommonResult addOrUpdateEquipWorkHealth(EquipmentHealthRecordDto dto) {
        if (dto.getId() != null){
            EquipmentHealthRecord workingRecord=this.getById(dto.getId());
            workingRecord.setTime(dto.getTime());
            workingRecord.setPosition(dto.getPosition());
            workingRecord.setChangeSituation(dto.getChangeSituation());
            workingRecord.setResult(dto.getResult());
            workingRecord.setQuestion(dto.getQuestion());
            workingRecord.setStopMachineTime(dto.getStopMachineTime());
            equipmentHealthRecordRepository.save(workingRecord);
        }else{
            EquipmentHealthRecord equipmentWorkingRecord = DtoFactory.convert(dto, new EquipmentHealthRecord());
            equipmentHealthRecordRepository.save(equipmentWorkingRecord);
        }
        return ResultFactory.commonSuccess();
    }

    @Override
    public PagedResult<EquipmentHealthRecordDto> getPagesByequipWorkHealth(EquipmentHealthRecordCriteria recordCriteria,String equipmentId) {
        Criteria criteria = getSession().createCriteria(EquipmentHealthRecord.class);
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
        List<EquipmentHealthRecord> beans = criteria.list();
        List<EquipmentHealthRecordDto> list = new ArrayList<>();
        for (EquipmentHealthRecord wp:beans){
            list.add(DtoFactory.convert(wp, new EquipmentHealthRecordDto()));
        }
        return new PagedResult<EquipmentHealthRecordDto>(list, recordCriteria.getPageNumber(), recordCriteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult delete(Long ids) {
        equipmentHealthRecordRepository.delete(ids);
        return ResultFactory.commonSuccess();
    }

    @Override
    public EquipmentHealthRecord getById(Long id) {
        EquipmentHealthRecord record=equipmentHealthRecordRepository.get(id);
        return record;
    }
}
