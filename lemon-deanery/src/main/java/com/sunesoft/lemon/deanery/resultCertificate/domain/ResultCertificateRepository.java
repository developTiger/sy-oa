package com.sunesoft.lemon.deanery.resultCertificate.domain;

import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateDto;

import java.util.List;

/**
 * Created by pxj on 2016/9/28.
 * 获奖成果数据接口
 */
public interface ResultCertificateRepository {
    Long save(ResultCertificate resultCertificate);

    ResultCertificate getByFormNo(Long formNo);

    ResultCertificate get(long l);

    List<ResultCertificate> findResultCertificate(ResultCertificateDto resultCertificateDto);

    List<ResultCertificate> query_ByApperId(long id);
}
