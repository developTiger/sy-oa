package com.sunesoft.lemon.deanery.typeOfTreatise.factory;

import com.sunesoft.lemon.deanery.projectPlanInput.applicatioin.dto.ProjectPlanInput;
import com.sunesoft.lemon.deanery.projectPlanInput.domain.ProjectPlanInputDate;
import com.sunesoft.lemon.deanery.typeOfTreatise.domain.TypeOfTreatise;
import com.sunesoft.lemon.deanery.typeOfTreatise.dtos.TypeOfTreatiseDto;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;

/**
 * Created by pxj on 2016/12/15.
 */
public class TypeOfTreatiseDeaneryUtil {
    public static TypeOfTreatiseDto converFromList(TypeOfTreatise typeOfTreatise) {
        TypeOfTreatiseDto typeOfTreatiseDto = new TypeOfTreatiseDto();
        BeanUtils.copyProperties(typeOfTreatise, typeOfTreatiseDto);
        if (typeOfTreatise.getCreateDateTime() != null) {
            typeOfTreatiseDto.setCreateDateTime_Str(DateHelper.formatDate(typeOfTreatise.getCreateDateTime(), "yyyy-MM-dd"));
        }
        if (typeOfTreatise.getLastUpdateTime() != null) {
            typeOfTreatiseDto.setLastUpdateTime_Str(DateHelper.formatDate(typeOfTreatise.getLastUpdateTime(), "yyyy-MM-dd"));
        }
        return typeOfTreatiseDto;
    }

    public static TypeOfTreatise converFromListTypeOfTreatise(TypeOfTreatiseDto typeOfTreatiseDto) {
        TypeOfTreatise typeOfTreatise = new TypeOfTreatise();
        BeanUtils.copyProperties(typeOfTreatiseDto,typeOfTreatise);
        return typeOfTreatise;
    }

}