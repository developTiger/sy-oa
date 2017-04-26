package com.sunesoft.lemon.deanery.project.application.criteria;

/**
 * Created by wangwj on 2016/8/4 0004.
 */
public class AccessCriteria {
    /**
     * 当前登录用户ID
     */
    private Long currentUserId;

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }
}
