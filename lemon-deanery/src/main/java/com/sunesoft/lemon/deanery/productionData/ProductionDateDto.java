package com.sunesoft.lemon.deanery.productionData;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zy on 2016/10/7.
 */

public class ProductionDateDto {


    /**
     * 样品批号
     */
    private String  YPPH;
    /**
     * 井号
     */
    private String  JH;
    /**
     * 报出日期
     */
    private Date BCRQ1;
    private String BCRQ;
    /**
     * 检测项目
     */
    private String  JCXM;
    /**
     * 子项目
     */
    private String  ZXM;
    /**
     * 类别
     */
    private String  LB;
    /**
     * 参数名称
     */
    private String  CSNAME;
    /**
     * 前期工序
     */
    private String  QQGX;
    /**
     * 项目组名称
     */
    private String  XMZMC;
    /**
     * 地区
     */
    private String  DQ;
    /**
     * 送样日期
     */
    private Date SYRQ1;
    private String SYRQ;
    /**
     * 委托单位
     */
    private String  WTDW;
    /**
     * 委托人
     */
    private String  WTR;
    /**
     * 结算日期
     */
    private Date JSRQ1;
    private String JSRQ;
    /**
     * 样品数量
     */
    private Long YPSL;
    /**
     * 计划数量
     */
    private Long JHSL;
    /**
     * 加工数量
     */
    private Long JGSL;

    /**
     * 原始单价
     */
    private Long YSDJ;
    /**
     * 单价
     */
    private Long DJ;
    /**
     * 金额
     */
    private Long JE;
    /**
     * 实收金额
     */
    private Long SSJE;
    /**
     * 核算岗
     */
    private String  HSG;
    /**
     * 院内
     */
    private String  YN;
    /**
     * 院外
     */
    private String  YW;
    /**
     * 实验中心
     */
    private String  SYZX;
    /**
     * 报告发送时间
     */
    private Date BGFSSJ1;
    private String BGFSSJ;
    /**
     * 报告份数
     */
    private String  BGFS;
    /**
     * 备注
     */
    private String  BZ;
    /**
     * 单位属性
     */
    private String  DWSX;
    /**
     * 课题名称
     */
    private String  KTMC;
    /**
     * 任务名称
     */
    private String  RWMC;
    /**
     * 课题负责人
     */
    private String  KTFZR;
    /**
     * 产值说明
     */
    private String  CZSM;

    /**
     * 手工录入
     */
    private String  SGLR;
    /**
     * 二级单位
     */
    private String  EJDW;
    /**
     * 结算状态
     */
    private String  JSZT;
    /**
     * 结算标识码
     */
    private String  JSBSM;
    /**
     * 基层审核码
     */
    private String  JCSHM;
    /**
     * 委托单位名称
     */
    private String  WTDWMC;

    /**
     * 委托单位编码
     */
    private String  WTDWBM;

    /**
     * 委托部门编码
     */
    private String  WTBMBM;

    /**
     * 井号编码
     */
    private String  JHBM;

    /**
     * 科研项目编码
     */
    private String  KTBM;

    /**
     * 二级单位编码
     */
    private String  EJDWBM;



    public String getYPPH() {
        return YPPH;
    }

    public void setYPPH(String YPPH) {
        this.YPPH = YPPH;
    }

    public String getJH() {
        return JH;
    }

    public void setJH(String JH) {
        this.JH = JH;
    }

    public Date getBCRQ1() {
        return BCRQ1;
    }

    public void setBCRQ1(Date BCRQ1) {
        this.BCRQ1 = BCRQ1;
    }

    public String getJCXM() {
        return JCXM;
    }

    public void setJCXM(String JCXM) {
        this.JCXM = JCXM;
    }

    public String getZXM() {
        return ZXM;
    }

    public void setZXM(String ZXM) {
        this.ZXM = ZXM;
    }

    public String getLB() {
        return LB;
    }

    public void setLB(String LB) {
        this.LB = LB;
    }

    public String getCSNAME() {
        return CSNAME;
    }

    public void setCSNAME(String CSNAME) {
        this.CSNAME = CSNAME;
    }

    public String getQQGX() {
        return QQGX;
    }

    public void setQQGX(String QQGX) {
        this.QQGX = QQGX;
    }

    public String getXMZMC() {
        return XMZMC;
    }

    public void setXMZMC(String XMZMC) {
        this.XMZMC = XMZMC;
    }

    public String getDQ() {
        return DQ;
    }

    public void setDQ(String DQ) {
        this.DQ = DQ;
    }

    public Date getSYRQ1() {
        return SYRQ1;
    }

    public void setSYRQ1(Date SYRQ1) {
        this.SYRQ1 = SYRQ1;
    }

    public String getWTDW() {
        return WTDW;
    }

    public void setWTDW(String WTDW) {
        this.WTDW = WTDW;
    }

    public String getWTR() {
        return WTR;
    }

    public void setWTR(String WTR) {
        this.WTR = WTR;
    }

    public Date getJSRQ1() {
        return JSRQ1;
    }

