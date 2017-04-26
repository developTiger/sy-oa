
package com.sunesoft.lemon.syms.rtx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deptID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isIncludeChildDepts" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "deptID",
    "isIncludeChildDepts"
})
@XmlRootElement(name = "getDeptUsers")
public class GetDeptUsers {

    @XmlElement(required = true, nillable = true)
    protected String deptID;
    protected boolean isIncludeChildDepts;

    /**
     * 获取deptID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeptID() {
        return deptID;
    }

    /**
     * 设置deptID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeptID(String value) {
        this.deptID = value;
    }

    /**
     * 获取isIncludeChildDepts属性的值。
     * 
     */
    public boolean isIsIncludeChildDepts() {
        return isIncludeChildDepts;
    }

    /**
     * 设置isIncludeChildDepts属性的值。
     * 
     */
    public void setIsIncludeChildDepts(boolean value) {
        this.isIncludeChildDepts = value;
    }

}
