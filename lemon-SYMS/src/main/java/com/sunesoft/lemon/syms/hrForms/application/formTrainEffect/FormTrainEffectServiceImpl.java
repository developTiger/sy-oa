package com.sunesoft.lemon.syms.hrForms.application.formTrainEffect;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainChooseEmpDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.FormTrainEffectDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.TrainFileDto;
import com.sunesoft.lemon.syms.hrForms.domain.*;
import com.sunesoft.lemon.syms.workflow.application.FormBase;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangkefan on 2016/7/27.
 */
@Service("formTrainEffectService")
public class FormTrainEffectServiceImpl extends FormBase2<FormTrainEffect,FormTrainEffectDto> implements  FormTrainEffectService {
    @Autowired
    FormTrainEffectRepository formTrainEffectRepository;

    @Autowired
    FormTrainRepository formTrainRepository;

    @Override
    protected CommonResult save(FormTrainEffect formTrainEffect) {
        formTrainEffectRepository.save(formTrainEffect);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormTrainEffect formTrainEffect) {
        FormTrainEffect f = formTrainEffectRepository.get(formTrainEffect.getId());
        f.setTrainPlace(formTrainEffect.getTrainPlace());
        f.setHelp(formTrainEffect.getHelp());
        f.setSatisfied(formTrainEffect.getSatisfied());
        f.setPractical(formTrainEffect.getPractical());
        f.setTrainBeginTime(formTrainEffect.getTrainBeginTime());
        f.setTrainEndTime(formTrainEffect.getTrainEndTime());
        f.setTrainPlace(formTrainEffect.getTrainPlace());
        f.setSuggest(formTrainEffect.getSuggest());
        f.setFiles(formTrainEffect.getFiles());
        formTrainEffectRepository.save(f);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormTrainEffect ConvetDto(FormTrainEffectDto dto) {
        FormTrainEffect formTrainEffect = new FormTrainEffect();
        formTrainEffect = DtoFactory.convert(dto,formTrainEffect);
        List<TrainFile> l = new ArrayList<>();
        List<TrainFileDto> lists =   dto.getFilesDto();
        for(TrainFileDto d : lists){
          TrainFile file =   DtoFactory.convert(d, new TrainFile());
          l.add(file);
        }
        formTrainEffect.setFiles(l);
        return formTrainEffect;
    }

    @Override
    protected String getSummery(FormTrainEffect formTrainEffect) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        return null;
    }

    @Override
    protected FormTrainEffect getByFormNo(Long formNo) {
        return formTrainEffectRepository.getByFormNo(formNo);
    }

    @Override
    public FormTrainEffectDto getFormByFormNo(Long formNo) {
        FormTrainEffect formTrainEffect = this.getByFormNo(formNo);
        List<TrainFileDto> ds = new ArrayList<>();
        List<TrainFile> files =  formTrainEffect.getFiles();
        for(TrainFile file: files ){
            TrainFileDto fileDto = DtoFactory.convert(file , new TrainFileDto());
            ds.add(fileDto);
        }
        FormTrainEffectDto formTrainEffectDto = new FormTrainEffectDto();
        formTrainEffectDto =  DtoFactory.convert(formTrainEffect,formTrainEffectDto);
        formTrainEffectDto.setFilesDto(ds);
//
//
//        formTrainEffect = DtoFactory.convert(formTrainEffectDto,formTrainEffect);
//        List<TrainFile> l = new ArrayList<>();
//        List<TrainFileDto> lists =   formTrainEffectDto.getFilesDto();
//        for(TrainFileDto d : lists){
//            TrainFile file =   DtoFactory.convert(d, new TrainFile());
//            l.add(file);
//        }
//        formTrainEffect.setFiles(l);
        return formTrainEffectDto;
    }

    @Override
    public List<FormTrain> getAllFormTrain() {
        return formTrainRepository.getAll();
    }
}