    public void setJSRQ1(Date JSRQ1) {
        this.JSRQ1 = JSRQ1;
    }

    public Long getYPSL() {
        return YPSL;
    }

    public void setYPSL(Long YPSL) {
        this.YPSL = YPSL;
    }

    public Long getJHSL() {
        return JHSL;
    }

    public void setJHSL(Long JHSL) {
        this.JHSL = JHSL;
    }

    public Long getJGSL() {
        return JGSL;
    }

    public void setJGSL(Long JGSL) {
        this.JGSL = JGSL;
    }

    public Long getYSDJ() {
        return YSDJ;
    }

    public void setYSDJ(Long YSDJ) {
        this.YSDJ = YSDJ;
    }

    public Long getDJ() {
        return DJ;
    }

    public void setDJ(Long DJ) {
        this.DJ = DJ;
    }

    public Long getJE() {
        return JE;
    }

    public void setJE(Long JE) {
        this.JE = JE;
    }

    public Long getSSJE() {
        return SSJE;
    }

    public void setSSJE(Long SSJE) {
        this.SSJE = SSJE;
    }

    public String getHSG() {
        return HSG;
    }

    public void setHSG(String HSG) {
        this.HSG = HSG;
    }

    public String getYN() {
        return YN;
    }

    public void setYN(String YN) {
        this.YN = YN;
    }

    public String getYW() {
        return YW;
    }

    public void setYW(String YW) {
        this.YW = YW;
    }

    public String getSYZX() {
        return SYZX;
    }

    public void setSYZX(String SYZX) {
        this.SYZX = SYZX;
    }

    public Date getBGFSSJ1() {
        return BGFSSJ1;
    }

    public void setBGFSSJ1(Date BGFSSJ1) {
        this.BGFSSJ1 = BGFSSJ1;
    }

    public String getBGFS() {
        return BGFS;
    }

    public void setBGFS(String BGFS) {
        this.BGFS = BGFS;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getDWSX() {
        return DWSX;
    }

    public void setDWSX(String DWSX) {
        this.DWSX = DWSX;
    }

    public String getKTMC() {
        return KTMC;
    }

    public void setKTMC(String KTMC) {
        this.KTMC = KTMC;
    }

    public String getRWMC() {
        return RWMC;
    }

    public void setRWMC(String RWMC) {
        this.RWMC = RWMC;
    }

    public String getKTFZR() {
        return KTFZR;
    }

    public void setKTFZR(String KTFZR) {
        this.KTFZR = KTFZR;
    }

    public String getCZSM() {
        return CZSM;
    }

    public void setCZSM(String CZSM) {
        this.CZSM = CZSM;
    }

    public String getSGLR() {
        return SGLR;
    }

    public void setSGLR(String SGLR) {
        this.SGLR = SGLR;
    }

    public String getEJDW() {
        return EJDW;
    }

    public void setEJDW(String EJDW) {
        this.EJDW = EJDW;
    }

    public String getJSZT() {
        return JSZT;
    }

    public void setJSZT(String JSZT) {
        this.JSZT = JSZT;
    }

    public String getJSBSM() {
        return JSBSM;
    }

    public void setJSBSM(String JSBSM) {
        this.JSBSM = JSBSM;
    }

    public String getJCSHM() {
        return JCSHM;
    }

    public void setJCSHM(String JCSHM) {
        this.JCSHM = JCSHM;
    }

    public String getWTDWMC() {
        return WTDWMC;
    }

    public void setWTDWMC(String WTDWMC) {
        this.WTDWMC = WTDWMC;
    }

    public String getWTDWBM() {
        return WTDWBM;
    }

    public void setWTDWBM(String WTDWBM) {
        this.WTDWBM = WTDWBM;
    }

    public String getWTBMBM() {
        return WTBMBM;
    }

    public void setWTBMBM(String WTBMBM) {
        this.WTBMBM = WTBMBM;
    }

    public String getJHBM() {
        return JHBM;
    }

    public void setJHBM(String JHBM) {
        this.JHBM = JHBM;
    }

    public String getKTBM() {
        return KTBM;
    }

    public void setKTBM(String KTBM) {
        this.KTBM = KTBM;
    }

    public String getEJDWBM() {
        return EJDWBM;
    }

    public void setEJDWBM(String EJDWBM) {
        this.EJDWBM = EJDWBM;
    }

    public String getBCRQ() {
        return BCRQ;
    }

    public void setBCRQ(String BCRQ) {
        this.BCRQ = BCRQ;
    }

    public String getSYRQ() {
        return SYRQ;
    }

    public void setSYRQ(String SYRQ) {
        this.SYRQ = SYRQ;
    }

    public String getJSRQ() {
        return JSRQ;
    }

    public void setJSRQ(String JSRQ) {
        this.JSRQ = JSRQ;
    }

    public String getBGFSSJ() {
        return BGFSSJ;
    }

    public void setBGFSSJ(String BGFSSJ) {
        this.BGFSSJ = BGFSSJ;
    }
}
