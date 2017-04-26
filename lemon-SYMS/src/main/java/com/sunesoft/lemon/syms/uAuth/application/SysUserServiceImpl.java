package com.sunesoft.lemon.syms.uAuth.application;

import com.sunesoft.lemon.fr.ddd.infrastructure.repo.hibernate.GenericHibernateFinder;
import com.sunesoft.lemon.fr.results.PagedResult;
import com.sunesoft.lemon.fr.utils.StringUtils;
import com.sunesoft.lemon.syms.uAuth.application.criteria.UserCriteria;
import com.sunesoft.lemon.syms.uAuth.application.dtos.RoleDto;
import com.sunesoft.lemon.syms.uAuth.application.dtos.UserDto;
import com.sunesoft.lemon.syms.uAuth.domain.SysRole;
import com.sunesoft.lemon.syms.uAuth.domain.SysRoleRepository;
import com.sunesoft.lemon.syms.uAuth.domain.SysUser;
import com.sunesoft.lemon.syms.uAuth.domain.SysUserRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouz on 2016/5/19.
 */
@Deprecated
@Service("sysUserService")
public class SysUserServiceImpl extends GenericHibernateFinder implements SysUserService {

    @Autowired
    SysUserRepository userRepository;

    @Autowired
    SysRoleRepository roleRepository;

    @Autowired
    SysRoleService roleService;

    /**
     * @param dto 用户信息
     * @return
     */
    @Override
    public Long addUser(UserDto dto) {
        List<UserDto> list=getAllUser(dto.getLoginName());
        for(UserDto userDto : list){
            if(userDto.getLoginName().equals(dto.getLoginName())){
                return -1L;
            }
        }
        return  userRepository.save(convertFromDto(dto));
    }

    @Override
    public SysUser login(String userName, String password) {
        Criteria criterion = getSession().createCriteria(SysUser.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("loginName", userName));
        criterion.add(Restrictions.eq("password", password));
        criterion.add(Restrictions.eq("status", 0));
        if (criterion.list().size() > 0) {
            Date time = new Date();
            ((SysUser) criterion.list().get(0)).setLastLoginTime(time);
            int i = ((SysUser) criterion.list().get(0)).getLoginCount();
            ((SysUser) criterion.list().get(0)).setLoginCount(++i);
            return (SysUser) criterion.list().get(0);
        }
        return null;
    }

    @Override
    public SysUser getBykey(Long id) {

        SysUser user=  userRepository.get(id);
//
//        user.addNewEdu(edu);
//
//        userRepository.save(user);

        if (user != null&&user.getIsActive() == true) {
                return user;
        }
        return null;
    }


    /**
     * 修改用户的password
     *
     * @param id
     * @param password
     * @return
     */
    @Override
    public Boolean changePassword(Long id, String password) {
        SysUser user = userRepository.get(id);
        if (user == null||user.getStatus() != 0) {
            return false;
        }

        user.setPassword(password);
        Date time = new Date();
        user.setLastUpdateTime(time);
        userRepository.save(user);
        return true;
    }



