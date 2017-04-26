package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.syms.eHr.application.criteria.*;
import com.sunesoft.lemon.syms.eHr.application.dtos.*;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/5/12.
 */

public interface EmployeeService {

//    /**
//     * 自己测试用到
//     * @param id
//     * @return
//     */
//    public Employee getById(Long id);

    //region 员工信息管理

    /**
     * 增加+修改
     *
     * @param dto
     * @return
     */
    public CommonResult addOrUpdateEpm(EmpDto dto);
    public CommonResult save(Employee emp);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    public EmpDto getEmpById(Long id);



//    public List<Employee> GetEmpsByIds(List<Long> ids);

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    public EmpSessionDto login(String userName, String password);


    /**
     * @param id       修改用户的ID
     * @param password 新密码
     * @return Boolean
     */
    public CommonResult changePassword(Long id, String password);

    /**
     * 页面右上角 更改密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public CommonResult userChangePassword(Long id,String oldPassword,String newPassword);


    /**
     * 设置用户状态
     * 状态修改就是删除，若是不考虑找回功能，就是将状态设置为false
     *
     * @param ids
     * @param status
     * @return
     */
    public CommonResult setEmpStatus(List<Long> ids, int status);


    /**
     * 删除用户，设置isActive状态
     *
     * @param ids
     * @return
     */
    public CommonResult deleteEpm(List<Long> ids);


    /**
     * 查询实例
     *
     * @param criteria 查询条件
     * @return
     */
    public PagedResult<EmpDto> findEmpsPaged(EmpCriteria criteria);

    /**
     * 通过deptid查询部门员工（可多个）
     * @param criteria
     * @return
     */
    public PagedResult<EmpDto> findEmpsByDeptPaged(EmpDeptsCriteria criteria);

    /**
     * 查询实例

     */
    public List<EmpDto> findEmpsByDept(Long deptId);

    /**
     * 该方法只取对象中三个字段，
     * @param deptId
     * @return
     */
    public List<EmpDto> findEmpsByDeptId(Long deptId);
    public List<EmpDto> findEmpsByDeptIdAndName(Long deptId,String name);

    /**
     * 获取所用用户根据用户名，这里考虑到用户名不重复
     *
     * @return
     * @ param loginName
     */
    public List<EmpDto> getEmpsByUserName(String loginName);


    /**
     * 获取所用用户
     *
     * @return
     */
    public List<EmpDto> getAllEmps();

    /**
     * 获取所有用户的简单信息
     * @return
     */
    public List<EmpDto> getAllSimpleEmps();

    /**
     * 获取可以做领导的所用用户
     *
     * @return
     */
    public List<EmpDto> getAllLeaders(Long id);

    /**
     * 获取院领导
     *
     * @return
     */
    public List<EmpDto> getAllLeaders(String deptNo);

    //endregion

    public EmpDto getLeader(Long leaderId);

    public List<EmpDto> getEmpByDeptName(String deptName);


    //region 教育背景管理
    /**
     * 增加教育
     *
     * @param dto
     * @return
     */
    public CommonResult addEdu(EducationDto dto);

    /**
     * 获取edcationdto
     * @param empId
     * @param eduId
     * @return
     */
    public EducationDto getByEduId(Long empId,Long eduId);

    /**
     * 修改教育背景
     *
     * @param dto
     * @return
     */
    public CommonResult updateEdu(EducationDto dto);



    /**
     * 删除教育背景
     *
     * @param eduId
     * @return
     */
    public CommonResult deleteEdu(Long empId,Long eduId);


    /**
     * 获取所有教育背景
     * @return
     */
    public List<EducationDto> getAllEdus(Long empId);



    /**
     * 分页
     * @param edu
     * @return
     */
    public PagedResult<EducationDto> finderEdusPaged(Long empId,EduCriteria edu);
    //endregion





    //region Description
//    /**
//     * 增加员工组
//     * @param dto
//     * @return
//     */
//    public CommonResult addGroup(EmpGroupDto dto);
//
//    /**
//     * EmpGroupDto
//     * @param empId
//     * @param groupId
//     * @return
//     */
//    public EmpGroupDto getByGroupId(Long empId,Long groupId);
//
//    /**
//     * 修改员工组
//     * @param dto
//     * @return
//     */
//    public CommonResult updateGroup(EmpGroupDto dto);
//
//    /**
//     * 删除员工组
//     * @param empId
//     * @return
//     */
//    public CommonResult deleteGroup(Long empId);
//
//    /**
//     * 根据id获取empGroup
//     * @param empId
//     * @return
//     */
//    public EmpGroupDto getGroupById(Long empId);
    //endregion





    //region 家庭成员管理

    /**
     * 增加家庭成员
     * @param dto
     * @return
     */
    public CommonResult addFam(FamilyDto dto);

