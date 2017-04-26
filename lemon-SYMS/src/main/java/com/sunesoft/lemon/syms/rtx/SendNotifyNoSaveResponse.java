
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
 *         &lt;element name="sendNotifyNoSaveReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "sendNotifyNoSaveReturn"
})
@XmlRootElement(name = "sendNotifyNoSaveResponse")
public class SendNotifyNoSaveResponse {

    @XmlElement(required = true, nillable = true)
    protected String sendNotifyNoSaveReturn;

    /**
     * 获取sendNotifyNoSaveReturn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendNotifyNoSaveReturn() {
        return sendNotifyNoSaveReturn;
    }

    /**
     * 设置sendNotifyNoSaveReturn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendNotifyNoSaveReturn(String value) {
        this.sendNotifyNoSaveReturn = value;
    }

}
