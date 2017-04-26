package com.sunesoft.lemon.deanery.typeOfTreatise.domain;

import java.util.List;

/**
 * Created by pxj on 2016/12/15.
 */
public interface TypeOfTreaiseRepository {
    Long save(TypeOfTreatise typeOfTreatise);

    TypeOfTreatise findById(String id);

    List<TypeOfTreatise> findAll();

    Long findByName(String awards_type);
}
