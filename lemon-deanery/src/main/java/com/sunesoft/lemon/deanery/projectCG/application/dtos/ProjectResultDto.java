package com.sunesoft.lemon.deanery.projectCG.application.dtos;

import com.sunesoft.lemon.deanery.prizewinner.domain.Prizewinner;
import com.sunesoft.lemon.syms.workflow.application.dtos.BaseFormDto;
import com.sunesoft.lemon.syms.workflow.domain.BaseFormEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by xubo on 2016/7/6 0006.
 *  成果DTO
 */
public class ProjectResultDto extends BaseFormDto {

    private String info;


    private String win_Result_Name;

    /**
     * 获奖者对应信息
     */
    private String winner_Info;

    private String issuing_Unit;

    private String certif_No;

    private Long win_Project;

    private String win_Result_type;

    private String win_Result_Classify;

    private String win_Level;

    private String leaderOpinion;

    private String deptOpinion;



    private String win_Grade;

    private Date win_Date;

    //页面显示文本日期
    private String win_Date_Str;

    private Boolean is_Cooperate_Result;

    private List<Prizewinner> listPrizes;

    public String getWin_Result_Name() {
        return win_Result_Name;
    }

    public void setWin_Result_Name(String win_Result_Name) {
        this.win_Result_Name = win_Result_Name;
    }

    public String getIssuing_Unit() {
        return issuing_Unit;
    }

    public void setIssuing_Unit(String issuing_Unit) {
        this.issuing_Unit = issuing_Unit;
    }

    public String getCertif_No() {
        return certif_No;
    }

    public void setCertif_No(String certif_No) {
        this.certif_No = certif_No;
    }

    public Long getWin_Project() {
        return win_Project;
    }

    public void setWin_Project(Long win_Project) {
        this.win_Project = win_Project;
    }

    public String getWin_Result_Classify() {
        return win_Result_Classify;
    }

    public void setWin_Result_Classify(String win_Result_Classify) {
        this.win_Result_Classify = win_Result_Classify;
    }

    public String getWin_Level() {
        return win_Level;
    }

    public void setWin_Level(String win_Level) {
        this.win_Level = win_Level;
    }

    public String getWin_Grade() {
        return win_Grade;
    }

    public void setWin_Grade(String win_Grade) {
        this.win_Grade = win_Grade;
    }

    public Date getWin_Date() {
        return win_Date;
    }

    public void setWin_Date(Date win_Date) {
        this.win_Date = win_Date;
    }

  /*  public String getIs_Cooperate_Result_Str() {
        return is_Cooperate_Result_Str;
    }*/

  /*  public void setIs_Cooperate_Result_Str(Boolean is_Cooperate_Result_Str) {
        String str = "";
        if (is_Cooperate_Result_Str){
            str = "合作";
        }else {
            str = "不合作";
        }
        this.is_Cooperate_Result_Str = str;
    }*/

    public String getWin_Date_Str() {
        return win_Date_Str;
    }

    public void setWin_Date_Str(String win_Date_Str) {
        this.win_Date_Str = win_Date_Str;
    }

    public String getWinner_Info() {
        return winner_Info;
    }

    public void setWinner_Info(String winner_Info) {
        this.winner_Info = winner_Info;
    }

    public Boolean getIs_Cooperate_Result() {
        return is_Cooperate_Result;
    }

    public void setIs_Cooperate_Result(Boolean is_Cooperate_Result) {
        this.is_Cooperate_Result = is_Cooperate_Result;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Prizewinner> getListPrizes() {
        return listPrizes;
    }

    public void setListPrizes(List<Prizewinner> listPrizes) {
        this.listPrizes = listPrizes;
    }

    public String getWin_Result_type() {
        return win_Result_type;
    }

    public void setWin_Result_type(String win_Result_type) {
        this.win_Result_type = win_Result_type;
    }

    public String getLeaderOpinion() {
        return leaderOpinion;
    }

    public void setLeaderOpinion(String leaderOpinion) {
        this.leaderOpinion = leaderOpinion;
    }

    public String getDeptOpinion() {
        return deptOpinion;
    }

    public void setDeptOpinion(String deptOpinion) {
        this.deptOpinion = deptOpinion;
    }
}
