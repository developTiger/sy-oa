package com.sunesoft.lemon.ay.partyGroup.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
public interface PropagandaManagementRepository {

    Long save(PropagandaManagement propagandaManagement);

    PropagandaManagement getById(Long id);

    List<PropagandaManagement> getAll();

    void delete(Long id);

}
