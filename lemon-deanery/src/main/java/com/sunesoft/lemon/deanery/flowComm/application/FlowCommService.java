//package com.sunesoft.lemon.deanery.flowComm.application;
//
//import org.snaker.engine.access.Page;
//import org.snaker.engine.access.QueryFilter;
//import org.snaker.engine.entity.*;
//import org.snaker.engine.entity.Process;
//
///**
// * Created by wangwj on 2016/8/10 0010.
// */
//public interface FlowCommService {
//    Page<WorkItem> queryDBFlow(Page<WorkItem> page,QueryFilter queryFilter);
//    Boolean autoTask(String processId,String orderId,String taskId);
//    public void getProcess(Page<Process> page,QueryFilter queryFilter);
//    public void delProcessById(String processId);
//    public Boolean initProcess();
//}