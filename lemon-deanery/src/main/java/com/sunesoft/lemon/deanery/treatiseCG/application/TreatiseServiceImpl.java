package com.sunesoft.lemon.deanery.treatiseCG.application;

import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.patentCG.domain.Patent;
import com.sunesoft.lemon.deanery.prizewinner.application.dtos.PrizewinnerDto;
import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.deanery.prizewinner.domain.PrizewinnerRepository;
import com.sunesoft.lemon.deanery.projectCG.application.ProjectResultService;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResult;
import com.sunesoft.lemon.deanery.treatiseCG.application.criteria.TreatiseCriteria;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseDto;
import com.sunesoft.lemon.deanery.treatiseCG.application.dtos.TreatiseFileDto;
import com.sunesoft.lemon.deanery.treatiseCG.domain.Treatise;
import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseFile;
import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseFileRepository;
import com.sunesoft.lemon.deanery.treatiseCG.domain.TreatiseRepository;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormList;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * Edit by jiangkefan on 2016/7/8
 */
@Service("treatiseService")
public class TreatiseServiceImpl extends FormBase2<Treatise,TreatiseDto> implements TreatiseService {
    @Autowired
    public TreatiseRepository treatiseRepository;

    @Autowired
    public PrizewinnerRepository prizewinnerRepository;

    @Autowired
    public TreatiseFileRepository treatiseFileRepository;

