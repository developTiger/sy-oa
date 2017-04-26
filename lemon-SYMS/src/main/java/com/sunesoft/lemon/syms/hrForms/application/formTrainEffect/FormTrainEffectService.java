package com.sunesoft.lemon.syms.hrForms.application.formTrainEffect;

import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainEffectDto;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrain;
import com.sunesoft.lemon.syms.hrForms.domain.FormTrainEffect;
import com.sunesoft.lemon.syms.workflow.application.serviceSelector.FormService;

import java.text.Normalizer;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/27.
 */
public interface FormTrainEffectService extends FormService<FormTrainEffect,FormTrainEffectDto> {

    public List<FormTrain> getAllFormTrain();
}
