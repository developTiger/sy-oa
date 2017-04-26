package com.sunesoft.lemon.deanery.projectCG.application;

import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.deanery.prizewinner.domain.PrizewinnerRepository;
import com.sunesoft.lemon.deanery.projectCG.application.criteria.ProjectResultCriteria;
import com.sunesoft.lemon.deanery.projectCG.application.dtos.ProjectResultDto;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResult;
import com.sunesoft.lemon.deanery.projectCG.domain.ProjectResultRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormList;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * Edit by jiangkefan on 2016/7/8
 */
@Service("projectResultService")
public class ProjectResultServiceImpl  extends FormBase2<ProjectResult,ProjectResultDto>
        implements ProjectResultService {

    @Autowired
    public ProjectResultRepository projectResultRepository;

    @Autowired
    public PrizewinnerRepository prizewinnerRepository;



    @Override
    public ProjectResultDto getById(Long id) {
        ProjectResult projectResult = projectResultRepository.get(id);
        ProjectResultDto projectResultDto = DeaneryUtil.converFromListProjectResultDto(projectResult);
        List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id,"syy_oa_project_result");
        if(prizewinners != null && prizewinners.size() > 0){
            projectResultDto.setInfo(prizewinners.get(0).getWinnerInfos());
            projectResultDto.setListPrizes(prizewinners);
        }
        projectResultDto.setWinner_Info(prizewinnerRepository.getPrizeWinnerNameInfos(id,"syy_oa_project_result"));
        return projectResultDto;
    }
    @Override
    public ProjectResultDto getProjectResultByFormNo(Long formNo){
        Criteria criteria=getSession().createCriteria(ProjectResult.class);
        criteria.add(Restrictions.eq("formNo",formNo));
        ProjectResult projectResult=(ProjectResult)criteria.uniqueResult();
        ProjectResultDto projectResultDto = DeaneryUtil.converFromListProjectResultDto(projectResult);
        List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(projectResult.getId(),"syy_oa_project_result");
        if(prizewinners != null && prizewinners.size() > 0){
            projectResultDto.setInfo(prizewinners.get(0).getWinnerInfos());
            projectResultDto.setListPrizes(prizewinners);
        }
        projectResultDto.setWinner_Info(prizewinnerRepository.getPrizeWinnerNameInfos(projectResult.getId(),"syy_oa_project_result"));
        return projectResultDto;
    }
    @Override
    public Long addOrUpdateProjectResult(ProjectResultDto projectResultDto) {
        Long id = 0l;
        String winner_Info = projectResultDto.getWinner_Info();
        ProjectResult projectResult = DeaneryUtil.converFromListProjectResultDto(projectResultDto);
        projectResult.setIsActive(true);
        Prizewinner prizewinner = null;
        //判断是否新增，ID空就是新增
        if( null == projectResult.getId() || projectResult.getId().equals(0l)){
            id = projectResultRepository.save(projectResult);
        }else{
            id = projectResultDto.getId();
            projectResultRepository.save(projectResult);
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            criteria.add(Restrictions.eq("cgName", "syy_oa_project_result"));
//            criteria.add(Restrictions.eq("cgId", projectResultDto.getId()));
            List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id,"syy_oa_project_result");
            for (Prizewinner p : prizewinners) {
                prizewinnerRepository.delete(p.getId());
            }
        }
        /**
         * 插入信息表
         */
        String[] infos = winner_Info.split(",");
