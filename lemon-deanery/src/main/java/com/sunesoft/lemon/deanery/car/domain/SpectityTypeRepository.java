package com.sunesoft.lemon.deanery.car.domain;

import java.util.List;


/**
 * Created by zy
 */
public interface SpectityTypeRepository {
    Long save(SpecialtyType type);

    void delete(Long id);

    SpecialtyType get(Long id);

    List<SpecialtyType> getAllType();




}
