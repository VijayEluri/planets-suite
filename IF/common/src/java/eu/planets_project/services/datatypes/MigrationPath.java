/**
 * 
 */
package eu.planets_project.services.datatypes;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Simple class to build path matrices from.  
 * 
 * Contains the input and outputs of the path, and allows for parameters for that mapping.
 * 
 * @author  <a href="mailto:Andrew.Jackson@bl.uk">Andy Jackson</a>
 */
@XmlRootElement(name = "path")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class MigrationPath {
    /**
     * A particular input format.
     */
    URI inputFormat;
    /**
     * The output format.
     */
    URI outputFormat;
    /**
     * The parameters that specifically apply to this pathway.
     */
    List<Parameter> parameters;

    /**
     * No arg constructor
     */
    protected MigrationPath() { }
    
    /**
	 * Parameterised constructor
	 *
     * @param in 
     * @param out 
     * @param pars 
     */
    public MigrationPath(URI in, URI out, List<Parameter> pars ) {
        this.inputFormat = in;
        this.outputFormat = out;
        this.parameters = pars;
    }

    /**
     * @return the inputFormat
     */
    public URI getInputFormat() {
        return inputFormat;
    }

    /**
     * @return the outputFormat
     */
    public URI getOutputFormat() {
        return outputFormat;
    }

    /**
     * @return the parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }
    
}