    /**
     * 更新或新增论著
     * @param treatiseDto
     * @return
     */
    @Override
    public Long addOrUpdate(TreatiseDto treatiseDto) {
        Long id = 0L;
        String info = treatiseDto.getWinner_info();
        Treatise treatise =  DeaneryUtil.converFromListTreatiseDto(treatiseDto);
        treatise.setIsActive(true);
        Prizewinner prizewinner = null;
        /**
         * 判断ID是否为空，进行新增或者修改操作
         */
        if(null == treatise.getId() || treatise.getId().equals(0l)){
            id = treatiseRepository.save(treatise);
        }
        /**
         * 修改
         */
        else{
            id = treatiseDto.getId();
            treatiseRepository.save(treatise);
            //加载PrizeWinner类，和对应的表，以及，syy_oa_treatise、syy_oa_prizewinner的id和cgId的关联关系
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            criteria.add(Restrictions.eq("cgName", "syy_oa_treatise"));
//            criteria.add(Restrictions.eq("cgId", treatiseDto.getId()));
            //以sortNo进行升序
//            criteria.addOrder(Order.asc("sortNo"));
            List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id,"syy_oa_treatise");
            //修改完syy_oa_treatise，然后删除对应的syy_oa_prizewinner
            for (Prizewinner p : prizewinners) {
                prizewinnerRepository.delete(p.getId());
            }
        }
        //删除对应的syy_oa_prizewinner，新增刚才syy_oa_treatise对应的syy_oa_prizewinner
        String[] infos = info.split(",");
        for (String in : infos) {
            prizewinner = this.getPrizeWinner(id,info, in);
            prizewinnerRepository.save(prizewinner);
            System.out.print(prizewinner);
        }
        return id;
    }

    /**
     * 批量删除论著信息
     * @param treaiseIds
     * @return
     */
    @Override
    public boolean delete(Long[] treaiseIds) {
        Criteria criteria  = getSession().createCriteria(Treatise.class);
        if(treaiseIds != null && treaiseIds.length > 0){
            criteria.add(Restrictions.in("id",treaiseIds));
        }
        List<Treatise> tLists = criteria.list();
        for(Treatise treatise : tLists){
            treatise.setIsActive(false);
            treatiseRepository.save(treatise);
        }
        //删除syy_oa_treatise之后，删除对应的syy_oa_prizewinner
        for(Long tId:treaiseIds){
//            Criteria critera =  getSession().createCriteria(Prizewinner.class);
//            Treatise treatise = treatiseRepository.get(tId);
//            critera.add(Restrictions.eq("cgId",treatise.getId()));
//            critera.add(Restrictions.eq("cgName","syy_oa_treatise"));
            List<Prizewinner> pLists =prizewinnerRepository.getPrizewinnerByCGName(tId, "syy_oa_treatise");
            for (Prizewinner p: pLists){
//                prizewinnerRepository.delete(p.getId());
                p.setIsActive(false); //软删除
            }
        }
        return true;
    }

    /**
     * 展示论著信息
     * @param treatiseCriteria
     * @return
     */
    @Override
    public PagedResult<TreatiseDto> getTreatise(TreatiseCriteria treatiseCriteria) {


        Criteria criterion = getSession().createCriteria(Treatise.class);
        criterion.add(Restrictions.eq("isActive", true));

        String tName = treatiseCriteria.getTreatise_Name();
        Date bDate = treatiseCriteria.getBegin_Date();
        Date eDate = treatiseCriteria.getEnd_Date();
        Date pDt = treatiseCriteria.getPublish_Time();
        String tLevel = treatiseCriteria.getTreatise_Level();
        String tPress = treatiseCriteria.getTreatise_Press();
        Boolean iCore = treatiseCriteria.getIs_Core();
        String unit = treatiseCriteria.getUnit();
        String mNo = treatiseCriteria.getMake_No();
        Boolean isCoop = treatiseCriteria.getIs_cooperate();

        if(null != tName && tName.length() > 0) {
            criterion.add(Restrictions.like("treatise_Name",tName, MatchMode.ANYWHERE));
        }
        if(null != bDate){
            criterion.add(Restrictions.ge("publish_Time",bDate));
        }
        if(null != eDate ){
            criterion.add(Restrictions.le("publish_Time",eDate));
        }
        if(null != tLevel && !"".equals(tLevel)){
            criterion.add(Restrictions.eq("treatise_Level",tLevel));
        }
        if(null != tPress && tPress.length() > 0){
            criterion.add(Restrictions.like("treatise_Press",tPress,MatchMode.ANYWHERE));
        }
        if(null != iCore && !"".equals(iCore)){
            criterion.add(Restrictions.eq("is_Core", iCore));
        }
        if(null != unit && unit.length()>0){
            criterion.add(Restrictions.like("unit",unit,MatchMode.ANYWHERE));
        }
        if(null != mNo && mNo.length()>0){
            criterion.add(Restrictions.like("make_No",mNo,MatchMode.ANYWHERE));
        }
        if(null != isCoop && !"".equals(isCoop)){
            criterion.add(Restrictions.eq("is_cooperate", isCoop));
        }
        criterion.add(Restrictions.eq("isComplete",true));
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.addOrder(Order.desc("publish_Time"));
        criterion.setFirstResult((treatiseCriteria.getPageNumber() - 1) * treatiseCriteria.getPageSize()).setMaxResults(treatiseCriteria.getPageSize());

        List<Treatise> ts = criterion.list();
        List<TreatiseDto> list = new ArrayList<TreatiseDto>();
        /**
         * 把syy_oa_prizewinner中的数据放入论著的DTO
         */
        for (Treatise t : ts) {
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            criteria.add(Restrictions.eq("cgName", "syy_oa_treatise"));
//            criteria.add(Restrictions.eq("cgId", t.getId()));
            //排序
//            criteria.addOrder(Order.asc("sortNo"));
            List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(t.getId(),"syy_oa_treatise");
            TreatiseDto treatiseDto = DeaneryUtil.converFromListTreatiseDto(t);
            if(prizewinners.size() > 0 ){
                treatiseDto.setListPrizes(prizewinners);
            }
            if(prizewinners != null && prizewinners.size() > 0){
                treatiseDto.setInfo(prizewinners.get(0).getWinnerInfos());
            }
            treatiseDto.setWinner_info(prizewinnerRepository.getPrizeWinnerNameInfos(t.getId(),"syy_oa_treatise"));
            list.add(treatiseDto);
         }

        return new PagedResult<TreatiseDto>(list, treatiseCriteria.getPageNumber(), treatiseCriteria.getPageSize(), totalCount);
    }

    /**
     * 根据ID查询对象
     * @param treatiseId
     * @return
     */
    @Override
    public TreatiseDto getByIdTreatise(Long treatiseId) {
         Treatise treatise = treatiseRepository.get(treatiseId);
         TreatiseDto treatiseDto =  DeaneryUtil.converFromListTreatiseDto(treatise);

//         Criteria criteria = getSession().createCriteria(Prizewinner.class);
//         criteria.add(Restrictions.eq("cgName","syy_oa_treatise"));
//         criteria.add(Restrictions.eq("cgId",treatiseId));
        //排序
//        criteria.addOrder(Order.asc("sortNo"));
         List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(treatiseId, "syy_oa_treatise");
         if(prizewinners.size() > 0 ){
             treatiseDto.setListPrizes(prizewinners);
             treatiseDto.setInfo(prizewinners.get(0).getWinnerInfos());
         }
        treatiseDto.setWinner_info(prizewinnerRepository.getPrizeWinnerNameInfos(treatiseId,"syy_oa_treatise"));

         return treatiseDto;
    }


    private boolean getReportTreatiseId(Long treatiseId){
       int n = 0;
       List<Treatise> list =  treatiseRepository.getAllTreatise();

        for(Treatise treatise : list){

             if(treatise.getId() == treatiseId){
                 n++;
             }
        }
        if(n > 0){
            return false;
        }
        return true;
    }

    private Prizewinner getPrizeWinner(Long id,String winner_Info,String in){
        Prizewinner prizewinner = new Prizewinner();
        prizewinner.setCgName("syy_oa_treatise");
        prizewinner.setCgId(id);
        prizewinner.setWinnerInfos(winner_Info);
        prizewinner.setIsActive(true);
        prizewinner.setLastUpdateTime(new Date());
        String[] ins = in.split("@");
        if (ins.length == 3) {
            prizewinner.setIsOurSystem(ins[0]);
            prizewinner.setSortNo(Integer.valueOf(ins[1]));
            prizewinner.setWinnerId(ins[2]);
        }
        return prizewinner;
    }

    /**
     * 论著统计查询
     * @return
     */
    @Override
    public List<Map<String,Object>> treatiseReport(){
        return treatiseRepository.treatiseReport();
    }

    @Override
    protected CommonResult save(Treatise treatise) {
        treatiseRepository.save(treatise);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(Treatise treatise) {
        return null;
    }

    @Override
    protected Treatise ConvetDto(TreatiseDto Dto) {
        Treatise treatise=DeaneryUtil.converFromListTreatiseDto(Dto);
        return treatise;
    }

    @Override
    protected String getSummery(Treatise treatise) {
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
    protected Treatise getByFormNo(Long formNo) {
        Criteria criteria=getSession().createCriteria(Treatise.class);
        criteria.add(Restrictions.eq("formNo",formNo));
        criteria.add(Restrictions.eq("isActive",true));
        List<ProjectResult> list = criteria.list();
        return (Treatise)criteria.uniqueResult();
    }

    @Override
    public TreatiseDto getFormByFormNo(Long formNo) {
        return null;
    }

    @Override
    public CommonResult addOrUpdate2(TreatiseDto treatiseDto) {
        Long id = 0L;
        String info = treatiseDto.getWinner_info();
        Treatise treatise =  DeaneryUtil.converFromListTreatiseDto(treatiseDto);
        treatise.setIsActive(true);
        Prizewinner prizewinner = null;
        FormList formInfo = formListRepository.getByFormKind(treatise.getFormKind());
        treatise.setFormType(formInfo.getFormType());
        treatise.setHasApplyView(formInfo.getHasApplyView());
        CommonResult result = intiHeader(formInfo, treatise.getFormKind(), treatise.getFormKindName(), treatise.getApplyer(), treatise.getApplyerName(), treatise.getDeptId(), treatise.getBlongDeptId(), treatise.getBlongDeptName(), treatise.getDeptName(), getSummery(treatise), treatise.getViewUrl(), treatise.getParentFormNo());
        if (result.getIsSuccess()) {
            treatise.setFormNo(result.getId());
            /**
             * 判断ID是否为空，进行新增或者修改操作
             */
            if (null == treatise.getId() || treatise.getId().equals(0l)) {
                id = treatiseRepository.save(treatise);
            }
            /**
             * 修改
             */
            else {
                 result=this.updateByDto(treatiseDto);
                //result = formLeaveService.addByDto(treatise);
              // treatiseRepository.save(treatise);
                //加载PrizeWinner类，和对应的表，以及，syy_oa_treatise、syy_oa_prizewinner的id和cgId的关联关系
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            criteria.add(Restrictions.eq("cgName", "syy_oa_treatise"));
//            criteria.add(Restrictions.eq("cgId", treatiseDto.getId()));
                //以sortNo进行升序
//            criteria.addOrder(Order.asc("sortNo"));
                List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id, "syy_oa_treatise");
                //修改完syy_oa_treatise，然后删除对应的syy_oa_prizewinner
                for (Prizewinner p : prizewinners) {
                    prizewinnerRepository.delete(p.getId());
                }
            }
            //删除对应的syy_oa_prizewinner，新增刚才syy_oa_treatise对应的syy_oa_prizewinner
            String[] infos = info.split(",");
            for(int i=0;i<infos.length;i++){
                prizewinner = this.getPrizeWinner(id,info,infos[i]);
                prizewinner.setSortNo((i+1));
                prizewinnerRepository.save(prizewinner);
            }
//            for (String in : infos) {
//                prizewinner = this.getPrizeWinner(id, info, in);
//                prizewinnerRepository.save(prizewinner);
//                System.out.print(prizewinner);
//            }
            if (treatiseDto.getFilesDto()!=null){
            for(TreatiseFileDto files:treatiseDto.getFilesDto()){
                TreatiseFile treatiseFile =  DeaneryUtil.converFromListTreatiseFileDto(files);
                treatiseFile.setTreatiseFileid(id);
                treatiseFileRepository.save(treatiseFile);
            }
            }
            return result;
        }
//        return id;

        return ResultFactory.commonError("新增失败！");
    }

    @Override
    public TreatiseDto getByFormNoTreatise(Long formNo){
        Criteria criteria=getSession().createCriteria(Treatise.class);
        criteria.add(Restrictions.eq("formNo",formNo));
        criteria.add(Restrictions.eq("isActive",true));
        Treatise treatise=(Treatise)criteria.uniqueResult();
        TreatiseDto treatiseDto =  DeaneryUtil.converFromListTreatiseDto(treatise);

//         Criteria criteria = getSession().createCriteria(Prizewinner.class);
//         criteria.add(Restrictions.eq("cgName","syy_oa_treatise"));
//         criteria.add(Restrictions.eq("cgId",treatiseId));
        //排序
//        criteria.addOrder(Order.asc("sortNo"));
        List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(treatise.getId(), "syy_oa_treatise");
        if(prizewinners.size() > 0 ){
            treatiseDto.setListPrizes(prizewinners);
            treatiseDto.setInfo(prizewinners.get(0).getWinnerInfos());
        }
        treatiseDto.setWinner_info(prizewinnerRepository.getPrizeWinnerNameInfos(treatise.getId(),"syy_oa_treatise"));
        List<TreatiseFile> treatiseFiles=treatiseFileRepository.getTreatiseFileByTreatiseFileID(treatise.getId());
        if(prizewinners.size() > 0 ){
            treatiseDto.setTreatisesFiles(treatiseFiles);
        }
        return treatiseDto;
    }

    @Override
    public CommonResult addOrUpdate3(TreatiseDto treatiseDto, ApproveFormDto dto) {
        Long id = 0L;
        String info = treatiseDto.getWinner_info();
        Treatise treatise =  DeaneryUtil.converFromListTreatiseDto(treatiseDto);
        if(treatiseDto.getPublish_EndTime()!=null){
            treatise.setPublish_EndTimes(DateHelper.parse(treatiseDto.getPublish_EndTime(),"yyyy-MM-dd"));
        }
        treatise.setIsActive(true);
        Prizewinner prizewinner = null;
        /**
         * 判断ID是否为空，进行新增或者修改操作
         */
        if(null == treatise.getId() || treatise.getId().equals(0l)){
            id = treatiseRepository.save(treatise);
        }
        /**
         * 修改
         */
        else{
           // id = treatiseDto.getId();
            //treatiseRepository.save(treatise);
            //加载PrizeWinner类，和对应的表，以及，syy_oa_treatise、syy_oa_prizewinner的id和cgId的关联关系
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            criteria.add(Restrictions.eq("cgName", "syy_oa_treatise"));
//            criteria.add(Restrictions.eq("cgId", treatiseDto.getId()));
            //以sortNo进行升序
//            criteria.addOrder(Order.asc("sortNo"));
          //  List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id,"syy_oa_treatise");
            //修改完syy_oa_treatise，然后删除对应的syy_oa_prizewinner
       /*     for (Prizewinner p : prizewinners) {
                prizewinnerRepository.delete(p.getId());
            }*/
        }
        //删除对应的syy_oa_prizewinner，新增刚才syy_oa_treatise对应的syy_oa_prizewinner
/*        String[] infos = info.split(",");
        for (String in : infos) {
            prizewinner = this.getPrizeWinner(id,info, in);
            prizewinnerRepository.save(prizewinner);
            System.out.print(prizewinner);
        }*/
        return super.doApprove(dto,null);
    }

    @Override
    public boolean findTreatise(TreatiseDto treatiseDto) {
       boolean b=false;
        List<Treatise> list=treatiseRepository.findTreatise(treatiseDto);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getClStep()==1&&list.get(i).getFormStatus().equals(FormStatus.WD)){
                list.remove(i);
            }
        }
        if(list.size()==0){
                b=true;
            }
        return b;
    }

    @Override
    public List<TreatiseDto> query_ByApplyer(Long id) {
        Criteria criterion = getSession().createCriteria(Treatise.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("isComplete",true));
        criterion.add(Restrictions.eq("applyer", id));
        List<Treatise> ts = criterion.list();
        List<TreatiseDto> list = new ArrayList<TreatiseDto>();
        for (Treatise t : ts) {
            TreatiseDto treatiseDto = DeaneryUtil.converFromListTreatiseDto(t);
            List<TreatiseFile> treatiseFiles=treatiseFileRepository.getTreatiseFileByTreatiseFileID(t.getId());
            if(treatiseFiles.size() > 0 ){
                treatiseDto.setTreatisesFiles(treatiseFiles);
            }
            list.add(treatiseDto);
        }
        return list;
    }
}
