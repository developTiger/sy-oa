package com.sunesoft.lemon.deanery.typeOfTreatise.domain;

import com.sunesoft.lemon.fr.ddd.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by pxj on 2016/12/15.
 */
@Entity
@Table(name="syy_oa_typeoftreatise")
public class TypeOfTreatise extends BaseEntity{
    /**
     * 部门id
     */
    @Column(name="dept_id")
    private Long dept_Id;

    /**
     * 论著类型名称
     */
    @Column(name="type_treatise_name",columnDefinition = "VARCHAR2(200) DEFAULT NULL")
    private String Type_Treatise_Name;

    /**
     * 部门名称
     */
    @Column(name="dept_Name",columnDefinition = "VARCHAR2(200) DEFAULT NULL")
    private String dept_Name;

    public Long getDept_Id() {
        return dept_Id;
    }

    public void setDept_Id(Long dept_Id) {
        this.dept_Id = dept_Id;
    }

    public String getType_Treatise_Name() {
        return Type_Treatise_Name;
    }

    public void setType_Treatise_Name(String type_Treatise_Name) {
        Type_Treatise_Name = type_Treatise_Name;
    }

    public String getDept_Name() {
        return dept_Name;
    }

    public void setDept_Name(String dept_Name) {
        this.dept_Name = dept_Name;
    }

    public TypeOfTreatise(){
        this.setIsActive(true);
    }

}
