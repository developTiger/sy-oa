package com.sunesoft.lemon.deanery.treatiseCG.application;

import com.sunesoft.lemon.deanery.patentCG.application.criteria.PatentCriteria;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.treatiseCG.application.criteria.TreatiseCriteria;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.deanery.treatiseCG.domain.Treatise;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 */
public interface TreatiseService extends FormService<Treatise,TreatiseDto> {

     public Long addOrUpdate(TreatiseDto treatiseDto);

     public boolean delete(Long ids[]);

     public PagedResult<TreatiseDto> getTreatise(TreatiseCriteria treatiseCriteria) ;

     public TreatiseDto getByIdTreatise(Long treatiseId);

     public List<Map<String,Object>> treatiseReport();

     public CommonResult addOrUpdate2(TreatiseDto treatiseDto);

     public TreatiseDto getByFormNoTreatise(Long formNo);

     public CommonResult addOrUpdate3(TreatiseDto treatiseDto,ApproveFormDto dto);

     public boolean findTreatise(TreatiseDto treatiseDto);

     public List<TreatiseDto> query_ByApplyer(Long id);
}
