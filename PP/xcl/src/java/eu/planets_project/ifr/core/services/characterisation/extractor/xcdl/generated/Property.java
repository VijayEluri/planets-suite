//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, vhudson-jaxb-ri-2.1-661
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2009.01.16 at 04:26:00 PM CET
//

package eu.planets_project.ifr.core.services.characterisation.extractor.xcdl.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
 *       &lt;sequence&gt;
 *         &lt;element ref=&quot;{http://www.planets-project.eu/xcl/schemas/xcl}name&quot;/&gt;
 *         &lt;element ref=&quot;{http://www.planets-project.eu/xcl/schemas/xcl}valueSet&quot; maxOccurs=&quot;unbounded&quot;/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name=&quot;id&quot; use=&quot;required&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}ID&quot; /&gt;
 *       &lt;attribute name=&quot;source&quot; type=&quot;{http://www.planets-project.eu/xcl/schemas/xcl}sourceType&quot; /&gt;
 *       &lt;attribute name=&quot;cat&quot; type=&quot;{http://www.planets-project.eu/xcl/schemas/xcl}catType&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "valueSets" })
@XmlRootElement(name = "property")
public class Property {

    @XmlElement(required = true)
    protected Name name;
    @XmlElement(name = "valueSet", required = true)
    protected List<ValueSet> valueSets;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute
    protected SourceType source;
    @XmlAttribute
    protected CatType cat;

    /**
     * Gets the value of the name property.
     * @return possible object is {@link Name }
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * @param value allowed object is {@link Name }
     */
    public void setName(Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the valueSets property.
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the valueSets property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getValueSets().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ValueSet }
     */
    public List<ValueSet> getValueSets() {
        if (valueSets == null) {
            valueSets = new ArrayList<ValueSet>();
        }
        return this.valueSets;
    }

    /**
     * Gets the value of the id property.
     * @return possible object is {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * @param value allowed object is {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the source property.
     * @return possible object is {@link SourceType }
     */
    public SourceType getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * @param value allowed object is {@link SourceType }
     */
    public void setSource(SourceType value) {
        this.source = value;
    }

    /**
     * Gets the value of the cat property.
     * @return possible object is {@link CatType }
     */
    public CatType getCat() {
        return cat;
    }

    /**
     * Sets the value of the cat property.
     * @param value allowed object is {@link CatType }
     */
    public void setCat(CatType value) {
        this.cat = value;
    }

}
