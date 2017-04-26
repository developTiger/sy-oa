package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentMaintenanceCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentMaintenanceDto;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenance;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentMaintenanceRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/6.
 */
@Service("equipmentMaintenanceService")
public class EquipmentMaintenanceServiceImpl extends GenericHibernateRepository<EquipmentMaintenanceDto,Long> implements  EquipmentMaintenanceService{

    @Autowired
    EquipmentMaintenanceRepository equipmentMaintenanceRepository;



    @Override
    public CommonResult addOrUpEquipmentMaintenance(EquipmentMaintenanceDto equipmentMaintenanceDto) {

        EquipmentMaintenance equipmentMaintenance = DtoFactory.convert(equipmentMaintenanceDto,new EquipmentMaintenance());

        return ResultFactory.commonSuccess(equipmentMaintenanceRepository.save(equipmentMaintenance));
    }

    @Override
    public List<EquipmentMaintenance> getAll() {
         return equipmentMaintenanceRepository.getAll();
    }

    @Override
    public EquipmentMaintenanceDto getById(Long id){
        EquipmentMaintenance equipmentMaintenance =  equipmentMaintenanceRepository.get(id);
        EquipmentMaintenanceDto equipmentMaintenanceDto = DtoFactory.convert(equipmentMaintenance,new EquipmentMaintenanceDto());
        equipmentMaintenanceDto.setApplyPerson(equipmentMaintenance.getApplyPerson());
        equipmentMaintenanceDto.setLinkId(equipmentMaintenance.getLinkId());
        return equipmentMaintenanceDto;
    }

    @Override
    public PagedResult<EquipmentMaintenanceDto> getPageEquipmentMaintenance(EquipmentMaintenanceCriteria equipmentMaintenanceCriteria,Long equipmentId) {
        Criteria criteria = getSession().createCriteria(EquipmentMaintenance.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("equipment.id",equipmentId));
        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((equipmentMaintenanceCriteria.getPageNumber() - 1) * equipmentMaintenanceCriteria.getPageSize()).setMaxResults(equipmentMaintenanceCriteria.getPageSize());
        List<EquipmentMaintenance> beans = criteria.list();
        List<EquipmentMaintenanceDto> list = new ArrayList<>();
        for(EquipmentMaintenance equipmentMaintenance : beans){
            EquipmentMaintenanceDto dto = DtoFactory.convert(equipmentMaintenance, new EquipmentMaintenanceDto());
            dto.setApplyPerson(equipmentMaintenance.getApplyPerson());
            list.add(dto);
        }
        return new PagedResult<>(list, equipmentMaintenanceCriteria.getPageNumber(), equipmentMaintenanceCriteria.getPageSize(), totalCount);
    }

    @Override
    public List<EquipmentMaintenanceDto> getAllByEquipmentId(Long equipmentId) {
        List<EquipmentMaintenance> list = equipmentMaintenanceRepository.getAllByEquipmentId(equipmentId);

        List<EquipmentMaintenanceDto> dtos= new ArrayList<>();
        for(EquipmentMaintenance e:list){
            EquipmentMaintenanceDto dto=DtoFactory.convert(e, new EquipmentMaintenanceDto());
            dto.setApplyPerson(e.getApplyPerson());
            // dto.setEquipmentDto(DtoFactory.convert(e.getEquipment(), new EquipmentDto()));
            dtos.add(dto);
        }
        return dtos;
    }
}
