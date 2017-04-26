package com.sunesoft.lemon.deanery.carWorkFlow.application;

import com.sunesoft.lemon.deanery.carWorkFlow.application.dtos.CarApplyDto;
import com.sunesoft.lemon.deanery.carWorkFlow.domain.CarApply;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.Map;

/**
 * Created by wangwj on 2016/7/26 0026.
 * 车辆申请流程服务接口
 */
public interface CarApplyService extends FormService<CarApply,CarApplyDto>{

   /* *//*
     *获取流程主键
     *//*
    public String getProcessId(String name);*/


    /**
     * 保存carapply数据
     */
    public CommonResult save_carapply(CarApplyDto dto);

    public CarApply getByFormNo(Long formNo,Boolean isActive);
    /*
     *保存数据第二部
     *//*
    public CommonResult saveCarApplyData_step2(Map<String, Object> params,String id);*/

   /* *//*
     第三步
     *//*
    public CommonResult saveCarApplyData_step3(Map<String, Object> params,String id);*/

}
