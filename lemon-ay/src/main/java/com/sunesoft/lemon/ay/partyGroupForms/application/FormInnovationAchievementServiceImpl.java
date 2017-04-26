package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.InnovationAchievementCriteria;
import com.sunesoft.lemon.ay.partyGroup.domain.InnovationAchievement;
import com.sunesoft.lemon.ay.partyGroup.domain.InnovationAchievementRepository;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormInnovationAchievementDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormInnovationAchievement;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormInnovationAchievementRepository;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.hibernate.Criteria;
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
 * Created by admin on 2016/9/3.
 */
@Service("formInnovationAchievementService")
public class FormInnovationAchievementServiceImpl extends FormBase2<FormInnovationAchievement,FormInnovationAchievementDto> implements FormInnovationAchievementService {

    @Autowired
    FormInnovationAchievementRepository formInnovationAchievementRepository;

    @Autowired
    InnovationAchievementRepository innovationAchievementRepository;


    @Override
    protected CommonResult save(FormInnovationAchievement achievement) {
        formInnovationAchievementRepository.save(achievement);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormInnovationAchievement achievement) {
        return null;
    }

    @Override
    protected FormInnovationAchievement ConvetDto(FormInnovationAchievementDto Dto) {
        FormInnovationAchievement formInnovationAchievement = DtoFactory.convert(Dto,new FormInnovationAchievement());
        return formInnovationAchievement;
    }

