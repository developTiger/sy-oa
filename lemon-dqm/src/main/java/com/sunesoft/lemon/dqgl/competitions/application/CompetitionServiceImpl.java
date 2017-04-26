package com.sunesoft.lemon.dqgl.competitions.application;

import com.sunesoft.lemon.dqgl.competitions.domain.ICompetitionAchievementRepository;
import com.sunesoft.lemon.dqgl.competitions.domain.ICompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaowy on 2016/8/11.
 */
public class CompetitionServiceImpl implements ICompetitionService {


    @Autowired
    ICompetitionRepository repository;

    @Autowired
    ICompetitionAchievementRepository achievementRepository;

}
