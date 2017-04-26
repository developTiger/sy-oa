package com.sunesoft.lemon.ay.partyGroup.domain;


import java.util.List;

/**
 * Created by admin on 2016/9/2.
 */
public interface WorkProjectRepository {

    Long save(WorkProject workProject);

    WorkProject getById(Long id);

    List<WorkProject> getAll();


}
