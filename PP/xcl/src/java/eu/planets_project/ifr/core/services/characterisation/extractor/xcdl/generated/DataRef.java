//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, vhudson-jaxb-ri-2.1-661
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2009.01.16 at 04:26:00 PM CET
//

package eu.planets_project.ifr.core.services.characterisation.extractor.xcdl.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;attribute name=&quot;ind&quot; use=&quot;required&quot; type=&quot;{http://www.planets-project.eu/xcl/schemas/xcl}dataRefType&quot; /&gt;
 *       &lt;attribute name=&quot;propertySetId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}IDREF&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "dataRef")
public class DataRef {

    @XmlAttribute(required = true)
    protected DataRefType ind;
    @XmlAttribute
//    @XmlIDREF
//    @XmlSchemaType(name = "IDREF")
//    protected java.lang.Object propertySetId;
    protected String propertySetId;

    /**
     * Gets the value of the ind property.
     * @return possible object is {@link DataRefType }
     */
    public DataRefType getInd() {
        return ind;
    }

    /**
     * Sets the value of the ind property.
     * @param value allowed object is {@link DataRefType }
     */
    public void setInd(DataRefType value) {
        this.ind = value;
    }

    /**
     * Gets the value of the propertySetId property.
     * @return possible object is {@link java.lang.Object }
     */
    public java.lang.Object getPropertySetId() {
        return propertySetId;
    }

    /**
     * Sets the value of the propertySetId property.
     * @param value allowed object is {@link java.lang.Object }
     */
    public void setPropertySetId(/*java.lang.Object*/String value) {
        this.propertySetId = value;
    }

}
