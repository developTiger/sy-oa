package com.sunesoft.lemon.ay.equipment.application.factory;

import com.sunesoft.lemon.ay.equimentForms.domain.FormEquiStatus;
import com.sunesoft.lemon.ay.equimentForms.domain.FormEquipment;
import com.sunesoft.lemon.ay.equimentForms.application.dtos.FormEquiStatusDto;
import com.sunesoft.lemon.fr.utils.BeanUtils;

/**
 * Created by jiangkefan on 2016/8/15.
 */
public class DtoFactory {
    public static <S, T> T convert(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }






}
