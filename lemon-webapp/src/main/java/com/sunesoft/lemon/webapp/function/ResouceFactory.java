package com.sunesoft.lemon.webapp.function;

import com.sunesoft.lemon.fr.utils.BeanUtils;
import com.sunesoft.lemon.syms.uAuth.application.EmpAuthService;
import com.sunesoft.lemon.syms.uAuth.application.dtos.AuthResDto;
import com.sunesoft.lemon.syms.uAuth.application.dtos.ResourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/7/14.
 */
@Component
public class ResouceFactory {


    @Autowired
    EmpAuthService empAuthService;

    private static Map<Long, List<AuthResDto>> factory = new HashMap<>();//改类型


    public Map<Long, List<AuthResDto>> getResourceMap() {
        if (factory.size() == 0) {
            factory = empAuthService.getAllAuthInfoByRole();
        }
        return factory;
    }

    //得到一个用户不重复的ResourceDto
    public List<ResourceDto> getUserResource(List<Long> roleids) {
        List<ResourceDto> list = new ArrayList<>();
        List<AuthResDto> auList = new ArrayList<>();

        if (factory.size() == 0) {
            factory = empAuthService.getAllAuthInfoByRole();
        }

        for (Long id : roleids) {
            if (factory.size() > 0 && factory.get(id) != null)
                auList.addAll(factory.get(id));
        }

        List<AuthResDto> resultList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for(AuthResDto dto:auList){
            if(!ids.contains(dto.getId())){
                ids.add(dto.getId());
                resultList.add(dto);
            }
        }

        list= convert(resultList);
        return list;
    }



    public String getFileAuth(List<Long> roleIds) {
        List<ResourceDto>  resourceDtos = this.getUserResource(roleIds);
        String authInfo = null;
        if (resourceDtos != null && resourceDtos.size() > 0) {
            for (ResourceDto dto : resourceDtos) {
                if (dto.getResType().equals(3)) {
                    authInfo = dto.getUrl();
                    if (authInfo.equals("all"))
                        return authInfo;
                }
            }
        }
        return authInfo;

    }

    public List<ResourceDto> getFormAuth(List<Long> roleIds) {
        List<ResourceDto> resourceDtos = this.getUserResource(roleIds);
        List<ResourceDto> formInfo = new ArrayList<>();
        String authInfo = null;
        if (resourceDtos != null && resourceDtos.size() > 0) {
            for (ResourceDto dto : resourceDtos) {
                if (dto.getResType().equals(2)) {
                    formInfo.add(dto);
                }
            }
        }
        return formInfo;
    }

    //转化为菜单
    private List<ResourceDto> convert( List<AuthResDto> reslist) {

                List<ResourceDto> resourceDtos = new ArrayList<>();
                for (AuthResDto dto : reslist) {
                    if (dto.getParentId() == null) {
                        ResourceDto r = new ResourceDto();
                        BeanUtils.copyProperties(dto, r);
                        r.setName(dto.getResName());
                        resourceDtos.add(r);
                    }
                }
                for (AuthResDto dto : reslist) {
                    if (dto.getParentId() != null) {
                        for (ResourceDto ddd : resourceDtos) {
                            if (dto.getParentId().equals(ddd.getId())) {
                                if (ddd.getChild() == null) {
                                    ddd.setChild(new ArrayList<ResourceDto>());
                                }
                                ResourceDto r = new ResourceDto();
                                BeanUtils.copyProperties(dto, r);
                                r.setName(dto.getResName());

                                ddd.getChild().add(r);
                            }
                        }
                    }
                }


        return resourceDtos;
    }
}
