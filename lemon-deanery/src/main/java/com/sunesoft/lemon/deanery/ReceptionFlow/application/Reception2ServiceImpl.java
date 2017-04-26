package com.sunesoft.lemon.deanery.ReceptionFlow.application;

import com.sunesoft.lemon.deanery.ReceptionFlow.application.dtos.ReceptionDto;
import com.sunesoft.lemon.deanery.ReceptionFlow.domain.*;
import com.sunesoft.lemon.deanery.StringCommHelper;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormOpenProjectFile;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.FormHeaderService;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.FormHeaderDto;
import com.sunesoft.lemon.syms.workflow.application.dtos.InnerFormAppPointDto;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.FormHeaderRepository;
import com.sunesoft.lemon.syms.workflow.domain.InnerFormApprovePoint;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangwj on 2016/8/2 0002.
 */
@Service(value = "reception2Service")
public class Reception2ServiceImpl extends FormBase2<ReceptionNB, ReceptionDto> implements Reception2Service {

    @Autowired
    private ReceptionRepository receptionRepository;
    @Autowired
    private AccommodationReposiroty accommodationReposiroty;
    @Autowired
    private WorkingLunchRepository workingLunchRepository;
    @Autowired
    private BanquetRepository banquetRepository;
    @Autowired
    FormHeaderService headerService;
    @Autowired
    FormHeaderRepository formHeaderRepository;


