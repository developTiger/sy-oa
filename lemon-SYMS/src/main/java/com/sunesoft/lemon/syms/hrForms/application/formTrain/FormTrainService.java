package com.sunesoft.lemon.syms.hrForms.application.formTrain;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDownloadDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainEmpDto;
import com.sunesoft.lemon.syms.hrForms.application.criteria.TrainEmpCriteria;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrain;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.util.List;

/**
 * Created by jiangkefan on 2016/7/22.
 */
public interface FormTrainService extends FormService<FormTrain,FormTrainDto>{
     public CommonResult addEmpId(Long formNo,List<Long> empIds);

     public CommonResult deleteEmpById(Long formNo, Long id);

     List<FormTrainDto> getFormTrainByEmpID(Long empId);

    List<FormTrainDownloadDto> getTrainDownloadDto(FormTrainDto evectionDto);

    List<FormTrainEmpDto> getAllTrainEmp(TrainEmpCriteria criteria);

    List<FormTrainDownloadDto> getTrainDownloadDto(TrainEmpCriteria criteria);
}
