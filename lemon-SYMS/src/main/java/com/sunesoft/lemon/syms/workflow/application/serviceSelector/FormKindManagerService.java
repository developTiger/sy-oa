package com.sunesoft.lemon.syms.workflow.application.serviceSelector;

/**
 * Created by zhouz on 2016/6/29.
 */
public interface FormKindManagerService {

    public FormService getFormService(String formKind);

}
