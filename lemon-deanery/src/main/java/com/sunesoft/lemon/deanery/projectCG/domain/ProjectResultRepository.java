package com.sunesoft.lemon.deanery.projectCG.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * 项目成果数据仓库
 */
public interface ProjectResultRepository {

     Long save(ProjectResult projectResult);

     void delete(Long id);

     ProjectResult get(Long id);

    /**
     * 项目成果统计汇总
     * @return
     */
    List<Map<String,Object>> projectCGReport();
}
