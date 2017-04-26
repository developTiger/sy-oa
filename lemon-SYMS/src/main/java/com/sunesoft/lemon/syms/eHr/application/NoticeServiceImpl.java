package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.fr.utils.DateHelper;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.eHr.application.criteria.NoticeCriteria;
import com.sunesoft.lemon.syms.eHr.application.dtos.DeptmentDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpEasyDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.NoticeDto;
import com.sunesoft.lemon.syms.eHr.application.factory.DtoFactory;
import com.sunesoft.lemon.syms.eHr.domain.notice.Notice;
import com.sunesoft.lemon.syms.eHr.domain.notice.NoticeRepository;
import com.sunesoft.lemon.syms.eHr.domain.notice.NoticeType;
import com.sunesoft.lemon.syms.fileSys.application.FileService;
import com.sunesoft.lemon.syms.fileSys.application.dtos.FileInfoDto;
import com.sunesoft.lemon.syms.hrForms.application.Dtos.*;
import com.sunesoft.lemon.syms.hrForms.application.formDeptAppraise.FormDeptAppraiseService;
import com.sunesoft.lemon.syms.hrForms.application.formDeptSubAppraise.FormDeptSubAppraiseService;
import com.sunesoft.lemon.syms.hrForms.application.formEmpAppraise.FormAppraiseService;
import com.sunesoft.lemon.syms.hrForms.application.formEmpSubAppraise.FormEmpSubAppraiseService;
import com.sunesoft.lemon.syms.hrForms.application.formTrain.FormTrainService;
import com.sunesoft.lemon.syms.hrForms.application.formTrainChoose.FormTrainChooseEmpService;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouz on 2016/7/22.
 */
