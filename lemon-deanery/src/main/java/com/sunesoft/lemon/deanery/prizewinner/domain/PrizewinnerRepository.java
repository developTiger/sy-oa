package com.sunesoft.lemon.deanery.prizewinner.domain;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public interface PrizewinnerRepository{
    Long save(Prizewinner prizewinner);

    void delete(Long id);

    Prizewinner get(Long id);

    List<Prizewinner> getPrizewinnerByCGName(Long cgId,String cgName);

    /**
     * 根据成果名称、主键ID获取对应的获奖人姓名
     * @param cgId 成果表主键ID
     * @param cgName 成果表名称
     * @return
     */
    String getPrizeWinnerNameInfos(Long cgId,String cgName);
}
