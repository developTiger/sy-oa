package com.sunesoft.lemon.webapp.function;


import com.sunesoft.lemon.fr.results.CommonResult;
import com.sunesoft.lemon.fr.results.ResultFactory;
import com.sunesoft.lemon.fr.results.UniqueResult;
import com.sunesoft.lemon.syms.eHr.application.EmployeeService;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpDto;
import com.sunesoft.lemon.syms.eHr.application.dtos.EmpSessionDto;
import com.sunesoft.lemon.syms.eHr.domain.empInfo.Employee;
import com.sunesoft.lemon.syms.uAuth.application.EmpAuthService;
import com.sunesoft.lemon.syms.uAuth.application.dtos.ResourceDto;
import com.sunesoft.lemon.webapp.utils.Des;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouz on 2016/5/13.
 */
@Component
public class UserSession {

    @Autowired
    EmpAuthService empAuthService;
    @Autowired
    ResouceFactory resouceFactory;

    @Autowired
    EmployeeService employeeService;

    private int sessionTime = 30 * 60;
    private String sessionKey = "empDto_";
    private static String cookiePath = "";

    /**
     * 获取当前登录用户信息
     *
     * @param request
     * @return
     */
    public EmpSessionDto getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (getUserIdFromCookie(request) == null) {
            return null;
        }
        EmpSessionDto auth = session.getAttribute(sessionKey + getUserIdFromCookie(request).toString()) == null ? null : (EmpSessionDto) session.getAttribute(sessionKey + getUserIdFromCookie(request).toString());
        return auth;
    }

    /**
     * 设置登录信息
     *
     * @param request
     * @param response
     * @param userName
     * @param password
     * @return
     */
    public UniqueResult<EmpSessionDto> login(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
        UniqueResult<EmpSessionDto> uniqueResult = empAuthService.getEmpInfoByLogin(userName, password);
        if (uniqueResult.getIsSuccess()) {
            setSystemUserCookie(uniqueResult.getT().getId().toString(), response);
            setUserSession(request, uniqueResult.getT());
        }
        return uniqueResult;
    }

    public UniqueResult<EmpSessionDto>  login(HttpServletRequest request, HttpServletResponse response, String userName) {
        UniqueResult<EmpSessionDto> uniqueResult = empAuthService.getEmpInfoByLogin(userName);
        if (uniqueResult.getIsSuccess()) {
            setSystemUserCookie(uniqueResult.getT().getId().toString(), response);
            setUserSession(request, uniqueResult.getT());
        }
        return uniqueResult;
    }

    public EmpSessionDto updateUserSession(HttpServletRequest request, HttpServletResponse response, String sysUserId) {
        EmpSessionDto auth = getCurrentUser(request);
        if (auth == null) {
            UniqueResult<EmpSessionDto> uniqueResult = empAuthService.GetEmpSessionDtoById(Long.parseLong(sysUserId));
            if (uniqueResult.getIsSuccess()) {
                auth = uniqueResult.getT();
            } else {
                return null;
            }
        }
        setSystemUserCookie(auth.getId().toString(), response);
        setUserSession(request, auth);
        return auth;
    }

    //
    public void setSystemUserCookie(String userId, HttpServletResponse response) {
        Cookie cookie = null;
        try {
            cookie = new Cookie("sra_cun", Des.encode(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //cookie.setDomain(cookiePath);
        cookie.setPath("/");
        cookie.setMaxAge(sessionTime);
        response.addCookie(cookie);
    }

    //
//    /**
//     * 把用户ID保存到cookie中，周期为30分钟
//     *
//     * @param response
//     * @param userId
//     */
    public void setUserCookie(HttpServletRequest request, HttpServletResponse response, String userId) {

//        UserServiceDAO userService = new UserServiceDAO();
//        SimpleUserInfoDTO simpleUserInfoDTO = userService.getSimpleUser(userId);
//        try {
//            BtUserSessionDao.Login(simpleUserInfoDTO, response);
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }

    //
//
//    /**
//     * 设置用户缓存信息
//     * @param request
//     * @param userInfo
//     */
    public void setUserSession(HttpServletRequest request, EmpSessionDto empSessionDto) {
        HttpSession session = request.getSession();
        String key = sessionKey + empSessionDto.getId();
        session.setAttribute(key, empSessionDto);
        session.setMaxInactiveInterval(sessionTime);
    }

    public String getSystemUserIdCookie(HttpServletRequest request) {
        String userId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if ("sra_cun".equals(c.getName())) {
                    try {
                        userId = Des.decode(c.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return userId;

    }

    //
//    /**
//     * 从cookie中获取到用户id
//     *
//     * @param request
//     * @return
//     */
    public String getUserIdFromCookie(HttpServletRequest request) {
        String userId = getSystemUserIdCookie(request);
        if (userId != null && !userId.equals("")) {
            return userId;
        }

        return null;
    }

    //
//    /**
//     * 删除用户cookie
//     *
//     * @param request
//     * @param response
//     * @param
//     */
    public Boolean removeUserCookie(HttpServletRequest request, HttpServletResponse response) {
        if (null != getUserIdFromCookie(request)) {
            String key = sessionKey + getUserIdFromCookie(request).toString();
            Cookie cookie = new Cookie("sra_cun", "");
            //    cookie.setDomain(cookiePath);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            request.getSession().removeAttribute(key); //删除某个session
        }
        return true;
    }


    public List<ResourceDto> getUserResource(List<Long> roleIds) {
        if (roleIds != null && roleIds.size() > 0)
            return resouceFactory.getUserResource(roleIds);
        return new ArrayList<>();
    }

}
