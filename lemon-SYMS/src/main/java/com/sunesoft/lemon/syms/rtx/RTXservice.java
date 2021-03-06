
package com.sunesoft.lemon.syms.rtx;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "RTXservice", targetNamespace = "http://webService.rtxservice.petrochina.com")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface RTXservice {


    /**
     * 
     */
    @WebMethod(action = "quartzSendMsg")
    @RequestWrapper(localName = "quartzSendMsg", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.QuartzSendMsg")
    @ResponseWrapper(localName = "quartzSendMsgResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.QuartzSendMsgResponse")
    public void quartzSendMsg();

    /**
     * 
     * @param systemName
     * @param endDate
     * @param userName
     * @param startDate
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "getMsg")
    @WebResult(name = "getMsgReturn", targetNamespace = "")
    @RequestWrapper(localName = "getMsg", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.GetMsg")
    @ResponseWrapper(localName = "getMsgResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.GetMsgResponse")
    public String getMsg(
            @WebParam(name = "userName", targetNamespace = "")
                    String userName,
            @WebParam(name = "systemName", targetNamespace = "")
                    String systemName,
            @WebParam(name = "startDate", targetNamespace = "")
                    String startDate,
            @WebParam(name = "endDate", targetNamespace = "")
                    String endDate);

    /**
     * 
     * @param deptId
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "deptIsExist")
    @WebResult(name = "deptIsExistReturn", targetNamespace = "")
    @RequestWrapper(localName = "deptIsExist", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.DeptIsExist")
    @ResponseWrapper(localName = "deptIsExistResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.DeptIsExistResponse")
    public String deptIsExist(
            @WebParam(name = "deptId", targetNamespace = "")
                    String deptId);

    /**
     * 
     * @param messageXml
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "sendNotifys")
    @WebResult(name = "sendNotifysReturn", targetNamespace = "")
    @RequestWrapper(localName = "sendNotifys", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.SendNotifys")
    @ResponseWrapper(localName = "sendNotifysResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.SendNotifysResponse")
    public String sendNotifys(
            @WebParam(name = "messageXml", targetNamespace = "")
                    String messageXml);

    /**
     * 
     * @param account
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "userIsExist")
    @WebResult(name = "userIsExistReturn", targetNamespace = "")
    @RequestWrapper(localName = "userIsExist", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.UserIsExist")
    @ResponseWrapper(localName = "userIsExistResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.UserIsExistResponse")
    public String userIsExist(
            @WebParam(name = "account", targetNamespace = "")
                    String account);

    /**
     * 
     * @param account
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "getUserSimpleInfo")
    @WebResult(name = "getUserSimpleInfoReturn", targetNamespace = "")
    @RequestWrapper(localName = "getUserSimpleInfo", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.GetUserSimpleInfo")
    @ResponseWrapper(localName = "getUserSimpleInfoResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.GetUserSimpleInfoResponse")
    public String getUserSimpleInfo(
            @WebParam(name = "account", targetNamespace = "")
                    String account);

    /**
     * 
     * @param account
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "queryUserState")
    @WebResult(name = "queryUserStateReturn", targetNamespace = "")
    @RequestWrapper(localName = "queryUserState", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.QueryUserState")
    @ResponseWrapper(localName = "queryUserStateResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.QueryUserStateResponse")
    public String queryUserState(
            @WebParam(name = "account", targetNamespace = "")
                    String account);

    /**
     * 
     * @param xxbt
     * @param xtmc
     * @param xxnr
     * @param yhm
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "sendNotify")
    @WebResult(name = "sendNotifyReturn", targetNamespace = "")
    @RequestWrapper(localName = "sendNotify", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.SendNotify")
    @ResponseWrapper(localName = "sendNotifyResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.SendNotifyResponse")
    public String sendNotify(
            @WebParam(name = "yhm", targetNamespace = "")
                    String yhm,
            @WebParam(name = "xxbt", targetNamespace = "")
                    String xxbt,
            @WebParam(name = "xxnr", targetNamespace = "")
                    String xxnr,
            @WebParam(name = "xtmc", targetNamespace = "")
                    String xtmc);

    /**
     * 
     * @param deptID
     * @param isIncludeChildDepts
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "getDeptUsers")
    @WebResult(name = "getDeptUsersReturn", targetNamespace = "")
    @RequestWrapper(localName = "getDeptUsers", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.GetDeptUsers")
    @ResponseWrapper(localName = "getDeptUsersResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.GetDeptUsersResponse")
    public String getDeptUsers(
            @WebParam(name = "deptID", targetNamespace = "")
                    String deptID,
            @WebParam(name = "isIncludeChildDepts", targetNamespace = "")
                    boolean isIncludeChildDepts);

    /**
     * 
     * @param smsInfo
     * @param receiver
     * @param sender
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "sendSms")
    @WebResult(name = "sendSmsReturn", targetNamespace = "")
    @RequestWrapper(localName = "sendSms", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.SendSms")
    @ResponseWrapper(localName = "sendSmsResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.SendSmsResponse")
    public String sendSms(
            @WebParam(name = "sender", targetNamespace = "")
                    String sender,
            @WebParam(name = "receiver", targetNamespace = "")
                    String receiver,
            @WebParam(name = "smsInfo", targetNamespace = "")
                    String smsInfo);

    /**
     * 
     * @param xxbt
     * @param xxnr
     * @param yhm
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "sendNotifyNoSave")
    @WebResult(name = "sendNotifyNoSaveReturn", targetNamespace = "")
    @RequestWrapper(localName = "sendNotifyNoSave", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.SendNotifyNoSave")
    @ResponseWrapper(localName = "sendNotifyNoSaveResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.SendNotifyNoSaveResponse")
    public String sendNotifyNoSave(
            @WebParam(name = "yhm", targetNamespace = "")
                    String yhm,
            @WebParam(name = "xxbt", targetNamespace = "")
                    String xxbt,
            @WebParam(name = "xxnr", targetNamespace = "")
                    String xxnr);

    /**
     * 
     * @param deptID
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "getDeptInfo")
    @WebResult(name = "getDeptInfoReturn", targetNamespace = "")
    @RequestWrapper(localName = "getDeptInfo", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.GetDeptInfo")
    @ResponseWrapper(localName = "getDeptInfoResponse", targetNamespace = "http://webService.rtxservice.petrochina.com", className = "com.rtx.client.GetDeptInfoResponse")
    public String getDeptInfo(
            @WebParam(name = "deptID", targetNamespace = "")
                    String deptID);

}
