package com.sunesoft.lemon.ay.equipment.application;

/**
 * Created by jiangkefan on 2016/8/19.
 */

import com.sunesoft.lemon.ay.equipment.application.Critera.AssessContentCritera;
import com.sunesoft.lemon.ay.equipment.application.dtos.AssessContentDto;
import com.sunesoft.lemon.ay.equipment.domain.AssessContent;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.List;

/**
 * 设备技术评估内容
 */
public interface AssessContentService {


    public Long addOrUpdateContent(AssessContentDto dto);


    public CommonResult delContent(List<Long> ids);


    public List<AssessContent> getAllContents();


    public PagedResult<AssessContentDto> getPageAssessContent(AssessContentCritera contentCritera);


    public AssessContent get(Long id);

    /**
     * 根据equipmentId获取AssessContent
     * @param equipmentId
     * @return
     */
    public List<AssessContentDto> getAllByEquipmentId(Long equipmentId);


}
