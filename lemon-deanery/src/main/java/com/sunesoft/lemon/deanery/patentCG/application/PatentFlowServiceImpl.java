package com.sunesoft.lemon.deanery.patentCG.application;

import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDeaneryUtil;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentDto;
import com.sunesoft.lemon.deanery.patentCG.application.dtos.PatentFlowDto;
import com.sunesoft.lemon.deanery.patentCG.domain.Patent;
import com.sunesoft.lemon.deanery.patentCG.domain.PatentRepository;
import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.deanery.prizewinner.domain.PrizewinnerRepository;
import com.sunesoft.lemon.deanery.projectWorkFlow.domain.FormOpenProjectFile;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by pxj on 2016/8/30.
 */
@Service("patentFlowService")
public class PatentFlowServiceImpl extends FormBase2<Patent, PatentDto> implements PatentFlowService {

    @Autowired
    PatentRepository patentRepository;
    @Autowired
    PrizewinnerRepository prizewinnerRepository;

    @Override
    protected CommonResult save(Patent patent) {
        patentRepository.save(patent);
        return ResultFactory.commonSuccess();

    }

    @Override
    protected CommonResult update(Patent patent) {
        return null;
    }

    @Override
    protected Patent ConvetDto(PatentDto Dto) {
        return PatentDeaneryUtil.convert(Dto, new Patent());
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
    protected Patent getByFormNo(Long formNo) {
        Criteria criterion = getSession().createCriteria(Patent.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("formNo", formNo));
        return (Patent) criterion.uniqueResult();
    }

    @Override
    protected String getSummery(Patent patent) {
        return null;
    }

    /***
     *
     * @param formNo syy_oa_patent的formNo
     * @return   PatentDto
     */
    @Override
    public PatentDto getFormByFormNo(Long formNo) {
        Patent patent=this.getByFormNo(formNo);
        PatentDto dto = PatentDeaneryUtil.convert(patent,new PatentDto());
        List<Prizewinner> prizewinners = prizewinnerRepository.getPrizewinnerByCGName(patent.getId(),"syy_oa_patent");
        dto.setListPrizers(prizewinners);
        if(prizewinners != null && prizewinners.size() > 0){
            dto.setInfo(prizewinners.get(0).getWinnerInfos());
            dto.setListPrizers(prizewinners);
        }
        dto.setWinner_info(prizewinnerRepository.getPrizeWinnerNameInfos(patent.getId(),"syy_oa_patent"));
        return dto;
    }

    /**
     * savePrizeWinner 保存专利发明人
     *
     * @param id          专利表syy_oa_patent的id
     * @param winner_Info 专利发明人id
     */
    @Override
    public void savePrizeWinner(Long id, String winner_Info) {
        String infos[] = winner_Info.split(",");
        for (String in : infos) {
            Prizewinner prizewinner = this.getPrizeWinner(id, winner_Info, in);
            prizewinnerRepository.save(prizewinner);
        }
    }

    @Override
    public List<PatentFlowDto> queryProject() {
        return null;
    }

    @Override
    public CommonResult nextProject(ApproveFormDto dto) {
        return super.doApprove(dto, null);
    }

    /***
     * findPatentId 查询专利表syy_oa_patent的id
     *
     * @param fromNo 专利表syy_oa_patent的fromNo
     * @return 专利表syy_oa_patent的id
     */
    @Override
    public Long findPatentId(Long fromNo) {
        return patentRepository.findPatentId(fromNo);
    }

    public Prizewinner getPrizeWinner(Long id, String winner_Info, String in) {
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
