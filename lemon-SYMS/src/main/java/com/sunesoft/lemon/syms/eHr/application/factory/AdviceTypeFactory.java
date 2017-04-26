package com.sunesoft.lemon.syms.eHr.application.factory;

import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.dtos.RoleTypeDto;
import com.sunesoft.lemon.syms.eHr.domain.notice.AdviceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiazl on 2017/1/18.
 */
public class AdviceTypeFactory extends DtoFactory {

    /**
     * 将dto 转变为实体类
     *
     * @param source
     * @param target
     * @return
     */
    public static AdviceType convertFromDto(RoleTypeDto source, AdviceType target) {
        target = convert(source, target);
        if (!StringUtils.isNullOrWhiteSpace(source.getScreateDateTime())) {
            target.setCreateDateTime(DateHelper.parse(source.getScreateDateTime()));
        }
        if (!StringUtils.isNullOrWhiteSpace(source.getSlastUpdateTime())) {
            target.setLastUpdateTime(DateHelper.parse(source.getSlastUpdateTime()));
        }
        return target;
    }

    /**
     * 将实体类转变为dto
     *
     * @param source
     * @return
     */
    public static RoleTypeDto convertToDto(AdviceType source) {
        RoleTypeDto target = new RoleTypeDto();
        target = convert(source, target);
        if (source.getCreateDateTime() != null) {
            target.setScreateDateTime(DateHelper.formatDate(source.getCreateDateTime()));
        }
        if (source.getLastUpdateTime() != null) {
            target.setSlastUpdateTime(DateHelper.formatDate(source.getLastUpdateTime()));
        }
        return target;
    }

    /**
     * list 转化Dtos
     * @param sources
     * @return
     */
    public static List<RoleTypeDto> convertToDto(List<AdviceType> sources) {
        List<RoleTypeDto> list = new ArrayList<>();
        if (sources != null && sources.size() > 0) {
            for (AdviceType at : sources) {
                list.add(convertToDto(at));
            }
        }
        return list;
    }
}
