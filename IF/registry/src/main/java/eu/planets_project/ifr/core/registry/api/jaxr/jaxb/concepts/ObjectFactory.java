//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.03.17 at 04:44:31 PM CET 
//


package eu.planets_project.ifr.core.registry.api.jaxr.jaxb.concepts;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.planets_project.ifr.core.registry.api.jaxb.concepts package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.planets_project.ifr.core.registry.api.jaxb.concepts
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Command }
     * @return a new Command
     * 
     */
    public Command createCommand() {
        return new Command();
    }

    /**
     * Create an instance of {@link PredefinedConcepts }
     * @return a new predefined concepts object
     * 
     */
    public PredefinedConcepts createPredefinedConcepts() {
        return new PredefinedConcepts();
    }

    /**
     * Create an instance of {@link JAXRClassificationScheme }
     * @return a new JAXR classification scheme
     * 
     */
    public JAXRClassificationScheme createJAXRClassificationScheme() {
        return new JAXRClassificationScheme();
    }

    /**
     * Create an instance of {@link Namepattern }
     * @return a new Namepattern
     * 
     */
    public Namepattern createNamepattern() {
        return new Namepattern();
    }

    /**
     * Create an instance of {@link JAXRConcept }
     * @return a new JAXR concept
     * 
     */
    public JAXRConcept createJAXRConcept() {
        return new JAXRConcept();
    }

    /**
     * Create an instance of {@link Result }
     * @return a new Result
     */
    public Result createResult() {
        return new Result();
    }

}
