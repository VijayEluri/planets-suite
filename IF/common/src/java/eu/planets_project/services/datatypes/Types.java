package eu.planets_project.services.datatypes;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * {@link "http://www.iana.org/assignments/media-types/"}
 * 
 * @author AnJackson
 * 
 */
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Types {

	/** An array of URIs denoting the format types */
	public URI[] types;
	/** The identification status goes here */
	public String status;

	/**
	 * No-args constructor required by JAXB
	 */
	public Types() { }

	/**
	 * 
	 * @param uris
	 * @param status
	 */
	public Types(URI[] uris, String status) {
		types = uris;
		this.status = status;
	}
}
