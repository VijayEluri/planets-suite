/**
 * 
 */
package eu.planets_project.services.datatypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author AnJackson
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Property {

    private String name;
    
    private String value;

    /**
     * @param name
     * @param value
     */
    public Property(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * 
     */
    public Property() {
    }

    
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
