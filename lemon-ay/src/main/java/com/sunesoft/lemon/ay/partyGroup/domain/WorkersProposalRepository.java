package com.sunesoft.lemon.ay.partyGroup.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
public interface WorkersProposalRepository {

    Long save(WorkersProposal workersProposal);

    WorkersProposal getById(Long id);

    List<WorkersProposal> getAll();

}
