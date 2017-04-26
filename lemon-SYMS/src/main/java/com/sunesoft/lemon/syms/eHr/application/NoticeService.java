package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.NoticeCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.NoticeDto;
import com.sunesoft.lemon.syms.eHr.domain.notice.NoticeType;

import java.util.List;

/**
 * Created by zhouz on 2016/7/21.
 */
public interface NoticeService {

    /**
     * 新增发布信息
     * @param dto 发布信息Dto
     * @return
     */
    CommonResult AddOrUpdateNotice(NoticeDto dto);

    /**
     * 新增发布信息
     * @param dto 发布信息
     * @param empSessionDto 发布人信息
     * @return
     */
    CommonResult AddOrUpdateNotice(NoticeDto dto, EmpSessionDto empSessionDto);

    /**
     * 删除notice
     * @param id
     * @return
     */
    CommonResult deleteNotice(Long id);

    /**
     * 获取前5条
     * @param type
     * @return
     */
    List<NoticeDto> NoticeGetTopsType(NoticeType type);

    /**
     * 查询分页信息
     * @param noticeCriteria
     * @return
     */
    PagedResult<NoticeDto> GetNoticePaged(NoticeCriteria noticeCriteria);

    /**
     * 根据ID 获取
     * @param id
     * @return
     */
    NoticeDto getById(Long id);

    /**
     * 根据名称查询查询数据
     *
     * @return
     */
    List<NoticeDto> getByName(String name);

}
