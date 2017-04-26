package com.sunesoft.lemon.ay.partyGroupForms.application;

import com.sunesoft.lemon.ay.equipment.application.factory.DtoFactory;
import com.sunesoft.lemon.ay.partyGroup.application.criteria.FormWorkAchievementCriteria;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievements;
import com.sunesoft.lemon.ay.partyGroup.domain.WorkAchievementsRepository;
import com.sunesoft.lemon.ay.partyGroup.domain.enums.PrizeLeval;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkAchievementsDto;
import com.sunesoft.lemon.ay.partyGroupForms.application.dtos.FormWorkProjectDto;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkAchievements;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkAchievementsRepository;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProject;
import com.sunesoft.lemon.ay.partyGroupForms.domain.FormWorkProjectRepository;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.hrForms.domain.DeptAppraise;
import com.sunesoft.lemon.syms.hrForms.domain.DeptAppraiseDetail;
import com.sunesoft.lemon.syms.hrForms.domain.DeptSubAppraise;
import com.sunesoft.lemon.syms.workflow.application.FormBase2;
import com.sunesoft.lemon.syms.workflow.application.dtos.ApproveFormDto;
import com.sunesoft.lemon.syms.workflow.domain.FormApprover;
import com.sunesoft.lemon.syms.workflow.domain.FormHeader;
import com.sunesoft.lemon.syms.workflow.domain.enums.AppValue;
import com.sunesoft.lemon.syms.workflow.domain.enums.ApproveStatus;
import com.sunesoft.lemon.syms.workflow.domain.enums.FormStatus;
import org.apache.poi.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用的还是原来的workAchievement类 不过里面内容是workProject
 * Created by admin on 2016/9/3.
 */
@Service("formWorkAchievementsService")
public class FormWorkAchievementsServiceImpl extends FormBase2<FormWorkProject, FormWorkProjectDto> implements FormWorkAchievementsService {

    @Autowired
    FormWorkAchievementsRepository formWorkAchievementsRepository;

    @Autowired
    WorkAchievementsRepository workAchievementsRepository;
    @Autowired
    FormWorkProjectRepository formWorkProjectRepository;


