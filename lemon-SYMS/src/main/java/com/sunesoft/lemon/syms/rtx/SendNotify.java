
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
 *         &lt;element name="yhm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xxbt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xxnr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xtmc" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "yhm",
    "xxbt",
    "xxnr",
    "xtmc"
})
@XmlRootElement(name = "sendNotify")
public class SendNotify {

    @XmlElement(required = true, nillable = true)
    protected String yhm;
    @XmlElement(required = true, nillable = true)
    protected String xxbt;
    @XmlElement(required = true, nillable = true)
    protected String xxnr;
    @XmlElement(required = true, nillable = true)
    protected String xtmc;

    /**
     * 获取yhm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYhm() {
        return yhm;
    }

    /**
     * 设置yhm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYhm(String value) {
        this.yhm = value;
    }

    /**
     * 获取xxbt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXxbt() {
        return xxbt;
    }

    /**
     * 设置xxbt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXxbt(String value) {
        this.xxbt = value;
    }

    /**
     * 获取xxnr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXxnr() {
        return xxnr;
    }

    /**
     * 设置xxnr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXxnr(String value) {
        this.xxnr = value;
    }

    /**
     * 获取xtmc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXtmc() {
        return xtmc;
    }

    /**
     * 设置xtmc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXtmc(String value) {
        this.xtmc = value;
    }

}
