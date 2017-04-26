package com.sunesoft.lemon.ay.partyGroupForms.domain;

/**
 * Created by admin on 2017/2/13.
 */
public interface CompetitionTitleTypeRepository {

    Long save(WorkProject_competitionTitleType titleType);

    void delete(Long id);

    WorkProject_competitionTitleType getById(Long id);



}
