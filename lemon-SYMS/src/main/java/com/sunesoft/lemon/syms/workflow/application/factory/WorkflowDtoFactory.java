package com.sunesoft.lemon.syms.workflow.application.factory;

import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormListDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.InnerFormAppPointDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApproveRole;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormList;
import com.sunesoft.lemon.syms.workflow.domain.InnerFormApprovePoint;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhouz on 2016/6/17.
 */
public class WorkflowDtoFactory {

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
    public static FormList convertFormListFromDto(FormListDto dto){
        FormList formList = new FormList();
        BeanUtils.copyProperties(dto, formList);

        return formList;
    }


    public static FormHeaderDto convertFormHeaderToDto(FormHeader header){
        FormHeaderDto formHeaderDto = new FormHeaderDto();
        BeanUtils.copyProperties(header, formHeaderDto);
        formHeaderDto.setFormNo(header.getId());
        if(!StringUtils.isNullOrWhiteSpace(header.getViewUrl())){
            formHeaderDto.setViewUrl(header.getViewUrl());
        }
        else {
            formHeaderDto.setViewUrl("forms");//兼容现有数据
        }
        if(header.getFormApprovers()!=null&&header.getFormApprovers().size()>0){
            formHeaderDto.setFormApprovers(header.getFormApprovers());
        }

        if(header.getInnerFormApprovePoints()!=null&& header.getInnerFormApprovePoints().size()>0){
            List<InnerFormAppPointDto>  innerFormAppPointDtos = new ArrayList<>();
            for(InnerFormApprovePoint point:header.getInnerFormApprovePoints()){
                InnerFormAppPointDto dto = DtoFactory.convert(point,new InnerFormAppPointDto());
                innerFormAppPointDtos.add(dto);
            }

            formHeaderDto.setInnerFormAppPointDtos(innerFormAppPointDtos);


        }

        return formHeaderDto;
    }
    public static FormListDto convertDtoToFormList(FormList formList){

        FormListDto dto = new FormListDto();
        BeanUtils.copyProperties(formList,dto);
        return dto;
    }

    public static FormApproveRole convertFormAppRoleFromDto(FormApproveRole dto){
        FormApproveRole role = new FormApproveRole();
        BeanUtils.copyProperties(dto, role);

        return role;
    }

    public static FormListDto convertFormListToDto(FormList formList){

        FormListDto dto = new FormListDto();
        BeanUtils.copyProperties(formList,dto);
        return dto;
    }
}
