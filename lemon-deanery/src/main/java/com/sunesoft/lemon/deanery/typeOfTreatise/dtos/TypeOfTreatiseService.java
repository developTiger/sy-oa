package com.sunesoft.lemon.deanery.typeOfTreatise.dtos;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by pxj on 2016/12/15.
 */
public interface TypeOfTreatiseService {
    /**
     * 项目计划列表
     * @param typeOfTreatiseDto
     * @return
     * @throws UnsupportedEncodingException
     */
    public PagedResult<TypeOfTreatiseDto> getTypeOfTreatise(TypeOfTreatiseDto typeOfTreatiseDto) throws UnsupportedEncodingException;

    CommonResult save(TypeOfTreatiseDto typeOfTreatiseDto);

    TypeOfTreatiseDto findById(String id);

    CommonResult delete(List<Long> listId);

    public List<TypeOfTreatiseDto> findAll();

    Long findByName(String awards_type);
}
