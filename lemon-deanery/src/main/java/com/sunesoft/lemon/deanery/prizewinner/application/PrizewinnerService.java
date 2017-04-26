package com.sunesoft.lemon.deanery.prizewinner.application;

import com.sunesoft.lemon.deanery.prizewinner.application.dtos.PrizewinnerDto;
import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public interface PrizewinnerService {

     public List<PrizewinnerDto> getPrizeWinner(Long cgId,String cgName);


}