    @Override
    protected String getSummery(FormInnovationAchievement achievement) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormInnovationAchievement formInnovationAchievement= this.getByFormNo(formNo);
        InnovationAchievement innovationAchievement = DtoFactory.convert(formInnovationAchievement,new InnovationAchievement());
        innovationAchievement.setId(null);
        innovationAchievementRepository.save(innovationAchievement);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormInnovationAchievement getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormInnovationAchievement.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("formNo",formNo));
        return (FormInnovationAchievement)criteria.uniqueResult();
    }

    @Override
    public FormInnovationAchievementDto getFormByFormNo(Long formNo) {
        FormInnovationAchievement formInnovationAchievement = this.getByFormNo(formNo);
        FormInnovationAchievementDto formInnovationAchievementDto = DtoFactory.convert(formInnovationAchievement,new FormInnovationAchievementDto());
        return formInnovationAchievementDto;
    }

    @Override
    public PagedResult<FormInnovationAchievementDto> getPagesFormsByFailAll(InnovationAchievementCriteria achievementCriteria) {
        Criteria criteria = getSession().createCriteria(FormInnovationAchievement.class);
        criteria.add(Restrictions.eq("isActive",true));

        if (!StringUtils.isNullOrWhiteSpace(achievementCriteria.getProjectName()))
            criteria.add(Restrictions.like("projectName","%"+achievementCriteria.getProjectName()+"%"));
        if (!StringUtils.isNullOrWhiteSpace(achievementCriteria.getDeptName()))
            criteria.add(Restrictions.eq("applyUnit", achievementCriteria.getProjectName()));
        if (achievementCriteria.getEducationDegree()!= null)
            criteria.add(Restrictions.eq("educationDegree", achievementCriteria.getEducationDegree()));

        if (achievementCriteria.getBeginTime() != null)
            criteria.add(Restrictions.ge("achiCreateTime",achievementCriteria.getBeginTime()));
        if (achievementCriteria.getEndTime() != null)
            criteria.add(Restrictions.le("achiCreateTime", achievementCriteria.getEndTime()));

        //添加formStatus字段查询，查询出待签核的表单
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.eq("clStep",Integer.parseInt(achievementCriteria.getStep())));

        int totalCount = ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.addOrder(Order.desc("approveScore"));

        criteria.setFirstResult((achievementCriteria.getPageNumber() - 1) * achievementCriteria.getPageSize()).setMaxResults(achievementCriteria.getPageSize());
        List<FormInnovationAchievement> beans = criteria.list();
        List<FormInnovationAchievementDto> list = new ArrayList<>();
        for (FormInnovationAchievement wp:beans){
            list.add(DtoFactory.convert(wp, new FormInnovationAchievementDto()));
        }
        return new PagedResult<FormInnovationAchievementDto>(list, achievementCriteria.getPageNumber(), achievementCriteria.getPageSize(), totalCount);
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {

        //在service层，只用使用repository，从数据库获取，简单明了
        FormInnovationAchievement formInnovationAchievement = formInnovationAchievementRepository.getByFormNo(dto.getFormNo());

        if ((param.get("clStep").toString().equals("2") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal()))) {
            String hidGroupMemberIds = param.get("hidGroupMemberIds").toString();//组员id
            String hidGroupMemberNames = param.get("hidGroupMemberNames").toString();//组员name

            String groupLeader = param.get("groupLeader").toString();//组长id
            String hidGroupLeaderName = param.get("hidGroupLeaderName").toString();//组长name

            if (!StringUtils.isNullOrWhiteSpace(hidGroupMemberIds)){
                formInnovationAchievement.setGroupMemberIds(hidGroupMemberIds.trim());
                formInnovationAchievement.setGroupMemberNames(hidGroupMemberNames.trim());
            }else{
                return ResultFactory.commonError("请选择成果评审小组组员！");
            }

            if (!StringUtils.isNullOrWhiteSpace(groupLeader)){
                formInnovationAchievement.setGroupLeaderId(groupLeader.trim());
                formInnovationAchievement.setGroupLeaderName(hidGroupLeaderName.trim());
            }else{
                return ResultFactory.commonError("请选择成果评审小组组长！");
            }

            //将T3节点的审核人设置为评审小组成员和评审小组组长
            List<Long> empIds = new ArrayList<>();
            String[] membersId = hidGroupMemberIds.split(",");
            for (String id:membersId){
                empIds.add(Long.parseLong(id));
            }
            empIds.add(Long.parseLong(groupLeader));

            super.resetNextApprover(dto.getFormNo(), empIds);

        }

        //当 step  3 的时候，进行设置
        if ((param.get("clStep").toString().equals("3") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal()))) {
            String approveScore = param.get("approveScore").toString();//评分
            String achiSuggestion = param.get("achiSuggestion").toString();

            if (StringUtils.isNullOrWhiteSpace(approveScore)) {
                return ResultFactory.commonError("请先填写成果评分！");
            }

            for (int i = 0; i < approveScore.length(); i++){
                if (!Character.isDigit(approveScore.charAt(i))){
                    return ResultFactory.commonError("请填写正确格式的成果评分！");
                }
            }

            Integer appScore = Integer.parseInt(approveScore);
            if (appScore<0 || appScore>100){
                return ResultFactory.commonError("请填写0——100之间的评分！");
            }

            if (Long.parseLong(formInnovationAchievement.getGroupLeaderId())==dto.getApproverId()){
                //组长 最终结果
                if (!StringUtils.isNullOrWhiteSpace(achiSuggestion)){
                    formInnovationAchievement.setAchiSuggestion(achiSuggestion.trim());
                }

                formInnovationAchievement.setApproveScore(approveScore);
            }else {
                //组员 字段展示
                if (!StringUtils.isNullOrWhiteSpace(achiSuggestion)) {
                    //评审意见

                    if (formInnovationAchievement.getInnoOnlyMembersAdvise() == null) {
                        formInnovationAchievement.setInnoOnlyMembersAdvise(dto.getApproverName() + ":" + achiSuggestion.trim());
                    } else {
                        String advise = formInnovationAchievement.getInnoOnlyMembersAdvise();
                        String[] arr_advise = advise.split("//");
                        boolean isExist = false;
                        for (int i = 0; i < arr_advise.length; i++) {
                            //存在
                            if (arr_advise[i].contains(dto.getApproverName())) {
                                arr_advise[i] = dto.getApproverName() + ":" + achiSuggestion.trim();
                                isExist = true;
                            }
                        }
                        String actual_advise = "";
                        for (int i = 0; i < arr_advise.length; i++) {
                            actual_advise = actual_advise + arr_advise[i] + "//";
                        }
                        if (isExist)
                            formInnovationAchievement.setInnoOnlyMembersAdvise(actual_advise);
                        else
                            formInnovationAchievement.setInnoOnlyMembersAdvise(advise + "//" + dto.getApproverName() + ":" + achiSuggestion.trim());

                    }
                }
                if (formInnovationAchievement.getInnoOnlyMembersScores() == null) {
                    formInnovationAchievement.setInnoOnlyMembersScores(dto.getApproverName() + ":" + approveScore);
                } else {
                    String score = formInnovationAchievement.getInnoOnlyMembersScores();
                    String[] arr_scores = score.split("//");
                    boolean isExist = false;
                    for (int i = 0; i < arr_scores.length; i++) {
                        //存在
                        if (arr_scores[i].contains(dto.getApproverName())) {
                            arr_scores[i] = dto.getApproverName() + ":" + approveScore;
                            isExist = true;
                        }
                    }
                    String actual_scores = "";
                    for (int i = 0; i < arr_scores.length; i++) {
                        actual_scores = actual_scores + arr_scores[i] + "//";
                    }
                    if (isExist)
                        formInnovationAchievement.setInnoOnlyMembersScores(actual_scores);
                    else
                        formInnovationAchievement.setInnoOnlyMembersScores(score + "//" + dto.getApproverName() + ":" + approveScore);
                }
            }

//            formInnovationAchievement.setApproveScore(approveScore);
//            formInnovationAchievement.setAchiSuggestion(achiSuggestion.trim());


        }

        if ((param.get("clStep").toString().equals("4") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal()))) {
            String prizeLeval = param.get("prizeLeval").toString();//获奖等级
            String awardDeptId = param.get("awardDeptId").toString();//颁发单位id
            String awardDeptName = param.get("awardDeptName").toString();//颁发单位name



            if (!StringUtils.isNullOrWhiteSpace(prizeLeval)) {
                if (prizeLeval.equals("first"))
                    formInnovationAchievement.setPrizeLeval(PrizeLeval.first);
                if (prizeLeval.equals("second"))
                    formInnovationAchievement.setPrizeLeval(PrizeLeval.second);
                if (prizeLeval.equals("third"))
                    formInnovationAchievement.setPrizeLeval(PrizeLeval.third);
                if (prizeLeval.equals("excellent"))
                    formInnovationAchievement.setPrizeLeval(PrizeLeval.excellent);
                if (prizeLeval.equals("no"))
                    formInnovationAchievement.setPrizeLeval(PrizeLeval.no);
            }

            if (!StringUtils.isNullOrWhiteSpace(awardDeptId)){
                formInnovationAchievement.setAwardDeptmentId(awardDeptId.trim());
                formInnovationAchievement.setAwardDeptment(awardDeptName.trim());
                formInnovationAchievement.setAwardTime(new Date());//颁发时间
            }else {
                return ResultFactory.commonError("请选择颁发单位！");
            }



        }


        formInnovationAchievementRepository.save(formInnovationAchievement);

