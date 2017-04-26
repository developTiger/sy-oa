package com.sunesoft.lemon.deanery.patentCG.application;

import com.sunesoft.lemon.deanery.patentCG.application.criteria.PatentCriteria;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 */
public interface PatentService {

     public PatentDto getByIdPatent(Long patentId);

     public Long addOrUpdatePatent(PatentDto patentDto);

     public boolean delPatent(Long[] ids);

     public PagedResult<PatentDto> getPatent(PatentCriteria patentCriteria) ;

     public List<Map<String,Object>> patentReport();

}
