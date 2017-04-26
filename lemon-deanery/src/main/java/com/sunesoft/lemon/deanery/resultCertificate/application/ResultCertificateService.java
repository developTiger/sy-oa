package com.sunesoft.lemon.deanery.resultCertificate.application;

import com.sunesoft.lemon.deanery.resultCertificate.application.criteria.ResultCertificateCriteria;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateDto;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificate;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by pxj on 2016/9/28.
 */
public interface ResultCertificateService extends FormService<ResultCertificate, ResultCertificateDto> {
    CommonResult nextProject(ApproveFormDto dto);

    ResultCertificateDto getResultCertificate(Long formNo);

    PagedResult getResultCertificateList(ResultCertificateCriteria resultCertificateCriteria) throws UnsupportedEncodingException;;

    ResultCertificateDto getResultCertificateById(long l);

    boolean findResultCertificate(ResultCertificateDto resultCertificateDto);

    List<ResultCertificateDto> query_ResultCertificate_ByApperId(long id);
}