//        for (String in : infos) {
//            prizewinner = this.getPrizeWinner(id,winner_Info,in);
//            prizewinnerRepository.save(prizewinner);
//        }
        for(int i=0;i<infos.length;i++){
            prizewinner = this.getPrizeWinner(id,winner_Info,infos[i]);
            prizewinner.setSortNo((i+1));
            prizewinnerRepository.save(prizewinner);
        }
        return id;
    }

    @Override
    public boolean deleteProjectResult(Long[] ids) {
        Criteria criteria = getSession().createCriteria(ProjectResult.class);
        if(ids.length>0 && ids != null){
            criteria.add(Restrictions.in("id",ids));
        }
        List<ProjectResult> lists = criteria.list();
        for(ProjectResult projectResult : lists){
            projectResult.setIsActive(false);
            projectResultRepository.save(projectResult);
        }
        return true;
    }

    @Override
    public PagedResult<ProjectResultDto> getCommonDrivers(ProjectResultCriteria projectResultCriteria) throws UnsupportedEncodingException {
        Criteria criterion = getSession().createCriteria(ProjectResult.class);
        criterion.add(Restrictions.eq("isActive", true));
        String wrn = URLDecoder.decode(projectResultCriteria.getWin_Result_Name(), "UTF-8");
        String iu = projectResultCriteria.getIssuing_Unit();
        String cn = projectResultCriteria.getCertif_No();
        String wl = projectResultCriteria.getWin_Level();
        String wg = projectResultCriteria.getWin_Grade();
        Date wbd = projectResultCriteria.getWin_begin_date();
        Date wed = projectResultCriteria.getWin_end_date();
        Boolean icr = projectResultCriteria.getIs_Cooperate_Result();
        if( null != wrn && wrn.length() > 0 ){
            criterion.add(Restrictions.like("win_Result_Name",wrn, MatchMode.ANYWHERE));
        }
        if( null != iu && iu.length() > 0 ){
            criterion.add(Restrictions.like("issuing_Unit",iu, MatchMode.ANYWHERE));
        }
        if( null != cn && cn.length() > 0 ){
            criterion.add(Restrictions.like("certif_No",cn, MatchMode.ANYWHERE));
        }
        if( null != wl && wl.length() > 0 ){
            criterion.add(Restrictions.eq("win_Level", wl));
        }
        if( null != wg && wg.length() > 0 ){
            criterion.add(Restrictions.eq("win_Grade",wg));
        }
        if( null != wbd ){
            criterion.add(Restrictions.ge("win_Date",wbd));
        }
        if( null != wed ){
            criterion.add(Restrictions.le("win_Date",wed));
        }
        if( null != icr ){
            criterion.add(Restrictions.eq("is_Cooperate_Result",icr));
        }
        int totalCount = ((Long)criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((projectResultCriteria.getPageNumber() - 1) * projectResultCriteria.getPageSize()).setMaxResults(projectResultCriteria.getPageSize());
        List<ProjectResult> list = criterion.list();
        List<ProjectResultDto> projectResultDtos = new ArrayList<ProjectResultDto>();
        for (ProjectResult p : list){
            projectResultDtos.add(DeaneryUtil.converFromListProjectResultDto(p));
        }
        return new PagedResult<ProjectResultDto>(projectResultDtos,projectResultCriteria.getPageNumber(),projectResultCriteria.getPageSize(),totalCount);
    }


    /**
     * 解析数据返回对象
     */
    private Prizewinner getPrizeWinner(Long id,String winner_Info,String in){
        Prizewinner prizewinner = new Prizewinner();
        prizewinner.setCgName("syy_oa_project_result");
        prizewinner.setCgId(id);
        prizewinner.setWinnerInfos(winner_Info);
        prizewinner.setIsActive(true);
        prizewinner.setLastUpdateTime(new Date());
        String[] ins = in.split("@");
        if (ins.length == 2) {
            prizewinner.setIsOurSystem(ins[0]);
//            prizewinner.setSortNo(Integer.valueOf(ins[1]));
            prizewinner.setWinnerId(ins[1]);
        }
        return prizewinner;
    }


    public List<Map<String,Object>> projectCGReport(){
        return projectResultRepository.projectCGReport();
    }

    @Override
    public CommonResult addOrUpdateProjectResult2(ProjectResultDto projectResultDto) {
        Long id = 0l;
        String winner_Info = projectResultDto.getWinner_Info();
        ProjectResult projectResult = DeaneryUtil.converFromListProjectResultDto(projectResultDto);
        projectResult.setIsActive(true);
        Prizewinner prizewinner = null;

        FormList formInfo = formListRepository.getByFormKind(projectResult.getFormKind());
        projectResult.setFormType(formInfo.getFormType());
        projectResult.setHasApplyView(formInfo.getHasApplyView());
        CommonResult result = intiHeader(formInfo, projectResult.getFormKind(), projectResult.getFormKindName(), projectResult.getApplyer(), projectResult.getApplyerName(), projectResult.getDeptId(), projectResult.getBlongDeptId(), projectResult.getBlongDeptName(), projectResult.getDeptName(), getSummery(projectResult), projectResult.getViewUrl(), projectResult.getParentFormNo());
        if (result.getIsSuccess()) {
            projectResult.setFormNo(result.getId());
            //判断是否新增，ID空就是新增
            if( null == projectResult.getId() || projectResult.getId().equals(0l)){
                id = projectResultRepository.save(projectResult);
            }else{
                id = projectResultDto.getId();
                projectResultRepository.save(projectResult);
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            criteria.add(Restrictions.eq("cgName", "syy_oa_project_result"));
//            criteria.add(Restrictions.eq("cgId", projectResultDto.getId()));
                List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id,"syy_oa_project_result");
                for (Prizewinner p : prizewinners) {
                    prizewinnerRepository.delete(p.getId());
                }
            }
            /**
             * 插入信息表
             */
            String[] infos = winner_Info.split(",");
//            for (String in : infos) {
//                prizewinner = this.getPrizeWinner(id,winner_Info,in);
//                prizewinnerRepository.save(prizewinner);
//            }
            for(int i=0;i<infos.length;i++){
                prizewinner = this.getPrizeWinner(id,winner_Info,infos[i]);
                prizewinner.setSortNo((i+1));
                prizewinnerRepository.save(prizewinner);
            }
            CommonResult commonResult=ResultFactory.commonSuccess();
            if(!commonResult.getIsSuccess()){
                return ResultFactory.commonError("新增失败！");
            }
            return result;
        }
        return ResultFactory.commonError("新增失败！");
    }


    @Override
    public CommonResult addOrUpdateProjectResult3(ProjectResultDto projectResultDto, ApproveFormDto dto){
        Long id = 0l;
        String winner_Info = projectResultDto.getWinner_Info();
        ProjectResult projectResult = DeaneryUtil.converFromListProjectResultDto(projectResultDto);
        projectResult.setIsActive(true);
        Prizewinner prizewinner = null;
        //判断是否新增，ID空就是新增
        if( null == projectResult.getId() || projectResult.getId().equals(0l)){
            id = projectResultRepository.save(projectResult);
        }else{
            id = projectResultDto.getId();
            projectResultRepository.save(projectResult);
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            criteria.add(Restrictions.eq("cgName", "syy_oa_project_result"));
//            criteria.add(Restrictions.eq("cgId", projectResultDto.getId()));
            List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id,"syy_oa_project_result");
            for (Prizewinner p : prizewinners) {
                prizewinnerRepository.delete(p.getId());
            }
        }
        /**
         * 插入信息表
         */
        String[] infos = winner_Info.split(",");
//        for (String in : infos) {
//            prizewinner = this.getPrizeWinner(id,winner_Info,in);
//            prizewinnerRepository.save(prizewinner);
//        }
        for(int i=0;i<infos.length;i++){
            prizewinner = this.getPrizeWinner(id,winner_Info,infos[i]);
            prizewinner.setSortNo((i+1));
            prizewinnerRepository.save(prizewinner);
        }
        return super.doApprove(dto,null);
    }
    @Override
    protected CommonResult save(ProjectResult projectResult) {
        projectResultRepository.save(projectResult);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(ProjectResult projectResult) {
        return null;
    }

    @Override
    protected ProjectResult ConvetDto(ProjectResultDto Dto) {
        return null;
    }

    @Override
    protected String getSummery(ProjectResult projectResult) {
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
    protected ProjectResult getByFormNo(Long formNo) {
        Criteria criteria=getSession().createCriteria(ProjectResult.class);
        criteria.add(Restrictions.eq("formNo",formNo));
        criteria.add(Restrictions.eq("isActive",true));
        List<ProjectResult> list = criteria.list();
        return (ProjectResult)criteria.uniqueResult();
    }

    @Override
    public ProjectResultDto getFormByFormNo(Long formNo) {
        return null;
    }
}