@Service("noticeService")
public class NoticeServiceImpl extends GenericHibernateFinder implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;


    @Autowired
    FormTrainService formTrainService;

    @Autowired
    FormDeptAppraiseService formDeptAppraiseService;

    @Autowired
    FormDeptSubAppraiseService formDeptSubAppraiseService;

    @Autowired
    DeptmentService deptmentService;

    @Autowired
    FormTrainChooseEmpService formTrainChooseEmpService;



    @Autowired
    FormAppraiseService formAppraiseService;

    @Autowired
    FormEmpSubAppraiseService  formEmpSubAppraiseService;



    @Override
    public CommonResult AddOrUpdateNotice(NoticeDto dto) {
        if(!isNoticeExist(dto.getNoticeName(),dto.getId())) {
            Notice notice = new Notice();
            if (dto.getId() != null && dto.getId() > 0) {
                notice = noticeRepository.get(dto.getId());
            }
            notice = DtoFactory.convert(dto, notice);
            long id = noticeRepository.save(notice);
            return ResultFactory.commonSuccess(id);
        }else
            return ResultFactory.commonError("发布信息名称已存在！");
    }

    @Override
    public CommonResult AddOrUpdateNotice(NoticeDto dto, EmpSessionDto empSessionDto) {
        CommonResult result = this.AddOrUpdateNotice(dto);
        if(dto.getId()!=null)
            return result;
        if (result.getIsSuccess()) {
            if(dto.getNoticeType()==NoticeType.DeptExamine){
                return noticeDeptAppraise(dto,empSessionDto,result.getId());
            }
            if (dto.getNoticeType() == NoticeType.Train) {
                return  noticeTrain(dto,empSessionDto,result.getId());
            }

            if(dto.getNoticeType()==NoticeType.EmpExaming){
                noticeEmpAppraise(dto,empSessionDto,result.getId());
            }

        }
        return result;
    }
    private CommonResult noticeTrain(NoticeDto dto, EmpSessionDto empSessionDto,Long noticeId) {




        CommonResult result;
        FormTrainDto formTrainDto = new FormTrainDto();

        formTrainDto.setApplyer(empSessionDto.getId());
        formTrainDto.setApplyerName(empSessionDto.getName());
        formTrainDto.setDeptId(empSessionDto.getDeptId());
        formTrainDto.setDeptName(empSessionDto.getDeptName());

        formTrainDto.setTrainBeginDate(dto.getsDate());
        formTrainDto.setTrainEndDate(dto.geteDate());
        formTrainDto.setTrainContent(dto.getNoticeDesc());
        formTrainDto.setTrainPlace(dto.getLocations());
        formTrainDto.setTrainName(dto.getNoticeName());
        formTrainDto.setFormKind("SYY_RS_LC01");
        formTrainDto.setTrainDepts(dto.getToDept());
        formTrainDto.setFormDeptName(dto.getToDeptName().replace(" ",""));

        formTrainDto.setfTemp(noticeId.toString());
        formTrainDto.setFormKindName("培训申请流程");

        //保存培训通知名称
        formTrainDto.setPublishTrainNewsName(dto.getNoticeName());
        formTrainDto.setFileId(dto.getFileId());
        formTrainDto.setFileName(dto.getFilename());

//                String[] dept_ids = dto.getToDept().split(",");
//                String[] deptNames = dto.getToDeptName().split(",");
//                List<EmpEasyDto> list = new ArrayList<>();
//                for(int i=0;i<deptNames.length;i++){
//                    EmpEasyDto empEasyDto = new EmpEasyDto();
//                    empEasyDto.setTrainDeptName(deptNames[i]);
//                    list.add(empEasyDto);
//                }
//                formTrainDto.setEmpLists(list);
        result = formTrainService.addByDto(formTrainDto);

        if(!result.getIsSuccess())
            return result;

        CommonResult resultsubmit = formTrainService.submitForm(result.getId(), "SYY_RS_LC01");

        Long parentFormNo = result.getId();

        String s = formTrainDto.getTrainDepts();
        String[] arr = s.split(",");
        String[] depts = dto.getToDeptName().split(",");
        FormTrainChooseEmpDto formTrainChooseEmpDto;
        List<EmpEasyDto> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {

            formTrainChooseEmpDto = new FormTrainChooseEmpDto();

            //下面四个 是表单头部信息 必备信息
            formTrainChooseEmpDto.setApplyer(empSessionDto.getId());
            formTrainChooseEmpDto.setApplyerName(empSessionDto.getName());
            formTrainChooseEmpDto.setDeptId(empSessionDto.getDeptId());
            formTrainChooseEmpDto.setDeptName(empSessionDto.getDeptName());

            formTrainChooseEmpDto.setTrainBeginDate(dto.getsDate());
            formTrainChooseEmpDto.setTrainEndDate(dto.geteDate());
            formTrainChooseEmpDto.setTrainName(formTrainDto.getTrainName());
            formTrainChooseEmpDto.setTrainContent(formTrainDto.getTrainContent());
            formTrainChooseEmpDto.setTrainPlace(formTrainDto.getTrainPlace());


            formTrainChooseEmpDto.setBlongDeptId(Long.parseLong(arr[i]));
            formTrainChooseEmpDto.setBlongDeptName(depts[i]);


            formTrainChooseEmpDto.setfTemp(noticeId.toString());
            EmpEasyDto empEasyDto = new EmpEasyDto();

            empEasyDto.setTrainDeptName(depts[i]);
            list.add(empEasyDto);
            formTrainChooseEmpDto.setEmpLists(list);

            formTrainChooseEmpDto.setFormKind("SYY_RS_LC01_01");
            formTrainChooseEmpDto.setFormKindName(formTrainDto.getFormKindName());

            //保存培训通知名称
            formTrainChooseEmpDto.setPublishTrainNewsName(dto.getNoticeName());
            formTrainChooseEmpDto.setFileId(dto.getFileId());
            formTrainChooseEmpDto.setFileName(dto.getFilename());

            formTrainChooseEmpDto.setParentFormNo(parentFormNo);
            CommonResult commonResult1 = formTrainChooseEmpService.addByDto((formTrainChooseEmpDto));
            result = formTrainChooseEmpService.submitForm(commonResult1.getId(), formTrainChooseEmpDto.getFormKind());

        }
        return result;
    }

    private CommonResult noticeEmpAppraise(NoticeDto dto, EmpSessionDto empSessionDto,Long noticeId) {

        FormEmpAppraiseDto  formEmpAppraiseDto = new FormEmpAppraiseDto();

        formEmpAppraiseDto.setApplyer(empSessionDto.getId());
        formEmpAppraiseDto.setApplyerName(empSessionDto.getName());
        formEmpAppraiseDto.setDeptId(empSessionDto.getDeptId());
        formEmpAppraiseDto.setDeptName(empSessionDto.getDeptName());

        formEmpAppraiseDto.setBeginDate(dto.getsDate());
        formEmpAppraiseDto.setEndDate(dto.geteDate());
        formEmpAppraiseDto.setAppraisTitle(dto.getNoticeName());
        formEmpAppraiseDto.setDescription(dto.getNoticeDesc());
        formEmpAppraiseDto.setAppraisYear(Long.valueOf(dto.getsDate().getYear()));
        formEmpAppraiseDto.setFormKind("SYY_RS_LC03");
        formEmpAppraiseDto.setfTemp(noticeId.toString());


        CommonResult result = formAppraiseService.addByDto(formEmpAppraiseDto);
        if(!result.getIsSuccess())
            return result;
        CommonResult commonResult = formAppraiseService.submitForm(result.getId(), formEmpAppraiseDto.getFormKind());
        //增加子表单
        List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
        Long parentFormNo = result.getId();
        //增加子表单
        for (DeptmentDto dept : deptmentDtos) {
            FormEmpSubAppraiseDto formEmpSubAppraiseDto = new FormEmpSubAppraiseDto();
            BeanUtils.copyProperties(formEmpAppraiseDto, formEmpSubAppraiseDto);
            formEmpSubAppraiseDto.setParentFormNo(parentFormNo);
            formEmpSubAppraiseDto.setBlongDeptId(dept.getId());
            formEmpSubAppraiseDto.setBlongDeptName(dept.getDeptName());
            formEmpSubAppraiseDto.setFormKind("SYY_RS_LC03_01");
            formEmpSubAppraiseDto.setFormKindName("员工考核部门评分");
            formEmpSubAppraiseDto.setHasApplyView(false);
            CommonResult res = formEmpSubAppraiseService.addByDto(formEmpSubAppraiseDto);
            commonResult = formEmpSubAppraiseService.submitForm(res.getId(), formEmpSubAppraiseDto.getFormKind());
        }
        return ResultFactory.commonSuccess();
    }

    private CommonResult noticeDeptAppraise(NoticeDto dto, EmpSessionDto empSessionDto,Long noticeId){
        DeptAppraiseDto  deptAppraiseDto = new DeptAppraiseDto();
        deptAppraiseDto.setApplyer(empSessionDto.getId());
        deptAppraiseDto.setApplyerName(empSessionDto.getName());
        deptAppraiseDto.setDeptId(empSessionDto.getDeptId());
        deptAppraiseDto.setDeptName(empSessionDto.getDeptName());

        deptAppraiseDto.setStartDate(dto.getsDate());
        deptAppraiseDto.setEndDate(dto.geteDate());
        deptAppraiseDto.setAppraisTitle(dto.getNoticeName());
        deptAppraiseDto.setDescription(dto.getNoticeDesc());
        deptAppraiseDto.setAppraisYear(Long.valueOf(dto.getsDate().getYear()));

        deptAppraiseDto.setfTemp(noticeId.toString());
        deptAppraiseDto.setFormKind("SYY_RS_LC04");

        CommonResult result=formDeptAppraiseService.addByDto(deptAppraiseDto);
        if(!result.getIsSuccess())
            return result;
        CommonResult commonResult=formDeptAppraiseService.submitForm(result.getId(),deptAppraiseDto.getFormKind());

        //增加子表单
        List<DeptmentDto> deptmentDtos = deptmentService.getAllDept();
        for(DeptmentDto dept:deptmentDtos){
            DeptSubAppraiseDto subAppraiseDto = new DeptSubAppraiseDto();
            BeanUtils.copyProperties(deptAppraiseDto, subAppraiseDto);
            subAppraiseDto.setParentFormNo(result.getId());
            subAppraiseDto.setBlongDeptId(dept.getId());
            subAppraiseDto.setBlongDeptName(dept.getDeptName());
            subAppraiseDto.setFormKind("SYY_RS_LC04_01");
            subAppraiseDto.setfTemp(noticeId.toString());
            subAppraiseDto.setFormKindName("部门考核自评");
            subAppraiseDto.setHasApplyView(false);
            CommonResult res=formDeptSubAppraiseService.addByDto(subAppraiseDto);
            commonResult=  formDeptSubAppraiseService.submitForm(res.getId(),subAppraiseDto.getFormKind());
        }


        return ResultFactory.commonSuccess();
    }

    @Override
    public CommonResult deleteNotice(Long id) {
        noticeRepository.delete(id);
        return ResultFactory.commonSuccess();
    }

    @Override
    public List<NoticeDto> NoticeGetTopsType(NoticeType type) {
        Criteria criterion = getSession().createCriteria(Notice.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("noticeType", type));
        criterion.addOrder(Order.desc("id"));
        criterion.setFirstResult(0).setMaxResults(5);
        List<Notice> notices = criterion.list();
        List<NoticeDto> dtos = new ArrayList<>();
        for (Notice notice : notices) {
            NoticeDto dto = new NoticeDto();
            dto = DtoFactory.convert(notice, dto);
            dtos.add(dto);
        }
        return dtos;

    }

    @Override
    public PagedResult<NoticeDto> GetNoticePaged(NoticeCriteria noticeCriteria) {
        Criteria criterion = getSession().createCriteria(Notice.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (noticeCriteria.getNoticeType() != null) {
            criterion.add(Restrictions.eq("noticeType", noticeCriteria.getNoticeType()));
        }
        if (!StringUtils.isNullOrWhiteSpace(noticeCriteria.getNoticeName())) {
            criterion.add(Restrictions.like("noticeName", "%" + noticeCriteria.getNoticeName() + "%", MatchMode.ANYWHERE));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.addOrder(Order.desc("id")); //addOrder() 增加一个代表排序的Criterion对象
        criterion.setFirstResult((noticeCriteria.getPageNumber() - 1) * noticeCriteria.getPageSize()).setMaxResults(noticeCriteria.getPageSize());

        List<Notice> notices = criterion.list();
        List<NoticeDto> dtos = new ArrayList<>();
        for (Notice notice : notices) {
            NoticeDto dto = new NoticeDto();
            dto = DtoFactory.convert(notice, dto);
            dtos.add(dto);
        }
        return new PagedResult<NoticeDto>(dtos, noticeCriteria.getPageNumber(), noticeCriteria.getPageSize(), totalCount);
    }

    @Override
    public NoticeDto getById(Long id) {
        Notice notice = noticeRepository.get(id);
        return DtoFactory.convert(notice, new NoticeDto());
    }

    @Override
    public List<NoticeDto> getByName(String name) {
        Criteria criterion = getSession().createCriteria(Notice.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("noticeName", name));
        List<Notice> notices = criterion.list();
        List<NoticeDto> dtos = new ArrayList<>();
        for (Notice notice : notices) {
            NoticeDto dto = new NoticeDto();
            dto = DtoFactory.convert(notice, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    private Boolean isNoticeExist(String name,Long upId) {
        Criteria criterion = getSession().createCriteria(Notice.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("noticeName", name));
        List<Notice> notices = criterion.list();

        if (upId == null || upId == 0) {
            if (notices == null || notices.size() == 0)
                return false;
            else
                return true;
        } else {
            if (notices.size() > 1)
                return true;
            if (notices == null || notices.size() == 0)
                return false;
            else //==1

                return !notices.get(0).getId().equals(upId);
        }
    }
}
