package com.sunesoft.lemon.deanery.ReceptionFlow.domain;

import java.util.List;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
public interface AccommodationReposiroty {
    Long save(Accommodation accommodation);
    void delete(Long id);
    Accommodation get(Long id);
    Accommodation getByRecepId(Long recepId);
}
