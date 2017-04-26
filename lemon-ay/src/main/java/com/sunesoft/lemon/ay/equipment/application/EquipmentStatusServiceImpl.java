package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentStatusDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentRepository;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatus;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStatusRepository;
import com.sunesoft.lemon.ay.equipment.domain.hibernate.EquipmentRepositoryImpl;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangkefan on 2016/8/8.
 */
@Service("equipmentStatusService")
public class EquipmentStatusServiceImpl extends GenericHibernateRepository<EquipmentStatusDto,Long> implements EquipmentStatusService{

    @Autowired
    EquipmentStatusRepository equipmentStatusRepository;

    @Override
    public CommonResult addOrUpEquipmentStatus(EquipmentStatusDto equipmentStatusDto) {

        EquipmentStatus equipmentStatus =  DtoFactory.convert(equipmentStatusDto, new EquipmentStatus());

        return ResultFactory.commonSuccess(equipmentStatusRepository.save(equipmentStatus));
    }

    @Override
    public EquipmentStatus getByResId(Long resId) {
        EquipmentStatus e  = equipmentStatusRepository.getByResID(resId);
        return e;
    }
}
