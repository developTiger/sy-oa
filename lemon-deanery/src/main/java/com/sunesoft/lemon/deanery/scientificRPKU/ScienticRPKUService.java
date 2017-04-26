package com.sunesoft.lemon.deanery.scientificRPKU;

import com.sunesoft.lemon.deanery.project.application.dtos.ScientificResearchProjectDto;
import com.sunesoft.lemon.fr.results.PagedResult;

import java.util.Date;
import java.util.List;

/**
 * Created by zy on 2016/10/7.
 */
public interface ScienticRPKUService {

    PagedResult<ScientificRPKUDto> getScientificResearchProjects(ScientificRPKUCriteria scientificRPKUCriteria);
    Long addScientificResearchProject(ScientificRPKU s);
    ScientificRPKU getIdByProjectNo(String projectNo);
    ScientificRPKUDto getIdByProjectNoDto(String projectNo);
    ScientificRPKUDto getIdByProjectNoDto1(String projectNo);
    List<ScientificRPKU> getProjectByformNo(String projectNo,String opinint);
    List<ScientificRPKU> getProjectByformNo1(String projectNo,String instust);
    ScientificRPKUDto getByProjectIdDto(Long id);
    List<ScientificRPKU> getAllScientificKu();
    List<ScientificRPKU> getAllScientificKu1();
    List<ScientificRPKU> getAllScientificKu2();
    //  导出
    List<ScientificRPKUDto1> getAllScientificKu3();

    /**
     * 更新时间
     * @param projectNo
     * @param Time
     */
    void saveByProjectNo(String projectNo,Date Time);

    /**
     *
     *   流程完成状态更改
     *   yx立项
     *   KT开题
     *   YS验收
     *   SB申报
     *   JC检测
     *
     */
    Long update(String projectNo,String YX,String KT,String YS,String SB,String JC);
    /**
     *
     *   流程开始状态更改
     *   yx立项
     *   KT开题
     *   YS验收
     *   SB申报
     *   JC检测
     *
     */
    Long updateByProjectApply(String projectNo,String YX,String KT,String YS,String SB,String JC);

    /**
     *
     * 项目库重置
     *
     */
    Long reset(String projectNo,String YX,String KT,String YS,String SB,String JC);



    List<ScientificRPKUDto> queryProjectApprove(String number);
}
