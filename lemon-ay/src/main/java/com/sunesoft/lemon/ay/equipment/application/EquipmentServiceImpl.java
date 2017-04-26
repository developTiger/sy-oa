package com.sunesoft.lemon.ay.equipment.application;

import com.sunesoft.lemon.ay.equipment.application.Critera.EquipmentCriteria;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentRepository;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentStation;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.DeptmentService;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangkefan on 2016/8/6.
 */
@Service("equipmentService")
public class EquipmentServiceImpl extends GenericHibernateFinder implements EquipmentService {
    @Autowired
    public EquipmentRepository equipmentRepository;

    @Autowired
    DeptmentService deptmentService;

    @Override
    public CommonResult addOrUpEquipment(EquipmentDto equipmentDto) {

//        Equipment equipment = DtoFactory.convert(equipmentDto, new Equipment());//todo 这段代码在修改信息的时候有问题
        Equipment e=equipmentRepository.getByNum(equipmentDto.getPropertyNum());
        Equipment equipment = null;
        if (equipmentDto.getId() == null) {
            //这里是新增

            if(e!=null&&e.getIsActive()){
                return ResultFactory.commonError("该资产编号的设备已经存在！");
            }
            equipment = DtoFactory.convert(equipmentDto, new Equipment());
        } else {
            //这里是修改
            equipment = equipmentRepository.get(equipmentDto.getId());
            if (equipment == null || !equipment.getIsActive()) {
                return ResultFactory.commonError("该设备不存在");
            } else {
                if(e!=null&&e.getIsActive()&&!e.getId().equals(equipmentDto.getId())){
                    return ResultFactory.commonError("修改后的资产编号已使用！");
                }
                equipment = DtoFactory.convert(equipmentDto, equipment);
            }
        }

        return ResultFactory.commonSuccess(equipmentRepository.save(equipment));
    }

    @Override
    public EquipmentDto getEquipById(Long id) {
        Equipment equipment = equipmentRepository.get(id);
        if (equipment != null && equipment.getIsActive())
            return DtoFactory.convert(equipment, new EquipmentDto());
        else return new EquipmentDto();
    }

    @Override
    public EquipmentDto getEquipByNun(String propertyNum) {
        Equipment e=equipmentRepository.getByNum(propertyNum);
        if(e!=null&&e.getIsActive()){
            return DtoFactory.convert(e,new EquipmentDto());
        }
        return null;
    }

    @Override
    public CommonResult deleteOne(Long id) {
        Equipment equipment = equipmentRepository.get(id);
        if (equipment != null && equipment.getIsActive()) {
            equipment.setIsActive(false);
            equipment.setLastUpdateTime(new Date());
            return ResultFactory.commonSuccess(equipmentRepository.save(equipment));
        }
        return ResultFactory.commonError("该设备不存在！");
    }

    @Override
    public CommonResult deleteMore(List<Long> ids) {
        if(ids!=null&&ids.size()>0){
            //这里不要多次获取数据,与数据库的链接很耗时
            Criteria criteria=getSession().createCriteria(Equipment.class);
            criteria.add(Restrictions.eq("isActive",true));
            criteria.add(Restrictions.in("id",ids));
            List<Equipment> list=criteria.list();
            if(list!=null&&list.size()>0){
                for (Equipment e:list){
                    e.setIsActive(false);
                    e.setLastUpdateTime(new Date());
                    equipmentRepository.save(e);
                }
                return ResultFactory.commonSuccess();
            }
            return ResultFactory.commonError("这些设备已经不存在!");

        }
        return ResultFactory.commonError("请选择设备！");
    }

    /**
     * 修改设备的状态：正常，维修中，已报废
     *
     * @param id
     * @param station
     * @return
     */
    @Override
    public CommonResult setStation(Long id, EquipmentStation station) {
        Equipment equipment = equipmentRepository.get(id);
        if (equipment != null && equipment.getIsActive()) {
            equipment.setCurrentStation(station);//设备状态的修改
            return ResultFactory.commonSuccess(equipmentRepository.save(equipment));//保存并且返回值
        }
        return ResultFactory.commonError("该设备不存在");
    }

