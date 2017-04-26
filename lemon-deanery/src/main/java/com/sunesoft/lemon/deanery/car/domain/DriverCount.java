package com.sunesoft.lemon.deanery.car.domain;

import javax.persistence.Entity;

/**
 * Created by zy
 */
public class DriverCount{

    private String driveName;
    private int ccno;
    private int ano;
    private int bno;
    private int cno;
    private int dno;

    public String getDriveName() {
        return driveName;
    }

    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }

    public int getCcno() {
        return ccno;
    }

    public void setCcno(int ccno) {
        this.ccno = ccno;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public int getDno() {
        return dno;
    }

    public void setDno(int dno) {
        this.dno = dno;
    }
}
