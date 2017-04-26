package com.sunesoft.lemon.deanery.productionData;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zy on 2016/10/7.
 */
@Entity
@Table(name = "syy_oa_ProductionDate")
public class ProductionDate extends BaseEntity {

     public ProductionDate(){
        this.setIsActive(true);
        this.setCreateDateTime(new Date());
        this.setLastUpdateTime(new Date());
    }

    /**
     * 样品批号
     */
    @Column(name = "YPPH",columnDefinition = "VARCHAR2(30) DEFAULT NULL")
    private String  YPPH;
    /**
     * 井号
     */
    @Column(name = "JH",columnDefinition = "VARCHAR2(60) DEFAULT NULL")
    private String  JH;
    /**
     * 报出日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BCRQ1")
    private Date BCRQ1;
    private String BCRQ2;
    /**
     * 检测项目
     */
    @Column(name = "JCXM",columnDefinition = "VARCHAR2(150) DEFAULT NULL")
    private String  JCXM;
    /**
     * 子项目
     */
    @Column(name = "ZXM",columnDefinition = "VARCHAR2(150) DEFAULT NULL")
    private String  ZXM;
    /**
     * 类别
     */
    @Column(name = "LB",columnDefinition = "VARCHAR2(50) DEFAULT NULL")
    private String  LB;
    /**
     * 参数名称
     */
    @Column(name = "CSNAME",columnDefinition = "VARCHAR2(50) DEFAULT NULL")
    private String  CSNAME;
    /**
     * 前期工序
     */
    @Column(name = "QQGX",columnDefinition = "VARCHAR2(1) DEFAULT NULL")
    private String  QQGX;
    /**
     * 项目组名称
     */
    @Column(name = "XMZMC",columnDefinition = "VARCHAR2(20) DEFAULT NULL")
    private String  XMZMC;
    /**
     * 地区
     */
    @Column(name = "DQ",columnDefinition = "VARCHAR2(30) DEFAULT NULL")
    private String  DQ;
    /**
     * 送样日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SYRQ1")
    private Date SYRQ1;
    private String SYRQ2;
    /**
     * 委托单位
     */
    @Column(name = "WTDW",columnDefinition = "VARCHAR2(50) DEFAULT NULL")
    private String  WTDW;
    /**
     * 委托人
     */
    @Column(name = "WTR",columnDefinition = "VARCHAR2(40) DEFAULT NULL")
    private String  WTR;
    /**
     * 结算日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JSRQ1")
    private Date JSRQ1;
    private  String JSRQ2;
    /**
     * 样品数量
     */
    @Column(name = "YPSL",columnDefinition = "NUMBER(20) DEFAULT NULL ")
    private Long YPSL;
    /**
     * 计划数量
     */
    @Column(name = "JHSL",columnDefinition = "NUMBER(20) DEFAULT NULL ")
    private Long JHSL;
    /**
     * 加工数量
     */
    @Column(name = "JGSL",columnDefinition = "NUMBER(20) DEFAULT NULL ")
    private Long JGSL;

