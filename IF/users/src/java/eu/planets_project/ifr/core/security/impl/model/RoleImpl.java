package eu.planets_project.ifr.core.security.impl.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import eu.planets_project.ifr.core.security.api.model.Role;


/**
 * This is the Role entity bean definition.  It is held in a Many-To-Many relationship with the User entity.
 *
 * @see eu.planets_project.ifr.core.security.api.services.UserManager
 * @author <a href="mailto:Andrew.Jackson@bl.uk">Andy Jackson</a>
 *
 */
@Entity
@Table(name="role")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class RoleImpl implements Role,  Serializable {
    private static final long serialVersionUID = 3690197650654049848L;
    
    private Long id;
    private String name;
    private String description;

    /**
     * No arg constructor
     */
    public RoleImpl() {}

    /**
     * Constructor to create with name
     * @param name
     */
    public RoleImpl(String name) {
        this.name = name;
    }

    /** 
     * @see eu.planets_project.ifr.core.security.api.model.Role#getId()
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /** 
     * @see eu.planets_project.ifr.core.security.api.model.Role#getName()
     */
    @Column(name="name", length=20, nullable=false, unique=true)
    public String getName() {
        return this.name;
    }

    /** 
     * @see eu.planets_project.ifr.core.security.api.model.Role#getDescription()
     */
    @Column(name="description", length=64)
    public String getDescription() {
        return this.description;
    }

    /** 
     * @see eu.planets_project.ifr.core.security.api.model.Role#setId(java.lang.Long)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * @see eu.planets_project.ifr.core.security.api.model.Role#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * @see eu.planets_project.ifr.core.security.api.model.Role#setDescription(java.lang.String)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Compare two Roles. Names must be unique.
     * @param o object to compare
     * @return true if objects equal
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleImpl)) return false;

        final RoleImpl role = (RoleImpl) o;

        return !(name != null ? !name.equals(role.getName()) : role.getName() != null);
    }

    /**
     * Compute a hashcode. Names must be unique.
     * @return int hashcode
     */
    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    /**
     * Convert to a String, using the name.
     * @return String representation
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append(this.name)
        .toString();
    }
}
