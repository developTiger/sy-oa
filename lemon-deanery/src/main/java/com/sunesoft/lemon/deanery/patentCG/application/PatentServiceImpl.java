package com.sunesoft.lemon.deanery.patentCG.application;

import com.sunesoft.lemon.deanery.car.application.factory.DeaneryUtil;
import com.sunesoft.lemon.deanery.patentCG.application.criteria.PatentCriteria;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.patentCG.domain.Patent;
import com.sunesoft.lemon.deanery.patentCG.domain.PatentRepository;

import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.deanery.prizewinner.domain.PrizewinnerRepository;
import com.sunesoft.lemon.deanery.treatiseCG.domain.Treatise;
import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xubo on 2016/7/6 0006.
 * Edit by jiangkefan on 2016/7/7
 */
@Service("patentService")
public class PatentServiceImpl extends GenericHibernateFinder implements PatentService {

    @Autowired
    PatentRepository patentRepository;

    @Autowired
    PrizewinnerRepository prizewinnerRepository;

    @Override
    public PatentDto getByIdPatent(Long patentId) {
        Patent patent = patentRepository.get(patentId);
        PatentDto patentDto = DeaneryUtil.converFromListPatentDto(patent);

//        Criteria criteria = getSession().createCriteria(Prizewinner.class);
//        criteria.add(Restrictions.eq("cgName","syy_oa_patent"));
//        criteria.add(Restrictions.eq("cgId",patentId));
//        criteria.addOrder(Order.asc("sortNo"));
        List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(patentId,"syy_oa_patent");
        patentDto.setListPrizers(prizewinners);
        if(prizewinners != null && prizewinners.size() > 0){
            patentDto.setInfo(prizewinners.get(0).getWinnerInfos());
            patentDto.setListPrizers(prizewinners);
        }
        patentDto.setWinner_info(prizewinnerRepository.getPrizeWinnerNameInfos(patentId,"syy_oa_patent"));
        return patentDto;
    }


    @Override
    public boolean delPatent(Long[] patentIds) {
        Criteria criterion = getSession().createCriteria(Patent.class);
        if (patentIds != null && patentIds.length > 0) {
            criterion.add(Restrictions.in("id", patentIds));
        }
        List<Patent> beans = criterion.list();

        for (Patent bean : beans) {
            bean.setIsActive(false);
            patentRepository.save(bean);
        }

        for(Long pId : patentIds){
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            Patent patent =  patentRepository.get(pId);
//            PatentDto patentDto =DeaneryUtil.converFromListPatentDto(patent);
//            criteria.add(Restrictions.eq("cgId", patentDto.getId()));
//            criteria.add(Restrictions.eq("cgName", "syy_oa_patent"));
            List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(pId,"syy_oa_patent");
            for (Prizewinner prizewinner : prizewinners) {
//                prizewinnerRepository.delete(prizewinner.getId());
                prizewinner.setIsActive(false);
            }
        }

        return true;
    }

    @Override
    public Long addOrUpdatePatent(PatentDto patentDto){
        Long id = 0L;
        String info = patentDto.getWinner_info();
        Patent patent = DeaneryUtil.converFromListPatentDto(patentDto);
        patent.setIsActive(true);
        Prizewinner prizewinner = null;
        if(null == patent.getId() || patent.getId().equals(0l)){
            id = patentRepository.save(patent);
        }
        else{
            id = patentDto.getId();
            patentRepository.save(patent);
//            Criteria criteria = getSession().createCriteria(Prizewinner.class);
//            criteria.add(Restrictions.eq("cgName", "syy_oa_patent"));
//            criteria.add(Restrictions.eq("cgId", patentDto.getId()));
//            criteria.addOrder(Order.asc("sortNo"));
            List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(id,"syy_oa_patent");
            for(Prizewinner p : prizewinners){
                prizewinnerRepository.delete(p.getId());
            }

        }
        String[] infos = info.split(",");
        for (String in : infos) {
            prizewinner = this.getPrizeWinner(id,info, in);
            prizewinnerRepository.save(prizewinner);
        }
        return id;

    }

    @Override
    public PagedResult<PatentDto> getPatent(PatentCriteria patentCriteria) {
        Criteria criterion = getSession().createCriteria(Patent.class);
        criterion.add(Restrictions.eq("isActive", true));

        String pName = patentCriteria.getPatent_Name();
        if(null != pName && pName.length() > 0){
            criterion.add(Restrictions.like("patent_Name",pName, MatchMode.ANYWHERE));
        }
        String appNo = patentCriteria.getApp_No();
        if(null != appNo && appNo.length() >0){
            criterion.add(Restrictions.like("app_No",appNo, MatchMode.ANYWHERE));
        }
        String pType = patentCriteria.getPatent_Type();
        if(null != pType && pType.length() > 0){
            criterion.add(Restrictions.like("patent_Type",pType,MatchMode.ANYWHERE));
        }
//        Date aDate = patentCriteria.getApply_Date();
//        if(null != aDate){
//            criterion.add(Restrictions.eq("apply_date",aDate));
//        }
        Date bDate = patentCriteria.getBeginDate();
        if(null != bDate){
            criterion.add(Restrictions.ge("apply_Date", bDate));
        }
        Date eDate = patentCriteria.getEndDate();
        if(null != eDate){
            criterion.add(Restrictions.le("apply_Date", eDate));
        }

        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((patentCriteria.getPageNumber() - 1) * patentCriteria.getPageSize()).setMaxResults(patentCriteria.getPageSize());
        List<Patent> patents = criterion.list();
        List<PatentDto> list = new ArrayList<PatentDto>();
        for (Patent p : patents) {
            list.add(DeaneryUtil.converFromListPatentDto(p));
        }
        return new PagedResult<PatentDto>(list, patentCriteria.getPageNumber(), patentCriteria.getPageSize(), totalCount);
    }

    /**
     * 统计
     * @return
     */
    @Override
    public List<Map<String, Object>> patentReport() {
        return patentRepository.patentReport();
    }


    private boolean getReportId(Long id){
        List<Patent> list = patentRepository.getAllPatents();
        int n= 0;
        for(int i = 0 ; i < list.size() ; i++){
            if(list.get(i).getId() == id){
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
        prizewinner.setCgName("syy_oa_patent");
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
}