    /**
     * 原始单价
     */
    @Column(name = "YSDJ",columnDefinition = "NUMBER(10,2) DEFAULT NULL ")
    private Long YSDJ;
    /**
     * 单价
     */
    @Column(name = "DJ",columnDefinition = "NUMBER(10,2) DEFAULT NULL ")
    private Long DJ;
    /**
     * 金额
     */
    @Column(name = "JE",columnDefinition = "NUMBER(10,2) DEFAULT NULL ")
    private Long JE;
    /**
     * 实收金额
     */
    @Column(name = "SSJE",columnDefinition = "NUMBER(10,2) DEFAULT NULL ")
    private Long SSJE;
    /**
     * 核算岗
     */
    @Column(name = "HSG",columnDefinition = "VARCHAR2(60) DEFAULT NULL")
    private String  HSG;
    /**
     * 院内
     */
    @Column(name = "YN",columnDefinition = "VARCHAR2(1) DEFAULT NULL")
    private String  YN;
    /**
     * 院外
     */
    @Column(name = "YW",columnDefinition = "VARCHAR2(1) DEFAULT NULL")
    private String  YW;
    /**
     * 实验中心
     */
    @Column(name = "SYZX",columnDefinition = "VARCHAR2(1) DEFAULT NULL")
    private String  SYZX;
    /**
     * 报告发送时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BGFSSJ1")
    private Date BGFSSJ1;
    private String BGFSSJ2;
    /**
     * 报告份数
     */
    @Column(name = "BGFS",columnDefinition = "VARCHAR2(1) DEFAULT NULL")
    private String  BGFS;
    /**
     * 备注
     */
    @Column(name = "BZ",columnDefinition = "VARCHAR2(80) DEFAULT NULL")
    private String  BZ;
    /**
     * 单位属性
     */
    @Column(name = "DWSX",columnDefinition = "VARCHAR2(8) DEFAULT NULL")
    private String  DWSX;
    /**
     * 课题名称
     */
    @Column(name = "KTMC",columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String  KTMC;
    /**
     * 任务名称
     */
    @Column(name = "RWMC",columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String  RWMC;
    /**
     * 课题负责人
     */
    @Column(name = "KTFZR",columnDefinition = "VARCHAR2(20) DEFAULT NULL")
    private String  KTFZR;
    /**
     * 产值说明
     */
    @Column(name = "CZSM",columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String  CZSM;

    /**
     * 手工录入
     */
    @Column(name = "SGLR",columnDefinition = "VARCHAR2(2) DEFAULT NULL")
    private String  SGLR;
    /**
     * 二级单位
     */
    @Column(name = "EJDW",columnDefinition = "VARCHAR2(100) DEFAULT NULL")
    private String  EJDW;
    /**
     * 结算状态
     */
    @Column(name = "JSZT",columnDefinition = "VARCHAR2(20) DEFAULT NULL")
    private String  JSZT;
    /**
     * 结算标识码
     */
    @Column(name = "JSBSM",columnDefinition = "VARCHAR2(20) DEFAULT NULL")
    private String  JSBSM;
    /**
     * 基层审核码
     */
    @Column(name = "JCSHM",columnDefinition = "VARCHAR2(20) DEFAULT NULL")
    private String  JCSHM;
    /**
     * 委托单位名称
     */
    @Column(name = "WTDWMC",columnDefinition = "VARCHAR2(20) DEFAULT NULL")
    private String  WTDWMC;

    /**
     * 委托单位编码
     */
    @Column(name = "WTDWBM",columnDefinition = "VARCHAR2(20) DEFAULT NULL")
    private String  WTDWBM;

    /**
     * 委托部门编码
     */
    @Column(name = "WTBMBM",columnDefinition = "VARCHAR2(40) DEFAULT NULL")
    private String  WTBMBM;

    /**
     * 井号编码
     */
    @Column(name = "JHBM",columnDefinition = "VARCHAR2(40) DEFAULT NULL")
    private String  JHBM;

    /**
     * 科研项目编码
     */
    @Column(name = "KTBM",columnDefinition = "VARCHAR2(40) DEFAULT NULL")
    private String  KTBM;

    /**
     * 二级单位编码
     */
    @Column(name = "EJDWBM",columnDefinition = "VARCHAR2(40) DEFAULT NULL")
    private String  EJDWBM;


    public void setBCRQ1(Date BCRQ1) {
        this.BCRQ1 = BCRQ1;
    }

    public String getBCRQ2() {
        return BCRQ2;
    }

    public void setBCRQ2(String BCRQ2) {
        this.BCRQ2 = BCRQ2;
    }

    public void setSYRQ1(Date SYRQ1) {
        this.SYRQ1 = SYRQ1;
    }

    public String getSYRQ2() {
        return SYRQ2;
    }

    public void setSYRQ2(String SYRQ2) {
        this.SYRQ2 = SYRQ2;
    }

    public void setJSRQ1(Date JSRQ1) {
        this.JSRQ1 = JSRQ1;
    }

    public String getJSRQ2() {
        return JSRQ2;
    }

    public void setJSRQ2(String JSRQ2) {
        this.JSRQ2 = JSRQ2;
    }

    public void setBGFSSJ1(Date BGFSSJ1) {
        this.BGFSSJ1 = BGFSSJ1;
    }

    public String getBGFSSJ2() {
        return BGFSSJ2;
    }

    public void setBGFSSJ2(String BGFSSJ2) {
        this.BGFSSJ2 = BGFSSJ2;
    }

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

    public void setBCRQ(Date BCRQ1) {
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

    public void setSYRQ(Date SYRQ1) {
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

    public void setJSRQ(Date JSRQ1) {
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

    public void setBGFSSJ(Date BGFSSJ1) {
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
}