    /**
     * FamilyDto
     * @param empId
     * @param famId
     * @return
     */
    public FamilyDto getByFamId(Long empId,Long famId);


    /**
     * 删除成员
     * @param empId
     * @param id
     * @return
     */
    public CommonResult deleteFam(Long empId,Long id);


    /**
     * 修改成员
     * @param dto
     * @return
     */
    public CommonResult updateFam(FamilyDto dto);


    /**
     * 获取所有成员
     * @return
     */
    public List<FamilyDto> getAllFams(Long empId);

    /**
     * 成员分页
     * @param criteria
     * @return
     */
    public PagedResult<FamilyDto> findFamsPaged(Long empId,FamCriteria criteria);
    //endregion


    //region 对qulification的操作

    /**
     * 增加资质信息
     * @param dto
     * @return
     */
    public CommonResult addQualification(QualificationDto dto);

    /**
     * 获取 QualificationDto
     * @param empId
     * @param quaId
     * @return
     */
    public QualificationDto getByQuaId(Long empId,Long quaId);

    /**
     * 修改资质信息
     * @param dto
     * @return
     */
    public CommonResult updateQualification(QualificationDto dto);

    /**
     * 删除资质信息
     * @param quaId
     * @return
     */
    public CommonResult deleteQualification(Long empId,Long quaId);

    /**
     * 获取所有资质信息
     * @return
     */
    public List<QualificationDto> getAllQualifications(Long empId);

    /**
     * 资质信息分页
     * @param criteria
     * @return
     */
    public PagedResult<QualificationDto> findQualificationsPaged(Long empId,QualificationCriteria criteria);
    //endregion


    //region 对TechPosition的操作

    /**
     *增加专业技术职务信息
     * @param dto
     * @return
     */
    public CommonResult addTechPosition(TechPositionDto dto);

    /**
     * 获取 TechPositionDto
     * @param empId
     * @param tpId
     * @return
     */
    public TechPositionDto getByTPId(Long empId,Long tpId);

    /**
     * 编修改专业技术职务信息
     * @param dto
     * @return
     */
    public CommonResult updateTechPosition(TechPositionDto dto);

    /**
     * 删除专业技术职务信息
     * @param tqId
     * @return
     */
    public CommonResult deleteTechPosition(Long empId,Long tqId);

    /**
     * 获取所有专业技术职务信息
     * @return
     */
    public List<TechPositionDto> getAllTechPositions(Long empId);

    /**
     * 专业技术职务信息分页
     * @param criteria
     * @return
     */
    public PagedResult<TechPositionDto> findTechPositionsPaged(Long empId,TechPositionCriteria criteria);
    //endregion


    //region 对WorkExperience的操作

    /**
     * 增加工作经历
     * @param dto
     * @return
     */
    public CommonResult addWorkExperience(WorkExperienceDto dto);

    /**
     * 获取 WorkExperienceDto
     * @param empId
     * @param wkId
     * @return
     */
    public WorkExperienceDto getByWorkExperienceId(Long empId,Long wkId);


    /**
     * 修改工作经历
     * @param dto
     * @return
     */
    public CommonResult updateWorkExperience(WorkExperienceDto dto);

    /**
     * 删除工作经历
     * @param weId
     * @return
     */
    public CommonResult deleteWorkExperience(Long empId,Long weId);

    /**
     * 获取所有工作经历
     * @return
     */
    public List<WorkExperienceDto> getAllWorkExperiences(Long empId);

    /**
     * 工作经历分页
     * @param criteria
     * @return
     */
    public PagedResult<WorkExperienceDto> findWorkExperiencesPaged(Long empId,WorkExperienceCriteria criteria);


    //endregion


    public EmpSessionDto GetEmpDtoById(Long id);

//    前往乘坐人员
    public List<Employee> GetOtherPeople(Long id);


    /**
     * 是否是院领导
     * @param id
     * @return
     */
    Boolean isLeader(Long id);


    /**
     * 根据部门id查院领导
     * @param id
     * @return
     */
    List<EmpDto> getAllHeader(Long id);

    /**
     * 获取院领导
     * @return
     */
    List<EmpDto> getHeader();

    /**
     * 将员工的年假与带薪休假重置
     * @param empIds
     * @return
     */
    public CommonResult resetYearAndSpa(List<Long> empIds);

    /**
     * 导出可请带薪年假或者疗养假的人员列表
     * 1 代表带薪年假 ，0代表疗养假
     * @param time
     * @param type
     * @return
     */
    public List<RestSpanAndYear> getCanRestYearOrSpan(Date time,int type);


    List<MultiSelectUserWithDeptDto> getAllDeptEmp();


    Employee getEmpByLoginName(String loginName);

    Map<Long,String> getEmployIdWithName(Long deptId);

}
