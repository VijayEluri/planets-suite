/**
 * Copyright (c) 2007, 2008, 2009, 2010 The Planets Project Partners.
 * 
 * All rights reserved. This program and the accompanying 
 * materials are made available under the terms of
 * the Apache License version 2.0 which accompanies
 * this distribution, and is available at:
 *   http://www.apache.org/licenses/LICENSE-2.0.txt
 * 
 */
package eu.planets_project.services.compare;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import eu.planets_project.services.datatypes.Property;

/**
 * This class encapsulates a comparison property.
 * 
 * That is, it encapsulates a single property related to the comparison of 
 * the content of two digital objects. The 'first' and 'second' objects.
 * 
 * In the 'significant property' case, this is the comparison of the values of 
 * a particular property that has been extracted from both files.
 * 
 * More generally, this may be a property that encapsulates the relationship 
 * between different subsets of properties from each object. i.e. when the same 
 * concept is encoded differently in each.
 * 
 * Finally, this can be a direct comparison property, like 
 * Peak Signal-to-Noise Ratio. These are not a simple comparisons of the 
 * properties of objects, but direct expression of the differences between the 
 * content or the expected performances.
 * 
 * @author Andrew.Jackson@bl.uk
 * 
 */
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public final class PropertyComparison {
    
    private Property comparison = null;
    
    private List<Property> firstProperties = null;
    
    private List<Property> secondProperties = null;
    
    enum Equivalence { 
        /** The property was found to be equal. */
        EQUAL, 
        /** The property was found to be different. */
        DIFFERENT, 
        /** The property was only found in one of the digital objects, and could not be compared. */
        MISSING,
        /** The equivalence is unknown */
        UNKNOWN
        };
        
    private Equivalence equivalence = null;
    
    

    /** For JAXB */
    protected PropertyComparison() { }

    /**
     * @param comparison
     * @param firstProperties
     * @param secondProperties
     * @param equivalence
     */
    public PropertyComparison(Property comparison,
            List<Property> firstProperties, List<Property> secondProperties,
            Equivalence equivalence) {
        super();
        this.comparison = comparison;
        this.firstProperties = firstProperties;
        this.secondProperties = secondProperties;
        this.equivalence = equivalence;
    }

    /**
     * @return the comparison
     */
    public Property getComparison() {
        return comparison;
    }

    /**
     * @return the firstProperties
     */
    public List<Property> getFirstProperties() {
        return firstProperties;
    }

    /**
     * @return the secondProperties
     */
    public List<Property> getSecondProperties() {
        return secondProperties;
    }

    /**
     * @return the equivalence
     */
    public Equivalence getEquivalence() {
        return equivalence;
    }

}
