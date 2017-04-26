package com.sunesoft.lemon.ay.partyGroupForms.domain;


/**
 * Created by admin on 2017/2/14.
 */
public interface CompetitionTypeRepository {

    Long save(CompetitionType type);

    void delete(Long id);

    CompetitionType getById(Long id);

}
