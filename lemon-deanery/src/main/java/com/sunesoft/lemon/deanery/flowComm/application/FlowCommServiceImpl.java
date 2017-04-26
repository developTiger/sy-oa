//package com.sunesoft.lemon.deanery.flowComm.application;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.beans.BeanInfo;
//import java.beans.Introspector;
//import java.beans.PropertyDescriptor;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by wangwj on 2016/8/10 0010.
// */
//@Service(value = "flowCommService")
//public class FlowCommServiceImpl implements FlowCommService {
//    @Autowired
//    private SnakerEngine snakerEngine;
//
//    @Override
//    public Page<WorkItem> queryDBFlow(Page<WorkItem> page,QueryFilter queryFilter){
//       /* try {
//            String path = this.getClass().getClassLoader().getResource("/").getPath();
//            snakerEngine.process().deploy(new FileInputStream(path+"flows/kg_lc01.snaker"));
//            snakerEngine.process().deploy(new FileInputStream(path+"flows/carapply.snaker"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }*/
//        snakerEngine.query().getWorkItems(page,queryFilter.setTaskType(TaskModel.TaskType.Major.ordinal()));
//        return page;
//    }
//
//    @Override
//    public Boolean autoTask(String processId, String orderId, String taskId) {
//        List<Task> tasks = snakerEngine.query().getActiveTasks(new QueryFilter().setProcessId(processId).setOrderId(orderId));
//        if(tasks != null && tasks.size()>0){
//            snakerEngine.executeTask(tasks.get(0).getId());
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 查询流程信息，结果直接存放在传入参数page中
//     * @param page
//     * @param queryFilter
//     */
//    @Override
//    public void getProcess(Page<Process> page,QueryFilter queryFilter){
//        snakerEngine.process().getProcesss(page,queryFilter);
//    }
//
//    @Override
//    public void delProcessById(String processId){
//        snakerEngine.process().undeploy(processId);
//    }
//
//    /**
//     * 初始化新增的流程
//     * @return
//     */
//    @Override
//    public Boolean initProcess(){
//        try {
//            String path = this.getClass().getClassLoader().getResource("/").getPath();
//            File f = new File(path+"flows");
//            if(f.isDirectory()){
//                File[] files = f.listFiles();
//                for(File file : files){
//                    if(file.isFile())
//                        snakerEngine.process().deploy(new FileInputStream(file));
//                }
//
//
//            }else
//                return false;
////            snakerEngine.process().deploy(new FileInputStream(path+"flows/kg_lc01.snaker"));
////            snakerEngine.process().deploy(new FileInputStream(path+"flows/carapply.snaker"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    private Map<String, Object> transBean2Map(Object obj) {
//
//        if(obj == null){
//            return null;
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            for (PropertyDescriptor property : propertyDescriptors) {
//                String key = property.getName();
//
//                // 过滤class属性
//                if (!key.equals("class")) {
//                    // 得到property对应的getter方法
//                    Method getter = property.getReadMethod();
//                    Object value = getter.invoke(obj);
//
//                    map.put(key, value);
//                }
//
//            }
//        } catch (Exception e) {
//            System.out.println("transBean2Map Error " + e);
//        }
//
//        return map;
//
//    }
//}
