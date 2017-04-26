package com.sunesoft.lemon.deanery.patentCG.application.dtos;

import com.sunesoft.lemon.deanery.patentCG.domain.Patent;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;

/**
 * Created by pxj on 2016/8/31.
 */
public class PatentDeaneryUtil {
    public static PatentDto convertFromListPatentFlowDto(PatentFlowDto patentFlowDto){
        PatentDto patentDto = new PatentDto();
        patentDto.setApply_Date(DateHelper.parse(patentFlowDto.getApply_Date(),"yyyy-MM-dd"));
        patentDto.setValid_Date(DateHelper.parse(patentFlowDto.getValid_Date(),"yyyy-MM-dd"));
        patentDto.setWinner_info(patentFlowDto.getWinner_info());
        patentDto.setInfo(patentFlowDto.getInfo());
        patentDto.setPatent_Name(patentFlowDto.getPatent_Name());
        patentDto.setApp_No(patentFlowDto.getApp_No());
        patentDto.setPatent_Type(patentFlowDto.getPatent_Type());
        patentDto.setListPrizers(patentFlowDto.getListPrizers());
        patentDto.setApply_Date_Str(patentFlowDto.getApply_Date_Str());
        patentDto.setValid_Date_Str(patentFlowDto.getValid_Date_Str());
        patentDto.setFormKind(patentFlowDto.getFormKind());
        patentDto.setFormKindName(patentFlowDto.getFormKindName());
        patentDto.setApplyer(patentFlowDto.getApplyer());
        patentDto.setApplyerName(patentFlowDto.getApplyerName());
        patentDto.setDeptId(patentFlowDto.getDeptId());
        patentDto.setDeptName(patentFlowDto.getDeptName());
        patentDto.setParentFormNo(patentFlowDto.getParentFormNo());
              //  t.getViewUrl(),
        return patentDto;
    }
    public static <S, T> T convert(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static Patent converFromListPatentDto(PatentDto patentDto) {
        Patent patent = new Patent();
        BeanUtils.copyProperties(patentDto, patent);
        return patent;
    }


}
