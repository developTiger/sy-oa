package com.sunesoft.lemon.dqgl.wxinnovations.application;

import com.sunesoft.lemon.dqgl.wxinnovations.domain.IWxInnovationRepository;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by zhaowy on 2016/8/11.
 */
public class WxInnovationServiceImpl implements IWxInnovationService {

    @Autowired
    IWxInnovationRepository repository;
}
