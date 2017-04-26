package com.sunesoft.lemon.syms.eHr.application;

import com.sunesoft.lemon.fr.utils.DateHelper;

import java.util.Date;
import java.util.Scanner;

/**
 * Created by temp on 2016/6/12.
 */
public class test {

    public static void main(String[] args){
        Float f= getT(1);
        System.out.println(f);

    }

    private static Float getT(Integer workAge) {
        Integer t = workAge == null ? 0 : workAge;
        Float canRest = 0f;
        if (t >= 1 && t < 5) {
            canRest = 4f;
        } else if (t >= 5 && t < 10) {
            canRest = 7f;
        } else if (t >= 10 && t < 15) {
            canRest = 10f;
        } else if (t >= 15) {
            canRest = 14f;
        } else {
            canRest = 0f;
        }
        //当年疗养假请了，就设置为0
        if ( 2015 == DateHelper.getYear(new Date())) {//疗养假是今年请的，那么带薪年假今年不能请了
            canRest = 0f;
        }

        return canRest;
    }

//    private Float getShouldSpa(Integer workAge) {
//        Integer t = workAge == null ? 0 : workAge;
//        Float cSpa = 0f;
//        if (t < 10) {
//            cSpa = 0f;//少于10年，不享受疗养假
//
//        } else if (t >= 10 && t < 20) {//10-19年
//            if(this.newSpaYear==null){
//                cSpa=15f;
//
//            }else {
//                if(DateHelper.getYear(new Date())-this.newSpaYear==4){
//                    cSpa=15f;
//
//                }
//            }
//        } else if (t >= 20 && t < 29) {
//            if(this.newSpaYear==null){
//                cSpa=15f;
//
//            }else {
//                if(DateHelper.getYear(new Date())-this.newSpaYear==3){
//                    cSpa=15f;
//
//                }
//            }
//        } else {
//            if(this.newSpaYear==null){
//                cSpa=15f;
//
//            }else {
//                if(DateHelper.getYear(new Date())-this.newSpaYear==2){
//                    cSpa=15f;
//
//                }
//            }
//        }
//
////等待计算规则的确认
//
//        if ((this.newRestYear != null && this.newRestYear == DateHelper.getYear(new Date()))) {//带薪年假是今年请的，那么疗养假今年不能请了
//            cSpa = 0f;
//
//        }
//
//        return cSpa;
//    }
}
