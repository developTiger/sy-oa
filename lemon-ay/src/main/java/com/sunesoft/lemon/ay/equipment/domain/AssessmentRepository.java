package com.sunesoft.lemon.ay.equipment.domain;

/**
 * Created by jiangkefan on 2016/8/18.
 */
public interface AssessmentRepository {

     Long  save(Assessment assessment);

     Assessment get(Long id);
}
