package com.sunesoft.lemon.syms.eHr.application.factory;

import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.AttendDto;
import com.sunesoft.lemon.syms.eHr.domain.attend.Attend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiazl on 2017/3/2.
 */
public class AttendFactory {

    /**
     * convert from dto
     *
     * @param source
     * @param target
     * @return
     */
    public static <S, T> T convert(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 将dto 转变为实体类
     *
     * @param dto
     * @param attend
     * @return
     */
    public static Attend convertFromDto(AttendDto dto, Attend attend) {
        attend = convert(dto, attend);
        if (!StringUtils.isNullOrWhiteSpace(dto.getSlastUpdateTime())) {
            attend.setLastUpdateTime(DateHelper.parse(dto.getSlastUpdateTime(), "yyyy-MM-dd"));
        }
        if (!StringUtils.isNullOrWhiteSpace(dto.getScreateDateTime())) {
            attend.setCreateDateTime(DateHelper.parse(dto.getScreateDateTime(), "yyyy-MM-dd"));
        }
        if (!StringUtils.isNullOrWhiteSpace(dto.getSoneSureTime())) {
            attend.setOneSureTime(DateHelper.parse(dto.getSoneSureTime()));
        }
        if (!StringUtils.isNullOrWhiteSpace(dto.getSdeptSureTime())) {
            attend.setDateTime(DateHelper.parse(dto.getSdeptSureTime()));
        }
        attend.setDateTime(DateHelper.parse(dto.getSdateTime(), "yyyy-MM-dd"));
        return attend;
    }

    /**
     * 将实体类转变为dto
     *
     * @param attend
     * @return
     */
    public static AttendDto convertToDto(Attend attend) {
        AttendDto dto = new AttendDto();
        dto = convert(attend, dto);
        if (attend.getLastUpdateTime() != null) {
            dto.setSlastUpdateTime(DateHelper.formatDate(attend.getLastUpdateTime(), "yyyy-MM-dd"));
        }
        if (attend.getCreateDateTime() != null) {
            dto.setScreateDateTime(DateHelper.formatDate(attend.getCreateDateTime(), "yyyy-MM-dd"));
        }
        if (attend.getOneSureTime() != null) {
            dto.setSoneSureTime(DateHelper.formatDate(attend.getOneSureTime()));
        }
        if (attend.getDeptSureTime() != null) {
            dto.setSdeptSureTime(DateHelper.formatDate(attend.getDeptSureTime()));
        }
        dto.setSdateTime(DateHelper.formatDate(attend.getDateTime(), "yyyy-MM-dd"));
        if (attend.getType() != null) {
            dto.setTypeName(attend.getType().getName());
            dto.setTypeId(attend.getType().getId());
        }
        return dto;
    }

    /**
     * 将实体类List 转变为dto List
     *
     * @param attends
     * @return
     */
    public static List<AttendDto> convertList(List<Attend> attends) {
        List<AttendDto> dtos = new ArrayList<>();
        if (attends != null && attends.size() > 0) {
            for (Attend attend : attends) {
                AttendDto dto = convertToDto(attend);
                dtos.add(dto);
            }
        }
        return dtos;
    }

}
