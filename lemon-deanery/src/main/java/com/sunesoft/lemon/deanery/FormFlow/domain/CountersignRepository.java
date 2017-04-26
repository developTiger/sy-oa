package com.sunesoft.lemon.deanery.FormFlow.domain;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;

/**
 * Created by wangwj on 2016/8/5 0005.
 */
public interface CountersignRepository {
    Long save(Countersign countersign);
    void delete(Long id);
    Countersign get(Long id);

    /**
     * 判断是否全部同意
     * @param formNo
     * @return
     */
    boolean counterAllOver(Long formNo);

    /**
     * 会签通过后的处理(包括全部通过后的处理)
     * @param formNo
     * @param appUserId 签核人ID
     * @param countRemark 审核意见
     * @param countResult 审核结果 0 同意 1 不同意
     * @return 返回当前表单名称
     */
    CommonResult counterAccess(Long formNo,Long appUserId,String countRemark,Integer countResult);

    /**
     * 启动会签流程
     * 如果是统一启动会签则DTO中直接放入FormNo即可
     * @param counterIdList 会签人员ID列表
     * @param dto
     * @param isSpecial 0 正常启动会签 1 需要统一启动会签
     * @return
     */
    CommonResult beginCounterAccess(String[] counterIdList,ApproveFormDto dto,Integer isSpecial);

    /**
     * 会签否决退回处理
     * @param appUserId
     * @param countRemark
     * @param countResult
     * @param formNo
     * @return
     */
    CommonResult rejectCounterAccess(Long appUserId,String countRemark,Integer countResult,Long formNo);
}
