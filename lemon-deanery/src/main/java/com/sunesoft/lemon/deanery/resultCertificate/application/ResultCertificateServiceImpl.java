package com.sunesoft.lemon.deanery.resultCertificate.application;

import com.sunesoft.lemon.deanery.resultCertificate.application.criteria.ResultCertificateCriteria;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateDto;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificateFileDto;
import com.sunesoft.lemon.deanery.resultCertificate.application.dtos.ResultCertificatePeopleDto;
import com.sunesoft.lemon.deanery.resultCertificate.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificate;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificateFile;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificatePeople;
import com.sunesoft.lemon.deanery.resultCertificate.domain.ResultCertificateRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pxj on 2016/9/28.
 */
@Service("resultCertificateService")
public class ResultCertificateServiceImpl extends FormBase2<ResultCertificate,ResultCertificateDto> implements ResultCertificateService{
    @Autowired
    ResultCertificateRepository resultCertificateRepository;

    @Override
    protected CommonResult save(ResultCertificate resultCertificate) {
        resultCertificateRepository.save(resultCertificate);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(ResultCertificate resultCertificate) {
        return null;
    }

    @Override
    protected ResultCertificate ConvetDto(ResultCertificateDto dto) {
        ResultCertificate resultCertificate = new ResultCertificate();
        resultCertificate = DeaneryUtil.convert(dto,resultCertificate);
        List<ResultCertificateFile> l = new ArrayList<>();
        List<ResultCertificatePeople> people=new ArrayList<>();
        List<ResultCertificateFileDto> lists =   dto.getFiles();
        for(ResultCertificateFileDto d : lists){
            ResultCertificateFile file =   DeaneryUtil.convert(d, new ResultCertificateFile());
            l.add(file);
        }
        resultCertificate.setFiles(l);
        List<ResultCertificatePeopleDto> peoples=dto.getPeople();
        for(ResultCertificatePeopleDto p : peoples){
            ResultCertificatePeople people1=DeaneryUtil.convert(p,new ResultCertificatePeople());
            people.add(people1);
        }
         resultCertificate.setPeople(people);
        return resultCertificate;
    }

    @Override
    protected String getSummery(ResultCertificate resultCertificate) {
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
    protected ResultCertificate getByFormNo(Long formNo) {
        Criteria criteria=getSession().createCriteria(ResultCertificate.class);
        criteria.add(Restrictions.eq("formNo",formNo));
        criteria.add(Restrictions.eq("isActive",true));
        List<ResultCertificate> list = criteria.list();
        return (ResultCertificate)criteria.uniqueResult();
    }

    @Override
    public ResultCertificateDto getFormByFormNo(Long formNo) {
        return null;
    }

    @Override
    public CommonResult nextProject(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }

    @Override
    public ResultCertificateDto getResultCertificate(Long formNo) {
        ResultCertificate resultCertificate = resultCertificateRepository.getByFormNo(formNo);
        List<ResultCertificateFileDto> ds = new ArrayList<>();
        List<ResultCertificateFile> files =  resultCertificate.getFiles();
        for(ResultCertificateFile file: files ){
            ResultCertificateFileDto fileDto = DeaneryUtil.convert(file , new ResultCertificateFileDto());
            ds.add(fileDto);
        }
        List<ResultCertificatePeopleDto> peopleDtos = new ArrayList<>();
        List<ResultCertificatePeople> peoples =  resultCertificate.getPeople();
        for(ResultCertificatePeople people: peoples ){
            ResultCertificatePeopleDto peopleDto = DeaneryUtil.convert(people , new ResultCertificatePeopleDto());
            peopleDtos.add(peopleDto);
        }
        ResultCertificateDto resultCertificateDto = new ResultCertificateDto();
        resultCertificateDto =  DeaneryUtil.convert(resultCertificate,resultCertificateDto);
        resultCertificateDto.setFiles(ds);
        resultCertificateDto.setPeople(peopleDtos);
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
        return resultCertificateDto;
    }

    @Override
    public PagedResult getResultCertificateList(ResultCertificateCriteria resultCertificateCriteria) throws UnsupportedEncodingException {
        Criteria criterion = getSession().createCriteria(ResultCertificate.class);
        criterion.add(Restrictions.eq("isActive", true));
        // String wrn = URLDecoder.decode(projectPlanInput.getProjectPlan_Name(), "UTF-8");
        //   String projectPlan_name =  URLDecoder.decode(projectPlanInput.getProjectPlan_Name(),"UTF-8");
/*        if (!StringUtils.isNullOrWhiteSpace(projectPlanInput.getProjectPlan_Type())){
            projectPlan_type = URLDecoder.decode(projectPlanInput.getProjectPlan_Type(),"UTF-8");
        }
        String startTime = URLDecoder.decode(projectPlanInput.getStartTime_Str(),"UTF-8");
        String endTime = URLDecoder.decode(projectPlanInput.getEndTime_Str(),"UTF-8");*/
        if (!StringUtils.isNullOrWhiteSpace(resultCertificateCriteria.getWin_Result_Name())){
            criterion.add(Restrictions.like("win_Result_Name",
                    URLDecoder.decode(resultCertificateCriteria.getWin_Result_Name(),"UTF-8"),
                    MatchMode.ANYWHERE));
        }
        if (!StringUtils.isNullOrWhiteSpace(resultCertificateCriteria.getCertif_No())){
            criterion.add(Restrictions.like("certif_No",
                    URLDecoder.decode(resultCertificateCriteria.getCertif_No(),"UTF-8"), MatchMode.ANYWHERE));
        }
        if (!StringUtils.isNullOrWhiteSpace(resultCertificateCriteria.getAwards_Type())){
            criterion.add(Restrictions.eq("awards_Type",
                    URLDecoder.decode(resultCertificateCriteria.getAwards_Type(),"UTF-8")));
        }
        if (!StringUtils.isNullOrWhiteSpace(resultCertificateCriteria.getBegin_Time())){
            Date st= DateHelper.parse(resultCertificateCriteria.getBegin_Time(),"yyyy-MM-dd");
            criterion.add(Restrictions.ge("win_Date",st));
        }
        if (!StringUtils.isNullOrWhiteSpace(resultCertificateCriteria.getEnd_Time())){
            Date et= DateHelper.parse(resultCertificateCriteria.getEnd_Time(),"yyyy-MM-dd");
            criterion.add(Restrictions.le("win_Date",et));
        }
       /* if(!StringUtils.isNullOrWhiteSpace(projectPlan_name)){
            criterion.add(Restrictions.like("projectPlan_Name",projectPlan_name, MatchMode.ANYWHERE));
        }*/
        if(!StringUtils.isNullOrWhiteSpace(resultCertificateCriteria.getWin_Level())){
            criterion.add(Restrictions.eq("win_Level",resultCertificateCriteria.getWin_Level()));
        }
        criterion.add(Restrictions.eq("isComplete",true));
        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((resultCertificateCriteria.getPageNumber() - 1) * resultCertificateCriteria.getPageSize()).setMaxResults(resultCertificateCriteria.getPageSize());
        List<ResultCertificate> list = criterion.list();
        List<ResultCertificateDto> resultCertificateDto = new ArrayList<ResultCertificateDto>();
        for (ResultCertificate r : list){
            resultCertificateDto.add(DeaneryUtil.convert(r,new ResultCertificateDto()));
        }
        for (int i=0;i<resultCertificateDto.size();i++) {
            List<ResultCertificatePeopleDto> resultCertificatePeopleDtos = new ArrayList<ResultCertificatePeopleDto>();
            List<ResultCertificateFileDto> resultCertificateFileDtos = new ArrayList<ResultCertificateFileDto>();
            String name = "";
            for (int a = 0; a < resultCertificateDto.get(i).getPeople().size(); a++) {
                ResultCertificatePeopleDto d = DeaneryUtil.convert(list.get(i).getPeople().get(a), new ResultCertificatePeopleDto());
                resultCertificatePeopleDtos.add(d);
                name = name + d.getPeople_Name() + ",";
                //resultCertificatePeopleDtos.add(DeaneryUtil.convert(list.get(i).getPeople(),new ResultCertificatePeopleDto()));
            }
            if (resultCertificatePeopleDtos.size() > 0) {
                resultCertificateDto.get(i).setPeople(resultCertificatePeopleDtos);
                resultCertificateDto.get(i).setWin_People_Str(name.substring(0, name.length() - 1));
            }
            for (int b = 0; b < resultCertificateDto.get(i).getFiles().size(); b++) {
                resultCertificateFileDtos.add(DeaneryUtil.convert(list.get(i).getFiles().get(b), new ResultCertificateFileDto()));
            }
            if (resultCertificateFileDtos.size() > 0) {
                resultCertificateDto.get(i).setFiles(resultCertificateFileDtos);
            }
            resultCertificateDto.get(i).setWin_Date_Str(DateHelper.formatDate(resultCertificateDto.get(i).getWin_Date(), "yyyy-MM-dd"));
/* 获奖类型
 String roleName = "";
            if (!StringUtils.isNullOrWhiteSpace(resultCertificateDto.get(i).getAwards_Type())) {
                switch (resultCertificateDto.get(i).getAwards_Type()) {
                    case "1":
                        roleName = "科研";//科管审核员
                        break;
                    case "2":
                        roleName = "生产运行";//安运审核员
                        break;
                    case "3":
                        roleName = "质量安全";//安运审核员
                        break;
                    case "4":
                        roleName = "综治维稳";//院办审核员
                        break;
                    case "5":
                        roleName = "工会共青团";//党群审核员
                        break;
                    case "6":
                        roleName = "党务";//人事审核员
                        break;
                    default:
                        roleName = "";
                        break;

                }
            }
            resultCertificateDto.get(i).setAwards_Type(roleName);*/
        }
        return new PagedResult<ResultCertificateDto>(resultCertificateDto,resultCertificateCriteria.getPageNumber(),resultCertificateCriteria.getPageSize(),totalCount);
    }

    @Override
    public ResultCertificateDto getResultCertificateById(long l) {
        ResultCertificate resultCertificate = resultCertificateRepository.get(l);
        ResultCertificateDto resultCertificateDto =  DeaneryUtil.convert(resultCertificate,new ResultCertificateDto());
        List<ResultCertificateFileDto> resultCertificateFileDtos=new ArrayList<>();
        List<ResultCertificatePeopleDto> resultCertificatePeopleDtos=new ArrayList<>();
        String name = "";
        for(ResultCertificatePeople p:resultCertificate.getPeople()){
             resultCertificatePeopleDtos.add(DeaneryUtil.convert(p,new ResultCertificatePeopleDto()));
             name = name + p.getPeople_Name() + ",";
        }
        resultCertificateDto.setPeople(resultCertificatePeopleDtos);
       // resultCertificateDto.setWin_People_Str(name.substring(0,name.length()-1));获奖人多个
        for(ResultCertificateFile f:resultCertificate.getFiles()){
            resultCertificateFileDtos.add(DeaneryUtil.convert(f,new ResultCertificateFileDto()));
        }
        resultCertificateDto.setFiles(resultCertificateFileDtos);
        resultCertificateDto.setWin_Date_Str(DateHelper.formatDate(resultCertificateDto.getWin_Date(),"yyyy-MM-dd"));
//         Criteria criteria = getSession().createCriteria(Prizewinner.class);
//         criteria.add(Restrictions.eq("cgName","syy_oa_treatise"));
//         criteria.add(Restrictions.eq("cgId",treatiseId));
        //排序
//        criteria.addOrder(Order.asc("sortNo"));
        return resultCertificateDto;
    }

    @Override
    public boolean findResultCertificate(ResultCertificateDto resultCertificateDto) {
        boolean b=false;
        List<ResultCertificate> resultCertificate= resultCertificateRepository.findResultCertificate(resultCertificateDto);
       for(int i=0;i<resultCertificate.size();i++){
           if(resultCertificate.get(i).getClStep()==1&&resultCertificate.get(i).getFormStatus().equals(FormStatus.WD)){
               resultCertificate.remove(i);
           }
       }
       if(resultCertificate.size()==0){
           b=true;
       }
        return b;
    }

    @Override
    public List<ResultCertificateDto> query_ResultCertificate_ByApperId(long id) {
        List<ResultCertificateDto> resultCertificateDto=new ArrayList<>();
        List<ResultCertificate> resultCertificate=resultCertificateRepository.query_ByApperId(id);
        for(ResultCertificate resultCertificate_copy:resultCertificate){
            ResultCertificateDto resultCertificateDto_copy =  DeaneryUtil.convert(resultCertificate_copy,new ResultCertificateDto());
            List<ResultCertificateFileDto> resultCertificateFileDtos=new ArrayList<>();
            List<ResultCertificatePeopleDto> resultCertificatePeopleDtos=new ArrayList<>();
            String name = "";
            for(ResultCertificatePeople p:resultCertificate_copy.getPeople()){
                resultCertificatePeopleDtos.add(DeaneryUtil.convert(p,new ResultCertificatePeopleDto()));
                name = name + p.getPeople_Name() + ",";
            }
            resultCertificateDto_copy.setPeople(resultCertificatePeopleDtos);
            for(ResultCertificateFile f:resultCertificate_copy.getFiles()){
                resultCertificateFileDtos.add(DeaneryUtil.convert(f,new ResultCertificateFileDto()));
            }
            resultCertificateDto_copy.setFiles(resultCertificateFileDtos);
            resultCertificateDto_copy.setWin_Date_Str(DateHelper.formatDate(resultCertificateDto_copy.getWin_Date(),"yyyy-MM-dd"));
            resultCertificateDto.add(resultCertificateDto_copy);
        }
        return resultCertificateDto;
    }
}
