package com.sunesoft.lemon.dqgl.competitions.proposals.application;

import com.sunesoft.lemon.dqgl.competitions.proposals.domain.IProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaowy on 2016/8/11.
 */
public class ProposalServiceImpl implements IPropsalService {

    @Autowired
    IProposalRepository repository;
}
