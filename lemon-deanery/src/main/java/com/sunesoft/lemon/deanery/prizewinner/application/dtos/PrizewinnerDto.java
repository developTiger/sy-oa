package com.sunesoft.lemon.deanery.prizewinner.application.dtos;

import java.util.Date;

/**
 * Created by xubo on 2016/7/6 0006.
 *  成果DTO
 */
public class PrizewinnerDto {

    private String cgName;

    private Long cgId;
    private String winnerInfos;
    private String isOurSystem;
    private String winnerId;
    private Integer sortNo;

    public String getCgName() {
        return cgName;
    }

    public void setCgName(String cgName) {
        this.cgName = cgName;
    }

    public Long getCgId() {
        return cgId;
    }

    public void setCgId(Long cgId) {
        this.cgId = cgId;
    }

    public String getWinnerInfos() {
        return winnerInfos;
    }

    public void setWinnerInfos(String winnerInfos) {
        this.winnerInfos = winnerInfos;
    }

    public String getIsOurSystem() {
        return isOurSystem;
    }

    public void setIsOurSystem(String isOurSystem) {
        this.isOurSystem = isOurSystem;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}