    @Override
    public Boolean setUserStatus(List<Long> ids, int status) {

        Criteria criterion = getSession().createCriteria(SysUser.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (ids == null|| ids.size()<1) {
            return  false;
        }
            criterion.add(Restrictions.in("id", ids));

        List<SysUser> beans = criterion.list();
        for (SysUser bean : beans) {
            bean.setStatus(status);
            Date time = new Date();
            bean.setLastUpdateTime(time);
            userRepository.save(bean);
        }
        return true;
    }

    private SysUser convertFromDto(UserDto dto) {
        SysUser user = null;
        if (dto.getId() != null && dto.getId() > 0) {
            user = userRepository.get(dto.getId());
        } else {
            user = new SysUser();
        }
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setLevels(dto.getLevels());
        user.setAlt(dto.getAlt());
        user.setBrief(dto.getBrief());

        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPhoto(dto.getPhoto());
        user.setQq(dto.getQq());
        user.setMobile(dto.getMobile());
        user.setLastUpdateTime(new Date());

        user.setLoginCount(dto.getLoginCount());
        user.setLastLoginTime(dto.getLastLoginTime());
        user.setStatus(dto.getStatus());
        user.setLoginName(dto.getLoginName());
        List<RoleDto> list=dto.getUserRoleList();
        List<SysRole> roles = new ArrayList<SysRole>();
        if (list != null && list.size() > 0) {
            for (RoleDto roleDto : list) {
                SysRole role = roleRepository.get(roleDto.getId());
                roles.add(role);
            }
            user.setUserRoleList(roles);
        }
        return user;
    }


    private UserDto convertToDto(SysUser user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setLoginName(user.getLoginName());
        dto.setName(user.getName());
        dto.setLevels(user.getLevels());
        dto.setAlt(user.getAlt());
        dto.setBrief(user.getBrief());

        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setPhoto(user.getPhoto());
        dto.setQq(user.getQq());
        dto.setMobile(user.getMobile());

        dto.setLoginCount(user.getLoginCount());
        dto.setLastLoginTime(user.getLastLoginTime());
        dto.setStatus(user.getStatus());
        dto.setCreateDateTime(user.getCreateDateTime());
        dto.setLastUpdateTime(user.getLastUpdateTime());

        List<SysRole> roles = user.getUserRoleList();
        List<RoleDto> dtos = new ArrayList<RoleDto>();
        if (roles != null && roles.size() > 0) {
            for (SysRole r : roles) {
                RoleDto dt = new RoleDto();
                dt.setId(r.getId());
                dt.setName(r.getName());
                dt.setIdCode(r.getIdCode());
                dt.setSort(r.getSort());
                dt.setDescription(r.getDescription());
                dtos.add(dt);
            }
            dto.setUserRoleList(dtos);
        }
        return dto;
    }

    @Override
    public PagedResult<UserDto> FindUser(UserCriteria searchCriteria) {
        Criteria criterion = getSession().createCriteria(SysUser.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (!StringUtils.isNullOrWhiteSpace(searchCriteria.getUserName())) {
            criterion.add(Restrictions.like("name", "%" + searchCriteria.getUserName() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(searchCriteria.getPhone())) {
            criterion.add(Restrictions.like("phone", "%" + searchCriteria.getPhone() + "%"));
        }
        if (!StringUtils.isNullOrWhiteSpace(searchCriteria.getLoginName())) {
            criterion.add(Restrictions.like("loginName", "%" + searchCriteria.getLoginName() + "%"));
        }
        int totalCount = ((Long) criterion.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        criterion.setProjection(null);
        criterion.setFirstResult((searchCriteria.getPageNumber() - 1) * searchCriteria.getPageSize()).setMaxResults(searchCriteria.getPageSize());
        List<SysUser> beans = criterion.list();
        List<UserDto> list = new ArrayList<UserDto>();
        for (SysUser user : beans) {
            list.add(convertToDto(user));
        }
        //  System.out.println(JsonHelper.toJson(beans));
        return new PagedResult<UserDto>(list, searchCriteria.getPageNumber(), searchCriteria.getPageSize(), totalCount);
    }


    @Override
    public Long addOrUpdate(UserDto dto) {
        if(dto.getId()!=null&&dto.getId()>0) {
            return userRepository.save(convertFromDto(dto));
        }else{
            List<UserDto> list=getAllUser(dto.getLoginName());
            if(list!=null&&list.size()>0){
                dto.setId(list.get(0).getId());
                addOrUpdate(dto);
            }
            return   userRepository.save(convertFromDto(dto));
        }
    }

    @Override
    public boolean delete(Long[] ids) {
        Criteria criterion = getSession().createCriteria(SysUser.class);
        criterion.add(Restrictions.eq("isActive", true));
        if (ids == null || ids.length<1) {
            return false;
        }
        criterion.add(Restrictions.in("id",ids));
        List<SysUser> list = criterion.list();
        for (SysUser user : list) {
            if (user.getStatus() == 0) {
                user.setIsActive(false);
            }
        }
        return true;
    }

    @Override
    public boolean updateUser(UserDto dto) {
        Date time = new Date();
        SysUser user = userRepository.get(dto.getId());
        if (user != null && user.getStatus() == 0) {
            user.setLastUpdateTime(time);

            user.setLoginName(dto.getLoginName());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPhone(dto.getPhone());

            List<RoleDto> listd=dto.getUserRoleList();
            if (listd != null && listd.size() > 0) {
                List<SysRole> list = new ArrayList<SysRole>();
                for (RoleDto d : listd) {
                    SysRole r = roleRepository.get(d.getId());
                    if (r != null) {
                        list.add(r);
                    }
                }
                user.setUserRoleList(list);
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<UserDto> getAllUser() {
        Criteria criterion = getSession().createCriteria(SysUser.class);
        criterion.add(Restrictions.eq("isActive", true));
        List<SysUser> list1 = criterion.list();
        List<UserDto> list = new ArrayList<UserDto>();
        for (SysUser user : list1) {
            list.add(convertToDto(user));
        }
        return list;
    }


    @Override
    public UserDto getById(Long id) {
        if (userRepository.get(id).getIsActive()) {
            return convertToDto(userRepository.get(id));
        }
        return null;
    }

    @Override
    public Long save(UserDto dto) {
        return userRepository.save(convertFromDto(dto));
    }

    @Override
    public List<UserDto> getAllUser(String loginName) {
        List<UserDto> list = new ArrayList<UserDto>();
        if(StringUtils.isNullOrWhiteSpace(loginName)){
            return list;
        }
        Criteria criterion = getSession().createCriteria(SysUser.class);
        criterion.add(Restrictions.eq("isActive", true));
        criterion.add(Restrictions.eq("loginName", loginName));
        List<SysUser> list1 = criterion.list();

        for (SysUser user : list1) {
            list.add(convertToDto(user));
        }
        return list;
    }

}
