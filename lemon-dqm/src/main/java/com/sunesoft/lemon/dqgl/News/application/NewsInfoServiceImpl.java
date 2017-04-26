package com.sunesoft.lemon.dqgl.News.application;

import com.sunesoft.lemon.dqgl.News.domain.INewsInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhaowy on 2016/8/11.
 */
public class NewsInfoServiceImpl implements INewsInfoService {

    @Autowired
    INewsInfoRepository repository;
}
