package com.sunesoft.lemon.ay.dateCheck.application;

import com.sunesoft.lemon.ay.dateCheck.application.Criteria.DateCheckCriteria;
import com.sunesoft.lemon.ay.dateCheck.application.dateCheckFactory.DateChechFactory;
import com.sunesoft.lemon.ay.dateCheck.application.dtos.DateCheckDto;
import com.sunesoft.lemon.ay.dateCheck.domain.DateCheck;
import com.sunesoft.lemon.ay.dateCheck.domain.DateCheckRepository;
import com.sunesoft.lemon.ay.equipment.application.EquipmentService;
import com.sunesoft.lemon.ay.equipment.application.dtos.EquipmentDto;
import com.sunesoft.lemon.ay.equipment.domain.Equipment;
import com.sunesoft.lemon.ay.equipment.domain.EquipmentRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by xiazl on 2016/10/21.
 */
@Service("dateCheckService")
public class DateCheckServiceImpl extends GenericHibernateFinder implements DateCheckService {
    @Autowired
    DateCheckRepository repository;

    @Autowired
    EquipmentService equipmentService;
    @Override
    public CommonResult add(DateCheckDto dto) {
        dto=perfectDto(dto);
        if(dto==null) return ResultFactory.commonError("该设备不存在！");
        DateCheck check = new DateCheck();
        check = DateChechFactory.convertDate(dto, check);
        return ResultFactory.commonSuccess(repository.save(check));
    }

    @Override
    public CommonResult update(DateCheckDto dto) {
        dto=perfectDto(dto);
        if(dto==null) return ResultFactory.commonError("该设备不存在！");
        DateCheck check = repository.get(dto.getId());
        if (check == null) return ResultFactory.commonError("修改的信息不存在");
        check = DateChechFactory.convertDate(dto, check);
        return ResultFactory.commonSuccess(repository.save(check));
    }

    @Override
    public DateCheckDto getById(Long id) {
        DateCheck check = repository.get(id);
        if (check == null) return null;
        return DateChechFactory.convertDto(check);
    }

    @Override
    public CommonResult deletes(List<Long> delIds) {
        Criteria criteria = getSession().createCriteria(DateCheck.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.in("id", delIds));
        List<DateCheck> list = criteria.list();
        if (list != null && list.size() > 0) {
            for (DateCheck dateCheck : list) {
                dateCheck.setIsActive(false);
                dateCheck.setLastUpdateTime(new Date());
                repository.save(dateCheck);
            }
        }
        return new CommonResult(false, "您要删除的对象已经不存在");
    }

    @Override
    public CommonResult delete(Long id) {
        DateCheck check = repository.get(id);
        if (check != null) {
            check.setIsActive(false);
            check.setLastUpdateTime(new Date());
            return ResultFactory.commonSuccess(repository.save(check));
        }
        return ResultFactory.commonError("您要删除的对象已经不存在");
    }


    @Override
    public List<DateCheckDto> getByName(String equpname) {
        Criteria criteria = getSession().createCriteria(DateCheck.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("equipName", equpname));
        List<DateCheck> list = criteria.list();
        if (list == null || list.size() == 0) return Collections.EMPTY_LIST;
        else
            return DateChechFactory.convertList(list);
    }

    @Override
    public PagedResult<DateCheckDto> paged(DateCheckCriteria checkCriteria) {
        PagedResult<DateCheck> pg = repository.page(checkCriteria);
        return DateChechFactory.convertPage(pg);
    }

    /**
     * 完善DateCheckDto数据
     * @param dto
     * @return
     */
    private DateCheckDto perfectDto(DateCheckDto dto){
       EquipmentDto equipmentDto=equipmentService.getEquipById(dto.getEquipId());
        if(equipmentDto==null)return null;
        dto.setEquipName(equipmentDto.getMeasuringName());
        dto.setFactoryName(equipmentDto.getFactoryName());
        dto.setOutFactoryNum(equipmentDto.getOutFactoryNum());
        dto.setStandard(equipmentDto.getStandard());
        if(equipmentDto.getProductTime()!=null) {
            dto.setSuseTime(DateHelper.formatDate(equipmentDto.getProductTime(),"yyyy-MM-dd"));
        }
        return dto;
    }
}
