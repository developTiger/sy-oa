package com.sunesoft.lemon.syms.workflow.domain.enums;

/**
 * Created by zhouz on 2016/5/30.
 */
public enum FormStatus {
    WA,//待签核
    UA,//签核中
    WP,//待处理
    AP,//已完成
    RJ,//签核人退回、否决
    RC,//撤回
    IM,//修改中
    CL,//已结案
    SA,//储存
    DE,//删除
    NC,//未完成
    WD,// 退回

    LW //流程入库后，剩余节点还可以打印
}