    @Override
    public CommonResult editBySystemNumber(EquipmentDto dto) {
        String propertyNum = dto.getPropertyNum();
        if (StringUtils.isNullOrWhiteSpace(propertyNum) || propertyNum.contains("."))
            return ResultFactory.commonError("资产编号格式不正确，必须为文本格式");
        Equipment newEquipment = DtoFactory.convert(dto, new Equipment());
        Object object = equipmentRepository.getByNum(propertyNum);
        Equipment existEquipment = null == object ? null : (Equipment) object;
        List<DeptmentDto> deptmentDtos = deptmentService.getDeptsByName(dto.getUnitName());
        if (deptmentDtos == null || deptmentDtos.size() == 0)
            return ResultFactory.commonError("仪器设备编号：" + dto.getPropertyNum() + " 对应的部门名称：" + dto.getUnitName() + " 与系统部门名称不匹配，请在上传文件中更新使用单位！");
        if (existEquipment != null) {
            existEquipment.setUnitId(deptmentDtos.get(0).getId().toString());
            DtoFactory.convert(newEquipment, existEquipment);
            return ResultFactory.commonSuccess(equipmentRepository.save(existEquipment));

        } else {
            newEquipment.setUnitId(deptmentDtos.get(0).getId().toString());
            return ResultFactory.commonSuccess(equipmentRepository.save(newEquipment));

        }
    }

    @Override
    public List<EquipmentDto> getAll() {

        Criteria criteria = getSession().createCriteria(Equipment.class);
        criteria.add(Restrictions.eq("isActive", true));
        List<Equipment> equipments = criteria.list();
        List<EquipmentDto> equipmentDtos = new ArrayList<>();
        if (equipments != null && equipments.size() > 0) {
            for (Equipment e : equipments) {
                equipmentDtos.add(DtoFactory.convert(e, new EquipmentDto()));
            }
        }
        return equipmentDtos;
    }

    @Override
    public List<EquipmentDto> getRepairAll(EquipmentStation station) {

        Criteria criteria = getSession().createCriteria(Equipment.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("currentStation", station));
        List<Equipment> equipments = criteria.list();
        List<EquipmentDto> equipmentDtos = new ArrayList<>();
        if (equipments != null && equipments.size() > 0) {
            for (Equipment e : equipments) {
                equipmentDtos.add(DtoFactory.convert(e, new EquipmentDto()));
            }
        }
        return equipmentDtos;
    }

    @Override
    public PagedResult<EquipmentDto> getPageEquipmentDto(EquipmentCriteria equipmentCriteria) {
        Criteria criteria = getSession().createCriteria(Equipment.class);
        criteria.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(equipmentCriteria.getBeginDate())) {
            criteria.add(Restrictions.ge("nextTestTime", DateHelper.parse(equipmentCriteria.getBeginDate())));
        }
        if (!StringUtils.isNullOrWhiteSpace(equipmentCriteria.getEndDate())) {
            criteria.add(Restrictions.le("nextTestTime", DateHelper.parse(equipmentCriteria.getEndDate())));
        }
        if (equipmentCriteria.getCurrentStation() != null)
            criteria.add(Restrictions.eq("currentStation", equipmentCriteria.getCurrentStation()));
        if (equipmentCriteria.getTestResult() != null)
            criteria.add(Restrictions.eq("testResult", equipmentCriteria.getTestResult()));
        if (!StringUtils.isNullOrWhiteSpace(equipmentCriteria.getMeasuringName()))
            criteria.add(Restrictions.like("measuringName", "%" + equipmentCriteria.getMeasuringName() + "%"));
        if (!StringUtils.isNullOrWhiteSpace(equipmentCriteria.getPropertyNum()))
            criteria.add(Restrictions.like("propertyNum", "%" + equipmentCriteria.getPropertyNum() + "%"));


        if (equipmentCriteria.getDeptId() != null)
            criteria.add(Restrictions.eq("unitId", equipmentCriteria.getDeptId()));


        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((equipmentCriteria.getPageNumber() - 1) * equipmentCriteria.getPageSize()).setMaxResults(equipmentCriteria.getPageSize());
        List<Equipment> beans = criteria.list();
        List<EquipmentDto> list = new ArrayList<>();
        for (Equipment equipment : beans) {
            list.add(com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory.convert(equipment, new EquipmentDto()));
        }
        return new PagedResult<EquipmentDto>(list, equipmentCriteria.getPageNumber(), equipmentCriteria.getPageSize(), totalCount);
    }


}
