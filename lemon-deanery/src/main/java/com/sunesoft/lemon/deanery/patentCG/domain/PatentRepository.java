package com.sunesoft.lemon.deanery.patentCG.domain;

import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * 专利数据仓库借口
 */
public interface PatentRepository {
    Long save(Patent patent);

    void delete(Long id);

    Patent get(Long id);

    /**
     * 专利统计查询
     * 目前根据专利类型统计查询
     * @return
     */
    List<Map<String,Object>> patentReport();

    /**
     * 获取所有专利
     * @return
     */
    List<Patent> getAllPatents();

    Long findPatentId(Long formNo);

}