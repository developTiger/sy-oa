package com.sunesoft.lemon.ay.partyGroupForms.domain;

import java.util.List;

/**
 * Created by admin on 2016/9/5.
 */
public interface FormWorkersProposalRepository {

    Long save(FormWorkersProposal formWorkersProposal);

    FormWorkersProposal getById(Long id);

    List<FormWorkersProposal> getAll();

}
