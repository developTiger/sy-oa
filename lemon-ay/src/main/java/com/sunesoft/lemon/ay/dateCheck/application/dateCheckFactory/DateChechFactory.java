package com.sunesoft.lemon.ay.dateCheck.application.dateCheckFactory;


import com.sunesoft.lemon.ay.dateCheck.application.dtos.DateCheckDto;
import com.sunesoft.lemon.ay.dateCheck.domain.DateCheck;
import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.fr.results.PagedCriteria;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiazl on 2016/10/21.
 */
public class DateChechFactory extends DtoFactory {

    /**
     * 转化成dto
     *
     * @param dateCheck
     * @return
     */
    public static DateCheckDto convertDto(DateCheck dateCheck) {
        DateCheckDto dto = new DateCheckDto();
        dto = convert(dateCheck, dto);
        if (dateCheck.getLastUpdateTime() != null)
            dto.setSlastUpdateTime(DateHelper.formatDate(dateCheck.getLastUpdateTime(), "yyyy-MM-dd"));
        if (dateCheck.getCreateDateTime() != null)
            dto.setScreateDateTime(DateHelper.formatDate(dateCheck.getCreateDateTime(), "yyyy-MM-dd"));
        if (dateCheck.getCheckTime() != null)
            dto.setScheckTime(DateHelper.formatDate(dateCheck.getCheckTime(), "yyyy-MM-dd"));
        if (dateCheck.getHeadTime() != null)
            dto.setSheadTime(DateHelper.formatDate(dateCheck.getHeadTime(), "yyyy-MM-dd"));
        if (dateCheck.getUseTime() != null)
            dto.setSuseTime(DateHelper.formatDate(dateCheck.getUseTime(), "yyyy-MM-dd"));
        if (dateCheck.getPersonTime() != null)
            dto.setSpersonTime(DateHelper.formatDate(dateCheck.getPersonTime(), "yyyy-MM-dd"));
        return dto;
    }

    /**
     * dto转化成实体类
     * @param dto
     * @param dateCheck
     * @return
     */
    public static DateCheck convertDate(DateCheckDto dto, DateCheck dateCheck) {
        dateCheck = convert(dto, dateCheck);
        if (!StringUtils.isNullOrWhiteSpace(dto.getSlastUpdateTime()))
            dateCheck.setLastUpdateTime(DateHelper.parse(dto.getSlastUpdateTime(), "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(dto.getScheckTime()))
            dateCheck.setCheckTime(DateHelper.parse(dto.getScheckTime(), "yyyy-MM-dd"));
        if (!StringUtils.isNullOrWhiteSpace(dto.getSuseTime()))
            dateCheck.setUseTime(DateHelper.parse(dto.getSuseTime(), "yyyy-MM-dd"));
        if(!StringUtils.isNullOrWhiteSpace(dto.getSpersonTime()))
            dateCheck.setPersonTime(DateHelper.parse(dto.getSpersonTime(),"yyyy-MM-dd"));
        if(!StringUtils.isNullOrWhiteSpace(dto.getSheadTime()))
            dateCheck.setHeadTime(DateHelper.parse(dto.getSheadTime(),"yyyy-MM-dd"));
        return dateCheck;
    }

    /**
     * 转化成dtos
     * @param checks
     * @return
     */
    public static List<DateCheckDto> convertList(List<DateCheck> checks){
        if(checks==null||checks.size()==0) return Collections.EMPTY_LIST;
        List<DateCheckDto> list=new ArrayList<>();
        for(DateCheck dateCheck:checks){
            list.add(convertDto(dateCheck));
        }
        return list;
    }

    /**
     * 转化成pageDto
     * @param pg
     * @return
     */
    public static PagedResult<DateCheckDto> convertPage(PagedResult<DateCheck> pg){
        return new PagedResult<DateCheckDto>(convertList(pg.getItems()),pg.getPageNumber(),pg.getPageSize(),pg.getTotalItemsCount());
    }
}
