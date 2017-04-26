package com.sunesoft.lemon.syms.workflow.domain.enums;

/**
 * Created by zhouz on 2016/5/30.
 */
public enum  ApproveStatus {

    U,// 可簽核(Under Approve)
    W,//待簽核 (Waiting for Approve)
    A,//簽核完成(Apporved)
    T,//轉簽完成(Transfer)
    R,//退回完成(Reject)
    P,//自動完成（Pass）
    C, //撤回(Recall)
    L,//删除(DeLete)
    H //退回(WithDrawal)

    }
