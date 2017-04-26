package com.sunesoft.lemon.deanery.treatiseCG.domain;

import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * 论著数据仓库借口
 */
public interface TreatiseRepository {
    Long save(Treatise treatise);

    void delete(Long id);

    Treatise get(Long id);

//    Treatise update(TreatiseDto treatise);

    /**
     * 论著统计查询
     * @return
     */
    List<Map<String,Object>> treatiseReport();

    /**
     * 获取所有的论著对象
     * @return
     */
    List<Treatise> getAllTreatise();

    List<Treatise> findTreatise(TreatiseDto treatiseDto);
}
