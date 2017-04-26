
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
 *         &lt;element name="getUserSimpleInfoReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "getUserSimpleInfoReturn"
})
@XmlRootElement(name = "getUserSimpleInfoResponse")
public class GetUserSimpleInfoResponse {

    @XmlElement(required = true, nillable = true)
    protected String getUserSimpleInfoReturn;

    /**
     * 获取getUserSimpleInfoReturn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetUserSimpleInfoReturn() {
        return getUserSimpleInfoReturn;
    }

    /**
     * 设置getUserSimpleInfoReturn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetUserSimpleInfoReturn(String value) {
        this.getUserSimpleInfoReturn = value;
    }

}
