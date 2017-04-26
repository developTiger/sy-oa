package com.sunesoft.lemon.deanery.patentCG.application;

import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentFlowDto;
import com.sunesoft.lemon.deanery.patentCG.domain.Patent;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by pxj on 2016/8/30.
 */
public interface PatentFlowService extends FormService<Patent, PatentDto> {
    public Long findPatentId(Long formNo);

    public void savePrizeWinner(Long id, String in);

    List<PatentFlowDto> queryProject();

    CommonResult nextProject(ApproveFormDto dto);
}
