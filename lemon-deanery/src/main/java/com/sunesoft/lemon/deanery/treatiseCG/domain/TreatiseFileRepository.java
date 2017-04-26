package com.sunesoft.lemon.deanery.treatiseCG.domain;

import java.util.List;

/**
 * Created by pxj on 2016/9/26.
 */
public interface TreatiseFileRepository {
    Long save(TreatiseFile treatiseFile);

    List<TreatiseFile> getTreatiseFileByTreatiseFileID(Long id);
}