//        return doApproveOk(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        return super.doApprove(dto, param);
    }


    @Override
    public CommonResult doApproveOk(Long formNo, String formKind, Long approverId, String approver, String content, List<Long> agentId) throws Exception {
        FormHeader header = formHeaderRepository.get(formNo);
        CommonResult result = header.approve2(approverId, approver, content, agentId);

        // step
        //如果审核人是组长

        // 更新当前节点未审核人FormApprover的状态   P

        //设置 header IsCurrentPiontComplete=true;
        if (header.getClStep()==3){
            FormInnovationAchievementDto dto = this.getFormByFormNo(formNo);
            if (Long.parseLong(dto.getGroupLeaderId()) == approverId){
                for (FormApprover formApprover:header.getFormApprovers()){
                    //待签核
                    if (formApprover.getApproveStatus().equals(ApproveStatus.W)){
                        formApprover.setApproveStatus(ApproveStatus.P);
                    }
                }
                header.setIsCurrentPiontComplete(true);
            }
        }

        if (header.getIsCurrentPiontComplete())
            header = this.activeNextApprover(header);

        if (result.getIsSuccess()) {
            if (header.getFormStatus() == FormStatus.AP) {


                result = doAafterAllApproved(formNo);

            } else
                result = doAafterApprovedStep(formNo, result.getId());
        }
        if (result != null && !result.getIsSuccess()) {
            throw new Exception(result.getMsg());
        }
        if (formHeaderRepository.save(header) > 0) {
            result = updateFormStstus(header.getId(), header.getClStep(), header.getIsStepComplete(), header.getFormStatus(), header.getCurrentApproveAction(), header.getCurrentViewAction());
            //subFormOperate(header.getId(),true,"ceshi",0);
            return result;
        } else
            return ResultFactory.commonError("签核失败");
    }

}