    @Override
    protected CommonResult save(ReceptionNB receptionNB) {
        receptionRepository.save(receptionNB);

        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(ReceptionNB receptionNB) {
        return null;
    }

    @Override
    protected ReceptionNB ConvetDto(ReceptionDto Dto) {
        ReceptionNB receptionNB = new ReceptionNB();
        BeanUtils.copyProperties(Dto, receptionNB);
        return receptionNB;
    }

    protected ReceptionDto ConvetToReceptionNB(ReceptionNB receptionNB) {
        ReceptionDto receptionDto=new ReceptionDto();
        BeanUtils.copyProperties(receptionNB,receptionDto);
        return receptionDto;
    }

    @Override
    protected String getSummery(ReceptionNB receptionNB) {
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
    protected ReceptionNB getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(ReceptionNB.class);
        criterion.add(Restrictions.eq("isActive",true));

        criterion.add(Restrictions.eq("formNo", formNo));
        return (ReceptionNB) criterion.uniqueResult();
    }

    @Override
    public ReceptionDto getFormByFormNo(Long formNo) {
        ReceptionNB receptionNB = this.getByFormNo(formNo);
        ReceptionDto receptionNBDto = DtoFactory.convert(receptionNB, new ReceptionDto());
        Accommodation accommodation=getAccommodation(receptionNB.getId());
        WorkingLunch workingLunch=getWorkingLunch(receptionNB.getId());
        List<Banquet> list=getListBanquet(receptionNB.getId());
        receptionNBDto.setAccommodation(accommodation);
        receptionNBDto.setWorkingLunch(workingLunch);
        receptionNBDto.setBanquets(list);
        return receptionNBDto;
    }

    @Override
    public CommonResult leaderApprove(ApproveFormDto dto, Integer leaderType, List<Long> empIds) {
//        FormHeader header = formHeaderRepository.get(dto.getFormNo());
//        Long nextId = header.getNextAppPointId();
//        List<Long> emplist = new ArrayList<>();
//        for (InnerFormApprovePoint point : header.getInnerFormApprovePoints()) {
//            if (nextId.equals(point.getId())) {
//                for (Employee e : point.getAppRole().getApprover()) {
//                    emplist.add(e.getId());
//                }
//            }
//        }
//        emplist.addAll(empIds);
        return this.resetNextApprover(dto.getFormNo(), empIds);
    }
    @Override
    public CommonResult dean(ReceptionDto receptionDto,ApproveFormDto dto,Map<String,Object> param){
        Long l=UpdateByDto(receptionDto);
        if(l!=0){
            return this.doApprove(dto,param);
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }

    }

    @Override
    public CommonResult secondary(ApproveFormDto dto,Map<String,Object> param){

        return null;
    }
    @Override
    public CommonResult mainApply(ReceptionDto receptionDto,ApproveFormDto dto,Map<String,Object> param,int choice){
        Long l=UpdateByDto(receptionDto);
        if(l!=0){
            if(choice==0){
                return this.doApprove(dto,param);
            }else{
                FormHeaderDto formDto=headerService.getHeaderByFormNo(dto.getFormNo());
                List<InnerFormAppPointDto> dtos = formDto.getInnerFormAppPointDtos();
                this.setPointAsNext(dto.getFormNo(),dtos.get(2).getId());
                return this.doApprove(dto,param);
            }
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
    }

    @Override
    public CommonResult dean2(ReceptionDto receptionDto,ApproveFormDto dto,Map<String,Object> param,int choice){
        Long l=UpdateByDto(receptionDto);
        if(l!=0){
            return this.doApprove(dto, param);
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
    }

    @Override
    public CommonResult confirm(ReceptionDto receptionDto,ApproveFormDto dto,Map<String,Object> param){
        Long l=UpdateByDto(receptionDto);
        if(l!=0){
            return  this.doAafterAllApproved(dto.getFormNo());
//            return this.doApprove(dto,param);
        }else{
            return ResultFactory.commonError("数据异常，签核失败！");
        }
    }

    @Override
    public CommonResult uploadProjectFile(Long formNo, String fileId, String fileName) {

        ReceptionNB receptionNB=this.getByFormNo(formNo);
        FormOpenProjectFile file  = new FormOpenProjectFile();
        file.setFileId(fileId);

        file.setFileName(fileName);
        receptionNB.getFormOpenProjectFiles().add(file);
        receptionNB.setFileId(fileId);
        receptionNB.setFileName(fileName);
        receptionRepository.save(receptionNB);

        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult uploadProjectFile1(Long formNo, String fileId, String fileName) {

        ReceptionNB receptionNB=this.getByFormNo(formNo);
        FormOpenProjectFile file  = new FormOpenProjectFile();
        file.setFileId(fileId);

        file.setFileName(fileName);
        receptionNB.getFormOpenProjectFiles().add(file);
        receptionNB.setFileId1(fileId);
        receptionNB.setFileName1(fileName);
        receptionRepository.save(receptionNB);

        return ResultFactory.commonSuccess();
    }

    @Override
    public ReceptionDto downByOrderIdReception(String id) {
        Criteria criteria=getSession().createCriteria(ReceptionNB.class);
        criteria.add(Restrictions.eq("formNo",Long.parseLong(id)));
        List<ReceptionNB> list=criteria.list();
        if(list!=null&&list.size()>0){
            ReceptionDto receptionDto=ConvetToReceptionNB(list.get(0));
            return receptionDto;
        }else{
            return null;
        }
    }

    @Override
    public List<ReceptionDto> receptionAll(String formKind,int clstep,boolean isComplete) {
        Criteria criteria=getSession().createCriteria(ReceptionNB.class);
        criteria.add(Restrictions.eq("formKind",formKind));
        criteria.add(Restrictions.eq("clStep",clstep));
        criteria.add(Restrictions.eq("isComplete",isComplete));
        List<ReceptionDto> listDto=new ArrayList<>();
        List<ReceptionNB> list=criteria.list();
        if(list.size()>0&&list!=null){
            for(int i=0;i<list.size();i++){
                listDto.add(ConvetToReceptionNB(list.get(i)));
            }
            return listDto;
        }
        return null;
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
        Integer clStep = Integer.parseInt(param.get("clStep").toString());
        Boolean isComplete = Boolean.valueOf(param.get("isStepComplete").toString());
        if(clStep.equals(1)&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            ReceptionNB receptionNB=receptionRepository.get(StringCommHelper.nullToLong(param.get("id")));
            String ids = param.get("hidid").toString();
            String perName=param.get("hidname").toString();
            updateProject(dto,ids);
            receptionNB.setCoopDept(StringCommHelper.nullToString(param.get("coopDept")));
            receptionNB.setReceptionWork(StringCommHelper.nullToString(param.get("receptionWork")));
            receptionNB.setPersonComp(perName);
            receptionNB.setProcessId(ids);

            receptionNB.setTaojian(Integer.parseInt(StringCommHelper.nullToString(param.get("taojian"))));
            receptionNB.setDanjian(Integer.parseInt(StringCommHelper.nullToString(param.get("danjian"))));
            receptionNB.setBiaozhunjian(Integer.parseInt(StringCommHelper.nullToString(param.get("biaozhunjian"))));
            receptionNB.setQitajian(Integer.parseInt(StringCommHelper.nullToString(param.get("qitajian"))));
            receptionNB.setRence1(StringCommHelper.nullToString(param.get("rence1")));
            receptionNB.setRence2(StringCommHelper.nullToString(param.get("rence2")));
            receptionNB.setRence3(StringCommHelper.nullToString(param.get("rence3")));
            receptionNB.setYanhuisj1(StringCommHelper.nullToString(param.get("yanhuisj1")));
            receptionNB.setYanhuisj2(StringCommHelper.nullToString(param.get("yanhuisj2")));
            receptionNB.setYanhuirs1(StringCommHelper.nullToString(param.get("yanhuirs1")));
            receptionNB.setYanhuirs2(StringCommHelper.nullToString(param.get("yanhuirs2")));
            receptionNB.setYanhuibz1(StringCommHelper.nullToString(param.get("yanhuibz1")));
            receptionNB.setYanhuibz2(StringCommHelper.nullToString(param.get("yanhuibz2")));
            receptionNB.setFeiyongyusuan1(StringCommHelper.nullToString(param.get("feiyongyusuan1")));
            receptionNB.setFeiyongyusuan2(StringCommHelper.nullToString(param.get("feiyongyusuan2")));
            receptionNB.setFeiyongyusuan3(StringCommHelper.nullToString(param.get("feiyongyusuan3")));
            receptionNB.setDaxie(StringCommHelper.nullToString(param.get("daxie")));
            receptionNB.setXiaoxie(StringCommHelper.nullToString(param.get("xiaoxie")));

            receptionRepository.save(receptionNB);
        }
        if(clStep.equals(2)&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            // 填写宾馆等信息
            Accommodation accommodation=new Accommodation();
            accommodation.setReceptionId(StringCommHelper.nullToLong(param.get("id")));
            accommodation.setBudget(StringCommHelper.nullToDouble(param.get("budget")));
            accommodation.setOtherRoomNum(StringCommHelper.nullToInteger(param.get("otherRoomNum")));
            accommodation.setSingleRoomNum(StringCommHelper.nullToInteger(param.get("singleRoomNum")));
            accommodation.setStandardRoomNum(StringCommHelper.nullToInteger(param.get("standardRoomNum")));
            accommodation.setSuiteNum(StringCommHelper.nullToInteger(param.get("suiteNum")));
            accommodation.setOtherRoomPrice(StringCommHelper.nullToDouble(param.get("otherRoomPrice")));
            accommodation.setSingleRoomPrice(StringCommHelper.nullToDouble(param.get("singleRoomPrice")));
            accommodation.setStandardRoomPrice(StringCommHelper.nullToDouble(param.get("standardRoomPrice")));
            accommodation.setSuitePrice(StringCommHelper.nullToDouble(param.get("suitePrice")));
            accommodationReposiroty.save(accommodation);
            //填写工作餐信息
            WorkingLunch workingLunch=new WorkingLunch();
            workingLunch.setReceptionId(StringCommHelper.nullToLong(param.get("id")));
            workingLunch.setBudget(StringCommHelper.nullToDouble(param.get("budgetn")));
            workingLunch.setEatNum(StringCommHelper.nullToInteger(param.get("eatNum")));
            workingLunch.setPeopleNum(StringCommHelper.nullToInteger(param.get("peopleNum")));
            workingLunch.setPrice(StringCommHelper.nullToDouble(param.get("price")));
            workingLunchRepository.save(workingLunch);
            //填写宴会信息
            Integer sum=  StringCommHelper.nullToInteger(param.get("sum"));
            for(int i=1;i<=sum;i++){
                Banquet banquet=new Banquet();
                Long receptionId=StringCommHelper.nullToLong(param.get("id").toString());
                banquet.setReceptionId(receptionId);
                banquet.setPeopleNum(StringCommHelper.nullToInteger(param.get("peopleNum"+i)));
                banquet.setBudget(StringCommHelper.nullToDouble(param.get("budget"+i)));
                banquet.setBanquetTime(DateHelper.parse(param.get("banquetTime"+i).toString()));
                banquet.setStandardCost(StringCommHelper.nullToDouble(param.get("standardCost"+i)));
                banquetRepository.save(banquet);
            }
            updateProject2(dto);
            ReceptionNB receptionNB=receptionRepository.get(StringCommHelper.nullToLong(param.get("id")));
            receptionNB.setSumAll(StringCommHelper.nullToDouble(param.get("sumAll")));
            receptionRepository.save(receptionNB);
        }
        if(clStep.equals(3)&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            ReceptionNB receptionNB=receptionRepository.get(StringCommHelper.nullToLong(param.get("id")));
            //receptionNB.setReceptionWork(StringCommHelper.nullToString(param.get("receptionWork")));
            receptionRepository.save(receptionNB);
        }
        if(clStep.equals(4)&&!isComplete&&dto.getAppValue().equals(AppValue.Y.ordinal())) {
            ReceptionNB receptionNB=receptionRepository.get(StringCommHelper.nullToLong(param.get("id")));
            receptionNB.setReceptionPerson(StringCommHelper.nullToString(param.get("receptionPerson")));
            receptionNB.setPersonComp(StringCommHelper.nullToString(param.get("personComp")));
            /*if(!param.get("actualCost").equals("")){*/
            /*if(!param.get("actualCost").toString().trim().equals("")){
                receptionNB.setActualCost(Long.parseLong(StringCommHelper.nullToString(param.get("actualCost"))));
            }*/

                receptionNB.setActualCost(StringCommHelper.nullToString(param.get("actualCost")));

            receptionNB.setActualBeginTime(StringCommHelper.nullToString(param.get("actualBeginTime")));
            receptionNB.setActualEndTime(StringCommHelper.nullToString(param.get("actualEndTime")));
            receptionNB.setReceptionProcess(StringCommHelper.nullToString(param.get("receptionProcess")));
            receptionRepository.save(receptionNB);
        }
        return super.doApprove(dto,param);
    }

    public CommonResult updateProject(ApproveFormDto dto,String empid) {
        if (empid!=""){
            FormHeader header = formHeaderRepository.get(dto.getFormNo());
            header.setContent( empid);
            formHeaderRepository.save(header);
        }
        //return super.doApprove(dto, null);
        return null;
    }
    public CommonResult updateProject2(ApproveFormDto dto) {
        FormHeader header = formHeaderRepository.get(dto.getFormNo());
        List<Long> emplist = new ArrayList<>();
        String empid= header.getContent();
        if(empid.indexOf(",") >= 0){
            List listid = new ArrayList();
            String[] empids = empid.split(",");
            for (String id : empids) {
                emplist.add(Long.parseLong(id));
            }
        }else{
            emplist.add(Long.parseLong(empid));
        }
        this.resetNextApprover(dto.getFormNo(), emplist);
        //return super.doApprove(dto, null);
        return null;
    }

    public Long UpdateByDto(ReceptionDto dto){
        ReceptionNB receptionNB=receptionRepository.get(dto.getId());
        BeanUtils.copyProperties(dto,receptionNB);
        Long l=receptionRepository.save(receptionNB);
        return l;
    }
    public Accommodation getAccommodation(Long receptionId){
        Criteria criterion = getSession().createCriteria(Accommodation.class);
        /*criterion.add(Restrictions.eq("isActive",true));*/
        criterion.add(Restrictions.eq("receptionId", receptionId));
        List<Accommodation> list=criterion.list();
        return list==null || list.size() == 0 ? null:list.get(0);
    }
    public WorkingLunch getWorkingLunch(Long receptionId){
        Criteria criterion = getSession().createCriteria(WorkingLunch.class);
        /*criterion.add(Restrictions.eq("isActive",true));*/
        criterion.add(Restrictions.eq("receptionId", receptionId));
        List<WorkingLunch> list=criterion.list();
        return list==null || list.size() == 0 ?null:list.get(0);
    }
    public  List<Banquet> getListBanquet(Long receptionId){
        Criteria criterion = getSession().createCriteria(Banquet.class);
        /*criterion.add(Restrictions.eq("isActive",true));*/
        criterion.add(Restrictions.eq("receptionId", receptionId));
        List<Banquet> list=criterion.list();
        return list;
    }
}
