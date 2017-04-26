package com.sunesoft.lemon.deanery.ReceptionFlow.application;

import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionDto;
//import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionNBDto;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.ReceptionNB;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;

import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
public interface ReceptionService {
//    List<FormApprover> getNextAppInfo(Long formNo,Integer nextClStep);
//    ReceptionDto getReceptionNBInfo(Long formNo);
    Long save(ReceptionDto dto);
    Long apply(ReceptionDto dto);
    String  getApplyUrl(String Id);

    //院长办公室1
    Long dean(ReceptionDto dto,int choice);

    //分管领导
    Long secondary(ReceptionDto dto,int choice);

    //主要领导
    Long mainApply(ReceptionDto dto,int choice);

    //院长办公室2
    Long dean2(ReceptionDto dto);

    //确认接待
    Long confirm(ReceptionDto dto);

    //获得实体对象
    ReceptionDto getById(String orderId);
}
