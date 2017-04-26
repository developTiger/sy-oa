package com.sunesoft.lemon.deanery.car.application;

import com.sunesoft.lemon.deanery.car.application.criteria.SpecialtyTypeCriteria;
import com.sunesoft.lemon.deanery.car.domain.SpecialtyType;
import com.sunesoft.lemon.deanery.scientificRPKU.ScientificRPKUDto;
import com.sunesoft.lemon.fr.results.PagedResult;


import java.util.List;

/**
 * Created by zy.
 *
 */
public interface SpecialtyTypeService {

    public SpecialtyType getByIdType(Long id);

    public Long addType(SpecialtyType type);

    public Long updateType(SpecialtyType type);

    public boolean getType(Long id);

    public boolean delTypes(Long[] ids);

    public List<SpecialtyType> getAllType();

    public PagedResult<SpecialtyType> getType(SpecialtyTypeCriteria specialtyTypeCriteria);


    ScientificRPKUDto getIdByProjectNoDto(String projectName);
}
