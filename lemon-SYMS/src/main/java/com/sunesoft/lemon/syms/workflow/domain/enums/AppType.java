package com.sunesoft.lemon.syms.workflow.domain.enums;

/**
 * Created by zhouz on 2016/5/30.
 */
public enum AppType {

    A ,//签核 允许否决退回
    D,// 处理 不允许否决退回
    M ,//手工签核
    B ,//无签核人(blank)
    AppValue, P, //系统自动
    F //签核 允许否决
}