    @Override
    protected CommonResult save(FormWorkProject formWorkAchievements) {
        formWorkProjectRepository.save(formWorkAchievements);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected CommonResult update(FormWorkProject formWorkAchievements) {
        FormWorkAchievements formWorkAch = new FormWorkAchievements();
        formWorkAch.setProjectName(formWorkAchievements.getProjectName());
        formWorkAch.setLeader(formWorkAchievements.getLeader());
        formWorkAch.setJoinPeople(formWorkAchievements.getJoinPeople());
        formWorkAchievementsRepository.save(formWorkAch);
        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormWorkProject ConvetDto(FormWorkProjectDto Dto) {
        FormWorkProject formWorkAchievements = DtoFactory.convert(Dto, new FormWorkProject());
        return formWorkAchievements;
    }

    @Override
    protected String getSummery(FormWorkProject formWorkAchievements) {
        return null;
    }

    @Override
    public CommonResult doAafterApprovedStep(Long formNo, Long step) {
        return null;
    }

    @Override
    public CommonResult doAafterAllApproved(Long formNo) {
        FormWorkProject formWorkAchievements = this.getByFormNo(formNo);
        WorkAchievements workAchievements = DtoFactory.convert(formWorkAchievements, new WorkAchievements());
        workAchievements.setId(null);
        workAchievementsRepository.save(workAchievements);

        return ResultFactory.commonSuccess();
    }

    @Override
    protected FormWorkProject getByFormNo(Long formNo) {
        Criteria criteria = getSession().createCriteria(FormWorkProject.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("formNo", formNo));
        return (FormWorkProject) criteria.uniqueResult();
    }

    @Override
    public FormWorkProjectDto getFormByFormNo(Long formNo) {
        FormWorkProject formWorkAchievements = this.getByFormNo(formNo);
        FormWorkProjectDto formWorkAchievementsDto = DtoFactory.convert(formWorkAchievements, new FormWorkProjectDto());
        return formWorkAchievementsDto;
    }

    @Override
    public CommonResult doApprove(ApproveFormDto dto, Map<String, Object> param) {
        //劳动成果申报流程 ，过程中用project取代achievement，最终入库的还是转化为workachievement
        //统计展示数据时，还是将成果的数据覆盖到项目上去，单纯展示，不需保存
        FormWorkProject formWorkProject = formWorkProjectRepository.getByFormNo(dto.getFormNo());

        if (param.get("clStep").toString().equals("2") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())) {
            String achiGroupMembersIds = param.get("hidJudgeMemberids").toString();//评审小组成员 id
            String hiddenJudgeMembers = param.get("hiddenJudgeMembers").toString();//评审小组成员 name

            String achi_leader_id = param.get("achi_leader_id").toString();//小组组长 id
            String hiddenMembersLeader = param.get("hiddenMembersLeader").toString();//小组组长 name

            if (!StringUtils.isNullOrWhiteSpace(achiGroupMembersIds)){
                formWorkProject.setAchiGroupMembersIds(achiGroupMembersIds);
                formWorkProject.setReviewGroupMembers(hiddenJudgeMembers.trim());
            }else{
                return ResultFactory.commonError("请选择成果评审小组成员!");
            }

            if (!StringUtils.isNullOrWhiteSpace(achi_leader_id)){
                formWorkProject.setAchi_leader_id(achi_leader_id);
                formWorkProject.setAchi_judgeMemberLeader(hiddenMembersLeader.trim());
            }else{
                return ResultFactory.commonError("请选择成果评审小组组长！");
            }

            //将T3节点的审核人设置为评审小组成员和评审小组组长
            List<Long> empIds = new ArrayList<>();
            String[] membersId = achiGroupMembersIds.split(",");
            for (String id:membersId){
                empIds.add(Long.parseLong(id));
            }
            empIds.add(Long.parseLong(achi_leader_id));

            super.resetNextApprover(dto.getFormNo(), empIds);

        }

        if (param.get("clStep").toString().equals("3") && param.get("isStepComplete").toString().equals("false") && dto.getAppValue().equals(AppValue.Y.ordinal())) {
            String approveScore = param.get("approveScore").toString();//成果评分

            String achievementJudgeSuggestions = param.get("achievementJudgeSuggestions").toString();//评审意见

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

            if (Long.parseLong(formWorkProject.getAchi_leader_id())==dto.getApproverId()){
                //组长 最终结果
                if (!StringUtils.isNullOrWhiteSpace(achievementJudgeSuggestions)){
                    formWorkProject.setAchievementJudgeSuggestions(achievementJudgeSuggestions.trim());
                }

                formWorkProject.setApproveScore(approveScore);
            }else{
                //组员 字段展示
                if (!StringUtils.isNullOrWhiteSpace(achievementJudgeSuggestions)){
                    //评审意见

                    if (formWorkProject.getAchiOnlyMembersAdvise() == null){
                        formWorkProject.setAchiOnlyMembersAdvise(dto.getApproverName()+":"+achievementJudgeSuggestions.trim());
                    }else{
                        String advise=formWorkProject.getAchiOnlyMembersAdvise();
                        String[] arr_advise = advise.split("//");
                        boolean isExist = false;
                        for (int i = 0; i < arr_advise.length; i++) {
                            //存在
                            if (arr_advise[i].contains(dto.getApproverName())){
                                arr_advise[i]=dto.getApproverName()+":"+achievementJudgeSuggestions.trim();
                                isExist=true;
                            }
                        }
                        String actual_advise = "";
                        for (int i = 0; i < arr_advise.length; i++) {
                            actual_advise = actual_advise+ arr_advise[i]+"//";
                        }
                        if (isExist)
                            formWorkProject.setAchiOnlyMembersAdvise(actual_advise);
                        else
                            formWorkProject.setAchiOnlyMembersAdvise(advise+"//"+dto.getApproverName()+":"+achievementJudgeSuggestions.trim());

                    }
                }
                if (formWorkProject.getAchiOnlyMembersScores() == null){
                    formWorkProject.setAchiOnlyMembersScores(dto.getApproverName()+":"+approveScore);
                }else{
                    String score = formWorkProject.getAchiOnlyMembersScores();
                    String[] arr_scores = score.split("//");
                    boolean isExist = false;
                    for (int i = 0; i < arr_scores.length; i++) {
                        //存在
                        if (arr_scores[i].contains(dto.getApproverName())){
                            arr_scores[i]=dto.getApproverName()+":"+approveScore;
                            isExist = true;
                        }
                    }
                    String actual_scores = "";
                    for (int i = 0; i < arr_scores.length; i++) {
                        actual_scores = actual_scores+ arr_scores[i]+"//";
                    }
                    if (isExist)
                        formWorkProject.setAchiOnlyMembersScores(actual_scores);
                    else
                        formWorkProject.setAchiOnlyMembersScores(score+"//"+dto.getApproverName()+":"+approveScore);
                }
//                formWorkProject.setAchiOnlyMembersAdvise("");
//                formWorkProject.setAchiOnlyMembersScores("");

            }




        }


        if (param.get("clStep").toString().equals("4") && param.get("isStepComplete").toString().equals("false")
                && dto.getAppValue().equals(AppValue.Y.ordinal())) {
//            String nameOrdept = param.get("nameOrdept").toString();
            String prizeLeval = param.get("prizeLevel").toString();
            String prizeName = param.get("prizeName").toString();
            String achiGetTime = param.get("achiGetTime").toString();
            String awardAgency = param.get("awardAgency").toString();

//            if (!StringUtils.isNullOrWhiteSpace(nameOrdept)) {
//                formWorkProject.setNameOrdept(nameOrdept);
//            }else{
//                return ResultFactory.commonError("请填写 姓名/单位名称！");
//            }

            if (!StringUtils.isNullOrWhiteSpace(prizeLeval)){
                formWorkProject.setPrizeLeval(prizeLeval);
            }else{
                return ResultFactory.commonError("请选择 荣誉级别！");
            }

            if (!StringUtils.isNullOrWhiteSpace(prizeName)){
                formWorkProject.setPrizeName(prizeName);
            }else{
                return ResultFactory.commonError("请填写荣誉名称！");
            }

            if (!StringUtils.isNullOrWhiteSpace(achiGetTime)){
                formWorkProject.setAchiGetTime(DateHelper.parse(achiGetTime,"yyyy-MM-dd"));
            }else{
                return ResultFactory.commonError("请填写获得时间！");
            }

            if (!StringUtils.isNullOrWhiteSpace(awardAgency)){
                formWorkProject.setAwardAgency(awardAgency);
            }else{
                return ResultFactory.commonError("请填写颁发机构！");
            }


        }



        formWorkProjectRepository.save(formWorkProject);

//        return doApproveOk(dto.getFormNo(), dto.getFormKind(), dto.getApproverId(), dto.getApproverName(), dto.getContent(), dto.getAgentId());
        return super.doApprove(dto, param);
    }

    @Override
    public PagedResult<FormWorkAchievementsDto> getPagesFormWorkAchievementDto(FormWorkAchievementCriteria achievementCriteria) {
        Criteria criteria = getSession().createCriteria(FormWorkAchievements.class);
        criteria.add(Restrictions.eq("isActive", true));

        if (!StringUtils.isNullOrWhiteSpace(achievementCriteria.getProjectName()))
            criteria.add(Restrictions.like("projectName", "%" + achievementCriteria.getProjectName() + "%"));
        if (!StringUtils.isNullOrWhiteSpace(achievementCriteria.getLeader()))
            criteria.add(Restrictions.like("leader", "%" + achievementCriteria.getLeader() + "%"));
        if (achievementCriteria.getCompetitionType() != null)
            criteria.add(Restrictions.eq("competitionType", achievementCriteria.getCompetitionType()));
        if (!StringUtils.isNullOrWhiteSpace(achievementCriteria.getDeptName()))
            criteria.add(Restrictions.eq("deptName", achievementCriteria.getDeptName()));
        //添加formStatus字段查询，查询出待签核的表单
        criteria.add(Restrictions.eq("formStatus", FormStatus.UA));
        criteria.add(Restrictions.eq("clStep", 2));

        int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criteria.setProjection(null);
        criteria.setFirstResult((achievementCriteria.getPageNumber() - 1) * achievementCriteria.getPageSize()).setMaxResults(achievementCriteria.getPageSize());
        List<FormWorkAchievements> beans = criteria.list();
        List<FormWorkAchievementsDto> list = new ArrayList<>();
        for (FormWorkAchievements wp : beans) {
            list.add(DtoFactory.convert(wp, new FormWorkAchievementsDto()));
        }
        return new PagedResult<FormWorkAchievementsDto>(list, achievementCriteria.getPageNumber(), achievementCriteria.getPageSize(), totalCount);
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
            FormWorkProjectDto dto=this.getFormByFormNo(formNo);
            if (Long.parseLong(dto.getAchi_leader_id())==approverId) {
                for (FormApprover formApprover : header.getFormApprovers()) {
                    //待签核
                    //判断当前审核人是否是评审组长
                    if (formApprover.getApproveStatus().equals(ApproveStatus.W)) {
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
