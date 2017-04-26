package com.sunesoft.lemon.ay.partyGroup.application.dtos;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by admin on 2016/9/5.
 */
public class WorkersProposalDto {

    /**
     * 答复意见 T3
     */
    private String answerAdvise;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 附议人
     */
    private String proposalPerson;

    /**
     * 案由
     */
    private String reason;

    /**
     * 类别   生产经营、人事劳资、劳动保护、生活福利、综合治理、其他
     */
    private String type;

    /**
     * 建议承办部门
     */
    private String undertakeDept;

    /**
     * 意见
     */
    private String suggestion;

    /**
     * 打分
     */
    private Integer score;

    /**
     * 申请时间
     */
    private Date time;
    /**
     * 提案标题
     */
    private String title;

    /**
     * 提案内容
     */
    private String content;

    public String getAnswerAdvise() {
        return answerAdvise;
    }

    public void setAnswerAdvise(String answerAdvise) {
        this.answerAdvise = answerAdvise;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProposalPerson() {
        return proposalPerson;
    }

    public void setProposalPerson(String proposalPerson) {
        this.proposalPerson = proposalPerson;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUndertakeDept() {
        return undertakeDept;
    }

    public void setUndertakeDept(String undertakeDept) {
        this.undertakeDept = undertakeDept;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
